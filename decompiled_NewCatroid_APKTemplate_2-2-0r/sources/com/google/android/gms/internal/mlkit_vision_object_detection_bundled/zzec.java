package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import org.slf4j.Marker;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
final class zzec implements zzct {
    final /* synthetic */ Class zza;
    final /* synthetic */ Class zzb;
    final /* synthetic */ zzcs zzc;

    zzec(Class cls, Class cls2, zzcs zzcsVar) {
        this.zza = cls;
        this.zzb = cls2;
        this.zzc = zzcsVar;
    }

    public final String toString() {
        String name2 = this.zzb.getName();
        String name3 = this.zza.getName();
        String strValueOf = String.valueOf(this.zzc);
        int length = String.valueOf(name2).length();
        StringBuilder sb = new StringBuilder(length + 24 + String.valueOf(name3).length() + String.valueOf(strValueOf).length());
        sb.append("Factory[type=");
        sb.append(name2);
        sb.append(Marker.ANY_NON_NULL_MARKER);
        sb.append(name3);
        sb.append(",adapter=");
        sb.append(strValueOf);
        sb.append("]");
        return sb.toString();
    }
}
