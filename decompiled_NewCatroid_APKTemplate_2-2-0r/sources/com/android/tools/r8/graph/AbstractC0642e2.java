package com.android.tools.r8.graph;

import com.android.tools.r8.graph.AbstractC0642e2;
import com.android.tools.r8.graph.U0;
import com.android.tools.r8.internal.AbstractC2734ot;
import com.android.tools.r8.internal.InterfaceC3010sj;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.graph.e2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC0642e2<D extends U0<D, R>, R extends AbstractC0642e2<D, R>> extends AbstractC0744s2 implements V4 {
    static final /* synthetic */ boolean g = true;
    public final C0767v2 e;
    public final C0759u2 f;

    public AbstractC0642e2(C0759u2 c0759u2, C0767v2 c0767v2) {
        boolean z = g;
        if (!z && c0767v2 == null) {
            throw new AssertionError();
        }
        this.e = c0767v2;
        if (!z && c0759u2 == null) {
            throw new AssertionError();
        }
        this.f = c0759u2;
    }

    public C0767v2 B0() {
        return this.e;
    }

    @Override // com.android.tools.r8.graph.AbstractC0744s2
    public final C0767v2 C() {
        return this.e;
    }

    public C0759u2 C0() {
        return this.f;
    }

    public abstract Iterable D0();

    public abstract AbstractC0741s0 a(AbstractC0727q0 abstractC0727q0);

    public final Iterable a(final C0722p1 c0722p1) {
        return AbstractC2734ot.a(D0(), new InterfaceC3010sj() { // from class: com.android.tools.r8.graph.e2$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return AbstractC0642e2.a(c0722p1, (C0767v2) obj);
            }
        });
    }

    public abstract Object a(Function function, Function function2);

    public abstract AbstractC0642e2 b(C0722p1 c0722p1, C0767v2 c0767v2);

    @Override // com.android.tools.r8.graph.AbstractC0744s2
    public final AbstractC0642e2 u0() {
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C0767v2 a(C0722p1 c0722p1, C0767v2 c0767v2) {
        return c0767v2.b(c0722p1);
    }
}
