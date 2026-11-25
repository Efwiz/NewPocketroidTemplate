package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class S3 {
    private int a;
    private T3[] b = new T3[6];

    S3() {
    }

    final T3[] a() {
        int i = this.a;
        T3[] t3Arr = new T3[i];
        System.arraycopy(this.b, 0, t3Arr, 0, i);
        return t3Arr;
    }

    final void a(T3 t3) {
        while (t3 != null) {
            int i = 0;
            while (true) {
                int i2 = this.a;
                if (i < i2) {
                    if (this.b[i].a.equals(t3.a)) {
                        break;
                    } else {
                        i++;
                    }
                } else {
                    T3[] t3Arr = this.b;
                    if (i2 >= t3Arr.length) {
                        T3[] t3Arr2 = new T3[t3Arr.length + 6];
                        System.arraycopy(t3Arr, 0, t3Arr2, 0, i2);
                        this.b = t3Arr2;
                    }
                    T3[] t3Arr3 = this.b;
                    int i3 = this.a;
                    this.a = i3 + 1;
                    t3Arr3[i3] = t3;
                    break;
                }
            }
            t3 = t3.c;
        }
    }
}
