package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzhf extends zzans<zzhf, zzhe> implements zzapg {
    private static final zzhf zzb;
    private int zze;
    private Object zzg;
    private float zzh;
    private int zzf = 0;
    private String zzi = "";

    static {
        zzhf zzhfVar = new zzhf();
        zzb = zzhfVar;
        zzans.zzM(zzhf.class, zzhfVar);
    }

    private zzhf() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0004\u0001\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001ျ\u0000\u0002ခ\u0002\u0003ျ\u0000\u0004ဈ\u0003", new Object[]{"zzg", "zzf", "zze", "zzh", "zzi"});
        }
        if (i2 == 3) {
            return new zzhf();
        }
        zzhd zzhdVar = null;
        if (i2 == 4) {
            return new zzhe(zzhdVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
