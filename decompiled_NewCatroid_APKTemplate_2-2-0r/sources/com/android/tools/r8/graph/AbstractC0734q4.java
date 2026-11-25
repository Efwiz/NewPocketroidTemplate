package com.android.tools.r8.graph;

import com.android.tools.r8.internal.CN;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/* JADX INFO: renamed from: com.android.tools.r8.graph.q4, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC0734q4 {
    static final /* synthetic */ boolean f = true;
    private final Map a;
    private final Map b;
    private final Map c;
    private final Map d;
    private final Map e;

    private AbstractC0734q4(Supplier supplier) {
        this((Map) supplier.get(), (Map) supplier.get(), (Map) supplier.get(), (Map) supplier.get(), (Map) supplier.get());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0684j2 c0684j2, o5 o5Var) {
        a(c0684j2, o5Var, this.a);
    }

    public final Map b() {
        return this.a;
    }

    public final Map c() {
        return this.b;
    }

    public final Map d() {
        return this.c;
    }

    public final Map e() {
        return this.d;
    }

    public final Map f() {
        return this.e;
    }

    public final boolean g(C0684j2 c0684j2, o5 o5Var) {
        return a(c0684j2, o5Var, this.b);
    }

    public final boolean h(C0684j2 c0684j2, o5 o5Var) {
        return a(c0684j2, o5Var, this.c);
    }

    public final boolean i(C0684j2 c0684j2, o5 o5Var) {
        return a(c0684j2, o5Var, this.d);
    }

    public final boolean j(C0684j2 c0684j2, o5 o5Var) {
        return a(c0684j2, o5Var, this.e);
    }

    private AbstractC0734q4(Map map, Map map2, Map map3, Map map4, Map map5) {
        this.a = map;
        this.b = map2;
        this.c = map3;
        this.d = map4;
        this.e = map5;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(C0684j2 c0684j2, o5 o5Var) {
        a(c0684j2, o5Var, this.b);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c(C0684j2 c0684j2, o5 o5Var) {
        a(c0684j2, o5Var, this.c);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d(C0684j2 c0684j2, o5 o5Var) {
        a(c0684j2, o5Var, this.d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e(C0684j2 c0684j2, o5 o5Var) {
        a(c0684j2, o5Var, this.e);
    }

    public final void a(final C0684j2 c0684j2, CN cn) {
        cn.forEach(new Consumer() { // from class: com.android.tools.r8.graph.q4$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c0684j2, (o5) obj);
            }
        });
    }

    public final boolean f(C0684j2 c0684j2, o5 o5Var) {
        return a(c0684j2, o5Var, this.a);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean a(C0684j2 c0684j2, o5 o5Var, Map map) {
        return ((CN) map.computeIfAbsent(c0684j2, new Function() { // from class: com.android.tools.r8.graph.q4$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return AbstractC0734q4.a((C0684j2) obj);
            }
        })).add(o5Var);
    }

    public final void b(final C0684j2 c0684j2, CN cn) {
        cn.forEach(new Consumer() { // from class: com.android.tools.r8.graph.q4$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b(c0684j2, (o5) obj);
            }
        });
    }

    public final void c(final C0684j2 c0684j2, CN cn) {
        cn.forEach(new Consumer() { // from class: com.android.tools.r8.graph.q4$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.c(c0684j2, (o5) obj);
            }
        });
    }

    public final void d(final C0684j2 c0684j2, CN cn) {
        cn.forEach(new Consumer() { // from class: com.android.tools.r8.graph.q4$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.d(c0684j2, (o5) obj);
            }
        });
    }

    public final void e(final C0684j2 c0684j2, CN cn) {
        cn.forEach(new Consumer() { // from class: com.android.tools.r8.graph.q4$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.e(c0684j2, (o5) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ CN a(C0684j2 c0684j2) {
        return CN.b();
    }

    public final C0762u4 a() {
        return new C0762u4(this.a, this.b, this.c, this.d, this.e);
    }
}
