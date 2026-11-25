package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzajv extends zzans<zzajv, zzaju> implements zzapg {
    private static final zzanz<Integer, Object> zzb = new zzajt();
    private static final zzajv zze;
    private int zzf;
    private int zzg;
    private String zzh = "";
    private zzany zzi = zzG();

    static {
        zzajv zzajvVar = new zzajv();
        zze = zzajvVar;
        zzans.zzM(zzajv.class, zzajvVar);
    }

    private zzajv() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zze, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0001\u0000\u0001ဈ\u0001\u0002င\u0000\u0003\u001e", new Object[]{"zzf", "zzh", "zzg", "zzi", zzajw.zza});
        }
        if (i2 == 3) {
            return new zzajv();
        }
        zzaiv zzaivVar = null;
        if (i2 == 4) {
            return new zzaju(zzaivVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zze;
    }
}
