package com.google.mlkit.vision.text.internal;

import com.google.android.gms.internal.mlkit_vision_text.zzkz;
import com.google.mlkit.common.sdkinternal.ExecutorSelector;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.TextRecognizerOptions;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes10.dex */
public final class zzk {
    private final zzn zza;
    private final ExecutorSelector zzb;

    zzk(zzn zznVar, ExecutorSelector executorSelector) {
        this.zza = zznVar;
        this.zzb = executorSelector;
    }

    public final TextRecognizer zza() {
        return zzb(TextRecognizerImpl.zzb);
    }

    public final TextRecognizer zzb(TextRecognizerOptions textRecognizerOptions) {
        return new TextRecognizerImpl(this.zza, this.zzb.getExecutorToUse(textRecognizerOptions.zza()), zzkz.zza("play-services-mlkit-text-recognition"));
    }
}
