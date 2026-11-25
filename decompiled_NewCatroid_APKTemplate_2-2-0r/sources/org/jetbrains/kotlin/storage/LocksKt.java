package org.jetbrains.kotlin.storage;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: locks.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a+\u0010\u0002\u001a\u0002H\u0003\"\u0004\b\u0000\u0010\u0003*\u00020\u00042\u000e\b\u0004\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0006H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\u0007\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\b"}, d2 = {"CHECK_CANCELLATION_PERIOD_MS", "", "guarded", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/storage/SimpleLock;", "computable", "Lkotlin/Function0;", "(Lorg/jetbrains/kotlin/storage/SimpleLock;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "util.runtime"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class LocksKt {
    public static final <T> T guarded(SimpleLock simpleLock, Function0<? extends T> computable) {
        Intrinsics.checkNotNullParameter(simpleLock, "<this>");
        Intrinsics.checkNotNullParameter(computable, "computable");
        simpleLock.lock();
        try {
            return computable.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            simpleLock.unlock();
            InlineMarker.finallyEnd(1);
        }
    }
}
