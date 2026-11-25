package com.android.tools.r8.internal;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.Spliterator;
import java.util.function.BiFunction;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.hm, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2178hm implements Map, Serializable {
    static final Map.Entry[] d = new Map.Entry[0];
    private transient AbstractC1048Hm a;
    private transient AbstractC1048Hm b;
    private transient AbstractC1271Pl c;

    AbstractC2178hm() {
    }

    public static AbstractC2178hm a(Comparable comparable, Comparable comparable2, Comparable comparable3, Comparable comparable4) {
        return PS.a(2, new Map.Entry[]{new AbstractMap.SimpleImmutableEntry(comparable, comparable2), new AbstractMap.SimpleImmutableEntry(comparable3, comparable4)});
    }

    public static AbstractC2178hm b(Object obj, Object obj2) {
        return new C3439yV(obj, obj2);
    }

    public static C1946em e() {
        return new C1946em(4);
    }

    public static AbstractC2178hm s() {
        return PS.h;
    }

    @Override // java.util.Map
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    public final Object compute(Object obj, BiFunction biFunction) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    public final Object computeIfAbsent(Object obj, Function function) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    public final Object computeIfPresent(Object obj, BiFunction biFunction) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    public boolean containsKey(Object obj) {
        return get(obj) != null;
    }

    @Override // java.util.Map
    public boolean containsValue(Object obj) {
        return values().contains(obj);
    }

    @Override // java.util.Map
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Map) {
            return entrySet().equals(((Map) obj).entrySet());
        }
        return false;
    }

    @Override // java.util.Map
    public abstract Object get(Object obj);

    @Override // java.util.Map
    public final Object getOrDefault(Object obj, Object obj2) {
        Object obj3 = get(obj);
        return obj3 != null ? obj3 : obj2;
    }

    @Override // java.util.Map
    public int hashCode() {
        return OU.a((Set) entrySet());
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        return size() == 0;
    }

    abstract AbstractC1048Hm k();

    abstract AbstractC1048Hm l();

    abstract AbstractC1271Pl m();

    @Override // java.util.Map
    public final Object merge(Object obj, Object obj2, BiFunction biFunction) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map, java.util.SortedMap
    /* JADX INFO: renamed from: n, reason: merged with bridge method [inline-methods] */
    public AbstractC1048Hm entrySet() {
        AbstractC1048Hm abstractC1048Hm = this.a;
        if (abstractC1048Hm != null) {
            return abstractC1048Hm;
        }
        AbstractC1048Hm abstractC1048HmK = k();
        this.a = abstractC1048HmK;
        return abstractC1048HmK;
    }

    abstract boolean o();

    AbstractC2963s10 p() {
        return new C1864dm(entrySet().iterator());
    }

    @Override // java.util.Map
    public final Object put(Object obj, Object obj2) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    public final void putAll(Map map) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    public final Object putIfAbsent(Object obj, Object obj2) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map, java.util.SortedMap
    /* JADX INFO: renamed from: q, reason: merged with bridge method [inline-methods] */
    public AbstractC1048Hm keySet() {
        AbstractC1048Hm abstractC1048Hm = this.b;
        if (abstractC1048Hm != null) {
            return abstractC1048Hm;
        }
        AbstractC1048Hm abstractC1048HmL = l();
        this.b = abstractC1048HmL;
        return abstractC1048HmL;
    }

    Spliterator r() {
        return AbstractC3142ub.a(entrySet().spliterator(), new YF$$ExternalSyntheticLambda1());
    }

    @Override // java.util.Map
    public final Object remove(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    public final boolean replace(Object obj, Object obj2, Object obj3) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    public final void replaceAll(BiFunction biFunction) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map, java.util.SortedMap
    /* JADX INFO: renamed from: t, reason: merged with bridge method [inline-methods] */
    public AbstractC1271Pl values() {
        AbstractC1271Pl abstractC1271Pl = this.c;
        if (abstractC1271Pl != null) {
            return abstractC1271Pl;
        }
        AbstractC1271Pl abstractC1271PlM = m();
        this.c = abstractC1271PlM;
        return abstractC1271PlM;
    }

    public final String toString() {
        int size = size();
        AbstractC2625nb.a(size, "size");
        StringBuilder sbAppend = new StringBuilder((int) Math.min(((long) size) * 8, 1073741824L)).append('{');
        boolean z = true;
        for (Map.Entry entry : entrySet()) {
            if (!z) {
                sbAppend.append(", ");
            }
            sbAppend.append(entry.getKey()).append('=').append(entry.getValue());
            z = false;
        }
        return sbAppend.append('}').toString();
    }

    @Override // java.util.Map
    public final boolean remove(Object obj, Object obj2) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    public final Object replace(Object obj, Object obj2) {
        throw new UnsupportedOperationException();
    }

    static IllegalArgumentException a(String str, Object obj, Object obj2) {
        String strValueOf = String.valueOf(obj);
        String strValueOf2 = String.valueOf(obj2);
        return new IllegalArgumentException(new StringBuilder(strValueOf2.length() + strValueOf.length() + str.length() + 34).append("Multiple entries with same ").append(str).append(": ").append(strValueOf).append(" and ").append(strValueOf2).toString());
    }

    public static AbstractC2178hm a(Map map) {
        if ((map instanceof AbstractC2178hm) && !(map instanceof SortedMap)) {
            AbstractC2178hm abstractC2178hm = (AbstractC2178hm) map;
            if (!abstractC2178hm.o()) {
                return abstractC2178hm;
            }
        } else if (map instanceof EnumMap) {
            EnumMap enumMap = new EnumMap((EnumMap) map);
            for (Map.Entry entry : enumMap.entrySet()) {
                AbstractC2625nb.a(entry.getKey(), entry.getValue());
            }
            return C1351Sl.a(enumMap);
        }
        Map.Entry[] entryArr = (Map.Entry[]) AbstractC2734ot.a(map.entrySet(), (Object[]) d);
        int length = entryArr.length;
        if (length == 0) {
            return PS.h;
        }
        if (length != 1) {
            return PS.a(entryArr.length, entryArr);
        }
        Map.Entry entry2 = entryArr[0];
        return new C3439yV(entry2.getKey(), entry2.getValue());
    }
}
