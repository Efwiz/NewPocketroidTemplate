package org.jetbrains.kotlin.ir.backend.js;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.WeakHashMap;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.backend.common.CompilationExceptionKt;
import org.jetbrains.kotlin.backend.common.ir.Ir;
import org.jetbrains.kotlin.backend.common.linkage.partial.PartialLinkageSupportForLoweringsImplKt;
import org.jetbrains.kotlin.backend.common.serialization.IrInterningService;
import org.jetbrains.kotlin.builtins.FunctionTypesKt;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.cli.common.arguments.K2JsArgumentConstants;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageViewDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrStatement;
import org.jetbrains.kotlin.ir.backend.js.ir.JsIrBuilder;
import org.jetbrains.kotlin.ir.backend.js.lower.JsInnerClassesSupport;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsCodeKt;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsGenerationGranularity;
import org.jetbrains.kotlin.ir.backend.js.transformers.irToJs.JsPolyfills;
import org.jetbrains.kotlin.ir.backend.js.utils.JsAnnotations;
import org.jetbrains.kotlin.ir.backend.js.utils.JsInlineClassesUtils;
import org.jetbrains.kotlin.ir.backend.js.utils.Keeper;
import org.jetbrains.kotlin.ir.backend.js.utils.MinimizedNameGenerator;
import org.jetbrains.kotlin.ir.backend.js.utils.OperatorNames;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.impl.IrExternalPackageFragmentImpl;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageConfigKt;
import org.jetbrains.kotlin.ir.linkage.partial.PartialLinkageSupportForLowerings;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFileSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.DescriptorlessExternalPackageFragmentSymbol;
import org.jetbrains.kotlin.ir.types.IrDynamicType;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeSystemContext;
import org.jetbrains.kotlin.ir.types.IrTypeSystemContextImpl;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.types.impl.IrDynamicTypeImpl;
import org.jetbrains.kotlin.ir.util.DescriptorSymbolTableExtension;
import org.jetbrains.kotlin.ir.util.IrMessageLoggerKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.js.backend.ast.JsExpression;
import org.jetbrains.kotlin.js.backend.ast.JsExpressionStatement;
import org.jetbrains.kotlin.js.backend.ast.JsFunction;
import org.jetbrains.kotlin.js.backend.ast.JsStatement;
import org.jetbrains.kotlin.js.config.ErrorTolerancePolicy;
import org.jetbrains.kotlin.js.config.JSConfigurationKeys;
import org.jetbrains.kotlin.js.config.RuntimeDiagnostic;
import org.jetbrains.kotlin.library.KotlinLibraryLayoutKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.utils.SmartList;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: JsIrBackendContext.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000Ö\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u0090\u00022\u00020\u0001:\u0002\u0090\u0002B\u0081\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\t\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0012\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0010\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0015\u0012\b\b\u0002\u0010\u0016\u001a\u00020\u0017\u0012\b\b\u0002\u0010\u0018\u001a\u00020\u0012¢\u0006\u0002\u0010\u0019J\u001b\u0010ä\u0001\u001a\u00030å\u00012\u0007\u0010æ\u0001\u001a\u00020>2\b\u0010ç\u0001\u001a\u00030¯\u0001J\u0013\u0010è\u0001\u001a\u00030Ö\u00012\u0007\u0010é\u0001\u001a\u00020fH\u0016J%\u0010ê\u0001\u001a\n\u0012\u0005\u0012\u00030ë\u00010Ý\u00012\b\u0010ì\u0001\u001a\u00030í\u00012\b\u0010î\u0001\u001a\u00030«\u0001H\u0002J\u0019\u0010ï\u0001\u001a\u00030ð\u00012\u0007\u0010ñ\u0001\u001a\u00020\nH\u0000¢\u0006\u0003\bò\u0001J\u0018\u0010ó\u0001\u001a\n\u0012\u0005\u0012\u00030ô\u00010Ý\u00012\u0007\u0010ñ\u0001\u001a\u00020\nJ\u0018\u0010õ\u0001\u001a\u00020V2\u0007\u0010ñ\u0001\u001a\u00020\nH\u0000¢\u0006\u0003\bö\u0001J\u0014\u0010÷\u0001\u001a\u0005\u0018\u00010¯\u00012\b\u0010æ\u0001\u001a\u00030®\u0001J\u0019\u0010ø\u0001\u001a\u00030ð\u00012\u0007\u0010î\u0001\u001a\u00020\fH\u0000¢\u0006\u0003\bù\u0001J\u0019\u0010ú\u0001\u001a\u00030ô\u00012\u0007\u0010î\u0001\u001a\u00020\fH\u0000¢\u0006\u0003\bû\u0001J\u0019\u0010ü\u0001\u001a\u00030ë\u00012\u0007\u0010î\u0001\u001a\u00020\fH\u0000¢\u0006\u0003\bý\u0001J)\u0010þ\u0001\u001a\u0004\u0018\u00010>2\b\u0010î\u0001\u001a\u00030«\u00012\b\u0010ÿ\u0001\u001a\u00030\u0080\u00022\n\u0010\u0081\u0002\u001a\u0005\u0018\u00010\u0080\u0002J\u0019\u0010\u0082\u0002\u001a\u00030ë\u00012\u0007\u0010ñ\u0001\u001a\u00020\nH\u0000¢\u0006\u0003\b\u0083\u0002J\u0013\u0010\u0084\u0002\u001a\u00020\u00122\b\u0010\u0085\u0002\u001a\u00030\u0086\u0002H\u0016J\u001a\u0010\u0087\u0002\u001a\u00030å\u00012\u000e\u0010\u0088\u0002\u001a\t\u0012\u0004\u0012\u00020\f0\u0089\u0002H\u0016J\u0011\u0010\u008a\u0002\u001a\n\u0012\u0005\u0012\u00030«\u00010Ý\u0001H\u0002J)\u0010\u008b\u0002\u001a\"\u0012\u0005\u0012\u00030«\u0001\u0012\u0017\u0012\u0015\u0012\u0004\u0012\u00020V\u0012\u000b\u0012\t\u0012\u0004\u0012\u00020>0¬\u00010j0jH\u0002J3\u0010\u008c\u0002\u001a\u00030å\u00012\n\u0010\u008d\u0002\u001a\u0005\u0018\u00010\u008e\u00022\t\u0010é\u0001\u001a\u0004\u0018\u00010f2\u0007\u0010\u0088\u0002\u001a\u00020\f2\u0007\u0010\u008f\u0002\u001a\u00020\u0012H\u0016R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR!\u0010\u001c\u001a\u0012\u0012\u0004\u0012\u00020\u001e0\u001dj\b\u0012\u0004\u0012\u00020\u001e`\u001f¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0011\u0010\"\u001a\u00020#¢\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u0014\u0010&\u001a\u00020'X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b(\u0010)R\u0014\u0010*\u001a\u00020+8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b,\u0010-R\u001d\u0010.\u001a\u000e\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020\f0/¢\u0006\b\n\u0000\u001a\u0004\b1\u00102R\u0014\u0010\r\u001a\u00020\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b3\u00104R\u0011\u00105\u001a\u000206¢\u0006\b\n\u0000\u001a\u0004\b7\u00108R\u0014\u00109\u001a\u00020:X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b;\u0010<R\u0014\u0010=\u001a\u00020>X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b?\u0010@R\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u0010¢\u0006\b\n\u0000\u001a\u0004\bA\u0010BR\u0017\u0010C\u001a\b\u0012\u0004\u0012\u00020E0D¢\u0006\b\n\u0000\u001a\u0004\bF\u0010GR\u001b\u0010H\u001a\u00020I8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bL\u0010M\u001a\u0004\bJ\u0010KR\u0011\u0010N\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\bO\u0010PR\u0011\u0010Q\u001a\u00020R¢\u0006\b\n\u0000\u001a\u0004\bS\u0010TR\u0014\u0010U\u001a\u00020VX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bW\u0010XR\u0013\u0010Y\u001a\u0004\u0018\u00010>¢\u0006\b\n\u0000\u001a\u0004\bZ\u0010@R\u0011\u0010[\u001a\u00020\\¢\u0006\b\n\u0000\u001a\u0004\b]\u0010^R\u0014\u0010_\u001a\u00020\u0012X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b`\u0010PR\u0011\u0010a\u001a\u00020>¢\u0006\b\n\u0000\u001a\u0004\bb\u0010@R\u001d\u0010c\u001a\u000e\u0012\u0004\u0012\u00020e\u0012\u0004\u0012\u00020f0d¢\u0006\b\n\u0000\u001a\u0004\bg\u0010hR)\u0010i\u001a\u001a\u0012\u0004\u0012\u000200\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020k\u0012\u0004\u0012\u00020\f0j0/¢\u0006\b\n\u0000\u001a\u0004\bl\u00102R\u001d\u0010m\u001a\u000e\u0012\u0004\u0012\u00020k\u0012\u0004\u0012\u00020n0/¢\u0006\b\n\u0000\u001a\u0004\bo\u00102R\u0011\u0010p\u001a\u00020q¢\u0006\b\n\u0000\u001a\u0004\br\u0010sR\u0011\u0010\u0016\u001a\u00020\u0017¢\u0006\b\n\u0000\u001a\u0004\bt\u0010uR\u001a\u0010v\u001a\u00020\u0012X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bw\u0010P\"\u0004\bx\u0010yR\u0011\u0010\u0018\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\bz\u0010PR\u0014\u0010{\u001a\u00020|X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b}\u0010~R\u0014\u0010\u007f\u001a\u00030\u0080\u0001¢\u0006\n\n\u0000\u001a\u0006\b\u0081\u0001\u0010\u0082\u0001R\u0010\u0010\u0083\u0001\u001a\u00030\u0084\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0085\u0001\u001a\u00020\nX\u0096\u0004¢\u0006\n\n\u0000\u001a\u0006\b\u0086\u0001\u0010\u0087\u0001R\u0015\u0010\u0088\u0001\u001a\u00030\u0089\u0001¢\u0006\n\n\u0000\u001a\u0006\b\u008a\u0001\u0010\u008b\u0001R\u001e\u0010\u008c\u0001\u001a\t\u0012\u0004\u0012\u00020\u00000\u008d\u0001X\u0096\u0004¢\u0006\n\n\u0000\u001a\u0006\b\u008e\u0001\u0010\u008f\u0001R\u0016\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\n\n\u0000\u001a\u0006\b\u0090\u0001\u0010\u0091\u0001R\u0018\u0010\u0092\u0001\u001a\u00030\u0093\u0001X\u0096\u0004¢\u0006\n\n\u0000\u001a\u0006\b\u0094\u0001\u0010\u0095\u0001R\u0015\u0010\u0096\u0001\u001a\u00030\u0097\u0001¢\u0006\n\n\u0000\u001a\u0006\b\u0098\u0001\u0010\u0099\u0001R\u0013\u0010\u009a\u0001\u001a\u00020>¢\u0006\t\n\u0000\u001a\u0005\b\u009b\u0001\u0010@R\u0013\u0010\u009c\u0001\u001a\u00020>¢\u0006\t\n\u0000\u001a\u0005\b\u009d\u0001\u0010@R\u001f\u0010\u009e\u0001\u001a\u000e\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020\f0/¢\u0006\t\n\u0000\u001a\u0005\b\u009f\u0001\u00102R\u0016\u0010\u0014\u001a\u00020\u0015X\u0096\u0004¢\u0006\n\n\u0000\u001a\u0006\b \u0001\u0010¡\u0001R\u0015\u0010¢\u0001\u001a\u00030£\u0001¢\u0006\n\n\u0000\u001a\u0006\b¤\u0001\u0010¥\u0001R\u0013\u0010\u0002\u001a\u00020\u0003¢\u0006\n\n\u0000\u001a\u0006\b¦\u0001\u0010§\u0001R\u0013\u0010¨\u0001\u001a\u00020>¢\u0006\t\n\u0000\u001a\u0005\b©\u0001\u0010@R/\u0010ª\u0001\u001a\"\u0012\u0005\u0012\u00030«\u0001\u0012\u0017\u0012\u0015\u0012\u0004\u0012\u00020V\u0012\u000b\u0012\t\u0012\u0004\u0012\u00020>0¬\u00010j0jX\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\u00ad\u0001\u001a\u0010\u0012\u0005\u0012\u00030®\u0001\u0012\u0005\u0012\u00030¯\u00010/X\u0082\u0004¢\u0006\u0002\n\u0000R#\u0010°\u0001\u001a\u0012\u0012\u0004\u0012\u00020f0\u001dj\b\u0012\u0004\u0012\u00020f`\u001f¢\u0006\t\n\u0000\u001a\u0005\b±\u0001\u0010!R\u0018\u0010²\u0001\u001a\u00030³\u0001X\u0096\u0004¢\u0006\n\n\u0000\u001a\u0006\b´\u0001\u0010µ\u0001R\u0015\u0010¶\u0001\u001a\u00030·\u0001¢\u0006\n\n\u0000\u001a\u0006\b¸\u0001\u0010¹\u0001R \u0010º\u0001\u001a\u000f\u0012\u0005\u0012\u00030«\u0001\u0012\u0004\u0012\u00020V0j¢\u0006\t\n\u0000\u001a\u0005\b»\u0001\u0010hR\u0018\u0010¼\u0001\u001a\u00030½\u0001X\u0096\u0004¢\u0006\n\n\u0000\u001a\u0006\b¾\u0001\u0010¿\u0001R\u0018\u0010À\u0001\u001a\u00030Á\u00018VX\u0096\u0004¢\u0006\b\u001a\u0006\bÂ\u0001\u0010Ã\u0001R\u0012\u0010\u0011\u001a\u00020\u0012¢\u0006\t\n\u0000\u001a\u0005\bÄ\u0001\u0010PR\u0014\u0010\u0013\u001a\u0004\u0018\u00010\u0010¢\u0006\t\n\u0000\u001a\u0005\bÅ\u0001\u0010BR\u0016\u0010Æ\u0001\u001a\u00020\u00128VX\u0096\u0004¢\u0006\u0007\u001a\u0005\bÇ\u0001\u0010PR\u0013\u0010È\u0001\u001a\u00020>¢\u0006\t\n\u0000\u001a\u0005\bÉ\u0001\u0010@R\u0018\u0010Ê\u0001\u001a\u00030Ë\u0001X\u0096\u0004¢\u0006\n\n\u0000\u001a\u0006\bÌ\u0001\u0010Í\u0001R\u0018\u0010Î\u0001\u001a\u0004\u0018\u00010>X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bÏ\u0001\u0010@R\u0013\u0010\u0006\u001a\u00020\u0007¢\u0006\n\n\u0000\u001a\u0006\bÐ\u0001\u0010Ñ\u0001R\u0018\u0010Ò\u0001\u001a\u0004\u0018\u00010>X\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bÓ\u0001\u0010@R4\u0010Ô\u0001\u001a\"\u0012\u0004\u0012\u00020f\u0012\u0005\u0012\u00030Ö\u00010Õ\u0001j\u0010\u0012\u0004\u0012\u00020f\u0012\u0005\u0012\u00030Ö\u0001`×\u0001¢\u0006\n\n\u0000\u001a\u0006\bØ\u0001\u0010Ù\u0001R\u0013\u0010Ú\u0001\u001a\u00020V¢\u0006\t\n\u0000\u001a\u0005\bÛ\u0001\u0010XR%\u0010Ü\u0001\u001a\t\u0012\u0004\u0012\u00020I0Ý\u00018FX\u0086\u0084\u0002¢\u0006\u000e\n\u0005\bß\u0001\u0010M\u001a\u0005\bÞ\u0001\u0010GR\u0018\u0010à\u0001\u001a\u00030á\u0001X\u0096\u0004¢\u0006\n\n\u0000\u001a\u0006\bâ\u0001\u0010ã\u0001¨\u0006\u0091\u0002"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext;", "Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "additionalExportedDeclarationNames", "", "Lorg/jetbrains/kotlin/name/FqName;", "keep", "", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "dceRuntimeDiagnostic", "Lorg/jetbrains/kotlin/js/config/RuntimeDiagnostic;", "safeExternalBoolean", "", "safeExternalBooleanDiagnostic", "mapping", "Lorg/jetbrains/kotlin/ir/backend/js/JsMapping;", "granularity", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsGenerationGranularity;", "incrementalCacheEnabled", "(Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/ir/IrBuiltIns;Lorg/jetbrains/kotlin/ir/util/SymbolTable;Ljava/util/Set;Ljava/util/Set;Lorg/jetbrains/kotlin/config/CompilerConfiguration;Lorg/jetbrains/kotlin/js/config/RuntimeDiagnostic;ZLorg/jetbrains/kotlin/js/config/RuntimeDiagnostic;Lorg/jetbrains/kotlin/ir/backend/js/JsMapping;Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsGenerationGranularity;Z)V", "getAdditionalExportedDeclarationNames", "()Ljava/util/Set;", "additionalExportedDeclarations", "Ljava/util/HashSet;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "Lkotlin/collections/HashSet;", "getAdditionalExportedDeclarations", "()Ljava/util/HashSet;", "bodilessBuiltInsPackageFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrPackageFragment;", "getBodilessBuiltInsPackageFragment", "()Lorg/jetbrains/kotlin/ir/declarations/IrPackageFragment;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "getBuiltIns", "()Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "catchAllThrowableType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "getCatchAllThrowableType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "classToItsId", "Ljava/util/WeakHashMap;", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getClassToItsId", "()Ljava/util/WeakHashMap;", "getConfiguration", "()Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "coroutineEmptyContinuation", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "getCoroutineEmptyContinuation", "()Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "coroutineSymbols", "Lorg/jetbrains/kotlin/ir/backend/js/JsCommonCoroutineSymbols;", "getCoroutineSymbols", "()Lorg/jetbrains/kotlin/ir/backend/js/JsCommonCoroutineSymbols;", "createEnumEntries", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "getCreateEnumEntries", "()Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "getDceRuntimeDiagnostic", "()Lorg/jetbrains/kotlin/js/config/RuntimeDiagnostic;", "declarationLevelJsModules", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithName;", "getDeclarationLevelJsModules", "()Ljava/util/List;", "defaultThrowableCtor", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "getDefaultThrowableCtor", "()Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "defaultThrowableCtor$delegate", "Lkotlin/Lazy;", "devMode", "getDevMode", "()Z", "dynamicType", "Lorg/jetbrains/kotlin/ir/types/IrDynamicType;", "getDynamicType", "()Lorg/jetbrains/kotlin/ir/types/IrDynamicType;", "enumEntries", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "getEnumEntries", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "errorCodeSymbol", "getErrorCodeSymbol", "errorPolicy", "Lorg/jetbrains/kotlin/js/config/ErrorTolerancePolicy;", "getErrorPolicy", "()Lorg/jetbrains/kotlin/js/config/ErrorTolerancePolicy;", "es6mode", "getEs6mode", "extendThrowableSymbol", "getExtendThrowableSymbol", "externalPackageFragment", "", "Lorg/jetbrains/kotlin/ir/symbols/IrFileSymbol;", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "getExternalPackageFragment", "()Ljava/util/Map;", "fieldDataCache", "", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "getFieldDataCache", "fieldToInitializer", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "getFieldToInitializer", "globalInternationService", "Lorg/jetbrains/kotlin/backend/common/serialization/IrInterningService;", "getGlobalInternationService", "()Lorg/jetbrains/kotlin/backend/common/serialization/IrInterningService;", "getGranularity", "()Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsGenerationGranularity;", "inVerbosePhase", "getInVerbosePhase", "setInVerbosePhase", "(Z)V", "getIncrementalCacheEnabled", "inlineClassesUtils", "Lorg/jetbrains/kotlin/ir/backend/js/utils/JsInlineClassesUtils;", "getInlineClassesUtils", "()Lorg/jetbrains/kotlin/ir/backend/js/utils/JsInlineClassesUtils;", "innerClassesSupport", "Lorg/jetbrains/kotlin/ir/backend/js/lower/JsInnerClassesSupport;", "getInnerClassesSupport", "()Lorg/jetbrains/kotlin/ir/backend/js/lower/JsInnerClassesSupport;", "internalPackage", "Lorg/jetbrains/kotlin/descriptors/PackageViewDescriptor;", "internalPackageFqn", "getInternalPackageFqn", "()Lorg/jetbrains/kotlin/name/FqName;", "intrinsics", "Lorg/jetbrains/kotlin/ir/backend/js/JsIntrinsics;", "getIntrinsics", "()Lorg/jetbrains/kotlin/ir/backend/js/JsIntrinsics;", KotlinLibraryLayoutKt.KLIB_IR_FOLDER_NAME, "Lorg/jetbrains/kotlin/backend/common/ir/Ir;", "getIr", "()Lorg/jetbrains/kotlin/backend/common/ir/Ir;", "getIrBuiltIns", "()Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "getIrFactory", "()Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "keeper", "Lorg/jetbrains/kotlin/ir/backend/js/utils/Keeper;", "getKeeper", "()Lorg/jetbrains/kotlin/ir/backend/js/utils/Keeper;", "klocalDelegateBuilder", "getKlocalDelegateBuilder", "kpropertyBuilder", "getKpropertyBuilder", "localClassNames", "getLocalClassNames", "getMapping", "()Lorg/jetbrains/kotlin/ir/backend/js/JsMapping;", "minimizedNameGenerator", "Lorg/jetbrains/kotlin/ir/backend/js/utils/MinimizedNameGenerator;", "getMinimizedNameGenerator", "()Lorg/jetbrains/kotlin/ir/backend/js/utils/MinimizedNameGenerator;", "getModule", "()Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "newThrowableSymbol", "getNewThrowableSymbol", "operatorMap", "Lorg/jetbrains/kotlin/name/Name;", "", "outlinedJsCodeFunctions", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "Lorg/jetbrains/kotlin/js/backend/ast/JsFunction;", "packageLevelJsModules", "getPackageLevelJsModules", "partialLinkageSupport", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageSupportForLowerings;", "getPartialLinkageSupport", "()Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageSupportForLowerings;", "polyfills", "Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsPolyfills;", "getPolyfills", "()Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsPolyfills;", "primitiveCompanionObjects", "getPrimitiveCompanionObjects", "propertyLazyInitialization", "Lorg/jetbrains/kotlin/ir/backend/js/PropertyLazyInitialization;", "getPropertyLazyInitialization", "()Lorg/jetbrains/kotlin/ir/backend/js/PropertyLazyInitialization;", "reflectionSymbols", "Lorg/jetbrains/kotlin/ir/backend/js/ReflectionSymbols;", "getReflectionSymbols", "()Lorg/jetbrains/kotlin/ir/backend/js/ReflectionSymbols;", "getSafeExternalBoolean", "getSafeExternalBooleanDiagnostic", "scriptMode", "getScriptMode", "setPropertiesToThrowableInstanceSymbol", "getSetPropertiesToThrowableInstanceSymbol", "sharedVariablesManager", "Lorg/jetbrains/kotlin/ir/backend/js/JsSharedVariablesManager;", "getSharedVariablesManager", "()Lorg/jetbrains/kotlin/ir/backend/js/JsSharedVariablesManager;", "suiteFun", "getSuiteFun", "getSymbolTable", "()Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "testFun", "getTestFun", "testFunsPerFile", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "Lkotlin/collections/HashMap;", "getTestFunsPerFile", "()Ljava/util/HashMap;", "throwableClass", "getThrowableClass", "throwableConstructors", "", "getThrowableConstructors", "throwableConstructors$delegate", "typeSystem", "Lorg/jetbrains/kotlin/ir/types/IrTypeSystemContext;", "getTypeSystem", "()Lorg/jetbrains/kotlin/ir/types/IrTypeSystemContext;", "addOutlinedJsCode", "", NativeSymbol.TYPE_NAME, "outlinedJsCode", "createTestContainerFun", "irFile", "findProperty", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "memberScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "name", "getClass", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "fqName", "getClass$backend_js", "getFunctions", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "getIrClass", "getIrClass$backend_js", "getJsCodeForFunction", "getJsInternalClass", "getJsInternalClass$backend_js", "getJsInternalFunction", "getJsInternalFunction$backend_js", "getJsInternalProperty", "getJsInternalProperty$backend_js", "getOperatorByName", "lhsType", "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "rhsType", "getProperty", "getProperty$backend_js", "isSideEffectFree", "call", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", K2JsArgumentConstants.RUNTIME_DIAGNOSTIC_LOG, CrashHianalyticsData.MESSAGE, "Lkotlin/Function0;", "primitivesWithImplicitCompanionObject", "referenceOperators", "report", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/ir/IrElement;", "isError", "Companion", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsIrBackendContext implements JsCommonBackendContext {
    private static final String COROUTINE_SUSPEND_OR_RETURN_JS_NAME;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final FqName ENUMS_PACKAGE_FQNAME;
    private static final String GET_COROUTINE_CONTEXT_NAME;
    private static final FqName JS_INTERNAL_PACKAGE_FQNAME;
    private static final FqName JS_PACKAGE_FQNAME;
    private static final FqName JS_POLYFILLS_PACKAGE;
    private static final FqName KOTLIN_PACKAGE_FQN;
    private static final FqName REFLECT_PACKAGE_FQNAME;
    private final Set<FqName> additionalExportedDeclarationNames;
    private final HashSet<IrDeclaration> additionalExportedDeclarations;
    private final IrPackageFragment bodilessBuiltInsPackageFragment;
    private final KotlinBuiltIns builtIns;
    private final WeakHashMap<IrClass, String> classToItsId;
    private final CompilerConfiguration configuration;
    private final IrPropertySymbol coroutineEmptyContinuation;
    private final JsCommonCoroutineSymbols coroutineSymbols;
    private final IrSimpleFunctionSymbol createEnumEntries;
    private final RuntimeDiagnostic dceRuntimeDiagnostic;
    private final List<IrDeclarationWithName> declarationLevelJsModules;

    /* JADX INFO: renamed from: defaultThrowableCtor$delegate, reason: from kotlin metadata */
    private final Lazy defaultThrowableCtor;
    private final boolean devMode;
    private final IrDynamicType dynamicType;
    private final IrClassSymbol enumEntries;
    private final IrSimpleFunctionSymbol errorCodeSymbol;
    private final ErrorTolerancePolicy errorPolicy;
    private final boolean es6mode;
    private final IrSimpleFunctionSymbol extendThrowableSymbol;
    private final Map<IrFileSymbol, IrFile> externalPackageFragment;
    private final WeakHashMap<IrClass, Map<IrField, String>> fieldDataCache;
    private final WeakHashMap<IrField, IrExpression> fieldToInitializer;
    private final IrInterningService globalInternationService;
    private final JsGenerationGranularity granularity;
    private boolean inVerbosePhase;
    private final boolean incrementalCacheEnabled;
    private final JsInlineClassesUtils inlineClassesUtils;
    private final JsInnerClassesSupport innerClassesSupport;
    private final PackageViewDescriptor internalPackage;
    private final FqName internalPackageFqn;
    private final JsIntrinsics intrinsics;
    private final Ir<JsIrBackendContext> ir;
    private final IrBuiltIns irBuiltIns;
    private final IrFactory irFactory;
    private final Keeper keeper;
    private final IrSimpleFunctionSymbol klocalDelegateBuilder;
    private final IrSimpleFunctionSymbol kpropertyBuilder;
    private final WeakHashMap<IrClass, String> localClassNames;
    private final JsMapping mapping;
    private final MinimizedNameGenerator minimizedNameGenerator;
    private final ModuleDescriptor module;
    private final IrSimpleFunctionSymbol newThrowableSymbol;
    private final Map<Name, Map<IrClassSymbol, Collection<IrSimpleFunctionSymbol>>> operatorMap;
    private final WeakHashMap<IrFunctionSymbol, JsFunction> outlinedJsCodeFunctions;
    private final HashSet<IrFile> packageLevelJsModules;
    private final PartialLinkageSupportForLowerings partialLinkageSupport;
    private final JsPolyfills polyfills;
    private final Map<Name, IrClassSymbol> primitiveCompanionObjects;
    private final PropertyLazyInitialization propertyLazyInitialization;
    private final boolean safeExternalBoolean;
    private final RuntimeDiagnostic safeExternalBooleanDiagnostic;
    private final IrSimpleFunctionSymbol setPropertiesToThrowableInstanceSymbol;
    private final JsSharedVariablesManager sharedVariablesManager;
    private final IrSimpleFunctionSymbol suiteFun;
    private final SymbolTable symbolTable;
    private final IrSimpleFunctionSymbol testFun;
    private final HashMap<IrFile, IrSimpleFunction> testFunsPerFile;
    private final IrClassSymbol throwableClass;

    /* JADX INFO: renamed from: throwableConstructors$delegate, reason: from kotlin metadata */
    private final Lazy throwableConstructors;
    private final IrTypeSystemContext typeSystem;

    public final SimpleFunctionDescriptor getJsInternalFunction$backend_js(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        MemberScope memberScope = this.internalPackage.getMemberScope();
        Name nameIdentifier = Name.identifier(name2);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        SimpleFunctionDescriptor simpleFunctionDescriptor = (SimpleFunctionDescriptor) CollectionsKt.singleOrNull((List) JsCommonBackendContextKt.findFunctions(memberScope, nameIdentifier));
        if (simpleFunctionDescriptor != null) {
            return simpleFunctionDescriptor;
        }
        throw new IllegalStateException(("Internal function '" + name2 + "' not found").toString());
    }

    public final PropertyDescriptor getJsInternalProperty$backend_js(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        MemberScope memberScope = this.internalPackage.getMemberScope();
        Name nameIdentifier = Name.identifier(name2);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        PropertyDescriptor propertyDescriptor = (PropertyDescriptor) CollectionsKt.singleOrNull((List) findProperty(memberScope, nameIdentifier));
        if (propertyDescriptor != null) {
            return propertyDescriptor;
        }
        throw new IllegalStateException(("Internal function '" + name2 + "' not found").toString());
    }

    @Override // org.jetbrains.kotlin.backend.common.CommonBackendContext
    public boolean getScriptMode() {
        return false;
    }

    public JsIrBackendContext(ModuleDescriptor module, IrBuiltIns irBuiltIns, SymbolTable symbolTable, Set<FqName> additionalExportedDeclarationNames, Set<String> keep, CompilerConfiguration configuration, RuntimeDiagnostic runtimeDiagnostic, boolean z, RuntimeDiagnostic runtimeDiagnostic2, JsMapping mapping, JsGenerationGranularity granularity, boolean z2) {
        Object next;
        KotlinType type;
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        Intrinsics.checkNotNullParameter(additionalExportedDeclarationNames, "additionalExportedDeclarationNames");
        Intrinsics.checkNotNullParameter(keep, "keep");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        Intrinsics.checkNotNullParameter(mapping, "mapping");
        Intrinsics.checkNotNullParameter(granularity, "granularity");
        this.module = module;
        this.irBuiltIns = irBuiltIns;
        this.symbolTable = symbolTable;
        this.additionalExportedDeclarationNames = additionalExportedDeclarationNames;
        this.configuration = configuration;
        this.dceRuntimeDiagnostic = runtimeDiagnostic;
        this.safeExternalBoolean = z;
        this.safeExternalBooleanDiagnostic = runtimeDiagnostic2;
        this.mapping = mapping;
        this.granularity = granularity;
        this.incrementalCacheEnabled = z2;
        this.polyfills = new JsPolyfills();
        this.fieldToInitializer = new WeakHashMap<>();
        this.globalInternationService = new IrInterningService();
        this.localClassNames = new WeakHashMap<>();
        this.classToItsId = new WeakHashMap<>();
        this.minimizedNameGenerator = new MinimizedNameGenerator();
        this.keeper = new Keeper(keep);
        this.fieldDataCache = new WeakHashMap<>();
        this.builtIns = module.getBuiltIns();
        this.typeSystem = new IrTypeSystemContextImpl(getIrBuiltIns());
        this.irFactory = symbolTable.getIrFactory();
        Boolean bool = (Boolean) getConfiguration().get(JSConfigurationKeys.DEVELOPER_MODE);
        Boolean bool2 = false;
        this.devMode = (bool == null ? bool2 : bool).booleanValue();
        ErrorTolerancePolicy errorTolerancePolicy = (ErrorTolerancePolicy) getConfiguration().get(JSConfigurationKeys.ERROR_TOLERANCE_POLICY);
        errorTolerancePolicy = errorTolerancePolicy == null ? ErrorTolerancePolicy.INSTANCE.getDEFAULT() : errorTolerancePolicy;
        Intrinsics.checkNotNull(errorTolerancePolicy);
        this.errorPolicy = errorTolerancePolicy;
        Boolean bool3 = (Boolean) getConfiguration().get(JSConfigurationKeys.USE_ES6_CLASSES);
        this.es6mode = (bool3 != null ? bool3 : false).booleanValue();
        this.externalPackageFragment = new LinkedHashMap();
        this.additionalExportedDeclarations = new HashSet<>();
        this.bodilessBuiltInsPackageFragment = new IrExternalPackageFragmentImpl(new DescriptorlessExternalPackageFragmentSymbol(), new FqName(IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME));
        this.packageLevelJsModules = new HashSet<>();
        this.declarationLevelJsModules = new ArrayList();
        this.testFunsPerFile = new HashMap<>();
        this.inlineClassesUtils = new JsInlineClassesUtils(this);
        this.innerClassesSupport = new JsInnerClassesSupport(getMapping(), getIrFactory());
        FqName fqName = JS_PACKAGE_FQNAME;
        this.internalPackage = module.getPackage(fqName);
        this.dynamicType = new IrDynamicTypeImpl(null, CollectionsKt.emptyList(), Variance.INVARIANT);
        this.intrinsics = new JsIntrinsics(getIrBuiltIns(), this);
        Object obj = getConfiguration().get(JSConfigurationKeys.PROPERTY_LAZY_INITIALIZATION, true);
        Intrinsics.checkNotNullExpressionValue(obj, "get(...)");
        this.propertyLazyInitialization = new PropertyLazyInitialization(((Boolean) obj).booleanValue(), symbolTable.getDescriptorExtension().referenceClass(getJsInternalClass$backend_js("EagerInitialization")));
        this.sharedVariablesManager = new JsSharedVariablesManager(this);
        this.internalPackageFqn = fqName;
        this.operatorMap = referenceOperators();
        this.coroutineSymbols = new JsCommonCoroutineSymbols(symbolTable, module, this);
        FqName fqName2 = ENUMS_PACKAGE_FQNAME;
        FqName fqNameChild = fqName2.child(Name.identifier("EnumEntries"));
        Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
        this.enumEntries = getIrClass$backend_js(fqNameChild);
        FqName fqNameChild2 = fqName2.child(Name.identifier("enumEntries"));
        Intrinsics.checkNotNullExpressionValue(fqNameChild2, "child(...)");
        Iterator<T> it = getFunctions(fqNameChild2).iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            List<ValueParameterDescriptor> valueParameters = ((SimpleFunctionDescriptor) next).getValueParameters();
            Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
            ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) CollectionsKt.firstOrNull((List) valueParameters);
            if (valueParameterDescriptor != null && (type = valueParameterDescriptor.getType()) != null) {
                Intrinsics.checkNotNull(type);
                if (!FunctionTypesKt.isFunctionType(type)) {
                    break;
                }
            }
        }
        SimpleFunctionDescriptor simpleFunctionDescriptor = (SimpleFunctionDescriptor) next;
        DescriptorSymbolTableExtension descriptorExtension = this.symbolTable.getDescriptorExtension();
        Intrinsics.checkNotNull(simpleFunctionDescriptor);
        this.createEnumEntries = descriptorExtension.referenceSimpleFunction(simpleFunctionDescriptor);
        this.ir = new Ir<JsIrBackendContext>(this) { // from class: org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext$ir$1
            private final JsIrBackendContext$ir$1$symbols$1 symbols;

            @Override // org.jetbrains.kotlin.backend.common.ir.Ir
            public boolean shouldGenerateHandlerParameterForDefaultBodyFun() {
                return true;
            }

            {
                super(this);
                this.symbols = new JsIrBackendContext$ir$1$symbols$1(this, this.getIrBuiltIns(), this.getSymbolTable());
            }

            @Override // org.jetbrains.kotlin.backend.common.ir.Ir
            public JsIrBackendContext$ir$1$symbols$1 getSymbols() {
                return this.symbols;
            }
        };
        this.errorCodeSymbol = this.errorPolicy.getAllowErrors() ? this.symbolTable.getDescriptorExtension().referenceSimpleFunction(getJsInternalFunction$backend_js("errorCode")) : null;
        FqName fqNameChild3 = KOTLIN_PACKAGE_FQN.child(Name.identifier("Throwable"));
        Intrinsics.checkNotNullExpressionValue(fqNameChild3, "child(...)");
        this.throwableClass = getIrClass$backend_js(fqNameChild3);
        List<Name> listPrimitivesWithImplicitCompanionObject = primitivesWithImplicitCompanionObject();
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(listPrimitivesWithImplicitCompanionObject, 10)), 16));
        for (Object obj2 : listPrimitivesWithImplicitCompanionObject) {
            FqName fqNameChild4 = JS_INTERNAL_PACKAGE_FQNAME.child(Name.identifier(((Name) obj2).getIdentifier() + "CompanionObject"));
            Intrinsics.checkNotNullExpressionValue(fqNameChild4, "child(...)");
            linkedHashMap.put(obj2, getIrClass$backend_js(fqNameChild4));
        }
        this.primitiveCompanionObjects = linkedHashMap;
        DescriptorSymbolTableExtension descriptorExtension2 = this.symbolTable.getDescriptorExtension();
        FqName fqNameFromSegments = FqName.fromSegments(CollectionsKt.listOf((Object[]) new String[]{IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME, "coroutines", "js", "internal", "EmptyContinuation"}));
        Intrinsics.checkNotNullExpressionValue(fqNameFromSegments, "fromSegments(...)");
        this.coroutineEmptyContinuation = descriptorExtension2.referenceProperty(getProperty$backend_js(fqNameFromSegments));
        this.newThrowableSymbol = this.symbolTable.getDescriptorExtension().referenceSimpleFunction(getJsInternalFunction$backend_js("newThrowable"));
        this.extendThrowableSymbol = this.symbolTable.getDescriptorExtension().referenceSimpleFunction(getJsInternalFunction$backend_js("extendThrowable"));
        this.setPropertiesToThrowableInstanceSymbol = this.symbolTable.getDescriptorExtension().referenceSimpleFunction(getJsInternalFunction$backend_js("setPropertiesToThrowableInstance"));
        SimpleFunctionDescriptor simpleFunctionDescriptor2 = (SimpleFunctionDescriptor) CollectionsKt.singleOrNull((List) getFunctions(new FqName("kotlin.test.suite")));
        this.suiteFun = simpleFunctionDescriptor2 != null ? this.symbolTable.getDescriptorExtension().referenceSimpleFunction(simpleFunctionDescriptor2) : null;
        SimpleFunctionDescriptor simpleFunctionDescriptor3 = (SimpleFunctionDescriptor) CollectionsKt.singleOrNull((List) getFunctions(new FqName("kotlin.test.test")));
        this.testFun = simpleFunctionDescriptor3 != null ? this.symbolTable.getDescriptorExtension().referenceSimpleFunction(simpleFunctionDescriptor3) : null;
        JsIrBackendContext jsIrBackendContext = this;
        this.throwableConstructors = JsCommonBackendContextKt.lazy2(jsIrBackendContext, new Function0<List<? extends IrConstructorSymbol>>() { // from class: org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext$throwableConstructors$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends IrConstructorSymbol> invoke() {
                List<IrDeclaration> declarations = this.this$0.getThrowableClass().getOwner().getDeclarations();
                ArrayList arrayList = new ArrayList();
                for (Object obj3 : declarations) {
                    if (obj3 instanceof IrConstructor) {
                        arrayList.add(obj3);
                    }
                }
                ArrayList arrayList2 = arrayList;
                ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
                Iterator it2 = arrayList2.iterator();
                while (it2.hasNext()) {
                    arrayList3.add(((IrConstructor) it2.next()).getSymbol());
                }
                return arrayList3;
            }
        });
        this.defaultThrowableCtor = JsCommonBackendContextKt.lazy2(jsIrBackendContext, new Function0<IrConstructorSymbol>() { // from class: org.jetbrains.kotlin.ir.backend.js.JsIrBackendContext$defaultThrowableCtor$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrConstructorSymbol invoke() {
                Object obj3 = null;
                boolean z3 = false;
                for (Object obj4 : this.this$0.getThrowableConstructors()) {
                    IrConstructorSymbol irConstructorSymbol = (IrConstructorSymbol) obj4;
                    if (!irConstructorSymbol.getOwner().getIsPrimary() && irConstructorSymbol.getOwner().getValueParameters().size() == 0) {
                        if (z3) {
                            throw new IllegalArgumentException("Collection contains more than one matching element.");
                        }
                        z3 = true;
                        obj3 = obj4;
                    }
                }
                if (!z3) {
                    throw new NoSuchElementException("Collection contains no element matching the predicate.");
                }
                return (IrConstructorSymbol) obj3;
            }
        });
        this.kpropertyBuilder = this.symbolTable.getDescriptorExtension().referenceSimpleFunction((SimpleFunctionDescriptor) CollectionsKt.single((List) getFunctions(new FqName("kotlin.js.getPropertyCallableRef"))));
        this.klocalDelegateBuilder = this.symbolTable.getDescriptorExtension().referenceSimpleFunction((SimpleFunctionDescriptor) CollectionsKt.single((List) getFunctions(new FqName("kotlin.js.getLocalDelegateReference"))));
        this.outlinedJsCodeFunctions = new WeakHashMap<>();
        this.partialLinkageSupport = PartialLinkageSupportForLoweringsImplKt.createPartialLinkageSupportForLowerings(PartialLinkageConfigKt.getPartialLinkageConfig(getConfiguration()), getIrBuiltIns(), IrMessageLoggerKt.getIrMessageLogger(getConfiguration()));
    }

    public final ModuleDescriptor getModule() {
        return this.module;
    }

    @Override // org.jetbrains.kotlin.backend.common.BackendContext
    public IrBuiltIns getIrBuiltIns() {
        return this.irBuiltIns;
    }

    public final SymbolTable getSymbolTable() {
        return this.symbolTable;
    }

    public final Set<FqName> getAdditionalExportedDeclarationNames() {
        return this.additionalExportedDeclarationNames;
    }

    @Override // org.jetbrains.kotlin.backend.common.CommonBackendContext
    public CompilerConfiguration getConfiguration() {
        return this.configuration;
    }

    public final RuntimeDiagnostic getDceRuntimeDiagnostic() {
        return this.dceRuntimeDiagnostic;
    }

    public final boolean getSafeExternalBoolean() {
        return this.safeExternalBoolean;
    }

    public final RuntimeDiagnostic getSafeExternalBooleanDiagnostic() {
        return this.safeExternalBooleanDiagnostic;
    }

    public /* synthetic */ JsIrBackendContext(ModuleDescriptor moduleDescriptor, IrBuiltIns irBuiltIns, SymbolTable symbolTable, Set set, Set set2, CompilerConfiguration compilerConfiguration, RuntimeDiagnostic runtimeDiagnostic, boolean z, RuntimeDiagnostic runtimeDiagnostic2, JsMapping jsMapping, JsGenerationGranularity jsGenerationGranularity, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(moduleDescriptor, irBuiltIns, symbolTable, set, set2, compilerConfiguration, (i & 64) != 0 ? null : runtimeDiagnostic, (i & 128) != 0 ? false : z, (i & 256) != 0 ? null : runtimeDiagnostic2, (i & 512) != 0 ? new JsMapping() : jsMapping, (i & 1024) != 0 ? JsGenerationGranularity.WHOLE_PROGRAM : jsGenerationGranularity, (i & 2048) != 0 ? false : z2);
    }

    @Override // org.jetbrains.kotlin.backend.common.CommonBackendContext
    public JsMapping getMapping() {
        return this.mapping;
    }

    public final JsGenerationGranularity getGranularity() {
        return this.granularity;
    }

    public final boolean getIncrementalCacheEnabled() {
        return this.incrementalCacheEnabled;
    }

    public final JsPolyfills getPolyfills() {
        return this.polyfills;
    }

    public final WeakHashMap<IrField, IrExpression> getFieldToInitializer() {
        return this.fieldToInitializer;
    }

    public final IrInterningService getGlobalInternationService() {
        return this.globalInternationService;
    }

    public final WeakHashMap<IrClass, String> getLocalClassNames() {
        return this.localClassNames;
    }

    public final WeakHashMap<IrClass, String> getClassToItsId() {
        return this.classToItsId;
    }

    public final MinimizedNameGenerator getMinimizedNameGenerator() {
        return this.minimizedNameGenerator;
    }

    public final Keeper getKeeper() {
        return this.keeper;
    }

    public final WeakHashMap<IrClass, Map<IrField, String>> getFieldDataCache() {
        return this.fieldDataCache;
    }

    @Override // org.jetbrains.kotlin.backend.common.BackendContext
    public KotlinBuiltIns getBuiltIns() {
        return this.builtIns;
    }

    @Override // org.jetbrains.kotlin.backend.common.BackendContext
    public IrTypeSystemContext getTypeSystem() {
        return this.typeSystem;
    }

    @Override // org.jetbrains.kotlin.backend.common.BackendContext
    public IrFactory getIrFactory() {
        return this.irFactory;
    }

    @Override // org.jetbrains.kotlin.backend.common.LoggingContext
    public boolean getInVerbosePhase() {
        return this.inVerbosePhase;
    }

    @Override // org.jetbrains.kotlin.backend.common.LoggingContext
    public void setInVerbosePhase(boolean z) {
        this.inVerbosePhase = z;
    }

    @Override // org.jetbrains.kotlin.backend.common.CommonBackendContext
    public boolean isSideEffectFree(IrCall call) {
        Intrinsics.checkNotNullParameter(call, "call");
        return this.intrinsics.getPrimitiveToLiteralConstructor().values().contains(call.getSymbol()) || Intrinsics.areEqual(call.getSymbol(), this.intrinsics.getArrayLiteral()) || Intrinsics.areEqual(call.getSymbol(), this.intrinsics.getArrayConcat()) || Intrinsics.areEqual(call.getSymbol(), this.intrinsics.getJsBoxIntrinsic()) || Intrinsics.areEqual(call.getSymbol(), this.intrinsics.getJsUnboxIntrinsic());
    }

    public final boolean getDevMode() {
        return this.devMode;
    }

    public final ErrorTolerancePolicy getErrorPolicy() {
        return this.errorPolicy;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext
    public boolean getEs6mode() {
        return this.es6mode;
    }

    public final Map<IrFileSymbol, IrFile> getExternalPackageFragment() {
        return this.externalPackageFragment;
    }

    public final HashSet<IrDeclaration> getAdditionalExportedDeclarations() {
        return this.additionalExportedDeclarations;
    }

    public final IrPackageFragment getBodilessBuiltInsPackageFragment() {
        return this.bodilessBuiltInsPackageFragment;
    }

    public final HashSet<IrFile> getPackageLevelJsModules() {
        return this.packageLevelJsModules;
    }

    public final List<IrDeclarationWithName> getDeclarationLevelJsModules() {
        return this.declarationLevelJsModules;
    }

    public final HashMap<IrFile, IrSimpleFunction> getTestFunsPerFile() {
        return this.testFunsPerFile;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext
    public IrSimpleFunction createTestContainerFun(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        HashMap<IrFile, IrSimpleFunction> map = this.testFunsPerFile;
        IrSimpleFunction irSimpleFunctionBuildFunction = map.get(irFile);
        if (irSimpleFunctionBuildFunction == null) {
            IrFactory irFactory = getIrFactory();
            IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
            Name nameIdentifier = Name.identifier("test fun");
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            irFunctionBuilder.setName(nameIdentifier);
            irFunctionBuilder.setReturnType(getIrBuiltIns().getUnitType());
            irFunctionBuilder.setOrigin(JsIrBuilder.SYNTHESIZED_DECLARATION.INSTANCE);
            irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
            IrFile irFile2 = irFile;
            irFile2.getDeclarations().add(irSimpleFunctionBuildFunction);
            irSimpleFunctionBuildFunction.setParent(irFile2);
            irSimpleFunctionBuildFunction.setBody(IrFactoryHelpersKt.createBlockBody(getIrFactory(), -1, -1, (List<? extends IrStatement>) CollectionsKt.emptyList()));
            map.put(irFile, irSimpleFunctionBuildFunction);
        }
        return irSimpleFunctionBuildFunction;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext, org.jetbrains.kotlin.backend.common.CommonBackendContext
    public JsInlineClassesUtils getInlineClassesUtils() {
        return this.inlineClassesUtils;
    }

    public final JsInnerClassesSupport getInnerClassesSupport() {
        return this.innerClassesSupport;
    }

    /* JADX INFO: compiled from: JsIrBackendContext.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u000b\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u000e\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/JsIrBackendContext$Companion;", "", "()V", "COROUTINE_SUSPEND_OR_RETURN_JS_NAME", "", "ENUMS_PACKAGE_FQNAME", "Lorg/jetbrains/kotlin/name/FqName;", "GET_COROUTINE_CONTEXT_NAME", "JS_INTERNAL_PACKAGE_FQNAME", "JS_PACKAGE_FQNAME", "JS_POLYFILLS_PACKAGE", "KOTLIN_PACKAGE_FQN", "getKOTLIN_PACKAGE_FQN", "()Lorg/jetbrains/kotlin/name/FqName;", "REFLECT_PACKAGE_FQNAME", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final FqName getKOTLIN_PACKAGE_FQN() {
            return JsIrBackendContext.KOTLIN_PACKAGE_FQN;
        }
    }

    static {
        FqName fqNameFromSegments = FqName.fromSegments(CollectionsKt.listOf(IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME));
        Intrinsics.checkNotNullExpressionValue(fqNameFromSegments, "fromSegments(...)");
        KOTLIN_PACKAGE_FQN = fqNameFromSegments;
        FqName fqNameChild = fqNameFromSegments.child(Name.identifier("reflect"));
        Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
        REFLECT_PACKAGE_FQNAME = fqNameChild;
        FqName fqNameChild2 = fqNameFromSegments.child(Name.identifier("js"));
        Intrinsics.checkNotNullExpressionValue(fqNameChild2, "child(...)");
        JS_PACKAGE_FQNAME = fqNameChild2;
        FqName fqNameChild3 = fqNameFromSegments.child(Name.identifier("enums"));
        Intrinsics.checkNotNullExpressionValue(fqNameChild3, "child(...)");
        ENUMS_PACKAGE_FQNAME = fqNameChild3;
        FqName fqNameChild4 = fqNameChild2.child(Name.identifier("polyfill"));
        Intrinsics.checkNotNullExpressionValue(fqNameChild4, "child(...)");
        JS_POLYFILLS_PACKAGE = fqNameChild4;
        FqName fqNameChild5 = fqNameChild2.child(Name.identifier("internal"));
        Intrinsics.checkNotNullExpressionValue(fqNameChild5, "child(...)");
        JS_INTERNAL_PACKAGE_FQNAME = fqNameChild5;
        COROUTINE_SUSPEND_OR_RETURN_JS_NAME = "suspendCoroutineUninterceptedOrReturnJS";
        GET_COROUTINE_CONTEXT_NAME = "getCoroutineContext";
    }

    public final IrDynamicType getDynamicType() {
        return this.dynamicType;
    }

    public final JsIntrinsics getIntrinsics() {
        return this.intrinsics;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext
    public ReflectionSymbols getReflectionSymbols() {
        return this.intrinsics.getReflectionSymbols();
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext
    public PropertyLazyInitialization getPropertyLazyInitialization() {
        return this.propertyLazyInitialization;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext
    public IrType getCatchAllThrowableType() {
        return this.dynamicType;
    }

    @Override // org.jetbrains.kotlin.backend.common.BackendContext
    public JsSharedVariablesManager getSharedVariablesManager() {
        return this.sharedVariablesManager;
    }

    @Override // org.jetbrains.kotlin.backend.common.BackendContext
    public FqName getInternalPackageFqn() {
        return this.internalPackageFqn;
    }

    private final List<Name> primitivesWithImplicitCompanionObject() {
        Set<PrimitiveType> set = PrimitiveType.NUMBER_TYPES;
        ArrayList arrayList = new ArrayList();
        for (Object obj : set) {
            PrimitiveType primitiveType = (PrimitiveType) obj;
            if (!Intrinsics.areEqual(primitiveType.name(), "LONG") && !Intrinsics.areEqual(primitiveType.name(), "CHAR")) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            arrayList3.add(((PrimitiveType) it.next()).getTypeName());
        }
        return CollectionsKt.plus((Collection) arrayList3, (Iterable) CollectionsKt.listOf((Object[]) new Name[]{Name.identifier(CommonClassNames.JAVA_LANG_STRING_SHORT), Name.identifier("Boolean")}));
    }

    public final IrSimpleFunctionSymbol getOperatorByName(Name name2, IrSimpleType lhsType, IrSimpleType rhsType) {
        Collection<IrSimpleFunctionSymbol> collection;
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(lhsType, "lhsType");
        Map<IrClassSymbol, Collection<IrSimpleFunctionSymbol>> map = this.operatorMap.get(name2);
        Object obj = null;
        if (map == null || (collection = map.get(lhsType.getClassifier())) == null) {
            return null;
        }
        if (rhsType == null) {
            return (IrSimpleFunctionSymbol) CollectionsKt.singleOrNull(collection);
        }
        boolean z = false;
        Object obj2 = null;
        for (Object obj3 : collection) {
            IrType type = ((IrSimpleFunctionSymbol) obj3).getOwner().getValueParameters().get(0).getType();
            if (type != null) {
                if (Intrinsics.areEqual(((IrSimpleType) type).getClassifier(), rhsType.getClassifier())) {
                    if (z) {
                        return (IrSimpleFunctionSymbol) obj;
                    }
                    z = true;
                    obj2 = obj3;
                }
            } else {
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
            }
        }
        if (z) {
            obj = obj2;
        }
        return (IrSimpleFunctionSymbol) obj;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext
    public JsCommonCoroutineSymbols getCoroutineSymbols() {
        return this.coroutineSymbols;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext
    public IrClassSymbol getEnumEntries() {
        return this.enumEntries;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext
    public IrSimpleFunctionSymbol getCreateEnumEntries() {
        return this.createEnumEntries;
    }

    @Override // org.jetbrains.kotlin.backend.common.CommonBackendContext, org.jetbrains.kotlin.backend.common.BackendContext
    public Ir<JsIrBackendContext> getIr() {
        return this.ir;
    }

    public final IrSimpleFunctionSymbol getErrorCodeSymbol() {
        return this.errorCodeSymbol;
    }

    public final IrClassSymbol getThrowableClass() {
        return this.throwableClass;
    }

    public final Map<Name, IrClassSymbol> getPrimitiveCompanionObjects() {
        return this.primitiveCompanionObjects;
    }

    public final IrPropertySymbol getCoroutineEmptyContinuation() {
        return this.coroutineEmptyContinuation;
    }

    public final IrSimpleFunctionSymbol getNewThrowableSymbol() {
        return this.newThrowableSymbol;
    }

    public final IrSimpleFunctionSymbol getExtendThrowableSymbol() {
        return this.extendThrowableSymbol;
    }

    public final IrSimpleFunctionSymbol getSetPropertiesToThrowableInstanceSymbol() {
        return this.setPropertiesToThrowableInstanceSymbol;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext
    public IrSimpleFunctionSymbol getSuiteFun() {
        return this.suiteFun;
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext
    public IrSimpleFunctionSymbol getTestFun() {
        return this.testFun;
    }

    public final List<IrConstructorSymbol> getThrowableConstructors() {
        return (List) this.throwableConstructors.getValue();
    }

    public final IrConstructorSymbol getDefaultThrowableCtor() {
        return (IrConstructorSymbol) this.defaultThrowableCtor.getValue();
    }

    public final IrSimpleFunctionSymbol getKpropertyBuilder() {
        return this.kpropertyBuilder;
    }

    public final IrSimpleFunctionSymbol getKlocalDelegateBuilder() {
        return this.klocalDelegateBuilder;
    }

    private final Map<Name, Map<IrClassSymbol, Collection<IrSimpleFunctionSymbol>>> referenceOperators() {
        List listEmptyList;
        List<IrType> primitiveIrTypes = getIrBuiltIns().getPrimitiveIrTypes();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(primitiveIrTypes, 10));
        Iterator<T> it = primitiveIrTypes.iterator();
        while (it.hasNext()) {
            IrClassifierSymbol classifierOrFail = IrTypesKt.getClassifierOrFail((IrType) it.next());
            Intrinsics.checkNotNull(classifierOrFail, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrClassSymbol");
            arrayList.add((IrClassSymbol) classifierOrFail);
        }
        ArrayList arrayList2 = arrayList;
        Set<Name> all = OperatorNames.INSTANCE.getALL();
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(all, 10)), 16));
        for (Object obj : all) {
            LinkedHashMap linkedHashMap2 = linkedHashMap;
            Name name2 = (Name) obj;
            ArrayList arrayList3 = arrayList2;
            LinkedHashMap linkedHashMap3 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(arrayList3, 10)), 16));
            for (Object obj2 : arrayList3) {
                LinkedHashMap linkedHashMap4 = linkedHashMap3;
                List<IrDeclaration> declarations = ((IrClassSymbol) obj2).getOwner().getDeclarations();
                if (declarations.isEmpty()) {
                    listEmptyList = CollectionsKt.emptyList();
                } else {
                    SmartList smartList = new SmartList();
                    for (Object obj3 : declarations) {
                        if (obj3 instanceof IrSimpleFunction) {
                            IrSimpleFunction irSimpleFunction = (IrSimpleFunction) obj3;
                            IrSimpleFunctionSymbol symbol = irSimpleFunction.getSymbol();
                            if (!Intrinsics.areEqual(irSimpleFunction.getName(), name2)) {
                                symbol = null;
                            }
                            if (symbol != null) {
                                smartList.add(symbol);
                            }
                        }
                    }
                    listEmptyList = smartList;
                }
                linkedHashMap4.put(obj2, listEmptyList);
            }
            linkedHashMap2.put(obj, linkedHashMap3);
        }
        return linkedHashMap;
    }

    private final List<PropertyDescriptor> findProperty(MemberScope memberScope, Name name2) {
        return CollectionsKt.toList(memberScope.getContributedVariables(name2, NoLookupLocation.FROM_BACKEND));
    }

    public final ClassDescriptor getJsInternalClass$backend_js(String name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        MemberScope memberScope = this.internalPackage.getMemberScope();
        Name nameIdentifier = Name.identifier(name2);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return JsCommonBackendContextKt.findClass(memberScope, nameIdentifier);
    }

    public final ClassDescriptor getClass$backend_js(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        ModuleDescriptor moduleDescriptor = this.module;
        FqName fqNameParent = fqName.parent();
        Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
        MemberScope memberScope = moduleDescriptor.getPackage(fqNameParent).getMemberScope();
        Name nameShortName = fqName.shortName();
        Intrinsics.checkNotNullExpressionValue(nameShortName, "shortName(...)");
        return JsCommonBackendContextKt.findClass(memberScope, nameShortName);
    }

    public final PropertyDescriptor getProperty$backend_js(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        ModuleDescriptor moduleDescriptor = this.module;
        FqName fqNameParent = fqName.parent();
        Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
        MemberScope memberScope = moduleDescriptor.getPackage(fqNameParent).getMemberScope();
        Name nameShortName = fqName.shortName();
        Intrinsics.checkNotNullExpressionValue(nameShortName, "shortName(...)");
        return (PropertyDescriptor) CollectionsKt.single((List) findProperty(memberScope, nameShortName));
    }

    public final IrClassSymbol getIrClass$backend_js(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        return this.symbolTable.getDescriptorExtension().referenceClass(getClass$backend_js(fqName));
    }

    public final List<SimpleFunctionDescriptor> getFunctions(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        ModuleDescriptor moduleDescriptor = this.module;
        FqName fqNameParent = fqName.parent();
        Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
        MemberScope memberScope = moduleDescriptor.getPackage(fqNameParent).getMemberScope();
        Name nameShortName = fqName.shortName();
        Intrinsics.checkNotNullExpressionValue(nameShortName, "shortName(...)");
        return JsCommonBackendContextKt.findFunctions(memberScope, nameShortName);
    }

    @Override // org.jetbrains.kotlin.backend.common.LoggingContext
    public void log(Function0<String> message) {
        Intrinsics.checkNotNullParameter(message, "message");
        if (getInVerbosePhase()) {
            System.out.print((Object) message.invoke());
        }
    }

    @Override // org.jetbrains.kotlin.backend.common.ErrorReportingContext
    public void report(IrElement element, IrFile irFile, String message, boolean isError) {
        Intrinsics.checkNotNullParameter(message, "message");
        System.out.print((Object) message);
    }

    public final void addOutlinedJsCode(IrSimpleFunctionSymbol symbol, JsFunction outlinedJsCode) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(outlinedJsCode, "outlinedJsCode");
        this.outlinedJsCodeFunctions.put(symbol, outlinedJsCode);
    }

    public final JsFunction getJsCodeForFunction(IrFunctionSymbol symbol) {
        JsExpression expression;
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        JsFunction jsFunction = this.outlinedJsCodeFunctions.get(symbol);
        if (jsFunction != null) {
            return jsFunction;
        }
        IrConstructorCall annotation = IrUtilsKt.getAnnotation(symbol.getOwner(), JsAnnotations.INSTANCE.getJsFunFqn());
        if (annotation == null) {
            return null;
        }
        IrExpression valueArgument = annotation.getValueArgument(0);
        if (valueArgument == null) {
            CompilationExceptionKt.compilationException("@JsFun annotation must contain an argument", annotation);
            throw null;
        }
        List<JsStatement> listTranslateJsCodeIntoStatementList = JsCodeKt.translateJsCodeIntoStatementList(valueArgument, this, symbol.getOwner());
        if (listTranslateJsCodeIntoStatementList == null) {
            CompilationExceptionKt.compilationException("Could not parse JS code", annotation);
            throw null;
        }
        JsStatement jsStatement = (JsStatement) CollectionsKt.singleOrNull((List) listTranslateJsCodeIntoStatementList);
        if (jsStatement != null) {
            if (!(jsStatement instanceof JsExpressionStatement)) {
                jsStatement = null;
            }
            JsExpressionStatement jsExpressionStatement = (JsExpressionStatement) jsStatement;
            if (jsExpressionStatement != null && (expression = jsExpressionStatement.getExpression()) != null) {
                if (!(expression instanceof JsFunction)) {
                    expression = null;
                }
                JsFunction jsFunction2 = (JsFunction) expression;
                if (jsFunction2 != null) {
                    this.outlinedJsCodeFunctions.put(symbol, jsFunction2);
                    return jsFunction2;
                }
            }
        }
        CompilationExceptionKt.compilationException("Provided JS code is not a js function", annotation);
        throw null;
    }

    @Override // org.jetbrains.kotlin.backend.common.CommonBackendContext
    public PartialLinkageSupportForLowerings getPartialLinkageSupport() {
        return this.partialLinkageSupport;
    }
}
