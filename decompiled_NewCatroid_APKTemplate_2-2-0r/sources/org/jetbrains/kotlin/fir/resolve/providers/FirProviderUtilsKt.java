package org.jetbrains.kotlin.fir.resolve.providers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.resolve.calls.FirSyntheticFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.name.ClassId;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirProviderUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0018\u0010\u0000\u001a\u0004\u0018\u00010\u0001*\u00020\u00022\n\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0004¨\u0006\u0005"}, d2 = {"getContainingFile", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirProvider;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "providers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirProviderUtilsKt {
    public static final FirFile getContainingFile(FirProvider firProvider, FirBasedSymbol<?> symbol) {
        Intrinsics.checkNotNullParameter(firProvider, "<this>");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        if (symbol instanceof FirSyntheticFunctionSymbol) {
            FirSyntheticFunctionSymbol firSyntheticFunctionSymbol = (FirSyntheticFunctionSymbol) symbol;
            return firProvider.getFirClassifierContainerFile(new ClassId(firSyntheticFunctionSymbol.getCallableId().getPackageName(), firSyntheticFunctionSymbol.getCallableId().getCallableName()));
        }
        if (symbol instanceof FirClassLikeSymbol) {
            return firProvider.getFirClassifierContainerFileIfAny((FirClassLikeSymbol<?>) symbol);
        }
        if (symbol instanceof FirCallableSymbol) {
            return firProvider.getFirCallableContainerFile((FirCallableSymbol) symbol);
        }
        return null;
    }
}
