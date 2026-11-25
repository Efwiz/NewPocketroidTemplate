package com.android.tools.r8.internal;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ci, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1785ci {
    private static final C1785ci d = new C1785ci(0);
    public static final /* synthetic */ int e = 0;
    private boolean b;
    private boolean c = false;
    private final BV a = BV.d(16);

    private C1785ci() {
    }

    public static C1785ci b() {
        return d;
    }

    public static C1785ci g() {
        return new C1785ci();
    }

    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public final C1785ci clone() {
        C1785ci c1785ci = new C1785ci();
        for (int i = 0; i < this.a.k(); i++) {
            Map.Entry entryA = this.a.a(i);
            c1785ci.c((InterfaceC1710bi) entryA.getKey(), entryA.getValue());
        }
        for (Map.Entry entry : this.a.l()) {
            c1785ci.c((InterfaceC1710bi) entry.getKey(), entry.getValue());
        }
        c1785ci.c = this.c;
        return c1785ci;
    }

    public final void c(InterfaceC1710bi interfaceC1710bi, Object obj) {
        C0985Fj c0985Fj = (C0985Fj) interfaceC1710bi;
        if (!c0985Fj.c) {
            b(c0985Fj.b, obj);
        } else {
            if (!(obj instanceof List)) {
                throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
            }
            ArrayList arrayList = new ArrayList();
            arrayList.addAll((List) obj);
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                b(c0985Fj.b, it.next());
            }
            obj = arrayList;
        }
        this.a.a(interfaceC1710bi, obj);
    }

    public final boolean d() {
        for (int i = 0; i < this.a.k(); i++) {
            if (!a(this.a.a(i))) {
                return false;
            }
        }
        Iterator it = this.a.l().iterator();
        while (it.hasNext()) {
            if (!a((Map.Entry) it.next())) {
                return false;
            }
        }
        return true;
    }

    public final Iterator e() {
        return this.c ? new WA(((HV) this.a.entrySet()).iterator()) : ((HV) this.a.entrySet()).iterator();
    }

    public final void f() {
        if (this.b) {
            return;
        }
        this.a.m();
        this.b = true;
    }

    public final boolean b(InterfaceC1710bi interfaceC1710bi) {
        if (((C0985Fj) interfaceC1710bi).c) {
            throw new IllegalArgumentException("hasField() can only be called on non-repeated fields.");
        }
        return this.a.get(interfaceC1710bi) != null;
    }

    /* JADX WARN: Code duplicated, block: B:17:0x0026  */
    private static void b(L20 l20, Object obj) {
        obj.getClass();
        boolean z = false;
        switch (l20.a().ordinal()) {
            case 0:
                z = obj instanceof Integer;
                break;
            case 1:
                z = obj instanceof Long;
                break;
            case 2:
                z = obj instanceof Float;
                break;
            case 3:
                z = obj instanceof Double;
                break;
            case 4:
                z = obj instanceof Boolean;
                break;
            case 5:
                z = obj instanceof String;
                break;
            case 6:
                if ((obj instanceof Y5) || (obj instanceof byte[])) {
                    z = true;
                }
                break;
            case 7:
                if ((obj instanceof Integer) || (obj instanceof InterfaceC2880qs)) {
                    z = true;
                }
                break;
            case 8:
                if (obj instanceof B0) {
                    z = true;
                }
                break;
        }
        if (!z) {
            throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
        }
    }

    private C1785ci(int i) {
        f();
    }

    public final Object a(InterfaceC1710bi interfaceC1710bi) {
        return this.a.get(interfaceC1710bi);
    }

    public final void a(InterfaceC1710bi interfaceC1710bi, Object obj) {
        List arrayList;
        C0985Fj c0985Fj = (C0985Fj) interfaceC1710bi;
        if (c0985Fj.c) {
            b(c0985Fj.b, obj);
            Object obj2 = this.a.get(interfaceC1710bi);
            if (obj2 == null) {
                arrayList = new ArrayList();
                this.a.a(interfaceC1710bi, arrayList);
            } else {
                arrayList = (List) obj2;
            }
            arrayList.add(obj);
            return;
        }
        throw new IllegalArgumentException("addRepeatedField() can only be called on repeated fields.");
    }

    public final int c() {
        int iB = 0;
        for (int i = 0; i < this.a.k(); i++) {
            Map.Entry entryA = this.a.a(i);
            iB += b((InterfaceC1710bi) entryA.getKey(), entryA.getValue());
        }
        for (Map.Entry entry : this.a.l()) {
            iB += b((InterfaceC1710bi) entry.getKey(), entry.getValue());
        }
        return iB;
    }

    private static boolean a(Map.Entry entry) {
        C0985Fj c0985Fj = (C0985Fj) ((InterfaceC1710bi) entry.getKey());
        if (c0985Fj.b.a() != M20.j) {
            return true;
        }
        if (c0985Fj.c) {
            Iterator it = ((List) entry.getValue()).iterator();
            while (it.hasNext()) {
                if (!((B0) it.next()).a()) {
                    return false;
                }
            }
            return true;
        }
        Object value = entry.getValue();
        if (value instanceof B0) {
            return ((B0) value).a();
        }
        throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
    }

    public final void a(C1785ci c1785ci) {
        for (int i = 0; i < c1785ci.a.k(); i++) {
            b(c1785ci.a.a(i));
        }
        Iterator it = c1785ci.a.l().iterator();
        while (it.hasNext()) {
            b((Map.Entry) it.next());
        }
    }

    public static Object a(C2396kb c2396kb, L20 l20) {
        switch (l20.ordinal()) {
            case 0:
                return Double.valueOf(Double.longBitsToDouble(c2396kb.i()));
            case 1:
                return Float.valueOf(Float.intBitsToFloat(c2396kb.h()));
            case 2:
                return Long.valueOf(c2396kb.k());
            case 3:
                return Long.valueOf(c2396kb.k());
            case 4:
                return Integer.valueOf(c2396kb.j());
            case 5:
                return Long.valueOf(c2396kb.i());
            case 6:
                return Integer.valueOf(c2396kb.h());
            case 7:
                return Boolean.valueOf(c2396kb.b());
            case 8:
                return c2396kb.m();
            case 9:
                throw new IllegalArgumentException("readPrimitiveField() cannot handle nested groups.");
            case 10:
                throw new IllegalArgumentException("readPrimitiveField() cannot handle embedded messages.");
            case 11:
                return c2396kb.c();
            case 12:
                return Integer.valueOf(c2396kb.j());
            case 13:
                throw new IllegalArgumentException("readPrimitiveField() cannot handle enums.");
            case 14:
                return Integer.valueOf(c2396kb.h());
            case 15:
                return Long.valueOf(c2396kb.i());
            case 16:
                int iJ = c2396kb.j();
                return Integer.valueOf((-(iJ & 1)) ^ (iJ >>> 1));
            case 17:
                return Long.valueOf(c2396kb.l());
            default:
                throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
        }
    }

    private void b(Map.Entry entry) {
        InterfaceC1710bi interfaceC1710bi = (InterfaceC1710bi) entry.getKey();
        Object value = entry.getValue();
        C0985Fj c0985Fj = (C0985Fj) interfaceC1710bi;
        if (c0985Fj.c) {
            Object arrayList = this.a.get(interfaceC1710bi);
            if (arrayList == null) {
                arrayList = new ArrayList();
            }
            for (Object obj : (List) value) {
                List list = (List) arrayList;
                if (obj instanceof byte[]) {
                    byte[] bArr = (byte[]) obj;
                    byte[] bArr2 = new byte[bArr.length];
                    System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
                    obj = bArr2;
                }
                list.add(obj);
            }
            this.a.a(interfaceC1710bi, arrayList);
            return;
        }
        if (c0985Fj.b.a() == M20.j) {
            Object obj2 = this.a.get(interfaceC1710bi);
            if (obj2 == null) {
                BV bv = this.a;
                if (value instanceof byte[]) {
                    byte[] bArr3 = (byte[]) value;
                    byte[] bArr4 = new byte[bArr3.length];
                    System.arraycopy(bArr3, 0, bArr4, 0, bArr3.length);
                    value = bArr4;
                }
                bv.a(interfaceC1710bi, value);
                return;
            }
            this.a.a(interfaceC1710bi, ((AbstractC0859Bj) ((B0) obj2).e()).a((AbstractC1045Hj) ((B0) value)).c());
            return;
        }
        BV bv2 = this.a;
        if (value instanceof byte[]) {
            byte[] bArr5 = (byte[]) value;
            byte[] bArr6 = new byte[bArr5.length];
            System.arraycopy(bArr5, 0, bArr6, 0, bArr5.length);
            value = bArr6;
        }
        bv2.a(interfaceC1710bi, value);
    }

    public static int b(InterfaceC1710bi interfaceC1710bi, Object obj) {
        C0985Fj c0985Fj = (C0985Fj) interfaceC1710bi;
        L20 l20 = c0985Fj.b;
        int i = c0985Fj.a;
        if (c0985Fj.c) {
            int iA = 0;
            if (c0985Fj.d) {
                Iterator it = ((List) obj).iterator();
                while (it.hasNext()) {
                    iA += a(l20, it.next());
                }
                return C2547mb.b(iA) + C2547mb.c(i) + iA;
            }
            for (Object obj2 : (List) obj) {
                int iC = C2547mb.c(i);
                if (l20 == L20.e) {
                    iC *= 2;
                }
                iA += iC + a(l20, obj2);
            }
            return iA;
        }
        int iC2 = C2547mb.c(i);
        if (l20 == L20.e) {
            iC2 *= 2;
        }
        return iC2 + a(l20, obj);
    }

    private static void a(C2547mb c2547mb, L20 l20, Object obj) throws IOException {
        switch (l20.ordinal()) {
            case 0:
                double dDoubleValue = ((Double) obj).doubleValue();
                c2547mb.getClass();
                c2547mb.c(Double.doubleToRawLongBits(dDoubleValue));
                break;
            case 1:
                float fFloatValue = ((Float) obj).floatValue();
                c2547mb.getClass();
                c2547mb.f(Float.floatToRawIntBits(fFloatValue));
                break;
            case 2:
                c2547mb.d(((Long) obj).longValue());
                break;
            case 3:
                c2547mb.d(((Long) obj).longValue());
                break;
            case 4:
                c2547mb.d(((Integer) obj).intValue());
                break;
            case 5:
                c2547mb.c(((Long) obj).longValue());
                break;
            case 6:
                c2547mb.f(((Integer) obj).intValue());
                break;
            case 7:
                c2547mb.e(((Boolean) obj).booleanValue() ? 1 : 0);
                break;
            case 8:
                c2547mb.getClass();
                byte[] bytes = ((String) obj).getBytes("UTF-8");
                c2547mb.g(bytes.length);
                c2547mb.b(bytes);
                break;
            case 9:
                c2547mb.getClass();
                ((B0) obj).a(c2547mb);
                break;
            case 10:
                c2547mb.b((B0) obj);
                break;
            case 11:
                if (obj instanceof Y5) {
                    Y5 y5 = (Y5) obj;
                    c2547mb.getClass();
                    c2547mb.g(y5.size());
                    c2547mb.b(y5);
                } else {
                    byte[] bArr = (byte[]) obj;
                    c2547mb.getClass();
                    c2547mb.g(bArr.length);
                    c2547mb.b(bArr);
                }
                break;
            case 12:
                c2547mb.g(((Integer) obj).intValue());
                break;
            case 13:
                if (obj instanceof InterfaceC2880qs) {
                    c2547mb.d(((InterfaceC2880qs) obj).a());
                } else {
                    c2547mb.d(((Integer) obj).intValue());
                }
                break;
            case 14:
                c2547mb.f(((Integer) obj).intValue());
                break;
            case 15:
                c2547mb.c(((Long) obj).longValue());
                break;
            case 16:
                int iIntValue = ((Integer) obj).intValue();
                c2547mb.g((iIntValue >> 31) ^ (iIntValue << 1));
                break;
            case 17:
                c2547mb.d(C2547mb.b(((Long) obj).longValue()));
                break;
        }
    }

    public static void a(InterfaceC1710bi interfaceC1710bi, Object obj, C2547mb c2547mb) throws IOException {
        C0985Fj c0985Fj = (C0985Fj) interfaceC1710bi;
        L20 l20 = c0985Fj.b;
        int i = c0985Fj.a;
        if (c0985Fj.c) {
            List list = (List) obj;
            if (c0985Fj.d) {
                c2547mb.g(N20.a(i, 2));
                Iterator it = list.iterator();
                int iA = 0;
                while (it.hasNext()) {
                    iA += a(l20, it.next());
                }
                c2547mb.g(iA);
                Iterator it2 = list.iterator();
                while (it2.hasNext()) {
                    a(c2547mb, l20, it2.next());
                }
                return;
            }
            for (Object obj2 : list) {
                if (l20 == L20.e) {
                    c2547mb.g(N20.a(i, 3));
                    ((B0) obj2).a(c2547mb);
                    c2547mb.g(N20.a(i, 4));
                } else {
                    c2547mb.g(N20.a(i, l20.b()));
                    a(c2547mb, l20, obj2);
                }
            }
            return;
        }
        if (l20 == L20.e) {
            c2547mb.g(N20.a(i, 3));
            ((B0) obj).a(c2547mb);
            c2547mb.g(N20.a(i, 4));
        } else {
            c2547mb.g(N20.a(i, l20.b()));
            a(c2547mb, l20, obj);
        }
    }

    private static int a(L20 l20, Object obj) {
        switch (l20.ordinal()) {
            case 0:
                ((Double) obj).doubleValue();
                return 8;
            case 1:
                ((Float) obj).floatValue();
                return 4;
            case 2:
                return C2547mb.a(((Long) obj).longValue());
            case 3:
                return C2547mb.a(((Long) obj).longValue());
            case 4:
                return C2547mb.a(((Integer) obj).intValue());
            case 5:
                ((Long) obj).longValue();
                return 8;
            case 6:
                ((Integer) obj).intValue();
                return 4;
            case 7:
                ((Boolean) obj).booleanValue();
                return 1;
            case 8:
                try {
                    byte[] bytes = ((String) obj).getBytes("UTF-8");
                    return C2547mb.b(bytes.length) + bytes.length;
                } catch (UnsupportedEncodingException e2) {
                    throw new RuntimeException("UTF-8 not supported.", e2);
                }
            case 9:
                return ((B0) obj).c();
            case 10:
                return C2547mb.a((B0) obj);
            case 11:
                if (obj instanceof Y5) {
                    return C2547mb.a((Y5) obj);
                }
                byte[] bArr = (byte[]) obj;
                return C2547mb.b(bArr.length) + bArr.length;
            case 12:
                return C2547mb.b(((Integer) obj).intValue());
            case 13:
                if (obj instanceof InterfaceC2880qs) {
                    return C2547mb.a(((InterfaceC2880qs) obj).a());
                }
                return C2547mb.a(((Integer) obj).intValue());
            case 14:
                ((Integer) obj).intValue();
                return 4;
            case 15:
                ((Long) obj).longValue();
                return 8;
            case 16:
                int iIntValue = ((Integer) obj).intValue();
                return C2547mb.b((iIntValue >> 31) ^ (iIntValue << 1));
            case 17:
                return C2547mb.a(C2547mb.b(((Long) obj).longValue()));
            default:
                throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
        }
    }
}
