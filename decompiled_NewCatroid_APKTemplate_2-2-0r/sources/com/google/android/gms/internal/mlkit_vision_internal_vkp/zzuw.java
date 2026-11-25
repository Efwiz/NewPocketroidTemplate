package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzuw extends zzans<zzuw, zzuv> implements zzapg {
    private static final zzuw zzb;
    private int zze;
    private String zzf = "";
    private String zzg = "";
    private String zzh = "";
    private String zzi = "";
    private String zzj = "";
    private String zzk = "";
    private String zzl = "";
    private zzaob<String> zzm = zzans.zzI();
    private String zzn = "";
    private boolean zzo;
    private boolean zzp;
    private boolean zzq;
    private int zzr;

    static {
        zzuw zzuwVar = new zzuw();
        zzb = zzuwVar;
        zzans.zzM(zzuw.class, zzuwVar);
    }

    private zzuw() {
    }

    public static zzuv zza() {
        return zzb.zzz();
    }

    public static zzuw zzd() {
        return zzb;
    }

    static /* synthetic */ void zzf(zzuw zzuwVar, String str) {
        zzuwVar.zze |= 1;
        zzuwVar.zzf = str;
    }

    static /* synthetic */ void zzg(zzuw zzuwVar, String str) {
        zzuwVar.zze |= 8;
        zzuwVar.zzi = str;
    }

    static /* synthetic */ void zzh(zzuw zzuwVar, String str) {
        zzuwVar.zze |= 16;
        zzuwVar.zzj = str;
    }

    static /* synthetic */ void zzi(zzuw zzuwVar, String str) {
        zzuwVar.zze |= 32;
        zzuwVar.zzk = str;
    }

    static /* synthetic */ void zzj(zzuw zzuwVar, String str) {
        zzuwVar.zze |= 64;
        zzuwVar.zzl = str;
    }

    static /* synthetic */ void zzk(zzuw zzuwVar, Iterable iterable) {
        zzaob<String> zzaobVar = zzuwVar.zzm;
        if (!zzaobVar.zzc()) {
            zzuwVar.zzm = zzans.zzJ(zzaobVar);
        }
        zzalo.zzt(iterable, zzuwVar.zzm);
    }

    static /* synthetic */ void zzl(zzuw zzuwVar, String str) {
        zzuwVar.zze |= 128;
        zzuwVar.zzn = str;
    }

    static /* synthetic */ void zzm(zzuw zzuwVar, boolean z) {
        zzuwVar.zze |= 256;
        zzuwVar.zzo = z;
    }

    static /* synthetic */ void zzn(zzuw zzuwVar, boolean z) {
        zzuwVar.zze |= 512;
        zzuwVar.zzp = z;
    }

    static /* synthetic */ void zzo(zzuw zzuwVar, int i) {
        zzuwVar.zze |= 2048;
        zzuwVar.zzr = i;
    }

    static /* synthetic */ void zzp(zzuw zzuwVar, String str) {
        zzuwVar.zze |= 2;
        zzuwVar.zzg = str;
    }

    static /* synthetic */ void zzq(zzuw zzuwVar, String str) {
        zzuwVar.zze |= 4;
        zzuwVar.zzh = str;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\r\u0000\u0001\u0001\r\r\u0000\u0001\u0000\u0001ဈ\u0000\u0002ဈ\u0001\u0003ဈ\u0002\u0004ဈ\u0003\u0005ဈ\u0004\u0006ဈ\u0005\u0007ဈ\u0006\b\u001a\tဈ\u0007\nဇ\b\u000bဇ\t\fဇ\n\rဋ\u000b", new Object[]{"zze", "zzf", "zzg", "zzh", "zzi", "zzj", "zzk", "zzl", "zzm", "zzn", "zzo", "zzp", "zzq", "zzr"});
        }
        if (i2 == 3) {
            return new zzuw();
        }
        zzmk zzmkVar = null;
        if (i2 == 4) {
            return new zzuv(zzmkVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }

    public final String zze() {
        return this.zzj;
    }
}
