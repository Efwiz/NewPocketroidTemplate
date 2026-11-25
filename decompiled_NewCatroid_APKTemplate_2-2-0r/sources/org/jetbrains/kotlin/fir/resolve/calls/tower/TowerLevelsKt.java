package org.jetbrains.kotlin.fir.resolve.calls.tower;

import kotlin.Metadata;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;

/* JADX INFO: compiled from: TowerLevels.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u001a\u0010\u0010\u0000\u001a\u00020\u0001*\u0006\u0012\u0002\b\u00030\u0002H\u0002¨\u0006\u0003"}, d2 = {"hasExtensionReceiver", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class TowerLevelsKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean hasExtensionReceiver(FirCallableSymbol<?> firCallableSymbol) {
        return ((FirCallableDeclaration) firCallableSymbol.getFir()).getReceiverParameter() != null;
    }
}
