package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzdh extends zzans<zzdh, zzdf> implements zzapg {
    private static final zzdh zzb;
    private int zze;
    private int zzf;
    private String zzg = "";

    static {
        zzdh zzdhVar = new zzdh();
        zzb = zzdhVar;
        zzans.zzM(zzdh.class, zzdhVar);
    }

    private zzdh() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဈ\u0001", new Object[]{"zze", "zzf", zzdg.zza, "zzg"});
        }
        if (i2 == 3) {
            return new zzdh();
        }
        zzde zzdeVar = null;
        if (i2 == 4) {
            return new zzdf(zzdeVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
