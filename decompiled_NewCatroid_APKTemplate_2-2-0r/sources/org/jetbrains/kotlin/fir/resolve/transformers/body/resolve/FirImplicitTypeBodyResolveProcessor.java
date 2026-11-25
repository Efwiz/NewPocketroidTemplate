package org.jetbrains.kotlin.fir.resolve.transformers.body.resolve;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.transformers.FirTransformerBasedResolveProcessor;

/* JADX INFO: compiled from: FirImplicitBodyResolve.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirImplicitTypeBodyResolveProcessor;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/FirTransformerBasedResolveProcessor;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;)V", "transformer", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirImplicitTypeBodyResolveTransformerAdapter;", "getTransformer", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirImplicitTypeBodyResolveTransformerAdapter;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirImplicitTypeBodyResolveProcessor extends FirTransformerBasedResolveProcessor {
    private final FirImplicitTypeBodyResolveTransformerAdapter transformer;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirImplicitTypeBodyResolveProcessor(FirSession session, ScopeSession scopeSession) {
        super(session, scopeSession, FirResolvePhase.IMPLICIT_TYPES_BODY_RESOLVE);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        this.transformer = new FirImplicitTypeBodyResolveTransformerAdapter(session, scopeSession);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.FirTransformerBasedResolveProcessor
    public FirImplicitTypeBodyResolveTransformerAdapter getTransformer() {
        return this.transformer;
    }
}
