package com.google.android.gms.internal.gtm;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzay extends com.google.android.gms.analytics.zzj<zzay> {
    private final Map<Integer, Double> zza = new HashMap(4);

    public final String toString() {
        HashMap map = new HashMap();
        for (Map.Entry<Integer, Double> entry : this.zza.entrySet()) {
            String strValueOf = String.valueOf(entry.getKey());
            StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 6);
            sb.append("metric");
            sb.append(strValueOf);
            map.put(sb.toString(), entry.getValue());
        }
        return zza(map);
    }

    @Override // com.google.android.gms.analytics.zzj
    public final /* bridge */ /* synthetic */ void zzc(com.google.android.gms.analytics.zzj zzjVar) {
        ((zzay) zzjVar).zza.putAll(this.zza);
    }

    public final Map<Integer, Double> zzd() {
        return Collections.unmodifiableMap(this.zza);
    }
}
