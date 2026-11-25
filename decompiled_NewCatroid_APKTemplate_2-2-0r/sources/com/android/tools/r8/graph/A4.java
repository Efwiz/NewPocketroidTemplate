package com.android.tools.r8.graph;

import com.android.tools.r8.internal.OU;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class A4 {
    static final /* synthetic */ boolean f = true;
    private final LinkedHashMap a;
    private final LinkedHashMap b;
    private final Function c;
    private final Set d;
    private final C0722p1 e;

    private A4(C0722p1 c0722p1, Function function) {
        this.a = new LinkedHashMap();
        this.b = new LinkedHashMap();
        this.d = OU.c();
        this.c = function;
        this.e = c0722p1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0767v2 c0767v2) {
        if (c0767v2 == null) {
            return;
        }
        ((O) this.c.apply(c0767v2)).b(new Consumer() { // from class: com.android.tools.r8.graph.A4$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b((AbstractC0727q0) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(AbstractC0727q0 abstractC0727q0) {
        boolean z = f;
        if (!z && !abstractC0727q0.isInterface()) {
            throw new AssertionError();
        }
        if (!z && abstractC0727q0.f != this.e.Z1) {
            throw new AssertionError();
        }
        if (this.a.getOrDefault(abstractC0727q0, W0.t) == null) {
            return;
        }
        this.a.put(abstractC0727q0, null);
        this.b.put(abstractC0727q0, null);
        for (C0767v2 c0767v2 : abstractC0727q0.g.a) {
            a(c0767v2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public I4 a(final AbstractC0727q0 abstractC0727q0) {
        Map.Entry entry;
        if (this.a.isEmpty() && this.b.isEmpty()) {
            return I4.b.c;
        }
        LinkedHashMap linkedHashMap = this.a;
        ArrayList arrayList = new ArrayList();
        for (Map.Entry entry2 : linkedHashMap.entrySet()) {
            W0 w0 = (W0) entry2.getValue();
            if (w0 != null && w0.G1()) {
                arrayList.add(entry2);
            }
        }
        if (arrayList.size() > 1) {
            return L4.a(com.android.tools.r8.utils.V1.a((Collection) arrayList, new Function() { // from class: com.android.tools.r8.graph.A4$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return (W0) ((Map.Entry) obj).getValue();
                }
            }));
        }
        LinkedHashMap linkedHashMap2 = this.b;
        ArrayList arrayList2 = new ArrayList();
        for (Map.Entry entry3 : linkedHashMap2.entrySet()) {
            W0 w1 = (W0) entry3.getValue();
            if (w1 != null && w1.G1()) {
                arrayList2.add(entry3);
            }
        }
        if (arrayList2.isEmpty()) {
            if (arrayList.isEmpty()) {
                Iterator it = this.a.entrySet().iterator();
                do {
                    entry = null;
                    if (!it.hasNext()) {
                        entry = null;
                        break;
                    }
                    entry = (Map.Entry) it.next();
                } while (((W0) entry.getValue()) == null);
                if (entry != null) {
                    entry = entry;
                    break;
                }
                for (Map.Entry entry4 : this.b.entrySet()) {
                    if (((W0) entry4.getValue()) != null) {
                        entry = entry4;
                        break;
                    }
                }
                if (!f && (entry == null || !((W0) entry.getValue()).s1())) {
                    throw new AssertionError();
                }
                if (abstractC0727q0 == null) {
                    abstractC0727q0 = (AbstractC0727q0) entry.getKey();
                }
                return I4.a(abstractC0727q0, (AbstractC0727q0) entry.getKey(), (W0) entry.getValue());
            }
            Map.Entry entry5 = (Map.Entry) arrayList.get(0);
            if (abstractC0727q0 == null) {
                abstractC0727q0 = (AbstractC0727q0) entry5.getKey();
            }
            return I4.a(abstractC0727q0, (AbstractC0727q0) entry5.getKey(), (W0) entry5.getValue());
        }
        final F4 f4A = I4.p().a();
        if (arrayList.isEmpty()) {
            if (!f && this.d.isEmpty()) {
                throw new AssertionError();
            }
            f4A.a(I4.b.c);
        } else {
            arrayList.forEach(new Consumer() { // from class: com.android.tools.r8.graph.A4$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    A4.a(f4A, abstractC0727q0, (Map.Entry) obj);
                }
            });
        }
        arrayList2.forEach(new Consumer() { // from class: com.android.tools.r8.graph.A4$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                A4.b(f4A, abstractC0727q0, (Map.Entry) obj);
            }
        });
        return f4A.a(I4.b.c, this.d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(F4 f4, AbstractC0727q0 abstractC0727q0, Map.Entry entry) {
        if (abstractC0727q0 == null) {
            abstractC0727q0 = (AbstractC0727q0) entry.getKey();
        }
        f4.a(I4.a(abstractC0727q0, (AbstractC0727q0) entry.getKey(), (W0) entry.getValue()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(F4 f4, AbstractC0727q0 abstractC0727q0, Map.Entry entry) {
        if (abstractC0727q0 == null) {
            abstractC0727q0 = (AbstractC0727q0) entry.getKey();
        }
        f4.a(I4.a(abstractC0727q0, (AbstractC0727q0) entry.getKey(), (W0) entry.getValue()));
    }
}
