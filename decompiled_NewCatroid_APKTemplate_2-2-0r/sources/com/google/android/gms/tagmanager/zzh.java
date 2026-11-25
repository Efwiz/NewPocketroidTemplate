package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.Map;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzh extends zzbu {
    private static final String zza = com.google.android.gms.internal.gtm.zza.ADWORDS_CLICK_REFERRER.toString();
    private static final String zzb = com.google.android.gms.internal.gtm.zzb.COMPONENT.toString();
    private static final String zzc = com.google.android.gms.internal.gtm.zzb.CONVERSION_ID.toString();
    private final Context zzd;

    public zzh(Context context) {
        super(zza, zzc);
        this.zzd = context;
    }

    @Override // com.google.android.gms.tagmanager.zzbu
    public final com.google.android.gms.internal.gtm.zzak zza(Map<String, com.google.android.gms.internal.gtm.zzak> map) {
        com.google.android.gms.internal.gtm.zzak zzakVar = map.get(zzc);
        if (zzakVar == null) {
            return zzfv.zzb();
        }
        String strZzn = zzfv.zzn(zzfv.zzl(zzakVar));
        com.google.android.gms.internal.gtm.zzak zzakVar2 = map.get(zzb);
        String strZzn2 = zzakVar2 != null ? zzfv.zzn(zzfv.zzl(zzakVar2)) : null;
        Context context = this.zzd;
        String string = zzcx.zza.get(strZzn);
        if (string == null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("gtm_click_referrers", 0);
            string = sharedPreferences != null ? sharedPreferences.getString(strZzn, "") : "";
            zzcx.zza.put(strZzn, string);
        }
        String strZza = zzcx.zza(string, strZzn2);
        return strZza != null ? zzfv.zzc(strZza) : zzfv.zzb();
    }

    @Override // com.google.android.gms.tagmanager.zzbu
    public final boolean zzb() {
        return true;
    }
}
