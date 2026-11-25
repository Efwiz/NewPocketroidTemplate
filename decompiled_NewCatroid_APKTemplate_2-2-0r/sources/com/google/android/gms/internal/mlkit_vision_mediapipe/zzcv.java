package com.google.android.gms.internal.mlkit_vision_mediapipe;

import android.graphics.Bitmap;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzcv extends zzdh {
    public zzcv(zzcx zzcxVar) {
        super(zzcxVar);
    }

    private final native long zza(long j, Bitmap bitmap);

    public final zzdf zza(Bitmap bitmap) {
        if (bitmap.getConfig() != Bitmap.Config.ARGB_8888) {
            throw new RuntimeException("bitmap must use ARGB_8888 config.");
        }
        return zzdf.zza(zza(this.zza.zza(), bitmap));
    }
}
