package org.jetbrains.kotlin.fir.serialization.constant;

import kotlin.Metadata;
import org.jetbrains.kotlin.constant.ConstantValue;
import org.jetbrains.kotlin.constant.EvaluatedConstTracker;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.expressions.FirExpression;

/* JADX INFO: compiled from: ConstValueProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0012\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H&R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R(\u0010\t\u001a\u0004\u0018\u00010\b2\b\u0010\u0007\u001a\u0004\u0018\u00010\b@GX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0012\u0010\u000e\u001a\u00020\u000fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/fir/serialization/constant/ConstValueProvider;", "", "()V", "evaluatedConstTracker", "Lorg/jetbrains/kotlin/constant/EvaluatedConstTracker;", "getEvaluatedConstTracker", "()Lorg/jetbrains/kotlin/constant/EvaluatedConstTracker;", "<set-?>", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "processingFirFile", "getProcessingFirFile", "()Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "setProcessingFirFile", "(Lorg/jetbrains/kotlin/fir/declarations/FirFile;)V", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "findConstantValueFor", "Lorg/jetbrains/kotlin/constant/ConstantValue;", "firExpression", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "fir-serialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ConstValueProvider {
    private FirFile processingFirFile;

    public abstract ConstantValue<?> findConstantValueFor(FirExpression firExpression);

    public abstract EvaluatedConstTracker getEvaluatedConstTracker();

    public abstract FirSession getSession();

    public final FirFile getProcessingFirFile() {
        return this.processingFirFile;
    }

    @ConstValueProviderInternals
    public final void setProcessingFirFile(FirFile firFile) {
        this.processingFirFile = firFile;
    }
}
