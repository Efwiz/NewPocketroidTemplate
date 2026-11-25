package org.jetbrains.kotlin.backend.jvm;

import androidx.exifinterface.media.ExifInterface;
import com.android.dx.rop.code.RegisterSpec;
import com.gaurav.avnc.vnc.XKeySym;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.reandroid.arsc.chunk.OverlayablePolicy;
import com.reandroid.arsc.chunk.TypeBlock;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.Deprecated;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.sequences.SequencesKt;
import net.bytebuddy.description.method.ParameterDescription;
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.asJava.elements.KtToJvmAnnotationsConverterKt;
import org.jetbrains.kotlin.backend.common.ir.Symbols;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.InlineClassRepresentation;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.annotations.KotlinRetention;
import org.jetbrains.kotlin.descriptors.annotations.KotlinTarget;
import org.jetbrains.kotlin.incremental.IncrementalJvmCacheKt;
import org.jetbrains.kotlin.ir.BuiltInOperatorNames;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrClassBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrFieldBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrPropertyBuilder;
import org.jetbrains.kotlin.ir.builders.declarations.IrValueParameterBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.impl.IrEnumEntryImpl;
import org.jetbrains.kotlin.ir.declarations.impl.IrExternalPackageFragmentImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstructorCallImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrEnumEntrySymbolImpl;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeAbbreviation;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImpl;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.InlineClassesUtilsKt;
import org.jetbrains.kotlin.resolve.jvm.JvmClassName;
import org.jetbrains.kotlin.storage.LockBasedStorageManager;
import org.jetbrains.kotlin.storage.MemoizedFunctionToNotNull;
import org.jetbrains.kotlin.types.Variance;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: JvmSymbols.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000ê\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b<\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010$\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u001e\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0017\u0018\u0000 ¬\u00022\u00020\u0001:\b¬\u0002\u00ad\u0002®\u0002¯\u0002B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0014\u0010\u0086\u0002\u001a\u00030\u0087\u00022\b\u0010\u0088\u0002\u001a\u00030\u0089\u0002H\u0002JP\u0010\u008a\u0002\u001a\u00020\b2\b\u0010\u008b\u0002\u001a\u00030\u008c\u00022\n\b\u0002\u0010\u008d\u0002\u001a\u00030\u008e\u00022\n\b\u0002\u0010\u008f\u0002\u001a\u00030\u0090\u00022\t\b\u0002\u0010\u0091\u0002\u001a\u00020Z2\u0018\b\u0002\u0010\u0092\u0002\u001a\u0011\u0012\u0005\u0012\u00030\u0089\u0002\u0012\u0005\u0012\u00030\u0087\u00020\u0093\u0002H\u0002J\u001c\u0010\u0094\u0002\u001a\u00020\b2\b\u0010\u0095\u0002\u001a\u00030\u0092\u00012\u0007\u0010\u0096\u0002\u001a\u00020ZH\u0002J\u0013\u0010\u0097\u0002\u001a\u00020\u001d2\b\u0010\u0098\u0002\u001a\u00030\u0099\u0002H\u0002J\u001d\u0010\u009a\u0002\u001a\u00020\b2\b\u0010\u008b\u0002\u001a\u00030\u008c\u00022\b\u0010\u009b\u0002\u001a\u00030¹\u0001H\u0002J\u0014\u0010\u009c\u0002\u001a\u00030\u0082\u00012\b\u0010\u008b\u0002\u001a\u00030\u008c\u0002H\u0002J\u0014\u0010\u009d\u0002\u001a\u00030\u0087\u00022\b\u0010\u0088\u0002\u001a\u00030\u0089\u0002H\u0002J\u0010\u0010\u009e\u0002\u001a\u00020\u001d2\u0007\u0010\u009f\u0002\u001a\u00020\fJ\u0011\u0010 \u0002\u001a\u00020\b2\b\u0010¡\u0002\u001a\u00030\u0092\u0001J\u0011\u0010¢\u0002\u001a\u00020\b2\b\u0010¡\u0002\u001a\u00030\u0092\u0001J\u0011\u0010£\u0002\u001a\u00020\b2\b\u0010¡\u0002\u001a\u00030\u0092\u0001J#\u0010¤\u0002\u001a\u00020\b2\u0007\u0010¥\u0002\u001a\u00020Z2\b\u0010¡\u0002\u001a\u00030\u0092\u00012\u0007\u0010¦\u0002\u001a\u00020ZJ\u0011\u0010§\u0002\u001a\u00020\u001d2\b\u0010\u009b\u0002\u001a\u00030¹\u0001J\u0018\u0010¨\u0002\u001a\u00030\u0087\u0002*\u00030\u0089\u00022\u0007\u0010\u009f\u0002\u001a\u00020\fH\u0002J\u0018\u0010©\u0002\u001a\u00030\u0087\u0002*\u00030\u0089\u00022\u0007\u0010\u009f\u0002\u001a\u00020\fH\u0002J\u0018\u0010ª\u0002\u001a\u00030\u0087\u0002*\u00030\u0089\u00022\u0007\u0010«\u0002\u001a\u00020ZH\u0002R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u000f\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000eR\u0011\u0010\u0011\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\nR*\u0010\u0013\u001a\u001e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00160\u0014j\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u0016`\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0018\u001a\u00020\u0019¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u001c\u001a\u00020\u001d¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0011\u0010 \u001a\u00020\u001d¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001fR\u0011\u0010\"\u001a\u00020\u001d¢\u0006\b\n\u0000\u001a\u0004\b#\u0010\u001fR\u0011\u0010$\u001a\u00020\u001d¢\u0006\b\n\u0000\u001a\u0004\b%\u0010\u001fR\u000e\u0010&\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010'\u001a\u00020\u001d¢\u0006\b\n\u0000\u001a\u0004\b(\u0010\u001fR\u0011\u0010)\u001a\u00020\u001d¢\u0006\b\n\u0000\u001a\u0004\b*\u0010\u001fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010+\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b,\u0010\nR\u0011\u0010-\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b.\u0010\nR\u0014\u0010/\u001a\u00020\u001d8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b0\u0010\u001fR\u0014\u00101\u001a\u00020\u001d8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b2\u0010\u001fR\u0014\u00103\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b4\u0010\nR\u0014\u00105\u001a\u00020\u001d8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b6\u0010\u001fR\u0011\u00107\u001a\u00020\u001d¢\u0006\b\n\u0000\u001a\u0004\b8\u0010\u001fR\u0014\u00109\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b:\u0010\nR\u000e\u0010;\u001a\u00020\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010<\u001a\u00020\u001d8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b>\u0010?\u001a\u0004\b=\u0010\u001fR\u0011\u0010@\u001a\u00020\u001d¢\u0006\b\n\u0000\u001a\u0004\bA\u0010\u001fR\u0011\u0010B\u001a\u00020\u001d¢\u0006\b\n\u0000\u001a\u0004\bC\u0010\u001fR\u0011\u0010D\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\bE\u0010\nR\u000e\u0010F\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010G\u001a\u00020\u001d¢\u0006\b\n\u0000\u001a\u0004\bH\u0010\u001fR\u0011\u0010I\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\bJ\u0010\nR\u0014\u0010K\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\bL\u0010\nR\u0011\u0010M\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\bN\u0010\nR\u0011\u0010O\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\bP\u0010\nR\u0011\u0010Q\u001a\u00020\u001d¢\u0006\b\n\u0000\u001a\u0004\bR\u0010\u001fR\u0011\u0010S\u001a\u00020\u001d¢\u0006\b\n\u0000\u001a\u0004\bT\u0010\u001fR\u0011\u0010U\u001a\u00020\u001d¢\u0006\b\n\u0000\u001a\u0004\bV\u0010\u001fR\u0011\u0010W\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\bX\u0010\nR\u000e\u0010Y\u001a\u00020ZX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010[\u001a\u00020\u001d¢\u0006\b\n\u0000\u001a\u0004\b\\\u0010\u001fR\u0014\u0010]\u001a\u00020\u001d8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b^\u0010\u001fR\u0011\u0010_\u001a\u00020\u001d¢\u0006\b\n\u0000\u001a\u0004\b`\u0010\u001fR'\u0010a\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u001d0b8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\be\u0010?\u001a\u0004\bc\u0010dR\u0011\u0010f\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\bg\u0010\nR\u0011\u0010h\u001a\u00020\u0019¢\u0006\b\n\u0000\u001a\u0004\bi\u0010\u001bR\u0011\u0010j\u001a\u00020\u001d¢\u0006\b\n\u0000\u001a\u0004\bk\u0010\u001fR\u0011\u0010l\u001a\u00020\u001d¢\u0006\b\n\u0000\u001a\u0004\bm\u0010\u001fR\u0011\u0010n\u001a\u00020\u001d¢\u0006\b\n\u0000\u001a\u0004\bo\u0010\u001fR\u000e\u0010p\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010q\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\br\u0010\nR\u000e\u0010s\u001a\u00020tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0015\u0010u\u001a\u00060vR\u00020\u0000¢\u0006\b\n\u0000\u001a\u0004\bw\u0010xR\u000e\u0010y\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010z\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b{\u0010\nR\u0011\u0010|\u001a\u00020\u0019¢\u0006\b\n\u0000\u001a\u0004\b}\u0010\u001bR\u000e\u0010~\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u007f\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000f\u0010\u0080\u0001\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0081\u0001\u001a\u00030\u0082\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u000f\u0010\u0083\u0001\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000f\u0010\u0084\u0001\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000f\u0010\u0085\u0001\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0086\u0001\u001a\u00030\u0082\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\u0087\u0001\u001a\u00030\u0088\u00018FX\u0086\u0084\u0002¢\u0006\u000f\n\u0005\b\u008b\u0001\u0010?\u001a\u0006\b\u0089\u0001\u0010\u008a\u0001R\u000f\u0010\u008c\u0001\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u008d\u0001\u001a\u00030\u0082\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010\u008e\u0001\u001a\u00020\u001d¢\u0006\t\n\u0000\u001a\u0005\b\u008f\u0001\u0010\u001fR0\u0010\u0090\u0001\u001a#\u0012\u000f\u0012\r \u0093\u0001*\u0005\u0018\u00010\u0092\u00010\u0092\u0001\u0012\r\u0012\u000b \u0093\u0001*\u0004\u0018\u00010\b0\b0\u0091\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010\u0094\u0001\u001a\u00020\u001d¢\u0006\t\n\u0000\u001a\u0005\b\u0095\u0001\u0010\u001fR\u0013\u0010\u0096\u0001\u001a\u00020\b¢\u0006\t\n\u0000\u001a\u0005\b\u0097\u0001\u0010\nR\u0013\u0010\u0098\u0001\u001a\u00020\u001d¢\u0006\t\n\u0000\u001a\u0005\b\u0099\u0001\u0010\u001fR\u0013\u0010\u009a\u0001\u001a\u00020\u001d¢\u0006\t\n\u0000\u001a\u0005\b\u009b\u0001\u0010\u001fR\u0013\u0010\u009c\u0001\u001a\u00020\b¢\u0006\t\n\u0000\u001a\u0005\b\u009d\u0001\u0010\nR\u0013\u0010\u009e\u0001\u001a\u00020\u001d¢\u0006\t\n\u0000\u001a\u0005\b\u009f\u0001\u0010\u001fR0\u0010 \u0001\u001a#\u0012\u000f\u0012\r \u0093\u0001*\u0005\u0018\u00010\u0092\u00010\u0092\u0001\u0012\r\u0012\u000b \u0093\u0001*\u0004\u0018\u00010\b0\b0\u0091\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0015\u0010¡\u0001\u001a\u00030¢\u0001¢\u0006\n\n\u0000\u001a\u0006\b£\u0001\u0010¤\u0001R\u0014\u0010¥\u0001\u001a\u00020\u0016¢\u0006\n\n\u0000\u001a\u0006\b¦\u0001\u0010§\u0001R\u000f\u0010¨\u0001\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010©\u0001\u001a\u00030\u0082\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u000f\u0010ª\u0001\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010«\u0001\u001a\u00030\u0082\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010¬\u0001\u001a\u00030\u0082\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u00ad\u0001\u001a\u00030\u0082\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010®\u0001\u001a\u00030\u0082\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0015\u0010¯\u0001\u001a\u00030\u0082\u0001¢\u0006\n\n\u0000\u001a\u0006\b°\u0001\u0010±\u0001R\u0010\u0010²\u0001\u001a\u00030\u0082\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010³\u0001\u001a\u00030\u0082\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010´\u0001\u001a\u00030\u0082\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010µ\u0001\u001a\u00030\u0082\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010¶\u0001\u001a\u00020\b¢\u0006\t\n\u0000\u001a\u0005\b·\u0001\u0010\nR\u0015\u0010¸\u0001\u001a\u00030¹\u0001¢\u0006\n\n\u0000\u001a\u0006\bº\u0001\u0010»\u0001R\u0013\u0010¼\u0001\u001a\u00020\u001d¢\u0006\t\n\u0000\u001a\u0005\b½\u0001\u0010\u001fR\u0013\u0010¾\u0001\u001a\u00020\u001d¢\u0006\t\n\u0000\u001a\u0005\b¿\u0001\u0010\u001fR \u0010À\u0001\u001a\u000f\u0012\u0005\u0012\u00030¹\u0001\u0012\u0004\u0012\u00020\b0b¢\u0006\t\n\u0000\u001a\u0005\bÁ\u0001\u0010dR&\u0010Â\u0001\u001a\t\u0012\u0004\u0012\u00020\b0Ã\u00018BX\u0082\u0084\u0002¢\u0006\u000f\n\u0005\bÆ\u0001\u0010?\u001a\u0006\bÄ\u0001\u0010Å\u0001R\u001d\u0010Ç\u0001\u001a\u0010\u0012\u0005\u0012\u00030É\u0001\u0012\u0004\u0012\u00020\b0È\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010Ê\u0001\u001a\u00020\b¢\u0006\t\n\u0000\u001a\u0005\bË\u0001\u0010\nR\u0013\u0010Ì\u0001\u001a\u00020\u001d¢\u0006\t\n\u0000\u001a\u0005\bÍ\u0001\u0010\u001fR\u0013\u0010Î\u0001\u001a\u00020\u001d¢\u0006\t\n\u0000\u001a\u0005\bÏ\u0001\u0010\u001fR\u0013\u0010Ð\u0001\u001a\u00020\b¢\u0006\t\n\u0000\u001a\u0005\bÑ\u0001\u0010\nR\u0013\u0010Ò\u0001\u001a\u00020\b¢\u0006\t\n\u0000\u001a\u0005\bÓ\u0001\u0010\nR\u000f\u0010Ô\u0001\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0015\u0010Õ\u0001\u001a\u00030¹\u0001¢\u0006\n\n\u0000\u001a\u0006\bÖ\u0001\u0010»\u0001R\u0016\u0010×\u0001\u001a\u00020\u001d8VX\u0096\u0004¢\u0006\u0007\u001a\u0005\bØ\u0001\u0010\u001fR\u0013\u0010Ù\u0001\u001a\u00020\u001d¢\u0006\t\n\u0000\u001a\u0005\bÚ\u0001\u0010\u001fR!\u0010Û\u0001\u001a\u00070Ü\u0001R\u00020\u0000¢\u0006\u0012\n\u0000\u0012\u0006\bÝ\u0001\u0010Þ\u0001\u001a\u0006\bß\u0001\u0010à\u0001R\u0013\u0010á\u0001\u001a\u00020\u001d¢\u0006\t\n\u0000\u001a\u0005\bâ\u0001\u0010\u001fR\u0013\u0010ã\u0001\u001a\u00020\u001d¢\u0006\t\n\u0000\u001a\u0005\bä\u0001\u0010\u001fR\u0013\u0010å\u0001\u001a\u00020\b¢\u0006\t\n\u0000\u001a\u0005\bæ\u0001\u0010\nR\u0010\u0010ç\u0001\u001a\u00030è\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010é\u0001\u001a\u00020\bX\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bê\u0001\u0010\nR\u0016\u0010ë\u0001\u001a\u00020\u001d8VX\u0096\u0004¢\u0006\u0007\u001a\u0005\bì\u0001\u0010\u001fR\u0013\u0010í\u0001\u001a\u00020\b¢\u0006\t\n\u0000\u001a\u0005\bî\u0001\u0010\nR\u0013\u0010ï\u0001\u001a\u00020\b¢\u0006\t\n\u0000\u001a\u0005\bð\u0001\u0010\nR\u0013\u0010ñ\u0001\u001a\u00020\u001d¢\u0006\t\n\u0000\u001a\u0005\bò\u0001\u0010\u001fR\u0016\u0010ó\u0001\u001a\u00020\u001dX\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bô\u0001\u0010\u001fR\u0013\u0010õ\u0001\u001a\u00020\u001d¢\u0006\t\n\u0000\u001a\u0005\bö\u0001\u0010\u001fR\u0016\u0010÷\u0001\u001a\u00020\u001dX\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bø\u0001\u0010\u001fR\u0016\u0010ù\u0001\u001a\u00020\u001dX\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bú\u0001\u0010\u001fR\u0016\u0010û\u0001\u001a\u00020\u001dX\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\bü\u0001\u0010\u001fR\u0013\u0010ý\u0001\u001a\u00020\u001d¢\u0006\t\n\u0000\u001a\u0005\bþ\u0001\u0010\u001fR\u0013\u0010ÿ\u0001\u001a\u00020\u001d¢\u0006\t\n\u0000\u001a\u0005\b\u0080\u0002\u0010\u001fR\u0013\u0010\u0081\u0002\u001a\u00020\u001d¢\u0006\t\n\u0000\u001a\u0005\b\u0082\u0002\u0010\u001fR\u0016\u0010\u0083\u0002\u001a\u00020\u001dX\u0096\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0084\u0002\u0010\u001fR\u001e\u0010\u0085\u0002\u001a\u0011\u0012\u0005\u0012\u00030¹\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u001d0bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006°\u0002"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmSymbols;", "Lorg/jetbrains/kotlin/backend/common/ir/Symbols;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;Lorg/jetbrains/kotlin/ir/util/SymbolTable;)V", "adaptedFunctionReference", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "getAdaptedFunctionReference", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "arrayOfAnyNType", "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "getArrayOfAnyNType", "()Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "arrayOfAnyType", "getArrayOfAnyType", "arraysClass", "getArraysClass", "arraysCopyOfFunctions", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "Lkotlin/collections/HashMap;", "assertionErrorConstructor", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "getAssertionErrorConstructor", "()Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "checkExpressionValueIsNotNull", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "getCheckExpressionValueIsNotNull", "()Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "checkNotNull", "getCheckNotNull", "checkNotNullExpressionValue", "getCheckNotNullExpressionValue", "checkNotNullWithMessage", "getCheckNotNullWithMessage", "collectionToArrayClass", "compareUnsignedInt", "getCompareUnsignedInt", "compareUnsignedLong", "getCompareUnsignedLong", "continuationClass", "getContinuationClass", "continuationImplClass", "getContinuationImplClass", "coroutineContextGetter", "getCoroutineContextGetter", "coroutineGetContext", "getCoroutineGetContext", "coroutineImpl", "getCoroutineImpl", "coroutineSuspendedGetter", "getCoroutineSuspendedGetter", "createEnumEntries", "getCreateEnumEntries", "defaultConstructorMarker", "getDefaultConstructorMarker", "defaultValueOfFunction", "desiredAssertionStatus", "getDesiredAssertionStatus", "desiredAssertionStatus$delegate", "Lkotlin/Lazy;", "divideUnsignedInt", "getDivideUnsignedInt", "divideUnsignedLong", "getDivideUnsignedLong", "enumEntries", "getEnumEntries", "enumEntriesKt", "enumValueOfFunction", "getEnumValueOfFunction", "funInterfaceConstructorReferenceClass", "getFunInterfaceConstructorReferenceClass", "functionAdapter", "getFunctionAdapter", "functionN", "getFunctionN", "functionReference", "getFunctionReference", "functionReferenceGetName", "getFunctionReferenceGetName", "functionReferenceGetOwner", "getFunctionReferenceGetOwner", "functionReferenceGetSignature", "getFunctionReferenceGetSignature", "functionReferenceImpl", "getFunctionReferenceImpl", "generateOptimizedCallableReferenceSuperClasses", "", "genericToArray", "getGenericToArray", "getContinuation", "getGetContinuation", "getOrCreateKotlinPackage", "getGetOrCreateKotlinPackage", "getProgressionLastElementByReturnType", "", "getGetProgressionLastElementByReturnType", "()Ljava/util/Map;", "getProgressionLastElementByReturnType$delegate", BuiltInOperatorNames.ILLEGAL_ARGUMENT_EXCEPTION, "getIllegalArgumentException", "illegalArgumentExceptionCtorString", "getIllegalArgumentExceptionCtorString", "indyLambdaMetafactoryIntrinsic", "getIndyLambdaMetafactoryIntrinsic", "intPostfixIncrDecr", "getIntPostfixIncrDecr", "intPrefixIncrDecr", "getIntPrefixIncrDecr", "intrinsicsClass", "intrinsicsKotlinClass", "getIntrinsicsKotlinClass", "irFactory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "javaAnnotations", "Lorg/jetbrains/kotlin/backend/jvm/JvmSymbols$JavaAnnotations;", "getJavaAnnotations", "()Lorg/jetbrains/kotlin/backend/jvm/JvmSymbols$JavaAnnotations;", "javaLangAssertionError", "javaLangClass", "getJavaLangClass", "javaLangDeprecatedConstructorWithDeprecatedFlag", "getJavaLangDeprecatedConstructorWithDeprecatedFlag", "javaLangDeprecatedWithDeprecatedFlag", "javaLangEnum", "javaLangInteger", "javaLangInvokePackage", "Lorg/jetbrains/kotlin/ir/declarations/IrPackageFragment;", "javaLangLong", "javaLangNoSuchFieldError", "javaLangObject", "javaLangPackage", "javaLangReflectSymbols", "Lorg/jetbrains/kotlin/backend/jvm/JvmReflectSymbols;", "getJavaLangReflectSymbols", "()Lorg/jetbrains/kotlin/backend/jvm/JvmReflectSymbols;", "javaLangReflectSymbols$delegate", "javaLangString", "javaUtilPackage", "jvmDebuggerInvokeSpecialIntrinsic", "getJvmDebuggerInvokeSpecialIntrinsic", "jvmFunctionClasses", "Lorg/jetbrains/kotlin/storage/MemoizedFunctionToNotNull;", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, "jvmIndyIntrinsic", "getJvmIndyIntrinsic", "jvmInlineAnnotation", "getJvmInlineAnnotation", "jvmMethodHandle", "getJvmMethodHandle", "jvmMethodType", "getJvmMethodType", "jvmName", "getJvmName", "jvmOriginalMethodTypeIntrinsic", "getJvmOriginalMethodTypeIntrinsic", "jvmSuspendFunctionClasses", "kClassJava", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "getKClassJava", "()Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "kClassJavaPropertyGetter", "getKClassJavaPropertyGetter", "()Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "kDeclarationContainer", "kotlinCoroutinesJvmInternalPackage", "kotlinCoroutinesJvmInternalRunSuspendKt", "kotlinCoroutinesPackage", "kotlinEnumsPackage", "kotlinInternalPackage", "kotlinJvmFunctionsPackage", "kotlinJvmInternalInvokeDynamicPackage", "getKotlinJvmInternalInvokeDynamicPackage", "()Lorg/jetbrains/kotlin/ir/declarations/IrPackageFragment;", "kotlinJvmInternalPackage", "kotlinJvmPackage", "kotlinPackage", "kotlinReflectPackage", "lambdaClass", "getLambdaClass", "noSuchFieldErrorType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "getNoSuchFieldErrorType", "()Lorg/jetbrains/kotlin/ir/types/IrType;", "nonGenericToArray", "getNonGenericToArray", "objectCloneFunction", "getObjectCloneFunction", "primitiveSpreadBuilders", "getPrimitiveSpreadBuilders", "progressionUtilClasses", "", "getProgressionUtilClasses", "()Ljava/util/List;", "progressionUtilClasses$delegate", "propertyReferenceClassCache", "", "Lorg/jetbrains/kotlin/backend/jvm/JvmSymbols$PropertyReferenceKey;", "reflection", "getReflection", "remainderUnsignedInt", "getRemainderUnsignedInt", "remainderUnsignedLong", "getRemainderUnsignedLong", "repeatableContainer", "getRepeatableContainer", "restrictedSuspendLambdaClass", "getRestrictedSuspendLambdaClass", "resultClassStub", "resultOfAnyType", "getResultOfAnyType", "returnIfSuspended", "getReturnIfSuspended", "runSuspendFunction", "getRunSuspendFunction", "serializedLambda", "Lorg/jetbrains/kotlin/backend/jvm/JvmSymbols$SerializedLambdaClass;", "getSerializedLambda$annotations", "()V", "getSerializedLambda", "()Lorg/jetbrains/kotlin/backend/jvm/JvmSymbols$SerializedLambdaClass;", "signatureStringIntrinsic", "getSignatureStringIntrinsic", "singleArgumentInlineFunction", "getSingleArgumentInlineFunction", "spreadBuilder", "getSpreadBuilder", "storageManager", "Lorg/jetbrains/kotlin/storage/LockBasedStorageManager;", "stringBuilder", "getStringBuilder", "suspendCoroutineUninterceptedOrReturn", "getSuspendCoroutineUninterceptedOrReturn", "suspendFunctionInterface", "getSuspendFunctionInterface", "suspendLambdaClass", "getSuspendLambdaClass", "throwIllegalAccessException", "getThrowIllegalAccessException", "throwKotlinNothingValueException", "getThrowKotlinNothingValueException", "throwNpe", "getThrowNpe", "throwNullPointerException", "getThrowNullPointerException", "throwTypeCastException", "getThrowTypeCastException", "throwUninitializedPropertyAccessException", "getThrowUninitializedPropertyAccessException", "throwUnsupportedOperationException", "getThrowUnsupportedOperationException", "toUnsignedStringInt", "getToUnsignedStringInt", "toUnsignedStringLong", "getToUnsignedStringLong", "unsafeCoerceIntrinsic", "getUnsafeCoerceIntrinsic", "valueOfFunctions", "addSuspendLambdaInterfaceFunctions", "", "klass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "createClass", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "classKind", "Lorg/jetbrains/kotlin/descriptors/ClassKind;", "classModality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "classIsValue", "block", "Lkotlin/Function1;", "createFunctionClass", "n", "isSuspend", "createIncrDecrFun", "intrinsicName", "", "createJavaPrimitiveClassWithUnsignedUtils", ModuleXmlParser.TYPE, "createPackage", "generateCallableReferenceMethods", "getArraysCopyOfFunction", "arrayType", "getFunction", "parameterCount", "getJvmFunctionClass", "getJvmSuspendFunctionClass", "getPropertyReferenceClass", "mutable", "impl", "typeToStringValueOfFunction", "addArraysCopyOfFunction", "addArraysEqualsFunction", "generateCallableReferenceSuperclassConstructors", "withArity", "Companion", "JavaAnnotations", "PropertyReferenceKey", "SerializedLambdaClass", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmSymbols extends Symbols {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final FqName FLEXIBLE_MUTABILITY_ANNOTATION_FQ_NAME;
    private static final FqName FLEXIBLE_NULLABILITY_ANNOTATION_FQ_NAME;
    public static final String INTRINSICS_CLASS_NAME = "kotlin/jvm/internal/Intrinsics";
    private static final FqName RAW_TYPE_ANNOTATION_FQ_NAME;
    private final IrClassSymbol adaptedFunctionReference;
    private final IrSimpleType arrayOfAnyNType;
    private final IrSimpleType arrayOfAnyType;
    private final IrClassSymbol arraysClass;
    private final HashMap<IrClassifierSymbol, IrSimpleFunction> arraysCopyOfFunctions;
    private final IrConstructorSymbol assertionErrorConstructor;
    private final IrSimpleFunctionSymbol checkExpressionValueIsNotNull;
    private final IrSimpleFunctionSymbol checkNotNull;
    private final IrSimpleFunctionSymbol checkNotNullExpressionValue;
    private final IrSimpleFunctionSymbol checkNotNullWithMessage;
    private final IrClassSymbol collectionToArrayClass;
    private final IrSimpleFunctionSymbol compareUnsignedInt;
    private final IrSimpleFunctionSymbol compareUnsignedLong;
    private final JvmBackendContext context;
    private final IrClassSymbol continuationClass;
    private final IrClassSymbol continuationImplClass;
    private final IrSimpleFunctionSymbol createEnumEntries;
    private final IrClassSymbol defaultConstructorMarker;
    private final IrSimpleFunctionSymbol defaultValueOfFunction;

    /* JADX INFO: renamed from: desiredAssertionStatus$delegate, reason: from kotlin metadata */
    private final Lazy desiredAssertionStatus;
    private final IrSimpleFunctionSymbol divideUnsignedInt;
    private final IrSimpleFunctionSymbol divideUnsignedLong;
    private final IrClassSymbol enumEntries;
    private final IrClassSymbol enumEntriesKt;
    private final IrSimpleFunctionSymbol enumValueOfFunction;
    private final IrClassSymbol funInterfaceConstructorReferenceClass;
    private final IrClassSymbol functionAdapter;
    private final IrClassSymbol functionN;
    private final IrClassSymbol functionReference;
    private final IrSimpleFunctionSymbol functionReferenceGetName;
    private final IrSimpleFunctionSymbol functionReferenceGetOwner;
    private final IrSimpleFunctionSymbol functionReferenceGetSignature;
    private final IrClassSymbol functionReferenceImpl;
    private final boolean generateOptimizedCallableReferenceSuperClasses;
    private final IrSimpleFunctionSymbol genericToArray;
    private final IrSimpleFunctionSymbol getOrCreateKotlinPackage;

    /* JADX INFO: renamed from: getProgressionLastElementByReturnType$delegate, reason: from kotlin metadata */
    private final Lazy getProgressionLastElementByReturnType;
    private final IrClassSymbol illegalArgumentException;
    private final IrConstructorSymbol illegalArgumentExceptionCtorString;
    private final IrSimpleFunctionSymbol indyLambdaMetafactoryIntrinsic;
    private final IrSimpleFunctionSymbol intPostfixIncrDecr;
    private final IrSimpleFunctionSymbol intPrefixIncrDecr;
    private final IrClassSymbol intrinsicsClass;
    private final IrClassSymbol intrinsicsKotlinClass;
    private final IrFactory irFactory;
    private final JavaAnnotations javaAnnotations;
    private final IrClassSymbol javaLangAssertionError;
    private final IrClassSymbol javaLangClass;
    private final IrConstructorSymbol javaLangDeprecatedConstructorWithDeprecatedFlag;
    private final IrClassSymbol javaLangDeprecatedWithDeprecatedFlag;
    private final IrClassSymbol javaLangEnum;
    private final IrClassSymbol javaLangInteger;
    private final IrPackageFragment javaLangInvokePackage;
    private final IrClassSymbol javaLangLong;
    private final IrClassSymbol javaLangNoSuchFieldError;
    private final IrClassSymbol javaLangObject;
    private final IrPackageFragment javaLangPackage;

    /* JADX INFO: renamed from: javaLangReflectSymbols$delegate, reason: from kotlin metadata */
    private final Lazy javaLangReflectSymbols;
    private final IrClassSymbol javaLangString;
    private final IrPackageFragment javaUtilPackage;
    private final IrSimpleFunctionSymbol jvmDebuggerInvokeSpecialIntrinsic;
    private final MemoizedFunctionToNotNull<Integer, IrClassSymbol> jvmFunctionClasses;
    private final IrSimpleFunctionSymbol jvmIndyIntrinsic;
    private final IrClassSymbol jvmInlineAnnotation;
    private final IrSimpleFunctionSymbol jvmMethodHandle;
    private final IrSimpleFunctionSymbol jvmMethodType;
    private final IrClassSymbol jvmName;
    private final IrSimpleFunctionSymbol jvmOriginalMethodTypeIntrinsic;
    private final MemoizedFunctionToNotNull<Integer, IrClassSymbol> jvmSuspendFunctionClasses;
    private final IrPropertySymbol kClassJava;
    private final IrSimpleFunction kClassJavaPropertyGetter;
    private final IrClassSymbol kDeclarationContainer;
    private final IrPackageFragment kotlinCoroutinesJvmInternalPackage;
    private final IrClassSymbol kotlinCoroutinesJvmInternalRunSuspendKt;
    private final IrPackageFragment kotlinCoroutinesPackage;
    private final IrPackageFragment kotlinEnumsPackage;
    private final IrPackageFragment kotlinInternalPackage;
    private final IrPackageFragment kotlinJvmFunctionsPackage;
    private final IrPackageFragment kotlinJvmInternalInvokeDynamicPackage;
    private final IrPackageFragment kotlinJvmInternalPackage;
    private final IrPackageFragment kotlinJvmPackage;
    private final IrPackageFragment kotlinPackage;
    private final IrPackageFragment kotlinReflectPackage;
    private final IrClassSymbol lambdaClass;
    private final IrType noSuchFieldErrorType;
    private final IrSimpleFunctionSymbol nonGenericToArray;
    private final IrSimpleFunctionSymbol objectCloneFunction;
    private final Map<IrType, IrClassSymbol> primitiveSpreadBuilders;

    /* JADX INFO: renamed from: progressionUtilClasses$delegate, reason: from kotlin metadata */
    private final Lazy progressionUtilClasses;
    private final Map<PropertyReferenceKey, IrClassSymbol> propertyReferenceClassCache;
    private final IrClassSymbol reflection;
    private final IrSimpleFunctionSymbol remainderUnsignedInt;
    private final IrSimpleFunctionSymbol remainderUnsignedLong;
    private final IrClassSymbol repeatableContainer;
    private final IrClassSymbol restrictedSuspendLambdaClass;
    private final IrClassSymbol resultClassStub;
    private final IrType resultOfAnyType;
    private final IrSimpleFunctionSymbol runSuspendFunction;
    private final SerializedLambdaClass serializedLambda;
    private final IrSimpleFunctionSymbol signatureStringIntrinsic;
    private final IrSimpleFunctionSymbol singleArgumentInlineFunction;
    private final IrClassSymbol spreadBuilder;
    private final LockBasedStorageManager storageManager;
    private final IrClassSymbol stringBuilder;
    private final IrClassSymbol suspendFunctionInterface;
    private final IrClassSymbol suspendLambdaClass;
    private final IrSimpleFunctionSymbol throwIllegalAccessException;
    private final IrSimpleFunctionSymbol throwKotlinNothingValueException;
    private final IrSimpleFunctionSymbol throwNpe;
    private final IrSimpleFunctionSymbol throwNullPointerException;
    private final IrSimpleFunctionSymbol throwTypeCastException;
    private final IrSimpleFunctionSymbol throwUninitializedPropertyAccessException;
    private final IrSimpleFunctionSymbol throwUnsupportedOperationException;
    private final IrSimpleFunctionSymbol toUnsignedStringInt;
    private final IrSimpleFunctionSymbol toUnsignedStringLong;
    private final IrSimpleFunctionSymbol unsafeCoerceIntrinsic;
    private final Map<IrType, IrSimpleFunctionSymbol> valueOfFunctions;

    public static /* synthetic */ void getSerializedLambda$annotations() {
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JvmSymbols(JvmBackendContext context, SymbolTable symbolTable) {
        IrValueParameter irValueParameter;
        IrType type;
        super(context.getIrBuiltIns(), symbolTable);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        this.context = context;
        this.storageManager = new LockBasedStorageManager(getClass().getSimpleName());
        this.irFactory = context.getIrFactory();
        this.kotlinPackage = createPackage(new FqName(IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME));
        this.kotlinCoroutinesPackage = createPackage(new FqName("kotlin.coroutines"));
        this.kotlinCoroutinesJvmInternalPackage = createPackage(new FqName("kotlin.coroutines.jvm.internal"));
        this.kotlinJvmPackage = createPackage(new FqName("kotlin.jvm"));
        this.kotlinJvmInternalPackage = createPackage(new FqName("kotlin.jvm.internal"));
        this.kotlinJvmFunctionsPackage = createPackage(new FqName("kotlin.jvm.functions"));
        this.kotlinEnumsPackage = createPackage(new FqName("kotlin.enums"));
        this.kotlinReflectPackage = createPackage(new FqName("kotlin.reflect"));
        this.javaLangPackage = createPackage(new FqName(CommonClassNames.DEFAULT_PACKAGE));
        this.javaLangInvokePackage = createPackage(new FqName("java.lang.invoke"));
        this.javaUtilPackage = createPackage(new FqName("java.util"));
        this.kotlinInternalPackage = createPackage(new FqName("kotlin.internal"));
        this.kotlinJvmInternalInvokeDynamicPackage = createPackage(new FqName("kotlin.jvm.internal.invokeDynamic"));
        this.generateOptimizedCallableReferenceSuperClasses = context.getConfig().getGenerateOptimizedCallableReferenceSuperClasses();
        FqName fqNameForTopLevelClassMaybeWithDollars = JvmClassName.byInternalName("kotlin/jvm/internal/Intrinsics").getFqNameForTopLevelClassMaybeWithDollars();
        Intrinsics.checkNotNullExpressionValue(fqNameForTopLevelClassMaybeWithDollars, "getFqNameForTopLevelClassMaybeWithDollars(...)");
        IrClassSymbol irClassSymbolCreateClass$default = createClass$default(this, fqNameForTopLevelClassMaybeWithDollars, null, null, false, new Function1<IrClass, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmSymbols$intrinsicsClass$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(IrClass irClass) {
                invoke2(irClass);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IrClass klass) {
                Intrinsics.checkNotNullParameter(klass, "klass");
                DeclarationBuildersKt.addValueParameter$default(DeclarationBuildersKt.addFunction$default(klass, "throwNullPointerException", this.this$0.getIrBuiltIns().getNothingType(), null, null, true, false, false, false, null, 0, 0, XKeySym.XK_Greek_mu, null), CrashHianalyticsData.MESSAGE, this.this$0.getIrBuiltIns().getStringType(), (IrDeclarationOrigin) null, 4, (Object) null);
                DeclarationBuildersKt.addValueParameter$default(DeclarationBuildersKt.addFunction$default(klass, "throwTypeCastException", this.this$0.getIrBuiltIns().getNothingType(), null, null, true, false, false, false, null, 0, 0, XKeySym.XK_Greek_mu, null), CrashHianalyticsData.MESSAGE, this.this$0.getIrBuiltIns().getStringType(), (IrDeclarationOrigin) null, 4, (Object) null);
                DeclarationBuildersKt.addValueParameter$default(DeclarationBuildersKt.addFunction$default(klass, "throwIllegalAccessException", this.this$0.getIrBuiltIns().getNothingType(), null, null, true, false, false, false, null, 0, 0, XKeySym.XK_Greek_mu, null), CrashHianalyticsData.MESSAGE, this.this$0.getIrBuiltIns().getStringType(), (IrDeclarationOrigin) null, 4, (Object) null);
                DeclarationBuildersKt.addValueParameter$default(DeclarationBuildersKt.addFunction$default(klass, "throwUnsupportedOperationException", this.this$0.getIrBuiltIns().getNothingType(), null, null, true, false, false, false, null, 0, 0, XKeySym.XK_Greek_mu, null), CrashHianalyticsData.MESSAGE, this.this$0.getIrBuiltIns().getStringType(), (IrDeclarationOrigin) null, 4, (Object) null);
                DeclarationBuildersKt.addValueParameter$default(DeclarationBuildersKt.addFunction$default(klass, "throwUninitializedPropertyAccessException", this.this$0.getIrBuiltIns().getUnitType(), null, null, true, false, false, false, null, 0, 0, XKeySym.XK_Greek_mu, null), "propertyName", this.this$0.getIrBuiltIns().getStringType(), (IrDeclarationOrigin) null, 4, (Object) null);
                DeclarationBuildersKt.addFunction$default(klass, "throwKotlinNothingValueException", this.this$0.getIrBuiltIns().getNothingType(), null, null, true, false, false, false, null, 0, 0, XKeySym.XK_Greek_mu, null);
                IrSimpleFunction irSimpleFunctionAddFunction$default = DeclarationBuildersKt.addFunction$default(klass, "checkExpressionValueIsNotNull", this.this$0.getIrBuiltIns().getUnitType(), null, null, true, false, false, false, null, 0, 0, XKeySym.XK_Greek_mu, null);
                JvmSymbols jvmSymbols = this.this$0;
                IrSimpleFunction irSimpleFunction = irSimpleFunctionAddFunction$default;
                DeclarationBuildersKt.addValueParameter$default(irSimpleFunction, "value", jvmSymbols.getIrBuiltIns().getAnyNType(), (IrDeclarationOrigin) null, 4, (Object) null);
                DeclarationBuildersKt.addValueParameter$default(irSimpleFunction, "expression", jvmSymbols.getIrBuiltIns().getStringType(), (IrDeclarationOrigin) null, 4, (Object) null);
                IrSimpleFunction irSimpleFunctionAddFunction$default2 = DeclarationBuildersKt.addFunction$default(klass, "checkNotNullExpressionValue", this.this$0.getIrBuiltIns().getUnitType(), null, null, true, false, false, false, null, 0, 0, XKeySym.XK_Greek_mu, null);
                JvmSymbols jvmSymbols2 = this.this$0;
                IrSimpleFunction irSimpleFunction2 = irSimpleFunctionAddFunction$default2;
                DeclarationBuildersKt.addValueParameter$default(irSimpleFunction2, "value", jvmSymbols2.getIrBuiltIns().getAnyNType(), (IrDeclarationOrigin) null, 4, (Object) null);
                DeclarationBuildersKt.addValueParameter$default(irSimpleFunction2, "expression", jvmSymbols2.getIrBuiltIns().getStringType(), (IrDeclarationOrigin) null, 4, (Object) null);
                IrSimpleFunction irSimpleFunctionAddFunction$default3 = DeclarationBuildersKt.addFunction$default(klass, "stringPlus", this.this$0.getIrBuiltIns().getStringType(), null, null, true, false, false, false, null, 0, 0, XKeySym.XK_Greek_mu, null);
                JvmSymbols jvmSymbols3 = this.this$0;
                IrSimpleFunction irSimpleFunction3 = irSimpleFunctionAddFunction$default3;
                DeclarationBuildersKt.addValueParameter$default(irSimpleFunction3, "self", IrTypesKt.makeNullable(jvmSymbols3.getIrBuiltIns().getStringType()), (IrDeclarationOrigin) null, 4, (Object) null);
                DeclarationBuildersKt.addValueParameter$default(irSimpleFunction3, "other", jvmSymbols3.getIrBuiltIns().getAnyNType(), (IrDeclarationOrigin) null, 4, (Object) null);
                DeclarationBuildersKt.addValueParameter$default(DeclarationBuildersKt.addFunction$default(klass, "checkNotNull", this.this$0.getIrBuiltIns().getUnitType(), null, null, true, false, false, false, null, 0, 0, XKeySym.XK_Greek_mu, null), "object", this.this$0.getIrBuiltIns().getAnyNType(), (IrDeclarationOrigin) null, 4, (Object) null);
                IrSimpleFunction irSimpleFunctionAddFunction$default4 = DeclarationBuildersKt.addFunction$default(klass, "checkNotNull", this.this$0.getIrBuiltIns().getUnitType(), null, null, true, false, false, false, null, 0, 0, XKeySym.XK_Greek_mu, null);
                JvmSymbols jvmSymbols4 = this.this$0;
                IrSimpleFunction irSimpleFunction4 = irSimpleFunctionAddFunction$default4;
                DeclarationBuildersKt.addValueParameter$default(irSimpleFunction4, "object", jvmSymbols4.getIrBuiltIns().getAnyNType(), (IrDeclarationOrigin) null, 4, (Object) null);
                DeclarationBuildersKt.addValueParameter$default(irSimpleFunction4, CrashHianalyticsData.MESSAGE, jvmSymbols4.getIrBuiltIns().getStringType(), (IrDeclarationOrigin) null, 4, (Object) null);
                DeclarationBuildersKt.addFunction$default(klass, "throwNpe", this.this$0.getIrBuiltIns().getUnitType(), null, null, true, false, false, false, null, 0, 0, XKeySym.XK_Greek_mu, null);
                DeclarationBuildersKt.addValueParameter$default(DeclarationBuildersKt.addFunction$default(klass, "singleArgumentInlineFunction", this.this$0.getIrBuiltIns().getUnitType(), null, null, true, false, false, true, null, 0, 0, 1900, null), ParameterDescription.NAME_PREFIX, IrTypesKt.getDefaultType(this.this$0.getIrBuiltIns().getFunctionClass()), (IrDeclarationOrigin) null, 4, (Object) null);
                List<IrDeclaration> declarations = klass.getDeclarations();
                IrFactory irFactory = this.this$0.irFactory;
                IrClassBuilder irClassBuilder = new IrClassBuilder();
                Name nameIdentifier = Name.identifier("Kotlin");
                Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                irClassBuilder.setName(nameIdentifier);
                IrClass irClassBuildClass = DeclarationBuildersKt.buildClass(irFactory, irClassBuilder);
                irClassBuildClass.setParent(klass);
                IrUtilsKt.createImplicitParameterDeclarationWithWrappedDescriptor(irClassBuildClass);
                declarations.add(irClassBuildClass);
            }
        }, 14, null);
        this.intrinsicsClass = irClassSymbolCreateClass$default;
        boolean z = false;
        IrSimpleFunctionSymbol irSimpleFunctionSymbol = null;
        for (IrSimpleFunctionSymbol irSimpleFunctionSymbol2 : IrUtilsKt.getFunctions(irClassSymbolCreateClass$default)) {
            if (Intrinsics.areEqual(irSimpleFunctionSymbol2.getOwner().getName().asString(), "singleArgumentInlineFunction")) {
                if (z) {
                    throw new IllegalArgumentException("Sequence contains more than one matching element.");
                }
                irSimpleFunctionSymbol = irSimpleFunctionSymbol2;
                z = true;
            }
        }
        if (z) {
            this.singleArgumentInlineFunction = irSimpleFunctionSymbol;
            boolean z2 = false;
            IrSimpleFunctionSymbol irSimpleFunctionSymbol3 = null;
            for (IrSimpleFunctionSymbol irSimpleFunctionSymbol4 : IrUtilsKt.getFunctions(this.intrinsicsClass)) {
                if (Intrinsics.areEqual(irSimpleFunctionSymbol4.getOwner().getName().asString(), "checkExpressionValueIsNotNull")) {
                    if (z2) {
                        throw new IllegalArgumentException("Sequence contains more than one matching element.");
                    }
                    irSimpleFunctionSymbol3 = irSimpleFunctionSymbol4;
                    z2 = true;
                }
            }
            if (z2) {
                this.checkExpressionValueIsNotNull = irSimpleFunctionSymbol3;
                boolean z3 = false;
                IrSimpleFunctionSymbol irSimpleFunctionSymbol5 = null;
                for (IrSimpleFunctionSymbol irSimpleFunctionSymbol6 : IrUtilsKt.getFunctions(this.intrinsicsClass)) {
                    if (Intrinsics.areEqual(irSimpleFunctionSymbol6.getOwner().getName().asString(), "checkNotNullExpressionValue")) {
                        if (z3) {
                            throw new IllegalArgumentException("Sequence contains more than one matching element.");
                        }
                        irSimpleFunctionSymbol5 = irSimpleFunctionSymbol6;
                        z3 = true;
                    }
                }
                if (z3) {
                    this.checkNotNullExpressionValue = irSimpleFunctionSymbol5;
                    boolean z4 = false;
                    IrSimpleFunction irSimpleFunction = null;
                    for (IrSimpleFunction irSimpleFunction2 : IrUtilsKt.getFunctions(this.intrinsicsClass.getOwner())) {
                        IrSimpleFunction irSimpleFunction3 = irSimpleFunction2;
                        if (Intrinsics.areEqual(irSimpleFunction3.getName().asString(), "checkNotNull") && irSimpleFunction3.getValueParameters().size() == 1) {
                            if (z4) {
                                throw new IllegalArgumentException("Sequence contains more than one matching element.");
                            }
                            irSimpleFunction = irSimpleFunction2;
                            z4 = true;
                        }
                    }
                    if (z4) {
                        this.checkNotNull = irSimpleFunction.getSymbol();
                        boolean z5 = false;
                        IrSimpleFunction irSimpleFunction4 = null;
                        for (IrSimpleFunction irSimpleFunction5 : IrUtilsKt.getFunctions(this.intrinsicsClass.getOwner())) {
                            IrSimpleFunction irSimpleFunction6 = irSimpleFunction5;
                            if (Intrinsics.areEqual(irSimpleFunction6.getName().asString(), "checkNotNull") && irSimpleFunction6.getValueParameters().size() == 2) {
                                if (z5) {
                                    throw new IllegalArgumentException("Sequence contains more than one matching element.");
                                }
                                irSimpleFunction4 = irSimpleFunction5;
                                z5 = true;
                            }
                        }
                        if (z5) {
                            this.checkNotNullWithMessage = irSimpleFunction4.getSymbol();
                            boolean z6 = false;
                            IrSimpleFunctionSymbol irSimpleFunctionSymbol7 = null;
                            for (IrSimpleFunctionSymbol irSimpleFunctionSymbol8 : IrUtilsKt.getFunctions(this.intrinsicsClass)) {
                                if (Intrinsics.areEqual(irSimpleFunctionSymbol8.getOwner().getName().asString(), "throwNpe")) {
                                    if (z6) {
                                        throw new IllegalArgumentException("Sequence contains more than one matching element.");
                                    }
                                    irSimpleFunctionSymbol7 = irSimpleFunctionSymbol8;
                                    z6 = true;
                                }
                            }
                            if (z6) {
                                this.throwNpe = irSimpleFunctionSymbol7;
                                boolean z7 = false;
                                IrSimpleFunctionSymbol irSimpleFunctionSymbol9 = null;
                                for (IrSimpleFunctionSymbol irSimpleFunctionSymbol10 : IrUtilsKt.getFunctions(this.intrinsicsClass)) {
                                    if (Intrinsics.areEqual(irSimpleFunctionSymbol10.getOwner().getName().asString(), "throwNullPointerException")) {
                                        if (z7) {
                                            throw new IllegalArgumentException("Sequence contains more than one matching element.");
                                        }
                                        irSimpleFunctionSymbol9 = irSimpleFunctionSymbol10;
                                        z7 = true;
                                    }
                                }
                                if (z7) {
                                    this.throwNullPointerException = irSimpleFunctionSymbol9;
                                    boolean z8 = false;
                                    IrSimpleFunctionSymbol irSimpleFunctionSymbol11 = null;
                                    for (IrSimpleFunctionSymbol irSimpleFunctionSymbol12 : IrUtilsKt.getFunctions(this.intrinsicsClass)) {
                                        if (Intrinsics.areEqual(irSimpleFunctionSymbol12.getOwner().getName().asString(), "throwTypeCastException")) {
                                            if (z8) {
                                                throw new IllegalArgumentException("Sequence contains more than one matching element.");
                                            }
                                            irSimpleFunctionSymbol11 = irSimpleFunctionSymbol12;
                                            z8 = true;
                                        }
                                    }
                                    if (z8) {
                                        this.throwTypeCastException = irSimpleFunctionSymbol11;
                                        boolean z9 = false;
                                        IrSimpleFunctionSymbol irSimpleFunctionSymbol13 = null;
                                        for (IrSimpleFunctionSymbol irSimpleFunctionSymbol14 : IrUtilsKt.getFunctions(this.intrinsicsClass)) {
                                            if (Intrinsics.areEqual(irSimpleFunctionSymbol14.getOwner().getName().asString(), "throwIllegalAccessException")) {
                                                if (z9) {
                                                    throw new IllegalArgumentException("Sequence contains more than one matching element.");
                                                }
                                                irSimpleFunctionSymbol13 = irSimpleFunctionSymbol14;
                                                z9 = true;
                                            }
                                        }
                                        if (z9) {
                                            this.throwIllegalAccessException = irSimpleFunctionSymbol13;
                                            boolean z10 = false;
                                            IrSimpleFunctionSymbol irSimpleFunctionSymbol15 = null;
                                            for (IrSimpleFunctionSymbol irSimpleFunctionSymbol16 : IrUtilsKt.getFunctions(this.intrinsicsClass)) {
                                                if (Intrinsics.areEqual(irSimpleFunctionSymbol16.getOwner().getName().asString(), "throwUnsupportedOperationException")) {
                                                    if (z10) {
                                                        throw new IllegalArgumentException("Sequence contains more than one matching element.");
                                                    }
                                                    irSimpleFunctionSymbol15 = irSimpleFunctionSymbol16;
                                                    z10 = true;
                                                }
                                            }
                                            if (z10) {
                                                this.throwUnsupportedOperationException = irSimpleFunctionSymbol15;
                                                boolean z11 = false;
                                                IrSimpleFunctionSymbol irSimpleFunctionSymbol17 = null;
                                                for (IrSimpleFunctionSymbol irSimpleFunctionSymbol18 : IrUtilsKt.getFunctions(this.intrinsicsClass)) {
                                                    if (Intrinsics.areEqual(irSimpleFunctionSymbol18.getOwner().getName().asString(), "throwUninitializedPropertyAccessException")) {
                                                        if (z11) {
                                                            throw new IllegalArgumentException("Sequence contains more than one matching element.");
                                                        }
                                                        irSimpleFunctionSymbol17 = irSimpleFunctionSymbol18;
                                                        z11 = true;
                                                    }
                                                }
                                                if (z11) {
                                                    this.throwUninitializedPropertyAccessException = irSimpleFunctionSymbol17;
                                                    boolean z12 = false;
                                                    IrSimpleFunctionSymbol irSimpleFunctionSymbol19 = null;
                                                    for (IrSimpleFunctionSymbol irSimpleFunctionSymbol20 : IrUtilsKt.getFunctions(this.intrinsicsClass)) {
                                                        if (Intrinsics.areEqual(irSimpleFunctionSymbol20.getOwner().getName().asString(), "throwKotlinNothingValueException")) {
                                                            if (z12) {
                                                                throw new IllegalArgumentException("Sequence contains more than one matching element.");
                                                            }
                                                            irSimpleFunctionSymbol19 = irSimpleFunctionSymbol20;
                                                            z12 = true;
                                                        }
                                                    }
                                                    if (z12) {
                                                        this.throwKotlinNothingValueException = irSimpleFunctionSymbol19;
                                                        boolean z13 = false;
                                                        Object obj = null;
                                                        for (Object obj2 : this.intrinsicsClass.getOwner().getDeclarations()) {
                                                            IrDeclaration irDeclaration = (IrDeclaration) obj2;
                                                            if ((irDeclaration instanceof IrClass) && Intrinsics.areEqual(((IrClass) irDeclaration).getName().asString(), "Kotlin")) {
                                                                if (z13) {
                                                                    throw new IllegalArgumentException("Collection contains more than one matching element.");
                                                                }
                                                                obj = obj2;
                                                                z13 = true;
                                                            }
                                                        }
                                                        if (z13) {
                                                            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
                                                            this.intrinsicsKotlinClass = ((IrClass) obj).getSymbol();
                                                            this.stringBuilder = createClass$default(this, new FqName(CommonClassNames.JAVA_LANG_STRING_BUILDER), null, null, false, new Function1<IrClass, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmSymbols$stringBuilder$1
                                                                {
                                                                    super(1);
                                                                }

                                                                @Override // kotlin.jvm.functions.Function1
                                                                public /* bridge */ /* synthetic */ Unit invoke(IrClass irClass) {
                                                                    invoke2(irClass);
                                                                    return Unit.INSTANCE;
                                                                }

                                                                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                                                public final void invoke2(IrClass klass) {
                                                                    Intrinsics.checkNotNullParameter(klass, "klass");
                                                                    IrFactory factory = klass.getFactory();
                                                                    IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
                                                                    irFunctionBuilder.setReturnType(IrUtilsKt.getDefaultType(klass));
                                                                    IrConstructor irConstructorBuildConstructor = DeclarationBuildersKt.buildConstructor(factory, irFunctionBuilder);
                                                                    klass.getDeclarations().add(irConstructorBuildConstructor);
                                                                    irConstructorBuildConstructor.setParent(klass);
                                                                    IrSimpleFunction irSimpleFunctionAddFunction$default = DeclarationBuildersKt.addFunction$default(klass, "toString", this.this$0.getIrBuiltIns().getStringType(), null, null, false, false, false, false, null, 0, 0, 2044, null);
                                                                    irSimpleFunctionAddFunction$default.setOverriddenSymbols(CollectionsKt.plus((Collection<? extends IrSimpleFunctionSymbol>) irSimpleFunctionAddFunction$default.getOverriddenSymbols(), JvmSymbolsKt.functionByName(this.this$0.getAny(), "toString")));
                                                                    IrBuiltIns irBuiltIns = this.this$0.getIrBuiltIns();
                                                                    Iterator it = CollectionsKt.listOf((Object[]) new IrType[]{irBuiltIns.getAnyNType(), IrTypesKt.makeNullable(irBuiltIns.getStringType()), irBuiltIns.getBooleanType(), irBuiltIns.getCharType(), irBuiltIns.getIntType(), irBuiltIns.getLongType(), irBuiltIns.getFloatType(), irBuiltIns.getDoubleType()}).iterator();
                                                                    while (it.hasNext()) {
                                                                        DeclarationBuildersKt.addValueParameter$default(DeclarationBuildersKt.addFunction$default(klass, "append", IrUtilsKt.getDefaultType(klass), null, null, false, false, false, false, null, 0, 0, 2044, null), "value", (IrType) it.next(), (IrDeclarationOrigin) null, 4, (Object) null);
                                                                    }
                                                                }
                                                            }, 14, null);
                                                            this.enumEntries = createClass$default(this, new FqName("kotlin.enums.EnumEntries"), ClassKind.INTERFACE, null, false, new Function1<IrClass, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmSymbols$enumEntries$1
                                                                {
                                                                    super(1);
                                                                }

                                                                @Override // kotlin.jvm.functions.Function1
                                                                public /* bridge */ /* synthetic */ Unit invoke(IrClass irClass) {
                                                                    invoke2(irClass);
                                                                    return Unit.INSTANCE;
                                                                }

                                                                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                                                public final void invoke2(IrClass klass) {
                                                                    Intrinsics.checkNotNullParameter(klass, "klass");
                                                                    DeclarationBuildersKt.addTypeParameter$default(klass, ExifInterface.LONGITUDE_EAST, this.this$0.getIrBuiltIns().getAnyNType(), null, 4, null);
                                                                }
                                                            }, 12, null);
                                                            IrClassSymbol irClassSymbolCreateClass$default2 = createClass$default(this, new FqName("kotlin.enums.EnumEntriesKt"), null, null, false, new Function1<IrClass, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmSymbols$enumEntriesKt$1
                                                                {
                                                                    super(1);
                                                                }

                                                                @Override // kotlin.jvm.functions.Function1
                                                                public /* bridge */ /* synthetic */ Unit invoke(IrClass irClass) {
                                                                    invoke2(irClass);
                                                                    return Unit.INSTANCE;
                                                                }

                                                                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                                                public final void invoke2(IrClass klass) {
                                                                    Intrinsics.checkNotNullParameter(klass, "klass");
                                                                    IrSimpleFunction irSimpleFunctionAddFunction$default = DeclarationBuildersKt.addFunction$default(klass, "enumEntries", IrTypesKt.getDefaultType(this.this$0.getEnumEntries()), null, null, true, false, false, false, null, 0, 0, XKeySym.XK_Greek_mu, null);
                                                                    JvmSymbols jvmSymbols = this.this$0;
                                                                    DeclarationBuildersKt.addValueParameter$default(irSimpleFunctionAddFunction$default, TypeBlock.NAME_entries, IrTypesKt.typeWith(jvmSymbols.getIrBuiltIns().getArrayClass(), IrTypesKt.getDefaultType(DeclarationBuildersKt.addTypeParameter$default(irSimpleFunctionAddFunction$default, ExifInterface.LONGITUDE_EAST, IrTypesKt.getDefaultType(jvmSymbols.getIrBuiltIns().getEnumClass()), null, 4, null))), (IrDeclarationOrigin) null, 4, (Object) null);
                                                                }
                                                            }, 14, null);
                                                            this.enumEntriesKt = irClassSymbolCreateClass$default2;
                                                            boolean z14 = false;
                                                            IrSimpleFunctionSymbol irSimpleFunctionSymbol21 = null;
                                                            for (IrSimpleFunctionSymbol irSimpleFunctionSymbol22 : IrUtilsKt.getFunctions(irClassSymbolCreateClass$default2)) {
                                                                if (Intrinsics.areEqual(irSimpleFunctionSymbol22.getOwner().getName().asString(), "enumEntries")) {
                                                                    if (z14) {
                                                                        throw new IllegalArgumentException("Sequence contains more than one matching element.");
                                                                    }
                                                                    irSimpleFunctionSymbol21 = irSimpleFunctionSymbol22;
                                                                    z14 = true;
                                                                }
                                                            }
                                                            if (z14) {
                                                                this.createEnumEntries = irSimpleFunctionSymbol21;
                                                                this.defaultConstructorMarker = createClass$default(this, new FqName("kotlin.jvm.internal.DefaultConstructorMarker"), null, null, false, null, 30, null);
                                                                FqName safe = StandardNames.FqNames.kDeclarationContainer.toSafe();
                                                                Intrinsics.checkNotNullExpressionValue(safe, "toSafe(...)");
                                                                this.kDeclarationContainer = createClass$default(this, safe, ClassKind.INTERFACE, Modality.ABSTRACT, false, null, 24, null);
                                                                this.javaLangClass = createClass$default(this, new FqName(CommonClassNames.JAVA_LANG_CLASS), null, null, false, new Function1<IrClass, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmSymbols$javaLangClass$1
                                                                    {
                                                                        super(1);
                                                                    }

                                                                    @Override // kotlin.jvm.functions.Function1
                                                                    public /* bridge */ /* synthetic */ Unit invoke(IrClass irClass) {
                                                                        invoke2(irClass);
                                                                        return Unit.INSTANCE;
                                                                    }

                                                                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                                                    public final void invoke2(IrClass klass) {
                                                                        Intrinsics.checkNotNullParameter(klass, "klass");
                                                                        DeclarationBuildersKt.addTypeParameter(klass, ExifInterface.GPS_DIRECTION_TRUE, this.this$0.getIrBuiltIns().getAnyNType(), Variance.INVARIANT);
                                                                        DeclarationBuildersKt.addFunction$default(klass, "desiredAssertionStatus", this.this$0.getIrBuiltIns().getBooleanType(), null, null, false, false, false, false, null, 0, 0, 2044, null);
                                                                    }
                                                                }, 14, null);
                                                                IrClassSymbol irClassSymbolCreateClass$default3 = createClass$default(this, new FqName(CommonClassNames.JAVA_LANG_DEPRECATED), ClassKind.ANNOTATION_CLASS, null, false, new Function1<IrClass, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmSymbols$javaLangDeprecatedWithDeprecatedFlag$1
                                                                    @Override // kotlin.jvm.functions.Function1
                                                                    public /* bridge */ /* synthetic */ Unit invoke(IrClass irClass) {
                                                                        invoke2(irClass);
                                                                        return Unit.INSTANCE;
                                                                    }

                                                                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                                                    public final void invoke2(IrClass klass) {
                                                                        Intrinsics.checkNotNullParameter(klass, "klass");
                                                                        IrFactory factory = klass.getFactory();
                                                                        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
                                                                        irFunctionBuilder.setPrimary(true);
                                                                        irFunctionBuilder.setReturnType(IrUtilsKt.getDefaultType(klass));
                                                                        IrConstructor irConstructorBuildConstructor = DeclarationBuildersKt.buildConstructor(factory, irFunctionBuilder);
                                                                        klass.getDeclarations().add(irConstructorBuildConstructor);
                                                                        irConstructorBuildConstructor.setParent(klass);
                                                                    }
                                                                }, 12, null);
                                                                this.javaLangDeprecatedWithDeprecatedFlag = irClassSymbolCreateClass$default3;
                                                                this.javaLangDeprecatedConstructorWithDeprecatedFlag = (IrConstructorSymbol) SequencesKt.single(IrUtilsKt.getConstructors(irClassSymbolCreateClass$default3));
                                                                IrClassSymbol irClassSymbolCreateClass$default4 = createClass$default(this, new FqName(CommonClassNames.JAVA_LANG_ASSERTION_ERROR), null, Modality.OPEN, false, new Function1<IrClass, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmSymbols$javaLangAssertionError$1
                                                                    {
                                                                        super(1);
                                                                    }

                                                                    @Override // kotlin.jvm.functions.Function1
                                                                    public /* bridge */ /* synthetic */ Unit invoke(IrClass irClass) {
                                                                        invoke2(irClass);
                                                                        return Unit.INSTANCE;
                                                                    }

                                                                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                                                    public final void invoke2(IrClass klass) {
                                                                        Intrinsics.checkNotNullParameter(klass, "klass");
                                                                        IrFactory factory = klass.getFactory();
                                                                        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
                                                                        irFunctionBuilder.setReturnType(IrUtilsKt.getDefaultType(klass));
                                                                        IrConstructor irConstructorBuildConstructor = DeclarationBuildersKt.buildConstructor(factory, irFunctionBuilder);
                                                                        klass.getDeclarations().add(irConstructorBuildConstructor);
                                                                        irConstructorBuildConstructor.setParent(klass);
                                                                        DeclarationBuildersKt.addValueParameter$default(irConstructorBuildConstructor, "detailMessage", this.this$0.getIrBuiltIns().getAnyNType(), (IrDeclarationOrigin) null, 4, (Object) null);
                                                                    }
                                                                }, 10, null);
                                                                this.javaLangAssertionError = irClassSymbolCreateClass$default4;
                                                                this.assertionErrorConstructor = (IrConstructorSymbol) SequencesKt.single(IrUtilsKt.getConstructors(irClassSymbolCreateClass$default4));
                                                                IrClassSymbol irClassSymbolCreateClass$default5 = createClass$default(this, new FqName("java.lang.NoSuchFieldError"), null, Modality.OPEN, false, new Function1<IrClass, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmSymbols$javaLangNoSuchFieldError$1
                                                                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                                                    public final void invoke2(IrClass it) {
                                                                        Intrinsics.checkNotNullParameter(it, "it");
                                                                    }

                                                                    @Override // kotlin.jvm.functions.Function1
                                                                    public /* bridge */ /* synthetic */ Unit invoke(IrClass irClass) {
                                                                        invoke2(irClass);
                                                                        return Unit.INSTANCE;
                                                                    }
                                                                }, 10, null);
                                                                this.javaLangNoSuchFieldError = irClassSymbolCreateClass$default5;
                                                                this.noSuchFieldErrorType = IrTypesKt.getDefaultType(irClassSymbolCreateClass$default5);
                                                                this.continuationClass = createClass$default(this, StandardNames.CONTINUATION_INTERFACE_FQ_NAME, ClassKind.INTERFACE, null, false, new Function1<IrClass, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmSymbols$continuationClass$1
                                                                    {
                                                                        super(1);
                                                                    }

                                                                    @Override // kotlin.jvm.functions.Function1
                                                                    public /* bridge */ /* synthetic */ Unit invoke(IrClass irClass) {
                                                                        invoke2(irClass);
                                                                        return Unit.INSTANCE;
                                                                    }

                                                                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                                                    public final void invoke2(IrClass klass) {
                                                                        Intrinsics.checkNotNullParameter(klass, "klass");
                                                                        DeclarationBuildersKt.addTypeParameter(klass, ExifInterface.GPS_DIRECTION_TRUE, this.this$0.getIrBuiltIns().getAnyNType(), Variance.IN_VARIANCE);
                                                                    }
                                                                }, 12, null);
                                                                IrClassSymbol irClassSymbolCreateClass$default6 = createClass$default(this, StandardNames.RESULT_FQ_NAME, null, null, true, new Function1<IrClass, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmSymbols$resultClassStub$1
                                                                    {
                                                                        super(1);
                                                                    }

                                                                    @Override // kotlin.jvm.functions.Function1
                                                                    public /* bridge */ /* synthetic */ Unit invoke(IrClass irClass) {
                                                                        invoke2(irClass);
                                                                        return Unit.INSTANCE;
                                                                    }

                                                                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                                                    public final void invoke2(IrClass klass) {
                                                                        Intrinsics.checkNotNullParameter(klass, "klass");
                                                                        DeclarationBuildersKt.addTypeParameter(klass, ExifInterface.GPS_DIRECTION_TRUE, this.this$0.getIrBuiltIns().getAnyNType(), Variance.OUT_VARIANCE);
                                                                        Name nameIdentifier = Name.identifier("value");
                                                                        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                                                                        IrType anyNType = this.this$0.getIrBuiltIns().getAnyNType();
                                                                        Intrinsics.checkNotNull(anyNType, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
                                                                        klass.setValueClassRepresentation(new InlineClassRepresentation(nameIdentifier, (IrSimpleType) anyNType));
                                                                    }
                                                                }, 6, null);
                                                                this.resultClassStub = irClassSymbolCreateClass$default6;
                                                                this.resultOfAnyType = IrTypesKt.typeWith(irClassSymbolCreateClass$default6, getIrBuiltIns().getAnyNType());
                                                                this.continuationImplClass = createClass$default(this, new FqName("kotlin.coroutines.jvm.internal.ContinuationImpl"), null, Modality.ABSTRACT, false, new Function1<IrClass, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmSymbols$continuationImplClass$1
                                                                    {
                                                                        super(1);
                                                                    }

                                                                    @Override // kotlin.jvm.functions.Function1
                                                                    public /* bridge */ /* synthetic */ Unit invoke(IrClass irClass) {
                                                                        invoke2(irClass);
                                                                        return Unit.INSTANCE;
                                                                    }

                                                                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                                                    public final void invoke2(IrClass klass) {
                                                                        Intrinsics.checkNotNullParameter(klass, "klass");
                                                                        IrSimpleType irSimpleTypeTypeWith = IrTypesKt.typeWith(this.this$0.getContinuationClass(), this.this$0.getIrBuiltIns().getAnyNType());
                                                                        klass.setSuperTypes(CollectionsKt.plus((Collection<? extends IrSimpleType>) klass.getSuperTypes(), irSimpleTypeTypeWith));
                                                                        IrFactory factory = klass.getFactory();
                                                                        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
                                                                        irFunctionBuilder.setReturnType(IrUtilsKt.getDefaultType(klass));
                                                                        IrConstructor irConstructorBuildConstructor = DeclarationBuildersKt.buildConstructor(factory, irFunctionBuilder);
                                                                        klass.getDeclarations().add(irConstructorBuildConstructor);
                                                                        irConstructorBuildConstructor.setParent(klass);
                                                                        DeclarationBuildersKt.addValueParameter$default(irConstructorBuildConstructor, CoroutineConstantsKt.SUSPEND_FUNCTION_COMPLETION_PARAMETER_NAME, IrTypesKt.makeNullable(irSimpleTypeTypeWith), (IrDeclarationOrigin) null, 4, (Object) null);
                                                                        DeclarationBuildersKt.addValueParameter$default(DeclarationBuildersKt.addFunction$default(klass, CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, this.this$0.getIrBuiltIns().getAnyNType(), Modality.ABSTRACT, null, false, false, false, false, null, 0, 0, XKeySym.XK_Greek_psi, null), CoroutineConstantsKt.SUSPEND_CALL_RESULT_NAME, this.this$0.getResultOfAnyType(), (IrDeclarationOrigin) null, 4, (Object) null);
                                                                    }
                                                                }, 10, null);
                                                                this.suspendFunctionInterface = createClass$default(this, new FqName("kotlin.coroutines.jvm.internal.SuspendFunction"), ClassKind.INTERFACE, null, false, null, 28, null);
                                                                this.lambdaClass = createClass$default(this, new FqName("kotlin.jvm.internal.Lambda"), null, Modality.ABSTRACT, false, new Function1<IrClass, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmSymbols$lambdaClass$1
                                                                    {
                                                                        super(1);
                                                                    }

                                                                    @Override // kotlin.jvm.functions.Function1
                                                                    public /* bridge */ /* synthetic */ Unit invoke(IrClass irClass) {
                                                                        invoke2(irClass);
                                                                        return Unit.INSTANCE;
                                                                    }

                                                                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                                                    public final void invoke2(IrClass klass) {
                                                                        Intrinsics.checkNotNullParameter(klass, "klass");
                                                                        IrFactory factory = klass.getFactory();
                                                                        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
                                                                        irFunctionBuilder.setReturnType(IrUtilsKt.getDefaultType(klass));
                                                                        IrConstructor irConstructorBuildConstructor = DeclarationBuildersKt.buildConstructor(factory, irFunctionBuilder);
                                                                        klass.getDeclarations().add(irConstructorBuildConstructor);
                                                                        irConstructorBuildConstructor.setParent(klass);
                                                                        DeclarationBuildersKt.addValueParameter$default(irConstructorBuildConstructor, "arity", this.this$0.getIrBuiltIns().getIntType(), (IrDeclarationOrigin) null, 4, (Object) null);
                                                                    }
                                                                }, 10, null);
                                                                this.suspendLambdaClass = createClass$default(this, new FqName("kotlin.coroutines.jvm.internal.SuspendLambda"), null, Modality.ABSTRACT, false, new Function1<IrClass, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmSymbols$suspendLambdaClass$1
                                                                    {
                                                                        super(1);
                                                                    }

                                                                    @Override // kotlin.jvm.functions.Function1
                                                                    public /* bridge */ /* synthetic */ Unit invoke(IrClass irClass) {
                                                                        invoke2(irClass);
                                                                        return Unit.INSTANCE;
                                                                    }

                                                                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                                                    public final void invoke2(IrClass klass) {
                                                                        Intrinsics.checkNotNullParameter(klass, "klass");
                                                                        this.this$0.addSuspendLambdaInterfaceFunctions(klass);
                                                                    }
                                                                }, 10, null);
                                                                this.restrictedSuspendLambdaClass = createClass$default(this, new FqName("kotlin.coroutines.jvm.internal.RestrictedSuspendLambda"), null, Modality.ABSTRACT, false, new Function1<IrClass, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmSymbols$restrictedSuspendLambdaClass$1
                                                                    {
                                                                        super(1);
                                                                    }

                                                                    @Override // kotlin.jvm.functions.Function1
                                                                    public /* bridge */ /* synthetic */ Unit invoke(IrClass irClass) {
                                                                        invoke2(irClass);
                                                                        return Unit.INSTANCE;
                                                                    }

                                                                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                                                    public final void invoke2(IrClass klass) {
                                                                        Intrinsics.checkNotNullParameter(klass, "klass");
                                                                        this.this$0.addSuspendLambdaInterfaceFunctions(klass);
                                                                    }
                                                                }, 10, null);
                                                                IrClassSymbol irClassSymbolCreateClass$default7 = createClass$default(this, new FqName("kotlin.jvm.internal.FunctionReference"), null, Modality.OPEN, false, new Function1<IrClass, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmSymbols$functionReference$1
                                                                    {
                                                                        super(1);
                                                                    }

                                                                    @Override // kotlin.jvm.functions.Function1
                                                                    public /* bridge */ /* synthetic */ Unit invoke(IrClass irClass) {
                                                                        invoke2(irClass);
                                                                        return Unit.INSTANCE;
                                                                    }

                                                                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                                                    public final void invoke2(IrClass klass) {
                                                                        Intrinsics.checkNotNullParameter(klass, "klass");
                                                                        IrFactory factory = klass.getFactory();
                                                                        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
                                                                        irFunctionBuilder.setReturnType(IrUtilsKt.getDefaultType(klass));
                                                                        IrConstructor irConstructorBuildConstructor = DeclarationBuildersKt.buildConstructor(factory, irFunctionBuilder);
                                                                        klass.getDeclarations().add(irConstructorBuildConstructor);
                                                                        IrClass irClass = klass;
                                                                        irConstructorBuildConstructor.setParent(irClass);
                                                                        DeclarationBuildersKt.addValueParameter$default(irConstructorBuildConstructor, "arity", this.this$0.getIrBuiltIns().getIntType(), (IrDeclarationOrigin) null, 4, (Object) null);
                                                                        IrFactory factory2 = klass.getFactory();
                                                                        IrFunctionBuilder irFunctionBuilder2 = new IrFunctionBuilder();
                                                                        irFunctionBuilder2.setReturnType(IrUtilsKt.getDefaultType(klass));
                                                                        IrConstructor irConstructorBuildConstructor2 = DeclarationBuildersKt.buildConstructor(factory2, irFunctionBuilder2);
                                                                        klass.getDeclarations().add(irConstructorBuildConstructor2);
                                                                        irConstructorBuildConstructor2.setParent(irClass);
                                                                        JvmSymbols jvmSymbols = this.this$0;
                                                                        IrConstructor irConstructor = irConstructorBuildConstructor2;
                                                                        DeclarationBuildersKt.addValueParameter$default(irConstructor, "arity", jvmSymbols.getIrBuiltIns().getIntType(), (IrDeclarationOrigin) null, 4, (Object) null);
                                                                        DeclarationBuildersKt.addValueParameter$default(irConstructor, "receiver", jvmSymbols.getIrBuiltIns().getAnyNType(), (IrDeclarationOrigin) null, 4, (Object) null);
                                                                        IrType anyNType = this.this$0.getIrBuiltIns().getAnyNType();
                                                                        DescriptorVisibility PROTECTED = DescriptorVisibilities.PROTECTED;
                                                                        Intrinsics.checkNotNullExpressionValue(PROTECTED, "PROTECTED");
                                                                        DeclarationBuildersKt.addField(klass, "receiver", anyNType, PROTECTED);
                                                                        this.this$0.generateCallableReferenceMethods(klass);
                                                                    }
                                                                }, 10, null);
                                                                this.functionReference = irClassSymbolCreateClass$default7;
                                                                this.functionReferenceGetSignature = JvmSymbolsKt.functionByName(irClassSymbolCreateClass$default7, "getSignature");
                                                                this.functionReferenceGetName = JvmSymbolsKt.functionByName(irClassSymbolCreateClass$default7, "getName");
                                                                this.functionReferenceGetOwner = JvmSymbolsKt.functionByName(irClassSymbolCreateClass$default7, "getOwner");
                                                                this.functionReferenceImpl = createClass$default(this, new FqName("kotlin.jvm.internal.FunctionReferenceImpl"), null, Modality.OPEN, false, new Function1<IrClass, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmSymbols$functionReferenceImpl$1
                                                                    {
                                                                        super(1);
                                                                    }

                                                                    @Override // kotlin.jvm.functions.Function1
                                                                    public /* bridge */ /* synthetic */ Unit invoke(IrClass irClass) {
                                                                        invoke2(irClass);
                                                                        return Unit.INSTANCE;
                                                                    }

                                                                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                                                    public final void invoke2(IrClass klass) {
                                                                        Intrinsics.checkNotNullParameter(klass, "klass");
                                                                        klass.setSuperTypes(CollectionsKt.listOf(IrTypesKt.getDefaultType(this.this$0.getFunctionReference())));
                                                                        if (this.this$0.generateOptimizedCallableReferenceSuperClasses) {
                                                                            this.this$0.generateCallableReferenceSuperclassConstructors(klass, true);
                                                                        }
                                                                    }
                                                                }, 10, null);
                                                                this.adaptedFunctionReference = createClass$default(this, new FqName("kotlin.jvm.internal.AdaptedFunctionReference"), null, Modality.OPEN, false, new Function1<IrClass, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmSymbols$adaptedFunctionReference$1
                                                                    {
                                                                        super(1);
                                                                    }

                                                                    @Override // kotlin.jvm.functions.Function1
                                                                    public /* bridge */ /* synthetic */ Unit invoke(IrClass irClass) {
                                                                        invoke2(irClass);
                                                                        return Unit.INSTANCE;
                                                                    }

                                                                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                                                    public final void invoke2(IrClass klass) {
                                                                        Intrinsics.checkNotNullParameter(klass, "klass");
                                                                        klass.setSuperTypes(CollectionsKt.listOf(this.this$0.getIrBuiltIns().getAnyType()));
                                                                        this.this$0.generateCallableReferenceSuperclassConstructors(klass, true);
                                                                    }
                                                                }, 10, null);
                                                                this.funInterfaceConstructorReferenceClass = createClass$default(this, new FqName("kotlin.jvm.internal.FunInterfaceConstructorReference"), null, Modality.OPEN, false, new Function1<IrClass, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmSymbols$funInterfaceConstructorReferenceClass$1
                                                                    {
                                                                        super(1);
                                                                    }

                                                                    @Override // kotlin.jvm.functions.Function1
                                                                    public /* bridge */ /* synthetic */ Unit invoke(IrClass irClass) {
                                                                        invoke2(irClass);
                                                                        return Unit.INSTANCE;
                                                                    }

                                                                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                                                    public final void invoke2(IrClass irClass) {
                                                                        Intrinsics.checkNotNullParameter(irClass, "irClass");
                                                                        irClass.setSuperTypes(CollectionsKt.listOf(this.this$0.getIrBuiltIns().getAnyType()));
                                                                        IrFactory factory = irClass.getFactory();
                                                                        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
                                                                        irFunctionBuilder.setReturnType(IrUtilsKt.getDefaultType(irClass));
                                                                        IrConstructor irConstructorBuildConstructor = DeclarationBuildersKt.buildConstructor(factory, irFunctionBuilder);
                                                                        irClass.getDeclarations().add(irConstructorBuildConstructor);
                                                                        irConstructorBuildConstructor.setParent(irClass);
                                                                        DeclarationBuildersKt.addValueParameter$default(irConstructorBuildConstructor, "funInterface", IrTypesKt.getStarProjectedType(this.this$0.getJavaLangClass()), (IrDeclarationOrigin) null, 4, (Object) null);
                                                                    }
                                                                }, 10, null);
                                                                MemoizedFunctionToNotNull<Integer, IrClassSymbol> memoizedFunctionToNotNullCreateMemoizedFunction = this.storageManager.createMemoizedFunction(new Function1<Integer, IrClassSymbol>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmSymbols$jvmFunctionClasses$1
                                                                    {
                                                                        super(1);
                                                                    }

                                                                    @Override // kotlin.jvm.functions.Function1
                                                                    public /* bridge */ /* synthetic */ IrClassSymbol invoke(Integer num) {
                                                                        return invoke(num.intValue());
                                                                    }

                                                                    public final IrClassSymbol invoke(int i) {
                                                                        return this.this$0.createFunctionClass(i, false);
                                                                    }
                                                                });
                                                                Intrinsics.checkNotNullExpressionValue(memoizedFunctionToNotNullCreateMemoizedFunction, "createMemoizedFunction(...)");
                                                                this.jvmFunctionClasses = memoizedFunctionToNotNullCreateMemoizedFunction;
                                                                MemoizedFunctionToNotNull<Integer, IrClassSymbol> memoizedFunctionToNotNullCreateMemoizedFunction2 = this.storageManager.createMemoizedFunction(new Function1<Integer, IrClassSymbol>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmSymbols$jvmSuspendFunctionClasses$1
                                                                    {
                                                                        super(1);
                                                                    }

                                                                    @Override // kotlin.jvm.functions.Function1
                                                                    public /* bridge */ /* synthetic */ IrClassSymbol invoke(Integer num) {
                                                                        return invoke(num.intValue());
                                                                    }

                                                                    public final IrClassSymbol invoke(int i) {
                                                                        return this.this$0.createFunctionClass(i, true);
                                                                    }
                                                                });
                                                                Intrinsics.checkNotNullExpressionValue(memoizedFunctionToNotNullCreateMemoizedFunction2, "createMemoizedFunction(...)");
                                                                this.jvmSuspendFunctionClasses = memoizedFunctionToNotNullCreateMemoizedFunction2;
                                                                this.functionN = createClass$default(this, new FqName("kotlin.jvm.functions.FunctionN"), ClassKind.INTERFACE, null, false, new Function1<IrClass, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmSymbols$functionN$1
                                                                    {
                                                                        super(1);
                                                                    }

                                                                    @Override // kotlin.jvm.functions.Function1
                                                                    public /* bridge */ /* synthetic */ Unit invoke(IrClass irClass) {
                                                                        invoke2(irClass);
                                                                        return Unit.INSTANCE;
                                                                    }

                                                                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                                                    public final void invoke2(IrClass klass) {
                                                                        Intrinsics.checkNotNullParameter(klass, "klass");
                                                                        IrSimpleFunction irSimpleFunctionAddFunction$default = DeclarationBuildersKt.addFunction$default(klass, "invoke", IrTypesKt.getDefaultType(DeclarationBuildersKt.addTypeParameter(klass, "R", this.this$0.getIrBuiltIns().getAnyNType(), Variance.OUT_VARIANCE)), Modality.ABSTRACT, null, false, false, false, false, null, 0, 0, XKeySym.XK_Greek_psi, null);
                                                                        JvmSymbols jvmSymbols = this.this$0;
                                                                        IrSimpleFunction irSimpleFunction7 = irSimpleFunctionAddFunction$default;
                                                                        IrValueParameterBuilder irValueParameterBuilder = new IrValueParameterBuilder();
                                                                        Name nameIdentifier = Name.identifier("args");
                                                                        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                                                                        irValueParameterBuilder.setName(nameIdentifier);
                                                                        irValueParameterBuilder.setType(IrTypesKt.typeWith(jvmSymbols.getIrBuiltIns().getArrayClass(), jvmSymbols.getIrBuiltIns().getAnyNType()));
                                                                        irValueParameterBuilder.setOrigin(IrDeclarationOrigin.DEFINED.INSTANCE);
                                                                        irValueParameterBuilder.setVarargElementType(jvmSymbols.getIrBuiltIns().getAnyNType());
                                                                        if (irValueParameterBuilder.getIndex() == -1) {
                                                                            irValueParameterBuilder.setIndex(irSimpleFunction7.getValueParameters().size());
                                                                        }
                                                                        irSimpleFunction7.setValueParameters(CollectionsKt.plus((Collection<? extends IrValueParameter>) irSimpleFunction7.getValueParameters(), DeclarationBuildersKt.buildValueParameter(irSimpleFunction7.getFactory(), irValueParameterBuilder, irSimpleFunction7)));
                                                                    }
                                                                }, 12, null);
                                                                IrClassSymbol irClassSymbolCreateClass$default8 = createClass$default(this, InlineClassesUtilsKt.getJVM_INLINE_ANNOTATION_FQ_NAME(), ClassKind.ANNOTATION_CLASS, null, false, null, 28, null);
                                                                IrClass owner = irClassSymbolCreateClass$default8.getOwner();
                                                                IrFactory factory = owner.getFactory();
                                                                IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
                                                                irFunctionBuilder.setPrimary(true);
                                                                irFunctionBuilder.setReturnType(IrUtilsKt.getDefaultType(owner));
                                                                IrConstructor irConstructorBuildConstructor = DeclarationBuildersKt.buildConstructor(factory, irFunctionBuilder);
                                                                owner.getDeclarations().add(irConstructorBuildConstructor);
                                                                irConstructorBuildConstructor.setParent(owner);
                                                                Unit unit = Unit.INSTANCE;
                                                                this.jvmInlineAnnotation = irClassSymbolCreateClass$default8;
                                                                this.propertyReferenceClassCache = new LinkedHashMap();
                                                                IrClassSymbol irClassSymbolCreateClass$default9 = createClass$default(this, new FqName("kotlin.jvm.internal.Reflection"), null, null, false, new Function1<IrClass, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmSymbols$reflection$1
                                                                    {
                                                                        super(1);
                                                                    }

                                                                    @Override // kotlin.jvm.functions.Function1
                                                                    public /* bridge */ /* synthetic */ Unit invoke(IrClass irClass) {
                                                                        invoke2(irClass);
                                                                        return Unit.INSTANCE;
                                                                    }

                                                                    /* JADX WARN: Multi-variable type inference failed */
                                                                    /* JADX WARN: Type inference failed for: r13v1 */
                                                                    /* JADX WARN: Type inference failed for: r13v2, types: [int] */
                                                                    /* JADX WARN: Type inference failed for: r13v5 */
                                                                    /* JADX WARN: Type inference failed for: r19v0 */
                                                                    /* JADX WARN: Type inference failed for: r2v29, types: [java.lang.StringBuilder] */
                                                                    /* JADX WARN: Type inference failed for: r2v32, types: [org.jetbrains.kotlin.ir.IrBuiltIns] */
                                                                    /* JADX WARN: Type inference fix 'apply assigned field type' failed
                                                                    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$PrimitiveArg
                                                                    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
                                                                    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
                                                                    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
                                                                    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
                                                                    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
                                                                    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
                                                                    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
                                                                     */
                                                                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                                                    public final void invoke2(IrClass klass) {
                                                                        Intrinsics.checkNotNullParameter(klass, "klass");
                                                                        IrSimpleType starProjectedType = IrTypesKt.getStarProjectedType(this.this$0.getJavaLangClass());
                                                                        IrSimpleType starProjectedType2 = IrTypesKt.getStarProjectedType(this.this$0.getIrBuiltIns().getKClassClass());
                                                                        IrSimpleFunction irSimpleFunctionAddFunction$default = DeclarationBuildersKt.addFunction$default(klass, "getOrCreateKotlinPackage", IrTypesKt.getDefaultType(this.this$0.kDeclarationContainer), null, null, true, false, false, false, null, 0, 0, XKeySym.XK_Greek_mu, null);
                                                                        JvmSymbols jvmSymbols = this.this$0;
                                                                        IrSimpleFunction irSimpleFunction7 = irSimpleFunctionAddFunction$default;
                                                                        IrSimpleType irSimpleType = starProjectedType;
                                                                        DeclarationBuildersKt.addValueParameter$default(irSimpleFunction7, "javaClass", irSimpleType, (IrDeclarationOrigin) null, 4, (Object) null);
                                                                        DeclarationBuildersKt.addValueParameter$default(irSimpleFunction7, "moduleName", jvmSymbols.getIrBuiltIns().getStringType(), (IrDeclarationOrigin) null, 4, (Object) null);
                                                                        DeclarationBuildersKt.addValueParameter$default(DeclarationBuildersKt.addFunction$default(klass, "getOrCreateKotlinClass", starProjectedType2, null, null, true, false, false, false, null, 0, 0, XKeySym.XK_Greek_mu, null), "javaClass", irSimpleType, (IrDeclarationOrigin) null, 4, (Object) null);
                                                                        boolean z15 = false;
                                                                        DeclarationBuildersKt.addValueParameter$default(DeclarationBuildersKt.addFunction$default(klass, "getOrCreateKotlinClasses", IrTypesKt.typeWith(this.this$0.getIrBuiltIns().getArrayClass(), starProjectedType2), null, null, true, false, false, false, null, 0, 0, XKeySym.XK_Greek_mu, null), "javaClasses", IrTypesKt.typeWith(this.this$0.getIrBuiltIns().getArrayClass(), starProjectedType), (IrDeclarationOrigin) null, 4, (Object) null);
                                                                        Iterator it = CollectionsKt.listOf((Object[]) new Boolean[]{false, true}).iterator();
                                                                        while (it.hasNext()) {
                                                                            boolean zBooleanValue = ((Boolean) it.next()).booleanValue();
                                                                            ?? r13 = z15;
                                                                            while (r13 < 3) {
                                                                                ?? r19 = r13;
                                                                                boolean z16 = zBooleanValue;
                                                                                Iterator it2 = it;
                                                                                boolean z17 = z15;
                                                                                DeclarationBuildersKt.addValueParameter$default(DeclarationBuildersKt.addFunction$default(klass, (zBooleanValue ? "mutableProperty" : "property") + r13, IrTypesKt.getStarProjectedType(this.this$0.getIrBuiltIns().getKPropertyClass(zBooleanValue, r13)), null, null, true, false, false, false, null, 0, 0, XKeySym.XK_Greek_mu, null), "p", IrTypesKt.getDefaultType(this.this$0.getPropertyReferenceClass(z16, r19 == true ? 1 : 0, z17)), (IrDeclarationOrigin) null, 4, (Object) null);
                                                                                z15 = z17;
                                                                                zBooleanValue = z16;
                                                                                it = it2;
                                                                                r13 = (r19 == true ? 1 : 0) + 1;
                                                                            }
                                                                        }
                                                                    }
                                                                }, 14, null);
                                                                this.reflection = irClassSymbolCreateClass$default9;
                                                                this.javaLangReflectSymbols = LazyKt.lazy(new Function0<JvmReflectSymbols>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmSymbols$javaLangReflectSymbols$2
                                                                    {
                                                                        super(0);
                                                                    }

                                                                    @Override // kotlin.jvm.functions.Function0
                                                                    public final JvmReflectSymbols invoke() {
                                                                        return new JvmReflectSymbols(this.this$0.context);
                                                                    }
                                                                });
                                                                this.functionAdapter = createClass$default(this, new FqName("kotlin.jvm.internal.FunctionAdapter"), ClassKind.INTERFACE, null, false, new Function1<IrClass, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmSymbols$functionAdapter$1
                                                                    {
                                                                        super(1);
                                                                    }

                                                                    @Override // kotlin.jvm.functions.Function1
                                                                    public /* bridge */ /* synthetic */ Unit invoke(IrClass irClass) {
                                                                        invoke2(irClass);
                                                                        return Unit.INSTANCE;
                                                                    }

                                                                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                                                    public final void invoke2(IrClass klass) {
                                                                        Intrinsics.checkNotNullParameter(klass, "klass");
                                                                        DeclarationBuildersKt.addFunction$default(klass, "getFunctionDelegate", IrTypesKt.getStarProjectedType(this.this$0.getIrBuiltIns().getFunctionClass()), Modality.ABSTRACT, null, false, false, false, false, null, 0, 0, XKeySym.XK_Greek_psi, null);
                                                                    }
                                                                }, 12, null);
                                                                this.getOrCreateKotlinPackage = JvmSymbolsKt.functionByName(irClassSymbolCreateClass$default9, "getOrCreateKotlinPackage");
                                                                this.desiredAssertionStatus = LazyKt.lazy(new Function0<IrSimpleFunctionSymbol>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmSymbols$desiredAssertionStatus$2
                                                                    {
                                                                        super(0);
                                                                    }

                                                                    @Override // kotlin.jvm.functions.Function0
                                                                    public final IrSimpleFunctionSymbol invoke() {
                                                                        return JvmSymbolsKt.functionByName(this.this$0.getJavaLangClass(), "desiredAssertionStatus");
                                                                    }
                                                                });
                                                                IrFactory irFactory = this.irFactory;
                                                                IrFunctionBuilder irFunctionBuilder2 = new IrFunctionBuilder();
                                                                Name nameSpecial = Name.special("<unsafe-coerce>");
                                                                Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
                                                                irFunctionBuilder2.setName(nameSpecial);
                                                                irFunctionBuilder2.setOrigin(IrDeclarationOrigin.IR_BUILTINS_STUB.INSTANCE);
                                                                Unit unit2 = Unit.INSTANCE;
                                                                IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder2);
                                                                irSimpleFunctionBuildFunction.setParent(this.kotlinJvmInternalPackage);
                                                                IrSimpleFunction irSimpleFunction7 = irSimpleFunctionBuildFunction;
                                                                IrTypeParameter irTypeParameterAddTypeParameter$default = DeclarationBuildersKt.addTypeParameter$default(irSimpleFunction7, ExifInterface.GPS_DIRECTION_TRUE, getIrBuiltIns().getAnyNType(), null, 4, null);
                                                                IrTypeParameter irTypeParameterAddTypeParameter$default2 = DeclarationBuildersKt.addTypeParameter$default(irSimpleFunction7, "R", getIrBuiltIns().getAnyNType(), null, 4, null);
                                                                DeclarationBuildersKt.addValueParameter$default(irSimpleFunctionBuildFunction, RegisterSpec.PREFIX, IrTypesKt.getDefaultType(irTypeParameterAddTypeParameter$default), (IrDeclarationOrigin) null, 4, (Object) null);
                                                                irSimpleFunctionBuildFunction.setReturnType(IrTypesKt.getDefaultType(irTypeParameterAddTypeParameter$default2));
                                                                Unit unit3 = Unit.INSTANCE;
                                                                this.unsafeCoerceIntrinsic = irSimpleFunctionBuildFunction.getSymbol();
                                                                this.progressionUtilClasses = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<List<? extends IrClassSymbol>>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmSymbols$progressionUtilClasses$2
                                                                    {
                                                                        super(0);
                                                                    }

                                                                    @Override // kotlin.jvm.functions.Function0
                                                                    public final List<? extends IrClassSymbol> invoke() {
                                                                        List<Pair> listListOf = CollectionsKt.listOf((Object[]) new Pair[]{TuplesKt.to("kotlin.internal.ProgressionUtilKt", CollectionsKt.listOf((Object[]) new IrClassSymbol[]{this.this$0.getInt(), this.this$0.getLong()})), TuplesKt.to("kotlin.internal.UProgressionUtilKt", CollectionsKt.listOfNotNull((Object[]) new IrClassSymbol[]{this.this$0.getUInt(), this.this$0.getULong()}))});
                                                                        final JvmSymbols jvmSymbols = this.this$0;
                                                                        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listListOf, 10));
                                                                        for (Pair pair : listListOf) {
                                                                            String str = (String) pair.component1();
                                                                            final List list = (List) pair.component2();
                                                                            arrayList.add(JvmSymbols.createClass$default(jvmSymbols, new FqName(str), null, null, false, new Function1<IrClass, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmSymbols$progressionUtilClasses$2$1$1
                                                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                                /* JADX WARN: Multi-variable type inference failed */
                                                                                {
                                                                                    super(1);
                                                                                }

                                                                                @Override // kotlin.jvm.functions.Function1
                                                                                public /* bridge */ /* synthetic */ Unit invoke(IrClass irClass) {
                                                                                    invoke2(irClass);
                                                                                    return Unit.INSTANCE;
                                                                                }

                                                                                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                                                                public final void invoke2(IrClass klass) {
                                                                                    IrSimpleType defaultType;
                                                                                    Intrinsics.checkNotNullParameter(klass, "klass");
                                                                                    for (IrClassSymbol irClassSymbol : list) {
                                                                                        IrSimpleFunction irSimpleFunctionAddFunction$default = DeclarationBuildersKt.addFunction$default(klass, "getProgressionLastElement", IrUtilsKt.getDefaultType(irClassSymbol.getOwner()), null, null, true, false, false, false, null, 0, 0, XKeySym.XK_Greek_mu, null);
                                                                                        JvmSymbols jvmSymbols2 = jvmSymbols;
                                                                                        String[] strArr = {"s", "e"};
                                                                                        for (int i = 0; i < 2; i++) {
                                                                                            DeclarationBuildersKt.addValueParameter$default(irSimpleFunctionAddFunction$default, strArr[i], IrUtilsKt.getDefaultType(irClassSymbol.getOwner()), (IrDeclarationOrigin) null, 4, (Object) null);
                                                                                        }
                                                                                        IrSimpleFunction irSimpleFunction8 = irSimpleFunctionAddFunction$default;
                                                                                        if (Intrinsics.areEqual(irClassSymbol, jvmSymbols2.getUInt())) {
                                                                                            defaultType = IrUtilsKt.getDefaultType(jvmSymbols2.getInt().getOwner());
                                                                                        } else {
                                                                                            defaultType = Intrinsics.areEqual(irClassSymbol, jvmSymbols2.getULong()) ? IrUtilsKt.getDefaultType(jvmSymbols2.getLong().getOwner()) : IrUtilsKt.getDefaultType(irClassSymbol.getOwner());
                                                                                        }
                                                                                        DeclarationBuildersKt.addValueParameter$default(irSimpleFunction8, "st", defaultType, (IrDeclarationOrigin) null, 4, (Object) null);
                                                                                    }
                                                                                }
                                                                            }, 14, null));
                                                                        }
                                                                        return arrayList;
                                                                    }
                                                                });
                                                                this.getProgressionLastElementByReturnType = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<Map<IrClassifierSymbol, ? extends IrSimpleFunctionSymbol>>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmSymbols$getProgressionLastElementByReturnType$2
                                                                    {
                                                                        super(0);
                                                                    }

                                                                    @Override // kotlin.jvm.functions.Function0
                                                                    public final Map<IrClassifierSymbol, ? extends IrSimpleFunctionSymbol> invoke() {
                                                                        List progressionUtilClasses = this.this$0.getProgressionUtilClasses();
                                                                        ArrayList arrayList = new ArrayList();
                                                                        Iterator it = progressionUtilClasses.iterator();
                                                                        while (it.hasNext()) {
                                                                            CollectionsKt.addAll(arrayList, SequencesKt.map(SequencesKt.filter(IrUtilsKt.getFunctions((IrClassSymbol) it.next()), new Function1<IrSimpleFunctionSymbol, Boolean>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmSymbols$getProgressionLastElementByReturnType$2$1$1
                                                                                @Override // kotlin.jvm.functions.Function1
                                                                                public final Boolean invoke(IrSimpleFunctionSymbol it2) {
                                                                                    Intrinsics.checkNotNullParameter(it2, "it");
                                                                                    return Boolean.valueOf(Intrinsics.areEqual(it2.getOwner().getName().getIdentifier(), "getProgressionLastElement"));
                                                                                }
                                                                            }), new Function1<IrSimpleFunctionSymbol, Pair<? extends IrClassifierSymbol, ? extends IrSimpleFunctionSymbol>>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmSymbols$getProgressionLastElementByReturnType$2$1$2
                                                                                @Override // kotlin.jvm.functions.Function1
                                                                                public final Pair<IrClassifierSymbol, IrSimpleFunctionSymbol> invoke(IrSimpleFunctionSymbol it2) {
                                                                                    Intrinsics.checkNotNullParameter(it2, "it");
                                                                                    return TuplesKt.to(IrTypesKt.getClassifierOrFail(it2.getOwner().getReturnType()), it2);
                                                                                }
                                                                            }));
                                                                        }
                                                                        return MapsKt.toMap(arrayList);
                                                                    }
                                                                });
                                                                IrSimpleType irSimpleTypeTypeWith = IrTypesKt.typeWith(getIrBuiltIns().getArrayClass(), getIrBuiltIns().getAnyType());
                                                                this.arrayOfAnyType = irSimpleTypeTypeWith;
                                                                this.arrayOfAnyNType = IrTypesKt.typeWith(getIrBuiltIns().getArrayClass(), getIrBuiltIns().getAnyNType());
                                                                IrFactory irFactory2 = this.irFactory;
                                                                IrFunctionBuilder irFunctionBuilder3 = new IrFunctionBuilder();
                                                                Name nameSpecial2 = Name.special("<jvm-indy-lambda-metafactory>");
                                                                Intrinsics.checkNotNullExpressionValue(nameSpecial2, "special(...)");
                                                                irFunctionBuilder3.setName(nameSpecial2);
                                                                irFunctionBuilder3.setOrigin(IrDeclarationOrigin.IR_BUILTINS_STUB.INSTANCE);
                                                                Unit unit4 = Unit.INSTANCE;
                                                                IrSimpleFunction irSimpleFunctionBuildFunction2 = DeclarationBuildersKt.buildFunction(irFactory2, irFunctionBuilder3);
                                                                irSimpleFunctionBuildFunction2.setParent(this.kotlinJvmInternalPackage);
                                                                IrTypeParameter irTypeParameterAddTypeParameter$default3 = DeclarationBuildersKt.addTypeParameter$default(irSimpleFunctionBuildFunction2, "SAM_TYPE", getIrBuiltIns().getAnyType(), null, 4, null);
                                                                IrSimpleFunction irSimpleFunction8 = irSimpleFunctionBuildFunction2;
                                                                DeclarationBuildersKt.addValueParameter$default(irSimpleFunction8, "samMethodType", getIrBuiltIns().getAnyNType(), (IrDeclarationOrigin) null, 4, (Object) null);
                                                                DeclarationBuildersKt.addValueParameter$default(irSimpleFunction8, "implMethodReference", getIrBuiltIns().getAnyNType(), (IrDeclarationOrigin) null, 4, (Object) null);
                                                                DeclarationBuildersKt.addValueParameter$default(irSimpleFunction8, "instantiatedMethodType", getIrBuiltIns().getAnyNType(), (IrDeclarationOrigin) null, 4, (Object) null);
                                                                IrValueParameterBuilder irValueParameterBuilder = new IrValueParameterBuilder();
                                                                Name nameIdentifier = Name.identifier("extraOverriddenMethodTypes");
                                                                Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                                                                irValueParameterBuilder.setName(nameIdentifier);
                                                                irValueParameterBuilder.setType(irSimpleTypeTypeWith);
                                                                irValueParameterBuilder.setVarargElementType(getIrBuiltIns().getAnyType());
                                                                if (irValueParameterBuilder.getIndex() == -1) {
                                                                    irValueParameterBuilder.setIndex(irSimpleFunction8.getValueParameters().size());
                                                                }
                                                                irSimpleFunction8.setValueParameters(CollectionsKt.plus((Collection<? extends IrValueParameter>) irSimpleFunction8.getValueParameters(), DeclarationBuildersKt.buildValueParameter(irSimpleFunction8.getFactory(), irValueParameterBuilder, irSimpleFunction8)));
                                                                DeclarationBuildersKt.addValueParameter$default(irSimpleFunction8, "shouldBeSerializable", getIrBuiltIns().getBooleanType(), (IrDeclarationOrigin) null, 4, (Object) null);
                                                                irSimpleFunctionBuildFunction2.setReturnType(IrTypesKt.getDefaultType(irTypeParameterAddTypeParameter$default3));
                                                                Unit unit5 = Unit.INSTANCE;
                                                                this.indyLambdaMetafactoryIntrinsic = irSimpleFunctionBuildFunction2.getSymbol();
                                                                this.serializedLambda = new SerializedLambdaClass();
                                                                IrClassSymbol irClassSymbolCreateClass$default10 = createClass$default(this, new FqName("java.lang.IllegalArgumentException"), null, null, false, new Function1<IrClass, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmSymbols$illegalArgumentException$1
                                                                    {
                                                                        super(1);
                                                                    }

                                                                    @Override // kotlin.jvm.functions.Function1
                                                                    public /* bridge */ /* synthetic */ Unit invoke(IrClass irClass) {
                                                                        invoke2(irClass);
                                                                        return Unit.INSTANCE;
                                                                    }

                                                                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                                                    public final void invoke2(IrClass irClass) {
                                                                        Intrinsics.checkNotNullParameter(irClass, "irClass");
                                                                        IrFactory factory2 = irClass.getFactory();
                                                                        IrFunctionBuilder irFunctionBuilder4 = new IrFunctionBuilder();
                                                                        Name nameSpecial3 = Name.special("<init>");
                                                                        Intrinsics.checkNotNullExpressionValue(nameSpecial3, "special(...)");
                                                                        irFunctionBuilder4.setName(nameSpecial3);
                                                                        irFunctionBuilder4.setReturnType(IrUtilsKt.getDefaultType(irClass));
                                                                        IrConstructor irConstructorBuildConstructor2 = DeclarationBuildersKt.buildConstructor(factory2, irFunctionBuilder4);
                                                                        irClass.getDeclarations().add(irConstructorBuildConstructor2);
                                                                        irConstructorBuildConstructor2.setParent(irClass);
                                                                        DeclarationBuildersKt.addValueParameter$default(irConstructorBuildConstructor2, CrashHianalyticsData.MESSAGE, this.this$0.getIrBuiltIns().getStringType(), (IrDeclarationOrigin) null, 4, (Object) null);
                                                                    }
                                                                }, 14, null);
                                                                this.illegalArgumentException = irClassSymbolCreateClass$default10;
                                                                this.illegalArgumentExceptionCtorString = (IrConstructorSymbol) SequencesKt.single(IrUtilsKt.getConstructors(irClassSymbolCreateClass$default10));
                                                                IrFactory irFactory3 = this.irFactory;
                                                                IrFunctionBuilder irFunctionBuilder4 = new IrFunctionBuilder();
                                                                Name nameSpecial3 = Name.special("<jvm-method-type>");
                                                                Intrinsics.checkNotNullExpressionValue(nameSpecial3, "special(...)");
                                                                irFunctionBuilder4.setName(nameSpecial3);
                                                                irFunctionBuilder4.setOrigin(IrDeclarationOrigin.IR_BUILTINS_STUB.INSTANCE);
                                                                Unit unit6 = Unit.INSTANCE;
                                                                IrSimpleFunction irSimpleFunctionBuildFunction3 = DeclarationBuildersKt.buildFunction(irFactory3, irFunctionBuilder4);
                                                                irSimpleFunctionBuildFunction3.setReturnType(getIrBuiltIns().getAnyType());
                                                                irSimpleFunctionBuildFunction3.setParent(this.kotlinJvmInternalPackage);
                                                                DeclarationBuildersKt.addValueParameter$default(irSimpleFunctionBuildFunction3, "descriptor", getIrBuiltIns().getStringType(), (IrDeclarationOrigin) null, 4, (Object) null);
                                                                Unit unit7 = Unit.INSTANCE;
                                                                this.jvmMethodType = irSimpleFunctionBuildFunction3.getSymbol();
                                                                IrFactory irFactory4 = this.irFactory;
                                                                IrFunctionBuilder irFunctionBuilder5 = new IrFunctionBuilder();
                                                                Name nameSpecial4 = Name.special("<jvm-method-handle>");
                                                                Intrinsics.checkNotNullExpressionValue(nameSpecial4, "special(...)");
                                                                irFunctionBuilder5.setName(nameSpecial4);
                                                                irFunctionBuilder5.setOrigin(IrDeclarationOrigin.IR_BUILTINS_STUB.INSTANCE);
                                                                Unit unit8 = Unit.INSTANCE;
                                                                IrSimpleFunction irSimpleFunctionBuildFunction4 = DeclarationBuildersKt.buildFunction(irFactory4, irFunctionBuilder5);
                                                                irSimpleFunctionBuildFunction4.setReturnType(getIrBuiltIns().getAnyType());
                                                                irSimpleFunctionBuildFunction4.setParent(this.kotlinJvmInternalPackage);
                                                                IrSimpleFunction irSimpleFunction9 = irSimpleFunctionBuildFunction4;
                                                                DeclarationBuildersKt.addValueParameter$default(irSimpleFunction9, "tag", getIrBuiltIns().getIntType(), (IrDeclarationOrigin) null, 4, (Object) null);
                                                                DeclarationBuildersKt.addValueParameter$default(irSimpleFunction9, "owner", getIrBuiltIns().getStringType(), (IrDeclarationOrigin) null, 4, (Object) null);
                                                                DeclarationBuildersKt.addValueParameter$default(irSimpleFunction9, "name", getIrBuiltIns().getStringType(), (IrDeclarationOrigin) null, 4, (Object) null);
                                                                DeclarationBuildersKt.addValueParameter$default(irSimpleFunction9, "descriptor", getIrBuiltIns().getStringType(), (IrDeclarationOrigin) null, 4, (Object) null);
                                                                DeclarationBuildersKt.addValueParameter$default(irSimpleFunction9, "isInterface", getIrBuiltIns().getBooleanType(), (IrDeclarationOrigin) null, 4, (Object) null);
                                                                Unit unit9 = Unit.INSTANCE;
                                                                this.jvmMethodHandle = irSimpleFunctionBuildFunction4.getSymbol();
                                                                IrFactory irFactory5 = this.irFactory;
                                                                IrFunctionBuilder irFunctionBuilder6 = new IrFunctionBuilder();
                                                                Name nameSpecial5 = Name.special("<jvm-indy>");
                                                                Intrinsics.checkNotNullExpressionValue(nameSpecial5, "special(...)");
                                                                irFunctionBuilder6.setName(nameSpecial5);
                                                                irFunctionBuilder6.setOrigin(IrDeclarationOrigin.IR_BUILTINS_STUB.INSTANCE);
                                                                Unit unit10 = Unit.INSTANCE;
                                                                IrSimpleFunction irSimpleFunctionBuildFunction5 = DeclarationBuildersKt.buildFunction(irFactory5, irFunctionBuilder6);
                                                                irSimpleFunctionBuildFunction5.setParent(this.kotlinJvmInternalPackage);
                                                                IrTypeParameter irTypeParameterAddTypeParameter$default4 = DeclarationBuildersKt.addTypeParameter$default(irSimpleFunctionBuildFunction5, ExifInterface.GPS_DIRECTION_TRUE, getIrBuiltIns().getAnyNType(), null, 4, null);
                                                                IrSimpleFunction irSimpleFunction10 = irSimpleFunctionBuildFunction5;
                                                                DeclarationBuildersKt.addValueParameter$default(irSimpleFunction10, "dynamicCall", IrTypesKt.getDefaultType(irTypeParameterAddTypeParameter$default4), (IrDeclarationOrigin) null, 4, (Object) null);
                                                                DeclarationBuildersKt.addValueParameter$default(irSimpleFunction10, "bootstrapMethodHandle", getIrBuiltIns().getAnyType(), (IrDeclarationOrigin) null, 4, (Object) null);
                                                                IrValueParameterBuilder irValueParameterBuilder2 = new IrValueParameterBuilder();
                                                                Name nameIdentifier2 = Name.identifier("bootstrapMethodArguments");
                                                                Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
                                                                irValueParameterBuilder2.setName(nameIdentifier2);
                                                                irValueParameterBuilder2.setType(irSimpleTypeTypeWith);
                                                                irValueParameterBuilder2.setVarargElementType(getIrBuiltIns().getAnyType());
                                                                if (irValueParameterBuilder2.getIndex() == -1) {
                                                                    irValueParameterBuilder2.setIndex(irSimpleFunction10.getValueParameters().size());
                                                                }
                                                                irSimpleFunction10.setValueParameters(CollectionsKt.plus((Collection<? extends IrValueParameter>) irSimpleFunction10.getValueParameters(), DeclarationBuildersKt.buildValueParameter(irSimpleFunction10.getFactory(), irValueParameterBuilder2, irSimpleFunction10)));
                                                                irSimpleFunctionBuildFunction5.setReturnType(IrTypesKt.getDefaultType(irTypeParameterAddTypeParameter$default4));
                                                                Unit unit11 = Unit.INSTANCE;
                                                                this.jvmIndyIntrinsic = irSimpleFunctionBuildFunction5.getSymbol();
                                                                IrFactory irFactory6 = this.irFactory;
                                                                IrFunctionBuilder irFunctionBuilder7 = new IrFunctionBuilder();
                                                                Name nameSpecial6 = Name.special("<jvm-original-method-type>");
                                                                Intrinsics.checkNotNullExpressionValue(nameSpecial6, "special(...)");
                                                                irFunctionBuilder7.setName(nameSpecial6);
                                                                irFunctionBuilder7.setOrigin(IrDeclarationOrigin.IR_BUILTINS_STUB.INSTANCE);
                                                                Unit unit12 = Unit.INSTANCE;
                                                                IrSimpleFunction irSimpleFunctionBuildFunction6 = DeclarationBuildersKt.buildFunction(irFactory6, irFunctionBuilder7);
                                                                irSimpleFunctionBuildFunction6.setParent(this.kotlinJvmInternalPackage);
                                                                DeclarationBuildersKt.addValueParameter$default(irSimpleFunctionBuildFunction6, "method", getIrBuiltIns().getAnyType(), (IrDeclarationOrigin) null, 4, (Object) null);
                                                                irSimpleFunctionBuildFunction6.setReturnType(getIrBuiltIns().getAnyType());
                                                                Unit unit13 = Unit.INSTANCE;
                                                                this.jvmOriginalMethodTypeIntrinsic = irSimpleFunctionBuildFunction6.getSymbol();
                                                                IrFactory irFactory7 = this.irFactory;
                                                                IrFunctionBuilder irFunctionBuilder8 = new IrFunctionBuilder();
                                                                Name nameSpecial7 = Name.special("<jvm-debugger-invokespecial>");
                                                                Intrinsics.checkNotNullExpressionValue(nameSpecial7, "special(...)");
                                                                irFunctionBuilder8.setName(nameSpecial7);
                                                                irFunctionBuilder8.setOrigin(IrDeclarationOrigin.IR_BUILTINS_STUB.INSTANCE);
                                                                Unit unit14 = Unit.INSTANCE;
                                                                IrSimpleFunction irSimpleFunctionBuildFunction7 = DeclarationBuildersKt.buildFunction(irFactory7, irFunctionBuilder8);
                                                                irSimpleFunctionBuildFunction7.setParent(this.kotlinJvmInternalPackage);
                                                                IrSimpleFunction irSimpleFunction11 = irSimpleFunctionBuildFunction7;
                                                                DeclarationBuildersKt.addValueParameter$default(irSimpleFunction11, "owner", getIrBuiltIns().getStringType(), (IrDeclarationOrigin) null, 4, (Object) null);
                                                                DeclarationBuildersKt.addValueParameter$default(irSimpleFunction11, "name", getIrBuiltIns().getStringType(), (IrDeclarationOrigin) null, 4, (Object) null);
                                                                DeclarationBuildersKt.addValueParameter$default(irSimpleFunction11, "descriptor", getIrBuiltIns().getStringType(), (IrDeclarationOrigin) null, 4, (Object) null);
                                                                DeclarationBuildersKt.addValueParameter$default(irSimpleFunction11, "isInterface", getIrBuiltIns().getBooleanType(), (IrDeclarationOrigin) null, 4, (Object) null);
                                                                irSimpleFunctionBuildFunction7.setReturnType(getIrBuiltIns().getAnyNType());
                                                                Unit unit15 = Unit.INSTANCE;
                                                                this.jvmDebuggerInvokeSpecialIntrinsic = irSimpleFunctionBuildFunction7.getSymbol();
                                                                IrClassSymbol irClassSymbolCreateClass$default11 = createClass$default(this, new FqName("kotlin.jvm.internal.CollectionToArray"), null, null, false, new Function1<IrClass, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmSymbols$collectionToArrayClass$1
                                                                    {
                                                                        super(1);
                                                                    }

                                                                    @Override // kotlin.jvm.functions.Function1
                                                                    public /* bridge */ /* synthetic */ Unit invoke(IrClass irClass) {
                                                                        invoke2(irClass);
                                                                        return Unit.INSTANCE;
                                                                    }

                                                                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                                                    public final void invoke2(IrClass klass) {
                                                                        Intrinsics.checkNotNullParameter(klass, "klass");
                                                                        klass.setOrigin(JvmLoweredDeclarationOrigin.TO_ARRAY.INSTANCE);
                                                                        IrSimpleType irSimpleTypeTypeWith2 = IrTypesKt.typeWith(this.this$0.getIrBuiltIns().getArrayClass(), this.this$0.getIrBuiltIns().getAnyNType());
                                                                        IrSimpleType starProjectedType = IrTypesKt.getStarProjectedType(this.this$0.getIrBuiltIns().getCollectionClass());
                                                                        IrSimpleType irSimpleType = irSimpleTypeTypeWith2;
                                                                        IrSimpleFunction irSimpleFunctionAddFunction$default = DeclarationBuildersKt.addFunction$default(klass, "toArray", irSimpleType, null, null, true, false, false, false, null, 0, 0, XKeySym.XK_Greek_mu, null);
                                                                        irSimpleFunctionAddFunction$default.setOrigin(JvmLoweredDeclarationOrigin.TO_ARRAY.INSTANCE);
                                                                        IrSimpleType irSimpleType2 = starProjectedType;
                                                                        DeclarationBuildersKt.addValueParameter(irSimpleFunctionAddFunction$default, "collection", irSimpleType2, JvmLoweredDeclarationOrigin.TO_ARRAY.INSTANCE);
                                                                        IrSimpleFunction irSimpleFunctionAddFunction$default2 = DeclarationBuildersKt.addFunction$default(klass, "toArray", irSimpleType, null, null, true, false, false, false, null, 0, 0, XKeySym.XK_Greek_mu, null);
                                                                        irSimpleFunctionAddFunction$default2.setOrigin(JvmLoweredDeclarationOrigin.TO_ARRAY.INSTANCE);
                                                                        IrSimpleFunction irSimpleFunction12 = irSimpleFunctionAddFunction$default2;
                                                                        DeclarationBuildersKt.addValueParameter(irSimpleFunction12, "collection", irSimpleType2, JvmLoweredDeclarationOrigin.TO_ARRAY.INSTANCE);
                                                                        DeclarationBuildersKt.addValueParameter(irSimpleFunction12, "array", IrTypesKt.makeNullable(irSimpleType), JvmLoweredDeclarationOrigin.TO_ARRAY.INSTANCE);
                                                                    }
                                                                }, 14, null);
                                                                this.collectionToArrayClass = irClassSymbolCreateClass$default11;
                                                                boolean z15 = false;
                                                                IrSimpleFunctionSymbol irSimpleFunctionSymbol23 = null;
                                                                for (IrSimpleFunctionSymbol irSimpleFunctionSymbol24 : IrUtilsKt.getFunctions(irClassSymbolCreateClass$default11)) {
                                                                    IrSimpleFunctionSymbol irSimpleFunctionSymbol25 = irSimpleFunctionSymbol24;
                                                                    if (Intrinsics.areEqual(irSimpleFunctionSymbol25.getOwner().getName().asString(), "toArray") && irSimpleFunctionSymbol25.getOwner().getValueParameters().size() == 1) {
                                                                        if (z15) {
                                                                            throw new IllegalArgumentException("Sequence contains more than one matching element.");
                                                                        }
                                                                        irSimpleFunctionSymbol23 = irSimpleFunctionSymbol24;
                                                                        z15 = true;
                                                                    }
                                                                }
                                                                if (z15) {
                                                                    this.nonGenericToArray = irSimpleFunctionSymbol23;
                                                                    boolean z16 = false;
                                                                    IrSimpleFunctionSymbol irSimpleFunctionSymbol26 = null;
                                                                    for (IrSimpleFunctionSymbol irSimpleFunctionSymbol27 : IrUtilsKt.getFunctions(this.collectionToArrayClass)) {
                                                                        IrSimpleFunctionSymbol irSimpleFunctionSymbol28 = irSimpleFunctionSymbol27;
                                                                        if (Intrinsics.areEqual(irSimpleFunctionSymbol28.getOwner().getName().asString(), "toArray") && irSimpleFunctionSymbol28.getOwner().getValueParameters().size() == 2) {
                                                                            if (z16) {
                                                                                throw new IllegalArgumentException("Sequence contains more than one matching element.");
                                                                            }
                                                                            irSimpleFunctionSymbol26 = irSimpleFunctionSymbol27;
                                                                            z16 = true;
                                                                        }
                                                                    }
                                                                    if (z16) {
                                                                        this.genericToArray = irSimpleFunctionSymbol26;
                                                                        IrClassSymbol irClassSymbolCreateClass$default12 = createClass$default(this, new FqName("kotlin.jvm.JvmName"), ClassKind.ANNOTATION_CLASS, null, false, new Function1<IrClass, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmSymbols$jvmName$1
                                                                            {
                                                                                super(1);
                                                                            }

                                                                            @Override // kotlin.jvm.functions.Function1
                                                                            public /* bridge */ /* synthetic */ Unit invoke(IrClass irClass) {
                                                                                invoke2(irClass);
                                                                                return Unit.INSTANCE;
                                                                            }

                                                                            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                                                            public final void invoke2(IrClass klass) {
                                                                                Intrinsics.checkNotNullParameter(klass, "klass");
                                                                                IrFactory factory2 = klass.getFactory();
                                                                                IrFunctionBuilder irFunctionBuilder9 = new IrFunctionBuilder();
                                                                                irFunctionBuilder9.setReturnType(IrUtilsKt.getDefaultType(klass));
                                                                                IrConstructor irConstructorBuildConstructor2 = DeclarationBuildersKt.buildConstructor(factory2, irFunctionBuilder9);
                                                                                klass.getDeclarations().add(irConstructorBuildConstructor2);
                                                                                irConstructorBuildConstructor2.setParent(klass);
                                                                                DeclarationBuildersKt.addValueParameter$default(irConstructorBuildConstructor2, "name", this.this$0.getIrBuiltIns().getStringType(), (IrDeclarationOrigin) null, 4, (Object) null);
                                                                            }
                                                                        }, 12, null);
                                                                        this.jvmName = irClassSymbolCreateClass$default12;
                                                                        IrFactory irFactory8 = this.irFactory;
                                                                        IrPropertyBuilder irPropertyBuilder = new IrPropertyBuilder();
                                                                        Name nameIdentifier3 = Name.identifier(SuffixConstants.EXTENSION_java);
                                                                        Intrinsics.checkNotNullExpressionValue(nameIdentifier3, "identifier(...)");
                                                                        irPropertyBuilder.setName(nameIdentifier3);
                                                                        Unit unit16 = Unit.INSTANCE;
                                                                        IrProperty irPropertyBuildProperty = DeclarationBuildersKt.buildProperty(irFactory8, irPropertyBuilder);
                                                                        irPropertyBuildProperty.setParent(createClass$default(this, new FqName("kotlin.jvm.JvmClassMappingKt"), null, null, false, null, 30, null).getOwner());
                                                                        IrFunctionBuilder irFunctionBuilder9 = new IrFunctionBuilder();
                                                                        Name nameSpecial8 = Name.special("<get-" + irPropertyBuildProperty.getName() + '>');
                                                                        Intrinsics.checkNotNullExpressionValue(nameSpecial8, "special(...)");
                                                                        irFunctionBuilder9.setName(nameSpecial8);
                                                                        Unit unit17 = Unit.INSTANCE;
                                                                        IrSimpleFunction irSimpleFunctionBuildFunction8 = DeclarationBuildersKt.buildFunction(irPropertyBuildProperty.getFactory(), irFunctionBuilder9);
                                                                        irPropertyBuildProperty.setGetter(irSimpleFunctionBuildFunction8);
                                                                        irSimpleFunctionBuildFunction8.setCorrespondingPropertySymbol(irPropertyBuildProperty.getSymbol());
                                                                        irSimpleFunctionBuildFunction8.setParent(irPropertyBuildProperty.getParent());
                                                                        IrConstructorCallImpl irConstructorCallImplFromSymbolOwner$default = IrConstructorCallImpl.Companion.fromSymbolOwner$default(IrConstructorCallImpl.INSTANCE, IrTypesKt.typeWith(irClassSymbolCreateClass$default12, new IrType[0]), (IrConstructorSymbol) SequencesKt.single(IrUtilsKt.getConstructors(irClassSymbolCreateClass$default12)), null, 4, null);
                                                                        irConstructorCallImplFromSymbolOwner$default.putValueArgument(0, IrConstImpl.INSTANCE.string(-1, -1, getIrBuiltIns().getStringType(), "getJavaClass"));
                                                                        irSimpleFunctionBuildFunction8.setAnnotations(CollectionsKt.listOf(irConstructorCallImplFromSymbolOwner$default));
                                                                        org.jetbrains.kotlin.backend.common.ir.IrUtilsKt.addExtensionReceiver$default(irSimpleFunctionBuildFunction8, IrTypesKt.getStarProjectedType(getIrBuiltIns().getKClassClass()), null, 2, null);
                                                                        irSimpleFunctionBuildFunction8.setReturnType(IrTypesKt.getStarProjectedType(this.javaLangClass));
                                                                        Unit unit18 = Unit.INSTANCE;
                                                                        IrPropertySymbol symbol = irPropertyBuildProperty.getSymbol();
                                                                        this.kClassJava = symbol;
                                                                        IrSimpleFunction getter = symbol.getOwner().getGetter();
                                                                        Intrinsics.checkNotNull(getter);
                                                                        this.kClassJavaPropertyGetter = getter;
                                                                        this.spreadBuilder = createClass$default(this, new FqName("kotlin.jvm.internal.SpreadBuilder"), null, null, false, new Function1<IrClass, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmSymbols$spreadBuilder$1
                                                                            {
                                                                                super(1);
                                                                            }

                                                                            @Override // kotlin.jvm.functions.Function1
                                                                            public /* bridge */ /* synthetic */ Unit invoke(IrClass irClass) {
                                                                                invoke2(irClass);
                                                                                return Unit.INSTANCE;
                                                                            }

                                                                            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                                                            public final void invoke2(IrClass klass) {
                                                                                Intrinsics.checkNotNullParameter(klass, "klass");
                                                                                IrFactory factory2 = klass.getFactory();
                                                                                IrFunctionBuilder irFunctionBuilder10 = new IrFunctionBuilder();
                                                                                irFunctionBuilder10.setReturnType(IrUtilsKt.getDefaultType(klass));
                                                                                IrConstructor irConstructorBuildConstructor2 = DeclarationBuildersKt.buildConstructor(factory2, irFunctionBuilder10);
                                                                                klass.getDeclarations().add(irConstructorBuildConstructor2);
                                                                                irConstructorBuildConstructor2.setParent(klass);
                                                                                DeclarationBuildersKt.addValueParameter$default(irConstructorBuildConstructor2, "size", this.this$0.getIrBuiltIns().getIntType(), (IrDeclarationOrigin) null, 4, (Object) null);
                                                                                DeclarationBuildersKt.addValueParameter$default(DeclarationBuildersKt.addFunction$default(klass, "addSpread", this.this$0.getIrBuiltIns().getUnitType(), null, null, false, false, false, false, null, 0, 0, 2044, null), "container", this.this$0.getIrBuiltIns().getAnyNType(), (IrDeclarationOrigin) null, 4, (Object) null);
                                                                                DeclarationBuildersKt.addValueParameter$default(DeclarationBuildersKt.addFunction$default(klass, ProductAction.ACTION_ADD, this.this$0.getIrBuiltIns().getUnitType(), null, null, false, false, false, false, null, 0, 0, 2044, null), CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, this.this$0.getIrBuiltIns().getAnyNType(), (IrDeclarationOrigin) null, 4, (Object) null);
                                                                                DeclarationBuildersKt.addFunction$default(klass, "size", this.this$0.getIrBuiltIns().getIntType(), null, null, false, false, false, false, null, 0, 0, 2044, null);
                                                                                IrSimpleFunction irSimpleFunctionAddFunction$default = DeclarationBuildersKt.addFunction$default(klass, "toArray", IrTypesKt.typeWith(this.this$0.getIrBuiltIns().getArrayClass(), this.this$0.getIrBuiltIns().getAnyNType()), null, null, false, false, false, false, null, 0, 0, 2044, null);
                                                                                JvmSymbols jvmSymbols = this.this$0;
                                                                                DeclarationBuildersKt.addValueParameter$default(irSimpleFunctionAddFunction$default, "a", IrTypesKt.typeWith(jvmSymbols.getIrBuiltIns().getArrayClass(), jvmSymbols.getIrBuiltIns().getAnyNType()), (IrDeclarationOrigin) null, 4, (Object) null);
                                                                            }
                                                                        }, 14, null);
                                                                        List<IrType> primitiveIrTypes = getIrBuiltIns().getPrimitiveIrTypes();
                                                                        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(primitiveIrTypes, 10)), 16));
                                                                        for (Object obj3 : primitiveIrTypes) {
                                                                            final IrType irType = (IrType) obj3;
                                                                            IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(irType);
                                                                            Intrinsics.checkNotNull(classOrNull);
                                                                            linkedHashMap.put(obj3, createClass$default(this, new FqName("kotlin.jvm.internal." + classOrNull.getOwner().getName() + "SpreadBuilder"), null, null, false, new Function1<IrClass, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmSymbols$primitiveSpreadBuilders$1$1
                                                                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                                                {
                                                                                    super(1);
                                                                                }

                                                                                @Override // kotlin.jvm.functions.Function1
                                                                                public /* bridge */ /* synthetic */ Unit invoke(IrClass irClass) {
                                                                                    invoke2(irClass);
                                                                                    return Unit.INSTANCE;
                                                                                }

                                                                                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                                                                public final void invoke2(IrClass klass) {
                                                                                    Intrinsics.checkNotNullParameter(klass, "klass");
                                                                                    IrFactory factory2 = klass.getFactory();
                                                                                    IrFunctionBuilder irFunctionBuilder10 = new IrFunctionBuilder();
                                                                                    irFunctionBuilder10.setReturnType(IrUtilsKt.getDefaultType(klass));
                                                                                    IrConstructor irConstructorBuildConstructor2 = DeclarationBuildersKt.buildConstructor(factory2, irFunctionBuilder10);
                                                                                    klass.getDeclarations().add(irConstructorBuildConstructor2);
                                                                                    irConstructorBuildConstructor2.setParent(klass);
                                                                                    DeclarationBuildersKt.addValueParameter$default(irConstructorBuildConstructor2, "size", this.this$0.getIrBuiltIns().getIntType(), (IrDeclarationOrigin) null, 4, (Object) null);
                                                                                    DeclarationBuildersKt.addValueParameter$default(DeclarationBuildersKt.addFunction$default(klass, "addSpread", this.this$0.getIrBuiltIns().getUnitType(), null, null, false, false, false, false, null, 0, 0, 2044, null), "container", this.this$0.getIrBuiltIns().getAnyNType(), (IrDeclarationOrigin) null, 4, (Object) null);
                                                                                    DeclarationBuildersKt.addValueParameter$default(DeclarationBuildersKt.addFunction$default(klass, ProductAction.ACTION_ADD, this.this$0.getIrBuiltIns().getUnitType(), null, null, false, false, false, false, null, 0, 0, 2044, null), CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, irType, (IrDeclarationOrigin) null, 4, (Object) null);
                                                                                    DeclarationBuildersKt.addFunction$default(klass, "toArray", IrTypesKt.getDefaultType((IrClassifierSymbol) MapsKt.getValue(this.this$0.getIrBuiltIns().getPrimitiveArrayForType(), irType)), null, null, false, false, false, false, null, 0, 0, 2044, null);
                                                                                }
                                                                            }, 14, null));
                                                                        }
                                                                        this.primitiveSpreadBuilders = linkedHashMap;
                                                                        this.arraysCopyOfFunctions = new HashMap<>();
                                                                        this.arraysClass = createClass$default(this, new FqName(CommonClassNames.JAVA_UTIL_ARRAYS), null, null, false, new Function1<IrClass, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmSymbols$arraysClass$1
                                                                            {
                                                                                super(1);
                                                                            }

                                                                            @Override // kotlin.jvm.functions.Function1
                                                                            public /* bridge */ /* synthetic */ Unit invoke(IrClass irClass) {
                                                                                invoke2(irClass);
                                                                                return Unit.INSTANCE;
                                                                            }

                                                                            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                                                            public final void invoke2(IrClass irClass) {
                                                                                Intrinsics.checkNotNullParameter(irClass, "irClass");
                                                                                for (IrSimpleType irSimpleType : CollectionsKt.listOf((Object[]) new IrSimpleType[]{this.this$0.getBooleanArrayType(), this.this$0.getByteArrayType(), this.this$0.getCharArrayType(), this.this$0.getShortArrayType(), this.this$0.getIntArrayType(), this.this$0.getLongArrayType(), this.this$0.getFloatArrayType(), this.this$0.getDoubleArrayType(), this.this$0.getArrayOfAnyNType()})) {
                                                                                    this.this$0.addArraysCopyOfFunction(irClass, irSimpleType);
                                                                                    this.this$0.addArraysEqualsFunction(irClass, irSimpleType);
                                                                                }
                                                                            }
                                                                        }, 14, null);
                                                                        IrClassSymbol irClassSymbolCreateJavaPrimitiveClassWithUnsignedUtils = createJavaPrimitiveClassWithUnsignedUtils(new FqName(CommonClassNames.JAVA_LANG_INTEGER), getIrBuiltIns().getIntType());
                                                                        this.javaLangInteger = irClassSymbolCreateJavaPrimitiveClassWithUnsignedUtils;
                                                                        this.compareUnsignedInt = JvmSymbolsKt.functionByName(irClassSymbolCreateJavaPrimitiveClassWithUnsignedUtils, "compareUnsigned");
                                                                        this.divideUnsignedInt = JvmSymbolsKt.functionByName(irClassSymbolCreateJavaPrimitiveClassWithUnsignedUtils, "divideUnsigned");
                                                                        this.remainderUnsignedInt = JvmSymbolsKt.functionByName(irClassSymbolCreateJavaPrimitiveClassWithUnsignedUtils, "remainderUnsigned");
                                                                        this.toUnsignedStringInt = JvmSymbolsKt.functionByName(irClassSymbolCreateJavaPrimitiveClassWithUnsignedUtils, "toUnsignedString");
                                                                        IrClassSymbol irClassSymbolCreateJavaPrimitiveClassWithUnsignedUtils2 = createJavaPrimitiveClassWithUnsignedUtils(new FqName(CommonClassNames.JAVA_LANG_LONG), getIrBuiltIns().getLongType());
                                                                        this.javaLangLong = irClassSymbolCreateJavaPrimitiveClassWithUnsignedUtils2;
                                                                        this.compareUnsignedLong = JvmSymbolsKt.functionByName(irClassSymbolCreateJavaPrimitiveClassWithUnsignedUtils2, "compareUnsigned");
                                                                        this.divideUnsignedLong = JvmSymbolsKt.functionByName(irClassSymbolCreateJavaPrimitiveClassWithUnsignedUtils2, "divideUnsigned");
                                                                        this.remainderUnsignedLong = JvmSymbolsKt.functionByName(irClassSymbolCreateJavaPrimitiveClassWithUnsignedUtils2, "remainderUnsigned");
                                                                        this.toUnsignedStringLong = JvmSymbolsKt.functionByName(irClassSymbolCreateJavaPrimitiveClassWithUnsignedUtils2, "toUnsignedString");
                                                                        this.intPostfixIncrDecr = createIncrDecrFun("<int-postfix-incr-decr>");
                                                                        this.intPrefixIncrDecr = createIncrDecrFun("<int-prefix-incr-decr>");
                                                                        IrFactory irFactory9 = this.irFactory;
                                                                        IrFunctionBuilder irFunctionBuilder10 = new IrFunctionBuilder();
                                                                        Name nameSpecial9 = Name.special("<signature-string>");
                                                                        Intrinsics.checkNotNullExpressionValue(nameSpecial9, "special(...)");
                                                                        irFunctionBuilder10.setName(nameSpecial9);
                                                                        irFunctionBuilder10.setOrigin(IrDeclarationOrigin.IR_BUILTINS_STUB.INSTANCE);
                                                                        Unit unit19 = Unit.INSTANCE;
                                                                        IrSimpleFunction irSimpleFunctionBuildFunction9 = DeclarationBuildersKt.buildFunction(irFactory9, irFunctionBuilder10);
                                                                        irSimpleFunctionBuildFunction9.setParent(this.kotlinJvmInternalPackage);
                                                                        DeclarationBuildersKt.addValueParameter$default(irSimpleFunctionBuildFunction9, RegisterSpec.PREFIX, getIrBuiltIns().getAnyNType(), (IrDeclarationOrigin) null, 4, (Object) null);
                                                                        irSimpleFunctionBuildFunction9.setReturnType(getIrBuiltIns().getStringType());
                                                                        Unit unit20 = Unit.INSTANCE;
                                                                        this.signatureStringIntrinsic = irSimpleFunctionBuildFunction9.getSymbol();
                                                                        IrClassSymbol irClassSymbolCreateClass$default13 = createClass$default(this, new FqName(CommonClassNames.JAVA_LANG_STRING), null, null, false, new Function1<IrClass, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmSymbols$javaLangString$1
                                                                            {
                                                                                super(1);
                                                                            }

                                                                            @Override // kotlin.jvm.functions.Function1
                                                                            public /* bridge */ /* synthetic */ Unit invoke(IrClass irClass) {
                                                                                invoke2(irClass);
                                                                                return Unit.INSTANCE;
                                                                            }

                                                                            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                                                            public final void invoke2(IrClass klass) {
                                                                                Intrinsics.checkNotNullParameter(klass, "klass");
                                                                                IrBuiltIns irBuiltIns = this.this$0.getIrBuiltIns();
                                                                                Iterator it = CollectionsKt.listOf((Object[]) new IrType[]{irBuiltIns.getAnyNType(), irBuiltIns.getBooleanType(), irBuiltIns.getCharType(), irBuiltIns.getIntType(), irBuiltIns.getLongType(), irBuiltIns.getFloatType(), irBuiltIns.getDoubleType()}).iterator();
                                                                                while (it.hasNext()) {
                                                                                    DeclarationBuildersKt.addValueParameter$default(DeclarationBuildersKt.addFunction$default(klass, "valueOf", this.this$0.getIrBuiltIns().getStringType(), null, null, true, false, false, false, null, 0, 0, XKeySym.XK_Greek_mu, null), "value", (IrType) it.next(), (IrDeclarationOrigin) null, 4, (Object) null);
                                                                                }
                                                                            }
                                                                        }, 14, null);
                                                                        this.javaLangString = irClassSymbolCreateClass$default13;
                                                                        boolean z17 = false;
                                                                        IrSimpleFunctionSymbol irSimpleFunctionSymbol29 = null;
                                                                        for (IrSimpleFunctionSymbol irSimpleFunctionSymbol30 : IrUtilsKt.getFunctions(irClassSymbolCreateClass$default13)) {
                                                                            IrSimpleFunctionSymbol irSimpleFunctionSymbol31 = irSimpleFunctionSymbol30;
                                                                            if (Intrinsics.areEqual(irSimpleFunctionSymbol31.getOwner().getName().asString(), "valueOf") && (irValueParameter = (IrValueParameter) CollectionsKt.singleOrNull((List) irSimpleFunctionSymbol31.getOwner().getValueParameters())) != null && (type = irValueParameter.getType()) != null && IrTypePredicatesKt.isNullableAny(type)) {
                                                                                if (z17) {
                                                                                    throw new IllegalArgumentException("Sequence contains more than one matching element.");
                                                                                }
                                                                                irSimpleFunctionSymbol29 = irSimpleFunctionSymbol30;
                                                                                z17 = true;
                                                                            }
                                                                        }
                                                                        if (z17) {
                                                                            this.defaultValueOfFunction = irSimpleFunctionSymbol29;
                                                                            List<IrType> primitiveIrTypes2 = this.context.getIrBuiltIns().getPrimitiveIrTypes();
                                                                            LinkedHashMap linkedHashMap2 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(primitiveIrTypes2, 10)), 16));
                                                                            for (Object obj4 : primitiveIrTypes2) {
                                                                                LinkedHashMap linkedHashMap3 = linkedHashMap2;
                                                                                IrType irType2 = (IrType) obj4;
                                                                                Iterator<IrSimpleFunctionSymbol> it = IrUtilsKt.getFunctions(this.javaLangString).iterator();
                                                                                boolean z18 = false;
                                                                                IrSimpleFunctionSymbol irSimpleFunctionSymbol32 = null;
                                                                                while (true) {
                                                                                    if (!it.hasNext()) {
                                                                                        if (z18) {
                                                                                            break;
                                                                                        }
                                                                                    } else {
                                                                                        IrSimpleFunctionSymbol next = it.next();
                                                                                        IrSimpleFunctionSymbol irSimpleFunctionSymbol33 = next;
                                                                                        if (Intrinsics.areEqual(irSimpleFunctionSymbol33.getOwner().getName().asString(), "valueOf")) {
                                                                                            IrValueParameter irValueParameter2 = (IrValueParameter) CollectionsKt.singleOrNull((List) irSimpleFunctionSymbol33.getOwner().getValueParameters());
                                                                                            if (!Intrinsics.areEqual(irValueParameter2 != null ? irValueParameter2.getType() : null, irType2)) {
                                                                                                continue;
                                                                                            } else if (!z18) {
                                                                                                irSimpleFunctionSymbol32 = next;
                                                                                                z18 = true;
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                    irSimpleFunctionSymbol32 = null;
                                                                                    break;
                                                                                }
                                                                                linkedHashMap3.put(obj4, irSimpleFunctionSymbol32);
                                                                            }
                                                                            this.valueOfFunctions = linkedHashMap2;
                                                                            IrClassSymbol irClassSymbolCreateClass$default14 = createClass$default(this, new FqName(CommonClassNames.JAVA_LANG_ENUM), null, null, false, new Function1<IrClass, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmSymbols$javaLangEnum$1
                                                                                {
                                                                                    super(1);
                                                                                }

                                                                                @Override // kotlin.jvm.functions.Function1
                                                                                public /* bridge */ /* synthetic */ Unit invoke(IrClass irClass) {
                                                                                    invoke2(irClass);
                                                                                    return Unit.INSTANCE;
                                                                                }

                                                                                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                                                                public final void invoke2(IrClass klass) {
                                                                                    Intrinsics.checkNotNullParameter(klass, "klass");
                                                                                    IrSimpleFunction irSimpleFunctionAddFunction$default = DeclarationBuildersKt.addFunction$default(klass, "valueOf", IrUtilsKt.getDefaultType(klass), null, null, true, false, false, false, null, 0, 0, XKeySym.XK_Greek_mu, null);
                                                                                    JvmSymbols jvmSymbols = this.this$0;
                                                                                    IrSimpleFunction irSimpleFunction12 = irSimpleFunctionAddFunction$default;
                                                                                    DeclarationBuildersKt.addValueParameter$default(irSimpleFunction12, "enumType", IrTypesKt.getStarProjectedType(jvmSymbols.getJavaLangClass()), (IrDeclarationOrigin) null, 4, (Object) null);
                                                                                    DeclarationBuildersKt.addValueParameter$default(irSimpleFunction12, "name", jvmSymbols.getIrBuiltIns().getStringType(), (IrDeclarationOrigin) null, 4, (Object) null);
                                                                                }
                                                                            }, 14, null);
                                                                            this.javaLangEnum = irClassSymbolCreateClass$default14;
                                                                            this.enumValueOfFunction = JvmSymbolsKt.functionByName(irClassSymbolCreateClass$default14, "valueOf");
                                                                            IrClassSymbol irClassSymbolCreateClass$default15 = createClass$default(this, new FqName(CommonClassNames.JAVA_LANG_OBJECT), null, null, false, new Function1<IrClass, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmSymbols$javaLangObject$1
                                                                                {
                                                                                    super(1);
                                                                                }

                                                                                @Override // kotlin.jvm.functions.Function1
                                                                                public /* bridge */ /* synthetic */ Unit invoke(IrClass irClass) {
                                                                                    invoke2(irClass);
                                                                                    return Unit.INSTANCE;
                                                                                }

                                                                                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                                                                public final void invoke2(IrClass klass) {
                                                                                    Intrinsics.checkNotNullParameter(klass, "klass");
                                                                                    DeclarationBuildersKt.addFunction$default(klass, "clone", this.this$0.getIrBuiltIns().getAnyType(), null, null, false, false, false, false, null, 0, 0, 2044, null);
                                                                                }
                                                                            }, 14, null);
                                                                            this.javaLangObject = irClassSymbolCreateClass$default15;
                                                                            this.objectCloneFunction = JvmSymbolsKt.functionByName(irClassSymbolCreateClass$default15, "clone");
                                                                            IrClassSymbol irClassSymbolCreateClass$default16 = createClass$default(this, new FqName("kotlin.coroutines.jvm.internal.RunSuspendKt"), null, null, false, new Function1<IrClass, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmSymbols$kotlinCoroutinesJvmInternalRunSuspendKt$1
                                                                                {
                                                                                    super(1);
                                                                                }

                                                                                @Override // kotlin.jvm.functions.Function1
                                                                                public /* bridge */ /* synthetic */ Unit invoke(IrClass irClass) {
                                                                                    invoke2(irClass);
                                                                                    return Unit.INSTANCE;
                                                                                }

                                                                                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                                                                                public final void invoke2(IrClass klass) {
                                                                                    Intrinsics.checkNotNullParameter(klass, "klass");
                                                                                    IrSimpleFunction irSimpleFunctionAddFunction$default = DeclarationBuildersKt.addFunction$default(klass, "runSuspend", this.this$0.getIrBuiltIns().getUnitType(), null, null, true, false, false, false, null, 0, 0, XKeySym.XK_Greek_mu, null);
                                                                                    JvmSymbols jvmSymbols = this.this$0;
                                                                                    DeclarationBuildersKt.addValueParameter$default(irSimpleFunctionAddFunction$default, "block", IrTypesKt.typeWith(jvmSymbols.getJvmSuspendFunctionClass(0), jvmSymbols.getIrBuiltIns().getUnitType()), (IrDeclarationOrigin) null, 4, (Object) null);
                                                                                }
                                                                            }, 14, null);
                                                                            this.kotlinCoroutinesJvmInternalRunSuspendKt = irClassSymbolCreateClass$default16;
                                                                            this.runSuspendFunction = JvmSymbolsKt.functionByName(irClassSymbolCreateClass$default16, "runSuspend");
                                                                            IrClassSymbol irClassSymbolCreateClass$default17 = createClass$default(this, new FqName(KtToJvmAnnotationsConverterKt.KOTLIN_JVM_INTERNAL_REPEATABLE_CONTAINER), ClassKind.ANNOTATION_CLASS, null, false, null, 28, null);
                                                                            IrClass owner2 = irClassSymbolCreateClass$default17.getOwner();
                                                                            IrFactory factory2 = owner2.getFactory();
                                                                            IrFunctionBuilder irFunctionBuilder11 = new IrFunctionBuilder();
                                                                            irFunctionBuilder11.setPrimary(true);
                                                                            irFunctionBuilder11.setReturnType(IrUtilsKt.getDefaultType(owner2));
                                                                            IrConstructor irConstructorBuildConstructor2 = DeclarationBuildersKt.buildConstructor(factory2, irFunctionBuilder11);
                                                                            owner2.getDeclarations().add(irConstructorBuildConstructor2);
                                                                            irConstructorBuildConstructor2.setParent(owner2);
                                                                            Unit unit21 = Unit.INSTANCE;
                                                                            this.repeatableContainer = irClassSymbolCreateClass$default17;
                                                                            this.javaAnnotations = new JavaAnnotations();
                                                                            return;
                                                                        }
                                                                        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
                                                                    }
                                                                    throw new NoSuchElementException("Sequence contains no element matching the predicate.");
                                                                }
                                                                throw new NoSuchElementException("Sequence contains no element matching the predicate.");
                                                            }
                                                            throw new NoSuchElementException("Sequence contains no element matching the predicate.");
                                                        }
                                                        throw new NoSuchElementException("Collection contains no element matching the predicate.");
                                                    }
                                                    throw new NoSuchElementException("Sequence contains no element matching the predicate.");
                                                }
                                                throw new NoSuchElementException("Sequence contains no element matching the predicate.");
                                            }
                                            throw new NoSuchElementException("Sequence contains no element matching the predicate.");
                                        }
                                        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
                                    }
                                    throw new NoSuchElementException("Sequence contains no element matching the predicate.");
                                }
                                throw new NoSuchElementException("Sequence contains no element matching the predicate.");
                            }
                            throw new NoSuchElementException("Sequence contains no element matching the predicate.");
                        }
                        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
                    }
                    throw new NoSuchElementException("Sequence contains no element matching the predicate.");
                }
                throw new NoSuchElementException("Sequence contains no element matching the predicate.");
            }
            throw new NoSuchElementException("Sequence contains no element matching the predicate.");
        }
        throw new NoSuchElementException("Sequence contains no element matching the predicate.");
    }

    public final IrPackageFragment getKotlinJvmInternalInvokeDynamicPackage() {
        return this.kotlinJvmInternalInvokeDynamicPackage;
    }

    private final IrPackageFragment createPackage(FqName fqName) {
        return IrExternalPackageFragmentImpl.INSTANCE.createEmptyExternalPackageFragment(this.context.getState().getModule(), fqName);
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ IrClassSymbol createClass$default(JvmSymbols jvmSymbols, FqName fqName, ClassKind classKind, Modality modality, boolean z, Function1 function1, int i, Object obj) {
        if ((i & 2) != 0) {
            classKind = ClassKind.CLASS;
        }
        ClassKind classKind2 = classKind;
        if ((i & 4) != 0) {
            modality = Modality.FINAL;
        }
        Modality modality2 = modality;
        if ((i & 8) != 0) {
            z = false;
        }
        boolean z2 = z;
        if ((i & 16) != 0) {
            function1 = new Function1<IrClass, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmSymbols.createClass.1
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(IrClass it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(IrClass irClass) {
                    invoke2(irClass);
                    return Unit.INSTANCE;
                }
            };
        }
        return jvmSymbols.createClass(fqName, classKind2, modality2, z2, function1);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private final IrClassSymbol createClass(FqName fqName, ClassKind classKind, Modality classModality, boolean classIsValue, Function1<? super IrClass, Unit> block) {
        IrPackageFragment irPackageFragment;
        IrFactory irFactory = this.irFactory;
        IrClassBuilder irClassBuilder = new IrClassBuilder();
        Name nameShortName = fqName.shortName();
        Intrinsics.checkNotNullExpressionValue(nameShortName, "shortName(...)");
        irClassBuilder.setName(nameShortName);
        irClassBuilder.setKind(classKind);
        irClassBuilder.setModality(classModality);
        irClassBuilder.setValue(classIsValue);
        IrClass irClassBuildClass = DeclarationBuildersKt.buildClass(irFactory, irClassBuilder);
        String strAsString = fqName.parent().asString();
        switch (strAsString.hashCode()) {
            case -2133248460:
                if (strAsString.equals("kotlin.jvm")) {
                    irPackageFragment = this.kotlinJvmPackage;
                    irClassBuildClass.setParent(irPackageFragment);
                    IrUtilsKt.createImplicitParameterDeclarationWithWrappedDescriptor(irClassBuildClass);
                    block.invoke(irClassBuildClass);
                    return irClassBuildClass.getSymbol();
                }
                break;
            case -1613221622:
                if (strAsString.equals("kotlin.internal")) {
                    irPackageFragment = this.kotlinInternalPackage;
                    irClassBuildClass.setParent(irPackageFragment);
                    IrUtilsKt.createImplicitParameterDeclarationWithWrappedDescriptor(irClassBuildClass);
                    block.invoke(irClassBuildClass);
                    return irClassBuildClass.getSymbol();
                }
                break;
            case -1357214619:
                if (strAsString.equals("kotlin.enums")) {
                    irPackageFragment = this.kotlinEnumsPackage;
                    irClassBuildClass.setParent(irPackageFragment);
                    IrUtilsKt.createImplicitParameterDeclarationWithWrappedDescriptor(irClassBuildClass);
                    block.invoke(irClassBuildClass);
                    return irClassBuildClass.getSymbol();
                }
                break;
            case -1125574399:
                if (strAsString.equals(IncrementalJvmCacheKt.KOTLIN_CACHE_DIRECTORY_NAME)) {
                    irPackageFragment = this.kotlinPackage;
                    irClassBuildClass.setParent(irPackageFragment);
                    IrUtilsKt.createImplicitParameterDeclarationWithWrappedDescriptor(irClassBuildClass);
                    block.invoke(irClassBuildClass);
                    return irClassBuildClass.getSymbol();
                }
                break;
            case -888658374:
                if (strAsString.equals(CommonClassNames.DEFAULT_PACKAGE)) {
                    irPackageFragment = this.javaLangPackage;
                    irClassBuildClass.setParent(irPackageFragment);
                    IrUtilsKt.createImplicitParameterDeclarationWithWrappedDescriptor(irClassBuildClass);
                    block.invoke(irClassBuildClass);
                    return irClassBuildClass.getSymbol();
                }
                break;
            case -888372146:
                if (strAsString.equals("java.util")) {
                    irPackageFragment = this.javaUtilPackage;
                    irClassBuildClass.setParent(irPackageFragment);
                    IrUtilsKt.createImplicitParameterDeclarationWithWrappedDescriptor(irClassBuildClass);
                    block.invoke(irClassBuildClass);
                    return irClassBuildClass.getSymbol();
                }
                break;
            case -638354960:
                if (strAsString.equals("kotlin.coroutines.jvm.internal")) {
                    irPackageFragment = this.kotlinCoroutinesJvmInternalPackage;
                    irClassBuildClass.setParent(irPackageFragment);
                    IrUtilsKt.createImplicitParameterDeclarationWithWrappedDescriptor(irClassBuildClass);
                    block.invoke(irClassBuildClass);
                    return irClassBuildClass.getSymbol();
                }
                break;
            case -232100144:
                if (strAsString.equals("kotlin.reflect")) {
                    irPackageFragment = this.kotlinReflectPackage;
                    irClassBuildClass.setParent(irPackageFragment);
                    IrUtilsKt.createImplicitParameterDeclarationWithWrappedDescriptor(irClassBuildClass);
                    block.invoke(irClassBuildClass);
                    return irClassBuildClass.getSymbol();
                }
                break;
            case 734962600:
                if (strAsString.equals("kotlin.coroutines")) {
                    irPackageFragment = this.kotlinCoroutinesPackage;
                    irClassBuildClass.setParent(irPackageFragment);
                    IrUtilsKt.createImplicitParameterDeclarationWithWrappedDescriptor(irClassBuildClass);
                    block.invoke(irClassBuildClass);
                    return irClassBuildClass.getSymbol();
                }
                break;
            case 1180319031:
                if (strAsString.equals("kotlin.jvm.internal")) {
                    irPackageFragment = this.kotlinJvmInternalPackage;
                    irClassBuildClass.setParent(irPackageFragment);
                    IrUtilsKt.createImplicitParameterDeclarationWithWrappedDescriptor(irClassBuildClass);
                    block.invoke(irClassBuildClass);
                    return irClassBuildClass.getSymbol();
                }
                break;
            case 1586716769:
                if (strAsString.equals("kotlin.jvm.functions")) {
                    irPackageFragment = this.kotlinJvmFunctionsPackage;
                    irClassBuildClass.setParent(irPackageFragment);
                    IrUtilsKt.createImplicitParameterDeclarationWithWrappedDescriptor(irClassBuildClass);
                    block.invoke(irClassBuildClass);
                    return irClassBuildClass.getSymbol();
                }
                break;
            case 1819684524:
                if (strAsString.equals("java.lang.invoke")) {
                    irPackageFragment = this.javaLangInvokePackage;
                    irClassBuildClass.setParent(irPackageFragment);
                    IrUtilsKt.createImplicitParameterDeclarationWithWrappedDescriptor(irClassBuildClass);
                    block.invoke(irClassBuildClass);
                    return irClassBuildClass.getSymbol();
                }
                break;
        }
        throw new IllegalStateException(("Other packages are not supported yet: " + fqName).toString());
    }

    public final IrSimpleFunctionSymbol getSingleArgumentInlineFunction() {
        return this.singleArgumentInlineFunction;
    }

    public final IrSimpleFunctionSymbol getCheckExpressionValueIsNotNull() {
        return this.checkExpressionValueIsNotNull;
    }

    public final IrSimpleFunctionSymbol getCheckNotNullExpressionValue() {
        return this.checkNotNullExpressionValue;
    }

    public final IrSimpleFunctionSymbol getCheckNotNull() {
        return this.checkNotNull;
    }

    public final IrSimpleFunctionSymbol getCheckNotNullWithMessage() {
        return this.checkNotNullWithMessage;
    }

    public final IrSimpleFunctionSymbol getThrowNpe() {
        return this.throwNpe;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.Symbols
    public IrSimpleFunctionSymbol getThrowNullPointerException() {
        return this.throwNullPointerException;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.Symbols
    public IrSimpleFunctionSymbol getThrowTypeCastException() {
        return this.throwTypeCastException;
    }

    public final IrSimpleFunctionSymbol getThrowIllegalAccessException() {
        return this.throwIllegalAccessException;
    }

    public final IrSimpleFunctionSymbol getThrowUnsupportedOperationException() {
        return this.throwUnsupportedOperationException;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.Symbols
    public IrSimpleFunctionSymbol getThrowUninitializedPropertyAccessException() {
        return this.throwUninitializedPropertyAccessException;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.Symbols
    public IrSimpleFunctionSymbol getThrowKotlinNothingValueException() {
        return this.throwKotlinNothingValueException;
    }

    public final IrClassSymbol getIntrinsicsKotlinClass() {
        return this.intrinsicsKotlinClass;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.Symbols
    public IrClassSymbol getStringBuilder() {
        return this.stringBuilder;
    }

    public final IrClassSymbol getEnumEntries() {
        return this.enumEntries;
    }

    public final IrSimpleFunctionSymbol getCreateEnumEntries() {
        return this.createEnumEntries;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.Symbols
    public IrClassSymbol getDefaultConstructorMarker() {
        return this.defaultConstructorMarker;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.Symbols
    public IrClassSymbol getCoroutineImpl() {
        throw new IllegalStateException("not implemented".toString());
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.Symbols
    public IrSimpleFunctionSymbol getCoroutineSuspendedGetter() {
        throw new IllegalStateException("not implemented".toString());
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.Symbols
    public IrSimpleFunctionSymbol getGetContinuation() {
        throw new IllegalStateException("not implemented".toString());
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.Symbols
    public IrSimpleFunctionSymbol getCoroutineContextGetter() {
        throw new IllegalStateException("not implemented".toString());
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.Symbols
    public IrSimpleFunctionSymbol getSuspendCoroutineUninterceptedOrReturn() {
        throw new IllegalStateException("not implemented".toString());
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.Symbols
    public IrSimpleFunctionSymbol getCoroutineGetContext() {
        throw new IllegalStateException("not implemented".toString());
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.Symbols
    public IrSimpleFunctionSymbol getReturnIfSuspended() {
        throw new IllegalStateException("not implemented".toString());
    }

    public final IrClassSymbol getJavaLangClass() {
        return this.javaLangClass;
    }

    public final IrConstructorSymbol getJavaLangDeprecatedConstructorWithDeprecatedFlag() {
        return this.javaLangDeprecatedConstructorWithDeprecatedFlag;
    }

    public final IrConstructorSymbol getAssertionErrorConstructor() {
        return this.assertionErrorConstructor;
    }

    public final IrType getNoSuchFieldErrorType() {
        return this.noSuchFieldErrorType;
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.Symbols
    public IrClassSymbol getContinuationClass() {
        return this.continuationClass;
    }

    public final IrType getResultOfAnyType() {
        return this.resultOfAnyType;
    }

    public final IrClassSymbol getContinuationImplClass() {
        return this.continuationImplClass;
    }

    public final IrClassSymbol getSuspendFunctionInterface() {
        return this.suspendFunctionInterface;
    }

    public final IrClassSymbol getLambdaClass() {
        return this.lambdaClass;
    }

    public final IrClassSymbol getSuspendLambdaClass() {
        return this.suspendLambdaClass;
    }

    public final IrClassSymbol getRestrictedSuspendLambdaClass() {
        return this.restrictedSuspendLambdaClass;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void addSuspendLambdaInterfaceFunctions(IrClass klass) {
        klass.setSuperTypes(CollectionsKt.plus((Collection<? extends IrType>) klass.getSuperTypes(), IrTypesKt.getDefaultType(this.suspendFunctionInterface)));
        IrFactory factory = klass.getFactory();
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        irFunctionBuilder.setReturnType(IrUtilsKt.getDefaultType(klass));
        IrConstructor irConstructorBuildConstructor = DeclarationBuildersKt.buildConstructor(factory, irFunctionBuilder);
        klass.getDeclarations().add(irConstructorBuildConstructor);
        irConstructorBuildConstructor.setParent(klass);
        IrConstructor irConstructor = irConstructorBuildConstructor;
        DeclarationBuildersKt.addValueParameter$default(irConstructor, "arity", getIrBuiltIns().getIntType(), (IrDeclarationOrigin) null, 4, (Object) null);
        DeclarationBuildersKt.addValueParameter$default(irConstructor, CoroutineConstantsKt.SUSPEND_FUNCTION_COMPLETION_PARAMETER_NAME, IrTypesKt.makeNullable(IrTypesKt.typeWith(getContinuationClass(), getIrBuiltIns().getAnyNType())), (IrDeclarationOrigin) null, 4, (Object) null);
        IrType anyNType = getIrBuiltIns().getAnyNType();
        Modality modality = Modality.ABSTRACT;
        DescriptorVisibility PROTECTED = DescriptorVisibilities.PROTECTED;
        Intrinsics.checkNotNullExpressionValue(PROTECTED, "PROTECTED");
        DeclarationBuildersKt.addValueParameter$default(DeclarationBuildersKt.addFunction$default(klass, CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, anyNType, modality, PROTECTED, false, false, false, false, null, 0, 0, XKeySym.XK_Greek_pi, null), CoroutineConstantsKt.SUSPEND_CALL_RESULT_NAME, this.resultOfAnyType, (IrDeclarationOrigin) null, 4, (Object) null);
        DeclarationBuildersKt.addValueParameter$default(DeclarationBuildersKt.addFunction$default(klass, CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, IrTypesKt.typeWith(getContinuationClass(), getIrBuiltIns().getUnitType()), Modality.OPEN, null, false, false, false, false, null, 0, 0, XKeySym.XK_Greek_psi, null), CoroutineConstantsKt.SUSPEND_FUNCTION_COMPLETION_PARAMETER_NAME, IrTypesKt.typeWith(getContinuationClass(), getIrBuiltIns().getNothingType()), (IrDeclarationOrigin) null, 4, (Object) null);
        IrSimpleFunction irSimpleFunctionAddFunction$default = DeclarationBuildersKt.addFunction$default(klass, CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, IrTypesKt.typeWith(getContinuationClass(), getIrBuiltIns().getUnitType()), Modality.OPEN, null, false, false, false, false, null, 0, 0, XKeySym.XK_Greek_psi, null);
        DeclarationBuildersKt.addValueParameter$default(irSimpleFunctionAddFunction$default, "value", getIrBuiltIns().getAnyNType(), (IrDeclarationOrigin) null, 4, (Object) null);
        DeclarationBuildersKt.addValueParameter$default(irSimpleFunctionAddFunction$default, CoroutineConstantsKt.SUSPEND_FUNCTION_COMPLETION_PARAMETER_NAME, IrTypesKt.typeWith(getContinuationClass(), getIrBuiltIns().getNothingType()), (IrDeclarationOrigin) null, 4, (Object) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void generateCallableReferenceMethods(IrClass klass) {
        DeclarationBuildersKt.addFunction$default(klass, "getSignature", getIrBuiltIns().getStringType(), Modality.OPEN, null, false, false, false, false, null, 0, 0, XKeySym.XK_Greek_psi, null);
        DeclarationBuildersKt.addFunction$default(klass, "getName", getIrBuiltIns().getStringType(), Modality.OPEN, null, false, false, false, false, null, 0, 0, XKeySym.XK_Greek_psi, null);
        DeclarationBuildersKt.addFunction$default(klass, "getOwner", IrTypesKt.getDefaultType(this.kDeclarationContainer), Modality.OPEN, null, false, false, false, false, null, 0, 0, XKeySym.XK_Greek_psi, null);
    }

    public final IrClassSymbol getFunctionReference() {
        return this.functionReference;
    }

    public final IrSimpleFunctionSymbol getFunctionReferenceGetSignature() {
        return this.functionReferenceGetSignature;
    }

    public final IrSimpleFunctionSymbol getFunctionReferenceGetName() {
        return this.functionReferenceGetName;
    }

    public final IrSimpleFunctionSymbol getFunctionReferenceGetOwner() {
        return this.functionReferenceGetOwner;
    }

    public final IrClassSymbol getFunctionReferenceImpl() {
        return this.functionReferenceImpl;
    }

    public final IrClassSymbol getAdaptedFunctionReference() {
        return this.adaptedFunctionReference;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void generateCallableReferenceSuperclassConstructors(IrClass irClass, boolean z) {
        Iterator it = CollectionsKt.listOf((Object[]) new Boolean[]{false, true}).iterator();
        while (it.hasNext()) {
            boolean zBooleanValue = ((Boolean) it.next()).booleanValue();
            IrFactory factory = irClass.getFactory();
            IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
            irFunctionBuilder.setReturnType(IrUtilsKt.getDefaultType(irClass));
            IrConstructor irConstructorBuildConstructor = DeclarationBuildersKt.buildConstructor(factory, irFunctionBuilder);
            irClass.getDeclarations().add(irConstructorBuildConstructor);
            irConstructorBuildConstructor.setParent(irClass);
            if (z) {
                DeclarationBuildersKt.addValueParameter$default(irConstructorBuildConstructor, "arity", getIrBuiltIns().getIntType(), (IrDeclarationOrigin) null, 4, (Object) null);
            }
            if (zBooleanValue) {
                DeclarationBuildersKt.addValueParameter$default(irConstructorBuildConstructor, "receiver", getIrBuiltIns().getAnyNType(), (IrDeclarationOrigin) null, 4, (Object) null);
            }
            IrConstructor irConstructor = irConstructorBuildConstructor;
            DeclarationBuildersKt.addValueParameter$default(irConstructor, "owner", IrTypesKt.getStarProjectedType(this.javaLangClass), (IrDeclarationOrigin) null, 4, (Object) null);
            DeclarationBuildersKt.addValueParameter$default(irConstructor, "name", getIrBuiltIns().getStringType(), (IrDeclarationOrigin) null, 4, (Object) null);
            DeclarationBuildersKt.addValueParameter$default(irConstructor, "signature", getIrBuiltIns().getStringType(), (IrDeclarationOrigin) null, 4, (Object) null);
            DeclarationBuildersKt.addValueParameter$default(irConstructor, OverlayablePolicy.NAME_flags, getIrBuiltIns().getIntType(), (IrDeclarationOrigin) null, 4, (Object) null);
        }
    }

    public final IrClassSymbol getFunInterfaceConstructorReferenceClass() {
        return this.funInterfaceConstructorReferenceClass;
    }

    public final IrClassSymbol getFunction(int parameterCount) {
        return getIrBuiltIns().functionN(parameterCount).getSymbol();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrClassSymbol createFunctionClass(final int n, final boolean isSuspend) {
        return createClass$default(this, new FqName("kotlin.jvm.functions.Function" + (n + (isSuspend ? 1 : 0))), ClassKind.INTERFACE, null, false, new Function1<IrClass, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmSymbols.createFunctionClass.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(IrClass irClass) {
                invoke2(irClass);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IrClass klass) {
                Intrinsics.checkNotNullParameter(klass, "klass");
                if (1 <= n) {
                    int i = 1;
                    while (true) {
                        DeclarationBuildersKt.addTypeParameter(klass, "P" + i, this.getIrBuiltIns().getAnyNType(), Variance.IN_VARIANCE);
                        if (i == n) {
                            break;
                        } else {
                            i++;
                        }
                    }
                }
                IrSimpleFunction irSimpleFunctionAddFunction$default = DeclarationBuildersKt.addFunction$default(klass, "invoke", IrTypesKt.getDefaultType(DeclarationBuildersKt.addTypeParameter(klass, "R", this.getIrBuiltIns().getAnyNType(), Variance.OUT_VARIANCE)), Modality.ABSTRACT, null, false, isSuspend, false, false, null, 0, 0, XKeySym.XK_Greek_PSI, null);
                int i2 = n;
                if (1 > i2) {
                    return;
                }
                int i3 = 1;
                while (true) {
                    DeclarationBuildersKt.addValueParameter$default(irSimpleFunctionAddFunction$default, "p" + i3, IrTypesKt.getDefaultType(klass.getTypeParameters().get(i3 - 1)), (IrDeclarationOrigin) null, 4, (Object) null);
                    if (i3 == i2) {
                        return;
                    } else {
                        i3++;
                    }
                }
            }
        }, 12, null);
    }

    public final IrClassSymbol getJvmFunctionClass(int parameterCount) {
        IrClassSymbol irClassSymbolInvoke = this.jvmFunctionClasses.invoke(Integer.valueOf(parameterCount));
        Intrinsics.checkNotNullExpressionValue(irClassSymbolInvoke, "invoke(...)");
        return irClassSymbolInvoke;
    }

    public final IrClassSymbol getJvmSuspendFunctionClass(int parameterCount) {
        IrClassSymbol irClassSymbolInvoke = this.jvmSuspendFunctionClasses.invoke(Integer.valueOf(parameterCount));
        Intrinsics.checkNotNullExpressionValue(irClassSymbolInvoke, "invoke(...)");
        return irClassSymbolInvoke;
    }

    public final IrClassSymbol getFunctionN() {
        return this.functionN;
    }

    public final IrClassSymbol getJvmInlineAnnotation() {
        return this.jvmInlineAnnotation;
    }

    /* JADX INFO: compiled from: JvmSymbols.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u000f\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0005HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J'\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00032\b\u0010\u0012\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0013\u001a\u00020\u0005HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmSymbols$PropertyReferenceKey;", "", "mutable", "", "parameterCount", "", "impl", "(ZIZ)V", "getImpl", "()Z", "getMutable", "getParameterCount", "()I", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "toString", "", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class PropertyReferenceKey {
        private final boolean impl;
        private final boolean mutable;
        private final int parameterCount;

        public static /* synthetic */ PropertyReferenceKey copy$default(PropertyReferenceKey propertyReferenceKey, boolean z, int i, boolean z2, int i2, Object obj) {
            if ((i2 & 1) != 0) {
                z = propertyReferenceKey.mutable;
            }
            if ((i2 & 2) != 0) {
                i = propertyReferenceKey.parameterCount;
            }
            if ((i2 & 4) != 0) {
                z2 = propertyReferenceKey.impl;
            }
            return propertyReferenceKey.copy(z, i, z2);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final boolean getMutable() {
            return this.mutable;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final int getParameterCount() {
            return this.parameterCount;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final boolean getImpl() {
            return this.impl;
        }

        public final PropertyReferenceKey copy(boolean mutable, int parameterCount, boolean impl) {
            return new PropertyReferenceKey(mutable, parameterCount, impl);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof PropertyReferenceKey)) {
                return false;
            }
            PropertyReferenceKey propertyReferenceKey = (PropertyReferenceKey) other;
            return this.mutable == propertyReferenceKey.mutable && this.parameterCount == propertyReferenceKey.parameterCount && this.impl == propertyReferenceKey.impl;
        }

        public int hashCode() {
            return (((Boolean.hashCode(this.mutable) * 31) + Integer.hashCode(this.parameterCount)) * 31) + Boolean.hashCode(this.impl);
        }

        public String toString() {
            return "PropertyReferenceKey(mutable=" + this.mutable + ", parameterCount=" + this.parameterCount + ", impl=" + this.impl + ')';
        }

        public PropertyReferenceKey(boolean z, int i, boolean z2) {
            this.mutable = z;
            this.parameterCount = i;
            this.impl = z2;
        }

        public final boolean getMutable() {
            return this.mutable;
        }

        public final int getParameterCount() {
            return this.parameterCount;
        }

        public final boolean getImpl() {
            return this.impl;
        }
    }

    public final IrClassSymbol getPropertyReferenceClass(final boolean mutable, final int parameterCount, final boolean impl) {
        PropertyReferenceKey propertyReferenceKey = new PropertyReferenceKey(mutable, parameterCount, impl);
        Map<PropertyReferenceKey, IrClassSymbol> map = this.propertyReferenceClassCache;
        IrClassSymbol irClassSymbolCreateClass$default = map.get(propertyReferenceKey);
        if (irClassSymbolCreateClass$default == null) {
            StringBuilder sb = new StringBuilder();
            if (mutable) {
                sb.append("Mutable");
            }
            sb.append("PropertyReference");
            sb.append(parameterCount);
            if (impl) {
                sb.append("Impl");
            }
            String string = sb.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            irClassSymbolCreateClass$default = createClass$default(this, new FqName("kotlin.jvm.internal." + string), null, impl ? Modality.FINAL : Modality.ABSTRACT, false, new Function1<IrClass, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmSymbols$getPropertyReferenceClass$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(IrClass irClass) {
                    invoke2(irClass);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(IrClass klass) {
                    Intrinsics.checkNotNullParameter(klass, "klass");
                    if (impl) {
                        IrFactory factory = klass.getFactory();
                        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
                        irFunctionBuilder.setReturnType(IrUtilsKt.getDefaultType(klass));
                        IrConstructor irConstructorBuildConstructor = DeclarationBuildersKt.buildConstructor(factory, irFunctionBuilder);
                        klass.getDeclarations().add(irConstructorBuildConstructor);
                        irConstructorBuildConstructor.setParent(klass);
                        JvmSymbols jvmSymbols = this;
                        IrConstructor irConstructor = irConstructorBuildConstructor;
                        DeclarationBuildersKt.addValueParameter$default(irConstructor, "owner", IrTypesKt.getDefaultType(jvmSymbols.kDeclarationContainer), (IrDeclarationOrigin) null, 4, (Object) null);
                        DeclarationBuildersKt.addValueParameter$default(irConstructor, "name", jvmSymbols.getIrBuiltIns().getStringType(), (IrDeclarationOrigin) null, 4, (Object) null);
                        DeclarationBuildersKt.addValueParameter$default(irConstructor, "string", jvmSymbols.getIrBuiltIns().getStringType(), (IrDeclarationOrigin) null, 4, (Object) null);
                        if (this.generateOptimizedCallableReferenceSuperClasses) {
                            this.generateCallableReferenceSuperclassConstructors(klass, false);
                        }
                        klass.setSuperTypes(CollectionsKt.plus((Collection<? extends IrType>) klass.getSuperTypes(), IrTypesKt.getDefaultType(this.getPropertyReferenceClass(mutable, parameterCount, false))));
                    } else {
                        IrFactory factory2 = klass.getFactory();
                        IrFunctionBuilder irFunctionBuilder2 = new IrFunctionBuilder();
                        irFunctionBuilder2.setReturnType(IrUtilsKt.getDefaultType(klass));
                        IrConstructor irConstructorBuildConstructor2 = DeclarationBuildersKt.buildConstructor(factory2, irFunctionBuilder2);
                        klass.getDeclarations().add(irConstructorBuildConstructor2);
                        IrClass irClass = klass;
                        irConstructorBuildConstructor2.setParent(irClass);
                        IrFactory factory3 = klass.getFactory();
                        IrFunctionBuilder irFunctionBuilder3 = new IrFunctionBuilder();
                        irFunctionBuilder3.setReturnType(IrUtilsKt.getDefaultType(klass));
                        IrConstructor irConstructorBuildConstructor3 = DeclarationBuildersKt.buildConstructor(factory3, irFunctionBuilder3);
                        klass.getDeclarations().add(irConstructorBuildConstructor3);
                        irConstructorBuildConstructor3.setParent(irClass);
                        DeclarationBuildersKt.addValueParameter$default(irConstructorBuildConstructor3, "receiver", this.getIrBuiltIns().getAnyNType(), (IrDeclarationOrigin) null, 4, (Object) null);
                    }
                    Name nameIdentifier = Name.identifier("receiver");
                    Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                    IrFactory factory4 = klass.getFactory();
                    IrPropertyBuilder irPropertyBuilder = new IrPropertyBuilder();
                    irPropertyBuilder.setName(nameIdentifier);
                    IrProperty irPropertyBuildProperty = DeclarationBuildersKt.buildProperty(factory4, irPropertyBuilder);
                    klass.getDeclarations().add(irPropertyBuildProperty);
                    IrClass irClass2 = klass;
                    irPropertyBuildProperty.setParent(irClass2);
                    JvmSymbols jvmSymbols2 = this;
                    IrFactory irFactory = jvmSymbols2.irFactory;
                    IrFieldBuilder irFieldBuilder = new IrFieldBuilder();
                    irFieldBuilder.setName(nameIdentifier);
                    irFieldBuilder.setType(jvmSymbols2.getIrBuiltIns().getAnyNType());
                    DescriptorVisibility PROTECTED = DescriptorVisibilities.PROTECTED;
                    Intrinsics.checkNotNullExpressionValue(PROTECTED, "PROTECTED");
                    irFieldBuilder.setVisibility(PROTECTED);
                    IrField irFieldBuildField = DeclarationBuildersKt.buildField(irFactory, irFieldBuilder);
                    irFieldBuildField.setParent(irClass2);
                    irPropertyBuildProperty.setBackingField(irFieldBuildField);
                    this.generateCallableReferenceMethods(klass);
                    IrSimpleFunction irSimpleFunctionAddFunction$default = DeclarationBuildersKt.addFunction$default(klass, "get", this.getIrBuiltIns().getAnyNType(), Modality.ABSTRACT, null, false, false, false, false, null, 0, 0, XKeySym.XK_Greek_psi, null);
                    int i = parameterCount;
                    JvmSymbols jvmSymbols3 = this;
                    for (int i2 = 0; i2 < i; i2++) {
                        DeclarationBuildersKt.addValueParameter$default(irSimpleFunctionAddFunction$default, "receiver" + i2, jvmSymbols3.getIrBuiltIns().getAnyNType(), (IrDeclarationOrigin) null, 4, (Object) null);
                    }
                    IrSimpleFunction irSimpleFunctionAddFunction$default2 = DeclarationBuildersKt.addFunction$default(klass, "invoke", this.getIrBuiltIns().getAnyNType(), Modality.FINAL, null, false, false, false, false, null, 0, 0, XKeySym.XK_Greek_psi, null);
                    int i3 = parameterCount;
                    JvmSymbols jvmSymbols4 = this;
                    for (int i4 = 0; i4 < i3; i4++) {
                        DeclarationBuildersKt.addValueParameter$default(irSimpleFunctionAddFunction$default2, "receiver" + i4, jvmSymbols4.getIrBuiltIns().getAnyNType(), (IrDeclarationOrigin) null, 4, (Object) null);
                    }
                    if (mutable) {
                        IrSimpleFunction irSimpleFunctionAddFunction$default3 = DeclarationBuildersKt.addFunction$default(klass, FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, this.getIrBuiltIns().getUnitType(), Modality.ABSTRACT, null, false, false, false, false, null, 0, 0, XKeySym.XK_Greek_psi, null);
                        int i5 = parameterCount;
                        JvmSymbols jvmSymbols5 = this;
                        for (int i6 = 0; i6 < i5; i6++) {
                            DeclarationBuildersKt.addValueParameter$default(irSimpleFunctionAddFunction$default3, "receiver" + i6, jvmSymbols5.getIrBuiltIns().getAnyNType(), (IrDeclarationOrigin) null, 4, (Object) null);
                        }
                        DeclarationBuildersKt.addValueParameter$default(irSimpleFunctionAddFunction$default3, "value", jvmSymbols5.getIrBuiltIns().getAnyNType(), (IrDeclarationOrigin) null, 4, (Object) null);
                    }
                }
            }, 10, null);
            map.put(propertyReferenceKey, irClassSymbolCreateClass$default);
        }
        return irClassSymbolCreateClass$default;
    }

    public final IrClassSymbol getReflection() {
        return this.reflection;
    }

    public final JvmReflectSymbols getJavaLangReflectSymbols() {
        return (JvmReflectSymbols) this.javaLangReflectSymbols.getValue();
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.Symbols
    public IrClassSymbol getFunctionAdapter() {
        return this.functionAdapter;
    }

    public final IrSimpleFunctionSymbol getGetOrCreateKotlinPackage() {
        return this.getOrCreateKotlinPackage;
    }

    public final IrSimpleFunctionSymbol getDesiredAssertionStatus() {
        return (IrSimpleFunctionSymbol) this.desiredAssertionStatus.getValue();
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.Symbols
    public IrSimpleFunctionSymbol getUnsafeCoerceIntrinsic() {
        return this.unsafeCoerceIntrinsic;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<IrClassSymbol> getProgressionUtilClasses() {
        return (List) this.progressionUtilClasses.getValue();
    }

    @Override // org.jetbrains.kotlin.backend.common.ir.BuiltinSymbolsBase
    public Map<IrClassifierSymbol, IrSimpleFunctionSymbol> getGetProgressionLastElementByReturnType() {
        return (Map) this.getProgressionLastElementByReturnType.getValue();
    }

    public final IrSimpleType getArrayOfAnyType() {
        return this.arrayOfAnyType;
    }

    public final IrSimpleType getArrayOfAnyNType() {
        return this.arrayOfAnyNType;
    }

    public final IrSimpleFunctionSymbol getIndyLambdaMetafactoryIntrinsic() {
        return this.indyLambdaMetafactoryIntrinsic;
    }

    /* JADX INFO: compiled from: JvmSymbols.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0007\b\u0001¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006R\u0011\u0010\u000b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006R\u0011\u0010\r\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0006R\u0011\u0010\u000f\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0006R\u0011\u0010\u0011\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0006R\u0011\u0010\u0013\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0006R\u0011\u0010\u0015\u001a\u00020\u0016¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0019\u001a\u00020\u001a¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0011\u0010\u001d\u001a\u00020\u001e¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 ¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmSymbols$SerializedLambdaClass;", "", "(Lorg/jetbrains/kotlin/backend/jvm/JvmSymbols;)V", "getCapturedArg", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getGetCapturedArg", "()Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getFunctionalInterfaceClass", "getGetFunctionalInterfaceClass", "getFunctionalInterfaceMethodName", "getGetFunctionalInterfaceMethodName", "getFunctionalInterfaceMethodSignature", "getGetFunctionalInterfaceMethodSignature", "getImplClass", "getGetImplClass", "getImplMethodKind", "getGetImplMethodKind", "getImplMethodName", "getGetImplMethodName", "getImplMethodSignature", "getGetImplMethodSignature", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getIrClass", "()Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "irType", "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "getIrType", "()Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "getSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class SerializedLambdaClass {
        private final IrSimpleFunction getCapturedArg;
        private final IrSimpleFunction getFunctionalInterfaceClass;
        private final IrSimpleFunction getFunctionalInterfaceMethodName;
        private final IrSimpleFunction getFunctionalInterfaceMethodSignature;
        private final IrSimpleFunction getImplClass;
        private final IrSimpleFunction getImplMethodKind;
        private final IrSimpleFunction getImplMethodName;
        private final IrSimpleFunction getImplMethodSignature;
        private final IrClass irClass;
        private final IrSimpleType irType;
        private final IrClassSymbol symbol;

        @Deprecated(message = "Should not be used outside of JvmSymbols")
        public SerializedLambdaClass() {
            IrClassSymbol irClassSymbolCreateClass$default = JvmSymbols.createClass$default(JvmSymbols.this, new FqName("java.lang.invoke.SerializedLambda"), null, null, false, null, 30, null);
            this.symbol = irClassSymbolCreateClass$default;
            IrClass owner = irClassSymbolCreateClass$default.getOwner();
            this.irClass = owner;
            this.irType = IrUtilsKt.getDefaultType(owner);
            this.getImplMethodName = DeclarationBuildersKt.addFunction$default(owner, "getImplMethodName", JvmSymbols.this.getIrBuiltIns().getStringType(), null, null, false, false, false, false, null, 0, 0, 2044, null);
            this.getImplMethodKind = DeclarationBuildersKt.addFunction$default(owner, "getImplMethodKind", JvmSymbols.this.getIrBuiltIns().getIntType(), null, null, false, false, false, false, null, 0, 0, 2044, null);
            this.getImplClass = DeclarationBuildersKt.addFunction$default(owner, "getImplClass", JvmSymbols.this.getIrBuiltIns().getStringType(), null, null, false, false, false, false, null, 0, 0, 2044, null);
            this.getImplMethodSignature = DeclarationBuildersKt.addFunction$default(owner, "getImplMethodSignature", JvmSymbols.this.getIrBuiltIns().getStringType(), null, null, false, false, false, false, null, 0, 0, 2044, null);
            this.getFunctionalInterfaceClass = DeclarationBuildersKt.addFunction$default(owner, "getFunctionalInterfaceClass", JvmSymbols.this.getIrBuiltIns().getStringType(), null, null, false, false, false, false, null, 0, 0, 2044, null);
            this.getFunctionalInterfaceMethodName = DeclarationBuildersKt.addFunction$default(owner, "getFunctionalInterfaceMethodName", JvmSymbols.this.getIrBuiltIns().getStringType(), null, null, false, false, false, false, null, 0, 0, 2044, null);
            this.getFunctionalInterfaceMethodSignature = DeclarationBuildersKt.addFunction$default(owner, "getFunctionalInterfaceMethodSignature", JvmSymbols.this.getIrBuiltIns().getStringType(), null, null, false, false, false, false, null, 0, 0, 2044, null);
            IrSimpleFunction irSimpleFunctionAddFunction$default = DeclarationBuildersKt.addFunction$default(owner, "getCapturedArg", JvmSymbols.this.getIrBuiltIns().getAnyNType(), null, null, false, false, false, false, null, 0, 0, 2044, null);
            Name nameIdentifier = Name.identifier(ASTExpr.DEFAULT_INDEX_VARIABLE_NAME);
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            DeclarationBuildersKt.addValueParameter$default(irSimpleFunctionAddFunction$default, nameIdentifier, JvmSymbols.this.getIrBuiltIns().getIntType(), (IrDeclarationOrigin) null, 4, (Object) null);
            this.getCapturedArg = irSimpleFunctionAddFunction$default;
        }

        public final IrClassSymbol getSymbol() {
            return this.symbol;
        }

        public final IrClass getIrClass() {
            return this.irClass;
        }

        public final IrSimpleType getIrType() {
            return this.irType;
        }

        public final IrSimpleFunction getGetImplMethodName() {
            return this.getImplMethodName;
        }

        public final IrSimpleFunction getGetImplMethodKind() {
            return this.getImplMethodKind;
        }

        public final IrSimpleFunction getGetImplClass() {
            return this.getImplClass;
        }

        public final IrSimpleFunction getGetImplMethodSignature() {
            return this.getImplMethodSignature;
        }

        public final IrSimpleFunction getGetFunctionalInterfaceClass() {
            return this.getFunctionalInterfaceClass;
        }

        public final IrSimpleFunction getGetFunctionalInterfaceMethodName() {
            return this.getFunctionalInterfaceMethodName;
        }

        public final IrSimpleFunction getGetFunctionalInterfaceMethodSignature() {
            return this.getFunctionalInterfaceMethodSignature;
        }

        public final IrSimpleFunction getGetCapturedArg() {
            return this.getCapturedArg;
        }
    }

    public final SerializedLambdaClass getSerializedLambda() {
        return this.serializedLambda;
    }

    public final IrClassSymbol getIllegalArgumentException() {
        return this.illegalArgumentException;
    }

    public final IrConstructorSymbol getIllegalArgumentExceptionCtorString() {
        return this.illegalArgumentExceptionCtorString;
    }

    public final IrSimpleFunctionSymbol getJvmMethodType() {
        return this.jvmMethodType;
    }

    public final IrSimpleFunctionSymbol getJvmMethodHandle() {
        return this.jvmMethodHandle;
    }

    public final IrSimpleFunctionSymbol getJvmIndyIntrinsic() {
        return this.jvmIndyIntrinsic;
    }

    public final IrSimpleFunctionSymbol getJvmOriginalMethodTypeIntrinsic() {
        return this.jvmOriginalMethodTypeIntrinsic;
    }

    public final IrSimpleFunctionSymbol getJvmDebuggerInvokeSpecialIntrinsic() {
        return this.jvmDebuggerInvokeSpecialIntrinsic;
    }

    public final IrSimpleFunctionSymbol getNonGenericToArray() {
        return this.nonGenericToArray;
    }

    public final IrSimpleFunctionSymbol getGenericToArray() {
        return this.genericToArray;
    }

    public final IrClassSymbol getJvmName() {
        return this.jvmName;
    }

    public final IrPropertySymbol getKClassJava() {
        return this.kClassJava;
    }

    public final IrSimpleFunction getKClassJavaPropertyGetter() {
        return this.kClassJavaPropertyGetter;
    }

    public final IrClassSymbol getSpreadBuilder() {
        return this.spreadBuilder;
    }

    public final Map<IrType, IrClassSymbol> getPrimitiveSpreadBuilders() {
        return this.primitiveSpreadBuilders;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void addArraysCopyOfFunction(IrClass irClass, IrSimpleType irSimpleType) {
        IrSimpleType irSimpleType2 = irSimpleType;
        IrSimpleFunction irSimpleFunctionAddFunction$default = DeclarationBuildersKt.addFunction$default(irClass, "copyOf", irSimpleType2, null, null, true, false, false, false, null, 0, 0, XKeySym.XK_Greek_mu, null);
        IrSimpleFunction irSimpleFunction = irSimpleFunctionAddFunction$default;
        DeclarationBuildersKt.addValueParameter$default(irSimpleFunction, "original", irSimpleType2, (IrDeclarationOrigin) null, 4, (Object) null);
        DeclarationBuildersKt.addValueParameter$default(irSimpleFunction, "newLength", getIrBuiltIns().getIntType(), (IrDeclarationOrigin) null, 4, (Object) null);
        this.arraysCopyOfFunctions.put(IrTypesKt.getClassifierOrFail(irSimpleType2), irSimpleFunctionAddFunction$default);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void addArraysEqualsFunction(IrClass irClass, IrSimpleType irSimpleType) {
        IrSimpleFunction irSimpleFunctionAddFunction$default = DeclarationBuildersKt.addFunction$default(irClass, Namer.EQUALS_METHOD_NAME, getIrBuiltIns().getBooleanType(), null, null, true, false, false, false, null, 0, 0, XKeySym.XK_Greek_mu, null);
        IrSimpleType irSimpleType2 = irSimpleType;
        DeclarationBuildersKt.addValueParameter$default(irSimpleFunctionAddFunction$default, "a", irSimpleType2, (IrDeclarationOrigin) null, 4, (Object) null);
        DeclarationBuildersKt.addValueParameter$default(irSimpleFunctionAddFunction$default, JvmAnnotationNames.SERIALIZED_IR_BYTES_FIELD_NAME, irSimpleType2, (IrDeclarationOrigin) null, 4, (Object) null);
    }

    public final IrClassSymbol getArraysClass() {
        return this.arraysClass;
    }

    public final IrSimpleFunctionSymbol getArraysCopyOfFunction(IrSimpleType arrayType) {
        Intrinsics.checkNotNullParameter(arrayType, "arrayType");
        IrSimpleFunction irSimpleFunction = this.arraysCopyOfFunctions.get(arrayType.getClassifier());
        if (irSimpleFunction != null) {
            return irSimpleFunction.getSymbol();
        }
        throw new AssertionError("Array type expected: " + RenderIrElementKt.render$default(arrayType, (DumpIrTreeOptions) null, 1, (Object) null));
    }

    public final IrSimpleFunctionSymbol getCompareUnsignedInt() {
        return this.compareUnsignedInt;
    }

    public final IrSimpleFunctionSymbol getDivideUnsignedInt() {
        return this.divideUnsignedInt;
    }

    public final IrSimpleFunctionSymbol getRemainderUnsignedInt() {
        return this.remainderUnsignedInt;
    }

    public final IrSimpleFunctionSymbol getToUnsignedStringInt() {
        return this.toUnsignedStringInt;
    }

    public final IrSimpleFunctionSymbol getCompareUnsignedLong() {
        return this.compareUnsignedLong;
    }

    public final IrSimpleFunctionSymbol getDivideUnsignedLong() {
        return this.divideUnsignedLong;
    }

    public final IrSimpleFunctionSymbol getRemainderUnsignedLong() {
        return this.remainderUnsignedLong;
    }

    public final IrSimpleFunctionSymbol getToUnsignedStringLong() {
        return this.toUnsignedStringLong;
    }

    public final IrSimpleFunctionSymbol getIntPostfixIncrDecr() {
        return this.intPostfixIncrDecr;
    }

    public final IrSimpleFunctionSymbol getIntPrefixIncrDecr() {
        return this.intPrefixIncrDecr;
    }

    private final IrSimpleFunctionSymbol createIncrDecrFun(String intrinsicName) {
        IrFactory irFactory = this.irFactory;
        IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
        Name nameSpecial = Name.special(intrinsicName);
        Intrinsics.checkNotNullExpressionValue(nameSpecial, "special(...)");
        irFunctionBuilder.setName(nameSpecial);
        irFunctionBuilder.setOrigin(IrDeclarationOrigin.IR_BUILTINS_STUB.INSTANCE);
        IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(irFactory, irFunctionBuilder);
        irSimpleFunctionBuildFunction.setParent(this.kotlinJvmInternalPackage);
        IrSimpleFunction irSimpleFunction = irSimpleFunctionBuildFunction;
        DeclarationBuildersKt.addValueParameter$default(irSimpleFunction, "value", getIrBuiltIns().getIntType(), (IrDeclarationOrigin) null, 4, (Object) null);
        DeclarationBuildersKt.addValueParameter$default(irSimpleFunction, "delta", getIrBuiltIns().getIntType(), (IrDeclarationOrigin) null, 4, (Object) null);
        irSimpleFunctionBuildFunction.setReturnType(getIrBuiltIns().getIntType());
        return irSimpleFunctionBuildFunction.getSymbol();
    }

    private final IrClassSymbol createJavaPrimitiveClassWithUnsignedUtils(FqName fqName, final IrType type) {
        return createClass$default(this, fqName, null, null, false, new Function1<IrClass, Unit>() { // from class: org.jetbrains.kotlin.backend.jvm.JvmSymbols.createJavaPrimitiveClassWithUnsignedUtils.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(IrClass irClass) {
                invoke2(irClass);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(IrClass klass) {
                Intrinsics.checkNotNullParameter(klass, "klass");
                IrSimpleFunction irSimpleFunctionAddFunction$default = DeclarationBuildersKt.addFunction$default(klass, "compareUnsigned", JvmSymbols.this.getIrBuiltIns().getIntType(), null, null, true, false, false, false, null, 0, 0, XKeySym.XK_Greek_mu, null);
                IrType irType = type;
                IrSimpleFunction irSimpleFunction = irSimpleFunctionAddFunction$default;
                DeclarationBuildersKt.addValueParameter$default(irSimpleFunction, "x", irType, (IrDeclarationOrigin) null, 4, (Object) null);
                DeclarationBuildersKt.addValueParameter$default(irSimpleFunction, "y", irType, (IrDeclarationOrigin) null, 4, (Object) null);
                IrSimpleFunction irSimpleFunctionAddFunction$default2 = DeclarationBuildersKt.addFunction$default(klass, "divideUnsigned", type, null, null, true, false, false, false, null, 0, 0, XKeySym.XK_Greek_mu, null);
                IrType irType2 = type;
                IrSimpleFunction irSimpleFunction2 = irSimpleFunctionAddFunction$default2;
                DeclarationBuildersKt.addValueParameter$default(irSimpleFunction2, "dividend", irType2, (IrDeclarationOrigin) null, 4, (Object) null);
                DeclarationBuildersKt.addValueParameter$default(irSimpleFunction2, "divisor", irType2, (IrDeclarationOrigin) null, 4, (Object) null);
                IrSimpleFunction irSimpleFunctionAddFunction$default3 = DeclarationBuildersKt.addFunction$default(klass, "remainderUnsigned", type, null, null, true, false, false, false, null, 0, 0, XKeySym.XK_Greek_mu, null);
                IrType irType3 = type;
                IrSimpleFunction irSimpleFunction3 = irSimpleFunctionAddFunction$default3;
                DeclarationBuildersKt.addValueParameter$default(irSimpleFunction3, "dividend", irType3, (IrDeclarationOrigin) null, 4, (Object) null);
                DeclarationBuildersKt.addValueParameter$default(irSimpleFunction3, "divisor", irType3, (IrDeclarationOrigin) null, 4, (Object) null);
                DeclarationBuildersKt.addValueParameter$default(DeclarationBuildersKt.addFunction$default(klass, "toUnsignedString", JvmSymbols.this.getIrBuiltIns().getStringType(), null, null, true, false, false, false, null, 0, 0, XKeySym.XK_Greek_mu, null), ASTExpr.DEFAULT_INDEX_VARIABLE_NAME, type, (IrDeclarationOrigin) null, 4, (Object) null);
            }
        }, 14, null);
    }

    public final IrSimpleFunctionSymbol getSignatureStringIntrinsic() {
        return this.signatureStringIntrinsic;
    }

    public final IrSimpleFunctionSymbol typeToStringValueOfFunction(IrType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        IrSimpleFunctionSymbol irSimpleFunctionSymbol = this.valueOfFunctions.get(type);
        return irSimpleFunctionSymbol == null ? this.defaultValueOfFunction : irSimpleFunctionSymbol;
    }

    public final IrSimpleFunctionSymbol getEnumValueOfFunction() {
        return this.enumValueOfFunction;
    }

    public final IrSimpleFunctionSymbol getObjectCloneFunction() {
        return this.objectCloneFunction;
    }

    public final IrSimpleFunctionSymbol getRunSuspendFunction() {
        return this.runSuspendFunction;
    }

    public final IrClassSymbol getRepeatableContainer() {
        return this.repeatableContainer;
    }

    public final JavaAnnotations getJavaAnnotations() {
        return this.javaAnnotations;
    }

    /* JADX INFO: compiled from: JvmSymbols.kt */
    @Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010)\u001a\u00020\n2\u0006\u0010*\u001a\u00020\u000eH\u0002J\u001a\u0010+\u001a\u00020\u000e2\u0006\u0010,\u001a\u00020\u00132\b\b\u0002\u0010-\u001a\u00020.H\u0002J\u0018\u0010/\u001a\u00020\u00062\u0006\u00100\u001a\u00020\u000e2\u0006\u00101\u001a\u000202H\u0002R\u001d\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u001d\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00060\u0017¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\bR\u0011\u0010\u001a\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\fR\u0011\u0010\u001c\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\fR\u0011\u0010\u001e\u001a\u00020\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0010R\u0011\u0010 \u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0011\u0010#\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\fR\u0011\u0010%\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b&\u0010\"R\u0011\u0010'\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b(\u0010\"¨\u00063"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmSymbols$JavaAnnotations;", "", "(Lorg/jetbrains/kotlin/backend/jvm/JvmSymbols;)V", "annotationRetentionMap", "", "Lorg/jetbrains/kotlin/descriptors/annotations/KotlinRetention;", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "getAnnotationRetentionMap", "()Ljava/util/Map;", "documentedConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "getDocumentedConstructor", "()Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "elementTypeEnum", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getElementTypeEnum", "()Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "etMethod", "javaLangAnnotation", "Lorg/jetbrains/kotlin/name/FqName;", "javaLangAnnotationPackage", "Lorg/jetbrains/kotlin/ir/declarations/IrPackageFragment;", "jvmTargetMap", "", "Lorg/jetbrains/kotlin/descriptors/annotations/KotlinTarget;", "getJvmTargetMap", "repeatableConstructor", "getRepeatableConstructor", "retentionConstructor", "getRetentionConstructor", "retentionPolicyEnum", "getRetentionPolicyEnum", "rpRuntime", "getRpRuntime", "()Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "targetConstructor", "getTargetConstructor", "typeParameterTarget", "getTypeParameterTarget", "typeUseTarget", "getTypeUseTarget", "buildAnnotationConstructor", "annotationClass", "buildClass", "fqName", "classKind", "Lorg/jetbrains/kotlin/descriptors/ClassKind;", "buildEnumEntry", "enumClass", "entryName", "", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class JavaAnnotations {
        private final Map<KotlinRetention, IrEnumEntry> annotationRetentionMap;
        private final IrConstructor documentedConstructor;
        private final IrClass elementTypeEnum;
        private final IrEnumEntry etMethod;
        private final FqName javaLangAnnotation;
        private final IrPackageFragment javaLangAnnotationPackage;
        private final Map<KotlinTarget, IrEnumEntry> jvmTargetMap;
        private final IrConstructor repeatableConstructor;
        private final IrConstructor retentionConstructor;
        private final IrClass retentionPolicyEnum;
        private final IrEnumEntry rpRuntime;
        private final IrConstructor targetConstructor;
        private final IrEnumEntry typeParameterTarget;
        private final IrEnumEntry typeUseTarget;

        public JavaAnnotations() {
            FqName fqName = new FqName("java.lang.annotation");
            this.javaLangAnnotation = fqName;
            this.javaLangAnnotationPackage = IrExternalPackageFragmentImpl.INSTANCE.createEmptyExternalPackageFragment(JvmSymbols.this.context.getState().getModule(), fqName);
            FqName DOCUMENTED_ANNOTATION = JvmAnnotationNames.DOCUMENTED_ANNOTATION;
            Intrinsics.checkNotNullExpressionValue(DOCUMENTED_ANNOTATION, "DOCUMENTED_ANNOTATION");
            this.documentedConstructor = buildAnnotationConstructor(buildClass$default(this, DOCUMENTED_ANNOTATION, null, 2, null));
            FqName RETENTION_POLICY_ENUM = JvmAnnotationNames.RETENTION_POLICY_ENUM;
            Intrinsics.checkNotNullExpressionValue(RETENTION_POLICY_ENUM, "RETENTION_POLICY_ENUM");
            IrClass irClassBuildClass = buildClass(RETENTION_POLICY_ENUM, ClassKind.ENUM_CLASS);
            this.retentionPolicyEnum = irClassBuildClass;
            IrEnumEntry irEnumEntryBuildEnumEntry = buildEnumEntry(irClassBuildClass, "RUNTIME");
            this.rpRuntime = irEnumEntryBuildEnumEntry;
            FqName RETENTION_ANNOTATION = JvmAnnotationNames.RETENTION_ANNOTATION;
            Intrinsics.checkNotNullExpressionValue(RETENTION_ANNOTATION, "RETENTION_ANNOTATION");
            IrConstructor irConstructorBuildAnnotationConstructor = buildAnnotationConstructor(buildClass$default(this, RETENTION_ANNOTATION, null, 2, null));
            DeclarationBuildersKt.addValueParameter(irConstructorBuildAnnotationConstructor, "value", IrUtilsKt.getDefaultType(irClassBuildClass), IrDeclarationOrigin.IR_EXTERNAL_JAVA_DECLARATION_STUB.INSTANCE);
            this.retentionConstructor = irConstructorBuildAnnotationConstructor;
            FqName ELEMENT_TYPE_ENUM = JvmAnnotationNames.ELEMENT_TYPE_ENUM;
            Intrinsics.checkNotNullExpressionValue(ELEMENT_TYPE_ENUM, "ELEMENT_TYPE_ENUM");
            IrClass irClassBuildClass2 = buildClass(ELEMENT_TYPE_ENUM, ClassKind.ENUM_CLASS);
            this.elementTypeEnum = irClassBuildClass2;
            IrEnumEntry irEnumEntryBuildEnumEntry2 = buildEnumEntry(irClassBuildClass2, "METHOD");
            this.etMethod = irEnumEntryBuildEnumEntry2;
            FqName TARGET_ANNOTATION = JvmAnnotationNames.TARGET_ANNOTATION;
            Intrinsics.checkNotNullExpressionValue(TARGET_ANNOTATION, "TARGET_ANNOTATION");
            IrConstructor irConstructorBuildAnnotationConstructor2 = buildAnnotationConstructor(buildClass$default(this, TARGET_ANNOTATION, null, 2, null));
            DeclarationBuildersKt.addValueParameter(irConstructorBuildAnnotationConstructor2, "value", IrUtilsKt.getDefaultType(irClassBuildClass2), IrDeclarationOrigin.IR_EXTERNAL_JAVA_DECLARATION_STUB.INSTANCE);
            this.targetConstructor = irConstructorBuildAnnotationConstructor2;
            FqName REPEATABLE_ANNOTATION = JvmAnnotationNames.REPEATABLE_ANNOTATION;
            Intrinsics.checkNotNullExpressionValue(REPEATABLE_ANNOTATION, "REPEATABLE_ANNOTATION");
            IrConstructor irConstructorBuildAnnotationConstructor3 = buildAnnotationConstructor(buildClass$default(this, REPEATABLE_ANNOTATION, null, 2, null));
            DeclarationBuildersKt.addValueParameter(irConstructorBuildAnnotationConstructor3, "value", IrTypesKt.getStarProjectedType(JvmSymbols.this.getIrBuiltIns().getKClassClass()), IrDeclarationOrigin.IR_EXTERNAL_JAVA_DECLARATION_STUB.INSTANCE);
            this.repeatableConstructor = irConstructorBuildAnnotationConstructor3;
            this.annotationRetentionMap = MapsKt.mapOf(TuplesKt.to(KotlinRetention.SOURCE, buildEnumEntry(irClassBuildClass, "SOURCE")), TuplesKt.to(KotlinRetention.BINARY, buildEnumEntry(irClassBuildClass, "CLASS")), TuplesKt.to(KotlinRetention.RUNTIME, irEnumEntryBuildEnumEntry));
            this.jvmTargetMap = MapsKt.mutableMapOf(TuplesKt.to(KotlinTarget.CLASS, buildEnumEntry(irClassBuildClass2, "TYPE")), TuplesKt.to(KotlinTarget.ANNOTATION_CLASS, buildEnumEntry(irClassBuildClass2, "ANNOTATION_TYPE")), TuplesKt.to(KotlinTarget.CONSTRUCTOR, buildEnumEntry(irClassBuildClass2, "CONSTRUCTOR")), TuplesKt.to(KotlinTarget.LOCAL_VARIABLE, buildEnumEntry(irClassBuildClass2, "LOCAL_VARIABLE")), TuplesKt.to(KotlinTarget.FUNCTION, irEnumEntryBuildEnumEntry2), TuplesKt.to(KotlinTarget.PROPERTY_GETTER, irEnumEntryBuildEnumEntry2), TuplesKt.to(KotlinTarget.PROPERTY_SETTER, irEnumEntryBuildEnumEntry2), TuplesKt.to(KotlinTarget.FIELD, buildEnumEntry(irClassBuildClass2, "FIELD")), TuplesKt.to(KotlinTarget.BACKING_FIELD, buildEnumEntry(irClassBuildClass2, "FIELD")), TuplesKt.to(KotlinTarget.VALUE_PARAMETER, buildEnumEntry(irClassBuildClass2, "PARAMETER")));
            this.typeParameterTarget = buildEnumEntry(irClassBuildClass2, "TYPE_PARAMETER");
            this.typeUseTarget = buildEnumEntry(irClassBuildClass2, "TYPE_USE");
        }

        static /* synthetic */ IrClass buildClass$default(JavaAnnotations javaAnnotations, FqName fqName, ClassKind classKind, int i, Object obj) {
            if ((i & 2) != 0) {
                classKind = ClassKind.ANNOTATION_CLASS;
            }
            return javaAnnotations.buildClass(fqName, classKind);
        }

        private final IrClass buildClass(FqName fqName, ClassKind classKind) {
            IrFactory irFactory = JvmSymbols.this.context.getIrFactory();
            IrClassBuilder irClassBuilder = new IrClassBuilder();
            if (!Intrinsics.areEqual(fqName.parent(), this.javaLangAnnotation)) {
                throw new IllegalStateException(fqName.toString());
            }
            Name nameShortName = fqName.shortName();
            Intrinsics.checkNotNullExpressionValue(nameShortName, "shortName(...)");
            irClassBuilder.setName(nameShortName);
            irClassBuilder.setKind(classKind);
            IrClass irClassBuildClass = DeclarationBuildersKt.buildClass(irFactory, irClassBuilder);
            irClassBuildClass.setParent(this.javaLangAnnotationPackage);
            IrClass irClass = irClassBuildClass;
            IrUtilsKt.addChild(this.javaLangAnnotationPackage, irClass);
            IrValueParameterBuilder irValueParameterBuilder = new IrValueParameterBuilder();
            Name nameIdentifier = Name.identifier("$this");
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            irValueParameterBuilder.setName(nameIdentifier);
            irValueParameterBuilder.setType(new IrSimpleTypeImpl((IrClassifierSymbol) irClassBuildClass.getSymbol(), false, CollectionsKt.emptyList(), CollectionsKt.emptyList(), (IrTypeAbbreviation) null, 16, (DefaultConstructorMarker) null));
            irClassBuildClass.setThisReceiver(DeclarationBuildersKt.buildValueParameter(irClass.getFactory(), irValueParameterBuilder, irClass));
            return irClassBuildClass;
        }

        private final IrEnumEntry buildEnumEntry(IrClass enumClass, String entryName) {
            IrDeclarationOrigin.IR_EXTERNAL_JAVA_DECLARATION_STUB ir_external_java_declaration_stub = IrDeclarationOrigin.IR_EXTERNAL_JAVA_DECLARATION_STUB.INSTANCE;
            IrEnumEntrySymbolImpl irEnumEntrySymbolImpl = new IrEnumEntrySymbolImpl(null, 1, null);
            Name nameIdentifier = Name.identifier(entryName);
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
            IrEnumEntryImpl irEnumEntryImpl = new IrEnumEntryImpl(-1, -1, ir_external_java_declaration_stub, irEnumEntrySymbolImpl, nameIdentifier, null, 32, null);
            irEnumEntryImpl.setParent(enumClass);
            IrUtilsKt.addChild(enumClass, irEnumEntryImpl);
            return irEnumEntryImpl;
        }

        public final IrConstructor getDocumentedConstructor() {
            return this.documentedConstructor;
        }

        public final IrClass getRetentionPolicyEnum() {
            return this.retentionPolicyEnum;
        }

        public final IrEnumEntry getRpRuntime() {
            return this.rpRuntime;
        }

        public final IrConstructor getRetentionConstructor() {
            return this.retentionConstructor;
        }

        public final IrClass getElementTypeEnum() {
            return this.elementTypeEnum;
        }

        public final IrConstructor getTargetConstructor() {
            return this.targetConstructor;
        }

        public final IrConstructor getRepeatableConstructor() {
            return this.repeatableConstructor;
        }

        public final Map<KotlinRetention, IrEnumEntry> getAnnotationRetentionMap() {
            return this.annotationRetentionMap;
        }

        public final Map<KotlinTarget, IrEnumEntry> getJvmTargetMap() {
            return this.jvmTargetMap;
        }

        public final IrEnumEntry getTypeParameterTarget() {
            return this.typeParameterTarget;
        }

        public final IrEnumEntry getTypeUseTarget() {
            return this.typeUseTarget;
        }

        private final IrConstructor buildAnnotationConstructor(IrClass annotationClass) {
            IrFactory factory = annotationClass.getFactory();
            IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
            irFunctionBuilder.setPrimary(true);
            irFunctionBuilder.setReturnType(IrUtilsKt.getDefaultType(annotationClass));
            IrConstructor irConstructorBuildConstructor = DeclarationBuildersKt.buildConstructor(factory, irFunctionBuilder);
            annotationClass.getDeclarations().add(irConstructorBuildConstructor);
            irConstructorBuildConstructor.setParent(annotationClass);
            return irConstructorBuildConstructor;
        }
    }

    /* JADX INFO: compiled from: JvmSymbols.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u000e\u0010\t\u001a\u00020\nX\u0086T¢\u0006\u0002\n\u0000R\u0011\u0010\u000b\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmSymbols$Companion;", "", "()V", "FLEXIBLE_MUTABILITY_ANNOTATION_FQ_NAME", "Lorg/jetbrains/kotlin/name/FqName;", "getFLEXIBLE_MUTABILITY_ANNOTATION_FQ_NAME", "()Lorg/jetbrains/kotlin/name/FqName;", "FLEXIBLE_NULLABILITY_ANNOTATION_FQ_NAME", "getFLEXIBLE_NULLABILITY_ANNOTATION_FQ_NAME", "INTRINSICS_CLASS_NAME", "", "RAW_TYPE_ANNOTATION_FQ_NAME", "getRAW_TYPE_ANNOTATION_FQ_NAME", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final FqName getFLEXIBLE_NULLABILITY_ANNOTATION_FQ_NAME() {
            return JvmSymbols.FLEXIBLE_NULLABILITY_ANNOTATION_FQ_NAME;
        }

        public final FqName getFLEXIBLE_MUTABILITY_ANNOTATION_FQ_NAME() {
            return JvmSymbols.FLEXIBLE_MUTABILITY_ANNOTATION_FQ_NAME;
        }

        public final FqName getRAW_TYPE_ANNOTATION_FQ_NAME() {
            return JvmSymbols.RAW_TYPE_ANNOTATION_FQ_NAME;
        }
    }

    static {
        FqName fqNameChild = IrBuiltIns.INSTANCE.getKOTLIN_INTERNAL_IR_FQN().child(Name.identifier("FlexibleNullability"));
        Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
        FLEXIBLE_NULLABILITY_ANNOTATION_FQ_NAME = fqNameChild;
        FqName fqNameChild2 = IrBuiltIns.INSTANCE.getKOTLIN_INTERNAL_IR_FQN().child(Name.identifier("FlexibleMutability"));
        Intrinsics.checkNotNullExpressionValue(fqNameChild2, "child(...)");
        FLEXIBLE_MUTABILITY_ANNOTATION_FQ_NAME = fqNameChild2;
        FqName fqNameChild3 = IrBuiltIns.INSTANCE.getKOTLIN_INTERNAL_IR_FQN().child(Name.identifier("RawType"));
        Intrinsics.checkNotNullExpressionValue(fqNameChild3, "child(...)");
        RAW_TYPE_ANNOTATION_FQ_NAME = fqNameChild3;
    }
}
