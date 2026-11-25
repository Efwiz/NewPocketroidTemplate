package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

/* JADX INFO: renamed from: com.android.tools.r8.internal.vB, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3189vB extends SF {
    static final /* synthetic */ boolean c = true;
    private final int b;

    public C3189vB(C0702m c0702m) {
        int iE;
        if (c0702m.q()) {
            iE = c0702m.Y().O0().e();
        } else {
            c0702m.Y().O0().getClass();
            iE = 100;
        }
        this.b = iE;
        if (!c && iE < 2) {
            throw new AssertionError();
        }
    }

    @Override // com.android.tools.r8.internal.SF
    public final Collection a(C2688oE c2688oE) {
        if (c2688oE.size() <= this.b || c2688oE.l()) {
            return Collections.singletonList(c2688oE);
        }
        LinkedList linkedList = new LinkedList();
        C2688oE c2688oE2 = new C2688oE();
        linkedList.add(c2688oE2);
        Iterator it = c2688oE.iterator();
        while (it.hasNext()) {
            C0729q2 c0729q2 = (C0729q2) it.next();
            if (c2688oE2.size() == this.b) {
                c2688oE2 = new C2688oE();
                linkedList.add(c2688oE2);
            }
            c2688oE2.a(c0729q2);
        }
        if (c2688oE2.size() == 1) {
            if (this.b == 2) {
                C2688oE c2688oE3 = (C2688oE) linkedList.removeLast();
                if (!c && c2688oE3 != c2688oE2) {
                    throw new AssertionError();
                }
            } else {
                c2688oE2.a(((C2688oE) linkedList.getFirst()).n());
            }
        }
        return linkedList;
    }

    @Override // com.android.tools.r8.internal.WL
    public final String e() {
        return "LimitGroups";
    }

    @Override // com.android.tools.r8.internal.WL
    public final boolean f() {
        return true;
    }
}
