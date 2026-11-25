package com.google.android.gms.internal.mlkit_vision_pose_common;

import android.content.Context;
import android.content.res.Resources;
import android.os.SystemClock;
import androidx.core.os.ConfigurationCompat;
import androidx.core.os.LocaleListCompat;
import com.google.android.gms.common.internal.LibraryVersion;
import com.google.android.gms.tasks.Task;
import com.google.firebase.components.Component;
import com.google.firebase.components.ComponentContainer;
import com.google.firebase.components.Dependency;
import com.google.mlkit.common.sdkinternal.CommonUtils;
import com.google.mlkit.common.sdkinternal.MLTaskExecutor;
import com.google.mlkit.common.sdkinternal.SharedPrefManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzej {
    public static final Component<?> zza = Component.builder(zzej.class).add(Dependency.required((Class<?>) Context.class)).add(Dependency.required((Class<?>) SharedPrefManager.class)).add(Dependency.required((Class<?>) zzc.class)).factory(zzen.zza).build();
    private static List<String> zzb = null;
    private static boolean zzk = true;
    private static boolean zzl = true;
    private final String zzc;
    private final String zzd;
    private final zzc zze;
    private final SharedPrefManager zzf;
    private final Task<String> zzh;
    private final Map<zzcb, Long> zzi = new HashMap();
    private final Map<zzcb, zzau<Object, Long>> zzj = new HashMap();
    private final Task<String> zzg = MLTaskExecutor.getInstance().scheduleCallable(zzem.zza);

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public interface zza {
        zzbm.zzad.zza zza();
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public interface zzb<K> {
        zzbm.zzad.zza zza(K k, int i, zzbm.zzab zzabVar);
    }

    /* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
    public interface zzc {
        void zza(zzbm.zzad zzadVar);
    }

    private zzej(Context context, SharedPrefManager sharedPrefManager, zzc zzcVar) {
        this.zzc = context.getPackageName();
        this.zzd = CommonUtils.getAppVersion(context);
        this.zzf = sharedPrefManager;
        this.zze = zzcVar;
        MLTaskExecutor mLTaskExecutor = MLTaskExecutor.getInstance();
        sharedPrefManager.getClass();
        this.zzh = mLTaskExecutor.scheduleCallable(zzel.zza(sharedPrefManager));
    }

    public final void zza(final zzbm.zzad.zza zzaVar, final zzcb zzcbVar) {
        MLTaskExecutor.workerThreadExecutor().execute(new Runnable(this, zzaVar, zzcbVar) { // from class: com.google.android.gms.internal.mlkit_vision_pose_common.zzeo
            private final zzej zza;
            private final zzbm.zzad.zza zzb;
            private final zzcb zzc;

            {
                this.zza = this;
                this.zzb = zzaVar;
                this.zzc = zzcbVar;
            }

            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.zza.zzb(this.zzb, this.zzc);
            }
        });
    }

    public final void zza(zza zzaVar, zzcb zzcbVar) {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        if (zza(zzcbVar, jElapsedRealtime, 30L)) {
            this.zzi.put(zzcbVar, Long.valueOf(jElapsedRealtime));
            zza(zzaVar.zza(), zzcbVar);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final <K> void zza(K k, long j, zzcb zzcbVar, zzb<K> zzbVar) {
        if (zzk) {
            if (!this.zzj.containsKey(zzcbVar)) {
                this.zzj.put(zzcbVar, zzw.zzf());
            }
            zzau<Object, Long> zzauVar = this.zzj.get(zzcbVar);
            zzauVar.zza(k, Long.valueOf(j));
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            if (zza(zzcbVar, jElapsedRealtime, 30L)) {
                this.zzi.put(zzcbVar, Long.valueOf(jElapsedRealtime));
                for (Object obj : zzauVar.zzh()) {
                    List<Long> listZzb = zzauVar.zzb(obj);
                    Collections.sort(listZzb);
                    zzbm.zzab.zza zzaVarZza = zzbm.zzab.zza();
                    Iterator<Long> it = listZzb.iterator();
                    long jLongValue = 0;
                    while (it.hasNext()) {
                        jLongValue += it.next().longValue();
                    }
                    zza(zzbVar.zza(obj, zzauVar.zzb(obj).size(), (zzbm.zzab) ((zzgd) zzaVarZza.zzc(jLongValue / ((long) listZzb.size())).zza(zza(listZzb, 100.0d)).zzf(zza(listZzb, 75.0d)).zze(zza(listZzb, 50.0d)).zzd(zza(listZzb, 25.0d)).zzb(zza(listZzb, 0.0d)).zzh())), zzcbVar);
                }
                this.zzj.remove(zzcbVar);
            }
        }
    }

    private static long zza(List<Long> list, double d) {
        return list.get(Math.max(((int) Math.ceil((d / 100.0d) * ((double) list.size()))) - 1, 0)).longValue();
    }

    private final boolean zza(zzcb zzcbVar, long j, long j2) {
        return this.zzi.get(zzcbVar) == null || j - this.zzi.get(zzcbVar).longValue() > TimeUnit.SECONDS.toMillis(30L);
    }

    private static synchronized List<String> zzb() {
        List<String> list = zzb;
        if (list != null) {
            return list;
        }
        LocaleListCompat locales = ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration());
        zzb = new ArrayList(locales.size());
        for (int i = 0; i < locales.size(); i++) {
            zzb.add(CommonUtils.languageTagFromLocale(locales.get(i)));
        }
        return zzb;
    }

    final /* synthetic */ void zzb(zzbm.zzad.zza zzaVar, zzcb zzcbVar) throws Throwable {
        String version;
        String mlSdkInstanceId;
        String strZza = zzaVar.zza().zza();
        if ("NA".equals(strZza) || "".equals(strZza)) {
            strZza = "NA";
        }
        zzbm.zzbh.zza zzaVarZzb = zzbm.zzbh.zzb().zza(this.zzc).zzb(this.zzd).zzd(strZza).zza(zzb()).zzb(true);
        if (this.zzg.isSuccessful()) {
            version = this.zzg.getResult();
        } else {
            version = LibraryVersion.getInstance().getVersion("pose-detection-common");
        }
        zzbm.zzbh.zza zzaVarZzc = zzaVarZzb.zzc(version);
        if (zzl) {
            if (this.zzh.isSuccessful()) {
                mlSdkInstanceId = this.zzh.getResult();
            } else {
                mlSdkInstanceId = this.zzf.getMlSdkInstanceId();
            }
            zzaVarZzc.zze(mlSdkInstanceId);
        }
        zzaVar.zza(zzcbVar).zza(zzaVarZzc);
        this.zze.zza((zzbm.zzad) ((zzgd) zzaVar.zzh()));
    }

    static final /* synthetic */ zzej zza(ComponentContainer componentContainer) {
        return new zzej((Context) componentContainer.get(Context.class), (SharedPrefManager) componentContainer.get(SharedPrefManager.class), (zzc) componentContainer.get(zzc.class));
    }
}
