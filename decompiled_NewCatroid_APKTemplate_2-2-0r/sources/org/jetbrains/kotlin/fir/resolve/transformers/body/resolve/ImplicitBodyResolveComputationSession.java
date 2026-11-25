package org.jetbrains.kotlin.fir.resolve.transformers.body.resolve;

import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.synthetic.FirSyntheticProperty;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirSyntheticPropertySymbol;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirImplicitBodyResolve.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0019\u0010\b\u001a\u00020\u00062\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\u0005H\u0000¢\u0006\u0002\b\nJ\u0012\u0010\u000b\u001a\u00020\f2\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\u0005J\u001a\u0010\r\u001a\u00020\f2\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\u00052\u0006\u0010\u000e\u001a\u00020\u000fR2\u0010\u0003\u001a&\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0005\u0012\u0004\u0012\u00020\u00060\u0004j\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0005\u0012\u0004\u0012\u00020\u0006`\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/ImplicitBodyResolveComputationSession;", "", "()V", "implicitBodyResolveStatusMap", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/ImplicitBodyResolveComputationStatus;", "Lkotlin/collections/HashMap;", "getStatus", NativeSymbol.TYPE_NAME, "getStatus$resolve", "startComputing", "", "storeResult", "transformedDeclaration", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ImplicitBodyResolveComputationSession {
    private final HashMap<FirCallableSymbol<?>, ImplicitBodyResolveComputationStatus> implicitBodyResolveStatusMap = new HashMap<>();

    /* JADX WARN: Multi-variable type inference failed */
    public final ImplicitBodyResolveComputationStatus getStatus$resolve(FirCallableSymbol<?> symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        if (symbol instanceof FirSyntheticPropertySymbol) {
            FirProperty firProperty = (FirProperty) ((FirSyntheticPropertySymbol) symbol).getFir();
            if (firProperty instanceof FirSyntheticProperty) {
                return getStatus$resolve(((FirSyntheticProperty) firProperty).getGetter().getDelegate().getSymbol());
            }
        }
        ImplicitBodyResolveComputationStatus implicitBodyResolveComputationStatus = this.implicitBodyResolveStatusMap.get(symbol);
        return implicitBodyResolveComputationStatus == null ? ImplicitBodyResolveComputationStatus.NotComputed.INSTANCE : implicitBodyResolveComputationStatus;
    }

    public final void startComputing(FirCallableSymbol<?> symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        if (this.implicitBodyResolveStatusMap.get(symbol) != null) {
            throw new IllegalArgumentException(("Unexpected static in startComputing for " + symbol + ": " + this.implicitBodyResolveStatusMap.get(symbol)).toString());
        }
        this.implicitBodyResolveStatusMap.put(symbol, ImplicitBodyResolveComputationStatus.Computing.INSTANCE);
    }

    public final void storeResult(FirCallableSymbol<?> symbol, FirCallableDeclaration transformedDeclaration) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(transformedDeclaration, "transformedDeclaration");
        if (!Intrinsics.areEqual(this.implicitBodyResolveStatusMap.get(symbol), ImplicitBodyResolveComputationStatus.Computing.INSTANCE)) {
            throw new IllegalArgumentException(("Unexpected static in storeResult for " + symbol + ": " + this.implicitBodyResolveStatusMap.get(symbol)).toString());
        }
        FirTypeRef returnTypeRef = transformedDeclaration.getReturnTypeRef();
        if (!(returnTypeRef instanceof FirResolvedTypeRef)) {
            throw new IllegalArgumentException(("Not FirResolvedTypeRef (" + UtilsKt.render(transformedDeclaration.getReturnTypeRef()) + ") in storeResult for: " + UtilsKt.render(symbol.getFir())).toString());
        }
        this.implicitBodyResolveStatusMap.put(symbol, new ImplicitBodyResolveComputationStatus.Computed((FirResolvedTypeRef) returnTypeRef, transformedDeclaration));
    }
}
