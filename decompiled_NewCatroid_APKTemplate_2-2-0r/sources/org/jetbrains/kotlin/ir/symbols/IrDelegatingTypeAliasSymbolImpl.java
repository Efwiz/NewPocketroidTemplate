package org.jetbrains.kotlin.ir.symbols;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrTypeAlias;

/* JADX INFO: compiled from: IrDelegatingSymbol.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u0014\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0002B\r\u0012\u0006\u0010\u0005\u001a\u00020\u0001¢\u0006\u0002\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/ir/symbols/IrDelegatingTypeAliasSymbolImpl;", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeAliasSymbol;", "Lorg/jetbrains/kotlin/ir/symbols/IrDelegatingSymbol;", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeAlias;", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "(Lorg/jetbrains/kotlin/ir/symbols/IrTypeAliasSymbol;)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrDelegatingTypeAliasSymbolImpl extends IrDelegatingSymbol<IrTypeAliasSymbol, IrTypeAlias, TypeAliasDescriptor> implements IrTypeAliasSymbol {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrDelegatingTypeAliasSymbolImpl(IrTypeAliasSymbol delegate) {
        super(delegate);
        Intrinsics.checkNotNullParameter(delegate, "delegate");
    }
}
