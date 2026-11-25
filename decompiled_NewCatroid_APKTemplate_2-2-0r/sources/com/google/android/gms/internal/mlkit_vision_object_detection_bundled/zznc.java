package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

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

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
public final class zznc {
    private static final MediaType zzb = MediaType.parse("application/json; charset=utf-8");
    public final zznd zza;
    private final zznh zze;
    private final OkHttpClient zzc = new OkHttpClient.Builder().connectTimeout(10000, TimeUnit.MILLISECONDS).readTimeout(10000, TimeUnit.MILLISECONDS).writeTimeout(10000, TimeUnit.MILLISECONDS).build();
    private zznk zzd = null;
    private final String zzf = "https://firebaseinstallations.googleapis.com/v1";

    public zznc(zznd zzndVar, zznh zznhVar) {
        this.zza = zzndVar;
        this.zze = zznhVar;
    }

    private static long zze(long j, String str) {
        return j + (Long.parseLong(str.replaceFirst("s$", "")) * 1000);
    }

    private final String zzf(Headers headers, String str, String str2, zzng zzngVar, zzng zzngVar2) {
        String strString;
        try {
            Response responseExecute = this.zzc.newCall(new Request.Builder().headers(headers).url(str).post(RequestBody.create(zzb, str2)).build()).execute();
            int iCode = responseExecute.code();
            zzngVar2.zzf(iCode);
            if (iCode >= 200 && iCode < 300) {
                try {
                    ResponseBody responseBodyBody = responseExecute.body();
                    try {
                        String strString2 = responseBodyBody.string();
                        if (responseBodyBody != null) {
                            responseBodyBody.close();
                        }
                        return strString2;
                    } catch (Throwable th) {
                        if (responseBodyBody != null) {
                            try {
                                responseBodyBody.close();
                            } catch (Throwable unused) {
                            }
                        }
                        throw th;
                    }
                } catch (IOException e) {
                    StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 60);
                    sb.append("Error retrieving response body from HTTPS POST request to <");
                    sb.append(str);
                    sb.append(">");
                    Log.e("MLKitFbInstsRestClient", sb.toString(), e);
                    zzngVar2.zzd(zzmq.RPC_ERROR);
                    zzngVar.zzb(zzmq.RPC_ERROR);
                    return null;
                }
            }
            StringBuilder sb2 = new StringBuilder(String.valueOf(str).length() + 57);
            sb2.append("Got HTTP status ");
            sb2.append(iCode);
            sb2.append(" from HTTPS POST request to <");
            sb2.append(str);
            sb2.append(">");
            Log.e("MLKitFbInstsRestClient", sb2.toString());
            try {
                ResponseBody responseBodyBody2 = responseExecute.body();
                try {
                    strString = responseBodyBody2.string();
                    if (responseBodyBody2 != null) {
                        responseBodyBody2.close();
                    }
                } catch (Throwable th2) {
                    if (responseBodyBody2 != null) {
                        try {
                            responseBodyBody2.close();
                        } catch (Throwable unused2) {
                        }
                    }
                    throw th2;
                }
            } catch (IOException unused3) {
                strString = "<none>";
            }
            String strValueOf = String.valueOf(strString);
            Log.d("MLKitFbInstsRestClient", strValueOf.length() != 0 ? "HTTP Response Body:\n".concat(strValueOf) : new String("HTTP Response Body:\n"));
            zzngVar2.zzd(zzmq.RPC_ERROR);
            zzngVar.zzb(zzmq.RPC_ERROR);
            return null;
        } catch (IOException e2) {
            StringBuilder sb3 = new StringBuilder(String.valueOf(str).length() + 62);
            sb3.append("Connection error (or timeout) sending HTTPS POST request to <");
            sb3.append(str);
            sb3.append(">");
            Log.e("MLKitFbInstsRestClient", sb3.toString(), e2);
            zzngVar2.zzd(zzmq.NO_CONNECTION);
            zzngVar.zzb(zzmq.NO_CONNECTION);
            return null;
        }
    }

    public final zznk zza() {
        return this.zzd;
    }

    final /* synthetic */ boolean zzb(zzmz zzmzVar, zzng zzngVar) throws zzpa, InterruptedException, IOException {
        zznh zznhVar;
        String str = String.format("%s/projects/%s/installations", this.zzf, this.zza.zzc());
        Headers headersBuild = new Headers.Builder().add("x-goog-api-key", this.zza.zza()).build();
        String str2 = String.format("{fid: '%s', appId: '%s', authVersion: '%s', sdkVersion: '%s'}", zzmzVar.zza(), this.zza.zzb(), "FIS_v2", "o:a:mlkit:1.0.0");
        long jCurrentTimeMillis = System.currentTimeMillis();
        zzng zzngVar2 = new zzng();
        zzngVar2.zzg();
        String strZzf = zzf(headersBuild, str, str2, zzngVar, zzngVar2);
        zzngVar2.zze();
        try {
            if (strZzf == null) {
                zznhVar = this.zze;
            } else {
                try {
                    zzcm zzcmVarZzb = zzco.zzb(strZzf).zzb();
                    try {
                        String strZzd = zzcmVarZzb.zzc("name").zzd();
                        zzmz zzmzVar2 = new zzmz(zzcmVarZzb.zzc("fid").zzd());
                        String strZzd2 = zzcmVarZzb.zzc("refreshToken").zzd();
                        zzcm zzcmVarZza = zzcmVarZzb.zza("authToken");
                        String strZzd3 = zzcmVarZza.zzc("token").zzd();
                        String strZzd4 = zzcmVarZza.zzc("expiresIn").zzd();
                        long jZze = zze(jCurrentTimeMillis, strZzd4);
                        String strValueOf = String.valueOf(strZzd);
                        Log.i("MLKitFbInstsRestClient", strValueOf.length() != 0 ? "installation name: ".concat(strValueOf) : new String("installation name: "));
                        String strValueOf2 = String.valueOf(zzmzVar2.zza());
                        Log.d("MLKitFbInstsRestClient", strValueOf2.length() != 0 ? "fid: ".concat(strValueOf2) : new String("fid: "));
                        String strValueOf3 = String.valueOf(strZzd2);
                        Log.d("MLKitFbInstsRestClient", strValueOf3.length() != 0 ? "refresh_token: ".concat(strValueOf3) : new String("refresh_token: "));
                        String strValueOf4 = String.valueOf(zzcmVarZza);
                        StringBuilder sb = new StringBuilder(String.valueOf(strValueOf4).length() + 12);
                        sb.append("auth token: ");
                        sb.append(strValueOf4);
                        Log.d("MLKitFbInstsRestClient", sb.toString());
                        String strValueOf5 = String.valueOf(strZzd4);
                        Log.d("MLKitFbInstsRestClient", strValueOf5.length() != 0 ? "auth token expires in: ".concat(strValueOf5) : new String("auth token expires in: "));
                        StringBuilder sb2 = new StringBuilder(39);
                        sb2.append("auth token expiry: ");
                        sb2.append(jZze);
                        Log.d("MLKitFbInstsRestClient", sb2.toString());
                        this.zzd = new zznk(zzmzVar2, strZzd2, strZzd3, jZze);
                        this.zze.zza(zzkp.INSTALLATION_ID_FIS_CREATE_INSTALLATION, zzngVar2);
                        return true;
                    } catch (ClassCastException | IllegalStateException | NullPointerException e) {
                        String strValueOf6 = String.valueOf(zzcmVarZzb);
                        StringBuilder sb3 = new StringBuilder(String.valueOf(str).length() + 75 + strZzf.length() + String.valueOf(strValueOf6).length());
                        sb3.append("Error traversing JSON object returned from url <");
                        sb3.append(str);
                        sb3.append(">:\nraw json:\n");
                        sb3.append(strZzf);
                        sb3.append("\nparsed json:\n");
                        sb3.append(strValueOf6);
                        Log.e("MLKitFbInstsRestClient", sb3.toString(), e);
                        zzngVar2.zzd(zzmq.RPC_RETURNED_INVALID_RESULT);
                        zzngVar.zzb(zzmq.RPC_RETURNED_INVALID_RESULT);
                        zznhVar = this.zze;
                    }
                } catch (zzcq | IllegalStateException | NullPointerException e2) {
                    StringBuilder sb4 = new StringBuilder(String.valueOf(str).length() + 44 + strZzf.length());
                    sb4.append("Error parsing JSON object returned from <");
                    sb4.append(str);
                    sb4.append(">:\n");
                    sb4.append(strZzf);
                    Log.e("MLKitFbInstsRestClient", sb4.toString(), e2);
                    zzngVar2.zzd(zzmq.RPC_RETURNED_MALFORMED_RESULT);
                    zzngVar.zzb(zzmq.RPC_RETURNED_MALFORMED_RESULT);
                    zznhVar = this.zze;
                }
            }
            zznhVar.zza(zzkp.INSTALLATION_ID_FIS_CREATE_INSTALLATION, zzngVar2);
            return false;
        } catch (Throwable th) {
            this.zze.zza(zzkp.INSTALLATION_ID_FIS_CREATE_INSTALLATION, zzngVar2);
            throw th;
        }
    }

    public final boolean zzc(final zzng zzngVar) throws InterruptedException {
        if (this.zzd == null) {
            return false;
        }
        boolean zZza = zzpc.zza(new zzpb() { // from class: com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zznb
            @Override // com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzpb
            public final boolean zza() {
                return this.zza.zzd(zzngVar);
            }
        });
        if (!zZza) {
            zzngVar.zzc(zzmq.RPC_EXPONENTIAL_BACKOFF_FAILED);
        }
        return zZza;
    }

    public final boolean zzd(zzng zzngVar) {
        zznh zznhVar;
        String str = String.format("%s/projects/%s/installations/%s/authTokens:generate", this.zzf, this.zza.zzc(), this.zzd.zzb().zza());
        Headers.Builder builder = new Headers.Builder();
        String strValueOf = String.valueOf(this.zzd.zzc());
        Headers headersBuild = builder.add("authorization", strValueOf.length() != 0 ? "FIS_v2 ".concat(strValueOf) : new String("FIS_v2 ")).add("x-goog-api-key", this.zza.zza()).build();
        String str2 = String.format("{installation:{sdkVersion:'%s'}}", "o:a:mlkit:1.0.0");
        long jCurrentTimeMillis = System.currentTimeMillis();
        zzng zzngVar2 = new zzng();
        zzngVar2.zzg();
        String strZzf = zzf(headersBuild, str, str2, zzngVar, zzngVar2);
        zzngVar2.zze();
        if (strZzf == null) {
            zznhVar = this.zze;
        } else {
            try {
                try {
                    zzcm zzcmVarZzb = zzco.zzb(strZzf).zzb();
                    try {
                        String strZzd = zzcmVarZzb.zzc("token").zzd();
                        String strZzd2 = zzcmVarZzb.zzc("expiresIn").zzd();
                        long jZze = zze(jCurrentTimeMillis, strZzd2);
                        String strValueOf2 = String.valueOf(strZzd);
                        Log.d("MLKitFbInstsRestClient", strValueOf2.length() != 0 ? "refreshed auth token: ".concat(strValueOf2) : new String("refreshed auth token: "));
                        String strValueOf3 = String.valueOf(strZzd2);
                        Log.d("MLKitFbInstsRestClient", strValueOf3.length() != 0 ? "auth token expires in: ".concat(strValueOf3) : new String("auth token expires in: "));
                        StringBuilder sb = new StringBuilder(39);
                        sb.append("auth token expiry: ");
                        sb.append(jZze);
                        Log.d("MLKitFbInstsRestClient", sb.toString());
                        this.zzd = new zznk(this.zzd.zzb(), this.zzd.zzc(), strZzd, jZze);
                        this.zze.zza(zzkp.INSTALLATION_ID_FIS_GENERATE_AUTH_TOKEN, zzngVar2);
                        return true;
                    } catch (ClassCastException | IllegalStateException | NullPointerException e) {
                        zzngVar2.zzd(zzmq.RPC_RETURNED_INVALID_RESULT);
                        zzngVar.zzb(zzmq.RPC_RETURNED_INVALID_RESULT);
                        String strValueOf4 = String.valueOf(zzcmVarZzb);
                        StringBuilder sb2 = new StringBuilder(String.valueOf(str).length() + 71 + strZzf.length() + String.valueOf(strValueOf4).length());
                        sb2.append("Error traversing JSON object returned from <");
                        sb2.append(str);
                        sb2.append(">:\nraw json:\n");
                        sb2.append(strZzf);
                        sb2.append("\nparsed json:\n");
                        sb2.append(strValueOf4);
                        Log.e("MLKitFbInstsRestClient", sb2.toString(), e);
                        zznhVar = this.zze;
                    }
                } catch (zzcq e2) {
                    StringBuilder sb3 = new StringBuilder(String.valueOf(str).length() + 44 + strZzf.length());
                    sb3.append("Error parsing JSON object returned from <");
                    sb3.append(str);
                    sb3.append(">:\n");
                    sb3.append(strZzf);
                    Log.e("MLKitFbInstsRestClient", sb3.toString(), e2);
                    zzngVar2.zzd(zzmq.RPC_RETURNED_MALFORMED_RESULT);
                    zzngVar.zzb(zzmq.RPC_RETURNED_MALFORMED_RESULT);
                    zznhVar = this.zze;
                }
            } catch (Throwable th) {
                this.zze.zza(zzkp.INSTALLATION_ID_FIS_GENERATE_AUTH_TOKEN, zzngVar2);
                throw th;
            }
        }
        zznhVar.zza(zzkp.INSTALLATION_ID_FIS_GENERATE_AUTH_TOKEN, zzngVar2);
        return false;
    }
}
