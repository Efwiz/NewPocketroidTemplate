package com.google.android.gms.cast.framework.media.internal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompat;
import android.view.KeyEvent;
import com.google.android.gms.cast.MediaSeekOptions;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast-framework@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzs extends MediaSessionCompat.Callback {
    final /* synthetic */ zzv zza;

    zzs(zzv zzvVar) {
        this.zza = zzvVar;
    }

    private final void zza(long j) {
        RemoteMediaClient remoteMediaClient = this.zza.zzp;
        if (remoteMediaClient == null) {
            return;
        }
        zzb(Math.min(remoteMediaClient.getStreamDuration(), Math.max(0L, remoteMediaClient.getApproximateStreamPosition() + j)));
    }

    private final void zzb(long j) {
        RemoteMediaClient remoteMediaClient = this.zza.zzp;
        if (remoteMediaClient == null) {
            return;
        }
        MediaSeekOptions.Builder builder = new MediaSeekOptions.Builder();
        builder.setPosition(j);
        remoteMediaClient.seek(builder.build());
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:17:0x0041  */
    @Override // android.support.v4.media.session.MediaSessionCompat.Callback
    public final void onCustomAction(String str, Bundle bundle) {
        byte b;
        zzv.zzb.d("onCustomAction with action = %s", str);
        switch (str) {
            case "com.google.android.gms.cast.framework.action.REWIND":
                b = 1;
                break;
            case "com.google.android.gms.cast.framework.action.STOP_CASTING":
                b = 2;
                break;
            case "com.google.android.gms.cast.framework.action.DISCONNECT":
                b = 3;
                break;
            case "com.google.android.gms.cast.framework.action.FORWARD":
                b = 0;
                break;
            default:
                b = -1;
                break;
        }
        if (b == 0) {
            zza(this.zza.zzg.getSkipStepMs());
            return;
        }
        if (b == 1) {
            zza(-this.zza.zzg.getSkipStepMs());
            return;
        }
        if (b == 2) {
            zzv zzvVar = this.zza;
            if (zzvVar.zzf != null) {
                zzvVar.zzf.endCurrentSession(true);
                return;
            }
            return;
        }
        if (b != 3) {
            Intent intent = new Intent(str);
            intent.setComponent(this.zza.zzi);
            this.zza.zzc.sendBroadcast(intent);
        } else {
            zzv zzvVar2 = this.zza;
            if (zzvVar2.zzf != null) {
                zzvVar2.zzf.endCurrentSession(false);
            }
        }
    }

    @Override // android.support.v4.media.session.MediaSessionCompat.Callback
    public final boolean onMediaButtonEvent(Intent intent) {
        zzv.zzb.d("onMediaButtonEvent", new Object[0]);
        KeyEvent keyEvent = (KeyEvent) intent.getParcelableExtra("android.intent.extra.KEY_EVENT");
        if (keyEvent == null) {
            return true;
        }
        if (keyEvent.getKeyCode() != 127 && keyEvent.getKeyCode() != 126) {
            return true;
        }
        zzv zzvVar = this.zza;
        if (zzvVar.zzp == null) {
            return true;
        }
        zzvVar.zzp.togglePlayback();
        return true;
    }

    @Override // android.support.v4.media.session.MediaSessionCompat.Callback
    public final void onPause() {
        zzv.zzb.d("onPause", new Object[0]);
        zzv zzvVar = this.zza;
        if (zzvVar.zzp != null) {
            zzvVar.zzp.togglePlayback();
        }
    }

    @Override // android.support.v4.media.session.MediaSessionCompat.Callback
    public final void onPlay() {
        zzv.zzb.d("onPlay", new Object[0]);
        zzv zzvVar = this.zza;
        if (zzvVar.zzp != null) {
            zzvVar.zzp.togglePlayback();
        }
    }

    @Override // android.support.v4.media.session.MediaSessionCompat.Callback
    public final void onSeekTo(long j) {
        zzv.zzb.d("onSeekTo %d", Long.valueOf(j));
        zzb(j);
    }

    @Override // android.support.v4.media.session.MediaSessionCompat.Callback
    public final void onSkipToNext() {
        zzv.zzb.d("onSkipToNext", new Object[0]);
        zzv zzvVar = this.zza;
        if (zzvVar.zzp != null) {
            zzvVar.zzp.queueNext(null);
        }
    }

    @Override // android.support.v4.media.session.MediaSessionCompat.Callback
    public final void onSkipToPrevious() {
        zzv.zzb.d("onSkipToPrevious", new Object[0]);
        zzv zzvVar = this.zza;
        if (zzvVar.zzp != null) {
            zzvVar.zzp.queuePrev(null);
        }
    }
}
