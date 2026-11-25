package org.jetbrains.kotlin.kotlinx.collections.immutable.implementations.immutableMap;

import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.AbstractMutableSet;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.jvm.internal.markers.KMutableSet;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX INFO: compiled from: PersistentHashMapBuilderContentViews.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0010'\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010)\n\u0002\b\u0002\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u00040\u00032\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u00040\u0005B\u0019\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0007¢\u0006\u0002\u0010\bJ\u001c\u0010\r\u001a\u00020\u000e2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0004H\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\u001d\u0010\u0012\u001a\u00020\u000e2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0004H\u0096\u0002J\u001b\u0010\u0013\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00040\u0014H\u0096\u0002J\u001c\u0010\u0015\u001a\u00020\u000e2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0004H\u0016R\u001a\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\f¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/immutableMap/PersistentHashMapBuilderEntries;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "", "", "Lkotlin/collections/AbstractMutableSet;", "builder", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/immutableMap/PersistentHashMapBuilder;", "(Lkotlinx/collections/immutable/implementations/immutableMap/PersistentHashMapBuilder;)V", "size", "", "getSize", "()I", ProductAction.ACTION_ADD, "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, LineReader.CLEAR, "", "contains", "iterator", "", ProductAction.ACTION_REMOVE, "kotlinx-collections-immutable"}, k = 1, mv = {1, 1, 16})
public final class PersistentHashMapBuilderEntries<K, V> extends AbstractMutableSet<Map.Entry<K, V>> implements Set<Map.Entry<K, V>>, KMutableSet {
    private final PersistentHashMapBuilder<K, V> builder;

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final /* bridge */ boolean contains(Object obj) {
        if (TypeIntrinsics.isMutableMapEntry(obj)) {
            return contains((Map.Entry) obj);
        }
        return false;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final /* bridge */ boolean remove(Object obj) {
        if (TypeIntrinsics.isMutableMapEntry(obj)) {
            return remove((Map.Entry) obj);
        }
        return false;
    }

    public PersistentHashMapBuilderEntries(PersistentHashMapBuilder<K, V> builder) {
        Intrinsics.checkParameterIsNotNull(builder, "builder");
        this.builder = builder;
    }

    @Override // kotlin.collections.AbstractMutableSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean add(Map.Entry<K, V> element) {
        Intrinsics.checkParameterIsNotNull(element, "element");
        throw new UnsupportedOperationException();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public void clear() {
        this.builder.clear();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public Iterator<Map.Entry<K, V>> iterator() {
        return new PersistentHashMapBuilderEntriesIterator(this.builder);
    }

    public boolean remove(Map.Entry<K, V> element) {
        Intrinsics.checkParameterIsNotNull(element, "element");
        if (TypeIntrinsics.isMutableMapEntry(element)) {
            return this.builder.remove(element.getKey(), element.getValue());
        }
        return false;
    }

    @Override // kotlin.collections.AbstractMutableSet
    public int getSize() {
        return this.builder.size();
    }

    public boolean contains(Map.Entry<K, V> element) {
        Intrinsics.checkParameterIsNotNull(element, "element");
        if (!TypeIntrinsics.isMutableMapEntry(element)) {
            return false;
        }
        V v = this.builder.get(element.getKey());
        if (v != null) {
            return Intrinsics.areEqual(v, element.getValue());
        }
        return element.getValue() == null && this.builder.containsKey(element.getKey());
    }
}
