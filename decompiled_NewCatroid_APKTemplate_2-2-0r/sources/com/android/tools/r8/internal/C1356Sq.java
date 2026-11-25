package com.android.tools.r8.internal;

import java.util.Comparator;
import java.util.Map;
import java.util.SortedSet;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Sq, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1356Sq extends AbstractC1658b1 {
    final Comparator a = new C1330Rq(this);
    final /* synthetic */ C2261ir b;

    C1356Sq(C2261ir c2261ir) {
        this.b = c2261ir;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final void clear() {
        this.b.clear();
    }

    @Override // java.util.SortedSet
    public final Comparator comparator() {
        return this.a;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        if (entry.getKey() == null || !(entry.getKey() instanceof Integer)) {
            return false;
        }
        return entry.equals(this.b.d(((Integer) entry.getKey()).intValue()));
    }

    @Override // java.util.SortedSet
    public final Object first() {
        return this.b.d;
    }

    @Override // java.util.SortedSet
    public final SortedSet headSet(Object obj) {
        return ((C2028fr) this.b.c(((InterfaceC0934Dq) obj).a())).c();
    }

    @Override // com.android.tools.r8.internal.AbstractC1658b1, com.android.tools.r8.internal.U0, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.ZJ, com.android.tools.r8.internal.InterfaceC2529mK, java.util.Set, com.android.tools.r8.internal.InterfaceC2842qK
    public final YJ iterator() {
        return new C1457Vq(this.b);
    }

    @Override // java.util.SortedSet
    public final Object last() {
        return this.b.e;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean remove(Object obj) {
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        if (entry.getKey() == null || !(entry.getKey() instanceof Integer)) {
            return false;
        }
        C1431Uq c1431UqD = this.b.d(((Integer) entry.getKey()).intValue());
        if (c1431UqD != null) {
            this.b.remove(c1431UqD.a);
        }
        return c1431UqD != null;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.b.c;
    }

    @Override // java.util.SortedSet
    public final SortedSet subSet(Object obj, Object obj2) {
        return ((C2028fr) this.b.a(((InterfaceC0934Dq) obj).a(), ((InterfaceC0934Dq) obj2).a())).c();
    }

    @Override // java.util.SortedSet
    public final SortedSet tailSet(Object obj) {
        return ((C2028fr) this.b.b(((InterfaceC0934Dq) obj).a())).c();
    }
}
