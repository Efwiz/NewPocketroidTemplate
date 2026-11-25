package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class S4 {
    private final int[] a;
    private final int b;
    private final int c;
    private final int d;
    private final List e;

    public S4(int... iArr) {
        List listB;
        AbstractC0936Ds.c(iArr, "numbers");
        this.a = iArr;
        Integer numA = AbstractC2129h3.a(iArr, 0);
        this.b = numA != null ? numA.intValue() : -1;
        Integer numA2 = AbstractC2129h3.a(iArr, 1);
        this.c = numA2 != null ? numA2.intValue() : -1;
        Integer numA3 = AbstractC2129h3.a(iArr, 2);
        this.d = numA3 != null ? numA3.intValue() : -1;
        if (iArr.length <= 3) {
            listB = C1940eg.a;
        } else {
            if (iArr.length > 1024) {
                throw new IllegalArgumentException("BinaryVersion with length more than 1024 are not supported. Provided length " + iArr.length + '.');
            }
            listB = AbstractC0823Ab.b((Iterable) AbstractC2129h3.a(iArr).subList(3, iArr.length));
        }
        this.e = listB;
    }

    public final int a() {
        return this.b;
    }

    public final int b() {
        return this.c;
    }

    public final int[] c() {
        return this.a;
    }

    public final boolean equals(Object obj) {
        if (obj != null && AbstractC0936Ds.a(getClass(), obj.getClass())) {
            S4 s4 = (S4) obj;
            if (this.b == s4.b && this.c == s4.c && this.d == s4.d && AbstractC0936Ds.a(this.e, s4.e)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        int i = this.b;
        int i2 = (i * 31) + this.c + i;
        int i3 = (i2 * 31) + this.d + i2;
        return this.e.hashCode() + (i3 * 31) + i3;
    }

    public final String toString() {
        int[] iArr = this.a;
        ArrayList arrayList = new ArrayList();
        for (int i : iArr) {
            if (i == -1) {
                break;
            }
            arrayList.add(Integer.valueOf(i));
        }
        return arrayList.isEmpty() ? "unknown" : AbstractC0823Ab.a(arrayList, ".", null, null, null, 62);
    }

    protected final boolean a(C3394xu c3394xu) {
        AbstractC0936Ds.c(c3394xu, "ourVersion");
        int i = this.b;
        return i != 0 ? !(i != c3394xu.b || this.c > c3394xu.c) : c3394xu.b == 0 && this.c == c3394xu.c;
    }
}
