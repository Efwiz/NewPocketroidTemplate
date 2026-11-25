package com.google.android.gms.cast.framework.media.widget;

import android.view.View;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast-framework@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzi implements View.OnClickListener {
    final /* synthetic */ ExpandedControllerActivity zza;

    zzi(ExpandedControllerActivity expandedControllerActivity) {
        this.zza = expandedControllerActivity;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        RemoteMediaClient remoteMediaClientZzj;
        if (!this.zza.zzI.isClickable() || (remoteMediaClientZzj = this.zza.zzj()) == null) {
            return;
        }
        remoteMediaClientZzj.skipAd();
    }
}
