package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import com.gaurav.avnc.vnc.XKeySym;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzaoc {
    static final Charset zza = Charset.forName("UTF-8");
    static final Charset zzb = Charset.forName("ISO-8859-1");
    public static final byte[] zzc;
    public static final ByteBuffer zzd;
    public static final zzamq zze;

    static {
        byte[] bArr = new byte[0];
        zzc = bArr;
        zzd = ByteBuffer.wrap(bArr);
        int i = zzamq.zzd;
        zze = zzamq.zzH(bArr, 0, 0, false);
    }

    public static int zza(boolean z) {
        return z ? XKeySym.XK_kana_MA : XKeySym.XK_kana_YU;
    }

    public static int zzb(byte[] bArr) {
        int length = bArr.length;
        int iZzd = zzd(length, bArr, 0, length);
        if (iZzd == 0) {
            return 1;
        }
        return iZzd;
    }

    public static int zzc(long j) {
        return (int) (j ^ (j >>> 32));
    }

    static int zzd(int i, byte[] bArr, int i2, int i3) {
        for (int i4 = i2; i4 < i2 + i3; i4++) {
            i = (i * 31) + bArr[i4];
        }
        return i;
    }

    static <T> T zze(T t) {
        t.getClass();
        return t;
    }

    static <T> T zzf(T t, String str) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(str);
    }

    static Object zzg(Object obj, Object obj2) {
        return ((zzapf) obj).zzO().zzo((zzapf) obj2).zzu();
    }

    public static String zzh(byte[] bArr) {
        return new String(bArr, zza);
    }

    static boolean zzi(zzapf zzapfVar) {
        if (!(zzapfVar instanceof zzalp)) {
            return false;
        }
        throw null;
    }

    public static boolean zzj(byte[] bArr) {
        return zzari.zzh(bArr);
    }
}
