package org.jetbrains.kotlin.kotlinx.collections.immutable.implementations.persistentOrderedSet;

import androidx.exifinterface.media.ExifInterface;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.AbstractMutableSet;
import kotlin.jvm.internal.Intrinsics;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentSet;
import org.jetbrains.kotlin.kotlinx.collections.immutable.implementations.immutableMap.PersistentHashMap;
import org.jetbrains.kotlin.kotlinx.collections.immutable.implementations.immutableMap.PersistentHashMapBuilder;
import org.jetbrains.kotlin.kotlinx.collections.immutable.internal.CommonFunctionsKt;
import org.jetbrains.kotlin.kotlinx.collections.immutable.internal.EndOfChain;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX INFO: compiled from: PersistentOrderedSetBuilder.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010)\n\u0002\b\u0002\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u0013\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005¢\u0006\u0002\u0010\u0006J\u0015\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u001aJ\u000e\u0010\u001b\u001a\b\u0012\u0004\u0012\u00028\u00000\u001cH\u0016J\b\u0010\u001d\u001a\u00020\u001eH\u0016J\u0016\u0010\u001f\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\u001aJ\u000f\u0010 \u001a\b\u0012\u0004\u0012\u00028\u00000!H\u0096\u0002J\u0015\u0010\"\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u001aR\u001c\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR \u0010\r\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u000f0\u000eX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0013\u001a\u00020\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/persistentOrderedSet/PersistentOrderedSetBuilder;", ExifInterface.LONGITUDE_EAST, "Lkotlin/collections/AbstractMutableSet;", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentSet$Builder;", FluentPropertyBeanIntrospector.DEFAULT_WRITE_METHOD_PREFIX, "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/persistentOrderedSet/PersistentOrderedSet;", "(Lkotlinx/collections/immutable/implementations/persistentOrderedSet/PersistentOrderedSet;)V", "firstElement", "", "getFirstElement$kotlinx_collections_immutable", "()Ljava/lang/Object;", "setFirstElement$kotlinx_collections_immutable", ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, "hashMapBuilder", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/immutableMap/PersistentHashMapBuilder;", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/persistentOrderedSet/Links;", "getHashMapBuilder$kotlinx_collections_immutable", "()Lkotlinx/collections/immutable/implementations/immutableMap/PersistentHashMapBuilder;", "lastElement", "size", "", "getSize", "()I", ProductAction.ACTION_ADD, "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "(Ljava/lang/Object;)Z", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentSet;", LineReader.CLEAR, "", "contains", "iterator", "", ProductAction.ACTION_REMOVE, "kotlinx-collections-immutable"}, k = 1, mv = {1, 1, 16})
public final class PersistentOrderedSetBuilder<E> extends AbstractMutableSet<E> implements PersistentSet.Builder<E> {
    private Object firstElement;
    private final PersistentHashMapBuilder<E, Links> hashMapBuilder;
    private Object lastElement;
    private PersistentOrderedSet<E> set;

    public PersistentOrderedSetBuilder(PersistentOrderedSet<E> set) {
        Intrinsics.checkParameterIsNotNull(set, "set");
        this.set = set;
        this.firstElement = set.getFirstElement();
        this.lastElement = this.set.getLastElement();
        this.hashMapBuilder = this.set.getHashMap$kotlinx_collections_immutable().builder();
    }

    /* JADX INFO: renamed from: getFirstElement$kotlinx_collections_immutable, reason: from getter */
    public final Object getFirstElement() {
        return this.firstElement;
    }

    public final PersistentHashMapBuilder<E, Links> getHashMapBuilder$kotlinx_collections_immutable() {
        return this.hashMapBuilder;
    }

    @Override // kotlin.collections.AbstractMutableSet
    public int getSize() {
        return this.hashMapBuilder.size();
    }

    @Override // org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentSet.Builder
    public PersistentSet<E> build() {
        PersistentOrderedSet<E> persistentOrderedSet;
        PersistentHashMap<E, Links> persistentHashMapBuild = this.hashMapBuilder.build();
        if (persistentHashMapBuild == this.set.getHashMap$kotlinx_collections_immutable()) {
            CommonFunctionsKt.m7306assert(this.firstElement == this.set.getFirstElement());
            CommonFunctionsKt.m7306assert(this.lastElement == this.set.getLastElement());
            persistentOrderedSet = this.set;
        } else {
            persistentOrderedSet = new PersistentOrderedSet<>(this.firstElement, this.lastElement, persistentHashMapBuild);
        }
        this.set = persistentOrderedSet;
        return persistentOrderedSet;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean contains(Object element) {
        return this.hashMapBuilder.containsKey(element);
    }

    @Override // kotlin.collections.AbstractMutableSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean add(E element) {
        if (this.hashMapBuilder.containsKey(element)) {
            return false;
        }
        if (isEmpty()) {
            this.firstElement = element;
            this.lastElement = element;
            this.hashMapBuilder.put(element, new Links());
            return true;
        }
        Links links = this.hashMapBuilder.get(this.lastElement);
        if (links == null) {
            Intrinsics.throwNpe();
        }
        this.hashMapBuilder.put(this.lastElement, links.withNext(element));
        this.hashMapBuilder.put(element, new Links(this.lastElement));
        this.lastElement = element;
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean remove(Object element) {
        Links linksRemove = this.hashMapBuilder.remove(element);
        if (linksRemove == null) {
            return false;
        }
        if (linksRemove.getHasPrevious()) {
            Links links = this.hashMapBuilder.get(linksRemove.getPrevious());
            if (links == null) {
                Intrinsics.throwNpe();
            }
            this.hashMapBuilder.put(linksRemove.getPrevious(), links.withNext(linksRemove.getNext()));
        } else {
            this.firstElement = linksRemove.getNext();
        }
        if (linksRemove.getHasNext()) {
            Links links2 = this.hashMapBuilder.get(linksRemove.getNext());
            if (links2 == null) {
                Intrinsics.throwNpe();
            }
            this.hashMapBuilder.put(linksRemove.getNext(), links2.withPrevious(linksRemove.getPrevious()));
            return true;
        }
        this.lastElement = linksRemove.getPrevious();
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public void clear() {
        this.hashMapBuilder.clear();
        this.firstElement = EndOfChain.INSTANCE;
        this.lastElement = EndOfChain.INSTANCE;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public Iterator<E> iterator() {
        return new PersistentOrderedSetMutableIterator(this);
    }
}
