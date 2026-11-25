package org.jetbrains.kotlin.fir;

import androidx.exifinterface.media.ExifInterface;
import com.reandroid.archive.PathTree;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.UnaryOperator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmInline;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import okhttp3.HttpUrl;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: Utils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0001\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u001e\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u000e\n\u0002\u0010(\n\u0002\b\u0005\n\u0002\u0010*\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0087@\u0018\u0000 ;*\u0006\b\u0000\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002:\u0001;B\u0013\b\u0012\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004¢\u0006\u0004\b\u0005\u0010\u0006B\u0017\u0012\u000e\u0010\u0003\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0007¢\u0006\u0004\b\u0005\u0010\bJ\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0004\b\u0010\u0010\u0011J\u001d\u0010\u0012\u001a\u00020\u000e2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00028\u00000\u0014H\u0016¢\u0006\u0004\b\u0015\u0010\u0016J\u001a\u0010\u0017\u001a\u00020\u000e2\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019HÖ\u0003¢\u0006\u0004\b\u001a\u0010\u0011J\u0018\u0010\u001b\u001a\u00028\u00002\u0006\u0010\u001c\u001a\u00020\nH\u0096\u0002¢\u0006\u0004\b\u001d\u0010\u001eJ\u0010\u0010\u001f\u001a\u00020\nHÖ\u0001¢\u0006\u0004\b \u0010\fJ\u0017\u0010!\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00028\u0000H\u0016¢\u0006\u0004\b\"\u0010#J\u000f\u0010$\u001a\u00020\u000eH\u0016¢\u0006\u0004\b%\u0010&J\u0016\u0010'\u001a\b\u0012\u0004\u0012\u00028\u00000(H\u0096\u0002¢\u0006\u0004\b)\u0010*J\u0017\u0010+\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00028\u0000H\u0016¢\u0006\u0004\b,\u0010#J\u0015\u0010-\u001a\b\u0012\u0004\u0012\u00028\u00000.H\u0016¢\u0006\u0004\b/\u00100J\u001d\u0010-\u001a\b\u0012\u0004\u0012\u00028\u00000.2\u0006\u0010\u001c\u001a\u00020\nH\u0016¢\u0006\u0004\b/\u00101J%\u00102\u001a\b\u0012\u0004\u0012\u00028\u00000\u00022\u0006\u00103\u001a\u00020\n2\u0006\u00104\u001a\u00020\nH\u0016¢\u0006\u0004\b5\u00106J\u000f\u00107\u001a\u000208H\u0016¢\u0006\u0004\b9\u0010:R\u0016\u0010\u0003\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u0007X\u0080\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f\u0088\u0001\u0003\u0092\u0001\n\u0012\u0004\u0012\u0002H\u0001\u0018\u00010\u0007¨\u0006<"}, d2 = {"Lorg/jetbrains/kotlin/fir/MutableOrEmptyList;", ExifInterface.GPS_DIRECTION_TRUE, "", SchemaSymbols.ATTVAL_LIST, "", "constructor-impl", "(Ljava/lang/Void;)Ljava/util/List;", "", "(Ljava/util/List;)Ljava/util/List;", "size", "", "getSize-impl", "(Ljava/util/List;)I", "contains", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "contains-impl", "(Ljava/util/List;Ljava/lang/Object;)Z", "containsAll", PathTree.NAME_elements, "", "containsAll-impl", "(Ljava/util/List;Ljava/util/Collection;)Z", Namer.EQUALS_METHOD_NAME, "other", "", "equals-impl", "get", "index", "get-impl", "(Ljava/util/List;I)Ljava/lang/Object;", "hashCode", "hashCode-impl", "indexOf", "indexOf-impl", "(Ljava/util/List;Ljava/lang/Object;)I", "isEmpty", "isEmpty-impl", "(Ljava/util/List;)Z", "iterator", "", "iterator-impl", "(Ljava/util/List;)Ljava/util/Iterator;", "lastIndexOf", "lastIndexOf-impl", "listIterator", "", "listIterator-impl", "(Ljava/util/List;)Ljava/util/ListIterator;", "(Ljava/util/List;I)Ljava/util/ListIterator;", "subList", "fromIndex", "toIndex", "subList-impl", "(Ljava/util/List;II)Ljava/util/List;", "toString", "", "toString-impl", "(Ljava/util/List;)Ljava/lang/String;", "Companion", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
@JvmInline
public final class MutableOrEmptyList<T> implements List<T>, KMappedMarker {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final List<T> EMPTY = m6757constructorimpl((Void) null);
    private static final List EMPTY_LIST_STUB;
    private static final Iterator EMPTY_LIST_STUB_ITERATOR;
    private static final ListIterator EMPTY_LIST_STUB_LIST_ITERATOR;
    private final List<T> list;

    /* JADX INFO: renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ MutableOrEmptyList m6756boximpl(List list) {
        return new MutableOrEmptyList(list);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static <T> List<T> m6758constructorimpl(List<T> list) {
        return list;
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m6761equalsimpl(List<T> list, Object obj) {
        return (obj instanceof MutableOrEmptyList) && Intrinsics.areEqual(list, ((MutableOrEmptyList) obj).getList());
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m6762equalsimpl0(List<T> list, List<T> list2) {
        return Intrinsics.areEqual(list, list2);
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m6765hashCodeimpl(List<T> list) {
        if (list == null) {
            return 0;
        }
        return list.hashCode();
    }

    @Override // java.util.List
    public void add(int i, T t) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.List, java.util.Collection
    public boolean add(T t) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.List
    public boolean addAll(int i, Collection<? extends T> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.List, java.util.Collection
    public boolean addAll(Collection<? extends T> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.List, java.util.Collection
    public void clear() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.List, java.util.Collection
    public boolean equals(Object obj) {
        return m6761equalsimpl(this.list, obj);
    }

    @Override // java.util.List, java.util.Collection
    public int hashCode() {
        return m6765hashCodeimpl(this.list);
    }

    @Override // java.util.List
    public T remove(int i) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.List, java.util.Collection
    public boolean remove(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.List, java.util.Collection
    public boolean removeAll(Collection<? extends Object> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.List
    public void replaceAll(UnaryOperator<T> unaryOperator) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.List, java.util.Collection
    public boolean retainAll(Collection<? extends Object> collection) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.List
    public T set(int i, T t) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.List
    public void sort(Comparator<? super T> comparator) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.List, java.util.Collection
    public Object[] toArray() {
        return CollectionToArray.toArray(this);
    }

    @Override // java.util.List, java.util.Collection
    public <T> T[] toArray(T[] array) {
        Intrinsics.checkNotNullParameter(array, "array");
        return (T[]) CollectionToArray.toArray(this, array);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name and from getter */
    public final /* synthetic */ List getList() {
        return this.list;
    }

    private /* synthetic */ MutableOrEmptyList(List list) {
        this.list = list;
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    private static <T> List<T> m6757constructorimpl(Void r0) {
        return m6758constructorimpl(TypeIntrinsics.asMutableList(r0));
    }

    /* JADX INFO: renamed from: getSize-impl, reason: not valid java name */
    public static int m6764getSizeimpl(List<T> list) {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override // java.util.List, java.util.Collection
    /* JADX INFO: renamed from: getSize, reason: merged with bridge method [inline-methods] */
    public int size() {
        return m6764getSizeimpl(this.list);
    }

    @Override // java.util.List
    public T get(int i) {
        return (T) m6763getimpl(this.list, i);
    }

    /* JADX INFO: renamed from: get-impl, reason: not valid java name */
    public static T m6763getimpl(List<T> list, int i) {
        Intrinsics.checkNotNull(list);
        return list.get(i);
    }

    @Override // java.util.List, java.util.Collection
    public boolean isEmpty() {
        return m6767isEmptyimpl(this.list);
    }

    /* JADX INFO: renamed from: isEmpty-impl, reason: not valid java name */
    public static boolean m6767isEmptyimpl(List<T> list) {
        if (list != null) {
            return list.isEmpty();
        }
        return true;
    }

    @Override // java.util.List, java.util.Collection, java.lang.Iterable
    public Iterator<T> iterator() {
        return m6768iteratorimpl(this.list);
    }

    /* JADX INFO: renamed from: iterator-impl, reason: not valid java name */
    public static Iterator<T> m6768iteratorimpl(List<T> list) {
        Iterator<T> it;
        return (list == null || (it = list.iterator()) == null) ? EMPTY_LIST_STUB_ITERATOR : it;
    }

    @Override // java.util.List
    public ListIterator<T> listIterator() {
        return m6770listIteratorimpl(this.list);
    }

    /* JADX INFO: renamed from: listIterator-impl, reason: not valid java name */
    public static ListIterator<T> m6770listIteratorimpl(List<T> list) {
        ListIterator<T> listIterator;
        return (list == null || (listIterator = list.listIterator()) == null) ? EMPTY_LIST_STUB_LIST_ITERATOR : listIterator;
    }

    @Override // java.util.List
    public ListIterator<T> listIterator(int i) {
        return m6771listIteratorimpl(this.list, i);
    }

    /* JADX INFO: renamed from: listIterator-impl, reason: not valid java name */
    public static ListIterator<T> m6771listIteratorimpl(List<T> list, int i) {
        ListIterator<T> listIterator;
        return (list == null || (listIterator = list.listIterator(i)) == null) ? EMPTY_LIST_STUB_LIST_ITERATOR : listIterator;
    }

    @Override // java.util.List
    public List<T> subList(int i, int i2) {
        return m6772subListimpl(this.list, i, i2);
    }

    /* JADX INFO: renamed from: subList-impl, reason: not valid java name */
    public static List<T> m6772subListimpl(List<T> list, int i, int i2) {
        if (list == null && i == 0 && i2 == 0) {
            return m6756boximpl(list);
        }
        Intrinsics.checkNotNull(list);
        return list.subList(i, i2);
    }

    @Override // java.util.List
    public int lastIndexOf(Object obj) {
        return m6769lastIndexOfimpl(this.list, obj);
    }

    /* JADX INFO: renamed from: lastIndexOf-impl, reason: not valid java name */
    public static int m6769lastIndexOfimpl(List<T> list, T t) {
        if (list != null) {
            return list.lastIndexOf(t);
        }
        return -1;
    }

    @Override // java.util.List
    public int indexOf(Object obj) {
        return m6766indexOfimpl(this.list, obj);
    }

    /* JADX INFO: renamed from: indexOf-impl, reason: not valid java name */
    public static int m6766indexOfimpl(List<T> list, T t) {
        if (list != null) {
            return list.indexOf(t);
        }
        return -1;
    }

    @Override // java.util.List, java.util.Collection
    public boolean containsAll(Collection<? extends Object> elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        return m6760containsAllimpl(this.list, elements);
    }

    /* JADX INFO: renamed from: containsAll-impl, reason: not valid java name */
    public static boolean m6760containsAllimpl(List<T> list, Collection<? extends T> elements) {
        Intrinsics.checkNotNullParameter(elements, "elements");
        return list != null ? list.containsAll(elements) : elements.isEmpty();
    }

    @Override // java.util.List, java.util.Collection
    public boolean contains(Object obj) {
        return m6759containsimpl(this.list, obj);
    }

    /* JADX INFO: renamed from: contains-impl, reason: not valid java name */
    public static boolean m6759containsimpl(List<T> list, T t) {
        if (list != null) {
            return list.contains(t);
        }
        return false;
    }

    public String toString() {
        return m6773toStringimpl(this.list);
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m6773toStringimpl(List<T> list) {
        String strJoinToString$default;
        return (list == null || (strJoinToString$default = CollectionsKt.joinToString$default(list, null, "[", "]", 0, null, null, 57, null)) == null) ? HttpUrl.PATH_SEGMENT_ENCODE_SET_URI : strJoinToString$default;
    }

    /* JADX INFO: compiled from: Utils.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010(\n\u0000\n\u0002\u0010*\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001f\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u000e0\u0004\"\u0004\b\u0001\u0010\u000eø\u0001\u0001ø\u0001\u0000¢\u0006\u0004\b\u000f\u0010\u0010R\u001c\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004ø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\n\u0002\u0010\u0006R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\fX\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0005\b¡\u001e0\u0001\n\u0002\b!¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/MutableOrEmptyList$Companion;", "", "()V", "EMPTY", "Lorg/jetbrains/kotlin/fir/MutableOrEmptyList;", "", "Ljava/util/List;", "EMPTY_LIST_STUB", "", "EMPTY_LIST_STUB_ITERATOR", "", "EMPTY_LIST_STUB_LIST_ITERATOR", "", "empty", ExifInterface.GPS_DIRECTION_TRUE, "empty-5e3fPpI", "()Ljava/util/List;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: renamed from: empty-5e3fPpI, reason: not valid java name */
        public final <T> List<T> m6775empty5e3fPpI() {
            return MutableOrEmptyList.EMPTY;
        }
    }

    static {
        List listEmptyList = CollectionsKt.emptyList();
        EMPTY_LIST_STUB = listEmptyList;
        EMPTY_LIST_STUB_ITERATOR = listEmptyList.iterator();
        EMPTY_LIST_STUB_LIST_ITERATOR = listEmptyList.listIterator();
    }
}
