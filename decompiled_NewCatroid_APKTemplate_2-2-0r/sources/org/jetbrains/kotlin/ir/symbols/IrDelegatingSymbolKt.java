package org.jetbrains.kotlin.ir.symbols;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;

/* JADX INFO: compiled from: IrDelegatingSymbol.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a.\u0010\u0000\u001a\"\u0012\u0012\b\u0001\u0012\u000e\u0012\u0002\b\u0003\u0012\u0006\b\u0001\u0012\u00020\u00030\u0002\u0012\u0006\b\u0001\u0012\u00020\u0003\u0012\u0002\b\u00030\u00012\u0006\u0010\u0004\u001a\u00020\u0005¨\u0006\u0006"}, d2 = {"wrapInDelegatedSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrDelegatingSymbol;", "Lorg/jetbrains/kotlin/ir/symbols/IrBindableSymbol;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrDelegatingSymbolKt {
    public static final IrDelegatingSymbol<? extends IrBindableSymbol<?, ? extends IrDeclaration>, ? extends IrDeclaration, ?> wrapInDelegatedSymbol(IrSymbol delegate) {
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        if (delegate instanceof IrClassSymbol) {
            return new IrDelegatingClassSymbolImpl((IrClassSymbol) delegate);
        }
        if (delegate instanceof IrEnumEntrySymbol) {
            return new IrDelegatingEnumEntrySymbolImpl((IrEnumEntrySymbol) delegate);
        }
        if (delegate instanceof IrSimpleFunctionSymbol) {
            return new IrDelegatingSimpleFunctionSymbolImpl((IrSimpleFunctionSymbol) delegate);
        }
        if (delegate instanceof IrConstructorSymbol) {
            return new IrDelegatingConstructorSymbolImpl((IrConstructorSymbol) delegate);
        }
        if (delegate instanceof IrPropertySymbol) {
            return new IrDelegatingPropertySymbolImpl((IrPropertySymbol) delegate);
        }
        if (delegate instanceof IrTypeAliasSymbol) {
            return new IrDelegatingTypeAliasSymbolImpl((IrTypeAliasSymbol) delegate);
        }
        throw new IllegalStateException(("Unexpected symbol to delegate to: " + delegate).toString());
    }
}
