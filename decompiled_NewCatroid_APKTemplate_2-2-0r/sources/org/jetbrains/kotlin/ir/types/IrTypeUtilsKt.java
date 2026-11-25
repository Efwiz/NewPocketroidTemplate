package org.jetbrains.kotlin.ir.types;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.symbols.FqNameEqualityChecker;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrScriptSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;

/* JADX INFO: compiled from: IrTypeUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00004\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\u001a\u0012\u0010\u0004\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0006\u001a\n\u0010\u0007\u001a\u00020\u0001*\u00020\u0002\u001a\u0012\u0010\b\u001a\u00020\u0001*\u00020\t2\u0006\u0010\n\u001a\u00020\u000b\u001a\u0012\u0010\b\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\n\u001a\u00020\u000b\u001a\u001a\u0010\f\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\r\u001a\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u000f\u001a\u0012\u0010\u0010\u001a\u00020\u0001*\u00020\t2\u0006\u0010\n\u001a\u00020\u000b\u001a\u0012\u0010\u0010\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\n\u001a\u00020\u000b\u001a\u0010\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00020\u0012*\u00020\t\u001a\u0012\u0010\u0013\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0006\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003¨\u0006\u0014"}, d2 = {"isBoxedArray", "", "Lorg/jetbrains/kotlin/ir/types/IrType;", "(Lorg/jetbrains/kotlin/ir/types/IrType;)Z", "getArrayElementType", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "isNullable", "isStrictSubtypeOfClass", "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "superClass", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "isSubtypeOf", "superType", "typeSystem", "Lorg/jetbrains/kotlin/ir/types/IrTypeSystemContext;", "isSubtypeOfClass", "superTypes", "", "toArrayOrPrimitiveArrayType", "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrTypeUtilsKt {

    /* JADX INFO: compiled from: IrTypeUtils.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[SimpleTypeNullability.values().length];
            try {
                iArr[SimpleTypeNullability.MARKED_NULLABLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[SimpleTypeNullability.NOT_SPECIFIED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[SimpleTypeNullability.DEFINITELY_NOT_NULL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final List<IrType> superTypes(IrClassifierSymbol irClassifierSymbol) {
        Intrinsics.checkNotNullParameter(irClassifierSymbol, "<this>");
        if (irClassifierSymbol instanceof IrClassSymbol) {
            return ((IrClassSymbol) irClassifierSymbol).getOwner().getSuperTypes();
        }
        if (irClassifierSymbol instanceof IrTypeParameterSymbol) {
            return ((IrTypeParameterSymbol) irClassifierSymbol).getOwner().getSuperTypes();
        }
        if (irClassifierSymbol instanceof IrScriptSymbol) {
            return CollectionsKt.emptyList();
        }
        throw new NoWhenBranchMatchedException();
    }

    public static final boolean isSubtypeOfClass(IrClassifierSymbol irClassifierSymbol, IrClassSymbol superClass) {
        Intrinsics.checkNotNullParameter(irClassifierSymbol, "<this>");
        Intrinsics.checkNotNullParameter(superClass, "superClass");
        return FqNameEqualityChecker.INSTANCE.areEqual(irClassifierSymbol, superClass) || isStrictSubtypeOfClass(irClassifierSymbol, superClass);
    }

    public static final boolean isStrictSubtypeOfClass(IrClassifierSymbol irClassifierSymbol, IrClassSymbol superClass) {
        Intrinsics.checkNotNullParameter(irClassifierSymbol, "<this>");
        Intrinsics.checkNotNullParameter(superClass, "superClass");
        List<IrType> listSuperTypes = superTypes(irClassifierSymbol);
        if ((listSuperTypes instanceof Collection) && listSuperTypes.isEmpty()) {
            return false;
        }
        Iterator<T> it = listSuperTypes.iterator();
        while (it.hasNext()) {
            if (isSubtypeOfClass((IrType) it.next(), superClass)) {
                return true;
            }
        }
        return false;
    }

    public static final boolean isSubtypeOfClass(IrType irType, IrClassSymbol superClass) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        Intrinsics.checkNotNullParameter(superClass, "superClass");
        return (irType instanceof IrSimpleType) && isSubtypeOfClass(((IrSimpleType) irType).getClassifier(), superClass);
    }

    public static final boolean isStrictSubtypeOfClass(IrType irType, IrClassSymbol superClass) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        Intrinsics.checkNotNullParameter(superClass, "superClass");
        return (irType instanceof IrSimpleType) && isStrictSubtypeOfClass(((IrSimpleType) irType).getClassifier(), superClass);
    }

    public static final boolean isSubtypeOf(IrType irType, IrType superType, IrTypeSystemContext typeSystem) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        Intrinsics.checkNotNullParameter(superType, "superType");
        Intrinsics.checkNotNullParameter(typeSystem, "typeSystem");
        return AbstractTypeChecker.isSubtypeOf$default(AbstractTypeChecker.INSTANCE, IrTypeCheckerStateKt.createIrTypeCheckerState(typeSystem), (KotlinTypeMarker) irType, (KotlinTypeMarker) superType, false, 8, (Object) null);
    }

    public static final boolean isNullable(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        if (irType instanceof IrSimpleType) {
            IrSimpleType irSimpleType = (IrSimpleType) irType;
            IrClassifierSymbol classifier = irSimpleType.getClassifier();
            if (classifier instanceof IrClassSymbol) {
                if (irSimpleType.getNullability() != SimpleTypeNullability.MARKED_NULLABLE) {
                    return false;
                }
            } else if (classifier instanceof IrTypeParameterSymbol) {
                int i = WhenMappings.$EnumSwitchMapping$0[irSimpleType.getNullability().ordinal()];
                if (i != 1) {
                    if (i != 2) {
                        if (i == 3) {
                            return false;
                        }
                        throw new NoWhenBranchMatchedException();
                    }
                    List<IrType> superTypes = ((IrTypeParameterSymbol) classifier).getOwner().getSuperTypes();
                    if ((superTypes instanceof Collection) && superTypes.isEmpty()) {
                        return false;
                    }
                    Iterator<T> it = superTypes.iterator();
                    while (it.hasNext()) {
                        if (isNullable((IrType) it.next())) {
                        }
                    }
                    return false;
                }
            } else {
                if (!(classifier instanceof IrScriptSymbol)) {
                    throw new NoWhenBranchMatchedException();
                }
                if (irSimpleType.getNullability() != SimpleTypeNullability.MARKED_NULLABLE) {
                    return false;
                }
            }
        } else if (!(irType instanceof IrDynamicType)) {
            if (irType instanceof IrErrorType) {
                return ((IrErrorType) irType).getIsMarkedNullable();
            }
            return false;
        }
        return true;
    }

    public static final boolean isBoxedArray(IrType irType) {
        IrClass owner;
        Intrinsics.checkNotNullParameter(irType, "<this>");
        IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(irType);
        return Intrinsics.areEqual((classOrNull == null || (owner = classOrNull.getOwner()) == null) ? null : IrUtilsKt.getFqNameWhenAvailable(owner), StandardNames.FqNames.array.toSafe());
    }

    public static final IrType getArrayElementType(IrType irType, IrBuiltIns irBuiltIns) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
        if (isBoxedArray(irType)) {
            IrTypeArgument irTypeArgument = (IrTypeArgument) CollectionsKt.singleOrNull((List) ((IrSimpleType) irType).getArguments());
            if (irTypeArgument instanceof IrTypeProjection) {
                return ((IrTypeProjection) irTypeArgument).getType();
            }
            if (irTypeArgument instanceof IrStarProjection) {
                return irBuiltIns.getAnyNType();
            }
            if (irTypeArgument == null) {
                throw new IllegalStateException("Unexpected array argument type: null".toString());
            }
            throw new NoWhenBranchMatchedException();
        }
        IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(irType);
        Intrinsics.checkNotNull(classOrNull);
        IrType irType2 = irBuiltIns.getPrimitiveArrayElementTypes().get(classOrNull);
        if (irType2 != null) {
            return irType2;
        }
        IrType irType3 = irBuiltIns.getUnsignedArraysElementTypes().get(classOrNull);
        if (irType3 != null) {
            return irType3;
        }
        throw new AssertionError("Primitive array expected: " + classOrNull);
    }

    public static final IrType toArrayOrPrimitiveArrayType(IrType irType, IrBuiltIns irBuiltIns) {
        IrType defaultType;
        Intrinsics.checkNotNullParameter(irType, "<this>");
        Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
        if (!IrTypePredicatesKt.isPrimitiveType$default(irType, false, 1, null)) {
            return IrTypesKt.typeWith(irBuiltIns.getArrayClass(), irType);
        }
        IrClassSymbol irClassSymbol = irBuiltIns.getPrimitiveArrayForType().get(irType);
        if (irClassSymbol == null || (defaultType = IrTypesKt.getDefaultType(irClassSymbol)) == null) {
            throw new AssertionError(irType + " not in primitiveArrayForType");
        }
        return defaultType;
    }
}
