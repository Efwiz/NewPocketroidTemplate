package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.RandomAccess;
import java.util.Set;
import javax.annotation.CheckForNull;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
abstract class zzv<K, V> extends zzx<K, V> implements Serializable {
    private transient Map<K, Collection<V>> zza;
    private transient int zzb;

    protected zzv(Map<K, Collection<V>> map) {
        if (!map.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.zza = map;
    }

    abstract Collection<V> zza();

    Collection<V> zzb(K k, Collection<V> collection) {
        throw null;
    }

    public final Collection<V> zze(K k) {
        Collection<V> collectionZza = this.zza.get(k);
        if (collectionZza == null) {
            collectionZza = zza();
        }
        return zzb(k, collectionZza);
    }

    final List<V> zzf(K k, List<V> list, @CheckForNull zzs zzsVar) {
        return list instanceof RandomAccess ? new zzq(this, k, list, zzsVar) : new zzu(this, k, list, zzsVar);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzx
    final Map<K, Collection<V>> zzh() {
        return new zzn(this, this.zza);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzx
    final Set<K> zzi() {
        return new zzp(this, this.zza);
    }

    public final void zzl() {
        Iterator<Collection<V>> it = this.zza.values().iterator();
        while (it.hasNext()) {
            it.next().clear();
        }
        this.zza.clear();
        this.zzb = 0;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzx, com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzbh
    public final boolean zzm(K k, V v) {
        Collection<V> collection = this.zza.get(k);
        if (collection != null) {
            if (!collection.add(v)) {
                return false;
            }
            this.zzb++;
            return true;
        }
        Collection<V> collectionZza = zza();
        if (!collectionZza.add(v)) {
            throw new AssertionError("New Collection violated the Collection spec");
        }
        this.zzb++;
        this.zza.put(k, collectionZza);
        return true;
    }

    static /* bridge */ /* synthetic */ void zzk(zzv zzvVar, Object obj) {
        Collection<V> collectionRemove;
        Map<K, Collection<V>> map = zzvVar.zza;
        map.getClass();
        try {
            collectionRemove = map.remove(obj);
        } catch (ClassCastException | NullPointerException unused) {
            collectionRemove = null;
        }
        Collection<V> collection = collectionRemove;
        if (collection != null) {
            int size = collection.size();
            collection.clear();
            zzvVar.zzb -= size;
        }
    }
}
