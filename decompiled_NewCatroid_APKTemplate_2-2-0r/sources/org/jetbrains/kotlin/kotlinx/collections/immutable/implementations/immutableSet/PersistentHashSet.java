package org.jetbrains.kotlin.kotlinx.collections.immutable.implementations.immutableSet;

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
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX INFO: compiled from: PersistentHashSet.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010(\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u0000 \u001f*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003:\u0001\u001fB\u001b\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u001b\u0010\r\u001a\b\u0012\u0004\u0012\u00028\u00000\u00032\u0006\u0010\u000e\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u000fJ\u001c\u0010\u0010\u001a\b\u0012\u0004\u0012\u00028\u00000\u00032\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00028\u00000\u0012H\u0016J\u000e\u0010\u0013\u001a\b\u0012\u0004\u0012\u00028\u00000\u0014H\u0016J\u000e\u0010\u0015\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u0016J\u0016\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u000e\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\u0018J\u000f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00028\u00000\u001aH\u0096\u0002J\u001b\u0010\u001b\u001a\b\u0012\u0004\u0012\u00028\u00000\u00032\u0006\u0010\u000e\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u000fJ\"\u0010\u001c\u001a\b\u0012\u0004\u0012\u00028\u00000\u00032\u0012\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00170\u001eH\u0016J\u001c\u0010\u001c\u001a\b\u0012\u0004\u0012\u00028\u00000\u00032\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00028\u00000\u0012H\u0016R\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/immutableSet/PersistentHashSet;", ExifInterface.LONGITUDE_EAST, "Lkotlin/collections/AbstractSet;", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentSet;", "node", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/immutableSet/TrieNode;", "size", "", "(Lkotlinx/collections/immutable/implementations/immutableSet/TrieNode;I)V", "getNode$kotlinx_collections_immutable", "()Lkotlinx/collections/immutable/implementations/immutableSet/TrieNode;", "getSize", "()I", ProductAction.ACTION_ADD, CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "(Ljava/lang/Object;)Lkotlinx/collections/immutable/PersistentSet;", "addAll", PathTree.NAME_elements, "", "builder", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentSet$Builder;", LineReader.CLEAR, "contains", "", "(Ljava/lang/Object;)Z", "iterator", "", ProductAction.ACTION_REMOVE, "removeAll", "predicate", "Lkotlin/Function1;", "Companion", "kotlinx-collections-immutable"}, k = 1, mv = {1, 1, 16})
public final class PersistentHashSet<E> extends AbstractSet<E> implements PersistentSet<E> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final PersistentHashSet EMPTY = new PersistentHashSet(TrieNode.INSTANCE.getEMPTY$kotlinx_collections_immutable(), 0);
    private final TrieNode<E> node;
    private final int size;

    public final TrieNode<E> getNode$kotlinx_collections_immutable() {
        return this.node;
    }

    public PersistentHashSet(TrieNode<E> node, int i) {
        Intrinsics.checkParameterIsNotNull(node, "node");
        this.node = node;
        this.size = i;
    }

    @Override // kotlin.collections.AbstractCollection
    /* JADX INFO: renamed from: getSize, reason: from getter */
    public int get_size() {
        return this.size;
    }

    @Override // kotlin.collections.AbstractCollection, java.util.Collection
    public boolean contains(Object element) {
        return this.node.contains(element != null ? element.hashCode() : 0, element, 0);
    }

    @Override // java.util.Collection, java.util.Set, org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentSet
    public PersistentSet<E> add(E e) {
        TrieNode<E> trieNodeAdd = this.node.add(e != null ? e.hashCode() : 0, e, 0);
        return this.node == trieNodeAdd ? this : new PersistentHashSet(trieNodeAdd, size() + 1);
    }

    @Override // java.util.Collection, java.util.Set, org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentSet
    public PersistentSet<E> addAll(Collection<? extends E> elements) {
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        PersistentSet.Builder<E> builder = builder();
        builder.addAll(elements);
        return builder.build();
    }

    @Override // java.util.Collection, java.util.Set, org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentSet
    public PersistentSet<E> remove(E e) {
        TrieNode<E> trieNodeRemove = this.node.remove(e != null ? e.hashCode() : 0, e, 0);
        return this.node == trieNodeRemove ? this : new PersistentHashSet(trieNodeRemove, size() - 1);
    }

    @Override // kotlin.collections.AbstractSet, kotlin.collections.AbstractCollection, java.util.Collection, java.lang.Iterable
    public Iterator<E> iterator() {
        return new PersistentHashSetIterator(this.node);
    }

    @Override // org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentSet
    public PersistentSet.Builder<E> builder() {
        return new PersistentHashSetBuilder(this);
    }

    /* JADX INFO: compiled from: PersistentHashSet.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0080\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0019\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\b0\u0007\"\u0004\b\u0001\u0010\bH\u0000¢\u0006\u0002\b\tR\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/immutableSet/PersistentHashSet$Companion;", "", "()V", "EMPTY", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/immutableSet/PersistentHashSet;", "", "emptyOf", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentSet;", ExifInterface.LONGITUDE_EAST, "emptyOf$kotlinx_collections_immutable", "kotlinx-collections-immutable"}, k = 1, mv = {1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final <E> PersistentSet<E> emptyOf$kotlinx_collections_immutable() {
            return PersistentHashSet.EMPTY;
        }
    }
}
