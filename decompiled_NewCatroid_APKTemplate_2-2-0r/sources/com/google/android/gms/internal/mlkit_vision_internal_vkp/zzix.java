package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzix extends zzans<zzix, zziw> implements zzapg {
    private static final zzix zzb;
    private int zze;
    private zzhi zzh;
    private zzhi zzi;
    private boolean zzj;
    private boolean zzk;
    private boolean zzl;
    private zzaro zzn;
    private String zzf = "";
    private String zzg = "";
    private int zzm = 1;

    static {
        zzix zzixVar = new zzix();
        zzb = zzixVar;
        zzans.zzM(zzix.class, zzixVar);
    }

    private zzix() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\t\u0000\u0001\u0001\n\t\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဇ\u0005\u0003ဇ\u0006\u0004ဉ\u0002\u0006ဇ\u0004\u0007င\u0007\bဉ\b\tဉ\u0003\nဈ\u0001", new Object[]{"zze", "zzf", "zzk", "zzl", "zzh", "zzj", "zzm", "zzn", "zzi", "zzg"});
        }
        if (i2 == 3) {
            return new zzix();
        }
        zziv zzivVar = null;
        if (i2 == 4) {
            return new zziw(zzivVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
