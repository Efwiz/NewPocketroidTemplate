package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.tG, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3048tG {
    static final /* synthetic */ boolean d = true;
    private final AbstractC0727q0 a;
    private final List b;
    private final List c;

    private C3048tG(AbstractC0727q0 abstractC0727q0, ArrayList arrayList, ArrayList arrayList2) {
        this.a = abstractC0727q0;
        this.b = arrayList;
        this.c = arrayList2;
    }

    public static C3048tG a(final C0702m c0702m, AbstractC0727q0 abstractC0727q0, Consumer consumer) {
        if (!d && !abstractC0727q0.D1()) {
            throw new AssertionError();
        }
        AbstractC0727q0 abstractC0727q0C = abstractC0727q0.G1() ? abstractC0727q0 : c0702m.c(abstractC0727q0.g1());
        if (abstractC0727q0C == null) {
            if (consumer == null) {
                return null;
            }
            consumer.accept(abstractC0727q0);
            return null;
        }
        final ArrayList arrayList = new ArrayList(abstractC0727q0C.i1().size());
        final ArrayList arrayList2 = new ArrayList();
        abstractC0727q0C.g(new Consumer() { // from class: com.android.tools.r8.internal.tG$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C3048tG.a(c0702m, arrayList, arrayList2, (C0767v2) obj);
            }
        });
        return new C3048tG(abstractC0727q0C, arrayList, arrayList2);
    }

    public final AbstractC0727q0 b() {
        return this.a;
    }

    public final List c() {
        return this.b;
    }

    public final List d() {
        return this.c;
    }

    public final boolean e() {
        return !this.c.isEmpty();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0702m c0702m, List list, List list2, C0767v2 c0767v2) {
        AbstractC0727q0 abstractC0727q0C = c0702m.c(c0767v2);
        if (abstractC0727q0C != null) {
            list.add(abstractC0727q0C);
        } else {
            list2.add(c0767v2);
        }
    }

    public final Iterable a() {
        return AbstractC2734ot.a(AbstractC2734ot.c(this.b, new InterfaceC3205vM() { // from class: com.android.tools.r8.internal.tG$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return ((AbstractC0727q0) obj).A1();
            }
        }), new InterfaceC3010sj() { // from class: com.android.tools.r8.internal.tG$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((AbstractC0727q0) obj).m();
            }
        });
    }
}
