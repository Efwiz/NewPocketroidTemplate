package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzaj extends zzans<zzaj, zzah> implements zzapg {
    private static final zzaj zzb;
    private int zze;
    private String zzf = "";
    private String zzg = "";
    private String zzh = "";
    private int zzi;

    static {
        zzaj zzajVar = new zzaj();
        zzb = zzajVar;
        zzans.zzM(zzaj.class, zzajVar);
    }

    private zzaj() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဈ\u0001\u0003ဈ\u0002\u0004ဌ\u0003", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi", zzai.zza});
        }
        if (i2 == 3) {
            return new zzaj();
        }
        zzag zzagVar = null;
        if (i2 == 4) {
            return new zzah(zzagVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
