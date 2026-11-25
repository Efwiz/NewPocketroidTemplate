package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ji, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2326ji extends AbstractC2252ii {
    private final NY c;
    private final int d;
    private final int e;
    private final int f;
    private int g;
    private int h;
    private C2583n2 i;
    private C2583n2 j;
    private C2583n2 k;
    private C2583n2 l;
    private T3 m;

    C2326ji(NY ny, int i, String str, String str2, String str3, Object obj) {
        super(589824);
        this.c = ny;
        this.d = i;
        this.e = ny.d(str);
        this.f = ny.d(str2);
        if (str3 != null) {
            this.g = ny.d(str3);
        }
        if (obj != null) {
            this.h = ny.a(obj).a;
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC2252ii
    public final void a() {
    }

    final int b() {
        int i;
        if (this.h != 0) {
            this.c.d("ConstantValue");
            i = 16;
        } else {
            i = 8;
        }
        int iA = i + T3.a(this.c, this.d, this.g) + C2583n2.a(this.i, this.j, this.k, this.l);
        T3 t3 = this.m;
        if (t3 == null) {
            return iA;
        }
        NY ny = this.c;
        C1549Za c1549Za = ny.a;
        int i2 = 0;
        while (t3 != null) {
            ny.d(t3.a);
            i2 += t3.a().b + 6;
            t3 = t3.c;
        }
        return iA + i2;
    }

    @Override // com.android.tools.r8.internal.AbstractC2252ii
    public final AbstractC2509m2 a(String str, boolean z) {
        if (z) {
            C2583n2 c2583n2A = C2583n2.a(this.c, str, this.i);
            this.i = c2583n2A;
            return c2583n2A;
        }
        C2583n2 c2583n2A2 = C2583n2.a(this.c, str, this.j);
        this.j = c2583n2A2;
        return c2583n2A2;
    }

    @Override // com.android.tools.r8.internal.AbstractC2252ii
    public final AbstractC2509m2 a(int i, C00 c00, String str, boolean z) {
        if (z) {
            C2583n2 c2583n2A = C2583n2.a(this.c, i, c00, str, this.k);
            this.k = c2583n2A;
            return c2583n2A;
        }
        C2583n2 c2583n2A2 = C2583n2.a(this.c, i, c00, str, this.l);
        this.l = c2583n2A2;
        return c2583n2A2;
    }

    @Override // com.android.tools.r8.internal.AbstractC2252ii
    public final void a(T3 t3) {
        t3.c = this.m;
        this.m = t3;
    }

    final void a(C1667b6 c1667b6) {
        boolean z = this.c.e() < 49;
        c1667b6.d((~(z ? 4096 : 0)) & this.d).d(this.e).d(this.f);
        int i = this.h == 0 ? 0 : 1;
        int i2 = this.d;
        if ((i2 & 4096) != 0 && z) {
            i++;
        }
        if (this.g != 0) {
            i++;
        }
        if ((131072 & i2) != 0) {
            i++;
        }
        if (this.i != null) {
            i++;
        }
        if (this.j != null) {
            i++;
        }
        if (this.k != null) {
            i++;
        }
        if (this.l != null) {
            i++;
        }
        T3 t3 = this.m;
        if (t3 != null) {
            int i3 = 0;
            while (t3 != null) {
                i3++;
                t3 = t3.c;
            }
            i += i3;
        }
        c1667b6.d(i);
        if (this.h != 0) {
            c1667b6.d(this.c.d("ConstantValue")).c(2).d(this.h);
        }
        T3.a(this.c, this.d, this.g, c1667b6);
        C2583n2.a(this.c, this.i, this.j, this.k, this.l, c1667b6);
        T3 t4 = this.m;
        if (t4 != null) {
            NY ny = this.c;
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
        s3.a(this.m);
    }
}
