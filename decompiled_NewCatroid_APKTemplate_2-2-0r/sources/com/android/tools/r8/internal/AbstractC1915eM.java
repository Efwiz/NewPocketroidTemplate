package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.utils.C3926l1;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

/* JADX INFO: renamed from: com.android.tools.r8.internal.eM, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1915eM implements com.android.tools.r8.utils.structural.x<AbstractC1915eM> {
    static final /* synthetic */ boolean f = true;
    protected final int a;
    protected final C0684j2 b;
    protected final AbstractC1915eM c;
    private final boolean d;
    private final boolean e;

    /* JADX INFO: renamed from: com.android.tools.r8.internal.eM$b */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class b extends AbstractC1915eM {
        public final C0759u2 g;
        static final /* synthetic */ boolean i = true;
        private static final b h = new b(-1, null, null, false, false, null);

        /* JADX INFO: renamed from: com.android.tools.r8.internal.eM$b$a */
        /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
        public static class a extends a<b, a> {
            static final /* synthetic */ boolean i = true;
            private C0759u2 h;

            @Override // com.android.tools.r8.internal.AbstractC1915eM.a
            final a c() {
                return this;
            }

            @Override // com.android.tools.r8.internal.AbstractC1915eM.a
            /* JADX INFO: renamed from: d, reason: merged with bridge method [inline-methods] */
            public b a() {
                boolean z = i;
                if (!z && !this.f && this.a < 0) {
                    throw new AssertionError();
                }
                if (z || this.g || this.b != null) {
                    return new b(this.a, this.b, this.c, this.d, this.e, this.h);
                }
                throw new AssertionError();
            }
        }

        private b(int i2, C0684j2 c0684j2, AbstractC1915eM abstractC1915eM, boolean z, boolean z2, C0759u2 c0759u2) {
            super(i2, c0684j2, abstractC1915eM, z, z2);
            this.g = c0759u2;
            if (!i && abstractC1915eM != null && abstractC1915eM.b == null) {
                throw new AssertionError();
            }
        }

        public static a x() {
            return new a();
        }

        @Override // com.android.tools.r8.internal.AbstractC1915eM, com.android.tools.r8.utils.structural.x
        public final com.android.tools.r8.utils.structural.x U() {
            return this;
        }

        @Override // com.android.tools.r8.internal.AbstractC1915eM
        public final a a() {
            a aVarA = x().a(this.a);
            aVarA.h = this.g;
            a aVarA2 = aVarA.a(this.b);
            aVarA2.c = this.c;
            a aVar = (a) aVarA2.c();
            aVar.d = ((AbstractC1915eM) this).d;
            a aVar2 = (a) aVar.c();
            aVar2.e = ((AbstractC1915eM) this).e;
            return aVar2.c();
        }

        @Override // com.android.tools.r8.internal.AbstractC1915eM
        public final int c() {
            return 1;
        }

        @Override // com.android.tools.r8.internal.AbstractC1915eM
        public final C0759u2 d() {
            return this.g;
        }

        @Override // com.android.tools.r8.internal.AbstractC1915eM
        public final boolean l() {
            return this.g != null;
        }

        @Override // com.android.tools.r8.utils.structural.x
        public final com.android.tools.r8.utils.structural.y p() {
            return new com.android.tools.r8.utils.structural.y() { // from class: com.android.tools.r8.internal.eM$b$$ExternalSyntheticLambda0
                @Override // com.android.tools.r8.utils.structural.y
                public final void a(com.android.tools.r8.utils.structural.z zVar) {
                    AbstractC1915eM.b.c(zVar);
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void c(com.android.tools.r8.utils.structural.z zVar) {
            zVar.a(new com.android.tools.r8.utils.structural.y() { // from class: com.android.tools.r8.internal.eM$b$$ExternalSyntheticLambda1
                @Override // com.android.tools.r8.utils.structural.y
                public final void a(com.android.tools.r8.utils.structural.z zVar2) {
                    AbstractC1915eM.m1379$$Nest$sma(zVar2);
                }
            }).h(new Function() { // from class: com.android.tools.r8.internal.eM$b$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((AbstractC1915eM) obj).d();
                }
            });
        }
    }

    /* JADX INFO: renamed from: com.android.tools.r8.internal.eM$c */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class c extends AbstractC1915eM {
        private static final c g = new c(-1, null, null, false, false);

        /* JADX INFO: renamed from: com.android.tools.r8.internal.eM$c$a */
        /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
        public static class a extends a<c, a> {
            static final /* synthetic */ boolean h = true;

            private a() {
            }

            @Override // com.android.tools.r8.internal.AbstractC1915eM.a
            final a c() {
                return this;
            }

            @Override // com.android.tools.r8.internal.AbstractC1915eM.a
            /* JADX INFO: renamed from: d, reason: merged with bridge method [inline-methods] */
            public c a() {
                boolean z = h;
                if (!z && !this.f && this.a < 0) {
                    throw new AssertionError();
                }
                if (z || this.g || this.b != null) {
                    return new c(this.a, this.b, this.c, this.d, this.e);
                }
                throw new AssertionError();
            }
        }

        private c(int i, C0684j2 c0684j2, AbstractC1915eM abstractC1915eM, boolean z, boolean z2) {
            super(i, c0684j2, abstractC1915eM, z, z2);
        }

        public static a x() {
            return new a();
        }

        @Override // com.android.tools.r8.internal.AbstractC1915eM, com.android.tools.r8.utils.structural.x
        public final com.android.tools.r8.utils.structural.x U() {
            return this;
        }

        @Override // com.android.tools.r8.internal.AbstractC1915eM
        public final a a() {
            a aVarA = x().a(this.a).a(this.b);
            aVarA.c = this.c;
            a aVar = (a) aVarA.c();
            aVar.d = ((AbstractC1915eM) this).d;
            a aVar2 = (a) aVar.c();
            aVar2.e = ((AbstractC1915eM) this).e;
            return aVar2.c();
        }

        @Override // com.android.tools.r8.internal.AbstractC1915eM
        public final int c() {
            return 2;
        }

        @Override // com.android.tools.r8.utils.structural.x
        public final com.android.tools.r8.utils.structural.y p() {
            return new com.android.tools.r8.utils.structural.y() { // from class: com.android.tools.r8.internal.eM$c$$ExternalSyntheticLambda0
                @Override // com.android.tools.r8.utils.structural.y
                public final void a(com.android.tools.r8.utils.structural.z zVar) {
                    AbstractC1915eM.m1379$$Nest$sma(zVar);
                }
            };
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX INFO: renamed from: -$$Nest$sma, reason: not valid java name */
    public static /* bridge */ /* synthetic */ void m1379$$Nest$sma(com.android.tools.r8.utils.structural.z zVar) {
        a(zVar);
    }

    private AbstractC1915eM(int i, C0684j2 c0684j2, AbstractC1915eM abstractC1915eM, boolean z, boolean z2) {
        this.a = i;
        this.b = c0684j2;
        this.c = abstractC1915eM;
        this.d = z;
        this.e = z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean a(C0684j2 c0684j2, AbstractC1915eM abstractC1915eM) {
        return abstractC1915eM.b == c0684j2;
    }

    public static AbstractC1915eM u() {
        return b.h;
    }

    public static c v() {
        return c.g;
    }

    @Override // com.android.tools.r8.utils.structural.x
    public com.android.tools.r8.utils.structural.x U() {
        return this;
    }

    public abstract a a();

    public final AbstractC1915eM b() {
        return this.c;
    }

    public abstract int c();

    public C0759u2 d() {
        return null;
    }

    public int e() {
        return this.a;
    }

    public final boolean equals(Object obj) {
        return com.android.tools.r8.utils.structural.k.a(this, obj);
    }

    public final C0684j2 g() {
        return this.b;
    }

    public final AbstractC1915eM h() {
        AbstractC1915eM abstractC1915eM = this;
        while (true) {
            AbstractC1915eM abstractC1915eM2 = abstractC1915eM.c;
            if (abstractC1915eM2 == null) {
                return abstractC1915eM;
            }
            abstractC1915eM = abstractC1915eM2;
        }
    }

    public final int hashCode() {
        int i = com.android.tools.r8.utils.structural.l.d;
        return com.android.tools.r8.utils.structural.l.a(this, p());
    }

    public C0684j2 i() {
        return null;
    }

    public C3926l1 j() {
        return null;
    }

    public final boolean k() {
        return this.c != null;
    }

    public boolean l() {
        return false;
    }

    public final boolean m() {
        return this.e;
    }

    public boolean o() {
        return this.a == -1;
    }

    public boolean q() {
        return this instanceof C1835dM;
    }

    public final boolean r() {
        return this.d;
    }

    public final boolean s() {
        return !o();
    }

    public final boolean t() {
        return this == c.g;
    }

    public final String toString() {
        if (o()) {
            return "--";
        }
        StringBuilder sb = new StringBuilder();
        if (l()) {
            sb.append(d()).append(":");
        }
        sb.append("#").append(this.a);
        if (this.b != null && this.c != null) {
            sb.append(":").append(this.b.f);
        }
        AbstractC1915eM abstractC1915eM = this.c;
        if (abstractC1915eM != null) {
            while (abstractC1915eM != null) {
                sb.append(";").append(abstractC1915eM.a).append(":").append(abstractC1915eM.b.f);
                abstractC1915eM = abstractC1915eM.c;
            }
        }
        if (q()) {
            sb.append(", isOutline = true");
        }
        if (i() != null) {
            sb.append(", outlineCallee = ").append(i());
        }
        if (j() != null) {
            sb.append(", outlineCallerPositions = ").append(j());
        }
        return sb.toString();
    }

    /* JADX INFO: renamed from: com.android.tools.r8.internal.eM$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static abstract class a<P extends AbstractC1915eM, B extends a<P, B>> {
        protected int a = -1;
        protected C0684j2 b;
        protected AbstractC1915eM c;
        protected boolean d;
        protected boolean e;
        protected boolean f;
        protected boolean g;

        public B a(int i) {
            this.a = i;
            return (B) c();
        }

        public abstract AbstractC1915eM a();

        public final a b(boolean z) {
            this.d = z;
            return c();
        }

        abstract a c();

        public B a(C0684j2 c0684j2) {
            this.b = c0684j2;
            return (B) c();
        }

        public B b() {
            this.g = true;
            return (B) c();
        }

        public final a a(AbstractC1915eM abstractC1915eM) {
            this.c = abstractC1915eM;
            return c();
        }

        public final a a(boolean z) {
            this.e = z;
            return c();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.a(new ToIntFunction() { // from class: com.android.tools.r8.internal.eM$$ExternalSyntheticLambda0
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((AbstractC1915eM) obj).c();
            }
        }).a(new ToIntFunction() { // from class: com.android.tools.r8.internal.eM$$ExternalSyntheticLambda1
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((AbstractC1915eM) obj).e();
            }
        }).h(new Function() { // from class: com.android.tools.r8.internal.eM$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((AbstractC1915eM) obj).g();
            }
        }).h(new Function() { // from class: com.android.tools.r8.internal.eM$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((AbstractC1915eM) obj).b();
            }
        }).b((Predicate) new RH$$ExternalSyntheticLambda1()).b(new Predicate() { // from class: com.android.tools.r8.internal.eM$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((AbstractC1915eM) obj).m();
            }
        });
    }

    public static AbstractC1915eM a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, AbstractC1305Qs abstractC1305Qs) {
        AbstractC1915eM abstractC1915eMY0 = abstractC1305Qs.Y0();
        if (abstractC1915eMY0.b == null) {
            if (!f && !abstractC1915eMY0.o()) {
                throw new AssertionError();
            }
            abstractC1915eMY0 = b.x().a(o5Var.getReference()).a();
        }
        if (o5Var.e().N0()) {
            C0684j2 c0684j2A = c0702m.E().a((AbstractC1476Wj) null, o5Var.getReference());
            if (!f && !abstractC1915eMY0.a(c0684j2A)) {
                throw new AssertionError();
            }
        } else if (!f && abstractC1915eMY0.h().b != c0702m.E().a((AbstractC1476Wj) null, o5Var.getReference())) {
            throw new AssertionError();
        }
        return abstractC1915eMY0;
    }

    public final AbstractC1915eM a(Predicate predicate, AbstractC1915eM abstractC1915eM) {
        AbstractC1915eM abstractC1915eMA = a(predicate, false);
        return abstractC1915eMA == null ? abstractC1915eM : abstractC1915eMA;
    }

    private AbstractC1915eM a(Predicate predicate, boolean z) {
        AbstractC1915eM abstractC1915eMA;
        if (k() && (abstractC1915eMA = this.c.a(predicate, true)) != null) {
            return abstractC1915eMA;
        }
        if (z && predicate.test(this)) {
            return this;
        }
        return null;
    }

    public final boolean a(final C0684j2 c0684j2) {
        Predicate predicate = new Predicate() { // from class: com.android.tools.r8.internal.eM$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return AbstractC1915eM.a(c0684j2, (AbstractC1915eM) obj);
            }
        };
        for (AbstractC1915eM abstractC1915eM = this; abstractC1915eM != null; abstractC1915eM = abstractC1915eM.c) {
            if (predicate.test(abstractC1915eM)) {
                return true;
            }
        }
        return false;
    }

    public final AbstractC1915eM a(AbstractC1915eM abstractC1915eM) {
        a aVarA = a();
        if (k()) {
            abstractC1915eM = this.c.a(abstractC1915eM);
        }
        aVarA.c = abstractC1915eM;
        return aVarA.c().a();
    }

    public final AbstractC1915eM a(AbstractC1915eM abstractC1915eM, AbstractC1915eM abstractC1915eM2) {
        if (this == abstractC1915eM) {
            return abstractC1915eM2;
        }
        if (!k()) {
            return this;
        }
        a aVarA = a();
        aVarA.c = this.c.a(abstractC1915eM, abstractC1915eM2);
        return aVarA.c().a();
    }
}
