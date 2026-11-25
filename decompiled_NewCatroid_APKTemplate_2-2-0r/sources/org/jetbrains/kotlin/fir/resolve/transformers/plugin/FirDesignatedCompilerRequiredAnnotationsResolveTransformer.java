package org.jetbrains.kotlin.fir.resolve.transformers.plugin;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.transformers.DesignationState;

/* JADX INFO: compiled from: FirCompilerRequiredAnnotationsResolveTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nR\u0014\u0010\u000b\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/FirDesignatedCompilerRequiredAnnotationsResolveTransformer;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/AbstractFirCompilerRequiredAnnotationsResolveTransformer;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "computationSession", "Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/CompilerRequiredAnnotationsComputationSession;", "designation", "Lorg/jetbrains/kotlin/fir/resolve/transformers/DesignationState;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/CompilerRequiredAnnotationsComputationSession;Lorg/jetbrains/kotlin/fir/resolve/transformers/DesignationState;)V", "annotationTransformer", "Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/AbstractFirSpecificAnnotationResolveTransformer;", "getAnnotationTransformer", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/AbstractFirSpecificAnnotationResolveTransformer;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirDesignatedCompilerRequiredAnnotationsResolveTransformer extends AbstractFirCompilerRequiredAnnotationsResolveTransformer {
    private final AbstractFirSpecificAnnotationResolveTransformer annotationTransformer;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirDesignatedCompilerRequiredAnnotationsResolveTransformer(FirSession session, ScopeSession scopeSession, CompilerRequiredAnnotationsComputationSession computationSession, DesignationState designation) {
        super(session, computationSession);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        Intrinsics.checkNotNullParameter(computationSession, "computationSession");
        Intrinsics.checkNotNullParameter(designation, "designation");
        this.annotationTransformer = new FirDesignatedSpecificAnnotationResolveTransformer(session, scopeSession, computationSession, designation);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.plugin.AbstractFirCompilerRequiredAnnotationsResolveTransformer
    public AbstractFirSpecificAnnotationResolveTransformer getAnnotationTransformer() {
        return this.annotationTransformer;
    }
}
