package com.android.tools.r8.internal;

import com.android.tools.r8.naming.C3634k;
import java.util.List;
import java.util.OptionalInt;

/* JADX INFO: renamed from: com.android.tools.r8.internal.vT, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3212vT {
    static final /* synthetic */ boolean d = true;
    private final C2923rT a;
    private final List b;
    private final OptionalInt c;

    C3212vT(C2923rT c2923rT, List list, OptionalInt optionalInt) {
        this.a = c2923rT;
        this.b = list;
        this.c = optionalInt;
    }

    public final List a() {
        return this.b;
    }

    public final OptionalInt b() {
        return this.c;
    }

    public final C2923rT c() {
        return this.a;
    }

    final boolean d() {
        com.android.tools.r8.naming.N0 n0;
        com.android.tools.r8.naming.N0 n1;
        List list = this.b;
        if (list == null) {
            return false;
        }
        if (list.size() > 1) {
            return true;
        }
        if (!d && this.b.isEmpty()) {
            throw new AssertionError();
        }
        List<C3634k.b> listA = ((C1676bE) this.b.get(0)).a();
        if (listA != null && !listA.isEmpty()) {
            C3634k.b bVar = (C3634k.b) listA.get(0);
            for (C3634k.b bVar2 : listA) {
                com.android.tools.r8.naming.N0 n2 = bVar2.c;
                if (n2 != null && n2.c() != 1 && ((n1 = bVar2.a) == null || n1.c() != bVar2.c.c())) {
                    return true;
                }
                if (bVar2 != bVar && ((n0 = bVar2.a) == null || !n0.equals(bVar.a))) {
                    return true;
                }
            }
        }
        return false;
    }
}
