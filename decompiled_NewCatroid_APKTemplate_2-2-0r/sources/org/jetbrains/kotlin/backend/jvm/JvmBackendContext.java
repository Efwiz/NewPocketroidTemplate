package org.jetbrains.kotlin.backend.jvm;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.KtDiagnosticReporterWithImplicitIrBasedContext;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.common.DefaultMapping;
import org.jetbrains.kotlin.backend.common.Mapping;
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext;
import org.jetbrains.kotlin.backend.common.ir.Ir;
import org.jetbrains.kotlin.backend.common.lower.LocalDeclarationsLowering;
import org.jetbrains.kotlin.backend.common.phaser.PhaseConfig;
import org.jetbrains.kotlin.backend.jvm.caches.BridgeLoweringCache;
import org.jetbrains.kotlin.backend.jvm.caches.CollectionStubComputer;
import org.jetbrains.kotlin.backend.jvm.extensions.JvmIrDeclarationOrigin;
import org.jetbrains.kotlin.backend.jvm.mapping.IrTypeMapper;
import org.jetbrains.kotlin.backend.jvm.mapping.MethodSignatureMapper;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.cli.common.arguments.K2JsArgumentConstants;
import org.jetbrains.kotlin.codegen.JvmBackendClassResolver;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.inline.SMAP;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.codegen.state.JvmBackendConfig;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.builders.IrBlockBuilder;
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope;
import org.jetbrains.kotlin.ir.declarations.IrAttributeContainer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.impl.IrFactoryImpl;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrStatementOrigin;
import org.jetbrains.kotlin.ir.linkage.IrProvider;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFileSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrLocalDelegatedPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.IrValueSymbol;
import org.jetbrains.kotlin.ir.types.IrTypeSystemContext;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.library.KotlinLibraryLayoutKt;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.jvm.JvmClassName;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmBackendErrors;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: JvmBackendContext.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000ò\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010#\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001:\u0004\u008e\u0002\u008f\u0002B_\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011\u0012\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013\u0012\u0006\u0010\u0015\u001a\u00020\u0016¢\u0006\u0002\u0010\u0017B_\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011\u0012\u0006\u0010\u0015\u001a\u00020\u0016\u0012\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013\u0012\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019¢\u0006\u0002\u0010\u001aJ\u0012\u0010á\u0001\u001a\u0004\u0018\u00010A2\u0007\u0010â\u0001\u001a\u00020?J8\u0010ã\u0001\u001a\u0003Hä\u0001\"\t\b\u0000\u0010ä\u0001*\u00020,2\u0007\u0010å\u0001\u001a\u00020+2\u0014\u0010æ\u0001\u001a\u000f\u0012\u0004\u0012\u00020+\u0012\u0005\u0012\u0003Hä\u00010a¢\u0006\u0003\u0010ç\u0001J\u0018\u0010è\u0001\u001a\t\u0012\u0005\u0012\u00030¾\u00010\u00132\b\u0010é\u0001\u001a\u00030³\u0001JO\u0010ê\u0001\u001a\u00030ë\u00012\u0015\u0010ì\u0001\u001a\u0010\u0012\u0005\u0012\u00030í\u0001\u0012\u0005\u0012\u00030í\u00010.2\u0015\u0010î\u0001\u001a\u0010\u0012\u0005\u0012\u00030Å\u0001\u0012\u0005\u0012\u00030Å\u00010.2\u0015\u0010ï\u0001\u001a\u0010\u0012\u0005\u0012\u00030¾\u0001\u0012\u0005\u0012\u00030¾\u00010.H\u0016J\u001b\u0010ð\u0001\u001a\u00030ë\u00012\u000f\u0010ñ\u0001\u001a\n\u0012\u0005\u0012\u00030ó\u00010ò\u0001H\u0016J\u001a\u0010ô\u0001\u001a\u00030ë\u00012\u0007\u0010â\u0001\u001a\u00020?2\u0007\u0010õ\u0001\u001a\u00020AJ\u0012\u0010ö\u0001\u001a\u00030ë\u00012\b\u0010é\u0001\u001a\u00030³\u0001J\u0012\u0010÷\u0001\u001a\u00030Å\u00012\b\u0010ø\u0001\u001a\u00030ù\u0001J\u001a\u0010ú\u0001\u001a\u00030û\u00012\b\u0010ø\u0001\u001a\u00030ü\u0001H\u0000¢\u0006\u0003\bý\u0001J5\u0010þ\u0001\u001a\u00030ë\u00012\u0007\u0010ÿ\u0001\u001a\u00020Q2\u0007\u0010\u0080\u0002\u001a\u00020Q2\u0017\u0010\u0081\u0002\u001a\u0012\u0012\u0005\u0012\u00030\u0082\u0002\u0012\u0005\u0012\u00030\u0082\u0002\u0018\u00010@H\u0016J5\u0010\u0083\u0002\u001a\u00030ë\u00012\n\u0010\u0084\u0002\u001a\u0005\u0018\u00010\u0085\u00022\n\u0010\u0086\u0002\u001a\u0005\u0018\u00010\u0087\u00022\b\u0010ñ\u0001\u001a\u00030ó\u00012\u0007\u0010\u0088\u0002\u001a\u00020nH\u0016J\u001e\u0010\u0089\u0002\u001a\u00030\u008a\u00022\b\u0010\u008b\u0002\u001a\u00030\u008c\u00022\b\u0010\u008d\u0002\u001a\u00030ó\u0001H\u0016R\u0011\u0010\u000e\u001a\u00020\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\u001d\u001a\u00020\u001e¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0014\u0010!\u001a\u00020\"X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0011\u0010%\u001a\u00020&¢\u0006\b\n\u0000\u001a\u0004\b'\u0010(R\u001a\u0010)\u001a\u000e\u0012\u0004\u0012\u00020+\u0012\u0004\u0012\u00020,0*X\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010-\u001a\u000e\u0012\u0004\u0012\u00020+\u0012\u0004\u0012\u00020/0.8F¢\u0006\u0006\u001a\u0004\b0\u00101R\u0011\u00102\u001a\u000203¢\u0006\b\n\u0000\u001a\u0004\b4\u00105R\u0011\u00106\u001a\u000207¢\u0006\b\n\u0000\u001a\u0004\b8\u00109R\u0014\u0010:\u001a\u00020;8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b<\u0010=R)\u0010>\u001a\u001a\u0012\u0004\u0012\u00020?\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020A\u0012\u0004\u0012\u00020B0@0.¢\u0006\b\n\u0000\u001a\u0004\bC\u00101R\u0011\u0010D\u001a\u00020E¢\u0006\b\n\u0000\u001a\u0004\bF\u0010GR\u0011\u0010H\u001a\u00020I¢\u0006\b\n\u0000\u001a\u0004\bJ\u0010KR\u0014\u0010L\u001a\u00020M8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\bN\u0010OR\u001d\u0010P\u001a\u000e\u0012\u0004\u0012\u00020Q\u0012\u0004\u0012\u00020Q0*¢\u0006\b\n\u0000\u001a\u0004\bR\u0010SR\u001a\u0010T\u001a\u00020UX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\bV\u0010W\"\u0004\bX\u0010YR\u0017\u0010Z\u001a\b\u0012\u0004\u0012\u00020Q0[¢\u0006\b\n\u0000\u001a\u0004\b\\\u0010]R\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b^\u0010_R(\u0010`\u001a\u0010\u0012\u0004\u0012\u00020b\u0012\u0006\u0012\u0004\u0018\u00010c0aX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\bd\u0010e\"\u0004\bf\u0010gR\u001d\u0010h\u001a\u000e\u0012\u0004\u0012\u00020i\u0012\u0004\u0012\u00020i0*¢\u0006\b\n\u0000\u001a\u0004\bj\u0010SR\u001d\u0010k\u001a\u000e\u0012\u0004\u0012\u00020i\u0012\u0004\u0012\u00020i0*¢\u0006\b\n\u0000\u001a\u0004\bl\u0010SR\u001a\u0010m\u001a\u00020nX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bo\u0010p\"\u0004\bq\u0010rR\u0011\u0010s\u001a\u00020t¢\u0006\b\n\u0000\u001a\u0004\bu\u0010vR\u0011\u0010w\u001a\u00020,¢\u0006\b\n\u0000\u001a\u0004\bx\u0010yR\u0011\u0010z\u001a\u00020{¢\u0006\b\n\u0000\u001a\u0004\b|\u0010}R\u0016\u0010~\u001a\u00020\u007fX\u0096\u0004¢\u0006\n\n\u0000\u001a\u0006\b\u0080\u0001\u0010\u0081\u0001R\u001c\u0010\u0082\u0001\u001a\u00070\u0083\u0001R\u00020\u0000X\u0096\u0004¢\u0006\n\n\u0000\u001a\u0006\b\u0084\u0001\u0010\u0085\u0001R\u0016\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\n\n\u0000\u001a\u0006\b\u0086\u0001\u0010\u0087\u0001R\u0013\u0010\u0015\u001a\u00020\u0016¢\u0006\n\n\u0000\u001a\u0006\b\u0088\u0001\u0010\u0089\u0001R\u0018\u0010\u008a\u0001\u001a\u00030\u008b\u0001X\u0096\u0004¢\u0006\n\n\u0000\u001a\u0006\b\u008c\u0001\u0010\u008d\u0001R\u0015\u0010\u0018\u001a\u0004\u0018\u00010\u0019¢\u0006\n\n\u0000\u001a\u0006\b\u008e\u0001\u0010\u008f\u0001R\u0019\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013¢\u0006\n\n\u0000\u001a\u0006\b\u0090\u0001\u0010\u0091\u0001R\u0015\u0010\u0010\u001a\u0004\u0018\u00010\u0011¢\u0006\n\n\u0000\u001a\u0006\b\u0092\u0001\u0010\u0093\u0001R\u0013\u0010\u0094\u0001\u001a\u00020n¢\u0006\t\n\u0000\u001a\u0005\b\u0094\u0001\u0010pR[\u0010\u0095\u0001\u001aI\u0012\r\u0012\u000b \u0097\u0001*\u0004\u0018\u00010?0?\u0012\r\u0012\u000b \u0097\u0001*\u0004\u0018\u00010n0n \u0097\u0001*#\u0012\r\u0012\u000b \u0097\u0001*\u0004\u0018\u00010?0?\u0012\r\u0012\u000b \u0097\u0001*\u0004\u0018\u00010n0n\u0018\u00010\u0096\u00010\u0096\u0001¢\u0006\n\n\u0000\u001a\u0006\b\u0095\u0001\u0010\u0098\u0001R\u0015\u0010\u0099\u0001\u001a\u00030\u009a\u0001¢\u0006\n\n\u0000\u001a\u0006\b\u009b\u0001\u0010\u009c\u0001R\u001b\u0010\u009d\u0001\u001a\u000e\u0012\u0004\u0012\u00020?\u0012\u0004\u0012\u00020A0*X\u0082\u0004¢\u0006\u0002\n\u0000R-\u0010\u009e\u0001\u001a\u0011\u0012\u0004\u0012\u00020Q\u0012\u0005\u0012\u00030\u009f\u0001\u0018\u00010.X\u0086\u000e¢\u0006\u0011\n\u0000\u001a\u0005\b \u0001\u00101\"\u0006\b¡\u0001\u0010¢\u0001R&\u0010£\u0001\u001a\u0015\u0012\u0004\u0012\u00020?\u0012\u000b\u0012\t\u0012\u0005\u0012\u00030¤\u00010\u00130*¢\u0006\t\n\u0000\u001a\u0005\b¥\u0001\u0010SR\u0018\u0010¦\u0001\u001a\u00030§\u0001X\u0096\u0004¢\u0006\n\n\u0000\u001a\u0006\b¨\u0001\u0010©\u0001R\u0015\u0010ª\u0001\u001a\u00030«\u0001¢\u0006\n\n\u0000\u001a\u0006\b¬\u0001\u0010\u00ad\u0001R\u001f\u0010®\u0001\u001a\u000e\u0012\u0004\u0012\u00020+\u0012\u0004\u0012\u00020+0.¢\u0006\t\n\u0000\u001a\u0005\b¯\u0001\u00101R\u001f\u0010°\u0001\u001a\u000e\u0012\u0004\u0012\u00020+\u0012\u0004\u0012\u00020/0.¢\u0006\t\n\u0000\u001a\u0005\b±\u0001\u00101R!\u0010²\u0001\u001a\u0010\u0012\u0005\u0012\u00030³\u0001\u0012\u0005\u0012\u00030³\u00010.¢\u0006\t\n\u0000\u001a\u0005\b´\u0001\u00101R&\u0010µ\u0001\u001a\u0015\u0012\u0004\u0012\u00020/\u0012\u000b\u0012\t\u0012\u0004\u0012\u00020+0¶\u00010.¢\u0006\t\n\u0000\u001a\u0005\b·\u0001\u00101R\u0016\u0010¸\u0001\u001a\u00020n8VX\u0096\u0004¢\u0006\u0007\u001a\u0005\b¹\u0001\u0010pR\u0016\u0010º\u0001\u001a\u00020n8VX\u0096\u0004¢\u0006\u0007\u001a\u0005\b»\u0001\u0010pR=\u0010¼\u0001\u001a0\u0012\u0005\u0012\u00030³\u0001\u0012\u000b\u0012\t\u0012\u0005\u0012\u00030¾\u00010\u00130½\u0001j\u0017\u0012\u0005\u0012\u00030³\u0001\u0012\u000b\u0012\t\u0012\u0005\u0012\u00030¾\u00010\u0013`¿\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010\n\u001a\u00020\u000b¢\u0006\n\n\u0000\u001a\u0006\bÀ\u0001\u0010Á\u0001R\u0016\u0010Â\u0001\u001a\u00020n8VX\u0096\u0004¢\u0006\u0007\u001a\u0005\bÃ\u0001\u0010pR\u001a\u0010Ä\u0001\u001a\t\u0012\u0005\u0012\u00030Å\u00010[¢\u0006\t\n\u0000\u001a\u0005\bÆ\u0001\u0010]R\u0016\u0010Ç\u0001\u001a\u00020nX\u0096D¢\u0006\t\n\u0000\u001a\u0005\bÈ\u0001\u0010pR\u0018\u0010É\u0001\u001a\u00030Ê\u0001X\u0096\u0004¢\u0006\n\n\u0000\u001a\u0006\bË\u0001\u0010Ì\u0001R\u0013\u0010\u0002\u001a\u00020\u0003¢\u0006\n\n\u0000\u001a\u0006\bÍ\u0001\u0010Î\u0001R!\u0010Ï\u0001\u001a\u0010\u0012\u0005\u0012\u00030¾\u0001\u0012\u0005\u0012\u00030³\u00010*¢\u0006\t\n\u0000\u001a\u0005\bÐ\u0001\u0010SR!\u0010Ñ\u0001\u001a\u0010\u0012\u0005\u0012\u00030³\u0001\u0012\u0005\u0012\u00030³\u00010*¢\u0006\t\n\u0000\u001a\u0005\bÒ\u0001\u0010SR\u001f\u0010Ó\u0001\u001a\u000e\u0012\u0004\u0012\u00020?\u0012\u0004\u0012\u00020Q0*¢\u0006\t\n\u0000\u001a\u0005\bÔ\u0001\u0010SR\u0013\u0010\b\u001a\u00020\t¢\u0006\n\n\u0000\u001a\u0006\bÕ\u0001\u0010Ö\u0001R\u0018\u0010×\u0001\u001a\u00030Ø\u0001X\u0096\u0004¢\u0006\n\n\u0000\u001a\u0006\bÙ\u0001\u0010Ú\u0001R \u0010Û\u0001\u001a\u000f\u0012\u0004\u0012\u00020A\u0012\u0005\u0012\u00030Ü\u00010.¢\u0006\t\n\u0000\u001a\u0005\bÝ\u0001\u00101R\u001a\u0010Þ\u0001\u001a\t\u0012\u0005\u0012\u00030ß\u00010[¢\u0006\t\n\u0000\u001a\u0005\bà\u0001\u0010]¨\u0006\u0090\u0002"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "irModuleFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "phaseConfig", "Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfig;", "generatorExtensions", "Lorg/jetbrains/kotlin/backend/jvm/JvmGeneratorExtensions;", "backendExtension", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendExtension;", "irSerializer", "Lorg/jetbrains/kotlin/backend/jvm/JvmIrSerializer;", "irProviders", "", "Lorg/jetbrains/kotlin/ir/linkage/IrProvider;", "irDeserializer", "Lorg/jetbrains/kotlin/backend/jvm/JvmIrDeserializer;", "(Lorg/jetbrains/kotlin/codegen/state/GenerationState;Lorg/jetbrains/kotlin/ir/IrBuiltIns;Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;Lorg/jetbrains/kotlin/ir/util/SymbolTable;Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfig;Lorg/jetbrains/kotlin/backend/jvm/JvmGeneratorExtensions;Lorg/jetbrains/kotlin/backend/jvm/JvmBackendExtension;Lorg/jetbrains/kotlin/backend/jvm/JvmIrSerializer;Ljava/util/List;Lorg/jetbrains/kotlin/backend/jvm/JvmIrDeserializer;)V", "irPluginContext", "Lorg/jetbrains/kotlin/backend/common/extensions/IrPluginContext;", "(Lorg/jetbrains/kotlin/codegen/state/GenerationState;Lorg/jetbrains/kotlin/ir/IrBuiltIns;Lorg/jetbrains/kotlin/ir/util/SymbolTable;Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfig;Lorg/jetbrains/kotlin/backend/jvm/JvmGeneratorExtensions;Lorg/jetbrains/kotlin/backend/jvm/JvmBackendExtension;Lorg/jetbrains/kotlin/backend/jvm/JvmIrSerializer;Lorg/jetbrains/kotlin/backend/jvm/JvmIrDeserializer;Ljava/util/List;Lorg/jetbrains/kotlin/backend/common/extensions/IrPluginContext;)V", "getBackendExtension", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendExtension;", "bridgeLoweringCache", "Lorg/jetbrains/kotlin/backend/jvm/caches/BridgeLoweringCache;", "getBridgeLoweringCache", "()Lorg/jetbrains/kotlin/backend/jvm/caches/BridgeLoweringCache;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "getBuiltIns", "()Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "cachedDeclarations", "Lorg/jetbrains/kotlin/backend/jvm/JvmCachedDeclarations;", "getCachedDeclarations", "()Lorg/jetbrains/kotlin/backend/jvm/JvmCachedDeclarations;", "classCodegens", "Ljava/util/concurrent/ConcurrentHashMap;", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "", "classNameOverride", "", "Lorg/jetbrains/kotlin/resolve/jvm/JvmClassName;", "getClassNameOverride", "()Ljava/util/Map;", "collectionStubComputer", "Lorg/jetbrains/kotlin/backend/jvm/caches/CollectionStubComputer;", "getCollectionStubComputer", "()Lorg/jetbrains/kotlin/backend/jvm/caches/CollectionStubComputer;", "config", "Lorg/jetbrains/kotlin/codegen/state/JvmBackendConfig;", "getConfig", "()Lorg/jetbrains/kotlin/codegen/state/JvmBackendConfig;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "getConfiguration", "()Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "continuationClassesVarsCountByType", "Lorg/jetbrains/kotlin/ir/declarations/IrAttributeContainer;", "", "Lorg/jetbrains/org/objectweb/asm/Type;", "", "getContinuationClassesVarsCountByType", "defaultMethodSignatureMapper", "Lorg/jetbrains/kotlin/backend/jvm/mapping/MethodSignatureMapper;", "getDefaultMethodSignatureMapper", "()Lorg/jetbrains/kotlin/backend/jvm/mapping/MethodSignatureMapper;", "defaultTypeMapper", "Lorg/jetbrains/kotlin/backend/jvm/mapping/IrTypeMapper;", "getDefaultTypeMapper", "()Lorg/jetbrains/kotlin/backend/jvm/mapping/IrTypeMapper;", "doWhileCounterLoopOrigin", "Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "getDoWhileCounterLoopOrigin", "()Lorg/jetbrains/kotlin/ir/expressions/IrStatementOrigin;", "enclosingMethodOverride", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "getEnclosingMethodOverride", "()Ljava/util/concurrent/ConcurrentHashMap;", "enumEntriesIntrinsicMappingsCache", "Lorg/jetbrains/kotlin/backend/jvm/EnumEntriesIntrinsicMappingsCache;", "getEnumEntriesIntrinsicMappingsCache", "()Lorg/jetbrains/kotlin/backend/jvm/EnumEntriesIntrinsicMappingsCache;", "setEnumEntriesIntrinsicMappingsCache", "(Lorg/jetbrains/kotlin/backend/jvm/EnumEntriesIntrinsicMappingsCache;)V", "functionsWithSpecialBridges", "", "getFunctionsWithSpecialBridges", "()Ljava/util/Set;", "getGeneratorExtensions", "()Lorg/jetbrains/kotlin/backend/jvm/JvmGeneratorExtensions;", "getIntrinsic", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "Lorg/jetbrains/kotlin/backend/jvm/IntrinsicMarker;", "getGetIntrinsic", "()Lkotlin/jvm/functions/Function1;", "setGetIntrinsic", "(Lkotlin/jvm/functions/Function1;)V", "hiddenConstructorsOfSealedClasses", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "getHiddenConstructorsOfSealedClasses", "hiddenConstructorsWithMangledParams", "getHiddenConstructorsWithMangledParams", "inVerbosePhase", "", "getInVerbosePhase", "()Z", "setInVerbosePhase", "(Z)V", "inlineClassReplacements", "Lorg/jetbrains/kotlin/backend/jvm/MemoizedInlineClassReplacements;", "getInlineClassReplacements", "()Lorg/jetbrains/kotlin/backend/jvm/MemoizedInlineClassReplacements;", "inlineMethodGenerationLock", "getInlineMethodGenerationLock", "()Ljava/lang/Object;", "innerClassesSupport", "Lorg/jetbrains/kotlin/backend/jvm/JvmInnerClassesSupport;", "getInnerClassesSupport", "()Lorg/jetbrains/kotlin/backend/jvm/JvmInnerClassesSupport;", "internalPackageFqn", "Lorg/jetbrains/kotlin/name/FqName;", "getInternalPackageFqn", "()Lorg/jetbrains/kotlin/name/FqName;", KotlinLibraryLayoutKt.KLIB_IR_FOLDER_NAME, "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext$JvmIr;", "getIr", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext$JvmIr;", "getIrBuiltIns", "()Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "getIrDeserializer", "()Lorg/jetbrains/kotlin/backend/jvm/JvmIrDeserializer;", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "getIrFactory", "()Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "getIrPluginContext", "()Lorg/jetbrains/kotlin/backend/common/extensions/IrPluginContext;", "getIrProviders", "()Ljava/util/List;", "getIrSerializer", "()Lorg/jetbrains/kotlin/backend/jvm/JvmIrSerializer;", "isCompilingAgainstJdk8OrLater", "isEnclosedInConstructor", "Ljava/util/concurrent/ConcurrentHashMap$KeySetView;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "()Ljava/util/concurrent/ConcurrentHashMap$KeySetView;", "ktDiagnosticReporter", "Lorg/jetbrains/kotlin/KtDiagnosticReporterWithImplicitIrBasedContext;", "getKtDiagnosticReporter", "()Lorg/jetbrains/kotlin/KtDiagnosticReporterWithImplicitIrBasedContext;", "localClassType", "localDeclarationsLoweringData", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext$LocalFunctionData;", "getLocalDeclarationsLoweringData", "setLocalDeclarationsLoweringData", "(Ljava/util/Map;)V", "localDelegatedProperties", "Lorg/jetbrains/kotlin/ir/symbols/IrLocalDelegatedPropertySymbol;", "getLocalDelegatedProperties", "mapping", "Lorg/jetbrains/kotlin/backend/common/Mapping;", "getMapping", "()Lorg/jetbrains/kotlin/backend/common/Mapping;", "multiFieldValueClassReplacements", "Lorg/jetbrains/kotlin/backend/jvm/MemoizedMultiFieldValueClassReplacements;", "getMultiFieldValueClassReplacements", "()Lorg/jetbrains/kotlin/backend/jvm/MemoizedMultiFieldValueClassReplacements;", "multifileFacadeClassForPart", "getMultifileFacadeClassForPart", "multifileFacadeForPart", "getMultifileFacadeForPart", "multifileFacadeMemberToPartMember", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getMultifileFacadeMemberToPartMember", "multifileFacadesToAdd", "", "getMultifileFacadesToAdd", "optimizeLoopsOverUnsignedArrays", "getOptimizeLoopsOverUnsignedArrays", "optimizeNullChecksUsingKotlinNullability", "getOptimizeNullChecksUsingKotlinNullability", "overridesWithoutStubs", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "Lkotlin/collections/HashMap;", "getPhaseConfig", "()Lorg/jetbrains/kotlin/backend/common/phaser/PhaseConfig;", "preferJavaLikeCounterLoop", "getPreferJavaLikeCounterLoop", "publicAbiSymbols", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "getPublicAbiSymbols", "scriptMode", "getScriptMode", "sharedVariablesManager", "Lorg/jetbrains/kotlin/backend/jvm/JvmSharedVariablesManager;", "getSharedVariablesManager", "()Lorg/jetbrains/kotlin/backend/jvm/JvmSharedVariablesManager;", "getState", "()Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "staticDefaultStubs", "getStaticDefaultStubs", "suspendFunctionOriginalToView", "getSuspendFunctionOriginalToView", "suspendLambdaToOriginalFunctionMap", "getSuspendLambdaToOriginalFunctionMap", "getSymbolTable", "()Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "typeSystem", "Lorg/jetbrains/kotlin/ir/types/IrTypeSystemContext;", "getTypeSystem", "()Lorg/jetbrains/kotlin/ir/types/IrTypeSystemContext;", "typeToCachedSMAP", "Lorg/jetbrains/kotlin/codegen/inline/SMAP;", "getTypeToCachedSMAP", "visitedDeclarationsForRegenerationLowering", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "getVisitedDeclarationsForRegenerationLowering", "getLocalClassType", "container", "getOrCreateClassCodegen", "ClassCodegen", "klass", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "getOverridesWithoutStubs", "function", "handleDeepCopy", "", "fileSymbolMap", "Lorg/jetbrains/kotlin/ir/symbols/IrFileSymbol;", "classSymbolMap", "functionSymbolMap", K2JsArgumentConstants.RUNTIME_DIAGNOSTIC_LOG, CrashHianalyticsData.MESSAGE, "Lkotlin/Function0;", "", "putLocalClassType", "value", "recordOverridesWithoutStubs", "referenceClass", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "referenceTypeParameter", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "referenceTypeParameter$backend_jvm", "remapMultiFieldValueClassStructure", "oldFunction", "newFunction", "parametersMappingOrNull", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "report", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/ir/IrElement;", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "isError", "throwUninitializedPropertyAccessException", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "builder", "Lorg/jetbrains/kotlin/ir/builders/IrBuilderWithScope;", "name", "JvmIr", "LocalFunctionData", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmBackendContext implements CommonBackendContext {
    private final JvmBackendExtension backendExtension;
    private final BridgeLoweringCache bridgeLoweringCache;
    private final KotlinBuiltIns builtIns;
    private final JvmCachedDeclarations cachedDeclarations;
    private final ConcurrentHashMap<IrClass, Object> classCodegens;
    private final CollectionStubComputer collectionStubComputer;
    private final JvmBackendConfig config;
    private final Map<IrAttributeContainer, Map<Type, Integer>> continuationClassesVarsCountByType;
    private final MethodSignatureMapper defaultMethodSignatureMapper;
    private final IrTypeMapper defaultTypeMapper;
    private final ConcurrentHashMap<IrFunction, IrFunction> enclosingMethodOverride;
    public EnumEntriesIntrinsicMappingsCache enumEntriesIntrinsicMappingsCache;
    private final Set<IrFunction> functionsWithSpecialBridges;
    private final JvmGeneratorExtensions generatorExtensions;
    public Function1<? super IrFunctionSymbol, ? extends IntrinsicMarker> getIntrinsic;
    private final ConcurrentHashMap<IrConstructor, IrConstructor> hiddenConstructorsOfSealedClasses;
    private final ConcurrentHashMap<IrConstructor, IrConstructor> hiddenConstructorsWithMangledParams;
    private boolean inVerbosePhase;
    private final MemoizedInlineClassReplacements inlineClassReplacements;
    private final Object inlineMethodGenerationLock;
    private final JvmInnerClassesSupport innerClassesSupport;
    private final FqName internalPackageFqn;
    private final JvmIr ir;
    private final IrBuiltIns irBuiltIns;
    private final JvmIrDeserializer irDeserializer;
    private final IrFactory irFactory;
    private final IrPluginContext irPluginContext;
    private final List<IrProvider> irProviders;
    private final JvmIrSerializer irSerializer;
    private final boolean isCompilingAgainstJdk8OrLater;
    private final ConcurrentHashMap.KeySetView<IrAttributeContainer, Boolean> isEnclosedInConstructor;
    private final KtDiagnosticReporterWithImplicitIrBasedContext ktDiagnosticReporter;
    private final ConcurrentHashMap<IrAttributeContainer, Type> localClassType;
    private Map<IrFunction, LocalFunctionData> localDeclarationsLoweringData;
    private final ConcurrentHashMap<IrAttributeContainer, List<IrLocalDelegatedPropertySymbol>> localDelegatedProperties;
    private final Mapping mapping;
    private final MemoizedMultiFieldValueClassReplacements multiFieldValueClassReplacements;
    private final Map<IrClass, IrClass> multifileFacadeClassForPart;
    private final Map<IrClass, JvmClassName> multifileFacadeForPart;
    private final Map<IrSimpleFunction, IrSimpleFunction> multifileFacadeMemberToPartMember;
    private final Map<JvmClassName, List<IrClass>> multifileFacadesToAdd;
    private final HashMap<IrSimpleFunction, List<IrSimpleFunctionSymbol>> overridesWithoutStubs;
    private final PhaseConfig phaseConfig;
    private final Set<IrClassSymbol> publicAbiSymbols;
    private final boolean scriptMode;
    private final JvmSharedVariablesManager sharedVariablesManager;
    private final GenerationState state;
    private final ConcurrentHashMap<IrSimpleFunctionSymbol, IrSimpleFunction> staticDefaultStubs;
    private final ConcurrentHashMap<IrSimpleFunction, IrSimpleFunction> suspendFunctionOriginalToView;
    private final ConcurrentHashMap<IrAttributeContainer, IrFunction> suspendLambdaToOriginalFunctionMap;
    private final SymbolTable symbolTable;
    private final IrTypeSystemContext typeSystem;
    private final Map<Type, SMAP> typeToCachedSMAP;
    private final Set<IrDeclaration> visitedDeclarationsForRegenerationLowering;

    @Override // org.jetbrains.kotlin.backend.common.CommonBackendContext
    public boolean getOptimizeLoopsOverUnsignedArrays() {
        return true;
    }

    @Override // org.jetbrains.kotlin.backend.common.CommonBackendContext
    public boolean getOptimizeNullChecksUsingKotlinNullability() {
        return false;
    }

    @Override // org.jetbrains.kotlin.backend.common.CommonBackendContext
    public boolean getPreferJavaLikeCounterLoop() {
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public JvmBackendContext(GenerationState state, IrBuiltIns irBuiltIns, SymbolTable symbolTable, PhaseConfig phaseConfig, JvmGeneratorExtensions generatorExtensions, JvmBackendExtension backendExtension, JvmIrSerializer jvmIrSerializer, JvmIrDeserializer irDeserializer, List<? extends IrProvider> irProviders, IrPluginContext irPluginContext) {
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        Intrinsics.checkNotNullParameter(phaseConfig, "phaseConfig");
        Intrinsics.checkNotNullParameter(generatorExtensions, "generatorExtensions");
        Intrinsics.checkNotNullParameter(backendExtension, "backendExtension");
        Intrinsics.checkNotNullParameter(irDeserializer, "irDeserializer");
        Intrinsics.checkNotNullParameter(irProviders, "irProviders");
        this.state = state;
        this.irBuiltIns = irBuiltIns;
        this.symbolTable = symbolTable;
        this.phaseConfig = phaseConfig;
        this.generatorExtensions = generatorExtensions;
        this.backendExtension = backendExtension;
        this.irSerializer = jvmIrSerializer;
        this.irDeserializer = irDeserializer;
        this.irProviders = irProviders;
        this.irPluginContext = irPluginContext;
        JvmBackendConfig config = state.getConfig();
        this.config = config;
        this.irFactory = IrFactoryImpl.INSTANCE;
        this.builtIns = state.getModule().getBuiltIns();
        this.typeSystem = new JvmIrTypeSystemContext(getIrBuiltIns());
        IrTypeMapper irTypeMapper = new IrTypeMapper(this);
        this.defaultTypeMapper = irTypeMapper;
        this.defaultMethodSignatureMapper = new MethodSignatureMapper(this, irTypeMapper);
        this.innerClassesSupport = new JvmInnerClassesSupport(getIrFactory());
        this.cachedDeclarations = new JvmCachedDeclarations(this, generatorExtensions.getCachedFields());
        this.mapping = new DefaultMapping(null, 1, 0 == true ? 1 : 0);
        this.ktDiagnosticReporter = new KtDiagnosticReporterWithImplicitIrBasedContext(state.getDiagnosticReporter(), state.getLanguageVersionSettings());
        this.ir = new JvmIr(this, symbolTable);
        this.sharedVariablesManager = new JvmSharedVariablesManager(state.getModule(), getIr().getSymbols(), getIrBuiltIns(), getIrFactory());
        this.typeToCachedSMAP = new LinkedHashMap();
        this.localClassType = new ConcurrentHashMap<>();
        JvmBackendClassResolver jvmBackendClassResolver = state.getJvmBackendClassResolver();
        Type objectType = Type.getObjectType("java/lang/invoke/LambdaMetafactory");
        Intrinsics.checkNotNullExpressionValue(objectType, "getObjectType(...)");
        this.isCompilingAgainstJdk8OrLater = !jvmBackendClassResolver.resolveToClassDescriptors(objectType).isEmpty();
        this.isEnclosedInConstructor = ConcurrentHashMap.newKeySet();
        this.enclosingMethodOverride = new ConcurrentHashMap<>();
        this.classCodegens = new ConcurrentHashMap<>();
        this.localDelegatedProperties = new ConcurrentHashMap<>();
        this.multifileFacadesToAdd = new LinkedHashMap();
        this.multifileFacadeForPart = new LinkedHashMap();
        this.multifileFacadeClassForPart = new LinkedHashMap();
        this.multifileFacadeMemberToPartMember = new LinkedHashMap();
        this.hiddenConstructorsWithMangledParams = new ConcurrentHashMap<>();
        this.hiddenConstructorsOfSealedClasses = new ConcurrentHashMap<>();
        this.collectionStubComputer = new CollectionStubComputer(this);
        this.overridesWithoutStubs = new HashMap<>();
        this.bridgeLoweringCache = new BridgeLoweringCache(this);
        ConcurrentHashMap.KeySetView keySetViewNewKeySet = ConcurrentHashMap.newKeySet();
        Intrinsics.checkNotNullExpressionValue(keySetViewNewKeySet, "newKeySet(...)");
        this.functionsWithSpecialBridges = keySetViewNewKeySet;
        this.internalPackageFqn = new FqName("kotlin.jvm");
        this.suspendLambdaToOriginalFunctionMap = new ConcurrentHashMap<>();
        this.suspendFunctionOriginalToView = new ConcurrentHashMap<>();
        this.staticDefaultStubs = new ConcurrentHashMap<>();
        this.inlineClassReplacements = new MemoizedInlineClassReplacements(config.getFunctionsWithInlineClassReturnTypesMangled(), getIrFactory(), this);
        this.multiFieldValueClassReplacements = new MemoizedMultiFieldValueClassReplacements(getIrFactory(), this);
        this.continuationClassesVarsCountByType = new HashMap();
        this.inlineMethodGenerationLock = new Object();
        this.publicAbiSymbols = new LinkedHashSet();
        ConcurrentHashMap.KeySetView keySetViewNewKeySet2 = ConcurrentHashMap.newKeySet();
        Intrinsics.checkNotNullExpressionValue(keySetViewNewKeySet2, "newKeySet(...)");
        this.visitedDeclarationsForRegenerationLowering = keySetViewNewKeySet2;
        state.setMapInlineClass(new Function1<ClassDescriptor, Type>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmBackendContext.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Type invoke(ClassDescriptor descriptor) {
                Intrinsics.checkNotNullParameter(descriptor, "descriptor");
                return IrTypeMapper.mapType$default(JvmBackendContext.this.getDefaultTypeMapper(), IrTypesKt.getDefaultType(JvmBackendContext.this.referenceClass(descriptor)), null, null, false, 14, null);
            }
        });
        state.setMultiFieldValueClassUnboxInfo(new Function1<ClassDescriptor, GenerationState.MultiFieldValueClassUnboxInfo>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmBackendContext.2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final GenerationState.MultiFieldValueClassUnboxInfo invoke(ClassDescriptor descriptor) {
                Intrinsics.checkNotNullParameter(descriptor, "descriptor");
                RootMfvcNode rootMfvcNodeOrNull = JvmBackendContext.this.getMultiFieldValueClassReplacements().getRootMfvcNodeOrNull(JvmBackendContext.this.getSymbolTable().getLazyWrapper().getDescriptorExtension().referenceClass(descriptor).getOwner());
                if (rootMfvcNodeOrNull == null) {
                    return null;
                }
                List<LeafMfvcNode> leaves = rootMfvcNodeOrNull.getLeaves();
                JvmBackendContext jvmBackendContext = JvmBackendContext.this;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(leaves, 10));
                for (LeafMfvcNode leafMfvcNode : leaves) {
                    Type typeMapType$default = IrTypeMapper.mapType$default(jvmBackendContext.getDefaultTypeMapper(), leafMfvcNode.getType(), null, null, false, 14, null);
                    LeafMfvcNode leafMfvcNode2 = leafMfvcNode;
                    arrayList.add(new Triple(typeMapType$default, MfvcNodeKt.getFullMethodName(leafMfvcNode2).asString(), MfvcNodeKt.getFullFieldName(leafMfvcNode2).asString()));
                }
                return new GenerationState.MultiFieldValueClassUnboxInfo(arrayList);
            }
        });
        state.setReportDuplicateClassNameError(new Function3<JvmDeclarationOrigin, String, String, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmBackendContext.3
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Unit invoke(JvmDeclarationOrigin jvmDeclarationOrigin, String str, String str2) {
                invoke2(jvmDeclarationOrigin, str, str2);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(JvmDeclarationOrigin origin, String internalName, String duplicateClasses) {
                Intrinsics.checkNotNullParameter(origin, "origin");
                Intrinsics.checkNotNullParameter(internalName, "internalName");
                Intrinsics.checkNotNullParameter(duplicateClasses, "duplicateClasses");
                IrDeclaration declaration = ((JvmIrDeclarationOrigin) origin).getDeclaration();
                if (declaration != null) {
                    JvmBackendContext.this.getKtDiagnosticReporter().at(declaration).report(JvmBackendErrors.INSTANCE.getDUPLICATE_CLASS_NAMES(), internalName, duplicateClasses);
                }
            }
        });
    }

    public final GenerationState getState() {
        return this.state;
    }

    @Override // org.jetbrains.kotlin.backend.common.BackendContext
    public IrBuiltIns getIrBuiltIns() {
        return this.irBuiltIns;
    }

    public final SymbolTable getSymbolTable() {
        return this.symbolTable;
    }

    public final PhaseConfig getPhaseConfig() {
        return this.phaseConfig;
    }

    public final JvmGeneratorExtensions getGeneratorExtensions() {
        return this.generatorExtensions;
    }

    public final JvmBackendExtension getBackendExtension() {
        return this.backendExtension;
    }

    public final JvmIrSerializer getIrSerializer() {
        return this.irSerializer;
    }

    public final JvmIrDeserializer getIrDeserializer() {
        return this.irDeserializer;
    }

    public final List<IrProvider> getIrProviders() {
        return this.irProviders;
    }

    public final IrPluginContext getIrPluginContext() {
        return this.irPluginContext;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @Deprecated(level = DeprecationLevel.ERROR, message = "irModuleFragment parameter is not needed anymore.")
    public JvmBackendContext(GenerationState state, IrBuiltIns irBuiltIns, IrModuleFragment irModuleFragment, SymbolTable symbolTable, PhaseConfig phaseConfig, JvmGeneratorExtensions generatorExtensions, JvmBackendExtension backendExtension, JvmIrSerializer jvmIrSerializer, List<? extends IrProvider> irProviders, JvmIrDeserializer irDeserializer) {
        this(state, irBuiltIns, symbolTable, phaseConfig, generatorExtensions, backendExtension, jvmIrSerializer, irDeserializer, irProviders, (IrPluginContext) null);
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
        Intrinsics.checkNotNullParameter(irModuleFragment, "irModuleFragment");
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        Intrinsics.checkNotNullParameter(phaseConfig, "phaseConfig");
        Intrinsics.checkNotNullParameter(generatorExtensions, "generatorExtensions");
        Intrinsics.checkNotNullParameter(backendExtension, "backendExtension");
        Intrinsics.checkNotNullParameter(irProviders, "irProviders");
        Intrinsics.checkNotNullParameter(irDeserializer, "irDeserializer");
    }

    /* JADX INFO: compiled from: JvmBackendContext.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\b0\u0005¢\u0006\u0002\u0010\tJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\u0015\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J\u0015\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\b0\u0005HÆ\u0003J?\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u0014\b\u0002\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u00052\u0014\b\u0002\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\b0\u0005HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001d\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\b0\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u001d\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\r¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext$LocalFunctionData;", "", "localContext", "Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$LocalFunctionContext;", "newParameterToOld", "", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "newParameterToCaptured", "Lorg/jetbrains/kotlin/ir/symbols/IrValueSymbol;", "(Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$LocalFunctionContext;Ljava/util/Map;Ljava/util/Map;)V", "getLocalContext", "()Lorg/jetbrains/kotlin/backend/common/lower/LocalDeclarationsLowering$LocalFunctionContext;", "getNewParameterToCaptured", "()Ljava/util/Map;", "getNewParameterToOld", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class LocalFunctionData {
        private final LocalDeclarationsLowering.LocalFunctionContext localContext;
        private final Map<IrValueParameter, IrValueSymbol> newParameterToCaptured;
        private final Map<IrValueParameter, IrValueParameter> newParameterToOld;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ LocalFunctionData copy$default(LocalFunctionData localFunctionData, LocalDeclarationsLowering.LocalFunctionContext localFunctionContext, Map map, Map map2, int i, Object obj) {
            if ((i & 1) != 0) {
                localFunctionContext = localFunctionData.localContext;
            }
            if ((i & 2) != 0) {
                map = localFunctionData.newParameterToOld;
            }
            if ((i & 4) != 0) {
                map2 = localFunctionData.newParameterToCaptured;
            }
            return localFunctionData.copy(localFunctionContext, map, map2);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final LocalDeclarationsLowering.LocalFunctionContext getLocalContext() {
            return this.localContext;
        }

        public final Map<IrValueParameter, IrValueParameter> component2() {
            return this.newParameterToOld;
        }

        public final Map<IrValueParameter, IrValueSymbol> component3() {
            return this.newParameterToCaptured;
        }

        public final LocalFunctionData copy(LocalDeclarationsLowering.LocalFunctionContext localContext, Map<IrValueParameter, ? extends IrValueParameter> newParameterToOld, Map<IrValueParameter, ? extends IrValueSymbol> newParameterToCaptured) {
            Intrinsics.checkNotNullParameter(localContext, "localContext");
            Intrinsics.checkNotNullParameter(newParameterToOld, "newParameterToOld");
            Intrinsics.checkNotNullParameter(newParameterToCaptured, "newParameterToCaptured");
            return new LocalFunctionData(localContext, newParameterToOld, newParameterToCaptured);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof LocalFunctionData)) {
                return false;
            }
            LocalFunctionData localFunctionData = (LocalFunctionData) other;
            return Intrinsics.areEqual(this.localContext, localFunctionData.localContext) && Intrinsics.areEqual(this.newParameterToOld, localFunctionData.newParameterToOld) && Intrinsics.areEqual(this.newParameterToCaptured, localFunctionData.newParameterToCaptured);
        }

        public int hashCode() {
            return (((this.localContext.hashCode() * 31) + this.newParameterToOld.hashCode()) * 31) + this.newParameterToCaptured.hashCode();
        }

        public String toString() {
            return "LocalFunctionData(localContext=" + this.localContext + ", newParameterToOld=" + this.newParameterToOld + ", newParameterToCaptured=" + this.newParameterToCaptured + ')';
        }

        /* JADX WARN: Multi-variable type inference failed */
        public LocalFunctionData(LocalDeclarationsLowering.LocalFunctionContext localContext, Map<IrValueParameter, ? extends IrValueParameter> newParameterToOld, Map<IrValueParameter, ? extends IrValueSymbol> newParameterToCaptured) {
            Intrinsics.checkNotNullParameter(localContext, "localContext");
            Intrinsics.checkNotNullParameter(newParameterToOld, "newParameterToOld");
            Intrinsics.checkNotNullParameter(newParameterToCaptured, "newParameterToCaptured");
            this.localContext = localContext;
            this.newParameterToOld = newParameterToOld;
            this.newParameterToCaptured = newParameterToCaptured;
        }

        public final LocalDeclarationsLowering.LocalFunctionContext getLocalContext() {
            return this.localContext;
        }

        public final Map<IrValueParameter, IrValueParameter> getNewParameterToOld() {
            return this.newParameterToOld;
        }

        public final Map<IrValueParameter, IrValueSymbol> getNewParameterToCaptured() {
            return this.newParameterToCaptured;
        }
    }

    public final JvmBackendConfig getConfig() {
        return this.config;
    }

    public final Map<IrFunction, LocalFunctionData> getLocalDeclarationsLoweringData() {
        return this.localDeclarationsLoweringData;
    }

    public final void setLocalDeclarationsLoweringData(Map<IrFunction, LocalFunctionData> map) {
        this.localDeclarationsLoweringData = map;
    }

    public final Map<IrClass, JvmClassName> getClassNameOverride() {
        return this.generatorExtensions.getClassNameOverride();
    }

    @Override // org.jetbrains.kotlin.backend.common.BackendContext
    public IrFactory getIrFactory() {
        return this.irFactory;
    }

    @Override // org.jetbrains.kotlin.backend.common.CommonBackendContext
    public boolean getScriptMode() {
        return this.scriptMode;
    }

    @Override // org.jetbrains.kotlin.backend.common.BackendContext
    public KotlinBuiltIns getBuiltIns() {
        return this.builtIns;
    }

    @Override // org.jetbrains.kotlin.backend.common.BackendContext
    public IrTypeSystemContext getTypeSystem() {
        return this.typeSystem;
    }

    public final IrTypeMapper getDefaultTypeMapper() {
        return this.defaultTypeMapper;
    }

    public final MethodSignatureMapper getDefaultMethodSignatureMapper() {
        return this.defaultMethodSignatureMapper;
    }

    public final JvmInnerClassesSupport getInnerClassesSupport() {
        return this.innerClassesSupport;
    }

    public final JvmCachedDeclarations getCachedDeclarations() {
        return this.cachedDeclarations;
    }

    @Override // org.jetbrains.kotlin.backend.common.CommonBackendContext
    public Mapping getMapping() {
        return this.mapping;
    }

    public final KtDiagnosticReporterWithImplicitIrBasedContext getKtDiagnosticReporter() {
        return this.ktDiagnosticReporter;
    }

    @Override // org.jetbrains.kotlin.backend.common.CommonBackendContext, org.jetbrains.kotlin.backend.common.BackendContext
    public JvmIr getIr() {
        return this.ir;
    }

    @Override // org.jetbrains.kotlin.backend.common.BackendContext
    public JvmSharedVariablesManager getSharedVariablesManager() {
        return this.sharedVariablesManager;
    }

    public final Function1<IrFunctionSymbol, IntrinsicMarker> getGetIntrinsic() {
        Function1 function1 = this.getIntrinsic;
        if (function1 != null) {
            return function1;
        }
        Intrinsics.throwUninitializedPropertyAccessException("getIntrinsic");
        return null;
    }

    public final void setGetIntrinsic(Function1<? super IrFunctionSymbol, ? extends IntrinsicMarker> function1) {
        Intrinsics.checkNotNullParameter(function1, "<set-?>");
        this.getIntrinsic = function1;
    }

    public final EnumEntriesIntrinsicMappingsCache getEnumEntriesIntrinsicMappingsCache() {
        EnumEntriesIntrinsicMappingsCache enumEntriesIntrinsicMappingsCache = this.enumEntriesIntrinsicMappingsCache;
        if (enumEntriesIntrinsicMappingsCache != null) {
            return enumEntriesIntrinsicMappingsCache;
        }
        Intrinsics.throwUninitializedPropertyAccessException("enumEntriesIntrinsicMappingsCache");
        return null;
    }

    public final void setEnumEntriesIntrinsicMappingsCache(EnumEntriesIntrinsicMappingsCache enumEntriesIntrinsicMappingsCache) {
        Intrinsics.checkNotNullParameter(enumEntriesIntrinsicMappingsCache, "<set-?>");
        this.enumEntriesIntrinsicMappingsCache = enumEntriesIntrinsicMappingsCache;
    }

    public final Map<Type, SMAP> getTypeToCachedSMAP() {
        return this.typeToCachedSMAP;
    }

    /* JADX INFO: renamed from: isCompilingAgainstJdk8OrLater, reason: from getter */
    public final boolean getIsCompilingAgainstJdk8OrLater() {
        return this.isCompilingAgainstJdk8OrLater;
    }

    public final Type getLocalClassType(IrAttributeContainer container) {
        Intrinsics.checkNotNullParameter(container, "container");
        return this.localClassType.get(container.getAttributeOwnerId());
    }

    public final void putLocalClassType(IrAttributeContainer container, Type value) {
        Intrinsics.checkNotNullParameter(container, "container");
        Intrinsics.checkNotNullParameter(value, "value");
        this.localClassType.put(container.getAttributeOwnerId(), value);
    }

    public final ConcurrentHashMap.KeySetView<IrAttributeContainer, Boolean> isEnclosedInConstructor() {
        return this.isEnclosedInConstructor;
    }

    public final ConcurrentHashMap<IrFunction, IrFunction> getEnclosingMethodOverride() {
        return this.enclosingMethodOverride;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Object getOrCreateClassCodegen$lambda$0(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return tmp0.invoke(obj);
    }

    public final <ClassCodegen> ClassCodegen getOrCreateClassCodegen(IrClass klass, final Function1<? super IrClass, ? extends ClassCodegen> create) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(create, "create");
        ClassCodegen classcodegen = (ClassCodegen) this.classCodegens.computeIfAbsent(klass, new Function() { // from class: org.jetbrains.kotlin.backend.jvm.JvmBackendContext$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return JvmBackendContext.getOrCreateClassCodegen$lambda$0(create, obj);
            }
        });
        Intrinsics.checkNotNull(classcodegen, "null cannot be cast to non-null type ClassCodegen of org.jetbrains.kotlin.backend.jvm.JvmBackendContext.getOrCreateClassCodegen");
        return classcodegen;
    }

    public final ConcurrentHashMap<IrAttributeContainer, List<IrLocalDelegatedPropertySymbol>> getLocalDelegatedProperties() {
        return this.localDelegatedProperties;
    }

    public final Map<JvmClassName, List<IrClass>> getMultifileFacadesToAdd() {
        return this.multifileFacadesToAdd;
    }

    public final Map<IrClass, JvmClassName> getMultifileFacadeForPart() {
        return this.multifileFacadeForPart;
    }

    public final Map<IrClass, IrClass> getMultifileFacadeClassForPart() {
        return this.multifileFacadeClassForPart;
    }

    public final Map<IrSimpleFunction, IrSimpleFunction> getMultifileFacadeMemberToPartMember() {
        return this.multifileFacadeMemberToPartMember;
    }

    public final ConcurrentHashMap<IrConstructor, IrConstructor> getHiddenConstructorsWithMangledParams() {
        return this.hiddenConstructorsWithMangledParams;
    }

    public final ConcurrentHashMap<IrConstructor, IrConstructor> getHiddenConstructorsOfSealedClasses() {
        return this.hiddenConstructorsOfSealedClasses;
    }

    public final CollectionStubComputer getCollectionStubComputer() {
        return this.collectionStubComputer;
    }

    public final void recordOverridesWithoutStubs(IrSimpleFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        this.overridesWithoutStubs.put(function, CollectionsKt.toList(function.getOverriddenSymbols()));
    }

    public final List<IrSimpleFunctionSymbol> getOverridesWithoutStubs(IrSimpleFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        List<IrSimpleFunctionSymbol> overriddenSymbols = this.overridesWithoutStubs.get(function);
        if (overriddenSymbols == null) {
            overriddenSymbols = function.getOverriddenSymbols();
        }
        return overriddenSymbols;
    }

    public final BridgeLoweringCache getBridgeLoweringCache() {
        return this.bridgeLoweringCache;
    }

    public final Set<IrFunction> getFunctionsWithSpecialBridges() {
        return this.functionsWithSpecialBridges;
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
    public CompilerConfiguration getConfiguration() {
        return this.state.getConfiguration();
    }

    @Override // org.jetbrains.kotlin.backend.common.BackendContext
    public FqName getInternalPackageFqn() {
        return this.internalPackageFqn;
    }

    public final ConcurrentHashMap<IrAttributeContainer, IrFunction> getSuspendLambdaToOriginalFunctionMap() {
        return this.suspendLambdaToOriginalFunctionMap;
    }

    public final ConcurrentHashMap<IrSimpleFunction, IrSimpleFunction> getSuspendFunctionOriginalToView() {
        return this.suspendFunctionOriginalToView;
    }

    public final ConcurrentHashMap<IrSimpleFunctionSymbol, IrSimpleFunction> getStaticDefaultStubs() {
        return this.staticDefaultStubs;
    }

    public final MemoizedInlineClassReplacements getInlineClassReplacements() {
        return this.inlineClassReplacements;
    }

    public final MemoizedMultiFieldValueClassReplacements getMultiFieldValueClassReplacements() {
        return this.multiFieldValueClassReplacements;
    }

    public final Map<IrAttributeContainer, Map<Type, Integer>> getContinuationClassesVarsCountByType() {
        return this.continuationClassesVarsCountByType;
    }

    public final Object getInlineMethodGenerationLock() {
        return this.inlineMethodGenerationLock;
    }

    public final Set<IrClassSymbol> getPublicAbiSymbols() {
        return this.publicAbiSymbols;
    }

    public final Set<IrDeclaration> getVisitedDeclarationsForRegenerationLowering() {
        return this.visitedDeclarationsForRegenerationLowering;
    }

    public final IrClassSymbol referenceClass(ClassDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return this.symbolTable.getLazyWrapper().getDescriptorExtension().referenceClass(descriptor);
    }

    public final IrTypeParameterSymbol referenceTypeParameter$backend_jvm(TypeParameterDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return this.symbolTable.getLazyWrapper().getDescriptorExtension().referenceTypeParameter(descriptor);
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

    @Override // org.jetbrains.kotlin.backend.common.CommonBackendContext
    public void handleDeepCopy(Map<IrFileSymbol, IrFileSymbol> fileSymbolMap, Map<IrClassSymbol, IrClassSymbol> classSymbolMap, Map<IrSimpleFunctionSymbol, IrSimpleFunctionSymbol> functionSymbolMap) {
        IrSimpleFunction irSimpleFunction;
        IrSimpleFunctionSymbol symbol;
        IrSimpleFunction owner;
        IrSimpleFunction irSimpleFunction2;
        IrSimpleFunctionSymbol irSimpleFunctionSymbol;
        IrSimpleFunction owner2;
        IrSimpleFunction replacementFunction;
        IrSimpleFunctionSymbol irSimpleFunctionSymbol2;
        IrSimpleFunction owner3;
        IrSimpleFunction replacementFunction2;
        IrSimpleFunctionSymbol irSimpleFunctionSymbol3;
        IrSimpleFunction owner4;
        IrSimpleFunction replacementFunction3;
        IrSimpleFunctionSymbol irSimpleFunctionSymbol4;
        IrSimpleFunction owner5;
        IrSimpleFunction replacementFunction4;
        IrClass owner6;
        Intrinsics.checkNotNullParameter(fileSymbolMap, "fileSymbolMap");
        Intrinsics.checkNotNullParameter(classSymbolMap, "classSymbolMap");
        Intrinsics.checkNotNullParameter(functionSymbolMap, "functionSymbolMap");
        for (IrClass irClass : CollectionsKt.toList(getClassNameOverride().keySet())) {
            IrClassSymbol irClassSymbol = classSymbolMap.get(irClass.getSymbol());
            if (irClassSymbol != null) {
                Map<IrClass, JvmClassName> classNameOverride = getClassNameOverride();
                IrClass owner7 = irClassSymbol.getOwner();
                JvmClassName jvmClassName = getClassNameOverride().get(irClass);
                Intrinsics.checkNotNull(jvmClassName);
                classNameOverride.put(owner7, jvmClassName);
            }
        }
        for (Map.Entry<JvmClassName, List<IrClass>> entry : this.multifileFacadesToAdd.entrySet()) {
            List<IrClass> value = entry.getValue();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(value, 10));
            for (IrClass irClass2 : value) {
                IrClassSymbol irClassSymbol2 = classSymbolMap.get(irClass2.getSymbol());
                if (irClassSymbol2 != null && (owner6 = irClassSymbol2.getOwner()) != null) {
                    irClass2 = owner6;
                }
                arrayList.add(irClass2);
            }
            entry.setValue(CollectionsKt.toMutableList((Collection) arrayList));
        }
        for (Map.Entry<IrFunction, IrFunction> entry2 : this.multiFieldValueClassReplacements.getOriginalFunctionForStaticReplacement().entrySet()) {
            IrFunction key = entry2.getKey();
            IrFunction value2 = entry2.getValue();
            if ((key instanceof IrSimpleFunction) && (irSimpleFunctionSymbol4 = functionSymbolMap.get(value2.getSymbol())) != null && (owner5 = irSimpleFunctionSymbol4.getOwner()) != null && (replacementFunction4 = this.multiFieldValueClassReplacements.getReplacementFunction(owner5)) != null) {
                functionSymbolMap.put(((IrSimpleFunction) key).getSymbol(), replacementFunction4.getSymbol());
            }
        }
        for (Map.Entry<IrFunction, IrFunction> entry3 : this.multiFieldValueClassReplacements.getOriginalFunctionForMethodReplacement().entrySet()) {
            IrFunction key2 = entry3.getKey();
            IrFunction value3 = entry3.getValue();
            if ((key2 instanceof IrSimpleFunction) && (irSimpleFunctionSymbol3 = functionSymbolMap.get(value3.getSymbol())) != null && (owner4 = irSimpleFunctionSymbol3.getOwner()) != null && (replacementFunction3 = this.multiFieldValueClassReplacements.getReplacementFunction(owner4)) != null) {
                functionSymbolMap.put(((IrSimpleFunction) key2).getSymbol(), replacementFunction3.getSymbol());
            }
        }
        for (Map.Entry<IrFunction, IrFunction> entry4 : this.inlineClassReplacements.getOriginalFunctionForStaticReplacement().entrySet()) {
            IrFunction key3 = entry4.getKey();
            IrFunction value4 = entry4.getValue();
            if ((key3 instanceof IrSimpleFunction) && (irSimpleFunctionSymbol2 = functionSymbolMap.get(value4.getSymbol())) != null && (owner3 = irSimpleFunctionSymbol2.getOwner()) != null && (replacementFunction2 = this.inlineClassReplacements.getReplacementFunction(owner3)) != null) {
                functionSymbolMap.put(((IrSimpleFunction) key3).getSymbol(), replacementFunction2.getSymbol());
            }
        }
        for (Map.Entry<IrFunction, IrFunction> entry5 : this.inlineClassReplacements.getOriginalFunctionForMethodReplacement().entrySet()) {
            IrFunction key4 = entry5.getKey();
            IrFunction value5 = entry5.getValue();
            if ((key4 instanceof IrSimpleFunction) && (irSimpleFunctionSymbol = functionSymbolMap.get(value5.getSymbol())) != null && (owner2 = irSimpleFunctionSymbol.getOwner()) != null && (replacementFunction = this.inlineClassReplacements.getReplacementFunction(owner2)) != null) {
                functionSymbolMap.put(((IrSimpleFunction) key4).getSymbol(), replacementFunction.getSymbol());
            }
        }
        for (Map.Entry<IrSimpleFunction, IrSimpleFunction> entry6 : this.suspendFunctionOriginalToView.entrySet()) {
            IrSimpleFunction key5 = entry6.getKey();
            IrSimpleFunction value6 = entry6.getValue();
            IrSimpleFunctionSymbol irSimpleFunctionSymbol5 = functionSymbolMap.get(key5.getSymbol());
            if (irSimpleFunctionSymbol5 != null && (owner = irSimpleFunctionSymbol5.getOwner()) != null && (irSimpleFunction2 = this.suspendFunctionOriginalToView.get(owner)) != null) {
                functionSymbolMap.put(value6.getSymbol(), irSimpleFunction2.getSymbol());
            }
        }
        for (Map.Entry<IrSimpleFunctionSymbol, IrSimpleFunction> entry7 : this.staticDefaultStubs.entrySet()) {
            IrSimpleFunctionSymbol key6 = entry7.getKey();
            IrSimpleFunctionSymbol symbol2 = entry7.getValue().getSymbol();
            IrSimpleFunctionSymbol irSimpleFunctionSymbol6 = functionSymbolMap.get(key6);
            if (irSimpleFunctionSymbol6 != null && (irSimpleFunction = this.staticDefaultStubs.get(irSimpleFunctionSymbol6)) != null && (symbol = irSimpleFunction.getSymbol()) != null) {
                functionSymbolMap.put(symbol2, symbol);
            }
        }
        super.handleDeepCopy(fileSymbolMap, classSymbolMap, functionSymbolMap);
    }

    @Override // org.jetbrains.kotlin.backend.common.CommonBackendContext
    public IrStatementOrigin getDoWhileCounterLoopOrigin() {
        return JvmLoweredStatementOrigin.DO_WHILE_COUNTER_LOOP.INSTANCE;
    }

    /* JADX INFO: compiled from: JvmBackendContext.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\b\u0086\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\b\u0010\n\u001a\u00020\u000bH\u0016R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext$JvmIr;", "Lorg/jetbrains/kotlin/backend/common/ir/Ir;", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;Lorg/jetbrains/kotlin/ir/util/SymbolTable;)V", "symbols", "Lorg/jetbrains/kotlin/backend/jvm/JvmSymbols;", "getSymbols", "()Lorg/jetbrains/kotlin/backend/jvm/JvmSymbols;", "shouldGenerateHandlerParameterForDefaultBodyFun", "", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class JvmIr extends Ir<JvmBackendContext> {
        private final JvmSymbols symbols;
        final /* synthetic */ JvmBackendContext this$0;

        @Override // org.jetbrains.kotlin.backend.common.ir.Ir
        public boolean shouldGenerateHandlerParameterForDefaultBodyFun() {
            return true;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public JvmIr(JvmBackendContext jvmBackendContext, SymbolTable symbolTable) {
            super(jvmBackendContext);
            Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
            this.this$0 = jvmBackendContext;
            this.symbols = new JvmSymbols(jvmBackendContext, symbolTable);
        }

        @Override // org.jetbrains.kotlin.backend.common.ir.Ir
        public JvmSymbols getSymbols() {
            return this.symbols;
        }
    }

    @Override // org.jetbrains.kotlin.backend.common.CommonBackendContext
    public void remapMultiFieldValueClassStructure(IrFunction oldFunction, IrFunction newFunction, Map<IrValueParameter, ? extends IrValueParameter> parametersMappingOrNull) {
        Map<IrValueParameter, ? extends IrValueParameter> map;
        MemoizedMultiFieldValueClassReplacements.RemappedParameter remappedParameterCopy$default;
        MemoizedMultiFieldValueClassReplacements.RemappedParameter regularMapping;
        Intrinsics.checkNotNullParameter(oldFunction, "oldFunction");
        Intrinsics.checkNotNullParameter(newFunction, "newFunction");
        if (parametersMappingOrNull != null) {
            map = parametersMappingOrNull;
        } else {
            if (IrUtilsKt.getExplicitParametersCount(oldFunction) != IrUtilsKt.getExplicitParametersCount(newFunction)) {
                throw new IllegalArgumentException(("Use non-default mapping instead:\n" + RenderIrElementKt.render$default(oldFunction, (DumpIrTreeOptions) null, 1, (Object) null) + '\n' + RenderIrElementKt.render$default(newFunction, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
            }
            map = MapsKt.toMap(CollectionsKt.zip(IrUtilsKt.getExplicitParameters(oldFunction), IrUtilsKt.getExplicitParameters(newFunction)));
        }
        List<MemoizedMultiFieldValueClassReplacements.RemappedParameter> list = this.multiFieldValueClassReplacements.getBindingNewFunctionToParameterTemplateStructure().get(oldFunction);
        if (list == null) {
            return;
        }
        ArrayList<MemoizedMultiFieldValueClassReplacements.RemappedParameter> arrayList = new ArrayList();
        for (MemoizedMultiFieldValueClassReplacements.RemappedParameter remappedParameter : list) {
            if (remappedParameter instanceof MemoizedMultiFieldValueClassReplacements.RemappedParameter.RegularMapping) {
                IrValueParameter irValueParameter = map.get(((MemoizedMultiFieldValueClassReplacements.RemappedParameter.RegularMapping) remappedParameter).getValueParameter());
                regularMapping = irValueParameter != null ? new MemoizedMultiFieldValueClassReplacements.RemappedParameter.RegularMapping(irValueParameter) : null;
            } else {
                if (!(remappedParameter instanceof MemoizedMultiFieldValueClassReplacements.RemappedParameter.MultiFieldValueClassMapping)) {
                    throw new NoWhenBranchMatchedException();
                }
                List<IrValueParameter> valueParameters = remappedParameter.getValueParameters();
                ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters, 10));
                Iterator<T> it = valueParameters.iterator();
                while (it.hasNext()) {
                    arrayList2.add(map.get((IrValueParameter) it.next()));
                }
                ArrayList<IrValueParameter> arrayList3 = arrayList2;
                boolean z = arrayList3 instanceof Collection;
                if (!z || !arrayList3.isEmpty()) {
                    Iterator it2 = arrayList3.iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            remappedParameterCopy$default = null;
                            break;
                        }
                        if (((IrValueParameter) it2.next()) != null) {
                            if (!z || !arrayList3.isEmpty()) {
                                Iterator it3 = arrayList3.iterator();
                                while (it3.hasNext()) {
                                    if (((IrValueParameter) it3.next()) == null) {
                                        throw new IllegalStateException(("Illegal new parameters:\n" + CollectionsKt.joinToString$default(arrayList3, "\n", null, null, 0, null, new Function1<IrValueParameter, CharSequence>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmBackendContext$remapMultiFieldValueClassStructure$newRemapsFromOld$1$5
                                            @Override // kotlin.jvm.functions.Function1
                                            public final CharSequence invoke(IrValueParameter irValueParameter2) {
                                                String strDump$default;
                                                if (irValueParameter2 == null || (strDump$default = DumpIrTreeKt.dump$default(irValueParameter2, null, 1, null)) == null) {
                                                    strDump$default = "null";
                                                }
                                                return strDump$default;
                                            }
                                        }, 30, null)).toString());
                                    }
                                }
                            }
                            MemoizedMultiFieldValueClassReplacements.RemappedParameter.MultiFieldValueClassMapping multiFieldValueClassMapping = (MemoizedMultiFieldValueClassReplacements.RemappedParameter.MultiFieldValueClassMapping) remappedParameter;
                            ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList3, 10));
                            for (IrValueParameter irValueParameter2 : arrayList3) {
                                Intrinsics.checkNotNull(irValueParameter2);
                                arrayList4.add(irValueParameter2);
                            }
                            remappedParameterCopy$default = MemoizedMultiFieldValueClassReplacements.RemappedParameter.MultiFieldValueClassMapping.copy$default(multiFieldValueClassMapping, null, null, arrayList4, 3, null);
                            break;
                        }
                    }
                } else {
                    remappedParameterCopy$default = null;
                    break;
                }
                regularMapping = remappedParameterCopy$default;
            }
            if (regularMapping != null) {
                arrayList.add(regularMapping);
            }
        }
        ArrayList arrayList5 = new ArrayList();
        for (MemoizedMultiFieldValueClassReplacements.RemappedParameter remappedParameter2 : arrayList) {
            List<IrValueParameter> valueParameters2 = remappedParameter2.getValueParameters();
            ArrayList arrayList6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(valueParameters2, 10));
            Iterator<T> it4 = valueParameters2.iterator();
            while (it4.hasNext()) {
                arrayList6.add(TuplesKt.to((IrValueParameter) it4.next(), remappedParameter2));
            }
            CollectionsKt.addAll(arrayList5, arrayList6);
        }
        Map map2 = MapsKt.toMap(arrayList5);
        List<IrValueParameter> explicitParameters = IrUtilsKt.getExplicitParameters(newFunction);
        ArrayList arrayList7 = new ArrayList(CollectionsKt.collectionSizeOrDefault(explicitParameters, 10));
        for (IrValueParameter irValueParameter3 : explicitParameters) {
            MemoizedMultiFieldValueClassReplacements.RemappedParameter.RegularMapping regularMapping2 = (MemoizedMultiFieldValueClassReplacements.RemappedParameter) map2.get(irValueParameter3);
            if (regularMapping2 == null) {
                regularMapping2 = new MemoizedMultiFieldValueClassReplacements.RemappedParameter.RegularMapping(irValueParameter3);
            }
            arrayList7.add(regularMapping2);
        }
        this.multiFieldValueClassReplacements.getBindingNewFunctionToParameterTemplateStructure().put(newFunction, CollectionsKt.distinct(arrayList7));
    }

    @Override // org.jetbrains.kotlin.backend.common.CommonBackendContext
    public IrExpression throwUninitializedPropertyAccessException(IrBuilderWithScope builder, String name2) {
        Intrinsics.checkNotNullParameter(builder, "builder");
        Intrinsics.checkNotNullParameter(name2, "name");
        IrBlockBuilder irBlockBuilder = new IrBlockBuilder(builder.getContext(), builder.getScope(), builder.getStartOffset(), builder.getEndOffset(), null, null, false, 64, null);
        irBlockBuilder.unaryPlus(super.throwUninitializedPropertyAccessException(builder, name2));
        return irBlockBuilder.getIrBlockBody();
    }
}
