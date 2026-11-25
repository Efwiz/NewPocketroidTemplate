package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzfd extends zzans<zzfd, zzfc> implements zzapg {
    private static final zzfd zzb;
    private int zze;
    private boolean zzf;
    private String zzg = "";
    private String zzh = "";

    static {
        zzfd zzfdVar = new zzfd();
        zzb = zzfdVar;
        zzans.zzM(zzfd.class, zzfdVar);
    }

    private zzfd() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဇ\u0000\u0002ဈ\u0001\u0003ဈ\u0002", new Object[]{"zze", "zzf", "zzg", "zzh"});
        }
        if (i2 == 3) {
            return new zzfd();
        }
        zzfb zzfbVar = null;
        if (i2 == 4) {
            return new zzfc(zzfbVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
