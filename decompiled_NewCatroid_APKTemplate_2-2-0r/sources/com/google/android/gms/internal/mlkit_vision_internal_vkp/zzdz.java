package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzdz extends zzans<zzdz, zzdy> implements zzapg {
    private static final zzdz zzb;
    private int zze;
    private int zzf = 3;
    private float zzg = 100000.0f;
    private float zzh;

    static {
        zzdz zzdzVar = new zzdz();
        zzb = zzdzVar;
        zzans.zzM(zzdz.class, zzdzVar);
    }

    private zzdz() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဌ\u0000\u0002ခ\u0001\u0003ခ\u0002", new Object[]{"zze", "zzf", zzdx.zza, "zzg", "zzh"});
        }
        if (i2 == 3) {
            return new zzdz();
        }
        zzdw zzdwVar = null;
        if (i2 == 4) {
            return new zzdy(zzdwVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
