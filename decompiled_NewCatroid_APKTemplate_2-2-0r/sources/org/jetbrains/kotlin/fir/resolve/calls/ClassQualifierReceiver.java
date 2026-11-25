package org.jetbrains.kotlin.fir.resolve.calls;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.scopes.FirScope;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;

/* JADX INFO: compiled from: QualifierReceiver.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B1\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\n\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0016J\n\u0010\u0017\u001a\u0004\u0018\u00010\u0016H\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0015\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/ClassQualifierReceiver;", "Lorg/jetbrains/kotlin/fir/resolve/calls/QualifierReceiver;", "explicitReceiver", "Lorg/jetbrains/kotlin/fir/expressions/FirResolvedQualifier;", "classSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "originalSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "useSiteSession", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "(Lorg/jetbrains/kotlin/fir/expressions/FirResolvedQualifier;Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;)V", "getClassSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "getOriginalSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "getScopeSession", "()Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "getUseSiteSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "callableScope", "Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "classifierScope", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClassQualifierReceiver extends QualifierReceiver {
    private final FirRegularClassSymbol classSymbol;
    private final FirClassLikeSymbol<?> originalSymbol;
    private final ScopeSession scopeSession;
    private final FirSession useSiteSession;

    public final FirRegularClassSymbol getClassSymbol() {
        return this.classSymbol;
    }

    public final FirClassLikeSymbol<?> getOriginalSymbol() {
        return this.originalSymbol;
    }

    public final FirSession getUseSiteSession() {
        return this.useSiteSession;
    }

    public final ScopeSession getScopeSession() {
        return this.scopeSession;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClassQualifierReceiver(FirResolvedQualifier explicitReceiver, FirRegularClassSymbol classSymbol, FirClassLikeSymbol<?> originalSymbol, FirSession useSiteSession, ScopeSession scopeSession) {
        super(explicitReceiver);
        Intrinsics.checkNotNullParameter(explicitReceiver, "explicitReceiver");
        Intrinsics.checkNotNullParameter(classSymbol, "classSymbol");
        Intrinsics.checkNotNullParameter(originalSymbol, "originalSymbol");
        Intrinsics.checkNotNullParameter(useSiteSession, "useSiteSession");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        this.classSymbol = classSymbol;
        this.originalSymbol = originalSymbol;
        this.useSiteSession = useSiteSession;
        this.scopeSession = scopeSession;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.resolve.calls.QualifierReceiver
    public FirScope callableScope() {
        FirClass firClass = (FirRegularClass) this.classSymbol.getFir();
        return firClass.getScopeProvider().getStaticMemberScopeForCallables(firClass, this.useSiteSession, this.scopeSession);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.resolve.calls.QualifierReceiver
    public FirScope classifierScope() {
        FirClass firClass = (FirRegularClass) this.classSymbol.getFir();
        return firClass.getScopeProvider().getNestedClassifierScope(firClass, this.useSiteSession, this.scopeSession);
    }
}
