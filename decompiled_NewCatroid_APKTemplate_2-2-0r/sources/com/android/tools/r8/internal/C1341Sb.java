package com.android.tools.r8.internal;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Sb, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1341Sb {
    static final /* synthetic */ boolean e = true;
    private final Consumer a;
    private final Thread b;
    private final ConcurrentHashMap c = new ConcurrentHashMap();
    private int d = 0;

    private C1341Sb(com.android.tools.r8.utils.A1 a1) {
        this.a = a1.n1.q;
        this.b = a1.N;
    }

    public static C1341Sb a(com.android.tools.r8.utils.A1 a1) {
        return new C1341Sb(a1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(AbstractC1176Mb abstractC1176Mb) {
        String string = abstractC1176Mb.a(new StringBuilder()).toString();
        String string2 = abstractC1176Mb.b(new StringBuilder()).toString();
        boolean z = e;
        if (!z && !string.endsWith(string2)) {
            throw new AssertionError();
        }
        Consumer consumer = this.a;
        if (consumer != null) {
            consumer.accept(string);
        }
        if (!z && this.c.put(string, string) != null) {
            throw new AssertionError("Duplicated use of context descriptor: " + string);
        }
    }

    public final C1257Pb a() {
        int i = this.d;
        this.d = i + 1;
        C1257Pb c1257Pb = new C1257Pb(this, i);
        boolean z = e;
        if (!z) {
            a(c1257Pb);
        }
        if (z || this.b == Thread.currentThread()) {
            return c1257Pb;
        }
        throw new AssertionError("Invoked on another thread than main");
    }
}
