package org.jetbrains.kotlin.resolve.calls.components;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: ExternalComponents.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000e\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u0010\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0007HÆ\u0003J)\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00072\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/components/ReturnArgumentsAnalysisResult;", "", "returnArgumentsInfo", "Lorg/jetbrains/kotlin/resolve/calls/components/ReturnArgumentsInfo;", "inferenceSession", "Lorg/jetbrains/kotlin/resolve/calls/components/InferenceSession;", "hasInapplicableCallForBuilderInference", "", "(Lorg/jetbrains/kotlin/resolve/calls/components/ReturnArgumentsInfo;Lorg/jetbrains/kotlin/resolve/calls/components/InferenceSession;Z)V", "getHasInapplicableCallForBuilderInference", "()Z", "getInferenceSession", "()Lorg/jetbrains/kotlin/resolve/calls/components/InferenceSession;", "getReturnArgumentsInfo", "()Lorg/jetbrains/kotlin/resolve/calls/components/ReturnArgumentsInfo;", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ReturnArgumentsAnalysisResult {
    private final boolean hasInapplicableCallForBuilderInference;
    private final InferenceSession inferenceSession;
    private final ReturnArgumentsInfo returnArgumentsInfo;

    public static /* synthetic */ ReturnArgumentsAnalysisResult copy$default(ReturnArgumentsAnalysisResult returnArgumentsAnalysisResult, ReturnArgumentsInfo returnArgumentsInfo, InferenceSession inferenceSession, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            returnArgumentsInfo = returnArgumentsAnalysisResult.returnArgumentsInfo;
        }
        if ((i & 2) != 0) {
            inferenceSession = returnArgumentsAnalysisResult.inferenceSession;
        }
        if ((i & 4) != 0) {
            z = returnArgumentsAnalysisResult.hasInapplicableCallForBuilderInference;
        }
        return returnArgumentsAnalysisResult.copy(returnArgumentsInfo, inferenceSession, z);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final ReturnArgumentsInfo getReturnArgumentsInfo() {
        return this.returnArgumentsInfo;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final InferenceSession getInferenceSession() {
        return this.inferenceSession;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final boolean getHasInapplicableCallForBuilderInference() {
        return this.hasInapplicableCallForBuilderInference;
    }

    public final ReturnArgumentsAnalysisResult copy(ReturnArgumentsInfo returnArgumentsInfo, InferenceSession inferenceSession, boolean hasInapplicableCallForBuilderInference) {
        Intrinsics.checkNotNullParameter(returnArgumentsInfo, "returnArgumentsInfo");
        return new ReturnArgumentsAnalysisResult(returnArgumentsInfo, inferenceSession, hasInapplicableCallForBuilderInference);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ReturnArgumentsAnalysisResult)) {
            return false;
        }
        ReturnArgumentsAnalysisResult returnArgumentsAnalysisResult = (ReturnArgumentsAnalysisResult) other;
        return Intrinsics.areEqual(this.returnArgumentsInfo, returnArgumentsAnalysisResult.returnArgumentsInfo) && Intrinsics.areEqual(this.inferenceSession, returnArgumentsAnalysisResult.inferenceSession) && this.hasInapplicableCallForBuilderInference == returnArgumentsAnalysisResult.hasInapplicableCallForBuilderInference;
    }

    public int hashCode() {
        int iHashCode = this.returnArgumentsInfo.hashCode() * 31;
        InferenceSession inferenceSession = this.inferenceSession;
        return ((iHashCode + (inferenceSession == null ? 0 : inferenceSession.hashCode())) * 31) + Boolean.hashCode(this.hasInapplicableCallForBuilderInference);
    }

    public String toString() {
        return "ReturnArgumentsAnalysisResult(returnArgumentsInfo=" + this.returnArgumentsInfo + ", inferenceSession=" + this.inferenceSession + ", hasInapplicableCallForBuilderInference=" + this.hasInapplicableCallForBuilderInference + ')';
    }

    public ReturnArgumentsAnalysisResult(ReturnArgumentsInfo returnArgumentsInfo, InferenceSession inferenceSession, boolean z) {
        Intrinsics.checkNotNullParameter(returnArgumentsInfo, "returnArgumentsInfo");
        this.returnArgumentsInfo = returnArgumentsInfo;
        this.inferenceSession = inferenceSession;
        this.hasInapplicableCallForBuilderInference = z;
    }

    public /* synthetic */ ReturnArgumentsAnalysisResult(ReturnArgumentsInfo returnArgumentsInfo, InferenceSession inferenceSession, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(returnArgumentsInfo, inferenceSession, (i & 4) != 0 ? false : z);
    }

    public final ReturnArgumentsInfo getReturnArgumentsInfo() {
        return this.returnArgumentsInfo;
    }

    public final InferenceSession getInferenceSession() {
        return this.inferenceSession;
    }

    public final boolean getHasInapplicableCallForBuilderInference() {
        return this.hasInapplicableCallForBuilderInference;
    }
}
