package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbcu extends zzans<zzbcu, zzbct> implements zzapg {
    private static final zzbcu zzb;
    private String zze = "";
    private String zzf = "";
    private double zzg;

    static {
        zzbcu zzbcuVar = new zzbcu();
        zzb = zzbcuVar;
        zzans.zzM(zzbcu.class, zzbcuVar);
    }

    private zzbcu() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0000\u0003\u0000\u0000\u0001\u0003\u0003\u0000\u0000\u0000\u0001Ȉ\u0002\u0000\u0003Ȉ", new Object[]{"zze", "zzg", "zzf"});
        }
        if (i2 == 3) {
            return new zzbcu();
        }
        zzbcs zzbcsVar = null;
        if (i2 == 4) {
            return new zzbct(zzbcsVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
