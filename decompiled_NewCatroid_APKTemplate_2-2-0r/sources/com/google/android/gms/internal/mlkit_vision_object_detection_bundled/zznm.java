package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
final class zznm extends zznp {
    private String zza;
    private Boolean zzb;
    private Integer zzc;

    zznm() {
    }

    @Override // com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zznp
    public final zznp zza(boolean z) {
        this.zzb = true;
        return this;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zznp
    public final zznp zzb(int i) {
        this.zzc = 1;
        return this;
    }

    public final zznp zzc(String str) {
        this.zza = str;
        return this;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zznp
    public final zznq zzd() {
        Boolean bool;
        String str = this.zza;
        if (str != null && (bool = this.zzb) != null && this.zzc != null) {
            return new zzno(str, bool.booleanValue(), this.zzc.intValue(), null);
        }
        StringBuilder sb = new StringBuilder();
        if (this.zza == null) {
            sb.append(" libraryName");
        }
        if (this.zzb == null) {
            sb.append(" enableFirelog");
        }
        if (this.zzc == null) {
            sb.append(" firelogEventType");
        }
        String strValueOf = String.valueOf(sb);
        StringBuilder sb2 = new StringBuilder(String.valueOf(strValueOf).length() + 28);
        sb2.append("Missing required properties:");
        sb2.append(strValueOf);
        throw new IllegalStateException(sb2.toString());
    }
}
