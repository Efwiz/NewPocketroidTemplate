package org.jetbrains.kotlin.fir.scopes.impl;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.scopes.FirOverrideChecker;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;

/* JADX INFO: compiled from: AbstractFirOverrideScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\"\n\u0000\b&\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J(\u0010\u0010\u001a\b\u0012\u0002\b\u0003\u0018\u00010\t*\u0006\u0012\u0002\b\u00030\t2\u0010\u0010\u0011\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\t0\u0012H\u0014R'\u0010\u0007\u001a\u0018\u0012\b\u0012\u0006\u0012\u0002\b\u00030\t\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\t0\b¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\u0004\u001a\u00020\u0005X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/AbstractFirOverrideScope;", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "overrideChecker", "Lorg/jetbrains/kotlin/fir/scopes/FirOverrideChecker;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/scopes/FirOverrideChecker;)V", "overrideByBase", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "getOverrideByBase", "()Ljava/util/Map;", "getOverrideChecker", "()Lorg/jetbrains/kotlin/fir/scopes/FirOverrideChecker;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "getOverridden", "overrideCandidates", "", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractFirOverrideScope extends FirTypeScope {
    private final Map<FirCallableSymbol<?>, FirCallableSymbol<?>> overrideByBase;
    private final FirOverrideChecker overrideChecker;
    private final FirSession session;

    public final FirSession getSession() {
        return this.session;
    }

    protected final FirOverrideChecker getOverrideChecker() {
        return this.overrideChecker;
    }

    public AbstractFirOverrideScope(FirSession session, FirOverrideChecker overrideChecker) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(overrideChecker, "overrideChecker");
        this.session = session;
        this.overrideChecker = overrideChecker;
        this.overrideByBase = new LinkedHashMap();
    }

    public final Map<FirCallableSymbol<?>, FirCallableSymbol<?>> getOverrideByBase() {
        return this.overrideByBase;
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected FirCallableSymbol<?> getOverridden(FirCallableSymbol<?> firCallableSymbol, Set<? extends FirCallableSymbol<?>> overrideCandidates) {
        Intrinsics.checkNotNullParameter(firCallableSymbol, "<this>");
        Intrinsics.checkNotNullParameter(overrideCandidates, "overrideCandidates");
        FirCallableSymbol<?> firCallableSymbol2 = this.overrideByBase.get(firCallableSymbol);
        if (firCallableSymbol2 != null) {
            return firCallableSymbol2;
        }
        Object fir = firCallableSymbol.getFir();
        Intrinsics.checkNotNull(fir, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration");
        FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) fir;
        for (Object obj : overrideCandidates) {
            FirCallableSymbol firCallableSymbol3 = (FirCallableSymbol) obj;
            Intrinsics.checkNotNull(firCallableSymbol3, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.FirBasedSymbol<*>");
            D fir2 = firCallableSymbol3.getFir();
            Intrinsics.checkNotNull(fir2, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration");
            FirCallableDeclaration firCallableDeclaration2 = (FirCallableDeclaration) fir2;
            if (firCallableDeclaration.getStatus().getModality() != Modality.FINAL && AbstractFirOverrideScopeKt.similarFunctionsOrBothProperties(this.overrideChecker, firCallableDeclaration2, firCallableDeclaration)) {
                FirCallableSymbol<?> firCallableSymbol4 = (FirCallableSymbol) obj;
                this.overrideByBase.put(firCallableSymbol, firCallableSymbol4);
                return firCallableSymbol4;
            }
        }
        obj = null;
        FirCallableSymbol<?> firCallableSymbol5 = (FirCallableSymbol) obj;
        this.overrideByBase.put(firCallableSymbol, firCallableSymbol5);
        return firCallableSymbol5;
    }
}
