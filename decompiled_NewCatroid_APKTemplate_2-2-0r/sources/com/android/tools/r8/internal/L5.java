package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0746s4;
import com.android.tools.r8.graph.C0762u4;
import com.android.tools.r8.shaking.C3733i;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class L5 {
    static final /* synthetic */ boolean c = true;
    private final C0702m a;
    private final M4 b = M4.d();

    L5(C0702m c0702m) {
        this.a = c0702m;
    }

    public final void a(final BiConsumer biConsumer) {
        this.b.a(new BiConsumer() { // from class: com.android.tools.r8.internal.L5$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(biConsumer, (Set) obj, (C0684j2) obj2);
            }
        });
    }

    public final boolean b() {
        return this.b.isEmpty();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(BiConsumer biConsumer, Set set, C0684j2 c0684j2) {
        com.android.tools.r8.graph.o5 o5VarA = c0684j2.a(this.a.b(c0684j2.B0()));
        if (o5VarA != null) {
            biConsumer.accept(o5VarA, o5VarA.e().i1().j());
        }
    }

    public final void a(Iterable iterable, C0684j2 c0684j2, C0684j2 c0684j3) {
        Object objC = this.b.c(c0684j3);
        Object obj = c0684j3;
        if (objC != null) {
            obj = objC;
        }
        C0684j2 c0684j4 = (C0684j2) obj;
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            C0684j2 c0684j5 = (C0684j2) it.next();
            Set setA = this.b.a(c0684j5);
            if (setA.isEmpty()) {
                linkedHashSet.add(c0684j5);
            } else {
                linkedHashSet.addAll(setA);
            }
        }
        if (!c && !linkedHashSet.contains(c0684j4)) {
            throw new AssertionError();
        }
        this.b.a((Iterable) linkedHashSet, (Object) c0684j2);
        this.b.b(c0684j2, c0684j4);
    }

    public final void a(final C0746s4 c0746s4) {
        final C0762u4 c0762u4X = ((C3733i) this.a.f()).x();
        this.b.keySet().forEach(new Consumer() { // from class: com.android.tools.r8.internal.L5$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                L5.a(c0762u4X, c0746s4, (C0684j2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0762u4 c0762u4, final C0746s4 c0746s4, final C0684j2 c0684j2) {
        c0762u4.a(c0684j2, new Consumer() { // from class: com.android.tools.r8.internal.L5$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                c0746s4.i(c0684j2, (com.android.tools.r8.graph.o5) obj);
            }
        });
        c0762u4.b(c0684j2, new Consumer() { // from class: com.android.tools.r8.internal.L5$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                c0746s4.j(c0684j2, (com.android.tools.r8.graph.o5) obj);
            }
        });
    }

    public final K5 a() {
        if (!c && this.b.isEmpty()) {
            throw new AssertionError();
        }
        return new K5(this.a, this.b);
    }
}
