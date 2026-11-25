package org.jetbrains.kotlin.utils;

import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.ArrayIteratorKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.jvm.internal.markers.KMutableIterator;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.org.jline.reader.LineReader;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: compiled from: SmartSet.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010)\n\u0002\b\u0004\u0018\u0000 \u0016*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002:\u0003\u0015\u0016\u0017B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0015\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u000fJ\b\u0010\u0010\u001a\u00020\u0011H\u0016J\u0016\u0010\u0012\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\u000fJ\u000f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00028\u00000\u0014H\u0096\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0006\u001a\u00020\u0007X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000b¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/utils/SmartSet;", ExifInterface.GPS_DIRECTION_TRUE, "Ljava/util/AbstractSet;", "()V", "data", "", "size", "", "getSize", "()I", "setSize", "(I)V", ProductAction.ACTION_ADD, "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "(Ljava/lang/Object;)Z", LineReader.CLEAR, "", "contains", "iterator", "", "ArrayIterator", "Companion", "SingletonIterator", "util.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SmartSet<T> extends AbstractSet<T> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private Object data;
    private int size;

    public /* synthetic */ SmartSet(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    @JvmStatic
    public static final <T> SmartSet<T> create() {
        return INSTANCE.create();
    }

    @JvmStatic
    public static final <T> SmartSet<T> create(Collection<? extends T> collection) {
        return INSTANCE.create(collection);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final /* bridge */ int size() {
        return getSize();
    }

    /* JADX INFO: compiled from: SmartSet.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0006\"\u0004\b\u0001\u0010\u0007H\u0007J\"\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0006\"\u0004\b\u0001\u0010\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00070\tH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/utils/SmartSet$Companion;", "", "()V", "ARRAY_THRESHOLD", "", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/utils/SmartSet;", ExifInterface.GPS_DIRECTION_TRUE, FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, "", "util.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final <T> SmartSet<T> create() {
            return new SmartSet<>(null);
        }

        @JvmStatic
        public final <T> SmartSet<T> create(Collection<? extends T> set) {
            Intrinsics.checkNotNullParameter(set, "set");
            SmartSet<T> smartSet = new SmartSet<>(null);
            smartSet.addAll(set);
            return smartSet;
        }
    }

    private SmartSet() {
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int i) {
        this.size = i;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public Iterator<T> iterator() {
        if (size() == 0) {
            return Collections.emptySet().iterator();
        }
        if (size() == 1) {
            return new SingletonIterator(this.data);
        }
        if (size() < 5) {
            Object obj = this.data;
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Array<T of org.jetbrains.kotlin.utils.SmartSet>");
            return new ArrayIterator((Object[]) obj);
        }
        Object obj2 = this.data;
        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.collections.MutableSet<T of org.jetbrains.kotlin.utils.SmartSet>");
        return TypeIntrinsics.asMutableSet(obj2).iterator();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean add(T element) {
        Object obj;
        if (size() == 0) {
            this.data = element;
        } else if (size() == 1) {
            if (Intrinsics.areEqual(this.data, element)) {
                return false;
            }
            this.data = new Object[]{this.data, element};
        } else if (size() < 5) {
            Object obj2 = this.data;
            Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Array<T of org.jetbrains.kotlin.utils.SmartSet>");
            Object[] objArr = (Object[]) obj2;
            if (ArraysKt.contains(objArr, element)) {
                return false;
            }
            if (size() == 4) {
                LinkedHashSet linkedHashSetLinkedSetOf = SetsKt.linkedSetOf(Arrays.copyOf(objArr, objArr.length));
                linkedHashSetLinkedSetOf.add(element);
                obj = linkedHashSetLinkedSetOf;
            } else {
                Object[] objArrCopyOf = Arrays.copyOf(objArr, size() + 1);
                Intrinsics.checkNotNullExpressionValue(objArrCopyOf, "copyOf(...)");
                objArrCopyOf[objArrCopyOf.length - 1] = element;
                obj = objArrCopyOf;
            }
            this.data = obj;
        } else {
            Object obj3 = this.data;
            Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type kotlin.collections.MutableSet<T of org.jetbrains.kotlin.utils.SmartSet>");
            if (!TypeIntrinsics.asMutableSet(obj3).add(element)) {
                return false;
            }
        }
        setSize(size() + 1);
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public void clear() {
        this.data = null;
        setSize(0);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean contains(Object element) {
        if (size() == 0) {
            return false;
        }
        if (size() == 1) {
            return Intrinsics.areEqual(this.data, element);
        }
        if (size() < 5) {
            Object obj = this.data;
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.Array<T of org.jetbrains.kotlin.utils.SmartSet>");
            return ArraysKt.contains((Object[]) obj, element);
        }
        Object obj2 = this.data;
        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.collections.Set<T of org.jetbrains.kotlin.utils.SmartSet>");
        return ((Set) obj2).contains(element);
    }

    /* JADX INFO: compiled from: SmartSet.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010)\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0001\n\u0000\b\u0002\u0018\u0000*\u0006\b\u0001\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00028\u0001¢\u0006\u0002\u0010\u0004J\t\u0010\u0006\u001a\u00020\u0007H\u0096\u0002J\u000e\u0010\b\u001a\u00028\u0001H\u0096\u0002¢\u0006\u0002\u0010\tJ\b\u0010\n\u001a\u00020\u000bH\u0016R\u0010\u0010\u0003\u001a\u00028\u0001X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0005R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/utils/SmartSet$SingletonIterator;", ExifInterface.GPS_DIRECTION_TRUE, "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "hasNext", "", ES6Iterator.NEXT_METHOD, "()Ljava/lang/Object;", ProductAction.ACTION_REMOVE, "", "util.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class SingletonIterator<T> implements Iterator<T>, KMutableIterator {
        private final T element;
        private boolean hasNext = true;

        public SingletonIterator(T t) {
            this.element = t;
        }

        @Override // java.util.Iterator
        public T next() {
            if (this.hasNext) {
                this.hasNext = false;
                return this.element;
            }
            throw new NoSuchElementException();
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.hasNext;
        }

        @Override // java.util.Iterator
        public Void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /* JADX INFO: compiled from: SmartSet.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010)\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010(\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0001\n\u0000\b\u0002\u0018\u0000*\u0006\b\u0001\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00010\u0004¢\u0006\u0002\u0010\u0005J\t\u0010\b\u001a\u00020\tH\u0096\u0002J\u000e\u0010\n\u001a\u00028\u0001H\u0096\u0002¢\u0006\u0002\u0010\u000bJ\b\u0010\f\u001a\u00020\rH\u0016R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/utils/SmartSet$ArrayIterator;", ExifInterface.GPS_DIRECTION_TRUE, "", "array", "", "([Ljava/lang/Object;)V", "arrayIterator", "", "hasNext", "", ES6Iterator.NEXT_METHOD, "()Ljava/lang/Object;", ProductAction.ACTION_REMOVE, "", "util.runtime"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class ArrayIterator<T> implements Iterator<T>, KMutableIterator {
        private final Iterator<T> arrayIterator;

        public ArrayIterator(T[] array) {
            Intrinsics.checkNotNullParameter(array, "array");
            this.arrayIterator = ArrayIteratorKt.iterator(array);
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.arrayIterator.hasNext();
        }

        @Override // java.util.Iterator
        public T next() {
            return this.arrayIterator.next();
        }

        @Override // java.util.Iterator
        public Void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
