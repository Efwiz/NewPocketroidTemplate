package org.jetbrains.kotlin.fir.resolve.transformers.plugin;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;

/* JADX INFO: compiled from: FirCompilerRequiredAnnotationsResolveTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0014\u0010\t\u001a\u00020\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/FirCompilerRequiredAnnotationsResolveTransformer;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/AbstractFirCompilerRequiredAnnotationsResolveTransformer;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "computationSession", "Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/CompilerRequiredAnnotationsComputationSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/CompilerRequiredAnnotationsComputationSession;)V", "annotationTransformer", "Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/AbstractFirSpecificAnnotationResolveTransformer;", "getAnnotationTransformer", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/AbstractFirSpecificAnnotationResolveTransformer;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class FirCompilerRequiredAnnotationsResolveTransformer extends AbstractFirCompilerRequiredAnnotationsResolveTransformer {
    private final AbstractFirSpecificAnnotationResolveTransformer annotationTransformer;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirCompilerRequiredAnnotationsResolveTransformer(FirSession session, ScopeSession scopeSession, CompilerRequiredAnnotationsComputationSession computationSession) {
        super(session, computationSession);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        Intrinsics.checkNotNullParameter(computationSession, "computationSession");
        this.annotationTransformer = new FirSpecificAnnotationResolveTransformer(session, scopeSession, computationSession);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.plugin.AbstractFirCompilerRequiredAnnotationsResolveTransformer
    public AbstractFirSpecificAnnotationResolveTransformer getAnnotationTransformer() {
        return this.annotationTransformer;
    }
}
