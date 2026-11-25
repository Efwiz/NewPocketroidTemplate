package com.android.tools.r8.internal;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.Writer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.cu, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1799cu implements Closeable, Flushable {
    private static final String[] j = new String[128];
    private static final String[] k;
    private final Writer a;
    private int[] b = new int[32];
    private int c = 0;
    private String d;
    private String e;
    private boolean f;
    private boolean g;
    private String h;
    private boolean i;

    static {
        for (int i = 0; i <= 31; i++) {
            j[i] = String.format("\\u%04x", Integer.valueOf(i));
        }
        String[] strArr = j;
        strArr[34] = "\\\"";
        strArr[92] = "\\\\";
        strArr[9] = "\\t";
        strArr[8] = "\\b";
        strArr[10] = "\\n";
        strArr[13] = "\\r";
        strArr[12] = "\\f";
        String[] strArr2 = (String[]) strArr.clone();
        k = strArr2;
        strArr2[60] = "\\u003c";
        strArr2[62] = "\\u003e";
        strArr2[38] = "\\u0026";
        strArr2[61] = "\\u003d";
        strArr2[39] = "\\u0027";
    }

    public C1799cu(Writer writer) {
        a(6);
        this.e = ":";
        this.i = true;
        if (writer == null) {
            throw new NullPointerException("out == null");
        }
        this.a = writer;
    }

    private void k() throws IOException {
        if (this.d == null) {
            return;
        }
        this.a.write("\n");
        int i = this.c;
        for (int i2 = 1; i2 < i; i2++) {
            this.a.write(this.d);
        }
    }

    private void n() throws IOException {
        if (this.h != null) {
            int i = this.c;
            if (i == 0) {
                throw new IllegalStateException("JsonWriter is closed.");
            }
            int i2 = this.b[i - 1];
            if (i2 == 5) {
                this.a.write(44);
            } else if (i2 != 3) {
                throw new IllegalStateException("Nesting problem.");
            }
            k();
            this.b[this.c - 1] = 4;
            c(this.h);
            this.h = null;
        }
    }

    public final void a(boolean z) {
        this.g = z;
    }

    public final void b(boolean z) {
        this.f = z;
    }

    public final void c(boolean z) {
        this.i = z;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.a.close();
        int i = this.c;
        if (i > 1 || (i == 1 && this.b[i - 1] != 7)) {
            throw new IOException("Incomplete document");
        }
        this.c = 0;
    }

    public void d() throws IOException {
        n();
        c();
        a(1);
        this.a.write("[");
    }

    public void e() throws IOException {
        n();
        c();
        a(3);
        this.a.write("{");
    }

    public void flush() throws IOException {
        if (this.c == 0) {
            throw new IllegalStateException("JsonWriter is closed.");
        }
        this.a.flush();
    }

    public final boolean h() {
        return this.i;
    }

    public final boolean i() {
        return this.g;
    }

    public final boolean j() {
        return this.f;
    }

    public C1799cu l() throws IOException {
        if (this.h != null) {
            if (!this.i) {
                this.h = null;
                return this;
            }
            n();
        }
        c();
        this.a.write("null");
        return this;
    }

    public final void m() {
        this.d = "  ";
        this.e = ": ";
    }

    private void a(int i) {
        int i2 = this.c;
        int[] iArr = this.b;
        if (i2 == iArr.length) {
            int[] iArr2 = new int[i2 * 2];
            System.arraycopy(iArr, 0, iArr2, 0, i2);
            this.b = iArr2;
        }
        int[] iArr3 = this.b;
        int i3 = this.c;
        this.c = i3 + 1;
        iArr3[i3] = i;
    }

    private void c() throws IOException {
        int i = this.c;
        if (i == 0) {
            throw new IllegalStateException("JsonWriter is closed.");
        }
        int[] iArr = this.b;
        int i2 = i - 1;
        int i3 = iArr[i2];
        if (i3 == 1) {
            iArr[i2] = 2;
            k();
            return;
        }
        if (i3 == 2) {
            this.a.append(',');
            k();
        } else {
            if (i3 == 4) {
                this.a.append((CharSequence) this.e);
                this.b[this.c - 1] = 5;
                return;
            }
            if (i3 != 6) {
                if (i3 != 7) {
                    throw new IllegalStateException("Nesting problem.");
                }
                if (!this.f) {
                    throw new IllegalStateException("JSON must have only one top-level value.");
                }
            }
            iArr[i2] = 7;
        }
    }

    public void b(String str) {
        if (str == null) {
            throw new NullPointerException("name == null");
        }
        if (this.h != null) {
            throw new IllegalStateException();
        }
        if (this.c == 0) {
            throw new IllegalStateException("JsonWriter is closed.");
        }
        this.h = str;
    }

    public void f() throws IOException {
        int i = this.c;
        if (i == 0) {
            throw new IllegalStateException("JsonWriter is closed.");
        }
        int i2 = i - 1;
        int i3 = this.b[i2];
        if (i3 != 2 && i3 != 1) {
            throw new IllegalStateException("Nesting problem.");
        }
        if (this.h != null) {
            throw new IllegalStateException("Dangling name: " + this.h);
        }
        this.c = i2;
        if (i3 == 2) {
            k();
        }
        this.a.write("]");
    }

    public void g() throws IOException {
        int i = this.c;
        if (i == 0) {
            throw new IllegalStateException("JsonWriter is closed.");
        }
        int i2 = i - 1;
        int i3 = this.b[i2];
        if (i3 != 5 && i3 != 3) {
            throw new IllegalStateException("Nesting problem.");
        }
        if (this.h != null) {
            throw new IllegalStateException("Dangling name: " + this.h);
        }
        this.c = i2;
        if (i3 == 5) {
            k();
        }
        this.a.write("}");
    }

    public void d(String str) throws IOException {
        if (str == null) {
            l();
            return;
        }
        n();
        c();
        c(str);
    }

    public void a(Boolean bool) throws IOException {
        if (bool == null) {
            l();
            return;
        }
        n();
        c();
        this.a.write(bool.booleanValue() ? "true" : "false");
    }

    public void d(boolean z) throws IOException {
        n();
        c();
        this.a.write(z ? "true" : "false");
    }

    public void a(long j2) throws IOException {
        n();
        c();
        this.a.write(Long.toString(j2));
    }

    public void a(Number number) throws IOException {
        if (number == null) {
            l();
            return;
        }
        n();
        String string = number.toString();
        if (!this.f && (string.equals("-Infinity") || string.equals("Infinity") || string.equals("NaN"))) {
            throw new IllegalArgumentException("Numeric values must be finite, but was " + number);
        }
        c();
        this.a.append((CharSequence) string);
    }

    /* JADX WARN: Code duplicated, block: B:20:0x0034  */
    private void c(String str) throws IOException {
        String str2;
        String[] strArr = this.g ? k : j;
        this.a.write("\"");
        int length = str.length();
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            char cCharAt = str.charAt(i2);
            if (cCharAt < 128) {
                str2 = strArr[cCharAt];
                if (str2 != null) {
                    if (i < i2) {
                        this.a.write(str, i, i2 - i);
                    }
                    this.a.write(str2);
                    i = i2 + 1;
                }
            } else {
                if (cCharAt == 8232) {
                    str2 = "\\u2028";
                } else if (cCharAt == 8233) {
                    str2 = "\\u2029";
                }
                if (i < i2) {
                    this.a.write(str, i, i2 - i);
                }
                this.a.write(str2);
                i = i2 + 1;
            }
        }
        if (i < length) {
            this.a.write(str, i, length - i);
        }
        this.a.write("\"");
    }
}
