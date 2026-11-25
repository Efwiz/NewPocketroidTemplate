package com.google.android.gms.internal.mlkit_vision_pose_common;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
final class zzal {
    static int zza(int i, int i2, int i3) {
        return (i & (~i3)) | (i2 & i3);
    }

    static int zzb(int i) {
        return (i < 32 ? 4 : 2) * (i + 1);
    }

    static Object zza(int i) {
        if (i < 2 || i > 1073741824 || Integer.highestOneBit(i) != i) {
            throw new IllegalArgumentException(new StringBuilder(52).append("must be power of 2 between 2^1 and 2^30: ").append(i).toString());
        }
        if (i <= 256) {
            return new byte[i];
        }
        return i <= 65536 ? new short[i] : new int[i];
    }

    static int zza(Object obj, int i) {
        if (obj instanceof byte[]) {
            return ((byte[]) obj)[i] & 255;
        }
        if (obj instanceof short[]) {
            return ((short[]) obj)[i] & 65535;
        }
        return ((int[]) obj)[i];
    }

    static void zza(Object obj, int i, int i2) {
        if (obj instanceof byte[]) {
            ((byte[]) obj)[i] = (byte) i2;
        } else if (obj instanceof short[]) {
            ((short[]) obj)[i] = (short) i2;
        } else {
            ((int[]) obj)[i] = i2;
        }
    }

    static int zza(@NullableDecl Object obj, @NullableDecl Object obj2, int i, Object obj3, int[] iArr, Object[] objArr, @NullableDecl Object[] objArr2) {
        int iZza = zzak.zza(obj);
        int i2 = iZza & i;
        int iZza2 = zza(obj3, i2);
        if (iZza2 == 0) {
            return -1;
        }
        int i3 = ~i;
        int i4 = iZza & i3;
        int i5 = -1;
        while (true) {
            int i6 = iZza2 - 1;
            int i7 = iArr[i6];
            if ((i7 & i3) == i4 && zze.zza(obj, objArr[i6]) && (objArr2 == null || zze.zza(obj2, objArr2[i6]))) {
                int i8 = i7 & i;
                if (i5 == -1) {
                    zza(obj3, i2, i8);
                } else {
                    iArr[i5] = zza(iArr[i5], i8, i);
                }
                return i6;
            }
            int i9 = i7 & i;
            if (i9 == 0) {
                return -1;
            }
            i5 = i6;
            iZza2 = i9;
        }
    }
}
