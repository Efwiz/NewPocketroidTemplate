package com.android.tools.r8.internal;

import com.android.dx.cf.attrib.AttSourceDebugExtension;
import com.android.dx.cf.attrib.AttSynthetic;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Za, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1549Za extends AbstractC1256Pa {
    private int A;
    private int B;
    private C1667b6 C;
    private int D;
    private C1667b6 E;
    private DQ F;
    private DQ G;
    private T3 H;
    private int I;
    private final int c;
    private int d;
    private final NY e;
    private int f;
    private int g;
    private int h;
    private int i;
    private int[] j;
    private C2326ji k;
    private C2326ji l;
    private C1986fF m;
    private C1986fF n;
    private int o;
    private C1667b6 p;
    private int q;
    private int r;
    private int s;
    private int t;
    private C1667b6 u;
    private C2583n2 v;
    private C2583n2 w;
    private C2583n2 x;
    private C2583n2 y;
    private C3193vF z;

    public C1549Za(int i) {
        super(589824);
        this.c = 0;
        this.e = new NY(this);
        this.I = 0;
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final void a() {
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final void b(String str) {
        if (this.C == null) {
            this.C = new C1667b6();
        }
        this.B++;
        this.C.d(this.e.a(str).a);
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final void c(String str) {
        if (this.E == null) {
            this.E = new C1667b6();
        }
        this.D++;
        this.E.d(this.e.a(str).a);
    }

    public final boolean a(int i) {
        return (this.c & i) == i;
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final void a(int i, int i2, String str, String str2, String str3, String[] strArr) {
        this.d = i;
        this.f = i2;
        int i3 = i & 65535;
        this.g = this.e.c(i3, str);
        if (str2 != null) {
            this.s = this.e.d(str2);
        }
        this.h = str3 == null ? 0 : this.e.a(str3).a;
        if (strArr != null && strArr.length > 0) {
            int length = strArr.length;
            this.i = length;
            this.j = new int[length];
            for (int i4 = 0; i4 < this.i; i4++) {
                this.j[i4] = this.e.a(strArr[i4]).a;
            }
        }
        if (this.I != 1 || i3 < 51) {
            return;
        }
        this.I = 2;
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final CQ b(String str, String str2, String str3) {
        DQ dq = new DQ(this.e, str, str2, str3);
        if (this.F == null) {
            this.F = dq;
        } else {
            this.G.a = dq;
        }
        this.G = dq;
        return dq;
    }

    public final byte[] b() {
        int iC;
        int i;
        int iB;
        int i2;
        String str;
        int i3;
        int i4;
        int iB2 = (this.i * 2) + 24;
        int i5 = 0;
        for (C2326ji c2326ji = this.k; c2326ji != null; c2326ji = (C2326ji) c2326ji.b) {
            i5++;
            iB2 += c2326ji.b();
        }
        int i6 = 0;
        for (C1986fF c1986fF = this.m; c1986fF != null; c1986fF = (C1986fF) c1986fF.i) {
            i6++;
            iB2 += c1986fF.d();
        }
        C1667b6 c1667b6 = this.p;
        if (c1667b6 != null) {
            iB2 += c1667b6.b + 8;
            this.e.d("InnerClasses");
            iC = 1;
        } else {
            iC = 0;
        }
        if (this.q != 0) {
            iC++;
            iB2 += 10;
            this.e.d("EnclosingMethod");
        }
        if ((this.f & 4096) != 0 && (this.d & 65535) < 49) {
            iC++;
            iB2 += 6;
            this.e.d(AttSynthetic.ATTRIBUTE_NAME);
        }
        if (this.s != 0) {
            iC++;
            iB2 += 8;
            this.e.d("Signature");
        }
        if (this.t != 0) {
            iC++;
            iB2 += 8;
            this.e.d("SourceFile");
        }
        C1667b6 c1667b7 = this.u;
        if (c1667b7 != null) {
            iC++;
            iB2 += c1667b7.b + 6;
            this.e.d(AttSourceDebugExtension.ATTRIBUTE_NAME);
        }
        if ((this.f & 131072) != 0) {
            iC++;
            iB2 += 6;
            this.e.d("Deprecated");
        }
        C2583n2 c2583n2 = this.v;
        if (c2583n2 != null) {
            iC++;
            iB2 += c2583n2.b("RuntimeVisibleAnnotations");
        }
        C2583n2 c2583n3 = this.w;
        if (c2583n3 != null) {
            iC++;
            iB2 += c2583n3.b("RuntimeInvisibleAnnotations");
        }
        C2583n2 c2583n4 = this.x;
        if (c2583n4 != null) {
            iC++;
            iB2 += c2583n4.b("RuntimeVisibleTypeAnnotations");
        }
        C2583n2 c2583n5 = this.y;
        if (c2583n5 != null) {
            iC++;
            iB2 += c2583n5.b("RuntimeInvisibleTypeAnnotations");
        }
        if (this.e.a() > 0) {
            iC++;
            iB2 += this.e.a();
        }
        C3193vF c3193vF = this.z;
        if (c3193vF != null) {
            iC += c3193vF.c();
            iB2 += this.z.b();
        }
        if (this.A != 0) {
            iC++;
            iB2 += 8;
            this.e.d("NestHost");
        }
        C1667b6 c1667b8 = this.C;
        if (c1667b8 != null) {
            iC++;
            iB2 += c1667b8.b + 8;
            this.e.d("NestMembers");
        }
        C1667b6 c1667b9 = this.E;
        if (c1667b9 != null) {
            iC++;
            i = 8;
            iB2 += c1667b9.b + 8;
            this.e.d("PermittedSubclasses");
        } else {
            i = 8;
        }
        String str2 = "Record";
        if ((this.f & 65536) == 0 && this.F == null) {
            iB = 0;
            i2 = 0;
        } else {
            iB = 0;
            i2 = 0;
            for (DQ dq = this.F; dq != null; dq = (DQ) dq.a) {
                i2++;
                iB += dq.b();
            }
            iC++;
            iB2 += iB + 8;
            this.e.d("Record");
        }
        T3 t3 = this.H;
        if (t3 != null) {
            int i7 = 0;
            while (t3 != null) {
                i7++;
                t3 = t3.c;
            }
            int i8 = iC + i7;
            T3 t4 = this.H;
            NY ny = this.e;
            t4.getClass();
            C1549Za c1549Za = ny.a;
            T3 t5 = t4;
            int i9 = 0;
            while (t5 != null) {
                ny.d(t5.a);
                i9 = t5.a().b + 6 + i9;
                t5 = t5.c;
                str2 = str2;
            }
            str = str2;
            iB2 += i9;
            iC = i8;
        } else {
            str = "Record";
        }
        int iD = iB2 + this.e.d();
        int iC2 = this.e.c();
        if (iC2 <= 65535) {
            C1667b6 c1667b10 = new C1667b6(iD);
            c1667b10.c(-889275714).c(this.d);
            this.e.b(c1667b10);
            c1667b10.d((~((this.d & 65535) < 49 ? 4096 : 0)) & this.f).d(this.g).d(this.h);
            c1667b10.d(this.i);
            for (int i10 = 0; i10 < this.i; i10++) {
                c1667b10.d(this.j[i10]);
            }
            c1667b10.d(i5);
            for (C2326ji c2326ji2 = this.k; c2326ji2 != null; c2326ji2 = (C2326ji) c2326ji2.b) {
                c2326ji2.a(c1667b10);
            }
            c1667b10.d(i6);
            boolean zF = false;
            boolean zG = false;
            for (C1986fF c1986fF2 = this.m; c1986fF2 != null; c1986fF2 = (C1986fF) c1986fF2.i) {
                zG |= c1986fF2.g();
                zF |= c1986fF2.f();
                c1986fF2.a(c1667b10);
            }
            c1667b10.d(iC);
            if (this.p != null) {
                C1667b6 c1667b6D = c1667b10.d(this.e.d("InnerClasses")).c(this.p.b + 2).d(this.o);
                C1667b6 c1667b11 = this.p;
                c1667b6D.a(c1667b11.a, 0, c1667b11.b);
            }
            if (this.q != 0) {
                c1667b10.d(this.e.d("EnclosingMethod")).c(4).d(this.q).d(this.r);
            }
            if ((this.f & 4096) != 0 && (this.d & 65535) < 49) {
                c1667b10.d(this.e.d(AttSynthetic.ATTRIBUTE_NAME)).c(0);
            }
            if (this.s != 0) {
                i3 = 2;
                c1667b10.d(this.e.d("Signature")).c(2).d(this.s);
            } else {
                i3 = 2;
            }
            if (this.t != 0) {
                c1667b10.d(this.e.d("SourceFile")).c(i3).d(this.t);
            }
            C1667b6 c1667b12 = this.u;
            if (c1667b12 != null) {
                int i11 = c1667b12.b;
                i4 = 0;
                c1667b10.d(this.e.d(AttSourceDebugExtension.ATTRIBUTE_NAME)).c(i11).a(this.u.a, 0, i11);
            } else {
                i4 = 0;
            }
            if ((this.f & 131072) != 0) {
                c1667b10.d(this.e.d("Deprecated")).c(i4);
            }
            C2583n2.a(this.e, this.v, this.w, this.x, this.y, c1667b10);
            this.e.a(c1667b10);
            C3193vF c3193vF2 = this.z;
            if (c3193vF2 != null) {
                c3193vF2.a(c1667b10);
            }
            if (this.A != 0) {
                c1667b10.d(this.e.d("NestHost")).c(2).d(this.A);
            }
            if (this.C != null) {
                C1667b6 c1667b6D2 = c1667b10.d(this.e.d("NestMembers")).c(this.C.b + 2).d(this.B);
                C1667b6 c1667b13 = this.C;
                c1667b6D2.a(c1667b13.a, 0, c1667b13.b);
            }
            if (this.E != null) {
                C1667b6 c1667b6D3 = c1667b10.d(this.e.d("PermittedSubclasses")).c(this.E.b + 2).d(this.D);
                C1667b6 c1667b14 = this.E;
                c1667b6D3.a(c1667b14.a, 0, c1667b14.b);
            }
            if ((this.f & 65536) != 0 || this.F != null) {
                c1667b10.d(this.e.d(str)).c(iB + 2).d(i2);
                for (DQ dq2 = this.F; dq2 != null; dq2 = (DQ) dq2.a) {
                    dq2.a(c1667b10);
                }
            }
            T3 t6 = this.H;
            if (t6 != null) {
                NY ny2 = this.e;
                C1549Za c1549Za2 = ny2.a;
                while (t6 != null) {
                    C1667b6 c1667b6A = t6.a();
                    c1667b10.d(ny2.d(t6.a)).c(c1667b6A.b);
                    c1667b10.a(c1667b6A.a, 0, c1667b6A.b);
                    t6 = t6.c;
                }
            }
            if (zF) {
                byte[] bArr = c1667b10.a;
                S3 s3 = new S3();
                s3.a(this.H);
                for (C2326ji c2326ji3 = this.k; c2326ji3 != null; c2326ji3 = (C2326ji) c2326ji3.b) {
                    c2326ji3.a(s3);
                }
                for (C1986fF c1986fF3 = this.m; c1986fF3 != null; c1986fF3 = (C1986fF) c1986fF3.i) {
                    c1986fF3.a(s3);
                }
                for (DQ dq3 = this.F; dq3 != null; dq3 = (DQ) dq3.a) {
                    dq3.a(s3);
                }
                T3[] t3ArrA = s3.a();
                this.k = null;
                this.l = null;
                this.m = null;
                this.n = null;
                this.v = null;
                this.w = null;
                this.x = null;
                this.y = null;
                this.z = null;
                this.A = 0;
                this.B = 0;
                this.C = null;
                this.D = 0;
                this.E = null;
                this.F = null;
                this.G = null;
                this.H = null;
                this.I = zG ? 3 : 0;
                new C1031Ha(bArr, false).a(this, t3ArrA, (zG ? i : 0) | 256);
                return b();
            }
            return c1667b10.a;
        }
        throw new C1149La(this.e.b(), iC2);
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final void a(String str, String str2) {
        if (str != null) {
            this.t = this.e.d(str);
        }
        if (str2 != null) {
            this.u = new C1667b6().a(str2, 0, Integer.MAX_VALUE);
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final AbstractC3116uF a(int i, String str, String str2) {
        NY ny = this.e;
        C3193vF c3193vF = new C3193vF(ny, ny.b(str).a, i, str2 == null ? 0 : this.e.d(str2));
        this.z = c3193vF;
        return c3193vF;
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final void a(String str) {
        this.A = this.e.a(str).a;
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final void a(String str, String str2, String str3) {
        this.q = this.e.a(str).a;
        if (str2 == null || str3 == null) {
            return;
        }
        this.r = this.e.a(str2, str3);
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final AbstractC2509m2 a(String str, boolean z) {
        if (z) {
            C2583n2 c2583n2A = C2583n2.a(this.e, str, this.v);
            this.v = c2583n2A;
            return c2583n2A;
        }
        C2583n2 c2583n2A2 = C2583n2.a(this.e, str, this.w);
        this.w = c2583n2A2;
        return c2583n2A2;
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final AbstractC2509m2 a(int i, C00 c00, String str, boolean z) {
        if (z) {
            C2583n2 c2583n2A = C2583n2.a(this.e, i, c00, str, this.x);
            this.x = c2583n2A;
            return c2583n2A;
        }
        C2583n2 c2583n2A2 = C2583n2.a(this.e, i, c00, str, this.y);
        this.y = c2583n2A2;
        return c2583n2A2;
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final void a(T3 t3) {
        t3.c = this.H;
        this.H = t3;
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final void a(int i, String str, String str2, String str3) {
        if (this.p == null) {
            this.p = new C1667b6();
        }
        MY myA = this.e.a(str);
        if (myA.g == 0) {
            this.o++;
            this.p.d(myA.a);
            this.p.d(str2 == null ? 0 : this.e.a(str2).a);
            this.p.d(str3 != null ? this.e.d(str3) : 0);
            this.p.d(i);
            myA.g = this.o;
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final AbstractC2252ii a(int i, String str, String str2, String str3, Object obj) {
        C2326ji c2326ji = new C2326ji(this.e, i, str, str2, str3, obj);
        if (this.k == null) {
            this.k = c2326ji;
        } else {
            this.l.b = c2326ji;
        }
        this.l = c2326ji;
        return c2326ji;
    }

    @Override // com.android.tools.r8.internal.AbstractC1256Pa
    public final AbstractC1899eF a(int i, String str, String str2, String str3, String[] strArr) {
        C1986fF c1986fF = new C1986fF(this.e, i, str, str2, str3, strArr, this.I);
        if (this.m == null) {
            this.m = c1986fF;
        } else {
            this.n.i = c1986fF;
        }
        this.n = c1986fF;
        return c1986fF;
    }

    public final int a(Object obj) {
        return this.e.a(obj).a;
    }
}
