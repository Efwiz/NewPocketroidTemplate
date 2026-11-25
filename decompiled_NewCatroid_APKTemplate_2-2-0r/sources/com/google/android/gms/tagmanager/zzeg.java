package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.google.android.gms.internal.gtm.zzsa;
import com.google.android.gms.internal.gtm.zzsd;
import com.google.android.gms.internal.gtm.zzse;
import com.google.android.gms.internal.gtm.zzsf;
import com.google.android.gms.internal.gtm.zzuj;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzeg implements Runnable {
    private final Context zza;
    private final zzse zzb;
    private final String zzc;
    private final String zzd;
    private zzdg<com.google.android.gms.internal.gtm.zzai> zze;
    private volatile zzap zzf;
    private volatile String zzg;
    private volatile String zzh;

    public zzeg(Context context, String str, zzap zzapVar) {
        zzse zzseVar = new zzse();
        this.zza = context;
        this.zzb = zzseVar;
        this.zzc = str;
        this.zzf = zzapVar;
        String strValueOf = String.valueOf(str);
        String strConcat = strValueOf.length() != 0 ? "/r?id=".concat(strValueOf) : new String("/r?id=");
        this.zzd = strConcat;
        this.zzg = strConcat;
        this.zzh = null;
    }

    /* JADX WARN: Code duplicated, block: B:54:0x020d A[Catch: all -> 0x0220, TryCatch #0 {, blocks: (B:44:0x0184, B:46:0x018a, B:48:0x0199, B:49:0x01b2, B:51:0x01b4, B:52:0x01d0, B:54:0x020d, B:55:0x0214), top: B:75:0x0184 }] */
    @Override // java.lang.Runnable
    public final void run() {
        if (this.zze == null) {
            throw new IllegalStateException("callback must be set before execute");
        }
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.zza.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            zzdh.zzb.zzd("...no network connectivity");
            this.zze.zza(1);
            return;
        }
        zzdh.zzb.zzd("Start loading resource from network ...");
        String strZza = this.zzf.zza();
        String str = this.zzg;
        StringBuilder sb = new StringBuilder(strZza.length() + 12 + String.valueOf(str).length());
        sb.append(strZza);
        sb.append(str);
        sb.append("&v=a65833898");
        String string = sb.toString();
        if (this.zzh != null && !this.zzh.trim().equals("")) {
            String strValueOf = String.valueOf(string);
            String str2 = this.zzh;
            StringBuilder sb2 = new StringBuilder(String.valueOf(strValueOf).length() + 4 + String.valueOf(str2).length());
            sb2.append(strValueOf);
            sb2.append("&pv=");
            sb2.append(str2);
            string = sb2.toString();
        }
        int iZze = zzea.zza().zze();
        InputStream inputStreamZza = null;
        if (iZze == 0) {
            throw null;
        }
        if (iZze == 3) {
            string = String.valueOf(string).concat("&gtm_debug=x");
        }
        zzsd zzsdVarZza = zzse.zza();
        try {
            try {
                inputStreamZza = zzsdVarZza.zza(string);
            } catch (zzsf unused) {
                String strValueOf2 = String.valueOf(string);
                zzdh.zzc(strValueOf2.length() != 0 ? "Error when loading resource for url: ".concat(strValueOf2) : new String("Error when loading resource for url: "));
                this.zze.zza(4);
            } catch (FileNotFoundException unused2) {
                String str3 = this.zzc;
                StringBuilder sb3 = new StringBuilder(String.valueOf(string).length() + 79 + String.valueOf(str3).length());
                sb3.append("No data is retrieved from the given url: ");
                sb3.append(string);
                sb3.append(". Make sure container_id: ");
                sb3.append(str3);
                sb3.append(" is correct.");
                zzdh.zzc(sb3.toString());
                this.zze.zza(3);
                zzsdVarZza.zzb();
                return;
            } catch (IOException e) {
                String message = e.getMessage();
                StringBuilder sb4 = new StringBuilder(String.valueOf(string).length() + 40 + String.valueOf(message).length());
                sb4.append("Error when loading resources from url: ");
                sb4.append(string);
                sb4.append(" ");
                sb4.append(message);
                zzdh.zzd(sb4.toString(), e);
                this.zze.zza(2);
                zzsdVarZza.zzb();
                return;
            }
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                zzsa.zzc(inputStreamZza, byteArrayOutputStream);
                com.google.android.gms.internal.gtm.zzai zzaiVarZzg = com.google.android.gms.internal.gtm.zzai.zzg(byteArrayOutputStream.toByteArray(), zzuj.zzb());
                String strValueOf3 = String.valueOf(zzaiVarZzg);
                StringBuilder sb5 = new StringBuilder(String.valueOf(strValueOf3).length() + 43);
                sb5.append("Successfully loaded supplemented resource: ");
                sb5.append(strValueOf3);
                zzdh.zzb.zzd(sb5.toString());
                if (!zzaiVarZzg.zzm() && zzaiVarZzg.zza() == 0) {
                    String strValueOf4 = String.valueOf(this.zzc);
                    zzdh.zzb.zzd(strValueOf4.length() != 0 ? "No change for container: ".concat(strValueOf4) : new String("No change for container: "));
                }
                zzdg<com.google.android.gms.internal.gtm.zzai> zzdgVar = this.zze;
                ((zzag) zzdgVar).zza.zzi.zzd();
                synchronized (((zzag) zzdgVar).zza) {
                    if (zzaiVarZzg.zzm()) {
                        zzal zzalVar = ((zzag) zzdgVar).zza;
                        zzalVar.zzu(zzaiVarZzg, zzalVar.zza.currentTimeMillis(), false);
                        long j = ((zzag) zzdgVar).zza.zzo;
                        StringBuilder sb6 = new StringBuilder(58);
                        sb6.append("setting refresh time to current time: ");
                        sb6.append(j);
                        zzdh.zzb.zzd(sb6.toString());
                        if (!((zzag) zzdgVar).zza.zzv()) {
                            ((zzag) zzdgVar).zza.zzt(zzaiVarZzg);
                        }
                    } else if (((zzag) zzdgVar).zza.zzn.zzm()) {
                        com.google.android.gms.internal.gtm.zzah zzahVarZzZ = zzaiVarZzg.zzZ();
                        zzahVarZzZ.zzc(((zzag) zzdgVar).zza.zzn.zzc());
                        zzaiVarZzg = zzahVarZzZ.zzC();
                        zzal zzalVar2 = ((zzag) zzdgVar).zza;
                        zzalVar2.zzu(zzaiVarZzg, zzalVar2.zza.currentTimeMillis(), false);
                        long j2 = ((zzag) zzdgVar).zza.zzo;
                        StringBuilder sb7 = new StringBuilder(58);
                        sb7.append("setting refresh time to current time: ");
                        sb7.append(j2);
                        zzdh.zzb.zzd(sb7.toString());
                        if (!((zzag) zzdgVar).zza.zzv()) {
                            ((zzag) zzdgVar).zza.zzt(zzaiVarZzg);
                        }
                    } else {
                        zzdh.zza("Current resource is null; network resource is also null");
                        ((zzag) zzdgVar).zza.zzr(((zzag) zzdgVar).zza.zzi.zzb());
                    }
                }
                zzsdVarZza.zzb();
                zzdh.zzb.zzd("Load resource from network finished.");
            } catch (IOException e2) {
                String message2 = e2.getMessage();
                StringBuilder sb8 = new StringBuilder(String.valueOf(string).length() + 51 + String.valueOf(message2).length());
                sb8.append("Error when parsing downloaded resources from url: ");
                sb8.append(string);
                sb8.append(" ");
                sb8.append(message2);
                zzdh.zzd(sb8.toString(), e2);
                this.zze.zza(3);
                zzsdVarZza.zzb();
            }
        } catch (Throwable th) {
            zzsdVarZza.zzb();
            throw th;
        }
    }

    final void zza(String str) {
        if (str == null) {
            this.zzg = this.zzd;
        } else {
            zzdh.zzb.zza(str.length() != 0 ? "Setting CTFE URL path: ".concat(str) : new String("Setting CTFE URL path: "));
            this.zzg = str;
        }
    }

    final void zzb(zzdg<com.google.android.gms.internal.gtm.zzai> zzdgVar) {
        this.zze = zzdgVar;
    }

    final void zzc(String str) {
        String strValueOf = String.valueOf(str);
        zzdh.zzb.zza(strValueOf.length() != 0 ? "Setting previous container version: ".concat(strValueOf) : new String("Setting previous container version: "));
        this.zzh = str;
    }
}
