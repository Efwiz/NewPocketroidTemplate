package com.google.android.gms.tagmanager;

import android.os.Handler;
import android.os.Message;
import com.google.android.gms.internal.gtm.zzga;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzfe implements zzfb {
    final /* synthetic */ zzff zza;
    private final Handler zzb;

    /* synthetic */ zzfe(zzff zzffVar, zzfd zzfdVar) {
        this.zza = zzffVar;
        this.zzb = new zzga(zzffVar.zzc.getMainLooper(), new zzfc(this));
    }

    private final Message zzd() {
        return this.zzb.obtainMessage(1, zzff.zza);
    }

    @Override // com.google.android.gms.tagmanager.zzfb
    public final void zza() {
        this.zzb.removeMessages(1, zzff.zza);
    }

    @Override // com.google.android.gms.tagmanager.zzfb
    public final void zzb() {
        this.zzb.removeMessages(1, zzff.zza);
        this.zzb.sendMessage(zzd());
    }

    @Override // com.google.android.gms.tagmanager.zzfb
    public final void zzc(long j) {
        this.zzb.removeMessages(1, zzff.zza);
        this.zzb.sendMessageDelayed(zzd(), 1800000L);
    }
}
