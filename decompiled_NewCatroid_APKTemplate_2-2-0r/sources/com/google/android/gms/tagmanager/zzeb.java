package com.google.android.gms.tagmanager;

import java.util.Map;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzeb extends zzbu {
    private static final String zza = com.google.android.gms.internal.gtm.zza.RANDOM.toString();
    private static final String zzb = com.google.android.gms.internal.gtm.zzb.MIN.toString();
    private static final String zzc = com.google.android.gms.internal.gtm.zzb.MAX.toString();

    public zzeb() {
        super(zza, new String[0]);
    }

    @Override // com.google.android.gms.tagmanager.zzbu
    public final com.google.android.gms.internal.gtm.zzak zza(Map<String, com.google.android.gms.internal.gtm.zzak> map) {
        com.google.android.gms.internal.gtm.zzak zzakVar = map.get(zzb);
        com.google.android.gms.internal.gtm.zzak zzakVar2 = map.get(zzc);
        double d = 2.147483647E9d;
        double d2 = 0.0d;
        if (zzakVar != null && zzakVar != zzfv.zzb() && zzakVar2 != null && zzakVar2 != zzfv.zzb()) {
            zzfu zzfuVarZze = zzfv.zze(zzfv.zzl(zzakVar));
            zzfu zzfuVarZze2 = zzfv.zze(zzfv.zzl(zzakVar2));
            if (zzfuVarZze != zzfv.zzd() && zzfuVarZze2 != zzfv.zzd()) {
                double dDoubleValue = zzfuVarZze.doubleValue();
                double dDoubleValue2 = zzfuVarZze2.doubleValue();
                if (dDoubleValue <= dDoubleValue2) {
                    d2 = dDoubleValue;
                    d = dDoubleValue2;
                }
            }
        }
        return zzfv.zzc(Long.valueOf(Math.round((Math.random() * (d - d2)) + d2)));
    }

    @Override // com.google.android.gms.tagmanager.zzbu
    public final boolean zzb() {
        return false;
    }
}
