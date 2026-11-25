package org.jetbrains.kotlin.context;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.storage.ExceptionTracker;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: context.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0016\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/context/SimpleGlobalContext;", "Lorg/jetbrains/kotlin/context/GlobalContext;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "exceptionTracker", "Lorg/jetbrains/kotlin/storage/ExceptionTracker;", "(Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/storage/ExceptionTracker;)V", "getExceptionTracker", "()Lorg/jetbrains/kotlin/storage/ExceptionTracker;", "getStorageManager", "()Lorg/jetbrains/kotlin/storage/StorageManager;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class SimpleGlobalContext implements GlobalContext {
    private final ExceptionTracker exceptionTracker;
    private final StorageManager storageManager;

    public SimpleGlobalContext(StorageManager storageManager, ExceptionTracker exceptionTracker) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(exceptionTracker, "exceptionTracker");
        this.storageManager = storageManager;
        this.exceptionTracker = exceptionTracker;
    }

    @Override // org.jetbrains.kotlin.context.GlobalContext
    public StorageManager getStorageManager() {
        return this.storageManager;
    }

    @Override // org.jetbrains.kotlin.context.GlobalContext
    public ExceptionTracker getExceptionTracker() {
        return this.exceptionTracker;
    }
}
