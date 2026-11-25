package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzaiz extends zzans<zzaiz, zzaiy> implements zzapg {
    private static final zzaiz zzb;
    private int zze;
    private int zzf = -1;
    private int zzg = -1;
    private zzanx zzh = zzF();
    private zzany zzi = zzG();
    private zzanx zzj = zzF();

    static {
        zzaiz zzaizVar = new zzaiz();
        zzb = zzaizVar;
        zzans.zzM(zzaiz.class, zzaizVar);
    }

    private zzaiz() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0005\u0000\u0001\u0001\u0006\u0005\u0000\u0003\u0000\u0001င\u0000\u0002င\u0001\u0003\u0013\u0004\u0016\u0006\u0013", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi", "zzj"});
        }
        if (i2 == 3) {
            return new zzaiz();
        }
        zzaiv zzaivVar = null;
        if (i2 == 4) {
            return new zzaiy(zzaivVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
