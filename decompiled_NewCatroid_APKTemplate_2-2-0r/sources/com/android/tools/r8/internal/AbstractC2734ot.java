package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ot, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2734ot {
    public static boolean a(Iterable iterable, Object obj) {
        AbstractC3069tZ abstractC3069tZ;
        AbstractC3069tZ abstractC3069tZ2;
        if (iterable instanceof Collection) {
            Collection collection = (Collection) iterable;
            int i = AbstractC3123uM.a;
            try {
                return collection.contains(obj);
            } catch (ClassCastException | NullPointerException unused) {
                return false;
            }
        }
        Iterator it = ((C2493lt) iterable).iterator();
        if (obj == null) {
            do {
                abstractC3069tZ2 = (AbstractC3069tZ) it;
                if (!abstractC3069tZ2.hasNext()) {
                    return false;
                }
            } while (abstractC3069tZ2.next() != null);
        } else {
            do {
                abstractC3069tZ = (AbstractC3069tZ) it;
                if (!abstractC3069tZ.hasNext()) {
                    return false;
                }
            } while (!obj.equals(abstractC3069tZ.next()));
        }
        return true;
    }

    public static Object b(Iterable iterable) {
        Iterator it = iterable.iterator();
        Object next = it.next();
        if (!it.hasNext()) {
            return next;
        }
        StringBuilder sbAppend = new StringBuilder("expected one element but was: <").append(next);
        for (int i = 0; i < 4 && it.hasNext(); i++) {
            sbAppend.append(", ").append(it.next());
        }
        if (it.hasNext()) {
            sbAppend.append(", ...");
        }
        sbAppend.append('>');
        throw new IllegalArgumentException(sbAppend.toString());
    }

    public static Iterable c(Iterable iterable, InterfaceC3205vM interfaceC3205vM) {
        int i = AbstractC3123uM.a;
        iterable.getClass();
        interfaceC3205vM.getClass();
        return new C2417kt(iterable, interfaceC3205vM);
    }

    public static Iterable d(Iterable iterable) {
        int i = AbstractC3123uM.a;
        iterable.getClass();
        AbstractC3123uM.a("number to skip cannot be negative", true);
        return new C2647nt(iterable);
    }

    public static boolean c(Iterable iterable) {
        if (iterable instanceof Collection) {
            return ((Collection) iterable).isEmpty();
        }
        return !iterable.iterator().hasNext();
    }

    public static boolean a(Iterable iterable, Iterable iterable2) {
        if ((iterable instanceof Collection) && (iterable2 instanceof Collection) && ((Collection) iterable).size() != ((Collection) iterable2).size()) {
            return false;
        }
        Iterator it = iterable.iterator();
        Iterator it2 = iterable2.iterator();
        while (it.hasNext()) {
            if (!it2.hasNext() || !AbstractC3428yK.a(it.next(), it2.next())) {
                return false;
            }
        }
        return !it2.hasNext();
    }

    /* JADX WARN: Code duplicated, block: B:13:0x0022 A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Code duplicated, block: B:19:? A[RETURN, SYNTHETIC] */
    public static boolean b(Iterable iterable, InterfaceC3205vM interfaceC3205vM) {
        Iterator it = iterable.iterator();
        int i = AbstractC3123uM.a;
        if (interfaceC3205vM != null) {
            int i2 = 0;
            while (it.hasNext()) {
                if (interfaceC3205vM.apply(it.next())) {
                    if (i2 != -1) {
                        return true;
                    }
                    return false;
                }
                i2++;
            }
            i2 = -1;
            if (i2 != -1) {
                return true;
            }
            return false;
        }
        throw new NullPointerException("predicate");
    }

    public static Iterable a(Iterable iterable) {
        int i = AbstractC3123uM.a;
        return new C0898Ci(iterable);
    }

    static Object[] a(Set set, Object[] objArr) {
        boolean z = set instanceof Collection;
        Collection collection = set;
        if (!z) {
            Iterator it = set.iterator();
            ArrayList arrayList = new ArrayList();
            AbstractC3533zt.a(arrayList, it);
            collection = arrayList;
        }
        return collection.toArray(objArr);
    }

    public static void a(Iterable iterable, Collection collection) {
        if (iterable instanceof Collection) {
            collection.addAll((Collection) iterable);
            return;
        }
        int i = AbstractC3123uM.a;
        iterable.getClass();
        AbstractC3533zt.a(collection, iterable.iterator());
    }

    public static Iterable a(List list, ArrayList arrayList) {
        return AbstractC0984Fi.a(list, arrayList);
    }

    public static boolean a(Iterable iterable, InterfaceC3205vM interfaceC3205vM) {
        Iterator it = iterable.iterator();
        int i = AbstractC3123uM.a;
        interfaceC3205vM.getClass();
        while (it.hasNext()) {
            if (!interfaceC3205vM.apply(it.next())) {
                return false;
            }
        }
        return true;
    }

    public static Object a(List list, InterfaceC3205vM interfaceC3205vM) {
        Iterator it = list.iterator();
        int i = AbstractC3123uM.a;
        it.getClass();
        interfaceC3205vM.getClass();
        while (it.hasNext()) {
            Object next = it.next();
            if (interfaceC3205vM.apply(next)) {
                return next;
            }
        }
        return null;
    }

    public static Iterable a(Iterable iterable, InterfaceC3010sj interfaceC3010sj) {
        int i = AbstractC3123uM.a;
        iterable.getClass();
        interfaceC3010sj.getClass();
        return new C2493lt(iterable, interfaceC3010sj);
    }
}
