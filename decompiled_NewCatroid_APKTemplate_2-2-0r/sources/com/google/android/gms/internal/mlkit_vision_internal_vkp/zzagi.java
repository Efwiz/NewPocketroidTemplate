package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import android.util.Log;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzagi {
    private static final MediaType zzb = MediaType.parse("application/json; charset=utf-8");
    public final zzagj zza;
    private final zzagn zze;
    private final OkHttpClient zzc = new OkHttpClient.Builder().connectTimeout(10000, TimeUnit.MILLISECONDS).readTimeout(10000, TimeUnit.MILLISECONDS).writeTimeout(10000, TimeUnit.MILLISECONDS).build();
    private zzagq zzd = null;
    private final String zzf = "https://firebaseinstallations.googleapis.com/v1";

    public zzagi(zzagj zzagjVar, zzagn zzagnVar) {
        this.zza = zzagjVar;
        this.zze = zzagnVar;
    }

    public final zzagq zza() {
        return this.zzd;
    }

    final /* synthetic */ boolean zzb(zzagg zzaggVar, zzagm zzagmVar) throws InterruptedException, zzahp, IOException {
        zzagn zzagnVar;
        String strString;
        String str = String.format("%s/projects/%s/installations", this.zzf, this.zza.zzc());
        Headers headersBuild = new Headers.Builder().add("x-goog-api-key", this.zza.zza()).build();
        String str2 = String.format("{fid: '%s', appId: '%s', authVersion: '%s', sdkVersion: '%s'}", zzaggVar.zza(), this.zza.zzb(), "FIS_v2", "o:a:mlkit:1.0.0");
        long jCurrentTimeMillis = System.currentTimeMillis();
        zzagm zzagmVar2 = new zzagm();
        zzagmVar2.zzg();
        Request requestBuild = new Request.Builder().headers(headersBuild).url(str).post(RequestBody.create(zzb, str2)).build();
        String str3 = null;
        try {
            Response responseExecute = this.zzc.newCall(requestBuild).execute();
            int iCode = responseExecute.code();
            zzagmVar2.zzf(iCode);
            if (iCode < 200 || iCode >= 300) {
                StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 57);
                sb.append("Got HTTP status ");
                sb.append(iCode);
                sb.append(" from HTTPS POST request to <");
                sb.append(str);
                sb.append(">");
                Log.e("MLKitFbInstsRestClient", sb.toString());
                try {
                    ResponseBody responseBodyBody = responseExecute.body();
                    try {
                        strString = responseBodyBody.string();
                        if (responseBodyBody != null) {
                            responseBodyBody.close();
                        }
                    } catch (Throwable th) {
                        if (responseBodyBody == null) {
                            throw th;
                        }
                        try {
                            responseBodyBody.close();
                            throw th;
                        } catch (Throwable unused) {
                            throw th;
                        }
                    }
                } catch (IOException unused2) {
                    strString = "<none>";
                }
                String strValueOf = String.valueOf(strString);
                Log.d("MLKitFbInstsRestClient", strValueOf.length() != 0 ? "HTTP Response Body:\n".concat(strValueOf) : new String("HTTP Response Body:\n"));
                zzagmVar2.zzd(zzafd.RPC_ERROR);
                zzagmVar.zzb(zzafd.RPC_ERROR);
            } else {
                try {
                    ResponseBody responseBodyBody2 = responseExecute.body();
                    try {
                        String strString2 = responseBodyBody2.string();
                        if (responseBodyBody2 != null) {
                            responseBodyBody2.close();
                        }
                        str3 = strString2;
                    } catch (Throwable th2) {
                        if (responseBodyBody2 == null) {
                            throw th2;
                        }
                        try {
                            responseBodyBody2.close();
                            throw th2;
                        } catch (Throwable unused3) {
                            throw th2;
                        }
                    }
                } catch (IOException e) {
                    StringBuilder sb2 = new StringBuilder(String.valueOf(str).length() + 60);
                    sb2.append("Error retrieving response body from HTTPS POST request to <");
                    sb2.append(str);
                    sb2.append(">");
                    Log.e("MLKitFbInstsRestClient", sb2.toString(), e);
                    zzagmVar2.zzd(zzafd.RPC_ERROR);
                    zzagmVar.zzb(zzafd.RPC_ERROR);
                }
            }
        } catch (IOException e2) {
            StringBuilder sb3 = new StringBuilder(String.valueOf(str).length() + 62);
            sb3.append("Connection error (or timeout) sending HTTPS POST request to <");
            sb3.append(str);
            sb3.append(">");
            Log.e("MLKitFbInstsRestClient", sb3.toString(), e2);
            zzagmVar2.zzd(zzafd.NO_CONNECTION);
            zzagmVar.zzb(zzafd.NO_CONNECTION);
        }
        zzagmVar2.zze();
        try {
            if (str3 == null) {
                zzagnVar = this.zze;
            } else {
                try {
                    zzvd zzvdVarZzb = zzvf.zzb(str3).zzb();
                    try {
                        String strZzd = zzvdVarZzb.zzc("name").zzd();
                        zzagg zzaggVar2 = new zzagg(zzvdVarZzb.zzc("fid").zzd());
                        String strZzd2 = zzvdVarZzb.zzc("refreshToken").zzd();
                        zzvd zzvdVarZza = zzvdVarZzb.zza("authToken");
                        String strZzd3 = zzvdVarZza.zzc("token").zzd();
                        String strZzd4 = zzvdVarZza.zzc("expiresIn").zzd();
                        long j = (Long.parseLong(strZzd4.replaceFirst("s$", "")) * 1000) + jCurrentTimeMillis;
                        String strValueOf2 = String.valueOf(strZzd);
                        Log.i("MLKitFbInstsRestClient", strValueOf2.length() != 0 ? "installation name: ".concat(strValueOf2) : new String("installation name: "));
                        String strValueOf3 = String.valueOf(zzaggVar2.zza());
                        Log.d("MLKitFbInstsRestClient", strValueOf3.length() != 0 ? "fid: ".concat(strValueOf3) : new String("fid: "));
                        String strValueOf4 = String.valueOf(strZzd2);
                        Log.d("MLKitFbInstsRestClient", strValueOf4.length() != 0 ? "refresh_token: ".concat(strValueOf4) : new String("refresh_token: "));
                        String strValueOf5 = String.valueOf(zzvdVarZza);
                        StringBuilder sb4 = new StringBuilder(String.valueOf(strValueOf5).length() + 12);
                        sb4.append("auth token: ");
                        sb4.append(strValueOf5);
                        Log.d("MLKitFbInstsRestClient", sb4.toString());
                        String strValueOf6 = String.valueOf(strZzd4);
                        Log.d("MLKitFbInstsRestClient", strValueOf6.length() != 0 ? "auth token expires in: ".concat(strValueOf6) : new String("auth token expires in: "));
                        StringBuilder sb5 = new StringBuilder(39);
                        sb5.append("auth token expiry: ");
                        sb5.append(j);
                        Log.d("MLKitFbInstsRestClient", sb5.toString());
                        this.zzd = new zzagq(zzaggVar2, strZzd2, strZzd3, j);
                        this.zze.zza(zzadr.INSTALLATION_ID_FIS_CREATE_INSTALLATION, zzagmVar2);
                        return true;
                    } catch (ClassCastException | IllegalStateException | NullPointerException e3) {
                        String strValueOf7 = String.valueOf(zzvdVarZzb);
                        StringBuilder sb6 = new StringBuilder(String.valueOf(str).length() + 75 + str3.length() + String.valueOf(strValueOf7).length());
                        sb6.append("Error traversing JSON object returned from url <");
                        sb6.append(str);
                        sb6.append(">:\nraw json:\n");
                        sb6.append(str3);
                        sb6.append("\nparsed json:\n");
                        sb6.append(strValueOf7);
                        Log.e("MLKitFbInstsRestClient", sb6.toString(), e3);
                        zzagmVar2.zzd(zzafd.RPC_RETURNED_INVALID_RESULT);
                        zzagmVar.zzb(zzafd.RPC_RETURNED_INVALID_RESULT);
                        zzagnVar = this.zze;
                    }
                } catch (zzvh | IllegalStateException | NullPointerException e4) {
                    StringBuilder sb7 = new StringBuilder(String.valueOf(str).length() + 44 + str3.length());
                    sb7.append("Error parsing JSON object returned from <");
                    sb7.append(str);
                    sb7.append(">:\n");
                    sb7.append(str3);
                    Log.e("MLKitFbInstsRestClient", sb7.toString(), e4);
                    zzagmVar2.zzd(zzafd.RPC_RETURNED_MALFORMED_RESULT);
                    zzagmVar.zzb(zzafd.RPC_RETURNED_MALFORMED_RESULT);
                    zzagnVar = this.zze;
                }
            }
            zzagnVar.zza(zzadr.INSTALLATION_ID_FIS_CREATE_INSTALLATION, zzagmVar2);
            return false;
        } catch (Throwable th3) {
            this.zze.zza(zzadr.INSTALLATION_ID_FIS_CREATE_INSTALLATION, zzagmVar2);
            throw th3;
        }
    }
}
