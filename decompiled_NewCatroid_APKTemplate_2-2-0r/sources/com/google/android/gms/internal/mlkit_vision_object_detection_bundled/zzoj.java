package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzoj {
    private JSONObject zza = new JSONObject();
    private Date zzb = zzok.zza;
    private JSONArray zzc = new JSONArray();

    private zzoj() {
    }

    public final zzoj zza(JSONObject jSONObject) {
        try {
            this.zza = new JSONObject(jSONObject.toString());
        } catch (JSONException unused) {
        }
        return this;
    }

    public final zzoj zzb(JSONArray jSONArray) {
        try {
            this.zzc = new JSONArray(jSONArray.toString());
        } catch (JSONException unused) {
        }
        return this;
    }

    public final zzoj zzc(Date date) {
        this.zzb = date;
        return this;
    }

    public final zzok zzd() throws JSONException {
        return new zzok(this.zza, this.zzb, this.zzc);
    }

    /* synthetic */ zzoj(zzoi zzoiVar) {
    }
}
