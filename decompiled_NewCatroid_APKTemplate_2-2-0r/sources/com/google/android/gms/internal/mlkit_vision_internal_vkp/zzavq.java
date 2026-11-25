package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzavq extends zzano<zzavq, zzavp> implements zzapg {
    private static final zzavq zze;
    private int zzf;
    private double zzg;
    private int zzh;
    private int zzi;
    private double zzj;
    private double zzk;
    private byte zzl = 2;

    static {
        zzavq zzavqVar = new zzavq();
        zze = zzavqVar;
        zzans.zzM(zzavq.class, zzavqVar);
    }

    private zzavq() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzl);
        }
        if (i2 == 2) {
            return zzL(zze, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0000\u0000\u0001က\u0000\u0002င\u0001\u0003င\u0002\u0004က\u0003\u0005က\u0004", new Object[]{"zzf", "zzg", "zzh", "zzi", "zzj", "zzk"});
        }
        if (i2 == 3) {
            return new zzavq();
        }
        zzavo zzavoVar = null;
        if (i2 == 4) {
            return new zzavp(zzavoVar);
        }
        if (i2 == 5) {
            return zze;
        }
        this.zzl = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }
}
