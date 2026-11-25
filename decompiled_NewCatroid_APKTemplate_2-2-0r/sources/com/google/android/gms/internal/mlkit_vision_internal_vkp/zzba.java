package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzba extends zzans<zzba, zzaz> implements zzapg {
    private static final zzba zzb;
    private int zze;
    private String zzf = "";
    private String zzg = "";
    private String zzh = "";

    static {
        zzba zzbaVar = new zzba();
        zzb = zzbaVar;
        zzans.zzM(zzba.class, zzbaVar);
    }

    private zzba() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဈ\u0001\u0003ဈ\u0002", new Object[]{"zze", "zzf", "zzg", "zzh"});
        }
        if (i2 == 3) {
            return new zzba();
        }
        zzax zzaxVar = null;
        if (i2 == 4) {
            return new zzaz(zzaxVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
