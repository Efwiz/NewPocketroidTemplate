package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzajh extends zzans<zzajh, zzajg> implements zzapg {
    private static final zzajh zzb;
    private int zze;
    private zzajf zzf;
    private double zzg;
    private boolean zzh;

    static {
        zzajh zzajhVar = new zzajh();
        zzb = zzajhVar;
        zzans.zzM(zzajh.class, zzajhVar);
    }

    private zzajh() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002က\u0001\u0003ဇ\u0002", new Object[]{"zze", "zzf", "zzg", "zzh"});
        }
        if (i2 == 3) {
            return new zzajh();
        }
        zzaiv zzaivVar = null;
        if (i2 == 4) {
            return new zzajg(zzaivVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
