package org.jetbrains.kotlin.buildtools.api;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CompilerArgumentsParseException.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/buildtools/api/CompilerArgumentsParseException;", "Lorg/jetbrains/kotlin/buildtools/api/KotlinBuildToolsException;", CrashHianalyticsData.MESSAGE, "", "(Ljava/lang/String;)V", "kotlin-build-tools-api"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class CompilerArgumentsParseException extends KotlinBuildToolsException {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CompilerArgumentsParseException(String message) {
        super(message, null);
        Intrinsics.checkNotNullParameter(message, "message");
    }
}
