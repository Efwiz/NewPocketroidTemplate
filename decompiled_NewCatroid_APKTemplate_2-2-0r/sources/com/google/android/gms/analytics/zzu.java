package com.google.android.gms.analytics;

import android.text.TextUtils;
import com.android.dx.rop.code.RegisterSpec;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.gtm.zzav;
import com.google.android.gms.internal.gtm.zzbi;
import com.google.android.gms.internal.gtm.zzbt;
import com.google.android.gms.internal.gtm.zzbx;
import com.google.android.gms.internal.gtm.zzex;
import com.google.android.gms.internal.gtm.zzfs;
import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import java.util.HashMap;
import java.util.Map;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.jetbrains.kotlin.ir.backend.js.utils.Namer;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzu implements Runnable {
    final /* synthetic */ Map zza;
    final /* synthetic */ boolean zzb;
    final /* synthetic */ String zzc;
    final /* synthetic */ long zzd;
    final /* synthetic */ boolean zze;
    final /* synthetic */ boolean zzf;
    final /* synthetic */ String zzg;
    final /* synthetic */ Tracker zzh;

    zzu(Tracker tracker, Map map, boolean z, String str, long j, boolean z2, boolean z3, String str2) {
        this.zzh = tracker;
        this.zza = map;
        this.zzb = z;
        this.zzc = str;
        this.zzd = j;
        this.zze = z2;
        this.zzf = z3;
        this.zzg = str2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        double d;
        if (this.zzh.zze.zzf()) {
            this.zza.put("sc", "start");
        }
        Map map = this.zza;
        GoogleAnalytics googleAnalyticsZzp = this.zzh.zzp();
        Preconditions.checkNotMainThread("getClientId can not be called from the main thread");
        String strZzb = googleAnalyticsZzp.zzb().zzi().zzb();
        if (strZzb != null && TextUtils.isEmpty((CharSequence) map.get("cid"))) {
            map.put("cid", strZzb);
        }
        String str = (String) this.zza.get("sf");
        if (str != null) {
            try {
                d = Double.parseDouble(str);
            } catch (NumberFormatException unused) {
                d = 100.0d;
            }
            if (zzfs.zzj(d, (String) this.zza.get("cid"))) {
                this.zzh.zzG("Sampling enabled. Hit sampled out. sample rate", Double.valueOf(d));
                return;
            }
        }
        zzbi zzbiVarZzr = this.zzh.zzr();
        if (this.zzb) {
            Map map2 = this.zza;
            boolean zZzb = zzbiVarZzr.zzb();
            if (!map2.containsKey("ate")) {
                map2.put("ate", true != zZzb ? "0" : SchemaSymbols.ATTVAL_TRUE_1);
            }
            zzfs.zzg(this.zza, "adid", zzbiVarZzr.zza());
        } else {
            this.zza.remove("ate");
            this.zza.remove("adid");
        }
        zzav zzavVarZza = this.zzh.zzu().zza();
        zzfs.zzg(this.zza, "an", zzavVarZza.zzf());
        zzfs.zzg(this.zza, "av", zzavVarZza.zzg());
        zzfs.zzg(this.zza, "aid", zzavVarZza.zzd());
        zzfs.zzg(this.zza, "aiid", zzavVarZza.zze());
        this.zza.put(RegisterSpec.PREFIX, SchemaSymbols.ATTVAL_TRUE_1);
        this.zza.put(Namer.SHARED_BOX_V, zzbt.zzb);
        zzfs.zzg(this.zza, "ul", this.zzh.zzx().zza().zzd());
        zzfs.zzg(this.zza, LanguageCodeUtil.SR, this.zzh.zzx().zzb());
        if (!this.zzc.equals("transaction") && !this.zzc.equals("item") && !this.zzh.zzd.zza()) {
            this.zzh.zzz().zzc(this.zza, "Too many hits sent too quickly, rate limiting invoked");
            return;
        }
        long jZza = zzfs.zza((String) this.zza.get("ht"));
        if (jZza == 0) {
            jZza = this.zzd;
        }
        long j = jZza;
        if (this.zze) {
            this.zzh.zzz().zzN("Dry run enabled. Would have sent hit", new zzex(this.zzh, this.zza, j, this.zzf));
            return;
        }
        String str2 = (String) this.zza.get("cid");
        HashMap map3 = new HashMap();
        zzfs.zzh(map3, "uid", this.zza);
        zzfs.zzh(map3, "an", this.zza);
        zzfs.zzh(map3, "aid", this.zza);
        zzfs.zzh(map3, "av", this.zza);
        zzfs.zzh(map3, "aiid", this.zza);
        Preconditions.checkNotNull(str2);
        this.zza.put("_s", String.valueOf(this.zzh.zzs().zza(new zzbx(0L, str2, this.zzg, !TextUtils.isEmpty((CharSequence) this.zza.get("adid")), 0L, map3))));
        this.zzh.zzs().zzh(new zzex(this.zzh, this.zza, j, this.zzf));
    }
}
