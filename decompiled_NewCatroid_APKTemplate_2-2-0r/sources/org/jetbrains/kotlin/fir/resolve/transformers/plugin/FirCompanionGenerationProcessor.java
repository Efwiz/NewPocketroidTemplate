package org.jetbrains.kotlin.fir.resolve.transformers.plugin;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.transformers.FirTransformerBasedResolveProcessor;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;

/* JADX INFO: compiled from: FirCompanionGenerationProcessor.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u001c\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/FirCompanionGenerationProcessor;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/FirTransformerBasedResolveProcessor;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;)V", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "", "getTransformer", "()Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirCompanionGenerationProcessor extends FirTransformerBasedResolveProcessor {
    private final FirTransformer transformer;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirCompanionGenerationProcessor(FirSession session, ScopeSession scopeSession) {
        super(session, scopeSession, FirResolvePhase.COMPILER_REQUIRED_ANNOTATIONS);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        this.transformer = new FirCompanionGenerationTransformer(session);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.FirTransformerBasedResolveProcessor
    public FirTransformer getTransformer() {
        return this.transformer;
    }
}
