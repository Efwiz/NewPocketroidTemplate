package com.android.tools.r8.internal;

import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.function.BiFunction;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Vl, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1452Vl extends C2491lr {
    private final InterfaceC2415kr b;

    private C1452Vl(C2807pq c2807pq) {
        this.b = c2807pq;
    }

    @Override // com.android.tools.r8.internal.C2491lr, com.android.tools.r8.internal.InterfaceC2415kr
    public final InterfaceC2415kr a(int i, int i2) {
        return this.b.a(i, i2);
    }

    @Override // com.android.tools.r8.internal.C2491lr, com.android.tools.r8.internal.InterfaceC2415kr
    public final InterfaceC2415kr b(int i) {
        return this.b.b(i);
    }

    @Override // com.android.tools.r8.internal.C2491lr, com.android.tools.r8.internal.C1021Gq, com.android.tools.r8.internal.InterfaceC0992Fq
    public final InterfaceC2529mK c() {
        return this.b.c();
    }

    @Override // java.util.Map
    public final Object compute(Object obj, BiFunction biFunction) {
        throw new C3405y10("Should not modify an immutable structure");
    }

    @Override // java.util.Map
    public final Object computeIfAbsent(Object obj, Function function) {
        throw new C3405y10("Should not modify an immutable structure");
    }

    @Override // java.util.Map
    public final Object computeIfPresent(Object obj, BiFunction biFunction) {
        throw new C3405y10("Should not modify an immutable structure");
    }

    @Override // com.android.tools.r8.internal.C2491lr, com.android.tools.r8.internal.InterfaceC2415kr
    public final int d() {
        return this.b.d();
    }

    @Override // com.android.tools.r8.internal.C2491lr, com.android.tools.r8.internal.C1021Gq
    /* JADX INFO: renamed from: e */
    public final InterfaceC2529mK entrySet() {
        return this.b.entrySet();
    }

    @Override // com.android.tools.r8.internal.C2491lr, com.android.tools.r8.internal.C1021Gq, java.util.Map
    public final InterfaceC2842qK entrySet() {
        return this.b.entrySet();
    }

    @Override // com.android.tools.r8.internal.C2491lr, com.android.tools.r8.internal.InterfaceC2415kr, java.util.SortedMap
    public final Integer firstKey() {
        return this.b.firstKey();
    }

    @Override // com.android.tools.r8.internal.C1021Gq, com.android.tools.r8.internal.InterfaceC3087tq
    public final Object get(int i) {
        return this.b.get(i);
    }

    @Override // java.util.Map
    public final Object getOrDefault(Object obj, Object obj2) {
        return this.b.getOrDefault(obj, obj2);
    }

    @Override // com.android.tools.r8.internal.C2491lr, java.util.SortedMap
    public final SortedMap headMap(Object obj) {
        return this.b.headMap((Integer) obj);
    }

    @Override // com.android.tools.r8.internal.C1021Gq, java.util.Map
    public final boolean isEmpty() {
        return this.b.isEmpty();
    }

    @Override // com.android.tools.r8.internal.C2491lr, com.android.tools.r8.internal.C1021Gq, com.android.tools.r8.internal.InterfaceC0992Fq, java.util.Map, com.android.tools.r8.internal.InterfaceC2415kr, java.util.SortedMap
    public final InterfaceC1277Pr keySet() {
        return this.b.keySet();
    }

    @Override // com.android.tools.r8.internal.C2491lr, com.android.tools.r8.internal.InterfaceC2415kr, java.util.SortedMap
    public final Integer lastKey() {
        return this.b.lastKey();
    }

    @Override // com.android.tools.r8.internal.E, java.util.Map
    public final /* bridge */ /* synthetic */ Object put(Object obj, Object obj2) {
        put((Integer) obj, obj2);
        throw null;
    }

    @Override // com.android.tools.r8.internal.C1021Gq, java.util.Map
    public final void putAll(Map map) {
        throw new C3405y10("Should not modify an immutable structure");
    }

    @Override // java.util.Map
    public final Object putIfAbsent(Object obj, Object obj2) {
        throw new C3405y10("Should not modify an immutable structure");
    }

    @Override // com.android.tools.r8.internal.E, com.android.tools.r8.internal.InterfaceC3087tq
    public final Object remove(int i) {
        throw new C3405y10("Should not modify an immutable structure");
    }

    @Override // com.android.tools.r8.internal.C1021Gq, com.android.tools.r8.internal.InterfaceC2100gj, java.util.Map
    public final int size() {
        return this.b.size();
    }

    @Override // com.android.tools.r8.internal.C2491lr, java.util.SortedMap
    public final SortedMap subMap(Object obj, Object obj2) {
        return this.b.subMap((Integer) obj, (Integer) obj2);
    }

    @Override // com.android.tools.r8.internal.C2491lr, java.util.SortedMap
    public final SortedMap tailMap(Object obj) {
        return this.b.tailMap((Integer) obj);
    }

    @Override // com.android.tools.r8.internal.C2491lr, com.android.tools.r8.internal.InterfaceC2415kr
    public final int a() {
        return this.b.a();
    }

    @Override // com.android.tools.r8.internal.C2491lr, com.android.tools.r8.internal.InterfaceC2415kr
    /* JADX INFO: renamed from: b */
    public final InterfaceC2415kr headMap(Integer num) {
        return this.b.headMap(num);
    }

    @Override // com.android.tools.r8.internal.C2491lr, com.android.tools.r8.internal.C1021Gq, com.android.tools.r8.internal.InterfaceC0992Fq
    public final InterfaceC2842qK c() {
        return this.b.c();
    }

    @Override // com.android.tools.r8.internal.C2491lr, com.android.tools.r8.internal.C1021Gq, java.util.Map
    public final Set entrySet() {
        return this.b.entrySet();
    }

    @Override // com.android.tools.r8.internal.C2491lr, java.util.SortedMap
    public final Object firstKey() {
        return this.b.firstKey();
    }

    @Override // com.android.tools.r8.internal.E, com.android.tools.r8.internal.InterfaceC0992Fq, java.util.Map
    public final Object get(Object obj) {
        return this.b.get(obj);
    }

    @Override // com.android.tools.r8.internal.C2491lr, com.android.tools.r8.internal.C1021Gq, com.android.tools.r8.internal.InterfaceC0992Fq, java.util.Map, com.android.tools.r8.internal.InterfaceC2415kr, java.util.SortedMap
    public final InterfaceC1357Sr keySet() {
        return this.b.keySet();
    }

    @Override // com.android.tools.r8.internal.C2491lr, java.util.SortedMap
    public final Object lastKey() {
        return this.b.lastKey();
    }

    @Override // java.util.Map
    public final boolean remove(Object obj, Object obj2) {
        throw new C3405y10("Should not modify an immutable structure");
    }

    @Override // com.android.tools.r8.internal.C2491lr, com.android.tools.r8.internal.InterfaceC2415kr
    /* JADX INFO: renamed from: a */
    public final InterfaceC2415kr tailMap(Integer num) {
        return this.b.tailMap(num);
    }

    @Override // com.android.tools.r8.internal.C2491lr, com.android.tools.r8.internal.InterfaceC2415kr
    public final InterfaceC2415kr c(int i) {
        return this.b.c(i);
    }

    @Override // com.android.tools.r8.internal.C2491lr, com.android.tools.r8.internal.C1021Gq, java.util.Map
    public final Set keySet() {
        return this.b.keySet();
    }

    @Override // com.android.tools.r8.internal.E, java.util.Map
    public final Object remove(Object obj) {
        throw new C3405y10("Should not modify an immutable structure");
    }

    @Override // com.android.tools.r8.internal.C2491lr, com.android.tools.r8.internal.InterfaceC2415kr
    /* JADX INFO: renamed from: a */
    public final InterfaceC2415kr subMap(Integer num, Integer num2) {
        return this.b.subMap(num, num2);
    }

    @Override // com.android.tools.r8.internal.E, com.android.tools.r8.internal.InterfaceC3087tq
    public final Object a(int i, Object obj) {
        throw new C3405y10("Should not modify an immutable structure");
    }

    @Override // com.android.tools.r8.internal.E, com.android.tools.r8.internal.InterfaceC0992Fq
    /* JADX INFO: renamed from: a */
    public final Object put(Integer num, Object obj) {
        throw new C3405y10("Should not modify an immutable structure");
    }
}
