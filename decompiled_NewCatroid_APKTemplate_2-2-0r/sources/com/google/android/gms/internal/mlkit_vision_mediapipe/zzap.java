package com.google.android.gms.internal.mlkit_vision_mediapipe;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public abstract class zzap {
    private static String zza = "com.google.android.gms.internal.mlkit_vision_mediapipe.zzbd";
    private static String zzb = "com.google.common.flogger.backend.google.GooglePlatform";
    private static String zzc = "com.google.common.flogger.backend.system.DefaultPlatform";
    private static final String[] zzd = {"com.google.android.gms.internal.mlkit_vision_mediapipe.zzbd", "com.google.common.flogger.backend.google.GooglePlatform", "com.google.common.flogger.backend.system.DefaultPlatform"};

    protected abstract zzal zzb(String str);

    protected abstract zzar zzb();

    protected boolean zzb(String str, Level level, boolean z) {
        return false;
    }

    protected abstract String zzh();

    public static zzar zza() {
        return zzao.zza.zzb();
    }

    public static zzal zza(String str) {
        return zzao.zza.zzb(str);
    }

    public static boolean zza(String str, Level level, boolean z) {
        return zzao.zza.zzb(str, level, z);
    }

    public static zzbp zzc() {
        return zzao.zza.zzd();
    }

    protected zzbp zzd() {
        return zzbp.zza();
    }

    public static long zze() {
        return zzao.zza.zzf();
    }

    protected long zzf() {
        return TimeUnit.MILLISECONDS.toNanos(System.currentTimeMillis());
    }

    public static String zzg() {
        return zzao.zza.zzh();
    }
}
