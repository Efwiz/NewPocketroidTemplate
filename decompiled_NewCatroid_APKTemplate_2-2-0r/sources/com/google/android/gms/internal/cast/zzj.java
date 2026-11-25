package com.google.android.gms.internal.cast;

import com.gaurav.avnc.vnc.XKeySym;
import com.google.android.gms.cast.SessionState;
import com.google.android.gms.cast.framework.SessionTransferCallback;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast-framework@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzj extends SessionTransferCallback {
    final /* synthetic */ zzk zza;

    public zzj(zzk zzkVar) {
        this.zza = zzkVar;
    }

    @Override // com.google.android.gms.cast.framework.SessionTransferCallback
    public final void onTransferFailed(int i, int i2) {
        zzk.zza.d("onTransferFailed with type = %d and reason = %d", Integer.valueOf(i), Integer.valueOf(i2));
        this.zza.zzs();
        zzk zzkVar = this.zza;
        this.zza.zzb.zze(zzkVar.zzd.zzf(zzkVar.zzh, i, i2), XKeySym.XK_egrave);
        this.zza.zzj = false;
    }

    @Override // com.google.android.gms.cast.framework.SessionTransferCallback
    public final void onTransferred(int i, SessionState sessionState) {
        zzk.zza.d("onTransferred with type = %d", Integer.valueOf(i));
        this.zza.zzs();
        zzk zzkVar = this.zza;
        this.zza.zzb.zze(zzkVar.zzd.zzg(zzkVar.zzh, i), XKeySym.XK_ccedilla);
        this.zza.zzj = false;
        this.zza.zzh = null;
    }

    @Override // com.google.android.gms.cast.framework.SessionTransferCallback
    public final void onTransferring(int i) {
        zzk.zza.d("onTransferring with type = %d", Integer.valueOf(i));
        this.zza.zzj = true;
        this.zza.zzs();
        zzk zzkVar = this.zza;
        this.zza.zzb.zze(zzkVar.zzd.zzg(zzkVar.zzh, i), XKeySym.XK_ae);
    }
}
