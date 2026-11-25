package org.jetbrains.kotlin.util;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AnalysisExceptions.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0007\u0018\u00002\u00060\u0001j\u0002`\u0002B-\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0016\b\u0002\u0010\u0007\u001a\u0010\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\t\u0018\u00010\b¢\u0006\u0002\u0010\nR\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001c\u0010\u0007\u001a\u0010\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\t\u0018\u00010\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/util/FileAnalysisException;", "Ljava/lang/Exception;", "Lkotlin/Exception;", "path", "", "cause", "", "lineAndOffset", "Lkotlin/Pair;", "", "(Ljava/lang/String;Ljava/lang/Throwable;Lkotlin/Pair;)V", "getCause", "()Ljava/lang/Throwable;", CrashHianalyticsData.MESSAGE, "getMessage", "()Ljava/lang/String;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FileAnalysisException extends Exception {
    private final Throwable cause;
    private final Pair<Integer, Integer> lineAndOffset;
    private final String path;

    public /* synthetic */ FileAnalysisException(String str, Throwable th, Pair pair, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, th, (i & 4) != 0 ? null : pair);
    }

    @Override // java.lang.Throwable
    public Throwable getCause() {
        return this.cause;
    }

    public FileAnalysisException(String path, Throwable cause, Pair<Integer, Integer> pair) {
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(cause, "cause");
        this.path = path;
        this.cause = cause;
        this.lineAndOffset = pair;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        Pair<Integer, Integer> pair = this.lineAndOffset;
        if (pair == null) {
            return "Somewhere in file " + this.path + ": " + AnalysisExceptionsKt.getClassNameAndMessage(getCause());
        }
        return "While analysing " + this.path + ':' + (pair.component1().intValue() + 1) + ':' + (pair.component2().intValue() + 1) + ": " + AnalysisExceptionsKt.getClassNameAndMessage(getCause());
    }
}
