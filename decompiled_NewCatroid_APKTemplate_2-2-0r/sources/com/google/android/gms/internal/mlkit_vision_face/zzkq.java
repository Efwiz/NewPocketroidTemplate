package com.google.android.gms.internal.mlkit_vision_face;

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

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-face-detection@@16.1.4 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzkq {
    private static zzbl<String> zza;
    private final String zzb;
    private final String zzc;
    private final zzkp zzd;
    private final SharedPrefManager zze;
    private final Task<String> zzf;
    private final Task<String> zzg;
    private final String zzh;
    private final Map<zziq, Long> zzi = new HashMap();
    private final Map<zziq, zzbp<Object, Long>> zzj = new HashMap();

    public zzkq(Context context, SharedPrefManager sharedPrefManager, zzkp zzkpVar, final String str) {
        this.zzb = context.getPackageName();
        this.zzc = CommonUtils.getAppVersion(context);
        this.zze = sharedPrefManager;
        this.zzd = zzkpVar;
        this.zzh = str;
        this.zzf = MLTaskExecutor.getInstance().scheduleCallable(new Callable(str) { // from class: com.google.android.gms.internal.mlkit_vision_face.zzkk
            private final String zza;

            {
                this.zza = str;
            }

            @Override // java.util.concurrent.Callable
            public final Object call() {
                return LibraryVersion.getInstance().getVersion(this.zza);
            }
        });
        MLTaskExecutor mLTaskExecutor = MLTaskExecutor.getInstance();
        sharedPrefManager.getClass();
        this.zzg = mLTaskExecutor.scheduleCallable(zzkl.zza(sharedPrefManager));
    }

    static long zzc(List<Long> list, double d) {
        return list.get(Math.max(((int) Math.ceil((d / 100.0d) * ((double) list.size()))) - 1, 0)).longValue();
    }

    private final boolean zzf(zziq zziqVar, long j, long j2) {
        return this.zzi.get(zziqVar) == null || j - this.zzi.get(zziqVar).longValue() > TimeUnit.SECONDS.toMillis(30L);
    }

    private static synchronized zzbl<String> zzg() {
        zzbl<String> zzblVar = zza;
        if (zzblVar != null) {
            return zzblVar;
        }
        LocaleListCompat locales = ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration());
        zzbi zzbiVar = new zzbi();
        for (int i = 0; i < locales.size(); i++) {
            zzbiVar.zzb(CommonUtils.languageTagFromLocale(locales.get(i)));
        }
        zzbl<String> zzblVarZzc = zzbiVar.zzc();
        zza = zzblVarZzc;
        return zzblVarZzc;
    }

    public final void zza(zzko zzkoVar, zziq zziqVar) {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        if (zzf(zziqVar, jElapsedRealtime, 30L)) {
            this.zzi.put(zziqVar, Long.valueOf(jElapsedRealtime));
            zzd(zzkoVar.zza(), zziqVar);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <K> void zzb(K k, long j, zziq zziqVar, zzkn<K> zzknVar) {
        if (!this.zzj.containsKey(zziqVar)) {
            this.zzj.put(zziqVar, zzar.zzr());
        }
        zzbp<Object, Long> zzbpVar = this.zzj.get(zziqVar);
        zzbpVar.zzd(k, Long.valueOf(j));
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        if (zzf(zziqVar, jElapsedRealtime, 30L)) {
            this.zzi.put(zziqVar, Long.valueOf(jElapsedRealtime));
            for (Object obj : zzbpVar.zzp()) {
                List<Long> listZzb = zzbpVar.zzb(obj);
                Collections.sort(listZzb);
                zzhv zzhvVar = new zzhv();
                Iterator<Long> it = listZzb.iterator();
                long jLongValue = 0;
                while (it.hasNext()) {
                    jLongValue += it.next().longValue();
                }
                zzhvVar.zzc(Long.valueOf(jLongValue / ((long) listZzb.size())));
                zzhvVar.zza(Long.valueOf(zzc(listZzb, 100.0d)));
                zzhvVar.zzf(Long.valueOf(zzc(listZzb, 75.0d)));
                zzhvVar.zze(Long.valueOf(zzc(listZzb, 50.0d)));
                zzhvVar.zzd(Long.valueOf(zzc(listZzb, 25.0d)));
                zzhvVar.zzb(Long.valueOf(zzc(listZzb, 0.0d)));
                zzd(zzknVar.zza(obj, zzbpVar.zzb(obj).size(), zzhvVar.zzg()), zziqVar);
            }
            this.zzj.remove(zziqVar);
        }
    }

    public final void zzd(final zzks zzksVar, final zziq zziqVar) {
        final byte[] bArr = null;
        MLTaskExecutor.workerThreadExecutor().execute(new Runnable(this, zzksVar, zziqVar, bArr) { // from class: com.google.android.gms.internal.mlkit_vision_face.zzkm
            private final zzkq zza;
            private final zziq zzb;
            private final zzks zzc;

            {
                this.zza = this;
                this.zzc = zzksVar;
                this.zzb = zziqVar;
            }

            @Override // java.lang.Runnable
            public final void run() {
                this.zza.zze(this.zzc, this.zzb);
            }
        });
    }

    final /* synthetic */ void zze(zzks zzksVar, zziq zziqVar) {
        zzksVar.zze(zziqVar);
        String strZzb = zzksVar.zzb();
        zzjz zzjzVar = new zzjz();
        zzjzVar.zza(this.zzb);
        zzjzVar.zzb(this.zzc);
        zzjzVar.zze(zzg());
        zzjzVar.zzh(true);
        zzjzVar.zzd(strZzb);
        zzjzVar.zzc(this.zzf.isSuccessful() ? this.zzf.getResult() : LibraryVersion.getInstance().getVersion(this.zzh));
        zzjzVar.zzf(this.zzg.isSuccessful() ? this.zzg.getResult() : this.zze.getMlSdkInstanceId());
        zzjzVar.zzj(10);
        zzksVar.zzd(zzjzVar);
        this.zzd.zza(zzksVar);
    }
}
