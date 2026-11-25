package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzjv extends zzans<zzjv, zzjs> implements zzapg {
    private static final zzanz<Integer, Object> zzb = new zzjr();
    private static final zzjv zze;
    private int zzf;
    private int zzh;
    private int zzg = 1;
    private int zzi = 4;
    private int zzj = 240;
    private zzany zzk = zzG();

    static {
        zzjv zzjvVar = new zzjv();
        zze = zzjvVar;
        zzans.zzM(zzjv.class, zzjvVar);
    }

    private zzjv() {
    }

    public static zzjv zzc() {
        return zze;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zze, "\u0001\u0005\u0000\u0001\u0003\n\u0005\u0000\u0001\u0000\u0003ဌ\u0000\u0007င\u0001\bင\u0002\tင\u0003\n,", new Object[]{"zzf", "zzg", zzju.zza, "zzh", "zzi", "zzj", "zzk", zzjt.zza});
        }
        if (i2 == 3) {
            return new zzjv();
        }
        zzjq zzjqVar = null;
        if (i2 == 4) {
            return new zzjs(zzjqVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zze;
    }
}
