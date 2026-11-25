package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzpy extends zzans<zzpy, zzpx> implements zzapg {
    private static final zzpy zzb;
    private int zze;
    private int zzf;
    private boolean zzg;
    private String zzh = "";

    static {
        zzpy zzpyVar = new zzpy();
        zzb = zzpyVar;
        zzans.zzM(zzpy.class, zzpyVar);
    }

    private zzpy() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဇ\u0001\u0003ဈ\u0002", new Object[]{"zze", "zzf", zzrm.zza, "zzg", "zzh"});
        }
        if (i2 == 3) {
            return new zzpy();
        }
        zzmk zzmkVar = null;
        if (i2 == 4) {
            return new zzpx(zzmkVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
