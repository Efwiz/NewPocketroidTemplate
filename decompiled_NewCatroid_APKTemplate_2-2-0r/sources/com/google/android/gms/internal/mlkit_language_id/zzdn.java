package com.google.android.gms.internal.mlkit_language_id;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Locale;

/* JADX INFO: compiled from: com.google.mlkit:language-id@@16.1.1 */
/* JADX INFO: loaded from: classes9.dex */
public abstract class zzdn implements Serializable, Iterable<Byte> {
    public static final zzdn zza = new zzdx(zzeq.zzb);
    private static final zzdt zzb;
    private static final Comparator<zzdn> zzd;
    private int zzc = 0;

    zzdn() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int zzb(byte b) {
        return b & 255;
    }

    public abstract boolean equals(Object obj);

    public abstract byte zza(int i);

    public abstract int zza();

    protected abstract int zza(int i, int i2, int i3);

    public abstract zzdn zza(int i, int i2);

    protected abstract String zza(Charset charset);

    abstract void zza(zzdk zzdkVar) throws IOException;

    abstract byte zzb(int i);

    public abstract boolean zzc();

    public static zzdn zza(String str) {
        return new zzdx(str.getBytes(zzeq.zza));
    }

    public final String zzb() {
        return zza() == 0 ? "" : zza(zzeq.zza);
    }

    public final int hashCode() {
        int iZza = this.zzc;
        if (iZza == 0) {
            int iZza2 = zza();
            iZza = zza(iZza2, 0, iZza2);
            if (iZza == 0) {
                iZza = 1;
            }
            this.zzc = iZza;
        }
        return iZza;
    }

    static zzdv zzc(int i) {
        return new zzdv(i, null);
    }

    protected final int zzd() {
        return this.zzc;
    }

    static int zzb(int i, int i2, int i3) {
        int i4 = i2 - i;
        if ((i | i2 | i4 | (i3 - i2)) >= 0) {
            return i4;
        }
        if (i < 0) {
            throw new IndexOutOfBoundsException(new StringBuilder(32).append("Beginning index: ").append(i).append(" < 0").toString());
        }
        if (i2 < i) {
            throw new IndexOutOfBoundsException(new StringBuilder(66).append("Beginning index larger than ending index: ").append(i).append(", ").append(i2).toString());
        }
        throw new IndexOutOfBoundsException(new StringBuilder(37).append("End index: ").append(i2).append(" >= ").append(i3).toString());
    }

    public final String toString() {
        return String.format(Locale.ROOT, "<ByteString@%s size=%d contents=\"%s\">", Integer.toHexString(System.identityHashCode(this)), Integer.valueOf(zza()), zza() <= 50 ? zzhd.zza(this) : String.valueOf(zzhd.zza(zza(0, 47))).concat("..."));
    }

    @Override // java.lang.Iterable
    public /* synthetic */ Iterator<Byte> iterator() {
        return new zzdm(this);
    }

    /* JADX WARN: Multi-variable type inference failed */
    static {
        zzb = zzdl.zza() ? new zzdw(null) : new zzdr(null);
        zzd = new zzdp();
    }
}
