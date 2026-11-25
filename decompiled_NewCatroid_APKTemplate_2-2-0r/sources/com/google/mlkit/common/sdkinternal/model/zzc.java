package com.google.mlkit.common.sdkinternal.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.google.android.gms.internal.mlkit_common.zzgy;
import com.google.android.gms.internal.mlkit_common.zzhe;
import com.google.android.gms.internal.mlkit_common.zzji;
import com.google.android.gms.internal.mlkit_common.zzjr;
import com.google.android.gms.internal.mlkit_common.zzjs;
import com.google.android.gms.internal.mlkit_common.zzjt;
import com.google.android.gms.internal.mlkit_common.zzju;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.mlkit.common.MlKitException;
import com.google.mlkit.common.model.RemoteModel;

/* JADX INFO: compiled from: com.google.mlkit:common@@17.4.0 */
/* JADX INFO: loaded from: classes10.dex */
final class zzc extends BroadcastReceiver {
    final /* synthetic */ RemoteModelDownloadManager zza;
    private final long zzb;
    private final TaskCompletionSource<Void> zzc;

    /* synthetic */ zzc(RemoteModelDownloadManager remoteModelDownloadManager, long j, TaskCompletionSource taskCompletionSource, zzb zzbVar) {
        this.zza = remoteModelDownloadManager;
        this.zzb = j;
        this.zzc = taskCompletionSource;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        long longExtra = intent.getLongExtra("extra_download_id", -1L);
        if (longExtra != this.zzb) {
            return;
        }
        Integer downloadingModelStatusCode = this.zza.getDownloadingModelStatusCode();
        synchronized (this.zza) {
            try {
                this.zza.zze.getApplicationContext().unregisterReceiver(this);
            } catch (IllegalArgumentException e) {
                RemoteModelDownloadManager.zza.w("ModelDownloadManager", "Exception thrown while trying to unregister the broadcast receiver for the download", e);
            }
            this.zza.zzc.remove(this.zzb);
            this.zza.zzd.remove(this.zzb);
        }
        if (downloadingModelStatusCode != null) {
            if (downloadingModelStatusCode.intValue() == 16) {
                zzjr zzjrVar = this.zza.zzi;
                zzji zzjiVarZzg = zzju.zzg();
                RemoteModel remoteModel = this.zza.zzg;
                RemoteModelDownloadManager remoteModelDownloadManager = this.zza;
                Long lValueOf = Long.valueOf(longExtra);
                zzjrVar.zzd(zzjiVarZzg, remoteModel, false, remoteModelDownloadManager.getFailureReason(lValueOf));
                this.zzc.setException(this.zza.zzl(lValueOf));
                return;
            }
            if (downloadingModelStatusCode.intValue() == 8) {
                zzjr zzjrVar2 = this.zza.zzi;
                zzji zzjiVarZzg2 = zzju.zzg();
                RemoteModel remoteModel2 = this.zza.zzg;
                zzjs zzjsVarZzh = zzjt.zzh();
                zzjsVarZzh.zzb(zzgy.NO_ERROR);
                zzjsVarZzh.zze(true);
                zzjsVarZzh.zzd(this.zza.zzg.getModelType());
                zzjsVarZzh.zza(zzhe.SUCCEEDED);
                zzjrVar2.zzf(zzjiVarZzg2, remoteModel2, zzjsVarZzh.zzh());
                this.zzc.setResult(null);
                return;
            }
        }
        this.zza.zzi.zzd(zzju.zzg(), this.zza.zzg, false, 0);
        this.zzc.setException(new MlKitException("Model downloading failed", 13));
    }
}
