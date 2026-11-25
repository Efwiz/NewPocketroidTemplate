package org.jetbrains.kotlin.fir.symbols.impl;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirVariable;

/* JADX INFO: compiled from: FirCallableSymbol.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\"\u0019\u0010\u0000\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u00028F¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003¨\u0006\u0004"}, d2 = {"isExtension", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;)Z", Constants.TYPE_TREE}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirCallableSymbolKt {
    public static final boolean isExtension(FirCallableSymbol<?> firCallableSymbol) {
        Intrinsics.checkNotNullParameter(firCallableSymbol, "<this>");
        FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) firCallableSymbol.getFir();
        if (firCallableDeclaration instanceof FirFunction) {
            if (((FirCallableDeclaration) firCallableSymbol.getFir()).getReceiverParameter() != null) {
                return true;
            }
        } else if (firCallableDeclaration instanceof FirProperty) {
            if (((FirCallableDeclaration) firCallableSymbol.getFir()).getReceiverParameter() != null) {
                return true;
            }
        } else if (!(firCallableDeclaration instanceof FirVariable)) {
            throw new NoWhenBranchMatchedException();
        }
        return false;
    }
}
