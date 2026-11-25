package com.google.android.gms.analytics;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.gtm.zzbs;
import com.google.android.gms.internal.gtm.zzbv;
import com.google.android.gms.internal.gtm.zzfr;
import java.util.HashMap;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzv extends zzbs {
    final /* synthetic */ Tracker zza;
    private boolean zzb;
    private int zzc;
    private long zzd;
    private boolean zze;
    private long zzf;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected zzv(Tracker tracker, zzbv zzbvVar) {
        super(zzbvVar);
        this.zza = tracker;
        this.zzd = -1L;
    }

    public final void zza(Activity activity) {
        String canonicalName;
        if (this.zzc == 0 && zzC().elapsedRealtime() >= this.zzf + Math.max(1000L, this.zzd)) {
            this.zze = true;
        }
        this.zzc++;
        if (this.zzb) {
            Intent intent = activity.getIntent();
            if (intent != null) {
                this.zza.setCampaignParamsOnNextHit(intent.getData());
            }
            HashMap map = new HashMap();
            map.put("&t", "screenview");
            Tracker tracker = this.zza;
            if (tracker.zzg != null) {
                zzfr zzfrVar = this.zza.zzg;
                canonicalName = activity.getClass().getCanonicalName();
                String str = zzfrVar.zzg.get(canonicalName);
                if (str != null) {
                    canonicalName = str;
                }
            } else {
                canonicalName = activity.getClass().getCanonicalName();
            }
            tracker.set("&cd", canonicalName);
            if (TextUtils.isEmpty((CharSequence) map.get("&dr"))) {
                Preconditions.checkNotNull(activity);
                Intent intent2 = activity.getIntent();
                String str2 = null;
                if (intent2 != null) {
                    String stringExtra = intent2.getStringExtra("android.intent.extra.REFERRER_NAME");
                    if (!TextUtils.isEmpty(stringExtra)) {
                        str2 = stringExtra;
                    }
                }
                if (!TextUtils.isEmpty(str2)) {
                    map.put("&dr", str2);
                }
            }
            this.zza.send(map);
        }
    }

    public final void zzb(Activity activity) {
        int i = this.zzc - 1;
        this.zzc = i;
        int iMax = Math.max(0, i);
        this.zzc = iMax;
        if (iMax == 0) {
            this.zzf = zzC().elapsedRealtime();
        }
    }

    public final void zzc(boolean z) {
        this.zzb = z;
        zzg();
    }

    @Override // com.google.android.gms.internal.gtm.zzbs
    protected final void zzd() {
    }

    public final void zze(long j) {
        this.zzd = j;
        zzg();
    }

    public final synchronized boolean zzf() {
        boolean z;
        z = this.zze;
        this.zze = false;
        return z;
    }

    private final void zzg() {
        if (this.zzd >= 0 || this.zzb) {
            zzp().zzk(this.zza.zze);
        } else {
            zzp().zzl(this.zza.zze);
        }
    }
}
