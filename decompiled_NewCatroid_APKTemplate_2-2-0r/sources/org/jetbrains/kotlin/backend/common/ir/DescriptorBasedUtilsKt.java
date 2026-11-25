package org.jetbrains.kotlin.backend.common.ir;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;

/* JADX INFO: compiled from: descriptorBasedUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003\"\u0015\u0010\u0004\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0003¨\u0006\u0005"}, d2 = {"isExpect", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;)Z", "isProperExpect", "ir.backend.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DescriptorBasedUtilsKt {
    public static final boolean isExpect(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        return ((irDeclaration instanceof IrClass) && ((IrClass) irDeclaration).getIsExpect()) || ((irDeclaration instanceof IrFunction) && ((IrFunction) irDeclaration).getIsExpect()) || ((irDeclaration instanceof IrProperty) && ((IrProperty) irDeclaration).getIsExpect());
    }

    public static final boolean isProperExpect(IrDeclaration irDeclaration) {
        boolean z;
        boolean z2;
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        boolean z3 = irDeclaration instanceof IrClass;
        if ((!z3 || !((IrClass) irDeclaration).getIsExpect()) && ((!((z = irDeclaration instanceof IrFunction)) || !((IrFunction) irDeclaration).getIsExpect()) && (!((z2 = irDeclaration instanceof IrProperty)) || !((IrProperty) irDeclaration).getIsExpect()))) {
            if (!z3 && !z && !z2 && !(irDeclaration instanceof IrEnumEntry) && !(irDeclaration instanceof IrTypeParameter)) {
                return false;
            }
            IrDeclarationParent parent = irDeclaration.getParent();
            IrDeclaration irDeclaration2 = parent instanceof IrDeclaration ? (IrDeclaration) parent : null;
            if (!(irDeclaration2 != null ? isProperExpect(irDeclaration2) : false)) {
                return false;
            }
        }
        return true;
    }
}
