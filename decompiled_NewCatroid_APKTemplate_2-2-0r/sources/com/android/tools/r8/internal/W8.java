package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0767v2;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class W8 {
    static final /* synthetic */ boolean e = true;
    public static final /* synthetic */ int f = 0;
    public final M7 a;
    public final M7 b;
    public final List<C0767v2> c;
    public final List<M7> d;

    public W8(M7 m7, M7 m8, List list, List list2) {
        this.a = m7;
        this.b = m8;
        this.c = list;
        this.d = list2;
        if (e) {
            return;
        }
        a(list);
    }

    public final void a(BiConsumer biConsumer) {
        Iterator<C0767v2> it = this.c.iterator();
        Iterator<M7> it2 = this.d.iterator();
        while (it.hasNext()) {
            biConsumer.accept(it.next(), it2.next());
        }
        if (!e && it2.hasNext()) {
            throw new AssertionError();
        }
    }

    public final void a(Consumer consumer) {
        this.d.forEach(consumer);
    }

    public final List a() {
        return this.d;
    }

    private static void a(List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            C0767v2 c0767v2 = (C0767v2) it.next();
            if (!e && c0767v2 == null) {
                throw new AssertionError();
            }
        }
    }

    public final int a(W8 w8, com.android.tools.r8.utils.structural.a aVar, final com.android.tools.r8.graph.C c) {
        return aVar.a(this, w8, (com.android.tools.r8.utils.structural.y<W8>) new com.android.tools.r8.utils.structural.y() { // from class: com.android.tools.r8.internal.W8$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.utils.structural.y
            public final void a(com.android.tools.r8.utils.structural.z zVar) {
                W8.a(c, zVar);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.graph.C c, com.android.tools.r8.utils.structural.z zVar) {
        Function function = new Function() { // from class: com.android.tools.r8.internal.W8$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((W8) obj).a;
            }
        };
        com.android.tools.r8.utils.structural.u uVarA = c.a();
        com.android.tools.r8.utils.structural.z zVarA = zVar.a(function, uVarA, uVarA);
        Function function2 = new Function() { // from class: com.android.tools.r8.internal.W8$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((W8) obj).b;
            }
        };
        com.android.tools.r8.utils.structural.u uVarA2 = c.a();
        zVarA.a(function2, uVarA2, uVarA2).f(new Function() { // from class: com.android.tools.r8.internal.W8$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((W8) obj).c;
            }
        }).a(new Function() { // from class: com.android.tools.r8.internal.W8$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((W8) obj).d;
            }
        }, c.a());
    }

    public final void a(final com.android.tools.r8.graph.J5 j5) {
        List<C0767v2> list = this.c;
        Objects.requireNonNull(j5);
        list.forEach(new Consumer() { // from class: com.android.tools.r8.internal.W8$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                j5.a((C0767v2) obj);
            }
        });
    }
}
