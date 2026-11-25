package com.google.mlkit.vision.text.internal;

import com.google.android.gms.internal.mlkit_vision_text.zzio;
import com.google.android.gms.internal.mlkit_vision_text.zziq;
import com.google.android.gms.internal.mlkit_vision_text.zzjr;
import com.google.android.gms.internal.mlkit_vision_text.zzko;
import com.google.android.gms.internal.mlkit_vision_text.zzkp;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.common.internal.MobileVisionBase;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.TextRecognizerOptions;
import java.util.concurrent.Executor;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes10.dex */
public class TextRecognizerImpl extends MobileVisionBase<Text> implements TextRecognizer {
    private static final TextRecognizerOptions zzb = new TextRecognizerOptions.Builder().build();

    TextRecognizerImpl(zzn zznVar, Executor executor, zzko zzkoVar) {
        super(zznVar, executor);
        zziq zziqVar = new zziq();
        zziqVar.zzc(false);
        zziqVar.zzd(new zzjr().zzc());
        zzkoVar.zzd(zzkp.zzc(zziqVar), zzio.ON_DEVICE_TEXT_CREATE);
    }

    @Override // com.google.mlkit.vision.text.TextRecognizer, com.google.mlkit.vision.common.Detector
    public final Task<Text> process(InputImage inputImage) {
        return super.processBase(inputImage);
    }
}
