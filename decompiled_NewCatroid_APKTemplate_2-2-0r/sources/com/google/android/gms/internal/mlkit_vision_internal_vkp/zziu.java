package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zziu extends zzans<zziu, zzir> implements zzapg {
    private static final zziu zzb;
    private int zze;
    private zzit zzh;
    private zzhi zzi;
    private zzhi zzj;
    private zzbbs zzk;
    private float zzn;
    private boolean zzq;
    private zzaro zzr;
    private String zzf = "";
    private String zzg = "";
    private String zzl = LanguageCodeUtil.EN;
    private int zzm = -1;
    private zzaob<String> zzo = zzans.zzI();
    private zzaob<String> zzp = zzans.zzI();
    private int zzs = -1;

    static {
        zziu zziuVar = new zziu();
        zzb = zziuVar;
        zzans.zzM(zziu.class, zziuVar);
    }

    private zziu() {
    }

    public static zzir zza() {
        return zzb.zzz();
    }

    static /* synthetic */ void zzd(zziu zziuVar, zzhi zzhiVar) {
        zzhiVar.getClass();
        zziuVar.zzi = zzhiVar;
        zziuVar.zze |= 8;
    }

    static /* synthetic */ void zze(zziu zziuVar, zzhi zzhiVar) {
        zzhiVar.getClass();
        zziuVar.zzj = zzhiVar;
        zziuVar.zze |= 16;
    }

    static /* synthetic */ void zzf(zziu zziuVar, int i) {
        zziuVar.zze |= 128;
        zziuVar.zzm = i;
    }

    static /* synthetic */ void zzg(zziu zziuVar, float f) {
        zziuVar.zze |= 256;
        zziuVar.zzn = f;
    }

    static /* synthetic */ void zzh(zziu zziuVar, Iterable iterable) {
        zzaob<String> zzaobVar = zziuVar.zzp;
        if (!zzaobVar.zzc()) {
            zziuVar.zzp = zzans.zzJ(zzaobVar);
        }
        zzalo.zzt(iterable, zziuVar.zzp);
    }

    static /* synthetic */ void zzi(zziu zziuVar, zzaro zzaroVar) {
        zzaroVar.getClass();
        zziuVar.zzr = zzaroVar;
        zziuVar.zze |= 1024;
    }

    static /* synthetic */ void zzj(zziu zziuVar, String str) {
        zziuVar.zze |= 1;
        zziuVar.zzf = "";
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\u000e\u0000\u0001\u0001\u000f\u000e\u0000\u0002\u0000\u0001ဈ\u0000\u0002င\u0007\u0003ခ\b\u0004\u001a\u0005\u001a\u0006ဉ\u0002\bဇ\t\tဉ\n\nဉ\u0003\u000bဈ\u0006\fဉ\u0004\rင\u000b\u000eဉ\u0005\u000fဈ\u0001", new Object[]{"zze", "zzf", "zzm", "zzn", "zzo", "zzp", "zzh", "zzq", "zzr", "zzi", "zzl", "zzj", "zzs", "zzk", "zzg"});
        }
        if (i2 == 3) {
            return new zziu();
        }
        zziq zziqVar = null;
        if (i2 == 4) {
            return new zzir(zziqVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
