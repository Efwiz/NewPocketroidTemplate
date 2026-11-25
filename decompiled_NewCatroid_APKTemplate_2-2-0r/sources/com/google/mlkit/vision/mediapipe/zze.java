package com.google.mlkit.vision.mediapipe;

import android.graphics.Bitmap;
import com.google.android.gms.internal.mlkit_vision_mediapipe.zzcv;
import com.google.android.gms.internal.mlkit_vision_mediapipe.zzdf;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes10.dex */
final class zze implements MediaPipeInput {
    private final Bitmap zza;
    private final long zzb;

    zze(Bitmap bitmap, long j) {
        this.zza = bitmap;
        this.zzb = j;
    }

    @Override // com.google.mlkit.vision.mediapipe.MediaPipeInput
    public final zzdf zza(zzcv zzcvVar) {
        return zzcvVar.zza(this.zza);
    }

    @Override // com.google.mlkit.vision.mediapipe.MediaPipeInput
    public final int zza() {
        return this.zza.getWidth();
    }

    @Override // com.google.mlkit.vision.mediapipe.MediaPipeInput
    public final int zzb() {
        return this.zza.getHeight();
    }

    @Override // com.google.mlkit.vision.mediapipe.MediaPipeInput
    public final long zzc() {
        return this.zzb;
    }
}
