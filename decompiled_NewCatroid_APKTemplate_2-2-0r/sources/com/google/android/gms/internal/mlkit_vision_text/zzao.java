package com.google.android.gms.internal.mlkit_vision_text;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.RandomAccess;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
abstract class zzao<K, V> extends zzaq<K, V> implements Serializable {
    private transient Map<K, Collection<V>> zza;
    private transient int zzb;

    protected zzao(Map<K, Collection<V>> map) {
        if (!map.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.zza = map;
    }

    static /* synthetic */ int zzk(zzao zzaoVar) {
        int i = zzaoVar.zzb;
        zzaoVar.zzb = i - 1;
        return i;
    }

    static /* synthetic */ int zzl(zzao zzaoVar) {
        int i = zzaoVar.zzb;
        zzaoVar.zzb = i + 1;
        return i;
    }

    static /* synthetic */ int zzm(zzao zzaoVar, int i) {
        int i2 = zzaoVar.zzb + i;
        zzaoVar.zzb = i2;
        return i2;
    }

    static /* synthetic */ int zzn(zzao zzaoVar, int i) {
        int i2 = zzaoVar.zzb - i;
        zzaoVar.zzb = i2;
        return i2;
    }

    static /* synthetic */ void zzo(zzao zzaoVar, Object obj) {
        Collection<V> collectionRemove;
        Map<K, Collection<V>> map = zzaoVar.zza;
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
            zzaoVar.zzb -= size;
        }
    }

    Collection<V> zza(@NullableDecl K k, Collection<V> collection) {
        throw null;
    }

    abstract Collection<V> zzc();

    @Override // com.google.android.gms.internal.mlkit_vision_text.zzaq, com.google.android.gms.internal.mlkit_vision_text.zzcc
    public final boolean zzd(@NullableDecl K k, @NullableDecl V v) {
        Collection<V> collection = this.zza.get(k);
        if (collection != null) {
            if (!collection.add(v)) {
                return false;
            }
            this.zzb++;
            return true;
        }
        Collection<V> collectionZzc = zzc();
        if (!collectionZzc.add(v)) {
            throw new AssertionError("New Collection violated the Collection spec");
        }
        this.zzb++;
        this.zza.put(k, collectionZzc);
        return true;
    }

    public final void zze() {
        Iterator<Collection<V>> it = this.zza.values().iterator();
        while (it.hasNext()) {
            it.next().clear();
        }
        this.zza.clear();
        this.zzb = 0;
    }

    public final Collection<V> zzf(@NullableDecl K k) {
        Collection<V> collectionZzc = this.zza.get(k);
        if (collectionZzc == null) {
            collectionZzc = zzc();
        }
        return zza(k, collectionZzc);
    }

    final List<V> zzg(@NullableDecl K k, List<V> list, @NullableDecl zzal zzalVar) {
        return list instanceof RandomAccess ? new zzaj(this, k, list, zzalVar) : new zzan(this, k, list, zzalVar);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_text.zzaq
    final Set<K> zzh() {
        return new zzai(this, this.zza);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_text.zzaq
    final Map<K, Collection<V>> zzi() {
        return new zzag(this, this.zza);
    }
}
