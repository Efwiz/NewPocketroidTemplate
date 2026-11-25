package org.jetbrains.kotlin.fir.resolve.calls;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner;
import org.jetbrains.kotlin.fir.types.ConeInferenceContext;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeIntersector;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeProjection;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.fir.types.builder.FirTypeProjectionWithVarianceBuilder;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: compiled from: TypeArgumentMapping.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J.\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0096@¢\u0006\u0002\u0010\rJ\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u000b\u001a\u00020\fH\u0002¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/calls/MapTypeArguments;", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionStage;", "()V", "check", "", "candidate", "Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;", "callInfo", "Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;", "sink", "Lorg/jetbrains/kotlin/fir/resolve/calls/CheckerSink;", "context", "Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;", "(Lorg/jetbrains/kotlin/fir/resolve/calls/Candidate;Lorg/jetbrains/kotlin/fir/resolve/calls/CallInfo;Lorg/jetbrains/kotlin/fir/resolve/calls/CheckerSink;Lorg/jetbrains/kotlin/fir/resolve/calls/ResolutionContext;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "computeDefaultMappingForRawTypeMember", "Lorg/jetbrains/kotlin/fir/resolve/calls/TypeArgumentMapping$Mapped;", "owner", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRefsOwner;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MapTypeArguments extends ResolutionStage {
    public static final MapTypeArguments INSTANCE = new MapTypeArguments();

    private MapTypeArguments() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.resolve.calls.ResolutionStage
    public Object check(Candidate candidate, CallInfo callInfo, CheckerSink checkerSink, ResolutionContext resolutionContext, Continuation<? super Unit> continuation) {
        Object objYield;
        ConeSimpleKotlinType dispatchReceiverType;
        List<FirTypeProjection> typeArguments = callInfo.getTypeArguments();
        FirAnnotationContainer fir = candidate.getSymbol().getFir();
        Intrinsics.checkNotNull(fir, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirTypeParameterRefsOwner");
        FirTypeParameterRefsOwner firTypeParameterRefsOwner = (FirTypeParameterRefsOwner) fir;
        if (typeArguments.isEmpty()) {
            if ((firTypeParameterRefsOwner instanceof FirCallableDeclaration) && (dispatchReceiverType = ((FirCallableDeclaration) firTypeParameterRefsOwner).getDispatchReceiverType()) != null && TypeUtilsKt.isRaw(dispatchReceiverType)) {
                candidate.setTypeArgumentMapping(computeDefaultMappingForRawTypeMember(firTypeParameterRefsOwner, resolutionContext));
            } else {
                candidate.setTypeArgumentMapping(TypeArgumentMapping.NoExplicitArguments.INSTANCE);
            }
            return Unit.INSTANCE;
        }
        if (typeArguments.size() != firTypeParameterRefsOwner.getTypeParameters().size() && !Intrinsics.areEqual(callInfo.getCallKind(), CallKind.DelegatingConstructorCall.INSTANCE)) {
            FirDeclaration firDeclaration = (FirDeclaration) firTypeParameterRefsOwner;
            if (!((firDeclaration != null ? firDeclaration.getOrigin() : null) instanceof FirDeclarationOrigin.DynamicScope)) {
                if (typeArguments.size() > firTypeParameterRefsOwner.getTypeParameters().size()) {
                    typeArguments = CollectionsKt.take(typeArguments, firTypeParameterRefsOwner.getTypeParameters().size());
                }
                candidate.setTypeArgumentMapping(new TypeArgumentMapping.Mapped(typeArguments));
                checkerSink.reportDiagnostic(InapplicableCandidate.INSTANCE);
                return (checkerSink.getNeedYielding() && (objYield = checkerSink.yield(continuation)) == IntrinsicsKt.getCOROUTINE_SUSPENDED()) ? objYield : Unit.INSTANCE;
            }
        }
        candidate.setTypeArgumentMapping(new TypeArgumentMapping.Mapped(typeArguments));
        return Unit.INSTANCE;
    }

    private final TypeArgumentMapping.Mapped computeDefaultMappingForRawTypeMember(FirTypeParameterRefsOwner owner, ResolutionContext context) {
        List<FirTypeParameterRef> typeParameters = owner.getTypeParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters, 10));
        for (FirTypeParameterRef firTypeParameterRef : typeParameters) {
            FirTypeProjectionWithVarianceBuilder firTypeProjectionWithVarianceBuilder = new FirTypeProjectionWithVarianceBuilder();
            ConeTypeIntersector coneTypeIntersector = ConeTypeIntersector.INSTANCE;
            ConeInferenceContext typeContext = context.getTypeContext();
            List<FirResolvedTypeRef> resolvedBounds = firTypeParameterRef.getSymbol().getResolvedBounds();
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(resolvedBounds, 10));
            Iterator<T> it = resolvedBounds.iterator();
            while (it.hasNext()) {
                arrayList2.add(((FirResolvedTypeRef) it.next()).getType());
            }
            firTypeProjectionWithVarianceBuilder.setTypeRef(TypeUtilsKt.toFirResolvedTypeRef$default(coneTypeIntersector.intersectTypes(typeContext, arrayList2), null, null, 3, null));
            firTypeProjectionWithVarianceBuilder.setVariance(Variance.INVARIANT);
            arrayList.add(firTypeProjectionWithVarianceBuilder.build());
        }
        return new TypeArgumentMapping.Mapped(arrayList);
    }
}
