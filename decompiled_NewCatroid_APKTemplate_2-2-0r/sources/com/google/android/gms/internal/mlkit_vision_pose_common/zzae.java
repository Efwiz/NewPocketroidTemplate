package com.google.android.gms.internal.mlkit_vision_pose_common;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: Add missing generic type declarations: [V, K] */
/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzae<K, V> extends AbstractSet<Map.Entry<K, V>> {
    private final /* synthetic */ zzaa zza;

    zzae(zzaa zzaaVar) {
        this.zza = zzaaVar;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.zza.size();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final void clear() {
        this.zza.clear();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator<Map.Entry<K, V>> iterator() {
        return this.zza.zzf();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(@NullableDecl Object obj) {
        Map<K, V> mapZzb = this.zza.zzb();
        if (mapZzb != null) {
            return mapZzb.entrySet().contains(obj);
        }
        if (obj instanceof Map.Entry) {
            Map.Entry entry = (Map.Entry) obj;
            int iZza = this.zza.zza(entry.getKey());
            if (iZza != -1 && zze.zza(this.zza.zzc[iZza], entry.getValue())) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean remove(@NullableDecl Object obj) {
        Map<K, V> mapZzb = this.zza.zzb();
        if (mapZzb != null) {
            return mapZzb.entrySet().remove(obj);
        }
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        if (this.zza.zza()) {
            return false;
        }
        int iZzi = this.zza.zzi();
        int iZza = zzal.zza(entry.getKey(), entry.getValue(), iZzi, this.zza.zze, this.zza.zza, this.zza.zzb, this.zza.zzc);
        if (iZza == -1) {
            return false;
        }
        this.zza.zza(iZza, iZzi);
        zzaa.zzd(this.zza);
        this.zza.zzc();
        return true;
    }
}
