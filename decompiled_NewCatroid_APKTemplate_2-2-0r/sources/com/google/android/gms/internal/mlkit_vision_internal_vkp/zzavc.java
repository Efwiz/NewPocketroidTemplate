package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzavc extends zzans<zzavc, zzauy> implements zzapg {
    private static final zzavc zzb;
    private int zze;
    private int zzf;
    private zzaut zzg;
    private zzaun zzh;

    static {
        zzavc zzavcVar = new zzavc();
        zzb = zzavcVar;
        zzans.zzM(zzavc.class, zzavcVar);
    }

    private zzavc() {
    }

    public static zzavc zze() {
        return zzb;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဉ\u0001\u0003ဉ\u0002", new Object[]{"zze", "zzf", zzava.zza, "zzg", "zzh"});
        }
        if (i2 == 3) {
            return new zzavc();
        }
        zzatr zzatrVar = null;
        if (i2 == 4) {
            return new zzauy(zzatrVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }

    public final int zzf() {
        int iZza = zzavb.zza(this.zzf);
        if (iZza == 0) {
            return 1;
        }
        return iZza;
    }
}
