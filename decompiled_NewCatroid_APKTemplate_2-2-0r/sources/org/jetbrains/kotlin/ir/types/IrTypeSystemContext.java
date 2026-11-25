package org.jetbrains.kotlin.ir.types;

import androidx.core.app.NotificationCompat;
import com.reandroid.arsc.container.SpecTypePair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.NotImplementedError;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.InlineClassRepresentation;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ValueClassRepresentation;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.declarations.IrAnnotationContainer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.symbols.FqNameEqualityChecker;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.types.impl.IrCapturedType;
import org.jetbrains.kotlin.ir.types.impl.IrDynamicTypeImpl;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImpl;
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImplKt;
import org.jetbrains.kotlin.ir.types.impl.IrStarProjectionImpl;
import org.jetbrains.kotlin.ir.types.impl.IrTypeProjectionImpl;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.types.TypeCheckerState;
import org.jetbrains.kotlin.types.TypeSystemCommonBackendContext;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.model.AnnotationMarker;
import org.jetbrains.kotlin.types.model.CaptureStatus;
import org.jetbrains.kotlin.types.model.CapturedTypeConstructorMarker;
import org.jetbrains.kotlin.types.model.CapturedTypeMarker;
import org.jetbrains.kotlin.types.model.DefinitelyNotNullTypeMarker;
import org.jetbrains.kotlin.types.model.FlexibleTypeMarker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentMarker;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;
import org.jetbrains.kotlin.types.model.TypeSystemCommonSuperTypesContext;
import org.jetbrains.kotlin.types.model.TypeSystemContext;
import org.jetbrains.kotlin.types.model.TypeSystemContextKt;
import org.jetbrains.kotlin.types.model.TypeVariableTypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeVariance;

/* JADX INFO: compiled from: IrTypeSystemContext.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000¤\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b.\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u001e\n\u0002\b\u0006\bf\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003J\b\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0012H\u0016J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u001a\u0010\u0018\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u0012\u0010\u001c\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u0019\u001a\u00020\u0017H\u0016J\b\u0010\u001d\u001a\u00020\u001eH\u0016J\u0010\u0010\u001f\u001a\u00020\u00152\u0006\u0010 \u001a\u00020!H\u0016J\u0018\u0010\"\u001a\u00020\u00172\u0006\u0010#\u001a\u00020\u00152\u0006\u0010$\u001a\u00020\u0015H\u0016J>\u0010%\u001a\u00020\u00152\u0006\u0010&\u001a\u00020\u00122\f\u0010'\u001a\b\u0012\u0004\u0012\u00020)0(2\u0006\u0010*\u001a\u00020\u00102\u0006\u0010+\u001a\u00020\u00102\u000e\u0010,\u001a\n\u0012\u0004\u0012\u00020-\u0018\u00010(H\u0016J\u0010\u0010.\u001a\u00020/2\u0006\u0010\b\u001a\u00020\tH\u0016J\u0018\u00100\u001a\u00020)2\u0006\u0010\u0019\u001a\u00020\u00172\u0006\u00101\u001a\u000202H\u0016J\u0010\u00103\u001a\u00020\u00172\u0006\u0010&\u001a\u00020\u0012H\u0016J\u0018\u00104\u001a\u0004\u0018\u00010\u00152\f\u00105\u001a\b\u0012\u0004\u0012\u00020\u00150(H\u0016J\u0016\u00106\u001a\b\u0012\u0004\u0012\u0002070(2\u0006\u00108\u001a\u00020\tH\u0002J\u0016\u00109\u001a\b\u0012\u0004\u0012\u00020:0(2\u0006\u0010;\u001a\u00020\u0012H\u0002J\u0016\u0010<\u001a\u00020\u00172\f\u0010=\u001a\b\u0012\u0004\u0012\u00020\u00170(H\u0016J\u0016\u0010<\u001a\u00020\u00152\f\u0010=\u001a\b\u0012\u0004\u0012\u00020\u00150(H\u0016J\u0018\u0010>\u001a\u00020?2\u0006\u0010@\u001a\u00020\u00102\u0006\u0010A\u001a\u00020\u0010H\u0016J\b\u0010B\u001a\u00020\u000eH\u0016J\b\u0010C\u001a\u00020\u000eH\u0016J\b\u0010D\u001a\u00020\u000eH\u0016J\u0010\u0010E\u001a\u00020F2\u0006\u0010\u0019\u001a\u00020\u0015H\u0016J\u001c\u0010G\u001a\u00020\u001e2\u0012\u0010H\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00170IH\u0016J\u001c\u0010J\u001a\b\u0012\u0004\u0012\u00020-0(2\f\u0010=\u001a\b\u0012\u0004\u0012\u00020\u00170(H\u0016J\f\u0010K\u001a\u00020L*\u00020\u0017H\u0016J\f\u0010M\u001a\u00020\u000e*\u00020\u0015H\u0016J\u000e\u0010N\u001a\u0004\u0018\u00010O*\u00020\u0015H\u0016J\u000e\u0010P\u001a\u0004\u0018\u00010Q*\u00020\u0015H\u0016J\u000e\u0010R\u001a\u0004\u0018\u00010S*\u00020TH\u0016J\u000e\u0010U\u001a\u0004\u0018\u00010T*\u00020\u0017H\u0016J\u000e\u0010V\u001a\u0004\u0018\u00010\u0015*\u00020\u0017H\u0016J\f\u0010W\u001a\u00020X*\u00020\u0017H\u0016J\f\u0010Y\u001a\u00020\u0010*\u00020\u0017H\u0016J\f\u0010Z\u001a\u00020\u001b*\u00020OH\u0016J\u0014\u0010[\u001a\u00020\u0010*\u00020\u00172\u0006\u0010&\u001a\u00020\u0012H\u0002J\f\u0010\\\u001a\u00020]*\u000202H\u0002J\u0016\u0010^\u001a\u0004\u0018\u00010_*\u00020\u00172\u0006\u0010`\u001a\u00020aH\u0016J\u0014\u0010b\u001a\u00020)*\u00020\u00172\u0006\u0010c\u001a\u00020LH\u0016J\u0012\u0010d\u001a\b\u0012\u0004\u0012\u00020)0(*\u00020\u0017H\u0016J\u0012\u0010e\u001a\b\u0012\u0004\u0012\u00020-0(*\u00020\u0017H\u0016J\u000e\u0010f\u001a\u0004\u0018\u00010g*\u00020\u0012H\u0016J\u0012\u0010h\u001a\b\u0012\u0004\u0012\u00020-0(*\u00020\u0017H\u0016J\f\u0010i\u001a\u00020j*\u00020\tH\u0016J\u000e\u0010k\u001a\u0004\u0018\u00010!*\u00020\u0012H\u0002J\u0014\u0010l\u001a\u00020m*\u00020\u00122\u0006\u0010c\u001a\u00020LH\u0016J\u0012\u0010n\u001a\b\u0012\u0004\u0012\u00020m0(*\u00020\u0012H\u0016J\u000e\u0010o\u001a\u0004\u0018\u00010p*\u00020\u0012H\u0016J\u000e\u0010q\u001a\u0004\u0018\u00010p*\u00020\u0012H\u0016J\f\u0010r\u001a\u00020\u0017*\u00020\tH\u0016J\u000e\u0010s\u001a\u0004\u0018\u00010\u0017*\u00020\u0017H\u0016J\f\u0010t\u001a\u000207*\u00020)H\u0016J\f\u0010u\u001a\u00020m*\u00020\tH\u0016J\u000e\u0010v\u001a\u0004\u0018\u00010\t*\u00020\u0012H\u0016J\u000e\u0010w\u001a\u0004\u0018\u00010\u0017*\u00020\u0017H\u0016J\u0014\u0010x\u001a\u00020\u0017*\u00020\t2\u0006\u0010c\u001a\u00020LH\u0016J\u0012\u0010y\u001a\b\u0012\u0004\u0012\u00020\u00170(*\u00020\tH\u0016J \u0010z\u001a\u0016\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020j\u0012\u0004\u0012\u00020\u00150{\u0018\u00010(*\u00020\u0012H\u0016J\f\u0010|\u001a\u000202*\u00020)H\u0016J\f\u0010|\u001a\u000202*\u00020\tH\u0016J\u0014\u0010}\u001a\u00020\u0010*\u00020\u00172\u0006\u0010`\u001a\u00020aH\u0016J\f\u0010~\u001a\u00020\u0010*\u00020\u0017H\u0016J\u0017\u0010\u007f\u001a\u00020\u0010*\u00020\t2\t\u0010\u0080\u0001\u001a\u0004\u0018\u00010\u0012H\u0016J\r\u0010\u0081\u0001\u001a\u00020\u0010*\u00020\u0012H\u0016J\r\u0010\u0082\u0001\u001a\u00020\u0010*\u00020\u0012H\u0016J\r\u0010\u0083\u0001\u001a\u00020\u0010*\u00020\u0017H\u0016J\r\u0010\u0084\u0001\u001a\u00020\u0010*\u00020\u0012H\u0016J\r\u0010\u0085\u0001\u001a\u00020\u0010*\u00020\u0012H\u0016J\r\u0010\u0086\u0001\u001a\u00020\u0010*\u00020\u0012H\u0016J\r\u0010\u0087\u0001\u001a\u00020\u0010*\u00020\u0017H\u0016J\r\u0010\u0087\u0001\u001a\u00020\u0010*\u00020\u0012H\u0016J\f\u0010+\u001a\u00020\u0010*\u00020\u0015H\u0016J\r\u0010\u0088\u0001\u001a\u00020\u0010*\u00020\u0012H\u0016J\r\u0010\u0089\u0001\u001a\u00020\u0010*\u00020\u0012H\u0016J\r\u0010\u008a\u0001\u001a\u00020\u0010*\u00020\u0012H\u0016J\r\u0010\u008b\u0001\u001a\u00020\u0010*\u00020\u0012H\u0016J\r\u0010\u008c\u0001\u001a\u00020\u0010*\u00020\u0012H\u0016J\r\u0010\u008d\u0001\u001a\u00020\u0010*\u00020\u0012H\u0016J\r\u0010\u008e\u0001\u001a\u00020\u0010*\u00020\u0012H\u0016J\r\u0010\u008f\u0001\u001a\u00020\u0010*\u00020\u0017H\u0016J\r\u0010\u0090\u0001\u001a\u00020\u0010*\u00020\u0012H\u0016J\r\u0010\u0091\u0001\u001a\u00020\u0010*\u00020\u0012H\u0016J\r\u0010\u0092\u0001\u001a\u00020\u0010*\u00020\u0017H\u0016J\r\u0010\u0092\u0001\u001a\u00020\u0010*\u00020\u0015H\u0016J\r\u0010\u0093\u0001\u001a\u00020\u0010*\u00020\u0012H\u0016J\r\u0010\u0094\u0001\u001a\u00020\u0010*\u00020\u0012H\u0016J\r\u0010\u0095\u0001\u001a\u00020\u0010*\u00020\u0017H\u0016J\r\u0010\u0096\u0001\u001a\u00020\u0010*\u00020OH\u0016J\r\u0010\u0097\u0001\u001a\u00020\u0010*\u00020\u0015H\u0016J\r\u0010\u0098\u0001\u001a\u00020\u0010*\u00020OH\u0016J\r\u0010\u0099\u0001\u001a\u00020\u0010*\u00020\u0017H\u0016J\r\u0010\u009a\u0001\u001a\u00020\u0010*\u00020\tH\u0016J\r\u0010\u009b\u0001\u001a\u00020\u0010*\u00020\u0015H\u0016J\r\u0010\u009c\u0001\u001a\u00020\u0010*\u00020)H\u0016J\r\u0010\u009d\u0001\u001a\u00020\u0010*\u00020\u0015H\u0016J\r\u0010\u009e\u0001\u001a\u00020\u0010*\u00020\u0015H\u0016J\r\u0010\u009f\u0001\u001a\u00020\u0010*\u00020\u0015H\u0016J\r\u0010 \u0001\u001a\u00020\u0010*\u00020\u0012H\u0016J\r\u0010¡\u0001\u001a\u00020\u0010*\u00020\u0017H\u0016J\r\u0010¢\u0001\u001a\u00020\u0010*\u00020\u0012H\u0016J\r\u0010£\u0001\u001a\u00020\u0010*\u00020\u0017H\u0016J\f\u0010#\u001a\u00020\u0015*\u00020TH\u0016J\u000f\u0010¤\u0001\u001a\u0004\u0018\u00010\u0017*\u00020OH\u0016J\r\u0010¥\u0001\u001a\u00020\u0017*\u00020\u0017H\u0016J\r\u0010¦\u0001\u001a\u00020\u0015*\u00020\u0015H\u0016J\r\u0010§\u0001\u001a\u00020\u0015*\u00020QH\u0016J\r\u0010¨\u0001\u001a\u00020L*\u00020\u0012H\u0016J\u0014\u0010©\u0001\u001a\t\u0012\u0004\u0012\u0002070ª\u0001*\u00020\u0015H\u0016J\u000e\u0010«\u0001\u001a\u00020)*\u00030¬\u0001H\u0016J\u001c\u0010\u00ad\u0001\u001a\u00020\u0017*\u00020\u00172\r\u0010®\u0001\u001a\b\u0012\u0004\u0012\u00020-0(H\u0016J\u0016\u0010¯\u0001\u001a\u00020)*\u00020)2\u0007\u0010°\u0001\u001a\u00020\u0017H\u0016J\u0015\u0010±\u0001\u001a\u00020\u0017*\u00020\u001e2\u0006\u0010\u0019\u001a\u00020\u0017H\u0016J\u0014\u0010²\u0001\u001a\t\u0012\u0004\u0012\u00020\u00170³\u0001*\u00020\u0012H\u0016J\r\u0010´\u0001\u001a\u00020\u0015*\u00020\u0012H\u0016J\r\u0010;\u001a\u00030¬\u0001*\u00020OH\u0016J\f\u0010;\u001a\u00020\u0012*\u00020\u0015H\u0016J\r\u0010µ\u0001\u001a\u00020L*\u00020\u0015H\u0016J\r\u0010¶\u0001\u001a\u00020\u0012*\u00020\u0012H\u0016J\f\u0010$\u001a\u00020\u0015*\u00020TH\u0016J\r\u0010·\u0001\u001a\u00020L*\u00020\tH\u0016J\u0015\u0010¸\u0001\u001a\u00020\u0017*\u00020\u00172\u0006\u0010*\u001a\u00020\u0010H\u0016J\u0015\u0010¸\u0001\u001a\u00020\u0015*\u00020\u00152\u0006\u0010*\u001a\u00020\u0010H\u0016R\u0012\u0010\u0004\u001a\u00020\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\u0004\u0018\u00010\t*\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006¹\u0001"}, d2 = {"Lorg/jetbrains/kotlin/ir/types/IrTypeSystemContext;", "Lorg/jetbrains/kotlin/types/model/TypeSystemContext;", "Lorg/jetbrains/kotlin/types/model/TypeSystemCommonSuperTypesContext;", "Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContext;", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "getIrBuiltIns", "()Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "typeParameter", "Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "Lorg/jetbrains/kotlin/types/model/TypeVariableTypeConstructorMarker;", "getTypeParameter", "(Lorg/jetbrains/kotlin/types/model/TypeVariableTypeConstructorMarker;)Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", SchemaSymbols.ATTVAL_ANYTYPE, "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "areEqualTypeConstructors", "", "c1", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "c2", "arrayType", "Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", "componentType", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "captureFromArguments", ModuleXmlParser.TYPE, NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/types/model/CaptureStatus;", "captureFromExpression", "createEmptySubstitutor", "Lorg/jetbrains/kotlin/types/model/TypeSubstitutorMarker;", "createErrorType", "debugName", "", "createFlexibleType", "lowerBound", "upperBound", "createSimpleType", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "arguments", "", "Lorg/jetbrains/kotlin/types/model/TypeArgumentMarker;", "nullable", "isExtensionFunction", "attributes", "Lorg/jetbrains/kotlin/types/model/AnnotationMarker;", "createStarProjection", "Lorg/jetbrains/kotlin/ir/types/impl/IrStarProjectionImpl;", "createTypeArgument", "variance", "Lorg/jetbrains/kotlin/types/model/TypeVariance;", "createUninferredType", "findCommonIntegerLiteralTypesSuperType", "explicitSupertypes", "getSuperTypes", "Lorg/jetbrains/kotlin/ir/types/IrType;", "typeParameterMarker", "getTypeParameters", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "typeConstructor", "intersectTypes", SpecTypePair.NAME_types, "newTypeCheckerState", "Lorg/jetbrains/kotlin/types/TypeCheckerState;", "errorTypesEqualToAnything", "stubTypesEqualToAnything", "nothingType", "nullableAnyType", "nullableNothingType", "substitutionSupertypePolicy", "Lorg/jetbrains/kotlin/types/TypeCheckerState$SupertypesPolicy;", "typeSubstitutorByTypeConstructor", "map", "", "unionTypeAttributes", "argumentsCount", "", "asArgumentList", "asCapturedType", "Lorg/jetbrains/kotlin/types/model/CapturedTypeMarker;", "asDefinitelyNotNullType", "Lorg/jetbrains/kotlin/types/model/DefinitelyNotNullTypeMarker;", "asDynamicType", "Lorg/jetbrains/kotlin/ir/types/IrDynamicType;", "Lorg/jetbrains/kotlin/types/model/FlexibleTypeMarker;", "asFlexibleType", "asSimpleType", "asTypeArgument", "Lorg/jetbrains/kotlin/ir/types/IrTypeArgument;", "canHaveUndefinedNullability", "captureStatus", "containsTypeConstructor", "convertVariance", "Lorg/jetbrains/kotlin/types/Variance;", "getAnnotationFirstArgumentValue", "", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "getArgument", "index", "getArguments", "getAttributes", "getClassFqNameUnsafe", "Lorg/jetbrains/kotlin/name/FqNameUnsafe;", "getCustomAttributes", "getName", "Lorg/jetbrains/kotlin/name/Name;", "getNameForClassUnderKotlinPackage", "getParameter", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "getParameters", "getPrimitiveArrayType", "Lorg/jetbrains/kotlin/builtins/PrimitiveType;", "getPrimitiveType", "getRepresentativeUpperBound", "getSubstitutedUnderlyingType", "getType", "getTypeConstructor", "getTypeParameterClassifier", "getUnsubstitutedUnderlyingType", "getUpperBound", "getUpperBounds", "getValueClassProperties", "Lkotlin/Pair;", "getVariance", "hasAnnotation", "hasCustomAttributes", "hasRecursiveBounds", "selfConstructor", "isAnonymous", "isAnyConstructor", "isArrayOrNullableArray", "isClassTypeConstructor", "isCommonFinalClassConstructor", "isDenotable", "isError", "isFinalClassOrEnumEntryOrAnnotationClassConstructor", "isInlineClass", "isInnerClass", "isIntegerConstantOperatorTypeConstructor", "isIntegerLiteralConstantTypeConstructor", "isIntegerLiteralTypeConstructor", "isInterface", "isInterfaceOrAnnotationClass", "isIntersection", "isLocalType", "isMarkedNullable", "isMultiFieldValueClass", "isNothingConstructor", "isNullableType", "isOldCapturedType", "isPrimitiveType", "isProjectionNotNull", "isRawType", "isReified", "isSingleClassifierType", "isStarProjection", "isStubType", "isStubTypeForBuilderInference", "isStubTypeForVariableInSubtyping", "isTypeParameterTypeConstructor", "isTypeVariableType", "isUnderKotlinPackage", "isUninferredParameter", "lowerType", "makeDefinitelyNotNullOrNotNull", "makeSimpleTypeDefinitelyNotNullOrNotNull", "original", "parametersCount", "possibleIntegerTypes", "", "projection", "Lorg/jetbrains/kotlin/types/model/CapturedTypeConstructorMarker;", "replaceCustomAttributes", "newAttributes", "replaceType", "newType", "safeSubstitute", "supertypes", "", "toErrorType", "typeDepth", "unwrapStubTypeVariableConstructor", "upperBoundCount", "withNullability", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IrTypeSystemContext extends TypeSystemCommonBackendContext, TypeSystemCommonSuperTypesContext, TypeSystemContext {

    /* JADX INFO: compiled from: IrTypeSystemContext.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[TypeVariance.values().length];
            try {
                iArr[TypeVariance.INV.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[TypeVariance.IN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[TypeVariance.OUT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    IrSimpleType anyType();

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean areEqualTypeConstructors(TypeConstructorMarker c1, TypeConstructorMarker c2);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    int argumentsCount(KotlinTypeMarker kotlinTypeMarker);

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    SimpleTypeMarker arrayType(KotlinTypeMarker componentType);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    IrSimpleType asArgumentList(SimpleTypeMarker simpleTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    CapturedTypeMarker asCapturedType(SimpleTypeMarker simpleTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    DefinitelyNotNullTypeMarker asDefinitelyNotNullType(SimpleTypeMarker simpleTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    IrDynamicType asDynamicType(FlexibleTypeMarker flexibleTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    FlexibleTypeMarker asFlexibleType(KotlinTypeMarker kotlinTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    SimpleTypeMarker asSimpleType(KotlinTypeMarker kotlinTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    IrTypeArgument asTypeArgument(KotlinTypeMarker kotlinTypeMarker);

    boolean canHaveUndefinedNullability(KotlinTypeMarker kotlinTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    SimpleTypeMarker captureFromArguments(SimpleTypeMarker type, CaptureStatus status);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    KotlinTypeMarker captureFromExpression(KotlinTypeMarker type);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    CaptureStatus captureStatus(CapturedTypeMarker capturedTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    TypeSubstitutorMarker createEmptySubstitutor();

    SimpleTypeMarker createErrorType(String debugName);

    KotlinTypeMarker createFlexibleType(SimpleTypeMarker lowerBound, SimpleTypeMarker upperBound);

    SimpleTypeMarker createSimpleType(TypeConstructorMarker constructor, List<? extends TypeArgumentMarker> arguments, boolean nullable, boolean isExtensionFunction, List<? extends AnnotationMarker> attributes);

    IrStarProjectionImpl createStarProjection(TypeParameterMarker typeParameter);

    TypeArgumentMarker createTypeArgument(KotlinTypeMarker type, TypeVariance variance);

    KotlinTypeMarker createUninferredType(TypeConstructorMarker constructor);

    SimpleTypeMarker findCommonIntegerLiteralTypesSuperType(List<? extends SimpleTypeMarker> explicitSupertypes);

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    Object getAnnotationFirstArgumentValue(KotlinTypeMarker kotlinTypeMarker, FqName fqName);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    TypeArgumentMarker getArgument(KotlinTypeMarker kotlinTypeMarker, int i);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    List<TypeArgumentMarker> getArguments(KotlinTypeMarker kotlinTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    List<AnnotationMarker> getAttributes(KotlinTypeMarker kotlinTypeMarker);

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    FqNameUnsafe getClassFqNameUnsafe(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    List<AnnotationMarker> getCustomAttributes(KotlinTypeMarker kotlinTypeMarker);

    IrBuiltIns getIrBuiltIns();

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    Name getName(TypeParameterMarker typeParameterMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    IrTypeParameterSymbol getParameter(TypeConstructorMarker typeConstructorMarker, int i);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    List<IrTypeParameterSymbol> getParameters(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    PrimitiveType getPrimitiveArrayType(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    PrimitiveType getPrimitiveType(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    KotlinTypeMarker getRepresentativeUpperBound(TypeParameterMarker typeParameterMarker);

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    KotlinTypeMarker getSubstitutedUnderlyingType(KotlinTypeMarker kotlinTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    IrType getType(TypeArgumentMarker typeArgumentMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    IrTypeParameterSymbol getTypeConstructor(TypeParameterMarker typeParameterMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    TypeParameterMarker getTypeParameter(TypeVariableTypeConstructorMarker typeVariableTypeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    TypeParameterMarker getTypeParameterClassifier(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    KotlinTypeMarker getUnsubstitutedUnderlyingType(KotlinTypeMarker kotlinTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    KotlinTypeMarker getUpperBound(TypeParameterMarker typeParameterMarker, int i);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    List<KotlinTypeMarker> getUpperBounds(TypeParameterMarker typeParameterMarker);

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    List<Pair<Name, SimpleTypeMarker>> getValueClassProperties(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    TypeVariance getVariance(TypeArgumentMarker typeArgumentMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    TypeVariance getVariance(TypeParameterMarker typeParameterMarker);

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    boolean hasAnnotation(KotlinTypeMarker kotlinTypeMarker, FqName fqName);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean hasCustomAttributes(KotlinTypeMarker kotlinTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean hasRecursiveBounds(TypeParameterMarker typeParameterMarker, TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    KotlinTypeMarker intersectTypes(List<? extends KotlinTypeMarker> types);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    /* JADX INFO: renamed from: intersectTypes */
    SimpleTypeMarker mo6107intersectTypes(List<? extends SimpleTypeMarker> types);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isAnonymous(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isAnyConstructor(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    boolean isArrayOrNullableArray(KotlinTypeMarker kotlinTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isClassTypeConstructor(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isCommonFinalClassConstructor(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isDenotable(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isError(KotlinTypeMarker kotlinTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isError(TypeConstructorMarker typeConstructorMarker);

    boolean isExtensionFunction(SimpleTypeMarker simpleTypeMarker);

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    boolean isFinalClassOrEnumEntryOrAnnotationClassConstructor(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    boolean isInlineClass(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    boolean isInnerClass(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isIntegerConstantOperatorTypeConstructor(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isIntegerLiteralConstantTypeConstructor(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isIntegerLiteralTypeConstructor(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isInterface(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    boolean isInterfaceOrAnnotationClass(KotlinTypeMarker kotlinTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isIntersection(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isLocalType(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isMarkedNullable(KotlinTypeMarker kotlinTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isMarkedNullable(SimpleTypeMarker simpleTypeMarker);

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    boolean isMultiFieldValueClass(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isNothingConstructor(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isNullableType(KotlinTypeMarker kotlinTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isOldCapturedType(CapturedTypeMarker capturedTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isPrimitiveType(SimpleTypeMarker simpleTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isProjectionNotNull(CapturedTypeMarker capturedTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isRawType(KotlinTypeMarker kotlinTypeMarker);

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    boolean isReified(TypeParameterMarker typeParameterMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isSingleClassifierType(SimpleTypeMarker simpleTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isStarProjection(TypeArgumentMarker typeArgumentMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isStubType(SimpleTypeMarker simpleTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isStubTypeForBuilderInference(SimpleTypeMarker simpleTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isStubTypeForVariableInSubtyping(SimpleTypeMarker simpleTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isTypeParameterTypeConstructor(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isTypeVariableType(KotlinTypeMarker kotlinTypeMarker);

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext
    boolean isUnderKotlinPackage(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    boolean isUninferredParameter(KotlinTypeMarker kotlinTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    SimpleTypeMarker lowerBound(FlexibleTypeMarker flexibleTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    KotlinTypeMarker lowerType(CapturedTypeMarker capturedTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    KotlinTypeMarker makeDefinitelyNotNullOrNotNull(KotlinTypeMarker kotlinTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    SimpleTypeMarker makeSimpleTypeDefinitelyNotNullOrNotNull(SimpleTypeMarker simpleTypeMarker);

    TypeCheckerState newTypeCheckerState(boolean errorTypesEqualToAnything, boolean stubTypesEqualToAnything);

    IrSimpleType nothingType();

    @Override // org.jetbrains.kotlin.types.TypeSystemCommonBackendContext, org.jetbrains.kotlin.types.model.TypeSystemBuiltInsContext
    IrSimpleType nullableAnyType();

    IrSimpleType nullableNothingType();

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    SimpleTypeMarker original(DefinitelyNotNullTypeMarker definitelyNotNullTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    int parametersCount(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    Set<IrType> possibleIntegerTypes(SimpleTypeMarker simpleTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    TypeArgumentMarker projection(CapturedTypeConstructorMarker capturedTypeConstructorMarker);

    KotlinTypeMarker replaceCustomAttributes(KotlinTypeMarker kotlinTypeMarker, List<? extends AnnotationMarker> list);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    TypeArgumentMarker replaceType(TypeArgumentMarker typeArgumentMarker, KotlinTypeMarker kotlinTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    KotlinTypeMarker safeSubstitute(TypeSubstitutorMarker typeSubstitutorMarker, KotlinTypeMarker kotlinTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    TypeCheckerState.SupertypesPolicy substitutionSupertypePolicy(SimpleTypeMarker type);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    Collection<KotlinTypeMarker> supertypes(TypeConstructorMarker typeConstructorMarker);

    SimpleTypeMarker toErrorType(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    CapturedTypeConstructorMarker typeConstructor(CapturedTypeMarker capturedTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    TypeConstructorMarker typeConstructor(SimpleTypeMarker simpleTypeMarker);

    int typeDepth(SimpleTypeMarker simpleTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    TypeSubstitutorMarker typeSubstitutorByTypeConstructor(Map<TypeConstructorMarker, ? extends KotlinTypeMarker> map);

    List<AnnotationMarker> unionTypeAttributes(List<? extends KotlinTypeMarker> types);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    TypeConstructorMarker unwrapStubTypeVariableConstructor(TypeConstructorMarker typeConstructorMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    SimpleTypeMarker upperBound(FlexibleTypeMarker flexibleTypeMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    int upperBoundCount(TypeParameterMarker typeParameterMarker);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    KotlinTypeMarker withNullability(KotlinTypeMarker kotlinTypeMarker, boolean z);

    @Override // org.jetbrains.kotlin.types.model.TypeSystemContext
    SimpleTypeMarker withNullability(SimpleTypeMarker simpleTypeMarker, boolean z);

    /* JADX INFO: compiled from: IrTypeSystemContext.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        public static DefinitelyNotNullTypeMarker asDefinitelyNotNullType(IrTypeSystemContext irTypeSystemContext, SimpleTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return null;
        }

        public static boolean hasCustomAttributes(IrTypeSystemContext irTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return false;
        }

        public static boolean isIntegerConstantOperatorTypeConstructor(IrTypeSystemContext irTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return false;
        }

        public static boolean isIntegerLiteralConstantTypeConstructor(IrTypeSystemContext irTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return false;
        }

        public static boolean isIntegerLiteralTypeConstructor(IrTypeSystemContext irTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return false;
        }

        public static boolean isIntersection(IrTypeSystemContext irTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return false;
        }

        public static boolean isOldCapturedType(IrTypeSystemContext irTypeSystemContext, CapturedTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return false;
        }

        public static boolean isProjectionNotNull(IrTypeSystemContext irTypeSystemContext, CapturedTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return false;
        }

        public static boolean isRawType(IrTypeSystemContext irTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return false;
        }

        public static boolean isSingleClassifierType(IrTypeSystemContext irTypeSystemContext, SimpleTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return true;
        }

        public static boolean isStubType(IrTypeSystemContext irTypeSystemContext, SimpleTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return false;
        }

        public static boolean isStubTypeForBuilderInference(IrTypeSystemContext irTypeSystemContext, SimpleTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return false;
        }

        public static boolean isStubTypeForVariableInSubtyping(IrTypeSystemContext irTypeSystemContext, SimpleTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return false;
        }

        public static boolean isTypeVariableType(IrTypeSystemContext irTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return false;
        }

        public static boolean isUninferredParameter(IrTypeSystemContext irTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return false;
        }

        public static KotlinTypeMarker replaceCustomAttributes(IrTypeSystemContext irTypeSystemContext, KotlinTypeMarker receiver, List<? extends AnnotationMarker> newAttributes) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            Intrinsics.checkNotNullParameter(newAttributes, "newAttributes");
            return receiver;
        }

        public static TypeConstructorMarker unwrapStubTypeVariableConstructor(IrTypeSystemContext irTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return receiver;
        }

        public static SimpleTypeMarker asSimpleType(IrTypeSystemContext irTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (receiver instanceof SimpleTypeMarker) {
                return (SimpleTypeMarker) receiver;
            }
            return null;
        }

        public static FlexibleTypeMarker asFlexibleType(IrTypeSystemContext irTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (receiver instanceof FlexibleTypeMarker) {
                return (FlexibleTypeMarker) receiver;
            }
            return null;
        }

        public static boolean isError(IrTypeSystemContext irTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return receiver instanceof IrErrorType;
        }

        public static IrDynamicType asDynamicType(IrTypeSystemContext irTypeSystemContext, FlexibleTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (receiver instanceof IrDynamicType) {
                return (IrDynamicType) receiver;
            }
            return null;
        }

        public static SimpleTypeMarker upperBound(IrTypeSystemContext irTypeSystemContext, FlexibleTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof IrDynamicType)) {
                throw new IllegalStateException(("Unexpected flexible type " + receiver.getClass().getSimpleName() + ": " + receiver).toString());
            }
            IrType anyNType = irTypeSystemContext.getIrBuiltIns().getAnyNType();
            Intrinsics.checkNotNull(anyNType, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
            return (IrSimpleType) anyNType;
        }

        public static SimpleTypeMarker lowerBound(IrTypeSystemContext irTypeSystemContext, FlexibleTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof IrDynamicType)) {
                throw new IllegalStateException(("Unexpected flexible type " + receiver.getClass().getSimpleName() + ": " + receiver).toString());
            }
            IrType nothingType = irTypeSystemContext.getIrBuiltIns().getNothingType();
            Intrinsics.checkNotNull(nothingType, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
            return (IrSimpleType) nothingType;
        }

        public static CapturedTypeMarker asCapturedType(IrTypeSystemContext irTypeSystemContext, SimpleTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return receiver instanceof IrCapturedType ? (IrCapturedType) receiver : null;
        }

        public static boolean isMarkedNullable(IrTypeSystemContext irTypeSystemContext, SimpleTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return (receiver instanceof IrSimpleType) && IrTypePredicatesKt.isMarkedNullable((IrSimpleType) receiver);
        }

        public static boolean isMarkedNullable(IrTypeSystemContext irTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return (receiver instanceof IrSimpleType) && IrTypePredicatesKt.isMarkedNullable((IrSimpleType) receiver);
        }

        public static SimpleTypeMarker withNullability(IrTypeSystemContext irTypeSystemContext, SimpleTypeMarker receiver, boolean z) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            IrSimpleType irSimpleType = (IrSimpleType) receiver;
            IrType irTypeMakeNullable = z ? IrTypesKt.makeNullable(irSimpleType) : IrTypesKt.makeNotNull(irSimpleType);
            Intrinsics.checkNotNull(irTypeMakeNullable, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
            return (IrSimpleType) irTypeMakeNullable;
        }

        public static TypeConstructorMarker typeConstructor(IrTypeSystemContext irTypeSystemContext, SimpleTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (receiver instanceof IrCapturedType) {
                return ((IrCapturedType) receiver).getConstructor();
            }
            if (receiver instanceof IrSimpleType) {
                return ((IrSimpleType) receiver).getClassifier();
            }
            if (receiver instanceof IrErrorType) {
                return ((IrErrorType) receiver).getErrorClassStubSymbol();
            }
            throw new IllegalStateException("Unknown type constructor".toString());
        }

        public static CapturedTypeConstructorMarker typeConstructor(IrTypeSystemContext irTypeSystemContext, CapturedTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return ((IrCapturedType) receiver).getConstructor();
        }

        public static CaptureStatus captureStatus(IrTypeSystemContext irTypeSystemContext, CapturedTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return ((IrCapturedType) receiver).getCaptureStatus();
        }

        public static TypeArgumentMarker projection(IrTypeSystemContext irTypeSystemContext, CapturedTypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return ((IrCapturedType.Constructor) receiver).getArgument();
        }

        public static int argumentsCount(IrTypeSystemContext irTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (receiver instanceof IrSimpleType) {
                return ((IrSimpleType) receiver).getArguments().size();
            }
            return 0;
        }

        public static TypeArgumentMarker getArgument(IrTypeSystemContext irTypeSystemContext, KotlinTypeMarker receiver, int i) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (receiver instanceof IrSimpleType) {
                IrSimpleType irSimpleType = (IrSimpleType) receiver;
                if (i >= irSimpleType.getArguments().size()) {
                    throw new IllegalStateException(("No argument " + i + " in type '" + RenderIrElementKt.render$default(irSimpleType, (DumpIrTreeOptions) null, 1, (Object) null) + '\'').toString());
                }
                return irSimpleType.getArguments().get(i);
            }
            throw new IllegalStateException(("Type " + receiver + " has no arguments").toString());
        }

        public static List<TypeArgumentMarker> getArguments(IrTypeSystemContext irTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (receiver instanceof IrSimpleType) {
                return ((IrSimpleType) receiver).getArguments();
            }
            throw new IllegalStateException(("Type " + receiver + " has no arguments").toString());
        }

        public static IrTypeArgument asTypeArgument(IrTypeSystemContext irTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return (IrTypeArgument) receiver;
        }

        public static KotlinTypeMarker lowerType(IrTypeSystemContext irTypeSystemContext, CapturedTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return ((IrCapturedType) receiver).getLowerType();
        }

        public static boolean isStarProjection(IrTypeSystemContext irTypeSystemContext, TypeArgumentMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return receiver instanceof IrStarProjection;
        }

        public static TypeVariance getVariance(IrTypeSystemContext irTypeSystemContext, TypeArgumentMarker receiver) {
            Variance variance;
            TypeVariance typeVarianceConvertVariance;
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            IrTypeProjection irTypeProjection = receiver instanceof IrTypeProjection ? (IrTypeProjection) receiver : null;
            return (irTypeProjection == null || (variance = irTypeProjection.getVariance()) == null || (typeVarianceConvertVariance = TypeSystemContextKt.convertVariance(variance)) == null) ? TypeVariance.OUT : typeVarianceConvertVariance;
        }

        public static TypeArgumentMarker replaceType(IrTypeSystemContext irTypeSystemContext, TypeArgumentMarker receiver, KotlinTypeMarker newType) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            Intrinsics.checkNotNullParameter(newType, "newType");
            if (!(receiver instanceof IrTypeArgument)) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            if (receiver instanceof IrStarProjection) {
                return receiver;
            }
            if (receiver instanceof IrTypeProjection) {
                return new IrTypeProjectionImpl((IrType) newType, ((IrTypeProjection) receiver).getVariance());
            }
            throw new NoWhenBranchMatchedException();
        }

        public static IrType getType(IrTypeSystemContext irTypeSystemContext, TypeArgumentMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return ((IrTypeProjection) receiver).getType();
        }

        private static List<IrTypeParameter> getTypeParameters(IrTypeSystemContext irTypeSystemContext, TypeConstructorMarker typeConstructorMarker) {
            if (typeConstructorMarker instanceof IrTypeParameterSymbol) {
                return CollectionsKt.emptyList();
            }
            if (typeConstructorMarker instanceof IrClassSymbol) {
                return IrTypeSystemContextKt.extractTypeParameters(((IrClassSymbol) typeConstructorMarker).getOwner());
            }
            throw new IllegalStateException("unsupported type constructor".toString());
        }

        public static int parametersCount(IrTypeSystemContext irTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return getTypeParameters(irTypeSystemContext, receiver).size();
        }

        public static IrTypeParameterSymbol getParameter(IrTypeSystemContext irTypeSystemContext, TypeConstructorMarker receiver, int i) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return getTypeParameters(irTypeSystemContext, receiver).get(i).getSymbol();
        }

        public static List<IrTypeParameterSymbol> getParameters(IrTypeSystemContext irTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            List<IrTypeParameter> typeParameters = getTypeParameters(irTypeSystemContext, receiver);
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters, 10));
            Iterator<T> it = typeParameters.iterator();
            while (it.hasNext()) {
                arrayList.add(((IrTypeParameter) it.next()).getSymbol());
            }
            return arrayList;
        }

        public static Collection<KotlinTypeMarker> supertypes(IrTypeSystemContext irTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (receiver instanceof IrCapturedType.Constructor) {
                return ((IrCapturedType.Constructor) receiver).getSuperTypes();
            }
            if (receiver instanceof IrClassSymbol) {
                return ((IrClassSymbol) receiver).getOwner().getSuperTypes();
            }
            if (receiver instanceof IrTypeParameterSymbol) {
                return ((IrTypeParameterSymbol) receiver).getOwner().getSuperTypes();
            }
            throw new IllegalStateException("unsupported type constructor".toString());
        }

        public static boolean isClassTypeConstructor(IrTypeSystemContext irTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return receiver instanceof IrClassSymbol;
        }

        public static boolean isInterface(IrTypeSystemContext irTypeSystemContext, TypeConstructorMarker receiver) {
            IrClass owner;
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            IrClassSymbol irClassSymbol = receiver instanceof IrClassSymbol ? (IrClassSymbol) receiver : null;
            return (irClassSymbol == null || (owner = irClassSymbol.getOwner()) == null || !IrUtilsKt.isInterface(owner)) ? false : true;
        }

        public static boolean isTypeParameterTypeConstructor(IrTypeSystemContext irTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return receiver instanceof IrTypeParameterSymbol;
        }

        public static TypeVariance getVariance(IrTypeSystemContext irTypeSystemContext, TypeParameterMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return TypeSystemContextKt.convertVariance(((IrTypeParameterSymbol) receiver).getOwner().getVariance());
        }

        private static List<IrType> getSuperTypes(IrTypeSystemContext irTypeSystemContext, TypeParameterMarker typeParameterMarker) {
            Intrinsics.checkNotNull(typeParameterMarker, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol");
            return ((IrTypeParameterSymbol) typeParameterMarker).getOwner().getSuperTypes();
        }

        public static int upperBoundCount(IrTypeSystemContext irTypeSystemContext, TypeParameterMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return getSuperTypes(irTypeSystemContext, receiver).size();
        }

        public static KotlinTypeMarker getUpperBound(IrTypeSystemContext irTypeSystemContext, TypeParameterMarker receiver, int i) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            IrType irType = getSuperTypes(irTypeSystemContext, receiver).get(i);
            Intrinsics.checkNotNull(irType, "null cannot be cast to non-null type org.jetbrains.kotlin.types.model.KotlinTypeMarker");
            return irType;
        }

        public static List<KotlinTypeMarker> getUpperBounds(IrTypeSystemContext irTypeSystemContext, TypeParameterMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            List<IrType> superTypes = getSuperTypes(irTypeSystemContext, receiver);
            Intrinsics.checkNotNull(superTypes, "null cannot be cast to non-null type kotlin.collections.List<org.jetbrains.kotlin.types.model.KotlinTypeMarker>");
            return superTypes;
        }

        public static IrTypeParameterSymbol getTypeConstructor(IrTypeSystemContext irTypeSystemContext, TypeParameterMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return (IrTypeParameterSymbol) receiver;
        }

        private static boolean containsTypeConstructor(IrTypeSystemContext irTypeSystemContext, KotlinTypeMarker kotlinTypeMarker, TypeConstructorMarker typeConstructorMarker) {
            if (Intrinsics.areEqual(irTypeSystemContext.typeConstructor(kotlinTypeMarker), typeConstructorMarker)) {
                return true;
            }
            int iArgumentsCount = irTypeSystemContext.argumentsCount(kotlinTypeMarker);
            for (int i = 0; i < iArgumentsCount; i++) {
                TypeArgumentMarker argument = irTypeSystemContext.getArgument(kotlinTypeMarker, i);
                if (irTypeSystemContext.isStarProjection(argument)) {
                    argument = null;
                }
                if (argument != null && containsTypeConstructor(irTypeSystemContext, irTypeSystemContext.getType(argument), typeConstructorMarker)) {
                    return true;
                }
            }
            return false;
        }

        public static boolean hasRecursiveBounds(IrTypeSystemContext irTypeSystemContext, TypeParameterMarker receiver, TypeConstructorMarker typeConstructorMarker) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            int iUpperBoundCount = irTypeSystemContext.upperBoundCount(receiver);
            for (int i = 0; i < iUpperBoundCount; i++) {
                KotlinTypeMarker upperBound = irTypeSystemContext.getUpperBound(receiver, i);
                if (containsTypeConstructor(irTypeSystemContext, upperBound, irTypeSystemContext.getTypeConstructor(receiver)) && (typeConstructorMarker == null || Intrinsics.areEqual(irTypeSystemContext.typeConstructor(upperBound), typeConstructorMarker))) {
                    return true;
                }
            }
            return false;
        }

        public static boolean areEqualTypeConstructors(IrTypeSystemContext irTypeSystemContext, TypeConstructorMarker c1, TypeConstructorMarker c2) {
            Intrinsics.checkNotNullParameter(c1, "c1");
            Intrinsics.checkNotNullParameter(c2, "c2");
            if ((c1 instanceof IrClassifierSymbol) && (c2 instanceof IrClassifierSymbol)) {
                return FqNameEqualityChecker.INSTANCE.areEqual((IrClassifierSymbol) c1, (IrClassifierSymbol) c2);
            }
            return c1 == c2;
        }

        public static boolean isDenotable(IrTypeSystemContext irTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return !(receiver instanceof IrCapturedType.Constructor);
        }

        public static boolean isCommonFinalClassConstructor(IrTypeSystemContext irTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            IrClassSymbol irClassSymbol = receiver instanceof IrClassSymbol ? (IrClassSymbol) receiver : null;
            if (irClassSymbol == null) {
                return false;
            }
            IrClass owner = irClassSymbol.getOwner();
            return (owner.getModality() != Modality.FINAL || owner.getKind() == ClassKind.ENUM_CLASS || owner.getKind() == ClassKind.ANNOTATION_CLASS) ? false : true;
        }

        /* JADX WARN: Code duplicated, block: B:42:0x00be  */
        public static SimpleTypeMarker captureFromArguments(IrTypeSystemContext irTypeSystemContext, SimpleTypeMarker type, CaptureStatus status) {
            IrType type2;
            Intrinsics.checkNotNullParameter(type, "type");
            Intrinsics.checkNotNullParameter(status, "status");
            if (!(type instanceof IrSimpleType)) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            if (type instanceof IrCapturedType) {
                return null;
            }
            IrSimpleType irSimpleType = (IrSimpleType) type;
            IrClassifierSymbol classifier = irSimpleType.getClassifier();
            IrClassSymbol irClassSymbol = classifier instanceof IrClassSymbol ? (IrClassSymbol) classifier : null;
            if (irClassSymbol == null) {
                return null;
            }
            List<IrTypeArgument> arguments = irSimpleType.getArguments();
            if (!irClassSymbol.isBound()) {
                return null;
            }
            List<IrTypeParameter> listExtractTypeParameters = IrTypeSystemContextKt.extractTypeParameters(irClassSymbol.getOwner());
            if (arguments.size() != listExtractTypeParameters.size()) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            List<IrTypeArgument> list = arguments;
            if ((list instanceof Collection) && list.isEmpty()) {
                return type;
            }
            for (IrTypeArgument irTypeArgument : list) {
                if (!(irTypeArgument instanceof IrTypeProjection) || ((IrTypeProjection) irTypeArgument).getVariance() != Variance.INVARIANT) {
                    ArrayList arrayList = new ArrayList(arguments.size());
                    int size = arguments.size();
                    for (int i = 0; i < size; i++) {
                        IrTypeParameter irTypeParameter = listExtractTypeParameters.get(i);
                        IrTypeArgument irTypeArgument2 = arguments.get(i);
                        boolean z = irTypeArgument2 instanceof IrTypeProjection;
                        if (z && ((IrTypeProjection) irTypeArgument2).getVariance() == Variance.INVARIANT) {
                            arrayList.add(null);
                        } else {
                            if (z) {
                                IrTypeProjection irTypeProjection = (IrTypeProjection) irTypeArgument2;
                                if (irTypeProjection.getVariance() == Variance.IN_VARIANCE) {
                                    type2 = irTypeProjection.getType();
                                } else {
                                    type2 = null;
                                }
                            } else {
                                type2 = null;
                            }
                            arrayList.add(new IrCapturedType(status, type2, irTypeArgument2, irTypeParameter));
                        }
                    }
                    ArrayList arrayList2 = new ArrayList(arguments.size());
                    List<IrTypeParameter> list2 = listExtractTypeParameters;
                    ArrayList arrayList3 = new ArrayList(list2.size());
                    Iterator<T> it = list2.iterator();
                    while (it.hasNext()) {
                        arrayList3.add(((IrTypeParameter) it.next()).getSymbol());
                    }
                    IrCapturedTypeSubstitutor irCapturedTypeSubstitutor = new IrCapturedTypeSubstitutor(org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList3), arguments, arrayList, irTypeSystemContext.getIrBuiltIns());
                    int size2 = arguments.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        IrTypeArgument irTypeArgument3 = arguments.get(i2);
                        IrTypeParameter irTypeParameter2 = listExtractTypeParameters.get(i2);
                        IrCapturedType irCapturedType = (IrCapturedType) arrayList.get(i2);
                        if (irCapturedType == null) {
                            if (irTypeArgument3 instanceof IrTypeProjection) {
                                ((IrTypeProjection) irTypeArgument3).getVariance();
                                Variance variance = Variance.INVARIANT;
                            }
                            arrayList2.add(irTypeArgument3);
                        } else {
                            ArrayList arrayList4 = new ArrayList();
                            Iterator<T> it2 = irTypeParameter2.getSuperTypes().iterator();
                            while (it2.hasNext()) {
                                arrayList4.add(irCapturedTypeSubstitutor.substitute((IrType) it2.next()));
                            }
                            ArrayList arrayList5 = arrayList4;
                            if (irTypeArgument3 instanceof IrTypeProjection) {
                                IrTypeProjection irTypeProjection2 = (IrTypeProjection) irTypeArgument3;
                                if (irTypeProjection2.getVariance() == Variance.OUT_VARIANCE) {
                                    arrayList5.add(irTypeProjection2.getType());
                                }
                            }
                            irCapturedType.getConstructor().initSuperTypes(arrayList4);
                            arrayList2.add(IrSimpleTypeImplKt.makeTypeProjection(irCapturedType, Variance.INVARIANT));
                        }
                    }
                    return new IrSimpleTypeImpl(irSimpleType.getClassifier(), irSimpleType.getNullability(), arrayList2, irSimpleType.getAnnotations(), (IrTypeAbbreviation) null, 16, (DefaultConstructorMarker) null);
                }
            }
            return type;
        }

        public static IrSimpleType asArgumentList(IrTypeSystemContext irTypeSystemContext, SimpleTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return (IrSimpleType) receiver;
        }

        public static boolean isAnyConstructor(IrTypeSystemContext irTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return (receiver instanceof IrClassSymbol) && IrTypePredicatesKt.isClassWithFqName((IrClassifierSymbol) receiver, StandardNames.FqNames.any);
        }

        public static boolean isNothingConstructor(IrTypeSystemContext irTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return (receiver instanceof IrClassSymbol) && IrTypePredicatesKt.isClassWithFqName((IrClassifierSymbol) receiver, StandardNames.FqNames.nothing);
        }

        public static Set<IrType> possibleIntegerTypes(IrTypeSystemContext irTypeSystemContext, SimpleTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            IrBuiltIns irBuiltIns = irTypeSystemContext.getIrBuiltIns();
            return SetsKt.setOf((Object[]) new IrType[]{irBuiltIns.getByteType(), irBuiltIns.getShortType(), irBuiltIns.getIntType(), irBuiltIns.getLongType()});
        }

        public static boolean isLocalType(IrTypeSystemContext irTypeSystemContext, TypeConstructorMarker receiver) {
            ClassId classId;
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return (receiver instanceof IrClassSymbol) && (classId = AdditionalIrUtilsKt.getClassId(((IrClassSymbol) receiver).getOwner())) != null && classId.isLocal();
        }

        public static boolean isAnonymous(IrTypeSystemContext irTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof IrClassSymbol)) {
                return false;
            }
            ClassId classId = AdditionalIrUtilsKt.getClassId(((IrClassSymbol) receiver).getOwner());
            return Intrinsics.areEqual(classId != null ? classId.getShortClassName() : null, SpecialNames.ANONYMOUS);
        }

        public static TypeParameterMarker getTypeParameter(IrTypeSystemContext irTypeSystemContext, TypeVariableTypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            throw new IllegalStateException("Type variables is unsupported in IR".toString());
        }

        public static KotlinTypeMarker createFlexibleType(IrTypeSystemContext irTypeSystemContext, SimpleTypeMarker lowerBound, SimpleTypeMarker upperBound) {
            Intrinsics.checkNotNullParameter(lowerBound, "lowerBound");
            Intrinsics.checkNotNullParameter(upperBound, "upperBound");
            if (!irTypeSystemContext.isNothing(lowerBound)) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            if (!(upperBound instanceof IrType) || !irTypeSystemContext.isNullableAny(upperBound)) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            return new IrDynamicTypeImpl(null, CollectionsKt.emptyList(), Variance.INVARIANT);
        }

        public static SimpleTypeMarker createSimpleType(IrTypeSystemContext irTypeSystemContext, TypeConstructorMarker constructor, List<? extends TypeArgumentMarker> arguments, boolean z, boolean z2, List<? extends AnnotationMarker> list) {
            List listEmptyList;
            Intrinsics.checkNotNullParameter(constructor, "constructor");
            Intrinsics.checkNotNullParameter(arguments, "arguments");
            if (list != null) {
                ArrayList arrayList = new ArrayList();
                for (Object obj : list) {
                    if (obj instanceof IrConstructorCall) {
                        arrayList.add(obj);
                    }
                }
                listEmptyList = org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList);
            } else {
                listEmptyList = null;
            }
            if (!Intrinsics.areEqual(listEmptyList != null ? Integer.valueOf(listEmptyList.size()) : null, list != null ? Integer.valueOf(list.size()) : null)) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            IrClassifierSymbol irClassifierSymbol = (IrClassifierSymbol) constructor;
            SimpleTypeNullability simpleTypeNullability = z ? SimpleTypeNullability.MARKED_NULLABLE : SimpleTypeNullability.DEFINITELY_NOT_NULL;
            List<? extends TypeArgumentMarker> list2 = arguments;
            ArrayList arrayList2 = new ArrayList(list2.size());
            for (TypeArgumentMarker typeArgumentMarker : list2) {
                Intrinsics.checkNotNull(typeArgumentMarker, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrTypeArgument");
                arrayList2.add((IrTypeArgument) typeArgumentMarker);
            }
            List listCompactIfPossible = org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList2);
            if (listEmptyList == null) {
                listEmptyList = CollectionsKt.emptyList();
            }
            return new IrSimpleTypeImpl(irClassifierSymbol, simpleTypeNullability, listCompactIfPossible, listEmptyList, (IrTypeAbbreviation) null, 16, (DefaultConstructorMarker) null);
        }

        private static Variance convertVariance(IrTypeSystemContext irTypeSystemContext, TypeVariance typeVariance) {
            int i = WhenMappings.$EnumSwitchMapping$0[typeVariance.ordinal()];
            if (i == 1) {
                return Variance.INVARIANT;
            }
            if (i == 2) {
                return Variance.IN_VARIANCE;
            }
            if (i == 3) {
                return Variance.OUT_VARIANCE;
            }
            throw new NoWhenBranchMatchedException();
        }

        public static TypeArgumentMarker createTypeArgument(IrTypeSystemContext irTypeSystemContext, KotlinTypeMarker type, TypeVariance variance) {
            Intrinsics.checkNotNullParameter(type, "type");
            Intrinsics.checkNotNullParameter(variance, "variance");
            return IrSimpleTypeImplKt.makeTypeProjection((IrType) type, convertVariance(irTypeSystemContext, variance));
        }

        public static IrStarProjectionImpl createStarProjection(IrTypeSystemContext irTypeSystemContext, TypeParameterMarker typeParameter) {
            Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
            return IrStarProjectionImpl.INSTANCE;
        }

        public static boolean canHaveUndefinedNullability(IrTypeSystemContext irTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return (receiver instanceof IrSimpleType) && (((IrSimpleType) receiver).getClassifier() instanceof IrTypeParameterSymbol);
        }

        public static boolean isExtensionFunction(IrTypeSystemContext irTypeSystemContext, SimpleTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof IrSimpleType)) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            return irTypeSystemContext.hasAnnotation(receiver, StandardNames.FqNames.extensionFunctionType);
        }

        public static int typeDepth(IrTypeSystemContext irTypeSystemContext, SimpleTypeMarker receiver) {
            Integer num;
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            Iterator<T> it = ((IrSimpleType) receiver).getArguments().iterator();
            if (it.hasNext()) {
                IrTypeArgument irTypeArgument = (IrTypeArgument) it.next();
                Integer numValueOf = Integer.valueOf(irTypeArgument instanceof IrStarProjection ? 1 : irTypeSystemContext.typeDepth(irTypeSystemContext.getType((TypeArgumentMarker) irTypeArgument)));
                while (it.hasNext()) {
                    IrTypeArgument irTypeArgument2 = (IrTypeArgument) it.next();
                    Integer numValueOf2 = Integer.valueOf(irTypeArgument2 instanceof IrStarProjection ? 1 : irTypeSystemContext.typeDepth(irTypeSystemContext.getType((TypeArgumentMarker) irTypeArgument2)));
                    if (numValueOf.compareTo(numValueOf2) < 0) {
                        numValueOf = numValueOf2;
                    }
                }
                num = numValueOf;
            } else {
                num = null;
            }
            Integer num2 = num;
            return (num2 != null ? num2.intValue() : 0) + 1;
        }

        public static SimpleTypeMarker toErrorType(IrTypeSystemContext irTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            throw new IllegalStateException("Should not be called");
        }

        public static boolean isError(IrTypeSystemContext irTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            throw new IllegalStateException("Should not be called");
        }

        public static SimpleTypeMarker findCommonIntegerLiteralTypesSuperType(IrTypeSystemContext irTypeSystemContext, List<? extends SimpleTypeMarker> explicitSupertypes) {
            Intrinsics.checkNotNullParameter(explicitSupertypes, "explicitSupertypes");
            IrType intType = irTypeSystemContext.getIrBuiltIns().getIntType();
            Intrinsics.checkNotNull(intType, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
            return (IrSimpleType) intType;
        }

        public static List<AnnotationMarker> unionTypeAttributes(IrTypeSystemContext irTypeSystemContext, List<? extends KotlinTypeMarker> types) {
            Intrinsics.checkNotNullParameter(types, "types");
            return CollectionsKt.emptyList();
        }

        public static boolean isNullableType(IrTypeSystemContext irTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return (receiver instanceof IrType) && IrTypeUtilsKt.isNullable((IrType) receiver);
        }

        /* JADX INFO: renamed from: intersectTypes, reason: collision with other method in class */
        public static SimpleTypeMarker m7220intersectTypes(IrTypeSystemContext irTypeSystemContext, List<? extends SimpleTypeMarker> types) {
            Intrinsics.checkNotNullParameter(types, "types");
            Object objMakeTypeIntersection = IrSimpleTypeImplKt.makeTypeIntersection(types);
            Intrinsics.checkNotNull(objMakeTypeIntersection, "null cannot be cast to non-null type org.jetbrains.kotlin.types.model.SimpleTypeMarker");
            return (SimpleTypeMarker) objMakeTypeIntersection;
        }

        public static KotlinTypeMarker intersectTypes(IrTypeSystemContext irTypeSystemContext, List<? extends KotlinTypeMarker> types) {
            Intrinsics.checkNotNullParameter(types, "types");
            return IrSimpleTypeImplKt.makeTypeIntersection(types);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static boolean isPrimitiveType(IrTypeSystemContext irTypeSystemContext, SimpleTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return (receiver instanceof IrSimpleType) && IrTypePredicatesKt.isPrimitiveType$default((IrType) receiver, false, 1, null);
        }

        public static List<AnnotationMarker> getAttributes(IrTypeSystemContext irTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof IrType)) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            List<IrConstructorCall> annotations = ((IrType) receiver).getAnnotations();
            ArrayList arrayList = new ArrayList(annotations.size());
            Iterator<T> it = annotations.iterator();
            while (it.hasNext()) {
                arrayList.add(new IrTypeSystemContext$getAttributes$1$1((IrConstructorCall) it.next()));
            }
            return org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList);
        }

        public static List<AnnotationMarker> getCustomAttributes(IrTypeSystemContext irTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (!(receiver instanceof IrType)) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            return CollectionsKt.emptyList();
        }

        public static SimpleTypeMarker createErrorType(IrTypeSystemContext irTypeSystemContext, String debugName) {
            Intrinsics.checkNotNullParameter(debugName, "debugName");
            throw new NotImplementedError("An operation is not implemented: IrTypeSystemContext doesn't support constraint system resolution");
        }

        public static KotlinTypeMarker createUninferredType(IrTypeSystemContext irTypeSystemContext, TypeConstructorMarker constructor) {
            Intrinsics.checkNotNullParameter(constructor, "constructor");
            throw new NotImplementedError("An operation is not implemented: IrTypeSystemContext doesn't support constraint system resolution");
        }

        public static IrSimpleType nullableAnyType(IrTypeSystemContext irTypeSystemContext) {
            IrType anyNType = irTypeSystemContext.getIrBuiltIns().getAnyNType();
            Intrinsics.checkNotNull(anyNType, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
            return (IrSimpleType) anyNType;
        }

        public static IrSimpleType nullableNothingType(IrTypeSystemContext irTypeSystemContext) {
            IrType nothingNType = irTypeSystemContext.getIrBuiltIns().getNothingNType();
            Intrinsics.checkNotNull(nothingNType, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
            return (IrSimpleType) nothingNType;
        }

        public static IrSimpleType nothingType(IrTypeSystemContext irTypeSystemContext) {
            IrType nothingType = irTypeSystemContext.getIrBuiltIns().getNothingType();
            Intrinsics.checkNotNull(nothingType, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
            return (IrSimpleType) nothingType;
        }

        public static IrSimpleType anyType(IrTypeSystemContext irTypeSystemContext) {
            IrType anyType = irTypeSystemContext.getIrBuiltIns().getAnyType();
            Intrinsics.checkNotNull(anyType, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
            return (IrSimpleType) anyType;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static SimpleTypeMarker arrayType(IrTypeSystemContext irTypeSystemContext, KotlinTypeMarker componentType) {
            Intrinsics.checkNotNullParameter(componentType, "componentType");
            return IrTypesKt.typeWith(irTypeSystemContext.getIrBuiltIns().getArrayClass(), componentType);
        }

        public static boolean isArrayOrNullableArray(IrTypeSystemContext irTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            IrType irType = (IrType) receiver;
            return IrTypePredicatesKt.isArray(irType) || IrTypePredicatesKt.isNullableArray(irType);
        }

        public static boolean isFinalClassOrEnumEntryOrAnnotationClassConstructor(IrTypeSystemContext irTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            IrClassifierSymbol irClassifierSymbol = (IrClassifierSymbol) receiver;
            if (irClassifierSymbol instanceof IrClassSymbol) {
                IrClass owner = ((IrClassSymbol) irClassifierSymbol).getOwner();
                if (owner.getModality() == Modality.FINAL && !IrUtilsKt.isEnumClass(owner)) {
                    return true;
                }
            }
            return false;
        }

        public static boolean hasAnnotation(IrTypeSystemContext irTypeSystemContext, KotlinTypeMarker receiver, FqName fqName) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            Intrinsics.checkNotNullParameter(fqName, "fqName");
            return IrUtilsKt.hasAnnotation((IrAnnotationContainer) receiver, fqName);
        }

        public static Object getAnnotationFirstArgumentValue(IrTypeSystemContext irTypeSystemContext, KotlinTypeMarker receiver, FqName fqName) {
            List<IrConstructorCall> annotations;
            Object next;
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            Intrinsics.checkNotNullParameter(fqName, "fqName");
            IrType irType = receiver instanceof IrType ? (IrType) receiver : null;
            if (irType == null || (annotations = irType.getAnnotations()) == null) {
                return null;
            }
            Iterator<T> it = annotations.iterator();
            do {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
            } while (!AdditionalIrUtilsKt.hasEqualFqName(IrUtilsKt.getParentAsClass(((IrConstructorCall) next).getSymbol().getOwner()), fqName));
            IrConstructorCall irConstructorCall = (IrConstructorCall) next;
            if (irConstructorCall == null || irConstructorCall.getValueArgumentsCount() <= 0) {
                return null;
            }
            IrExpression valueArgument = irConstructorCall.getValueArgument(0);
            IrConst irConst = valueArgument instanceof IrConst ? (IrConst) valueArgument : null;
            if (irConst != null) {
                return irConst.getValue();
            }
            return null;
        }

        public static TypeParameterMarker getTypeParameterClassifier(IrTypeSystemContext irTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return receiver instanceof IrTypeParameterSymbol ? (IrTypeParameterSymbol) receiver : null;
        }

        public static boolean isInlineClass(IrTypeSystemContext irTypeSystemContext, TypeConstructorMarker receiver) {
            IrClass owner;
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            IrClassSymbol irClassSymbol = receiver instanceof IrClassSymbol ? (IrClassSymbol) receiver : null;
            return (irClassSymbol == null || (owner = irClassSymbol.getOwner()) == null || !IrDeclarationsKt.isSingleFieldValueClass(owner)) ? false : true;
        }

        public static boolean isMultiFieldValueClass(IrTypeSystemContext irTypeSystemContext, TypeConstructorMarker receiver) {
            IrClass owner;
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            IrClassSymbol irClassSymbol = receiver instanceof IrClassSymbol ? (IrClassSymbol) receiver : null;
            return (irClassSymbol == null || (owner = irClassSymbol.getOwner()) == null || !IrDeclarationsKt.isMultiFieldValueClass(owner)) ? false : true;
        }

        public static List<Pair<Name, SimpleTypeMarker>> getValueClassProperties(IrTypeSystemContext irTypeSystemContext, TypeConstructorMarker receiver) {
            IrClass owner;
            ValueClassRepresentation<IrSimpleType> valueClassRepresentation;
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            IrClassSymbol irClassSymbol = receiver instanceof IrClassSymbol ? (IrClassSymbol) receiver : null;
            if (irClassSymbol == null || (owner = irClassSymbol.getOwner()) == null || (valueClassRepresentation = owner.getValueClassRepresentation()) == null) {
                return null;
            }
            return valueClassRepresentation.getUnderlyingPropertyNamesToTypes();
        }

        public static boolean isInnerClass(IrTypeSystemContext irTypeSystemContext, TypeConstructorMarker receiver) {
            IrClass owner;
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            IrClassSymbol irClassSymbol = receiver instanceof IrClassSymbol ? (IrClassSymbol) receiver : null;
            return (irClassSymbol == null || (owner = irClassSymbol.getOwner()) == null || !owner.getIsInner()) ? false : true;
        }

        public static KotlinTypeMarker getRepresentativeUpperBound(IrTypeSystemContext irTypeSystemContext, TypeParameterMarker receiver) {
            Object next;
            IrClass owner;
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            IrTypeParameterSymbol irTypeParameterSymbol = (IrTypeParameterSymbol) receiver;
            Iterator<T> it = irTypeParameterSymbol.getOwner().getSuperTypes().iterator();
            while (true) {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
                IrClassSymbol classOrNull = IrTypesKt.getClassOrNull((IrType) next);
                if (classOrNull != null && (owner = classOrNull.getOwner()) != null && owner.getKind() != ClassKind.INTERFACE && owner.getKind() != ClassKind.ANNOTATION_CLASS) {
                    break;
                }
            }
            IrType irType = (IrType) next;
            return irType != null ? irType : (KotlinTypeMarker) CollectionsKt.first((List) irTypeParameterSymbol.getOwner().getSuperTypes());
        }

        public static KotlinTypeMarker getUnsubstitutedUnderlyingType(IrTypeSystemContext irTypeSystemContext, KotlinTypeMarker receiver) {
            IrClass owner;
            InlineClassRepresentation<IrSimpleType> inlineClassRepresentation;
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            IrClassSymbol classOrNull = IrTypesKt.getClassOrNull((IrType) receiver);
            return (classOrNull == null || (owner = classOrNull.getOwner()) == null || (inlineClassRepresentation = IrDeclarationsKt.getInlineClassRepresentation(owner)) == null) ? null : (IrSimpleType) inlineClassRepresentation.getUnderlyingType();
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r7v0 */
        /* JADX WARN: Type inference failed for: r7v1, types: [java.util.List] */
        /* JADX WARN: Type inference failed for: r7v2 */
        public static KotlinTypeMarker getSubstitutedUnderlyingType(IrTypeSystemContext irTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            KotlinTypeMarker unsubstitutedUnderlyingType = irTypeSystemContext.getUnsubstitutedUnderlyingType(receiver);
            Object arguments = null;
            if (unsubstitutedUnderlyingType != null) {
                IrClass irClass = IrTypesKt.getClass((IrType) receiver);
                Intrinsics.checkNotNull(irClass);
                List<IrTypeParameter> typeParameters = irClass.getTypeParameters();
                ArrayList arrayList = new ArrayList(typeParameters.size());
                Iterator it = typeParameters.iterator();
                while (it.hasNext()) {
                    arrayList.add(((IrTypeParameter) it.next()).getSymbol());
                }
                List listCompactIfPossible = org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList);
                IrSimpleType irSimpleType = receiver instanceof IrSimpleType ? (IrSimpleType) receiver : null;
                arguments = irSimpleType != null ? irSimpleType.getArguments() : null;
                arguments = new IrTypeSubstitutor(listCompactIfPossible, arguments == null ? CollectionsKt.emptyList() : arguments, irTypeSystemContext.getIrBuiltIns(), false, 8, null).substitute((IrType) unsubstitutedUnderlyingType);
            }
            return (KotlinTypeMarker) arguments;
        }

        public static PrimitiveType getPrimitiveType(IrTypeSystemContext irTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            String nameForClassUnderKotlinPackage = getNameForClassUnderKotlinPackage(irTypeSystemContext, receiver);
            if (nameForClassUnderKotlinPackage != null) {
                return PrimitiveType.INSTANCE.getByShortName(nameForClassUnderKotlinPackage);
            }
            return null;
        }

        public static PrimitiveType getPrimitiveArrayType(IrTypeSystemContext irTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            String nameForClassUnderKotlinPackage = getNameForClassUnderKotlinPackage(irTypeSystemContext, receiver);
            if (nameForClassUnderKotlinPackage != null) {
                return PrimitiveType.INSTANCE.getByShortArrayName(nameForClassUnderKotlinPackage);
            }
            return null;
        }

        private static String getNameForClassUnderKotlinPackage(IrTypeSystemContext irTypeSystemContext, TypeConstructorMarker typeConstructorMarker) {
            if (!(typeConstructorMarker instanceof IrClassSymbol)) {
                return null;
            }
            IrClassSymbol irClassSymbol = (IrClassSymbol) typeConstructorMarker;
            IdSignature signature = irClassSymbol.getSignature();
            IdSignature.CommonSignature accessorSignature = signature != null ? signature.getAccessorSignature() : null;
            if (accessorSignature != null) {
                if (Intrinsics.areEqual(accessorSignature.getPackageFqName(), StandardNames.BUILT_INS_PACKAGE_NAME.asString())) {
                    return accessorSignature.getDeclarationFqName();
                }
                return null;
            }
            IrDeclarationParent parent = irClassSymbol.getOwner().getParent();
            if ((parent instanceof IrPackageFragment) && Intrinsics.areEqual(((IrPackageFragment) parent).getPackageFqName(), StandardNames.BUILT_INS_PACKAGE_FQ_NAME)) {
                return irClassSymbol.getOwner().getName().asString();
            }
            return null;
        }

        public static boolean isUnderKotlinPackage(IrTypeSystemContext irTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            IrClassifierSymbol irClassifierSymbol = receiver instanceof IrClassifierSymbol ? (IrClassifierSymbol) receiver : null;
            IrSymbolOwner owner = irClassifierSymbol != null ? irClassifierSymbol.getOwner() : null;
            IrClass irClass = owner instanceof IrClass ? (IrClass) owner : null;
            if (irClass != null) {
                IrClass irClass2 = irClass;
                do {
                    IrDeclarationParent parent = irClass2.getParent();
                    if (parent instanceof IrPackageFragment) {
                        return ((IrPackageFragment) parent).getPackageFqName().startsWith(StandardNames.BUILT_INS_PACKAGE_NAME);
                    }
                    irClass2 = parent instanceof IrDeclaration ? (IrDeclaration) parent : null;
                } while (irClass2 != null);
            }
            return false;
        }

        public static FqNameUnsafe getClassFqNameUnsafe(IrTypeSystemContext irTypeSystemContext, TypeConstructorMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            FqName fqNameWhenAvailable = IrUtilsKt.getFqNameWhenAvailable(((IrClassSymbol) receiver).getOwner());
            if (fqNameWhenAvailable != null) {
                return fqNameWhenAvailable.toUnsafe();
            }
            return null;
        }

        public static Name getName(IrTypeSystemContext irTypeSystemContext, TypeParameterMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return ((IrTypeParameterSymbol) receiver).getOwner().getName();
        }

        public static boolean isReified(IrTypeSystemContext irTypeSystemContext, TypeParameterMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return ((IrTypeParameterSymbol) receiver).getOwner().getIsReified();
        }

        public static boolean isInterfaceOrAnnotationClass(IrTypeSystemContext irTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            IrClassSymbol classOrNull = IrTypesKt.getClassOrNull((IrType) receiver);
            IrClass owner = classOrNull != null ? classOrNull.getOwner() : null;
            return owner != null && (IrUtilsKt.isInterface(owner) || IrUtilsKt.isAnnotationClass(owner));
        }

        public static TypeCheckerState newTypeCheckerState(IrTypeSystemContext irTypeSystemContext, boolean z, boolean z2) {
            return IrTypeCheckerStateKt.createIrTypeCheckerState(irTypeSystemContext);
        }

        public static KotlinTypeMarker withNullability(IrTypeSystemContext irTypeSystemContext, KotlinTypeMarker receiver, boolean z) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            if (irTypeSystemContext.isSimpleType(receiver)) {
                SimpleTypeMarker simpleTypeMarkerAsSimpleType = irTypeSystemContext.asSimpleType(receiver);
                Intrinsics.checkNotNull(simpleTypeMarkerAsSimpleType);
                return irTypeSystemContext.withNullability(simpleTypeMarkerAsSimpleType, z);
            }
            throw new IllegalStateException("withNullability for non-simple types is not supported in IR".toString());
        }

        public static KotlinTypeMarker captureFromExpression(IrTypeSystemContext irTypeSystemContext, KotlinTypeMarker type) {
            Intrinsics.checkNotNullParameter(type, "type");
            throw new IllegalStateException("Captured type is unsupported in IR".toString());
        }

        public static SimpleTypeMarker original(IrTypeSystemContext irTypeSystemContext, DefinitelyNotNullTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            throw new IllegalStateException("DefinitelyNotNullTypeMarker.original() type is unsupported in IR".toString());
        }

        public static KotlinTypeMarker makeDefinitelyNotNullOrNotNull(IrTypeSystemContext irTypeSystemContext, KotlinTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            throw new IllegalStateException("makeDefinitelyNotNullOrNotNull is not supported in IR".toString());
        }

        public static SimpleTypeMarker makeSimpleTypeDefinitelyNotNullOrNotNull(IrTypeSystemContext irTypeSystemContext, SimpleTypeMarker receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            throw new IllegalStateException("makeSimpleTypeDefinitelyNotNullOrNotNull is not yet supported in IR".toString());
        }

        public static TypeCheckerState.SupertypesPolicy substitutionSupertypePolicy(IrTypeSystemContext irTypeSystemContext, SimpleTypeMarker type) {
            Intrinsics.checkNotNullParameter(type, "type");
            if (!(type instanceof IrSimpleType)) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            IrSimpleType irSimpleType = (IrSimpleType) type;
            IrClassifierSymbol classifier = irSimpleType.getClassifier();
            Intrinsics.checkNotNull(classifier, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrClassSymbol");
            List<IrTypeParameter> listExtractTypeParameters = IrTypeSystemContextKt.extractTypeParameters(((IrClassSymbol) classifier).getOwner());
            ArrayList arrayList = new ArrayList(listExtractTypeParameters.size());
            Iterator<T> it = listExtractTypeParameters.iterator();
            while (it.hasNext()) {
                arrayList.add(((IrTypeParameter) it.next()).getSymbol());
            }
            final IrTypeSubstitutor irTypeSubstitutor = new IrTypeSubstitutor(org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList), irSimpleType.getArguments(), irTypeSystemContext.getIrBuiltIns(), false, 8, null);
            return new TypeCheckerState.SupertypesPolicy.DoCustomTransform() { // from class: org.jetbrains.kotlin.ir.types.IrTypeSystemContext.substitutionSupertypePolicy.1
                @Override // org.jetbrains.kotlin.types.TypeCheckerState.SupertypesPolicy
                /* JADX INFO: renamed from: transformType */
                public SimpleTypeMarker mo7474transformType(TypeCheckerState state, KotlinTypeMarker type2) {
                    Intrinsics.checkNotNullParameter(state, "state");
                    Intrinsics.checkNotNullParameter(type2, "type");
                    if (!(type2 instanceof IrType)) {
                        throw new IllegalArgumentException("Failed requirement.".toString());
                    }
                    IrType irTypeSubstitute = irTypeSubstitutor.substitute((IrType) type2);
                    Intrinsics.checkNotNull(irTypeSubstitute, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
                    return (IrSimpleType) irTypeSubstitute;
                }
            };
        }

        public static TypeSubstitutorMarker typeSubstitutorByTypeConstructor(IrTypeSystemContext irTypeSystemContext, Map<TypeConstructorMarker, ? extends KotlinTypeMarker> map) {
            Intrinsics.checkNotNullParameter(map, "map");
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            for (Map.Entry<TypeConstructorMarker, ? extends KotlinTypeMarker> entry : map.entrySet()) {
                TypeConstructorMarker key = entry.getKey();
                KotlinTypeMarker value = entry.getValue();
                Intrinsics.checkNotNull(key, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol");
                arrayList.add((IrTypeParameterSymbol) key);
                Intrinsics.checkNotNull(value, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrTypeArgument");
                arrayList2.add((IrTypeArgument) value);
            }
            return new IrTypeSubstitutor(arrayList, arrayList2, irTypeSystemContext.getIrBuiltIns(), false, 8, null);
        }

        public static TypeSubstitutorMarker createEmptySubstitutor(IrTypeSystemContext irTypeSystemContext) {
            return new IrTypeSubstitutor(CollectionsKt.emptyList(), CollectionsKt.emptyList(), irTypeSystemContext.getIrBuiltIns(), false, 8, null);
        }

        public static KotlinTypeMarker safeSubstitute(IrTypeSystemContext irTypeSystemContext, TypeSubstitutorMarker receiver, KotlinTypeMarker type) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            Intrinsics.checkNotNullParameter(type, "type");
            if (!(receiver instanceof AbstractIrTypeSubstitutor)) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            if (!(type instanceof IrType)) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            return ((AbstractIrTypeSubstitutor) receiver).substitute((IrType) type);
        }
    }
}
