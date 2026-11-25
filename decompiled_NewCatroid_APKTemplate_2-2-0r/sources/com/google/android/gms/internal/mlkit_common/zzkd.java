package com.google.android.gms.internal.mlkit_common;

import android.os.SystemClock;
import com.google.android.gms.common.internal.GmsLogger;
import com.google.mlkit.common.model.RemoteModel;
import com.google.mlkit.common.sdkinternal.ModelType;
import com.google.mlkit.common.sdkinternal.SharedPrefManager;

/* JADX INFO: compiled from: com.google.mlkit:common@@17.4.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzkd {
    private static final GmsLogger zza = new GmsLogger("RemoteModelUtils", "");

    public static zzhg zza(RemoteModel remoteModel, SharedPrefManager sharedPrefManager, zzjt zzjtVar) {
        zzhi zzhiVar;
        ModelType modelTypeZzb = zzjtVar.zzb();
        String modelHash = remoteModel.getModelHash();
        zzhm zzhmVar = new zzhm();
        zzhh zzhhVar = new zzhh();
        zzhhVar.zzc(remoteModel.getModelNameForBackend());
        zzhhVar.zzd(zzhj.CLOUD);
        zzhhVar.zza(zzae.zzb(modelHash));
        ModelType modelType = ModelType.UNKNOWN;
        int iOrdinal = modelTypeZzb.ordinal();
        if (iOrdinal == 2) {
            zzhiVar = zzhi.BASE_TRANSLATE;
        } else if (iOrdinal != 4) {
            zzhiVar = iOrdinal != 5 ? zzhi.TYPE_UNKNOWN : zzhi.BASE_DIGITAL_INK;
        } else {
            zzhiVar = zzhi.CUSTOM;
        }
        zzhhVar.zzb(zzhiVar);
        zzhmVar.zzb(zzhhVar.zzg());
        zzhp zzhpVarZzc = zzhmVar.zzc();
        zzhd zzhdVar = new zzhd();
        zzhdVar.zzd(zzjtVar.zzc());
        zzhdVar.zzc(zzjtVar.zzd());
        zzhdVar.zzb(Long.valueOf(zzjtVar.zza()));
        zzhdVar.zzf(zzhpVarZzc);
        if (zzjtVar.zzg()) {
            long modelDownloadBeginTimeMs = sharedPrefManager.getModelDownloadBeginTimeMs(remoteModel);
            if (modelDownloadBeginTimeMs == 0) {
                zza.w("RemoteModelUtils", "Model downloaded without its beginning time recorded.");
            } else {
                long modelFirstUseTimeMs = sharedPrefManager.getModelFirstUseTimeMs(remoteModel);
                if (modelFirstUseTimeMs == 0) {
                    modelFirstUseTimeMs = SystemClock.elapsedRealtime();
                    sharedPrefManager.setModelFirstUseTimeMs(remoteModel, modelFirstUseTimeMs);
                }
                zzhdVar.zzg(Long.valueOf(modelFirstUseTimeMs - modelDownloadBeginTimeMs));
            }
        }
        if (zzjtVar.zzf()) {
            long modelDownloadBeginTimeMs2 = sharedPrefManager.getModelDownloadBeginTimeMs(remoteModel);
            if (modelDownloadBeginTimeMs2 == 0) {
                zza.w("RemoteModelUtils", "Model downloaded without its beginning time recorded.");
            } else {
                zzhdVar.zze(Long.valueOf(SystemClock.elapsedRealtime() - modelDownloadBeginTimeMs2));
            }
        }
        return zzhdVar.zzi();
    }
}
