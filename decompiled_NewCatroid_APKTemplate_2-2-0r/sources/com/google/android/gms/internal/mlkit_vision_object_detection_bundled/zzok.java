package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
final class zzok {
    private static final Date zza = new Date(0);
    private final JSONObject zzb;
    private final JSONObject zzc;
    private final Date zzd;
    private final JSONArray zze;

    zzok(JSONObject jSONObject, Date date, JSONArray jSONArray) throws JSONException {
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("configs_key", jSONObject);
        jSONObject2.put("fetch_time_key", date.getTime());
        jSONObject2.put("abt_experiments_key", jSONArray);
        this.zzc = jSONObject;
        this.zzd = date;
        this.zze = jSONArray;
        this.zzb = jSONObject2;
    }

    public static zzoj zza() {
        return new zzoj(null);
    }

    public final String toString() {
        return this.zzb.toString();
    }

    public final Date zzc() {
        return this.zzd;
    }

    public final JSONObject zzd() {
        return this.zzc;
    }
}
