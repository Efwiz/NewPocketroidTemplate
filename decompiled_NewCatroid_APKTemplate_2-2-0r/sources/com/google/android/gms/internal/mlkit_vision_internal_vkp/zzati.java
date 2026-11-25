package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzati extends zzans<zzati, zzath> implements zzapg {
    private static final zzati zzb;
    private int zze;
    private int zzh;
    private String zzf = "";
    private String zzg = "";
    private String zzi = "";

    static {
        zzati zzatiVar = new zzati();
        zzb = zzatiVar;
        zzans.zzM(zzati.class, zzatiVar);
    }

    private zzati() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဈ\u0001\u0003င\u0002\u0004ဈ\u0003", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi"});
        }
        if (i2 == 3) {
            return new zzati();
        }
        zzarm zzarmVar = null;
        if (i2 == 4) {
            return new zzath(zzarmVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
