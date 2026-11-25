package org.jetbrains.kotlin.storage;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: locks.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0002\u001a\u00020\u0007H\u0016J\b\u0010\b\u001a\u00020\u0007H\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/storage/DefaultSimpleLock;", "Lorg/jetbrains/kotlin/storage/SimpleLock;", "lock", "Ljava/util/concurrent/locks/Lock;", "(Ljava/util/concurrent/locks/Lock;)V", "getLock", "()Ljava/util/concurrent/locks/Lock;", "", "unlock", "util.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class DefaultSimpleLock implements SimpleLock {
    private final Lock lock;

    /* JADX WARN: Multi-variable type inference failed */
    public DefaultSimpleLock() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public DefaultSimpleLock(Lock lock) {
        Intrinsics.checkNotNullParameter(lock, "lock");
        this.lock = lock;
    }

    public /* synthetic */ DefaultSimpleLock(ReentrantLock reentrantLock, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new ReentrantLock() : reentrantLock);
    }

    protected final Lock getLock() {
        return this.lock;
    }

    @Override // org.jetbrains.kotlin.storage.SimpleLock
    public void lock() {
        this.lock.lock();
    }

    @Override // org.jetbrains.kotlin.storage.SimpleLock
    public void unlock() {
        this.lock.unlock();
    }
}
