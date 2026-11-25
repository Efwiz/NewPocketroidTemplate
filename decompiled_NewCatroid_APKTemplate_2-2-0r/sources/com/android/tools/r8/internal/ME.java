package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class ME extends AbstractC1899eF {
    public int A;
    public List B;
    public List C;
    public List D;
    private boolean E;
    public int j;
    public String k;
    public String l;
    public ArrayList m;
    public List n;
    public List o;
    public List p;
    public List q;
    public List r;
    public Object s;
    public int t;
    public List[] u;
    public int v;
    public List[] w;
    public C0863Bn x;
    public List y;
    public int z;

    public ME(int i, int i2, String str, String str2, String[] strArr) {
        super(i);
        this.j = i2;
        this.k = str;
        this.l = str2;
        P10.a(strArr);
        if ((i2 & 1024) == 0) {
            this.B = new ArrayList(5);
        }
        this.y = new ArrayList();
        this.x = new C0863Bn();
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final AbstractC2509m2 a() {
        return new C2432l2(new LE(this));
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void b() {
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public void c() {
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void d(int i, int i2) {
        this.x.a(new C2200i20(i, i2));
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final AbstractC2509m2 a(String str, boolean z) {
        C2432l2 c2432l2 = new C2432l2(str);
        if (z) {
            this.n = P10.a(this.n, c2432l2);
        } else {
            this.o = P10.a(this.o, c2432l2);
        }
        return c2432l2;
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void b(int i, String str) {
        if (this.m == null) {
            this.m = new ArrayList(5);
        }
        this.m.add(new C2843qL(str, i));
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final AbstractC2509m2 c(int i, C00 c00, String str, boolean z) {
        C3251w00 c3251w00 = new C3251w00(i, c00, str);
        if (z) {
            this.p = P10.a(this.p, c3251w00);
        } else {
            this.q = P10.a(this.q, c3251w00);
        }
        return c3251w00;
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void b(int i, int i2) {
        this.x.a(new C3531zr(i, i2));
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(int i, boolean z) {
        if (z) {
            this.t = i;
        } else {
            this.v = i;
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final AbstractC2509m2 b(int i, C00 c00, String str, boolean z) {
        C3443yZ c3443yZ = (C3443yZ) this.y.get((16776960 & i) >> 8);
        C3251w00 c3251w00 = new C3251w00(i, c00, str);
        if (z) {
            c3443yZ.e = P10.a(c3443yZ.e, c3251w00);
        } else {
            c3443yZ.f = P10.a(c3443yZ.f, c3251w00);
        }
        return c3251w00;
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void c(int i, String str) {
        this.x.a(new A00(i, str));
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void c(int i, int i2) {
        this.z = i;
        this.A = i2;
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final AbstractC2509m2 a(int i, String str, boolean z) {
        C2432l2 c2432l2 = new C2432l2(str);
        if (z) {
            if (this.u == null) {
                this.u = new List[CZ.a(this.l).length];
            }
            List[] listArr = this.u;
            listArr[i] = P10.a(listArr[i], c2432l2);
        } else {
            if (this.w == null) {
                this.w = new List[CZ.a(this.l).length];
            }
            List[] listArr2 = this.w;
            listArr2[i] = P10.a(listArr2[i], c2432l2);
        }
        return c2432l2;
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void b(int i, C3263wA c3263wA) {
        this.x.a(new C3339xB(i, b(c3263wA)));
    }

    protected static C3338xA b(C3263wA c3263wA) {
        if (!(c3263wA.a instanceof C3338xA)) {
            c3263wA.a = new C3338xA();
        }
        return c3263wA.a;
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(T3 t3) {
        this.r = P10.a(this.r, t3);
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(int i, int i2, Object[] objArr, int i3, Object[] objArr2) {
        Object[] objArr3;
        Object[] objArr4;
        C0863Bn c0863Bn = this.x;
        if (objArr == null) {
            objArr3 = null;
        } else {
            Object[] objArr5 = new Object[objArr.length];
            int length = objArr.length;
            for (int i4 = 0; i4 < length; i4++) {
                Object objB = objArr[i4];
                if (objB instanceof C3263wA) {
                    objB = b((C3263wA) objB);
                }
                objArr5[i4] = objB;
            }
            objArr3 = objArr5;
        }
        if (objArr2 == null) {
            objArr4 = null;
        } else {
            Object[] objArr6 = new Object[objArr2.length];
            int length2 = objArr2.length;
            for (int i5 = 0; i5 < length2; i5++) {
                Object objB2 = objArr2[i5];
                if (objB2 instanceof C3263wA) {
                    objB2 = b((C3263wA) objB2);
                }
                objArr6[i5] = objB2;
            }
            objArr4 = objArr6;
        }
        c0863Bn.a(new C1295Qi(i, i2, objArr3, i3, objArr4));
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(int i) {
        this.x.a(new C0903Cn(i));
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(int i, String str, String str2, String str3) {
        this.x.a(new C1130Kh(i, str, str2, str3));
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(int i, String str, String str2, String str3, boolean z) {
        if (this.h < 327680 && (i & 256) == 0) {
            super.a(i, str, str2, str3, z);
        } else {
            this.x.a(new HE(i & (-257), str, str2, str3, z));
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(String str, String str2, C1944ek c1944ek, Object... objArr) {
        this.x.a(new C1194Ms(str, str2, c1944ek, objArr));
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public void a(int i, C3263wA c3263wA) {
        this.x.a(new C1871du(i, b(c3263wA)));
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(C3263wA c3263wA) {
        this.x.a(b(c3263wA));
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(Object obj) {
        this.x.a(new ZA(obj));
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(int i, int i2) {
        this.x.a(new C1134Kl(i, i2));
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(int i, int i2, C3263wA c3263wA, C3263wA... c3263wAArr) {
        C0863Bn c0863Bn = this.x;
        C3338xA c3338xAB = b(c3263wA);
        C3338xA[] c3338xAArr = new C3338xA[c3263wAArr.length];
        int length = c3263wAArr.length;
        for (int i3 = 0; i3 < length; i3++) {
            c3338xAArr[i3] = b(c3263wAArr[i3]);
        }
        c0863Bn.a(new YY(i, i2, c3338xAB, c3338xAArr));
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(C3263wA c3263wA, int[] iArr, C3263wA[] c3263wAArr) {
        C0863Bn c0863Bn = this.x;
        C3338xA c3338xAB = b(c3263wA);
        C3338xA[] c3338xAArr = new C3338xA[c3263wAArr.length];
        int length = c3263wAArr.length;
        for (int i = 0; i < length; i++) {
            c3338xAArr[i] = b(c3263wAArr[i]);
        }
        c0863Bn.a(new C2056gD(c3338xAB, iArr, c3338xAArr));
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(int i, String str) {
        this.x.a(new DF(str, i));
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final AbstractC2509m2 a(int i, C00 c00, String str, boolean z) {
        AbstractC3097u abstractC3097uB = this.x.b();
        while (abstractC3097uB.a == -1) {
            abstractC3097uB = abstractC3097uB.d;
        }
        C3251w00 c3251w00 = new C3251w00(i, c00, str);
        if (z) {
            abstractC3097uB.b = P10.a(abstractC3097uB.b, c3251w00);
        } else {
            abstractC3097uB.c = P10.a(abstractC3097uB.c, c3251w00);
        }
        return c3251w00;
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(C3263wA c3263wA, C3263wA c3263wA2, C3263wA c3263wA3, String str) {
        this.y = P10.a(this.y, new C3443yZ(b(c3263wA), b(c3263wA2), b(c3263wA3), str));
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(String str, String str2, String str3, C3263wA c3263wA, C3263wA c3263wA2, int i) {
        this.B = P10.a(this.B, new CC(str, str2, str3, b(c3263wA), b(c3263wA2), i));
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final AbstractC2509m2 a(int i, C00 c00, C3263wA[] c3263wAArr, C3263wA[] c3263wAArr2, int[] iArr, String str, boolean z) {
        C3338xA[] c3338xAArr = new C3338xA[c3263wAArr.length];
        int length = c3263wAArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            c3338xAArr[i2] = b(c3263wAArr[i2]);
        }
        C3338xA[] c3338xAArr2 = new C3338xA[c3263wAArr2.length];
        int length2 = c3263wAArr2.length;
        for (int i3 = 0; i3 < length2; i3++) {
            c3338xAArr2[i3] = b(c3263wAArr2[i3]);
        }
        BC bc = new BC(i, c00, c3338xAArr, c3338xAArr2, iArr, str);
        if (z) {
            this.C = P10.a(this.C, bc);
        } else {
            this.D = P10.a(this.D, bc);
        }
        return bc;
    }

    public final void a(AbstractC1899eF abstractC1899eF) {
        ArrayList arrayList = this.m;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                C2843qL c2843qL = (C2843qL) this.m.get(i);
                abstractC1899eF.b(c2843qL.b, c2843qL.a);
            }
        }
        if (this.s != null) {
            AbstractC2509m2 abstractC2509m2A = abstractC1899eF.a();
            C2432l2.a(abstractC2509m2A, (String) null, this.s);
            if (abstractC2509m2A != null) {
                abstractC2509m2A.a();
            }
        }
        List list = this.n;
        if (list != null) {
            int size2 = list.size();
            for (int i2 = 0; i2 < size2; i2++) {
                C2432l2 c2432l2 = (C2432l2) this.n.get(i2);
                c2432l2.a(abstractC1899eF.a(c2432l2.b, true));
            }
        }
        List list2 = this.o;
        if (list2 != null) {
            int size3 = list2.size();
            for (int i3 = 0; i3 < size3; i3++) {
                C2432l2 c2432l3 = (C2432l2) this.o.get(i3);
                c2432l3.a(abstractC1899eF.a(c2432l3.b, false));
            }
        }
        List list3 = this.p;
        if (list3 != null) {
            int size4 = list3.size();
            for (int i4 = 0; i4 < size4; i4++) {
                C3251w00 c3251w00 = (C3251w00) this.p.get(i4);
                c3251w00.a(abstractC1899eF.c(c3251w00.d, c3251w00.e, c3251w00.b, true));
            }
        }
        List list4 = this.q;
        if (list4 != null) {
            int size5 = list4.size();
            for (int i5 = 0; i5 < size5; i5++) {
                C3251w00 c3251w01 = (C3251w00) this.q.get(i5);
                c3251w01.a(abstractC1899eF.c(c3251w01.d, c3251w01.e, c3251w01.b, false));
            }
        }
        int i6 = this.t;
        if (i6 > 0) {
            abstractC1899eF.a(i6, true);
        }
        List[] listArr = this.u;
        if (listArr != null) {
            int length = listArr.length;
            for (int i7 = 0; i7 < length; i7++) {
                List list5 = this.u[i7];
                if (list5 != null) {
                    int size6 = list5.size();
                    for (int i8 = 0; i8 < size6; i8++) {
                        C2432l2 c2432l4 = (C2432l2) list5.get(i8);
                        c2432l4.a(abstractC1899eF.a(i7, c2432l4.b, true));
                    }
                }
            }
        }
        int i9 = this.v;
        if (i9 > 0) {
            abstractC1899eF.a(i9, false);
        }
        List[] listArr2 = this.w;
        if (listArr2 != null) {
            int length2 = listArr2.length;
            for (int i10 = 0; i10 < length2; i10++) {
                List list6 = this.w[i10];
                if (list6 != null) {
                    int size7 = list6.size();
                    for (int i11 = 0; i11 < size7; i11++) {
                        C2432l2 c2432l5 = (C2432l2) list6.get(i11);
                        c2432l5.a(abstractC1899eF.a(i10, c2432l5.b, false));
                    }
                }
            }
        }
        if (this.E) {
            this.x.d();
        }
        List list7 = this.r;
        if (list7 != null) {
            int size8 = list7.size();
            for (int i12 = 0; i12 < size8; i12++) {
                abstractC1899eF.a((T3) this.r.get(i12));
            }
        }
        if (this.x.size() > 0) {
            abstractC1899eF.b();
            List list8 = this.y;
            if (list8 != null) {
                int size9 = list8.size();
                for (int i13 = 0; i13 < size9; i13++) {
                    C3443yZ c3443yZ = (C3443yZ) this.y.get(i13);
                    int i14 = (i13 << 8) | 1107296256;
                    List list9 = c3443yZ.e;
                    if (list9 != null) {
                        int size10 = list9.size();
                        for (int i15 = 0; i15 < size10; i15++) {
                            ((C3251w00) c3443yZ.e.get(i15)).d = i14;
                        }
                    }
                    List list10 = c3443yZ.f;
                    if (list10 != null) {
                        int size11 = list10.size();
                        for (int i16 = 0; i16 < size11; i16++) {
                            ((C3251w00) c3443yZ.f.get(i16)).d = i14;
                        }
                    }
                    ((C3443yZ) this.y.get(i13)).a(abstractC1899eF);
                }
            }
            this.x.a(abstractC1899eF);
            List list11 = this.B;
            if (list11 != null) {
                int size12 = list11.size();
                for (int i17 = 0; i17 < size12; i17++) {
                    CC cc = (CC) this.B.get(i17);
                    abstractC1899eF.a(cc.a, cc.b, cc.c, cc.d.d(), cc.e.d(), cc.f);
                }
            }
            List list12 = this.C;
            if (list12 != null) {
                int size13 = list12.size();
                for (int i18 = 0; i18 < size13; i18++) {
                    ((BC) this.C.get(i18)).a(abstractC1899eF, true);
                }
            }
            List list13 = this.D;
            if (list13 != null) {
                int size14 = list13.size();
                for (int i19 = 0; i19 < size14; i19++) {
                    ((BC) this.D.get(i19)).a(abstractC1899eF, false);
                }
            }
            abstractC1899eF.c(this.z, this.A);
            this.E = true;
        }
        abstractC1899eF.c();
    }
}
