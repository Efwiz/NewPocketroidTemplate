package com.google.android.gms.internal.mlkit_vision_mediapipe;

import org.apache.commons.lang3.SystemProperties;

/* JADX INFO: compiled from: com.google.mlkit:mediapipe-internal@@16.0.0 */
/* JADX INFO: loaded from: classes9.dex */
public abstract class zzch extends zzcf {
    private static final String zza = zza();

    abstract int zza(zzcc<?> zzccVar, int i, String str, int i2, int i3, int i4) throws zzce;

    private static String zza() {
        try {
            String property = System.getProperty(SystemProperties.LINE_SEPARATOR);
            return property.matches("\\n|\\r(?:\\n)?") ? property : "\n";
        } catch (SecurityException unused) {
            return "\n";
        }
    }

    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzcf
    public final void zza(StringBuilder sb, String str, int i, int i2) {
        int i3 = i;
        while (i < i2) {
            int i4 = i + 1;
            if (str.charAt(i) == '%') {
                if (i4 == i2) {
                    break;
                }
                char cCharAt = str.charAt(i4);
                if (cCharAt == '%') {
                    sb.append((CharSequence) str, i3, i4);
                } else if (cCharAt == 'n') {
                    sb.append((CharSequence) str, i3, i);
                    sb.append(zza);
                }
                i3 = i + 2;
                i = i3;
            }
            i = i4;
        }
        if (i3 < i2) {
            sb.append((CharSequence) str, i3, i2);
        }
    }

    /* JADX WARN: Code duplicated, block: B:44:0x009b  */
    /* JADX WARN: Code duplicated, block: B:47:0x00b8 A[LOOP:2: B:42:0x0095->B:47:0x00b8, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:61:0x00bb A[SYNTHETIC] */
    @Override // com.google.android.gms.internal.mlkit_vision_mediapipe.zzcf
    protected final <T> void zza(zzcc<T> zzccVar) throws zzce {
        int i;
        char cCharAt;
        int i2;
        int i3;
        int i4;
        int i5;
        String strZze = zzccVar.zze();
        int iZza = zza(strZze, 0);
        int i6 = 0;
        int i7 = -1;
        while (iZza >= 0) {
            int i8 = iZza + 1;
            int i9 = i8;
            int i10 = 0;
            while (true) {
                if (i9 < strZze.length()) {
                    i = i9 + 1;
                    cCharAt = strZze.charAt(i9);
                    char c = (char) (cCharAt - '0');
                    if (c < '\n') {
                        i10 = (i10 * 10) + c;
                        if (i10 >= 1000000) {
                            throw zzce.zza("index too large", strZze, iZza, i);
                        }
                        i9 = i;
                    }
                } else {
                    throw zzce.zzb("unterminated parameter", strZze, iZza);
                }
            }
            if (cCharAt != '$') {
                if (cCharAt != '<') {
                    i2 = i6;
                    i6++;
                    i3 = i8;
                } else {
                    if (i7 == -1) {
                        throw zzce.zza("invalid relative parameter", strZze, iZza, i);
                    }
                    if (i == strZze.length()) {
                        throw zzce.zzb("unterminated parameter", strZze, iZza);
                    }
                    i4 = i9 + 2;
                    strZze.charAt(i);
                    i2 = i7;
                }
                i5 = i - 1;
                while (true) {
                    if (i5 < strZze.length()) {
                        if (((char) ((strZze.charAt(i5) & (-33)) - 65)) < 26) {
                            break;
                        } else {
                            i5++;
                        }
                    } else {
                        throw zzce.zzb("unterminated parameter", strZze, iZza);
                    }
                }
                iZza = zza(strZze, zza(zzccVar, i2, strZze, iZza, i3, i5));
                i6 = i6;
                i7 = i2;
            } else {
                if (i9 - i8 == 0) {
                    throw zzce.zza("missing index", strZze, iZza, i);
                }
                if (strZze.charAt(i8) == '0') {
                    throw zzce.zza("index has leading zero", strZze, iZza, i);
                }
                int i11 = i10 - 1;
                if (i == strZze.length()) {
                    throw zzce.zzb("unterminated parameter", strZze, iZza);
                }
                i4 = i9 + 2;
                strZze.charAt(i);
                i2 = i11;
            }
            i3 = i;
            i = i4;
            i5 = i - 1;
            while (true) {
                if (i5 < strZze.length()) {
                    if (((char) ((strZze.charAt(i5) & (-33)) - 65)) < 26) {
                        break;
                    } else {
                        i5++;
                    }
                } else {
                    throw zzce.zzb("unterminated parameter", strZze, iZza);
                }
            }
            iZza = zza(strZze, zza(zzccVar, i2, strZze, iZza, i3, i5));
            i6 = i6;
            i7 = i2;
        }
    }

    private static int zza(String str, int i) throws zzce {
        while (i < str.length()) {
            int i2 = i + 1;
            if (str.charAt(i) != '%') {
                i = i2;
            } else if (i2 < str.length()) {
                char cCharAt = str.charAt(i2);
                if (cCharAt != '%' && cCharAt != 'n') {
                    return i;
                }
                i += 2;
            } else {
                throw zzce.zzb("trailing unquoted '%' character", str, i);
            }
        }
        return -1;
    }
}
