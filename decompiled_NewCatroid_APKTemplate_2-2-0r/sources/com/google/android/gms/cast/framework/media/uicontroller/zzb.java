package com.google.android.gms.cast.framework.media.uicontroller;

import android.view.View;
import android.widget.ImageView;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast-framework@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzb implements View.OnClickListener {
    final /* synthetic */ UIMediaController zza;

    zzb(UIMediaController uIMediaController) {
        this.zza = uIMediaController;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        this.zza.onMuteToggleClicked((ImageView) view);
    }
}
