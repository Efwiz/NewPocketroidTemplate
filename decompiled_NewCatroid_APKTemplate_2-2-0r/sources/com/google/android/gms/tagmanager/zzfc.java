package com.google.android.gms.tagmanager;

import android.os.Handler;
import android.os.Message;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzfc implements Handler.Callback {
    final /* synthetic */ zzfe zza;

    zzfc(zzfe zzfeVar) {
        this.zza = zzfeVar;
    }

    @Override // android.os.Handler.Callback
    public final boolean handleMessage(Message message) {
        if (message.what == 1 && zzff.zza.equals(message.obj)) {
            this.zza.zza.zza();
            if (!this.zza.zza.zzm()) {
                this.zza.zzc(1800000L);
            }
        }
        return true;
    }
}
