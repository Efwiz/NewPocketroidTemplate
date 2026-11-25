package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.vF, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3193vF extends AbstractC3116uF {
    private final NY b;
    private final int c;
    private final int d;
    private final int e;
    private int f;
    private int h;
    private int j;
    private int l;
    private int n;
    private int p;
    private int r;
    private final C1667b6 g = new C1667b6();
    private final C1667b6 i = new C1667b6();
    private final C1667b6 k = new C1667b6();
    private final C1667b6 m = new C1667b6();
    private final C1667b6 o = new C1667b6();
    private final C1667b6 q = new C1667b6();

    C3193vF(NY ny, int i, int i2, int i3) {
        this.b = ny;
        this.c = i;
        this.d = i2;
        this.e = i3;
    }

    @Override // com.android.tools.r8.internal.AbstractC3116uF
    public final void a() {
    }

    @Override // com.android.tools.r8.internal.AbstractC3116uF
    public final void b(String str) {
        this.q.d(this.b.c(str).a);
        this.p++;
    }

    @Override // com.android.tools.r8.internal.AbstractC3116uF
    public final void c(String str) {
        this.m.d(this.b.a(str).a);
        this.l++;
    }

    @Override // com.android.tools.r8.internal.AbstractC3116uF
    public final void a(String str) {
        this.r = this.b.a(str).a;
    }

    @Override // com.android.tools.r8.internal.AbstractC3116uF
    public final void a(int i, String str, String str2) {
        this.g.d(this.b.b(str).a).d(i).d(str2 == null ? 0 : this.b.d(str2));
        this.f++;
    }

    @Override // com.android.tools.r8.internal.AbstractC3116uF
    public final void b(String str, int i, String... strArr) {
        this.k.d(this.b.c(str).a).d(i);
        if (strArr == null) {
            this.k.d(0);
        } else {
            this.k.d(strArr.length);
            for (String str2 : strArr) {
                this.k.d(this.b.b(str2).a);
            }
        }
        this.j++;
    }

    final int c() {
        return (this.p > 0 ? 1 : 0) + 1 + (this.r > 0 ? 1 : 0);
    }

    @Override // com.android.tools.r8.internal.AbstractC3116uF
    public final void a(String str, int i, String... strArr) {
        this.i.d(this.b.c(str).a).d(i);
        if (strArr == null) {
            this.i.d(0);
        } else {
            this.i.d(strArr.length);
            for (String str2 : strArr) {
                this.i.d(this.b.b(str2).a);
            }
        }
        this.h++;
    }

    final int b() {
        this.b.d("Module");
        int i = this.g.b + 22 + this.i.b + this.k.b + this.m.b + this.o.b;
        if (this.p > 0) {
            this.b.d("ModulePackages");
            i += this.q.b + 8;
        }
        if (this.r <= 0) {
            return i;
        }
        this.b.d("ModuleMainClass");
        return i + 8;
    }

    @Override // com.android.tools.r8.internal.AbstractC3116uF
    public final void a(String str, String... strArr) {
        this.o.d(this.b.a(str).a);
        this.o.d(strArr.length);
        for (String str2 : strArr) {
            this.o.d(this.b.a(str2).a);
        }
        this.n++;
    }

    final void a(C1667b6 c1667b6) {
        C1667b6 c1667b6D = c1667b6.d(this.b.d("Module")).c(this.g.b + 16 + this.i.b + this.k.b + this.m.b + this.o.b).d(this.c).d(this.d).d(this.e).d(this.f);
        C1667b6 c1667b7 = this.g;
        C1667b6 c1667b6D2 = c1667b6D.a(c1667b7.a, 0, c1667b7.b).d(this.h);
        C1667b6 c1667b8 = this.i;
        C1667b6 c1667b6D3 = c1667b6D2.a(c1667b8.a, 0, c1667b8.b).d(this.j);
        C1667b6 c1667b9 = this.k;
        C1667b6 c1667b6D4 = c1667b6D3.a(c1667b9.a, 0, c1667b9.b).d(this.l);
        C1667b6 c1667b10 = this.m;
        C1667b6 c1667b6D5 = c1667b6D4.a(c1667b10.a, 0, c1667b10.b).d(this.n);
        C1667b6 c1667b11 = this.o;
        c1667b6D5.a(c1667b11.a, 0, c1667b11.b);
        if (this.p > 0) {
            C1667b6 c1667b6D6 = c1667b6.d(this.b.d("ModulePackages")).c(this.q.b + 2).d(this.p);
            C1667b6 c1667b12 = this.q;
            c1667b6D6.a(c1667b12.a, 0, c1667b12.b);
        }
        if (this.r > 0) {
            c1667b6.d(this.b.d("ModuleMainClass")).c(2).d(this.r);
        }
    }
}
