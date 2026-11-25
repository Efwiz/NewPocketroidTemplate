package com.android.tools.r8.internal;

import java.io.BufferedWriter;
import java.io.IOException;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class W4 {
    static final /* synthetic */ boolean c = true;
    final int a;
    final int b;

    public W4(int i, int i2) {
        this.a = i;
        this.b = i2;
        if (!c && !d()) {
            throw new AssertionError();
        }
    }

    private boolean d() {
        return this.a <= this.b;
    }

    public final void a(BufferedWriter bufferedWriter) throws IOException {
        bufferedWriter.write(new StringBuilder().append(this.a).toString());
        bufferedWriter.write(" ");
        bufferedWriter.write(new StringBuilder().append(this.b).toString());
    }

    public final int b() {
        return this.b - this.a;
    }

    public final W4 c() {
        int iB = b() / 2;
        int i = this.a;
        return new W4(i, iB + i);
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof W4)) {
            return false;
        }
        W4 w4 = (W4) obj;
        return this.a == w4.a && this.b == w4.b;
    }

    public final int hashCode() {
        return (this.a * 31) + this.b;
    }

    public final String toString() {
        return "[" + this.a + ";" + this.b + "]";
    }

    public final boolean a() {
        return this.a == this.b;
    }

    public W4(String str) {
        int iIndexOf = str.indexOf(32);
        this.a = Integer.parseInt(str.substring(0, iIndexOf).trim());
        this.b = Integer.parseInt(str.substring(iIndexOf + 1).trim());
        if (!c && !d()) {
            throw new AssertionError();
        }
    }

    public final W4 a(W4 w4) {
        if (a()) {
            return w4;
        }
        if (w4.a()) {
            return this;
        }
        if (c || this.a == w4.b || this.b == w4.a) {
            return new W4(Integer.min(this.a, w4.a), Integer.max(this.b, w4.b));
        }
        throw new AssertionError();
    }
}
