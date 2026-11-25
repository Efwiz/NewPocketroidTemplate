package com.android.tools.r8.internal;

import java.util.Comparator;
import java.util.Map;
import java.util.SortedSet;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Yq, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1539Yq extends AbstractC1658b1 {
    final /* synthetic */ C2028fr a;

    C1539Yq(C2028fr c2028fr) {
        this.a = c2028fr;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final void clear() {
        this.a.clear();
    }

    @Override // java.util.SortedSet
    public final Comparator comparator() {
        return this.a.i.c().comparator();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        C1431Uq c1431UqD;
        if (!(obj instanceof Map.Entry)) {
            return false;
        }
        Map.Entry entry = (Map.Entry) obj;
        return entry.getKey() != null && (entry.getKey() instanceof Integer) && (c1431UqD = this.a.i.d(((Integer) entry.getKey()).intValue())) != null && this.a.d(c1431UqD.a) && entry.equals(c1431UqD);
    }

    @Override // java.util.SortedSet
    public final Object first() {
        return this.a.k();
    }

    @Override // java.util.SortedSet
    public final SortedSet headSet(Object obj) {
        return ((C2028fr) this.a.c(((InterfaceC0934Dq) obj).a())).c();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean isEmpty() {
        C2028fr c2028fr = this.a;
        C1431Uq c1431Uq = c2028fr.i.d;
        return !(c2028fr.k() != null);
    }

    @Override // com.android.tools.r8.internal.AbstractC1658b1, com.android.tools.r8.internal.U0, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, com.android.tools.r8.internal.ZJ, com.android.tools.r8.internal.InterfaceC2529mK, java.util.Set, com.android.tools.r8.internal.InterfaceC2842qK
    public final YJ iterator() {
        return new C1719br(this.a);
    }

    @Override // java.util.SortedSet
    public final Object last() {
        return this.a.l();
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
        C1431Uq c1431UqD = this.a.i.d(((Integer) entry.getKey()).intValue());
        if (c1431UqD != null && this.a.d(c1431UqD.a)) {
            this.a.remove(c1431UqD.a);
        }
        return c1431UqD != null;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        Object it = iterator();
        int i = 0;
        while (((AbstractC2113gr) it).hasNext()) {
            i++;
            ((C1719br) it).a();
        }
        return i;
    }

    @Override // java.util.SortedSet
    public final SortedSet subSet(Object obj, Object obj2) {
        return ((C2028fr) this.a.a(((InterfaceC0934Dq) obj).a(), ((InterfaceC0934Dq) obj2).a())).c();
    }

    @Override // java.util.SortedSet
    public final SortedSet tailSet(Object obj) {
        return ((C2028fr) this.a.b(((InterfaceC0934Dq) obj).a())).c();
    }
}
