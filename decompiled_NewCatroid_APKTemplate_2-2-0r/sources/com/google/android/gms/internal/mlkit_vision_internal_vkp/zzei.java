package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzei extends zzans<zzei, zzeh> implements zzapg {
    private static final zzei zzb;
    private int zze;
    private zzfp zzf;
    private zzxv zzg;
    private zzbk zzh;
    private zzfg zzi;
    private zzeo zzj;
    private zzel zzk;
    private zzfa zzl;
    private byte zzm = 2;

    static {
        zzei zzeiVar = new zzei();
        zzb = zzeiVar;
        zzans.zzM(zzei.class, zzeiVar);
    }

    private zzei() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzm);
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0007\u0000\u0001\u0001\u0007\u0007\u0000\u0000\u0001\u0001ဉ\u0000\u0002ဉ\u0001\u0003ᐉ\u0002\u0004ဉ\u0003\u0005ဉ\u0004\u0006ဉ\u0005\u0007ဉ\u0006", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi", "zzj", "zzk", "zzl"});
        }
        if (i2 == 3) {
            return new zzei();
        }
        zzeg zzegVar = null;
        if (i2 == 4) {
            return new zzeh(zzegVar);
        }
        if (i2 == 5) {
            return zzb;
        }
        this.zzm = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }
}
