package com.android.tools.r8.internal;

import com.android.tools.r8.ClassFileConsumer;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.utils.InterfaceC3905h3;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class D7 {
    public static C3118uH a(C0702m c0702m, com.android.tools.r8.androidapi.b bVar) {
        if (c0702m.Y().A0.b()) {
            return new C3118uH(c0702m, bVar);
        }
        return c0702m.Y().f instanceof ClassFileConsumer ? C3118uH.a(c0702m) : C3118uH.b(c0702m);
    }

    public abstract C1952es a(C2569ms c2569ms);

    public abstract C1952es a(Predicate predicate, C2569ms c2569ms);

    public abstract Collection a(B7 b7, InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob);

    public abstract void a(com.android.tools.r8.graph.o5 o5Var, G7 g7);

    public abstract void a(G7 g7, C3124uN c3124uN, com.android.tools.r8.graph.o5 o5Var);

    public abstract void a(C1230Ob c1230Ob, com.android.tools.r8.graph.o5 o5Var, G7 g7);

    public abstract void a(InterfaceC3905h3 interfaceC3905h3);

    public abstract void a(Consumer consumer);

    public abstract boolean a(com.android.tools.r8.graph.o5 o5Var);

    public static C1399Uf a() {
        return C1399Uf.b();
    }
}
