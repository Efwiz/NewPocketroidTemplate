package org.jetbrains.kotlin.backend.jvm.ir;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.JvmSymbols;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.types.model.FlexibleTypeMarker;

/* JADX INFO: compiled from: IrJvmFlexibleType.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u001a\u0016\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0000\u001a\f\u0010\u0005\u001a\u00020\u0006*\u00020\u0002H\u0000\u001a\n\u0010\u0007\u001a\u00020\u0006*\u00020\u0002¨\u0006\b"}, d2 = {"asJvmFlexibleType", "Lorg/jetbrains/kotlin/types/model/FlexibleTypeMarker;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "builtIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "isWithFlexibleMutability", "", "isWithFlexibleNullability", "backend.jvm"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrJvmFlexibleTypeKt {
    public static final boolean isWithFlexibleNullability(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return IrUtilsKt.hasAnnotation(irType, JvmSymbols.INSTANCE.getFLEXIBLE_NULLABILITY_ANNOTATION_FQ_NAME());
    }

    public static final boolean isWithFlexibleMutability(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return IrUtilsKt.hasAnnotation(irType, JvmSymbols.INSTANCE.getFLEXIBLE_MUTABILITY_ANNOTATION_FQ_NAME());
    }

    public static final FlexibleTypeMarker asJvmFlexibleType(IrType irType, IrBuiltIns builtIns) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        if (!(irType instanceof IrSimpleType) || irType.getAnnotations().isEmpty()) {
            return null;
        }
        boolean zIsWithFlexibleNullability = isWithFlexibleNullability(irType);
        boolean zIsWithFlexibleMutability = isWithFlexibleMutability(irType);
        boolean zIsRawType = JvmIrUtilsKt.isRawType((IrSimpleType) irType);
        if (!zIsWithFlexibleNullability && !zIsWithFlexibleMutability && !zIsRawType) {
            return null;
        }
        IrType irTypeRemoveAnnotations = IrTypesKt.removeAnnotations(irType, new Function1<IrConstructorCall, Boolean>() { // from class: org.jetbrains.kotlin.backend.jvm.ir.IrJvmFlexibleTypeKt$asJvmFlexibleType$baseType$1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrConstructorCall irCtorCall) {
                Intrinsics.checkNotNullParameter(irCtorCall, "irCtorCall");
                FqName classFqName = IrTypesKt.getClassFqName(irCtorCall.getType());
                return Boolean.valueOf(Intrinsics.areEqual(classFqName, JvmSymbols.INSTANCE.getFLEXIBLE_NULLABILITY_ANNOTATION_FQ_NAME()) || Intrinsics.areEqual(classFqName, JvmSymbols.INSTANCE.getFLEXIBLE_MUTABILITY_ANNOTATION_FQ_NAME()) || Intrinsics.areEqual(classFqName, JvmSymbols.INSTANCE.getRAW_TYPE_ANNOTATION_FQ_NAME()));
            }
        });
        Intrinsics.checkNotNull(irTypeRemoveAnnotations, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.types.IrSimpleType");
        return new IrJvmFlexibleTypeImpl((IrSimpleType) irTypeRemoveAnnotations, builtIns, zIsWithFlexibleNullability, zIsWithFlexibleMutability, zIsRawType);
    }
}
