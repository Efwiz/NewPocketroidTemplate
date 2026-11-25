package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

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
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
public final class zznw {
    private static zzau<String> zza;
    private final String zzb;
    private final String zzc;
    private final zznv zzd;
    private final SharedPrefManager zze;
    private final Task<String> zzf;
    private final Task<String> zzg;
    private final String zzh;
    private final Map<zzkp, Long> zzi = new HashMap();
    private final Map<zzkp, zzaz<Object, Long>> zzj = new HashMap();

    public zznw(Context context, final SharedPrefManager sharedPrefManager, zznv zznvVar, final String str) {
        this.zzb = context.getPackageName();
        this.zzc = CommonUtils.getAppVersion(context);
        this.zze = sharedPrefManager;
        this.zzd = zznvVar;
        this.zzh = str;
        this.zzf = MLTaskExecutor.getInstance().scheduleCallable(new Callable() { // from class: com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zznu
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return LibraryVersion.getInstance().getVersion(str);
            }
        });
        MLTaskExecutor mLTaskExecutor = MLTaskExecutor.getInstance();
        sharedPrefManager.getClass();
        this.zzg = mLTaskExecutor.scheduleCallable(new Callable() { // from class: com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zznt
            @Override // java.util.concurrent.Callable
            public final Object call() {
                return sharedPrefManager.getMlSdkInstanceId();
            }
        });
    }

    static long zza(List<Long> list, double d) {
        return list.get(Math.max(((int) Math.ceil((d / 100.0d) * ((double) list.size()))) - 1, 0)).longValue();
    }

    private static synchronized zzau<String> zzg() {
        zzau<String> zzauVar = zza;
        if (zzauVar != null) {
            return zzauVar;
        }
        LocaleListCompat locales = ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration());
        zzar zzarVar = new zzar();
        for (int i = 0; i < locales.size(); i++) {
            zzarVar.zzc(CommonUtils.languageTagFromLocale(locales.get(i)));
        }
        zzau<String> zzauVarZzd = zzarVar.zzd();
        zza = zzauVarZzd;
        return zzauVarZzd;
    }

    private final String zzh() {
        return this.zzf.isSuccessful() ? this.zzf.getResult() : LibraryVersion.getInstance().getVersion(this.zzh);
    }

    private final boolean zzi(zzkp zzkpVar, long j, long j2) {
        return this.zzi.get(zzkpVar) == null || j - this.zzi.get(zzkpVar).longValue() > TimeUnit.SECONDS.toMillis(30L);
    }

    final /* synthetic */ void zzb(zznz zznzVar, zzkp zzkpVar, String str) {
        zznzVar.zzf(zzkpVar);
        String strZzb = zznzVar.zzb();
        zzmt zzmtVar = new zzmt();
        zzmtVar.zzb(this.zzb);
        zzmtVar.zzc(this.zzc);
        zzmtVar.zzh(zzg());
        zzmtVar.zzg(true);
        zzmtVar.zzk(strZzb);
        zzmtVar.zzj(str);
        zzmtVar.zzi(this.zzg.isSuccessful() ? this.zzg.getResult() : this.zze.getMlSdkInstanceId());
        zzmtVar.zzd(10);
        zznzVar.zzg(zzmtVar);
        this.zzd.zza(zznzVar);
    }

    public final void zzc(zznz zznzVar, zzkp zzkpVar) {
        zzd(zznzVar, zzkpVar, zzh());
    }

    public final void zzd(final zznz zznzVar, final zzkp zzkpVar, final String str) {
        final byte[] bArr = null;
        MLTaskExecutor.workerThreadExecutor().execute(new Runnable(zznzVar, zzkpVar, str, bArr) { // from class: com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzns
            public final /* synthetic */ zzkp zzb;
            public final /* synthetic */ String zzc;
            public final /* synthetic */ zznz zzd;

            @Override // java.lang.Runnable
            public final void run() {
                this.zza.zzb(this.zzd, this.zzb, this.zzc);
            }
        });
    }

    public final void zze(com.google.mlkit.vision.objects.defaults.internal.zzd zzdVar, zzkp zzkpVar) {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        if (zzi(zzkpVar, jElapsedRealtime, 30L)) {
            this.zzi.put(zzkpVar, Long.valueOf(jElapsedRealtime));
            zzd(zzdVar.zza(), zzkpVar, zzh());
        }
    }

    public final <K> void zzf(K k, long j, zzkp zzkpVar, com.google.mlkit.vision.objects.defaults.internal.zzc zzcVar) {
        if (!this.zzj.containsKey(zzkpVar)) {
            this.zzj.put(zzkpVar, zzy.zzp());
        }
        zzaz<Object, Long> zzazVar = this.zzj.get(zzkpVar);
        zzazVar.zzm(k, Long.valueOf(j));
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        if (zzi(zzkpVar, jElapsedRealtime, 30L)) {
            this.zzi.put(zzkpVar, Long.valueOf(jElapsedRealtime));
            for (Object obj : zzazVar.zzo()) {
                List<Long> listZzc = zzazVar.zzc(obj);
                Collections.sort(listZzc);
                zzjv zzjvVar = new zzjv();
                Iterator<Long> it = listZzc.iterator();
                long jLongValue = 0;
                while (it.hasNext()) {
                    jLongValue += it.next().longValue();
                }
                zzjvVar.zza(Long.valueOf(jLongValue / ((long) listZzc.size())));
                zzjvVar.zzc(Long.valueOf(zza(listZzc, 100.0d)));
                zzjvVar.zzf(Long.valueOf(zza(listZzc, 75.0d)));
                zzjvVar.zzd(Long.valueOf(zza(listZzc, 50.0d)));
                zzjvVar.zzb(Long.valueOf(zza(listZzc, 25.0d)));
                zzjvVar.zze(Long.valueOf(zza(listZzc, 0.0d)));
                zzjx zzjxVarZzg = zzjvVar.zzg();
                int size = zzazVar.zzc(obj).size();
                zzkq zzkqVar = new zzkq();
                zzkqVar.zze(zzkn.TYPE_THICK);
                zzfd zzfdVar = new zzfd();
                zzfdVar.zza(Integer.valueOf(size));
                zzfdVar.zzc((zzfg) obj);
                zzfdVar.zzb(zzjxVarZzg);
                zzkqVar.zzd(zzfdVar.zze());
                zzd(zznz.zzd(zzkqVar), zzkpVar, zzh());
            }
            this.zzj.remove(zzkpVar);
        }
    }
}
