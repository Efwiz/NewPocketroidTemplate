package org.jetbrains.kotlin.kotlinx.collections.immutable.implementations.immutableMap;

import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.firebase.crashlytics.internal.metadata.UserMetadata;
import com.reandroid.arsc.chunk.TypeBlock;
import com.reandroid.arsc.value.CompoundEntry;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.AbstractMap;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.kotlinx.collections.immutable.ImmutableCollection;
import org.jetbrains.kotlin.kotlinx.collections.immutable.ImmutableSet;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentMap;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX INFO: compiled from: PersistentHashMap.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010&\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\"\n\u0002\b\u0005\n\u0002\u0010$\n\u0002\b\u0004\b\u0000\u0018\u0000 ,*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u00032\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0004:\u0001,B!\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0014\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u001aH\u0016J\u0014\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0004H\u0016J\u0015\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u001fJ\u001a\u0010 \u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\f0\u000bH\u0002J\u0018\u0010!\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u001e\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\"J\u001a\u0010\r\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\f0#H\u0001J)\u0010$\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00002\u0006\u0010\u001e\u001a\u00028\u00002\u0006\u0010%\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010&J*\u0010'\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00042\u0014\u0010(\u001a\u0010\u0012\u0006\b\u0001\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010)H\u0016J!\u0010*\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00002\u0006\u0010\u001e\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010+J)\u0010*\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00002\u0006\u0010\u001e\u001a\u00028\u00002\u0006\u0010%\u001a\u00028\u0001H\u0016¢\u0006\u0002\u0010&R&\u0010\n\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\f0\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u000eR \u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0006X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0015\u001a\b\u0012\u0004\u0012\u00028\u00010\u00168VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018¨\u0006-"}, d2 = {"Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/immutableMap/PersistentHashMap;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Lkotlin/collections/AbstractMap;", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentMap;", "node", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/immutableMap/TrieNode;", "size", "", "(Lkotlinx/collections/immutable/implementations/immutableMap/TrieNode;I)V", TypeBlock.NAME_entries, "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/ImmutableSet;", "", "getEntries", "()Lkotlinx/collections/immutable/ImmutableSet;", UserMetadata.KEYDATA_FILENAME, "getKeys", "getNode$kotlinx_collections_immutable", "()Lkotlinx/collections/immutable/implementations/immutableMap/TrieNode;", "getSize", "()I", CompoundEntry.NAME_values, "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/ImmutableCollection;", "getValues", "()Lkotlinx/collections/immutable/ImmutableCollection;", "builder", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/immutableMap/PersistentHashMapBuilder;", LineReader.CLEAR, "containsKey", "", ASTExpr.DEFAULT_MAP_KEY_NAME, "(Ljava/lang/Object;)Z", "createEntries", "get", "(Ljava/lang/Object;)Ljava/lang/Object;", "", "put", "value", "(Ljava/lang/Object;Ljava/lang/Object;)Lkotlinx/collections/immutable/implementations/immutableMap/PersistentHashMap;", "putAll", "m", "", ProductAction.ACTION_REMOVE, "(Ljava/lang/Object;)Lkotlinx/collections/immutable/implementations/immutableMap/PersistentHashMap;", "Companion", "kotlinx-collections-immutable"}, k = 1, mv = {1, 1, 16})
public final class PersistentHashMap<K, V> extends AbstractMap<K, V> implements PersistentMap<K, V> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final PersistentHashMap EMPTY = new PersistentHashMap(TrieNode.INSTANCE.getEMPTY$kotlinx_collections_immutable(), 0);
    private final TrieNode<K, V> node;
    private final int size;

    public final TrieNode<K, V> getNode$kotlinx_collections_immutable() {
        return this.node;
    }

    public PersistentHashMap(TrieNode<K, V> node, int i) {
        Intrinsics.checkParameterIsNotNull(node, "node");
        this.node = node;
        this.size = i;
    }

    @Override // kotlin.collections.AbstractMap
    public int getSize() {
        return this.size;
    }

    @Override // kotlin.collections.AbstractMap
    public ImmutableSet<K> getKeys() {
        return new PersistentHashMapKeys(this);
    }

    @Override // kotlin.collections.AbstractMap
    public ImmutableCollection<V> getValues() {
        return new PersistentHashMapValues(this);
    }

    private final ImmutableSet<Map.Entry<K, V>> createEntries() {
        return new PersistentHashMapEntries(this);
    }

    @Override // kotlin.collections.AbstractMap
    public final Set<Map.Entry<K, V>> getEntries() {
        return createEntries();
    }

    @Override // kotlin.collections.AbstractMap, java.util.Map
    public boolean containsKey(Object key) {
        return this.node.containsKey(key != null ? key.hashCode() : 0, key, 0);
    }

    @Override // kotlin.collections.AbstractMap, java.util.Map
    public V get(Object key) {
        return this.node.get(key != null ? key.hashCode() : 0, key, 0);
    }

    @Override // kotlin.collections.AbstractMap, java.util.Map
    public PersistentHashMap<K, V> put(K k, V v) {
        TrieNode.ModificationResult<K, V> modificationResultPut = this.node.put(k != null ? k.hashCode() : 0, k, v, 0);
        return modificationResultPut != null ? new PersistentHashMap<>(modificationResultPut.getNode(), size() + modificationResultPut.getSizeDelta()) : this;
    }

    @Override // kotlin.collections.AbstractMap, java.util.Map
    public PersistentHashMap<K, V> remove(K k) {
        TrieNode<K, V> trieNodeRemove = this.node.remove(k != null ? k.hashCode() : 0, k, 0);
        return this.node == trieNodeRemove ? this : new PersistentHashMap<>(trieNodeRemove, size() - 1);
    }

    @Override // java.util.Map, org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentMap
    public PersistentHashMap<K, V> remove(K k, V v) {
        TrieNode<K, V> trieNodeRemove = this.node.remove(k != null ? k.hashCode() : 0, k, v, 0);
        return this.node == trieNodeRemove ? this : new PersistentHashMap<>(trieNodeRemove, size() - 1);
    }

    @Override // java.util.Map, org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentMap
    public PersistentMap<K, V> putAll(Map<? extends K, ? extends V> m) {
        Intrinsics.checkParameterIsNotNull(m, "m");
        PersistentMap.Builder<K, V> builder = builder();
        builder.putAll(m);
        return builder.build();
    }

    @Override // java.util.Map, org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentMap
    public PersistentMap<K, V> clear() {
        return INSTANCE.emptyOf$kotlinx_collections_immutable();
    }

    @Override // org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentMap
    public PersistentHashMapBuilder<K, V> builder() {
        return new PersistentHashMapBuilder<>(this);
    }

    /* JADX INFO: compiled from: PersistentHashMap.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0005\b\u0080\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J%\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u0002H\u0007\u0012\u0004\u0012\u0002H\b0\u0004\"\u0004\b\u0002\u0010\u0007\"\u0004\b\u0003\u0010\bH\u0000¢\u0006\u0002\b\tR\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/immutableMap/PersistentHashMap$Companion;", "", "()V", "EMPTY", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/implementations/immutableMap/PersistentHashMap;", "", "emptyOf", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "emptyOf$kotlinx_collections_immutable", "kotlinx-collections-immutable"}, k = 1, mv = {1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final <K, V> PersistentHashMap<K, V> emptyOf$kotlinx_collections_immutable() {
            PersistentHashMap<K, V> persistentHashMap = PersistentHashMap.EMPTY;
            if (persistentHashMap != null) {
                return persistentHashMap;
            }
            throw new TypeCastException("null cannot be cast to non-null type kotlinx.collections.immutable.implementations.immutableMap.PersistentHashMap<K, V>");
        }
    }
}
