package org.eclipse.jgit.internal.storage.dfs;

import androidx.lifecycle.LifecycleKt$$ExternalSyntheticBackportWithForwarding0;
import com.google.common.util.concurrent.Striped$SmallLazyStriped$$ExternalSyntheticBackportWithForwarding0;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.stream.LongStream;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.pack.PackExt;

/* JADX INFO: loaded from: classes13.dex */
public final class DfsBlockCache {
    private static volatile DfsBlockCache cache;
    private final int blockSize;
    private final int blockSizeShift;
    private Ref clockHand;
    private final ReentrantLock clockLock;
    private final AtomicReference<AtomicLong[]> liveBytes;
    private final ReentrantLock[] loadLocks;
    private final long maxBytes;
    private final long maxStreamThroughCache;
    private final Consumer<Long> refLockWaitTime;
    private final ReentrantLock[] refLocks;
    private final AtomicReference<AtomicLong[]> statEvict;
    private final AtomicReference<AtomicLong[]> statHit;
    private final AtomicReference<AtomicLong[]> statMiss;
    private final AtomicReferenceArray<HashEntry> table;
    private final int tableSize;

    @FunctionalInterface
    interface ReadableChannelSupplier {
        ReadableChannel get() throws IOException;
    }

    @FunctionalInterface
    interface RefLoader<T> {
        Ref<T> load() throws IOException;
    }

    static {
        reconfigure(new DfsBlockCacheConfig());
    }

    public static void reconfigure(DfsBlockCacheConfig dfsBlockCacheConfig) {
        cache = new DfsBlockCache(dfsBlockCacheConfig);
    }

    public static DfsBlockCache getInstance() {
        return cache;
    }

    private DfsBlockCache(DfsBlockCacheConfig dfsBlockCacheConfig) {
        int iTableSize = tableSize(dfsBlockCacheConfig);
        this.tableSize = iTableSize;
        if (iTableSize < 1) {
            throw new IllegalArgumentException(JGitText.get().tSizeMustBeGreaterOrEqual1);
        }
        this.table = new AtomicReferenceArray<>(iTableSize);
        this.loadLocks = new ReentrantLock[dfsBlockCacheConfig.getConcurrencyLevel()];
        int i = 0;
        int i2 = 0;
        while (true) {
            ReentrantLock[] reentrantLockArr = this.loadLocks;
            if (i2 >= reentrantLockArr.length) {
                break;
            }
            reentrantLockArr[i2] = new ReentrantLock(true);
            i2++;
        }
        this.refLocks = new ReentrantLock[dfsBlockCacheConfig.getConcurrencyLevel()];
        while (true) {
            ReentrantLock[] reentrantLockArr2 = this.refLocks;
            if (i < reentrantLockArr2.length) {
                reentrantLockArr2[i] = new ReentrantLock(true);
                i++;
            } else {
                long blockLimit = dfsBlockCacheConfig.getBlockLimit();
                this.maxBytes = blockLimit;
                this.maxStreamThroughCache = (long) (blockLimit * dfsBlockCacheConfig.getStreamRatio());
                int blockSize = dfsBlockCacheConfig.getBlockSize();
                this.blockSize = blockSize;
                this.blockSizeShift = Integer.numberOfTrailingZeros(blockSize);
                this.clockLock = new ReentrantLock(true);
                Ref ref = new Ref(DfsStreamKey.of(new DfsRepositoryDescription(""), "", null), -1L, 0L, null);
                this.clockHand = ref;
                ref.next = ref;
                this.statHit = new AtomicReference<>(newCounters());
                this.statMiss = new AtomicReference<>(newCounters());
                this.statEvict = new AtomicReference<>(newCounters());
                this.liveBytes = new AtomicReference<>(newCounters());
                this.refLockWaitTime = dfsBlockCacheConfig.getRefLockWaitTimeConsumer();
                return;
            }
        }
    }

    boolean shouldCopyThroughCache(long j) {
        return j <= this.maxStreamThroughCache;
    }

    public long[] getCurrentSize() {
        return getStatVals(this.liveBytes);
    }

    public long getFillPercentage() {
        return (LongStream.of(getCurrentSize()).sum() * 100) / this.maxBytes;
    }

    public long[] getHitCount() {
        return getStatVals(this.statHit);
    }

    public long[] getMissCount() {
        return getStatVals(this.statMiss);
    }

    public long[] getTotalRequestCount() {
        AtomicLong[] atomicLongArr = this.statHit.get();
        AtomicLong[] atomicLongArr2 = this.statMiss.get();
        long[] jArr = new long[Math.max(atomicLongArr.length, atomicLongArr2.length)];
        for (int i = 0; i < atomicLongArr.length; i++) {
            jArr[i] = jArr[i] + atomicLongArr[i].get();
        }
        for (int i2 = 0; i2 < atomicLongArr2.length; i2++) {
            jArr[i2] = jArr[i2] + atomicLongArr2[i2].get();
        }
        return jArr;
    }

    public long[] getHitRatio() {
        AtomicLong[] atomicLongArr = this.statHit.get();
        AtomicLong[] atomicLongArr2 = this.statMiss.get();
        int iMax = Math.max(atomicLongArr.length, atomicLongArr2.length);
        long[] jArr = new long[iMax];
        for (int i = 0; i < iMax; i++) {
            if (i >= atomicLongArr.length) {
                jArr[i] = 0;
            } else if (i >= atomicLongArr2.length) {
                jArr[i] = 100;
            } else {
                long j = atomicLongArr[i].get();
                long j2 = atomicLongArr2[i].get() + j;
                jArr[i] = j2 != 0 ? (j * 100) / j2 : 0L;
            }
        }
        return jArr;
    }

    public long[] getEvictions() {
        return getStatVals(this.statEvict);
    }

    public boolean hasBlock0(DfsStreamKey dfsStreamKey) {
        DfsBlock dfsBlock = (DfsBlock) scan(this.table.get(slot(dfsStreamKey, 0L)), dfsStreamKey, 0L);
        return dfsBlock != null && dfsBlock.contains(dfsStreamKey, 0L);
    }

    private int hash(int i, long j) {
        return i + ((int) (j >>> this.blockSizeShift));
    }

    int getBlockSize() {
        return this.blockSize;
    }

    private static int tableSize(DfsBlockCacheConfig dfsBlockCacheConfig) {
        int blockSize = dfsBlockCacheConfig.getBlockSize();
        long blockLimit = dfsBlockCacheConfig.getBlockLimit();
        if (blockSize <= 0) {
            throw new IllegalArgumentException(JGitText.get().invalidWindowSize);
        }
        long j = blockSize;
        if (blockLimit < j) {
            throw new IllegalArgumentException(JGitText.get().windowSizeMustBeLesserThanLimit);
        }
        return (int) Math.min(((blockLimit / j) * 5) / 2, 2147483647L);
    }

    DfsBlock getOrLoad(BlockBasedFile blockBasedFile, long j, DfsReader dfsReader, ReadableChannelSupplier readableChannelSupplier) throws IOException {
        DfsBlock dfsBlock;
        long jAlignToBlock = blockBasedFile.alignToBlock(j);
        DfsStreamKey dfsStreamKey = blockBasedFile.key;
        int iSlot = slot(dfsStreamKey, jAlignToBlock);
        HashEntry hashEntry = this.table.get(iSlot);
        DfsBlock dfsBlock2 = (DfsBlock) scan(hashEntry, dfsStreamKey, jAlignToBlock);
        if (dfsBlock2 != null && dfsBlock2.contains(dfsStreamKey, j)) {
            dfsReader.stats.blockCacheHit++;
            getStat(this.statHit, dfsStreamKey).incrementAndGet();
            return dfsBlock2;
        }
        reserveSpace(this.blockSize, dfsStreamKey);
        ReentrantLock reentrantLockLockFor = lockFor(dfsStreamKey, jAlignToBlock);
        reentrantLockLockFor.lock();
        try {
            HashEntry hashEntry2 = this.table.get(iSlot);
            if (hashEntry2 != hashEntry && (dfsBlock = (DfsBlock) scan(hashEntry2, dfsStreamKey, jAlignToBlock)) != null) {
                dfsReader.stats.blockCacheHit++;
                getStat(this.statHit, dfsStreamKey).incrementAndGet();
                creditSpace(this.blockSize, dfsStreamKey);
                reentrantLockLockFor.unlock();
                return dfsBlock;
            }
            getStat(this.statMiss, dfsStreamKey).incrementAndGet();
            try {
                DfsBlock oneBlock = blockBasedFile.readOneBlock(jAlignToBlock, dfsReader, readableChannelSupplier.get());
                if (jAlignToBlock != oneBlock.start) {
                    jAlignToBlock = oneBlock.start;
                    iSlot = slot(dfsStreamKey, jAlignToBlock);
                    hashEntry2 = this.table.get(iSlot);
                }
                long j2 = jAlignToBlock;
                int i = iSlot;
                HashEntry hashEntry3 = hashEntry2;
                Ref ref = new Ref(dfsStreamKey, j2, oneBlock.size(), oneBlock);
                ref.hot = true;
                while (!Striped$SmallLazyStriped$$ExternalSyntheticBackportWithForwarding0.m(this.table, i, hashEntry3, new HashEntry(clean(hashEntry3), ref))) {
                    hashEntry3 = this.table.get(i);
                }
                addToClock(ref, this.blockSize - oneBlock.size());
                reentrantLockLockFor.unlock();
                return oneBlock.contains(blockBasedFile.key, j) ? oneBlock : getOrLoad(blockBasedFile, j, dfsReader, readableChannelSupplier);
            } catch (Throwable th) {
                creditSpace(this.blockSize, dfsStreamKey);
                throw th;
            }
        } catch (Throwable th2) {
            reentrantLockLockFor.unlock();
            throw th2;
        }
    }

    private void reserveSpace(long j, DfsStreamKey dfsStreamKey) {
        this.clockLock.lock();
        try {
            long jSum = LongStream.of(getCurrentSize()).sum() + j;
            if (this.maxBytes < jSum) {
                Ref ref = this.clockHand;
                Ref ref2 = ref.next;
                do {
                    if (ref2.hot) {
                        ref2.hot = false;
                        Ref ref3 = ref2;
                        ref2 = ref2.next;
                        ref = ref3;
                    } else {
                        if (ref == ref2) {
                            break;
                        }
                        Ref ref4 = ref2.next;
                        ref.next = ref4;
                        ref2.next = null;
                        ref2.value = null;
                        jSum -= ref2.size;
                        getStat(this.liveBytes, ref2.key).addAndGet(-ref2.size);
                        getStat(this.statEvict, ref2.key).incrementAndGet();
                        ref2 = ref4;
                    }
                } while (this.maxBytes < jSum);
                this.clockHand = ref;
            }
            getStat(this.liveBytes, dfsStreamKey).addAndGet(j);
        } finally {
            this.clockLock.unlock();
        }
    }

    private void creditSpace(long j, DfsStreamKey dfsStreamKey) {
        this.clockLock.lock();
        try {
            getStat(this.liveBytes, dfsStreamKey).addAndGet(-j);
        } finally {
            this.clockLock.unlock();
        }
    }

    private void addToClock(Ref ref, long j) {
        this.clockLock.lock();
        if (j != 0) {
            try {
                getStat(this.liveBytes, ref.key).addAndGet(-j);
            } finally {
                this.clockLock.unlock();
            }
        }
        Ref ref2 = this.clockHand;
        ref.next = ref2.next;
        ref2.next = ref;
        this.clockHand = ref;
    }

    void put(DfsBlock dfsBlock) {
        put(dfsBlock.stream, dfsBlock.start, dfsBlock.size(), dfsBlock);
    }

    <T> Ref<T> getOrLoadRef(DfsStreamKey dfsStreamKey, long j, RefLoader<T> refLoader) throws IOException {
        Ref<T> refScanRef;
        int iSlot = slot(dfsStreamKey, j);
        HashEntry hashEntry = this.table.get(iSlot);
        Ref<T> refScanRef2 = scanRef(hashEntry, dfsStreamKey, j);
        if (refScanRef2 != null) {
            getStat(this.statHit, dfsStreamKey).incrementAndGet();
            return refScanRef2;
        }
        ReentrantLock reentrantLockLockForRef = lockForRef(dfsStreamKey);
        long jCurrentTimeMillis = System.currentTimeMillis();
        reentrantLockLockForRef.lock();
        try {
            HashEntry hashEntry2 = this.table.get(iSlot);
            if (hashEntry2 != hashEntry && (refScanRef = scanRef(hashEntry2, dfsStreamKey, j)) != null) {
                getStat(this.statHit, dfsStreamKey).incrementAndGet();
                return refScanRef;
            }
            Consumer<Long> consumer = this.refLockWaitTime;
            if (consumer != null) {
                consumer.accept(Long.valueOf(System.currentTimeMillis() - jCurrentTimeMillis));
            }
            getStat(this.statMiss, dfsStreamKey).incrementAndGet();
            Ref<T> refLoad = refLoader.load();
            refLoad.hot = true;
            reserveSpace(refLoad.size, dfsStreamKey);
            while (!Striped$SmallLazyStriped$$ExternalSyntheticBackportWithForwarding0.m(this.table, iSlot, hashEntry2, new HashEntry(clean(hashEntry2), refLoad))) {
                hashEntry2 = this.table.get(iSlot);
            }
            addToClock(refLoad, 0L);
            return refLoad;
        } finally {
            reentrantLockLockForRef.unlock();
        }
    }

    <T> Ref<T> putRef(DfsStreamKey dfsStreamKey, long j, T t) {
        return put(dfsStreamKey, 0L, j, t);
    }

    <T> Ref<T> put(DfsStreamKey dfsStreamKey, long j, long j2, T t) {
        Ref<T> refScanRef;
        int iSlot = slot(dfsStreamKey, j);
        HashEntry hashEntry = this.table.get(iSlot);
        Ref<T> refScanRef2 = scanRef(hashEntry, dfsStreamKey, j);
        if (refScanRef2 != null) {
            return refScanRef2;
        }
        reserveSpace(j2, dfsStreamKey);
        ReentrantLock reentrantLockLockFor = lockFor(dfsStreamKey, j);
        reentrantLockLockFor.lock();
        try {
            HashEntry hashEntry2 = this.table.get(iSlot);
            if (hashEntry2 != hashEntry && (refScanRef = scanRef(hashEntry2, dfsStreamKey, j)) != null) {
                creditSpace(j2, dfsStreamKey);
                return refScanRef;
            }
            Ref<T> ref = new Ref<>(dfsStreamKey, j, j2, t);
            ref.hot = true;
            while (!Striped$SmallLazyStriped$$ExternalSyntheticBackportWithForwarding0.m(this.table, iSlot, hashEntry2, new HashEntry(clean(hashEntry2), ref))) {
                hashEntry2 = this.table.get(iSlot);
            }
            addToClock(ref, 0L);
            return ref;
        } finally {
            reentrantLockLockFor.unlock();
        }
    }

    boolean contains(DfsStreamKey dfsStreamKey, long j) {
        return scan(this.table.get(slot(dfsStreamKey, j)), dfsStreamKey, j) != null;
    }

    <T> T get(DfsStreamKey dfsStreamKey, long j) {
        T t = (T) scan(this.table.get(slot(dfsStreamKey, j)), dfsStreamKey, j);
        if (t == null) {
            getStat(this.statMiss, dfsStreamKey).incrementAndGet();
        } else {
            getStat(this.statHit, dfsStreamKey).incrementAndGet();
        }
        return t;
    }

    private <T> T scan(HashEntry hashEntry, DfsStreamKey dfsStreamKey, long j) {
        Ref<T> refScanRef = scanRef(hashEntry, dfsStreamKey, j);
        if (refScanRef != null) {
            return refScanRef.get();
        }
        return null;
    }

    private <T> Ref<T> scanRef(HashEntry hashEntry, DfsStreamKey dfsStreamKey, long j) {
        while (hashEntry != null) {
            Ref<T> ref = hashEntry.ref;
            if (ref.position != j || !ref.key.equals(dfsStreamKey)) {
                hashEntry = hashEntry.next;
            } else {
                if (ref.get() != null) {
                    return ref;
                }
                return null;
            }
        }
        return null;
    }

    private int slot(DfsStreamKey dfsStreamKey, long j) {
        return (hash(dfsStreamKey.hash, j) >>> 1) % this.tableSize;
    }

    private ReentrantLock lockFor(DfsStreamKey dfsStreamKey, long j) {
        return this.loadLocks[(hash(dfsStreamKey.hash, j) >>> 1) % this.loadLocks.length];
    }

    private ReentrantLock lockForRef(DfsStreamKey dfsStreamKey) {
        return this.refLocks[(dfsStreamKey.hash >>> 1) % this.refLocks.length];
    }

    private static AtomicLong[] newCounters() {
        int length = PackExt.valuesCustom().length;
        AtomicLong[] atomicLongArr = new AtomicLong[length];
        for (int i = 0; i < length; i++) {
            atomicLongArr[i] = new AtomicLong();
        }
        return atomicLongArr;
    }

    private static AtomicLong getStat(AtomicReference<AtomicLong[]> atomicReference, DfsStreamKey dfsStreamKey) {
        AtomicLong[] atomicLongArr;
        AtomicLong[] atomicLongArr2;
        int i = dfsStreamKey.packExtPos;
        do {
            atomicLongArr = atomicReference.get();
            if (i < atomicLongArr.length) {
                return atomicLongArr[i];
            }
            int iMax = Math.max(i + 1, PackExt.valuesCustom().length);
            atomicLongArr2 = new AtomicLong[iMax];
            System.arraycopy(atomicLongArr, 0, atomicLongArr2, 0, atomicLongArr.length);
            for (int length = atomicLongArr.length; length < iMax; length++) {
                atomicLongArr2[length] = new AtomicLong();
            }
        } while (!LifecycleKt$$ExternalSyntheticBackportWithForwarding0.m(atomicReference, atomicLongArr, atomicLongArr2));
        return atomicLongArr2[i];
    }

    private static long[] getStatVals(AtomicReference<AtomicLong[]> atomicReference) {
        AtomicLong[] atomicLongArr = atomicReference.get();
        long[] jArr = new long[atomicLongArr.length];
        for (int i = 0; i < atomicLongArr.length; i++) {
            jArr[i] = atomicLongArr[i].get();
        }
        return jArr;
    }

    private static HashEntry clean(HashEntry hashEntry) {
        while (hashEntry != null && hashEntry.ref.next == null) {
            hashEntry = hashEntry.next;
        }
        if (hashEntry == null) {
            return null;
        }
        HashEntry hashEntryClean = clean(hashEntry.next);
        return hashEntryClean == hashEntry.next ? hashEntry : new HashEntry(hashEntryClean, hashEntry.ref);
    }

    private static final class HashEntry {
        final HashEntry next;
        final Ref ref;

        HashEntry(HashEntry hashEntry, Ref ref) {
            this.next = hashEntry;
            this.ref = ref;
        }
    }

    static final class Ref<T> {
        volatile boolean hot;
        final DfsStreamKey key;
        Ref next;
        final long position;
        final long size;
        volatile T value;

        Ref(DfsStreamKey dfsStreamKey, long j, long j2, T t) {
            this.key = dfsStreamKey;
            this.position = j;
            this.size = j2;
            this.value = t;
        }

        T get() {
            T t = this.value;
            if (t != null) {
                this.hot = true;
            }
            return t;
        }

        boolean has() {
            return this.value != null;
        }
    }
}
