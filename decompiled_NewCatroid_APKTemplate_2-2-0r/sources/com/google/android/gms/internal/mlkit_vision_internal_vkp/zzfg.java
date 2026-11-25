package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzfg extends zzans<zzfg, zzff> implements zzapg {
    private static final zzfg zzb;
    private int zze;
    private float zzf;
    private boolean zzg;
    private zzamk zzh = zzamk.zzb;

    static {
        zzfg zzfgVar = new zzfg();
        zzb = zzfgVar;
        zzans.zzM(zzfg.class, zzfgVar);
    }

    private zzfg() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ခ\u0000\u0002ဇ\u0001\u0003ည\u0002", new Object[]{"zze", "zzf", "zzg", "zzh"});
        }
        if (i2 == 3) {
            return new zzfg();
        }
        zzfe zzfeVar = null;
        if (i2 == 4) {
            return new zzff(zzfeVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
