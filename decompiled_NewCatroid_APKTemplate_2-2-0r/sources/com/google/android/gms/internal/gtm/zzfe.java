package com.google.android.gms.internal.gtm;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import com.google.android.gms.common.internal.Preconditions;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.zip.GZIPOutputStream;
import kotlin.text.Typography;
import net.bytebuddy.description.type.TypeDescription;
import org.apache.commons.compress.compressors.CompressorStreamFactory;
import org.eclipse.jgit.util.HttpSupport;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzfe extends zzbs {
    private static final byte[] zza = "\n".getBytes();
    private final String zzb;
    private final zzfo zzc;

    zzfe(zzbv zzbvVar) {
        super(zzbvVar);
        this.zzb = String.format("%s/%s (Linux; U; Android %s; %s; %s Build/%s)", "GoogleAnalytics", zzbt.zza, Build.VERSION.RELEASE, zzfs.zzd(Locale.getDefault()), Build.MODEL, Build.ID);
        this.zzc = new zzfo(zzbvVar.zzr());
    }

    /* JADX WARN: Code duplicated, block: B:46:0x009f  */
    /* JADX WARN: Code duplicated, block: B:48:0x0095 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:60:? A[SYNTHETIC] */
    private final int zzg(URL url, byte[] bArr) throws Throwable {
        Throwable th;
        HttpURLConnection httpURLConnectionZzb;
        IOException e;
        OutputStream outputStream;
        Preconditions.checkNotNull(url);
        Preconditions.checkNotNull(bArr);
        int length = bArr.length;
        zzH("POST bytes, url", Integer.valueOf(length), url);
        if (zzV()) {
            zzP("Post payload\n", new String(bArr));
        }
        OutputStream outputStream2 = null;
        outputStream2 = null;
        outputStream2 = null;
        HttpURLConnection httpURLConnection = null;
        try {
            zzo().getPackageName();
            httpURLConnectionZzb = zzb(url);
            try {
                httpURLConnectionZzb.setDoOutput(true);
                httpURLConnectionZzb.setFixedLengthStreamingMode(length);
                httpURLConnectionZzb.connect();
                outputStream2 = httpURLConnectionZzb.getOutputStream();
                outputStream2.write(bArr);
                zzk(httpURLConnectionZzb);
                int responseCode = httpURLConnectionZzb.getResponseCode();
                if (responseCode == 200) {
                    zzs().zzi();
                    responseCode = 200;
                }
                zzG("POST status", Integer.valueOf(responseCode));
                if (outputStream2 != null) {
                    try {
                        outputStream2.close();
                    } catch (IOException e2) {
                        zzK("Error closing http post connection output stream", e2);
                    }
                }
                if (httpURLConnectionZzb != null) {
                    httpURLConnectionZzb.disconnect();
                }
                return responseCode;
            } catch (IOException e3) {
                e = e3;
                OutputStream outputStream3 = outputStream2;
                httpURLConnection = httpURLConnectionZzb;
                outputStream = outputStream3;
                try {
                    zzS("Network POST connection error", e);
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (IOException e4) {
                            zzK("Error closing http post connection output stream", e4);
                        }
                    }
                    if (httpURLConnection == null) {
                        return 0;
                    }
                    httpURLConnection.disconnect();
                    return 0;
                } catch (Throwable th2) {
                    th = th2;
                    HttpURLConnection httpURLConnection2 = httpURLConnection;
                    outputStream2 = outputStream;
                    httpURLConnectionZzb = httpURLConnection2;
                    if (outputStream2 != null) {
                        try {
                            outputStream2.close();
                        } catch (IOException e5) {
                            zzK("Error closing http post connection output stream", e5);
                        }
                    }
                    if (httpURLConnectionZzb != null) {
                        throw th;
                    }
                    httpURLConnectionZzb.disconnect();
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                if (outputStream2 != null) {
                    outputStream2.close();
                }
                if (httpURLConnectionZzb != null) {
                    throw th;
                }
                httpURLConnectionZzb.disconnect();
                throw th;
            }
        } catch (IOException e6) {
            e = e6;
            outputStream = null;
        } catch (Throwable th4) {
            th = th4;
            httpURLConnectionZzb = null;
        }
    }

    private final URL zzh() {
        zzw();
        String strZzi = zzct.zzi();
        zzw();
        String strZzb = zzeu.zzt.zzb();
        try {
            return new URL(strZzb.length() != 0 ? strZzi.concat(strZzb) : new String(strZzi));
        } catch (MalformedURLException e) {
            zzK("Error trying to parse the hardcoded host url", e);
            return null;
        }
    }

    private final URL zzi(zzex zzexVar) {
        String str;
        String strConcat;
        if (zzexVar.zzh()) {
            zzw();
            String strZzi = zzct.zzi();
            zzw();
            String strZzj = zzct.zzj();
            if (strZzj.length() != 0) {
                strConcat = strZzi.concat(strZzj);
            } else {
                str = new String(strZzi);
                strConcat = str;
            }
        } else {
            zzw();
            String strZzk = zzct.zzk();
            zzw();
            String strZzj2 = zzct.zzj();
            if (strZzj2.length() != 0) {
                strConcat = strZzk.concat(strZzj2);
            } else {
                str = new String(strZzk);
                strConcat = str;
            }
        }
        try {
            return new URL(strConcat);
        } catch (MalformedURLException e) {
            zzK("Error trying to parse the hardcoded host url", e);
            return null;
        }
    }

    private final URL zzj(zzex zzexVar, String str) {
        String string;
        if (zzexVar.zzh()) {
            zzw();
            String strZzi = zzct.zzi();
            zzw();
            String strZzj = zzct.zzj();
            int length = strZzi.length();
            StringBuilder sb = new StringBuilder(length + 1 + strZzj.length() + str.length());
            sb.append(strZzi);
            sb.append(strZzj);
            sb.append(TypeDescription.Generic.OfWildcardType.SYMBOL);
            sb.append(str);
            string = sb.toString();
        } else {
            zzw();
            String strZzk = zzct.zzk();
            zzw();
            String strZzj2 = zzct.zzj();
            int length2 = strZzk.length();
            StringBuilder sb2 = new StringBuilder(length2 + 1 + strZzj2.length() + str.length());
            sb2.append(strZzk);
            sb2.append(strZzj2);
            sb2.append(TypeDescription.Generic.OfWildcardType.SYMBOL);
            sb2.append(str);
            string = sb2.toString();
        }
        try {
            return new URL(string);
        } catch (MalformedURLException e) {
            zzK("Error trying to parse the hardcoded host url", e);
            return null;
        }
    }

    private final void zzk(HttpURLConnection httpURLConnection) throws Throwable {
        InputStream inputStream;
        try {
            inputStream = httpURLConnection.getInputStream();
            try {
                do {
                } while (inputStream.read(new byte[1024]) > 0);
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        zzK("Error closing http connection input stream", e);
                    }
                }
            } catch (Throwable th) {
                th = th;
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e2) {
                        zzK("Error closing http connection input stream", e2);
                    }
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            inputStream = null;
        }
    }

    private static final void zzl(StringBuilder sb, String str, String str2) throws UnsupportedEncodingException {
        if (sb.length() != 0) {
            sb.append(Typography.amp);
        }
        sb.append(URLEncoder.encode(str, "UTF-8"));
        sb.append('=');
        sb.append(URLEncoder.encode(str2, "UTF-8"));
    }

    final String zza(zzex zzexVar, boolean z) {
        Preconditions.checkNotNull(zzexVar);
        StringBuilder sb = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : zzexVar.zzg().entrySet()) {
                String key = entry.getKey();
                if (!"ht".equals(key) && !"qt".equals(key) && !"AppUID".equals(key) && !CompressorStreamFactory.Z.equals(key) && !"_gmsv".equals(key)) {
                    zzl(sb, key, entry.getValue());
                }
            }
            zzl(sb, "ht", String.valueOf(zzexVar.zzd()));
            zzl(sb, "qt", String.valueOf(zzC().currentTimeMillis() - zzexVar.zzd()));
            zzw();
            if (z) {
                long jZzc = zzexVar.zzc();
                zzl(sb, CompressorStreamFactory.Z, jZzc != 0 ? String.valueOf(jZzc) : String.valueOf(zzexVar.zzb()));
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            zzK("Failed to encode name or value", e);
            return null;
        }
    }

    final HttpURLConnection zzb(URL url) throws IOException {
        URLConnection uRLConnectionOpenConnection = url.openConnection();
        if (!(uRLConnectionOpenConnection instanceof HttpURLConnection)) {
            throw new IOException("Failed to obtain http connection");
        }
        HttpURLConnection httpURLConnection = (HttpURLConnection) uRLConnectionOpenConnection;
        httpURLConnection.setDefaultUseCaches(false);
        zzw();
        httpURLConnection.setConnectTimeout(zzeu.zzE.zzb().intValue());
        zzw();
        httpURLConnection.setReadTimeout(zzeu.zzF.zzb().intValue());
        httpURLConnection.setInstanceFollowRedirects(false);
        httpURLConnection.setRequestProperty("User-Agent", this.zzb);
        httpURLConnection.setDoInput(true);
        return httpURLConnection;
    }

    /* JADX WARN: Code duplicated, block: B:102:0x01f3  */
    /* JADX WARN: Code duplicated, block: B:103:0x0201  */
    /* JADX WARN: Code duplicated, block: B:105:0x0218  */
    /* JADX WARN: Code duplicated, block: B:143:0x02c7  */
    /* JADX WARN: Code duplicated, block: B:163:0x01c0 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:165:0x01da A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:182:0x0326 A[EDGE_INSN: B:182:0x0326->B:158:0x0326 BREAK  A[LOOP:1: B:109:0x0234->B:185:?], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:185:? A[LOOP:1: B:109:0x0234->B:185:?, LOOP_END, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:186:? A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:27:0x0088  */
    /* JADX WARN: Code duplicated, block: B:88:0x01cb  */
    /* JADX WARN: Code duplicated, block: B:98:0x01e5  */
    public final List<Long> zzc(List<zzex> list) throws Throwable {
        boolean z;
        boolean z2;
        HttpURLConnection httpURLConnection;
        int size;
        int iZzg;
        Integer numValueOf;
        Throwable th;
        OutputStream outputStream;
        HttpURLConnection httpURLConnectionZzb;
        HttpURLConnection httpURLConnection2;
        OutputStream outputStream2;
        com.google.android.gms.analytics.zzr.zzh();
        zzW();
        Preconditions.checkNotNull(list);
        if (zzw().zza().isEmpty()) {
            z = false;
            z2 = false;
        } else {
            zzfo zzfoVar = this.zzc;
            zzw();
            if (zzfoVar.zzc(((long) zzeu.zzC.zzb().intValue()) * 1000)) {
                zzw();
                String strZzb = zzeu.zzv.zzb();
                z = "BATCH_BY_SESSION".equalsIgnoreCase(strZzb) || "BATCH_BY_TIME".equalsIgnoreCase(strZzb) || "BATCH_BY_BRUTE_FORCE".equalsIgnoreCase(strZzb) || "BATCH_BY_COUNT".equalsIgnoreCase(strZzb) || "BATCH_BY_SIZE".equalsIgnoreCase(strZzb);
                zzw();
                if (true == "GZIP".equalsIgnoreCase(zzeu.zzw.zzb())) {
                    z2 = true;
                }
            } else {
                z = false;
            }
            z2 = false;
        }
        if (!z) {
            ArrayList arrayList = new ArrayList(list.size());
            for (zzex zzexVar : list) {
                Preconditions.checkNotNull(zzexVar);
                String strZza = zza(zzexVar, !zzexVar.zzh());
                if (strZza != null) {
                    int length = strZza.length();
                    zzw();
                    if (length > zzeu.zzu.zzb().intValue()) {
                        String strZza2 = zza(zzexVar, false);
                        if (strZza2 != null) {
                            byte[] bytes = strZza2.getBytes();
                            int length2 = bytes.length;
                            zzw();
                            if (length2 <= zzeu.zzz.zzb().intValue()) {
                                URL urlZzi = zzi(zzexVar);
                                if (urlZzi == null) {
                                    zzJ("Failed to build collect POST endpoint url");
                                    break;
                                }
                                if (zzg(urlZzi, bytes) != 200) {
                                    break;
                                }
                            } else {
                                zzz().zzb(zzexVar, "Hit payload exceeds size limit");
                            }
                        } else {
                            zzz().zzb(zzexVar, "Error formatting hit for POST upload");
                        }
                        arrayList.add(Long.valueOf(zzexVar.zzb()));
                        size = arrayList.size();
                        zzw();
                        if (size >= zzct.zzh()) {
                            break;
                        }
                    } else {
                        URL urlZzj = zzj(zzexVar, strZza);
                        if (urlZzj == null) {
                            zzJ("Failed to build collect GET endpoint url");
                            break;
                        }
                        Preconditions.checkNotNull(urlZzj);
                        zzG("GET request", urlZzj);
                        try {
                            HttpURLConnection httpURLConnectionZzb2 = zzb(urlZzj);
                            try {
                                httpURLConnectionZzb2.connect();
                                zzk(httpURLConnectionZzb2);
                                int responseCode = httpURLConnectionZzb2.getResponseCode();
                                if (responseCode == 200) {
                                    zzs().zzi();
                                    responseCode = 200;
                                }
                                zzG("GET status", Integer.valueOf(responseCode));
                                if (httpURLConnectionZzb2 != null) {
                                    httpURLConnectionZzb2.disconnect();
                                }
                                if (responseCode != 200) {
                                    break;
                                }
                            } catch (IOException e) {
                                e = e;
                                httpURLConnection = httpURLConnectionZzb2;
                                try {
                                    zzS("Network GET connection error", e);
                                    if (httpURLConnection != null) {
                                        httpURLConnection.disconnect();
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                    if (httpURLConnection != null) {
                                        httpURLConnection.disconnect();
                                    }
                                    throw th;
                                }
                            } catch (Throwable th3) {
                                th = th3;
                                httpURLConnection = httpURLConnectionZzb2;
                                if (httpURLConnection != null) {
                                    httpURLConnection.disconnect();
                                }
                                throw th;
                            }
                        } catch (IOException e2) {
                            e = e2;
                            httpURLConnection = null;
                        } catch (Throwable th4) {
                            th = th4;
                            httpURLConnection = null;
                        }
                    }
                } else {
                    zzz().zzb(zzexVar, "Error formatting hit for upload");
                }
                arrayList.add(Long.valueOf(zzexVar.zzb()));
                size = arrayList.size();
                zzw();
                if (size >= zzct.zzh()) {
                    break;
                    break;
                }
            }
            return arrayList;
        }
        Preconditions.checkArgument(!list.isEmpty());
        zzQ("Uploading batched hits. compression, count", Boolean.valueOf(z2), Integer.valueOf(list.size()));
        zzfd zzfdVar = new zzfd(this);
        ArrayList arrayList2 = new ArrayList();
        for (zzex zzexVar2 : list) {
            if (!zzfdVar.zzb(zzexVar2)) {
                break;
            }
            arrayList2.add(Long.valueOf(zzexVar2.zzb()));
        }
        if (zzfdVar.zza() == 0) {
            return arrayList2;
        }
        URL urlZzh = zzh();
        if (urlZzh == null) {
            zzJ("Failed to build batching endpoint url");
            return Collections.emptyList();
        }
        if (z2) {
            byte[] bArrZzc = zzfdVar.zzc();
            Preconditions.checkNotNull(urlZzh);
            Preconditions.checkNotNull(bArrZzc);
            try {
                zzo().getPackageName();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
                gZIPOutputStream.write(bArrZzc);
                gZIPOutputStream.close();
                byteArrayOutputStream.close();
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                int length3 = byteArray.length;
                Integer numValueOf2 = Integer.valueOf(length3);
                int length4 = bArrZzc.length;
                zzI("POST compressed size, ratio %, url", numValueOf2, Long.valueOf((((long) length3) * 100) / ((long) length4)), urlZzh);
                if (length3 > length4) {
                    zzT("Compressed payload is larger then uncompressed. compressed, uncompressed", numValueOf2, Integer.valueOf(length4));
                }
                if (zzV()) {
                    String str = new String(bArrZzc);
                    zzP("Post payload", str.length() != 0 ? "\n".concat(str) : new String("\n"));
                }
                httpURLConnectionZzb = zzb(urlZzh);
                try {
                    httpURLConnectionZzb.setDoOutput(true);
                    httpURLConnectionZzb.addRequestProperty("Content-Encoding", HttpSupport.ENCODING_GZIP);
                    httpURLConnectionZzb.setFixedLengthStreamingMode(length3);
                    httpURLConnectionZzb.connect();
                    OutputStream outputStream3 = httpURLConnectionZzb.getOutputStream();
                    try {
                        outputStream3.write(byteArray);
                        outputStream3.close();
                        zzk(httpURLConnectionZzb);
                        int responseCode2 = httpURLConnectionZzb.getResponseCode();
                        if (responseCode2 == 200) {
                            zzs().zzi();
                            responseCode2 = 200;
                        }
                        zzG("POST status", Integer.valueOf(responseCode2));
                        if (httpURLConnectionZzb != null) {
                            httpURLConnectionZzb.disconnect();
                        }
                        iZzg = responseCode2;
                    } catch (IOException e3) {
                        e = e3;
                        httpURLConnection2 = httpURLConnectionZzb;
                        outputStream2 = outputStream3;
                        try {
                            zzS("Network compressed POST connection error", e);
                            if (outputStream2 != null) {
                                try {
                                    outputStream2.close();
                                } catch (IOException e4) {
                                    zzK("Error closing http compressed post connection output stream", e4);
                                }
                            }
                            if (httpURLConnection2 != null) {
                                httpURLConnection2.disconnect();
                            }
                            iZzg = 0;
                        } catch (Throwable th5) {
                            th = th5;
                            OutputStream outputStream4 = outputStream2;
                            httpURLConnectionZzb = httpURLConnection2;
                            outputStream = outputStream4;
                            if (outputStream != null) {
                                try {
                                    outputStream.close();
                                } catch (IOException e5) {
                                    zzK("Error closing http compressed post connection output stream", e5);
                                }
                            }
                            if (httpURLConnectionZzb != null) {
                                throw th;
                            }
                            httpURLConnectionZzb.disconnect();
                            throw th;
                        }
                    } catch (Throwable th6) {
                        th = th6;
                        outputStream = outputStream3;
                        th = th;
                        if (outputStream != null) {
                            outputStream.close();
                        }
                        if (httpURLConnectionZzb != null) {
                            throw th;
                        }
                        httpURLConnectionZzb.disconnect();
                        throw th;
                    }
                } catch (IOException e6) {
                    e = e6;
                    httpURLConnection2 = httpURLConnectionZzb;
                    outputStream2 = null;
                    zzS("Network compressed POST connection error", e);
                    if (outputStream2 != null) {
                        outputStream2.close();
                    }
                    if (httpURLConnection2 != null) {
                        httpURLConnection2.disconnect();
                    }
                    iZzg = 0;
                    if (iZzg == 200) {
                        zzP("Batched upload completed. Hits batched", Integer.valueOf(zzfdVar.zza()));
                        return arrayList2;
                    }
                    numValueOf = Integer.valueOf(iZzg);
                    zzP("Network error uploading hits. status code", numValueOf);
                    if (zzw().zza().contains(numValueOf)) {
                        zzR("Server instructed the client to stop batching");
                        this.zzc.zzb();
                    }
                    return Collections.emptyList();
                } catch (Throwable th7) {
                    th = th7;
                    outputStream = null;
                }
            } catch (IOException e7) {
                e = e7;
                httpURLConnection2 = null;
            } catch (Throwable th8) {
                th = th8;
                outputStream = null;
                httpURLConnectionZzb = null;
            }
        } else {
            iZzg = zzg(urlZzh, zzfdVar.zzc());
        }
        if (iZzg == 200) {
            zzP("Batched upload completed. Hits batched", Integer.valueOf(zzfdVar.zza()));
            return arrayList2;
        }
        numValueOf = Integer.valueOf(iZzg);
        zzP("Network error uploading hits. status code", numValueOf);
        if (zzw().zza().contains(numValueOf)) {
            zzR("Server instructed the client to stop batching");
            this.zzc.zzb();
        }
        return Collections.emptyList();
    }

    @Override // com.google.android.gms.internal.gtm.zzbs
    protected final void zzd() {
        zzP("Network initialized. User agent", this.zzb);
    }

    public final boolean zze() {
        NetworkInfo activeNetworkInfo;
        com.google.android.gms.analytics.zzr.zzh();
        zzW();
        try {
            activeNetworkInfo = ((ConnectivityManager) zzo().getSystemService("connectivity")).getActiveNetworkInfo();
        } catch (SecurityException unused) {
            activeNetworkInfo = null;
        }
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            return true;
        }
        zzO("No network connectivity");
        return false;
    }
}
