package org.jetbrains.kotlin.buildtools.api;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: KotlinBuildToolsException.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00060\u0001j\u0002`\u0002B\u000f\b\u0004\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005\u0082\u0001\u0001\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/buildtools/api/KotlinBuildToolsException;", "Ljava/lang/Exception;", "Lkotlin/Exception;", CrashHianalyticsData.MESSAGE, "", "(Ljava/lang/String;)V", "Lorg/jetbrains/kotlin/buildtools/api/CompilerArgumentsParseException;", "kotlin-build-tools-api"}, k = 1, mv = {1, 5, 1}, xi = 48)
public abstract class KotlinBuildToolsException extends Exception {
    public /* synthetic */ KotlinBuildToolsException(String str, DefaultConstructorMarker defaultConstructorMarker) {
        this(str);
    }

    private KotlinBuildToolsException(String str) {
        super(str);
    }
}
