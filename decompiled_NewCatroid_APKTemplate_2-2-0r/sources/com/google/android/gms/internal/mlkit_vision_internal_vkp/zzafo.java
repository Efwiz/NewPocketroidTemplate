package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.mlkit.common.sdkinternal.MlKitContext;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzafo {
    private static final ExecutorService zza = Executors.newSingleThreadExecutor();
    private static final long zzb = TimeUnit.DAYS.toSeconds(7);
    private static final long zzc = TimeUnit.HOURS.toSeconds(12);
    private static zzafo zzd;
    private final zzafs zze;
    private final ExecutorService zzf;
    private final zzafp zzg;
    private final zzagc zzh;
    private final long zzi;
    private final long zzj;
    private final zzafu zzk;
    private final Object zzl;
    private final zzagk zzm;

    private zzafo(zzagk zzagkVar, ExecutorService executorService, zzafs zzafsVar, zzafp zzafpVar, long j, long j2) {
        zzagf zzagfVar = new zzagf();
        zzafu zzafuVar = new zzafu();
        this.zzl = new Object();
        this.zzm = zzagkVar;
        this.zzf = executorService;
        this.zze = zzafsVar;
        this.zzh = zzagfVar;
        this.zzg = zzafpVar;
        this.zzi = j;
        this.zzj = j2;
        this.zzk = zzafuVar;
    }

    public static synchronized zzafo zza() {
        if (zzd == null) {
            zzd = new zzafo(zzagk.zzb(), zza, new zzafs((Context) MlKitContext.getInstance().get(Context.class)), new zzafp(), zzb, zzc);
        }
        return zzd;
    }

    public final Task<zzarq> zzb(final String str, final String str2, String str3, int i) {
        Log.d("AccelerationAllowlist", "fetchAccelerationAllowlist");
        final String str4 = "com.google.perception";
        final zzagb zzagbVar = new zzagb("com.google.perception", 2);
        zzagbVar.zzf();
        final TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        final int i2 = 2;
        this.zzf.execute(new Runnable(str, str2, str4, i2, zzagbVar, taskCompletionSource) { // from class: com.google.android.gms.internal.mlkit_vision_internal_vkp.zzafm
            public final /* synthetic */ String zzb;
            public final /* synthetic */ String zzc;
            public final /* synthetic */ String zzd = "com.google.perception";
            public final /* synthetic */ zzagb zze;
            public final /* synthetic */ TaskCompletionSource zzf;

            {
                this.zze = zzagbVar;
                this.zzf = taskCompletionSource;
            }

            @Override // java.lang.Runnable
            public final void run() {
                this.zza.zzd(this.zzb, this.zzc, this.zzd, 2, this.zze, this.zzf);
            }
        });
        return taskCompletionSource.getTask();
    }

    public final zzarq zzc(String str, String str2, String str3, int i) {
        zzagb zzagbVar = new zzagb("com.google.perception", 2);
        zzagbVar.zzf();
        try {
            return this.zze.zza(str, str2, "com.google.perception", 2, zzagbVar, this.zzi);
        } finally {
            zzagbVar.zze();
            this.zzg.zzb(zzagbVar);
        }
    }

    final /* synthetic */ void zzd(String str, String str2, String str3, int i, zzagb zzagbVar, TaskCompletionSource taskCompletionSource) {
        zzagg zzaggVarZza;
        zzarq zzarqVar;
        try {
            try {
                zzarq zzarqVarZza = this.zze.zza(str, str2, "com.google.perception", 2, zzagbVar, this.zzj);
                if (zzarqVarZza != null) {
                    Log.d("AccelerationAllowlist", "Cached copy available, not calling the server");
                    taskCompletionSource.setResult(zzarqVarZza);
                } else {
                    Log.d("AccelerationAllowlist", "fetching allowlist");
                    zzbfe zzbfeVarZze = zzbff.zze();
                    zzbfeVarZze.zzc(Build.VERSION.SDK);
                    zzbfeVarZze.zzb(Build.TYPE);
                    zzbfeVarZze.zza(Build.ID);
                    zzbfeVarZze.zzd(Build.VERSION.PREVIEW_SDK_INT);
                    zzbfh zzbfhVarZze = zzbfi.zze();
                    zzbfhVarZze.zza(zzbfeVarZze.zzt());
                    zzbfk zzbfkVarZzc = zzbfl.zzc();
                    zzbfkVarZzc.zzb(Build.DEVICE);
                    zzbfkVarZzc.zze(Build.PRODUCT);
                    zzbfkVarZzc.zzd(Build.MODEL);
                    zzbfkVarZzc.zzc(Build.MANUFACTURER);
                    zzbfkVarZzc.zza(Build.BRAND);
                    zzbfhVarZze.zzb(zzbfkVarZzc.zzt());
                    zzbfhVarZze.zzc(com.google.android.libraries.intelligence.acceleration.zzd.zza().zzf());
                    zzbfi zzbfiVarZzt = zzbfhVarZze.zzt();
                    zzaux zzauxVarZzb = com.google.android.libraries.intelligence.acceleration.zzd.zzb();
                    zzbfq zzbfqVarZzc = zzbfr.zzc();
                    for (zzauw zzauwVar : zzauxVarZzb.zzf()) {
                        zzbfn zzbfnVarZze = zzbfo.zze();
                        zzbfnVarZze.zza(zzauwVar.zzf());
                        zzbfnVarZze.zzc(zzauwVar.zzg());
                        zzbfnVarZze.zzb((int) zzauwVar.zzc());
                        zzbfqVarZzc.zza(zzbfnVarZze.zzt());
                    }
                    zzafv zzafvVar = new zzafv(zzbfiVarZzt, zzbfqVarZzc.zzt());
                    synchronized (this.zzl) {
                        this.zzm.zzc();
                        zzaggVarZza = this.zzm.zza();
                    }
                    zzafn zzafnVar = new zzafn(zzagbVar, str, str2, "com.google.perception", 2, zzafvVar, zzaggVarZza, this.zzh, this.zzg);
                    if (zzahr.zza(zzafnVar)) {
                        Log.d("AccelerationAllowlist", "Saving allowlist");
                        zzarq zzarqVarZza2 = zzafnVar.zza();
                        this.zze.zzc(zzarqVarZza2, str, str2, "com.google.perception", 2, zzagbVar);
                        zzarqVar = zzarqVarZza2;
                    } else {
                        zzagbVar.zzd(zzyf.RPC_EXPONENTIAL_BACKOFF_FAILED);
                        zzarqVar = null;
                    }
                    taskCompletionSource.setResult(zzarqVar);
                }
            } catch (Throwable th) {
                zzagbVar.zze();
                this.zzg.zzb(zzagbVar);
                throw th;
            }
        } catch (IOException | InterruptedException e) {
            zzagbVar.zzd(zzyf.RPC_ERROR);
            taskCompletionSource.setException(e);
        }
        zzagbVar.zze();
        this.zzg.zzb(zzagbVar);
    }
}
