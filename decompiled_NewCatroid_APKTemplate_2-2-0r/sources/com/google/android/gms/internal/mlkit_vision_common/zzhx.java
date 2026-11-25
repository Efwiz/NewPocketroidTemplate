package com.google.android.gms.internal.mlkit_vision_common;

import android.content.Context;
import android.content.res.Resources;
import android.os.SystemClock;
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
import java.util.concurrent.TimeUnit;

/* JADX INFO: compiled from: com.google.mlkit:vision-common@@16.6.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzhx {
    private static zzo<String> zza;
    private final String zzb;
    private final String zzc;
    private final zzhw zzd;
    private final SharedPrefManager zze;
    private final Task<String> zzf;
    private final Task<String> zzg;
    private final String zzh;
    private final Map<zzfs, Long> zzi = new HashMap();
    private final Map<zzfs, Object> zzj = new HashMap();

    public zzhx(Context context, final SharedPrefManager sharedPrefManager, zzhw zzhwVar, final String str) {
        this.zzb = context.getPackageName();
        this.zzc = CommonUtils.getAppVersion(context);
        this.zze = sharedPrefManager;
        this.zzd = zzhwVar;
        this.zzh = str;
        this.zzf = MLTaskExecutor.getInstance().scheduleCallable(new Callable() { // from class: com.google.android.gms.internal.mlkit_vision_common.zzhv
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return LibraryVersion.getInstance().getVersion(str);
            }
        });
        MLTaskExecutor mLTaskExecutor = MLTaskExecutor.getInstance();
        sharedPrefManager.getClass();
        this.zzg = mLTaskExecutor.scheduleCallable(new Callable() { // from class: com.google.android.gms.internal.mlkit_vision_common.zzhu
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return sharedPrefManager.getMlSdkInstanceId();
            }
        });
    }

    private static synchronized zzo<String> zzc() {
        zzo<String> zzoVar = zza;
        if (zzoVar != null) {
            return zzoVar;
        }
        LocaleListCompat locales = ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration());
        zzl zzlVar = new zzl();
        for (int i = 0; i < locales.size(); i++) {
            zzlVar.zzb(CommonUtils.languageTagFromLocale(locales.get(i)));
        }
        zzo<String> zzoVarZzc = zzlVar.zzc();
        zza = zzoVarZzc;
        return zzoVarZzc;
    }

    final /* synthetic */ void zza(zzhy zzhyVar, zzfs zzfsVar, String str) {
        zzhyVar.zzd(zzfsVar);
        String strZza = zzhyVar.zza();
        zzhe zzheVar = new zzhe();
        zzheVar.zzb(this.zzb);
        zzheVar.zzc(this.zzc);
        zzheVar.zzh(zzc());
        zzheVar.zzg(true);
        zzheVar.zzk(strZza);
        zzheVar.zzj(str);
        zzheVar.zzi(this.zzg.isSuccessful() ? this.zzg.getResult() : this.zze.getMlSdkInstanceId());
        zzheVar.zzd(10);
        zzhyVar.zze(zzheVar);
        this.zzd.zza(zzhyVar);
    }

    public final void zzb(zzih zzihVar, final zzfs zzfsVar) {
        zzfg zzfgVar;
        zzfl zzflVar;
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        if (this.zzi.get(zzfsVar) != null && jElapsedRealtime - this.zzi.get(zzfsVar).longValue() <= TimeUnit.SECONDS.toMillis(30L)) {
            return;
        }
        this.zzi.put(zzfsVar, Long.valueOf(jElapsedRealtime));
        int i = zzihVar.zza;
        int i2 = zzihVar.zzb;
        int i3 = zzihVar.zzc;
        int i4 = zzihVar.zzd;
        int i5 = zzihVar.zze;
        long j = zzihVar.zzf;
        int i6 = zzihVar.zzg;
        zzfk zzfkVar = new zzfk();
        if (i == -1) {
            zzfgVar = zzfg.BITMAP;
        } else if (i == 35) {
            zzfgVar = zzfg.YUV_420_888;
        } else if (i == 842094169) {
            zzfgVar = zzfg.YV12;
        } else if (i != 16) {
            zzfgVar = i != 17 ? zzfg.UNKNOWN_FORMAT : zzfg.NV21;
        } else {
            zzfgVar = zzfg.NV16;
        }
        zzfkVar.zzd(zzfgVar);
        if (i2 == 1) {
            zzflVar = zzfl.BITMAP;
        } else if (i2 == 2) {
            zzflVar = zzfl.BYTEARRAY;
        } else if (i2 != 3) {
            zzflVar = i2 != 4 ? zzfl.ANDROID_MEDIA_IMAGE : zzfl.FILEPATH;
        } else {
            zzflVar = zzfl.BYTEBUFFER;
        }
        zzfkVar.zzf(zzflVar);
        zzfkVar.zzc(Integer.valueOf(i3));
        zzfkVar.zze(Integer.valueOf(i4));
        zzfkVar.zzg(Integer.valueOf(i5));
        zzfkVar.zzb(Long.valueOf(j));
        zzfkVar.zzh(Integer.valueOf(i6));
        zzfn zzfnVarZzj = zzfkVar.zzj();
        zzft zzftVar = new zzft();
        zzftVar.zzd(zzfnVarZzj);
        final zzhy zzhyVarZzc = zzhy.zzc(zzftVar);
        final String result = this.zzf.isSuccessful() ? this.zzf.getResult() : LibraryVersion.getInstance().getVersion(this.zzh);
        final byte[] bArr = null;
        MLTaskExecutor.workerThreadExecutor().execute(new Runnable(zzhyVarZzc, zzfsVar, result, bArr) { // from class: com.google.android.gms.internal.mlkit_vision_common.zzht
            public final /* synthetic */ zzfs zzb;
            public final /* synthetic */ String zzc;
            public final /* synthetic */ zzhy zzd;

            @Override // java.lang.Runnable
            public final void run() {
                this.zza.zza(this.zzd, this.zzb, this.zzc);
            }
        });
    }
}
