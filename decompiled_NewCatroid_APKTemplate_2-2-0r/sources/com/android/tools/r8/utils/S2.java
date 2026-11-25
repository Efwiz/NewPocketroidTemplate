package com.android.tools.r8.utils;

import com.beust.jcommander.Parameters;
import java.util.Objects;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class S2 implements Comparable<S2> {
    private static S2 e = a(0, 0, 0);
    private static S2 f = a(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
    private final int a;
    private final int b;
    private final int c;
    private final String d;

    private S2(int i, int i2, int i3, String str) {
        this.a = i;
        this.b = i2;
        this.c = i3;
        this.d = str;
    }

    public static S2 a(String str) {
        int iIndexOf = str.indexOf(46);
        if (iIndexOf <= 0) {
            throw new IllegalArgumentException("Invalid semantic version: " + str);
        }
        int i = iIndexOf + 1;
        int iIndexOf2 = str.indexOf(46, i);
        if (iIndexOf2 <= i) {
            throw new IllegalArgumentException("Invalid semantic version: " + str);
        }
        int i2 = iIndexOf2 + 1;
        int iIndexOf3 = str.indexOf(45, i2);
        int length = -1;
        if (iIndexOf3 == -1) {
            iIndexOf3 = str.length();
        } else {
            if (iIndexOf3 <= i2) {
                throw new IllegalArgumentException("Invalid semantic version: " + str);
            }
            length = str.length();
        }
        try {
            return new S2(Integer.parseInt(str.substring(0, iIndexOf)), Integer.parseInt(str.substring(i, iIndexOf2)), Integer.parseInt(str.substring(i2, iIndexOf3)), length < 0 ? null : str.substring(iIndexOf3 + 1, length));
        } catch (NumberFormatException e2) {
            throw new IllegalArgumentException("Invalid semantic version: " + str, e2);
        }
    }

    public static S2 d() {
        return f;
    }

    public static S2 e() {
        return e;
    }

    public int b() {
        return this.b;
    }

    public int c() {
        return this.c;
    }

    @Override // java.lang.Comparable
    public final int compareTo(S2 s2) {
        S2 s3 = s2;
        if (equals(s3)) {
            return 0;
        }
        return b(s3) ? -1 : 1;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof S2)) {
            return false;
        }
        S2 s2 = (S2) obj;
        return this.a == s2.a && this.b == s2.b && this.c == s2.c && Objects.equals(this.d, s2.d);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(this.a), Integer.valueOf(this.b), Integer.valueOf(this.c), this.d);
    }

    public String toString() {
        int i = this.a;
        int i2 = this.b;
        int i3 = this.c;
        String str = this.d;
        return i + "." + i2 + "." + i3 + (str != null ? Parameters.DEFAULT_OPTION_PREFIXES + str : "");
    }

    public boolean b(S2 s2) {
        return a(s2) || equals(s2);
    }

    public static S2 a(int i, int i2, int i3) {
        return new S2(i, i2, i3, null);
    }

    public int a() {
        return this.a;
    }

    public final boolean a(S2 s2) {
        int i = this.a;
        int i2 = s2.a;
        if (i != i2) {
            return i > i2;
        }
        int i3 = this.b;
        int i4 = s2.b;
        if (i3 != i4) {
            return i3 > i4;
        }
        return this.c > s2.c;
    }
}
