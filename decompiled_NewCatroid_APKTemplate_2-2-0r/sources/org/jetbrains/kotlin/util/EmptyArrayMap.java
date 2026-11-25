package org.jetbrains.kotlin.util;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.eclipse.jgit.lib.ConfigConstants;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: compiled from: ArrayMap.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010(\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\bÀ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u000e\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\u0016J\u0013\u0010\t\u001a\u0004\u0018\u00010\u00022\u0006\u0010\n\u001a\u00020\u0005H\u0096\u0002J\u000f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00020\fH\u0096\u0002J\u0019\u0010\r\u001a\u00020\u000e2\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0002H\u0096\u0002R\u0014\u0010\u0004\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/util/EmptyArrayMap;", "Lorg/jetbrains/kotlin/util/ArrayMap;", "", "()V", "size", "", "getSize", "()I", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "get", "index", "iterator", "", FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, "", "value", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EmptyArrayMap extends ArrayMap {
    public static final EmptyArrayMap INSTANCE = new EmptyArrayMap();

    @Override // org.jetbrains.kotlin.util.ArrayMap
    public Void get(int index) {
        return null;
    }

    @Override // org.jetbrains.kotlin.util.ArrayMap
    public int getSize() {
        return 0;
    }

    private EmptyArrayMap() {
        super(null);
    }

    @Override // org.jetbrains.kotlin.util.ArrayMap
    public void set(int index, Void value) {
        Intrinsics.checkNotNullParameter(value, "value");
        throw new IllegalStateException();
    }

    @Override // org.jetbrains.kotlin.util.ArrayMap
    public ArrayMap copy() {
        return this;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.util.EmptyArrayMap$iterator$1, reason: invalid class name */
    /* JADX INFO: compiled from: ArrayMap.kt */
    @Metadata(d1 = {"\u0000\u0017\n\u0000\n\u0002\u0010(\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\t\u0010\u0003\u001a\u00020\u0004H\u0096\u0002J\t\u0010\u0005\u001a\u00020\u0002H\u0096\u0002¨\u0006\u0006"}, d2 = {"org/jetbrains/kotlin/util/EmptyArrayMap$iterator$1", "", "", "hasNext", "", ES6Iterator.NEXT_METHOD, "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class AnonymousClass1 implements Iterator, KMappedMarker {
        @Override // java.util.Iterator
        public boolean hasNext() {
            return false;
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        AnonymousClass1() {
        }

        @Override // java.util.Iterator
        public Void next() {
            throw new NoSuchElementException();
        }
    }

    @Override // org.jetbrains.kotlin.util.ArrayMap, java.lang.Iterable
    public Iterator iterator() {
        return new AnonymousClass1();
    }
}
