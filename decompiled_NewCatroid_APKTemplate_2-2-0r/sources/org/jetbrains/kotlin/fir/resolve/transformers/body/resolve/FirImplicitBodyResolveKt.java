package org.jetbrains.kotlin.fir.resolve.transformers.body.resolve;

import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.resolve.ResolutionMode;
import org.jetbrains.kotlin.fir.resolve.transformers.ReturnTypeCalculator;
import org.jetbrains.kotlin.fir.resolve.transformers.contracts.FirContractResolveTransformerAdapterKt;

/* JADX INFO: compiled from: FirImplicitBodyResolve.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a=\u0010\u0000\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u0002*\u0002H\u00012\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\u0002\u0010\u000b¨\u0006\f"}, d2 = {"runContractAndBodiesResolutionForLocalClass", "F", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "components", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformer$BodyResolveTransformerComponents;", "resolutionMode", "Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode;", "localClassesNavigationInfo", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/LocalClassesNavigationInfo;", "firResolveContextCollector", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirResolveContextCollector;", "(Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformer$BodyResolveTransformerComponents;Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode;Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/LocalClassesNavigationInfo;Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirResolveContextCollector;)Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirImplicitBodyResolveKt {
    public static /* synthetic */ FirClassLikeDeclaration runContractAndBodiesResolutionForLocalClass$default(FirClassLikeDeclaration firClassLikeDeclaration, FirAbstractBodyResolveTransformer.BodyResolveTransformerComponents bodyResolveTransformerComponents, ResolutionMode resolutionMode, LocalClassesNavigationInfo localClassesNavigationInfo, FirResolveContextCollector firResolveContextCollector, int i, Object obj) {
        if ((i & 8) != 0) {
            firResolveContextCollector = null;
        }
        return runContractAndBodiesResolutionForLocalClass(firClassLikeDeclaration, bodyResolveTransformerComponents, resolutionMode, localClassesNavigationInfo, firResolveContextCollector);
    }

    public static final <F extends FirClassLikeDeclaration> F runContractAndBodiesResolutionForLocalClass(F f, FirAbstractBodyResolveTransformer.BodyResolveTransformerComponents components, ResolutionMode resolutionMode, LocalClassesNavigationInfo localClassesNavigationInfo, FirResolveContextCollector firResolveContextCollector) {
        Map<FirCallableDeclaration, List<FirClassLikeDeclaration>> mapEmptyMap;
        ImplicitBodyResolveComputationSession implicitBodyResolveComputationSession;
        Intrinsics.checkNotNullParameter(f, "<this>");
        Intrinsics.checkNotNullParameter(components, "components");
        Intrinsics.checkNotNullParameter(resolutionMode, "resolutionMode");
        Intrinsics.checkNotNullParameter(localClassesNavigationInfo, "localClassesNavigationInfo");
        ReturnTypeCalculator returnTypeCalculator = components.getContext().getReturnTypeCalculator();
        ReturnTypeCalculatorWithJump returnTypeCalculatorWithJump = returnTypeCalculator instanceof ReturnTypeCalculatorWithJump ? (ReturnTypeCalculatorWithJump) returnTypeCalculator : null;
        if (returnTypeCalculatorWithJump == null || (mapEmptyMap = returnTypeCalculatorWithJump.getDesignationMapForLocalClasses()) == null) {
            mapEmptyMap = MapsKt.emptyMap();
        }
        Pair pair = TuplesKt.to(MapsKt.plus(mapEmptyMap, localClassesNavigationInfo.getDesignationMap()), SetsKt.plus(SetsKt.plus(localClassesNavigationInfo.getParentForClass().keySet(), f), (Iterable) components.getContext().getTargetedLocalClasses()));
        Map map = (Map) pair.component1();
        Set<? extends FirClassLikeDeclaration> set = (Set) pair.component2();
        if (returnTypeCalculatorWithJump == null || (implicitBodyResolveComputationSession = returnTypeCalculatorWithJump.getImplicitBodyResolveComputationSession()) == null) {
            implicitBodyResolveComputationSession = new ImplicitBodyResolveComputationSession();
        }
        ImplicitBodyResolveComputationSession implicitBodyResolveComputationSession2 = implicitBodyResolveComputationSession;
        ReturnTypeCalculatorWithJump returnTypeCalculatorWithJump2 = new ReturnTypeCalculatorWithJump(components.getScopeSession(), implicitBodyResolveComputationSession2, map, returnTypeCalculatorWithJump);
        ReturnTypeCalculatorWithJump returnTypeCalculatorWithJump3 = returnTypeCalculatorWithJump2;
        BodyResolveContext bodyResolveContextCreateSnapshotForLocalClasses = components.getContext().createSnapshotForLocalClasses(returnTypeCalculatorWithJump3, set);
        returnTypeCalculatorWithJump2.setOuterBodyResolveContext(bodyResolveContextCreateSnapshotForLocalClasses);
        FirContractResolveTransformerAdapterKt.runContractResolveForLocalClass(f, components.getSession(), components.getScopeSession(), components.getContext(), set);
        return (F) f.transform(new FirImplicitAwareBodyResolveTransformer(components.getSession(), components.getScopeSession(), implicitBodyResolveComputationSession2, FirResolvePhase.BODY_RESOLVE, false, returnTypeCalculatorWithJump3, bodyResolveContextCreateSnapshotForLocalClasses, firResolveContextCollector), resolutionMode);
    }
}
