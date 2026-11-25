package com.google.android.gms.internal.cast;

import com.google.android.gms.cast.Cast;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast-framework@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzcf extends Cast.Listener {
    final /* synthetic */ zzcg zza;

    zzcf(zzcg zzcgVar) {
        this.zza = zzcgVar;
    }

    @Override // com.google.android.gms.cast.Cast.Listener
    public final void onVolumeChanged() {
        this.zza.zza();
    }
}
