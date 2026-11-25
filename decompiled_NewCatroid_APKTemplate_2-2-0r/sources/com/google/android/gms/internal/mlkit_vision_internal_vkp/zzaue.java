package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzaue extends zzans<zzaue, zzaud> implements zzapg {
    private static final zzaue zzb;
    private int zze;
    private zzauc zzf;
    private zzauc zzg;
    private boolean zzh;

    static {
        zzaue zzaueVar = new zzaue();
        zzb = zzaueVar;
        zzans.zzM(zzaue.class, zzaueVar);
    }

    private zzaue() {
    }

    public static zzaue zzf() {
        return zzb;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဇ\u0002", new Object[]{"zze", "zzf", "zzg", "zzh"});
        }
        if (i2 == 3) {
            return new zzaue();
        }
        zzatr zzatrVar = null;
        if (i2 == 4) {
            return new zzaud(zzatrVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }

    public final zzauc zzc() {
        zzauc zzaucVar = this.zzf;
        return zzaucVar == null ? zzauc.zze() : zzaucVar;
    }

    public final boolean zzg() {
        return this.zzh;
    }

    public final boolean zzh() {
        return (this.zze & 2) != 0;
    }

    public final boolean zzi() {
        return (this.zze & 1) != 0;
    }
}
