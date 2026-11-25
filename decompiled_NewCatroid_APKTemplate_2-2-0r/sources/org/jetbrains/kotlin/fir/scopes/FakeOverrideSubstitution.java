package org.jetbrains.kotlin.fir.scopes;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: FakeOverrideTypeCalculator.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\r\u0010\f\u001a\u0006\u0012\u0002\b\u00030\u0005HÆ\u0003J!\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\f\b\u0002\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0015\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/FakeOverrideSubstitution;", "", "substitutor", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "baseSymbol", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "(Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;)V", "getBaseSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "getSubstitutor", "()Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class FakeOverrideSubstitution {
    private final FirBasedSymbol<?> baseSymbol;
    private final ConeSubstitutor substitutor;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ FakeOverrideSubstitution copy$default(FakeOverrideSubstitution fakeOverrideSubstitution, ConeSubstitutor coneSubstitutor, FirBasedSymbol firBasedSymbol, int i, Object obj) {
        if ((i & 1) != 0) {
            coneSubstitutor = fakeOverrideSubstitution.substitutor;
        }
        if ((i & 2) != 0) {
            firBasedSymbol = fakeOverrideSubstitution.baseSymbol;
        }
        return fakeOverrideSubstitution.copy(coneSubstitutor, firBasedSymbol);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final ConeSubstitutor getSubstitutor() {
        return this.substitutor;
    }

    public final FirBasedSymbol<?> component2() {
        return this.baseSymbol;
    }

    public final FakeOverrideSubstitution copy(ConeSubstitutor substitutor, FirBasedSymbol<?> baseSymbol) {
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        Intrinsics.checkNotNullParameter(baseSymbol, "baseSymbol");
        return new FakeOverrideSubstitution(substitutor, baseSymbol);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof FakeOverrideSubstitution)) {
            return false;
        }
        FakeOverrideSubstitution fakeOverrideSubstitution = (FakeOverrideSubstitution) other;
        return Intrinsics.areEqual(this.substitutor, fakeOverrideSubstitution.substitutor) && Intrinsics.areEqual(this.baseSymbol, fakeOverrideSubstitution.baseSymbol);
    }

    public int hashCode() {
        return (this.substitutor.hashCode() * 31) + this.baseSymbol.hashCode();
    }

    public String toString() {
        return "FakeOverrideSubstitution(substitutor=" + this.substitutor + ", baseSymbol=" + this.baseSymbol + ')';
    }

    public FakeOverrideSubstitution(ConeSubstitutor substitutor, FirBasedSymbol<?> baseSymbol) {
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        Intrinsics.checkNotNullParameter(baseSymbol, "baseSymbol");
        this.substitutor = substitutor;
        this.baseSymbol = baseSymbol;
    }

    public final ConeSubstitutor getSubstitutor() {
        return this.substitutor;
    }

    public final FirBasedSymbol<?> getBaseSymbol() {
        return this.baseSymbol;
    }
}
