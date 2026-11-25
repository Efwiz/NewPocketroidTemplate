package com.android.tools.r8.naming;

import com.android.tools.r8.DataEntryResource;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.C2053gA;
import com.android.tools.r8.internal.InterfaceC2743oz;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class K {
    static final /* synthetic */ boolean b = true;
    private final C0702m a;

    public K(C0702m c0702m) {
        this.a = c0702m;
    }

    public static boolean a(DataEntryResource dataEntryResource) {
        return dataEntryResource.getName().endsWith(".kotlin_module");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ J b(String str) {
        return new J(str, this.a);
    }

    public final List a() {
        C0767v2 c0767v2A;
        String str;
        HashMap map = new HashMap();
        HashMap map2 = new HashMap();
        for (C0729q2 c0729q2 : this.a.e().e()) {
            InterfaceC2743oz interfaceC2743ozV1 = c0729q2.V1();
            if (interfaceC2743ozV1.c()) {
                ((J) map.computeIfAbsent(interfaceC2743ozV1.n().u(), new Function() { // from class: com.android.tools.r8.naming.K$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return this.f$0.a((String) obj);
                    }
                })).a(c0729q2);
            } else if (interfaceC2743ozV1.s()) {
                final C2053gA c2053gAQ = interfaceC2743ozV1.q();
                map2.computeIfAbsent(c2053gAQ.u(), new Function() { // from class: com.android.tools.r8.naming.K$$ExternalSyntheticLambda1
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return K.a(c2053gAQ, (String) obj);
                    }
                });
                ((J) map.computeIfAbsent(c2053gAQ.v(), new Function() { // from class: com.android.tools.r8.naming.K$$ExternalSyntheticLambda2
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return this.f$0.b((String) obj);
                    }
                })).a(c0729q2);
            }
        }
        for (C0729q2 c0729q3 : this.a.e().e()) {
            if (c0729q3.V1().r() && (c0767v2A = this.a.E().a(c0729q3.getType())) != null && (str = (String) map2.get(c0767v2A.a1())) != null) {
                J j = (J) map.get(str);
                if (!b && j == null) {
                    throw new AssertionError();
                }
                j.a(c0729q3);
            }
        }
        if (map.isEmpty()) {
            return Collections.emptyList();
        }
        final ArrayList arrayList = new ArrayList();
        map.values().forEach(new Consumer() { // from class: com.android.tools.r8.naming.K$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                K.a(arrayList, (J) obj);
            }
        });
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ J a(String str) {
        return new J(str, this.a);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ String a(C2053gA c2053gA, String str) {
        return c2053gA.v();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(final List list, J j) {
        Optional optionalA = j.a();
        Objects.requireNonNull(list);
        optionalA.ifPresent(new Consumer() { // from class: com.android.tools.r8.naming.K$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                list.add((DataEntryResource) obj);
            }
        });
    }
}
