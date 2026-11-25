package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import com.google.android.gms.common.util.AndroidUtilsLight;
import com.google.android.gms.common.util.Hex;
import com.reandroid.arsc.chunk.TypeBlock;
import com.reandroid.arsc.value.ResConfigBase;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
final class zzol {
    private static final Pattern zza = Pattern.compile("^[^:]+:([0-9]+):(android|ios|web):([0-9a-f]+)");
    private final Context zzb;
    private final String zzc;
    private final String zzd;
    private final String zze;
    private final zzov zzf;

    public zzol(Context context, String str, String str2, String str3, long j, long j2, zzov zzovVar) {
        this.zzb = context;
        this.zzc = str;
        this.zzd = str2;
        Matcher matcher = zza.matcher(str);
        this.zze = matcher.matches() ? matcher.group(1) : null;
        this.zzf = zzovVar;
    }

    final zzom zza(HttpURLConnection httpURLConnection, String str, String str2, Map<String, String> map, String str3, Map<String, String> map2, Date date, String str4, String str5, zzng zzngVar) throws zzou {
        String strBytesToStringUppercase;
        String str6;
        InputStream errorStream;
        JSONObject jSONObject;
        JSONArray jSONArray;
        zzom zzomVarZzc;
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setConnectTimeout((int) TimeUnit.SECONDS.toMillis(5L));
        httpURLConnection.setReadTimeout((int) TimeUnit.SECONDS.toMillis(5L));
        httpURLConnection.setRequestProperty("If-None-Match", null);
        httpURLConnection.setRequestProperty("X-Goog-Api-Key", this.zzd);
        httpURLConnection.setRequestProperty("X-Android-Package", this.zzb.getPackageName());
        try {
            Context context = this.zzb;
            byte[] packageCertificateHashBytes = AndroidUtilsLight.getPackageCertificateHashBytes(context, context.getPackageName());
            if (packageCertificateHashBytes == null) {
                String strValueOf = String.valueOf(this.zzb.getPackageName());
                Log.e("MLKitRemoteConfigFetch", strValueOf.length() != 0 ? "Could not get fingerprint hash for package: ".concat(strValueOf) : new String("Could not get fingerprint hash for package: "));
                strBytesToStringUppercase = null;
            } else {
                strBytesToStringUppercase = Hex.bytesToStringUppercase(packageCertificateHashBytes, false);
            }
        } catch (PackageManager.NameNotFoundException e) {
            String strValueOf2 = String.valueOf(this.zzb.getPackageName());
            Log.e("MLKitRemoteConfigFetch", strValueOf2.length() != 0 ? "No such package: ".concat(strValueOf2) : new String("No such package: "), e);
        }
        httpURLConnection.setRequestProperty("X-Android-Cert", strBytesToStringUppercase);
        httpURLConnection.setRequestProperty("X-Google-GFE-Can-Retry", "yes");
        httpURLConnection.setRequestProperty("Content-Type", "application/json");
        httpURLConnection.setRequestProperty("Accept", "application/json");
        for (Map.Entry entry : ((zzaw) map2).entrySet()) {
            httpURLConnection.setRequestProperty((String) entry.getKey(), (String) entry.getValue());
        }
        if (Log.isLoggable("MLKitRemoteConfigFetch", 3)) {
            for (Map.Entry<String, List<String>> entry2 : httpURLConnection.getRequestProperties().entrySet()) {
                String key = entry2.getKey();
                if (!zza.zza(key).contains("api-key") && !zza.zza(key).contains("android-cert")) {
                    for (String str7 : entry2.getValue()) {
                        StringBuilder sb = new StringBuilder(String.valueOf(key).length() + 23 + String.valueOf(str7).length());
                        sb.append("HTTP Request Header: ");
                        sb.append(key);
                        sb.append(": ");
                        sb.append(str7);
                        Log.d("MLKitRemoteConfigFetch", sb.toString());
                    }
                }
            }
        }
        HashMap map3 = new HashMap();
        if (str == null) {
            throw new zzot("Fetch failed: Firebase instance id is null.");
        }
        map3.put("appInstanceId", str);
        map3.put("appInstanceIdToken", str2);
        map3.put("appId", this.zzc);
        Locale locale = this.zzb.getResources().getConfiguration().locale;
        map3.put("countryCode", locale.getCountry());
        map3.put("languageCode", locale.toString());
        map3.put("platformVersion", Integer.toString(Build.VERSION.SDK_INT));
        map3.put("timeZone", TimeZone.getDefault().getID());
        try {
            PackageInfo packageInfo = this.zzb.getPackageManager().getPackageInfo(this.zzb.getPackageName(), 0);
            str6 = packageInfo != null ? packageInfo.versionName : null;
        } catch (PackageManager.NameNotFoundException unused) {
        }
        String str8 = String.format("%s-MLKit-%s", str6, str5);
        String strValueOf3 = String.valueOf(str8);
        Log.d("MLKitRemoteConfigFetch", strValueOf3.length() != 0 ? "APP_VERSION: ".concat(strValueOf3) : new String("APP_VERSION: "));
        map3.put("appVersion", str8);
        map3.put("packageName", this.zzb.getPackageName());
        map3.put(ResConfigBase.NAME_sdkVersion, "o:a:mlkit:1.0.0");
        map3.put("analyticsUserProperties", new JSONObject(map));
        JSONObject jSONObject2 = new JSONObject(map3);
        if (Log.isLoggable("MLKitRemoteConfigFetch", 3)) {
            JSONObject jSONObject3 = new JSONObject();
            Iterator<String> itKeys = jSONObject2.keys();
            while (itKeys.hasNext()) {
                String next = itKeys.next();
                try {
                    jSONObject3.put(next, jSONObject2.get(next));
                } catch (JSONException e2) {
                    throw new IllegalStateException("shallowCopyJsonObject: concurrent mutation?", e2);
                }
            }
            jSONObject3.remove("appInstanceIdToken");
            String strValueOf4 = String.valueOf(jSONObject3);
            StringBuilder sb2 = new StringBuilder(String.valueOf(strValueOf4).length() + 19);
            sb2.append("HTTP Request Body: ");
            sb2.append(strValueOf4);
            Log.d("MLKitRemoteConfigFetch", sb2.toString());
        }
        byte[] bytes = jSONObject2.toString().getBytes(zzb.zzc);
        zzng zzngVar2 = new zzng();
        zzngVar2.zzg();
        try {
            Log.d("MLKitRemoteConfigFetch", "Getting remote config");
            String strValueOf5 = String.valueOf(httpURLConnection);
            StringBuilder sb3 = new StringBuilder(String.valueOf(strValueOf5).length() + 12);
            sb3.append("Connecting: ");
            sb3.append(strValueOf5);
            Log.d("MLKitRemoteConfigFetch", sb3.toString());
            try {
                httpURLConnection.setFixedLengthStreamingMode(bytes.length);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(httpURLConnection.getOutputStream());
                bufferedOutputStream.write(bytes);
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
                httpURLConnection.connect();
                int responseCode = httpURLConnection.getResponseCode();
                try {
                    zzngVar2.zzf(responseCode);
                    StringBuilder sb4 = new StringBuilder(44);
                    sb4.append("Remote config: got response code ");
                    sb4.append(responseCode);
                    Log.d("MLKitRemoteConfigFetch", sb4.toString());
                    if (responseCode != 200) {
                        zzngVar2.zzd(zzmq.RPC_ERROR);
                        zzngVar.zzb(zzmq.RPC_ERROR);
                        if (Log.isLoggable("MLKitRemoteConfigFetch", 4)) {
                            for (Map.Entry<String, List<String>> entry3 : httpURLConnection.getHeaderFields().entrySet()) {
                                for (String str9 : entry3.getValue()) {
                                    String key2 = entry3.getKey();
                                    StringBuilder sb5 = new StringBuilder(String.valueOf(key2).length() + 24 + String.valueOf(str9).length());
                                    sb5.append("HTTP Response Header: ");
                                    sb5.append(key2);
                                    sb5.append(": ");
                                    sb5.append(str9);
                                    Log.i("MLKitRemoteConfigFetch", sb5.toString());
                                }
                            }
                        }
                        if (Log.isLoggable("MLKitRemoteConfigFetch", 5) && (errorStream = httpURLConnection.getErrorStream()) != null) {
                            Scanner scannerUseDelimiter = new Scanner(errorStream).useDelimiter("\\A");
                            if (scannerUseDelimiter.hasNext()) {
                                String strValueOf6 = String.valueOf(scannerUseDelimiter.next());
                                Log.w("MLKitRemoteConfigFetch", strValueOf6.length() != 0 ? "HTTP Error Stream: ".concat(strValueOf6) : new String("HTTP Error Stream: "));
                            }
                        }
                        try {
                            throw new zzoy(responseCode, httpURLConnection.getResponseMessage());
                        } catch (IOException e3) {
                            throw new zzoy(responseCode, "<error reading HTTP response>", e3);
                        }
                    }
                    if (Log.isLoggable("MLKitRemoteConfigFetch", 3)) {
                        for (Map.Entry<String, List<String>> entry4 : httpURLConnection.getHeaderFields().entrySet()) {
                            for (String str10 : entry4.getValue()) {
                                String key3 = entry4.getKey();
                                StringBuilder sb6 = new StringBuilder(String.valueOf(key3).length() + 24 + String.valueOf(str10).length());
                                sb6.append("HTTP Response Header: ");
                                sb6.append(key3);
                                sb6.append(": ");
                                sb6.append(str10);
                                Log.d("MLKitRemoteConfigFetch", sb6.toString());
                            }
                        }
                    }
                    String headerField = httpURLConnection.getHeaderField("ETag");
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), zzb.zzc));
                        StringBuilder sb7 = new StringBuilder();
                        while (true) {
                            int i = bufferedReader.read();
                            if (i == -1) {
                                break;
                            }
                            sb7.append((char) i);
                        }
                        JSONObject jSONObject4 = new JSONObject(sb7.toString());
                        String strValueOf7 = String.valueOf(headerField);
                        Log.d("MLKitRemoteConfigFetch", strValueOf7.length() != 0 ? "Remote config: got response ETag: ".concat(strValueOf7) : new String("Remote config: got response ETag: "));
                        String strValueOf8 = String.valueOf(jSONObject4);
                        StringBuilder sb8 = new StringBuilder(String.valueOf(strValueOf8).length() + 29);
                        sb8.append("Remote config: got response: ");
                        sb8.append(strValueOf8);
                        Log.d("MLKitRemoteConfigFetch", sb8.toString());
                        Log.d("MLKitRemoteConfigFetch", "Remote config: disconnecting...");
                        httpURLConnection.disconnect();
                        Log.d("MLKitRemoteConfigFetch", "Remote config: disconnected");
                        try {
                            if (jSONObject4.get("state").equals("NO_CHANGE")) {
                                zzomVarZzc = zzom.zzb(date);
                            } else {
                                try {
                                    zzoj zzojVarZza = zzok.zza();
                                    zzojVarZza.zzc(date);
                                    try {
                                        jSONObject = jSONObject4.getJSONObject(TypeBlock.NAME_entries);
                                    } catch (JSONException unused2) {
                                        jSONObject = null;
                                    }
                                    if (jSONObject != null) {
                                        zzojVarZza.zza(jSONObject);
                                    }
                                    try {
                                        jSONArray = jSONObject4.getJSONArray("experimentDescriptions");
                                    } catch (JSONException unused3) {
                                        jSONArray = null;
                                    }
                                    if (jSONArray != null) {
                                        zzojVarZza.zzb(jSONArray);
                                    }
                                    zzomVarZzc = zzom.zzc(zzojVarZza.zzd(), headerField);
                                } catch (JSONException e4) {
                                    zzngVar2.zzd(zzmq.RPC_RETURNED_INVALID_RESULT);
                                    zzngVar.zzb(zzmq.RPC_RETURNED_INVALID_RESULT);
                                    throw new zzot("Fetch failed: fetch response could not be parsed.", e4);
                                }
                            }
                        } catch (JSONException unused4) {
                        }
                        zzngVar2.zze();
                        this.zzf.zzd(zzngVar2);
                        return zzomVarZzc;
                    } catch (IOException e5) {
                        zzngVar2.zzd(zzmq.RPC_ERROR);
                        zzngVar.zzb(zzmq.RPC_ERROR);
                        throw new zzot("The client had an error while calling the backend!", e5);
                    } catch (JSONException e6) {
                        zzngVar2.zzd(zzmq.RPC_RETURNED_MALFORMED_RESULT);
                        zzngVar.zzb(zzmq.RPC_RETURNED_MALFORMED_RESULT);
                        throw new zzot("Error parsing the server output", e6);
                    }
                } catch (Throwable th) {
                    Log.d("MLKitRemoteConfigFetch", "Remote config: disconnecting...");
                    httpURLConnection.disconnect();
                    Log.d("MLKitRemoteConfigFetch", "Remote config: disconnected");
                    throw th;
                }
            } catch (IOException e7) {
                zzngVar2.zzd(zzmq.NO_CONNECTION);
                zzngVar.zzb(zzmq.NO_CONNECTION);
                String strValueOf9 = String.valueOf(httpURLConnection);
                StringBuilder sb9 = new StringBuilder(String.valueOf(strValueOf9).length() + 20);
                sb9.append("Error connecting to ");
                sb9.append(strValueOf9);
                throw new zzot(sb9.toString(), e7);
            }
        } catch (Throwable th2) {
            zzngVar2.zze();
            this.zzf.zzd(zzngVar2);
            throw th2;
        }
    }

    public final HttpURLConnection zzb() throws zzou {
        try {
            return (HttpURLConnection) new URL(String.format("https://firebaseremoteconfig.googleapis.com/v1/projects/%s/namespaces/%s:fetch", this.zze, "firebase")).openConnection();
        } catch (IOException e) {
            throw new zzou(e.getMessage());
        }
    }
}
