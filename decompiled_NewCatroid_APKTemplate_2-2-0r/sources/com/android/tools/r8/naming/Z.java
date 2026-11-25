package com.android.tools.r8.naming;

import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.W0;
import com.android.tools.r8.internal.C2173hh;
import com.android.tools.r8.utils.C3894f2;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class Z {
    static final /* synthetic */ boolean c = true;
    private HashMap a = null;
    private HashSet b = null;

    Z() {
    }

    final void a(C0759u2 c0759u2, W0 w0) {
        if (this.b == null) {
            if (!c && this.a != null) {
                throw new AssertionError();
            }
            this.a = new HashMap();
            this.b = new HashSet();
        }
        ((Set) this.a.computeIfAbsent(C3894f2.a().c(w0.getReference()), new Function() { // from class: com.android.tools.r8.naming.Z$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Z.a((C2173hh) obj);
            }
        })).add(c0759u2);
        this.b.add(c0759u2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Set a(C2173hh c2173hh) {
        return new HashSet();
    }
}
