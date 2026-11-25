package com.google.android.gms.internal.cast;

import android.graphics.Bitmap;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast-framework@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzbw implements com.google.android.gms.cast.framework.media.internal.zza {
    final /* synthetic */ zzbx zza;

    zzbw(zzbx zzbxVar) {
        this.zza = zzbxVar;
    }

    @Override // com.google.android.gms.cast.framework.media.internal.zza
    public final void zza(Bitmap bitmap) {
        if (bitmap != null) {
            this.zza.zza.setImageBitmap(bitmap);
        }
    }
}
