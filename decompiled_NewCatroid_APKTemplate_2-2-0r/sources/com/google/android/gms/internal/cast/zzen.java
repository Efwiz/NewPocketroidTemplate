package com.google.android.gms.internal.cast;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast-framework@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
public abstract class zzen {
    private static final ThreadLocal zza = new zzeh();

    public static zzen zzb() {
        return (zzen) zza.get();
    }

    public abstract void zza(zzek zzekVar);
}
