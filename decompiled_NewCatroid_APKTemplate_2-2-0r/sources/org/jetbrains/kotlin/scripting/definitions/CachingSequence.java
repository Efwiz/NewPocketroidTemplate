package org.jetbrains.kotlin.scripting.definitions;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.exifinterface.media.ExifInterface;
import com.koushikdutta.async.http.cache.ResponseCacheMiddleware;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.sequences.Sequence;
import org.jetbrains.kotlin.utils.PathUtil;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: compiled from: LazyScriptDefinitionProvider.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010(\n\u0002\b\u0003\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002:\u0001\rB\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002¢\u0006\u0002\u0010\u0004J\u000f\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00000\u000bH\u0096\u0002R\u001e\u0010\u0005\u001a\u0012\u0012\u0004\u0012\u00028\u00000\u0006j\b\u0012\u0004\u0012\u00028\u0000`\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/scripting/definitions/CachingSequence;", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlin/sequences/Sequence;", TypedValues.TransitionType.S_FROM, "(Lkotlin/sequences/Sequence;)V", ResponseCacheMiddleware.CACHE, "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "lock", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "sequenceIterator", "", "iterator", "CachingIterator", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
final class CachingSequence<T> implements Sequence<T> {
    private final ArrayList<T> cache;
    private final ReentrantReadWriteLock lock;
    private final Iterator<T> sequenceIterator;

    public CachingSequence(Sequence<? extends T> from) {
        Intrinsics.checkNotNullParameter(from, "from");
        this.lock = new ReentrantReadWriteLock();
        this.sequenceIterator = from.iterator();
        this.cache = new ArrayList<>();
    }

    /* JADX INFO: compiled from: LazyScriptDefinitionProvider.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010(\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0082\u0004\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001B\u0005¢\u0006\u0002\u0010\u0002J\t\u0010\u0005\u001a\u00020\u0006H\u0096\u0002J\u000e\u0010\u0007\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/scripting/definitions/CachingSequence$CachingIterator;", "", "(Lorg/jetbrains/kotlin/scripting/definitions/CachingSequence;)V", "cacheCursor", "", "hasNext", "", ES6Iterator.NEXT_METHOD, "()Ljava/lang/Object;", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class CachingIterator implements Iterator<T>, KMappedMarker {
        private int cacheCursor;

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        public CachingIterator() {
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            ReentrantReadWriteLock reentrantReadWriteLock = ((CachingSequence) CachingSequence.this).lock;
            CachingSequence<T> cachingSequence = CachingSequence.this;
            ReentrantReadWriteLock.ReadLock lock = reentrantReadWriteLock.readLock();
            lock.lock();
            try {
                boolean z = this.cacheCursor < ((CachingSequence) cachingSequence).cache.size();
                lock.unlock();
                if (z) {
                    return true;
                }
                ReentrantReadWriteLock reentrantReadWriteLock2 = ((CachingSequence) CachingSequence.this).lock;
                CachingSequence<T> cachingSequence2 = CachingSequence.this;
                ReentrantReadWriteLock.ReadLock lock2 = reentrantReadWriteLock2.readLock();
                int readHoldCount = reentrantReadWriteLock2.getWriteHoldCount() == 0 ? reentrantReadWriteLock2.getReadHoldCount() : 0;
                for (int i = 0; i < readHoldCount; i++) {
                    lock2.unlock();
                }
                ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock2.writeLock();
                writeLock.lock();
                try {
                    boolean z2 = this.cacheCursor < ((CachingSequence) cachingSequence2).cache.size() || ((CachingSequence) cachingSequence2).sequenceIterator.hasNext();
                    for (int i2 = 0; i2 < readHoldCount; i2++) {
                        lock2.lock();
                    }
                    writeLock.unlock();
                    return z2;
                } catch (Throwable th) {
                    for (int i3 = 0; i3 < readHoldCount; i3++) {
                        lock2.lock();
                    }
                    writeLock.unlock();
                    throw th;
                }
            } catch (Throwable th2) {
                lock.unlock();
                throw th2;
            }
        }

        @Override // java.util.Iterator
        public T next() {
            T t;
            ReentrantReadWriteLock reentrantReadWriteLock = ((CachingSequence) CachingSequence.this).lock;
            CachingSequence<T> cachingSequence = CachingSequence.this;
            ReentrantReadWriteLock.ReadLock lock = reentrantReadWriteLock.readLock();
            lock.lock();
            try {
                if (this.cacheCursor < ((CachingSequence) cachingSequence).cache.size()) {
                    ArrayList arrayList = ((CachingSequence) cachingSequence).cache;
                    int i = this.cacheCursor;
                    this.cacheCursor = i + 1;
                    T t2 = (T) arrayList.get(i);
                    lock.unlock();
                    return t2;
                }
                Unit unit = Unit.INSTANCE;
                lock.unlock();
                ReentrantReadWriteLock reentrantReadWriteLock2 = ((CachingSequence) CachingSequence.this).lock;
                CachingSequence<T> cachingSequence2 = CachingSequence.this;
                ReentrantReadWriteLock.ReadLock lock2 = reentrantReadWriteLock2.readLock();
                int i2 = 0;
                int readHoldCount = reentrantReadWriteLock2.getWriteHoldCount() == 0 ? reentrantReadWriteLock2.getReadHoldCount() : 0;
                for (int i3 = 0; i3 < readHoldCount; i3++) {
                    lock2.unlock();
                }
                ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock2.writeLock();
                writeLock.lock();
                try {
                    if (this.cacheCursor < ((CachingSequence) cachingSequence2).cache.size()) {
                        ArrayList arrayList2 = ((CachingSequence) cachingSequence2).cache;
                        int i4 = this.cacheCursor;
                        this.cacheCursor = i4 + 1;
                        t = (T) arrayList2.get(i4);
                    } else {
                        Object next = ((CachingSequence) cachingSequence2).sequenceIterator.next();
                        ((CachingSequence) cachingSequence2).cache.add(next);
                        t = (T) next;
                    }
                    while (i2 < readHoldCount) {
                        lock2.lock();
                        i2++;
                    }
                    return t;
                } finally {
                    while (i2 < readHoldCount) {
                        lock2.lock();
                        i2++;
                    }
                    writeLock.unlock();
                }
            } catch (Throwable th) {
                lock.unlock();
                throw th;
            }
        }
    }

    @Override // kotlin.sequences.Sequence
    public Iterator<T> iterator() {
        return new CachingIterator();
    }
}
