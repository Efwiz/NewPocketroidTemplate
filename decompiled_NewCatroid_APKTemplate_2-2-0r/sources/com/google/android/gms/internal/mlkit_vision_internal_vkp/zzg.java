package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzg extends zzans<zzg, zzf> implements zzapg {
    private static final zzg zzb;
    private int zze;
    private int zzf;
    private String zzg = "";
    private float zzh;
    private float zzi;

    static {
        zzg zzgVar = new zzg();
        zzb = zzgVar;
        zzans.zzM(zzg.class, zzgVar);
    }

    private zzg() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001င\u0000\u0002ဈ\u0001\u0003ခ\u0002\u0004ခ\u0003", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi"});
        }
        if (i2 == 3) {
            return new zzg();
        }
        zze zzeVar = null;
        if (i2 == 4) {
            return new zzf(zzeVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
