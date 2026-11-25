package com.google.android.gms.internal.mlkit_vision_mediapipe;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbb implements zzbe {
    private final String zza;
    private final boolean zzb;

    private zzbb(String str, boolean z) {
        this.zza = str;
        this.zzb = true;
    }

    public zzbb() {
        this("", true);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzbe
    public final zzal zza(String str) {
        return new zzbc(this.zza, str, this.zzb);
    }
}
