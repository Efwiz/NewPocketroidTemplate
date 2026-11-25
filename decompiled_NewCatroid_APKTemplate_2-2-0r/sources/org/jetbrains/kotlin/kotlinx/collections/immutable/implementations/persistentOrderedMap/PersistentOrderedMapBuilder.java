package org.jetbrains.kotlin.kotlinx.collections.immutable.implementations.persistentOrderedMap;

import androidx.exifinterface.media.ExifInterface;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.firebase.crashlytics.internal.metadata.UserMetadata;
import com.reandroid.arsc.chunk.TypeBlock;
import com.reandroid.arsc.value.CompoundEntry;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.AbstractMutableMap;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentMap;
import org.jetbrains.kotlin.kotlinx.collections.immutable.implementations.immutableMap.PersistentHashMap;
import org.jetbrains.kotlin.kotlinx.collections.immutable.implementations.immutableMap.PersistentHashMapBuilder;
import org.jetbrains.kotlin.kotlinx.collections.immutable.internal.CommonFunctionsKt;
import org.jetbrains.kotlin.kotlinx.collections.immutable.internal.EndOfChain;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX INFO: compiled from: PersistentOrderedMapBuilder.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0010'\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u001f\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u00032\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0004B\u0019\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0006¢\u0006\u0002\u0010\u0007J\u0014\u0010\"\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010#H\u0016J\b\u0010$\u001a\u00020%H\u0016J\u0015\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010)J\u0018\u0010*\u001a\u0004\u0018\u00018\u00012\u0006\u0010(\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010+J\u001f\u0010,\u001a\u0004\u0018\u00018\u00012\u0006\u0010(\u001a\u00028\u00002\u0006\u0010-\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010.J\u0017\u0010/\u001a\u0004\u0018\u00018\u00012\u0006\u0010(\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010+J\u001b\u0010/\u001a\u00020'2\u0006\u0010(\u001a\u00028\u00002\u0006\u0010-\u001a\u00028\u0001¢\u0006\u0002\u00100R&\u0010\b\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\n0\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\"\u0010\u000f\u001a\u0004\u0018\u00010\u000e2\b\u0010\r\u001a\u0004\u0018\u00010\u000e@BX\u0080\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R&\u0010\u0012\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\u00140\u0013X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0017\u001a\b\u0012\u0004\u0012\u00028\u00000\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\fR\u0010\u0010\u0019\u001a\u0004\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u001a\u001a\u00020\u001b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u001a\u0010\u001e\u001a\b\u0012\u0004\u0012\u00028\u00010\u001f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b \u0010!¨\u00061"}, d2 = {"Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/persistentOrderedMap/PersistentOrderedMapBuilder;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Lkotlin/collections/AbstractMutableMap;", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentMap$Builder;", "map", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/persistentOrderedMap/PersistentOrderedMap;", "(Lkotlinx/collections/immutable/implementations/persistentOrderedMap/PersistentOrderedMap;)V", TypeBlock.NAME_entries, "", "", "getEntries", "()Ljava/util/Set;", "<set-?>", "", "firstKey", "getFirstKey$kotlinx_collections_immutable", "()Ljava/lang/Object;", "hashMapBuilder", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/immutableMap/PersistentHashMapBuilder;", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/persistentOrderedMap/LinkedValue;", "getHashMapBuilder$kotlinx_collections_immutable", "()Lkotlinx/collections/immutable/implementations/immutableMap/PersistentHashMapBuilder;", UserMetadata.KEYDATA_FILENAME, "getKeys", "lastKey", "size", "", "getSize", "()I", CompoundEntry.NAME_values, "", "getValues", "()Ljava/util/Collection;", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentMap;", LineReader.CLEAR, "", "containsKey", "", ASTExpr.DEFAULT_MAP_KEY_NAME, "(Ljava/lang/Object;)Z", "get", "(Ljava/lang/Object;)Ljava/lang/Object;", "put", "value", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", ProductAction.ACTION_REMOVE, "(Ljava/lang/Object;Ljava/lang/Object;)Z", "kotlinx-collections-immutable"}, k = 1, mv = {1, 1, 16})
public final class PersistentOrderedMapBuilder<K, V> extends AbstractMutableMap<K, V> implements PersistentMap.Builder<K, V> {
    private Object firstKey;
    private final PersistentHashMapBuilder<K, LinkedValue<V>> hashMapBuilder;
    private Object lastKey;
    private PersistentOrderedMap<K, V> map;

    public PersistentOrderedMapBuilder(PersistentOrderedMap<K, V> map) {
        Intrinsics.checkParameterIsNotNull(map, "map");
        this.map = map;
        this.firstKey = map.getFirstKey();
        this.lastKey = this.map.getLastKey();
        this.hashMapBuilder = this.map.getHashMap$kotlinx_collections_immutable().builder();
    }

    /* JADX INFO: renamed from: getFirstKey$kotlinx_collections_immutable, reason: from getter */
    public final Object getFirstKey() {
        return this.firstKey;
    }

    public final PersistentHashMapBuilder<K, LinkedValue<V>> getHashMapBuilder$kotlinx_collections_immutable() {
        return this.hashMapBuilder;
    }

    @Override // kotlin.collections.AbstractMutableMap
    public int getSize() {
        return this.hashMapBuilder.size();
    }

    @Override // org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentMap.Builder
    public PersistentMap<K, V> build() {
        PersistentOrderedMap<K, V> persistentOrderedMap;
        PersistentHashMap<K, LinkedValue<V>> persistentHashMapBuild = this.hashMapBuilder.build();
        if (persistentHashMapBuild == this.map.getHashMap$kotlinx_collections_immutable()) {
            CommonFunctionsKt.m7306assert(this.firstKey == this.map.getFirstKey());
            CommonFunctionsKt.m7306assert(this.lastKey == this.map.getLastKey());
            persistentOrderedMap = this.map;
        } else {
            persistentOrderedMap = new PersistentOrderedMap<>(this.firstKey, this.lastKey, persistentHashMapBuild);
        }
        this.map = persistentOrderedMap;
        return persistentOrderedMap;
    }

    @Override // kotlin.collections.AbstractMutableMap
    public Set<Map.Entry<K, V>> getEntries() {
        return new PersistentOrderedMapBuilderEntries(this);
    }

    @Override // kotlin.collections.AbstractMutableMap
    public Set<K> getKeys() {
        return new PersistentOrderedMapBuilderKeys(this);
    }

    @Override // kotlin.collections.AbstractMutableMap
    public Collection<V> getValues() {
        return new PersistentOrderedMapBuilderValues(this);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsKey(Object key) {
        return this.hashMapBuilder.containsKey(key);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V get(Object key) {
        LinkedValue<V> linkedValue = this.hashMapBuilder.get(key);
        if (linkedValue != null) {
            return linkedValue.getValue();
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlin.collections.AbstractMutableMap, java.util.AbstractMap, java.util.Map
    public V put(K key, V value) {
        LinkedValue<V> linkedValue = this.hashMapBuilder.get(key);
        if (linkedValue != null) {
            if (linkedValue.getValue() == value) {
                return value;
            }
            this.hashMapBuilder.put(key, linkedValue.withValue(value));
            return linkedValue.getValue();
        }
        if (isEmpty()) {
            this.firstKey = key;
            this.lastKey = key;
            this.hashMapBuilder.put(key, new LinkedValue<>(value));
            return null;
        }
        Object obj = this.lastKey;
        LinkedValue<V> linkedValue2 = this.hashMapBuilder.get(obj);
        if (linkedValue2 == null) {
            Intrinsics.throwNpe();
        }
        LinkedValue<V> linkedValue3 = linkedValue2;
        CommonFunctionsKt.m7306assert(!linkedValue3.getHasNext());
        this.hashMapBuilder.put(obj, linkedValue3.withNext(key));
        this.hashMapBuilder.put(key, new LinkedValue<>(value, obj));
        this.lastKey = key;
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.AbstractMap, java.util.Map
    public V remove(Object key) {
        LinkedValue<V> linkedValueRemove = this.hashMapBuilder.remove(key);
        if (linkedValueRemove == null) {
            return null;
        }
        if (linkedValueRemove.getHasPrevious()) {
            LinkedValue<V> linkedValue = this.hashMapBuilder.get(linkedValueRemove.getPrevious());
            if (linkedValue == null) {
                Intrinsics.throwNpe();
            }
            this.hashMapBuilder.put(linkedValueRemove.getPrevious(), linkedValue.withNext(linkedValueRemove.getNext()));
        } else {
            this.firstKey = linkedValueRemove.getNext();
        }
        if (linkedValueRemove.getHasNext()) {
            LinkedValue<V> linkedValue2 = this.hashMapBuilder.get(linkedValueRemove.getNext());
            if (linkedValue2 == null) {
                Intrinsics.throwNpe();
            }
            this.hashMapBuilder.put(linkedValueRemove.getNext(), linkedValue2.withPrevious(linkedValueRemove.getPrevious()));
        } else {
            this.lastKey = linkedValueRemove.getPrevious();
        }
        return linkedValueRemove.getValue();
    }

    @Override // java.util.Map
    public final boolean remove(K key, V value) {
        LinkedValue<V> linkedValue = this.hashMapBuilder.get(key);
        if (linkedValue == null || !Intrinsics.areEqual(linkedValue.getValue(), value)) {
            return false;
        }
        remove(key);
        return true;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        this.hashMapBuilder.clear();
        this.firstKey = EndOfChain.INSTANCE;
        this.lastKey = EndOfChain.INSTANCE;
    }
}
