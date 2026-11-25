package com.google.android.gms.internal.mlkit_vision_mediapipe;

import java.lang.reflect.InvocationTargetException;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzao {
    private static final zzap zza = zza(zzap.zzd);

    private static zzap zza(String[] strArr) {
        zzap zzapVarZza;
        try {
            zzapVarZza = zzaq.zza();
        } catch (NoClassDefFoundError unused) {
            zzapVarZza = null;
        }
        if (zzapVarZza != null) {
            return zzapVarZza;
        }
        StringBuilder sb = new StringBuilder();
        for (String str : strArr) {
            try {
                return (zzap) Class.forName(str).getConstructor(new Class[0]).newInstance(new Object[0]);
            } catch (Throwable th) {
                th = th;
                if (th instanceof InvocationTargetException) {
                    th = th.getCause();
                }
                sb.append('\n').append(str).append(": ").append(th);
            }
        }
        throw new IllegalStateException(sb.insert(0, "No logging platforms found:").toString());
    }
}
