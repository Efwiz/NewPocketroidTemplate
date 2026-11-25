package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.mlkit.common.sdkinternal.MlKitContext;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzos {
    private static zzos zzd;
    private final Context zze;
    private final Executor zzf;
    private final Executor zzg;
    private final zzne zzh;
    private final zzol zzi;
    private volatile zzaw<String, String> zzj;
    private volatile zzaw<String, String> zzk;
    private final Map<String, String> zzl = new TreeMap();
    private final zzox zzm;
    private final zzov zzn;
    private String zzo;
    private final zzon zzp;
    private static final ExecutorService zzb = Executors.newSingleThreadExecutor();
    private static final ExecutorService zzc = Executors.newSingleThreadExecutor();
    public static final long zza = TimeUnit.HOURS.toSeconds(12);

    zzos(Context context, zzne zzneVar, ExecutorService executorService, ExecutorService executorService2, zzon zzonVar, zzov zzovVar, zznd zzndVar, byte[] bArr) {
        this.zze = context;
        this.zzh = zzneVar;
        this.zzf = executorService;
        this.zzg = executorService2;
        this.zzp = zzonVar;
        this.zzn = zzovVar;
        this.zzi = new zzol(context, zzndVar.zzb(), zzndVar.zza(), "firebase", 5L, 5L, zzovVar);
        this.zzm = new zzox(context);
    }

    public static synchronized zzos zzf() {
        if (zzd == null) {
            zzd = new zzos((Context) MlKitContext.getInstance().get(Context.class), zzne.zzb(), zzb, zzc, zzon.zza, new zzov(), zznl.zza, null);
        }
        return zzd;
    }

    private final Pair<zzaw<String, String>, Date> zzl(zzng zzngVar, boolean z) {
        zzok zzokVarZza = this.zzm.zza(zzngVar);
        if (zzokVarZza == null) {
            return null;
        }
        JSONObject jSONObjectZzd = zzokVarZza.zzd();
        try {
            zzaw<String, String> zzawVarZzm = zzm(jSONObjectZzd);
            this.zzk = zzawVarZzm;
            if (z) {
                zzng zzngVar2 = new zzng();
                zzngVar2.zzg();
                this.zzj = this.zzk;
                zzngVar2.zze();
                this.zzn.zzb(zzngVar2);
            }
            zzngVar.zzh();
            return Pair.create(zzawVarZzm, zzokVarZza.zzc());
        } catch (JSONException e) {
            zzngVar.zzc(zzmq.FILE_READ_RETURNED_MALFORMED_DATA);
            String strValueOf = String.valueOf(jSONObjectZzd);
            StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 48);
            sb.append("Saved remote config setting has invalid format: ");
            sb.append(strValueOf);
            Log.e("MLKit RemoteConfigRestC", sb.toString(), e);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static zzaw<String, String> zzm(JSONObject jSONObject) throws JSONException {
        String string;
        zzoo zzooVar = new zzoo(jSONObject);
        zzav zzavVar = new zzav();
        Iterator<String> itKeys = zzooVar.zza.keys();
        while (itKeys.hasNext()) {
            String next = itKeys.next();
            try {
                String string2 = jSONObject.getString(next);
                if (string2 == null) {
                    string = null;
                } else if (string2.isEmpty()) {
                    string = "";
                } else {
                    StringBuilder sb = new StringBuilder(string2.length() + 13);
                    sb.append("{ \"value\": ");
                    sb.append(string2);
                    sb.append(" }");
                    string = new JSONObject(sb.toString()).getString("value");
                }
                zzavVar.zza(next, string);
            } catch (JSONException e) {
                StringBuilder sb2 = new StringBuilder(String.valueOf(next).length() + 55);
                sb2.append("Getting JSON string value for remote config key ");
                sb2.append(next);
                sb2.append(" failed");
                Log.e("MLKit RemoteConfigRestC", sb2.toString(), e);
                throw e;
            }
        }
        return zzavVar.zzb();
    }

    public final Task<Void> zza(final long j) {
        final Date date = new Date(System.currentTimeMillis());
        final zzng zzngVar = new zzng();
        zzngVar.zzg();
        final TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        final boolean z = false;
        this.zzg.execute(new Runnable(date, j, zzngVar, z, taskCompletionSource) { // from class: com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzoq
            public final /* synthetic */ Date zzb;
            public final /* synthetic */ long zzc;
            public final /* synthetic */ zzng zzd;
            public final /* synthetic */ TaskCompletionSource zze;

            {
                this.zze = taskCompletionSource;
            }

            @Override // java.lang.Runnable
            public final void run() {
                this.zza.zzi(this.zzb, this.zzc, this.zzd, false, this.zze);
            }
        });
        return taskCompletionSource.getTask();
    }

    public final Task<Void> zzb() {
        final zzng zzngVar = new zzng();
        zzngVar.zzg();
        final TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        final boolean z = true;
        this.zzf.execute(new Runnable(zzngVar, z, taskCompletionSource) { // from class: com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzop
            public final /* synthetic */ zzng zzb;
            public final /* synthetic */ TaskCompletionSource zzc;

            {
                this.zzc = taskCompletionSource;
            }

            @Override // java.lang.Runnable
            public final void run() {
                this.zza.zzj(this.zzb, true, this.zzc);
            }
        });
        return taskCompletionSource.getTask();
    }

    public final String zzh(String str) {
        String str2;
        zzaw<String, String> zzawVar = this.zzj;
        if (zzawVar != null) {
            return zzawVar.get("vision_object_detection_enable_acceleration");
        }
        synchronized (this.zzl) {
            str2 = this.zzl.get("vision_object_detection_enable_acceleration");
        }
        return str2;
    }

    final /* synthetic */ void zzi(Date date, long j, zzng zzngVar, boolean z, TaskCompletionSource taskCompletionSource) {
        zzaw<String, String> zzawVarZzb;
        try {
            try {
                Pair<zzaw<String, String>, Date> pairZzl = zzl(zzngVar, false);
                if (pairZzl == null) {
                    zzawVarZzb = null;
                } else {
                    zzawVarZzb = (zzaw) pairZzl.first;
                    if (date.after(new Date(((Date) pairZzl.second).getTime() + TimeUnit.SECONDS.toMillis(j)))) {
                        Log.i("MLKit RemoteConfigRestC", "Saved remote config is past its expiration time.");
                        zzawVarZzb = null;
                    }
                }
                if (zzawVarZzb == null) {
                    try {
                        Log.d("MLKit RemoteConfigRestC", "Getting installation id...");
                        this.zzh.zzd();
                        Log.d("MLKit RemoteConfigRestC", "Got installation id. Checking temporary token for expiry...");
                        String strZzc = this.zzh.zzc();
                        Log.d("MLKit RemoteConfigRestC", "Got valid temporary auth token. Fetching remote config...");
                        zzor zzorVar = new zzor(this, strZzc, date, zzngVar);
                        if (zzpc.zza(zzorVar)) {
                            zzawVarZzb = zzorVar.zzb();
                            String strValueOf = String.valueOf(zzawVarZzb);
                            StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 26);
                            sb.append("writeAndSetFetchedConfig: ");
                            sb.append(strValueOf);
                            Log.d("MLKit RemoteConfigRestC", sb.toString());
                            this.zzm.zzb(zzorVar.zzc(), zzngVar);
                            this.zzk = zzawVarZzb;
                        } else {
                            zzngVar.zzd(zzmq.RPC_EXPONENTIAL_BACKOFF_FAILED);
                            zzawVarZzb = null;
                        }
                    } catch (IOException e) {
                        zzngVar.zzd(zzmq.UNKNOWN_ERROR);
                        Log.e("MLKit RemoteConfigRestC", "Initializing installation id failed", e);
                    }
                }
                if (zzawVarZzb == null) {
                    Log.e("MLKit RemoteConfigRestC", "Remote config was null!");
                } else {
                    Log.d("MLKit RemoteConfigRestC", "Got remote config.");
                }
                taskCompletionSource.setResult(null);
            } finally {
                zzngVar.zze();
                this.zzn.zzc(zzngVar);
            }
        } catch (zzou | InterruptedException | RuntimeException e2) {
            Log.e("MLKit RemoteConfigRestC", "Fetch failed", e2);
            taskCompletionSource.setException(e2);
        }
    }

    final /* synthetic */ void zzj(zzng zzngVar, boolean z, TaskCompletionSource taskCompletionSource) {
        try {
            try {
                if (zzl(zzngVar, true) == null) {
                    Log.w("MLKit RemoteConfigRestC", "Cached remote config was null!");
                } else {
                    Log.d("MLKit RemoteConfigRestC", "Loaded cached remote config.");
                }
                taskCompletionSource.setResult(null);
            } catch (RuntimeException e) {
                Log.e("MLKit RemoteConfigRestC", "Load failed", e);
                taskCompletionSource.setException(e);
            }
        } finally {
            zzngVar.zze();
            this.zzn.zze(zzngVar);
        }
    }

    public final void zzk(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.zzo = str;
    }
}
