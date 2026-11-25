package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0729q2;
import java.util.Collection;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class HK {
    private final C2688oE a;
    private final Set b;
    private final Set c;
    private final Set d;

    HK(Set set, Set set2, C0729q2 c0729q2) {
        this.a = new C2688oE(c0729q2);
        this.b = com.android.tools.r8.utils.T2.a(c0729q2);
        this.c = set;
        this.d = set2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean b(C0729q2 c0729q2) {
        return !this.b.contains(c0729q2);
    }

    final void a(Set set, Set set2, C0729q2 c0729q2) {
        this.a.a(c0729q2);
        this.b.add(c0729q2);
        AbstractC2734ot.a(AbstractC2734ot.c(set, new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.HK$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return this.f$0.a((C0729q2) obj);
            }
        }), (Collection) this.c);
        this.c.remove(c0729q2);
        AbstractC2734ot.a(AbstractC2734ot.c(set2, new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.HK$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return this.f$0.b((C0729q2) obj);
            }
        }), (Collection) this.d);
        this.d.remove(c0729q2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(C0729q2 c0729q2) {
        return !this.b.contains(c0729q2);
    }
}
