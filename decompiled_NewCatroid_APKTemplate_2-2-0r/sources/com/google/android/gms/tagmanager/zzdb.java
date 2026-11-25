package com.google.android.gms.tagmanager;

import android.util.LruCache;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzdb<K, V> {
    private final LruCache<K, V> zza;

    zzdb(int i, zzr<K, V> zzrVar) {
        this.zza = new zzda(this, 1048576, zzrVar);
    }

    public final V zza(K k) {
        return this.zza.get(k);
    }

    public final void zzb(K k, V v) {
        this.zza.put(k, v);
    }
}
