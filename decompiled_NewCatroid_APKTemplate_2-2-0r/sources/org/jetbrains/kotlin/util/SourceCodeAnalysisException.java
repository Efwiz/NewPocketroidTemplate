package org.jetbrains.kotlin.util;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;

/* JADX INFO: compiled from: AnalysisExceptions.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u00002\u00060\u0001j\u0002`\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/util/SourceCodeAnalysisException;", "Ljava/lang/Exception;", "Lkotlin/Exception;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "cause", "", "(Lorg/jetbrains/kotlin/KtSourceElement;Ljava/lang/Throwable;)V", "getCause", "()Ljava/lang/Throwable;", CrashHianalyticsData.MESSAGE, "", "getMessage", "()Ljava/lang/String;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SourceCodeAnalysisException extends Exception {
    private final Throwable cause;
    private final KtSourceElement source;

    public SourceCodeAnalysisException(KtSourceElement source, Throwable cause) {
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(cause, "cause");
        this.source = source;
        this.cause = cause;
    }

    @Override // java.lang.Throwable
    public Throwable getCause() {
        return this.cause;
    }

    public final KtSourceElement getSource() {
        return this.source;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return AnalysisExceptionsKt.getClassNameAndMessage(getCause());
    }
}
