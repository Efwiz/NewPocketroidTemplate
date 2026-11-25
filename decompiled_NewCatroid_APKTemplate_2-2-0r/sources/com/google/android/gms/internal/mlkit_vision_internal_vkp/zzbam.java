package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbam extends zzans<zzbam, zzbal> implements zzapg {
    private static final zzbam zzb;
    private int zze;
    private zzbap zzf;
    private zzazr zzg;
    private zzazy zzh;

    static {
        zzbam zzbamVar = new zzbam();
        zzb = zzbamVar;
        zzans.zzM(zzbam.class, zzbamVar);
    }

    private zzbam() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဉ\u0002", new Object[]{"zze", "zzf", "zzg", "zzh"});
        }
        if (i2 == 3) {
            return new zzbam();
        }
        zzbak zzbakVar = null;
        if (i2 == 4) {
            return new zzbal(zzbakVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
