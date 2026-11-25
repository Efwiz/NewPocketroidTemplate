package org.jetbrains.kotlin.cli.metadata;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.analyzer.AnalysisResult;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: CommonAnalysis.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0082\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u00052\b\u0010\u000f\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0010\u001a\u00020\u0011HÖ\u0001J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/cli/metadata/AnalysisResultWithHasErrors;", "", "result", "Lorg/jetbrains/kotlin/analyzer/AnalysisResult;", "hasErrors", "", "(Lorg/jetbrains/kotlin/analyzer/AnalysisResult;Z)V", "getHasErrors", "()Z", "getResult", "()Lorg/jetbrains/kotlin/analyzer/AnalysisResult;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
final /* data */ class AnalysisResultWithHasErrors {
    private final boolean hasErrors;
    private final AnalysisResult result;

    public static /* synthetic */ AnalysisResultWithHasErrors copy$default(AnalysisResultWithHasErrors analysisResultWithHasErrors, AnalysisResult analysisResult, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            analysisResult = analysisResultWithHasErrors.result;
        }
        if ((i & 2) != 0) {
            z = analysisResultWithHasErrors.hasErrors;
        }
        return analysisResultWithHasErrors.copy(analysisResult, z);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final AnalysisResult getResult() {
        return this.result;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final boolean getHasErrors() {
        return this.hasErrors;
    }

    public final AnalysisResultWithHasErrors copy(AnalysisResult result, boolean hasErrors) {
        Intrinsics.checkNotNullParameter(result, "result");
        return new AnalysisResultWithHasErrors(result, hasErrors);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AnalysisResultWithHasErrors)) {
            return false;
        }
        AnalysisResultWithHasErrors analysisResultWithHasErrors = (AnalysisResultWithHasErrors) other;
        return Intrinsics.areEqual(this.result, analysisResultWithHasErrors.result) && this.hasErrors == analysisResultWithHasErrors.hasErrors;
    }

    public int hashCode() {
        return (this.result.hashCode() * 31) + Boolean.hashCode(this.hasErrors);
    }

    public String toString() {
        return "AnalysisResultWithHasErrors(result=" + this.result + ", hasErrors=" + this.hasErrors + ')';
    }

    public AnalysisResultWithHasErrors(AnalysisResult result, boolean z) {
        Intrinsics.checkNotNullParameter(result, "result");
        this.result = result;
        this.hasErrors = z;
    }

    public final boolean getHasErrors() {
        return this.hasErrors;
    }

    public final AnalysisResult getResult() {
        return this.result;
    }
}
