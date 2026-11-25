package org.jetbrains.kotlin.psi2ir.generators;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;

/* JADX INFO: compiled from: PropertyGenerator.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0000¨\u0006\u0003"}, d2 = {"linkCorrespondingPropertySymbol", "", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "ir.psi2ir"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class PropertyGeneratorKt {
    public static final void linkCorrespondingPropertySymbol(IrProperty irProperty) {
        Intrinsics.checkNotNullParameter(irProperty, "<this>");
        IrField backingField = irProperty.getBackingField();
        if (backingField != null) {
            backingField.setCorrespondingPropertySymbol(irProperty.getSymbol());
        }
        IrSimpleFunction getter = irProperty.getGetter();
        if (getter != null) {
            getter.setCorrespondingPropertySymbol(irProperty.getSymbol());
        }
        IrSimpleFunction setter = irProperty.getSetter();
        if (setter == null) {
            return;
        }
        setter.setCorrespondingPropertySymbol(irProperty.getSymbol());
    }
}
