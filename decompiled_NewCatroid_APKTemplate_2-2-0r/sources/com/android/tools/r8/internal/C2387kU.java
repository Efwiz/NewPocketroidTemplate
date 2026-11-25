package com.android.tools.r8.internal;

import java.util.Arrays;
import java.util.Stack;

/* JADX INFO: renamed from: com.android.tools.r8.internal.kU, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2387kU {
    private final Stack a;

    private C2387kU() {
        this.a = new Stack();
    }

    static Y5 a(C2387kU c2387kU, Y5 y5, Y5 y6) {
        c2387kU.a(y5);
        c2387kU.a(y6);
        Y5 c2615nU = (Y5) c2387kU.a.pop();
        while (!c2387kU.a.isEmpty()) {
            c2615nU = new C2615nU((Y5) c2387kU.a.pop(), c2615nU, 0);
        }
        return c2615nU;
    }

    /* synthetic */ C2387kU(int i) {
        this();
    }

    private void a(Y5 y5) {
        int i;
        if (y5.b()) {
            int iBinarySearch = Arrays.binarySearch(C2615nU.i, y5.size());
            if (iBinarySearch < 0) {
                iBinarySearch = (-(iBinarySearch + 1)) - 1;
            }
            int i2 = C2615nU.i[iBinarySearch + 1];
            if (!this.a.isEmpty() && ((Y5) this.a.peek()).size() < i2) {
                int i3 = C2615nU.i[iBinarySearch];
                Y5 c2615nU = (Y5) this.a.pop();
                while (true) {
                    i = 0;
                    if (this.a.isEmpty() || ((Y5) this.a.peek()).size() >= i3) {
                        break;
                    } else {
                        c2615nU = new C2615nU((Y5) this.a.pop(), c2615nU, i);
                    }
                }
                C2615nU c2615nU2 = new C2615nU(c2615nU, y5, i);
                while (!this.a.isEmpty()) {
                    int iBinarySearch2 = Arrays.binarySearch(C2615nU.i, c2615nU2.size());
                    if (iBinarySearch2 < 0) {
                        iBinarySearch2 = (-(iBinarySearch2 + 1)) - 1;
                    }
                    if (((Y5) this.a.peek()).size() >= C2615nU.i[iBinarySearch2 + 1]) {
                        break;
                    } else {
                        c2615nU2 = new C2615nU((Y5) this.a.pop(), c2615nU2, i);
                    }
                }
                this.a.push(c2615nU2);
                return;
            }
            this.a.push(y5);
            return;
        }
        if (y5 instanceof C2615nU) {
            C2615nU c2615nU3 = (C2615nU) y5;
            a(c2615nU3.d);
            a(c2615nU3.e);
            return;
        }
        String strValueOf = String.valueOf(y5.getClass());
        throw new IllegalArgumentException(new StringBuilder(strValueOf.length() + 49).append("Has a new type of ByteString been created? Found ").append(strValueOf).toString());
    }
}
