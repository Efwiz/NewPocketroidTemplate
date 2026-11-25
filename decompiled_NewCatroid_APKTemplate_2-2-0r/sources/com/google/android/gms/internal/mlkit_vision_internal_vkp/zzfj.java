package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzfj extends zzans<zzfj, zzfi> implements zzapg {
    private static final zzfj zzb;
    private int zze;
    private boolean zzf;
    private float zzg = 0.8f;
    private int zzh;
    private int zzi;

    static {
        zzfj zzfjVar = new zzfj();
        zzb = zzfjVar;
        zzans.zzM(zzfj.class, zzfjVar);
    }

    private zzfj() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001ဇ\u0000\u0002ခ\u0001\u0003င\u0002\u0004င\u0003", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi"});
        }
        if (i2 == 3) {
            return new zzfj();
        }
        zzfh zzfhVar = null;
        if (i2 == 4) {
            return new zzfi(zzfhVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
