package org.jetbrains.kotlin.ir.symbols.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrReturnTarget;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;

/* JADX INFO: compiled from: IrPrivateSymbolBase.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u00012\u00020\u0004B\u0011\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0002¢\u0006\u0002\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/ir/symbols/impl/IrSimpleFunctionSymbolImpl;", "Lorg/jetbrains/kotlin/ir/symbols/impl/IrBindableSymbolBase;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "descriptor", "(Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrSimpleFunctionSymbolImpl extends IrBindableSymbolBase<FunctionDescriptor, IrSimpleFunction> implements IrSimpleFunctionSymbol {
    /* JADX WARN: Multi-variable type inference failed */
    public IrSimpleFunctionSymbolImpl() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public /* synthetic */ IrSimpleFunctionSymbolImpl(FunctionDescriptor functionDescriptor, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : functionDescriptor);
    }

    @Override // org.jetbrains.kotlin.ir.symbols.impl.IrSymbolBase, org.jetbrains.kotlin.ir.symbols.IrSymbol
    public /* bridge */ /* synthetic */ FunctionDescriptor getDescriptor() {
        return (FunctionDescriptor) getDescriptor();
    }

    @Override // org.jetbrains.kotlin.ir.symbols.impl.IrBindableSymbolBase, org.jetbrains.kotlin.ir.symbols.IrSymbol
    public /* bridge */ /* synthetic */ IrFunction getOwner() {
        return (IrFunction) getOwner();
    }

    @Override // org.jetbrains.kotlin.ir.symbols.impl.IrBindableSymbolBase, org.jetbrains.kotlin.ir.symbols.IrSymbol
    public /* bridge */ /* synthetic */ IrReturnTarget getOwner() {
        return (IrReturnTarget) getOwner();
    }

    public IrSimpleFunctionSymbolImpl(FunctionDescriptor functionDescriptor) {
        super(functionDescriptor);
    }
}
