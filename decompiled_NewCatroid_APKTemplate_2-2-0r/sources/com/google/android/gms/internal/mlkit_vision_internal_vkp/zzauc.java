package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzauc extends zzans<zzauc, zzaub> implements zzapg {
    private static final zzauc zzb;
    private int zze;
    private int zzf;
    private int zzg;
    private int zzh;
    private int zzi;

    static {
        zzauc zzaucVar = new zzauc();
        zzb = zzaucVar;
        zzans.zzM(zzauc.class, zzaucVar);
    }

    private zzauc() {
    }

    public static zzauc zze() {
        return zzb;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001င\u0000\u0002င\u0001\u0003င\u0002\u0004င\u0003", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi"});
        }
        if (i2 == 3) {
            return new zzauc();
        }
        zzatr zzatrVar = null;
        if (i2 == 4) {
            return new zzaub(zzatrVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }

    public final boolean zzf() {
        return (this.zze & 1) != 0;
    }

    public final boolean zzg() {
        return (this.zze & 2) != 0;
    }
}
