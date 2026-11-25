package com.google.android.gms.internal.mlkit_vision_object_detection_bundled;

import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;

/* JADX INFO: compiled from: com.google.mlkit:object-detection@@16.2.7 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzeo implements Closeable {
    private final Reader zzb;
    private long zzi;
    private int zzj;
    private int[] zzk;
    private String[] zzm;
    private int[] zzn;
    private boolean zzc = false;
    private final char[] zzd = new char[1024];
    private int zze = 0;
    private int zzf = 0;
    private int zzg = 0;
    private int zzh = 0;
    int zza = 0;
    private int zzl = 1;

    static {
        zzcu.zza = new zzen();
    }

    public zzeo(Reader reader) {
        int[] iArr = new int[32];
        this.zzk = iArr;
        iArr[0] = 6;
        this.zzm = new String[32];
        this.zzn = new int[32];
        this.zzb = reader;
    }

    private final int zzo(boolean z) throws IOException {
        char[] cArr = this.zzd;
        int i = this.zze;
        int i2 = this.zzf;
        while (true) {
            if (i == i2) {
                this.zze = i;
                if (!zzv(1)) {
                    if (!z) {
                        return -1;
                    }
                    String strValueOf = String.valueOf(zzb());
                    throw new EOFException(strValueOf.length() != 0 ? "End of input".concat(strValueOf) : new String("End of input"));
                }
                i = this.zze;
                i2 = this.zzf;
            }
            int i3 = i + 1;
            char c = cArr[i];
            if (c == '\n') {
                this.zzg++;
                this.zzh = i3;
            } else if (c != ' ' && c != '\r' && c != '\t') {
                if (c == '/') {
                    this.zze = i3;
                    if (i3 == i2) {
                        this.zze = i;
                        boolean zZzv = zzv(2);
                        this.zze++;
                        if (!zZzv) {
                            return 47;
                        }
                    }
                    zzs();
                    int i4 = this.zze;
                    char c2 = cArr[i4];
                    if (c2 == '*') {
                        this.zze = i4 + 1;
                        while (true) {
                            if (this.zze + 2 > this.zzf && !zzv(2)) {
                                throw zzp("Unterminated comment");
                            }
                            char[] cArr2 = this.zzd;
                            int i5 = this.zze;
                            if (cArr2[i5] != '\n') {
                                int i6 = 0;
                                while (true) {
                                    if (i6 >= 2) {
                                        i = this.zze + 2;
                                        i2 = this.zzf;
                                        break;
                                    }
                                    if (this.zzd[this.zze + i6] != "*/".charAt(i6)) {
                                        break;
                                    }
                                    i6++;
                                }
                            } else {
                                this.zzg++;
                                this.zzh = i5 + 1;
                            }
                            this.zze++;
                        }
                    } else {
                        if (c2 != '/') {
                            return 47;
                        }
                        this.zze = i4 + 1;
                        zzu();
                        i = this.zze;
                        i2 = this.zzf;
                    }
                } else {
                    if (c != '#') {
                        this.zze = i3;
                        return c;
                    }
                    this.zze = i3;
                    zzs();
                    zzu();
                    i = this.zze;
                    i2 = this.zzf;
                }
            }
            i = i3;
        }
    }

    private final IOException zzp(String str) throws IOException {
        String strValueOf = String.valueOf(zzb());
        throw new zzer(strValueOf.length() != 0 ? str.concat(strValueOf) : new String(str));
    }

    private final String zzq(char c) throws IOException {
        int i;
        int i2;
        char[] cArr = this.zzd;
        StringBuilder sb = null;
        do {
            int i3 = this.zze;
            int i4 = this.zzf;
            while (true) {
                int i5 = i4;
                i = i3;
                while (true) {
                    if (i3 < i5) {
                        int i6 = i3 + 1;
                        char c2 = cArr[i3];
                        if (c2 == c) {
                            this.zze = i6;
                            int i7 = (i6 - i) - 1;
                            if (sb == null) {
                                return new String(cArr, i, i7);
                            }
                            sb.append(cArr, i, i7);
                            return sb.toString();
                        }
                        char c3 = '\n';
                        if (c2 == '\\') {
                            this.zze = i6;
                            int i8 = i6 - i;
                            int i9 = i8 - 1;
                            if (sb == null) {
                                sb = new StringBuilder(Math.max(i8 + i8, 16));
                            }
                            sb.append(cArr, i, i9);
                            if (this.zze == this.zzf && !zzv(1)) {
                                throw zzp("Unterminated escape sequence");
                            }
                            char[] cArr2 = this.zzd;
                            int i10 = this.zze;
                            int i11 = i10 + 1;
                            this.zze = i11;
                            char c4 = cArr2[i10];
                            if (c4 != '\n') {
                                if (c4 != '\"' && c4 != '\'' && c4 != '/' && c4 != '\\') {
                                    if (c4 == 'b') {
                                        c3 = '\b';
                                    } else if (c4 == 'f') {
                                        c3 = '\f';
                                    } else if (c4 == 'n') {
                                        continue;
                                    } else if (c4 == 'r') {
                                        c3 = '\r';
                                    } else if (c4 == 't') {
                                        c3 = '\t';
                                    } else {
                                        if (c4 != 'u') {
                                            throw zzp("Invalid escape sequence");
                                        }
                                        if (i10 + 5 > this.zzf && !zzv(4)) {
                                            throw zzp("Unterminated escape sequence");
                                        }
                                        int i12 = this.zze;
                                        int i13 = i12 + 4;
                                        c3 = 0;
                                        while (i12 < i13) {
                                            char[] cArr3 = this.zzd;
                                            char c5 = cArr3[i12];
                                            char c6 = (char) (c3 << 4);
                                            if (c5 >= '0' && c5 <= '9') {
                                                i2 = c5 - '0';
                                            } else if (c5 >= 'a' && c5 <= 'f') {
                                                i2 = c5 - 'W';
                                            } else {
                                                if (c5 < 'A' || c5 > 'F') {
                                                    String str = new String(cArr3, this.zze, 4);
                                                    throw new NumberFormatException(str.length() != 0 ? "\\u".concat(str) : new String("\\u"));
                                                }
                                                i2 = c5 - '7';
                                            }
                                            c3 = (char) (c6 + i2);
                                            i12++;
                                        }
                                        this.zze += 4;
                                    }
                                }
                                sb.append(c3);
                                i3 = this.zze;
                                i4 = this.zzf;
                            } else {
                                this.zzg++;
                                this.zzh = i11;
                            }
                            c3 = c4;
                            sb.append(c3);
                            i3 = this.zze;
                            i4 = this.zzf;
                        } else {
                            if (c2 == '\n') {
                                this.zzg++;
                                this.zzh = i6;
                            }
                            i3 = i6;
                        }
                    }
                }
            }
            if (sb == null) {
                int i14 = i3 - i;
                sb = new StringBuilder(Math.max(i14 + i14, 16));
            }
            sb.append(cArr, i, i3 - i);
            this.zze = i3;
        } while (zzv(1));
        throw zzp("Unterminated string");
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:32:0x0042. Please report as an issue. */
    private final String zzr() throws IOException {
        String string;
        StringBuilder sb = null;
        int i = 0;
        while (true) {
            int i2 = 0;
            while (true) {
                int i3 = this.zze + i2;
                if (i3 < this.zzf) {
                    char c = this.zzd[i3];
                    if (c != '\t' && c != '\n' && c != '\f' && c != '\r' && c != ' ') {
                        if (c != '#') {
                            if (c != ',') {
                                if (c != '/' && c != '=') {
                                    if (c != '{' && c != '}' && c != ':') {
                                        if (c != ';') {
                                            switch (c) {
                                                case '[':
                                                case ']':
                                                    break;
                                                case '\\':
                                                    break;
                                                default:
                                                    i2++;
                                                    break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        zzs();
                    }
                    i = i2;
                } else if (i2 >= 1024) {
                    if (sb == null) {
                        sb = new StringBuilder(Math.max(i2, 16));
                    }
                    sb.append(this.zzd, this.zze, i2);
                    this.zze += i2;
                    if (!zzv(1)) {
                    }
                } else if (!zzv(i2 + 1)) {
                    i = i2;
                }
                if (sb == null) {
                    string = new String(this.zzd, this.zze, i);
                } else {
                    sb.append(this.zzd, this.zze, i);
                    string = sb.toString();
                }
                this.zze += i;
                return string;
            }
        }
    }

    private final void zzs() throws IOException {
        if (!this.zzc) {
            throw zzp("Use JsonReader.setLenient(true) to accept malformed JSON");
        }
    }

    private final void zzt(int i) {
        int i2 = this.zzl;
        int[] iArr = this.zzk;
        if (i2 == iArr.length) {
            int i3 = i2 + i2;
            this.zzk = Arrays.copyOf(iArr, i3);
            this.zzn = Arrays.copyOf(this.zzn, i3);
            this.zzm = (String[]) Arrays.copyOf(this.zzm, i3);
        }
        int[] iArr2 = this.zzk;
        int i4 = this.zzl;
        this.zzl = i4 + 1;
        iArr2[i4] = i;
    }

    private final void zzu() throws IOException {
        char c;
        do {
            if (this.zze >= this.zzf && !zzv(1)) {
                return;
            }
            char[] cArr = this.zzd;
            int i = this.zze;
            int i2 = i + 1;
            this.zze = i2;
            c = cArr[i];
            if (c == '\n') {
                this.zzg++;
                this.zzh = i2;
                return;
            }
        } while (c != '\r');
    }

    private final boolean zzv(int i) throws IOException {
        int i2;
        char[] cArr = this.zzd;
        int i3 = this.zzh;
        int i4 = this.zze;
        this.zzh = i3 - i4;
        int i5 = this.zzf;
        if (i5 != i4) {
            int i6 = i5 - i4;
            this.zzf = i6;
            System.arraycopy(cArr, i4, cArr, 0, i6);
        } else {
            this.zzf = 0;
        }
        this.zze = 0;
        do {
            Reader reader = this.zzb;
            int i7 = this.zzf;
            int i8 = reader.read(cArr, i7, 1024 - i7);
            if (i8 == -1) {
                return false;
            }
            i2 = this.zzf + i8;
            this.zzf = i2;
            if (this.zzg == 0 && this.zzh == 0 && i2 > 0 && cArr[0] == 65279) {
                this.zze++;
                this.zzh = 1;
                i++;
            }
        } while (i2 < i);
        return true;
    }

    private final boolean zzw(char c) throws IOException {
        if (c == '\t' || c == '\n' || c == '\f' || c == '\r' || c == ' ') {
            return false;
        }
        if (c != '#') {
            if (c == ',') {
                return false;
            }
            if (c != '/' && c != '=') {
                if (c == '{' || c == '}' || c == ':') {
                    return false;
                }
                if (c != ';') {
                    switch (c) {
                        case '[':
                        case ']':
                            return false;
                        case '\\':
                            break;
                        default:
                            return true;
                    }
                }
            }
        }
        zzs();
        return false;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() throws IOException {
        this.zza = 0;
        this.zzk[0] = 8;
        this.zzl = 1;
        this.zzb.close();
    }

    public final String toString() {
        String strValueOf = String.valueOf(getClass().getSimpleName());
        String strValueOf2 = String.valueOf(zzb());
        return strValueOf2.length() != 0 ? strValueOf.concat(strValueOf2) : new String(strValueOf);
    }

    /* JADX WARN: Code duplicated, block: B:105:0x015d  */
    /* JADX WARN: Code duplicated, block: B:107:0x0165  */
    /* JADX WARN: Code duplicated, block: B:112:0x017d  */
    /* JADX WARN: Code duplicated, block: B:115:0x018f  */
    /* JADX WARN: Code duplicated, block: B:118:0x0196  */
    /* JADX WARN: Code duplicated, block: B:121:0x01a1  */
    /* JADX WARN: Code duplicated, block: B:122:0x01a7 A[PHI: r3 r4
      0x01a7: PHI (r3v10 int) = (r3v9 int), (r3v12 int) binds: [B:114:0x018d, B:121:0x01a1] A[DONT_GENERATE, DONT_INLINE]
      0x01a7: PHI (r4v9 int) = (r4v8 int), (r4v10 int) binds: [B:114:0x018d, B:121:0x01a1] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:124:0x01af  */
    /* JADX WARN: Code duplicated, block: B:126:0x01b3  */
    /* JADX WARN: Code duplicated, block: B:166:0x021e  */
    /* JADX WARN: Code duplicated, block: B:167:0x0220  */
    /* JADX WARN: Code duplicated, block: B:169:0x0226 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:171:0x0229  */
    /* JADX WARN: Code duplicated, block: B:174:0x0231 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:176:0x0234 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:181:0x0248 A[DONT_INVERT, PHI: r1 r9
      0x0248: PHI (r1v68 int) = (r1v67 int), (r1v72 int) binds: [B:165:0x021c, B:180:0x0246] A[DONT_GENERATE, DONT_INLINE]
      0x0248: PHI (r9v17 int) = (r9v8 int), (r9v18 int) binds: [B:165:0x021c, B:180:0x0246] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:182:0x024a  */
    /* JADX WARN: Code duplicated, block: B:195:0x026a  */
    /* JADX WARN: Code duplicated, block: B:197:0x026e  */
    /* JADX WARN: Code duplicated, block: B:200:0x0273  */
    /* JADX WARN: Code duplicated, block: B:205:0x027f A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:206:0x0280  */
    /* JADX WARN: Code duplicated, block: B:208:0x028c  */
    /* JADX WARN: Code duplicated, block: B:210:0x0294  */
    /* JADX WARN: Code duplicated, block: B:212:0x029b A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:213:0x029c  */
    /* JADX WARN: Code duplicated, block: B:215:0x029f A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:217:0x02a2  */
    /* JADX WARN: Code duplicated, block: B:219:0x02a6  */
    /* JADX WARN: Code duplicated, block: B:229:0x02c2  */
    /* JADX WARN: Code duplicated, block: B:231:0x02ca  */
    /* JADX WARN: Code duplicated, block: B:272:0x0193 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:273:0x0193 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:274:0x019e A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:285:0x0156 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:65:0x00e4 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:66:0x00e6  */
    /* JADX WARN: Code duplicated, block: B:70:0x00ee A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:71:0x00f0  */
    /* JADX WARN: Code duplicated, block: B:73:0x00f4  */
    /* JADX WARN: Code duplicated, block: B:89:0x0123  */
    /* JADX WARN: Code duplicated, block: B:92:0x012f  */
    /* JADX WARN: Code duplicated, block: B:94:0x0136  */
    /* JADX WARN: Code duplicated, block: B:97:0x013f  */
    /* JADX WARN: Code restructure failed: missing block: B:174:0x0231, code lost:
    
        if (r14 == 0) goto L178;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    final int zza() throws java.io.IOException {
        /*
            Method dump skipped, instruction units count: 819
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.mlkit_vision_object_detection_bundled.zzeo.zza():int");
    }

    final String zzb() {
        int i = this.zzg + 1;
        int i2 = (this.zze - this.zzh) + 1;
        StringBuilder sb = new StringBuilder("$");
        int i3 = this.zzl;
        for (int i4 = 0; i4 < i3; i4++) {
            int i5 = this.zzk[i4];
            if (i5 == 1 || i5 == 2) {
                sb.append('[');
                sb.append(this.zzn[i4]);
                sb.append(']');
            } else if (i5 == 3 || i5 == 4 || i5 == 5) {
                sb.append('.');
                String str = this.zzm[i4];
                if (str != null) {
                    sb.append(str);
                }
            }
        }
        String string = sb.toString();
        StringBuilder sb2 = new StringBuilder(String.valueOf(string).length() + 45);
        sb2.append(" at line ");
        sb2.append(i);
        sb2.append(" column ");
        sb2.append(i2);
        sb2.append(" path ");
        sb2.append(string);
        return sb2.toString();
    }

    public final String zzc() throws IOException {
        String strZzq;
        int iZza = this.zza;
        if (iZza == 0) {
            iZza = zza();
        }
        if (iZza == 14) {
            strZzq = zzr();
        } else if (iZza == 12) {
            strZzq = zzq('\'');
        } else {
            if (iZza != 13) {
                String strZza = zzep.zza(zzn());
                String strZzb = zzb();
                StringBuilder sb = new StringBuilder(strZza.length() + 24 + String.valueOf(strZzb).length());
                sb.append("Expected a name but was ");
                sb.append(strZza);
                sb.append(strZzb);
                throw new IllegalStateException(sb.toString());
            }
            strZzq = zzq('\"');
        }
        this.zza = 0;
        this.zzm[this.zzl - 1] = strZzq;
        return strZzq;
    }

    public final String zzd() throws IOException {
        String str;
        int iZza = this.zza;
        if (iZza == 0) {
            iZza = zza();
        }
        if (iZza == 10) {
            str = zzr();
        } else if (iZza == 8) {
            str = zzq('\'');
        } else if (iZza == 9) {
            str = zzq('\"');
        } else if (iZza == 11) {
            str = null;
        } else if (iZza == 15) {
            str = Long.toString(this.zzi);
        } else {
            if (iZza != 16) {
                String strZza = zzep.zza(zzn());
                String strZzb = zzb();
                StringBuilder sb = new StringBuilder(strZza.length() + 26 + String.valueOf(strZzb).length());
                sb.append("Expected a string but was ");
                sb.append(strZza);
                sb.append(strZzb);
                throw new IllegalStateException(sb.toString());
            }
            str = new String(this.zzd, this.zze, this.zzj);
            this.zze += this.zzj;
        }
        this.zza = 0;
        int[] iArr = this.zzn;
        int i = this.zzl - 1;
        iArr[i] = iArr[i] + 1;
        return str;
    }

    public final void zze() throws IOException {
        int iZza = this.zza;
        if (iZza == 0) {
            iZza = zza();
        }
        if (iZza == 3) {
            zzt(1);
            this.zzn[this.zzl - 1] = 0;
            this.zza = 0;
            return;
        }
        String strZza = zzep.zza(zzn());
        String strZzb = zzb();
        StringBuilder sb = new StringBuilder(strZza.length() + 29 + String.valueOf(strZzb).length());
        sb.append("Expected BEGIN_ARRAY but was ");
        sb.append(strZza);
        sb.append(strZzb);
        throw new IllegalStateException(sb.toString());
    }

    public final void zzf() throws IOException {
        int iZza = this.zza;
        if (iZza == 0) {
            iZza = zza();
        }
        if (iZza == 1) {
            zzt(3);
            this.zza = 0;
            return;
        }
        String strZza = zzep.zza(zzn());
        String strZzb = zzb();
        StringBuilder sb = new StringBuilder(strZza.length() + 30 + String.valueOf(strZzb).length());
        sb.append("Expected BEGIN_OBJECT but was ");
        sb.append(strZza);
        sb.append(strZzb);
        throw new IllegalStateException(sb.toString());
    }

    public final void zzg() throws IOException {
        int iZza = this.zza;
        if (iZza == 0) {
            iZza = zza();
        }
        if (iZza == 4) {
            int i = this.zzl;
            this.zzl = i - 1;
            int[] iArr = this.zzn;
            int i2 = i - 2;
            iArr[i2] = iArr[i2] + 1;
            this.zza = 0;
            return;
        }
        String strZza = zzep.zza(zzn());
        String strZzb = zzb();
        StringBuilder sb = new StringBuilder(strZza.length() + 27 + String.valueOf(strZzb).length());
        sb.append("Expected END_ARRAY but was ");
        sb.append(strZza);
        sb.append(strZzb);
        throw new IllegalStateException(sb.toString());
    }

    public final void zzh() throws IOException {
        int iZza = this.zza;
        if (iZza == 0) {
            iZza = zza();
        }
        if (iZza == 2) {
            int i = this.zzl;
            int i2 = i - 1;
            this.zzl = i2;
            this.zzm[i2] = null;
            int[] iArr = this.zzn;
            int i3 = i - 2;
            iArr[i3] = iArr[i3] + 1;
            this.zza = 0;
            return;
        }
        String strZza = zzep.zza(zzn());
        String strZzb = zzb();
        StringBuilder sb = new StringBuilder(strZza.length() + 28 + String.valueOf(strZzb).length());
        sb.append("Expected END_OBJECT but was ");
        sb.append(strZza);
        sb.append(strZzb);
        throw new IllegalStateException(sb.toString());
    }

    public final void zzi() throws IOException {
        int iZza = this.zza;
        if (iZza == 0) {
            iZza = zza();
        }
        if (iZza == 7) {
            this.zza = 0;
            int[] iArr = this.zzn;
            int i = this.zzl - 1;
            iArr[i] = iArr[i] + 1;
            return;
        }
        String strZza = zzep.zza(zzn());
        String strZzb = zzb();
        StringBuilder sb = new StringBuilder(strZza.length() + 22 + String.valueOf(strZzb).length());
        sb.append("Expected null but was ");
        sb.append(strZza);
        sb.append(strZzb);
        throw new IllegalStateException(sb.toString());
    }

    public final void zzj(boolean z) {
        this.zzc = z;
    }

    public final boolean zzk() throws IOException {
        int iZza = this.zza;
        if (iZza == 0) {
            iZza = zza();
        }
        return (iZza == 2 || iZza == 4) ? false : true;
    }

    public final boolean zzl() {
        return this.zzc;
    }

    public final boolean zzm() throws IOException {
        int iZza = this.zza;
        if (iZza == 0) {
            iZza = zza();
        }
        if (iZza == 5) {
            this.zza = 0;
            int[] iArr = this.zzn;
            int i = this.zzl - 1;
            iArr[i] = iArr[i] + 1;
            return true;
        }
        if (iZza == 6) {
            this.zza = 0;
            int[] iArr2 = this.zzn;
            int i2 = this.zzl - 1;
            iArr2[i2] = iArr2[i2] + 1;
            return false;
        }
        String strZza = zzep.zza(zzn());
        String strZzb = zzb();
        StringBuilder sb = new StringBuilder(strZza.length() + 27 + String.valueOf(strZzb).length());
        sb.append("Expected a boolean but was ");
        sb.append(strZza);
        sb.append(strZzb);
        throw new IllegalStateException(sb.toString());
    }

    public final int zzn() throws IOException {
        int iZza = this.zza;
        if (iZza == 0) {
            iZza = zza();
        }
        switch (iZza) {
            case 1:
                return 3;
            case 2:
                return 4;
            case 3:
                return 1;
            case 4:
                return 2;
            case 5:
            case 6:
                return 8;
            case 7:
                return 9;
            case 8:
            case 9:
            case 10:
            case 11:
                return 6;
            case 12:
            case 13:
            case 14:
                return 5;
            case 15:
            case 16:
                return 7;
            default:
                return 10;
        }
    }
}
