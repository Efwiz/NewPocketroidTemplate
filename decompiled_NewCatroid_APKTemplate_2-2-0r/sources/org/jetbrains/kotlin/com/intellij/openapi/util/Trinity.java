package org.jetbrains.kotlin.com.intellij.openapi.util;

import java.util.Arrays;

/* JADX INFO: loaded from: classes3.dex */
public class Trinity<A, B, C> {
    public final A first;
    public final B second;
    public final C third;

    public Trinity(A a, B b, C c) {
        this.first = a;
        this.second = b;
        this.third = c;
    }

    public static <A, B, C> Trinity<A, B, C> create(A a, B b, C c) {
        return new Trinity<>(a, b, c);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof Trinity) {
            Trinity trinity = (Trinity) obj;
            if (Comparing.equal(this.first, trinity.first) && Comparing.equal(this.second, trinity.second) && Comparing.equal(this.third, trinity.third)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        A a = this.first;
        int iHashCode = a != null ? hashCode(a) : 0;
        B b = this.second;
        if (b != null) {
            iHashCode += hashCode(b);
        }
        C c = this.third;
        return c != null ? iHashCode + hashCode(c) : iHashCode;
    }

    private static int hashCode(Object obj) {
        return obj instanceof Object[] ? Arrays.hashCode((Object[]) obj) : obj.hashCode();
    }

    public String toString() {
        return "<" + this.first + "," + this.second + "," + this.third + ">";
    }
}
