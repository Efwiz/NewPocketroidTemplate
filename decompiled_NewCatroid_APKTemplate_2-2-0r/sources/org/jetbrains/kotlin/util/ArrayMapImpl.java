package org.jetbrains.kotlin.util;

import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.reandroid.arsc.chunk.TypeBlock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.AbstractIterator;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.antlr.stringtemplate.language.ASTExpr;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: ArrayMap.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010(\n\u0002\b\u0007\b\u0000\u0018\u0000 \u001e*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003:\u0002\u001e\u001fB\u0007\b\u0016¢\u0006\u0002\u0010\u0004B\u001f\b\u0002\u0012\u000e\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u000e\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u0016J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\bH\u0002J\u0012\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00150\u0014J\u0018\u0010\u0016\u001a\u0004\u0018\u00018\u00002\u0006\u0010\u0012\u001a\u00020\bH\u0096\u0002¢\u0006\u0002\u0010\u0017J\u000f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00028\u00000\u0019H\u0096\u0002J\u000e\u0010\u001a\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\bJ\u001e\u0010\u001b\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\b2\u0006\u0010\u001c\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\u001dR\u0018\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0006X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\nR\u001e\u0010\f\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\b@RX\u0096\u000e¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/util/ArrayMapImpl;", ExifInterface.GPS_DIRECTION_TRUE, "", "Lorg/jetbrains/kotlin/util/ArrayMap;", "()V", "data", "", "initialSize", "", "([Ljava/lang/Object;I)V", "[Ljava/lang/Object;", "<set-?>", "size", "getSize", "()I", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "ensureCapacity", "", "index", TypeBlock.NAME_entries, "", "Lorg/jetbrains/kotlin/util/ArrayMapImpl$Entry;", "get", "(I)Ljava/lang/Object;", "iterator", "", ProductAction.ACTION_REMOVE, FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, "value", "(ILjava/lang/Object;)V", "Companion", "Entry", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ArrayMapImpl<T> extends ArrayMap<T> {
    private Object[] data;
    private int size;

    private ArrayMapImpl(Object[] objArr, int i) {
        super(null);
        this.data = objArr;
        this.size = i;
    }

    public ArrayMapImpl() {
        this(new Object[20], 0);
    }

    @Override // org.jetbrains.kotlin.util.ArrayMap
    public int getSize() {
        return this.size;
    }

    private final void ensureCapacity(int index) {
        Object[] objArr = this.data;
        if (objArr.length > index) {
            return;
        }
        int length = objArr.length;
        do {
            length *= 2;
        } while (length <= index);
        Object[] objArrCopyOf = Arrays.copyOf(this.data, length);
        Intrinsics.checkNotNullExpressionValue(objArrCopyOf, "copyOf(...)");
        this.data = objArrCopyOf;
    }

    @Override // org.jetbrains.kotlin.util.ArrayMap
    public void set(int index, T value) {
        Intrinsics.checkNotNullParameter(value, "value");
        ensureCapacity(index);
        if (this.data[index] == null) {
            this.size = getSize() + 1;
        }
        this.data[index] = value;
    }

    @Override // org.jetbrains.kotlin.util.ArrayMap
    public T get(int index) {
        return (T) ArraysKt.getOrNull(this.data, index);
    }

    @Override // org.jetbrains.kotlin.util.ArrayMap
    public ArrayMap<T> copy() {
        Object[] objArr = this.data;
        Object[] objArrCopyOf = Arrays.copyOf(objArr, objArr.length);
        Intrinsics.checkNotNullExpressionValue(objArrCopyOf, "copyOf(...)");
        return new ArrayMapImpl(objArrCopyOf, getSize());
    }

    @Override // org.jetbrains.kotlin.util.ArrayMap, java.lang.Iterable
    public Iterator<T> iterator() {
        return new AbstractIterator<T>(this) { // from class: org.jetbrains.kotlin.util.ArrayMapImpl.iterator.1
            private int index = -1;
            final /* synthetic */ ArrayMapImpl<T> this$0;

            {
                this.this$0 = this;
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.collections.AbstractIterator
            protected void computeNext() {
                do {
                    int i = this.index + 1;
                    this.index = i;
                    if (i >= ((ArrayMapImpl) this.this$0).data.length) {
                        break;
                    }
                } while (((ArrayMapImpl) this.this$0).data[this.index] == null);
                if (this.index < ((ArrayMapImpl) this.this$0).data.length) {
                    Object obj = ((ArrayMapImpl) this.this$0).data[this.index];
                    Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type T of org.jetbrains.kotlin.util.ArrayMapImpl");
                    setNext(obj);
                    return;
                }
                done();
            }
        };
    }

    public final void remove(int index) {
        if (this.data[index] != null) {
            this.size = getSize() - 1;
        }
        this.data[index] = null;
    }

    public final List<Entry<T>> entries() {
        Object[] objArr = this.data;
        ArrayList arrayList = new ArrayList();
        int length = objArr.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            Object obj = objArr[i];
            int i3 = i2 + 1;
            Entry entry = obj != null ? new Entry(i2, obj) : null;
            if (entry != null) {
                arrayList.add(entry);
            }
            i++;
            i2 = i3;
        }
        return arrayList;
    }

    /* JADX INFO: compiled from: ArrayMap.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010&\n\u0002\u0010\b\n\u0002\b\r\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u0000*\u0004\b\u0001\u0010\u00012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u0002H\u00010\u0002B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00028\u0001¢\u0006\u0002\u0010\u0006J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\u000e\u0010\r\u001a\u00028\u0001HÆ\u0003¢\u0006\u0002\u0010\nJ(\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00010\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00028\u0001HÆ\u0001¢\u0006\u0002\u0010\u000fJ\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001R\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0016\u0010\u0005\u001a\u00028\u0001X\u0096\u0004¢\u0006\n\n\u0002\u0010\u000b\u001a\u0004\b\t\u0010\n¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/util/ArrayMapImpl$Entry;", ExifInterface.GPS_DIRECTION_TRUE, "", "", ASTExpr.DEFAULT_MAP_KEY_NAME, "value", "(ILjava/lang/Object;)V", "getKey", "()Ljava/lang/Integer;", "getValue", "()Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "(ILjava/lang/Object;)Lorg/jetbrains/kotlin/util/ArrayMapImpl$Entry;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "toString", "", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Entry<T> implements Map.Entry<Integer, T>, KMappedMarker {
        private final int key;
        private final T value;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ Entry copy$default(Entry entry, int i, Object obj, int i2, Object obj2) {
            if ((i2 & 1) != 0) {
                i = entry.key;
            }
            if ((i2 & 2) != 0) {
                obj = entry.value;
            }
            return entry.copy(i, obj);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final int getKey() {
            return this.key;
        }

        public final T component2() {
            return this.value;
        }

        public final Entry<T> copy(int key, T value) {
            return new Entry<>(key, value);
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) other;
            return this.key == entry.key && Intrinsics.areEqual(this.value, entry.value);
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            int iHashCode = Integer.hashCode(this.key) * 31;
            T t = this.value;
            return iHashCode + (t == null ? 0 : t.hashCode());
        }

        @Override // java.util.Map.Entry
        public T setValue(T t) {
            throw new UnsupportedOperationException("Operation is not supported for read-only collection");
        }

        public String toString() {
            return "Entry(key=" + this.key + ", value=" + this.value + ')';
        }

        public Entry(int i, T t) {
            this.key = i;
            this.value = t;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // java.util.Map.Entry
        public Integer getKey() {
            return Integer.valueOf(this.key);
        }

        @Override // java.util.Map.Entry
        public T getValue() {
            return this.value;
        }
    }
}
