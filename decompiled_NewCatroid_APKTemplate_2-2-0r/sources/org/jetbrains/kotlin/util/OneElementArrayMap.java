package org.jetbrains.kotlin.util;

import androidx.exifinterface.media.ExifInterface;
import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.eclipse.jgit.lib.ConfigConstants;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: compiled from: ArrayMap.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010(\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0000\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u0015\u0012\u0006\u0010\u0004\u001a\u00028\u0000\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u000e\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u0016J\u0018\u0010\u0010\u001a\u0004\u0018\u00018\u00002\u0006\u0010\u0005\u001a\u00020\u0006H\u0096\u0002¢\u0006\u0002\u0010\u0011J\u000f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00028\u00000\u0013H\u0096\u0002J\u001e\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0004\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\u0016R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\tR\u0013\u0010\u0004\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\u000e\u001a\u0004\b\f\u0010\r¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/util/OneElementArrayMap;", ExifInterface.GPS_DIRECTION_TRUE, "", "Lorg/jetbrains/kotlin/util/ArrayMap;", "value", "index", "", "(Ljava/lang/Object;I)V", "getIndex", "()I", "size", "getSize", "getValue", "()Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, ConfigConstants.CONFIG_RENAMELIMIT_COPY, "get", "(I)Ljava/lang/Object;", "iterator", "", FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, "", "(ILjava/lang/Object;)V", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class OneElementArrayMap<T> extends ArrayMap<T> {
    private final int index;
    private final T value;

    @Override // org.jetbrains.kotlin.util.ArrayMap
    public int getSize() {
        return 1;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public OneElementArrayMap(T value, int i) {
        super(null);
        Intrinsics.checkNotNullParameter(value, "value");
        this.value = value;
        this.index = i;
    }

    public final int getIndex() {
        return this.index;
    }

    public final T getValue() {
        return this.value;
    }

    @Override // org.jetbrains.kotlin.util.ArrayMap
    public void set(int index, T value) {
        Intrinsics.checkNotNullParameter(value, "value");
        throw new IllegalStateException();
    }

    @Override // org.jetbrains.kotlin.util.ArrayMap
    public T get(int index) {
        if (index == this.index) {
            return this.value;
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.util.ArrayMap
    public ArrayMap<T> copy() {
        return new OneElementArrayMap(this.value, this.index);
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.util.OneElementArrayMap$iterator$1, reason: invalid class name */
    /* JADX INFO: compiled from: ArrayMap.kt */
    @Metadata(d1 = {"\u0000\u0013\n\u0000\n\u0002\u0010(\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\t\u0010\u0004\u001a\u00020\u0003H\u0096\u0002J\u000e\u0010\u0005\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\u0006R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0007"}, d2 = {"org/jetbrains/kotlin/util/OneElementArrayMap$iterator$1", "", "notVisited", "", "hasNext", ES6Iterator.NEXT_METHOD, "()Ljava/lang/Object;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class AnonymousClass1 implements Iterator<T>, KMappedMarker {
        private boolean notVisited = true;
        final /* synthetic */ OneElementArrayMap<T> this$0;

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        AnonymousClass1(OneElementArrayMap<T> oneElementArrayMap) {
            this.this$0 = oneElementArrayMap;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.notVisited;
        }

        @Override // java.util.Iterator
        public T next() {
            if (this.notVisited) {
                this.notVisited = false;
                return this.this$0.getValue();
            }
            throw new NoSuchElementException();
        }
    }

    @Override // org.jetbrains.kotlin.util.ArrayMap, java.lang.Iterable
    public Iterator<T> iterator() {
        return new AnonymousClass1(this);
    }
}
