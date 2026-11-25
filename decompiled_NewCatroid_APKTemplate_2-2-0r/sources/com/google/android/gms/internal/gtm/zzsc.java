package com.google.android.gms.internal.gtm;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzsc implements zzsd {
    private HttpURLConnection zza;
    private InputStream zzb = null;

    zzsc() {
    }

    @Override // com.google.android.gms.internal.gtm.zzsd
    public final InputStream zza(String str) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        httpURLConnection.setReadTimeout(20000);
        httpURLConnection.setConnectTimeout(20000);
        this.zza = httpURLConnection;
        int responseCode = httpURLConnection.getResponseCode();
        if (responseCode == 200) {
            InputStream inputStream = httpURLConnection.getInputStream();
            this.zzb = inputStream;
            return inputStream;
        }
        StringBuilder sb = new StringBuilder(25);
        sb.append("Bad response: ");
        sb.append(responseCode);
        String string = sb.toString();
        if (responseCode == 404) {
            throw new FileNotFoundException(string);
        }
        if (responseCode == 503) {
            throw new zzsf(string);
        }
        throw new IOException(string);
    }

    @Override // com.google.android.gms.internal.gtm.zzsd
    public final void zzb() {
        HttpURLConnection httpURLConnection = this.zza;
        try {
            InputStream inputStream = this.zzb;
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException e) {
            String strValueOf = String.valueOf(e.getMessage());
            com.google.android.gms.tagmanager.zzdh.zzb(strValueOf.length() != 0 ? "HttpUrlConnectionNetworkClient: Error when closing http input stream: ".concat(strValueOf) : new String("HttpUrlConnectionNetworkClient: Error when closing http input stream: "), e);
        }
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
    }
}
