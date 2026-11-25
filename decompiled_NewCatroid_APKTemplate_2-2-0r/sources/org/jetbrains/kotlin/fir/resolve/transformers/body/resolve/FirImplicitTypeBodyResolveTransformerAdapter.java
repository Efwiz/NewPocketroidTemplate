package org.jetbrains.kotlin.fir.resolve.transformers.body.resolve;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.resolve.ResolutionMode;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.transformers.AdapterForResolveProcessor;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;

/* JADX INFO: compiled from: FirImplicitBodyResolve.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J)\u0010\u000e\u001a\u0002H\u000f\"\b\b\u0000\u0010\u000f*\u00020\u00102\u0006\u0010\u0011\u001a\u0002H\u000f2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0002H\u0016¢\u0006\u0002\u0010\u0013J\u001a\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00152\b\u0010\u0012\u001a\u0004\u0018\u00010\u0002H\u0016R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirImplicitTypeBodyResolveTransformerAdapter;", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;)V", "implicitBodyResolveComputationSession", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/ImplicitBodyResolveComputationSession;", "returnTypeCalculator", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/ReturnTypeCalculatorWithJump;", "transformer", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirImplicitAwareBodyResolveTransformer;", "transformElement", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "data", "(Lorg/jetbrains/kotlin/fir/FirElement;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", "transformFile", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "file", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
@AdapterForResolveProcessor
public final class FirImplicitTypeBodyResolveTransformerAdapter extends FirTransformer<Object> {
    private final ImplicitBodyResolveComputationSession implicitBodyResolveComputationSession;
    private final ReturnTypeCalculatorWithJump returnTypeCalculator;
    private final FirImplicitAwareBodyResolveTransformer transformer;

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public <E extends FirElement> E transformElement(E element, Object data) {
        Intrinsics.checkNotNullParameter(element, "element");
        return element;
    }

    public FirImplicitTypeBodyResolveTransformerAdapter(FirSession session, ScopeSession scopeSession) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        ImplicitBodyResolveComputationSession implicitBodyResolveComputationSession = new ImplicitBodyResolveComputationSession();
        this.implicitBodyResolveComputationSession = implicitBodyResolveComputationSession;
        ReturnTypeCalculatorWithJump returnTypeCalculatorWithJump = new ReturnTypeCalculatorWithJump(scopeSession, implicitBodyResolveComputationSession, null, null, 12, null);
        this.returnTypeCalculator = returnTypeCalculatorWithJump;
        this.transformer = new FirImplicitAwareBodyResolveTransformer(session, scopeSession, implicitBodyResolveComputationSession, FirResolvePhase.IMPLICIT_TYPES_BODY_RESOLVE, true, returnTypeCalculatorWithJump, null, null, 192, null);
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirFile transformFile(FirFile file, Object data) {
        Intrinsics.checkNotNullParameter(file, "file");
        try {
            return (FirFile) file.transform(this.transformer, ResolutionMode.ContextIndependent.INSTANCE);
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(file.getModuleData().getSession()).handleExceptionOnFileAnalysis(file, th);
            throw null;
        }
    }
}
