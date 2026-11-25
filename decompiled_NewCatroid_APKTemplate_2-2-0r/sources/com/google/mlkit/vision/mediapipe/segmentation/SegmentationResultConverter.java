package com.google.mlkit.vision.mediapipe.segmentation;

import com.google.mlkit.common.MlKitException;
import com.google.mlkit.vision.mediapipe.Converter;
import java.nio.ByteBuffer;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes10.dex */
public class SegmentationResultConverter implements Converter<SegmentationResultHolder> {
    private final int zza;
    private final int zzb;

    public SegmentationResultConverter(int i, int i2) {
        this.zza = i;
        this.zzb = i2;
    }

    @Override // com.google.mlkit.vision.mediapipe.Converter
    public final /* synthetic */ SegmentationResultHolder zza(ByteBuffer byteBuffer) throws MlKitException {
        return new SegmentationResultHolder(byteBuffer, this.zza, this.zzb);
    }
}
