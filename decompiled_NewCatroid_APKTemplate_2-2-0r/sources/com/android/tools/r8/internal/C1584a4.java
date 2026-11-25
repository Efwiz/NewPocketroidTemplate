package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0737r2;
import java.util.Collection;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.a4, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
class C1584a4 extends AbstractC1739c4 {
    private final InterfaceC2047g4 b;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    C1584a4(C0684j2 c0684j2, InterfaceC2047g4 interfaceC2047g4) {
        this(c0684j2, interfaceC2047g4, 0);
        c0684j2.f.toString();
    }

    protected com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
        return l.B;
    }

    C1584a4(C0684j2 c0684j2, InterfaceC2047g4 interfaceC2047g4, int i) {
        super(c0684j2);
        this.b = interfaceC2047g4;
    }

    @Override // com.android.tools.r8.internal.AbstractC1739c4
    public Collection a(I7 i7, C0702m c0702m, W3 w3, C1230Ob c1230Ob, AC ac) {
        com.android.tools.r8.graph.o5 o5VarA = a(c0702m, c1230Ob);
        w3.e(o5VarA, c1230Ob.b());
        return AbstractC1639am.c(new I7(184, o5VarA.getReference(), false));
    }

    private com.android.tools.r8.graph.o5 a(final C0702m c0702m, C1230Ob c1230Ob) {
        return c0702m.D().b(new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.a4$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return this.f$0.a(l);
            }
        }, c1230Ob.a(), c0702m, new Consumer() { // from class: com.android.tools.r8.internal.a4$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c0702m, (com.android.tools.r8.synthesis.H) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(final C0702m c0702m, com.android.tools.r8.synthesis.H h) {
        h.b().a(a(c0702m.a())).a(C0725p4.Q()).a(new com.android.tools.r8.synthesis.G() { // from class: com.android.tools.r8.internal.a4$$ExternalSyntheticLambda2
            @Override // com.android.tools.r8.synthesis.G
            public final com.android.tools.r8.graph.W a(C0684j2 c0684j2) {
                return this.f$0.a(c0702m, c0684j2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public com.android.tools.r8.graph.W a(C0702m c0702m, C0684j2 c0684j2) {
        return this.b.a(c0702m.a(), c0684j2);
    }

    public C0737r2 a(C0722p1 c0722p1) {
        return this.a.h;
    }
}
