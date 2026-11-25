package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ng, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1208Ng extends AbstractC1236Og {
    private final C0767v2 d;
    private final C3380xg e;

    public C1208Ng(C0702m c0702m, C0767v2 c0767v2, C0767v2 c0767v3, C3380xg c3380xg) {
        super(c0702m, c0767v2);
        this.d = c0767v3;
        this.e = c3380xg;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(List list, C0722p1 c0722p1, C2284j7 c2284j7, Integer num, B1 b1) {
        M7 m7 = new M7();
        list.add(new O7(EnumC1812d20.a(c0722p1.X1), 0));
        a(list, b1, c0722p1.X1);
        list.add(new I7(182, c0722p1.k4.i, false));
        EnumC1076Il enumC1076Il = EnumC1076Il.EQ;
        EnumC1812d20 enumC1812d20 = EnumC1812d20.INT;
        list.add(new C3039t7(enumC1076Il, enumC1812d20, m7));
        list.add(new C1818d7(num.intValue(), enumC1812d20));
        list.add(new C3109u8(enumC1812d20));
        list.add(m7);
        list.add(c2284j7.clone());
    }

    public final C0753u b() {
        final C0722p1 c0722p1A = this.a.a();
        final ArrayList arrayList = new ArrayList();
        final C2284j7 c2284j7A = C2284j7.T().a((InterfaceC1348Si) InterfaceC1348Si.a(c0722p1A.X1)).a();
        M7 m7 = new M7();
        arrayList.add(new O7(EnumC1812d20.a(c0722p1A.X1), 0));
        arrayList.add(new C3039t7(EnumC1076Il.NE, EnumC1812d20.OBJECT, m7));
        arrayList.add(new V7(c0722p1A.s3));
        D8.a aVar = D8.a.Dup;
        arrayList.add(new D8(aVar));
        arrayList.add(new C1891e7(this.a.a().c("Name is null")));
        arrayList.add(new I7(183, c0722p1A.v4.b, false));
        arrayList.add(new V8());
        arrayList.add(m7);
        arrayList.add(c2284j7A);
        this.e.a(new BiConsumer() { // from class: com.android.tools.r8.internal.Ng$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(arrayList, c0722p1A, c2284j7A, (Integer) obj, (B1) obj2);
            }
        });
        arrayList.add(new V7(c0722p1A.o3));
        arrayList.add(new D8(aVar));
        arrayList.add(new C1891e7(this.a.a().c("No enum constant " + this.d.r0().replace('$', '.') + ".")));
        arrayList.add(new O7(EnumC1812d20.a(c0722p1A.X1), 0));
        arrayList.add(new I7(182, c0722p1A.k4.d, false));
        arrayList.add(new I7(183, c0722p1A.w4.a, false));
        arrayList.add(new V8());
        return a(arrayList);
    }
}
