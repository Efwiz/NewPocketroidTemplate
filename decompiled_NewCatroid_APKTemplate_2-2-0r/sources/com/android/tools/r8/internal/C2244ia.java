package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.BooleanSupplier;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ia, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2244ia {
    static final /* synthetic */ boolean d = true;
    private final Set a;
    private final Set b = OU.c();
    private final IdentityHashMap c = new IdentityHashMap();

    C2244ia(S10 s10) {
        this.a = com.android.tools.r8.utils.T2.a(s10);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ List e(S10 s10) {
        return new ArrayList();
    }

    final void a(S10 s10, BooleanSupplier booleanSupplier) {
        boolean z = d;
        if (!z && this.a.contains(s10)) {
            throw new AssertionError();
        }
        if (!this.b.contains(s10)) {
            ((List) this.c.computeIfAbsent(s10, new Function() { // from class: com.android.tools.r8.internal.ia$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return C2244ia.e((S10) obj);
                }
            })).add(booleanSupplier);
        } else if (!z && this.c.containsKey(s10)) {
            throw new AssertionError();
        }
    }

    final boolean b(S10 s10) {
        if (this.b.contains(s10)) {
            return false;
        }
        List list = (List) this.c.get(s10);
        if (list != null) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                if (!((BooleanSupplier) it.next()).getAsBoolean()) {
                    return false;
                }
            }
        }
        this.c.remove(s10);
        boolean zAdd = this.a.add(s10);
        if (d || zAdd) {
            return true;
        }
        throw new AssertionError(s10.toString() + " already added as an alias");
    }

    final boolean c(S10 s10) {
        return this.a.contains(s10);
    }

    final boolean d(S10 s10) {
        return this.a.contains(s10);
    }

    final boolean a(S10 s10) {
        if (this.a.contains(s10)) {
            return false;
        }
        this.b.add(s10);
        this.c.remove(s10);
        return true;
    }
}
