package org.catrobat.catroid.codeanalysis;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.huawei.hms.ml.language.common.utils.Constant;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/codeanalysis/AnalysisResult.class
  classes12.dex
 */
/* JADX INFO: compiled from: AnalysisResult.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0005HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0014"}, d2 = {"Lorg/catrobat/catroid/codeanalysis/AnalysisResult;", "", "severity", "Lorg/catrobat/catroid/codeanalysis/Severity;", CrashHianalyticsData.MESSAGE, "", "(Lorg/catrobat/catroid/codeanalysis/Severity;Ljava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "getSeverity", "()Lorg/catrobat/catroid/codeanalysis/Severity;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class AnalysisResult {
    private final String message;
    private final Severity severity;

    public static /* synthetic */ AnalysisResult copy$default(AnalysisResult analysisResult, Severity severity, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            severity = analysisResult.severity;
        }
        if ((i & 2) != 0) {
            str = analysisResult.message;
        }
        return analysisResult.copy(severity, str);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final Severity getSeverity() {
        return this.severity;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getMessage() {
        return this.message;
    }

    public final AnalysisResult copy(Severity severity, String message) {
        Intrinsics.checkNotNullParameter(severity, "severity");
        Intrinsics.checkNotNullParameter(message, "message");
        return new AnalysisResult(severity, message);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AnalysisResult)) {
            return false;
        }
        AnalysisResult analysisResult = (AnalysisResult) other;
        return this.severity == analysisResult.severity && Intrinsics.areEqual(this.message, analysisResult.message);
    }

    public int hashCode() {
        return (this.severity.hashCode() * 31) + this.message.hashCode();
    }

    public String toString() {
        return "AnalysisResult(severity=" + this.severity + ", message=" + this.message + Constant.AFTER_QUTO;
    }

    public AnalysisResult(Severity severity, String message) {
        Intrinsics.checkNotNullParameter(severity, "severity");
        Intrinsics.checkNotNullParameter(message, "message");
        this.severity = severity;
        this.message = message;
    }

    public final Severity getSeverity() {
        return this.severity;
    }

    public final String getMessage() {
        return this.message;
    }
}
