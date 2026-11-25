package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.CN;
import com.android.tools.r8.internal.EnumC1953et;
import com.android.tools.r8.internal.KE;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.utils.C3912j0;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.graph.u4, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0762u4 {
    private final Map a;
    private final Map b;
    private final Map c;
    private final Map d;
    private final Map e;

    private C0762u4(Map map, Map map2, Map map3, Map map4, Map map5) {
        this.a = map;
        this.b = map2;
        this.c = map3;
        this.d = map4;
        this.e = map5;
    }

    public static C0739r4 a() {
        return new C0739r4();
    }

    public static C0746s4 b() {
        return new C0746s4();
    }

    public final C0752t4 c() {
        return new C0752t4(this.a, this.b, this.c, this.d, this.e);
    }

    public final void d(BiConsumer biConsumer) {
        this.d.forEach(biConsumer);
    }

    public final void e(BiConsumer biConsumer) {
        this.e.forEach(biConsumer);
    }

    public final C0762u4 a(Q0 q0, AbstractC1476Wj abstractC1476Wj) {
        C0746s4 c0746s4 = new C0746s4();
        a(c0746s4, this.a, q0, abstractC1476Wj, EnumC1953et.c);
        a(c0746s4, this.b, q0, abstractC1476Wj, EnumC1953et.d);
        a(c0746s4, this.c, q0, abstractC1476Wj, EnumC1953et.e);
        a(c0746s4, this.d, q0, abstractC1476Wj, EnumC1953et.f);
        a(c0746s4, this.e, q0, abstractC1476Wj, EnumC1953et.g);
        return c0746s4.a();
    }

    public final void b(BiConsumer biConsumer) {
        this.b.forEach(biConsumer);
    }

    public final void c(BiConsumer biConsumer) {
        this.c.forEach(biConsumer);
    }

    public final void b(C0684j2 c0684j2, Consumer consumer) {
        ((CN) this.e.getOrDefault(c0684j2, CN.e())).forEach(consumer);
    }

    public final void a(Consumer consumer) {
        Set setC = OU.c();
        this.a.keySet().forEach(C3912j0.a(consumer, setC));
        this.b.keySet().forEach(C3912j0.a(consumer, setC));
        this.c.keySet().forEach(C3912j0.a(consumer, setC));
        this.d.keySet().forEach(C3912j0.a(consumer, setC));
        this.e.keySet().forEach(C3912j0.a(consumer, setC));
    }

    public final void a(BiConsumer biConsumer) {
        this.a.forEach(biConsumer);
    }

    public final void a(C0684j2 c0684j2, Consumer consumer) {
        ((CN) this.d.getOrDefault(c0684j2, CN.e())).forEach(consumer);
    }

    private static void a(final C0746s4 c0746s4, Map map, final Q0 q0, final AbstractC1476Wj abstractC1476Wj, final EnumC1953et enumC1953et) {
        map.forEach(new BiConsumer() { // from class: com.android.tools.r8.graph.u4$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C0762u4.a(q0, abstractC1476Wj, enumC1953et, c0746s4, (C0684j2) obj, (CN) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(Q0 q0, AbstractC1476Wj abstractC1476Wj, EnumC1953et enumC1953et, AbstractC0734q4 abstractC0734q4, C0684j2 c0684j2, CN cn) {
        for (o5 o5Var : cn.a(q0, abstractC1476Wj)) {
            KE keA = abstractC1476Wj.a(c0684j2, o5Var.getReference(), enumC1953et, (AbstractC1476Wj) null);
            C0684j2 c0684j3 = (C0684j2) keA.b();
            EnumC1953et enumC1953etF = keA.f();
            abstractC0734q4.getClass();
            int iOrdinal = enumC1953etF.ordinal();
            if (iOrdinal == 0) {
                AbstractC0734q4.a(c0684j3, o5Var, abstractC0734q4.a);
            } else if (iOrdinal == 1) {
                AbstractC0734q4.a(c0684j3, o5Var, abstractC0734q4.b);
            } else if (iOrdinal == 2) {
                AbstractC0734q4.a(c0684j3, o5Var, abstractC0734q4.c);
            } else if (iOrdinal == 3) {
                AbstractC0734q4.a(c0684j3, o5Var, abstractC0734q4.d);
            } else if (iOrdinal != 4) {
                if (!AbstractC0734q4.f) {
                    throw new AssertionError();
                }
            } else {
                AbstractC0734q4.a(c0684j3, o5Var, abstractC0734q4.e);
            }
        }
    }
}
