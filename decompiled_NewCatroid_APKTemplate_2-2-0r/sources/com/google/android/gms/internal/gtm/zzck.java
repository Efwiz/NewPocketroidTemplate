package com.google.android.gms.internal.gtm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.gms.analytics.CampaignTrackingReceiver;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.wrappers.Wrappers;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.time.DateUtils;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzck extends zzbs {
    private boolean zza;
    private final zzce zzb;
    private final zzfe zzc;
    private final zzfc zzd;
    private final zzcc zze;
    private long zzf;
    private final zzcw zzg;
    private final zzcw zzh;
    private final zzfo zzi;
    private long zzj;
    private boolean zzk;

    protected zzck(zzbv zzbvVar, zzbw zzbwVar) {
        super(zzbvVar);
        Preconditions.checkNotNull(zzbwVar);
        this.zzf = Long.MIN_VALUE;
        this.zzd = new zzfc(zzbvVar);
        this.zzb = new zzce(zzbvVar);
        this.zzc = new zzfe(zzbvVar);
        this.zze = new zzcc(zzbvVar);
        this.zzi = new zzfo(zzC());
        this.zzg = new zzcg(this, zzbvVar);
        this.zzh = new zzch(this, zzbvVar);
    }

    private final void zzag() {
        zzcy zzcyVarZzy = zzy();
        if (zzcyVarZzy.zze()) {
            zzcyVarZzy.zza();
        }
    }

    private final void zzah() {
        if (this.zzg.zzh()) {
            zzO("All hits dispatched or no network/service. Going to power save mode");
        }
        this.zzg.zzf();
    }

    private final void zzai() {
        long jZzc;
        zzcy zzcyVarZzy = zzy();
        if (zzcyVarZzy.zzc() && !zzcyVarZzy.zze()) {
            com.google.android.gms.analytics.zzr.zzh();
            zzW();
            try {
                jZzc = this.zzb.zzc();
            } catch (SQLiteException e) {
                zzK("Failed to get min/max hit times from local store", e);
                jZzc = 0;
            }
            if (jZzc != 0) {
                long jAbs = Math.abs(zzC().currentTimeMillis() - jZzc);
                zzw();
                if (jAbs <= zzeu.zzn.zzb().longValue()) {
                    zzw();
                    zzP("Dispatch alarm scheduled (ms)", Long.valueOf(zzct.zzd()));
                    zzcyVarZzy.zzb();
                }
            }
        }
    }

    private final void zzaj(zzbx zzbxVar, zzaw zzawVar) {
        Preconditions.checkNotNull(zzbxVar);
        Preconditions.checkNotNull(zzawVar);
        com.google.android.gms.analytics.zza zzaVar = new com.google.android.gms.analytics.zza(zzt());
        zzaVar.zzc(zzbxVar.zzc());
        zzaVar.zzd(zzbxVar.zzf());
        com.google.android.gms.analytics.zzh zzhVarZza = zzaVar.zza();
        zzbe zzbeVar = (zzbe) zzhVarZza.zzb(zzbe.class);
        zzbeVar.zzk("data");
        zzbeVar.zzl(true);
        zzhVarZza.zzg(zzawVar);
        zzaz zzazVar = (zzaz) zzhVarZza.zzb(zzaz.class);
        zzav zzavVar = (zzav) zzhVarZza.zzb(zzav.class);
        for (Map.Entry<String, String> entry : zzbxVar.zzd().entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if ("an".equals(key)) {
                zzavVar.zzk(value);
            } else if ("av".equals(key)) {
                zzavVar.zzl(value);
            } else if ("aid".equals(key)) {
                zzavVar.zzi(value);
            } else if ("aiid".equals(key)) {
                zzavVar.zzj(value);
            } else if ("uid".equals(key)) {
                zzbeVar.zzm(value);
            } else {
                zzazVar.zze(key, value);
            }
        }
        zzH("Sending installation campaign to", zzbxVar.zzc(), zzawVar);
        zzhVarZza.zzj(zzA().zza());
        zzhVarZza.zzk();
    }

    private final boolean zzak(String str) {
        return Wrappers.packageManager(zzo()).checkCallingOrSelfPermission(str) == 0;
    }

    static /* bridge */ /* synthetic */ void zzc(zzck zzckVar) {
        try {
            zzckVar.zzb.zza();
            zzckVar.zzae();
        } catch (SQLiteException e) {
            zzckVar.zzS("Failed to delete stale hits", e);
        }
        zzcw zzcwVar = zzckVar.zzh;
        zzckVar.zzw();
        zzcwVar.zzg(DateUtils.MILLIS_PER_DAY);
    }

    public final void zzZ(long j) {
        com.google.android.gms.analytics.zzr.zzh();
        zzW();
        if (j < 0) {
            j = 0;
        }
        this.zzf = j;
        zzae();
    }

    public final long zza() {
        long j = this.zzf;
        if (j != Long.MIN_VALUE) {
            return j;
        }
        zzw();
        long jLongValue = zzeu.zzi.zzb().longValue();
        zzft zzftVarZzB = zzB();
        zzftVarZzB.zzW();
        if (!zzftVarZzB.zzc) {
            return jLongValue;
        }
        zzft zzftVarZzB2 = zzB();
        zzftVarZzB2.zzW();
        return ((long) zzftVarZzB2.zzd) * 1000;
    }

    final void zzaa() {
        zzW();
        Preconditions.checkState(!this.zza, "Analytics backend already started");
        this.zza = true;
        zzq().zzi(new zzci(this));
    }

    protected final void zzab() {
        zzW();
        zzw();
        com.google.android.gms.analytics.zzr.zzh();
        Context contextZza = zzt().zza();
        if (!zzfi.zza(contextZza)) {
            zzR("AnalyticsReceiver is not registered or is disabled. Register the receiver for reliable dispatching on non-Google Play devices. See http://goo.gl/8Rd3yj for instructions.");
        } else if (!zzfn.zzh(contextZza)) {
            zzJ("AnalyticsService is not registered or is disabled. Analytics service at risk of not starting. See http://goo.gl/8Rd3yj for instructions.");
        }
        if (!CampaignTrackingReceiver.zzb(contextZza)) {
            zzR("CampaignTrackingReceiver is not registered, not exported or is disabled. Installation campaign tracking is not possible. See http://goo.gl/8Rd3yj for instructions.");
        }
        zzA().zza();
        if (!zzak("android.permission.ACCESS_NETWORK_STATE")) {
            zzJ("Missing required android.permission.ACCESS_NETWORK_STATE. Google Analytics disabled. See http://goo.gl/8Rd3yj for instructions");
            zzad();
        }
        if (!zzak("android.permission.INTERNET")) {
            zzJ("Missing required android.permission.INTERNET. Google Analytics disabled. See http://goo.gl/8Rd3yj for instructions");
            zzad();
        }
        if (zzfn.zzh(zzo())) {
            zzO("AnalyticsService registered in the app manifest and enabled");
        } else {
            zzw();
            zzR("AnalyticsService not registered in the app manifest. Hits might not be delivered reliably. See http://goo.gl/8Rd3yj for instructions.");
        }
        if (!this.zzk) {
            zzw();
            if (!this.zzb.zzac()) {
                zzi();
            }
        }
        zzae();
    }

    public final void zzac() {
        com.google.android.gms.analytics.zzr.zzh();
        zzW();
        zzF("Sync dispatching local hits");
        long j = this.zzj;
        zzw();
        zzi();
        try {
            zzaf();
            zzA().zzi();
            zzae();
            if (this.zzj != j) {
                this.zzd.zzb();
            }
        } catch (Exception e) {
            zzK("Sync local dispatch failed", e);
            zzae();
        }
    }

    public final void zzad() {
        zzW();
        com.google.android.gms.analytics.zzr.zzh();
        this.zzk = true;
        this.zze.zzc();
        zzae();
    }

    public final void zzae() {
        long jMin;
        com.google.android.gms.analytics.zzr.zzh();
        zzW();
        if (!this.zzk) {
            zzw();
            if (zza() > 0) {
                if (this.zzb.zzac()) {
                    this.zzd.zzc();
                    zzah();
                    zzag();
                    return;
                }
                if (!zzeu.zzJ.zzb().booleanValue()) {
                    this.zzd.zza();
                    if (!this.zzd.zzd()) {
                        zzah();
                        zzag();
                        zzai();
                        return;
                    }
                }
                zzai();
                long jZza = zza();
                long jZzb = zzA().zzb();
                if (jZzb != 0) {
                    jMin = jZza - Math.abs(zzC().currentTimeMillis() - jZzb);
                    if (jMin <= 0) {
                        zzw();
                        jMin = Math.min(zzct.zze(), jZza);
                    }
                } else {
                    zzw();
                    jMin = Math.min(zzct.zze(), jZza);
                }
                zzP("Dispatch scheduled (ms)", Long.valueOf(jMin));
                if (!this.zzg.zzh()) {
                    this.zzg.zzg(jMin);
                    return;
                } else {
                    this.zzg.zze(Math.max(1L, jMin + this.zzg.zzb()));
                    return;
                }
            }
        }
        this.zzd.zzc();
        zzah();
        zzag();
    }

    protected final boolean zzaf() {
        boolean z;
        com.google.android.gms.analytics.zzr.zzh();
        zzW();
        zzO("Dispatching a batch of local hits");
        if (this.zze.zzg()) {
            z = false;
        } else {
            zzw();
            z = true;
        }
        boolean zZze = this.zzc.zze();
        if (z && !zZze) {
            zzO("No network or service available. Will retry later");
            return false;
        }
        zzw();
        int iZzh = zzct.zzh();
        zzw();
        long jMax = Math.max(iZzh, zzct.zzg());
        ArrayList arrayList = new ArrayList();
        long jMax2 = 0;
        while (true) {
            try {
                this.zzb.zzm();
                arrayList.clear();
                try {
                    List<zzex> listZzj = this.zzb.zzj(jMax);
                    if (listZzj.isEmpty()) {
                        zzO("Store is empty, nothing to dispatch");
                        zzah();
                        zzag();
                        try {
                            this.zzb.zzab();
                            this.zzb.zzaa();
                            return false;
                        } catch (SQLiteException e) {
                            zzK("Failed to commit local dispatch transaction", e);
                            zzah();
                            zzag();
                            return false;
                        }
                    }
                    zzP("Hits loaded from store. count", Integer.valueOf(listZzj.size()));
                    Iterator<zzex> it = listZzj.iterator();
                    while (it.hasNext()) {
                        if (it.next().zzb() == jMax2) {
                            zzL("Database contains successfully uploaded hit", Long.valueOf(jMax2), Integer.valueOf(listZzj.size()));
                            zzah();
                            zzag();
                            try {
                                this.zzb.zzab();
                                this.zzb.zzaa();
                                return false;
                            } catch (SQLiteException e2) {
                                zzK("Failed to commit local dispatch transaction", e2);
                                zzah();
                                zzag();
                                return false;
                            }
                        }
                    }
                    if (this.zze.zzg()) {
                        zzw();
                        zzO("Service connected, sending hits to the service");
                        while (!listZzj.isEmpty()) {
                            zzex zzexVar = listZzj.get(0);
                            if (!this.zze.zzh(zzexVar)) {
                                break;
                            }
                            jMax2 = Math.max(jMax2, zzexVar.zzb());
                            listZzj.remove(zzexVar);
                            zzG("Hit sent do device AnalyticsService for delivery", zzexVar);
                            try {
                                this.zzb.zzn(zzexVar.zzb());
                                arrayList.add(Long.valueOf(zzexVar.zzb()));
                            } catch (SQLiteException e3) {
                                zzK("Failed to remove hit that was send for delivery", e3);
                                zzah();
                                zzag();
                                try {
                                    this.zzb.zzab();
                                    this.zzb.zzaa();
                                    return false;
                                } catch (SQLiteException e4) {
                                    zzK("Failed to commit local dispatch transaction", e4);
                                    zzah();
                                    zzag();
                                    return false;
                                }
                            }
                        }
                    }
                    if (this.zzc.zze()) {
                        List<Long> listZzc = this.zzc.zzc(listZzj);
                        Iterator<Long> it2 = listZzc.iterator();
                        while (it2.hasNext()) {
                            jMax2 = Math.max(jMax2, it2.next().longValue());
                        }
                        try {
                            this.zzb.zzZ(listZzc);
                            arrayList.addAll(listZzc);
                        } catch (SQLiteException e5) {
                            zzK("Failed to remove successfully uploaded hits", e5);
                            zzah();
                            zzag();
                            try {
                                this.zzb.zzab();
                                this.zzb.zzaa();
                                return false;
                            } catch (SQLiteException e6) {
                                zzK("Failed to commit local dispatch transaction", e6);
                                zzah();
                                zzag();
                                return false;
                            }
                        }
                    }
                    if (arrayList.isEmpty()) {
                        try {
                            this.zzb.zzab();
                            this.zzb.zzaa();
                            return false;
                        } catch (SQLiteException e7) {
                            zzK("Failed to commit local dispatch transaction", e7);
                            zzah();
                            zzag();
                            return false;
                        }
                    }
                    try {
                        this.zzb.zzab();
                        this.zzb.zzaa();
                    } catch (SQLiteException e8) {
                        zzK("Failed to commit local dispatch transaction", e8);
                        zzah();
                        zzag();
                        return false;
                    }
                } catch (SQLiteException e9) {
                    zzS("Failed to read hits from persisted store", e9);
                    zzah();
                    zzag();
                    try {
                        this.zzb.zzab();
                        this.zzb.zzaa();
                        return false;
                    } catch (SQLiteException e10) {
                        zzK("Failed to commit local dispatch transaction", e10);
                        zzah();
                        zzag();
                        return false;
                    }
                }
            } catch (Throwable th) {
                this.zzb.zzab();
                this.zzb.zzaa();
                throw th;
            }
            try {
                this.zzb.zzab();
                this.zzb.zzaa();
                throw th;
            } catch (SQLiteException e11) {
                zzK("Failed to commit local dispatch transaction", e11);
                zzah();
                zzag();
                return false;
            }
        }
    }

    public final long zzb(zzbx zzbxVar, boolean z) {
        Preconditions.checkNotNull(zzbxVar);
        zzW();
        com.google.android.gms.analytics.zzr.zzh();
        try {
            this.zzb.zzm();
            zzce zzceVar = this.zzb;
            String strZzb = zzbxVar.zzb();
            Preconditions.checkNotEmpty(strZzb);
            zzceVar.zzW();
            com.google.android.gms.analytics.zzr.zzh();
            int iDelete = zzceVar.zzf().delete("properties", "app_uid=? AND cid<>?", new String[]{"0", strZzb});
            if (iDelete > 0) {
                zzceVar.zzP("Deleted property records", Integer.valueOf(iDelete));
            }
            long jZze = this.zzb.zze(0L, zzbxVar.zzb(), zzbxVar.zzc());
            zzbxVar.zze(1 + jZze);
            zzce zzceVar2 = this.zzb;
            Preconditions.checkNotNull(zzbxVar);
            zzceVar2.zzW();
            com.google.android.gms.analytics.zzr.zzh();
            SQLiteDatabase sQLiteDatabaseZzf = zzceVar2.zzf();
            Map<String, String> mapZzd = zzbxVar.zzd();
            Preconditions.checkNotNull(mapZzd);
            Uri.Builder builder = new Uri.Builder();
            for (Map.Entry<String, String> entry : mapZzd.entrySet()) {
                builder.appendQueryParameter(entry.getKey(), entry.getValue());
            }
            String encodedQuery = builder.build().getEncodedQuery();
            if (encodedQuery == null) {
                encodedQuery = "";
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put("app_uid", (Long) 0L);
            contentValues.put("cid", zzbxVar.zzb());
            contentValues.put("tid", zzbxVar.zzc());
            contentValues.put("adid", Integer.valueOf(zzbxVar.zzf() ? 1 : 0));
            contentValues.put("hits_count", Long.valueOf(zzbxVar.zza()));
            contentValues.put("params", encodedQuery);
            try {
                if (sQLiteDatabaseZzf.insertWithOnConflict("properties", null, contentValues, 5) == -1) {
                    zzceVar2.zzJ("Failed to insert/update a property (got -1)");
                }
            } catch (SQLiteException e) {
                zzceVar2.zzK("Error storing a property", e);
            }
            this.zzb.zzab();
            return jZze;
        } catch (SQLiteException e2) {
            zzK("Failed to update Analytics property", e2);
            return -1L;
        } finally {
            try {
                this.zzb.zzaa();
            } catch (SQLiteException e3) {
                zzK("Failed to end transaction", e3);
            }
        }
    }

    @Override // com.google.android.gms.internal.gtm.zzbs
    protected final void zzd() {
        this.zzb.zzX();
        this.zzc.zzX();
        this.zze.zzX();
    }

    public final void zzf(zzcz zzczVar) {
        zzg(zzczVar, this.zzj);
    }

    public final void zzg(zzcz zzczVar, long j) {
        com.google.android.gms.analytics.zzr.zzh();
        zzW();
        long jZzb = zzA().zzb();
        zzG("Dispatching local hits. Elapsed time since last dispatch (ms)", Long.valueOf(jZzb != 0 ? Math.abs(zzC().currentTimeMillis() - jZzb) : -1L));
        zzw();
        zzi();
        try {
            zzaf();
            zzA().zzi();
            zzae();
            if (zzczVar != null) {
                zzczVar.zza(null);
            }
            if (this.zzj != j) {
                this.zzd.zzb();
            }
        } catch (Exception e) {
            zzK("Local dispatch failed", e);
            zzA().zzi();
            zzae();
            if (zzczVar != null) {
                zzczVar.zza(e);
            }
        }
    }

    public final void zzh() {
        com.google.android.gms.analytics.zzr.zzh();
        zzW();
        zzw();
        zzO("Delete all hits from local store");
        try {
            zzce zzceVar = this.zzb;
            com.google.android.gms.analytics.zzr.zzh();
            zzceVar.zzW();
            zzceVar.zzf().delete("hits2", null, null);
            zzce zzceVar2 = this.zzb;
            com.google.android.gms.analytics.zzr.zzh();
            zzceVar2.zzW();
            zzceVar2.zzf().delete("properties", null, null);
            zzae();
        } catch (SQLiteException e) {
            zzS("Failed to delete hits from store", e);
        }
        zzi();
        if (this.zze.zze()) {
            zzO("Device service unavailable. Can't clear hits stored on the device service.");
        }
    }

    protected final void zzi() {
        if (this.zzk) {
            return;
        }
        zzw();
        if (zzct.zzl() && !this.zze.zzg()) {
            zzw();
            if (this.zzi.zzc(zzeu.zzO.zzb().longValue())) {
                this.zzi.zzb();
                zzO("Connecting to service");
                if (this.zze.zzf()) {
                    zzO("Connected to service");
                    this.zzi.zza();
                    zzm();
                }
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:60:0x019d A[Catch: SQLiteException -> 0x0209, TryCatch #1 {SQLiteException -> 0x0209, blocks: (B:16:0x0090, B:17:0x00af, B:19:0x00b5, B:21:0x00c9, B:23:0x00d1, B:25:0x00d9, B:26:0x00e3, B:29:0x00ef, B:31:0x00f8, B:74:0x0205, B:32:0x0103, B:34:0x011e, B:36:0x012f, B:56:0x0188, B:37:0x0134, B:44:0x0175, B:60:0x019d, B:61:0x01a0, B:62:0x01a1, B:64:0x01cf, B:66:0x01de, B:73:0x0200, B:65:0x01d7, B:67:0x01e3, B:69:0x01ef, B:70:0x01f5), top: B:81:0x0090, inners: #2 }] */
    /* JADX WARN: Code duplicated, block: B:64:0x01cf A[Catch: SQLiteException -> 0x0209, TryCatch #1 {SQLiteException -> 0x0209, blocks: (B:16:0x0090, B:17:0x00af, B:19:0x00b5, B:21:0x00c9, B:23:0x00d1, B:25:0x00d9, B:26:0x00e3, B:29:0x00ef, B:31:0x00f8, B:74:0x0205, B:32:0x0103, B:34:0x011e, B:36:0x012f, B:56:0x0188, B:37:0x0134, B:44:0x0175, B:60:0x019d, B:61:0x01a0, B:62:0x01a1, B:64:0x01cf, B:66:0x01de, B:73:0x0200, B:65:0x01d7, B:67:0x01e3, B:69:0x01ef, B:70:0x01f5), top: B:81:0x0090, inners: #2 }] */
    /* JADX WARN: Code duplicated, block: B:65:0x01d7 A[Catch: SQLiteException -> 0x0209, TryCatch #1 {SQLiteException -> 0x0209, blocks: (B:16:0x0090, B:17:0x00af, B:19:0x00b5, B:21:0x00c9, B:23:0x00d1, B:25:0x00d9, B:26:0x00e3, B:29:0x00ef, B:31:0x00f8, B:74:0x0205, B:32:0x0103, B:34:0x011e, B:36:0x012f, B:56:0x0188, B:37:0x0134, B:44:0x0175, B:60:0x019d, B:61:0x01a0, B:62:0x01a1, B:64:0x01cf, B:66:0x01de, B:73:0x0200, B:65:0x01d7, B:67:0x01e3, B:69:0x01ef, B:70:0x01f5), top: B:81:0x0090, inners: #2 }] */
    /* JADX WARN: Code duplicated, block: B:69:0x01ef A[Catch: SQLiteException -> 0x01ff, TryCatch #2 {SQLiteException -> 0x01ff, blocks: (B:67:0x01e3, B:69:0x01ef, B:70:0x01f5), top: B:83:0x01e3, outer: #1 }] */
    /* JADX WARN: Code duplicated, block: B:70:0x01f5 A[Catch: SQLiteException -> 0x01ff, TRY_LEAVE, TryCatch #2 {SQLiteException -> 0x01ff, blocks: (B:67:0x01e3, B:69:0x01ef, B:70:0x01f5), top: B:83:0x01e3, outer: #1 }] */
    public final void zzj(zzex zzexVar) throws Throwable {
        String strZzk;
        long jInsert;
        Cursor cursorQuery;
        List<Long> listEmptyList;
        Pair<String, Long> pairZza;
        zzex zzexVarZze = zzexVar;
        Preconditions.checkNotNull(zzexVar);
        com.google.android.gms.analytics.zzr.zzh();
        zzW();
        if (this.zzk) {
            zzF("Hit delivery not possible. Missing network permissions. See http://goo.gl/8Rd3yj for instructions");
        } else {
            zzP("Delivering hit", zzexVarZze);
        }
        if (TextUtils.isEmpty(zzexVar.zzf()) && (pairZza = zzA().zze().zza()) != null) {
            Long l = (Long) pairZza.second;
            String str = (String) pairZza.first;
            String strValueOf = String.valueOf(l);
            StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 1 + String.valueOf(str).length());
            sb.append(strValueOf);
            sb.append(":");
            sb.append(str);
            String string = sb.toString();
            HashMap map = new HashMap(zzexVar.zzg());
            map.put("_m", string);
            zzexVarZze = zzex.zze(this, zzexVarZze, map);
        }
        zzex zzexVar2 = zzexVarZze;
        zzi();
        if (this.zze.zzh(zzexVar2)) {
            zzF("Hit sent to the device AnalyticsService for delivery");
            return;
        }
        zzw();
        try {
            zzce zzceVar = this.zzb;
            Preconditions.checkNotNull(zzexVar2);
            com.google.android.gms.analytics.zzr.zzh();
            zzceVar.zzW();
            Preconditions.checkNotNull(zzexVar2);
            Uri.Builder builder = new Uri.Builder();
            for (Map.Entry<String, String> entry : zzexVar2.zzg().entrySet()) {
                String key = entry.getKey();
                if (!"ht".equals(key) && !"qt".equals(key) && !"AppUID".equals(key)) {
                    builder.appendQueryParameter(key, entry.getValue());
                }
            }
            String encodedQuery = builder.build().getEncodedQuery();
            if (encodedQuery == null) {
                encodedQuery = "";
            }
            String str2 = encodedQuery;
            if (str2.length() > 8192) {
                zzceVar.zzz().zzb(zzexVar2, "Hit length exceeds the maximum allowed size");
            } else {
                zzceVar.zzw();
                int iIntValue = zzeu.zzf.zzb().intValue();
                long jZzb = zzceVar.zzb();
                Cursor cursor = null;
                if (jZzb > iIntValue - 1) {
                    long j = (jZzb - ((long) iIntValue)) + 1;
                    com.google.android.gms.analytics.zzr.zzh();
                    zzceVar.zzW();
                    if (j <= 0) {
                        listEmptyList = Collections.emptyList();
                    } else {
                        SQLiteDatabase sQLiteDatabaseZzf = zzceVar.zzf();
                        ArrayList arrayList = new ArrayList();
                        try {
                            cursorQuery = sQLiteDatabaseZzf.query("hits2", new String[]{"hit_id"}, null, null, null, null, String.format("%s ASC", "hit_id"), Long.toString(j));
                            try {
                                try {
                                    if (cursorQuery.moveToFirst()) {
                                        do {
                                            arrayList.add(Long.valueOf(cursorQuery.getLong(0)));
                                        } while (cursorQuery.moveToNext());
                                    }
                                    if (cursorQuery != null) {
                                        cursorQuery.close();
                                    }
                                } catch (SQLiteException e) {
                                    e = e;
                                    zzceVar.zzS("Error selecting hit ids", e);
                                    if (cursorQuery != null) {
                                    }
                                    listEmptyList = arrayList;
                                    zzceVar.zzS("Store full, deleting hits to make room, count", Integer.valueOf(listEmptyList.size()));
                                    zzceVar.zzZ(listEmptyList);
                                    SQLiteDatabase sQLiteDatabaseZzf2 = zzceVar.zzf();
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("hit_string", str2);
                                    contentValues.put("hit_time", Long.valueOf(zzexVar2.zzd()));
                                    contentValues.put("hit_app_id", Integer.valueOf(zzexVar2.zza()));
                                    if (zzexVar2.zzh()) {
                                        zzceVar.zzw();
                                        strZzk = zzct.zzi();
                                    } else {
                                        zzceVar.zzw();
                                        strZzk = zzct.zzk();
                                    }
                                    contentValues.put("hit_url", strZzk);
                                    jInsert = sQLiteDatabaseZzf2.insert("hits2", null, contentValues);
                                    if (jInsert == -1) {
                                        zzceVar.zzJ("Failed to insert a hit (got -1)");
                                    } else {
                                        zzceVar.zzH("Hit saved to database. db-id, hit", Long.valueOf(jInsert), zzexVar2);
                                    }
                                    zzae();
                                }
                            } catch (Throwable th) {
                                th = th;
                                cursor = cursorQuery;
                                if (cursor != null) {
                                    cursor.close();
                                }
                                throw th;
                            }
                        } catch (SQLiteException e2) {
                            e = e2;
                            cursorQuery = null;
                        } catch (Throwable th2) {
                            th = th2;
                            if (cursor != null) {
                                cursor.close();
                            }
                            throw th;
                        }
                        listEmptyList = arrayList;
                    }
                    zzceVar.zzS("Store full, deleting hits to make room, count", Integer.valueOf(listEmptyList.size()));
                    zzceVar.zzZ(listEmptyList);
                }
                SQLiteDatabase sQLiteDatabaseZzf3 = zzceVar.zzf();
                ContentValues contentValues2 = new ContentValues();
                contentValues2.put("hit_string", str2);
                contentValues2.put("hit_time", Long.valueOf(zzexVar2.zzd()));
                contentValues2.put("hit_app_id", Integer.valueOf(zzexVar2.zza()));
                if (zzexVar2.zzh()) {
                    zzceVar.zzw();
                    strZzk = zzct.zzi();
                } else {
                    zzceVar.zzw();
                    strZzk = zzct.zzk();
                }
                contentValues2.put("hit_url", strZzk);
                try {
                    jInsert = sQLiteDatabaseZzf3.insert("hits2", null, contentValues2);
                    if (jInsert == -1) {
                        zzceVar.zzJ("Failed to insert a hit (got -1)");
                    } else {
                        zzceVar.zzH("Hit saved to database. db-id, hit", Long.valueOf(jInsert), zzexVar2);
                    }
                } catch (SQLiteException e3) {
                    zzceVar.zzK("Error storing a hit", e3);
                }
            }
            zzae();
        } catch (SQLiteException e4) {
            zzK("Delivery failed to save hit to a database", e4);
            zzz().zzb(zzexVar2, "deliver: failed to insert hit to database");
        }
    }

    protected final void zzk(zzbx zzbxVar) {
        com.google.android.gms.analytics.zzr.zzh();
        zzG("Sending first hit to property", zzbxVar.zzc());
        zzfo zzfoVarZzf = zzA().zzf();
        zzw();
        if (zzfoVarZzf.zzc(zzct.zzc())) {
            return;
        }
        String strZzg = zzA().zzg();
        if (TextUtils.isEmpty(strZzg)) {
            return;
        }
        zzaw zzawVarZzb = zzfs.zzb(zzz(), strZzg);
        zzG("Found relevant installation campaign", zzawVarZzb);
        zzaj(zzbxVar, zzawVarZzb);
    }

    final void zzl() {
        com.google.android.gms.analytics.zzr.zzh();
        this.zzj = zzC().currentTimeMillis();
    }

    protected final void zzm() {
        com.google.android.gms.analytics.zzr.zzh();
        zzw();
        com.google.android.gms.analytics.zzr.zzh();
        zzW();
        zzE();
        zzw();
        if (!zzct.zzl()) {
            zzR("Service client disabled. Can't dispatch local hits to device AnalyticsService");
        }
        if (!this.zze.zzg()) {
            zzO("Service not connected");
            return;
        }
        if (this.zzb.zzac()) {
            return;
        }
        zzO("Dispatching local hits to device AnalyticsService");
        while (true) {
            try {
                zzce zzceVar = this.zzb;
                zzw();
                List<zzex> listZzj = zzceVar.zzj(zzct.zzh());
                if (listZzj.isEmpty()) {
                    zzae();
                    return;
                }
                while (!listZzj.isEmpty()) {
                    zzex zzexVar = listZzj.get(0);
                    if (!this.zze.zzh(zzexVar)) {
                        zzae();
                        return;
                    }
                    listZzj.remove(zzexVar);
                    try {
                        this.zzb.zzn(zzexVar.zzb());
                    } catch (SQLiteException e) {
                        zzK("Failed to remove hit that was send for delivery", e);
                        zzah();
                        zzag();
                        return;
                    }
                }
            } catch (SQLiteException e2) {
                zzK("Failed to read hits from store", e2);
                zzah();
                zzag();
                return;
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:57:0x014e  */
    public final void zzn(String str) throws Throwable {
        Cursor cursor;
        Preconditions.checkNotEmpty(str);
        com.google.android.gms.analytics.zzr.zzh();
        zzE();
        zzaw zzawVarZzb = zzfs.zzb(zzz(), str);
        if (zzawVarZzb == null) {
            zzS("Parsing failed. Ignoring invalid campaign data", str);
            return;
        }
        String strZzg = zzA().zzg();
        if (str.equals(strZzg)) {
            zzR("Ignoring duplicate install campaign");
            return;
        }
        if (!TextUtils.isEmpty(strZzg)) {
            zzL("Ignoring multiple install campaigns. original, new", strZzg, str);
            return;
        }
        zzA().zzh(str);
        zzfo zzfoVarZzf = zzA().zzf();
        zzw();
        if (zzfoVarZzf.zzc(zzct.zzc())) {
            zzS("Campaign received too late, ignoring", zzawVarZzb);
            return;
        }
        zzG("Received installation campaign", zzawVarZzb);
        zzce zzceVar = this.zzb;
        zzceVar.zzW();
        com.google.android.gms.analytics.zzr.zzh();
        SQLiteDatabase sQLiteDatabaseZzf = zzceVar.zzf();
        try {
            zzceVar.zzw();
            int iIntValue = zzeu.zzh.zzb().intValue();
            Cursor cursorQuery = sQLiteDatabaseZzf.query("properties", new String[]{"cid", "tid", "adid", "hits_count", "params"}, "app_uid=?", new String[]{"0"}, null, null, null, String.valueOf(iIntValue));
            try {
                ArrayList arrayList = new ArrayList();
                if (cursorQuery.moveToFirst()) {
                    do {
                        String string = cursorQuery.getString(0);
                        String string2 = cursorQuery.getString(1);
                        boolean z = cursorQuery.getInt(2) != 0;
                        long j = cursorQuery.getInt(3);
                        Map<String, String> mapZzl = zzceVar.zzl(cursorQuery.getString(4));
                        if (TextUtils.isEmpty(string) || TextUtils.isEmpty(string2)) {
                            zzceVar.zzT("Read property with empty client id or tracker id", string, string2);
                        } else {
                            arrayList.add(new zzbx(0L, string, string2, z, j, mapZzl));
                        }
                    } while (cursorQuery.moveToNext());
                }
                if (arrayList.size() >= iIntValue) {
                    zzceVar.zzR("Sending hits to too many properties. Campaign report might be incorrect");
                }
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    zzaj((zzbx) it.next(), zzawVarZzb);
                }
            } catch (SQLiteException e) {
                e = e;
                cursor = cursorQuery;
                try {
                    zzceVar.zzK("Error loading hits from the database", e);
                    throw e;
                } catch (Throwable th) {
                    th = th;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                cursor = cursorQuery;
                if (cursor != null) {
                    cursor.close();
                }
                throw th;
            }
        } catch (SQLiteException e2) {
            e = e2;
            cursor = null;
        } catch (Throwable th3) {
            th = th3;
            cursor = null;
        }
    }
}
