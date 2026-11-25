package org.jetbrains.kotlin.fir.resolve.transformers.contracts;

import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.resolve.ResolutionMode;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.transformers.ReturnTypeCalculatorForFullBodyResolve;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.BodyResolveContext;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;

/* JADX INFO: compiled from: FirContractResolveTransformerAdapter.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\b\u0002\u001a1\u0010\u0000\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u0002*\u0002H\u00012\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\t\u001a?\u0010\n\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u000b*\u0002H\u00012\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\r¢\u0006\u0002\u0010\u000e¨\u0006\u000f"}, d2 = {"runContractResolveForFunction", "F", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "outerBodyResolveContext", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/BodyResolveContext;", "(Lorg/jetbrains/kotlin/fir/declarations/FirFunction;Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/BodyResolveContext;)Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "runContractResolveForLocalClass", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "targetedClasses", "", "(Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/BodyResolveContext;Ljava/util/Set;)Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirContractResolveTransformerAdapterKt {
    public static final <F extends FirClassLikeDeclaration> F runContractResolveForLocalClass(F f, FirSession session, ScopeSession scopeSession, BodyResolveContext outerBodyResolveContext, Set<? extends FirClassLikeDeclaration> targetedClasses) {
        Intrinsics.checkNotNullParameter(f, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        Intrinsics.checkNotNullParameter(outerBodyResolveContext, "outerBodyResolveContext");
        Intrinsics.checkNotNullParameter(targetedClasses, "targetedClasses");
        return (F) FirTransformerUtilKt.transformSingle(f, new FirContractResolveTransformer(session, scopeSession, outerBodyResolveContext.createSnapshotForLocalClasses(ReturnTypeCalculatorForFullBodyResolve.INSTANCE.getContract(), targetedClasses)), ResolutionMode.ContextIndependent.INSTANCE);
    }

    public static final <F extends FirFunction> F runContractResolveForFunction(F f, FirSession session, ScopeSession scopeSession, BodyResolveContext outerBodyResolveContext) {
        Intrinsics.checkNotNullParameter(f, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        Intrinsics.checkNotNullParameter(outerBodyResolveContext, "outerBodyResolveContext");
        return (F) FirTransformerUtilKt.transformSingle(f, new FirContractResolveTransformer(session, scopeSession, outerBodyResolveContext), ResolutionMode.ContextIndependent.INSTANCE);
    }
}
