package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzig extends zzans<zzig, zzic> implements zzapg {
    private static final zzig zzb;
    private int zze;
    private zzie zzg;
    private zzhi zzh;
    private zzbbs zzi;
    private zzaro zzl;
    private String zzf = "";
    private String zzj = LanguageCodeUtil.EN;
    private int zzk = 1;
    private int zzm = -1;

    static {
        zzig zzigVar = new zzig();
        zzb = zzigVar;
        zzans.zzM(zzig.class, zzigVar);
    }

    private zzig() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzans
    protected final Object zzb(int i, Object obj, Object obj2) {
        int i2 = i - 1;
        if (i2 == 0) {
            return (byte) 1;
        }
        if (i2 == 2) {
            return zzL(zzb, "\u0001\b\u0000\u0001\u0001\b\b\u0000\u0000\u0000\u0001ဈ\u0000\u0002ဉ\u0001\u0003ဌ\u0005\u0004ဉ\u0006\u0005ဉ\u0002\u0006ဈ\u0004\u0007င\u0007\bဉ\u0003", new Object[]{"zze", "zzf", "zzg", "zzk", zzif.zza, "zzl", "zzh", "zzj", "zzm", "zzi"});
        }
        if (i2 == 3) {
            return new zzig();
        }
        zzib zzibVar = null;
        if (i2 == 4) {
            return new zzic(zzibVar);
        }
        if (i2 != 5) {
            return null;
        }
        return zzb;
    }
}
