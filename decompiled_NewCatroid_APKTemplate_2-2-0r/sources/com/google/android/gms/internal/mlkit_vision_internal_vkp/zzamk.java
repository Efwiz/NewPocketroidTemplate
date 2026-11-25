package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Locale;
import org.slf4j.Marker;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public abstract class zzamk implements Iterable<Byte>, Serializable {
    private static final Comparator<zzamk> zza;
    public static final zzamk zzb = new zzamh(zzaoc.zzc);
    private static final zzamj zzd;
    private int zzc = 0;

    static {
        int i = zzalr.zza;
        zzd = new zzamj(null);
        zza = new zzamb();
    }

    zzamk() {
    }

    public static zzamk zzr(byte[] bArr) {
        return zzs(bArr, 0, bArr.length);
    }

    public static zzamk zzs(byte[] bArr, int i, int i2) {
        zzo(i, i + i2, bArr.length);
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, i, bArr2, 0, i2);
        return new zzamh(bArr2);
    }

    public static zzamk zzt(String str) {
        return new zzamh(str.getBytes(zzaoc.zza));
    }

    public static zzamk zzu(InputStream inputStream) throws IOException {
        ArrayList arrayList = new ArrayList();
        int iMin = 256;
        while (true) {
            byte[] bArr = new byte[iMin];
            int i = 0;
            while (i < iMin) {
                int i2 = inputStream.read(bArr, i, iMin - i);
                if (i2 == -1) {
                    break;
                }
                i += i2;
            }
            zzamk zzamkVarZzs = i == 0 ? null : zzs(bArr, 0, i);
            if (zzamkVarZzs == null) {
                break;
            }
            arrayList.add(zzamkVarZzs);
            iMin = Math.min(iMin + iMin, 8192);
        }
        int size = arrayList.size();
        return size == 0 ? zzb : zzc(arrayList.iterator(), size);
    }

    static zzamk zzv(byte[] bArr) {
        return new zzamh(bArr);
    }

    static void zzx(int i, int i2) {
        if (((i2 - (i + 1)) | i) < 0) {
            if (i < 0) {
                StringBuilder sb = new StringBuilder(22);
                sb.append("Index < 0: ");
                sb.append(i);
                throw new ArrayIndexOutOfBoundsException(sb.toString());
            }
            StringBuilder sb2 = new StringBuilder(40);
            sb2.append("Index > length: ");
            sb2.append(i);
            sb2.append(", ");
            sb2.append(i2);
            throw new ArrayIndexOutOfBoundsException(sb2.toString());
        }
    }

    public abstract boolean equals(Object obj);

    public final int hashCode() {
        int iZzi = this.zzc;
        if (iZzi == 0) {
            int iZzd = zzd();
            iZzi = zzi(iZzd, 0, iZzd);
            if (iZzi == 0) {
                iZzi = 1;
            }
            this.zzc = iZzi;
        }
        return iZzi;
    }

    public final String toString() {
        return String.format(Locale.ROOT, "<ByteString@%s size=%d contents=\"%s\">", Integer.toHexString(System.identityHashCode(this)), Integer.valueOf(zzd()), zzd() <= 50 ? zzaqo.zza(this) : String.valueOf(zzaqo.zza(zzk(0, 47))).concat("..."));
    }

    public abstract byte zza(int i);

    abstract byte zzb(int i);

    public abstract int zzd();

    protected abstract void zze(byte[] bArr, int i, int i2, int i3);

    protected abstract int zzf();

    protected abstract boolean zzh();

    protected abstract int zzi(int i, int i2, int i3);

    protected abstract int zzj(int i, int i2, int i3);

    public abstract zzamk zzk(int i, int i2);

    protected abstract String zzl(Charset charset);

    abstract void zzm(zzaly zzalyVar) throws IOException;

    public abstract boolean zzn();

    protected final int zzp() {
        return this.zzc;
    }

    @Override // java.lang.Iterable
    /* JADX INFO: renamed from: zzq, reason: merged with bridge method [inline-methods] */
    public zzame iterator() {
        return new zzalz(this);
    }

    public final String zzw(Charset charset) {
        return zzd() == 0 ? "" : zzl(charset);
    }

    @Deprecated
    public final void zzy(byte[] bArr, int i, int i2, int i3) {
        zzo(0, i3, zzd());
        zzo(i2, i2 + i3, bArr.length);
        if (i3 > 0) {
            zze(bArr, 0, i2, i3);
        }
    }

    private static zzamk zzc(Iterator<zzamk> it, int i) {
        if (i <= 0) {
            throw new IllegalArgumentException(String.format("length (%s) must be >= 1", Integer.valueOf(i)));
        }
        if (i == 1) {
            return it.next();
        }
        int i2 = i >>> 1;
        zzamk zzamkVarZzc = zzc(it, i2);
        zzamk zzamkVarZzc2 = zzc(it, i - i2);
        if (Integer.MAX_VALUE - zzamkVarZzc.zzd() >= zzamkVarZzc2.zzd()) {
            return zzapy.zzA(zzamkVarZzc, zzamkVarZzc2);
        }
        int iZzd = zzamkVarZzc.zzd();
        int iZzd2 = zzamkVarZzc2.zzd();
        StringBuilder sb = new StringBuilder(53);
        sb.append("ByteString would be too long: ");
        sb.append(iZzd);
        sb.append(Marker.ANY_NON_NULL_MARKER);
        sb.append(iZzd2);
        throw new IllegalArgumentException(sb.toString());
    }

    static int zzo(int i, int i2, int i3) {
        int i4 = i2 - i;
        if ((i | i2 | i4 | (i3 - i2)) >= 0) {
            return i4;
        }
        if (i < 0) {
            StringBuilder sb = new StringBuilder(32);
            sb.append("Beginning index: ");
            sb.append(i);
            sb.append(" < 0");
            throw new IndexOutOfBoundsException(sb.toString());
        }
        if (i2 < i) {
            StringBuilder sb2 = new StringBuilder(66);
            sb2.append("Beginning index larger than ending index: ");
            sb2.append(i);
            sb2.append(", ");
            sb2.append(i2);
            throw new IndexOutOfBoundsException(sb2.toString());
        }
        StringBuilder sb3 = new StringBuilder(37);
        sb3.append("End index: ");
        sb3.append(i2);
        sb3.append(" >= ");
        sb3.append(i3);
        throw new IndexOutOfBoundsException(sb3.toString());
    }
}
