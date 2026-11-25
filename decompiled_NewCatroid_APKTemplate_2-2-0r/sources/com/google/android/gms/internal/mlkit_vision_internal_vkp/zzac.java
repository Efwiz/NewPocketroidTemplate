package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzac extends zzans<zzac, zzab> implements zzapg {
    private static final zzac zzb;
    private int zze;
    private int zzg;
    private float zzi;
    private boolean zzj;
    private boolean zzk;
    private String zzf = "";
    private String zzh = "";

    static {
        zzac zzacVar = new zzac();
        zzb = zzacVar;
        zzans.zzM(zzac.class, zzacVar);
    }

    private zzac() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0006\u0000\u0001\u0001\u0006\u0006\u0000\u0000\u0000\u0001ဈ\u0000\u0002င\u0001\u0003ဈ\u0002\u0004ခ\u0003\u0005ဇ\u0004\u0006ဇ\u0005", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi", "zzj", "zzk"});
        }
        if (i2 == 3) {
            return new zzac();
        }
        zzaa zzaaVar = null;
        if (i2 == 4) {
            return new zzab(zzaaVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
