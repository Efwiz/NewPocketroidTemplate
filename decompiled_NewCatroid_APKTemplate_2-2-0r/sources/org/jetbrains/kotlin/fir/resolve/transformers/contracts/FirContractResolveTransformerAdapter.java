package org.jetbrains.kotlin.fir.resolve.transformers.contracts;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.resolve.ResolutionMode;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.transformers.AdapterForResolveProcessor;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;

/* JADX INFO: compiled from: FirContractResolveTransformerAdapter.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J)\u0010\n\u001a\u0002H\u000b\"\b\b\u0000\u0010\u000b*\u00020\f2\u0006\u0010\r\u001a\u0002H\u000b2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0002H\u0016¢\u0006\u0002\u0010\u000fJ\u001a\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\b\u0010\u000e\u001a\u0004\u0018\u00010\u0002H\u0016R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/contracts/FirContractResolveTransformerAdapter;", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;)V", "transformer", "Lorg/jetbrains/kotlin/fir/resolve/transformers/contracts/FirContractResolveTransformer;", "transformElement", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "data", "(Lorg/jetbrains/kotlin/fir/FirElement;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", "transformFile", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "file", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
@AdapterForResolveProcessor
public final class FirContractResolveTransformerAdapter extends FirTransformer<Object> {
    private final FirContractResolveTransformer transformer;

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public <E extends FirElement> E transformElement(E element, Object data) {
        Intrinsics.checkNotNullParameter(element, "element");
        return element;
    }

    public FirContractResolveTransformerAdapter(FirSession session, ScopeSession scopeSession) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        this.transformer = new FirContractResolveTransformer(session, scopeSession, null, 4, null);
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
