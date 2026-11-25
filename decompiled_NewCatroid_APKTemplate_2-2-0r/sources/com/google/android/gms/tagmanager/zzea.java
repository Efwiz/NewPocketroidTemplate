package com.google.android.gms.tagmanager;

import android.net.Uri;
import com.huawei.hms.framework.common.ContainerUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzea {
    private static zzea zza;
    private volatile int zze = 1;
    private volatile String zzc = null;
    private volatile String zzb = null;
    private volatile String zzd = null;

    zzea() {
    }

    static zzea zza() {
        zzea zzeaVar;
        synchronized (zzea.class) {
            if (zza == null) {
                zza = new zzea();
            }
            zzeaVar = zza;
        }
        return zzeaVar;
    }

    private static final String zzf(String str) {
        return str.split(ContainerUtils.FIELD_DELIMITER)[0].split(ContainerUtils.KEY_VALUE_DELIMITER)[1];
    }

    final String zzb() {
        return this.zzc;
    }

    final String zzc() {
        return this.zzb;
    }

    final synchronized boolean zzd(Uri uri) {
        try {
            String strDecode = URLDecoder.decode(uri.toString(), "UTF-8");
            if (!strDecode.matches("^tagmanager.c.\\S+:\\/\\/preview\\/p\\?id=\\S+&gtm_auth=\\S+&gtm_preview=\\d+(&gtm_debug=x)?$")) {
                if (!strDecode.matches("^tagmanager.c.\\S+:\\/\\/preview\\/p\\?id=\\S+&gtm_preview=$")) {
                    String strValueOf = String.valueOf(strDecode);
                    zzdh.zzc(strValueOf.length() != 0 ? "Invalid preview uri: ".concat(strValueOf) : new String("Invalid preview uri: "));
                    return false;
                }
                if (!zzf(uri.getQuery()).equals(this.zzb)) {
                    return false;
                }
                String strValueOf2 = String.valueOf(this.zzb);
                zzdh.zzb.zzd(strValueOf2.length() != 0 ? "Exit preview mode for container: ".concat(strValueOf2) : new String("Exit preview mode for container: "));
                this.zze = 1;
                this.zzc = null;
                return true;
            }
            String strValueOf3 = String.valueOf(strDecode);
            zzdh.zzb.zzd(strValueOf3.length() != 0 ? "Container preview url: ".concat(strValueOf3) : new String("Container preview url: "));
            if (strDecode.matches(".*?&gtm_debug=x$")) {
                this.zze = 3;
            } else {
                this.zze = 2;
            }
            this.zzd = uri.getQuery().replace("&gtm_debug=x", "");
            if (this.zze == 2 || this.zze == 3) {
                String strValueOf4 = String.valueOf(this.zzd);
                this.zzc = strValueOf4.length() != 0 ? "/r?".concat(strValueOf4) : new String("/r?");
            }
            this.zzb = zzf(this.zzd);
            return true;
        } catch (UnsupportedEncodingException unused) {
            return false;
        }
    }

    final int zze() {
        return this.zze;
    }
}
