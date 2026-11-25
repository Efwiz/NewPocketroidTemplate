package com.android.tools.r8.naming;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import java.util.Map;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class A {
    final C0702m a;
    final Map b;

    A(C0702m c0702m, Map map) {
        this.a = c0702m;
        this.b = map;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Object a(C0767v2 c0767v2) {
        return a();
    }

    abstract Object a();

    final Object b() {
        this.a.Y().D0().getClass();
        return this.b.get(this.a.a().D1);
    }

    final Object c() {
        this.a.Y().D0().getClass();
        return this.b.computeIfAbsent(this.a.a().D1, new Function() { // from class: com.android.tools.r8.naming.A$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a((C0767v2) obj);
            }
        });
    }
}
