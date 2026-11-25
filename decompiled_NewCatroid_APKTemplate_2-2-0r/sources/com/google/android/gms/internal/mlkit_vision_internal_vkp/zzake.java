package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzake extends zzans<zzake, zzakd> implements zzapg {
    private static final zzake zzb;
    private int zze;
    private float zzg;
    private String zzf = "";
    private String zzh = "";

    static {
        zzake zzakeVar = new zzake();
        zzb = zzakeVar;
        zzans.zzM(zzake.class, zzakeVar);
    }

    private zzake() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဈ\u0000\u0002ခ\u0001\u0003ဈ\u0002", new Object[]{"zze", "zzf", "zzg", "zzh"});
        }
        if (i2 == 3) {
            return new zzake();
        }
        zzaiv zzaivVar = null;
        if (i2 == 4) {
            return new zzakd(zzaivVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
