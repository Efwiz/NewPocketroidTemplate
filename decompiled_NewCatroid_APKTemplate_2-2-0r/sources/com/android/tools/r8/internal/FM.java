package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class FM extends SF {
    static final /* synthetic */ boolean c = true;
    private final C0702m b;

    public FM(C0702m c0702m, EnumC0928Dk enumC0928Dk) {
        if (!c && !enumC0928Dk.b()) {
            throw new AssertionError();
        }
        this.b = c0702m;
    }

    @Override // com.android.tools.r8.internal.SF
    public final Collection a(C2688oE c2688oE) {
        ArrayList<EM> arrayList = new ArrayList();
        Iterator it = c2688oE.iterator();
        while (it.hasNext()) {
            final C0729q2 c0729q2 = (C0729q2) it.next();
            if (!AbstractC2734ot.b(arrayList, new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.FM$$ExternalSyntheticLambda0
                @Override // com.android.tools.r8.internal.InterfaceC3205vM
                public final boolean apply(Object obj) {
                    return this.f$0.a(c0729q2, (EM) obj);
                }
            })) {
                EM em = new EM();
                boolean zA = em.a(this.b, c0729q2);
                if (!c && !zA) {
                    throw new AssertionError();
                }
                arrayList.add(em);
            }
        }
        LinkedList linkedList = new LinkedList();
        for (EM em2 : arrayList) {
            if (!em2.a.m()) {
                linkedList.add(em2.a);
            }
        }
        return linkedList;
    }

    @Override // com.android.tools.r8.internal.WL
    public final String e() {
        return "PreserveMethodCharacteristics";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(C0729q2 c0729q2, EM em) {
        return em.a(this.b, c0729q2);
    }
}
