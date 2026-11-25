package org.jetbrains.kotlin.ir.symbols;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrReturnTarget;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;

/* JADX INFO: compiled from: IrDelegatingSymbol.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u0014\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0002B\r\u0012\u0006\u0010\u0005\u001a\u00020\u0001¢\u0006\u0002\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/ir/symbols/IrDelegatingSimpleFunctionSymbolImpl;", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "Lorg/jetbrains/kotlin/ir/symbols/IrDelegatingSymbol;", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "(Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrDelegatingSimpleFunctionSymbolImpl extends IrDelegatingSymbol<IrSimpleFunctionSymbol, IrSimpleFunction, FunctionDescriptor> implements IrSimpleFunctionSymbol {
    @Override // org.jetbrains.kotlin.ir.symbols.IrDelegatingSymbol, org.jetbrains.kotlin.ir.symbols.IrBindableSymbol, org.jetbrains.kotlin.ir.symbols.IrSymbol
    public /* bridge */ /* synthetic */ FunctionDescriptor getDescriptor() {
        return (FunctionDescriptor) getDescriptor();
    }

    @Override // org.jetbrains.kotlin.ir.symbols.IrDelegatingSymbol, org.jetbrains.kotlin.ir.symbols.IrBindableSymbol, org.jetbrains.kotlin.ir.symbols.IrSymbol
    public /* bridge */ /* synthetic */ IrFunction getOwner() {
        return (IrFunction) getOwner();
    }

    @Override // org.jetbrains.kotlin.ir.symbols.IrDelegatingSymbol, org.jetbrains.kotlin.ir.symbols.IrBindableSymbol, org.jetbrains.kotlin.ir.symbols.IrSymbol
    public /* bridge */ /* synthetic */ IrReturnTarget getOwner() {
        return (IrReturnTarget) getOwner();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrDelegatingSimpleFunctionSymbolImpl(IrSimpleFunctionSymbol delegate) {
        super(delegate);
        Intrinsics.checkNotNullParameter(delegate, "delegate");
    }
}
