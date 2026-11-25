package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzcs extends zzans<zzcs, zzcr> implements zzapg {
    private static final zzcs zzb;
    private int zze;
    private zzamk zzf = zzamk.zzb;
    private float zzg;
    private zzgt zzh;
    private long zzi;

    static {
        zzcs zzcsVar = new zzcs();
        zzb = zzcsVar;
        zzans.zzM(zzcs.class, zzcsVar);
    }

    private zzcs() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001ည\u0000\u0002ခ\u0001\u0003ဉ\u0002\u0004ဂ\u0003", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi"});
        }
        if (i2 == 3) {
            return new zzcs();
        }
        zzcp zzcpVar = null;
        if (i2 == 4) {
            return new zzcr(zzcpVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
