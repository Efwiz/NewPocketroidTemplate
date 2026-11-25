package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0717o2;
import com.android.tools.r8.graph.C0767v2;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.nf, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2631nf implements Map {
    private final Map a;

    private C2631nf(HashMap map) {
        this.a = map;
    }

    public static C2631nf e() {
        return new C2631nf(new HashMap());
    }

    public final void a(com.android.tools.r8.graph.W0 w0, C0767v2 c0767v2, BiFunction biFunction) {
        this.a.merge(w0.getReference().J0(), c0767v2, biFunction);
    }

    public final C2720of b(Set set) {
        C2720of c2720ofA = C2720of.a();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            AbstractC0717o2 abstractC0717o2 = (AbstractC0717o2) it.next();
            if (this.a.containsKey(abstractC0717o2)) {
                c2720ofA.a(abstractC0717o2);
            }
        }
        return c2720ofA;
    }

    @Override // java.util.Map
    public final void clear() {
        this.a.clear();
    }

    @Override // java.util.Map
    public final Object compute(Object obj, BiFunction biFunction) {
        return this.a.compute((AbstractC0717o2) obj, biFunction);
    }

    @Override // java.util.Map
    public final Object computeIfAbsent(Object obj, Function function) {
        return this.a.computeIfAbsent((AbstractC0717o2) obj, function);
    }

    @Override // java.util.Map
    public final Object computeIfPresent(Object obj, BiFunction biFunction) {
        return this.a.computeIfPresent((AbstractC0717o2) obj, biFunction);
    }

    @Override // java.util.Map
    public final boolean containsKey(Object obj) {
        return this.a.containsKey(obj);
    }

    @Override // java.util.Map
    public final boolean containsValue(Object obj) {
        return this.a.containsValue(obj);
    }

    @Override // java.util.Map
    public final Set entrySet() {
        return this.a.entrySet();
    }

    @Override // java.util.Map
    public final void forEach(BiConsumer biConsumer) {
        this.a.forEach(biConsumer);
    }

    @Override // java.util.Map
    public final Object get(Object obj) {
        return this.a.get(obj);
    }

    @Override // java.util.Map
    public final Object getOrDefault(Object obj, Object obj2) {
        return this.a.getOrDefault(obj, obj2);
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        return this.a.isEmpty();
    }

    @Override // java.util.Map
    public final Set keySet() {
        return this.a.keySet();
    }

    @Override // java.util.Map
    public final Object merge(Object obj, Object obj2, BiFunction biFunction) {
        return this.a.merge((AbstractC0717o2) obj, obj2, biFunction);
    }

    @Override // java.util.Map
    public final Object put(Object obj, Object obj2) {
        return this.a.put((AbstractC0717o2) obj, obj2);
    }

    @Override // java.util.Map
    public final void putAll(Map map) {
    }

    @Override // java.util.Map
    public final Object putIfAbsent(Object obj, Object obj2) {
        return this.a.putIfAbsent((AbstractC0717o2) obj, obj2);
    }

    @Override // java.util.Map
    public final boolean remove(Object obj, Object obj2) {
        return this.a.remove(obj, obj2);
    }

    @Override // java.util.Map
    public final Object replace(Object obj, Object obj2) {
        return this.a.replace((AbstractC0717o2) obj, obj2);
    }

    @Override // java.util.Map
    public final void replaceAll(BiFunction biFunction) {
        this.a.replaceAll(biFunction);
    }

    @Override // java.util.Map
    public final int size() {
        return this.a.size();
    }

    @Override // java.util.Map
    public final Collection values() {
        return this.a.values();
    }

    @Override // java.util.Map
    public final Object remove(Object obj) {
        return this.a.remove(obj);
    }

    @Override // java.util.Map
    public final boolean replace(Object obj, Object obj2, Object obj3) {
        return this.a.replace((AbstractC0717o2) obj, obj2, obj3);
    }

    public final boolean a(Set set) {
        Iterator it = set.iterator();
        while (it.hasNext()) {
            if (this.a.containsKey((AbstractC0717o2) it.next())) {
                return true;
            }
        }
        return false;
    }
}
