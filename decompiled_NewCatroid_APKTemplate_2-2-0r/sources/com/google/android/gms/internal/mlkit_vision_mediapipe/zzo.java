package com.google.android.gms.internal.mlkit_vision_mediapipe;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public abstract class zzo implements zzp {
    public static final zzo zza = new zzn();

    public abstract String zza();

    public abstract String zzb();

    public abstract int zzc();

    public abstract String zzd();

    public final String toString() {
        StringBuilder sbAppend = new StringBuilder("LogSite{ class=").append(zza()).append(", method=").append(zzb()).append(", line=").append(zzc());
        if (zzd() != null) {
            sbAppend.append(", file=").append(zzd());
        }
        return sbAppend.append(" }").toString();
    }

    @Deprecated
    public static zzo zza(String str, String str2, int i, @NullableDecl String str3) {
        return new zzq(str, str2, i, str3, null);
    }
}
