package com.google.android.gms.cast.framework.media;

import com.google.android.gms.common.api.Status;
import java.util.Iterator;

/* JADX INFO: compiled from: com.google.android.gms:play-services-cast-framework@@21.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzbi implements com.google.android.gms.cast.internal.zzas {
    final /* synthetic */ zzbk zza;

    zzbi(zzbk zzbkVar) {
        this.zza = zzbkVar;
    }

    @Override // com.google.android.gms.cast.internal.zzas
    public final void zza(String str, long j, int i, Object obj, long j2, long j3) {
        try {
            zzbk zzbkVar = this.zza;
            try {
                Status status = new Status(i);
                Object obj2 = obj;
                if (true != (obj2 instanceof com.google.android.gms.cast.internal.zzap)) {
                    obj2 = null;
                }
                zzbkVar.setResult(new zzbl(status, obj2 != null ? ((com.google.android.gms.cast.internal.zzap) obj2).zza : null, obj2 != null ? ((com.google.android.gms.cast.internal.zzap) obj2).zzb : null));
            } catch (IllegalStateException e) {
                e = e;
                RemoteMediaClient.zza.e(e, "Result already set when calling onRequestCompleted", new Object[0]);
            }
        } catch (IllegalStateException e2) {
            e = e2;
        }
        Iterator it = this.zza.zzg.zzj.iterator();
        while (it.hasNext()) {
            ((RemoteMediaClient.Callback) it.next()).zza(str, j, i, j2, j3);
        }
    }

    @Override // com.google.android.gms.cast.internal.zzas
    public final void zzb(String str, long j, long j2, long j3) {
        try {
            zzbk zzbkVar = this.zza;
            zzbkVar.setResult(new zzbj(zzbkVar, new Status(2103)));
        } catch (IllegalStateException e) {
            RemoteMediaClient.zza.e(e, "Result already set when calling onRequestReplaced", new Object[0]);
        }
        Iterator it = this.zza.zzg.zzj.iterator();
        while (it.hasNext()) {
            ((RemoteMediaClient.Callback) it.next()).zza(str, j, 2103, j2, j3);
        }
    }
}
