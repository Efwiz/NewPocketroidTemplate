package com.android.tools.r8.naming;

import java.util.Objects;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class N0 {
    static final /* synthetic */ boolean d = true;
    public final int a;
    public final int b;
    public final boolean c;

    public N0(int i) {
        this(i, i, true);
    }

    public final boolean a() {
        return this.a == 0 && this.b == 65535;
    }

    public final boolean b() {
        return this.a == 0 && this.b == 0;
    }

    public final int c() {
        if (this.c) {
            return 1;
        }
        return (this.b - this.a) + 1;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof N0)) {
            return false;
        }
        N0 n0 = (N0) obj;
        return this.a == n0.a && this.b == n0.b && this.c == n0.c;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(this.a), Integer.valueOf(this.b), Boolean.valueOf(this.c));
    }

    public final String toString() {
        StringBuilder sbAppend;
        if (this.c) {
            sbAppend = new StringBuilder().append(this.a);
        } else {
            int i = this.a;
            sbAppend = new StringBuilder().append(i).append(":").append(this.b);
        }
        return sbAppend.toString();
    }

    public N0(int i, int i2) {
        this(i, i2, false);
    }

    private N0(int i, int i2, boolean z) {
        this.a = i;
        this.b = i2;
        this.c = z;
        if (!d && i > i2) {
            throw new AssertionError();
        }
    }
}
