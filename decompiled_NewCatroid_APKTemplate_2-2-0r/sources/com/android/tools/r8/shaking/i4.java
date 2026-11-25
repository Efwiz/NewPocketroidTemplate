package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.JA;
import com.android.tools.r8.internal.OU;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class i4 {
    static final /* synthetic */ boolean b = true;
    private ConcurrentHashMap a = new ConcurrentHashMap();

    public final void a(C0767v2 c0767v2, C0684j2 c0684j2, com.android.tools.r8.graph.W0 w0) {
        boolean z = b;
        if (!z && w0 == com.android.tools.r8.graph.W0.t) {
            throw new AssertionError();
        }
        Map map = (Map) this.a.computeIfAbsent(c0767v2, new Function() { // from class: com.android.tools.r8.shaking.i4$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return i4.a((C0767v2) obj);
            }
        });
        if (w0 == null) {
            w0 = com.android.tools.r8.graph.W0.t;
        }
        if (!z && map.getOrDefault(c0684j2, w0) != w0) {
            throw new AssertionError();
        }
        map.putIfAbsent(c0684j2, w0);
    }

    public final boolean b(C0684j2 c0684j2, C0767v2 c0767v2) {
        Map map = (Map) this.a.get(c0767v2);
        if (map == null) {
            return false;
        }
        return map.containsKey(c0684j2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Map a(C0767v2 c0767v2) {
        return new ConcurrentHashMap();
    }

    public final void a(C0767v2 c0767v2, final C3733i c3733i) {
        this.a.remove(c0767v2);
        final Set setC = OU.c();
        c3733i.a(c0767v2, new Consumer() { // from class: com.android.tools.r8.shaking.i4$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c3733i, setC, (C0729q2) obj);
            }
        }, new Consumer() { // from class: com.android.tools.r8.shaking.i4$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                i4.a((JA) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C3733i c3733i, final Set set, C0729q2 c0729q2) {
        c3733i.a(c0729q2, new com.android.tools.r8.utils.G3() { // from class: com.android.tools.r8.shaking.i4$$ExternalSyntheticLambda3
            @Override // com.android.tools.r8.utils.G3
            public final Object a(Object obj, Object obj2, Object obj3) {
                return this.f$0.a(set, (C0767v2) obj, (AbstractC0727q0) obj2, (Boolean) obj3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.tools.r8.utils.D3 a(Set set, C0767v2 c0767v2, AbstractC0727q0 abstractC0727q0, Boolean bool) {
        if (set.add(c0767v2)) {
            this.a.remove(c0767v2);
            return com.android.tools.r8.utils.D3.e();
        }
        return com.android.tools.r8.utils.D3.d();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(JA ja) {
        if (!b) {
            throw new AssertionError();
        }
    }

    public final com.android.tools.r8.graph.W0 a(C0684j2 c0684j2, C0767v2 c0767v2) {
        com.android.tools.r8.graph.W0 w0;
        Map map = (Map) this.a.get(c0767v2);
        if (map == null || (w0 = (com.android.tools.r8.graph.W0) map.get(c0684j2)) == com.android.tools.r8.graph.W0.t) {
            return null;
        }
        return w0;
    }
}
