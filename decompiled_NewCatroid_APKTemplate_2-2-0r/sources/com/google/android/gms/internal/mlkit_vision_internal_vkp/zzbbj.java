package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbbj extends zzans<zzbbj, zzbbi> implements zzapg {
    private static final zzbbj zzb;
    private int zze;
    private float zzf;
    private float zzg;
    private float zzh;
    private float zzi;
    private byte zzj = 2;

    static {
        zzbbj zzbbjVar = new zzbbj();
        zzb = zzbbjVar;
        zzans.zzM(zzbbj.class, zzbbjVar);
    }

    private zzbbj() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzj);
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0004\u0001ᔁ\u0000\u0002ᔁ\u0001\u0003ᔁ\u0002\u0004ᔁ\u0003", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi"});
        }
        if (i2 == 3) {
            return new zzbbj();
        }
        zzbbh zzbbhVar = null;
        if (i2 == 4) {
            return new zzbbi(zzbbhVar);
        }
        if (i2 == 5) {
            return zzb;
        }
        this.zzj = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }
}
