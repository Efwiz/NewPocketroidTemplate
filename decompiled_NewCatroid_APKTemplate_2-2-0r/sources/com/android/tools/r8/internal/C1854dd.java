package com.android.tools.r8.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;

/* JADX INFO: renamed from: com.android.tools.r8.internal.dd, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1854dd {
    private final Map a;

    public C1854dd(Map map) {
        this.a = map;
    }

    public final InterfaceC1833dK a(I00 i00) {
        C1469Wc c1469Wc;
        Type typeB = i00.b();
        Class clsA = i00.a();
        AbstractC1288Qc.a(this.a.get(typeB));
        AbstractC1288Qc.a(this.a.get(clsA));
        InterfaceC1833dK c1396Uc = null;
        try {
            Constructor declaredConstructor = clsA.getDeclaredConstructor(new Class[0]);
            if (!declaredConstructor.isAccessible()) {
                declaredConstructor.setAccessible(true);
            }
            c1469Wc = new C1469Wc(declaredConstructor);
        } catch (NoSuchMethodException unused) {
            c1469Wc = null;
        }
        if (c1469Wc != null) {
            return c1469Wc;
        }
        if (Collection.class.isAssignableFrom(clsA)) {
            if (SortedSet.class.isAssignableFrom(clsA)) {
                c1396Uc = new C1498Xc();
            } else if (EnumSet.class.isAssignableFrom(clsA)) {
                c1396Uc = new C1524Yc(typeB);
            } else if (Set.class.isAssignableFrom(clsA)) {
                c1396Uc = new C1551Zc();
            } else {
                c1396Uc = Queue.class.isAssignableFrom(clsA) ? new C1625ad() : new C1705bd();
            }
        } else if (Map.class.isAssignableFrom(clsA)) {
            if (ConcurrentNavigableMap.class.isAssignableFrom(clsA)) {
                c1396Uc = new C1778cd();
            } else if (ConcurrentMap.class.isAssignableFrom(clsA)) {
                c1396Uc = new C1315Rc();
            } else if (SortedMap.class.isAssignableFrom(clsA)) {
                c1396Uc = new C1342Sc();
            } else {
                c1396Uc = (!(typeB instanceof ParameterizedType) || String.class.isAssignableFrom(I00.a(((ParameterizedType) typeB).getActualTypeArguments()[0]).a())) ? new C1396Uc() : new C1368Tc();
            }
        }
        return c1396Uc != null ? c1396Uc : new C1443Vc(clsA, typeB);
    }

    public final String toString() {
        return this.a.toString();
    }
}
