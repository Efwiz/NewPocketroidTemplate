package com.android.tools.r8.internal;

import io.netty.util.internal.StringUtil;
import java.util.ArrayList;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Oi, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1238Oi {
    private R10 a;
    private R10[] b;
    private int c;
    private int d;
    private int e;

    public C1238Oi(int i, int i2) {
        this.b = new R10[(i2 >= 0 ? i2 : 4) + i];
        this.c = i;
        this.d = 0;
        this.e = i2 < 0 ? 65536 : i2;
    }

    public final C1238Oi a(C1238Oi c1238Oi) {
        this.a = c1238Oi.a;
        R10[] r10Arr = this.b;
        int length = r10Arr.length;
        R10[] r10Arr2 = c1238Oi.b;
        if (length < r10Arr2.length) {
            this.b = (R10[]) r10Arr2.clone();
        } else {
            System.arraycopy(r10Arr2, 0, r10Arr, 0, r10Arr2.length);
        }
        this.c = c1238Oi.c;
        this.d = c1238Oi.d;
        this.e = c1238Oi.e;
        return this;
    }

    public final void b(E4 e4) {
        this.a = e4;
    }

    public final int c() {
        return this.d;
    }

    public final R10 d() {
        int i = this.d;
        if (i == 0) {
            throw new IndexOutOfBoundsException("Cannot pop operand off an empty stack.");
        }
        R10[] r10Arr = this.b;
        int i2 = this.c;
        int i3 = i - 1;
        this.d = i3;
        return r10Arr[i2 + i3];
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.c; i++) {
            sb.append(a(i));
        }
        sb.append(StringUtil.SPACE);
        for (int i2 = 0; i2 < this.d; i2++) {
            sb.append(b(i2).toString());
        }
        return sb.toString();
    }

    public final int b() {
        return this.c;
    }

    public final R10 b(int i) {
        return this.b[this.c + i];
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public C1238Oi(C1238Oi c1238Oi) {
        int i = c1238Oi.c;
        this(i, c1238Oi.b.length - i);
        a(c1238Oi);
    }

    public final R10 a(int i) {
        if (i < this.c) {
            return this.b[i];
        }
        throw new IndexOutOfBoundsException(AbstractC1857dg.a("Trying to get an inexistant local variable ", i));
    }

    public final void a(int i, E4 e4) {
        if (i < this.c) {
            this.b[i] = e4;
            return;
        }
        throw new IndexOutOfBoundsException(AbstractC1857dg.a("Trying to set an inexistant local variable ", i));
    }

    public final void a() {
        this.d = 0;
    }

    public final void a(E4 e4) {
        int i = this.c + this.d;
        R10[] r10Arr = this.b;
        if (i >= r10Arr.length) {
            if (i < this.e) {
                R10[] r10Arr2 = new R10[r10Arr.length * 2];
                this.b = r10Arr2;
                System.arraycopy(r10Arr, 0, r10Arr2, 0, r10Arr.length);
            } else {
                throw new IndexOutOfBoundsException("Insufficient maximum stack size.");
            }
        }
        R10[] r10Arr3 = this.b;
        int i2 = this.c;
        int i3 = this.d;
        this.d = i3 + 1;
        r10Arr3[i2 + i3] = e4;
    }

    public final void a(AbstractC3097u abstractC3097u, AbstractC3462ys abstractC3462ys) throws W1 {
        int i;
        R10 r10A;
        E4 e4A;
        E4 e4;
        int iB = abstractC3097u.b();
        switch (iB) {
            case 0:
                return;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
                a(abstractC3462ys.a(abstractC3097u));
                return;
            default:
                switch (iB) {
                    case 21:
                    case 22:
                    case 23:
                    case 24:
                    case 25:
                        a(abstractC3462ys.a(abstractC3097u, a(((C2200i20) abstractC3097u).g)));
                        return;
                    default:
                        switch (iB) {
                            case 46:
                            case 47:
                            case 48:
                            case 49:
                            case 50:
                            case 51:
                            case 52:
                            case 53:
                                break;
                            case 54:
                            case 55:
                            case 56:
                            case 57:
                            case 58:
                                E4 e4A2 = abstractC3462ys.a(abstractC3097u, d());
                                int i2 = ((C2200i20) abstractC3097u).g;
                                a(i2, e4A2);
                                if (e4A2.a() == 2) {
                                    a(i2 + 1, F4.a((CZ) null));
                                }
                                if (i2 <= 0 || (r10A = a((i = i2 - 1))) == null || ((E4) r10A).a() != 2) {
                                    return;
                                }
                                a(i, F4.a((CZ) null));
                                return;
                            default:
                                switch (iB) {
                                    case 79:
                                    case 80:
                                    case 81:
                                    case 82:
                                    case 83:
                                    case 84:
                                    case 85:
                                    case 86:
                                        R10 r10D = d();
                                        R10 r10D2 = d();
                                        R10 r10D3 = d();
                                        ((F4) abstractC3462ys).getClass();
                                        E4 e5 = (E4) r10D3;
                                        E4 e6 = (E4) r10D2;
                                        E4 e7 = (E4) r10D;
                                        switch (abstractC3097u.b()) {
                                            case 79:
                                                e4A = F4.a(CZ.f("[I"));
                                                e4 = E4.c;
                                                break;
                                            case 80:
                                                e4A = F4.a(CZ.f("[J"));
                                                e4 = E4.e;
                                                break;
                                            case 81:
                                                e4A = F4.a(CZ.f("[F"));
                                                e4 = E4.d;
                                                break;
                                            case 82:
                                                e4A = F4.a(CZ.f("[D"));
                                                e4 = E4.f;
                                                break;
                                            case 83:
                                                e4 = E4.g;
                                                e4A = e5;
                                                break;
                                            case 84:
                                                if (e5.equals(F4.a(CZ.f("[Z")))) {
                                                    e4A = F4.a(CZ.f("[Z"));
                                                } else {
                                                    e4A = F4.a(CZ.f("[B"));
                                                }
                                                e4 = E4.c;
                                                break;
                                            case 85:
                                                e4A = F4.a(CZ.f("[C"));
                                                e4 = E4.c;
                                                break;
                                            case 86:
                                                e4A = F4.a(CZ.f("[S"));
                                                e4 = E4.c;
                                                break;
                                            default:
                                                throw new AssertionError();
                                        }
                                        if (e5.equals(e4A)) {
                                            E4 e8 = E4.c;
                                            if (e8.equals(e6)) {
                                                if (!e7.equals(e4)) {
                                                    throw new W1(abstractC3097u, "Third argument", e4, e7);
                                                }
                                                return;
                                            }
                                            throw new W1(abstractC3097u, "Second argument", e8, e6);
                                        }
                                        throw new W1(abstractC3097u, "First argument", "a " + e4A + " array reference", e5);
                                    case 87:
                                        if (((E4) d()).a() == 2) {
                                            throw new W1(abstractC3097u, "Illegal use of POP");
                                        }
                                        return;
                                    case 88:
                                        if (((E4) d()).a() == 1 && ((E4) d()).a() != 1) {
                                            throw new W1(abstractC3097u, "Illegal use of POP2");
                                        }
                                        return;
                                    case 89:
                                        E4 e9 = (E4) d();
                                        if (e9.a() == 1) {
                                            a(e9);
                                            a(abstractC3462ys.a(abstractC3097u, e9));
                                            return;
                                        }
                                        throw new W1(abstractC3097u, "Illegal use of DUP");
                                    case 90:
                                        R10 r10D4 = d();
                                        R10 r10D5 = d();
                                        E4 e10 = (E4) r10D4;
                                        if (e10.a() == 1) {
                                            E4 e11 = (E4) r10D5;
                                            if (e11.a() == 1) {
                                                a(abstractC3462ys.a(abstractC3097u, e10));
                                                a(e11);
                                                a(e10);
                                                return;
                                            }
                                        }
                                        throw new W1(abstractC3097u, "Illegal use of DUP_X1");
                                    case 91:
                                        E4 e12 = (E4) d();
                                        if (e12.a() == 1) {
                                            E4 e13 = (E4) d();
                                            if (e13.a() == 1) {
                                                E4 e14 = (E4) d();
                                                if (e14.a() == 1) {
                                                    a(abstractC3462ys.a(abstractC3097u, e12));
                                                    a(e14);
                                                    a(e13);
                                                    a(e12);
                                                    return;
                                                }
                                            } else {
                                                a(abstractC3462ys.a(abstractC3097u, e12));
                                                a(e13);
                                                a(e12);
                                                return;
                                            }
                                        }
                                        throw new W1(abstractC3097u, "Illegal use of DUP_X2");
                                    case 92:
                                        E4 e15 = (E4) d();
                                        if (e15.a() == 1) {
                                            E4 e16 = (E4) d();
                                            if (e16.a() == 1) {
                                                a(e16);
                                                a(e15);
                                                a(abstractC3462ys.a(abstractC3097u, e16));
                                                a(abstractC3462ys.a(abstractC3097u, e15));
                                                return;
                                            }
                                            throw new W1(abstractC3097u, "Illegal use of DUP2");
                                        }
                                        a(e15);
                                        a(abstractC3462ys.a(abstractC3097u, e15));
                                        return;
                                    case 93:
                                        E4 e17 = (E4) d();
                                        if (e17.a() == 1) {
                                            E4 e18 = (E4) d();
                                            if (e18.a() == 1) {
                                                E4 e19 = (E4) d();
                                                if (e19.a() == 1) {
                                                    a(abstractC3462ys.a(abstractC3097u, e18));
                                                    a(abstractC3462ys.a(abstractC3097u, e17));
                                                    a(e19);
                                                    a(e18);
                                                    a(e17);
                                                    return;
                                                }
                                            }
                                        } else {
                                            E4 e20 = (E4) d();
                                            if (e20.a() == 1) {
                                                a(abstractC3462ys.a(abstractC3097u, e17));
                                                a(e20);
                                                a(e17);
                                                return;
                                            }
                                        }
                                        throw new W1(abstractC3097u, "Illegal use of DUP2_X1");
                                    case 94:
                                        E4 e21 = (E4) d();
                                        if (e21.a() == 1) {
                                            E4 e22 = (E4) d();
                                            if (e22.a() == 1) {
                                                E4 e23 = (E4) d();
                                                if (e23.a() == 1) {
                                                    E4 e24 = (E4) d();
                                                    if (e24.a() == 1) {
                                                        a(abstractC3462ys.a(abstractC3097u, e22));
                                                        a(abstractC3462ys.a(abstractC3097u, e21));
                                                        a(e24);
                                                        a(e23);
                                                        a(e22);
                                                        a(e21);
                                                        return;
                                                    }
                                                } else {
                                                    a(abstractC3462ys.a(abstractC3097u, e22));
                                                    a(abstractC3462ys.a(abstractC3097u, e21));
                                                    a(e23);
                                                    a(e22);
                                                    a(e21);
                                                    return;
                                                }
                                            }
                                        } else {
                                            E4 e25 = (E4) d();
                                            if (e25.a() == 1) {
                                                E4 e26 = (E4) d();
                                                if (e26.a() == 1) {
                                                    a(abstractC3462ys.a(abstractC3097u, e21));
                                                    a(e26);
                                                    a(e25);
                                                    a(e21);
                                                    return;
                                                }
                                            } else {
                                                a(abstractC3462ys.a(abstractC3097u, e21));
                                                a(e25);
                                                a(e21);
                                                return;
                                            }
                                        }
                                        throw new W1(abstractC3097u, "Illegal use of DUP2_X2");
                                    case 95:
                                        R10 r10D6 = d();
                                        E4 e27 = (E4) d();
                                        if (e27.a() == 1) {
                                            E4 e28 = (E4) r10D6;
                                            if (e28.a() == 1) {
                                                a(abstractC3462ys.a(abstractC3097u, e28));
                                                a(abstractC3462ys.a(abstractC3097u, e27));
                                                return;
                                            }
                                        }
                                        throw new W1(abstractC3097u, "Illegal use of SWAP");
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
                                    case 148:
                                    case 149:
                                    case 150:
                                    case 151:
                                    case 152:
                                        break;
                                    case 116:
                                    case 117:
                                    case 118:
                                    case 119:
                                        a(abstractC3462ys.b(abstractC3097u, d()));
                                        return;
                                    case 132:
                                        int i3 = ((C1134Kl) abstractC3097u).g;
                                        a(i3, abstractC3462ys.b(abstractC3097u, a(i3)));
                                        return;
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
                                        a(abstractC3462ys.b(abstractC3097u, d()));
                                        return;
                                    case 153:
                                    case 154:
                                    case 155:
                                    case 156:
                                    case 157:
                                    case 158:
                                        abstractC3462ys.b(abstractC3097u, d());
                                        return;
                                    case 159:
                                    case 160:
                                    case 161:
                                    case 162:
                                    case 163:
                                    case 164:
                                    case 165:
                                    case 166:
                                    case 181:
                                        abstractC3462ys.a(abstractC3097u, d(), d());
                                        return;
                                    case 167:
                                    case 169:
                                        return;
                                    case 168:
                                        a(abstractC3462ys.a(abstractC3097u));
                                        return;
                                    case 170:
                                    case 171:
                                        abstractC3462ys.b(abstractC3097u, d());
                                        return;
                                    case 172:
                                    case 173:
                                    case 174:
                                    case 175:
                                    case 176:
                                        R10 r10D7 = d();
                                        abstractC3462ys.b(abstractC3097u, r10D7);
                                        E4 e29 = (E4) r10D7;
                                        E4 e30 = (E4) this.a;
                                        if (!e29.equals(e30)) {
                                            throw new W1(abstractC3097u, "Incompatible return type", e30, e29);
                                        }
                                        return;
                                    case 177:
                                        if (this.a != null) {
                                            throw new W1(abstractC3097u, "Incompatible return type");
                                        }
                                        return;
                                    case 178:
                                        a(abstractC3462ys.a(abstractC3097u));
                                        return;
                                    case 179:
                                        abstractC3462ys.b(abstractC3097u, d());
                                        return;
                                    case 180:
                                        a(abstractC3462ys.b(abstractC3097u, d()));
                                        return;
                                    case 182:
                                    case 183:
                                    case 184:
                                    case 185:
                                        String str = ((HE) abstractC3097u).i;
                                        ArrayList arrayList = new ArrayList();
                                        for (int length = CZ.a(str).length; length > 0; length--) {
                                            arrayList.add(0, d());
                                        }
                                        if (abstractC3097u.b() != 184 && abstractC3097u.b() != 186) {
                                            arrayList.add(0, d());
                                        }
                                        if (CZ.e(str) == CZ.e) {
                                            abstractC3462ys.a(abstractC3097u, arrayList);
                                            return;
                                        } else {
                                            a(abstractC3462ys.a(abstractC3097u, arrayList));
                                            return;
                                        }
                                    case 186:
                                        String str2 = ((C1194Ms) abstractC3097u).h;
                                        ArrayList arrayList2 = new ArrayList();
                                        for (int length2 = CZ.a(str2).length; length2 > 0; length2--) {
                                            arrayList2.add(0, d());
                                        }
                                        if (abstractC3097u.b() != 184 && abstractC3097u.b() != 186) {
                                            arrayList2.add(0, d());
                                        }
                                        if (CZ.e(str2) == CZ.e) {
                                            abstractC3462ys.a(abstractC3097u, arrayList2);
                                            return;
                                        } else {
                                            a(abstractC3462ys.a(abstractC3097u, arrayList2));
                                            return;
                                        }
                                    case 187:
                                        a(abstractC3462ys.a(abstractC3097u));
                                        return;
                                    case 188:
                                    case 189:
                                    case 190:
                                        a(abstractC3462ys.b(abstractC3097u, d()));
                                        return;
                                    case 191:
                                        abstractC3462ys.b(abstractC3097u, d());
                                        return;
                                    case 192:
                                    case 193:
                                        a(abstractC3462ys.b(abstractC3097u, d()));
                                        return;
                                    case 194:
                                    case 195:
                                        abstractC3462ys.b(abstractC3097u, d());
                                        return;
                                    default:
                                        switch (iB) {
                                            case 197:
                                                ArrayList arrayList3 = new ArrayList();
                                                for (int i4 = ((DF) abstractC3097u).h; i4 > 0; i4--) {
                                                    arrayList3.add(0, d());
                                                }
                                                a(abstractC3462ys.a(abstractC3097u, arrayList3));
                                                return;
                                            case 198:
                                            case 199:
                                                abstractC3462ys.b(abstractC3097u, d());
                                                return;
                                            default:
                                                throw new W1(abstractC3097u, "Illegal opcode " + abstractC3097u.b());
                                        }
                                }
                                break;
                        }
                        a(abstractC3462ys.a(abstractC3097u, d(), d()));
                        return;
                }
        }
    }

    public final boolean a(C1238Oi c1238Oi, AbstractC3462ys abstractC3462ys) throws W1 {
        if (this.d == c1238Oi.d) {
            boolean z = false;
            for (int i = 0; i < this.c + this.d; i++) {
                R10 r10 = this.b[i];
                R10 r11 = c1238Oi.b[i];
                ((F4) abstractC3462ys).getClass();
                E4 e4 = (E4) r10;
                if (!e4.equals((E4) r11)) {
                    e4 = E4.b;
                }
                if (!e4.equals(this.b[i])) {
                    this.b[i] = e4;
                    z = true;
                }
            }
            return z;
        }
        throw new W1(null, "Incompatible stack heights");
    }

    public final void a(C1238Oi c1238Oi, boolean[] zArr) {
        for (int i = 0; i < this.c; i++) {
            if (!zArr[i] && !this.b[i].equals(c1238Oi.b[i])) {
                this.b[i] = c1238Oi.b[i];
            }
        }
    }
}
