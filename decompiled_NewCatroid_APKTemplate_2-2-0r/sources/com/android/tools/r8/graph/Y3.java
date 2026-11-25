package com.android.tools.r8.graph;

import com.android.tools.r8.ClassFileConsumer;
import com.android.tools.r8.internal.A6;
import com.android.tools.r8.internal.A7;
import com.android.tools.r8.internal.AbstractC1899eF;
import com.android.tools.r8.internal.AbstractC1915eM;
import com.android.tools.r8.internal.AbstractC2206i7;
import com.android.tools.r8.internal.B6;
import com.android.tools.r8.internal.B7;
import com.android.tools.r8.internal.C1064Ic;
import com.android.tools.r8.internal.C1367Tb;
import com.android.tools.r8.internal.C1590a7;
import com.android.tools.r8.internal.C1591a8;
import com.android.tools.r8.internal.C1668b7;
import com.android.tools.r8.internal.C1733c10;
import com.android.tools.r8.internal.C1818d7;
import com.android.tools.r8.internal.C1891e7;
import com.android.tools.r8.internal.C1944ek;
import com.android.tools.r8.internal.C2049g6;
import com.android.tools.r8.internal.C2051g8;
import com.android.tools.r8.internal.C2284j7;
import com.android.tools.r8.internal.C2732or;
import com.android.tools.r8.internal.C2899r7;
import com.android.tools.r8.internal.C3039t7;
import com.android.tools.r8.internal.C3109u8;
import com.android.tools.r8.internal.C3185v7;
import com.android.tools.r8.internal.C3186v8;
import com.android.tools.r8.internal.C3260w7;
import com.android.tools.r8.internal.C3263wA;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.C3481z6;
import com.android.tools.r8.internal.CR;
import com.android.tools.r8.internal.CZ;
import com.android.tools.r8.internal.D6;
import com.android.tools.r8.internal.D8;
import com.android.tools.r8.internal.EnumC1076Il;
import com.android.tools.r8.internal.EnumC1812d20;
import com.android.tools.r8.internal.EnumC2523mE;
import com.android.tools.r8.internal.EnumC3423yF;
import com.android.tools.r8.internal.I7;
import com.android.tools.r8.internal.InterfaceC0993Fr;
import com.android.tools.r8.internal.InterfaceC1348Si;
import com.android.tools.r8.internal.InterfaceC2806pp;
import com.android.tools.r8.internal.InterfaceC3054tM;
import com.android.tools.r8.internal.J7;
import com.android.tools.r8.internal.JK;
import com.android.tools.r8.internal.K7;
import com.android.tools.r8.internal.M6;
import com.android.tools.r8.internal.M7;
import com.android.tools.r8.internal.O7;
import com.android.tools.r8.internal.Q7;
import com.android.tools.r8.internal.R7;
import com.android.tools.r8.internal.R8;
import com.android.tools.r8.internal.S7;
import com.android.tools.r8.internal.U6;
import com.android.tools.r8.internal.U7;
import com.android.tools.r8.internal.U8;
import com.android.tools.r8.internal.V7;
import com.android.tools.r8.internal.V8;
import com.android.tools.r8.internal.W7;
import com.android.tools.r8.internal.W8;
import com.android.tools.r8.internal.X6;
import com.android.tools.r8.internal.Y6;
import com.android.tools.r8.internal.Y7;
import com.android.tools.r8.internal.YJ;
import com.android.tools.r8.internal.Z6;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.MethodPosition;
import com.android.tools.r8.position.Position;
import com.android.tools.r8.position.TextPosition;
import com.android.tools.r8.position.TextRange;
import com.android.tools.r8.references.MethodReference;
import com.android.tools.r8.utils.C3970u0;
import com.android.tools.r8.utils.StringDiagnostic;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.function.Function;
import java.util.function.Supplier;
import net.bytebuddy.implementation.auxiliary.TypeProxy;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class Y3 extends AbstractC1899eF {
    static final /* synthetic */ boolean D = true;
    private int A;
    private int B;
    private final Supplier C;
    private final M3 j;
    private final C0722p1 k;
    private final V3 l;
    private int m;
    private int n;
    private boolean o;
    private ArrayList p;
    private ArrayList q;
    private ArrayList r;
    private final HashMap s;
    private M7 t;
    private C2732or u;
    private IdentityHashMap v;
    private IdentityHashMap w;
    private final Z3 x;
    private final C0684j2 y;
    private final Origin z;

    Y3(M3 m3, C0684j2 c0684j2, Z3 z3, Origin origin, V3 v3, Supplier supplier) {
        super(589824);
        this.s = new HashMap();
        this.A = Integer.MAX_VALUE;
        this.B = -1;
        this.l = v3;
        this.j = m3;
        this.k = m3.a();
        this.x = z3;
        this.y = c0684j2;
        this.z = origin;
        this.C = supplier;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ X a(X x, X x2) {
        return x;
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void b() {
        this.m = 0;
        this.n = 0;
        this.p = new ArrayList();
        this.q = new ArrayList();
        this.r = new ArrayList();
        this.t = null;
        this.u = new C2732or(16);
        this.v = new IdentityHashMap();
        this.w = new IdentityHashMap();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void c() {
        if (this.p == null) {
            if (!D && (this.q != null || this.r != null || this.v != null)) {
                throw new AssertionError();
            }
            Origin origin = this.z;
            MethodReference methodReferenceE0 = this.y.E0();
            int i = this.A;
            throw new C1367Tb("Absent Code attribute in method that is not native or abstract", null, origin, MethodPosition.create(methodReferenceE0, i != Integer.MAX_VALUE ? i == this.B ? new TextPosition(0L, this.A, -1) : new TextRange(new TextPosition(0L, this.A, -1), new TextPosition(0L, this.B, -1)) : Position.UNKNOWN));
        }
        InterfaceC0993Fr interfaceC0993FrListIterator = this.u.listIterator(0);
        while (interfaceC0993FrListIterator.hasNext()) {
            int iIntValue = ((Integer) interfaceC0993FrListIterator.next()).intValue();
            B7 b7 = (B7) this.p.get(iIntValue);
            if (!D && !b7.G()) {
                throw new AssertionError();
            }
            C2284j7 c2284j7I = b7.i();
            C2284j7.a aVarA = C2284j7.T().a(c2284j7I.W());
            YJ it = c2284j7I.W().b().iterator();
            while (it.hasNext()) {
                InterfaceC2806pp interfaceC2806pp = (InterfaceC2806pp) it.next();
                InterfaceC1348Si interfaceC1348Si = (InterfaceC1348Si) interfaceC2806pp.getValue();
                if (interfaceC1348Si.y() && interfaceC1348Si.D() == null) {
                    C1733c10 c1733c10Z = interfaceC1348Si.z();
                    M7 m7R = c1733c10Z.R();
                    V7 v7 = (V7) this.w.get(m7R);
                    if (v7 != null) {
                        c1733c10Z = new C1733c10(m7R, v7.getType());
                    }
                    interfaceC2806pp.setValue(c1733c10Z);
                }
            }
            for (InterfaceC3054tM interfaceC3054tM : c2284j7I.Z()) {
                if (interfaceC3054tM.y() && interfaceC3054tM.D() == null) {
                    C1733c10 c1733c10Z2 = interfaceC3054tM.z();
                    M7 m7R2 = c1733c10Z2.R();
                    V7 v8 = (V7) this.w.get(m7R2);
                    if (v8 != null) {
                        c1733c10Z2 = new C1733c10(m7R2, v8.getType());
                    }
                    aVarA.a((InterfaceC3054tM) c1733c10Z2);
                } else {
                    aVarA.a(interfaceC3054tM);
                }
            }
            this.p.set(iIntValue, aVarA.a());
        }
        Z3 z3 = this.x;
        C0767v2 c0767v2 = this.y.e;
        int i2 = this.o ? Integer.MAX_VALUE : this.m;
        int i3 = this.n;
        ArrayList arrayList = this.p;
        ArrayList arrayList2 = this.q;
        ArrayList arrayList3 = this.r;
        int i4 = this.A;
        C0753u c0753u = new C0753u(c0767v2, i2, i3, arrayList, arrayList2, arrayList3, i4 == Integer.MAX_VALUE ? Position.UNKNOWN : i4 == this.B ? new TextPosition(0L, this.A, -1) : new TextRange(new TextPosition(0L, this.A, -1), new TextPosition(0L, this.B, -1)), C2049g6.a());
        boolean z = Z3.i;
        if (!z && z3.f != null) {
            throw new AssertionError();
        }
        if (!z && z3.g == null) {
            throw new AssertionError();
        }
        z3.f = c0753u;
        z3.g = null;
        z3.e = null;
    }

    /* JADX WARN: Code duplicated, block: B:10:0x0025  */
    /* JADX WARN: Code duplicated, block: B:11:0x0028  */
    /* JADX WARN: Code duplicated, block: B:12:0x002b  */
    /* JADX WARN: Code duplicated, block: B:15:0x0031  */
    /* JADX WARN: Code duplicated, block: B:8:0x001f  */
    /* JADX WARN: Code duplicated, block: B:9:0x0022  */
    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void d(int i, int i2) {
        EnumC1812d20 enumC1812d20;
        if (i == 169) {
            a((B7) new K7(i2));
            return;
        }
        switch (i) {
            case 21:
                enumC1812d20 = EnumC1812d20.INT;
                if (21 <= i || i > 25) {
                    a((B7) new R8(enumC1812d20, i2));
                    return;
                } else {
                    a((B7) new O7(enumC1812d20, i2));
                    return;
                }
            case 22:
                enumC1812d20 = EnumC1812d20.LONG;
                if (21 <= i) {
                    break;
                }
                a((B7) new R8(enumC1812d20, i2));
                return;
            case 23:
                enumC1812d20 = EnumC1812d20.FLOAT;
                if (21 <= i) {
                    break;
                }
                a((B7) new R8(enumC1812d20, i2));
                return;
            case 24:
                enumC1812d20 = EnumC1812d20.DOUBLE;
                if (21 <= i) {
                    break;
                }
                a((B7) new R8(enumC1812d20, i2));
                return;
            case 25:
                enumC1812d20 = EnumC1812d20.OBJECT;
                if (21 <= i) {
                    break;
                }
                a((B7) new R8(enumC1812d20, i2));
                return;
            default:
                switch (i) {
                    case 54:
                        enumC1812d20 = EnumC1812d20.INT;
                        break;
                    case 55:
                        enumC1812d20 = EnumC1812d20.LONG;
                        break;
                    case 56:
                        enumC1812d20 = EnumC1812d20.FLOAT;
                        break;
                    case 57:
                        enumC1812d20 = EnumC1812d20.DOUBLE;
                        break;
                    case 58:
                        enumC1812d20 = EnumC1812d20.OBJECT;
                        break;
                    default:
                        throw new C3405y10("Unexpected VarInsn opcode: " + i);
                }
                if (21 <= i) {
                    break;
                }
                a((B7) new R8(enumC1812d20, i2));
                return;
        }
    }

    private void a(B7 b7) {
        this.p.add(b7);
        if (b7.G() || (b7 instanceof C2051g8)) {
            return;
        }
        this.t = null;
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(int i, int i2, Object[] objArr, int i3, Object[] objArr2) {
        if (!D && i != -1) {
            throw new AssertionError();
        }
        C2284j7.a aVarT = C2284j7.T();
        for (int i4 = 0; i4 < i2; i4++) {
            aVarT.a(a(objArr[i4], aVarT));
        }
        aVarT.a(i3);
        for (int i5 = 0; i5 < i3; i5++) {
            InterfaceC1348Si interfaceC1348SiA = a(objArr2[i5], aVarT);
            if (interfaceC1348SiA.J()) {
                aVarT.a(interfaceC1348SiA.F());
            } else {
                this.j.a.e.warning(new StringDiagnostic("Unexpected frame with imprecise value on stack", this.z));
                return;
            }
        }
        if (aVarT.c()) {
            this.u.a(this.p.size());
        }
        a((B7) aVarT.a());
    }

    private M7 b(C3263wA c3263wA) {
        return (M7) this.v.computeIfAbsent(c3263wA, new Function() { // from class: com.android.tools.r8.graph.Y3$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Y3.c((C3263wA) obj);
            }
        });
    }

    private C0767v2 b(Object obj) {
        boolean z = D;
        if (!z && (obj == null || obj == JK.a)) {
            throw new AssertionError();
        }
        if (obj == JK.b) {
            return this.k.A1;
        }
        if (obj == JK.c) {
            return this.k.z1;
        }
        if (obj == JK.e) {
            return this.k.B1;
        }
        if (obj == JK.d) {
            return this.k.y1;
        }
        if (obj == JK.f) {
            return C0722p1.S5;
        }
        if (obj instanceof String) {
            String str = (String) obj;
            if (z || str.indexOf(46) == -1) {
                return this.k.e(CZ.d(str).b());
            }
            throw new AssertionError();
        }
        throw new C3405y10("Unexpected ASM type: " + obj);
    }

    private InterfaceC1348Si a(Object obj, C2284j7.a aVar) {
        if (obj instanceof C3263wA) {
            M7 m7B = b((C3263wA) obj);
            V7 v7 = (V7) this.w.get(m7B);
            if (v7 != null) {
                C0767v2 type = v7.getType();
                int i = InterfaceC1348Si.a;
                return new C1733c10(m7B, type);
            }
            aVar.d();
            int i2 = InterfaceC1348Si.a;
            return new C1733c10(m7B, null);
        }
        if (obj == JK.g) {
            return InterfaceC1348Si.g();
        }
        if (obj != null && obj != JK.a) {
            return InterfaceC1348Si.a(b(obj));
        }
        return InterfaceC1348Si.B();
    }

    /* JADX WARN: Code duplicated, block: B:10:0x0021  */
    /* JADX WARN: Code duplicated, block: B:12:0x0024  */
    /* JADX WARN: Code duplicated, block: B:14:0x0027  */
    /* JADX WARN: Code duplicated, block: B:16:0x002a  */
    /* JADX WARN: Code duplicated, block: B:18:0x002d  */
    /* JADX WARN: Code duplicated, block: B:20:0x0030  */
    /* JADX WARN: Code duplicated, block: B:6:0x001b  */
    /* JADX WARN: Code duplicated, block: B:8:0x001e  */
    private static EnumC2523mE b(int i) {
        switch (i) {
            case 46:
                return EnumC2523mE.INT;
            case 47:
                return EnumC2523mE.LONG;
            case 48:
                return EnumC2523mE.FLOAT;
            case 49:
                return EnumC2523mE.DOUBLE;
            case 50:
                return EnumC2523mE.OBJECT;
            case 51:
                return EnumC2523mE.BOOLEAN_OR_BYTE;
            case 52:
                return EnumC2523mE.CHAR;
            case 53:
                return EnumC2523mE.SHORT;
            default:
                switch (i) {
                    case 79:
                        return EnumC2523mE.INT;
                    case 80:
                        return EnumC2523mE.LONG;
                    case 81:
                        return EnumC2523mE.FLOAT;
                    case 82:
                        return EnumC2523mE.DOUBLE;
                    case 83:
                        return EnumC2523mE.OBJECT;
                    case 84:
                        return EnumC2523mE.BOOLEAN_OR_BYTE;
                    case 85:
                        return EnumC2523mE.CHAR;
                    case 86:
                        return EnumC2523mE.SHORT;
                    default:
                        throw new C3405y10("Unexpected array opcode " + i);
                }
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(int i) {
        if (i == 190) {
            a((B7) new A6());
            return;
        }
        if (i == 191) {
            a((B7) new V8());
            return;
        }
        if (i == 194) {
            a((B7) new R7(EnumC3423yF.ENTER));
            return;
        }
        if (i != 195) {
            switch (i) {
                case 0:
                    a((B7) new Y7());
                    return;
                case 1:
                    a((B7) new C1668b7());
                    return;
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                    a((B7) new C1818d7(i - 3, EnumC1812d20.INT));
                    return;
                case 9:
                case 10:
                    a((B7) new C1818d7(i - 9, EnumC1812d20.LONG));
                    return;
                case 11:
                case 12:
                case 13:
                    a((B7) new C1818d7(Float.floatToRawIntBits(i - 11), EnumC1812d20.FLOAT));
                    return;
                case 14:
                case 15:
                    a((B7) new C1818d7(Double.doubleToRawLongBits(i - 14), EnumC1812d20.DOUBLE));
                    return;
                default:
                    switch (i) {
                        case 46:
                        case 47:
                        case 48:
                        case 49:
                        case 50:
                        case 51:
                        case 52:
                        case 53:
                            a((B7) new B6(b(i)));
                            return;
                        default:
                            switch (i) {
                                case 79:
                                case 80:
                                case 81:
                                case 82:
                                case 83:
                                case 84:
                                case 85:
                                case 86:
                                    a((B7) new D6(b(i)));
                                    return;
                                case 87:
                                case 88:
                                case 89:
                                case 90:
                                case 91:
                                case 92:
                                case 93:
                                case 94:
                                case 95:
                                    a((B7) D8.a(i));
                                    return;
                                case 96:
                                case 97:
                                case 98:
                                case 99:
                                case 100:
                                case 101:
                                case 102:
                                case 103:
                                case 104:
                                case 105:
                                case 106:
                                case 107:
                                case 108:
                                case 109:
                                case 110:
                                case 111:
                                case 112:
                                case 113:
                                case 114:
                                case 115:
                                    a((B7) C3481z6.a(i));
                                    return;
                                case 116:
                                case 117:
                                case 118:
                                case 119:
                                    a((B7) U7.a(i));
                                    return;
                                case 120:
                                case 121:
                                case 122:
                                case 123:
                                case 124:
                                case 125:
                                case 126:
                                case 127:
                                case 128:
                                case 129:
                                case 130:
                                case 131:
                                    a((B7) Q7.a(i));
                                    return;
                                default:
                                    switch (i) {
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
                                            a((B7) C1591a8.a(i));
                                            return;
                                        case 148:
                                        case 149:
                                        case 150:
                                        case 151:
                                        case 152:
                                            a((B7) U6.a(i));
                                            return;
                                        default:
                                            switch (i) {
                                                case 172:
                                                    a((B7) new C3109u8(EnumC1812d20.INT));
                                                    return;
                                                case 173:
                                                    a((B7) new C3109u8(EnumC1812d20.LONG));
                                                    return;
                                                case 174:
                                                    a((B7) new C3109u8(EnumC1812d20.FLOAT));
                                                    return;
                                                case 175:
                                                    a((B7) new C3109u8(EnumC1812d20.DOUBLE));
                                                    return;
                                                case 176:
                                                    a((B7) new C3109u8(EnumC1812d20.OBJECT));
                                                    return;
                                                case 177:
                                                    a((B7) new C3186v8());
                                                    return;
                                                default:
                                                    throw new C3405y10("Unknown instruction");
                                            }
                                    }
                            }
                    }
            }
        }
        a((B7) new R7(EnumC3423yF.EXIT));
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void b(int i, int i2) {
        if (i == 16 || i == 17) {
            a((B7) new C1818d7(i2, EnumC1812d20.INT));
        } else {
            if (i == 188) {
                C0722p1 c0722p1 = this.k;
                a((B7) new W7(c0722p1.a(1, a(i2, c0722p1))));
                return;
            }
            throw new C3405y10("Unexpected int opcode " + i);
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void b(int i, C3263wA c3263wA) {
        this.A = Math.min(i, this.A);
        this.B = Math.max(i, this.B);
        if (this.l.a) {
            a((B7) new C2051g8(b(c3263wA), AbstractC1915eM.b.x().a(i).a(this.y).a()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ M7 c(C3263wA c3263wA) {
        return new M7();
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void c(int i, String str) {
        C0767v2 c0767v2E = this.k.e(CZ.d(str).b());
        if (i == 187) {
            V7 v7 = new V7(this.t, c0767v2E);
            if (v7.U()) {
                this.w.put(v7.T(), v7);
            }
            a((B7) v7);
            return;
        }
        if (i == 189) {
            a(new W7(this.k.a(1, c0767v2E)));
        } else if (i == 192) {
            a(new M6(c0767v2E));
        } else {
            if (i == 193) {
                a(new A7(c0767v2E));
                return;
            }
            throw new C3405y10("Unexpected TypeInsn opcode: " + i);
        }
    }

    private static EnumC1076Il c(int i) {
        switch (i) {
            case 153:
            case 159:
            case 165:
                return EnumC1076Il.EQ;
            case 154:
            case 160:
            case 166:
                return EnumC1076Il.NE;
            case 155:
            case 161:
                return EnumC1076Il.LT;
            case 156:
            case 162:
                return EnumC1076Il.GE;
            case 157:
            case 163:
                return EnumC1076Il.GT;
            case 158:
            case 164:
                return EnumC1076Il.LE;
            default:
                throw new C3405y10("Unexpected If instruction opcode: " + i);
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void c(int i, int i2) {
        boolean z = D;
        if (!z && i < 0) {
            throw new AssertionError();
        }
        if (!z && i2 < 0) {
            throw new AssertionError();
        }
        this.m = i;
        this.n = i2;
    }

    private static C0767v2 a(int i, C0722p1 c0722p1) {
        switch (i) {
            case 4:
                return c0722p1.v1;
            case 5:
                return c0722p1.x1;
            case 6:
                return c0722p1.z1;
            case 7:
                return c0722p1.y1;
            case 8:
                return c0722p1.w1;
            case 9:
                return c0722p1.C1;
            case 10:
                return c0722p1.A1;
            case 11:
                return c0722p1.B1;
            default:
                throw new C3405y10("Unexpected array-type code " + i);
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(int i, String str, String str2, String str3) {
        C0722p1 c0722p1 = this.k;
        if (!D && str.indexOf(46) != -1) {
            throw new AssertionError();
        }
        Y0 y0A = c0722p1.a(this.k.e(CZ.d(str).b()), this.k.e(str3), str2);
        a((B7) AbstractC2206i7.a(i, y0A, y0A));
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(int i, String str, String str2, String str3, boolean z) {
        M3 m3 = this.j;
        C0684j2 c0684j2B = m3.b(m3.f(str), str2, str3);
        if (!this.j.a.a.a(c0684j2B)) {
            a((B7) new I7(i, c0684j2B, z));
            return;
        }
        throw new C1367Tb("Invalid input code with a call to <clinit>");
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(String str, String str2, C1944ek c1944ek, Object... objArr) {
        a((B7) new J7(C0721p0.a(this.j, this.y.e, str, str2, c1944ek, objArr)));
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(int i, C3263wA c3263wA) {
        EnumC1812d20 enumC1812d20;
        M7 m7B = b(c3263wA);
        if (153 <= i && i <= 166) {
            if (i <= 158) {
                a((B7) new C3039t7(c(i), EnumC1812d20.INT, m7B));
                return;
            }
            if (i <= 164) {
                enumC1812d20 = EnumC1812d20.INT;
            } else {
                enumC1812d20 = EnumC1812d20.OBJECT;
            }
            a((B7) new C3185v7(c(i), enumC1812d20, m7B));
            return;
        }
        if (i == 167) {
            a((B7) new C2899r7(m7B));
        } else {
            if (i == 168) {
                throw new X3();
            }
            if (i != 198 && i != 199) {
                throw new C3405y10("Unexpected JumpInsn opcode: " + i);
            }
            a((B7) new C3039t7(i == 198 ? EnumC1076Il.EQ : EnumC1076Il.NE, EnumC1812d20.OBJECT, m7B));
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(C3263wA c3263wA) {
        M7 m7B = b(c3263wA);
        a((B7) m7B);
        this.t = m7B;
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(Object obj) {
        if (obj instanceof CZ) {
            CZ cz = (CZ) obj;
            if (cz.e() == 11) {
                a((B7) new C1590a7(this.j.c(cz.b())));
                return;
            } else {
                a((B7) new X6(this.k.e(cz.b())));
                return;
            }
        }
        if (obj instanceof String) {
            a((B7) new C1891e7(this.k.c((String) obj)));
            return;
        }
        if (obj instanceof Long) {
            a((B7) new C1818d7(((Long) obj).longValue(), EnumC1812d20.LONG));
            return;
        }
        if (obj instanceof Double) {
            a((B7) new C1818d7(Double.doubleToRawLongBits(((Double) obj).doubleValue()), EnumC1812d20.DOUBLE));
            return;
        }
        if (obj instanceof Integer) {
            a((B7) new C1818d7(((Integer) obj).intValue(), EnumC1812d20.INT));
            return;
        }
        if (obj instanceof Float) {
            a((B7) new C1818d7(Float.floatToRawIntBits(((Float) obj).floatValue()), EnumC1812d20.FLOAT));
            return;
        }
        if (obj instanceof C1944ek) {
            a((B7) new Z6(C0699l2.a((C1944ek) obj, this.j, this.y.e)));
            return;
        }
        if (obj instanceof C1064Ic) {
            CR cr = (CR) this.C.get();
            int iIntValue = ((Integer) cr.getOrDefault(obj, -1)).intValue();
            if (iIntValue == -1) {
                iIntValue = cr.size();
                cr.a(iIntValue, (C1064Ic) obj);
            }
            a((B7) Y6.a(iIntValue, (C1064Ic) obj, this.j, this.y.e));
            return;
        }
        throw new C1367Tb("Unsupported constant: " + obj.toString());
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(int i, int i2) {
        a((B7) new C3260w7(i, i2));
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(int i, int i2, C3263wA c3263wA, C3263wA... c3263wAArr) {
        if (!D && i2 != (c3263wAArr.length + i) - 1) {
            throw new AssertionError();
        }
        ArrayList arrayList = new ArrayList(c3263wAArr.length);
        for (C3263wA c3263wA2 : c3263wAArr) {
            arrayList.add(b(c3263wA2));
        }
        a((B7) new U8(U8.a.TABLE, b(c3263wA), new int[]{i}, arrayList));
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(C3263wA c3263wA, int[] iArr, C3263wA[] c3263wAArr) {
        ArrayList arrayList = new ArrayList(c3263wAArr.length);
        for (C3263wA c3263wA2 : c3263wAArr) {
            arrayList.add(b(c3263wA2));
        }
        a((B7) new U8(U8.a.LOOKUP, b(c3263wA), iArr, arrayList));
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(int i, String str) {
        com.android.tools.r8.utils.A1 a1 = this.j.a;
        if ((a1.f instanceof ClassFileConsumer) && !a1.n1.K) {
            a((B7) new S7(i, this.k.e(str)));
            return;
        }
        a(Integer.valueOf(i));
        b(188, 10);
        for (int i2 = i - 1; i2 >= 0; i2--) {
            a(90);
            a(95);
            a(Integer.valueOf(i2));
            a(95);
            a(79);
        }
        String strSubstring = str.substring(i);
        int i3 = C3970u0.c;
        if (strSubstring.length() != 1 ? false : C3970u0.a(strSubstring.charAt(0))) {
            a(178, C3970u0.b(strSubstring.charAt(0)), "TYPE", "Ljava/lang/Class;");
        } else if (C3970u0.G(strSubstring)) {
            a(178, "java/lang/Void", "TYPE", "Ljava/lang/Class;");
        } else {
            a(CZ.f(strSubstring));
        }
        a(95);
        a(184, "java/lang/reflect/Array", TypeProxy.SilentConstruction.Appender.NEW_INSTANCE_METHOD_NAME, "(Ljava/lang/Class;[I)Ljava/lang/Object;", false);
        c(192, str);
        this.o = true;
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(C3263wA c3263wA, C3263wA c3263wA2, C3263wA c3263wA3, String str) {
        C0767v2 c0767v2E;
        if (str == null) {
            c0767v2E = this.k.m3;
        } else {
            if (!D && str.indexOf(46) != -1) {
                throw new AssertionError();
            }
            c0767v2E = this.k.e(CZ.d(str).b());
        }
        this.q.add(new W8(b(c3263wA), b(c3263wA2), Collections.singletonList(c0767v2E), Collections.singletonList(b(c3263wA3))));
    }

    @Override // com.android.tools.r8.internal.AbstractC1899eF
    public final void a(String str, String str2, String str3, C3263wA c3263wA, C3263wA c3263wA2, int i) {
        if (this.l.b) {
            X xA = a(new X(this.k.c(str), str3 == null ? null : this.k.c(str3), this.k.e(str2)));
            ArrayList arrayList = this.r;
            M7 m7B = b(c3263wA);
            M7 m7B2 = b(c3263wA2);
            C0753u.a aVar = new C0753u.a(i, xA, m7B);
            aVar.a(m7B2);
            arrayList.add(aVar);
        }
    }

    private X a(final X x) {
        return (X) this.s.computeIfAbsent(x, new Function() { // from class: com.android.tools.r8.graph.Y3$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Y3.a(x, (X) obj);
            }
        });
    }
}
