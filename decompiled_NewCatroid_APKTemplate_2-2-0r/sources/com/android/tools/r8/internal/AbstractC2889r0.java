package com.android.tools.r8.internal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.RandomAccess;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.r0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC2889r0 extends E0 implements Serializable {
    private transient Map e;
    private transient int f;

    protected AbstractC2889r0(HashMap map) {
        AbstractC3123uM.a(map.isEmpty());
        this.e = map;
    }

    static /* synthetic */ void b(AbstractC2889r0 abstractC2889r0) {
        abstractC2889r0.f++;
    }

    static /* synthetic */ void c(AbstractC2889r0 abstractC2889r0) {
        abstractC2889r0.f--;
    }

    @Override // com.android.tools.r8.internal.VF
    public void clear() {
        Iterator it = this.e.values().iterator();
        while (it.hasNext()) {
            ((Collection) it.next()).clear();
        }
        this.e.clear();
        this.f = 0;
    }

    @Override // com.android.tools.r8.internal.VF
    public boolean containsKey(Object obj) {
        return this.e.containsKey(obj);
    }

    @Override // com.android.tools.r8.internal.E0
    final Map d() {
        return new C2273j0(this, this.e);
    }

    @Override // com.android.tools.r8.internal.E0
    final Collection e() {
        return new D0(this);
    }

    @Override // com.android.tools.r8.internal.E0
    final Set f() {
        return new C2428l0(this, this.e);
    }

    @Override // com.android.tools.r8.internal.E0
    final InterfaceC1600aG g() {
        return new YF(this);
    }

    @Override // com.android.tools.r8.internal.VF
    public Collection get(Object obj) {
        Collection arrayList = (Collection) this.e.get(obj);
        if (arrayList == null) {
            arrayList = new ArrayList(((C1582a3) this).g);
        }
        AbstractC1663b3 abstractC1663b3 = (AbstractC1663b3) this;
        List list = (List) arrayList;
        return list instanceof RandomAccess ? new C2505m0(abstractC1663b3, obj, list, null) : new C2818q0(abstractC1663b3, obj, list, null);
    }

    @Override // com.android.tools.r8.internal.E0
    final Iterator h() {
        return new C2039g0(this);
    }

    @Override // com.android.tools.r8.internal.E0
    final Spliterator i() {
        return AbstractC3142ub.a(this.e.entrySet().spliterator(), new Function() { // from class: com.android.tools.r8.internal.r0$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return AbstractC2889r0.a((Map.Entry) obj);
            }
        }, 64, size());
    }

    @Override // com.android.tools.r8.internal.VF
    public int size() {
        return this.f;
    }

    static /* synthetic */ void a(AbstractC2889r0 abstractC2889r0, int i) {
        abstractC2889r0.f += i;
    }

    static /* synthetic */ void b(AbstractC2889r0 abstractC2889r0, int i) {
        abstractC2889r0.f -= i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Map.Entry b(Object obj, Object obj2) {
        return new C1325Rl(obj, obj2);
    }

    public boolean a(C2173hh c2173hh, S10 s10) {
        Collection collection = (Collection) this.e.get(c2173hh);
        if (collection == null) {
            ArrayList arrayList = new ArrayList(((C1582a3) this).g);
            if (arrayList.add(s10)) {
                this.f++;
                this.e.put(c2173hh, arrayList);
                return true;
            }
            throw new AssertionError("New Collection violated the Collection spec");
        }
        if (!collection.add(s10)) {
            return false;
        }
        this.f++;
        return true;
    }

    static Iterator a(Collection collection) {
        if (collection instanceof List) {
            return ((List) collection).listIterator();
        }
        return collection.iterator();
    }

    static void a(AbstractC2889r0 abstractC2889r0, Object obj) {
        Object objRemove;
        Map map = abstractC2889r0.e;
        int i = AbstractC3123uM.a;
        map.getClass();
        try {
            objRemove = map.remove(obj);
        } catch (ClassCastException | NullPointerException unused) {
            objRemove = null;
        }
        Collection collection = (Collection) objRemove;
        if (collection != null) {
            int size = collection.size();
            collection.clear();
            abstractC2889r0.f -= size;
        }
    }

    @Override // com.android.tools.r8.internal.E0, com.android.tools.r8.internal.VF
    public Collection a() {
        return super.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Spliterator a(Map.Entry entry) {
        final Object key = entry.getKey();
        return AbstractC3142ub.a(((Collection) entry.getValue()).spliterator(), new Function() { // from class: com.android.tools.r8.internal.r0$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return AbstractC2889r0.b(key, obj);
            }
        });
    }
}
