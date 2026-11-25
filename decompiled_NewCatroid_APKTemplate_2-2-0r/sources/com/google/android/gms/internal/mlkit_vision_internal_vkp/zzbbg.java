package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbbg extends zzans<zzbbg, zzbbf> implements zzapg {
    private static final zzbbg zzb;
    private int zze;
    private float zzf;
    private float zzg;
    private float zzh;
    private float zzi;
    private zzbbj zzj;
    private float zzk;
    private zzbas zzl;
    private float zzm;
    private byte zzp = 2;
    private zzamk zzn = zzamk.zzb;
    private zzamk zzo = zzamk.zzb;

    static {
        zzbbg zzbbgVar = new zzbbg();
        zzb = zzbbgVar;
        zzans.zzM(zzbbg.class, zzbbgVar);
    }

    private zzbbg() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzp);
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\n\u0000\u0001\u0001\n\n\u0000\u0000\u0004\u0001ᔁ\u0000\u0002ᔁ\u0001\u0003ᔁ\u0002\u0004ခ\u0003\u0005ခ\u0007\u0006ည\b\u0007ခ\u0005\bဉ\u0006\tᐉ\u0004\nည\t", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi", "zzm", "zzn", "zzk", "zzl", "zzj", "zzo"});
        }
        if (i2 == 3) {
            return new zzbbg();
        }
        zzbbe zzbbeVar = null;
        if (i2 == 4) {
            return new zzbbf(zzbbeVar);
        }
        if (i2 == 5) {
            return zzb;
        }
        this.zzp = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }
}
