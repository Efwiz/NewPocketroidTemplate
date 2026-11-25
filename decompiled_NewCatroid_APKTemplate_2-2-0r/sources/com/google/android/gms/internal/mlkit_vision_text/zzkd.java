package com.google.android.gms.internal.mlkit_vision_text;

import com.google.android.datatransport.Priority;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
final class zzkd extends zzkf {
    private String zza;
    private Boolean zzb;
    private Boolean zzc;
    private Priority zzd;
    private Integer zze;

    zzkd() {
    }

    public final zzkf zza(String str) {
        this.zza = "play-services-mlkit-text-recognition";
        return this;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_text.zzkf
    public final zzkf zzb(boolean z) {
        this.zzb = Boolean.valueOf(z);
        return this;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_text.zzkf
    public final zzkf zzc(boolean z) {
        this.zzc = true;
        return this;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_text.zzkf
    public final zzkf zzd(Priority priority) {
        if (priority == null) {
            throw new NullPointerException("Null firelogEventPriority");
        }
        this.zzd = priority;
        return this;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_text.zzkf
    public final zzkf zze(int i) {
        this.zze = 0;
        return this;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_text.zzkf
    public final zzkg zzf() {
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
            return new zzke(this.zza, this.zzb.booleanValue(), this.zzc.booleanValue(), this.zzd, this.zze.intValue(), null);
        }
        String strValueOf = String.valueOf(strConcat);
        throw new IllegalStateException(strValueOf.length() != 0 ? "Missing required properties:".concat(strValueOf) : new String("Missing required properties:"));
    }
}
