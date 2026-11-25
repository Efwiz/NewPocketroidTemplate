package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Mg, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1181Mg extends AbstractC1236Og {
    private final C0767v2 d;
    private final C3380xg e;
    private final B1 f;

    public C1181Mg(C0702m c0702m, C0767v2 c0767v2, C3150ug c3150ug, com.android.tools.r8.graph.Y0 y0, B1 b1) {
        super(c0702m, c0767v2);
        this.d = y0.getType();
        this.e = c3150ug.a(y0).c();
        this.f = b1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(List list, C0722p1 c0722p1, C2284j7.a aVar, Integer num, B1 b1) {
        M7 m7 = new M7();
        list.add(new O7(EnumC1812d20.a(c0722p1.A1), 0));
        long jIntValue = num.intValue();
        EnumC1812d20 enumC1812d20 = EnumC1812d20.INT;
        list.add(new C1818d7(jIntValue, enumC1812d20));
        list.add(new C3185v7(EnumC1076Il.NE, enumC1812d20, m7));
        a(list, b1, this.d);
        list.add(new C3109u8(EnumC1812d20.a(this.d)));
        list.add(m7);
        list.add(aVar.a());
    }

    public final C0753u b() {
        final C0722p1 c0722p1A = this.a.a();
        final ArrayList arrayList = new ArrayList();
        final C2284j7.a aVarA = C2284j7.T().a((InterfaceC1348Si) InterfaceC1348Si.a(c0722p1A.A1));
        this.e.a(new BiConsumer() { // from class: com.android.tools.r8.internal.Mg$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(arrayList, c0722p1A, aVarA, (Integer) obj, (B1) obj2);
            }
        });
        B1 b1 = this.f;
        if (b1 != null) {
            a(arrayList, b1, this.d);
            arrayList.add(new C3109u8(EnumC1812d20.a(this.d)));
        } else {
            arrayList.add(new C1668b7());
            arrayList.add(new V8());
        }
        return a(arrayList);
    }
}
