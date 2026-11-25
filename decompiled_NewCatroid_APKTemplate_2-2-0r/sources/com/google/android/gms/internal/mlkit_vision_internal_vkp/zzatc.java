package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzatc extends zzans<zzatc, zzatb> implements zzapg {
    private static final zzatc zzb;
    private int zze;
    private String zzf = "";
    private long zzg;
    private long zzh;
    private long zzi;

    static {
        zzatc zzatcVar = new zzatc();
        zzb = zzatcVar;
        zzans.zzM(zzatc.class, zzatcVar);
    }

    private zzatc() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဂ\u0001\u0003ဂ\u0002\u0004ဂ\u0003", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi"});
        }
        if (i2 == 3) {
            return new zzatc();
        }
        zzarm zzarmVar = null;
        if (i2 == 4) {
            return new zzatb(zzarmVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
