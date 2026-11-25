package org.jetbrains.kotlin.fir.resolve.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.kotlinx.collections.immutable.ImmutableList;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: ConeDiagnostics.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeCyclicTypeBound;", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "bounds", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/ImmutableList;", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;Lkotlinx/collections/immutable/ImmutableList;)V", "getBounds", "()Lkotlinx/collections/immutable/ImmutableList;", "reason", "", "getReason", "()Ljava/lang/String;", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeCyclicTypeBound implements ConeDiagnostic {
    private final ImmutableList<FirTypeRef> bounds;
    private final FirTypeParameterSymbol symbol;

    /* JADX WARN: Multi-variable type inference failed */
    public ConeCyclicTypeBound(FirTypeParameterSymbol symbol, ImmutableList<? extends FirTypeRef> bounds) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(bounds, "bounds");
        this.symbol = symbol;
        this.bounds = bounds;
    }

    public final ImmutableList<FirTypeRef> getBounds() {
        return this.bounds;
    }

    public final FirTypeParameterSymbol getSymbol() {
        return this.symbol;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic
    public String getReason() {
        return "Type parameter " + ((FirTypeParameter) this.symbol.getFir()).getName() + " has cyclic bounds";
    }
}
