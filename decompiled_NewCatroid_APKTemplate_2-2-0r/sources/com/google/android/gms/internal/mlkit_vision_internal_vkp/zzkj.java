package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import java.util.logging.Logger;
import javax.annotation.CheckForNull;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzkj {
    private static final Logger zza = Logger.getLogger(zzkj.class.getName());
    private static final zzki zzb = new zzki(null);

    private zzkj() {
    }

    static String zza(@CheckForNull String str) {
        return str == null ? "" : str;
    }

    static boolean zzb(@CheckForNull String str) {
        return str == null || str.isEmpty();
    }
}
