package com.android.tools.r8.internal;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;

/* JADX INFO: renamed from: com.android.tools.r8.internal.dk, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1862dk {
    private final ThreadLocal a;
    private final ConcurrentHashMap b;
    private final List c;
    private final C1854dd d;
    private final boolean e;
    private final boolean f;
    private final boolean g;
    private final boolean h;
    private final C1195Mt i;

    static {
        new C1505Xj();
    }

    public C1862dk() {
        this(C2722oh.c, Collections.emptyMap(), Collections.emptyList());
    }

    private static EZ a() {
        return new C1532Yj();
    }

    private static EZ b() {
        return new C1558Zj();
    }

    private static EZ c() {
        return AbstractC2961s00.k;
    }

    public final String toString() {
        return "{serializeNulls:" + this.e + "factories:" + this.c + ",instanceCreators:" + this.d + "}";
    }

    private static EZ a(EZ ez) {
        return new DZ(new C1632ak(ez));
    }

    private static EZ b(EZ ez) {
        return new DZ(new C1712bk(ez));
    }

    public final EZ a(I00 i00) {
        boolean z;
        EZ ez = (EZ) this.b.get(i00);
        if (ez != null) {
            return ez;
        }
        Map map = (Map) this.a.get();
        if (map == null) {
            map = new HashMap();
            this.a.set(map);
            z = true;
        } else {
            z = false;
        }
        C1787ck c1787ck = (C1787ck) map.get(i00);
        if (c1787ck != null) {
            return c1787ck;
        }
        try {
            C1787ck c1787ck2 = new C1787ck();
            map.put(i00, c1787ck2);
            Iterator it = this.c.iterator();
            while (it.hasNext()) {
                EZ ezA = ((FZ) it.next()).a(this, i00);
                if (ezA != null) {
                    c1787ck2.a(ezA);
                    this.b.put(i00, ezA);
                    map.remove(i00);
                    if (z) {
                        this.a.remove();
                    }
                    return ezA;
                }
            }
            throw new IllegalArgumentException("GSON cannot handle " + i00);
        } catch (Throwable th) {
            map.remove(i00);
            if (z) {
                this.a.remove();
            }
            throw th;
        }
    }

    C1862dk(C2722oh c2722oh, Map map, List list) {
        this.a = new ThreadLocal();
        this.b = new ConcurrentHashMap();
        C1854dd c1854dd = new C1854dd(map);
        this.d = c1854dd;
        this.e = false;
        this.g = false;
        this.f = true;
        this.h = false;
        ArrayList arrayList = new ArrayList();
        arrayList.add(AbstractC2961s00.B);
        arrayList.add(C3349xK.b);
        arrayList.add(c2722oh);
        arrayList.addAll(list);
        arrayList.add(AbstractC2961s00.p);
        arrayList.add(AbstractC2961s00.g);
        arrayList.add(AbstractC2961s00.d);
        arrayList.add(AbstractC2961s00.e);
        arrayList.add(AbstractC2961s00.f);
        EZ ezC = c();
        arrayList.add(AbstractC2961s00.a(Long.TYPE, Long.class, ezC));
        arrayList.add(AbstractC2961s00.a(Double.TYPE, Double.class, a()));
        arrayList.add(AbstractC2961s00.a(Float.TYPE, Float.class, b()));
        arrayList.add(AbstractC2961s00.l);
        arrayList.add(AbstractC2961s00.h);
        arrayList.add(AbstractC2961s00.i);
        arrayList.add(AbstractC2961s00.a(AtomicLong.class, a(ezC)));
        arrayList.add(AbstractC2961s00.a(AtomicLongArray.class, b(ezC)));
        arrayList.add(AbstractC2961s00.j);
        arrayList.add(AbstractC2961s00.m);
        arrayList.add(AbstractC2961s00.q);
        arrayList.add(AbstractC2961s00.r);
        arrayList.add(AbstractC2961s00.a(BigDecimal.class, AbstractC2961s00.n));
        arrayList.add(AbstractC2961s00.a(BigInteger.class, AbstractC2961s00.o));
        arrayList.add(AbstractC2961s00.s);
        arrayList.add(AbstractC2961s00.t);
        arrayList.add(AbstractC2961s00.v);
        arrayList.add(AbstractC2961s00.w);
        arrayList.add(AbstractC2961s00.z);
        arrayList.add(AbstractC2961s00.u);
        arrayList.add(AbstractC2961s00.b);
        arrayList.add(C1178Md.c);
        arrayList.add(AbstractC2961s00.y);
        arrayList.add(C1932eZ.b);
        arrayList.add(QV.b);
        arrayList.add(AbstractC2961s00.x);
        arrayList.add(C1886e3.c);
        arrayList.add(AbstractC2961s00.a);
        arrayList.add(new C3515zb(c1854dd));
        arrayList.add(new C3045tD(c1854dd));
        C1195Mt c1195Mt = new C1195Mt(c1854dd);
        this.i = c1195Mt;
        arrayList.add(c1195Mt);
        arrayList.add(AbstractC2961s00.C);
        arrayList.add(new GS(c1854dd, c2722oh, c1195Mt));
        this.c = Collections.unmodifiableList(arrayList);
    }

    public final EZ a(C2722oh c2722oh, I00 i00) {
        boolean zContains = this.c.contains(c2722oh);
        FZ fz = c2722oh;
        if (!zContains) {
            fz = this.i;
        }
        boolean z = false;
        for (FZ fz2 : this.c) {
            if (z) {
                EZ ezA = fz2.a(this, i00);
                if (ezA != null) {
                    return ezA;
                }
            } else if (fz2 == fz) {
                z = true;
            }
        }
        throw new IllegalArgumentException("GSON cannot serialize " + i00);
    }

    public final String a(LinkedHashMap linkedHashMap) {
        Class<?> cls = linkedHashMap.getClass();
        StringWriter stringWriter = new StringWriter();
        try {
            if (this.g) {
                stringWriter.write(")]}'\n");
            }
            C1799cu c1799cu = new C1799cu(stringWriter);
            if (this.h) {
                c1799cu.m();
            }
            c1799cu.c(this.e);
            a(linkedHashMap, cls, c1799cu);
            return stringWriter.toString();
        } catch (IOException e) {
            throw new C1279Pt(e);
        }
    }

    public final void a(LinkedHashMap linkedHashMap, Class cls, C1799cu c1799cu) {
        EZ ezA = a(I00.a((Type) cls));
        boolean zJ = c1799cu.j();
        c1799cu.b(true);
        boolean zI = c1799cu.i();
        c1799cu.a(this.f);
        boolean zH = c1799cu.h();
        c1799cu.c(this.e);
        try {
            try {
                ezA.a(c1799cu, linkedHashMap);
                c1799cu.b(zJ);
                c1799cu.a(zI);
                c1799cu.c(zH);
            } catch (IOException e) {
                throw new C1279Pt(e);
            }
        } catch (Throwable th) {
            c1799cu.b(zJ);
            c1799cu.a(zI);
            c1799cu.c(zH);
            throw th;
        }
    }
}
