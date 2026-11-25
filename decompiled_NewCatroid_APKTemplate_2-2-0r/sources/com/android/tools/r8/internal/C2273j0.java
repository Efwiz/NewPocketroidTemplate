package com.android.tools.r8.internal;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.RandomAccess;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.j0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2273j0 extends AbstractMap {
    private transient Set a;
    private transient Collection b;
    final transient Map c;
    final /* synthetic */ AbstractC2889r0 d;

    C2273j0(AbstractC2889r0 abstractC2889r0, Map map) {
        this.d = abstractC2889r0;
        this.c = map;
    }

    final Map.Entry a(Map.Entry entry) {
        Object key = entry.getKey();
        AbstractC2889r0 abstractC2889r0 = this.d;
        Collection collection = (Collection) entry.getValue();
        AbstractC1663b3 abstractC1663b3 = (AbstractC1663b3) abstractC2889r0;
        abstractC1663b3.getClass();
        List list = (List) collection;
        return new C1325Rl(key, list instanceof RandomAccess ? new C2505m0(abstractC1663b3, key, list, null) : new C2818q0(abstractC1663b3, key, list, null));
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final void clear() {
        if (this.c == this.d.e) {
            this.d.clear();
            return;
        }
        Iterator it = this.c.entrySet().iterator();
        int i = AbstractC3123uM.a;
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Collection collection = (Collection) entry.getValue();
            a(entry);
            AbstractC3123uM.b("no calls to next() since the last call to remove()", collection != null);
            it.remove();
            AbstractC2889r0.b(this.d, collection.size());
            collection.clear();
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final boolean containsKey(Object obj) {
        Map map = this.c;
        int i = AbstractC3123uM.a;
        map.getClass();
        try {
            return map.containsKey(obj);
        } catch (ClassCastException | NullPointerException unused) {
            return false;
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Set entrySet() {
        Set set = this.a;
        if (set != null) {
            return set;
        }
        C2123h0 c2123h0 = new C2123h0(this);
        this.a = c2123h0;
        return c2123h0;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final boolean equals(Object obj) {
        return this == obj || this.c.equals(obj);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Object get(Object obj) {
        Collection collection = (Collection) RD.a(this.c, obj);
        if (collection == null) {
            return null;
        }
        AbstractC1663b3 abstractC1663b3 = (AbstractC1663b3) this.d;
        abstractC1663b3.getClass();
        List list = (List) collection;
        return list instanceof RandomAccess ? new C2505m0(abstractC1663b3, obj, list, null) : new C2818q0(abstractC1663b3, obj, list, null);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final int hashCode() {
        return this.c.hashCode();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Set keySet() {
        return this.d.keySet();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Object remove(Object obj) {
        Collection collection = (Collection) this.c.remove(obj);
        if (collection == null) {
            return null;
        }
        C1582a3 c1582a3 = (C1582a3) this.d;
        c1582a3.getClass();
        ArrayList arrayList = new ArrayList(c1582a3.g);
        arrayList.addAll(collection);
        AbstractC2889r0.b(this.d, collection.size());
        collection.clear();
        return arrayList;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final int size() {
        return this.c.size();
    }

    @Override // java.util.AbstractMap
    public final String toString() {
        return this.c.toString();
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final Collection values() {
        Collection collection = this.b;
        if (collection != null) {
            return collection;
        }
        QD qd = new QD(this);
        this.b = qd;
        return qd;
    }
}
