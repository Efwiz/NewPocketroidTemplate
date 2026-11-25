package org.jetbrains.kotlin.fir.resolve.transformers.body.resolve;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.transformers.ReturnTypeCalculator;
import org.jetbrains.kotlin.fir.resolve.transformers.ReturnTypeCalculatorForFullBodyResolve;

/* JADX INFO: compiled from: FirBodyResolveTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u00002\u00020\u0001BG\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f¢\u0006\u0002\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\u0012¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0015\u001a\u00020\u0016¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirBodyResolveTransformer;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformerDispatcher;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", TypedValues.CycleType.S_WAVE_PHASE, "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "implicitTypeOnly", "", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "returnTypeCalculator", "Lorg/jetbrains/kotlin/fir/resolve/transformers/ReturnTypeCalculator;", "outerBodyResolveContext", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/BodyResolveContext;", "firResolveContextCollector", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirResolveContextCollector;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;ZLorg/jetbrains/kotlin/fir/resolve/ScopeSession;Lorg/jetbrains/kotlin/fir/resolve/transformers/ReturnTypeCalculator;Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/BodyResolveContext;Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirResolveContextCollector;)V", "declarationsTransformer", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirDeclarationsResolveTransformer;", "getDeclarationsTransformer", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirDeclarationsResolveTransformer;", "expressionsTransformer", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirExpressionsResolveTransformer;", "getExpressionsTransformer", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirExpressionsResolveTransformer;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class FirBodyResolveTransformer extends FirAbstractBodyResolveTransformerDispatcher {
    private final FirDeclarationsResolveTransformer declarationsTransformer;
    private final FirExpressionsResolveTransformer expressionsTransformer;

    public /* synthetic */ FirBodyResolveTransformer(FirSession firSession, FirResolvePhase firResolvePhase, boolean z, ScopeSession scopeSession, ReturnTypeCalculator returnTypeCalculator, BodyResolveContext bodyResolveContext, FirResolveContextCollector firResolveContextCollector, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(firSession, firResolvePhase, z, scopeSession, (i & 16) != 0 ? ReturnTypeCalculatorForFullBodyResolve.INSTANCE.getDefault() : returnTypeCalculator, (i & 32) != 0 ? null : bodyResolveContext, (i & 64) != 0 ? null : firResolveContextCollector);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirBodyResolveTransformer(FirSession session, FirResolvePhase phase, boolean z, ScopeSession scopeSession, ReturnTypeCalculator returnTypeCalculator, BodyResolveContext bodyResolveContext, FirResolveContextCollector firResolveContextCollector) {
        super(session, phase, z, scopeSession, returnTypeCalculator, bodyResolveContext, firResolveContextCollector);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(phase, "phase");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        Intrinsics.checkNotNullParameter(returnTypeCalculator, "returnTypeCalculator");
        FirBodyResolveTransformer firBodyResolveTransformer = this;
        this.expressionsTransformer = new FirExpressionsResolveTransformer(firBodyResolveTransformer);
        this.declarationsTransformer = new FirDeclarationsResolveTransformer(firBodyResolveTransformer);
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
