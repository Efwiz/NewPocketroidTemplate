package com.android.tools.r8.utils;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class W1 {
    static final /* synthetic */ boolean c = true;
    private final long a;
    private final long b;

    public W1(int i, int i2) {
        if (!c && i > i2) {
            throw new AssertionError();
        }
        this.a = i;
        this.b = i2;
    }

    public long a() {
        return this.b;
    }

    public long b() {
        return this.a;
    }

    public final long c() {
        if (c || d()) {
            return this.a;
        }
        throw new AssertionError();
    }

    public final boolean d() {
        return this.a == this.b;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof W1)) {
            return false;
        }
        W1 w1 = (W1) obj;
        return w1.a == this.a && w1.b == this.b;
    }

    public final int hashCode() {
        return super.hashCode();
    }

    public final String toString() {
        return "[" + this.a + ", " + this.b + "]";
    }

    public final boolean a(long j) {
        return this.a <= j && j <= this.b;
    }

    public final boolean a(W1 w1) {
        return w1.b >= this.a && this.b >= w1.a;
    }

    public W1(long j, long j2) {
        if (!c && j > j2) {
            throw new AssertionError();
        }
        this.a = j;
        this.b = j2;
    }
}
