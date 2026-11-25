package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import java.util.Map;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
final class zzai extends zzw {
    final /* synthetic */ zzak zza;
    private final Object zzb;
    private int zzc;

    zzai(zzak zzakVar, int i) {
        this.zza = zzakVar;
        this.zzb = zzak.zzg(zzakVar, i);
        this.zzc = i;
    }

    private final void zza() {
        int i = this.zzc;
        if (i == -1 || i >= this.zza.size() || !zzd.zza(this.zzb, zzak.zzg(this.zza, this.zzc))) {
            this.zzc = this.zza.zzw(this.zzb);
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzw, java.util.Map.Entry
    public final Object getKey() {
        return this.zzb;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzw, java.util.Map.Entry
    public final Object getValue() {
        Map mapZzl = this.zza.zzl();
        if (mapZzl != null) {
            return mapZzl.get(this.zzb);
        }
        zza();
        int i = this.zzc;
        if (i == -1) {
            return null;
        }
        return zzak.zzj(this.zza, i);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzw, java.util.Map.Entry
    public final Object setValue(Object obj) {
        Map mapZzl = this.zza.zzl();
        if (mapZzl != null) {
            return mapZzl.put(this.zzb, obj);
        }
        zza();
        int i = this.zzc;
        if (i == -1) {
            this.zza.put(this.zzb, obj);
            return null;
        }
        Object objZzj = zzak.zzj(this.zza, i);
        zzak.zzn(this.zza, this.zzc, obj);
        return objZzj;
    }
}
