package com.google.android.gms.cast.framework.media.uicontroller;

import android.widget.SeekBar;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast-framework@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzi implements SeekBar.OnSeekBarChangeListener {
    final /* synthetic */ SeekBar zza;
    final /* synthetic */ UIMediaController zzb;

    zzi(UIMediaController uIMediaController, SeekBar seekBar) {
        this.zzb = uIMediaController;
        this.zza = seekBar;
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public final void onProgressChanged(SeekBar seekBar, int i, boolean z) {
        RemoteMediaClient remoteMediaClient = this.zzb.getRemoteMediaClient();
        if (remoteMediaClient != null && remoteMediaClient.hasMediaSession() && remoteMediaClient.zzw()) {
            if (z && i < this.zzb.zza.zzd()) {
                UIMediaController uIMediaController = this.zzb;
                SeekBar seekBar2 = this.zza;
                int iZzd = uIMediaController.zza.zzd();
                seekBar2.setProgress(iZzd);
                this.zzb.onSeekBarProgressChanged(seekBar, iZzd, true);
                return;
            }
            if (z && i > this.zzb.zza.zzc()) {
                UIMediaController uIMediaController2 = this.zzb;
                SeekBar seekBar3 = this.zza;
                int iZzc = uIMediaController2.zza.zzc();
                seekBar3.setProgress(iZzc);
                this.zzb.onSeekBarProgressChanged(seekBar, iZzc, true);
                return;
            }
        }
        this.zzb.onSeekBarProgressChanged(seekBar, i, z);
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public final void onStartTrackingTouch(SeekBar seekBar) {
        this.zzb.onSeekBarStartTrackingTouch(seekBar);
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public final void onStopTrackingTouch(SeekBar seekBar) {
        this.zzb.onSeekBarStopTrackingTouch(seekBar);
    }
}
