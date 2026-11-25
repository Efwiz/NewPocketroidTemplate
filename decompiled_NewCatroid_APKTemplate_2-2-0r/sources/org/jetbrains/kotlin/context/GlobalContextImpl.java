package org.jetbrains.kotlin.context;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.storage.ExceptionTracker;
import org.jetbrains.kotlin.storage.LockBasedStorageManager;
import org.jetbrains.kotlin.storage.StorageManager;

/* JADX INFO: compiled from: context.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0016\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/context/GlobalContextImpl;", "Lorg/jetbrains/kotlin/context/SimpleGlobalContext;", "storageManager", "Lorg/jetbrains/kotlin/storage/LockBasedStorageManager;", "exceptionTracker", "Lorg/jetbrains/kotlin/storage/ExceptionTracker;", "(Lorg/jetbrains/kotlin/storage/LockBasedStorageManager;Lorg/jetbrains/kotlin/storage/ExceptionTracker;)V", "getStorageManager", "()Lorg/jetbrains/kotlin/storage/LockBasedStorageManager;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class GlobalContextImpl extends SimpleGlobalContext {
    private final LockBasedStorageManager storageManager;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GlobalContextImpl(LockBasedStorageManager storageManager, ExceptionTracker exceptionTracker) {
        super(storageManager, exceptionTracker);
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(exceptionTracker, "exceptionTracker");
        StorageManager storageManager2 = super.getStorageManager();
        Intrinsics.checkNotNull(storageManager2, "null cannot be cast to non-null type org.jetbrains.kotlin.storage.LockBasedStorageManager");
        this.storageManager = (LockBasedStorageManager) storageManager2;
    }

    @Override // org.jetbrains.kotlin.context.SimpleGlobalContext, org.jetbrains.kotlin.context.GlobalContext
    public LockBasedStorageManager getStorageManager() {
        return this.storageManager;
    }
}
