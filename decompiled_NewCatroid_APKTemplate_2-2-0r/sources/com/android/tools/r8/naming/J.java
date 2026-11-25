package com.android.tools.r8.naming;

import com.android.tools.r8.DataEntryResource;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.InterfaceC2743oz;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.utils.C3888e1;
import com.android.tools.r8.utils.C3979v2;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class J {
    static final /* synthetic */ boolean i = true;
    private final String a;
    private final AbstractC1476Wj b;
    private final AbstractC3649o0 c;
    private final C0722p1 d;
    private final HashMap e;
    private final LinkedHashMap f;
    private final HashMap g;
    private final com.android.tools.r8.utils.N h;

    /* JADX INFO: renamed from: $r8$lambda$wBZ5N9SAmxJLRX-vx8hIUeo_fgc, reason: not valid java name */
    public static /* synthetic */ ArrayList m1964$r8$lambda$wBZ5N9SAmxJLRXvx8hIUeo_fgc() {
        return new ArrayList();
    }

    private J(String str, C0702m c0702m) {
        this.e = new HashMap();
        this.f = new LinkedHashMap();
        this.g = new HashMap();
        this.h = new com.android.tools.r8.utils.N();
        this.a = str;
        this.b = c0702m.E();
        this.c = c0702m.z();
        this.d = c0702m.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0729q2 c0729q2) {
        C0767v2 type = c0729q2.getType();
        final InterfaceC2743oz interfaceC2743ozV1 = c0729q2.V1();
        C0767v2 c0767v2A = this.c.a(this.d, type);
        if (interfaceC2743ozV1.c()) {
            this.h.a(new Supplier() { // from class: com.android.tools.r8.naming.J$$ExternalSyntheticLambda2
                @Override // java.util.function.Supplier
                public final Object get() {
                    return interfaceC2743ozV1.t();
                }
            });
            ((List) this.e.computeIfAbsent(c0767v2A.I0(), C3888e1.a(new Supplier() { // from class: com.android.tools.r8.naming.J$$ExternalSyntheticLambda3
                @Override // java.util.function.Supplier
                public final Object get() {
                    return J.m1964$r8$lambda$wBZ5N9SAmxJLRXvx8hIUeo_fgc();
                }
            }))).add(c0767v2A.a1());
            return;
        }
        if (interfaceC2743ozV1.r()) {
            this.h.a(new Supplier() { // from class: com.android.tools.r8.naming.J$$ExternalSyntheticLambda2
                @Override // java.util.function.Supplier
                public final Object get() {
                    return interfaceC2743ozV1.t();
                }
            });
            ((List) this.f.computeIfAbsent(c0767v2A.I0(), C3888e1.a(new Supplier() { // from class: com.android.tools.r8.naming.J$$ExternalSyntheticLambda3
                @Override // java.util.function.Supplier
                public final Object get() {
                    return J.m1964$r8$lambda$wBZ5N9SAmxJLRXvx8hIUeo_fgc();
                }
            }))).add(C3979v2.a(this.b.a(type).a1(), c0767v2A.a1()));
        } else {
            if (!i && !interfaceC2743ozV1.s()) {
                throw new AssertionError();
            }
            this.h.a(new Supplier() { // from class: com.android.tools.r8.naming.J$$ExternalSyntheticLambda2
                @Override // java.util.function.Supplier
                public final Object get() {
                    return interfaceC2743ozV1.t();
                }
            });
            ((List) this.g.computeIfAbsent(interfaceC2743ozV1.q().u(), C3888e1.a(new Supplier() { // from class: com.android.tools.r8.naming.J$$ExternalSyntheticLambda3
                @Override // java.util.function.Supplier
                public final Object get() {
                    return J.m1964$r8$lambda$wBZ5N9SAmxJLRXvx8hIUeo_fgc();
                }
            }))).add(c0767v2A.a1());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Optional a() {
        if (this.e.isEmpty() && this.f.isEmpty()) {
            return Optional.empty();
        }
        if (!i && !this.h.c()) {
            throw new AssertionError();
        }
        ArrayList<String> arrayList = new ArrayList(this.e.keySet());
        for (String str : this.f.keySet()) {
            if (!this.e.containsKey(str)) {
                arrayList.add(str);
            }
        }
        Collections.sort(arrayList);
        com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.z zVar = new com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.z();
        for (String str2 : arrayList) {
            final LinkedHashMap linkedHashMap = new LinkedHashMap();
            ((List) this.f.getOrDefault(str2, Collections.emptyList())).forEach(new Consumer() { // from class: com.android.tools.r8.naming.J$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(linkedHashMap, (C3979v2) obj);
                }
            });
            zVar.a(str2, (List) this.e.getOrDefault(str2, Collections.emptyList()), linkedHashMap);
        }
        return Optional.of(DataEntryResource.fromBytes(zVar.a((int[]) this.h.a()).a(), "META-INF/" + this.a + ".kotlin_module", Origin.unknown()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(final Map map, C3979v2 c3979v2) {
        String str = (String) c3979v2.a();
        final String str2 = (String) c3979v2.b();
        ((List) this.g.getOrDefault(str, Collections.emptyList())).forEach(new Consumer() { // from class: com.android.tools.r8.naming.J$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                map.put((String) obj, str2);
            }
        });
    }
}
