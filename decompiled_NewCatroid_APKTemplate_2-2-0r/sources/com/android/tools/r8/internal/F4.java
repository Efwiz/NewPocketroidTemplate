package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Iterator;
import net.bytebuddy.implementation.auxiliary.TypeProxy;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class F4 extends AbstractC3462ys implements JK {
    public static final CZ h = CZ.d("null");

    /* JADX WARN: Code duplicated, block: B:24:0x00ab  */
    /* JADX WARN: Code duplicated, block: B:25:0x00ae  */
    /* JADX WARN: Code duplicated, block: B:26:0x00b1  */
    /* JADX WARN: Code duplicated, block: B:28:0x00b7  */
    /* JADX WARN: Code duplicated, block: B:48:0x010a  */
    /* JADX WARN: Code duplicated, block: B:49:0x010d  */
    /* JADX WARN: Code duplicated, block: B:50:0x0110  */
    /* JADX WARN: Code duplicated, block: B:51:0x0113  */
    @Override // com.android.tools.r8.internal.AbstractC3462ys
    public final E4 a(AbstractC3097u abstractC3097u, R10 r10, R10 r11) throws W1 {
        E4 e4A;
        E4 e4A2;
        E4 e4 = (E4) r10;
        E4 e5 = (E4) r11;
        int iB = abstractC3097u.b();
        if (iB != 181) {
            switch (iB) {
                case 46:
                    e4A2 = a(CZ.f("[I"));
                    e4A = E4.c;
                    break;
                case 47:
                    e4A2 = a(CZ.f("[J"));
                    e4A = E4.c;
                    break;
                case 48:
                    e4A2 = a(CZ.f("[F"));
                    e4A = E4.c;
                    break;
                case 49:
                    e4A2 = a(CZ.f("[D"));
                    e4A = E4.c;
                    break;
                case 50:
                    e4A2 = a(CZ.f("[Ljava/lang/Object;"));
                    e4A = E4.c;
                    break;
                case 51:
                    e4A2 = e4.equals(a(CZ.f("[Z"))) ? a(CZ.f("[Z")) : a(CZ.f("[B"));
                    e4A = E4.c;
                    break;
                case 52:
                    e4A2 = a(CZ.f("[C"));
                    e4A = E4.c;
                    break;
                case 53:
                    e4A2 = a(CZ.f("[S"));
                    e4A = E4.c;
                    break;
                default:
                    switch (iB) {
                        case 96:
                        case 100:
                        case 104:
                        case 108:
                        case 112:
                            e4A2 = E4.c;
                            e4A = e4A2;
                            break;
                        case 97:
                        case 101:
                        case 105:
                        case 109:
                        case 113:
                            e4A2 = E4.e;
                            e4A = e4A2;
                            break;
                        case 98:
                        case 102:
                        case 106:
                        case 110:
                        case 114:
                            e4A2 = E4.d;
                            e4A = e4A2;
                            break;
                        case 99:
                        case 103:
                        case 107:
                        case 111:
                        case 115:
                            e4A2 = E4.f;
                            e4A = e4A2;
                            break;
                        default:
                            switch (iB) {
                                case 120:
                                case 122:
                                case 124:
                                case 126:
                                case 128:
                                case 130:
                                    e4A2 = E4.c;
                                    e4A = e4A2;
                                    break;
                                case 121:
                                case 123:
                                case 125:
                                    e4A2 = E4.e;
                                    e4A = E4.c;
                                    break;
                                case 127:
                                case 129:
                                case 131:
                                    e4A2 = E4.e;
                                    e4A = e4A2;
                                    break;
                                default:
                                    switch (iB) {
                                        case 148:
                                            e4A2 = E4.e;
                                            e4A = e4A2;
                                            break;
                                        case 149:
                                        case 150:
                                            e4A2 = E4.d;
                                            e4A = e4A2;
                                            break;
                                        case 151:
                                        case 152:
                                            e4A2 = E4.f;
                                            e4A = e4A2;
                                            break;
                                        default:
                                            switch (iB) {
                                                case 159:
                                                case 160:
                                                case 161:
                                                case 162:
                                                case 163:
                                                case 164:
                                                    e4A2 = E4.c;
                                                    break;
                                                case 165:
                                                case 166:
                                                    e4A2 = E4.g;
                                                    break;
                                                default:
                                                    throw new AssertionError();
                                            }
                                            e4A = e4A2;
                                            break;
                                    }
                                    break;
                            }
                            break;
                    }
                    break;
            }
        } else {
            C1130Kh c1130Kh = (C1130Kh) abstractC3097u;
            E4 e4A3 = a(CZ.d(c1130Kh.g));
            e4A = a(CZ.f(c1130Kh.i));
            e4A2 = e4A3;
        }
        if (!e4.equals(e4A2)) {
            throw new W1(abstractC3097u, "First argument", e4A2, e4);
        }
        if (!e5.equals(e4A)) {
            throw new W1(abstractC3097u, "Second argument", e4A, e5);
        }
        if (abstractC3097u.b() == 50) {
            return E4.g;
        }
        int iB2 = abstractC3097u.b();
        if (iB2 != 181) {
            switch (iB2) {
                case 46:
                case 51:
                case 52:
                case 53:
                    return E4.c;
                case 47:
                    return E4.e;
                case 48:
                    return E4.d;
                case 49:
                    return E4.f;
                case 50:
                    return E4.g;
                default:
                    switch (iB2) {
                        case 96:
                        case 100:
                        case 104:
                        case 108:
                        case 112:
                            return E4.c;
                        case 97:
                        case 101:
                        case 105:
                        case 109:
                        case 113:
                            return E4.e;
                        case 98:
                        case 102:
                        case 106:
                        case 110:
                        case 114:
                            return E4.d;
                        case 99:
                        case 103:
                        case 107:
                        case 111:
                        case 115:
                            return E4.f;
                        default:
                            switch (iB2) {
                                case 120:
                                case 122:
                                case 124:
                                case 126:
                                case 128:
                                case 130:
                                    return E4.c;
                                case 121:
                                case 123:
                                case 125:
                                case 127:
                                case 129:
                                case 131:
                                    return E4.e;
                                default:
                                    switch (iB2) {
                                        case 148:
                                        case 149:
                                        case 150:
                                        case 151:
                                        case 152:
                                            return E4.c;
                                        default:
                                            switch (iB2) {
                                                case 159:
                                                case 160:
                                                case 161:
                                                case 162:
                                                case 163:
                                                case 164:
                                                case 165:
                                                case 166:
                                                    break;
                                                default:
                                                    throw new AssertionError();
                                            }
                                            break;
                                    }
                                    break;
                            }
                            break;
                    }
                    break;
            }
        }
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:17:0x002c  */
    /* JADX WARN: Code duplicated, block: B:18:0x002f  */
    /* JADX WARN: Code duplicated, block: B:19:0x0032  */
    /* JADX WARN: Code duplicated, block: B:25:0x0048  */
    @Override // com.android.tools.r8.internal.AbstractC3462ys
    public final E4 b(AbstractC3097u abstractC3097u, R10 r10) throws W1 {
        E4 e4A;
        E4 e4 = (E4) r10;
        int iB = abstractC3097u.b();
        if (iB == 179) {
            e4A = a(CZ.f(((C1130Kh) abstractC3097u).i));
        } else {
            if (iB != 180) {
                if (iB != 198 && iB != 199) {
                    switch (iB) {
                        case 116:
                            e4A = E4.c;
                            break;
                        case 117:
                            e4A = E4.e;
                            break;
                        case 118:
                            e4A = E4.d;
                            break;
                        case 119:
                            e4A = E4.f;
                            break;
                        default:
                            switch (iB) {
                                case 132:
                                case 133:
                                case 134:
                                case 135:
                                case 145:
                                case 146:
                                case 147:
                                    e4A = E4.c;
                                    break;
                                case 136:
                                case 137:
                                case 138:
                                    e4A = E4.e;
                                    break;
                                case 139:
                                case 140:
                                case 141:
                                    e4A = E4.d;
                                    break;
                                case 142:
                                case 143:
                                case 144:
                                    e4A = E4.f;
                                    break;
                                default:
                                    switch (iB) {
                                        default:
                                            switch (iB) {
                                                case 170:
                                                case 171:
                                                case 172:
                                                    break;
                                                case 173:
                                                    e4A = E4.e;
                                                    break;
                                                case 174:
                                                    e4A = E4.d;
                                                    break;
                                                case 175:
                                                    e4A = E4.f;
                                                    break;
                                                case 176:
                                                    break;
                                                default:
                                                    switch (iB) {
                                                        case 188:
                                                        case 189:
                                                            break;
                                                        case 190:
                                                            if (e4.b()) {
                                                                return b(abstractC3097u);
                                                            }
                                                            throw new W1(abstractC3097u, null, "an array reference", e4);
                                                        case 191:
                                                        case 192:
                                                        case 193:
                                                        case 194:
                                                        case 195:
                                                            break;
                                                        default:
                                                            throw new AssertionError();
                                                    }
                                                    break;
                                            }
                                        case 153:
                                        case 154:
                                        case 155:
                                        case 156:
                                        case 157:
                                        case 158:
                                            e4A = E4.c;
                                            break;
                                    }
                                    break;
                            }
                            break;
                    }
                }
                if (e4.b()) {
                    return b(abstractC3097u);
                }
                throw new W1(abstractC3097u, null, "an object reference", e4);
            }
            e4A = a(CZ.d(((C1130Kh) abstractC3097u).g));
        }
        if (e4.equals(e4A)) {
            return b(abstractC3097u);
        }
        throw new W1(abstractC3097u, null, e4A, e4);
    }

    /* JADX WARN: Code duplicated, block: B:17:0x002a  */
    /* JADX WARN: Code duplicated, block: B:19:0x002d  */
    /* JADX WARN: Code duplicated, block: B:21:0x0030  */
    /* JADX WARN: Code duplicated, block: B:23:0x0033  */
    private static E4 b(AbstractC3097u abstractC3097u) throws W1 {
        int iB = abstractC3097u.b();
        if (iB != 179) {
            if (iB == 180) {
                return a(CZ.f(((C1130Kh) abstractC3097u).i));
            }
            if (iB != 198 && iB != 199) {
                switch (iB) {
                    case 116:
                        return E4.c;
                    case 117:
                        return E4.e;
                    case 118:
                        return E4.d;
                    case 119:
                        return E4.f;
                    default:
                        switch (iB) {
                            case 132:
                            case 136:
                            case 139:
                            case 142:
                            case 145:
                            case 146:
                            case 147:
                                return E4.c;
                            case 133:
                            case 140:
                            case 143:
                                return E4.e;
                            case 134:
                            case 137:
                            case 144:
                                return E4.d;
                            case 135:
                            case 138:
                            case 141:
                                return E4.f;
                            default:
                                switch (iB) {
                                    default:
                                        switch (iB) {
                                            case 170:
                                            case 171:
                                            case 172:
                                            case 173:
                                            case 174:
                                            case 175:
                                            case 176:
                                                break;
                                            default:
                                                switch (iB) {
                                                    case 188:
                                                        switch (((C3531zr) abstractC3097u).g) {
                                                            case 4:
                                                                return a(CZ.f("[Z"));
                                                            case 5:
                                                                return a(CZ.f("[C"));
                                                            case 6:
                                                                return a(CZ.f("[F"));
                                                            case 7:
                                                                return a(CZ.f("[D"));
                                                            case 8:
                                                                return a(CZ.f("[B"));
                                                            case 9:
                                                                return a(CZ.f("[S"));
                                                            case 10:
                                                                return a(CZ.f("[I"));
                                                            case 11:
                                                                return a(CZ.f("[J"));
                                                            default:
                                                                throw new W1(abstractC3097u, "Invalid array type");
                                                        }
                                                    case 189:
                                                        return a(CZ.f("[" + CZ.d(((A00) abstractC3097u).g)));
                                                    case 190:
                                                        return E4.c;
                                                    case 191:
                                                        return null;
                                                    case 192:
                                                        return a(CZ.d(((A00) abstractC3097u).g));
                                                    case 193:
                                                        return E4.c;
                                                    case 194:
                                                    case 195:
                                                        break;
                                                    default:
                                                        throw new AssertionError();
                                                }
                                                break;
                                        }
                                    case 153:
                                    case 154:
                                    case 155:
                                    case 156:
                                    case 157:
                                    case 158:
                                        return null;
                                }
                                break;
                        }
                        break;
                }
            }
            return null;
        }
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:18:0x0034  */
    /* JADX WARN: Code duplicated, block: B:19:0x0037  */
    /* JADX WARN: Code duplicated, block: B:20:0x003a  */
    /* JADX WARN: Code duplicated, block: B:21:0x003d  */
    /* JADX WARN: Code duplicated, block: B:25:0x0046  */
    @Override // com.android.tools.r8.internal.AbstractC3462ys
    public final E4 a(AbstractC3097u abstractC3097u, R10 r10) throws W1 {
        E4 e4;
        E4 e5 = (E4) r10;
        int iB = abstractC3097u.b();
        switch (iB) {
            case 21:
                e4 = E4.c;
                if (!e4.equals(e5)) {
                    throw new W1(abstractC3097u, null, e4, e5);
                }
                return e5;
            case 22:
                e4 = E4.e;
                if (!e4.equals(e5)) {
                    throw new W1(abstractC3097u, null, e4, e5);
                }
                return e5;
            case 23:
                e4 = E4.d;
                if (!e4.equals(e5)) {
                    throw new W1(abstractC3097u, null, e4, e5);
                }
                return e5;
            case 24:
                e4 = E4.f;
                if (!e4.equals(e5)) {
                    throw new W1(abstractC3097u, null, e4, e5);
                }
                return e5;
            case 25:
                if (!e5.b()) {
                    throw new W1(abstractC3097u, null, "an object reference", e5);
                }
                return e5;
            default:
                switch (iB) {
                    case 54:
                        e4 = E4.c;
                        if (!e4.equals(e5)) {
                            throw new W1(abstractC3097u, null, e4, e5);
                        }
                        return e5;
                    case 55:
                        e4 = E4.e;
                        if (!e4.equals(e5)) {
                            throw new W1(abstractC3097u, null, e4, e5);
                        }
                        return e5;
                    case 56:
                        e4 = E4.d;
                        if (!e4.equals(e5)) {
                            throw new W1(abstractC3097u, null, e4, e5);
                        }
                        return e5;
                    case 57:
                        e4 = E4.f;
                        if (!e4.equals(e5)) {
                            throw new W1(abstractC3097u, null, e4, e5);
                        }
                        return e5;
                    case 58:
                        if (!e5.b() && !E4.h.equals(e5)) {
                            throw new W1(abstractC3097u, null, "an object reference or a return address", e5);
                        }
                    default:
                        return e5;
                }
                break;
        }
    }

    public static E4 a(CZ cz) {
        if (cz == null) {
            return E4.b;
        }
        switch (cz.e()) {
            case 0:
                return null;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                return E4.c;
            case 6:
                return E4.d;
            case 7:
                return E4.e;
            case 8:
                return E4.f;
            case 9:
            case 10:
                return E4.g;
            default:
                throw new AssertionError();
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC3462ys
    public final E4 a(AbstractC3097u abstractC3097u) throws W1 {
        int iB = abstractC3097u.b();
        if (iB == 168) {
            return E4.h;
        }
        if (iB == 178) {
            return a(CZ.f(((C1130Kh) abstractC3097u).i));
        }
        if (iB != 187) {
            switch (iB) {
                case 1:
                    return a(h);
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                    return E4.c;
                case 9:
                case 10:
                    return E4.e;
                case 11:
                case 12:
                case 13:
                    return E4.d;
                case 14:
                case 15:
                    return E4.f;
                case 16:
                case 17:
                    return E4.c;
                case 18:
                    Object obj = ((ZA) abstractC3097u).g;
                    if (obj instanceof Integer) {
                        return E4.c;
                    }
                    if (obj instanceof Float) {
                        return E4.d;
                    }
                    if (obj instanceof Long) {
                        return E4.e;
                    }
                    if (obj instanceof Double) {
                        return E4.f;
                    }
                    if (obj instanceof String) {
                        return a(CZ.d("java/lang/String"));
                    }
                    if (obj instanceof CZ) {
                        int iE = ((CZ) obj).e();
                        if (iE == 10 || iE == 9) {
                            return a(CZ.d(TypeProxy.SilentConstruction.Appender.JAVA_LANG_CLASS_INTERNAL_NAME));
                        }
                        if (iE == 11) {
                            return a(CZ.d("java/lang/invoke/MethodType"));
                        }
                        throw new W1(abstractC3097u, "Illegal LDC value " + obj);
                    }
                    if (obj instanceof C1944ek) {
                        return a(CZ.d("java/lang/invoke/MethodHandle"));
                    }
                    if (obj instanceof C1064Ic) {
                        return a(CZ.f(((C1064Ic) obj).d()));
                    }
                    throw new W1(abstractC3097u, "Illegal LDC value " + obj);
                default:
                    throw new AssertionError();
            }
        }
        return a(CZ.d(((A00) abstractC3097u).g));
    }

    @Override // com.android.tools.r8.internal.AbstractC3462ys
    public final E4 a(AbstractC3097u abstractC3097u, ArrayList arrayList) throws W1 {
        int i;
        String str;
        int iB = abstractC3097u.b();
        if (iB == 197) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                E4 e4 = (E4) it.next();
                E4 e5 = E4.c;
                if (!e5.equals(e4)) {
                    throw new W1(abstractC3097u, null, e5, e4);
                }
            }
        } else {
            int i2 = 0;
            if (iB == 184 || iB == 186) {
                i = 0;
            } else {
                CZ czD = CZ.d(((HE) abstractC3097u).g);
                if (!((E4) arrayList.get(0)).equals(a(czD))) {
                    throw new W1(abstractC3097u, "Method owner", a(czD), (R10) arrayList.get(0));
                }
                i = 1;
            }
            if (iB == 186) {
                str = ((C1194Ms) abstractC3097u).h;
            } else {
                str = ((HE) abstractC3097u).i;
            }
            CZ[] czArrA = CZ.a(str);
            while (i < arrayList.size()) {
                int i3 = i2 + 1;
                E4 e4A = a(czArrA[i2]);
                int i4 = i + 1;
                E4 e6 = (E4) arrayList.get(i);
                if (!e6.equals(e4A)) {
                    throw new W1(abstractC3097u, AbstractC1857dg.a("Argument ", i3), e4A, e6);
                }
                i2 = i3;
                i = i4;
            }
        }
        int iB2 = abstractC3097u.b();
        if (iB2 == 197) {
            return a(CZ.f(((DF) abstractC3097u).g));
        }
        if (iB2 == 186) {
            return a(CZ.e(((C1194Ms) abstractC3097u).h));
        }
        return a(CZ.e(((HE) abstractC3097u).i));
    }
}
