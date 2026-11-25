package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzatw extends zzans<zzatw, zzatv> implements zzapg {
    private static final zzatw zzb;
    private int zze;
    private int zzf;
    private String zzg = "";
    private String zzh = "";
    private String zzi = "";
    private String zzj = "";
    private String zzk = "";
    private String zzl = "";
    private String zzm = "";
    private String zzn = "";
    private String zzo = "";

    static {
        zzatw zzatwVar = new zzatw();
        zzb = zzatwVar;
        zzans.zzM(zzatw.class, zzatwVar);
    }

    private zzatw() {
    }

    public static zzatw zze(byte[] bArr, zzanc zzancVar) throws zzaoe {
        return (zzatw) zzans.zzD(zzb, bArr, zzancVar);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\n\u0000\u0001\u0001\n\n\u0000\u0000\u0000\u0001င\u0000\u0002ဈ\u0001\u0003ဈ\u0002\u0004ဈ\u0003\u0005ဈ\u0004\u0006ဈ\u0005\u0007ဈ\u0006\bဈ\u0007\tဈ\b\nဈ\t", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi", "zzj", "zzk", "zzl", "zzm", "zzn", "zzo"});
        }
        if (i2 == 3) {
            return new zzatw();
        }
        zzatr zzatrVar = null;
        if (i2 == 4) {
            return new zzatv(zzatrVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }

    public final String zzf() {
        return this.zzn;
    }
}
