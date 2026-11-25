package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0594a1;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayList;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class ZB implements UB {
    static final /* synthetic */ boolean b = true;
    private final RB a;

    public ZB(RB rb) {
        this.a = rb;
    }

    public final int a() {
        return b() - this.a.a();
    }

    public abstract void a(int i);

    public abstract void a(int i, Object obj, Object obj2);

    public abstract void a(com.android.tools.r8.graph.Y0 y0);

    public abstract void a(com.android.tools.r8.graph.Y0 y0, Object obj);

    public abstract void a(com.android.tools.r8.graph.Y0 y0, Object obj, Object obj2);

    public abstract void a(C0759u2 c0759u2);

    public abstract void a(C0767v2 c0767v2);

    public abstract void a(C0767v2 c0767v2, Object obj);

    public abstract void a(C0767v2 c0767v2, ArrayList arrayList);

    public abstract void a(EnumC1076Il enumC1076Il, int i, Object obj);

    public abstract void a(Object obj);

    public abstract int b();

    public abstract void b(int i);

    public void b(C0684j2 c0684j2, ArrayList arrayList) {
        f();
    }

    public abstract void b(EnumC2527mI enumC2527mI, Object obj, Object obj2);

    public abstract void b(Object obj);

    public abstract void c();

    public void c(EnumC2527mI enumC2527mI, Object obj, Object obj2) {
        f();
    }

    public abstract void c(Object obj);

    public abstract void d();

    public void d(EnumC2527mI enumC2527mI, Object obj, Object obj2) {
        f();
    }

    public abstract void d(Object obj);

    public abstract void e();

    public void e(EnumC2527mI enumC2527mI, Object obj, Object obj2) {
        f();
    }

    public abstract void e(Object obj);

    public void f() {
    }

    public abstract void g();

    public void a(EnumC2527mI enumC2527mI, Object obj, Object obj2) {
        f();
    }

    public void b(C0684j2 c0684j2, ArrayList arrayList, boolean z) {
        b(c0684j2, arrayList);
    }

    public void c(C0684j2 c0684j2, ArrayList arrayList, boolean z) {
        b(c0684j2, arrayList);
    }

    public void a(int i, Object obj) {
        boolean z = b;
        if (!z && 133 > i) {
            throw new AssertionError();
        }
        if (!z && i > 147) {
            throw new AssertionError();
        }
        C1591a8 c1591a8A = C1591a8.a(i);
        a(c1591a8A.U(), c1591a8A.V(), obj);
    }

    public void b(C0767v2 c0767v2) {
        f();
    }

    public void c(C0684j2 c0684j2, ArrayList arrayList) {
        b(c0684j2, arrayList);
    }

    private ArrayList b(VB vb) {
        ArrayList arrayList = new ArrayList();
        while (vb.k()) {
            arrayList.add(this.a.e(vb.a(), b()));
        }
        return arrayList;
    }

    public void a(EnumC2527mI enumC2527mI, EnumC2527mI enumC2527mI2, Object obj) {
        f();
    }

    public void a(C0684j2 c0684j2, ArrayList arrayList, boolean z) {
        b(c0684j2, arrayList);
    }

    public void a(C0684j2 c0684j2, ArrayList arrayList) {
        b(c0684j2, arrayList);
    }

    @Override // com.android.tools.r8.internal.UB
    public void a(VB vb) {
        int iB = vb.b();
        if (iB == 177) {
            g();
            return;
        }
        if (iB != 178) {
            if (iB != 184) {
                if (iB == 185) {
                    a((C0684j2) this.a.j(vb.n()), b(vb));
                    return;
                }
                if (iB == 187) {
                    AbstractC0594a1 abstractC0594a1J = this.a.j(vb.n());
                    if (abstractC0594a1J instanceof C0767v2) {
                        b((C0767v2) abstractC0594a1J);
                        return;
                    }
                    throw new Z00();
                }
                if (iB == 188) {
                    a((C0767v2) this.a.j(vb.n()), this.a.e(vb.a(), b()));
                    return;
                }
                if (iB == 190) {
                    a(this.a.e(vb.a(), b()));
                    return;
                }
                if (iB == 191) {
                    e(this.a.e(vb.a(), b()));
                    return;
                }
                if (iB == 194) {
                    c(this.a.e(vb.a(), b()));
                    return;
                }
                if (iB != 195) {
                    switch (iB) {
                        case 1:
                            c();
                            return;
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                        case 7:
                        case 8:
                            a(iB - 3);
                            return;
                        case 9:
                            f();
                            return;
                        case 10:
                            f();
                            return;
                        case 11:
                        case 12:
                        case 13:
                            Float.floatToRawIntBits(iB - 11);
                            f();
                            return;
                        case 14:
                            Double.doubleToRawLongBits(0.0d);
                            f();
                            return;
                        case 15:
                            Double.doubleToRawLongBits(1.0d);
                            f();
                            return;
                        default:
                            if (iB == 18) {
                                AbstractC0594a1 abstractC0594a1J2 = this.a.j(vb.n());
                                if (abstractC0594a1J2 instanceof C0759u2) {
                                    a((C0759u2) abstractC0594a1J2);
                                    return;
                                }
                                throw new Z00();
                            }
                            if (iB == 154) {
                                a(EnumC1076Il.NE, vb.g(), this.a.e(vb.a(), b()));
                                return;
                            }
                            if (iB != 167) {
                                switch (iB) {
                                    case 96:
                                        a(EnumC2527mI.INT, this.a.e(vb.a(), b()), this.a.e(vb.a(), b()));
                                        return;
                                    case 97:
                                        a(EnumC2527mI.LONG, this.a.e(vb.a(), b()), this.a.e(vb.a(), b()));
                                        return;
                                    case 98:
                                        a(EnumC2527mI.FLOAT, this.a.e(vb.a(), b()), this.a.e(vb.a(), b()));
                                        return;
                                    case 99:
                                        a(EnumC2527mI.DOUBLE, this.a.e(vb.a(), b()), this.a.e(vb.a(), b()));
                                        return;
                                    case 100:
                                        e(EnumC2527mI.INT, this.a.e(vb.a(), b()), this.a.e(vb.a(), b()));
                                        return;
                                    case 101:
                                        e(EnumC2527mI.LONG, this.a.e(vb.a(), b()), this.a.e(vb.a(), b()));
                                        return;
                                    case 102:
                                        e(EnumC2527mI.FLOAT, this.a.e(vb.a(), b()), this.a.e(vb.a(), b()));
                                        return;
                                    case 103:
                                        e(EnumC2527mI.DOUBLE, this.a.e(vb.a(), b()), this.a.e(vb.a(), b()));
                                        return;
                                    case 104:
                                        c(EnumC2527mI.INT, this.a.e(vb.a(), b()), this.a.e(vb.a(), b()));
                                        return;
                                    case 105:
                                        c(EnumC2527mI.LONG, this.a.e(vb.a(), b()), this.a.e(vb.a(), b()));
                                        return;
                                    case 106:
                                        c(EnumC2527mI.FLOAT, this.a.e(vb.a(), b()), this.a.e(vb.a(), b()));
                                        return;
                                    case 107:
                                        c(EnumC2527mI.DOUBLE, this.a.e(vb.a(), b()), this.a.e(vb.a(), b()));
                                        return;
                                    case 108:
                                        b(EnumC2527mI.INT, this.a.e(vb.a(), b()), this.a.e(vb.a(), b()));
                                        return;
                                    case 109:
                                        b(EnumC2527mI.LONG, this.a.e(vb.a(), b()), this.a.e(vb.a(), b()));
                                        return;
                                    case 110:
                                        b(EnumC2527mI.FLOAT, this.a.e(vb.a(), b()), this.a.e(vb.a(), b()));
                                        return;
                                    case 111:
                                        b(EnumC2527mI.DOUBLE, this.a.e(vb.a(), b()), this.a.e(vb.a(), b()));
                                        return;
                                    case 112:
                                        d(EnumC2527mI.INT, this.a.e(vb.a(), b()), this.a.e(vb.a(), b()));
                                        return;
                                    case 113:
                                        d(EnumC2527mI.LONG, this.a.e(vb.a(), b()), this.a.e(vb.a(), b()));
                                        return;
                                    case 114:
                                        d(EnumC2527mI.FLOAT, this.a.e(vb.a(), b()), this.a.e(vb.a(), b()));
                                        return;
                                    case 115:
                                        d(EnumC2527mI.DOUBLE, this.a.e(vb.a(), b()), this.a.e(vb.a(), b()));
                                        return;
                                    default:
                                        switch (iB) {
                                            case 133:
                                            case 134:
                                            case 135:
                                            case 136:
                                            case 137:
                                            case 138:
                                            case 139:
                                            case 140:
                                            case 141:
                                            case 142:
                                            case 143:
                                            case 144:
                                            case 145:
                                            case 146:
                                            case 147:
                                                a(iB, this.a.e(vb.a(), b()));
                                                return;
                                            case 148:
                                            case 149:
                                            case 150:
                                            case 151:
                                            case 152:
                                                a(iB, this.a.e(vb.a(), b()), this.a.e(vb.a(), b()));
                                                return;
                                            default:
                                                switch (iB) {
                                                    case 180:
                                                        a((com.android.tools.r8.graph.Y0) this.a.j(vb.n()), this.a.e(vb.a(), b()));
                                                        return;
                                                    case 181:
                                                        a((com.android.tools.r8.graph.Y0) this.a.j(vb.n()), this.a.e(vb.a(), b()), this.a.e(vb.a(), b()));
                                                        return;
                                                    case 182:
                                                        c((C0684j2) this.a.j(vb.n()), b(vb));
                                                        return;
                                                    default:
                                                        switch (iB) {
                                                            case 200:
                                                                a(vb.m());
                                                                return;
                                                            case 201:
                                                                vb.h();
                                                                f();
                                                                return;
                                                            case 202:
                                                                vb.m();
                                                                f();
                                                                return;
                                                            case 203:
                                                                vb.h();
                                                                f();
                                                                return;
                                                            case 204:
                                                                break;
                                                            case 205:
                                                            case 206:
                                                                a((C0684j2) this.a.j(vb.n()), b(vb), iB == 206);
                                                                return;
                                                            case 207:
                                                            case 208:
                                                                c((C0684j2) this.a.j(vb.n()), b(vb), iB == 208);
                                                                return;
                                                            case 209:
                                                                d();
                                                                return;
                                                            case 210:
                                                                C0767v2 c0767v2 = (C0767v2) this.a.j(vb.n());
                                                                ArrayList arrayList = new ArrayList();
                                                                while (vb.k()) {
                                                                    arrayList.add(this.a.e(vb.a(), b()));
                                                                }
                                                                a(c0767v2, arrayList);
                                                                return;
                                                            case 211:
                                                                e();
                                                                return;
                                                            case 212:
                                                                a((C0767v2) this.a.j(vb.n()));
                                                                return;
                                                            case 213:
                                                                b(this.a.e(vb.a(), b()));
                                                                return;
                                                            default:
                                                                throw new Z00("No dispatch for opcode " + YB.a(iB));
                                                        }
                                                        break;
                                                }
                                                break;
                                        }
                                        break;
                                }
                            } else {
                                b(vb.g());
                                return;
                            }
                            break;
                    }
                } else {
                    d(this.a.e(vb.a(), b()));
                    return;
                }
            }
            b((C0684j2) this.a.j(vb.n()), b(vb), iB == 204);
            return;
        }
        a((com.android.tools.r8.graph.Y0) this.a.j(vb.n()));
    }
}
