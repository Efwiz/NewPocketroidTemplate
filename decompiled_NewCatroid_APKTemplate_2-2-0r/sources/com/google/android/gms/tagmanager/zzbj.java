package com.google.android.gms.tagmanager;

import android.os.Build;
import java.util.Map;

/* JADX INFO: compiled from: com.google.android.gms:play-services-tagmanager-v4-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
final class zzbj extends zzbu {
    private static final String zza = com.google.android.gms.internal.gtm.zza.DEVICE_NAME.toString();

    public zzbj() {
        super(zza, new String[0]);
    }

    @Override // com.google.android.gms.tagmanager.zzbu
    public final com.google.android.gms.internal.gtm.zzak zza(Map<String, com.google.android.gms.internal.gtm.zzak> map) {
        String str = Build.MANUFACTURER;
        String string = Build.MODEL;
        if (!string.startsWith(str) && !str.equals("unknown")) {
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 1 + String.valueOf(string).length());
            sb.append(str);
            sb.append(" ");
            sb.append(string);
            string = sb.toString();
        }
        return zzfv.zzc(string);
    }

    @Override // com.google.android.gms.tagmanager.zzbu
    public final boolean zzb() {
        return true;
    }
}
