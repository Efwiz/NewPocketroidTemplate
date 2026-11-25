package org.jetbrains.kotlin.kotlinx.collections.immutable.implementations.immutableList;

import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.reandroid.archive.PathTree;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import kotlin.Metadata;
import kotlin.collections.AbstractList;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.kotlinx.collections.immutable.ImmutableList;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentList;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX INFO: compiled from: AbstractPersistentList.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010(\n\u0000\n\u0002\u0010*\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u0005¢\u0006\u0002\u0010\u0004J$\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u00022\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\tH\u0016J\u001c\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u00022\f\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\tH\u0016J\u000e\u0010\u000b\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002H\u0016J\u0016\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\u000fJ\u0016\u0010\u0010\u001a\u00020\r2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\tH\u0016J\u000f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00028\u00000\u0012H\u0096\u0002J\u000e\u0010\u0013\u001a\b\u0012\u0004\u0012\u00028\u00000\u0014H\u0016J\u001b\u0010\u0015\u001a\b\u0012\u0004\u0012\u00028\u00000\u00022\u0006\u0010\u000e\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0016J\u001c\u0010\u0017\u001a\b\u0012\u0004\u0012\u00028\u00000\u00022\f\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\tH\u0016J\u001e\u0010\u0018\u001a\b\u0012\u0004\u0012\u00028\u00000\u00192\u0006\u0010\u001a\u001a\u00020\u00072\u0006\u0010\u001b\u001a\u00020\u0007H\u0016¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/immutableList/AbstractPersistentList;", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentList;", "Lkotlin/collections/AbstractList;", "()V", "addAll", "index", "", "c", "", PathTree.NAME_elements, LineReader.CLEAR, "contains", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "(Ljava/lang/Object;)Z", "containsAll", "iterator", "", "listIterator", "", ProductAction.ACTION_REMOVE, "(Ljava/lang/Object;)Lkotlinx/collections/immutable/PersistentList;", "removeAll", "subList", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/ImmutableList;", "fromIndex", "toIndex", "kotlinx-collections-immutable"}, k = 1, mv = {1, 1, 16})
public abstract class AbstractPersistentList<E> extends AbstractList<E> implements PersistentList<E> {
    @Override // kotlin.collections.AbstractList, java.util.List
    public ImmutableList<E> subList(int fromIndex, int toIndex) {
        return PersistentList.DefaultImpls.subList(this, fromIndex, toIndex);
    }

    @Override // java.util.Collection, java.util.List, org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentList
    public PersistentList<E> remove(E e) {
        int iIndexOf = indexOf(e);
        if (iIndexOf != -1) {
            return removeAt(iIndexOf);
        }
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlin.collections.AbstractCollection, java.util.Collection
    public boolean contains(Object element) {
        return indexOf(element) != -1;
    }

    @Override // kotlin.collections.AbstractCollection, java.util.Collection
    public boolean containsAll(Collection<? extends Object> elements) {
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        Collection<? extends Object> collection = elements;
        if (collection.isEmpty()) {
            return true;
        }
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            if (!contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    @Override // kotlin.collections.AbstractList, kotlin.collections.AbstractCollection, java.util.Collection, java.lang.Iterable
    public Iterator<E> iterator() {
        return listIterator();
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public ListIterator<E> listIterator() {
        return listIterator(0);
    }

    @Override // java.util.Collection, java.util.List, org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentList
    public PersistentList<E> addAll(Collection<? extends E> elements) {
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        PersistentList.Builder<E> builder = builder();
        builder.addAll(elements);
        return builder.build();
    }

    @Override // java.util.List, org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentList
    public PersistentList<E> addAll(int index, Collection<? extends E> c) {
        Intrinsics.checkParameterIsNotNull(c, "c");
        PersistentList.Builder<E> builder = builder();
        builder.addAll(index, c);
        return builder.build();
    }
}
