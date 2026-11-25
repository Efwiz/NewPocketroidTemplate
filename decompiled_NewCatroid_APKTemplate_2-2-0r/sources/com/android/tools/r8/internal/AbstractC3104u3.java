package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0664h;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.profile.art.ArtProfileProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.u3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3104u3 {
    public static AbstractC3104u3 a(C0664h c0664h, com.android.tools.r8.utils.A1 a1) {
        D3 d3Q0 = a1.q0();
        List<ArtProfileProvider> listA = d3Q0.a();
        ArrayList arrayList = new ArrayList(com.android.tools.r8.utils.M.a(d3Q0.d()) + listA.size());
        for (ArtProfileProvider artProfileProvider : listA) {
            C2434l3.a aVarA = C2434l3.a(artProfileProvider, a1);
            artProfileProvider.getArtProfile(aVarA);
            arrayList.add(aVarA.build());
        }
        if (d3Q0.d()) {
            arrayList.add(a(c0664h));
        }
        return arrayList.isEmpty() ? C1345Sf.d() : new C3049tH(arrayList);
    }

    public static C1345Sf b() {
        return C1345Sf.d();
    }

    public abstract C3049tH a();

    public abstract AbstractC3104u3 a(C0702m c0702m, AbstractC1476Wj abstractC1476Wj);

    public abstract AbstractC3104u3 a(com.android.tools.r8.graph.u5 u5Var);

    public abstract void a(C0702m c0702m);

    public abstract AbstractC3104u3 b(C0702m c0702m);

    public abstract boolean c();

    public abstract boolean isEmpty();

    private static C2434l3 a(C0664h c0664h) {
        boolean z = C2434l3.b;
        final C2434l3.a aVar = new C2434l3.a();
        for (C0729q2 c0729q2 : c0664h.e()) {
            aVar.a(C2966s3.d().a(c0729q2.getType()).build());
            c0729q2.f(new Consumer() { // from class: com.android.tools.r8.internal.u3$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    AbstractC3104u3.a(aVar, (com.android.tools.r8.graph.W0) obj);
                }
            });
        }
        return aVar.build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C2434l3.a aVar, com.android.tools.r8.graph.W0 w0) {
        aVar.a(A3.d().b(w0.getReference()).a(new Consumer() { // from class: com.android.tools.r8.internal.u3$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AbstractC3104u3.a((C3.a) obj);
            }
        }).build());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C3.a aVar) {
        aVar.d().f().e();
    }
}
