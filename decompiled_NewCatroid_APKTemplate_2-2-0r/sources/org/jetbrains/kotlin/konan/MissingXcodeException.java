package org.jetbrains.kotlin.konan;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Exceptions.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/konan/MissingXcodeException;", "Lorg/jetbrains/kotlin/konan/KonanException;", CrashHianalyticsData.MESSAGE, "", "cause", "", "(Ljava/lang/String;Ljava/lang/Throwable;)V", "kotlin-native-utils"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class MissingXcodeException extends KonanException {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MissingXcodeException(String message, Throwable th) {
        super(message, th);
        Intrinsics.checkNotNullParameter(message, "message");
    }

    public /* synthetic */ MissingXcodeException(String str, Throwable th, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : th);
    }
}
