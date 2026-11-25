package org.jetbrains.kotlin.fir.resolve.inference;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.fir.expressions.FirExpression;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: PostponedArgumentsAnalyzer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007J\u000f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u000b\u0010\r\u001a\u0004\u0018\u00010\u0006HÆ\u0003J%\u0010\u000e\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/inference/ReturnArgumentsAnalysisResult;", "", "returnArguments", "", "Lorg/jetbrains/kotlin/fir/expressions/FirExpression;", "inferenceSession", "Lorg/jetbrains/kotlin/fir/resolve/inference/FirInferenceSession;", "(Ljava/util/Collection;Lorg/jetbrains/kotlin/fir/resolve/inference/FirInferenceSession;)V", "getInferenceSession", "()Lorg/jetbrains/kotlin/fir/resolve/inference/FirInferenceSession;", "getReturnArguments", "()Ljava/util/Collection;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ReturnArgumentsAnalysisResult {
    private final FirInferenceSession inferenceSession;
    private final Collection<FirExpression> returnArguments;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ReturnArgumentsAnalysisResult copy$default(ReturnArgumentsAnalysisResult returnArgumentsAnalysisResult, Collection collection, FirInferenceSession firInferenceSession, int i, Object obj) {
        if ((i & 1) != 0) {
            collection = returnArgumentsAnalysisResult.returnArguments;
        }
        if ((i & 2) != 0) {
            firInferenceSession = returnArgumentsAnalysisResult.inferenceSession;
        }
        return returnArgumentsAnalysisResult.copy(collection, firInferenceSession);
    }

    public final Collection<FirExpression> component1() {
        return this.returnArguments;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final FirInferenceSession getInferenceSession() {
        return this.inferenceSession;
    }

    public final ReturnArgumentsAnalysisResult copy(Collection<? extends FirExpression> returnArguments, FirInferenceSession inferenceSession) {
        Intrinsics.checkNotNullParameter(returnArguments, "returnArguments");
        return new ReturnArgumentsAnalysisResult(returnArguments, inferenceSession);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ReturnArgumentsAnalysisResult)) {
            return false;
        }
        ReturnArgumentsAnalysisResult returnArgumentsAnalysisResult = (ReturnArgumentsAnalysisResult) other;
        return Intrinsics.areEqual(this.returnArguments, returnArgumentsAnalysisResult.returnArguments) && Intrinsics.areEqual(this.inferenceSession, returnArgumentsAnalysisResult.inferenceSession);
    }

    public int hashCode() {
        int iHashCode = this.returnArguments.hashCode() * 31;
        FirInferenceSession firInferenceSession = this.inferenceSession;
        return iHashCode + (firInferenceSession == null ? 0 : firInferenceSession.hashCode());
    }

    public String toString() {
        return "ReturnArgumentsAnalysisResult(returnArguments=" + this.returnArguments + ", inferenceSession=" + this.inferenceSession + ')';
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ReturnArgumentsAnalysisResult(Collection<? extends FirExpression> returnArguments, FirInferenceSession firInferenceSession) {
        Intrinsics.checkNotNullParameter(returnArguments, "returnArguments");
        this.returnArguments = returnArguments;
        this.inferenceSession = firInferenceSession;
    }

    public final Collection<FirExpression> getReturnArguments() {
        return this.returnArguments;
    }

    public final FirInferenceSession getInferenceSession() {
        return this.inferenceSession;
    }
}
