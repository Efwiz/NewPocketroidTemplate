package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbe extends zzans<zzbe, zzbd> implements zzapg {
    private static final zzbe zzb;
    private int zze;
    private zzbbv zzh;
    private zzjp zzi;
    private boolean zzl;
    private boolean zzf = true;
    private boolean zzg = true;
    private float zzj = 0.7f;
    private float zzk = 0.8f;

    static {
        zzbe zzbeVar = new zzbe();
        zzb = zzbeVar;
        zzans.zzM(zzbe.class, zzbeVar);
    }

    private zzbe() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0007\u0000\u0001\u0001\u0007\u0007\u0000\u0000\u0000\u0001ဇ\u0000\u0002ဉ\u0002\u0003ဉ\u0003\u0004ဇ\u0001\u0005ခ\u0004\u0006ခ\u0005\u0007ဇ\u0006", new Object[]{"zze", "zzf", "zzh", "zzi", "zzg", "zzj", "zzk", "zzl"});
        }
        if (i2 == 3) {
            return new zzbe();
        }
        zzbc zzbcVar = null;
        if (i2 == 4) {
            return new zzbd(zzbcVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
