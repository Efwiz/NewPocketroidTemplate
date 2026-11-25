package com.google.android.gms.internal.cast;

import android.view.Choreographer;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast-framework@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzem extends zzen {
    private final Choreographer zza = Choreographer.getInstance();

    @Override // com.google.android.gms.internal.cast.zzen
    public final void zza(zzek zzekVar) {
        this.zza.postFrameCallback(zzekVar.zzb());
    }
}
