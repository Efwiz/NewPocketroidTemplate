package org.jetbrains.kotlin.fir.resolve.transformers.body.resolve;

import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvedDeclarationStatus;
import org.jetbrains.kotlin.fir.resolve.BodyResolveComponentsKt;
import org.jetbrains.kotlin.fir.resolve.ResolutionMode;
import org.jetbrains.kotlin.fir.resolve.transformers.FirStatusResolveTransformerKt;
import org.jetbrains.kotlin.fir.resolve.transformers.FirSupertypesResolutionKt;
import org.jetbrains.kotlin.fir.resolve.transformers.FirTypeResolveTransformerKt;
import org.jetbrains.kotlin.fir.resolve.transformers.plugin.FirCompanionGenerationProcessorKt;
import org.jetbrains.kotlin.fir.resolve.transformers.plugin.FirCompilerRequiredAnnotationsResolveTransformerKt;

/* JADX INFO: compiled from: LocalClassesResolution.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a;\u0010\u0000\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u0002*\u0002H\u00012\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\n¢\u0006\u0002\u0010\u000b¨\u0006\f"}, d2 = {"runAllPhasesForLocalClass", "F", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "transformer", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformer;", "components", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformer$BodyResolveTransformerComponents;", "resolutionMode", "Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode;", "firResolveContextCollector", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirResolveContextCollector;", "(Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformer;Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformer$BodyResolveTransformerComponents;Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode;Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirResolveContextCollector;)Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class LocalClassesResolutionKt {
    public static final <F extends FirClassLikeDeclaration> F runAllPhasesForLocalClass(F f, FirAbstractBodyResolveTransformer transformer, FirAbstractBodyResolveTransformer.BodyResolveTransformerComponents components, ResolutionMode resolutionMode, FirResolveContextCollector firResolveContextCollector) {
        Intrinsics.checkNotNullParameter(f, "<this>");
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        Intrinsics.checkNotNullParameter(components, "components");
        Intrinsics.checkNotNullParameter(resolutionMode, "resolutionMode");
        if (f.getStatus() instanceof FirResolvedDeclarationStatus) {
            return f;
        }
        if (f instanceof FirRegularClass) {
            components.getContext().storeClassIfNotNested((FirRegularClass) f, components.getSession());
        }
        f.transformAnnotations(transformer, ResolutionMode.ContextIndependent.INSTANCE);
        LocalClassesNavigationInfo localClassesNavigationInfoCollectLocalClassesNavigationInfo = LocalClassesNavigationKt.collectLocalClassesNavigationInfo(f);
        for (Map.Entry<FirClassLikeDeclaration, FirClassLikeDeclaration> entry : localClassesNavigationInfoCollectLocalClassesNavigationInfo.getParentForClass().entrySet()) {
            FirClassLikeDeclaration key = entry.getKey();
            FirClassLikeDeclaration value = entry.getValue();
            if (value != null) {
                components.getContext().getOuterLocalClassForNested().put(key.getSymbol(), value.getSymbol());
            }
        }
        FirCompilerRequiredAnnotationsResolveTransformerKt.runCompilerRequiredAnnotationsResolvePhaseForLocalClass(f, components.getSession(), components.getScopeSession(), localClassesNavigationInfoCollectLocalClassesNavigationInfo, components.getFile(), components.getContainingDeclarations());
        FirCompanionGenerationProcessorKt.runCompanionGenerationPhaseForLocalClass(f, components.getSession());
        FirAbstractBodyResolveTransformer.BodyResolveTransformerComponents bodyResolveTransformerComponents = components;
        FirSupertypesResolutionKt.runSupertypeResolvePhaseForLocalClass(f, components.getSession(), components.getScopeSession(), BodyResolveComponentsKt.createCurrentScopeList(bodyResolveTransformerComponents), localClassesNavigationInfoCollectLocalClassesNavigationInfo, components.getFile(), components.getContainingDeclarations());
        FirTypeResolveTransformerKt.runTypeResolvePhaseForLocalClass(f, components.getSession(), components.getScopeSession(), BodyResolveComponentsKt.createCurrentScopeList(bodyResolveTransformerComponents), components.getFile(), components.getContainingDeclarations());
        FirStatusResolveTransformerKt.runStatusResolveForLocalClass(f, components.getSession(), components.getScopeSession(), BodyResolveComponentsKt.createCurrentScopeList(bodyResolveTransformerComponents), localClassesNavigationInfoCollectLocalClassesNavigationInfo);
        FirImplicitBodyResolveKt.runContractAndBodiesResolutionForLocalClass(f, components, resolutionMode, localClassesNavigationInfoCollectLocalClassesNavigationInfo, firResolveContextCollector);
        return f;
    }
}
