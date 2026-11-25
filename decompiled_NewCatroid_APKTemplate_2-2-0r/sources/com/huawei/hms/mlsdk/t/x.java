package com.huawei.hms.mlsdk.t;

import android.media.AudioTrack;
import android.os.Bundle;
import android.os.Message;

/* JADX INFO: compiled from: AudioPlayer.java */
/* JADX INFO: loaded from: classes10.dex */
class x implements AudioTrack.OnPlaybackPositionUpdateListener {
    final /* synthetic */ w a;

    x(w wVar) {
        this.a = wVar;
    }

    @Override // android.media.AudioTrack.OnPlaybackPositionUpdateListener
    public void onMarkerReached(AudioTrack audioTrack) {
        if (audioTrack.getState() == 0) {
            i0.d("AudioPlayer", "onMarkerReached|STATE_UNINITIALIZED");
            return;
        }
        try {
            Bundle bundle = new Bundle();
            bundle.putInt("position", audioTrack.getPlaybackHeadPosition());
            bundle.putString("taskid", this.a.i);
            this.a.z.sendMessage(Message.obtain(this.a.z, 1, bundle));
        } catch (IllegalStateException e) {
            i0.b("AudioPlayer", a.a("IllegalStateException:").append(e.getMessage()).toString());
        }
    }

    @Override // android.media.AudioTrack.OnPlaybackPositionUpdateListener
    public void onPeriodicNotification(AudioTrack audioTrack) {
        if (audioTrack.getState() == 0) {
            i0.d("AudioPlayer", "onPeriodicNotification|STATE_UNINITIALIZED");
            return;
        }
        try {
            int playbackHeadPosition = audioTrack.getPlaybackHeadPosition() + this.a.t;
            if (!this.a.r && w.a(this.a, playbackHeadPosition)) {
                i0.c("AudioPlayer", "onPeriodicNotification|position=" + playbackHeadPosition);
                this.a.e.a(this.a.i);
            }
            if (this.a.o.isEmpty()) {
                return;
            }
            i0.c("AudioPlayer", "onPeriodicNotification|position=" + playbackHeadPosition + " eventQueue:" + ((c0) this.a.o.peek()).b());
            if (playbackHeadPosition >= ((c0) this.a.o.peek()).b()) {
                c0 c0Var = (c0) this.a.o.poll();
                Bundle bundleA = c0Var.a();
                this.a.e.onRangeStart(c0Var.c(), bundleA.getInt("rangeStart"), bundleA.getInt("rangeEnd"));
            }
        } catch (IllegalStateException e) {
            i0.b("AudioPlayer", a.a("IllegalStateException:").append(e.getMessage()).toString());
        }
    }
}
