package org.jetbrains.kotlin.util;

import androidx.exifinterface.media.ExifInterface;
import java.lang.ref.WeakReference;
import kotlin.Metadata;

/* JADX INFO: compiled from: WeakPair.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u00020\u0003B\u0015\u0012\u0006\u0010\u0004\u001a\u00028\u0000\u0012\u0006\u0010\u0005\u001a\u00028\u0001¢\u0006\u0002\u0010\u0006R\u0013\u0010\u0004\u001a\u0004\u0018\u00018\u00008F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0013\u0010\u0005\u001a\u0004\u0018\u00018\u00018F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\bR\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00028\u00010\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/util/WeakPair;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "", "first", "second", "(Ljava/lang/Object;Ljava/lang/Object;)V", "getFirst", "()Ljava/lang/Object;", "firstReference", "Ljava/lang/ref/WeakReference;", "getSecond", "secondReference", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class WeakPair<K, V> {
    private final WeakReference<K> firstReference;
    private final WeakReference<V> secondReference;

    public WeakPair(K k, V v) {
        this.firstReference = new WeakReference<>(k);
        this.secondReference = new WeakReference<>(v);
    }

    public final K getFirst() {
        return this.firstReference.get();
    }

    public final V getSecond() {
        return this.secondReference.get();
    }
}
