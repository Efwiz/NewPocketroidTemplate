package org.jetbrains.kotlin.ir.symbols.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.descriptors.ParameterDescriptor;
import org.jetbrains.kotlin.descriptors.ValueDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;

/* JADX INFO: compiled from: IrPrivateSymbolBase.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u00012\u00020\u0004B\u0011\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0002¢\u0006\u0002\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/ir/symbols/impl/IrValueParameterSymbolImpl;", "Lorg/jetbrains/kotlin/ir/symbols/impl/IrBindableSymbolBase;", "Lorg/jetbrains/kotlin/descriptors/ParameterDescriptor;", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "Lorg/jetbrains/kotlin/ir/symbols/IrValueParameterSymbol;", "descriptor", "(Lorg/jetbrains/kotlin/descriptors/ParameterDescriptor;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrValueParameterSymbolImpl extends IrBindableSymbolBase<ParameterDescriptor, IrValueParameter> implements IrValueParameterSymbol {
    /* JADX WARN: Multi-variable type inference failed */
    public IrValueParameterSymbolImpl() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public /* synthetic */ IrValueParameterSymbolImpl(ParameterDescriptor parameterDescriptor, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : parameterDescriptor);
    }

    @Override // org.jetbrains.kotlin.ir.symbols.impl.IrSymbolBase, org.jetbrains.kotlin.ir.symbols.IrSymbol
    public /* bridge */ /* synthetic */ ValueDescriptor getDescriptor() {
        return (ValueDescriptor) getDescriptor();
    }

    @Override // org.jetbrains.kotlin.ir.symbols.impl.IrBindableSymbolBase, org.jetbrains.kotlin.ir.symbols.IrSymbol
    public /* bridge */ /* synthetic */ IrValueDeclaration getOwner() {
        return (IrValueDeclaration) getOwner();
    }

    public IrValueParameterSymbolImpl(ParameterDescriptor parameterDescriptor) {
        super(parameterDescriptor);
    }
}
