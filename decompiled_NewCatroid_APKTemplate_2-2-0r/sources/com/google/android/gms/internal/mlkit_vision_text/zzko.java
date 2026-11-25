package com.google.android.gms.internal.mlkit_vision_text;

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

/* JADX INFO: compiled from: com.google.android.gms:play-services-mlkit-text-recognition@@16.1.3 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzko {
    private static zzbl<String> zza;
    private final String zzb;
    private final String zzc;
    private final zzkn zzd;
    private final SharedPrefManager zze;
    private final Task<String> zzf;
    private final Task<String> zzg;
    private final String zzh;
    private final Map<zzio, Long> zzi = new HashMap();
    private final Map<zzio, zzbp<Object, Long>> zzj = new HashMap();

    public zzko(Context context, SharedPrefManager sharedPrefManager, zzkn zzknVar, final String str) {
        this.zzb = context.getPackageName();
        this.zzc = CommonUtils.getAppVersion(context);
        this.zze = sharedPrefManager;
        this.zzd = zzknVar;
        this.zzh = str;
        this.zzf = MLTaskExecutor.getInstance().scheduleCallable(new Callable(str) { // from class: com.google.android.gms.internal.mlkit_vision_text.zzki
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
        this.zzg = mLTaskExecutor.scheduleCallable(zzkj.zza(sharedPrefManager));
    }

    static long zzc(List<Long> list, double d) {
        return list.get(Math.max(((int) Math.ceil((d / 100.0d) * ((double) list.size()))) - 1, 0)).longValue();
    }

    private final boolean zzf(zzio zzioVar, long j, long j2) {
        return this.zzi.get(zzioVar) == null || j - this.zzi.get(zzioVar).longValue() > TimeUnit.SECONDS.toMillis(30L);
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

    public final void zza(zzkm zzkmVar, zzio zzioVar) {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        if (zzf(zzioVar, jElapsedRealtime, 30L)) {
            this.zzi.put(zzioVar, Long.valueOf(jElapsedRealtime));
            zzd(zzkmVar.zza(), zzioVar);
        }
    }

    public final <K> void zzb(K k, long j, zzio zzioVar, zzkl<K> zzklVar) {
        if (!this.zzj.containsKey(zzioVar)) {
            this.zzj.put(zzioVar, zzar.zzr());
        }
        zzbp<Object, Long> zzbpVar = this.zzj.get(zzioVar);
        zzbpVar.zzd(k, Long.valueOf(j));
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        if (zzf(zzioVar, jElapsedRealtime, 30L)) {
            this.zzi.put(zzioVar, Long.valueOf(jElapsedRealtime));
            for (Object obj : zzbpVar.zzp()) {
                List<Long> listZzb = zzbpVar.zzb(obj);
                Collections.sort(listZzb);
                zzhz zzhzVar = new zzhz();
                Iterator<Long> it = listZzb.iterator();
                long jLongValue = 0;
                while (it.hasNext()) {
                    jLongValue += it.next().longValue();
                }
                zzhzVar.zzc(Long.valueOf(jLongValue / ((long) listZzb.size())));
                zzhzVar.zza(Long.valueOf(zzc(listZzb, 100.0d)));
                zzhzVar.zzf(Long.valueOf(zzc(listZzb, 75.0d)));
                zzhzVar.zze(Long.valueOf(zzc(listZzb, 50.0d)));
                zzhzVar.zzd(Long.valueOf(zzc(listZzb, 25.0d)));
                zzhzVar.zzb(Long.valueOf(zzc(listZzb, 0.0d)));
                zzia zziaVarZzg = zzhzVar.zzg();
                int size = zzbpVar.zzb(obj).size();
                zziq zziqVar = new zziq();
                zziqVar.zzc(false);
                zzea zzeaVar = new zzea();
                zzeaVar.zzb(Integer.valueOf(size));
                zzeaVar.zza((zzec) obj);
                zzeaVar.zzc(zziaVarZzg);
                zziqVar.zze(zzeaVar.zzd());
                zzd(zzkp.zzc(zziqVar), zzioVar);
            }
            this.zzj.remove(zzioVar);
        }
    }

    public final void zzd(final zzkp zzkpVar, final zzio zzioVar) {
        final byte[] bArr = null;
        MLTaskExecutor.workerThreadExecutor().execute(new Runnable(this, zzkpVar, zzioVar, bArr) { // from class: com.google.android.gms.internal.mlkit_vision_text.zzkk
            private final zzko zza;
            private final zzio zzb;
            private final zzkp zzc;

            {
                this.zza = this;
                this.zzc = zzkpVar;
                this.zzb = zzioVar;
            }

            @Override // java.lang.Runnable
            public final void run() {
                this.zza.zze(this.zzc, this.zzb);
            }
        });
    }

    final /* synthetic */ void zze(zzkp zzkpVar, zzio zzioVar) {
        zzkpVar.zze(zzioVar);
        String strZzb = zzkpVar.zzb();
        zzjx zzjxVar = new zzjx();
        zzjxVar.zza(this.zzb);
        zzjxVar.zzb(this.zzc);
        zzjxVar.zze(zzg());
        zzjxVar.zzh(true);
        zzjxVar.zzd(strZzb);
        zzjxVar.zzc(this.zzf.isSuccessful() ? this.zzf.getResult() : LibraryVersion.getInstance().getVersion(this.zzh));
        zzjxVar.zzf(this.zzg.isSuccessful() ? this.zzg.getResult() : this.zze.getMlSdkInstanceId());
        zzjxVar.zzj(10);
        zzkpVar.zzd(zzjxVar);
        this.zzd.zza(zzkpVar);
    }
}
