package com.google.mlkit.vision.mediapipe;

import com.google.android.gms.tasks.TaskCompletionSource;
import java.nio.ByteBuffer;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes10.dex */
final class zzc implements MediaPipeGraphRunner.Callback {
    private final /* synthetic */ TaskCompletionSource zza;

    zzc(zzb zzbVar, TaskCompletionSource taskCompletionSource) {
        this.zza = taskCompletionSource;
    }

    @Override // com.google.mlkit.vision.mediapipe.MediaPipeGraphRunner.Callback
    public final void zza(ByteBuffer byteBuffer) {
        this.zza.setResult(byteBuffer);
    }

    @Override // com.google.mlkit.vision.mediapipe.MediaPipeGraphRunner.Callback
    public final void zza(String str) {
        this.zza.setException(new Exception(str));
    }
}
