package org.jetbrains.kotlin.js.resolve.diagnostics;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;

/* JADX INFO: compiled from: JsCallChecker.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0016\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/js/resolve/diagnostics/JsCallData;", "", "reportRange", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/TextRange;", CrashHianalyticsData.MESSAGE, "", "(Lcom/intellij/openapi/util/TextRange;Ljava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "getReportRange", "()Lcom/intellij/openapi/util/TextRange;", "js.frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class JsCallData {
    private final String message;
    private final TextRange reportRange;

    public JsCallData(TextRange reportRange, String message) {
        Intrinsics.checkNotNullParameter(reportRange, "reportRange");
        Intrinsics.checkNotNullParameter(message, "message");
        this.reportRange = reportRange;
        this.message = message;
    }

    public final String getMessage() {
        return this.message;
    }

    public final TextRange getReportRange() {
        return this.reportRange;
    }
}
