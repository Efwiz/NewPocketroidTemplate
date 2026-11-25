package org.jetbrains.kotlin.fir.renderer;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirEnumEntrySymbol;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirSymbolRendererWithStaticFlag.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0006H\u0014¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/renderer/FirSymbolRendererWithStaticFlag;", "Lorg/jetbrains/kotlin/fir/renderer/FirSymbolRenderer;", "()V", "renderReference", "", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirSymbolRendererWithStaticFlag extends FirSymbolRenderer {
    @Override // org.jetbrains.kotlin.fir.renderer.FirSymbolRenderer
    protected String renderReference(FirBasedSymbol<?> symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        if (!(symbol instanceof FirCallableSymbol)) {
            return super.renderReference(symbol);
        }
        FirCallableSymbol firCallableSymbol = (FirCallableSymbol) symbol;
        return firCallableSymbol.getCallableId() + (((symbol instanceof FirEnumEntrySymbol) || !firCallableSymbol.getRawStatus().isStatic()) ? "" : "*s");
    }
}
