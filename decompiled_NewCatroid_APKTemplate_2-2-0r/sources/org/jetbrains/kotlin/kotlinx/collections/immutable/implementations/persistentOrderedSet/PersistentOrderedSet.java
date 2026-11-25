package org.jetbrains.kotlin.kotlinx.collections.immutable.implementations.persistentOrderedSet;

import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.reandroid.archive.PathTree;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.AbstractSet;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentSet;
import org.jetbrains.kotlin.kotlinx.collections.immutable.implementations.immutableMap.PersistentHashMap;
import org.jetbrains.kotlin.kotlinx.collections.immutable.internal.EndOfChain;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX INFO: compiled from: PersistentOrderedSet.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010(\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u0000 &*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003:\u0001&B-\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\t0\b¢\u0006\u0002\u0010\nJ\u001b\u0010\u0014\u001a\b\u0012\u0004\u0012\u00028\u00000\u00032\u0006\u0010\u0015\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0016J\u001c\u0010\u0017\u001a\b\u0012\u0004\u0012\u00028\u00000\u00032\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00028\u00000\u0019H\u0016J\u000e\u0010\u001a\u001a\b\u0012\u0004\u0012\u00028\u00000\u001bH\u0016J\u000e\u0010\u001c\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u0016J\u0016\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u0015\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\u001fJ\u000f\u0010 \u001a\b\u0012\u0004\u0012\u00028\u00000!H\u0096\u0002J\u001b\u0010\"\u001a\b\u0012\u0004\u0012\u00028\u00000\u00032\u0006\u0010\u0015\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0016J\"\u0010#\u001a\b\u0012\u0004\u0012\u00028\u00000\u00032\u0012\u0010$\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u001e0%H\u0016J\u001c\u0010#\u001a\b\u0012\u0004\u0012\u00028\u00000\u00032\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00028\u00000\u0019H\u0016R\u0016\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR \u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\t0\bX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0016\u0010\u0006\u001a\u0004\u0018\u00010\u0005X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\fR\u0014\u0010\u0010\u001a\u00020\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/persistentOrderedSet/PersistentOrderedSet;", ExifInterface.LONGITUDE_EAST, "Lkotlin/collections/AbstractSet;", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentSet;", "firstElement", "", "lastElement", "hashMap", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/immutableMap/PersistentHashMap;", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/persistentOrderedSet/Links;", "(Ljava/lang/Object;Ljava/lang/Object;Lkotlinx/collections/immutable/implementations/immutableMap/PersistentHashMap;)V", "getFirstElement$kotlinx_collections_immutable", "()Ljava/lang/Object;", "getHashMap$kotlinx_collections_immutable", "()Lkotlinx/collections/immutable/implementations/immutableMap/PersistentHashMap;", "getLastElement$kotlinx_collections_immutable", "size", "", "getSize", "()I", ProductAction.ACTION_ADD, CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "(Ljava/lang/Object;)Lkotlinx/collections/immutable/PersistentSet;", "addAll", PathTree.NAME_elements, "", "builder", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentSet$Builder;", LineReader.CLEAR, "contains", "", "(Ljava/lang/Object;)Z", "iterator", "", ProductAction.ACTION_REMOVE, "removeAll", "predicate", "Lkotlin/Function1;", "Companion", "kotlinx-collections-immutable"}, k = 1, mv = {1, 1, 16})
public final class PersistentOrderedSet<E> extends AbstractSet<E> implements PersistentSet<E> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final PersistentOrderedSet EMPTY = new PersistentOrderedSet(EndOfChain.INSTANCE, EndOfChain.INSTANCE, PersistentHashMap.INSTANCE.emptyOf$kotlinx_collections_immutable());
    private final Object firstElement;
    private final PersistentHashMap<E, Links> hashMap;
    private final Object lastElement;

    /* JADX INFO: renamed from: getFirstElement$kotlinx_collections_immutable, reason: from getter */
    public final Object getFirstElement() {
        return this.firstElement;
    }

    /* JADX INFO: renamed from: getLastElement$kotlinx_collections_immutable, reason: from getter */
    public final Object getLastElement() {
        return this.lastElement;
    }

    public final PersistentHashMap<E, Links> getHashMap$kotlinx_collections_immutable() {
        return this.hashMap;
    }

    public PersistentOrderedSet(Object obj, Object obj2, PersistentHashMap<E, Links> hashMap) {
        Intrinsics.checkParameterIsNotNull(hashMap, "hashMap");
        this.firstElement = obj;
        this.lastElement = obj2;
        this.hashMap = hashMap;
    }

    @Override // kotlin.collections.AbstractCollection
    public int getSize() {
        return this.hashMap.size();
    }

    @Override // kotlin.collections.AbstractCollection, java.util.Collection
    public boolean contains(Object element) {
        return this.hashMap.containsKey(element);
    }

    @Override // java.util.Collection, java.util.Set, org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentSet
    public PersistentSet<E> add(E e) {
        if (this.hashMap.containsKey(e)) {
            return this;
        }
        if (isEmpty()) {
            return new PersistentOrderedSet(e, e, this.hashMap.put(e, new Links()));
        }
        Object obj = this.lastElement;
        Links links = this.hashMap.get(obj);
        if (links == null) {
            Intrinsics.throwNpe();
        }
        return new PersistentOrderedSet(this.firstElement, e, this.hashMap.put((E) obj, links.withNext(e)).put(e, new Links(obj)));
    }

    @Override // java.util.Collection, java.util.Set, org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentSet
    public PersistentSet<E> addAll(Collection<? extends E> elements) {
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        PersistentSet.Builder<E> builder = builder();
        builder.addAll(elements);
        return builder.build();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.Collection, java.util.Set, org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentSet
    public PersistentSet<E> remove(E e) {
        Links links = this.hashMap.get(e);
        if (links == null) {
            return this;
        }
        PersistentHashMap persistentHashMapRemove = this.hashMap.remove(e);
        if (links.getHasPrevious()) {
            V v = persistentHashMapRemove.get(links.getPrevious());
            if (v == 0) {
                Intrinsics.throwNpe();
            }
            persistentHashMapRemove = persistentHashMapRemove.put(links.getPrevious(), ((Links) v).withNext(links.getNext()));
        }
        if (links.getHasNext()) {
            V v2 = persistentHashMapRemove.get(links.getNext());
            if (v2 == 0) {
                Intrinsics.throwNpe();
            }
            persistentHashMapRemove = persistentHashMapRemove.put(links.getNext(), ((Links) v2).withPrevious(links.getPrevious()));
        }
        return new PersistentOrderedSet(!links.getHasPrevious() ? links.getNext() : this.firstElement, !links.getHasNext() ? links.getPrevious() : this.lastElement, persistentHashMapRemove);
    }

    @Override // kotlin.collections.AbstractSet, kotlin.collections.AbstractCollection, java.util.Collection, java.lang.Iterable
    public Iterator<E> iterator() {
        return new PersistentOrderedSetIterator(this.firstElement, this.hashMap);
    }

    @Override // org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentSet
    public PersistentSet.Builder<E> builder() {
        return new PersistentOrderedSetBuilder(this);
    }

    /* JADX INFO: compiled from: PersistentOrderedSet.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0080\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0019\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\b0\u0007\"\u0004\b\u0001\u0010\bH\u0000¢\u0006\u0002\b\tR\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/persistentOrderedSet/PersistentOrderedSet$Companion;", "", "()V", "EMPTY", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/persistentOrderedSet/PersistentOrderedSet;", "", "emptyOf", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentSet;", ExifInterface.LONGITUDE_EAST, "emptyOf$kotlinx_collections_immutable", "kotlinx-collections-immutable"}, k = 1, mv = {1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final <E> PersistentSet<E> emptyOf$kotlinx_collections_immutable() {
            return PersistentOrderedSet.EMPTY;
        }
    }
}
