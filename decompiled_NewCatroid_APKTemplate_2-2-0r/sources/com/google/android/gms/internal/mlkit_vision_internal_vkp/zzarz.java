package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzarz extends zzans<zzarz, zzarx> implements zzapg {
    private static final zzarz zzb;
    private int zze;
    private String zzf = "";
    private int zzg = 1;
    private boolean zzh;
    private int zzi;

    static {
        zzarz zzarzVar = new zzarz();
        zzb = zzarzVar;
        zzans.zzM(zzarz.class, zzarzVar);
    }

    private zzarz() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဌ\u0001\u0003ဇ\u0002\u0004င\u0003", new Object[]{"zze", "zzf", "zzg", zzary.zza, "zzh", "zzi"});
        }
        if (i2 == 3) {
            return new zzarz();
        }
        zzarm zzarmVar = null;
        if (i2 == 4) {
            return new zzarx(zzarmVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
