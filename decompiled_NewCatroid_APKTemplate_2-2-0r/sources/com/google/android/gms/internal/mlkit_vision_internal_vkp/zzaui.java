package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzaui extends zzans<zzaui, zzaua> implements zzapg {
    private static final zzaui zzb;
    private int zze;
    private int zzf;
    private int zzg;
    private zzatu zzj;
    private zzaue zzk;
    private int zzl;
    private int zzn;
    private String zzh = "";
    private String zzi = "";
    private zzaob<zzaur> zzm = zzI();

    static {
        zzaui zzauiVar = new zzaui();
        zzb = zzauiVar;
        zzans.zzM(zzaui.class, zzauiVar);
    }

    private zzaui() {
    }

    public static zzaui zzg() {
        return zzb;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\t\u0000\u0001\u0001\t\t\u0000\u0001\u0000\u0001ဌ\u0000\u0002ဌ\u0001\u0003ဈ\u0002\u0004ဈ\u0003\u0005ဉ\u0004\u0006ဉ\u0005\u0007င\u0006\b\u001b\tင\u0007", new Object[]{"zze", "zzf", zzaty.zza, "zzg", zzaug.zza, "zzh", "zzi", "zzj", "zzk", "zzl", "zzm", zzaur.class, "zzn"});
        }
        if (i2 == 3) {
            return new zzaui();
        }
        zzatr zzatrVar = null;
        if (i2 == 4) {
            return new zzaua(zzatrVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }

    public final zzatu zzc() {
        zzatu zzatuVar = this.zzj;
        return zzatuVar == null ? zzatu.zze() : zzatuVar;
    }

    public final zzaue zze() {
        zzaue zzaueVar = this.zzk;
        return zzaueVar == null ? zzaue.zzf() : zzaueVar;
    }

    public final int zzh() {
        int iZza = zzatz.zza(this.zzf);
        if (iZza == 0) {
            return 1;
        }
        return iZza;
    }

    public final int zzi() {
        int iZza = zzauh.zza(this.zzg);
        if (iZza == 0) {
            return 1;
        }
        return iZza;
    }
}
