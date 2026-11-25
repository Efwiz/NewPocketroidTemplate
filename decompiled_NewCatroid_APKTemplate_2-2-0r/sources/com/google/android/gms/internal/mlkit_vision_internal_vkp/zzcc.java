package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzcc extends zzans<zzcc, zzcb> implements zzapg {
    private static final zzcc zzb;
    private int zze;
    private float zzg;
    private int zzj;
    private float zzk;
    private zzaob<String> zzf = zzans.zzI();
    private boolean zzh = true;
    private float zzi = 0.8f;

    static {
        zzcc zzccVar = new zzcc();
        zzb = zzccVar;
        zzans.zzM(zzcc.class, zzccVar);
    }

    private zzcc() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0006\u0000\u0001\u0001\u0006\u0006\u0000\u0001\u0000\u0001\u001a\u0002ခ\u0000\u0003ဇ\u0001\u0004ခ\u0002\u0005င\u0003\u0006ခ\u0004", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi", "zzj", "zzk"});
        }
        if (i2 == 3) {
            return new zzcc();
        }
        zzca zzcaVar = null;
        if (i2 == 4) {
            return new zzcb(zzcaVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
