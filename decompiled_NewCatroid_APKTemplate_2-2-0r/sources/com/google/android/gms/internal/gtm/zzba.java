package com.google.android.gms.internal.gtm;

import android.text.TextUtils;
import com.reandroid.arsc.value.ResConfigBase;
import java.util.HashMap;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzba extends com.google.android.gms.analytics.zzj<zzba> {
    public int zza;
    public int zzb;
    private String zzc;

    public final String toString() {
        HashMap map = new HashMap();
        map.put("language", this.zzc);
        map.put("screenColors", 0);
        map.put(ResConfigBase.NAME_screenWidth, Integer.valueOf(this.zza));
        map.put(ResConfigBase.NAME_screenHeight, Integer.valueOf(this.zzb));
        map.put("viewportWidth", 0);
        map.put("viewportHeight", 0);
        return zza(map);
    }

    @Override // com.google.android.gms.analytics.zzj
    public final /* bridge */ /* synthetic */ void zzc(com.google.android.gms.analytics.zzj zzjVar) {
        zzba zzbaVar = (zzba) zzjVar;
        int i = this.zza;
        if (i != 0) {
            zzbaVar.zza = i;
        }
        int i2 = this.zzb;
        if (i2 != 0) {
            zzbaVar.zzb = i2;
        }
        if (TextUtils.isEmpty(this.zzc)) {
            return;
        }
        zzbaVar.zzc = this.zzc;
    }

    public final String zzd() {
        return this.zzc;
    }

    public final void zze(String str) {
        this.zzc = str;
    }
}
