package org.jetbrains.kotlin.ir.util;

import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrFunctionWithLateBinding;
import org.jetbrains.kotlin.ir.declarations.IrPropertyWithLateBinding;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.linkage.partial.IrUnimplementedOverridesStrategy;
import org.jetbrains.kotlin.ir.overrides.FakeOverrideBuilderStrategy;
import org.jetbrains.kotlin.ir.symbols.impl.IrPropertySymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrSimpleFunctionSymbolImpl;

/* JADX INFO: compiled from: IrUtils.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0014J\u0018\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0007\u001a\u00020\bH\u0014¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/FakeOverrideBuilderForLowerings;", "Lorg/jetbrains/kotlin/ir/overrides/FakeOverrideBuilderStrategy;", "()V", "linkFunctionFakeOverride", "", "function", "Lorg/jetbrains/kotlin/ir/declarations/IrFunctionWithLateBinding;", "manglerCompatibleMode", "", "linkPropertyFakeOverride", "property", "Lorg/jetbrains/kotlin/ir/declarations/IrPropertyWithLateBinding;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class FakeOverrideBuilderForLowerings extends FakeOverrideBuilderStrategy {
    public FakeOverrideBuilderForLowerings() {
        super(MapsKt.emptyMap(), IrUnimplementedOverridesStrategy.ProcessAsFakeOverrides.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.ir.overrides.FakeOverrideBuilderStrategy
    protected void linkFunctionFakeOverride(IrFunctionWithLateBinding function, boolean manglerCompatibleMode) {
        Intrinsics.checkNotNullParameter(function, "function");
        function.acquireSymbol(new IrSimpleFunctionSymbolImpl(null, 1, null));
    }

    @Override // org.jetbrains.kotlin.ir.overrides.FakeOverrideBuilderStrategy
    protected void linkPropertyFakeOverride(IrPropertyWithLateBinding property, boolean manglerCompatibleMode) {
        Intrinsics.checkNotNullParameter(property, "property");
        IrPropertySymbolImpl irPropertySymbolImpl = new IrPropertySymbolImpl(null, 1, null);
        IrSimpleFunction getter = property.getGetter();
        if (getter != null) {
            getter.setCorrespondingPropertySymbol(irPropertySymbolImpl);
        }
        IrSimpleFunction setter = property.getSetter();
        if (setter != null) {
            setter.setCorrespondingPropertySymbol(irPropertySymbolImpl);
        }
        property.acquireSymbol(irPropertySymbolImpl);
        IrSimpleFunction getter2 = property.getGetter();
        if (getter2 != null) {
            getter2.setCorrespondingPropertySymbol(property.getSymbol());
            IrFunctionWithLateBinding irFunctionWithLateBinding = getter2 instanceof IrFunctionWithLateBinding ? (IrFunctionWithLateBinding) getter2 : null;
            if (irFunctionWithLateBinding == null) {
                throw new IllegalStateException(("Unexpected fake override getter: " + getter2).toString());
            }
            linkFunctionFakeOverride(irFunctionWithLateBinding, manglerCompatibleMode);
        }
        IrSimpleFunction setter2 = property.getSetter();
        if (setter2 != null) {
            setter2.setCorrespondingPropertySymbol(property.getSymbol());
            IrFunctionWithLateBinding irFunctionWithLateBinding2 = setter2 instanceof IrFunctionWithLateBinding ? (IrFunctionWithLateBinding) setter2 : null;
            if (irFunctionWithLateBinding2 == null) {
                throw new IllegalStateException(("Unexpected fake override setter: " + setter2).toString());
            }
            linkFunctionFakeOverride(irFunctionWithLateBinding2, manglerCompatibleMode);
        }
    }
}
