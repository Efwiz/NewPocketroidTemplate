package com.android.tools.r8.graph;

import com.android.tools.r8.utils.AbstractC3938n2;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.graph.i3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC0677i3 extends AbstractC0713n4<T0, Y0> {
    static final /* synthetic */ boolean a = true;

    public static C0700l3 C() {
        return C0700l3.b;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(AbstractC0629c3 abstractC0629c3) {
    }

    public static C0596a3 o() {
        return new C0596a3();
    }

    public static C0611b3 p() {
        return C0611b3.b;
    }

    public boolean A() {
        return false;
    }

    public boolean B() {
        return false;
    }

    public AbstractC0727q0 a() {
        return null;
    }

    public abstract void a(Consumer consumer, Consumer consumer2, Consumer consumer3);

    public final void b(Consumer consumer) {
        a(consumer, consumer, new Consumer() { // from class: com.android.tools.r8.graph.i3$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AbstractC0677i3.a((AbstractC0629c3) obj);
            }
        });
    }

    @Override // com.android.tools.r8.graph.AbstractC0713n4
    public final AbstractC0677i3 e() {
        return this;
    }

    @Override // com.android.tools.r8.graph.AbstractC0713n4
    public final boolean i() {
        return true;
    }

    @Override // com.android.tools.r8.graph.AbstractC0713n4
    public boolean j() {
        return false;
    }

    public C0668h3 k() {
        return null;
    }

    public a<?> l() {
        return null;
    }

    public C0692k3 m() {
        return null;
    }

    @Override // com.android.tools.r8.graph.AbstractC0713n4
    /* JADX INFO: renamed from: n, reason: merged with bridge method [inline-methods] */
    public a g() {
        return null;
    }

    public m5 q() {
        return null;
    }

    /* JADX INFO: renamed from: r, reason: merged with bridge method [inline-methods] */
    public AbstractC0735r0 c() {
        return null;
    }

    public T0 s() {
        return null;
    }

    public Y0 t() {
        return null;
    }

    public m5 u() {
        return null;
    }

    public boolean v() {
        return false;
    }

    public boolean w() {
        return false;
    }

    public boolean x() {
        return false;
    }

    public boolean y() {
        return false;
    }

    public boolean z() {
        return false;
    }

    /* JADX INFO: renamed from: com.android.tools.r8.graph.i3$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static abstract class a<T extends AbstractC0727q0> extends AbstractC0677i3 implements C5 {
        static final /* synthetic */ boolean e = true;
        private final AbstractC0727q0 b;
        private final AbstractC0727q0 c;
        private final T0 d;

        a(AbstractC0727q0 abstractC0727q0, AbstractC0727q0 abstractC0727q1, T0 t0) {
            if (!e && abstractC0727q1.d != t0.J0()) {
                throw new AssertionError();
            }
            this.b = abstractC0727q0;
            this.c = abstractC0727q1;
            this.d = t0;
        }

        @Override // com.android.tools.r8.graph.AbstractC0677i3
        public final boolean B() {
            return true;
        }

        @Override // com.android.tools.r8.graph.AbstractC0713n4
        public final AbstractC3938n2 a(InterfaceC0694k5 interfaceC0694k5, C0702m c0702m, C0680j c0680j) {
            return AbstractC0639e.a(c(), this.b, interfaceC0694k5, c0702m, c0680j);
        }

        @Override // com.android.tools.r8.graph.C5
        public final U0 b() {
            return this.d;
        }

        @Override // com.android.tools.r8.graph.C5
        public T d() {
            return (T) this.c;
        }

        @Override // com.android.tools.r8.graph.AbstractC0677i3, com.android.tools.r8.graph.AbstractC0713n4
        public final C5 g() {
            return this;
        }

        @Override // com.android.tools.r8.graph.AbstractC0677i3, com.android.tools.r8.graph.AbstractC0713n4
        public final boolean j() {
            return true;
        }

        @Override // com.android.tools.r8.graph.AbstractC0677i3
        public final a l() {
            return this;
        }

        @Override // com.android.tools.r8.graph.AbstractC0677i3
        /* JADX INFO: renamed from: n */
        public final a g() {
            return this;
        }

        @Override // com.android.tools.r8.graph.AbstractC0677i3
        /* JADX INFO: renamed from: r */
        public final AbstractC0735r0 c() {
            return AbstractC0735r0.a(this.c, this.d);
        }

        @Override // com.android.tools.r8.graph.AbstractC0677i3
        public final T0 s() {
            return this.d;
        }

        @Override // com.android.tools.r8.graph.AbstractC0677i3
        public final Y0 t() {
            return this.d.getReference();
        }

        @Override // com.android.tools.r8.graph.AbstractC0677i3
        public final boolean y() {
            return true;
        }

        @Override // com.android.tools.r8.graph.AbstractC0677i3
        public final AbstractC0727q0 a() {
            return this.b;
        }
    }

    public final void a(Consumer<AbstractC0677i3> consumer) {
        a(consumer, consumer, consumer);
    }

    public static a a(AbstractC0727q0 abstractC0727q0, AbstractC0727q0 abstractC0727q1, T0 t0) {
        if (abstractC0727q1.f0()) {
            return new C0685j3(abstractC0727q0, abstractC0727q1.d0(), t0);
        }
        if (abstractC0727q1 instanceof C0754u0) {
            return new C0668h3(abstractC0727q0, abstractC0727q1.m(), t0);
        }
        if (a || abstractC0727q1.e0()) {
            return new C0692k3(abstractC0727q0, abstractC0727q1.a0(), t0);
        }
        throw new AssertionError();
    }
}
