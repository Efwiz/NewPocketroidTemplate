package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.hz, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2192hz extends AbstractC2502lz {
    private static final C2192hz c = new C2192hz(AbstractC1639am.i());
    private final List b;

    private C2192hz(AbstractC1639am abstractC1639am) {
        this.b = abstractC1639am;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static C2192hz a(AbstractC3467yx.a aVar, C0722p1 c0722p1) {
        if (aVar.a().isEmpty()) {
            return c;
        }
        C1507Xl c1507XlG = AbstractC1639am.g();
        Iterator<AbstractC3467yx> it = aVar.a().iterator();
        while (it.hasNext()) {
            c1507XlG.a(AbstractC2502lz.a(it.next(), c0722p1));
        }
        return new C2192hz(c1507XlG.a());
    }

    @Override // com.android.tools.r8.shaking.InterfaceC3680a0
    public final void a(com.android.tools.r8.graph.Q0 q0) {
        Iterator it = this.b.iterator();
        while (it.hasNext()) {
            ((AbstractC2502lz) it.next()).a(q0);
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC2502lz
    final boolean a(Consumer consumer, C0702m c0702m) {
        final ArrayList arrayList = new ArrayList();
        Iterator it = this.b.iterator();
        boolean zA = false;
        while (it.hasNext()) {
            zA |= ((AbstractC2502lz) it.next()).a(new Consumer() { // from class: com.android.tools.r8.internal.hz$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C2192hz.a(arrayList, (AbstractC3467yx) obj);
                }
            }, c0702m);
        }
        consumer.accept(new AbstractC3467yx.a(arrayList));
        return zA;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(List list, AbstractC3467yx abstractC3467yx) {
        if (abstractC3467yx != null) {
            list.add(abstractC3467yx);
        }
    }
}
