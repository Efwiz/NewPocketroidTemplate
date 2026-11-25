package org.jetbrains.kotlin.fir.resolve.transformers;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;

/* JADX INFO: compiled from: FirSupertypesResolution.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J)\u0010\u0010\u001a\u0002H\u0011\"\b\b\u0000\u0010\u0011*\u00020\u00122\u0006\u0010\u0013\u001a\u0002H\u00112\b\u0010\u0014\u001a\u0004\u0018\u00010\u0002H\u0016¢\u0006\u0002\u0010\u0015J\u001a\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00172\b\u0010\u0014\u001a\u0004\u0018\u00010\u0002H\u0016R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/FirSupertypeResolverTransformer;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/FirAbstractPhaseTransformer;", "", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;)V", "applySupertypesTransformer", "Lorg/jetbrains/kotlin/fir/resolve/transformers/FirApplySupertypesTransformer;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "supertypeComputationSession", "Lorg/jetbrains/kotlin/fir/resolve/transformers/SupertypeComputationSession;", "supertypeResolverVisitor", "Lorg/jetbrains/kotlin/fir/resolve/transformers/FirSupertypeResolverVisitor;", "transformElement", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "data", "(Lorg/jetbrains/kotlin/fir/FirElement;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", "transformFile", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "file", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirSupertypeResolverTransformer extends FirAbstractPhaseTransformer<Object> {
    private final FirApplySupertypesTransformer applySupertypesTransformer;
    private final FirSession session;
    private final SupertypeComputationSession supertypeComputationSession;
    private final FirSupertypeResolverVisitor supertypeResolverVisitor;

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public <E extends FirElement> E transformElement(E element, Object data) {
        Intrinsics.checkNotNullParameter(element, "element");
        return element;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.FirAbstractPhaseTransformer
    public FirSession getSession() {
        return this.session;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirSupertypeResolverTransformer(FirSession session, ScopeSession scopeSession) {
        super(FirResolvePhase.SUPER_TYPES);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        this.session = session;
        SupertypeComputationSession supertypeComputationSession = new SupertypeComputationSession();
        this.supertypeComputationSession = supertypeComputationSession;
        this.supertypeResolverVisitor = new FirSupertypeResolverVisitor(getSession(), supertypeComputationSession, scopeSession, null, null, null, null, 120, null);
        this.applySupertypesTransformer = new FirApplySupertypesTransformer(supertypeComputationSession, getSession(), scopeSession);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.FirAbstractPhaseTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirFile transformFile(FirFile file, Object data) {
        Intrinsics.checkNotNullParameter(file, "file");
        checkSessionConsistency(file);
        try {
            file.accept(this.supertypeResolverVisitor, null);
            this.supertypeComputationSession.breakLoops(getSession());
            return (FirFile) file.transform(this.applySupertypesTransformer, null);
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(file.getModuleData().getSession()).handleExceptionOnFileAnalysis(file, th);
            throw null;
        }
    }
}
