package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.IntentFilter;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzff extends zzey {
    private static final Object zza = new Object();
    private static zzff zzb;
    private Context zzc;
    private zzcd zzd;
    private zzfb zzh;
    private zzdk zzi;
    private volatile zzcc zzk;
    private boolean zze = true;
    private boolean zzf = false;
    private boolean zzg = true;
    private final zzez zzl = new zzez(this);
    private boolean zzj = false;

    private zzff() {
    }

    public static zzff zzg() {
        if (zzb == null) {
            zzb = new zzff();
        }
        return zzb;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean zzm() {
        return this.zzj || !this.zzg;
    }

    @Override // com.google.android.gms.tagmanager.zzey
    public final synchronized void zza() {
        if (this.zzf) {
            this.zzk.zze(new zzfa(this));
        } else {
            zzdh.zzb.zzd("Dispatch call queued. Dispatch will run once initialization is complete.");
            this.zze = true;
        }
    }

    @Override // com.google.android.gms.tagmanager.zzey
    public final synchronized void zzb() {
        if (zzm()) {
            return;
        }
        this.zzh.zzb();
    }

    @Override // com.google.android.gms.tagmanager.zzey
    public final synchronized void zzc(boolean z) {
        zzi(this.zzj, z);
    }

    final synchronized void zzi(boolean z, boolean z2) {
        boolean zZzm = zzm();
        this.zzj = z;
        this.zzg = z2;
        if (zzm() != zZzm) {
            if (zzm()) {
                this.zzh.zza();
                zzdh.zzb.zzd("PowerSaveMode initiated.");
            } else {
                this.zzh.zzc(1800000L);
                zzdh.zzb.zzd("PowerSaveMode terminated.");
            }
        }
    }

    final synchronized void zzl(Context context, zzcc zzccVar) {
        if (this.zzc != null) {
            return;
        }
        this.zzc = context.getApplicationContext();
        if (this.zzk == null) {
            this.zzk = zzccVar;
        }
    }

    final synchronized zzcd zzf() {
        zzfd zzfdVar = null;
        if (this.zzd == null) {
            Context context = this.zzc;
            if (context == null) {
                throw new IllegalStateException("Cant get a store unless we have a context");
            }
            this.zzd = new zzdw(this.zzl, context, null);
        }
        if (this.zzh == null) {
            zzfe zzfeVar = new zzfe(this, zzfdVar);
            this.zzh = zzfeVar;
            zzfeVar.zzc(1800000L);
        }
        this.zzf = true;
        if (this.zze) {
            zza();
            this.zze = false;
        }
        if (this.zzi == null) {
            zzdk zzdkVar = new zzdk(this);
            this.zzi = zzdkVar;
            Context context2 = this.zzc;
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            context2.registerReceiver(zzdkVar, intentFilter);
            IntentFilter intentFilter2 = new IntentFilter();
            intentFilter2.addAction("com.google.analytics.RADIO_POWERED");
            intentFilter2.addCategory(context2.getPackageName());
            context2.registerReceiver(zzdkVar, intentFilter2);
        }
        return this.zzd;
    }
}
