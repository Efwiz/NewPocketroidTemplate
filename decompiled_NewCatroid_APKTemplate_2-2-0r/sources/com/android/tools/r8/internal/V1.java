package com.android.tools.r8.internal;

import com.badlogic.gdx.graphics.GL20;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class V1 implements JK {
    private final AbstractC3462ys h;
    private C0863Bn i;
    private int j;
    private List[] k;
    private C1238Oi[] l;
    private C3295wY[] m;
    private boolean[] n;
    private int[] o;
    private int p;

    public V1(F4 f4) {
        this.h = f4;
    }

    public final void b(ME me) {
        int iB = CZ.b(me.l) >> 2;
        if ((me.j & 8) != 0) {
            iB--;
        }
        ListIterator it = me.x.iterator();
        while (it.hasNext()) {
            AbstractC3097u abstractC3097u = (AbstractC3097u) it.next();
            if (abstractC3097u instanceof C2200i20) {
                iB = Math.max(iB, ((C2200i20) abstractC3097u).g + ((abstractC3097u.b() == 22 || abstractC3097u.b() == 24 || abstractC3097u.b() == 55 || abstractC3097u.b() == 57) ? 2 : 1));
            } else if (abstractC3097u instanceof C1134Kl) {
                iB = Math.max(iB, ((C1134Kl) abstractC3097u).g + 1);
            }
        }
        me.A = iB;
        me.z = -1;
        a(me);
        int iMax = 0;
        for (C1238Oi c1238Oi : this.l) {
            if (c1238Oi != null) {
                int iA = 0;
                for (int i = 0; i < c1238Oi.c(); i++) {
                    iA += ((E4) c1238Oi.b(i)).a();
                }
                iMax = Math.max(iMax, iA);
            }
        }
        me.z = iMax;
    }

    protected void c(ME me) {
    }

    public final void a(ME me) {
        int i;
        AbstractC3097u abstractC3097u;
        AbstractC3097u abstractC3097u2;
        C1238Oi c1238Oi;
        CZ czD;
        C3295wY c3295wY;
        boolean z = false;
        if ((me.j & GL20.GL_INVALID_ENUM) != 0) {
            this.l = new C1238Oi[0];
            return;
        }
        C0863Bn c0863Bn = me.x;
        this.i = c0863Bn;
        int size = c0863Bn.size();
        this.j = size;
        this.k = new List[size];
        this.l = new C1238Oi[size];
        this.m = new C3295wY[size];
        this.n = new boolean[size];
        this.o = new int[size];
        this.p = 0;
        for (int i2 = 0; i2 < me.y.size(); i2++) {
            C3443yZ c3443yZ = (C3443yZ) me.y.get(i2);
            int iB = this.i.b(c3443yZ.b);
            for (int iB2 = this.i.b(c3443yZ.a); iB2 < iB; iB2++) {
                List arrayList = this.k[iB2];
                if (arrayList == null) {
                    arrayList = new ArrayList();
                    this.k[iB2] = arrayList;
                }
                arrayList.add(c3443yZ);
            }
        }
        int i3 = me.A;
        C3295wY c3295wY2 = null;
        C3295wY c3295wY3 = new C3295wY(null, i3, null);
        ArrayList arrayList2 = new ArrayList();
        a(0, c3295wY3, arrayList2);
        HashMap map = new HashMap();
        while (!arrayList2.isEmpty()) {
            C1871du c1871du = (C1871du) arrayList2.remove(0);
            C3295wY c3295wY4 = (C3295wY) map.get(c1871du.g);
            if (c3295wY4 == null) {
                C3295wY c3295wY5 = new C3295wY(c1871du.g, i3, c1871du);
                map.put(c1871du.g, c3295wY5);
                a(this.i.b(c1871du.g), c3295wY5, arrayList2);
            } else {
                c3295wY4.c.add(c1871du);
            }
        }
        for (int i4 = 0; i4 < this.j; i4++) {
            C3295wY[] c3295wYArr = this.m;
            C3295wY c3295wY6 = c3295wYArr[i4];
            if (c3295wY6 != null && c3295wY6.a == null) {
                c3295wYArr[i4] = null;
            }
        }
        C1238Oi c1238Oi2 = new C1238Oi(me.A, me.z);
        int i5 = 8;
        if ((me.j & 8) == 0) {
            CZ czD2 = CZ.d("dummy");
            this.h.getClass();
            c1238Oi2.a(0, F4.a(czD2));
            i = 1;
        } else {
            i = 0;
        }
        for (CZ cz : CZ.a(me.l)) {
            this.h.getClass();
            c1238Oi2.a(i, F4.a(cz));
            int i6 = i + 1;
            if (cz.d() == 2) {
                this.h.getClass();
                c1238Oi2.a(i6, F4.a((CZ) null));
                i += 2;
            } else {
                i = i6;
            }
        }
        while (i < me.A) {
            this.h.getClass();
            c1238Oi2.a(i, F4.a((CZ) null));
            i++;
        }
        AbstractC3462ys abstractC3462ys = this.h;
        CZ czE = CZ.e(me.l);
        abstractC3462ys.getClass();
        c1238Oi2.b(F4.a(czE));
        a(0, c1238Oi2, (C3295wY) null);
        c(me);
        while (true) {
            int i7 = this.p;
            if (i7 <= 0) {
                return;
            }
            int[] iArr = this.o;
            int i8 = i7 - 1;
            this.p = i8;
            int i9 = iArr[i8];
            C1238Oi c1238Oi3 = this.l[i9];
            C3295wY c3295wY7 = this.m[i9];
            this.n[i9] = z;
            try {
                try {
                    AbstractC3097u abstractC3097uJ = me.x.j(i9);
                    try {
                        int iB3 = abstractC3097uJ.b();
                        int iC = abstractC3097uJ.c();
                        if (iC != i5 && iC != 15 && iC != 14) {
                            c1238Oi2.a(c1238Oi3).a(abstractC3097uJ, this.h);
                            C3295wY c3295wY8 = c3295wY7 == null ? c3295wY2 : new C3295wY(c3295wY7);
                            if (abstractC3097uJ instanceof C1871du) {
                                try {
                                    C1871du c1871du2 = (C1871du) abstractC3097uJ;
                                    if (iB3 != 167 && iB3 != 168) {
                                        a(i9 + 1, c1238Oi2, c3295wY8);
                                    }
                                    int iB4 = this.i.b(c1871du2.g);
                                    C3338xA c3338xA = c1871du2.g;
                                    if (iB3 == 168) {
                                        a(iB4, c1238Oi2, new C3295wY(c3338xA, me.A, c1871du2));
                                    } else {
                                        a(iB4, c1238Oi2, c3295wY8);
                                    }
                                } catch (RuntimeException e) {
                                    e = e;
                                    abstractC3097u = abstractC3097uJ;
                                }
                            } else if (abstractC3097uJ instanceof C2056gD) {
                                C2056gD c2056gD = (C2056gD) abstractC3097uJ;
                                a(this.i.b(c2056gD.g), c1238Oi2, c3295wY8);
                                for (int i10 = 0; i10 < c2056gD.i.size(); i10++) {
                                    a(this.i.b((C3338xA) c2056gD.i.get(i10)), c1238Oi2, c3295wY8);
                                }
                            } else if (abstractC3097uJ instanceof YY) {
                                YY yy = (YY) abstractC3097uJ;
                                a(this.i.b(yy.i), c1238Oi2, c3295wY8);
                                for (int i11 = 0; i11 < yy.j.size(); i11++) {
                                    a(this.i.b((C3338xA) yy.j.get(i11)), c1238Oi2, c3295wY8);
                                }
                            } else {
                                if (iB3 == 169) {
                                    if (c3295wY8 != null) {
                                        int i12 = 0;
                                        while (i12 < c3295wY8.c.size()) {
                                            int iB5 = this.i.b((C1871du) c3295wY8.c.get(i12));
                                            C1238Oi c1238Oi4 = this.l[iB5];
                                            if (c1238Oi4 != null) {
                                                a(iB5 + 1, c1238Oi4, c1238Oi2, this.m[iB5], c3295wY8.b);
                                            }
                                            i12++;
                                            c1238Oi3 = c1238Oi3;
                                            c3295wY8 = c3295wY8;
                                            abstractC3097uJ = abstractC3097uJ;
                                        }
                                    } else {
                                        abstractC3097u2 = abstractC3097uJ;
                                        throw new W1(abstractC3097u2, "RET instruction outside of a subroutine");
                                    }
                                    e = e;
                                    abstractC3097u = abstractC3097u2;
                                    throw new W1(abstractC3097u, AbstractC1588a5.a("Error at instruction ", i9, ": ").append(e.getMessage()).toString(), e);
                                }
                                c3295wY = c3295wY8;
                                abstractC3097u2 = abstractC3097uJ;
                                c1238Oi = c1238Oi3;
                                if (iB3 != 191 && (iB3 < 172 || iB3 > 177)) {
                                    if (c3295wY != null) {
                                        if (abstractC3097u2 instanceof C2200i20) {
                                            int i13 = ((C2200i20) abstractC3097u2).g;
                                            boolean[] zArr = c3295wY.b;
                                            zArr[i13] = true;
                                            if (iB3 == 22 || iB3 == 24 || iB3 == 55 || iB3 == 57) {
                                                zArr[i13 + 1] = true;
                                            }
                                        } else if (abstractC3097u2 instanceof C1134Kl) {
                                            c3295wY.b[((C1134Kl) abstractC3097u2).g] = true;
                                        }
                                    }
                                    a(i9 + 1, c1238Oi2, c3295wY);
                                }
                                c3295wY7 = c3295wY;
                            }
                            c3295wY = c3295wY8;
                            abstractC3097u2 = abstractC3097uJ;
                            c1238Oi = c1238Oi3;
                            c3295wY7 = c3295wY;
                        } else {
                            abstractC3097u2 = abstractC3097uJ;
                            c1238Oi = c1238Oi3;
                            a(i9 + 1, c1238Oi, c3295wY7);
                        }
                        try {
                            List<C3443yZ> list = this.k[i9];
                            if (list != null) {
                                for (C3443yZ c3443yZ2 : list) {
                                    String str = c3443yZ2.d;
                                    if (str == null) {
                                        czD = CZ.d("java/lang/Throwable");
                                    } else {
                                        czD = CZ.d(str);
                                    }
                                    this.i.b(c3443yZ2.c);
                                    C1238Oi c1238Oi5 = new C1238Oi(c1238Oi);
                                    c1238Oi5.a();
                                    this.h.getClass();
                                    c1238Oi5.a(F4.a(czD));
                                    a(this.i.b(c3443yZ2.c), c1238Oi5, c3295wY7);
                                }
                            }
                            z = false;
                            c3295wY2 = null;
                            i5 = 8;
                        } catch (RuntimeException e2) {
                            e = e2;
                        }
                    } catch (RuntimeException e3) {
                        e = e3;
                        abstractC3097u2 = abstractC3097uJ;
                    }
                } catch (RuntimeException e4) {
                    e = e4;
                    abstractC3097u = null;
                }
            } catch (W1 e5) {
                throw new W1(e5.a, AbstractC1588a5.a("Error at instruction ", i9, ": ").append(e5.getMessage()).toString(), e5);
            }
        }
    }

    private void a(int i, C3295wY c3295wY, ArrayList arrayList) throws W1 {
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(Integer.valueOf(i));
        while (!arrayList2.isEmpty()) {
            int iIntValue = ((Integer) arrayList2.remove(arrayList2.size() - 1)).intValue();
            if (iIntValue >= 0 && iIntValue < this.j) {
                C3295wY[] c3295wYArr = this.m;
                if (c3295wYArr[iIntValue] == null) {
                    c3295wYArr[iIntValue] = new C3295wY(c3295wY);
                    AbstractC3097u abstractC3097uJ = this.i.j(iIntValue);
                    if (abstractC3097uJ instanceof C1871du) {
                        if (abstractC3097uJ.b() == 168) {
                            arrayList.add(abstractC3097uJ);
                        } else {
                            arrayList2.add(Integer.valueOf(this.i.b(((C1871du) abstractC3097uJ).g)));
                        }
                    } else if (abstractC3097uJ instanceof YY) {
                        YY yy = (YY) abstractC3097uJ;
                        a(this.i.b(yy.i), c3295wY, arrayList);
                        for (int size = yy.j.size() - 1; size >= 0; size--) {
                            arrayList2.add(Integer.valueOf(this.i.b((C3338xA) yy.j.get(size))));
                        }
                    } else if (abstractC3097uJ instanceof C2056gD) {
                        C2056gD c2056gD = (C2056gD) abstractC3097uJ;
                        a(this.i.b(c2056gD.g), c3295wY, arrayList);
                        for (int size2 = c2056gD.i.size() - 1; size2 >= 0; size2--) {
                            arrayList2.add(Integer.valueOf(this.i.b((C3338xA) c2056gD.i.get(size2))));
                        }
                    }
                    List list = this.k[iIntValue];
                    if (list != null) {
                        Iterator it = list.iterator();
                        while (it.hasNext()) {
                            arrayList2.add(Integer.valueOf(this.i.b(((C3443yZ) it.next()).c)));
                        }
                    }
                    int iB = abstractC3097uJ.b();
                    if (iB != 167 && iB != 191) {
                        switch (iB) {
                            case 169:
                            case 170:
                            case 171:
                            case 172:
                            case 173:
                            case 174:
                            case 175:
                            case 176:
                            case 177:
                                break;
                            default:
                                arrayList2.add(Integer.valueOf(iIntValue + 1));
                                break;
                        }
                    }
                }
            } else {
                throw new W1(null, "Execution can fall off the end of the code");
            }
        }
    }

    public final C1238Oi[] a() {
        return this.l;
    }

    public final List a(int i) {
        return this.k[i];
    }

    private void a(int i, C1238Oi c1238Oi, C3295wY c3295wY) throws W1 {
        boolean zA;
        C1238Oi[] c1238OiArr = this.l;
        C1238Oi c1238Oi2 = c1238OiArr[i];
        if (c1238Oi2 == null) {
            c1238OiArr[i] = new C1238Oi(c1238Oi);
            zA = true;
        } else {
            zA = c1238Oi2.a(c1238Oi, this.h);
        }
        C3295wY[] c3295wYArr = this.m;
        C3295wY c3295wY2 = c3295wYArr[i];
        if (c3295wY2 == null) {
            if (c3295wY != null) {
                c3295wYArr[i] = new C3295wY(c3295wY);
                zA = true;
            }
        } else if (c3295wY != null) {
            zA |= c3295wY2.a(c3295wY);
        }
        if (zA) {
            boolean[] zArr = this.n;
            if (zArr[i]) {
                return;
            }
            zArr[i] = true;
            int[] iArr = this.o;
            int i2 = this.p;
            this.p = i2 + 1;
            iArr[i2] = i;
        }
    }

    private void a(int i, C1238Oi c1238Oi, C1238Oi c1238Oi2, C3295wY c3295wY, boolean[] zArr) throws W1 {
        boolean zA;
        c1238Oi2.a(c1238Oi, zArr);
        C1238Oi[] c1238OiArr = this.l;
        C1238Oi c1238Oi3 = c1238OiArr[i];
        if (c1238Oi3 == null) {
            c1238OiArr[i] = new C1238Oi(c1238Oi2);
            zA = true;
        } else {
            zA = c1238Oi3.a(c1238Oi2, this.h);
        }
        C3295wY c3295wY2 = this.m[i];
        if (c3295wY2 != null && c3295wY != null) {
            zA |= c3295wY2.a(c3295wY);
        }
        if (zA) {
            boolean[] zArr2 = this.n;
            if (zArr2[i]) {
                return;
            }
            zArr2[i] = true;
            int[] iArr = this.o;
            int i2 = this.p;
            this.p = i2 + 1;
            iArr[i2] = i;
        }
    }
}
