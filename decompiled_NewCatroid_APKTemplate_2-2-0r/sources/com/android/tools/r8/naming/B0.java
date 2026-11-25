package com.android.tools.r8.naming;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.position.Position;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class B0 extends C3610b0 {
    private final Set h;
    private final HashMap i;

    B0(C0702m c0702m, C3614d c3614d, T t, C3666v c3666v, HashSet hashSet) {
        super(c0702m, c3614d, t, c3666v);
        this.h = hashSet;
        this.i = new HashMap();
        c3614d.b.forEach(new BiConsumer() { // from class: com.android.tools.r8.naming.B0$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a((C0767v2) obj, (C0759u2) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0767v2 c0767v2, C0759u2 c0759u2) {
        if (c0767v2.e != c0759u2) {
            this.i.put(c0759u2, c0767v2);
        }
    }

    @Override // com.android.tools.r8.naming.C3610b0, com.android.tools.r8.naming.AbstractC3649o0
    protected final C0759u2 a(C0767v2 c0767v2) {
        if (this.h.contains(c0767v2) && this.i.containsKey(c0767v2.e)) {
            this.d.Y().e.error(C3603a.a(((C0767v2) this.i.get(c0767v2.e)).toString(), c0767v2.r0(), Position.UNKNOWN));
            this.h.remove(c0767v2);
        }
        return super.a(c0767v2);
    }
}
