package org.jetbrains.kotlin.fir.resolve.transformers;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.visitors.FirDefaultTransformer;

/* JADX INFO: compiled from: FirAbstractPhaseTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0004J\u001d\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0014R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0012\u0010\b\u001a\u00020\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u0007¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/FirAbstractPhaseTransformer;", "D", "Lorg/jetbrains/kotlin/fir/visitors/FirDefaultTransformer;", "baseTransformerPhase", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "(Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;)V", "getBaseTransformerPhase", "()Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "transformerPhase", "getTransformerPhase", "checkSessionConsistency", "", "file", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "transformFile", "data", "(Lorg/jetbrains/kotlin/fir/declarations/FirFile;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirAbstractPhaseTransformer<D> extends FirDefaultTransformer<D> {
    private final FirResolvePhase baseTransformerPhase;

    public abstract FirSession getSession();

    public final FirResolvePhase getBaseTransformerPhase() {
        return this.baseTransformerPhase;
    }

    public FirAbstractPhaseTransformer(FirResolvePhase baseTransformerPhase) {
        Intrinsics.checkNotNullParameter(baseTransformerPhase, "baseTransformerPhase");
        this.baseTransformerPhase = baseTransformerPhase;
        FirResolvePhase firResolvePhase = FirResolvePhase.RAW_FIR;
    }

    public FirResolvePhase getTransformerPhase() {
        return this.baseTransformerPhase;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirFile transformFile(FirFile file, D data) {
        Intrinsics.checkNotNullParameter(file, "file");
        checkSessionConsistency(file);
        try {
            return super.transformFile(file, data);
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(file.getModuleData().getSession()).handleExceptionOnFileAnalysis(file, th);
            throw null;
        }
    }

    protected final void checkSessionConsistency(FirFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        getSession();
        file.getModuleData().getSession();
    }
}
