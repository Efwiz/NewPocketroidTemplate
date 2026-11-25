package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class DQ extends CQ {
    private final NY b;
    private final int c;
    private final int d;
    private int e;
    private C2583n2 f;
    private C2583n2 g;
    private C2583n2 h;
    private C2583n2 i;
    private T3 j;

    DQ(NY ny, String str, String str2, String str3) {
        this.b = ny;
        this.c = ny.d(str);
        this.d = ny.d(str2);
        if (str3 != null) {
            this.e = ny.d(str3);
        }
    }

    @Override // com.android.tools.r8.internal.CQ
    public final void a() {
    }

    final int b() {
        int i = 0;
        int iA = T3.a(this.b, 0, this.e) + 6 + C2583n2.a(this.f, this.g, this.h, this.i);
        T3 t3 = this.j;
        if (t3 == null) {
            return iA;
        }
        NY ny = this.b;
        C1549Za c1549Za = ny.a;
        while (t3 != null) {
            ny.d(t3.a);
            i += t3.a().b + 6;
            t3 = t3.c;
        }
        return iA + i;
    }

    @Override // com.android.tools.r8.internal.CQ
    public final AbstractC2509m2 a(String str, boolean z) {
        if (z) {
            C2583n2 c2583n2A = C2583n2.a(this.b, str, this.f);
            this.f = c2583n2A;
            return c2583n2A;
        }
        C2583n2 c2583n2A2 = C2583n2.a(this.b, str, this.g);
        this.g = c2583n2A2;
        return c2583n2A2;
    }

    @Override // com.android.tools.r8.internal.CQ
    public final AbstractC2509m2 a(int i, C00 c00, String str, boolean z) {
        if (z) {
            C2583n2 c2583n2A = C2583n2.a(this.b, i, c00, str, this.h);
            this.h = c2583n2A;
            return c2583n2A;
        }
        C2583n2 c2583n2A2 = C2583n2.a(this.b, i, c00, str, this.i);
        this.i = c2583n2A2;
        return c2583n2A2;
    }

    @Override // com.android.tools.r8.internal.CQ
    public final void a(T3 t3) {
        t3.c = this.j;
        this.j = t3;
    }

    final void a(C1667b6 c1667b6) {
        c1667b6.d(this.c).d(this.d);
        int i = this.e != 0 ? 1 : 0;
        if (this.f != null) {
            i++;
        }
        if (this.g != null) {
            i++;
        }
        if (this.h != null) {
            i++;
        }
        if (this.i != null) {
            i++;
        }
        T3 t3 = this.j;
        if (t3 != null) {
            int i2 = 0;
            while (t3 != null) {
                i2++;
                t3 = t3.c;
            }
            i += i2;
        }
        c1667b6.d(i);
        T3.a(this.b, 0, this.e, c1667b6);
        C2583n2.a(this.b, this.f, this.g, this.h, this.i, c1667b6);
        T3 t4 = this.j;
        if (t4 != null) {
            NY ny = this.b;
            C1549Za c1549Za = ny.a;
            while (t4 != null) {
                C1667b6 c1667b6A = t4.a();
                c1667b6.d(ny.d(t4.a)).c(c1667b6A.b);
                c1667b6.a(c1667b6A.a, 0, c1667b6A.b);
                t4 = t4.c;
            }
        }
    }

    final void a(S3 s3) {
        s3.a(this.j);
    }
}
