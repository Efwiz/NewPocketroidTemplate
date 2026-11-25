package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzavj extends zzano<zzavj, zzavg> implements zzapg {
    private static final zzavj zze;
    private int zzf;
    private boolean zzh;
    private byte zzi = 2;
    private zzaob<zzavi> zzg = zzI();

    static {
        zzavj zzavjVar = new zzavj();
        zze = zzavjVar;
        zzans.zzM(zzavj.class, zzavjVar);
    }

    private zzavj() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzi);
        }
        if (i2 == 2) {
            return zzL(zze, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0001\u0000\u0001\u001b\u0002ဇ\u0000", new Object[]{"zzf", "zzg", zzavi.class, "zzh"});
        }
        if (i2 == 3) {
            return new zzavj();
        }
        zzatr zzatrVar = null;
        if (i2 == 4) {
            return new zzavg(zzatrVar);
        }
        if (i2 == 5) {
            return zze;
        }
        this.zzi = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }
}
