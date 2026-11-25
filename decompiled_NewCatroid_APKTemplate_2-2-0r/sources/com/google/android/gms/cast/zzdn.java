package com.google.android.gms.cast;

import android.util.Log;
import com.google.android.gms.common.api.Status;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzdn implements com.google.android.gms.cast.internal.zzas {
    final /* synthetic */ zzdp zza;

    zzdn(zzdp zzdpVar) {
        this.zza = zzdpVar;
    }

    @Override // com.google.android.gms.cast.internal.zzas
    public final void zza(String str, long j, int i, Object obj, long j2, long j3) {
        try {
            zzdp zzdpVar = this.zza;
            Status status = new Status(i);
            if (true != (obj instanceof com.google.android.gms.cast.internal.zzap)) {
                obj = null;
            }
            zzdpVar.setResult(new zzdq(status, obj != null ? ((com.google.android.gms.cast.internal.zzap) obj).zza : null));
        } catch (IllegalStateException e) {
            Log.e("RemoteMediaPlayer", "Result already set when calling onRequestCompleted", e);
        }
    }

    @Override // com.google.android.gms.cast.internal.zzas
    public final void zzb(String str, long j, long j2, long j3) {
        try {
            zzdp zzdpVar = this.zza;
            zzdpVar.setResult(new zzdo(zzdpVar, new Status(2103)));
        } catch (IllegalStateException e) {
            Log.e("RemoteMediaPlayer", "Result already set when calling onRequestReplaced", e);
        }
    }
}
