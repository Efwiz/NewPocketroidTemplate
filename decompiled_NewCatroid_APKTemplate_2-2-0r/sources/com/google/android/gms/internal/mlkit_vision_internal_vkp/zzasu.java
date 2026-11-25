package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzasu extends zzans<zzasu, zzast> implements zzapg {
    private static final zzasu zzb;
    private int zze;
    private int zzf;
    private int zzg = 100;
    private int zzh;

    static {
        zzasu zzasuVar = new zzasu();
        zzb = zzasuVar;
        zzans.zzM(zzasu.class, zzasuVar);
    }

    private zzasu() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဌ\u0000\u0002င\u0001\u0003င\u0002", new Object[]{"zze", "zzf", zzass.zza, "zzg", "zzh"});
        }
        if (i2 == 3) {
            return new zzasu();
        }
        zzarm zzarmVar = null;
        if (i2 == 4) {
            return new zzast(zzarmVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
