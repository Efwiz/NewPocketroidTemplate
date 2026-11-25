package org.jetbrains.kotlin.fir.util;

import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.SetsKt;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.kotlinx.collections.immutable.ExtensionsKt;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentMap;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentSet;

/* JADX INFO: compiled from: PersistentMultimap.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\b\u0007\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u00020\u0003B\u0007\b\u0016¢\u0006\u0002\u0010\u0004B!\b\u0002\u0012\u0018\u0010\u0005\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\u00070\u0006¢\u0006\u0002\u0010\bJ\u001c\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00010\n2\u0006\u0010\u000b\u001a\u00028\u0000H\u0086\u0002¢\u0006\u0002\u0010\fJ'\u0010\r\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00002\u0006\u0010\u000b\u001a\u00028\u00002\u0006\u0010\u000e\u001a\u00028\u0001¢\u0006\u0002\u0010\u000fJ'\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00002\u0006\u0010\u000b\u001a\u00028\u00002\u0006\u0010\u000e\u001a\u00028\u0001¢\u0006\u0002\u0010\u000fR \u0010\u0005\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/fir/util/PersistentSetMultimap;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "", "()V", "map", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentMap;", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentSet;", "(Lkotlinx/collections/immutable/PersistentMap;)V", "get", "", ASTExpr.DEFAULT_MAP_KEY_NAME, "(Ljava/lang/Object;)Ljava/util/Set;", "put", "value", "(Ljava/lang/Object;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/util/PersistentSetMultimap;", ProductAction.ACTION_REMOVE, "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PersistentSetMultimap<K, V> {
    private final PersistentMap<K, PersistentSet<V>> map;

    /* JADX WARN: Multi-variable type inference failed */
    private PersistentSetMultimap(PersistentMap<K, ? extends PersistentSet<? extends V>> persistentMap) {
        this.map = persistentMap;
    }

    public PersistentSetMultimap() {
        this(ExtensionsKt.persistentMapOf(new Pair[0]));
    }

    public final PersistentSetMultimap<K, V> put(K key, V value) {
        PersistentSet<V> persistentSetPersistentSetOf = (PersistentSet) this.map.get(key);
        if (persistentSetPersistentSetOf == null) {
            persistentSetPersistentSetOf = ExtensionsKt.persistentSetOf();
        }
        PersistentSet<V> persistentSetAdd = persistentSetPersistentSetOf.add(value);
        return persistentSetAdd == persistentSetPersistentSetOf ? this : new PersistentSetMultimap<>(this.map.put((Object) key, persistentSetAdd));
    }

    public final PersistentSetMultimap<K, V> remove(K key, V value) {
        PersistentSet<V> persistentSetRemove;
        PersistentMap<K, PersistentSet<V>> persistentMapPut;
        PersistentSet<V> persistentSet = (PersistentSet) this.map.get(key);
        if (persistentSet == null || persistentSet == (persistentSetRemove = persistentSet.remove(value))) {
            return this;
        }
        if (persistentSetRemove.isEmpty()) {
            persistentMapPut = this.map.remove((Object) key);
        } else {
            persistentMapPut = this.map.put((Object) key, persistentSetRemove);
        }
        return new PersistentSetMultimap<>(persistentMapPut);
    }

    public final Set<V> get(K key) {
        PersistentSet persistentSet = (PersistentSet) this.map.get(key);
        return persistentSet != null ? persistentSet : SetsKt.emptySet();
    }
}
