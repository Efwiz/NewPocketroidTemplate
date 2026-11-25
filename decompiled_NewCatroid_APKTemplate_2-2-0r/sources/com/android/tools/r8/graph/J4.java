package com.android.tools.r8.graph;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class J4 extends I4.a {
    private final Collection c;

    private J4(List list) {
        super(com.android.tools.r8.utils.V1.a((Collection) list, new Function() { // from class: com.android.tools.r8.graph.J4$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((W0) obj).J0();
            }
        }));
        this.c = list;
    }

    @Override // com.android.tools.r8.graph.I4.a
    public final void a(Consumer consumer, Consumer consumer2) {
        super.a((Consumer<C0767v2>) consumer, (Consumer<? super W0>) consumer2);
        this.c.forEach(consumer2);
    }

    @Override // com.android.tools.r8.graph.I4.a
    public final boolean z() {
        return this.c.size() > 0;
    }
}
