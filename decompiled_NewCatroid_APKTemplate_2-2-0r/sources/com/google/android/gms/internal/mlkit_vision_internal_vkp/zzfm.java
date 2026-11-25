package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzfm extends zzans<zzfm, zzfl> implements zzapg {
    private static final zzfm zzb;
    private int zze;
    private String zzf = "";
    private String zzg = "";

    static {
        zzfm zzfmVar = new zzfm();
        zzb = zzfmVar;
        zzans.zzM(zzfm.class, zzfmVar);
    }

    private zzfm() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဈ\u0001", new Object[]{"zze", "zzf", "zzg"});
        }
        if (i2 == 3) {
            return new zzfm();
        }
        zzfk zzfkVar = null;
        if (i2 == 4) {
            return new zzfl(zzfkVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
