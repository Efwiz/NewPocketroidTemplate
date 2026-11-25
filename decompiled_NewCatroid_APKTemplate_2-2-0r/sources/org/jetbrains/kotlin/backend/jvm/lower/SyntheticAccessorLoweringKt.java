package org.jetbrains.kotlin.backend.jvm.lower;

import kotlin.Metadata;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrFakeOverrideUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;

/* JADX INFO: compiled from: SyntheticAccessorLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u00020\u0001H\u0002¨\u0006\u0002"}, d2 = {"resolveFakeOverride", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "backend.jvm.lower"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SyntheticAccessorLoweringKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final IrField resolveFakeOverride(IrField irField) {
        IrPropertySymbol correspondingPropertySymbol = irField.getCorrespondingPropertySymbol();
        IrProperty owner = correspondingPropertySymbol != null ? correspondingPropertySymbol.getOwner() : null;
        if (owner == null || !owner.getIsFakeOverride()) {
            return irField;
        }
        IrProperty irProperty = (IrProperty) IrFakeOverrideUtilsKt.resolveFakeOverride$default(owner, false, null, 3, null);
        if (irProperty == null) {
            throw new AssertionError("No real override for " + RenderIrElementKt.render$default(owner, (DumpIrTreeOptions) null, 1, (Object) null));
        }
        IrField backingField = irProperty.getBackingField();
        if (backingField != null) {
            return backingField;
        }
        throw new AssertionError("Fake override property " + RenderIrElementKt.render$default(owner, (DumpIrTreeOptions) null, 1, (Object) null) + " with backing field overrides a real property with no backing field: " + RenderIrElementKt.render$default(irProperty, (DumpIrTreeOptions) null, 1, (Object) null));
    }
}
