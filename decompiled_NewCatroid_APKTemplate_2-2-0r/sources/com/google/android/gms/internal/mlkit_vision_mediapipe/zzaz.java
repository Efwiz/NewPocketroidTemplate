package com.google.android.gms.internal.mlkit_vision_mediapipe;

import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzaz {
    private final zzcf zza;
    private final String zzb;

    public zzaz(zzcf zzcfVar, String str) {
        this.zza = (zzcf) zzcj.zza(zzcfVar, "parser");
        this.zzb = (String) zzcj.zza(str, CrashHianalyticsData.MESSAGE);
    }

    public final zzcf zza() {
        return this.zza;
    }

    public final String zzb() {
        return this.zzb;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof zzaz) {
            zzaz zzazVar = (zzaz) obj;
            if (this.zza.equals(zzazVar.zza) && this.zzb.equals(zzazVar.zzb)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return this.zza.hashCode() ^ this.zzb.hashCode();
    }
}
