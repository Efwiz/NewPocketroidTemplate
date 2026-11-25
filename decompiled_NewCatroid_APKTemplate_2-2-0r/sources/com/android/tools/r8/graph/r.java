package com.android.tools.r8.graph;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class r extends G {
    private final Function g;

    private r(C0702m c0702m, Function function, int i) {
        super(c0702m, i);
        this.g = function;
    }

    public static r a(C0702m c0702m, B5 b5) {
        Objects.requireNonNull(b5);
        return new r(c0702m, new r$$ExternalSyntheticLambda0(b5), 1);
    }

    final G b() {
        return this;
    }

    public static r b(C0702m c0702m, B5 b5) {
        return a(c0702m, new r$$ExternalSyntheticLambda0(b5));
    }

    public static r a(C0702m c0702m, Function function) {
        return new r(c0702m, function, 4);
    }

    public final G a() {
        this.e = true;
        return b();
    }

    @Override // com.android.tools.r8.graph.G
    final void a(AbstractC0727q0 abstractC0727q0) {
        if ((this.e && abstractC0727q0.isInterface()) || this.c.contains(abstractC0727q0)) {
            return;
        }
        this.d.addFirst(abstractC0727q0);
        Iterator it = ((Iterable) this.g.apply(abstractC0727q0.getType())).iterator();
        while (it.hasNext()) {
            AbstractC0727q0 abstractC0727q0A = this.a.a((C0767v2) it.next());
            if (abstractC0727q0A != null && (this.b != 4 || abstractC0727q0A.e0())) {
                a(abstractC0727q0A);
            }
        }
    }
}
