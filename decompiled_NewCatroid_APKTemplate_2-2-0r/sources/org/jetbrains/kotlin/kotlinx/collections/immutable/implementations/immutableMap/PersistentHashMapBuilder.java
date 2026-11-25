package org.jetbrains.kotlin.kotlinx.collections.immutable.implementations.immutableMap;

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
import kotlin.TypeCastException;
import kotlin.collections.AbstractMutableMap;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentMap;
import org.jetbrains.kotlin.kotlinx.collections.immutable.internal.MutabilityOwnership;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX INFO: compiled from: PersistentHashMapBuilder.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0010'\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u001f\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\t\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u00032\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0004B\u0019\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0006¢\u0006\u0002\u0010\u0007J\u0014\u0010.\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0006H\u0016J\b\u0010/\u001a\u000200H\u0016J\u0015\u00101\u001a\u0002022\u0006\u00103\u001a\u00028\u0000H\u0016¢\u0006\u0002\u00104J\u0018\u00105\u001a\u0004\u0018\u00018\u00012\u0006\u00103\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u00106J\u001f\u00107\u001a\u0004\u0018\u00018\u00012\u0006\u00103\u001a\u00028\u00002\u0006\u0010&\u001a\u00028\u0001H\u0016¢\u0006\u0002\u00108J\u0017\u00109\u001a\u0004\u0018\u00018\u00012\u0006\u00103\u001a\u00028\u0000H\u0016¢\u0006\u0002\u00106J\u001b\u00109\u001a\u0002022\u0006\u00103\u001a\u00028\u00002\u0006\u0010&\u001a\u00028\u0001¢\u0006\u0002\u0010:R&\u0010\b\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\n0\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\b\u0012\u0004\u0012\u00028\u00000\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\fR\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u000f\u001a\u00020\u0010X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R&\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0016X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001e\u0010\u001b\u001a\u0004\u0018\u00018\u0001X\u0080\u000e¢\u0006\u0010\n\u0002\u0010 \u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u001e\u0010#\u001a\u00020\"2\u0006\u0010!\u001a\u00020\"@BX\u0080\u000e¢\u0006\b\n\u0000\u001a\u0004\b$\u0010%R$\u0010'\u001a\u00020\u00102\u0006\u0010&\u001a\u00020\u0010@VX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010\u0012\"\u0004\b)\u0010\u0014R\u001a\u0010*\u001a\b\u0012\u0004\u0012\u00028\u00010+8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b,\u0010-¨\u0006;"}, d2 = {"Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/immutableMap/PersistentHashMapBuilder;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentMap$Builder;", "Lkotlin/collections/AbstractMutableMap;", "map", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/immutableMap/PersistentHashMap;", "(Lkotlinx/collections/immutable/implementations/immutableMap/PersistentHashMap;)V", TypeBlock.NAME_entries, "", "", "getEntries", "()Ljava/util/Set;", UserMetadata.KEYDATA_FILENAME, "getKeys", "modCount", "", "getModCount$kotlinx_collections_immutable", "()I", "setModCount$kotlinx_collections_immutable", "(I)V", "node", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/immutableMap/TrieNode;", "getNode$kotlinx_collections_immutable", "()Lkotlinx/collections/immutable/implementations/immutableMap/TrieNode;", "setNode$kotlinx_collections_immutable", "(Lkotlinx/collections/immutable/implementations/immutableMap/TrieNode;)V", "operationResult", "getOperationResult$kotlinx_collections_immutable", "()Ljava/lang/Object;", "setOperationResult$kotlinx_collections_immutable", ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "<set-?>", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/internal/MutabilityOwnership;", "ownership", "getOwnership$kotlinx_collections_immutable", "()Lkotlinx/collections/immutable/internal/MutabilityOwnership;", "value", "size", "getSize", "setSize", CompoundEntry.NAME_values, "", "getValues", "()Ljava/util/Collection;", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, LineReader.CLEAR, "", "containsKey", "", ASTExpr.DEFAULT_MAP_KEY_NAME, "(Ljava/lang/Object;)Z", "get", "(Ljava/lang/Object;)Ljava/lang/Object;", "put", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", ProductAction.ACTION_REMOVE, "(Ljava/lang/Object;Ljava/lang/Object;)Z", "kotlinx-collections-immutable"}, k = 1, mv = {1, 1, 16})
public final class PersistentHashMapBuilder<K, V> extends AbstractMutableMap<K, V> implements PersistentMap.Builder<K, V> {
    private PersistentHashMap<K, V> map;
    private int modCount;
    private TrieNode<K, V> node;
    private V operationResult;
    private MutabilityOwnership ownership;
    private int size;

    public PersistentHashMapBuilder(PersistentHashMap<K, V> map) {
        Intrinsics.checkParameterIsNotNull(map, "map");
        this.map = map;
        this.ownership = new MutabilityOwnership();
        this.node = this.map.getNode$kotlinx_collections_immutable();
        this.size = this.map.size();
    }

    /* JADX INFO: renamed from: getOwnership$kotlinx_collections_immutable, reason: from getter */
    public final MutabilityOwnership getOwnership() {
        return this.ownership;
    }

    public final TrieNode<K, V> getNode$kotlinx_collections_immutable() {
        return this.node;
    }

    public final void setOperationResult$kotlinx_collections_immutable(V v) {
        this.operationResult = v;
    }

    /* JADX INFO: renamed from: getModCount$kotlinx_collections_immutable, reason: from getter */
    public final int getModCount() {
        return this.modCount;
    }

    public final void setModCount$kotlinx_collections_immutable(int i) {
        this.modCount = i;
    }

    @Override // kotlin.collections.AbstractMutableMap
    public int getSize() {
        return this.size;
    }

    public void setSize(int i) {
        this.size = i;
        this.modCount++;
    }

    @Override // org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentMap.Builder
    public PersistentHashMap<K, V> build() {
        PersistentHashMap<K, V> persistentHashMap;
        if (this.node == this.map.getNode$kotlinx_collections_immutable()) {
            persistentHashMap = this.map;
        } else {
            this.ownership = new MutabilityOwnership();
            persistentHashMap = new PersistentHashMap<>(this.node, size());
        }
        this.map = persistentHashMap;
        return persistentHashMap;
    }

    @Override // kotlin.collections.AbstractMutableMap
    public Set<Map.Entry<K, V>> getEntries() {
        return new PersistentHashMapBuilderEntries(this);
    }

    @Override // kotlin.collections.AbstractMutableMap
    public Set<K> getKeys() {
        return new PersistentHashMapBuilderKeys(this);
    }

    @Override // kotlin.collections.AbstractMutableMap
    public Collection<V> getValues() {
        return new PersistentHashMapBuilderValues(this);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsKey(Object key) {
        return this.node.containsKey(key != null ? key.hashCode() : 0, key, 0);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V get(Object key) {
        return this.node.get(key != null ? key.hashCode() : 0, key, 0);
    }

    @Override // kotlin.collections.AbstractMutableMap, java.util.AbstractMap, java.util.Map
    public V put(K key, V value) {
        this.operationResult = null;
        this.node = this.node.mutablePut(key != null ? key.hashCode() : 0, key, value, 0, this);
        return this.operationResult;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V remove(Object key) {
        this.operationResult = null;
        this.node = this.node.mutableRemove(key != null ? key.hashCode() : 0, key, 0, this);
        return this.operationResult;
    }

    @Override // java.util.Map
    public final boolean remove(K key, V value) {
        int size = size();
        this.node = this.node.mutableRemove(key != null ? key.hashCode() : 0, key, value, 0, this);
        return size != size();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        TrieNode<K, V> eMPTY$kotlinx_collections_immutable = TrieNode.INSTANCE.getEMPTY$kotlinx_collections_immutable();
        if (eMPTY$kotlinx_collections_immutable == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlinx.collections.immutable.implementations.immutableMap.TrieNode<K, V>");
        }
        this.node = eMPTY$kotlinx_collections_immutable;
        setSize(0);
    }
}
