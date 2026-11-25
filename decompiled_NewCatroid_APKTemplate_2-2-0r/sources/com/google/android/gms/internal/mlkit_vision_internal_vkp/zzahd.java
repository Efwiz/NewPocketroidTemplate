package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import android.content.Context;
import android.content.res.Resources;
import androidx.core.os.ConfigurationCompat;
import androidx.core.os.LocaleListCompat;
import com.google.android.gms.common.internal.LibraryVersion;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.common.sdkinternal.CommonUtils;
import com.google.mlkit.common.sdkinternal.MLTaskExecutor;
import com.google.mlkit.common.sdkinternal.SharedPrefManager;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzahd {
    private static zzkz<String> zza;
    private final String zzb;
    private final String zzc;
    private final zzahc zzd;
    private final SharedPrefManager zze;
    private final Task<String> zzf;
    private final Task<String> zzg;
    private final String zzh;
    private final Map<zzadr, Long> zzi = new HashMap();
    private final Map<zzadr, Object> zzj = new HashMap();

    public zzahd(Context context, final SharedPrefManager sharedPrefManager, zzahc zzahcVar, final String str) {
        this.zzb = context.getPackageName();
        this.zzc = CommonUtils.getAppVersion(context);
        this.zze = sharedPrefManager;
        this.zzd = zzahcVar;
        this.zzh = str;
        this.zzf = MLTaskExecutor.getInstance().scheduleCallable(new Callable() { // from class: com.google.android.gms.internal.mlkit_vision_internal_vkp.zzahb
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return LibraryVersion.getInstance().getVersion(str);
            }
        });
        MLTaskExecutor mLTaskExecutor = MLTaskExecutor.getInstance();
        sharedPrefManager.getClass();
        this.zzg = mLTaskExecutor.scheduleCallable(new Callable() { // from class: com.google.android.gms.internal.mlkit_vision_internal_vkp.zzaha
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return sharedPrefManager.getMlSdkInstanceId();
            }
        });
    }

    private static synchronized zzkz<String> zzd() {
        zzkz<String> zzkzVar = zza;
        if (zzkzVar != null) {
            return zzkzVar;
        }
        LocaleListCompat locales = ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration());
        zzkw zzkwVar = new zzkw();
        for (int i = 0; i < locales.size(); i++) {
            zzkwVar.zzb(CommonUtils.languageTagFromLocale(locales.get(i)));
        }
        zzkz<String> zzkzVarZzc = zzkwVar.zzc();
        zza = zzkzVarZzc;
        return zzkzVarZzc;
    }

    final /* synthetic */ void zza(zzagv zzagvVar, zzadr zzadrVar, String str) {
        zzagvVar.zzb(zzadrVar);
        String strZzd = zzagvVar.zzd();
        zzafg zzafgVar = new zzafg();
        zzafgVar.zzb(this.zzb);
        zzafgVar.zzc(this.zzc);
        zzafgVar.zzh(zzd());
        zzafgVar.zzg(true);
        zzafgVar.zzk(strZzd);
        zzafgVar.zzj(str);
        zzafgVar.zzi(this.zzg.isSuccessful() ? this.zzg.getResult() : this.zze.getMlSdkInstanceId());
        zzafgVar.zzd(10);
        zzagvVar.zzc(zzafgVar);
        this.zzd.zza(zzagvVar);
    }

    public final void zzb(zzagv zzagvVar, zzadr zzadrVar) {
        zzc(zzagvVar, zzadrVar, this.zzf.isSuccessful() ? this.zzf.getResult() : LibraryVersion.getInstance().getVersion(this.zzh));
    }

    public final void zzc(final zzagv zzagvVar, final zzadr zzadrVar, final String str) {
        MLTaskExecutor.workerThreadExecutor().execute(new Runnable() { // from class: com.google.android.gms.internal.mlkit_vision_internal_vkp.zzagz
            @Override // java.lang.Runnable
            public final void run() {
                this.zza.zza(zzagvVar, zzadrVar, str);
            }
        });
    }
}
