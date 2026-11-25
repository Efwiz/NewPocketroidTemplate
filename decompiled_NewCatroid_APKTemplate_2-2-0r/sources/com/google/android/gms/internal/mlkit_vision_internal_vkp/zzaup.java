package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzaup extends zzans<zzaup, zzauo> implements zzapg {
    private static final zzaup zzb;
    private int zze;
    private zzatw zzf;
    private zzaux zzg;
    private zzaul zzh;
    private int zzi;
    private zzaui zzj;
    private zzavj zzk;
    private long zzl;
    private long zzm;
    private boolean zzn;
    private int zzo;
    private byte zzp = 2;

    static {
        zzaup zzaupVar = new zzaup();
        zzb = zzaupVar;
        zzans.zzM(zzaup.class, zzaupVar);
    }

    private zzaup() {
    }

    public static zzauo zzf() {
        return zzb.zzz();
    }

    static /* synthetic */ void zzh(zzaup zzaupVar, int i) {
        zzaupVar.zze |= 512;
        zzaupVar.zzo = 3;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzp);
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\n\u0000\u0001\u0001\n\n\u0000\u0000\u0001\u0001ဉ\u0000\u0002ဉ\u0001\u0003ဌ\u0003\u0004ဉ\u0004\u0005ᐉ\u0005\u0006ဂ\u0006\u0007ဂ\u0007\bဇ\b\tင\t\nဉ\u0002", new Object[]{"zze", "zzf", "zzg", "zzi", zzauj.zza, "zzj", "zzk", "zzl", "zzm", "zzn", "zzo", "zzh"});
        }
        if (i2 == 3) {
            return new zzaup();
        }
        zzatr zzatrVar = null;
        if (i2 == 4) {
            return new zzauo(zzatrVar);
        }
        if (i2 == 5) {
            return zzb;
        }
        this.zzp = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }

    public final long zzc() {
        return this.zzm;
    }

    public final zzaui zze() {
        zzaui zzauiVar = this.zzj;
        return zzauiVar == null ? zzaui.zzg() : zzauiVar;
    }
}
