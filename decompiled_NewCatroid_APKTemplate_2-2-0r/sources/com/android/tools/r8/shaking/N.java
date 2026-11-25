package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.internal.UM$$ExternalSyntheticLambda6;
import com.android.tools.r8.utils.InterfaceC3880d;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class N {
    static final /* synthetic */ boolean c = true;
    private final Set a;
    private final IdentityHashMap b;

    private N() {
        this.a = OU.c();
        this.b = new IdentityHashMap();
    }

    final boolean a(Object obj, L0 l0) {
        if (!this.a.add(obj)) {
            return false;
        }
        ((List) this.b.getOrDefault(obj, Collections.emptyList())).forEach(new UM$$ExternalSyntheticLambda6());
        return true;
    }

    final boolean a(C0729q2 c0729q2) {
        return this.a.contains(c0729q2);
    }

    final boolean a(Object obj, InterfaceC3880d interfaceC3880d) {
        if (this.a.contains(obj)) {
            return false;
        }
        ((List) this.b.computeIfAbsent(obj, new Function() { // from class: com.android.tools.r8.shaking.N$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj2) {
                return N.a(obj2);
            }
        })).add(interfaceC3880d);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ List a(Object obj) {
        return new ArrayList();
    }
}
