package com.google.mlkit.vision.mediapipe;

import com.google.android.gms.internal.mlkit_vision_mediapipe.zzcv;
import com.google.android.gms.internal.mlkit_vision_mediapipe.zzdf;
import java.nio.ByteBuffer;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes10.dex */
final class zzd implements MediaPipeInput {
    private final ByteBuffer zza;
    private final int zzb;
    private final int zzc;
    private final long zzd;

    zzd(ByteBuffer byteBuffer, int i, int i2, long j) {
        this.zza = byteBuffer;
        this.zzb = i;
        this.zzc = i2;
        this.zzd = j;
    }

    @Override // com.google.mlkit.vision.mediapipe.MediaPipeInput
    public final zzdf zza(zzcv zzcvVar) {
        return zzcvVar.zza(this.zza, this.zzb, this.zzc);
    }

    @Override // com.google.mlkit.vision.mediapipe.MediaPipeInput
    public final int zza() {
        return this.zzb;
    }

    @Override // com.google.mlkit.vision.mediapipe.MediaPipeInput
    public final int zzb() {
        return this.zzc;
    }

    @Override // com.google.mlkit.vision.mediapipe.MediaPipeInput
    public final long zzc() {
        return this.zzd;
    }
}
