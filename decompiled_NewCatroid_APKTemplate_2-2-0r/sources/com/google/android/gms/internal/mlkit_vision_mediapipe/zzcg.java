package com.google.android.gms.internal.mlkit_vision_mediapipe;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzcg {
    private static final zzci zza = zzci.zza();

    @NullableDecl
    public static StackTraceElement zza(Class<?> cls, Throwable th, int i) {
        zzcj.zza(cls, "target");
        zzcj.zza(th, "throwable");
        StackTraceElement[] stackTrace = zza != null ? null : th.getStackTrace();
        boolean z = false;
        int i2 = 1;
        while (true) {
            try {
                zzci zzciVar = zza;
                StackTraceElement stackTraceElementZza = zzciVar != null ? zzciVar.zza(th, i2) : stackTrace[i2];
                if (cls.getName().equals(stackTraceElementZza.getClassName())) {
                    z = true;
                } else if (z) {
                    return stackTraceElementZza;
                }
                i2++;
            } catch (Exception unused) {
                return null;
            }
        }
    }

    public static StackTraceElement[] zza(Class<?> cls, Throwable th, int i, int i2) {
        StackTraceElement[] stackTrace;
        int length;
        StackTraceElement stackTraceElementZza;
        zzcj.zza(cls, "target");
        zzcj.zza(th, "throwable");
        if (i <= 0 && i != -1) {
            throw new IllegalArgumentException(new StringBuilder(34).append("invalid maximum depth: ").append(i).toString());
        }
        zzci zzciVar = zza;
        if (zzciVar != null) {
            length = zzciVar.zza(th);
            stackTrace = null;
        } else {
            stackTrace = th.getStackTrace();
            length = stackTrace.length;
        }
        boolean z = false;
        for (int i3 = 1; i3 < length; i3++) {
            zzci zzciVar2 = zza;
            StackTraceElement stackTraceElementZza2 = zzciVar2 != null ? zzciVar2.zza(th, i3) : stackTrace[i3];
            if (cls.getName().equals(stackTraceElementZza2.getClassName())) {
                z = true;
            } else if (z) {
                int i4 = length - i3;
                if (i <= 0 || i >= i4) {
                    i = i4;
                }
                StackTraceElement[] stackTraceElementArr = new StackTraceElement[i];
                stackTraceElementArr[0] = stackTraceElementZza2;
                for (int i5 = 1; i5 < i; i5++) {
                    zzci zzciVar3 = zza;
                    if (zzciVar3 != null) {
                        stackTraceElementZza = zzciVar3.zza(th, i3 + i5);
                    } else {
                        stackTraceElementZza = stackTrace[i3 + i5];
                    }
                    stackTraceElementArr[i5] = stackTraceElementZza;
                }
                return stackTraceElementArr;
            }
        }
        return new StackTraceElement[0];
    }
}
