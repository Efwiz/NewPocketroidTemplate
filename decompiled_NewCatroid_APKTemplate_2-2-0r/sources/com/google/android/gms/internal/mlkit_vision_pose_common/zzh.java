package com.google.android.gms.internal.mlkit_vision_pose_common;

import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzh {
    public static void zza(boolean z, @NullableDecl Object obj) {
        if (!z) {
            throw new IllegalStateException(String.valueOf(obj));
        }
    }

    @NonNullDecl
    public static <T> T zza(@NonNullDecl T t) {
        t.getClass();
        return t;
    }

    public static int zza(int i, int i2) {
        String strZza;
        if (i >= 0 && i < i2) {
            return i;
        }
        if (i < 0) {
            strZza = zzg.zza("%s (%s) must not be negative", "index", Integer.valueOf(i));
        } else if (i2 >= 0) {
            strZza = zzg.zza("%s (%s) must be less than size (%s)", "index", Integer.valueOf(i), Integer.valueOf(i2));
        } else {
            throw new IllegalArgumentException(new StringBuilder(26).append("negative size: ").append(i2).toString());
        }
        throw new IndexOutOfBoundsException(strZza);
    }

    public static int zzb(int i, int i2) {
        if (i < 0 || i > i2) {
            throw new IndexOutOfBoundsException(zza(i, i2, "index"));
        }
        return i;
    }

    private static String zza(int i, int i2, @NullableDecl String str) {
        if (i < 0) {
            return zzg.zza("%s (%s) must not be negative", str, Integer.valueOf(i));
        }
        if (i2 < 0) {
            throw new IllegalArgumentException(new StringBuilder(26).append("negative size: ").append(i2).toString());
        }
        return zzg.zza("%s (%s) must not be greater than size (%s)", str, Integer.valueOf(i), Integer.valueOf(i2));
    }

    public static void zza(int i, int i2, int i3) {
        String strZza;
        if (i < 0 || i2 < i || i2 > i3) {
            if (i < 0 || i > i3) {
                strZza = zza(i, i3, "start index");
            } else if (i2 < 0 || i2 > i3) {
                strZza = zza(i2, i3, "end index");
            } else {
                strZza = zzg.zza("end index (%s) must not be less than start index (%s)", Integer.valueOf(i2), Integer.valueOf(i));
            }
            throw new IndexOutOfBoundsException(strZza);
        }
    }
}
