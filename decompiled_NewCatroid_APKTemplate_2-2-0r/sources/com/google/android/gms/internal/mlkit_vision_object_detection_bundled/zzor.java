package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import android.util.Log;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
final class zzor implements zzpb {
    final /* synthetic */ zzos zza;
    private final String zzb;
    private final Date zzc;
    private final zzng zzd;
    private zzaw<String, String> zze;
    private zzok zzf;

    zzor(zzos zzosVar, String str, Date date, zzng zzngVar) {
        this.zza = zzosVar;
        this.zzb = str;
        this.zzc = date;
        this.zzd = zzngVar;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzpb
    public final boolean zza() throws zzou {
        try {
            zzol zzolVar = this.zza.zzi;
            Log.d("MLKit RemoteConfigRestC", "Creating HTTP connection to remote config service");
            zzom zzomVarZza = this.zza.zzi.zza(zzolVar.zzb(), this.zza.zzh.zza().zza(), this.zzb, zzaw.zzc(), null, zzaw.zzc(), this.zzc, "o:a:mlkit:1.0.0", this.zza.zzo, this.zzd);
            Log.d("MLKit RemoteConfigRestC", "Got FetchResponse");
            zzok zzokVarZza = zzomVarZza.zza();
            this.zzf = zzokVarZza;
            JSONObject jSONObjectZzd = zzokVarZza.zzd();
            try {
                this.zze = zzos.zzm(jSONObjectZzd);
                return true;
            } catch (JSONException e) {
                this.zzd.zzb(zzmq.RPC_RETURNED_MALFORMED_RESULT);
                String strValueOf = String.valueOf(jSONObjectZzd);
                StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 50);
                sb.append("Fetched remote config setting has invalid format: ");
                sb.append(strValueOf);
                Log.e("MLKit RemoteConfigRestC", sb.toString(), e);
                return false;
            }
        } catch (zzou e2) {
            Log.e("MLKit RemoteConfigRestC", "Creating HTTP connection to remote config service failed", e2);
            this.zzd.zzb(zzmq.NO_CONNECTION);
            return false;
        }
    }

    public final zzaw<String, String> zzb() {
        return this.zze;
    }

    public final zzok zzc() {
        return this.zzf;
    }
}
