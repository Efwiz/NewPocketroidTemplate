package org.jetbrains.kotlin.fir.util;

import androidx.exifinterface.media.ExifInterface;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: compiled from: ChainedIterator.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010(\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0019\u0012\u0012\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00020\u0004¢\u0006\u0002\u0010\u0005J\t\u0010\b\u001a\u00020\tH\u0096\u0002J\u000e\u0010\n\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\u000bJ\b\u0010\f\u001a\u00020\rH\u0002R\u0016\u0010\u0006\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0002X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00020\u0002X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/util/ChainedIterator;", ExifInterface.GPS_DIRECTION_TRUE, "", "delegates", "", "(Ljava/util/Collection;)V", "currentIterator", "metaIterator", "hasNext", "", ES6Iterator.NEXT_METHOD, "()Ljava/lang/Object;", "promote", "", "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ChainedIterator<T> implements Iterator<T>, KMappedMarker {
    private Iterator<? extends T> currentIterator;
    private Iterator<? extends Iterator<? extends T>> metaIterator;

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public ChainedIterator(Collection<? extends Iterator<? extends T>> delegates) {
        Intrinsics.checkNotNullParameter(delegates, "delegates");
        this.metaIterator = delegates.iterator();
    }

    private final void promote() {
        Iterator<? extends T> it = this.currentIterator;
        if (it == null || !it.hasNext()) {
            while (this.metaIterator.hasNext()) {
                Iterator<? extends T> next = this.metaIterator.next();
                this.currentIterator = next;
                Intrinsics.checkNotNull(next);
                if (next.hasNext()) {
                    return;
                }
            }
        }
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        promote();
        Iterator<? extends T> it = this.currentIterator;
        return it != null && it.hasNext();
    }

    @Override // java.util.Iterator
    public T next() {
        T next;
        promote();
        Iterator<? extends T> it = this.currentIterator;
        if (it == null || (next = it.next()) == null) {
            throw new NoSuchElementException();
        }
        return next;
    }
}
