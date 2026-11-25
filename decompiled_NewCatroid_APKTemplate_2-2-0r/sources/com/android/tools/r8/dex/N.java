package com.android.tools.r8.dex;

import com.android.tools.r8.graph.C0729q2;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class N implements Comparable {
    static final /* synthetic */ boolean f = true;
    public final Set a;
    public int b;
    public int c;
    public boolean d;
    final /* synthetic */ P e;

    public N(P p) {
        this.e = p;
        this.b = -1;
        this.c = -1;
        this.d = false;
        this.a = new HashSet();
    }

    public final void a() {
        r0 r0Var = new r0(this.e.g);
        Iterator it = this.a.iterator();
        while (it.hasNext()) {
            r0Var.a((C0729q2) it.next());
        }
        this.b = r0Var.h();
        this.c = r0Var.i();
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        N n = (N) obj;
        boolean z = f;
        if (!z && (this.a.isEmpty() || n.a.isEmpty() || this.b == -1 || this.c == -1)) {
            throw new AssertionError();
        }
        if (this == n) {
            return 0;
        }
        int i = this.c;
        int i2 = n.c;
        if (i != i2 || (i = this.b) != (i2 = n.b)) {
            return i - i2;
        }
        if (this.a.size() != n.a.size()) {
            return this.a.size() - n.a.size();
        }
        Comparator comparator = P.j;
        ArrayList arrayList = new ArrayList(this.a);
        Collections.sort(arrayList, comparator);
        C0729q2 c0729q2 = (C0729q2) arrayList.iterator().next();
        ArrayList arrayList2 = new ArrayList(n.a);
        Collections.sort(arrayList2, comparator);
        int iCompare = comparator.compare(c0729q2, (C0729q2) arrayList2.iterator().next());
        if (z || iCompare != 0) {
            return iCompare;
        }
        throw new AssertionError();
    }

    public N(P p, HashSet hashSet) {
        this.e = p;
        this.b = -1;
        this.c = -1;
        this.d = false;
        this.a = hashSet;
        a();
    }
}
