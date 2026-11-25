package com.google.android.gms.internal.mlkit_vision_face;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes9.dex */
final class zzbd {
    static int zza(int i) {
        return (int) (((long) Integer.rotateLeft((int) (((long) i) * (-862048943)), 15)) * 461845907);
    }

    static int zzb(@NullableDecl Object obj) {
        return zza(obj == null ? 0 : obj.hashCode());
    }
}
