package com.google.android.gms.internal.mlkit_vision_face;

import com.google.android.datatransport.Priority;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes9.dex */
final class zzkf extends zzkh {
    private String zza;
    private Boolean zzb;
    private Boolean zzc;
    private Priority zzd;
    private Integer zze;

    zzkf() {
    }

    public final zzkh zza(String str) {
        if (str == null) {
            throw new NullPointerException("Null libraryName");
        }
        this.zza = str;
        return this;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_face.zzkh
    public final zzkh zzb(boolean z) {
        this.zzb = Boolean.valueOf(z);
        return this;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_face.zzkh
    public final zzkh zzc(boolean z) {
        this.zzc = true;
        return this;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_face.zzkh
    public final zzkh zzd(Priority priority) {
        if (priority == null) {
            throw new NullPointerException("Null firelogEventPriority");
        }
        this.zzd = priority;
        return this;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_face.zzkh
    public final zzkh zze(int i) {
        this.zze = 1;
        return this;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_face.zzkh
    public final zzki zzf() {
        String strConcat = this.zza == null ? " libraryName" : "";
        if (this.zzb == null) {
            strConcat = strConcat.concat(" enableClearcut");
        }
        if (this.zzc == null) {
            strConcat = String.valueOf(strConcat).concat(" enableFirelog");
        }
        if (this.zzd == null) {
            strConcat = String.valueOf(strConcat).concat(" firelogEventPriority");
        }
        if (this.zze == null) {
            strConcat = String.valueOf(strConcat).concat(" firelogEventType");
        }
        if (strConcat.isEmpty()) {
            return new zzkg(this.zza, this.zzb.booleanValue(), this.zzc.booleanValue(), this.zzd, this.zze.intValue(), null);
        }
        String strValueOf = String.valueOf(strConcat);
        throw new IllegalStateException(strValueOf.length() != 0 ? "Missing required properties:".concat(strValueOf) : new String("Missing required properties:"));
    }
}
