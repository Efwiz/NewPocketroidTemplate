package org.jetbrains.kotlin.utils;

import androidx.exifinterface.media.ExifInterface;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: coreLib.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\u001a/\u0010\u0000\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u0002*\u0004\u0018\u0001H\u00012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\u0006\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0007"}, d2 = {"sure", ExifInterface.GPS_DIRECTION_TRUE, "", CrashHianalyticsData.MESSAGE, "Lkotlin/Function0;", "", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "util.runtime"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class CoreLibKt {
    public static final <T> T sure(T t, Function0<String> message) {
        Intrinsics.checkNotNullParameter(message, "message");
        if (t != null) {
            return t;
        }
        throw new AssertionError(message.invoke());
    }
}
