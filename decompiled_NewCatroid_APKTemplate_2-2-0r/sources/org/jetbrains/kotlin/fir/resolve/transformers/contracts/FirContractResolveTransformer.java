package org.jetbrains.kotlin.fir.resolve.transformers.contracts;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.BodyResolveContext;

/* JADX INFO: compiled from: FirContractResolveTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bR\u0018\u0010\t\u001a\u00060\nR\u00020\u00018TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/contracts/FirContractResolveTransformer;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/contracts/FirAbstractContractResolveTransformerDispatcher;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "outerBodyResolveContext", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/BodyResolveContext;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/BodyResolveContext;)V", "contractDeclarationsTransformer", "Lorg/jetbrains/kotlin/fir/resolve/transformers/contracts/FirAbstractContractResolveTransformerDispatcher$FirDeclarationsContractResolveTransformer;", "getContractDeclarationsTransformer", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/contracts/FirAbstractContractResolveTransformerDispatcher$FirDeclarationsContractResolveTransformer;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirContractResolveTransformer extends FirAbstractContractResolveTransformerDispatcher {
    public /* synthetic */ FirContractResolveTransformer(FirSession firSession, ScopeSession scopeSession, BodyResolveContext bodyResolveContext, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(firSession, scopeSession, (i & 4) != 0 ? null : bodyResolveContext);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirContractResolveTransformer(FirSession session, ScopeSession scopeSession, BodyResolveContext bodyResolveContext) {
        super(session, scopeSession, bodyResolveContext);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.contracts.FirAbstractContractResolveTransformerDispatcher
    protected FirAbstractContractResolveTransformerDispatcher.FirDeclarationsContractResolveTransformer getContractDeclarationsTransformer() {
        return new FirAbstractContractResolveTransformerDispatcher.FirDeclarationsContractResolveTransformer();
    }
}
