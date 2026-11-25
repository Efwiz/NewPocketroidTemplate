package com.android.tools.r8.internal;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class OU {
    public static HashSet a(Iterable iterable) {
        if (iterable instanceof Collection) {
            return new HashSet((Collection) iterable);
        }
        Iterator it = iterable.iterator();
        HashSet hashSet = new HashSet();
        AbstractC3533zt.a(hashSet, it);
        return hashSet;
    }

    public static HashSet b() {
        return new HashSet();
    }

    public static Set c() {
        return Collections.newSetFromMap(new IdentityHashMap());
    }

    public static LinkedHashSet d() {
        return new LinkedHashSet();
    }

    public static NU b(Set set, Set set2) {
        int i = AbstractC3123uM.a;
        if (set == null) {
            throw new NullPointerException("set1");
        }
        if (set2 != null) {
            return new FU(set, set2);
        }
        throw new NullPointerException("set2");
    }

    public static HashSet a(int i) {
        return new HashSet(RD.a(i));
    }

    public static Set a() {
        return Collections.newSetFromMap(new ConcurrentHashMap());
    }

    public static NU a(Set set, Set set2) {
        int i = AbstractC3123uM.a;
        if (set == null) {
            throw new NullPointerException("set1");
        }
        if (set2 != null) {
            return new HU(set, set2);
        }
        throw new NullPointerException("set2");
    }

    static int a(Set set) {
        Iterator it = set.iterator();
        int i = 0;
        while (it.hasNext()) {
            Object next = it.next();
            i = ~(~(i + (next != null ? next.hashCode() : 0)));
        }
        return i;
    }

    static boolean a(Set set, Iterator it) {
        boolean zRemove = false;
        while (it.hasNext()) {
            zRemove |= set.remove(it.next());
        }
        return zRemove;
    }

    static boolean a(Set set, Collection collection) {
        int i = AbstractC3123uM.a;
        collection.getClass();
        if (collection instanceof InterfaceC1600aG) {
            collection = ((InterfaceC1600aG) collection).I();
        }
        if ((collection instanceof Set) && collection.size() > set.size()) {
            return AbstractC3533zt.b(collection, set.iterator());
        }
        return a(set, collection.iterator());
    }

    public static Set a(int i, Set set) {
        C1946em c1946em = new C1946em(set.size());
        Iterator it = set.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            c1946em.a(it.next(), Integer.valueOf(i2));
            i2++;
        }
        AbstractC2178hm abstractC2178hmA = c1946em.a();
        AbstractC2625nb.a(i, "size");
        boolean z = i <= abstractC2178hmA.size();
        int size = abstractC2178hmA.size();
        int i3 = AbstractC3123uM.a;
        if (!z) {
            throw new IllegalArgumentException(AbstractC2619nY.a("size (%s) must be <= set.size() (%s)", Integer.valueOf(i), Integer.valueOf(size)));
        }
        if (i == 0) {
            int i4 = AbstractC1048Hm.c;
            return new AV(SS.h);
        }
        if (i == abstractC2178hmA.size()) {
            AbstractC1048Hm abstractC1048HmQ = abstractC2178hmA.keySet();
            int i5 = AbstractC1048Hm.c;
            return new AV(abstractC1048HmQ);
        }
        return new LU(i, abstractC2178hmA);
    }
}
