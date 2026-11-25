package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzaiq extends zzans<zzaiq, zzaip> implements zzapg {
    private static final zzaiq zzb;
    private int zze;
    private int zzf;
    private int zzg;
    private int zzh;
    private String zzi = "";

    static {
        zzaiq zzaiqVar = new zzaiq();
        zzb = zzaiqVar;
        zzans.zzM(zzaiq.class, zzaiqVar);
    }

    private zzaiq() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဌ\u0001\u0003ဏ\u0002\u0004ဈ\u0003", new Object[]{"zze", "zzf", zzaiu.zza, "zzg", zzait.zza, "zzh", "zzi"});
        }
        if (i2 == 3) {
            return new zzaiq();
        }
        zzaio zzaioVar = null;
        if (i2 == 4) {
            return new zzaip(zzaioVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
