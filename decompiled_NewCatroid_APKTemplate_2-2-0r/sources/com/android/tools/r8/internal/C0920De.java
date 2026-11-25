package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.C3912j0;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.function.IntFunction;

/* JADX INFO: renamed from: com.android.tools.r8.internal.De, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0920De implements InterfaceC2135h8 {
    private final C0702m a;
    private final C0891Ce b;

    public C0920De(C0702m c0702m) {
        this.a = c0702m;
        this.b = new C0891Ce(c0702m);
    }

    public static C0920De a(C0702m c0702m) {
        Iterator it = c0702m.a().f3.iterator();
        while (it.hasNext()) {
            if (c0702m.f().d((C0767v2) it.next()) != null) {
                return new C0920De(c0702m);
            }
        }
        return null;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2135h8
    public final void a(Collection collection, AbstractC2591n8 abstractC2591n8, ExecutorService executorService) {
        Iterator it = this.a.a().f3.iterator();
        while (it.hasNext()) {
            AbstractC0727q0 abstractC0727q0D = this.a.g().d((C0767v2) it.next());
            if (abstractC0727q0D != null && abstractC0727q0D.e0()) {
                C0729q2 c0729q2A0 = abstractC0727q0D.a0();
                c0729q2A0.a(a(c0729q2A0, c0729q2A0.w1()));
                c0729q2A0.b(a(c0729q2A0, c0729q2A0.N1()));
            }
        }
    }

    private com.android.tools.r8.graph.T0[] a(C0729q2 c0729q2, List list) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            com.android.tools.r8.graph.T0 t0A = (com.android.tools.r8.graph.T0) it.next();
            com.android.tools.r8.graph.Y0 y0A = this.b.a(t0A.getReference(), c0729q2);
            if (y0A != null) {
                t0A = t0A.a(this.a, y0A, C3912j0.b());
            }
            arrayList.add(t0A);
        }
        return (com.android.tools.r8.graph.T0[]) arrayList.toArray(new IntFunction() { // from class: com.android.tools.r8.internal.De$$ExternalSyntheticLambda0
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return C0920De.a(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.tools.r8.graph.T0[] a(int i) {
        return new com.android.tools.r8.graph.T0[i];
    }
}
