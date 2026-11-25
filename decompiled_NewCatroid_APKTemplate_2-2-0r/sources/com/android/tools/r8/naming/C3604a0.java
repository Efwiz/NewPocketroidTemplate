package com.android.tools.r8.naming;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.utils.C3894f2;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.naming.a0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3604a0 extends Y {
    private final C3604a0 c;

    private C3604a0(C3604a0 c3604a0, Function function) {
        super(function);
        this.c = c3604a0;
    }

    public static C3604a0 a(Function function) {
        return new C3604a0(null, function);
    }

    final Set d(C0684j2 c0684j2) {
        C3604a0 c3604a0;
        HashMap map;
        Z z = (Z) b(c0684j2);
        Set set = null;
        if (z != null && (map = z.a) != null) {
            set = (Set) map.get(C3894f2.a().c(c0684j2));
        }
        return (set != null || (c3604a0 = this.c) == null) ? set : c3604a0.d(c0684j2);
    }

    final C3604a0 a() {
        return new C3604a0(this, this.b);
    }

    final boolean a(C0684j2 c0684j2, C0759u2 c0759u2) {
        HashSet hashSet;
        Z z = (Z) b(c0684j2);
        if (z != null && (hashSet = z.b) != null && hashSet.contains(c0759u2)) {
            return true;
        }
        C3604a0 c3604a0 = this.c;
        if (c3604a0 != null) {
            return c3604a0.a(c0684j2, c0759u2);
        }
        return false;
    }

    @Override // com.android.tools.r8.naming.Y
    final Object a(C0684j2 c0684j2) {
        return new Z();
    }
}
