package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzuu extends zzans<zzuu, zzus> implements zzapg {
    private static final zzanz<Integer, Object> zzb = new zzup();
    private static final zzanz<Integer, Object> zze = new zzuq();
    private static final zzanz<Integer, Object> zzf = new zzur();
    private static final zzuu zzg;
    private int zzh;
    private long zzi;
    private zzany zzj = zzG();
    private zzany zzk = zzG();
    private zzany zzl = zzG();
    private int zzm;

    static {
        zzuu zzuuVar = new zzuu();
        zzg = zzuuVar;
        zzans.zzM(zzuu.class, zzuuVar);
    }

    private zzuu() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzg, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0003\u0000\u0001ဃ\u0000\u0002\u001e\u0003\u001e\u0004\u001e\u0005င\u0001", new Object[]{"zzh", "zzi", "zzj", zzut.zza, "zzk", zzut.zza, "zzl", zzut.zza, "zzm"});
        }
        if (i2 == 3) {
            return new zzuu();
        }
        zzmk zzmkVar = null;
        if (i2 == 4) {
            return new zzus(zzmkVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzg;
    }
}
