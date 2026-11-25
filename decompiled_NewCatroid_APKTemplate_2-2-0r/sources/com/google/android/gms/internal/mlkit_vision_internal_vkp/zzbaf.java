package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbaf extends zzans<zzbaf, zzbae> implements zzapg {
    private static final zzbaf zzb;
    private int zze;
    private zzbah zzh;
    private zzbah zzk;
    private zzbah zzn;
    private zzbah zzq;
    private String zzf = "";
    private zzamk zzg = zzamk.zzb;
    private String zzi = "";
    private zzamk zzj = zzamk.zzb;
    private String zzl = "";
    private zzamk zzm = zzamk.zzb;
    private String zzo = "";
    private String zzp = "";

    static {
        zzbaf zzbafVar = new zzbaf();
        zzb = zzbafVar;
        zzans.zzM(zzbaf.class, zzbafVar);
    }

    private zzbaf() {
    }

    public static zzbae zzc() {
        return zzb.zzz();
    }

    public static zzbaf zzf() {
        return zzb;
    }

    static /* synthetic */ void zzg(zzbaf zzbafVar, zzamk zzamkVar) {
        zzamkVar.getClass();
        zzbafVar.zze |= 16;
        zzbafVar.zzj = zzamkVar;
    }

    static /* synthetic */ void zzh(zzbaf zzbafVar, zzamk zzamkVar) {
        zzamkVar.getClass();
        zzbafVar.zze |= 128;
        zzbafVar.zzm = zzamkVar;
    }

    static /* synthetic */ void zzi(zzbaf zzbafVar, zzbah zzbahVar) {
        zzbahVar.getClass();
        zzbafVar.zzh = zzbahVar;
        zzbafVar.zze |= 4;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\f\u0000\u0001\u0001\f\f\u0000\u0000\u0000\u0001ဈ\u0000\u0002ည\u0001\u0003ည\u0004\u0004ဈ\u0003\u0005ဈ\u0006\u0006ည\u0007\u0007ဈ\t\bဈ\n\tဉ\u0002\nဉ\u0005\u000bဉ\b\fဉ\u000b", new Object[]{"zze", "zzf", "zzg", "zzj", "zzi", "zzl", "zzm", "zzo", "zzp", "zzh", "zzk", "zzn", "zzq"});
        }
        if (i2 == 3) {
            return new zzbaf();
        }
        zzbac zzbacVar = null;
        if (i2 == 4) {
            return new zzbae(zzbacVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
