package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
public final class zznd {
    private final String zzb;
    private final String zza = "722550545529";
    private final String zzc = "82c62205f0ef0ea96608a8";

    public zznd(String str, String str2, String str3) {
        this.zzb = str2;
    }

    public final String zza() {
        return this.zzb;
    }

    public final String zzb() {
        String str = this.zza;
        String str2 = this.zzc;
        StringBuilder sb = new StringBuilder(str.length() + 11 + str2.length());
        sb.append("1:");
        sb.append(str);
        sb.append(":android:");
        sb.append(str2);
        return sb.toString();
    }

    public final String zzc() {
        return this.zza;
    }
}
