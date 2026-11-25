package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzuh extends zzans<zzuh, zzuf> implements zzapg {
    private static final zzuh zzb;
    private int zze;
    private int zzf;
    private boolean zzg;
    private String zzh = "";

    static {
        zzuh zzuhVar = new zzuh();
        zzb = zzuhVar;
        zzans.zzM(zzuh.class, zzuhVar);
    }

    private zzuh() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0003\u0000\u0001\u0003\u0005\u0003\u0000\u0000\u0000\u0003ဌ\u0000\u0004ဇ\u0001\u0005ဈ\u0002", new Object[]{"zze", "zzf", zzug.zza, "zzg", "zzh"});
        }
        if (i2 == 3) {
            return new zzuh();
        }
        zzmk zzmkVar = null;
        if (i2 == 4) {
            return new zzuf(zzmkVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
