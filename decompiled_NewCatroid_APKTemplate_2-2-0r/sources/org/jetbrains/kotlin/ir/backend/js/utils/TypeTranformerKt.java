package org.jetbrains.kotlin.ir.backend.js.utils;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrDynamicType;
import org.jetbrains.kotlin.ir.types.IrErrorType;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: TypeTranformer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0007\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\b\u001a\u00020\t\"\u001a\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0017\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0006¨\u0006\n"}, d2 = {"erasedUpperBound", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "getErasedUpperBound", "(Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;)Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "(Lorg/jetbrains/kotlin/ir/types/IrType;)Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "eraseGenerics", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "backend.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class TypeTranformerKt {
    public static final IrType eraseGenerics(IrType irType, IrBuiltIns irBuiltIns) {
        IrSimpleType defaultType;
        Intrinsics.checkNotNullParameter(irType, "<this>");
        Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
        if ((irType instanceof IrDynamicType) || (irType instanceof IrErrorType)) {
            return irType;
        }
        IrClass erasedUpperBound = getErasedUpperBound(irType);
        IrSimpleType anyType = (erasedUpperBound == null || (defaultType = IrUtilsKt.getDefaultType(erasedUpperBound)) == null) ? irBuiltIns.getAnyType() : defaultType;
        return !org.jetbrains.kotlin.ir.types.IrTypeUtilsKt.isNullable(irType) ? anyType : IrTypesKt.makeNullable(anyType);
    }

    private static final IrClass getErasedUpperBound(IrTypeParameter irTypeParameter) {
        Iterator<IrType> it = irTypeParameter.getSuperTypes().iterator();
        while (it.hasNext()) {
            IrClass erasedUpperBound = getErasedUpperBound(it.next());
            if (erasedUpperBound != null) {
                return erasedUpperBound;
            }
        }
        return null;
    }

    public static final IrClass getErasedUpperBound(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        IrClassifierSymbol classifierOrNull = IrTypesKt.getClassifierOrNull(irType);
        if (classifierOrNull instanceof IrClassSymbol) {
            return ((IrClassSymbol) classifierOrNull).getOwner();
        }
        if (classifierOrNull instanceof IrTypeParameterSymbol) {
            return getErasedUpperBound(((IrTypeParameterSymbol) classifierOrNull).getOwner());
        }
        throw new IllegalStateException();
    }
}
