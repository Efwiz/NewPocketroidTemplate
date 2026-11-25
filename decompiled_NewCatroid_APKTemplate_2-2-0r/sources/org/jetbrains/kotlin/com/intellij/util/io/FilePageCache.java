package org.jetbrains.kotlin.com.intellij.util.io;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.ToIntFunction;
import org.eclipse.jgit.storage.pack.PackConfig;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.com.intellij.util.SystemProperties;
import org.jetbrains.kotlin.com.intellij.util.containers.hash.LinkedHashMap;
import org.jetbrains.kotlin.com.intellij.util.lang.CompoundRuntimeException;
import org.jetbrains.kotlin.com.intellij.util.system.CpuArch;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

/* JADX INFO: loaded from: classes3.dex */
public final class FilePageCache {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int BUFFER_SIZE;
    private static final Logger LOG;
    private static final int LOWER_LIMIT;
    private static final int UPPER_LIMIT;
    private volatile int myMappingChangeCount;
    private volatile long mySize;
    private final Int2ObjectMap<PagedFileStorage> myIndex2Storage = Int2ObjectMaps.synchronize(new Int2ObjectOpenHashMap());
    private final ReentrantLock mySegmentsAccessLock = new ReentrantLock();
    private final ReentrantLock mySegmentsAllocationLock = new ReentrantLock();
    private final ConcurrentLinkedQueue<DirectBufferWrapper> mySegmentsToRemove = new ConcurrentLinkedQueue<>();
    private volatile long mySizeLimit = UPPER_LIMIT;
    private final LinkedHashMap<Integer, DirectBufferWrapper> mySegments = new LinkedHashMap<Integer, DirectBufferWrapper>(10, 0.75f, true) { // from class: org.jetbrains.kotlin.com.intellij.util.io.FilePageCache.1
        @Override // org.jetbrains.kotlin.com.intellij.util.containers.hash.LinkedHashMap
        protected boolean removeEldestEntry(Map.Entry<Integer, DirectBufferWrapper> entry) {
            return FilePageCache.this.mySize > FilePageCache.this.mySizeLimit;
        }

        @Override // org.jetbrains.kotlin.com.intellij.util.containers.hash.LinkedHashMap, java.util.AbstractMap, java.util.Map
        public DirectBufferWrapper remove(Object obj) {
            DirectBufferWrapper directBufferWrapper = (DirectBufferWrapper) super.remove(obj);
            if (directBufferWrapper != null) {
                FilePageCache.access$204(FilePageCache.this);
                FilePageCache.this.mySegmentsToRemove.offer(directBufferWrapper);
                FilePageCache.access$022(FilePageCache.this, directBufferWrapper.getLength());
            }
            return directBufferWrapper;
        }
    };

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str;
        int i2;
        if (i != 1) {
            str = i != 2 ? "Argument for @NotNull parameter '%s' of %s.%s must not be null" : "@NotNull method %s.%s must not return null";
        } else {
            str = "Seems accessed storage has been closed";
        }
        if (i != 1) {
            i2 = i != 2 ? 3 : 2;
        } else {
            i2 = 0;
        }
        Object[] objArr = new Object[i2];
        if (i != 1) {
            if (i != 2) {
                objArr[0] = "storage";
            } else {
                objArr[0] = "org/jetbrains/kotlin/com/intellij/util/io/FilePageCache";
            }
        }
        if (i != 1) {
            if (i != 2) {
                objArr[1] = "org/jetbrains/kotlin/com/intellij/util/io/FilePageCache";
            } else {
                objArr[1] = "createValue";
            }
        }
        if (i != 1 && i != 2) {
            objArr[2] = "registerPagedFileStorage";
        }
        String str2 = String.format(str, objArr);
        if (i != 1 && i != 2) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    static /* synthetic */ long access$022(FilePageCache filePageCache, long j) {
        long j2 = filePageCache.mySize - j;
        filePageCache.mySize = j2;
        return j2;
    }

    static /* synthetic */ int access$204(FilePageCache filePageCache) {
        int i = filePageCache.myMappingChangeCount + 1;
        filePageCache.myMappingChangeCount = i;
        return i;
    }

    static {
        Logger logger = Logger.getInstance((Class<?>) FilePageCache.class);
        LOG = logger;
        int i = CpuArch.is32Bit() ? 200 : 500;
        int iMax = Math.max(1, SystemProperties.getIntProperty("idea.paged.storage.page.size", 10)) * 1048576;
        BUFFER_SIZE = iMax;
        long jMaxDirectMemory = maxDirectMemory() - (((long) iMax) * 2);
        int iMin = (int) Math.min(PackConfig.DEFAULT_MINSIZE_PREVENT_RACY_PACK, jMaxDirectMemory);
        LOWER_LIMIT = iMin;
        int iMin2 = (int) Math.min(Math.max(iMin, SystemProperties.getIntProperty("idea.max.paged.storage.cache", i) * 1048576), jMaxDirectMemory);
        UPPER_LIMIT = iMin2;
        logger.info("lower=" + (iMin / 1048576) + "; upper=" + (iMin2 / 1048576) + "; buffer=" + (iMax / 1048576) + "; max=" + (jMaxDirectMemory / 1048576));
    }

    private static long maxDirectMemory() {
        try {
            try {
                return ((Long) Class.forName("sun.misc.VM").getMethod("maxDirectMemory", new Class[0]).invoke(null, new Object[0])).longValue();
            } catch (Throwable unused) {
                return Runtime.getRuntime().maxMemory();
            }
        } catch (Throwable unused2) {
            Field declaredField = Class.forName("java.nio.Bits").getDeclaredField("maxMemory");
            declaredField.setAccessible(true);
            return ((Long) declaredField.get(null)).longValue();
        }
    }

    int getMappingChangeCount() {
        return this.myMappingChangeCount;
    }

    int registerPagedFileStorage(PagedFileStorage pagedFileStorage) {
        int i;
        if (pagedFileStorage == null) {
            $$$reportNull$$$0(0);
        }
        synchronized (this.myIndex2Storage) {
            int size = this.myIndex2Storage.size();
            while (true) {
                i = size << 16;
                if (this.myIndex2Storage.get(i) != null) {
                    size++;
                } else {
                    this.myIndex2Storage.put(i, pagedFileStorage);
                }
            }
        }
        return i;
    }

    private PagedFileStorage getRegisteredPagedFileStorageByIndex(int i) {
        PagedFileStorage pagedFileStorage = this.myIndex2Storage.get(i);
        if (pagedFileStorage == null) {
            $$$reportNull$$$0(1);
        }
        return pagedFileStorage;
    }

    DirectBufferWrapper get(Integer num, boolean z, boolean z2) throws IOException {
        try {
            this.mySegmentsAccessLock.lock();
            DirectBufferWrapper directBufferWrapper = this.mySegments.get(num);
            if (directBufferWrapper != null) {
                this.mySegmentsAccessLock.unlock();
                return directBufferWrapper;
            }
            this.mySegmentsAccessLock.unlock();
            this.mySegmentsAllocationLock.lock();
            try {
                this.mySegmentsAccessLock.lock();
                try {
                    DirectBufferWrapper directBufferWrapper2 = this.mySegments.get(num);
                    if (directBufferWrapper2 != null) {
                        this.mySegmentsAccessLock.unlock();
                        this.mySegmentsAllocationLock.unlock();
                        return directBufferWrapper2;
                    }
                    this.mySegmentsAccessLock.unlock();
                    long jCurrentTimeMillis = IOStatistics.DEBUG ? System.currentTimeMillis() : 0L;
                    DirectBufferWrapper directBufferWrapperCreateValue = createValue(num, z, z2);
                    if (IOStatistics.DEBUG) {
                        long jCurrentTimeMillis2 = System.currentTimeMillis() - jCurrentTimeMillis;
                        if (jCurrentTimeMillis2 > 100) {
                            IOStatistics.dump("Mapping " + directBufferWrapperCreateValue + " for " + jCurrentTimeMillis2);
                        }
                    }
                    this.mySegmentsAccessLock.lock();
                    try {
                        this.mySegments.put(num, directBufferWrapperCreateValue);
                        this.mySize += (long) directBufferWrapperCreateValue.getLength();
                        this.mySegmentsAccessLock.unlock();
                        ensureSize(this.mySizeLimit);
                        this.mySegmentsAllocationLock.unlock();
                        return directBufferWrapperCreateValue;
                    } catch (Throwable th) {
                        this.mySegmentsAccessLock.unlock();
                        throw th;
                    }
                } catch (Throwable th2) {
                    this.mySegmentsAccessLock.unlock();
                    throw th2;
                }
            } catch (Throwable th3) {
                this.mySegmentsAllocationLock.unlock();
                throw th3;
            }
        } catch (Throwable th4) {
            this.mySegmentsAccessLock.unlock();
            throw th4;
        }
    }

    private void disposeRemovedSegments() {
        if (this.mySegmentsToRemove.isEmpty()) {
            return;
        }
        Iterator<DirectBufferWrapper> it = this.mySegmentsToRemove.iterator();
        while (it.hasNext()) {
            try {
                it.next().release();
            } catch (IOException e) {
                LOG.error((Throwable) e);
            }
            it.remove();
        }
    }

    private void ensureSize(long j) {
        try {
            this.mySegmentsAccessLock.lock();
            while (this.mySize > j) {
                this.mySegments.doRemoveEldestEntry();
            }
            this.mySegmentsAccessLock.unlock();
            disposeRemovedSegments();
        } catch (Throwable th) {
            this.mySegmentsAccessLock.unlock();
            throw th;
        }
    }

    private DirectBufferWrapper createValue(Integer num, boolean z, boolean z2) throws IOException {
        DirectBufferWrapper writeDirect;
        PagedFileStorage registeredPagedFileStorageByIndex = getRegisteredPagedFileStorageByIndex(num.intValue() & (-65536));
        registeredPagedFileStorageByIndex.getStorageLockContext().checkThreadAccess(z);
        long jIntValue = ((long) (num.intValue() & 65535)) * ((long) registeredPagedFileStorageByIndex.myPageSize);
        long length = registeredPagedFileStorageByIndex.length();
        if (jIntValue > length) {
            throw new IndexOutOfBoundsException("off=" + jIntValue + " key.owner.length()=" + length);
        }
        int iMin = (int) Math.min(length - jIntValue, registeredPagedFileStorageByIndex.myPageSize);
        if (z2) {
            writeDirect = DirectBufferWrapper.readOnlyDirect(registeredPagedFileStorageByIndex, jIntValue, iMin);
        } else {
            writeDirect = DirectBufferWrapper.readWriteDirect(registeredPagedFileStorageByIndex, jIntValue, iMin);
        }
        if (writeDirect == null) {
            $$$reportNull$$$0(2);
        }
        return writeDirect;
    }

    private Map<Integer, DirectBufferWrapper> getBuffersOrderedForOwner(int i, StorageLockContext storageLockContext) {
        this.mySegmentsAccessLock.lock();
        try {
            storageLockContext.checkThreadAccess(true);
            TreeMap treeMap = null;
            for (Map.Entry<Integer, DirectBufferWrapper> entry : this.mySegments.entrySet()) {
                if ((entry.getKey().intValue() & (-65536)) == i) {
                    if (treeMap == null) {
                        treeMap = new TreeMap(Comparator.comparingInt(new ToIntFunction() { // from class: org.jetbrains.kotlin.com.intellij.util.io.FilePageCache$$ExternalSyntheticLambda0
                            @Override // java.util.function.ToIntFunction
                            public final int applyAsInt(Object obj) {
                                return ((Integer) obj).intValue();
                            }
                        }));
                    }
                    treeMap.put(entry.getKey(), entry.getValue());
                }
            }
            return treeMap;
        } finally {
            this.mySegmentsAccessLock.unlock();
        }
    }

    void unmapBuffersForOwner(int i, StorageLockContext storageLockContext) {
        Map<Integer, DirectBufferWrapper> buffersOrderedForOwner = getBuffersOrderedForOwner(i, storageLockContext);
        if (buffersOrderedForOwner != null) {
            this.mySegmentsAccessLock.lock();
            try {
                Iterator<Integer> it = buffersOrderedForOwner.keySet().iterator();
                while (it.hasNext()) {
                    this.mySegments.remove(it.next());
                }
                this.mySegmentsAccessLock.unlock();
                this.mySegmentsAllocationLock.lock();
                try {
                    disposeRemovedSegments();
                } finally {
                    this.mySegmentsAllocationLock.unlock();
                }
            } catch (Throwable th) {
                this.mySegmentsAccessLock.unlock();
                throw th;
            }
        }
    }

    void flushBuffersForOwner(int i, StorageLockContext storageLockContext) throws IOException {
        storageLockContext.checkThreadAccess(false);
        Map<Integer, DirectBufferWrapper> buffersOrderedForOwner = getBuffersOrderedForOwner(i, storageLockContext);
        if (buffersOrderedForOwner != null) {
            SmartList smartList = new SmartList();
            this.mySegmentsAllocationLock.lock();
            try {
                try {
                    for (DirectBufferWrapper directBufferWrapper : buffersOrderedForOwner.values()) {
                        if (directBufferWrapper.isDirty()) {
                            directBufferWrapper.force();
                        }
                    }
                } catch (IOException e) {
                    smartList.add(e);
                }
                this.mySegmentsAllocationLock.unlock();
                if (!smartList.isEmpty()) {
                    throw new IOException(new CompoundRuntimeException(smartList));
                }
            } catch (Throwable th) {
                this.mySegmentsAllocationLock.unlock();
                throw th;
            }
        }
    }

    void invalidateBuffer(int i) {
        this.mySegmentsAccessLock.lock();
        try {
            this.mySegments.remove(Integer.valueOf(i));
            this.mySegmentsAccessLock.unlock();
            this.mySegmentsAllocationLock.lock();
            try {
                disposeRemovedSegments();
            } finally {
                this.mySegmentsAllocationLock.unlock();
            }
        } catch (Throwable th) {
            this.mySegmentsAccessLock.unlock();
            throw th;
        }
    }

    void removeStorage(int i) {
        this.myIndex2Storage.remove(i);
    }
}
