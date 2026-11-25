package com.google.mlkit.vision.text;

import com.google.mlkit.common.sdkinternal.MlKitContext;
import com.google.mlkit.vision.text.internal.zzk;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes10.dex */
public class TextRecognition {
    private TextRecognition() {
    }

    public static TextRecognizer getClient() {
        return ((zzk) MlKitContext.getInstance().get(zzk.class)).zza();
    }

    public static TextRecognizer getClient(TextRecognizerOptions textRecognizerOptions) {
        return ((zzk) MlKitContext.getInstance().get(zzk.class)).zzb(textRecognizerOptions);
    }
}
