package org.jetbrains.kotlin.fir.resolve.transformers.plugin;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.transformers.ReturnTypeCalculator;
import org.jetbrains.kotlin.fir.resolve.transformers.ReturnTypeCalculatorForFullBodyResolve;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.BodyResolveContext;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirAbstractBodyResolveTransformerDispatcher;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirDeclarationsResolveTransformer;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirExpressionsResolveTransformer;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirResolveContextCollector;

/* JADX INFO: compiled from: FirAnnotationArgumentsResolveTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u00002\u00020\u0001B?\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r¢\u0006\u0002\u0010\u000eR\u0011\u0010\u000f\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0013\u001a\u00020\u0014¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/FirAnnotationArgumentsResolveTransformer;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformerDispatcher;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "resolvePhase", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "outerBodyResolveContext", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/BodyResolveContext;", "returnTypeCalculator", "Lorg/jetbrains/kotlin/fir/resolve/transformers/ReturnTypeCalculator;", "firResolveContextCollector", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirResolveContextCollector;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/BodyResolveContext;Lorg/jetbrains/kotlin/fir/resolve/transformers/ReturnTypeCalculator;Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirResolveContextCollector;)V", "declarationsTransformer", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirDeclarationsResolveTransformer;", "getDeclarationsTransformer", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirDeclarationsResolveTransformer;", "expressionsTransformer", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirExpressionsResolveTransformer;", "getExpressionsTransformer", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirExpressionsResolveTransformer;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class FirAnnotationArgumentsResolveTransformer extends FirAbstractBodyResolveTransformerDispatcher {
    private final FirDeclarationsResolveTransformer declarationsTransformer;
    private final FirExpressionsResolveTransformer expressionsTransformer;

    public /* synthetic */ FirAnnotationArgumentsResolveTransformer(FirSession firSession, ScopeSession scopeSession, FirResolvePhase firResolvePhase, BodyResolveContext bodyResolveContext, ReturnTypeCalculator returnTypeCalculator, FirResolveContextCollector firResolveContextCollector, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(firSession, scopeSession, firResolvePhase, (i & 8) != 0 ? null : bodyResolveContext, (i & 16) != 0 ? ReturnTypeCalculatorForFullBodyResolve.INSTANCE.getDefault() : returnTypeCalculator, (i & 32) != 0 ? null : firResolveContextCollector);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirAnnotationArgumentsResolveTransformer(FirSession session, ScopeSession scopeSession, FirResolvePhase resolvePhase, BodyResolveContext bodyResolveContext, ReturnTypeCalculator returnTypeCalculator, FirResolveContextCollector firResolveContextCollector) {
        super(session, resolvePhase, false, scopeSession, returnTypeCalculator, bodyResolveContext, firResolveContextCollector);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        Intrinsics.checkNotNullParameter(resolvePhase, "resolvePhase");
        Intrinsics.checkNotNullParameter(returnTypeCalculator, "returnTypeCalculator");
        FirAnnotationArgumentsResolveTransformer firAnnotationArgumentsResolveTransformer = this;
        this.expressionsTransformer = new FirExpressionsResolveTransformerForSpecificAnnotations(firAnnotationArgumentsResolveTransformer);
        this.declarationsTransformer = new FirDeclarationsResolveTransformerForArgumentAnnotations(firAnnotationArgumentsResolveTransformer);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirAbstractBodyResolveTransformerDispatcher
    public final FirExpressionsResolveTransformer getExpressionsTransformer() {
        return this.expressionsTransformer;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirAbstractBodyResolveTransformerDispatcher
    public final FirDeclarationsResolveTransformer getDeclarationsTransformer() {
        return this.declarationsTransformer;
    }
}
