package com.google.mlkit.vision.face.internal;

import android.util.Pair;
import com.google.mlkit.common.MlKitException;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import java.util.List;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes10.dex */
interface zzb {
    boolean zza() throws MlKitException;

    Pair<List<Face>, List<Face>> zzb(InputImage inputImage) throws MlKitException;

    void zzc();
}
