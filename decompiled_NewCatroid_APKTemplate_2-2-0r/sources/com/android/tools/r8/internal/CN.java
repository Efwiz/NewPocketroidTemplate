package com.android.tools.r8.internal;

import com.android.tools.r8.utils.InterfaceC3877c1;
import java.util.AbstractMap;
import java.util.IdentityHashMap;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class CN extends AbstractC2401kf<com.android.tools.r8.graph.o5> {
    private static final CN d = new CN(new AN$$ExternalSyntheticLambda2());

    /* JADX INFO: renamed from: $r8$lambda$M9VxlhG3rk-SEAeHx7D93-1fcEc, reason: not valid java name */
    public static /* synthetic */ IdentityHashMap m796$r8$lambda$M9VxlhG3rkSEAeHx7D931fcEc() {
        return new IdentityHashMap();
    }

    public static /* synthetic */ ConcurrentHashMap $r8$lambda$QAT0o7F8T79RZ3bOugQmmUXiMwE() {
        return new ConcurrentHashMap();
    }

    protected CN(Supplier supplier) {
        super(supplier);
    }

    public static CN a(InterfaceC3877c1 interfaceC3877c1) {
        CN cnB = b();
        Objects.requireNonNull(cnB);
        interfaceC3877c1.forEach(new CN$$ExternalSyntheticLambda2(cnB));
        return cnB;
    }

    public static CN b() {
        return new CN(new CN$$ExternalSyntheticLambda1());
    }

    public static CN c() {
        return new CN(new Supplier() { // from class: com.android.tools.r8.internal.CN$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                return CN.$r8$lambda$QAT0o7F8T79RZ3bOugQmmUXiMwE();
            }
        });
    }

    public static BB d() {
        return new BB();
    }

    public static CN e() {
        return d;
    }

    public static CN j(int i) {
        return new CN(new CN$$ExternalSyntheticLambda1(), new IdentityHashMap(i));
    }

    public static BB k(int i) {
        return new BB(i);
    }

    protected CN(Supplier supplier, AbstractMap abstractMap) {
        super(supplier, abstractMap);
    }

    public static CN b(CN cn) {
        CN cnB = b();
        cnB.a.putAll(cn.a);
        return cnB;
    }

    public final void a(CN cn) {
        this.a.putAll(cn.a);
    }

    public CN a(final com.android.tools.r8.graph.Q0 q0, final AbstractC1476Wj abstractC1476Wj) {
        final CN cn = new CN(this.b);
        forEach(new Consumer() { // from class: com.android.tools.r8.internal.CN$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                CN.a(abstractC1476Wj, q0, cn, (com.android.tools.r8.graph.o5) obj);
            }
        });
        return cn;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(AbstractC1476Wj abstractC1476Wj, com.android.tools.r8.graph.Q0 q0, CN cn, com.android.tools.r8.graph.o5 o5Var) {
        com.android.tools.r8.graph.o5 o5VarA = abstractC1476Wj.a(o5Var, q0);
        if (o5VarA != null) {
            cn.add(o5VarA);
        }
    }
}
