package org.jetbrains.kotlin.fir.resolve.transformers;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;

/* JADX INFO: compiled from: FirResolveProcessor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B!\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\b\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0010H\u0016R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u0082\u0001\u0002\u0012\u0013¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/FirResolveProcessor;", "", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", TypedValues.CycleType.S_WAVE_PHASE, "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;)V", "getPhase", "()Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "getScopeSession", "()Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "afterPhase", "", "beforePhase", "Lorg/jetbrains/kotlin/fir/resolve/transformers/FirGlobalResolveProcessor;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/FirTransformerBasedResolveProcessor;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirResolveProcessor {
    private final FirResolvePhase phase;
    private final ScopeSession scopeSession;
    private final FirSession session;

    public /* synthetic */ FirResolveProcessor(FirSession firSession, ScopeSession scopeSession, FirResolvePhase firResolvePhase, DefaultConstructorMarker defaultConstructorMarker) {
        this(firSession, scopeSession, firResolvePhase);
    }

    private FirResolveProcessor(FirSession firSession, ScopeSession scopeSession, FirResolvePhase firResolvePhase) {
        this.session = firSession;
        this.scopeSession = scopeSession;
        this.phase = firResolvePhase;
    }

    public final FirResolvePhase getPhase() {
        return this.phase;
    }

    public final ScopeSession getScopeSession() {
        return this.scopeSession;
    }

    public final FirSession getSession() {
        return this.session;
    }

    public void beforePhase() {
        if (this.phase != null) {
            FirLazyDeclarationResolverKt.getLazyDeclarationResolver(this.session).startResolvingPhase(this.phase);
        }
    }

    public void afterPhase() {
        if (this.phase != null) {
            FirLazyDeclarationResolverKt.getLazyDeclarationResolver(this.session).finishResolvingPhase(this.phase);
        }
    }
}
