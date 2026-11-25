package com.google.mlkit.vision.mediapipe.pose;

import com.google.android.gms.internal.mlkit_vision_mediapipe.zzcu;
import com.google.android.gms.internal.mlkit_vision_mediapipe.zzfi;
import com.google.mlkit.common.MlKitException;
import com.google.mlkit.vision.mediapipe.Converter;
import java.nio.ByteBuffer;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes10.dex */
public class PoseHolderConverter implements Converter<PoseHolder> {
    private static PoseHolder zzb(ByteBuffer byteBuffer) throws MlKitException {
        try {
            return new PoseHolder(zzcu.zzb.zza(byteBuffer.array()));
        } catch (zzfi e) {
            throw new MlKitException(e.getMessage(), 13);
        }
    }

    @Override // com.google.mlkit.vision.mediapipe.Converter
    public final /* synthetic */ PoseHolder zza(ByteBuffer byteBuffer) throws MlKitException {
        return zzb(byteBuffer);
    }
}
