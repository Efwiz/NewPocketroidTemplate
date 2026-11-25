package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import java.util.Map;
import java.util.Set;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzcm extends zzcj {
    private final zzdd<String, zzcj> zza = new zzdd<>();

    public final boolean equals(Object obj) {
        if (obj != this) {
            return (obj instanceof zzcm) && ((zzcm) obj).zza.equals(this.zza);
        }
        return true;
    }

    public final int hashCode() {
        return this.zza.hashCode();
    }

    public final zzcm zza(String str) {
        return (zzcm) this.zza.get("authToken");
    }

    public final zzcp zzc(String str) {
        return (zzcp) this.zza.get(str);
    }

    public final Set<Map.Entry<String, zzcj>> zzd() {
        return this.zza.entrySet();
    }

    public final void zze(String str, zzcj zzcjVar) {
        this.zza.put(str, zzcjVar);
    }
}
