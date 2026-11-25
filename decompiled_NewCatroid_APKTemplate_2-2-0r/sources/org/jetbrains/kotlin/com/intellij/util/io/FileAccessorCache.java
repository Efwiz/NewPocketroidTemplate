package org.jetbrains.kotlin.com.intellij.util.io;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.jetbrains.kotlin.com.intellij.util.containers.SLRUMap;
import org.jetbrains.kotlin.com.intellij.util.containers.hash.EqualityPolicy;

/* JADX INFO: loaded from: classes3.dex */
public abstract class FileAccessorCache<K, T> implements EqualityPolicy<K> {
    private final SLRUMap<K, Handle<T>> myCache;
    private final List<T> myElementsToBeDisposed = new ArrayList();
    private final Object myCacheLock = new Object();
    private final Object myUpdateLock = new Object();

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "org/jetbrains/kotlin/com/intellij/util/io/FileAccessorCache", "get"));
    }

    protected abstract T createAccessor(K k) throws IOException;

    protected abstract void disposeAccessor(T t) throws IOException;

    public FileAccessorCache(int i, int i2) {
        this.myCache = new SLRUMap<K, Handle<T>>(i, i2, this) { // from class: org.jetbrains.kotlin.com.intellij.util.io.FileAccessorCache.1
            private static /* synthetic */ void $$$reportNull$$$0(int i3) {
                throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "value", "org/jetbrains/kotlin/com/intellij/util/io/FileAccessorCache$1", "onDropFromCache"));
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // org.jetbrains.kotlin.com.intellij.util.containers.SLRUMap
            public void onDropFromCache(K k, Handle<T> handle) {
                if (handle == null) {
                    $$$reportNull$$$0(0);
                }
                handle.release();
            }
        };
    }

    public final Handle<T> get(K k) {
        Handle<T> ifCached = getIfCached(k);
        if (ifCached != null) {
            if (ifCached == null) {
                $$$reportNull$$$0(0);
            }
            return ifCached;
        }
        synchronized (this.myUpdateLock) {
            Handle<T> ifCached2 = getIfCached(k);
            if (ifCached2 != null) {
                if (ifCached2 == null) {
                    $$$reportNull$$$0(1);
                }
                return ifCached2;
            }
            Handle<T> handleCreateHandle = createHandle(k);
            if (handleCreateHandle == null) {
                $$$reportNull$$$0(2);
            }
            return handleCreateHandle;
        }
    }

    private Handle<T> createHandle(K k) {
        try {
            Handle<T> handle = new Handle<>(createAccessor(k), this);
            handle.allocate();
            synchronized (this.myCacheLock) {
                this.myCache.put(k, handle);
            }
            disposeInvalidAccessors();
            return handle;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void disposeInvalidAccessors() {
        synchronized (this.myCacheLock) {
            if (this.myElementsToBeDisposed.isEmpty()) {
                return;
            }
            ArrayList arrayList = new ArrayList(this.myElementsToBeDisposed);
            this.myElementsToBeDisposed.clear();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                try {
                    disposeAccessor(it.next());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public Handle<T> getIfCached(K k) {
        Handle<T> handle;
        synchronized (this.myCacheLock) {
            handle = this.myCache.get(k);
            if (handle != null) {
                handle.allocate();
            }
        }
        return handle;
    }

    public boolean remove(K k) {
        boolean zRemove;
        try {
            synchronized (this.myCacheLock) {
                zRemove = this.myCache.remove(k);
            }
            synchronized (this.myUpdateLock) {
                disposeInvalidAccessors();
            }
            return zRemove;
        } catch (Throwable th) {
            synchronized (this.myUpdateLock) {
                disposeInvalidAccessors();
                throw th;
            }
        }
    }

    public void clear() {
        try {
            synchronized (this.myCacheLock) {
                this.myCache.clear();
            }
            synchronized (this.myUpdateLock) {
                disposeInvalidAccessors();
            }
        } catch (Throwable th) {
            synchronized (this.myUpdateLock) {
                disposeInvalidAccessors();
                throw th;
            }
        }
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.containers.hash.EqualityPolicy
    public int getHashCode(K k) {
        return k.hashCode();
    }

    @Override // org.jetbrains.kotlin.com.intellij.util.containers.hash.EqualityPolicy
    public boolean isEqual(K k, K k2) {
        return k.equals(k2);
    }

    public static final class Handle<T> extends ResourceHandle<T> {
        private final FileAccessorCache<?, ? super T> myOwner;
        private final AtomicInteger myRefCount;
        private final T myResource;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            String str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
            Object[] objArr = new Object[i != 2 ? 3 : 2];
            if (i == 1) {
                objArr[0] = "owner";
            } else if (i != 2) {
                objArr[0] = "fileAccessor";
            } else {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/io/FileAccessorCache$Handle";
            }
            if (i != 2) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/util/io/FileAccessorCache$Handle";
            } else {
                objArr[1] = "get";
            }
            if (i != 2) {
                objArr[2] = "<init>";
            }
            String str2 = String.format(str, objArr);
            if (i == 2) {
                throw new IllegalStateException(str2);
            }
        }

        public Handle(T t, FileAccessorCache<?, ? super T> fileAccessorCache) {
            if (t == null) {
                $$$reportNull$$$0(0);
            }
            if (fileAccessorCache == null) {
                $$$reportNull$$$0(1);
            }
            this.myRefCount = new AtomicInteger(1);
            this.myResource = t;
            this.myOwner = fileAccessorCache;
        }

        public void allocate() {
            this.myRefCount.incrementAndGet();
        }

        public void release() {
            if (this.myRefCount.decrementAndGet() == 0) {
                synchronized (((FileAccessorCache) this.myOwner).myCacheLock) {
                    ((FileAccessorCache) this.myOwner).myElementsToBeDisposed.add(this.myResource);
                }
            }
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            release();
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.io.ResourceHandle
        public T get() {
            T t = this.myResource;
            if (t == null) {
                $$$reportNull$$$0(2);
            }
            return t;
        }
    }
}
