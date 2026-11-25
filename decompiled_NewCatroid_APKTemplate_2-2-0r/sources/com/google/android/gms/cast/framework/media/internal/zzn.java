package com.google.android.gms.cast.framework.media.internal;

import android.graphics.Bitmap;
import android.net.Uri;
import com.google.android.gms.common.images.WebImage;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast-framework@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzn {
    public final Uri zza;
    public Bitmap zzb;

    public zzn(WebImage webImage) {
        this.zza = webImage == null ? null : webImage.getUrl();
    }
}
