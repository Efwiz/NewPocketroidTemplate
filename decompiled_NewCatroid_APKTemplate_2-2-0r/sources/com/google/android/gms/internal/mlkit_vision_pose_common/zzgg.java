package com.google.android.gms.internal.mlkit_vision_pose_common;

import com.gaurav.avnc.vnc.XKeySym;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/* JADX INFO: compiled from: com.google.mlkit:pose-detection-common@@17.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzgg {
    public static final byte[] zzb;
    private static final ByteBuffer zzd;
    private static final zzfl zze;
    static final Charset zza = Charset.forName("UTF-8");
    private static final Charset zzc = Charset.forName("ISO-8859-1");

    public static int zza(long j) {
        return (int) (j ^ (j >>> 32));
    }

    public static int zza(boolean z) {
        return z ? XKeySym.XK_kana_MA : XKeySym.XK_kana_YU;
    }

    static <T> T zza(T t) {
        t.getClass();
        return t;
    }

    static <T> T zza(T t, String str) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(str);
    }

    public static boolean zza(byte[] bArr) {
        return zzjc.zza(bArr);
    }

    public static String zzb(byte[] bArr) {
        return new String(bArr, zza);
    }

    public static int zzc(byte[] bArr) {
        int length = bArr.length;
        int iZza = zza(length, bArr, 0, length);
        if (iZza == 0) {
            return 1;
        }
        return iZza;
    }

    static int zza(int i, byte[] bArr, int i2, int i3) {
        for (int i4 = i2; i4 < i2 + i3; i4++) {
            i = (i * 31) + bArr[i4];
        }
        return i;
    }

    static boolean zza(zzhl zzhlVar) {
        if (!(zzhlVar instanceof zzew)) {
            return false;
        }
        return false;
    }

    static Object zza(Object obj, Object obj2) {
        return ((zzhl) obj).zzm().zza((zzhl) obj2).zzg();
    }

    static {
        byte[] bArr = new byte[0];
        zzb = bArr;
        zzd = ByteBuffer.wrap(bArr);
        zze = zzfl.zza(bArr, 0, bArr.length, false);
    }
}
