package org.jetbrains.kotlin.fir.resolve.transformers.body.resolve;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.resolve.ResolutionMode;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.transformers.ReturnTypeCalculator;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;

/* JADX INFO: compiled from: FirImplicitBodyResolve.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001BM\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011¢\u0006\u0002\u0010\u0012J-\u0010\u0013\u001a\u0002H\u0014\"\b\b\u0000\u0010\u0014*\u00020\u00152\u0006\u0010\u0016\u001a\u0002H\u00142\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00140\u0018H\u0002¢\u0006\u0002\u0010\u0019J\u0018\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\u0018\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020 2\u0006\u0010\u001d\u001a\u00020\u001eH\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\""}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirImplicitAwareBodyResolveTransformer;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirBodyResolveTransformer;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "implicitBodyResolveComputationSession", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/ImplicitBodyResolveComputationSession;", TypedValues.CycleType.S_WAVE_PHASE, "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "implicitTypeOnly", "", "returnTypeCalculator", "Lorg/jetbrains/kotlin/fir/resolve/transformers/ReturnTypeCalculator;", "outerBodyResolveContext", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/BodyResolveContext;", "firResolveContextCollector", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirResolveContextCollector;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/ImplicitBodyResolveComputationSession;Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;ZLorg/jetbrains/kotlin/fir/resolve/transformers/ReturnTypeCalculator;Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/BodyResolveContext;Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirResolveContextCollector;)V", "computeCachedTransformationResult", "D", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "member", "transform", "Lkotlin/Function0;", "(Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;Lkotlin/jvm/functions/Function0;)Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "transformProperty", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "property", "data", "Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode;", "transformSimpleFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "simpleFunction", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class FirImplicitAwareBodyResolveTransformer extends FirBodyResolveTransformer {
    private final ImplicitBodyResolveComputationSession implicitBodyResolveComputationSession;

    public /* synthetic */ FirImplicitAwareBodyResolveTransformer(FirSession firSession, ScopeSession scopeSession, ImplicitBodyResolveComputationSession implicitBodyResolveComputationSession, FirResolvePhase firResolvePhase, boolean z, ReturnTypeCalculator returnTypeCalculator, BodyResolveContext bodyResolveContext, FirResolveContextCollector firResolveContextCollector, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(firSession, scopeSession, implicitBodyResolveComputationSession, firResolvePhase, z, returnTypeCalculator, (i & 64) != 0 ? null : bodyResolveContext, (i & 128) != 0 ? null : firResolveContextCollector);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirImplicitAwareBodyResolveTransformer(FirSession session, ScopeSession scopeSession, ImplicitBodyResolveComputationSession implicitBodyResolveComputationSession, FirResolvePhase phase, boolean z, ReturnTypeCalculator returnTypeCalculator, BodyResolveContext bodyResolveContext, FirResolveContextCollector firResolveContextCollector) {
        super(session, phase, z, scopeSession, returnTypeCalculator, bodyResolveContext, firResolveContextCollector);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        Intrinsics.checkNotNullParameter(implicitBodyResolveComputationSession, "implicitBodyResolveComputationSession");
        Intrinsics.checkNotNullParameter(phase, "phase");
        Intrinsics.checkNotNullParameter(returnTypeCalculator, "returnTypeCalculator");
        this.implicitBodyResolveComputationSession = implicitBodyResolveComputationSession;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirAbstractBodyResolveTransformerDispatcher, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirSimpleFunction transformSimpleFunction(final FirSimpleFunction simpleFunction, final ResolutionMode data) {
        Intrinsics.checkNotNullParameter(simpleFunction, "simpleFunction");
        Intrinsics.checkNotNullParameter(data, "data");
        return (FirSimpleFunction) computeCachedTransformationResult(simpleFunction, new Function0<FirSimpleFunction>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirImplicitAwareBodyResolveTransformer.transformSimpleFunction.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final FirSimpleFunction invoke() {
                return FirImplicitAwareBodyResolveTransformer.super.transformSimpleFunction(simpleFunction, data);
            }
        });
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirAbstractBodyResolveTransformerDispatcher, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirProperty transformProperty(final FirProperty property, final ResolutionMode data) {
        Intrinsics.checkNotNullParameter(property, "property");
        Intrinsics.checkNotNullParameter(data, "data");
        return (FirProperty) computeCachedTransformationResult(property, new Function0<FirProperty>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirImplicitAwareBodyResolveTransformer.transformProperty.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final FirProperty invoke() {
                return FirImplicitAwareBodyResolveTransformer.super.transformProperty(property, data);
            }
        });
    }

    private final <D extends FirCallableDeclaration> D computeCachedTransformationResult(D member, Function0<? extends D> transform) {
        if (!getImplicitTypeOnly() && (member.getReturnTypeRef() instanceof FirResolvedTypeRef)) {
            return transform.invoke();
        }
        boolean z = (member instanceof FirProperty) && ((FirProperty) member).getBackingField() != null;
        if ((member.getReturnTypeRef() instanceof FirResolvedTypeRef) && !z) {
            return member;
        }
        FirCallableSymbol<? extends FirCallableDeclaration> symbol = member.getSymbol();
        ImplicitBodyResolveComputationStatus status$resolve = this.implicitBodyResolveComputationSession.getStatus$resolve(symbol);
        if (status$resolve instanceof ImplicitBodyResolveComputationStatus.Computed) {
            D d = (D) ((ImplicitBodyResolveComputationStatus.Computed) status$resolve).getTransformedDeclaration();
            Intrinsics.checkNotNull(d, "null cannot be cast to non-null type D of org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirImplicitAwareBodyResolveTransformer.computeCachedTransformationResult");
            return d;
        }
        if (!(status$resolve instanceof ImplicitBodyResolveComputationStatus.NotComputed)) {
            throw new IllegalArgumentException(("Unexpected status in transformCallableMember (" + status$resolve + ") for " + UtilsKt.render(member)).toString());
        }
        this.implicitBodyResolveComputationSession.startComputing(symbol);
        D dInvoke = transform.invoke();
        this.implicitBodyResolveComputationSession.storeResult(symbol, dInvoke);
        return dInvoke;
    }
}
