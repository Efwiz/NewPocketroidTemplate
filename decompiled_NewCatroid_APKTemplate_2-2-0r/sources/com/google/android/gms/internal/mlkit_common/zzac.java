package com.google.android.gms.internal.mlkit_common;

import java.util.logging.Logger;
import javax.annotation.CheckForNull;

/* JADX INFO: compiled from: com.google.mlkit:common@@17.4.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzac {
    private static final Logger zza = Logger.getLogger(zzac.class.getName());
    private static final zzab zzb = new zzab(null);

    private zzac() {
    }

    static String zza(@CheckForNull String str) {
        return str == null ? "" : str;
    }

    static boolean zzb(@CheckForNull String str) {
        return str == null || str.isEmpty();
    }
}
