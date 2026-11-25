package org.jetbrains.kotlin.fir.resolve.transformers.plugin;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.LocalClassesNavigationInfo;
import org.jetbrains.kotlin.fir.scopes.ImportingScopesKt;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;

/* JADX INFO: compiled from: FirCompilerRequiredAnnotationsResolveTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\u001aG\u0010\u0000\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u0002*\u0002H\u00012\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f¢\u0006\u0002\u0010\u000e¨\u0006\u000f"}, d2 = {"runCompilerRequiredAnnotationsResolvePhaseForLocalClass", "F", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "localClassesNavigationInfo", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/LocalClassesNavigationInfo;", "useSiteFile", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "containingDeclarations", "", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "(Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/LocalClassesNavigationInfo;Lorg/jetbrains/kotlin/fir/declarations/FirFile;Ljava/util/List;)Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "resolve"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirCompilerRequiredAnnotationsResolveTransformerKt {
    public static final <F extends FirClassLikeDeclaration> F runCompilerRequiredAnnotationsResolvePhaseForLocalClass(F f, FirSession session, ScopeSession scopeSession, LocalClassesNavigationInfo localClassesNavigationInfo, FirFile useSiteFile, List<? extends FirDeclaration> containingDeclarations) {
        Intrinsics.checkNotNullParameter(f, "<this>");
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        Intrinsics.checkNotNullParameter(localClassesNavigationInfo, "localClassesNavigationInfo");
        Intrinsics.checkNotNullParameter(useSiteFile, "useSiteFile");
        Intrinsics.checkNotNullParameter(containingDeclarations, "containingDeclarations");
        FirSpecificAnnotationForLocalClassesResolveTransformer firSpecificAnnotationForLocalClassesResolveTransformer = new FirSpecificAnnotationForLocalClassesResolveTransformer(session, scopeSession, new CompilerRequiredAnnotationsComputationSession(), containingDeclarations, localClassesNavigationInfo);
        FirSpecificAnnotationForLocalClassesResolveTransformer firSpecificAnnotationForLocalClassesResolveTransformer2 = firSpecificAnnotationForLocalClassesResolveTransformer;
        firSpecificAnnotationForLocalClassesResolveTransformer2.setScopes(ImportingScopesKt.createImportingScopes(useSiteFile, firSpecificAnnotationForLocalClassesResolveTransformer2.getSession(), firSpecificAnnotationForLocalClassesResolveTransformer2.getScopeSession(), firSpecificAnnotationForLocalClassesResolveTransformer2.getComputationSession().getUseCacheForImportScope()));
        return (F) FirTransformerUtilKt.transformSingle(f, firSpecificAnnotationForLocalClassesResolveTransformer, null);
    }
}
