package com.android.tools.r8.naming;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.internal.e8$$ExternalSyntheticLambda0;
import com.android.tools.r8.internal.jB$$ExternalSyntheticLambda12;
import com.android.tools.r8.ir.optimize.v0$$ExternalSyntheticLambda5;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

/* JADX INFO: renamed from: com.android.tools.r8.naming.w, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3668w {
    static final /* synthetic */ boolean d = true;
    private final C3669x a;
    private final C0702m b;
    private final Set c = OU.c();

    C3668w(C3669x c3669x) {
        this.a = c3669x;
        this.b = c3669x.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ArrayList a(List list) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            AbstractC0727q0 abstractC0727q0 = (AbstractC0727q0) it.next();
            if (abstractC0727q0 != null && this.c.add(abstractC0727q0.d)) {
                TreeSet treeSetA = a(abstractC0727q0);
                boolean z = d;
                if (!z && treeSetA.isEmpty()) {
                    throw new AssertionError();
                }
                if (!z && !treeSetA.stream().allMatch(new jB$$ExternalSyntheticLambda12())) {
                    throw new AssertionError();
                }
                if (!z) {
                    Stream map = treeSetA.stream().map(new e8$$ExternalSyntheticLambda0());
                    Set set = this.c;
                    Objects.requireNonNull(set);
                    if (!map.allMatch(new v0$$ExternalSyntheticLambda5(set))) {
                        throw new AssertionError();
                    }
                }
                arrayList.add(treeSetA);
            }
        }
        return arrayList;
    }

    private TreeSet a(AbstractC0727q0 abstractC0727q0) {
        TreeSet treeSet = new TreeSet(Comparator.comparing(new e8$$ExternalSyntheticLambda0()));
        ArrayDeque arrayDeque = new ArrayDeque();
        arrayDeque.add(abstractC0727q0.d);
        while (!arrayDeque.isEmpty()) {
            C0767v2 c0767v2 = (C0767v2) arrayDeque.removeFirst();
            AbstractC0727q0 abstractC0727q0C = this.b.c(c0767v2);
            if (abstractC0727q0C != null) {
                for (C0767v2 c0767v3 : abstractC0727q0C.g.a) {
                    if (this.c.add(c0767v3)) {
                        arrayDeque.add(c0767v3);
                    }
                }
                if (abstractC0727q0C.isInterface()) {
                    treeSet.add(abstractC0727q0C);
                    for (C0767v2 c0767v4 : this.a.b.c(c0767v2)) {
                        if (this.c.add(c0767v4)) {
                            arrayDeque.add(c0767v4);
                        }
                    }
                } else if (abstractC0727q0C.d != this.b.a().Z1) {
                    if (this.c.add(abstractC0727q0C.f)) {
                        arrayDeque.add(abstractC0727q0C.f);
                    }
                    for (C0767v2 c0767v5 : this.a.b.a(c0767v2)) {
                        if (this.c.add(c0767v5)) {
                            arrayDeque.add(c0767v5);
                        }
                    }
                }
            }
        }
        return treeSet;
    }
}
