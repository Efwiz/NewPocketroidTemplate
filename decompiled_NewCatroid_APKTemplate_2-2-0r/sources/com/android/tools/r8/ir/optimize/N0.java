package com.android.tools.r8.ir.optimize;

import java.util.List;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class N0 {
    static final /* synthetic */ boolean d = true;
    private final int a;
    private final List b;
    private final List c;

    private N0(int i, List list, List list2) {
        this.a = i;
        this.b = list;
        this.c = list2;
        if (d || !L0.b(i)) {
            return;
        }
        if (list.isEmpty() || list2.isEmpty()) {
            throw new AssertionError();
        }
    }

    public final void a(Consumer consumer) {
        this.b.forEach(consumer);
    }

    public final boolean b() {
        return this.a == 3;
    }

    public final boolean c() {
        return L0.b(this.a);
    }

    public final List a() {
        return this.c;
    }
}
