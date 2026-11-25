package com.android.tools.r8.internal;

import com.android.tools.r8.naming.C3634k;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.bE, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1676bE {
    private final C3634k.c a;
    private final com.android.tools.r8.naming.Q b;

    C1676bE(com.android.tools.r8.naming.Q q, C3634k.c cVar) {
        this.b = q;
        this.a = cVar;
    }

    final List a(int i) {
        return this.a.a(i, false);
    }

    final List b() {
        return this.a.a(0, true);
    }

    public final com.android.tools.r8.naming.Q c() {
        return this.b;
    }

    public final boolean d() {
        com.android.tools.r8.naming.N0 n0;
        return this.a.b().size() == 1 && (n0 = ((C3634k.b) com.android.tools.r8.utils.V1.a(this.a.b())).a) != null && n0.a();
    }

    final List e() {
        return com.android.tools.r8.utils.V1.a((Collection) this.a.b(), new Predicate() { // from class: com.android.tools.r8.internal.bE$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C1676bE.a((C3634k.b) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C3634k.b bVar) {
        return bVar.a == null;
    }

    final List a() {
        return this.a.b();
    }
}
