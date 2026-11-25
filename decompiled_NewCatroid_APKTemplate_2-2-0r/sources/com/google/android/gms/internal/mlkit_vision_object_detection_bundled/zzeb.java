package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
final class zzeb implements zzct {
    final /* synthetic */ Class zza;
    final /* synthetic */ zzcs zzb;

    zzeb(Class cls, zzcs zzcsVar) {
        this.zza = cls;
        this.zzb = zzcsVar;
    }

    public final String toString() {
        String name2 = this.zza.getName();
        String strValueOf = String.valueOf(this.zzb);
        StringBuilder sb = new StringBuilder(String.valueOf(name2).length() + 23 + String.valueOf(strValueOf).length());
        sb.append("Factory[type=");
        sb.append(name2);
        sb.append(",adapter=");
        sb.append(strValueOf);
        sb.append("]");
        return sb.toString();
    }
}
