package org.jetbrains.kotlin.com.intellij.util.io;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/* JADX INFO: loaded from: classes3.dex */
public final class StorageLockContext {
    private static final FilePageCache ourDefaultCache = new FilePageCache();
    private final boolean myCacheChannels;
    private final boolean myCheckThreadAccess;
    private final FilePageCache myFilePageCache;
    private final ReentrantReadWriteLock myLock;
    private final boolean myUseReadWriteLock;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = i != 1 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        Object[] objArr = new Object[i != 1 ? 3 : 2];
        if (i != 1) {
            objArr[0] = "filePageCache";
        } else {
            objArr[0] = "org/jetbrains/kotlin/com/intellij/util/io/StorageLockContext";
        }
        if (i != 1) {
            objArr[1] = "org/jetbrains/kotlin/com/intellij/util/io/StorageLockContext";
        } else {
            objArr[1] = "getBufferCache";
        }
        if (i != 1) {
            objArr[2] = "<init>";
        }
        String str2 = String.format(str, objArr);
        if (i == 1) {
            throw new IllegalStateException(str2);
        }
    }

    public StorageLockContext(FilePageCache filePageCache, boolean z, boolean z2, boolean z3) {
        if (filePageCache == null) {
            $$$reportNull$$$0(0);
        }
        this.myLock = new ReentrantReadWriteLock();
        this.myFilePageCache = filePageCache;
        this.myCheckThreadAccess = z;
        this.myUseReadWriteLock = z2;
        this.myCacheChannels = z3;
    }

    public StorageLockContext(boolean z, boolean z2) {
        this(ourDefaultCache, z, z2, false);
    }

    boolean useChannelCache() {
        return this.myCacheChannels;
    }

    public void lockRead() {
        if (this.myUseReadWriteLock) {
            this.myLock.readLock().lock();
        } else {
            this.myLock.writeLock().lock();
        }
    }

    public void unlockRead() {
        if (this.myUseReadWriteLock) {
            this.myLock.readLock().unlock();
        } else {
            this.myLock.writeLock().unlock();
        }
    }

    public void lockWrite() {
        this.myLock.writeLock().lock();
    }

    public void unlockWrite() {
        this.myLock.writeLock().unlock();
    }

    FilePageCache getBufferCache() {
        FilePageCache filePageCache = this.myFilePageCache;
        if (filePageCache == null) {
            $$$reportNull$$$0(1);
        }
        return filePageCache;
    }

    void checkThreadAccess(boolean z) {
        if (this.myCheckThreadAccess) {
            if (z) {
                if (this.myLock.getReadHoldCount() <= 0 && !this.myLock.writeLock().isHeldByCurrentThread()) {
                    throw new IllegalStateException("Must hold StorageLock read lock to access PagedFileStorage");
                }
            } else if (!this.myLock.writeLock().isHeldByCurrentThread()) {
                throw new IllegalStateException("Must hold StorageLock write lock to access PagedFileStorage");
            }
        }
    }
}
