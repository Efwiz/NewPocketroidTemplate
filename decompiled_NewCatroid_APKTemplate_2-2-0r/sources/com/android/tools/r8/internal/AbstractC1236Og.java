package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Og, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1236Og extends QY {
    static final /* synthetic */ boolean c = true;

    AbstractC1236Og(C0702m c0702m, C0767v2 c0767v2) {
        super(c0702m, c0767v2);
    }

    final void a(List list, B1 b1, C0767v2 c0767v2) {
        if (b1.C()) {
            if (!c && c0767v2 != this.a.a().X1) {
                throw new AssertionError();
            }
            list.add(new C1891e7(b1.q().F()));
            return;
        }
        if (!(b1 instanceof C2853qV)) {
            throw new C3405y10("Only Number and String fields in enums are supported.");
        }
        if (!c0767v2.X0()) {
            long jH = b1.e().H();
            EnumC1812d20 enumC1812d20 = EnumC1812d20.OBJECT;
            list.add(new C1818d7(jH, EnumC1812d20.a((char) c0767v2.e.e[0])));
        } else {
            if (!c && !b1.x()) {
                throw new AssertionError();
            }
            list.add(new C1668b7());
        }
    }
}
