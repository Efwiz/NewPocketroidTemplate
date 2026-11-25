package com.google.android.gms.internal.gtm;

import com.gaurav.avnc.vnc.XKeySym;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/* JADX INFO: compiled from: com.google.android.gms:play-services-analytics-impl@@17.0.1 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzvi {
    static final Charset zza = Charset.forName("UTF-8");
    static final Charset zzb = Charset.forName("ISO-8859-1");
    public static final byte[] zzc;
    public static final ByteBuffer zzd;
    public static final zztj zze;

    static {
        byte[] bArr = new byte[0];
        zzc = bArr;
        zzd = ByteBuffer.wrap(bArr);
        int i = zztj.zzd;
        zztf zztfVar = new zztf(bArr, 0, 0, false, null);
        try {
            zztfVar.zzb(0);
            zze = zztfVar;
        } catch (zzvk e) {
            throw new IllegalArgumentException(e);
        }
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
        return ((zzwk) obj).zzap().zzx((zzwk) obj2).zzD();
    }

    public static String zzh(byte[] bArr) {
        return new String(bArr, zza);
    }

    static boolean zzi(zzwk zzwkVar) {
        if (!(zzwkVar instanceof zzsi)) {
            return false;
        }
        throw null;
    }

    public static boolean zzj(byte[] bArr) {
        return zzyd.zze(bArr);
    }
}
