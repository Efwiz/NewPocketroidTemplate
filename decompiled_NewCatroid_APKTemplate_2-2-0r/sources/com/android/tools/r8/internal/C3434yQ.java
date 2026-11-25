package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.yQ, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3434yQ extends QY {
    private final com.android.tools.r8.graph.Y0[] c;

    public C3434yQ(C0702m c0702m, C0767v2 c0767v2, com.android.tools.r8.graph.Y0[] y0Arr) {
        super(c0702m, c0767v2);
        this.c = y0Arr;
    }

    public static void a(final C0722p1 c0722p1) {
        c0722p1.d("[Ljava/lang/Object;");
        c0722p1.G5.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.yQ$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C3434yQ.a(c0722p1, (C0767v2) obj, (C0767v2) obj2);
            }
        });
    }

    public final C0753u b() {
        int i = 0;
        if (this.a.q() && this.a.Y().n1.u0) {
            ArrayList arrayList = new ArrayList();
            com.android.tools.r8.graph.Y0[] y0Arr = this.c;
            int length = y0Arr.length;
            while (i < length) {
                a(arrayList, y0Arr[i]);
                i++;
            }
            arrayList.add(new C2830q8(this.c));
            arrayList.add(new C3109u8(EnumC1812d20.OBJECT));
            return a(arrayList);
        }
        C0722p1 c0722p1A = this.a.a();
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(new C1818d7(this.c.length, EnumC1812d20.INT));
        arrayList2.add(new W7(c0722p1A.c2));
        arrayList2.add(new R8(EnumC1812d20.OBJECT, 1));
        while (true) {
            com.android.tools.r8.graph.Y0[] y0Arr2 = this.c;
            if (i >= y0Arr2.length) {
                EnumC1812d20 enumC1812d20 = EnumC1812d20.OBJECT;
                arrayList2.add(new O7(enumC1812d20, 1));
                arrayList2.add(new C3109u8(enumC1812d20));
                return a(arrayList2);
            }
            com.android.tools.r8.graph.Y0 y0 = y0Arr2[i];
            arrayList2.add(new O7(EnumC1812d20.OBJECT, 1));
            arrayList2.add(new C1818d7(i, EnumC1812d20.INT));
            a(arrayList2, y0);
            arrayList2.add(new D6(EnumC2523mE.OBJECT));
            i++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0722p1 c0722p1, C0767v2 c0767v2, C0767v2 c0767v3) {
        c0722p1.d(c0767v2.b1());
        c0722p1.d(c0767v3.b1());
    }

    private void a(final ArrayList arrayList, final com.android.tools.r8.graph.Y0 y0) {
        final C0722p1 c0722p1A = this.a.a();
        arrayList.add(new O7(EnumC1812d20.OBJECT, 0));
        arrayList.add(new C3413y7(y0, y0));
        if (y0.h.W0()) {
            c0722p1A.G5.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.yQ$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    C3434yQ.a(y0, arrayList, c0722p1A, (C0767v2) obj, (C0767v2) obj2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.graph.Y0 y0, List list, C0722p1 c0722p1, C0767v2 c0767v2, C0767v2 c0767v3) {
        if (c0767v2 == y0.h) {
            list.add(new I7(184, c0722p1.a(c0767v3, c0722p1.b(c0767v3, c0767v2), c0722p1.g0), false));
        }
    }
}
