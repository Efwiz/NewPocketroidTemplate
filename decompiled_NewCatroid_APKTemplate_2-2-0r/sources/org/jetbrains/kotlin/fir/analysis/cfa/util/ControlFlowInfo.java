package org.jetbrains.kotlin.fir.analysis.cfa.util;

import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.firebase.crashlytics.internal.metadata.UserMetadata;
import com.reandroid.arsc.chunk.TypeBlock;
import com.reandroid.arsc.value.CompoundEntry;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.fir.analysis.cfa.util.ControlFlowInfo;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.kotlinx.collections.immutable.ImmutableCollection;
import org.jetbrains.kotlin.kotlinx.collections.immutable.ImmutableSet;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentMap;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX INFO: compiled from: ControlFlowInfo.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010&\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0011\n\u0002\u0010$\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\b&\u0018\u0000*\u001a\b\u0000\u0010\u0001*\u0014\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0000*\b\b\u0001\u0010\u0002*\u00020\u0004*\b\b\u0002\u0010\u0003*\u00020\u00042\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0005B\u001b\b\u0004\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u0005¢\u0006\u0002\u0010\u0007J\u0015\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u001eH\u0096\u0001J\u0015\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u0005H\u0096\u0001J\u0016\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00028\u0001H\u0096\u0001¢\u0006\u0002\u0010#J\u0016\u0010$\u001a\u00020!2\u0006\u0010%\u001a\u00028\u0002H\u0096\u0001¢\u0006\u0002\u0010#J\u0013\u0010&\u001a\u00020!2\b\u0010'\u001a\u0004\u0018\u00010\u0004H\u0096\u0002J\u0018\u0010(\u001a\u0004\u0018\u00018\u00022\u0006\u0010\"\u001a\u00028\u0001H\u0096\u0003¢\u0006\u0002\u0010)J\b\u0010*\u001a\u00020\u0016H\u0016J\t\u0010+\u001a\u00020!H\u0096\u0001J\u0015\u0010,\u001a\u00028\u00002\u0006\u0010'\u001a\u00028\u0000H&¢\u0006\u0002\u0010-J\u0015\u0010.\u001a\u00028\u00002\u0006\u0010'\u001a\u00028\u0000H&¢\u0006\u0002\u0010-J\u001d\u0010/\u001a\u00028\u00002\u0006\u0010\"\u001a\u00028\u00012\u0006\u0010%\u001a\u00028\u0002H\u0016¢\u0006\u0002\u00100J+\u00101\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u00052\u0014\u00102\u001a\u0010\u0012\u0006\b\u0001\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u000203H\u0096\u0001J\u0015\u00104\u001a\u00028\u00002\u0006\u0010\"\u001a\u00028\u0001H\u0016¢\u0006\u0002\u00105J*\u00104\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u00052\u0006\u0010\"\u001a\u00028\u00012\u0006\u0010%\u001a\u00028\u0002H\u0096\u0001¢\u0006\u0002\u00106J\b\u00107\u001a\u000208H\u0016R*\u0010\b\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u0005\u0012\u0004\u0012\u00028\u00000\tX¤\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR$\u0010\f\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u000e0\rX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0018\u0010\u0011\u001a\b\u0012\u0004\u0012\u00028\u00010\rX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0010R \u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020\u0005X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0012\u0010\u0015\u001a\u00020\u0016X\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0018\u0010\u0019\u001a\b\u0012\u0004\u0012\u00028\u00020\u001aX\u0096\u0005¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001c¨\u00069"}, d2 = {"Lorg/jetbrains/kotlin/fir/analysis/cfa/util/ControlFlowInfo;", ExifInterface.LATITUDE_SOUTH, "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentMap;", "map", "(Lkotlinx/collections/immutable/PersistentMap;)V", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lkotlin/Function1;", "getConstructor", "()Lkotlin/jvm/functions/Function1;", TypeBlock.NAME_entries, "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/ImmutableSet;", "", "getEntries", "()Lkotlinx/collections/immutable/ImmutableSet;", UserMetadata.KEYDATA_FILENAME, "getKeys", "getMap", "()Lkotlinx/collections/immutable/PersistentMap;", "size", "", "getSize", "()I", CompoundEntry.NAME_values, "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/ImmutableCollection;", "getValues", "()Lkotlinx/collections/immutable/ImmutableCollection;", "builder", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentMap$Builder;", LineReader.CLEAR, "containsKey", "", ASTExpr.DEFAULT_MAP_KEY_NAME, "(Ljava/lang/Object;)Z", "containsValue", "value", Namer.EQUALS_METHOD_NAME, "other", "get", "(Ljava/lang/Object;)Ljava/lang/Object;", "hashCode", "isEmpty", "merge", "(Lorg/jetbrains/kotlin/fir/analysis/cfa/util/ControlFlowInfo;)Lorg/jetbrains/kotlin/fir/analysis/cfa/util/ControlFlowInfo;", "plus", "put", "(Ljava/lang/Object;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/analysis/cfa/util/ControlFlowInfo;", "putAll", "m", "", ProductAction.ACTION_REMOVE, "(Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/analysis/cfa/util/ControlFlowInfo;", "(Ljava/lang/Object;Ljava/lang/Object;)Lkotlinx/collections/immutable/PersistentMap;", "toString", "", "checkers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ControlFlowInfo<S extends ControlFlowInfo<S, K, V>, K, V> implements PersistentMap<K, V> {
    private final PersistentMap<K, V> map;

    @Override // org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentMap
    public PersistentMap.Builder<K, V> builder() {
        return this.map.builder();
    }

    @Override // org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentMap, java.util.Map
    public PersistentMap<K, V> clear() {
        return this.map.clear();
    }

    @Override // java.util.Map
    public void clear() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public V compute(K k, BiFunction<? super K, ? super V, ? extends V> biFunction) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public V computeIfAbsent(K k, Function<? super K, ? extends V> function) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public V computeIfPresent(K k, BiFunction<? super K, ? super V, ? extends V> biFunction) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public boolean containsKey(K key) {
        if (key == null) {
            return false;
        }
        return this.map.containsKey(key);
    }

    @Override // java.util.Map
    public boolean containsValue(V value) {
        if (value == null) {
            return false;
        }
        return this.map.containsValue(value);
    }

    @Override // java.util.Map
    public V get(K key) {
        if (key == null) {
            return null;
        }
        return (V) this.map.get(key);
    }

    protected abstract Function1<PersistentMap<K, ? extends V>, S> getConstructor();

    public ImmutableSet<Map.Entry<K, V>> getEntries() {
        return (ImmutableSet) this.map.entrySet();
    }

    public ImmutableSet<K> getKeys() {
        return (ImmutableSet) this.map.keySet();
    }

    public int getSize() {
        return this.map.size();
    }

    public ImmutableCollection<V> getValues() {
        return (ImmutableCollection) this.map.values();
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override // java.util.Map
    public V merge(K k, V v, BiFunction<? super V, ? super V, ? extends V> biFunction) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public abstract S merge(S other);

    public abstract S plus(S other);

    @Override // java.util.Map
    public V put(K k, V v) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentMap, java.util.Map
    public PersistentMap<K, V> putAll(Map<? extends K, ? extends V> m) {
        Intrinsics.checkNotNullParameter(m, "m");
        return this.map.putAll((Map) m);
    }

    @Override // java.util.Map
    public void putAll(Map<? extends K, ? extends V> map) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public V putIfAbsent(K k, V v) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public V remove(Object obj) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentMap, java.util.Map
    public PersistentMap<K, V> remove(K key, V value) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        return this.map.remove((Object) key, (Object) value);
    }

    @Override // java.util.Map
    public boolean remove(Object obj, Object obj2) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public V replace(K k, V v) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public boolean replace(K k, V v, V v2) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    @Override // java.util.Map
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> biFunction) {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected ControlFlowInfo(PersistentMap<K, ? extends V> map) {
        Intrinsics.checkNotNullParameter(map, "map");
        this.map = map;
    }

    @Override // java.util.Map
    public final /* bridge */ ImmutableSet<Map.Entry<K, V>> entrySet() {
        return getEntries();
    }

    @Override // java.util.Map
    public final /* bridge */ ImmutableSet<K> keySet() {
        return getKeys();
    }

    @Override // java.util.Map
    public final /* bridge */ int size() {
        return getSize();
    }

    @Override // java.util.Map
    public final /* bridge */ ImmutableCollection<V> values() {
        return getValues();
    }

    protected final PersistentMap<K, V> getMap() {
        return this.map;
    }

    @Override // java.util.Map
    public boolean equals(Object other) {
        PersistentMap<K, V> persistentMap = this.map;
        ControlFlowInfo controlFlowInfo = other instanceof ControlFlowInfo ? (ControlFlowInfo) other : null;
        return Intrinsics.areEqual(persistentMap, controlFlowInfo != null ? controlFlowInfo.map : null);
    }

    @Override // java.util.Map
    public int hashCode() {
        return this.map.hashCode();
    }

    public String toString() {
        return this.map.toString();
    }

    @Override // org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentMap, java.util.Map
    public S put(K key, V value) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        return getConstructor().invoke(this.map.put((Object) key, (Object) value));
    }

    @Override // org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentMap, java.util.Map
    public S remove(K key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return getConstructor().invoke(this.map.remove((Object) key));
    }
}
