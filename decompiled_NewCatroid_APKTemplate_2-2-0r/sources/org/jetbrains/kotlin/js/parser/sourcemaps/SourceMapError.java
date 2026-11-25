package org.jetbrains.kotlin.js.parser.sourcemaps;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SourceMap.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/js/parser/sourcemaps/SourceMapError;", "Lorg/jetbrains/kotlin/js/parser/sourcemaps/SourceMapParseResult;", CrashHianalyticsData.MESSAGE, "", "(Ljava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "js.parser"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SourceMapError extends SourceMapParseResult {
    private final String message;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SourceMapError(String message) {
        super(null);
        Intrinsics.checkNotNullParameter(message, "message");
        this.message = message;
    }

    public final String getMessage() {
        return this.message;
    }
}
