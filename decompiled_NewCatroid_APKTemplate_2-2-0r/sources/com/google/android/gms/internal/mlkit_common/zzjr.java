package com.google.android.gms.internal.mlkit_common;

import android.content.Context;
import android.content.res.Resources;
import androidx.core.os.ConfigurationCompat;
import androidx.core.os.LocaleListCompat;
import com.google.android.gms.common.internal.LibraryVersion;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.common.model.RemoteModel;
import com.google.mlkit.common.sdkinternal.CommonUtils;
import com.google.mlkit.common.sdkinternal.MLTaskExecutor;
import com.google.mlkit.common.sdkinternal.ModelType;
import com.google.mlkit.common.sdkinternal.SharedPrefManager;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/* JADX INFO: compiled from: com.google.mlkit:common@@17.4.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzjr {
    private static zzan<String> zza;
    private final String zzb;
    private final String zzc;
    private final zzjq zzd;
    private final SharedPrefManager zze;
    private final Task<String> zzf;
    private final Task<String> zzg;
    private final String zzh;
    private final Map<zzgz, Long> zzi = new HashMap();
    private final Map<zzgz, Object> zzj = new HashMap();

    public zzjr(Context context, final SharedPrefManager sharedPrefManager, zzjq zzjqVar, final String str) {
        this.zzb = context.getPackageName();
        this.zzc = CommonUtils.getAppVersion(context);
        this.zze = sharedPrefManager;
        this.zzd = zzjqVar;
        this.zzh = str;
        this.zzf = MLTaskExecutor.getInstance().scheduleCallable(new Callable() { // from class: com.google.android.gms.internal.mlkit_common.zzjp
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return LibraryVersion.getInstance().getVersion(str);
            }
        });
        MLTaskExecutor mLTaskExecutor = MLTaskExecutor.getInstance();
        sharedPrefManager.getClass();
        this.zzg = mLTaskExecutor.scheduleCallable(new Callable() { // from class: com.google.android.gms.internal.mlkit_common.zzjo
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return sharedPrefManager.getMlSdkInstanceId();
            }
        });
    }

    private static synchronized zzan<String> zzg() {
        zzan<String> zzanVar = zza;
        if (zzanVar != null) {
            return zzanVar;
        }
        LocaleListCompat locales = ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration());
        zzak zzakVar = new zzak();
        for (int i = 0; i < locales.size(); i++) {
            zzakVar.zzb(CommonUtils.languageTagFromLocale(locales.get(i)));
        }
        zzan<String> zzanVarZzc = zzakVar.zzc();
        zza = zzanVarZzc;
        return zzanVarZzc;
    }

    private final zziu zzh(String str, String str2) {
        zziu zziuVar = new zziu();
        zziuVar.zzb(this.zzb);
        zziuVar.zzc(this.zzc);
        zziuVar.zzh(zzg());
        zziuVar.zzg(true);
        zziuVar.zzk(str);
        zziuVar.zzj(str2);
        zziuVar.zzi(this.zzg.isSuccessful() ? this.zzg.getResult() : this.zze.getMlSdkInstanceId());
        zziuVar.zzd(10);
        return zziuVar;
    }

    private final String zzi() {
        return this.zzf.isSuccessful() ? this.zzf.getResult() : LibraryVersion.getInstance().getVersion(this.zzh);
    }

    final /* synthetic */ void zza(zzji zzjiVar, zzgz zzgzVar, String str) {
        zzjiVar.zza(zzgzVar);
        zzjiVar.zzc(zzh(zzjiVar.zzd(), str));
        this.zzd.zza(zzjiVar);
    }

    final /* synthetic */ void zzb(zzji zzjiVar, zzjt zzjtVar, RemoteModel remoteModel) {
        zzjiVar.zza(zzgz.MODEL_DOWNLOAD);
        zzjiVar.zzc(zzh(zzjtVar.zze(), zzi()));
        zzjiVar.zzb(zzkd.zza(remoteModel, this.zze, zzjtVar));
        this.zzd.zza(zzjiVar);
    }

    public final void zzc(final zzji zzjiVar, final zzgz zzgzVar) {
        final String strZzi = zzi();
        MLTaskExecutor.workerThreadExecutor().execute(new Runnable() { // from class: com.google.android.gms.internal.mlkit_common.zzjm
            @Override // java.lang.Runnable
            public final void run() {
                this.zza.zza(zzjiVar, zzgzVar, strZzi);
            }
        });
    }

    public final void zzd(zzji zzjiVar, RemoteModel remoteModel, boolean z, int i) {
        zzjs zzjsVarZzh = zzjt.zzh();
        zzjsVarZzh.zzf(false);
        zzjsVarZzh.zzd(remoteModel.getModelType());
        zzjsVarZzh.zza(zzhe.FAILED);
        zzjsVarZzh.zzb(zzgy.DOWNLOAD_FAILED);
        zzjsVarZzh.zzc(i);
        zzf(zzjiVar, remoteModel, zzjsVarZzh.zzh());
    }

    public final void zze(zzji zzjiVar, RemoteModel remoteModel, zzgy zzgyVar, boolean z, ModelType modelType, zzhe zzheVar) {
        zzjs zzjsVarZzh = zzjt.zzh();
        zzjsVarZzh.zzf(z);
        zzjsVarZzh.zzd(modelType);
        zzjsVarZzh.zzb(zzgyVar);
        zzjsVarZzh.zza(zzheVar);
        zzf(zzjiVar, remoteModel, zzjsVarZzh.zzh());
    }

    public final void zzf(final zzji zzjiVar, final RemoteModel remoteModel, final zzjt zzjtVar) {
        MLTaskExecutor.workerThreadExecutor().execute(new Runnable() { // from class: com.google.android.gms.internal.mlkit_common.zzjn
            @Override // java.lang.Runnable
            public final void run() {
                this.zza.zzb(zzjiVar, zzjtVar, remoteModel);
            }
        });
    }
}
