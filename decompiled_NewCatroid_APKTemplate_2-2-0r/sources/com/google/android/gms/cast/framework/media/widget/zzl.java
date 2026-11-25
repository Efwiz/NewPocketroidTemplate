package com.google.android.gms.cast.framework.media.widget;

import com.google.android.gms.cast.Cast;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast-framework@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzl extends Cast.Listener {
    final /* synthetic */ ExpandedControllerActivity zza;

    zzl(ExpandedControllerActivity expandedControllerActivity) {
        this.zza = expandedControllerActivity;
    }

    @Override // com.google.android.gms.cast.Cast.Listener
    public final void onDeviceNameChanged() {
        this.zza.zzn();
    }
}
