package com.google.android.gms.internal.mlkit_vision_pose_common;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.RandomAccess;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
abstract class zzl<K, V> extends zzx<K, V> implements Serializable {
    private transient Map<K, Collection<V>> zza;
    private transient int zzb;

    protected zzl(Map<K, Collection<V>> map) {
        if (!map.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.zza = map;
    }

    abstract Collection<V> zzb();

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzx, com.google.android.gms.internal.mlkit_vision_pose_common.zzbc
    public boolean zza(@NullableDecl K k, @NullableDecl V v) {
        Collection<V> collection = this.zza.get(k);
        if (collection == null) {
            Collection<V> collectionZzb = zzb();
            if (collectionZzb.add(v)) {
                this.zzb++;
                this.zza.put(k, collectionZzb);
                return true;
            }
            throw new AssertionError("New Collection violated the Collection spec");
        }
        if (!collection.add(v)) {
            return false;
        }
        this.zzb++;
        return true;
    }

    public void zzc() {
        Iterator<Collection<V>> it = this.zza.values().iterator();
        while (it.hasNext()) {
            it.next().clear();
        }
        this.zza.clear();
        this.zzb = 0;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzbc
    public Collection<V> zzb(@NullableDecl K k) {
        Collection<V> collectionZzb = this.zza.get(k);
        if (collectionZzb == null) {
            collectionZzb = zzb();
        }
        return zza((Object) k, (Collection) collectionZzb);
    }

    Collection<V> zza(@NullableDecl K k, Collection<V> collection) {
        return new zzq(this, k, collection, null);
    }

    final List<V> zza(@NullableDecl K k, List<V> list, @NullableDecl zzq zzqVar) {
        if (list instanceof RandomAccess) {
            return new zzr(this, k, list, zzqVar);
        }
        return new zzs(this, k, list, zzqVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <E> Iterator<E> zzb(Collection<E> collection) {
        if (collection instanceof List) {
            return ((List) collection).listIterator();
        }
        return collection.iterator();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzx
    final Set<K> zzd() {
        return new zzp(this, this.zza);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzc(Object obj) {
        Collection collection = (Collection) zzax.zzc(this.zza, obj);
        if (collection != null) {
            int size = collection.size();
            collection.clear();
            this.zzb -= size;
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_pose_common.zzx
    final Map<K, Collection<V>> zze() {
        return new zzk(this, this.zza);
    }

    static /* synthetic */ int zzb(zzl zzlVar) {
        int i = zzlVar.zzb;
        zzlVar.zzb = i - 1;
        return i;
    }

    static /* synthetic */ int zzc(zzl zzlVar) {
        int i = zzlVar.zzb;
        zzlVar.zzb = i + 1;
        return i;
    }

    static /* synthetic */ int zza(zzl zzlVar, int i) {
        int i2 = zzlVar.zzb + i;
        zzlVar.zzb = i2;
        return i2;
    }

    static /* synthetic */ int zzb(zzl zzlVar, int i) {
        int i2 = zzlVar.zzb - i;
        zzlVar.zzb = i2;
        return i2;
    }
}
