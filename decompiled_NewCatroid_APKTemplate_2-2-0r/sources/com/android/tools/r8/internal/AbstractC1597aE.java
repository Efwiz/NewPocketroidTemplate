package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0642e2;
import java.util.function.Function;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: renamed from: com.android.tools.r8.internal.aE, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1597aE {
    private final AbstractC0642e2 a;
    private final AbstractC0642e2 b;

    AbstractC1597aE(AbstractC0642e2 abstractC0642e2, AbstractC0642e2 abstractC0642e3) {
        this.a = abstractC0642e2;
        this.b = abstractC0642e3;
    }

    public AbstractC0642e2 a() {
        return this.b;
    }

    public AbstractC0642e2 b() {
        return this.a;
    }

    public boolean c() {
        return this.b != null;
    }

    public AbstractC0642e2 a(N4 n4) {
        AbstractC0642e2 abstractC0642e2 = this.b;
        return (AbstractC0642e2) n4.getOrDefault(abstractC0642e2, abstractC0642e2);
    }

    public AbstractC0642e2 b(N4 n4) {
        AbstractC0642e2 abstractC0642e2 = this.a;
        return (AbstractC0642e2) n4.getOrDefault(abstractC0642e2, abstractC0642e2);
    }

    public AbstractC0642e2 a(Function function) {
        AbstractC0642e2 abstractC0642e2 = (AbstractC0642e2) function.apply(this.b);
        return abstractC0642e2 != null ? abstractC0642e2 : this.b;
    }
}
