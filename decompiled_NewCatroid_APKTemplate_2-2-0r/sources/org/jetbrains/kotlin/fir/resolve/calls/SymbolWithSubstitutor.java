package org.jetbrains.kotlin.fir.resolve.calls;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: ConstructorProcessing.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u0019\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\r\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J!\u0010\r\u001a\u00020\u00002\f\b\u0002\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0015\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/SymbolWithSubstitutor;", "", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;", "substitutor", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;)V", "getSubstitutor", "()Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
final /* data */ class SymbolWithSubstitutor {
    private final ConeSubstitutor substitutor;
    private final FirClassifierSymbol<?> symbol;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ SymbolWithSubstitutor copy$default(SymbolWithSubstitutor symbolWithSubstitutor, FirClassifierSymbol firClassifierSymbol, ConeSubstitutor coneSubstitutor, int i, Object obj) {
        if ((i & 1) != 0) {
            firClassifierSymbol = symbolWithSubstitutor.symbol;
        }
        if ((i & 2) != 0) {
            coneSubstitutor = symbolWithSubstitutor.substitutor;
        }
        return symbolWithSubstitutor.copy(firClassifierSymbol, coneSubstitutor);
    }

    public final FirClassifierSymbol<?> component1() {
        return this.symbol;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final ConeSubstitutor getSubstitutor() {
        return this.substitutor;
    }

    public final SymbolWithSubstitutor copy(FirClassifierSymbol<?> symbol, ConeSubstitutor substitutor) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        return new SymbolWithSubstitutor(symbol, substitutor);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SymbolWithSubstitutor)) {
            return false;
        }
        SymbolWithSubstitutor symbolWithSubstitutor = (SymbolWithSubstitutor) other;
        return Intrinsics.areEqual(this.symbol, symbolWithSubstitutor.symbol) && Intrinsics.areEqual(this.substitutor, symbolWithSubstitutor.substitutor);
    }

    public int hashCode() {
        return (this.symbol.hashCode() * 31) + this.substitutor.hashCode();
    }

    public String toString() {
        return "SymbolWithSubstitutor(symbol=" + this.symbol + ", substitutor=" + this.substitutor + ')';
    }

    public SymbolWithSubstitutor(FirClassifierSymbol<?> symbol, ConeSubstitutor substitutor) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        this.symbol = symbol;
        this.substitutor = substitutor;
    }

    public final ConeSubstitutor getSubstitutor() {
        return this.substitutor;
    }

    public final FirClassifierSymbol<?> getSymbol() {
        return this.symbol;
    }
}
