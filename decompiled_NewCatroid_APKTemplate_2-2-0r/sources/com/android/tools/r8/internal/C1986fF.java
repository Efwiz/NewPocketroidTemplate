package com.android.tools.r8.internal;

import org.eclipse.jdt.internal.compiler.util.Util;

/* JADX INFO: renamed from: com.android.tools.r8.internal.fF, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1986fF extends AbstractC1899eF {
    private static final int[] i0 = {0, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 1, 1, 1, 2, 2, 1, 1, 1, 0, 0, 1, 2, 1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, -1, 0, -1, -1, -1, -1, -1, -2, -1, -2, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -3, -4, -3, -4, -3, -3, -3, -3, -1, -2, 1, 1, 1, 2, 2, 2, 0, -1, -2, -1, -2, -1, -2, -1, -2, -1, -2, -1, -2, -1, -2, -1, -2, -1, -2, -1, -2, 0, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, -2, -1, -2, -1, -2, 0, 1, 0, 1, -1, -1, 0, 0, 1, 1, -1, 0, -1, 0, 0, 0, -3, -1, -1, -3, -3, -1, -1, -1, -1, -1, -1, -2, -2, -2, -2, -2, -2, -2, -2, 0, 1, 0, -1, -1, -1, -2, -1, -2, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, -1, -1, 0, 0, -1, -1, 0, 0};
    private int A;
    private C1667b6 B;
    private C2583n2 C;
    private C2583n2 D;
    private final int E;
    private final int[] F;
    private final int G;
    private C2583n2 H;
    private C2583n2 I;
    private int J;
    private C2583n2[] K;
    private int L;
    private C2583n2[] M;
    private C2583n2 N;
    private C2583n2 O;
    private C1667b6 P;
    private int Q;
    private C1667b6 R;
    private T3 S;
    private final int T;
    private C3263wA U;
    private C3263wA V;
    private C3263wA W;
    private int X;
    private int Y;
    private int Z;
    private int a0;
    private int[] b0;
    private int[] c0;
    private boolean d0;
    private boolean e0;
    private int f0;
    private int g0;
    private int h0;
    private final NY j;
    private final int k;
    private final int l;
    private final String m;
    private final int n;
    private final String o;
    private int p;
    private int q;
    private final C1667b6 r;
    private C2021fk s;
    private C2021fk t;
    private int u;
    private C1667b6 v;
    private int w;
    private C1667b6 x;
    private int y;
    private C1667b6 z;

    C1986fF(NY ny, int i, String str, String str2, String str3, String[] strArr, int i2) {
        super(589824);
        this.r = new C1667b6();
        this.j = ny;
        this.k = "<init>".equals(str) ? 262144 | i : i;
        this.l = ny.d(str);
        this.m = str;
        this.n = ny.d(str2);
        this.o = str2;
        this.G = str3 == null ? 0 : ny.d(str3);
        if (strArr == null || strArr.length <= 0) {
            this.E = 0;
            this.F = null;
        } else {
            int length = strArr.length;
            this.E = length;
            this.F = new int[length];
            for (int i3 = 0; i3 < this.E; i3++) {
                this.F[i3] = ny.a(strArr[i3]).a;
            }
        }
        this.T = i2;
        if (i2 != 0) {
            int iB = CZ.b(str2) >> 2;
            iB = (i & 8) != 0 ? iB - 1 : iB;
            this.q = iB;
            this.Z = iB;
            C3263wA c3263wA = new C3263wA();
            this.U = c3263wA;
            a(c3263wA);
        }
    }

    private void e() {
        int i = this.T;
        if (i != 4) {
            if (i == 1) {
                this.W.i = (short) this.Y;
                this.W = null;
                return;
            }
            return;
        }
        C3263wA c3263wA = new C3263wA();
        c3263wA.k = new C1267Pi(c3263wA);
        C1667b6 c1667b6 = this.r;
        c3263wA.a(c1667b6.b, c1667b6.a);
        this.V.l = c3263wA;
        this.V = c3263wA;
        this.W = null;
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final AbstractC2509m2 a() {
        C1667b6 c1667b6 = new C1667b6();
        this.P = c1667b6;
        return new C2583n2(this.j, false, c1667b6, null);
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void b() {
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void c() {
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void d(int i, int i2) {
        C1667b6 c1667b6 = this.r;
        this.f0 = c1667b6.b;
        if (i2 < 4 && i != 169) {
            c1667b6.b((i < 54 ? ((i - 21) << 2) + 26 : ((i - 54) << 2) + 59) + i2);
        } else if (i2 >= 256) {
            c1667b6.b(196).c(i, i2);
        } else {
            c1667b6.a(i, i2);
        }
        C3263wA c3263wA = this.W;
        if (c3263wA != null) {
            int i3 = this.T;
            if (i3 == 4 || i3 == 3) {
                c3263wA.k.a(i, i2, (MY) null, (NY) null);
            } else if (i == 169) {
                c3263wA.b = (short) (c3263wA.b | 64);
                c3263wA.h = (short) this.X;
                e();
            } else {
                int i4 = this.X + i0[i];
                if (i4 > this.Y) {
                    this.Y = i4;
                }
                this.X = i4;
            }
        }
        int i5 = this.T;
        if (i5 != 0) {
            int i6 = (i == 22 || i == 24 || i == 55 || i == 57) ? i2 + 2 : i2 + 1;
            if (i6 > this.q) {
                this.q = i6;
            }
        }
        if (i < 54 || i5 != 4 || this.s == null) {
            return;
        }
        a(new C3263wA());
    }

    final boolean f() {
        return this.e0;
    }

    final boolean g() {
        return this.A > 0;
    }

    final void h() {
        char c;
        if (this.b0 != null) {
            if (this.B == null) {
                this.B = new C1667b6();
            }
            int[] iArr = this.c0;
            int i = iArr[1];
            int i2 = iArr[2];
            if (this.j.e() < 50) {
                this.B.d(this.c0[0]).d(i);
                int i3 = i + 3;
                e(3, i3);
                this.B.d(i2);
                e(i3, i2 + i3);
            } else {
                int i4 = this.A == 0 ? this.c0[0] : (this.c0[0] - this.b0[0]) - 1;
                int i5 = this.b0[1];
                int i6 = i - i5;
                if (i2 == 0) {
                    switch (i6) {
                        case -3:
                        case -2:
                        case -1:
                            c = 248;
                            break;
                        case 0:
                            c = i4 < 64 ? (char) 0 : (char) 251;
                            break;
                        case 1:
                        case 2:
                        case 3:
                            c = 252;
                            break;
                        default:
                            c = 255;
                            break;
                    }
                } else if (i6 == 0 && i2 == 1) {
                    c = i4 < 63 ? '@' : (char) 247;
                } else {
                    c = 255;
                }
                if (c != 255) {
                    int i7 = 3;
                    for (int i8 = 0; i8 < i5 && i8 < i; i8++) {
                        if (this.c0[i7] != this.b0[i7]) {
                            c = 255;
                        } else {
                            i7++;
                        }
                    }
                }
                if (c == 0) {
                    this.B.b(i4);
                } else if (c == '@') {
                    this.B.b(i4 + 64);
                    e(i + 3, i + 4);
                } else if (c == 247) {
                    this.B.b(247).d(i4);
                    e(i + 3, i + 4);
                } else if (c == 248) {
                    this.B.b(i6 + 251).d(i4);
                } else if (c == 251) {
                    this.B.b(251).d(i4);
                } else if (c != 252) {
                    this.B.b(255).d(i4).d(i);
                    int i9 = i + 3;
                    e(3, i9);
                    this.B.d(i2);
                    e(i9, i2 + i9);
                } else {
                    this.B.b(i6 + 251).d(i4);
                    e(i5 + 3, i + 3);
                }
            }
            this.A++;
        }
        this.b0 = this.c0;
        this.c0 = null;
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void b(int i, String str) {
        if (this.R == null) {
            this.R = new C1667b6();
        }
        this.Q++;
        this.R.d(str == null ? 0 : this.j.d(str)).d(i);
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final AbstractC2509m2 c(int i, C00 c00, String str, boolean z) {
        if (z) {
            C2583n2 c2583n2A = C2583n2.a(this.j, i, c00, str, this.N);
            this.N = c2583n2A;
            return c2583n2A;
        }
        C2583n2 c2583n2A2 = C2583n2.a(this.j, i, c00, str, this.O);
        this.O = c2583n2A2;
        return c2583n2A2;
    }

    final void f(int i, int i2) {
        this.g0 = i + 6;
        this.h0 = i2 - 6;
    }

    final void g(int i, int i2) {
        this.c0[i] = i2;
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final AbstractC2509m2 a(String str, boolean z) {
        if (z) {
            C2583n2 c2583n2A = C2583n2.a(this.j, str, this.H);
            this.H = c2583n2A;
            return c2583n2A;
        }
        C2583n2 c2583n2A2 = C2583n2.a(this.j, str, this.I);
        this.I = c2583n2A2;
        return c2583n2A2;
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void b(int i, int i2) {
        C1667b6 c1667b6 = this.r;
        this.f0 = c1667b6.b;
        if (i == 17) {
            c1667b6.c(i, i2);
        } else {
            c1667b6.a(i, i2);
        }
        C3263wA c3263wA = this.W;
        if (c3263wA != null) {
            int i3 = this.T;
            if (i3 == 4 || i3 == 3) {
                c3263wA.k.a(i, i2, (MY) null, (NY) null);
            } else if (i != 188) {
                int i4 = this.X + 1;
                if (i4 > this.Y) {
                    this.Y = i4;
                }
                this.X = i4;
            }
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(int i, boolean z) {
        if (z) {
            this.J = i;
        } else {
            this.L = i;
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void c(int i, String str) {
        this.f0 = this.r.b;
        MY myA = this.j.a(str);
        this.r.c(i, myA.a);
        C3263wA c3263wA = this.W;
        if (c3263wA != null) {
            int i2 = this.T;
            if (i2 == 4 || i2 == 3) {
                c3263wA.k.a(i, this.f0, myA, this.j);
            } else if (i == 187) {
                int i3 = this.X + 1;
                if (i3 > this.Y) {
                    this.Y = i3;
                }
                this.X = i3;
            }
        }
    }

    private void e(int i, int i2) {
        while (i < i2) {
            NY ny = this.j;
            int i3 = this.c0[i];
            C1667b6 c1667b6 = this.B;
            int i4 = ((-67108864) & i3) >> 26;
            if (i4 == 0) {
                int i5 = i3 & 1048575;
                int i6 = i3 & 62914560;
                if (i6 == 4194304) {
                    c1667b6.b(i5);
                } else if (i6 == 8388608) {
                    c1667b6.b(7).d(ny.a(ny.c(i5).e).a);
                } else if (i6 == 12582912) {
                    c1667b6.b(8).d((int) ny.c(i5).f);
                } else {
                    throw new AssertionError();
                }
            } else {
                StringBuilder sb = new StringBuilder();
                while (true) {
                    int i7 = i4 - 1;
                    if (i4 > 0) {
                        sb.append('[');
                        i4 = i7;
                    } else {
                        if ((i3 & 62914560) == 8388608) {
                            sb.append('L').append(ny.c(i3 & 1048575).e).append(';');
                        } else {
                            int i8 = i3 & 1048575;
                            if (i8 == 1) {
                                sb.append('I');
                            } else if (i8 == 2) {
                                sb.append(Util.C_FLOAT);
                            } else if (i8 == 3) {
                                sb.append(Util.C_DOUBLE);
                            } else if (i8 != 4) {
                                switch (i8) {
                                    case 9:
                                        sb.append('Z');
                                        break;
                                    case 10:
                                        sb.append(Util.C_BYTE);
                                        break;
                                    case 11:
                                        sb.append(Util.C_CHAR);
                                        break;
                                    case 12:
                                        sb.append(Util.C_SHORT);
                                        break;
                                    default:
                                        throw new AssertionError();
                                }
                            } else {
                                sb.append(Util.C_LONG);
                            }
                        }
                        c1667b6.b(7).d(ny.a(sb.toString()).a);
                    }
                }
            }
            i++;
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final AbstractC2509m2 a(int i, String str, boolean z) {
        if (z) {
            if (this.K == null) {
                this.K = new C2583n2[CZ.a(this.o).length];
            }
            C2583n2[] c2583n2Arr = this.K;
            C2583n2 c2583n2A = C2583n2.a(this.j, str, c2583n2Arr[i]);
            c2583n2Arr[i] = c2583n2A;
            return c2583n2A;
        }
        if (this.M == null) {
            this.M = new C2583n2[CZ.a(this.o).length];
        }
        C2583n2[] c2583n2Arr2 = this.M;
        C2583n2 c2583n2A2 = C2583n2.a(this.j, str, c2583n2Arr2[i]);
        c2583n2Arr2[i] = c2583n2A2;
        return c2583n2A2;
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void c(int i, int i2) {
        int i3 = this.T;
        if (i3 == 4) {
            C2021fk c2021fk = this.s;
            while (true) {
                if (c2021fk == null) {
                    break;
                }
                String str = c2021fk.e;
                int iE = this.j.e(str != null ? str : "java/lang/Throwable") | 8388608;
                C3263wA c3263wAA = c2021fk.c.a();
                c3263wAA.b = (short) (c3263wAA.b | 2);
                C3263wA c3263wAA2 = c2021fk.b.a();
                for (C3263wA c3263wAA3 = c2021fk.a.a(); c3263wAA3 != c3263wAA2; c3263wAA3 = c3263wAA3.l) {
                    c3263wAA3.m = new C1235Of(iE, c3263wAA, c3263wAA3.m);
                }
                c2021fk = c2021fk.f;
            }
            C1267Pi c1267Pi = this.U.k;
            c1267Pi.a(this.j, this.k, this.o, this.q);
            c1267Pi.a(this);
            C3263wA c3263wA = this.U;
            c3263wA.n = C3263wA.o;
            int iMax = 0;
            while (c3263wA != C3263wA.o) {
                C3263wA c3263wA2 = c3263wA.n;
                c3263wA.n = null;
                c3263wA.b = (short) (c3263wA.b | 8);
                int iA = c3263wA.k.a() + c3263wA.i;
                if (iA > iMax) {
                    iMax = iA;
                }
                for (C1235Of c1235Of = c3263wA.m; c1235Of != null; c1235Of = c1235Of.c) {
                    C3263wA c3263wAA4 = c1235Of.b.a();
                    if (c3263wA.k.a(this.j, c3263wAA4.k, c1235Of.a) && c3263wAA4.n == null) {
                        c3263wAA4.n = c3263wA2;
                        c3263wA2 = c3263wAA4;
                    }
                }
                c3263wA = c3263wA2;
            }
            for (C3263wA c3263wA3 = this.U; c3263wA3 != null; c3263wA3 = c3263wA3.l) {
                if ((c3263wA3.b & 10) == 10) {
                    c3263wA3.k.a(this);
                }
                if ((c3263wA3.b & 8) == 0) {
                    C3263wA c3263wA4 = c3263wA3.l;
                    int i4 = c3263wA3.e;
                    int i5 = (c3263wA4 == null ? this.r.b : c3263wA4.e) - 1;
                    if (i5 >= i4) {
                        for (int i6 = i4; i6 < i5; i6++) {
                            this.r.a[i6] = 0;
                        }
                        this.r.a[i5] = -65;
                        a(i4, 0, 1);
                        this.c0[3] = this.j.e("java/lang/Throwable") | 8388608;
                        h();
                        this.s = C2021fk.a(this.s, c3263wA3, c3263wA4);
                        iMax = Math.max(iMax, 1);
                    }
                }
            }
            this.p = iMax;
            return;
        }
        if (i3 != 1) {
            if (i3 == 2) {
                this.p = this.Y;
                return;
            } else {
                this.p = i;
                this.q = i2;
                return;
            }
        }
        for (C2021fk c2021fk2 = this.s; c2021fk2 != null; c2021fk2 = c2021fk2.f) {
            C3263wA c3263wA5 = c2021fk2.c;
            C3263wA c3263wA6 = c2021fk2.b;
            for (C3263wA c3263wA7 = c2021fk2.a; c3263wA7 != c3263wA6; c3263wA7 = c3263wA7.l) {
                if ((c3263wA7.b & 16) == 0) {
                    c3263wA7.m = new C1235Of(Integer.MAX_VALUE, c3263wA5, c3263wA7.m);
                } else {
                    C1235Of c1235Of2 = c3263wA7.m.c;
                    c1235Of2.c = new C1235Of(Integer.MAX_VALUE, c3263wA5, c1235Of2.c);
                }
            }
        }
        if (this.d0) {
            this.U.a((short) 1);
            short s = 1;
            for (short s2 = 1; s2 <= s; s2 = (short) (s2 + 1)) {
                for (C3263wA c3263wA8 = this.U; c3263wA8 != null; c3263wA8 = c3263wA8.l) {
                    if ((c3263wA8.b & 16) != 0 && c3263wA8.j == s2) {
                        C3263wA c3263wA9 = c3263wA8.m.c.b;
                        if (c3263wA9.j == 0) {
                            s = (short) (s + 1);
                            c3263wA9.a(s);
                        }
                    }
                }
            }
            for (C3263wA c3263wA10 = this.U; c3263wA10 != null; c3263wA10 = c3263wA10.l) {
                if ((c3263wA10.b & 16) != 0) {
                    c3263wA10.m.c.b.a(c3263wA10);
                }
            }
        }
        C3263wA c3263wA11 = this.U;
        c3263wA11.n = C3263wA.o;
        int i7 = this.p;
        while (c3263wA11 != C3263wA.o) {
            C3263wA c3263wA12 = c3263wA11.n;
            short s3 = c3263wA11.g;
            int i8 = c3263wA11.i + s3;
            if (i8 > i7) {
                i7 = i8;
            }
            C1235Of c1235Of3 = c3263wA11.m;
            if ((c3263wA11.b & 16) != 0) {
                c1235Of3 = c1235Of3.c;
            }
            c3263wA11 = c3263wA12;
            while (c1235Of3 != null) {
                C3263wA c3263wA13 = c1235Of3.b;
                if (c3263wA13.n == null) {
                    int i9 = c1235Of3.a;
                    c3263wA13.g = (short) (i9 == Integer.MAX_VALUE ? 1 : i9 + s3);
                    c3263wA13.n = c3263wA11;
                    c3263wA11 = c3263wA13;
                }
                c1235Of3 = c1235Of3.c;
            }
        }
        this.p = i7;
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(T3 t3) {
        t3.getClass();
        t3.c = this.S;
        this.S = t3;
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final AbstractC2509m2 b(int i, C00 c00, String str, boolean z) {
        if (z) {
            C2583n2 c2583n2A = C2583n2.a(this.j, i, c00, str, this.C);
            this.C = c2583n2A;
            return c2583n2A;
        }
        C2583n2 c2583n2A2 = C2583n2.a(this.j, i, c00, str, this.D);
        this.D = c2583n2A2;
        return c2583n2A2;
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(int i, int i2, Object[] objArr, int i3, Object[] objArr2) {
        int i4;
        int i5 = this.T;
        if (i5 == 4) {
            return;
        }
        int i6 = 3;
        if (i5 == 3) {
            C3263wA c3263wA = this.W;
            C1267Pi c1267Pi = c3263wA.k;
            if (c1267Pi == null) {
                C2935rd c2935rd = new C2935rd(c3263wA);
                c3263wA.k = c2935rd;
                c2935rd.a(this.j, this.k, this.o, i2);
                this.W.k.a(this);
            } else {
                if (i == -1) {
                    c1267Pi.a(this.j, i2, objArr, i3, objArr2);
                }
                this.W.k.a(this);
            }
        } else if (i == -1) {
            if (this.b0 == null) {
                int iB = CZ.b(this.o) >> 2;
                C1267Pi c1267Pi2 = new C1267Pi(new C3263wA());
                c1267Pi2.a(this.j, this.k, this.o, iB);
                c1267Pi2.a(this);
            }
            this.Z = i2;
            a(this.r.b, i2, i3);
            int i7 = 0;
            while (i7 < i2) {
                this.c0[i6] = C1267Pi.a(this.j, objArr[i7]);
                i7++;
                i6++;
            }
            int i8 = 0;
            while (i8 < i3) {
                this.c0[i6] = C1267Pi.a(this.j, objArr2[i8]);
                i8++;
                i6++;
            }
            h();
        } else if (this.j.e() >= 50) {
            if (this.B == null) {
                this.B = new C1667b6();
                i4 = this.r.b;
            } else {
                i4 = (this.r.b - this.a0) - 1;
                if (i4 < 0) {
                    if (i != 3) {
                        throw new IllegalStateException();
                    }
                    return;
                }
            }
            if (i == 0) {
                this.Z = i2;
                this.B.b(255).d(i4).d(i2);
                for (int i9 = 0; i9 < i2; i9++) {
                    b(objArr[i9]);
                }
                this.B.d(i3);
                for (int i10 = 0; i10 < i3; i10++) {
                    b(objArr2[i10]);
                }
            } else if (i == 1) {
                this.Z += i2;
                this.B.b(i2 + 251).d(i4);
                for (int i11 = 0; i11 < i2; i11++) {
                    b(objArr[i11]);
                }
            } else if (i == 2) {
                this.Z -= i2;
                this.B.b(251 - i2).d(i4);
            } else if (i != 3) {
                if (i != 4) {
                    throw new IllegalArgumentException();
                }
                if (i4 < 64) {
                    this.B.b(i4 + 64);
                } else {
                    this.B.b(247).d(i4);
                }
                b(objArr2[0]);
            } else if (i4 < 64) {
                this.B.b(i4);
            } else {
                this.B.b(251).d(i4);
            }
            this.a0 = this.r.b;
            this.A++;
        } else {
            throw new IllegalArgumentException("Class versions V1_5 or less must use F_NEW frames.");
        }
        if (this.T == 2) {
            this.X = i3;
            for (int i12 = 0; i12 < i3; i12++) {
                Object obj = objArr2[i12];
                if (obj == JK.e || obj == JK.d) {
                    this.X++;
                }
            }
            int i13 = this.X;
            if (i13 > this.Y) {
                this.Y = i13;
            }
        }
        this.p = Math.max(this.p, i3);
        this.q = Math.max(this.q, this.Z);
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void b(int i, C3263wA c3263wA) {
        if (this.v == null) {
            this.v = new C1667b6();
        }
        this.u++;
        this.v.d(c3263wA.e);
        this.v.d(i);
    }

    private void b(Object obj) {
        if (obj instanceof Integer) {
            this.B.b(((Integer) obj).intValue());
        } else if (obj instanceof String) {
            this.B.b(7).d(this.j.a((String) obj).a);
        } else {
            this.B.b(8).d(((C3263wA) obj).e);
        }
    }

    final int d() {
        int iB;
        if (this.g0 != 0) {
            return this.h0 + 6;
        }
        int i = this.r.b;
        int i2 = 0;
        if (i <= 0) {
            iB = 8;
        } else if (i <= 65535) {
            this.j.d("Code");
            int i3 = this.r.b + 16;
            int i4 = 0;
            for (C2021fk c2021fk = this.s; c2021fk != null; c2021fk = c2021fk.f) {
                i4++;
            }
            iB = (i4 * 8) + 2 + i3 + 8;
            if (this.B != null) {
                this.j.d(this.j.e() >= 50 ? "StackMapTable" : "StackMap");
                iB += this.B.b + 8;
            }
            if (this.v != null) {
                this.j.d("LineNumberTable");
                iB += this.v.b + 8;
            }
            if (this.x != null) {
                this.j.d("LocalVariableTable");
                iB += this.x.b + 8;
            }
            if (this.z != null) {
                this.j.d("LocalVariableTypeTable");
                iB += this.z.b + 8;
            }
            C2583n2 c2583n2 = this.C;
            if (c2583n2 != null) {
                iB += c2583n2.b("RuntimeVisibleTypeAnnotations");
            }
            C2583n2 c2583n3 = this.D;
            if (c2583n3 != null) {
                iB += c2583n3.b("RuntimeInvisibleTypeAnnotations");
            }
        } else {
            throw new C1828dF(this.r.b, this.j.b(), this.m, this.o);
        }
        if (this.E > 0) {
            this.j.d("Exceptions");
            iB += (this.E * 2) + 8;
        }
        int iA = iB + T3.a(this.j, this.k, this.G) + C2583n2.a(this.H, this.I, this.N, this.O);
        C2583n2[] c2583n2Arr = this.K;
        if (c2583n2Arr != null) {
            int length = this.J;
            if (length == 0) {
                length = c2583n2Arr.length;
            }
            int iB2 = (length * 2) + 7;
            for (int i5 = 0; i5 < length; i5++) {
                C2583n2 c2583n4 = c2583n2Arr[i5];
                iB2 += c2583n4 == null ? 0 : c2583n4.b("RuntimeVisibleParameterAnnotations") - 8;
            }
            iA += iB2;
        }
        C2583n2[] c2583n2Arr2 = this.M;
        if (c2583n2Arr2 != null) {
            int length2 = this.L;
            if (length2 == 0) {
                length2 = c2583n2Arr2.length;
            }
            int iB3 = (length2 * 2) + 7;
            for (int i6 = 0; i6 < length2; i6++) {
                C2583n2 c2583n5 = c2583n2Arr2[i6];
                iB3 += c2583n5 == null ? 0 : c2583n5.b("RuntimeInvisibleParameterAnnotations") - 8;
            }
            iA += iB3;
        }
        if (this.P != null) {
            this.j.d("AnnotationDefault");
            iA += this.P.b + 6;
        }
        if (this.R != null) {
            this.j.d("MethodParameters");
            iA += this.R.b + 7;
        }
        T3 t3 = this.S;
        if (t3 == null) {
            return iA;
        }
        NY ny = this.j;
        C1549Za c1549Za = ny.a;
        while (t3 != null) {
            ny.d(t3.a);
            i2 += t3.a().b + 6;
            t3 = t3.c;
        }
        return iA + i2;
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(int i) {
        C1667b6 c1667b6 = this.r;
        this.f0 = c1667b6.b;
        c1667b6.b(i);
        C3263wA c3263wA = this.W;
        if (c3263wA != null) {
            int i2 = this.T;
            if (i2 != 4 && i2 != 3) {
                int i3 = this.X + i0[i];
                if (i3 > this.Y) {
                    this.Y = i3;
                }
                this.X = i3;
            } else {
                c3263wA.k.a(i, 0, (MY) null, (NY) null);
            }
            if ((i < 172 || i > 177) && i != 191) {
                return;
            }
            e();
        }
    }

    private void c(int i, C3263wA c3263wA) {
        C3263wA c3263wA2 = this.W;
        c3263wA2.m = new C1235Of(i, c3263wA, c3263wA2.m);
    }

    /* JADX WARN: Code duplicated, block: B:28:0x004a A[PHI: r4
      0x004a: PHI (r4v3 int) = (r4v1 int), (r4v4 int), (r4v4 int) binds: [B:26:0x0047, B:16:0x0035, B:17:0x0037] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:30:0x004e A[PHI: r4
      0x004e: PHI (r4v7 int) = (r4v2 int), (r4v2 int), (r4v8 int) binds: [B:20:0x003c, B:21:0x003e, B:13:0x0030] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(int i, String str, String str2, String str3) {
        int i2;
        this.f0 = this.r.b;
        MY myA = this.j.a(str, str2, str3);
        this.r.c(i, myA.a);
        C3263wA c3263wA = this.W;
        if (c3263wA != null) {
            int i3 = this.T;
            int i4 = 0;
            if (i3 != 4 && i3 != 3) {
                char cCharAt = str3.charAt(0);
                switch (i) {
                    case 178:
                        i2 = this.X;
                        if (cCharAt == 'D' || cCharAt == 'J') {
                            i4 = 2;
                        } else {
                            i4 = 1;
                        }
                        break;
                    case 179:
                        i2 = this.X;
                        if (cCharAt == 'D' || cCharAt == 'J') {
                            i4 = -2;
                        } else {
                            i4 = -1;
                        }
                        break;
                    case 180:
                        i2 = this.X;
                        if (cCharAt == 'D' || cCharAt == 'J') {
                            i4 = 1;
                        }
                        break;
                    default:
                        i2 = this.X;
                        if (cCharAt == 'D' || cCharAt == 'J') {
                            i4 = -3;
                        } else {
                            i4 = -2;
                        }
                        break;
                }
                int i5 = i2 + i4;
                if (i5 > this.Y) {
                    this.Y = i5;
                }
                this.X = i5;
                return;
            }
            c3263wA.k.a(i, 0, myA, this.j);
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(int i, String str, String str2, String str3, boolean z) {
        int i2;
        this.f0 = this.r.b;
        MY myA = this.j.a(str, str2, str3, z);
        if (i == 185) {
            C1667b6 c1667b6C = this.r.c(185, myA.a);
            if (myA.g == 0) {
                myA.g = CZ.b(myA.e);
            }
            c1667b6C.a(myA.g >> 2, 0);
        } else {
            this.r.c(i, myA.a);
        }
        C3263wA c3263wA = this.W;
        if (c3263wA != null) {
            int i3 = this.T;
            if (i3 != 4 && i3 != 3) {
                if (myA.g == 0) {
                    myA.g = CZ.b(myA.e);
                }
                int i4 = myA.g;
                int i5 = (i4 & 3) - (i4 >> 2);
                if (i == 184) {
                    i2 = this.X + i5 + 1;
                } else {
                    i2 = this.X + i5;
                }
                if (i2 > this.Y) {
                    this.Y = i2;
                }
                this.X = i2;
                return;
            }
            c3263wA.k.a(i, 0, myA, this.j);
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(String str, String str2, C1944ek c1944ek, Object... objArr) {
        this.f0 = this.r.b;
        MY myA = this.j.a(str, str2, c1944ek, objArr);
        this.r.c(186, myA.a);
        this.r.d(0);
        C3263wA c3263wA = this.W;
        if (c3263wA != null) {
            int i = this.T;
            if (i != 4 && i != 3) {
                if (myA.g == 0) {
                    myA.g = CZ.b(myA.e);
                }
                int i2 = myA.g;
                int i3 = this.X + ((i2 & 3) - (i2 >> 2)) + 1;
                if (i3 > this.Y) {
                    this.Y = i3;
                }
                this.X = i3;
                return;
            }
            c3263wA.k.a(186, 0, myA, this.j);
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(int i, C3263wA c3263wA) {
        boolean z;
        C1667b6 c1667b6 = this.r;
        int i2 = c1667b6.b;
        this.f0 = i2;
        int i3 = i >= 200 ? i - 33 : i;
        if ((c3263wA.b & 4) == 0 || c3263wA.e - i2 >= -32768) {
            if (i3 != i) {
                c1667b6.b(i);
                C1667b6 c1667b7 = this.r;
                c3263wA.a(c1667b7, c1667b7.b - 1, true);
            } else {
                c1667b6.b(i3);
                C1667b6 c1667b8 = this.r;
                c3263wA.a(c1667b8, c1667b8.b - 1, false);
            }
            z = false;
        } else {
            if (i3 == 167) {
                c1667b6.b(200);
            } else {
                if (i3 == 168) {
                    c1667b6.b(201);
                } else {
                    c1667b6.b(i3 >= 198 ? i3 ^ 1 : ((i3 + 1) ^ 1) - 1);
                    this.r.d(8);
                    this.r.b(220);
                    this.e0 = true;
                    z = true;
                }
                C1667b6 c1667b9 = this.r;
                c3263wA.a(c1667b9, c1667b9.b - 1, true);
            }
            z = false;
            C1667b6 c1667b10 = this.r;
            c3263wA.a(c1667b10, c1667b10.b - 1, true);
        }
        C3263wA c3263wA2 = this.W;
        if (c3263wA2 != null) {
            int i4 = this.T;
            C3263wA c3263wA3 = null;
            if (i4 == 4) {
                c3263wA2.k.a(i3, 0, (MY) null, (NY) null);
                C3263wA c3263wAA = c3263wA.a();
                c3263wAA.b = (short) (c3263wAA.b | 2);
                c(0, c3263wA);
                if (i3 != 167) {
                    c3263wA3 = new C3263wA();
                }
            } else if (i4 == 3) {
                c3263wA2.k.a(i3, 0, (MY) null, (NY) null);
            } else if (i4 == 2) {
                this.X += i0[i3];
            } else if (i3 == 168) {
                short s = c3263wA.b;
                if ((s & 32) == 0) {
                    c3263wA.b = (short) (s | 32);
                    this.d0 = true;
                }
                c3263wA2.b = (short) (c3263wA2.b | 16);
                c(this.X + 1, c3263wA);
                c3263wA3 = new C3263wA();
            } else {
                int i5 = this.X + i0[i3];
                this.X = i5;
                c(i5, c3263wA);
            }
            if (c3263wA3 != null) {
                if (z) {
                    c3263wA3.b = (short) (c3263wA3.b | 2);
                }
                a(c3263wA3);
            }
            if (i3 == 167) {
                e();
            }
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(C3263wA c3263wA) {
        boolean z = this.e0;
        C1667b6 c1667b6 = this.r;
        this.e0 = z | c3263wA.a(c1667b6.b, c1667b6.a);
        short s = c3263wA.b;
        if ((s & 1) != 0) {
            return;
        }
        int i = this.T;
        if (i == 4) {
            C3263wA c3263wA2 = this.W;
            if (c3263wA2 != null) {
                if (c3263wA.e == c3263wA2.e) {
                    c3263wA2.b = (short) ((s & 2) | c3263wA2.b);
                    c3263wA.k = c3263wA2.k;
                    return;
                }
                c(0, c3263wA);
            }
            C3263wA c3263wA3 = this.V;
            if (c3263wA3 != null) {
                if (c3263wA.e == c3263wA3.e) {
                    c3263wA3.b = (short) (c3263wA3.b | (c3263wA.b & 2));
                    c3263wA.k = c3263wA3.k;
                    this.W = c3263wA3;
                    return;
                }
                c3263wA3.l = c3263wA;
            }
            this.V = c3263wA;
            this.W = c3263wA;
            c3263wA.k = new C1267Pi(c3263wA);
            return;
        }
        if (i == 3) {
            C3263wA c3263wA4 = this.W;
            if (c3263wA4 == null) {
                this.W = c3263wA;
                return;
            } else {
                c3263wA4.k.a = c3263wA;
                return;
            }
        }
        if (i == 1) {
            C3263wA c3263wA5 = this.W;
            if (c3263wA5 != null) {
                c3263wA5.i = (short) this.Y;
                c(this.X, c3263wA);
            }
            this.W = c3263wA;
            this.X = 0;
            this.Y = 0;
            C3263wA c3263wA6 = this.V;
            if (c3263wA6 != null) {
                c3263wA6.l = c3263wA;
            }
            this.V = c3263wA;
            return;
        }
        if (i == 2 && this.W == null) {
            this.W = c3263wA;
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(Object obj) {
        char cCharAt;
        this.f0 = this.r.b;
        MY myA = this.j.a(obj);
        int i = myA.a;
        int i2 = myA.b;
        boolean z = i2 == 5 || i2 == 6 || (i2 == 17 && ((cCharAt = myA.e.charAt(0)) == 'J' || cCharAt == 'D'));
        if (z) {
            this.r.c(20, i);
        } else if (i >= 256) {
            this.r.c(19, i);
        } else {
            this.r.a(18, i);
        }
        C3263wA c3263wA = this.W;
        if (c3263wA != null) {
            int i3 = this.T;
            if (i3 != 4 && i3 != 3) {
                int i4 = this.X + (z ? 2 : 1);
                if (i4 > this.Y) {
                    this.Y = i4;
                }
                this.X = i4;
                return;
            }
            c3263wA.k.a(18, 0, myA, this.j);
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(int i, int i2) {
        int i3;
        int i4;
        C1667b6 c1667b6 = this.r;
        this.f0 = c1667b6.b;
        if (i <= 255 && i2 <= 127 && i2 >= -128) {
            c1667b6.b(132).a(i, i2);
        } else {
            c1667b6.b(196).c(132, i).d(i2);
        }
        C3263wA c3263wA = this.W;
        if (c3263wA != null && ((i4 = this.T) == 4 || i4 == 3)) {
            c3263wA.k.a(132, i, (MY) null, (NY) null);
        }
        if (this.T == 0 || (i3 = i + 1) <= this.q) {
            return;
        }
        this.q = i3;
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(int i, int i2, C3263wA c3263wA, C3263wA... c3263wAArr) {
        C1667b6 c1667b6 = this.r;
        this.f0 = c1667b6.b;
        c1667b6.b(170).a((byte[]) null, 0, (4 - (this.r.b % 4)) % 4);
        c3263wA.a(this.r, this.f0, true);
        this.r.c(i).c(i2);
        for (C3263wA c3263wA2 : c3263wAArr) {
            c3263wA2.a(this.r, this.f0, true);
        }
        a(c3263wA, c3263wAArr);
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(C3263wA c3263wA, int[] iArr, C3263wA[] c3263wAArr) {
        C1667b6 c1667b6 = this.r;
        this.f0 = c1667b6.b;
        c1667b6.b(171).a((byte[]) null, 0, (4 - (this.r.b % 4)) % 4);
        c3263wA.a(this.r, this.f0, true);
        this.r.c(c3263wAArr.length);
        for (int i = 0; i < c3263wAArr.length; i++) {
            this.r.c(iArr[i]);
            c3263wAArr[i].a(this.r, this.f0, true);
        }
        a(c3263wA, c3263wAArr);
    }

    private void a(C3263wA c3263wA, C3263wA[] c3263wAArr) {
        C3263wA c3263wA2 = this.W;
        if (c3263wA2 != null) {
            int i = this.T;
            if (i == 4) {
                c3263wA2.k.a(171, 0, (MY) null, (NY) null);
                c(0, c3263wA);
                C3263wA c3263wAA = c3263wA.a();
                c3263wAA.b = (short) (c3263wAA.b | 2);
                for (C3263wA c3263wA3 : c3263wAArr) {
                    c(0, c3263wA3);
                    C3263wA c3263wAA2 = c3263wA3.a();
                    c3263wAA2.b = (short) (c3263wAA2.b | 2);
                }
            } else if (i == 1) {
                int i2 = this.X - 1;
                this.X = i2;
                c(i2, c3263wA);
                for (C3263wA c3263wA4 : c3263wAArr) {
                    c(this.X, c3263wA4);
                }
            }
            e();
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(int i, String str) {
        this.f0 = this.r.b;
        MY myA = this.j.a(str);
        this.r.c(197, myA.a).b(i);
        C3263wA c3263wA = this.W;
        if (c3263wA != null) {
            int i2 = this.T;
            if (i2 != 4 && i2 != 3) {
                this.X = (1 - i) + this.X;
            } else {
                c3263wA.k.a(197, i, myA, this.j);
            }
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final AbstractC2509m2 a(int i, C00 c00, String str, boolean z) {
        if (z) {
            C2583n2 c2583n2A = C2583n2.a(this.j, (i & (-16776961)) | (this.f0 << 8), c00, str, this.C);
            this.C = c2583n2A;
            return c2583n2A;
        }
        C2583n2 c2583n2A2 = C2583n2.a(this.j, (i & (-16776961)) | (this.f0 << 8), c00, str, this.D);
        this.D = c2583n2A2;
        return c2583n2A2;
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(C3263wA c3263wA, C3263wA c3263wA2, C3263wA c3263wA3, String str) {
        C2021fk c2021fk = new C2021fk(c3263wA, c3263wA2, c3263wA3, str != null ? this.j.a(str).a : 0, str);
        if (this.s == null) {
            this.s = c2021fk;
        } else {
            this.t.f = c2021fk;
        }
        this.t = c2021fk;
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(String str, String str2, String str3, C3263wA c3263wA, C3263wA c3263wA2, int i) {
        if (str3 != null) {
            if (this.z == null) {
                this.z = new C1667b6();
            }
            this.y++;
            this.z.d(c3263wA.e).d(c3263wA2.e - c3263wA.e).d(this.j.d(str)).d(this.j.d(str3)).d(i);
        }
        if (this.x == null) {
            this.x = new C1667b6();
        }
        this.w++;
        this.x.d(c3263wA.e).d(c3263wA2.e - c3263wA.e).d(this.j.d(str)).d(this.j.d(str2)).d(i);
        if (this.T != 0) {
            char cCharAt = str2.charAt(0);
            int i2 = i + ((cCharAt == 'J' || cCharAt == 'D') ? 2 : 1);
            if (i2 > this.q) {
                this.q = i2;
            }
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final AbstractC2509m2 a(int i, C00 c00, C3263wA[] c3263wAArr, C3263wA[] c3263wAArr2, int[] iArr, String str, boolean z) {
        C1667b6 c1667b6 = new C1667b6();
        c1667b6.b(i >>> 24).d(c3263wAArr.length);
        for (int i2 = 0; i2 < c3263wAArr.length; i2++) {
            c1667b6.d(c3263wAArr[i2].e).d(c3263wAArr2[i2].e - c3263wAArr[i2].e).d(iArr[i2]);
        }
        C00.a(c00, c1667b6);
        c1667b6.d(this.j.d(str)).d(0);
        if (z) {
            C2583n2 c2583n2 = new C2583n2(this.j, true, c1667b6, this.C);
            this.C = c2583n2;
            return c2583n2;
        }
        C2583n2 c2583n3 = new C2583n2(this.j, true, c1667b6, this.D);
        this.D = c2583n3;
        return c2583n3;
    }

    final void a(int i, int i2, int i3) {
        int i4 = i2 + 3 + i3;
        int[] iArr = this.c0;
        if (iArr == null || iArr.length < i4) {
            this.c0 = new int[i4];
        }
        int[] iArr2 = this.c0;
        iArr2[0] = i;
        iArr2[1] = i2;
        iArr2[2] = i3;
    }

    final boolean a(C1031Ha c1031Ha, boolean z, boolean z2, int i, int i2, int i3) {
        if (c1031Ha == this.j.f() && i == this.n && i2 == this.G) {
            if (z2 == ((this.k & 131072) != 0)) {
                if (z != (this.j.e() < 49 && (this.k & 4096) != 0)) {
                    return false;
                }
                if (i3 == 0) {
                    if (this.E != 0) {
                        return false;
                    }
                } else if (c1031Ha.g(i3) == this.E) {
                    int i4 = i3 + 2;
                    for (int i5 = 0; i5 < this.E; i5++) {
                        if (c1031Ha.g(i4) != this.F[i5]) {
                            return false;
                        }
                        i4 += 2;
                    }
                }
                return true;
            }
        }
        return false;
    }

    final void a(C1667b6 c1667b6) {
        int i;
        boolean z = this.j.e() < 49;
        c1667b6.d((~(z ? 4096 : 0)) & this.k).d(this.l).d(this.n);
        if (this.g0 != 0) {
            c1667b6.a(this.j.f().b, this.g0, this.h0);
            return;
        }
        int i2 = this.r.b > 0 ? 1 : 0;
        if (this.E > 0) {
            i2++;
        }
        int i3 = this.k;
        if ((i3 & 4096) != 0 && z) {
            i2++;
        }
        if (this.G != 0) {
            i2++;
        }
        if ((131072 & i3) != 0) {
            i2++;
        }
        if (this.H != null) {
            i2++;
        }
        if (this.I != null) {
            i2++;
        }
        if (this.K != null) {
            i2++;
        }
        if (this.M != null) {
            i2++;
        }
        if (this.N != null) {
            i2++;
        }
        if (this.O != null) {
            i2++;
        }
        if (this.P != null) {
            i2++;
        }
        if (this.R != null) {
            i2++;
        }
        T3 t3 = this.S;
        if (t3 != null) {
            int i4 = 0;
            while (t3 != null) {
                i4++;
                t3 = t3.c;
            }
            i2 += i4;
        }
        c1667b6.d(i2);
        int i5 = this.r.b;
        if (i5 > 0) {
            int i6 = i5 + 10;
            int i7 = 0;
            for (C2021fk c2021fk = this.s; c2021fk != null; c2021fk = c2021fk.f) {
                i7++;
            }
            int iB = (i7 * 8) + 2 + i6;
            C1667b6 c1667b7 = this.B;
            if (c1667b7 != null) {
                iB += c1667b7.b + 8;
                i = 1;
            } else {
                i = 0;
            }
            C1667b6 c1667b8 = this.v;
            if (c1667b8 != null) {
                iB += c1667b8.b + 8;
                i++;
            }
            C1667b6 c1667b9 = this.x;
            if (c1667b9 != null) {
                iB += c1667b9.b + 8;
                i++;
            }
            C1667b6 c1667b10 = this.z;
            if (c1667b10 != null) {
                iB += c1667b10.b + 8;
                i++;
            }
            C2583n2 c2583n2 = this.C;
            if (c2583n2 != null) {
                iB += c2583n2.b("RuntimeVisibleTypeAnnotations");
                i++;
            }
            C2583n2 c2583n3 = this.D;
            if (c2583n3 != null) {
                iB += c2583n3.b("RuntimeInvisibleTypeAnnotations");
                i++;
            }
            C1667b6 c1667b6C = c1667b6.d(this.j.d("Code")).c(iB).d(this.p).d(this.q).c(this.r.b);
            C1667b6 c1667b11 = this.r;
            c1667b6C.a(c1667b11.a, 0, c1667b11.b);
            C2021fk c2021fk2 = this.s;
            int i8 = 0;
            for (C2021fk c2021fk3 = c2021fk2; c2021fk3 != null; c2021fk3 = c2021fk3.f) {
                i8++;
            }
            c1667b6.d(i8);
            while (c2021fk2 != null) {
                c1667b6.d(c2021fk2.a.e).d(c2021fk2.b.e).d(c2021fk2.c.e).d(c2021fk2.d);
                c2021fk2 = c2021fk2.f;
            }
            c1667b6.d(i);
            if (this.B != null) {
                C1667b6 c1667b6D = c1667b6.d(this.j.d(this.j.e() >= 50 ? "StackMapTable" : "StackMap")).c(this.B.b + 2).d(this.A);
                C1667b6 c1667b12 = this.B;
                c1667b6D.a(c1667b12.a, 0, c1667b12.b);
            }
            if (this.v != null) {
                C1667b6 c1667b6D2 = c1667b6.d(this.j.d("LineNumberTable")).c(this.v.b + 2).d(this.u);
                C1667b6 c1667b13 = this.v;
                c1667b6D2.a(c1667b13.a, 0, c1667b13.b);
            }
            if (this.x != null) {
                C1667b6 c1667b6D3 = c1667b6.d(this.j.d("LocalVariableTable")).c(this.x.b + 2).d(this.w);
                C1667b6 c1667b14 = this.x;
                c1667b6D3.a(c1667b14.a, 0, c1667b14.b);
            }
            if (this.z != null) {
                C1667b6 c1667b6D4 = c1667b6.d(this.j.d("LocalVariableTypeTable")).c(this.z.b + 2).d(this.y);
                C1667b6 c1667b15 = this.z;
                c1667b6D4.a(c1667b15.a, 0, c1667b15.b);
            }
            C2583n2 c2583n4 = this.C;
            if (c2583n4 != null) {
                c2583n4.a(this.j.d("RuntimeVisibleTypeAnnotations"), c1667b6);
            }
            C2583n2 c2583n5 = this.D;
            if (c2583n5 != null) {
                c2583n5.a(this.j.d("RuntimeInvisibleTypeAnnotations"), c1667b6);
            }
        }
        if (this.E > 0) {
            c1667b6.d(this.j.d("Exceptions")).c((this.E * 2) + 2).d(this.E);
            for (int i9 : this.F) {
                c1667b6.d(i9);
            }
        }
        T3.a(this.j, this.k, this.G, c1667b6);
        C2583n2.a(this.j, this.H, this.I, this.N, this.O, c1667b6);
        if (this.K != null) {
            int iD = this.j.d("RuntimeVisibleParameterAnnotations");
            C2583n2[] c2583n2Arr = this.K;
            int length = this.J;
            if (length == 0) {
                length = c2583n2Arr.length;
            }
            C2583n2.a(iD, c2583n2Arr, length, c1667b6);
        }
        if (this.M != null) {
            int iD2 = this.j.d("RuntimeInvisibleParameterAnnotations");
            C2583n2[] c2583n2Arr2 = this.M;
            int length2 = this.L;
            if (length2 == 0) {
                length2 = c2583n2Arr2.length;
            }
            C2583n2.a(iD2, c2583n2Arr2, length2, c1667b6);
        }
        if (this.P != null) {
            C1667b6 c1667b6C2 = c1667b6.d(this.j.d("AnnotationDefault")).c(this.P.b);
            C1667b6 c1667b16 = this.P;
            c1667b6C2.a(c1667b16.a, 0, c1667b16.b);
        }
        if (this.R != null) {
            C1667b6 c1667b6B = c1667b6.d(this.j.d("MethodParameters")).c(this.R.b + 1).b(this.Q);
            C1667b6 c1667b17 = this.R;
            c1667b6B.a(c1667b17.a, 0, c1667b17.b);
        }
        T3 t4 = this.S;
        if (t4 != null) {
            NY ny = this.j;
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
        s3.a(this.S);
        s3.a(null);
    }
}
