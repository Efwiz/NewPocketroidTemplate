package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import com.beust.jcommander.Parameters;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzfj implements zzbk {
    private final String zza;
    private final Context zzb;
    private final zzfi zzc;
    private final zzfh zzd = new zzfh();

    zzfj(Context context, zzfi zzfiVar) {
        String string;
        this.zzb = context.getApplicationContext();
        this.zzc = zzfiVar;
        String str = Build.VERSION.RELEASE;
        Locale locale = Locale.getDefault();
        if (locale == null || locale.getLanguage() == null || locale.getLanguage().length() == 0) {
            string = null;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(locale.getLanguage().toLowerCase());
            if (locale.getCountry() != null && locale.getCountry().length() != 0) {
                sb.append(Parameters.DEFAULT_OPTION_PREFIXES);
                sb.append(locale.getCountry().toLowerCase());
            }
            string = sb.toString();
        }
        this.zza = String.format("%s/%s (Linux; U; Android %s; %s; %s Build/%s)", "GoogleTagManager", "4.00", str, string, Build.MODEL, Build.ID);
    }

    static final URL zzc(zzca zzcaVar) {
        try {
            return new URL(zzcaVar.zzc());
        } catch (MalformedURLException unused) {
            zzdh.zza("Error trying to parse the GTM url.");
            return null;
        }
    }

    /* JADX WARN: Code duplicated, block: B:36:0x00cb  */
    /* JADX WARN: Code duplicated, block: B:37:0x00d0  */
    /* JADX WARN: Code duplicated, block: B:47:0x00ab A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // com.google.android.gms.tagmanager.zzbk
    public final void zza(List<zzca> list) throws Throwable {
        boolean z;
        IOException e;
        String strValueOf;
        String str;
        int iMin = Math.min(list.size(), 40);
        boolean z2 = true;
        for (int i = 0; i < iMin; i++) {
            zzca zzcaVar = list.get(i);
            URL urlZzc = zzc(zzcaVar);
            if (urlZzc == null) {
                zzdh.zzc("No destination: discarding hit.");
                ((zzdu) this.zzc).zza.zzl(zzcaVar.zzb());
                long jZzb = zzcaVar.zzb();
                StringBuilder sb = new StringBuilder(57);
                sb.append("Permanent failure dispatching hitId: ");
                sb.append(jZzb);
                zzdh.zzb.zzd(sb.toString());
            } else {
                try {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) urlZzc.openConnection();
                    InputStream inputStream = null;
                    if (z2) {
                        try {
                            zzdk.zza(this.zzb);
                        } catch (Throwable th) {
                            th = th;
                            z = true;
                            if (inputStream != null) {
                                try {
                                    inputStream.close();
                                } catch (IOException e2) {
                                    e = e2;
                                    strValueOf = String.valueOf(e.getClass().getSimpleName());
                                    if (strValueOf.length() != 0) {
                                        str = "Exception sending hit: ".concat(strValueOf);
                                    } else {
                                        str = new String("Exception sending hit: ");
                                    }
                                    zzdh.zzc(str);
                                    zzdh.zzc(e.getMessage());
                                    this.zzc.zza(zzcaVar);
                                    z2 = z;
                                }
                            }
                            httpURLConnection.disconnect();
                            throw th;
                        }
                    }
                    try {
                        httpURLConnection.setRequestProperty("User-Agent", this.zza);
                        int responseCode = httpURLConnection.getResponseCode();
                        inputStream = httpURLConnection.getInputStream();
                        if (responseCode != 200) {
                            StringBuilder sb2 = new StringBuilder(25);
                            sb2.append("Bad response: ");
                            sb2.append(responseCode);
                            zzdh.zzc(sb2.toString());
                            this.zzc.zza(zzcaVar);
                        } else {
                            ((zzdu) this.zzc).zza.zzl(zzcaVar.zzb());
                        }
                        if (inputStream != null) {
                            try {
                                inputStream.close();
                            } catch (IOException e3) {
                                e = e3;
                                z = false;
                                strValueOf = String.valueOf(e.getClass().getSimpleName());
                                if (strValueOf.length() != 0) {
                                    str = "Exception sending hit: ".concat(strValueOf);
                                } else {
                                    str = new String("Exception sending hit: ");
                                }
                                zzdh.zzc(str);
                                zzdh.zzc(e.getMessage());
                                this.zzc.zza(zzcaVar);
                                z2 = z;
                            }
                        }
                        httpURLConnection.disconnect();
                        z2 = false;
                    } catch (Throwable th2) {
                        th = th2;
                        z = false;
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        httpURLConnection.disconnect();
                        throw th;
                    }
                } catch (IOException e4) {
                    z = z2;
                    e = e4;
                }
            }
        }
    }

    @Override // com.google.android.gms.tagmanager.zzbk
    public final boolean zzb() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.zzb.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            return true;
        }
        zzdh.zzb.zzd("...no network connectivity");
        return false;
    }
}
