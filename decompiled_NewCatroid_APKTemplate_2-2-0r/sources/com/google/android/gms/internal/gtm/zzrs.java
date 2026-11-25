package com.google.android.gms.internal.gtm;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzrs {
    private final List<zzrw> zza;
    private final Map<String, List<zzro>> zzb;
    private final String zzc;

    /* synthetic */ zzrs(List list, Map map, String str, int i, zzrr zzrrVar) {
        this.zza = Collections.unmodifiableList(list);
        this.zzb = Collections.unmodifiableMap(map);
        this.zzc = str;
    }

    public static zzru zza() {
        return new zzru(null);
    }

    public final String toString() {
        String strValueOf = String.valueOf(this.zza);
        String strValueOf2 = String.valueOf(this.zzb);
        StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 17 + String.valueOf(strValueOf2).length());
        sb.append("Rules: ");
        sb.append(strValueOf);
        sb.append("  Macros: ");
        sb.append(strValueOf2);
        return sb.toString();
    }

    public final String zzb() {
        return this.zzc;
    }

    public final List<zzrw> zzc() {
        return this.zza;
    }

    public final Map<String, List<zzro>> zzd() {
        return this.zzb;
    }
}
