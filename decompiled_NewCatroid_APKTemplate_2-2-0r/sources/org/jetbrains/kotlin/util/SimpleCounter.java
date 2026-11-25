package org.jetbrains.kotlin.util;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PerformanceCounter.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J!\u0010\u0005\u001a\u0002H\u0006\"\u0004\b\u0000\u0010\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00060\bH\u0014¢\u0006\u0002\u0010\t¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/util/SimpleCounter;", "Lorg/jetbrains/kotlin/util/PerformanceCounter;", "name", "", "(Ljava/lang/String;)V", "countTime", ExifInterface.GPS_DIRECTION_TRUE, "block", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "util.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class SimpleCounter extends PerformanceCounter {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SimpleCounter(String name2) {
        super(name2);
        Intrinsics.checkNotNullParameter(name2, "name");
    }

    @Override // org.jetbrains.kotlin.util.PerformanceCounter
    protected <T> T countTime(Function0<? extends T> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        long jCurrentTime = PerformanceCounter.INSTANCE.currentTime();
        try {
            return block.invoke();
        } finally {
            incrementTime(PerformanceCounter.INSTANCE.currentTime() - jCurrentTime);
        }
    }
}
