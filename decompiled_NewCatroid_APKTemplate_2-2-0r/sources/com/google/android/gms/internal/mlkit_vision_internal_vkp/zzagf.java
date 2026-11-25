package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import android.util.Base64;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzagf implements zzagc {
    static final String zza;
    private final String zzb;
    private final String zzc;
    private final zzage zzd;

    static {
        byte[] bArrDecode = Base64.decode("opMaSwzKffHEPgAzu/wXmmAoBSQ+L5trn/RQom0", 3);
        byte[] bArrDecode2 = Base64.decode("oh/AACypu7EhHIzJlqtCgyEK8MToFuQ8E7pIO7A", 3);
        byte[] bArr = new byte[bArrDecode.length];
        for (int i = 0; i < bArrDecode.length; i++) {
            bArr[i] = (byte) (bArrDecode[i] ^ bArrDecode2[i]);
        }
        zza = Base64.encodeToString(bArr, 3);
    }

    public zzagf() {
        String str = zza;
        zzage zzageVar = new Object() { // from class: com.google.android.gms.internal.mlkit_vision_internal_vkp.zzage
        };
        this.zzb = str;
        this.zzc = "https://mobilemlaccelerationcompatibility.googleapis.com";
        this.zzd = zzageVar;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_internal_vkp.zzagc
    public final zzarq zza(zzaft zzaftVar, zzagg zzaggVar, String str, String str2, String str3, int i) throws zzagd {
        String str4 = this.zzb;
        try {
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(this.zzc.concat("/v1/advisor?alt=PROTO")).openConnection();
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setRequestProperty("Accept", "application/x-protobuf");
                httpURLConnection.setRequestProperty("Content-Type", "application/json; utf-8");
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setRequestProperty("X-Goog-Api-Key", str4);
                try {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("installation_id", zzaggVar.zza());
                    jSONObject.put("client", "MLKIT");
                    jSONObject.put("client_library", str);
                    jSONObject.put("client_library_version", str2);
                    JSONObject jSONObjectPut = new JSONObject().put("client_info", jSONObject);
                    zzbfi zzbfiVarZza = zzaftVar.zza();
                    zzbfl zzbflVarZzg = zzbfiVarZza.zzg();
                    zzbff zzbffVarZzc = zzbfiVarZza.zzc();
                    JSONObject jSONObjectPut2 = jSONObjectPut.put("android_info", new JSONObject().put("product", new JSONObject().put("device", zzbflVarZzg.zzh()).put("product", zzbflVarZzg.zzk()).put("model", zzbflVarZzg.zzj()).put("manufacturer", zzbflVarZzg.zzi()).put("brand", zzbflVarZzg.zzg())).put(JsonPOJOBuilder.DEFAULT_BUILD_METHOD, new JSONObject().put("os_version", zzbffVarZzc.zzj()).put("build_type", zzbffVarZzc.zzi()).put("preview_sdk", zzbffVarZzc.zzc()).put("build_id", zzbffVarZzc.zzh())).put("soc", zzbfiVarZza.zzh()));
                    zzbfr zzbfrVarZzb = zzaftVar.zzb();
                    JSONArray jSONArray = new JSONArray();
                    for (zzbfo zzbfoVar : zzbfrVarZzb.zzf()) {
                        jSONArray.put(new JSONObject().put("device", zzbfoVar.zzg()).put("version", zzbfoVar.zzh()).put("feature_level", zzbfoVar.zzc()));
                    }
                    JSONObject jSONObjectPut3 = jSONObjectPut2.put("nnapi_info", new JSONObject().put("nnapi_driver_versions", jSONArray)).put("model_namespace", "com.google.perception");
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    try {
                        byte[] bytes = jSONObjectPut3.toString().getBytes(zzkd.zzc);
                        outputStream.write(bytes, 0, bytes.length);
                        if (outputStream != null) {
                            outputStream.close();
                        }
                        try {
                            httpURLConnection.connect();
                            if (httpURLConnection.getResponseCode() != 200) {
                                throw new zzagd(zzyf.RPC_ERROR, String.format("Error response (%d: '%s') from MlGoldblumServer", Integer.valueOf(httpURLConnection.getResponseCode()), httpURLConnection.getResponseMessage()));
                            }
                            String headerField = httpURLConnection.getHeaderField("Content-Type");
                            if (headerField == null || !zzjy.zza(headerField, "application/x-protobuf")) {
                                String strValueOf = String.valueOf(headerField);
                                throw new zzagd(zzyf.RPC_RETURNED_MALFORMED_RESULT, strValueOf.length() != 0 ? "Invalid response from MlGoldblum, expected proto buf but got ".concat(strValueOf) : new String("Invalid response from MlGoldblum, expected proto buf but got "));
                            }
                            try {
                                return zzbfc.zzf(httpURLConnection.getInputStream(), zzanc.zza()).zzc();
                            } catch (IOException e) {
                                throw new zzagd(zzyf.RPC_RETURNED_MALFORMED_RESULT, "Error while reading response from MlGoldblum", e);
                            }
                        } catch (IOException e2) {
                            throw new zzagd(zzyf.NO_CONNECTION, "Error while requesting allowlist to MlGoldblum", e2);
                        }
                    } catch (Throwable th) {
                        if (outputStream != null) {
                            try {
                                outputStream.close();
                            } catch (Throwable unused) {
                            }
                        }
                        throw th;
                    }
                } catch (IOException | JSONException e3) {
                    throw zzagd.zzb("Error creating request", e3);
                }
            } catch (IOException e4) {
                throw zzagd.zzb("Error while building allowlist request to MlGoldblum", e4);
            }
        } catch (IOException e5) {
            throw zzagd.zzb("Invalid URL built while trying to connect to MlGoldblum", e5);
        }
    }
}
