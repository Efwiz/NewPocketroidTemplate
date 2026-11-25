package org.jetbrains.kotlin.analyzer;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CompilationErrorException.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00060\u0001j\u0002`\u0002B\u0007\b\u0016¢\u0006\u0002\u0010\u0003B\u000f\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/analyzer/CompilationErrorException;", "Ljava/lang/RuntimeException;", "Lkotlin/RuntimeException;", "()V", CrashHianalyticsData.MESSAGE, "", "(Ljava/lang/String;)V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CompilationErrorException extends RuntimeException {
    public CompilationErrorException() {
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CompilationErrorException(String message) {
        super(message);
        Intrinsics.checkNotNullParameter(message, "message");
    }
}
