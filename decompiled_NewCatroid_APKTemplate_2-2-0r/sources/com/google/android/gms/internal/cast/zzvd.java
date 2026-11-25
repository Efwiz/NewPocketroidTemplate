package com.google.android.gms.internal.cast;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzvd {
    private static final zzvc zza;
    private static final zzvc zzb;

    static {
        zzvc zzvcVar;
        try {
            zzvcVar = (zzvc) Class.forName("com.google.protobuf.NewInstanceSchemaFull").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception unused) {
            zzvcVar = null;
        }
        zza = zzvcVar;
        zzb = new zzvc();
    }

    static zzvc zza() {
        return zza;
    }

    static zzvc zzb() {
        return zzb;
    }
}
