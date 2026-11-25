package com.android.tools.r8.graph;

import java.util.Iterator;
import java.util.TreeMap;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class z5 extends s5 {
    static final /* synthetic */ boolean b = true;

    private z5() {
        super(new TreeMap());
    }

    public static z5 a(C0702m c0702m) {
        if (!b && c0702m.f().g().d()) {
            throw new AssertionError();
        }
        z5 z5Var = new z5();
        Iterator<C0729q2> it = c0702m.f().d().iterator();
        while (it.hasNext()) {
            z5Var.a(it.next());
        }
        return z5Var;
    }

    public final void a(C0729q2 c0729q2) {
        ((r5) this.a.computeIfAbsent(c0729q2.getType().H0(), new Function() { // from class: com.android.tools.r8.graph.z5$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return new y5((String) obj);
            }
        })).a(c0729q2);
    }
}
