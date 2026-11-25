package org.jetbrains.kotlin.kotlinx.collections.immutable.implementations.immutableSet;

import androidx.exifinterface.media.ExifInterface;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.AbstractMutableSet;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentSet;
import org.jetbrains.kotlin.kotlinx.collections.immutable.internal.MutabilityOwnership;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX INFO: compiled from: PersistentHashSetBuilder.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010)\n\u0002\b\u0002\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u0013\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005¢\u0006\u0002\u0010\u0006J\u0015\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u001cJ\u000e\u0010\u001d\u001a\b\u0012\u0004\u0012\u00028\u00000\u001eH\u0016J\b\u0010\u001f\u001a\u00020 H\u0016J\u0016\u0010!\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\u001cJ\u000f\u0010\"\u001a\b\u0012\u0004\u0012\u00028\u00000#H\u0096\u0002J\u0015\u0010$\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u001cR\u001e\u0010\t\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\b@BX\u0080\u000e¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR*\u0010\r\u001a\b\u0012\u0004\u0012\u00028\u00000\f2\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\f@BX\u0080\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001e\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0007\u001a\u00020\u0010@BX\u0080\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R$\u0010\u0015\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\b@VX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u000b\"\u0004\b\u0017\u0010\u0018¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/immutableSet/PersistentHashSetBuilder;", ExifInterface.LONGITUDE_EAST, "Lkotlin/collections/AbstractMutableSet;", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentSet$Builder;", FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/immutableSet/PersistentHashSet;", "(Lkotlinx/collections/immutable/implementations/immutableSet/PersistentHashSet;)V", "<set-?>", "", "modCount", "getModCount$kotlinx_collections_immutable", "()I", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/immutableSet/TrieNode;", "node", "getNode$kotlinx_collections_immutable", "()Lkotlinx/collections/immutable/implementations/immutableSet/TrieNode;", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/internal/MutabilityOwnership;", "ownership", "getOwnership$kotlinx_collections_immutable", "()Lkotlinx/collections/immutable/internal/MutabilityOwnership;", "value", "size", "getSize", "setSize", "(I)V", ProductAction.ACTION_ADD, "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "(Ljava/lang/Object;)Z", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentSet;", LineReader.CLEAR, "", "contains", "iterator", "", ProductAction.ACTION_REMOVE, "kotlinx-collections-immutable"}, k = 1, mv = {1, 1, 16})
public final class PersistentHashSetBuilder<E> extends AbstractMutableSet<E> implements PersistentSet.Builder<E> {
    private int modCount;
    private TrieNode<E> node;
    private MutabilityOwnership ownership;
    private PersistentHashSet<E> set;
    private int size;

    public PersistentHashSetBuilder(PersistentHashSet<E> set) {
        Intrinsics.checkParameterIsNotNull(set, "set");
        this.set = set;
        this.ownership = new MutabilityOwnership();
        this.node = this.set.getNode$kotlinx_collections_immutable();
        this.size = this.set.size();
    }

    /* JADX INFO: renamed from: getOwnership$kotlinx_collections_immutable, reason: from getter */
    public final MutabilityOwnership getOwnership() {
        return this.ownership;
    }

    public final TrieNode<E> getNode$kotlinx_collections_immutable() {
        return this.node;
    }

    /* JADX INFO: renamed from: getModCount$kotlinx_collections_immutable, reason: from getter */
    public final int getModCount() {
        return this.modCount;
    }

    @Override // kotlin.collections.AbstractMutableSet
    public int getSize() {
        return this.size;
    }

    public void setSize(int i) {
        this.size = i;
        this.modCount++;
    }

    @Override // org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentSet.Builder
    public PersistentSet<E> build() {
        PersistentHashSet<E> persistentHashSet;
        if (this.node == this.set.getNode$kotlinx_collections_immutable()) {
            persistentHashSet = this.set;
        } else {
            this.ownership = new MutabilityOwnership();
            persistentHashSet = new PersistentHashSet<>(this.node, size());
        }
        this.set = persistentHashSet;
        return persistentHashSet;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean contains(Object element) {
        return this.node.contains(element != null ? element.hashCode() : 0, element, 0);
    }

    @Override // kotlin.collections.AbstractMutableSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean add(E element) {
        int size = size();
        this.node = this.node.mutableAdd(element != null ? element.hashCode() : 0, element, 0, this);
        return size != size();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean remove(Object element) {
        int size = size();
        this.node = this.node.mutableRemove(element != null ? element.hashCode() : 0, element, 0, this);
        return size != size();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public void clear() {
        TrieNode<E> eMPTY$kotlinx_collections_immutable = TrieNode.INSTANCE.getEMPTY$kotlinx_collections_immutable();
        if (eMPTY$kotlinx_collections_immutable == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlinx.collections.immutable.implementations.immutableSet.TrieNode<E>");
        }
        this.node = eMPTY$kotlinx_collections_immutable;
        setSize(0);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public Iterator<E> iterator() {
        return new PersistentHashSetMutableIterator(this);
    }
}
