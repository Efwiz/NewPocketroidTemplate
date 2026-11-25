package org.jetbrains.kotlin.storage;

import java.util.concurrent.atomic.AtomicLong;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.util.ModificationTracker;
import org.jetbrains.kotlin.util.ReenteringLazyValueComputationException;
import org.jetbrains.kotlin.utils.ExceptionUtilsKt;

/* JADX INFO: compiled from: ExceptionTracker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0016\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0006\u001a\u00020\u0007H\u0016J\u0014\u0010\b\u001a\u00060\tj\u0002`\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\u000eH\u0002J\b\u0010\u000f\u001a\u00020\u0010H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/storage/ExceptionTracker;", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/ModificationTracker;", "Lorg/jetbrains/kotlin/storage/LockBasedStorageManager$ExceptionHandlingStrategy;", "()V", "cancelledTracker", "Ljava/util/concurrent/atomic/AtomicLong;", "getModificationCount", "", "handleException", "Ljava/lang/RuntimeException;", "Lkotlin/RuntimeException;", "throwable", "", "incCounter", "", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class ExceptionTracker implements ModificationTracker, LockBasedStorageManager.ExceptionHandlingStrategy {
    private final AtomicLong cancelledTracker = new AtomicLong();

    @Override // org.jetbrains.kotlin.storage.LockBasedStorageManager.ExceptionHandlingStrategy
    public RuntimeException handleException(Throwable throwable) {
        Intrinsics.checkNotNullParameter(throwable, "throwable");
        if (!(throwable instanceof ReenteringLazyValueComputationException) && (!ExceptionUtilsKt.isProcessCanceledException(throwable) || CacheResetOnProcessCanceled.INSTANCE.getEnabled())) {
            incCounter();
        }
        throw ExceptionUtilsKt.rethrow(throwable);
    }

    private final void incCounter() {
        this.cancelledTracker.getAndIncrement();
    }

    @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ModificationTracker
    public long getModificationCount() {
        return this.cancelledTracker.get();
    }

    public String toString() {
        return getClass().getName() + ": " + getModificationCount();
    }
}
