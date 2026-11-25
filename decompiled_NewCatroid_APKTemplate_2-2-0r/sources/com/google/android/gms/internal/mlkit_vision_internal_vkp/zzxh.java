package com.google.android.gms.internal.mlkit_vision_internal_vkp;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;

/* JADX INFO: compiled from: com.google.mlkit:vision-internal-vkp@@18.2.0 */
/* JADX INFO: loaded from: classes9.dex */
public final class zzxh implements Closeable, Flushable {
    private static final String[] zza = new String[128];
    private static final String[] zzb;
    private final Writer zzc;
    private int[] zzd = new int[32];
    private int zze = 0;
    private final String zzf;
    private boolean zzg;
    private String zzh;

    static {
        for (int i = 0; i <= 31; i++) {
            zza[i] = String.format("\\u%04x", Integer.valueOf(i));
        }
        String[] strArr = zza;
        strArr[34] = "\\\"";
        strArr[92] = "\\\\";
        strArr[9] = "\\t";
        strArr[8] = "\\b";
        strArr[10] = "\\n";
        strArr[13] = "\\r";
        strArr[12] = "\\f";
        String[] strArr2 = (String[]) strArr.clone();
        zzb = strArr2;
        strArr2[60] = "\\u003c";
        strArr2[62] = "\\u003e";
        strArr2[38] = "\\u0026";
        strArr2[61] = "\\u003d";
        strArr2[39] = "\\u0027";
    }

    public zzxh(Writer writer) {
        zzo(6);
        this.zzf = ":";
        this.zzc = writer;
    }

    private final int zzk() {
        int i = this.zze;
        if (i != 0) {
            return this.zzd[i - 1];
        }
        throw new IllegalStateException("JsonWriter is closed.");
    }

    private final zzxh zzl(int i, int i2, char c) throws IOException {
        int iZzk = zzk();
        if (iZzk != i2 && iZzk != i) {
            throw new IllegalStateException("Nesting problem.");
        }
        String str = this.zzh;
        if (str != null) {
            throw new IllegalStateException(str.length() != 0 ? "Dangling name: ".concat(str) : new String("Dangling name: "));
        }
        this.zze--;
        this.zzc.write(c);
        return this;
    }

    private final zzxh zzm(int i, char c) throws IOException {
        zzn();
        zzo(i);
        this.zzc.write(c);
        return this;
    }

    private final void zzn() throws IOException {
        int iZzk = zzk();
        if (iZzk == 1) {
            zzp(2);
            return;
        }
        if (iZzk == 2) {
            this.zzc.append(',');
            return;
        }
        if (iZzk == 4) {
            this.zzc.append((CharSequence) this.zzf);
            zzp(5);
            return;
        }
        if (iZzk != 6) {
            if (iZzk != 7) {
                throw new IllegalStateException("Nesting problem.");
            }
            if (!this.zzg) {
                throw new IllegalStateException("JSON must have only one top-level value.");
            }
        }
        zzp(7);
    }

    private final void zzo(int i) {
        int i2 = this.zze;
        int[] iArr = this.zzd;
        if (i2 == iArr.length) {
            this.zzd = Arrays.copyOf(iArr, i2 + i2);
        }
        int[] iArr2 = this.zzd;
        int i3 = this.zze;
        this.zze = i3 + 1;
        iArr2[i3] = i;
    }

    private final void zzp(int i) {
        this.zzd[this.zze - 1] = i;
    }

    /* JADX WARN: Code duplicated, block: B:16:0x002d  */
    private final void zzq(String str) throws IOException {
        String str2;
        String[] strArr = zza;
        this.zzc.write(34);
        int length = str.length();
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            char cCharAt = str.charAt(i2);
            if (cCharAt < 128) {
                str2 = strArr[cCharAt];
                if (str2 != null) {
                    if (i < i2) {
                        this.zzc.write(str, i, i2 - i);
                    }
                    this.zzc.write(str2);
                    i = i2 + 1;
                }
            } else {
                if (cCharAt == 8232) {
                    str2 = "\\u2028";
                } else if (cCharAt == 8233) {
                    str2 = "\\u2029";
                }
                if (i < i2) {
                    this.zzc.write(str, i, i2 - i);
                }
                this.zzc.write(str2);
                i = i2 + 1;
            }
        }
        if (i < length) {
            this.zzc.write(str, i, length - i);
        }
        this.zzc.write(34);
    }

    private final void zzr() throws IOException {
        if (this.zzh != null) {
            int iZzk = zzk();
            if (iZzk == 5) {
                this.zzc.write(44);
            } else if (iZzk != 3) {
                throw new IllegalStateException("Nesting problem.");
            }
            zzp(4);
            zzq(this.zzh);
            this.zzh = null;
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() throws IOException {
        this.zzc.close();
        int i = this.zze;
        if (i > 1 || (i == 1 && this.zzd[0] != 7)) {
            throw new IOException("Incomplete document");
        }
        this.zze = 0;
    }

    @Override // java.io.Flushable
    public final void flush() throws IOException {
        if (this.zze == 0) {
            throw new IllegalStateException("JsonWriter is closed.");
        }
        this.zzc.flush();
    }

    public final zzxh zza() throws IOException {
        zzr();
        zzm(1, '[');
        return this;
    }

    public final zzxh zzb() throws IOException {
        zzr();
        zzm(3, '{');
        return this;
    }

    public final zzxh zzc() throws IOException {
        zzl(1, 2, ']');
        return this;
    }

    public final zzxh zzd() throws IOException {
        zzl(3, 5, '}');
        return this;
    }

    public final zzxh zze(String str) throws IOException {
        if (str == null) {
            throw new NullPointerException("name == null");
        }
        if (this.zzh != null) {
            throw new IllegalStateException();
        }
        if (this.zze == 0) {
            throw new IllegalStateException("JsonWriter is closed.");
        }
        this.zzh = str;
        return this;
    }

    public final zzxh zzf() throws IOException {
        if (this.zzh != null) {
            zzr();
        }
        zzn();
        this.zzc.write("null");
        return this;
    }

    public final zzxh zzg(Number number) throws IOException {
        zzr();
        String string = number.toString();
        if (this.zzg || !(string.equals("-Infinity") || string.equals("Infinity") || string.equals("NaN"))) {
            zzn();
            this.zzc.append((CharSequence) string);
            return this;
        }
        String strValueOf = String.valueOf(number);
        StringBuilder sb = new StringBuilder(String.valueOf(strValueOf).length() + 39);
        sb.append("Numeric values must be finite, but was ");
        sb.append(strValueOf);
        throw new IllegalArgumentException(sb.toString());
    }

    public final zzxh zzh(String str) throws IOException {
        if (str == null) {
            zzf();
            return this;
        }
        zzr();
        zzn();
        zzq(str);
        return this;
    }

    public final zzxh zzi(boolean z) throws IOException {
        zzr();
        zzn();
        this.zzc.write(true != z ? "false" : "true");
        return this;
    }

    public final void zzj(boolean z) {
        this.zzg = true;
    }
}
