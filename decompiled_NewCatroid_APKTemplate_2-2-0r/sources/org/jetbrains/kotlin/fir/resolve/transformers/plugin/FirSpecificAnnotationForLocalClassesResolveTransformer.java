package org.jetbrains.kotlin.fir.resolve.transformers.plugin;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.LocalClassesNavigationInfo;

/* JADX INFO: compiled from: FirCompilerRequiredAnnotationsResolveTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0002\u0018\u00002\u00020\u0001B3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u0012\u0006\u0010\u000b\u001a\u00020\f¢\u0006\u0002\u0010\rJ\u0010\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\nH\u0016R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\u00020\u000f8TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/FirSpecificAnnotationForLocalClassesResolveTransformer;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/AbstractFirSpecificAnnotationResolveTransformer;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "computationSession", "Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/CompilerRequiredAnnotationsComputationSession;", "containingDeclarations", "", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "localClassesNavigationInfo", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/LocalClassesNavigationInfo;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/CompilerRequiredAnnotationsComputationSession;Ljava/util/List;Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/LocalClassesNavigationInfo;)V", "shouldRecordIntoPredicateBasedProvider", "", "getShouldRecordIntoPredicateBasedProvider", "()Z", "shouldTransformDeclaration", "declaration", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class FirSpecificAnnotationForLocalClassesResolveTransformer extends AbstractFirSpecificAnnotationResolveTransformer {
    private final LocalClassesNavigationInfo localClassesNavigationInfo;

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.plugin.AbstractFirSpecificAnnotationResolveTransformer
    protected boolean getShouldRecordIntoPredicateBasedProvider() {
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirSpecificAnnotationForLocalClassesResolveTransformer(FirSession session, ScopeSession scopeSession, CompilerRequiredAnnotationsComputationSession computationSession, List<? extends FirDeclaration> containingDeclarations, LocalClassesNavigationInfo localClassesNavigationInfo) {
        super(session, scopeSession, computationSession, containingDeclarations);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        Intrinsics.checkNotNullParameter(computationSession, "computationSession");
        Intrinsics.checkNotNullParameter(containingDeclarations, "containingDeclarations");
        Intrinsics.checkNotNullParameter(localClassesNavigationInfo, "localClassesNavigationInfo");
        this.localClassesNavigationInfo = localClassesNavigationInfo;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.plugin.AbstractFirSpecificAnnotationResolveTransformer
    public boolean shouldTransformDeclaration(FirDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (declaration instanceof FirClassLikeDeclaration) {
            return this.localClassesNavigationInfo.getParentForClass().containsKey(declaration);
        }
        return true;
    }
}
