package com.android.tools.r8.internal;

import com.android.dx.cf.attrib.AttSynthetic;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class T3 {
    public final String a;
    private byte[] b;
    T3 c;

    protected T3(String str) {
        this.a = str;
    }

    protected T3 a(C1031Ha c1031Ha, int i, int i2) {
        T3 t3 = new T3(this.a);
        byte[] bArr = new byte[i2];
        t3.b = bArr;
        System.arraycopy(c1031Ha.b, i, bArr, 0, i2);
        return t3;
    }

    protected C1667b6 a() {
        return new C1667b6(this.b);
    }

    static int a(NY ny, int i, int i2) {
        int i3;
        if ((i & 4096) == 0 || ny.e() >= 49) {
            i3 = 0;
        } else {
            ny.d(AttSynthetic.ATTRIBUTE_NAME);
            i3 = 6;
        }
        if (i2 != 0) {
            ny.d("Signature");
            i3 += 8;
        }
        if ((i & 131072) == 0) {
            return i3;
        }
        ny.d("Deprecated");
        return i3 + 6;
    }

    static void a(NY ny, int i, int i2, C1667b6 c1667b6) {
        if ((i & 4096) != 0 && ny.e() < 49) {
            c1667b6.d(ny.d(AttSynthetic.ATTRIBUTE_NAME)).c(0);
        }
        if (i2 != 0) {
            c1667b6.d(ny.d("Signature")).c(2).d(i2);
        }
        if ((i & 131072) != 0) {
            c1667b6.d(ny.d("Deprecated")).c(0);
        }
    }
}
