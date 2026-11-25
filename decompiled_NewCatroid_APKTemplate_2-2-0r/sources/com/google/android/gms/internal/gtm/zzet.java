package com.google.android.gms.internal.gtm;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzet<V> {
    private final zzes<V> zza;
    private final V zzb;
    private final V zzc;
    private final Object zzd = new Object();

    private zzet(V v, V v2, zzes<V> zzesVar) {
        this.zzb = v;
        this.zzc = v2;
        this.zza = zzesVar;
    }

    static <T> zzet<T> zza(T t, T t2, zzes<T> zzesVar) {
        return new zzet<>(t, t2, zzesVar);
    }

    public final V zzb() {
        synchronized (this.zzd) {
        }
        return this.zzb;
    }
}
