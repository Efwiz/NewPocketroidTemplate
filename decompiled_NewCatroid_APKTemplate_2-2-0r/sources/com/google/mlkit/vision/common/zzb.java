package com.google.mlkit.vision.common;

import android.media.Image;

/* JADX INFO: compiled from: com.google.mlkit:vision-common@@16.6.0 */
/* JADX INFO: loaded from: classes10.dex */
final class zzb {
    private final Image zza;

    zzb(Image image) {
        this.zza = image;
    }

    final Image zza() {
        return this.zza;
    }

    final Image.Plane[] zzb() {
        return this.zza.getPlanes();
    }
}
