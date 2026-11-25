package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzatu extends zzans<zzatu, zzats> implements zzapg {
    private static final zzatu zzb;
    private int zze;
    private int zzf;
    private zzavc zzg;

    static {
        zzatu zzatuVar = new zzatu();
        zzb = zzatuVar;
        zzans.zzM(zzatu.class, zzatuVar);
    }

    private zzatu() {
    }

    public static zzatu zze() {
        return zzb;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001ဌ\u0000\u0002ဉ\u0001", new Object[]{"zze", "zzf", zzatt.zza, "zzg"});
        }
        if (i2 == 3) {
            return new zzatu();
        }
        zzatr zzatrVar = null;
        if (i2 == 4) {
            return new zzats(zzatrVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }

    public final zzavc zzf() {
        zzavc zzavcVar = this.zzg;
        return zzavcVar == null ? zzavc.zze() : zzavcVar;
    }
}
