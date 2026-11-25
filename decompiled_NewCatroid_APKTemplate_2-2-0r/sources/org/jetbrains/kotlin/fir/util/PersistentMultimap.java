package org.jetbrains.kotlin.fir.util;

import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.firebase.crashlytics.internal.metadata.UserMetadata;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.kotlinx.collections.immutable.ExtensionsKt;
import org.jetbrains.kotlin.kotlinx.collections.immutable.ImmutableSet;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentList;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentMap;

/* JADX INFO: compiled from: PersistentMultimap.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0007\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u00020\u0003B\u0007\b\u0016¢\u0006\u0002\u0010\u0004B!\b\u0002\u0012\u0018\u0010\u0005\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\u00070\u0006¢\u0006\u0002\u0010\bJ\u001c\u0010\r\u001a\b\u0012\u0004\u0012\u00028\u00010\u000e2\u0006\u0010\u000f\u001a\u00028\u0000H\u0086\u0002¢\u0006\u0002\u0010\u0010J'\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00002\u0006\u0010\u000f\u001a\u00028\u00002\u0006\u0010\u0012\u001a\u00028\u0001¢\u0006\u0002\u0010\u0013J'\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u00002\u0006\u0010\u000f\u001a\u00028\u00002\u0006\u0010\u0012\u001a\u00028\u0001¢\u0006\u0002\u0010\u0013R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\n8F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR \u0010\u0005\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\u00070\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/fir/util/PersistentMultimap;", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "", "()V", "map", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentMap;", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentList;", "(Lkotlinx/collections/immutable/PersistentMap;)V", UserMetadata.KEYDATA_FILENAME, "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/ImmutableSet;", "getKeys", "()Lkotlinx/collections/immutable/ImmutableSet;", "get", "", ASTExpr.DEFAULT_MAP_KEY_NAME, "(Ljava/lang/Object;)Ljava/util/List;", "put", "value", "(Ljava/lang/Object;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/util/PersistentMultimap;", ProductAction.ACTION_REMOVE, "cones"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PersistentMultimap<K, V> {
    private final PersistentMap<K, PersistentList<V>> map;

    /* JADX WARN: Multi-variable type inference failed */
    private PersistentMultimap(PersistentMap<K, ? extends PersistentList<? extends V>> persistentMap) {
        this.map = persistentMap;
    }

    public PersistentMultimap() {
        this(ExtensionsKt.persistentMapOf(new Pair[0]));
    }

    public final PersistentMultimap<K, V> put(K key, V value) {
        PersistentList<V> persistentListPersistentListOf = (PersistentList) this.map.get(key);
        if (persistentListPersistentListOf == null) {
            persistentListPersistentListOf = ExtensionsKt.persistentListOf();
        }
        PersistentList<V> persistentListAdd = persistentListPersistentListOf.add(value);
        return persistentListAdd == persistentListPersistentListOf ? this : new PersistentMultimap<>(this.map.put((Object) key, persistentListAdd));
    }

    public final PersistentMultimap<K, V> remove(K key, V value) {
        PersistentList<V> persistentListRemove;
        PersistentMap<K, PersistentList<V>> persistentMapPut;
        PersistentList<V> persistentList = (PersistentList) this.map.get(key);
        if (persistentList == null || persistentList == (persistentListRemove = persistentList.remove(value))) {
            return this;
        }
        if (persistentListRemove.isEmpty()) {
            persistentMapPut = this.map.remove((Object) key);
        } else {
            persistentMapPut = this.map.put((Object) key, persistentListRemove);
        }
        return new PersistentMultimap<>(persistentMapPut);
    }

    public final List<V> get(K key) {
        PersistentList persistentList = (PersistentList) this.map.get(key);
        return persistentList != null ? persistentList : CollectionsKt.emptyList();
    }

    public final ImmutableSet<K> getKeys() {
        return (ImmutableSet) this.map.keySet();
    }
}
