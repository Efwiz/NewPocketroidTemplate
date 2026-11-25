package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.phaser.DumperVerifierKt;
import org.jetbrains.kotlin.backend.common.phaser.NamedCompilerPhase;
import org.jetbrains.kotlin.backend.common.phaser.PhaseBuildersKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrStarProjection;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypeProjection;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.utils.DFS;

/* JADX INFO: compiled from: ToArrayLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00008\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\f\u0010\u000e\u001a\u00020\b*\u00020\u000fH\u0002\u001a\u001c\u0010\u0010\u001a\u00020\b*\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u0013H\u0002\u001a\u0014\u0010\u0014\u001a\u00020\b*\u00020\u00152\u0006\u0010\u0011\u001a\u00020\u0002H\u0000\u001a\f\u0010\u0016\u001a\u00020\b*\u00020\u0015H\u0000\"0\u0010\u0000\u001a\u001e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001j\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003`\u0004X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0018\u0010\u0007\u001a\u00020\b*\u00020\t8@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\n\"\u001a\u0010\u000b\u001a\u0004\u0018\u00010\t*\u00020\t8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006\u0017"}, d2 = {"toArrayPhase", "Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "Lorg/jetbrains/kotlin/backend/common/phaser/SameTypeNamedCompilerPhase;", "getToArrayPhase", "()Lorg/jetbrains/kotlin/backend/common/phaser/NamedCompilerPhase;", "isCollectionSubClass", "", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Z", "superClass", "getSuperClass", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "isArrayOrNullableArray", "Lorg/jetbrains/kotlin/ir/types/IrType;", "isArrayOrNullableArrayOf", "context", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "isGenericToArray", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "isNonGenericToArray", "backend.jvm.lower"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ToArrayLoweringKt {
    private static final NamedCompilerPhase<JvmBackendContext, IrFile> toArrayPhase = PhaseBuildersKt.makeIrFilePhase(ToArrayLoweringKt$toArrayPhase$1.INSTANCE, "ToArray", "Handle toArray functions", (248 & 8) != 0 ? SetsKt.emptySet() : null, (248 & 16) != 0 ? SetsKt.emptySet() : null, (248 & 32) != 0 ? SetsKt.emptySet() : null, (248 & 64) != 0 ? SetsKt.emptySet() : null, (248 & 128) != 0 ? SetsKt.setOf((Object[]) new Function3[]{DumperVerifierKt.getDefaultDumper(), DumperVerifierKt.getValidationAction()}) : null);

    public static final NamedCompilerPhase<JvmBackendContext, IrFile> getToArrayPhase() {
        return toArrayPhase;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IrClass getSuperClass(IrClass irClass) {
        List<IrType> superTypes = irClass.getSuperTypes();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = superTypes.iterator();
        while (it.hasNext()) {
            IrClass irClass2 = IrTypesKt.getClass((IrType) it.next());
            if (irClass2 == null || !IrUtilsKt.isClass(irClass2)) {
                irClass2 = null;
            }
            if (irClass2 != null) {
                arrayList.add(irClass2);
            }
        }
        return (IrClass) CollectionsKt.singleOrNull((List) arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Iterable _get_isCollectionSubClass_$lambda$2(IrType irType) {
        List<IrType> listEmptyList;
        Intrinsics.checkNotNull(irType);
        IrClass irClass = IrTypesKt.getClass(irType);
        if (irClass == null || (listEmptyList = irClass.getSuperTypes()) == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        return listEmptyList;
    }

    public static final boolean isCollectionSubClass(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        Boolean boolIfAny = DFS.ifAny(irClass.getSuperTypes(), new DFS.Neighbors() { // from class: org.jetbrains.kotlin.backend.jvm.lower.ToArrayLoweringKt$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.utils.DFS.Neighbors
            public final Iterable getNeighbors(Object obj) {
                return ToArrayLoweringKt._get_isCollectionSubClass_$lambda$2((IrType) obj);
            }
        }, new Function1<IrType, Boolean>() { // from class: org.jetbrains.kotlin.backend.jvm.lower.ToArrayLoweringKt.isCollectionSubClass.2
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrType irType) {
                Intrinsics.checkNotNull(irType);
                return Boolean.valueOf(IrTypePredicatesKt.isCollection(irType));
            }
        });
        Intrinsics.checkNotNullExpressionValue(boolIfAny, "ifAny(...)");
        return boolIfAny.booleanValue();
    }

    private static final boolean isArrayOrNullableArrayOf(IrType irType, JvmBackendContext jvmBackendContext, IrClassifierSymbol irClassifierSymbol) {
        IrClassSymbol anyClass;
        if (!(irType instanceof IrSimpleType)) {
            return false;
        }
        if (!IrTypePredicatesKt.isArray(irType) && !IrTypePredicatesKt.isNullableArray(irType)) {
            return false;
        }
        IrSimpleType irSimpleType = (IrSimpleType) irType;
        if (irSimpleType.getArguments().size() != 1) {
            return false;
        }
        IrTypeArgument irTypeArgument = irSimpleType.getArguments().get(0);
        if (irTypeArgument instanceof IrStarProjection) {
            anyClass = jvmBackendContext.getIrBuiltIns().getAnyClass();
        } else {
            if (!(irTypeArgument instanceof IrTypeProjection)) {
                throw new NoWhenBranchMatchedException();
            }
            IrTypeProjection irTypeProjection = (IrTypeProjection) irTypeArgument;
            anyClass = irTypeProjection.getVariance() == Variance.IN_VARIANCE ? jvmBackendContext.getIrBuiltIns().getAnyClass() : IrTypesKt.getClassifierOrNull(irTypeProjection.getType());
        }
        return Intrinsics.areEqual(irClassifierSymbol, anyClass);
    }

    public static final boolean isGenericToArray(IrSimpleFunction irSimpleFunction, JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(irSimpleFunction, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        return Intrinsics.areEqual(irSimpleFunction.getName().asString(), "toArray") && irSimpleFunction.getTypeParameters().size() == 1 && irSimpleFunction.getValueParameters().size() == 1 && irSimpleFunction.getExtensionReceiverParameter() == null && isArrayOrNullableArrayOf(irSimpleFunction.getReturnType(), context, irSimpleFunction.getTypeParameters().get(0).getSymbol()) && isArrayOrNullableArrayOf(irSimpleFunction.getValueParameters().get(0).getType(), context, irSimpleFunction.getTypeParameters().get(0).getSymbol());
    }

    public static final boolean isNonGenericToArray(IrSimpleFunction irSimpleFunction) {
        Intrinsics.checkNotNullParameter(irSimpleFunction, "<this>");
        return Intrinsics.areEqual(irSimpleFunction.getName().asString(), "toArray") && irSimpleFunction.getTypeParameters().isEmpty() && irSimpleFunction.getValueParameters().isEmpty() && irSimpleFunction.getExtensionReceiverParameter() == null && isArrayOrNullableArray(irSimpleFunction.getReturnType());
    }

    private static final boolean isArrayOrNullableArray(IrType irType) {
        return (irType instanceof IrSimpleType) && (IrTypePredicatesKt.isArray(irType) || IrTypePredicatesKt.isNullableArray(irType));
    }
}
