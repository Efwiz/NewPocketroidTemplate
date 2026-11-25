package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.internal.InterfaceC2125h1;
import com.android.tools.r8.internal.InterfaceC2197i1;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.eN, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1917eN<MethodRule extends InterfaceC2197i1, MethodRuleBuilder extends InterfaceC2125h1> {
    static final /* synthetic */ boolean c = true;
    private final ConcurrentHashMap a = new ConcurrentHashMap();
    private final ConcurrentHashMap b = new ConcurrentHashMap();

    public void a(C0684j2 c0684j2, C0684j2 c0684j3) {
        ((Set) this.b.computeIfAbsent(c0684j2, com.android.tools.r8.utils.Z1.a(new IP$$ExternalSyntheticLambda3()))).add(c0684j3);
        ((Set) this.a.computeIfAbsent(c0684j3, com.android.tools.r8.utils.Z1.a(new IP$$ExternalSyntheticLambda3()))).add(c0684j2);
    }

    public void a(final Map<C0684j2, MethodRuleBuilder> map) {
        com.android.tools.r8.utils.O3.d((Iterable) this.a.keySet()).a(new BiConsumer() { // from class: com.android.tools.r8.internal.eN$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(map, (C0684j2) obj, (com.android.tools.r8.utils.O3) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Map map, C0684j2 c0684j2, final com.android.tools.r8.utils.O3 o3) {
        InterfaceC2125h1 interfaceC2125h1 = (InterfaceC2125h1) map.get(c0684j2);
        for (final C0684j2 c0684j3 : (Set) this.a.getOrDefault(c0684j2, Collections.emptySet())) {
            ((InterfaceC2125h1) map.get(c0684j3)).a(interfaceC2125h1, new Runnable() { // from class: com.android.tools.r8.internal.eN$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    o3.d(c0684j3);
                }
            });
        }
    }

    public boolean a() {
        Set setC = OU.c();
        for (C0684j2 c0684j2 : this.a.keySet()) {
            if (setC.add(c0684j2)) {
                setC.addAll(a(c0684j2));
            }
        }
        return true;
    }

    public Set<C0684j2> a(C0684j2 c0684j2) {
        final Set setC = OU.c();
        final Set setC2 = OU.c();
        final com.android.tools.r8.utils.O3 o3H = com.android.tools.r8.utils.O3.h(c0684j2);
        o3H.a(new Consumer() { // from class: com.android.tools.r8.internal.eN$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(setC, setC2, o3H, (C0684j2) obj);
            }
        });
        if (c || setC2.isEmpty()) {
            return o3H.b();
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Set set, Set set2, com.android.tools.r8.utils.O3 o3, C0684j2 c0684j2) {
        if (set.add(c0684j2)) {
            set2.add(c0684j2);
            o3.b(c0684j2);
            for (C0684j2 c0684j3 : (Set) this.a.getOrDefault(c0684j2, Collections.emptySet())) {
                if (!c && set2.contains(c0684j3)) {
                    throw new AssertionError("Found a cycle");
                }
                o3.a(c0684j3);
            }
            return;
        }
        boolean zRemove = set2.remove(c0684j2);
        if (!c && !zRemove) {
            throw new AssertionError();
        }
    }
}
