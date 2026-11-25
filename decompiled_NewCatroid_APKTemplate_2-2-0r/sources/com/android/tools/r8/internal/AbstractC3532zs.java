package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.InterfaceC3949p3;
import java.util.IdentityHashMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.zs, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3532zs {
    static final /* synthetic */ boolean i = true;
    final C0702m a;
    final AbstractC3327x1 b;
    final InterfaceC2475ld c;
    final A1 d;
    final IdentityHashMap e = new IdentityHashMap();
    final IdentityHashMap f = new IdentityHashMap();
    final IdentityHashMap g = new IdentityHashMap();
    final C0842As h;

    public AbstractC3532zs(C0702m c0702m, AbstractC3327x1 abstractC3327x1, InterfaceC2475ld interfaceC2475ld, A1 a1, C0842As c0842As) {
        this.a = c0702m;
        this.b = abstractC3327x1;
        this.c = interfaceC2475ld;
        this.d = a1;
        this.h = c0842As;
    }

    private AbstractC3327x1 b(final Object obj) {
        return ((AbstractC3327x1) this.c.a(obj, this.b, new BiFunction() { // from class: com.android.tools.r8.internal.zs$$ExternalSyntheticLambda1
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj2, Object obj3) {
                return this.f$0.a(obj, obj2, (AbstractC3327x1) obj3);
            }
        }).c().i()).clone();
    }

    public final AbstractC1125Kd a(C3332x4 c3332x4, com.android.tools.r8.utils.y3 y3Var) {
        return a(com.android.tools.r8.utils.O3.h(c3332x4), y3Var);
    }

    public final boolean c(Object obj) {
        return this.c.b(obj) && d(this.c.a(obj));
    }

    public final boolean d(Object obj) {
        if (this.h.c()) {
            InterfaceC2475ld interfaceC2475ld = this.c;
            if (interfaceC2475ld.c(obj) && interfaceC2475ld.b(interfaceC2475ld.e(obj)) && obj != this.c.a() && !this.c.f(obj)) {
                return true;
            }
        }
        return false;
    }

    public final AbstractC1125Kd f(Object obj) {
        return a(com.android.tools.r8.utils.O3.h(obj), com.android.tools.r8.utils.y3.b());
    }

    boolean g(final Object obj) {
        return com.android.tools.r8.utils.E3.a(new Consumer() { // from class: com.android.tools.r8.internal.zs$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj2) {
                this.f$0.a(obj, (Function) obj2);
            }
        });
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    private AbstractC1125Kd a(final com.android.tools.r8.utils.O3 o3, final com.android.tools.r8.utils.y3 y3Var) {
        while (o3.c()) {
            final Object objF = o3.f();
            InterfaceC2998sZ interfaceC2998sZC = this.d.c(objF, (AbstractC3327x1) y3Var.a("Compute block entry state", new InterfaceC3949p3() { // from class: com.android.tools.r8.internal.zs$$ExternalSyntheticLambda3
                @Override // com.android.tools.r8.utils.InterfaceC3949p3
                public final Object get() {
                    return this.f$0.e(objF);
                }
            }));
            if (interfaceC2998sZC.b()) {
                return new C1065Id();
            }
            AbstractC3327x1 abstractC3327x1A = interfaceC2998sZC.a();
            y3Var.a("Compute transfers");
            Object obj = null;
            do {
                final boolean zD = this.c.d(objF);
                com.android.tools.r8.utils.D3 d3B = this.c.b(objF, abstractC3327x1A, new BiFunction() { // from class: com.android.tools.r8.internal.zs$$ExternalSyntheticLambda4
                    @Override // java.util.function.BiFunction
                    public final Object apply(Object obj2, Object obj3) {
                        return this.f$0.a(zD, objF, y3Var, (InterfaceC3174v) obj2, (AbstractC3327x1) obj3);
                    }
                });
                if (d3B.f()) {
                    return (AbstractC1125Kd) d3B.a().i();
                }
                abstractC3327x1A = (AbstractC3327x1) d3B.c().i();
                if (c(objF)) {
                    objF = this.c.a(objF);
                } else {
                    obj = objF;
                    objF = null;
                }
            } while (objF != null);
            y3Var.c();
            boolean z = i;
            if (!z && c(obj)) {
                throw new AssertionError();
            }
            AbstractC3327x1 abstractC3327x1 = (AbstractC3327x1) this.f.put(obj, abstractC3327x1A);
            if (!z && abstractC3327x1 != null && !abstractC3327x1A.a(this.a, abstractC3327x1)) {
                throw new AssertionError();
            }
            if (!abstractC3327x1A.equals(abstractC3327x1)) {
                this.c.c(new Consumer() { // from class: com.android.tools.r8.internal.zs$$ExternalSyntheticLambda5
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj2) {
                        o3.d(obj2);
                    }
                }, obj);
            }
            a(obj, abstractC3327x1A);
        }
        return new C1097Jd(this.f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.tools.r8.utils.D3 a(boolean z, Object obj, com.android.tools.r8.utils.y3 y3Var, InterfaceC3174v interfaceC3174v, AbstractC3327x1 abstractC3327x1) {
        if (interfaceC3174v.g() && z) {
            this.d.getClass();
            a(obj, interfaceC3174v, abstractC3327x1);
        }
        InterfaceC2998sZ interfaceC2998sZA = this.d.a(interfaceC3174v, abstractC3327x1);
        if (interfaceC2998sZA.b()) {
            y3Var.c();
            return com.android.tools.r8.utils.D3.a(new C1065Id());
        }
        if (i || interfaceC2998sZA.c()) {
            return com.android.tools.r8.utils.D3.b(interfaceC2998sZA.a());
        }
        throw new AssertionError();
    }

    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public final AbstractC3327x1 e(Object obj) {
        AbstractC3327x1 abstractC3327x1B;
        if (obj == this.c.a()) {
            AbstractC3327x1 abstractC3327x1B2 = this.d.b(obj, this.b);
            C0702m c0702m = this.a;
            if (g(obj)) {
                abstractC3327x1B = ((AbstractC3327x1) this.e.getOrDefault(obj, this.b)).clone();
            } else {
                abstractC3327x1B = b(obj);
            }
            return abstractC3327x1B2.b(c0702m, abstractC3327x1B);
        }
        if (this.c.f(obj)) {
            return ((AbstractC3327x1) this.g.getOrDefault(obj, this.b)).clone();
        }
        if (g(obj)) {
            return ((AbstractC3327x1) this.e.getOrDefault(obj, this.b)).clone();
        }
        return b(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.tools.r8.utils.D3 a(Object obj, Object obj2, AbstractC3327x1 abstractC3327x1) {
        return com.android.tools.r8.utils.D3.b(abstractC3327x1.b(this.a, this.d.a(obj, obj2, ((AbstractC3327x1) this.f.getOrDefault(obj2, this.b)).clone())));
    }

    final void a(final Object obj, final AbstractC3327x1 abstractC3327x1) {
        this.c.b(new Consumer() { // from class: com.android.tools.r8.internal.zs$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj2) {
                this.f$0.a(obj, abstractC3327x1, obj2);
            }
        }, obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Object obj, AbstractC3327x1 abstractC3327x1, Object obj2) {
        if (g(obj2)) {
            AbstractC3327x1 abstractC3327x1A = this.d.a(obj2, obj, abstractC3327x1);
            IdentityHashMap identityHashMap = this.e;
            identityHashMap.put(obj2, ((AbstractC3327x1) identityHashMap.getOrDefault(obj2, this.b)).b(this.a, abstractC3327x1A));
        }
    }

    final void a(final Object obj, final InterfaceC3174v interfaceC3174v, final AbstractC3327x1 abstractC3327x1) {
        this.c.a(obj, new BiConsumer() { // from class: com.android.tools.r8.internal.zs$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj2, Object obj3) {
                this.f$0.a(obj, interfaceC3174v, abstractC3327x1, obj2, (C0767v2) obj3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Object obj, InterfaceC3174v interfaceC3174v, AbstractC3327x1 abstractC3327x1, Object obj2, C0767v2 c0767v2) {
        AbstractC3327x1 abstractC3327x1A = this.d.a(obj2, c0767v2, obj, interfaceC3174v, abstractC3327x1);
        IdentityHashMap identityHashMap = this.g;
        identityHashMap.put(obj2, ((AbstractC3327x1) identityHashMap.getOrDefault(obj2, this.b)).b(this.a, abstractC3327x1A));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Object obj, Function function) {
        this.c.i(obj, function);
    }
}
