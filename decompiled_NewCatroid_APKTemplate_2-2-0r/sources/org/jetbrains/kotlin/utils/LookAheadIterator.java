package org.jetbrains.kotlin.utils;

import androidx.exifinterface.media.ExifInterface;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: compiled from: parametersMap.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010(\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\bH\u0096\u0002J\u000e\u0010\t\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\nJ\u000b\u0010\u000b\u001a\u00028\u0000¢\u0006\u0002\u0010\nR\u0012\u0010\u0005\u001a\u0004\u0018\u00018\u0000X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u0006R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/utils/LookAheadIterator;", ExifInterface.GPS_DIRECTION_TRUE, "", "iterator", "(Ljava/util/Iterator;)V", "currentLookAhead", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "hasNext", "", ES6Iterator.NEXT_METHOD, "()Ljava/lang/Object;", "nextWithoutConsuming", "cli-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class LookAheadIterator<T> implements Iterator<T>, KMappedMarker {
    private T currentLookAhead;
    private final Iterator<T> iterator;

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public LookAheadIterator(Iterator<? extends T> iterator) {
        Intrinsics.checkNotNullParameter(iterator, "iterator");
        this.iterator = iterator;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.currentLookAhead != null || this.iterator.hasNext();
    }

    @Override // java.util.Iterator
    public T next() {
        T t = this.currentLookAhead;
        if (t != null) {
            this.currentLookAhead = null;
            return t;
        }
        return this.iterator.next();
    }

    public final T nextWithoutConsuming() {
        T t = this.currentLookAhead;
        if (t != null) {
            return t;
        }
        T next = this.iterator.next();
        this.currentLookAhead = next;
        return next;
    }
}
