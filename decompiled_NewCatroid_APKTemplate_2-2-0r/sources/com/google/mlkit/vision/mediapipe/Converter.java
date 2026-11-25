package com.google.mlkit.vision.mediapipe;

import com.google.mlkit.common.MlKitException;
import java.nio.ByteBuffer;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes10.dex */
public interface Converter<ResultT> {
    ResultT zza(ByteBuffer byteBuffer) throws MlKitException;
}
