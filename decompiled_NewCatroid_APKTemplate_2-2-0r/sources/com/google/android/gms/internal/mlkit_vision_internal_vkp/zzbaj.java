package com.google.android.gms.internal.mlkit_vision_internal_vkp;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbaj extends zzano<zzbaj, zzbad> implements zzapg {
    private static final zzbaj zze;
    private int zzA;
    private int zzf;
    private float zzj;
    private boolean zzm;
    private boolean zzn;
    private boolean zzo;
    private int zzp;
    private zzbam zzq;
    private zzazm zzr;
    private zzaxf zzs;
    private zzbab zzt;
    private zzbaf zzu;
    private zzaro zzw;
    private boolean zzx;
    private boolean zzy;
    private int zzz;
    private byte zzB = 2;
    private String zzg = "";
    private int zzh = 10;
    private int zzi = 1;
    private float zzk = 0.3f;
    private zzaob<String> zzl = zzans.zzI();
    private int zzv = 1;

    static {
        zzbaj zzbajVar = new zzbaj();
        zze = zzbajVar;
        zzans.zzM(zzbaj.class, zzbajVar);
    }

    private zzbaj() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static zzbad zzc() {
        return (zzbad) zze.zzz();
    }

    static /* synthetic */ void zzf(zzbaj zzbajVar, String str) {
        zzbajVar.zzf |= 1;
        zzbajVar.zzg = "MobileSSDTfLiteClient";
    }

    static /* synthetic */ void zzg(zzbaj zzbajVar, boolean z) {
        zzbajVar.zzf |= 32;
        zzbajVar.zzm = true;
    }

    static /* synthetic */ void zzi(zzbaj zzbajVar, zzbaf zzbafVar) {
        zzbafVar.getClass();
        zzbajVar.zzu = zzbafVar;
        zzbajVar.zzf |= 8192;
    }

    static /* synthetic */ void zzj(zzbaj zzbajVar, zzaro zzaroVar) {
        zzaroVar.getClass();
        zzbajVar.zzw = zzaroVar;
        zzbajVar.zzf |= 32768;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return Byte.valueOf(this.zzB);
        }
        if (i2 == 2) {
            return zzL(zze, "\u0001\u0015\u0000\u0001\u0001\u0016\u0015\u0000\u0001\u0000\u0001ဈ\u0000\u0002ဋ\u0001\u0003ဋ\u0002\u0004ခ\u0003\u0005ခ\u0004\u0006\u001a\u0007ဇ\u0005\bဇ\u0006\tဇ\u0007\nဋ\b\u000bဉ\t\fဉ\n\rဉ\f\u000fင\u000e\u0010ဉ\r\u0011ဉ\u000f\u0012ဇ\u0010\u0013ဇ\u0011\u0014င\u0012\u0015ဉ\u000b\u0016ဌ\u0013", new Object[]{"zzf", "zzg", "zzh", "zzi", "zzj", "zzk", "zzl", "zzm", "zzn", "zzo", "zzp", "zzq", "zzr", "zzt", "zzv", "zzu", "zzw", "zzx", "zzy", "zzz", "zzs", "zzA", zzbai.zza});
        }
        if (i2 == 3) {
            return new zzbaj();
        }
        zzbac zzbacVar = null;
        if (i2 == 4) {
            return new zzbad(zzbacVar);
        }
        if (i2 == 5) {
            return zze;
        }
        this.zzB = obj == null ? (byte) 0 : (byte) 1;
        return null;
    }
}
