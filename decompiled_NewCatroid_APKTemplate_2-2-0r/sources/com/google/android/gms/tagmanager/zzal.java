package com.google.android.gms.tagmanager;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Looper;
import androidx.lifecycle.CoroutineLiveDataKt;
import androidx.work.PeriodicWorkRequest;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BasePendingResult;
import com.google.android.gms.common.util.Clock;
import com.google.android.gms.common.util.DefaultClock;
import com.google.android.gms.internal.gtm.zzrk;
import com.google.android.gms.internal.gtm.zzrl;
import com.google.android.gms.internal.gtm.zzrm;
import com.google.android.gms.internal.gtm.zzrs;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzal extends BasePendingResult<ContainerHolder> {
    private final Clock zza;
    private final zzai zzb;
    private final Looper zzc;
    private final zzec zzd;
    private final int zze;
    private final Context zzf;
    private final TagManager zzg;
    private final String zzh;
    private final zzam zzi;
    private zzak zzj;
    private final zzrm zzk;
    private volatile zzaa zzl;
    private volatile boolean zzm;
    private com.google.android.gms.internal.gtm.zzai zzn;
    private long zzo;
    private String zzp;
    private zzaj zzq;
    private zzac zzr;

    public zzal(Context context, TagManager tagManager, Looper looper, String str, int i, zzap zzapVar) {
        zzem zzemVar = new zzem(context, str);
        zzej zzejVar = new zzej(context, str, zzapVar, null, null, null);
        zzrm zzrmVar = new zzrm(context);
        Clock defaultClock = DefaultClock.getInstance();
        zzdf zzdfVar = new zzdf(1, 5, PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS, CoroutineLiveDataKt.DEFAULT_TIMEOUT, "refreshing", DefaultClock.getInstance());
        zzam zzamVar = new zzam(context, str);
        super(looper == null ? Looper.getMainLooper() : looper);
        this.zzf = context;
        this.zzg = tagManager;
        this.zzc = looper == null ? Looper.getMainLooper() : looper;
        this.zzh = str;
        this.zze = i;
        this.zzj = zzemVar;
        this.zzq = zzejVar;
        this.zzk = zzrmVar;
        this.zzb = new zzai(this, null);
        this.zzn = com.google.android.gms.internal.gtm.zzai.zzd().zzC();
        this.zza = defaultClock;
        this.zzd = zzdfVar;
        this.zzi = zzamVar;
        if (zzv()) {
            zzo(zzea.zza().zzb());
        }
        zzapVar.zza();
    }

    static /* bridge */ /* synthetic */ boolean zzp(zzal zzalVar) {
        boolean z = zzalVar.zzm;
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final synchronized void zzr(long j) {
        zzaj zzajVar = this.zzq;
        if (zzajVar == null) {
            zzdh.zzc("Refresh requested, but no network load scheduler.");
        } else {
            zzajVar.zza(j, this.zzn.zzh());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void zzs(boolean z) {
        this.zzj.zzd(new zzae(this, null));
        this.zzq.zzc(new zzag(this, 0 == true ? 1 : 0));
        zzrs zzrsVarZza = this.zzj.zza(this.zze);
        if (zzrsVarZza != null) {
            TagManager tagManager = this.zzg;
            this.zzl = new zzaa(tagManager, this.zzc, new Container(this.zzf, tagManager.getDataLayer(), this.zzh, 0L, zzrsVarZza), this.zzb);
        }
        this.zzr = new zzac(this, z);
        if (zzv()) {
            this.zzq.zza(0L, "");
        } else {
            this.zzj.zzb();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final synchronized void zzt(com.google.android.gms.internal.gtm.zzai zzaiVar) {
        if (this.zzj != null) {
            zzrk zzrkVarZze = zzrl.zze();
            zzrkVarZze.zzc(0L);
            zzrkVarZze.zza(com.google.android.gms.internal.gtm.zzaa.zzl());
            zzrkVarZze.zzc(this.zzo);
            zzrkVarZze.zza(com.google.android.gms.internal.gtm.zzaa.zzj().zzC());
            zzrkVarZze.zzb(zzaiVar);
            this.zzj.zzc(zzrkVarZze.zzC());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final synchronized void zzu(com.google.android.gms.internal.gtm.zzai zzaiVar, long j, boolean z) {
        if (isReady() && this.zzl == null) {
            return;
        }
        this.zzn = zzaiVar;
        this.zzo = j;
        long jZza = this.zzi.zza();
        zzr(Math.max(0L, Math.min(jZza, (this.zzo + jZza) - this.zza.currentTimeMillis())));
        Container container = new Container(this.zzf, this.zzg.getDataLayer(), this.zzh, j, zzaiVar);
        if (this.zzl == null) {
            this.zzl = new zzaa(this.zzg, this.zzc, container, this.zzb);
        } else {
            this.zzl.zzc(container);
        }
        if (isReady() || !this.zzr.zza(container)) {
            return;
        }
        setResult(this.zzl);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean zzv() {
        zzea zzeaVarZza = zzea.zza();
        return (zzeaVarZza.zze() == 2 || zzeaVarZza.zze() == 3) && this.zzh.equals(zzeaVarZza.zzc());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.common.api.internal.BasePendingResult
    /* JADX INFO: renamed from: zzd, reason: merged with bridge method [inline-methods] */
    public final ContainerHolder createFailedResult(Status status) {
        if (this.zzl != null) {
            return this.zzl;
        }
        if (status == Status.RESULT_TIMEOUT) {
            zzdh.zza("timer expired: setting result to failure");
        }
        return new zzaa(status);
    }

    final synchronized String zzh() {
        return this.zzp;
    }

    public final void zzl() {
        zzrs zzrsVarZza = this.zzj.zza(this.zze);
        if (zzrsVarZza != null) {
            setResult(new zzaa(this.zzg, this.zzc, new Container(this.zzf, this.zzg.getDataLayer(), this.zzh, 0L, zzrsVarZza), new zzab(this)));
        } else {
            zzdh.zza("Default was requested, but no default container was found");
            setResult(createFailedResult(new Status(10, "Default was requested, but no default container was found", (PendingIntent) null)));
        }
        this.zzq = null;
        this.zzj = null;
    }

    public final void zzm() {
        zzs(true);
    }

    public final void zzn() {
        zzs(false);
    }

    final synchronized void zzo(String str) {
        this.zzp = str;
        zzaj zzajVar = this.zzq;
        if (zzajVar != null) {
            zzajVar.zzb(str);
        }
    }
}
