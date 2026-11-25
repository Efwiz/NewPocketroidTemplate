package com.google.android.gms.internal.mlkit_vision_mediapipe;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzab {
    private static final long zza;
    private static final zzab zzb;
    private final int zzc;
    private final int zzd;
    private final int zze;

    private static int zza(char c) {
        return ((int) ((zza >>> ((c - ' ') * 3)) & 7)) - 1;
    }

    public static zzab zza() {
        return zzb;
    }

    public static zzab zza(String str, int i, int i2, boolean z) throws zzce {
        if (i == i2 && !z) {
            return zzb;
        }
        int i3 = z ? 128 : 0;
        while (i != i2) {
            int i4 = i + 1;
            char cCharAt = str.charAt(i);
            if (cCharAt < ' ' || cCharAt > '0') {
                if (cCharAt > '9') {
                    throw zzce.zza("invalid flag", str, i);
                }
                int i5 = cCharAt - ExternalAnnotationProvider.NULLABLE;
                while (i4 != i2) {
                    int i6 = i4 + 1;
                    char cCharAt2 = str.charAt(i4);
                    if (cCharAt2 == '.') {
                        return new zzab(i3, i5, zza(str, i6, i2));
                    }
                    char c = (char) (cCharAt2 - '0');
                    if (c >= '\n') {
                        throw zzce.zza("invalid width character", str, i4);
                    }
                    i5 = (i5 * 10) + c;
                    if (i5 > 999999) {
                        throw zzce.zza("width too large", str, i, i2);
                    }
                    i4 = i6;
                }
                return new zzab(i3, i5, -1);
            }
            int iZza = zza(cCharAt);
            if (iZza < 0) {
                if (cCharAt == '.') {
                    return new zzab(i3, -1, zza(str, i4, i2));
                }
                throw zzce.zza("invalid flag", str, i);
            }
            int i7 = 1 << iZza;
            if ((i3 & i7) != 0) {
                throw zzce.zza("repeated flag", str, i);
            }
            i3 |= i7;
            i = i4;
        }
        return new zzab(i3, -1, -1);
    }

    private static int zza(String str, int i, int i2) throws zzce {
        if (i == i2) {
            throw zzce.zza("missing precision", str, i - 1);
        }
        int i3 = 0;
        for (int i4 = i; i4 < i2; i4++) {
            char cCharAt = (char) (str.charAt(i4) - '0');
            if (cCharAt >= '\n') {
                throw zzce.zza("invalid precision character", str, i4);
            }
            i3 = (i3 * 10) + cCharAt;
            if (i3 > 999999) {
                throw zzce.zza("precision too large", str, i, i2);
            }
        }
        if (i3 != 0 || i2 == i + 1) {
            return i3;
        }
        throw zzce.zza("invalid precision", str, i, i2);
    }

    static int zza(String str, boolean z) {
        int i = z ? 128 : 0;
        for (int i2 = 0; i2 < str.length(); i2++) {
            int iZza = zza(str.charAt(i2));
            if (iZza < 0) {
                String strValueOf = String.valueOf(str);
                throw new IllegalArgumentException(strValueOf.length() != 0 ? "invalid flags: ".concat(strValueOf) : new String("invalid flags: "));
            }
            i |= 1 << iZza;
        }
        return i;
    }

    private zzab(int i, int i2, int i3) {
        this.zzc = i;
        this.zzd = i2;
        this.zze = i3;
    }

    public final zzab zza(int i, boolean z, boolean z2) {
        if (zzb()) {
            return this;
        }
        int i2 = this.zzc;
        int i3 = i2 & 128;
        if (i3 == 0) {
            return zzb;
        }
        return (i3 == i2 && -1 == this.zzd && -1 == this.zze) ? this : new zzab(i3, -1, -1);
    }

    public final boolean zzb() {
        return this == zzb;
    }

    public final int zzc() {
        return this.zzd;
    }

    public final int zzd() {
        return this.zze;
    }

    public final boolean zza(int i, boolean z) {
        int i2;
        if (zzb()) {
            return true;
        }
        int i3 = this.zzc;
        if (((~i) & i3) != 0) {
            return false;
        }
        if (!z && this.zze != -1) {
            return false;
        }
        boolean z2 = this.zzd != -1;
        if ((i3 & 9) == 9 || (i2 = i3 & 96) == 96) {
            return false;
        }
        return i2 == 0 || z2;
    }

    public final boolean zza(zzy zzyVar) {
        return zza(zzyVar.zzc(), zzyVar.zzb().zza());
    }

    public final int zze() {
        return this.zzc;
    }

    public final boolean zzf() {
        return (this.zzc & 128) != 0;
    }

    public final StringBuilder zza(StringBuilder sb) {
        if (!zzb()) {
            int i = this.zzc & (-129);
            int i2 = 0;
            while (true) {
                int i3 = 1 << i2;
                if (i3 > i) {
                    break;
                }
                if ((i3 & i) != 0) {
                    sb.append(" #(+,-0".charAt(i2));
                }
                i2++;
            }
            int i4 = this.zzd;
            if (i4 != -1) {
                sb.append(i4);
            }
            if (this.zze != -1) {
                sb.append('.').append(this.zze);
            }
        }
        return sb;
    }

    public final boolean equals(@NullableDecl Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof zzab) {
            zzab zzabVar = (zzab) obj;
            if (zzabVar.zzc == this.zzc && zzabVar.zzd == this.zzd && zzabVar.zze == this.zze) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return (((this.zzc * 31) + this.zzd) * 31) + this.zze;
    }

    static {
        long jCharAt = 0;
        for (int i = 0; i < 7; i++) {
            jCharAt |= (((long) i) + 1) << ((int) (((long) (" #(+,-0".charAt(i) - ' ')) * 3));
        }
        zza = jCharAt;
        zzb = new zzab(0, -1, -1);
    }
}
