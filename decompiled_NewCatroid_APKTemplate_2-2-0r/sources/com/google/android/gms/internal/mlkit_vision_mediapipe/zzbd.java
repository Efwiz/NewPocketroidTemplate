package com.google.android.gms.internal.mlkit_vision_mediapipe;

import android.os.Build;
import dalvik.system.VMStack;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzbd extends zzap {
    private static final boolean zza = zza.zza();
    private static final boolean zzb;
    private static final zzar zzc;

    /* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
    static final class zza {
        zza() {
        }

        static boolean zza() {
            return zzbd.zzj();
        }
    }

    static boolean zzj() {
        try {
            Class.forName("dalvik.system.VMStack").getMethod("getStackClass2", new Class[0]);
            return zza.class.getName().equals(zzk());
        } catch (Throwable unused) {
            return false;
        }
    }

    static String zzk() {
        try {
            return VMStack.getStackClass2().getName();
        } catch (Throwable unused) {
            return null;
        }
    }

    static Class<?> zzl() {
        return VMStack.getStackClass2();
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzap
    protected final zzar zzb() {
        return zzc;
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzap
    protected final zzal zzb(String str) {
        return zzbi.zza(str);
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzap
    protected final String zzh() {
        return "platform: Android";
    }

    static {
        zzb = Build.FINGERPRINT == null || "robolectric".equals(Build.FINGERPRINT);
        zzc = new zzar() { // from class: com.google.android.gms.internal.mlkit_vision_mediapipe.zzbd.1
            @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzar
            public String zza(Class<? extends zzd<?>> cls) {
                StackTraceElement stackTraceElementZza;
                if (zzbd.zza) {
                    try {
                        Class<?> clsZzl = zzbd.zzl();
                        if (cls.equals(clsZzl)) {
                            return VMStack.getStackClass2().getName();
                        }
                        String strValueOf = String.valueOf(cls);
                        String strValueOf2 = String.valueOf(clsZzl);
                        throw new IllegalStateException(new StringBuilder(String.valueOf(strValueOf).length() + 43 + String.valueOf(strValueOf2).length()).append("Unexpected stack depth, expected: ").append(strValueOf).append(" but was ").append(strValueOf2).toString());
                    } catch (Throwable unused) {
                    }
                }
                if (!zzbd.zzb || (stackTraceElementZza = zzcg.zza(cls, new Throwable(), 1)) == null) {
                    return null;
                }
                return stackTraceElementZza.getClassName();
            }

            @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzar
            public zzo zza(Class<?> cls, int i) {
                return zzo.zza;
            }
        };
    }
}
