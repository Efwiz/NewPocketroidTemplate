package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzsg extends zzans<zzsg, zzsf> implements zzapg {
    private static final zzanz<Integer, Object> zzb = new zzsb();
    private static final zzanz<Integer, Object> zze = new zzsc();
    private static final zzsg zzf;
    private int zzg;
    private zzqv zzh;
    private zzxm zzi;
    private zzany zzj = zzG();
    private zzany zzk = zzG();
    private zzqr zzl;

    static {
        zzsg zzsgVar = new zzsg();
        zzf = zzsgVar;
        zzans.zzM(zzsg.class, zzsgVar);
    }

    private zzsg() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzf, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0002\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003\u001e\u0004\u001e\u0005ဉ\u0002", new Object[]{"zzg", "zzh", "zzi", "zzj", zzsd.zza, "zzk", zzse.zza, "zzl"});
        }
        if (i2 == 3) {
            return new zzsg();
        }
        zzmk zzmkVar = null;
        if (i2 == 4) {
            return new zzsf(zzmkVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzf;
    }
}
