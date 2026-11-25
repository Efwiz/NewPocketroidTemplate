package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ti, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3079ti {
    private final int a;
    private final int b;
    private final int c;

    /* JADX INFO: renamed from: com.android.tools.r8.internal.ti$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static final class a {
        public static final C3079ti a;

        static {
            C3152ui c3152ui = AbstractC3382xi.q;
            AbstractC0936Ds.b(c3152ui, "DECLARES_DEFAULT_VALUE");
            a = new C3079ti(c3152ui);
            C3152ui c3152ui2 = AbstractC3382xi.r;
            AbstractC0936Ds.b(c3152ui2, "IS_CROSSINLINE");
            new C3079ti(c3152ui2);
            C3152ui c3152ui3 = AbstractC3382xi.s;
            AbstractC0936Ds.b(c3152ui3, "IS_NOINLINE");
            new C3079ti(c3152ui3);
        }
    }

    static {
        C3152ui c3152ui = AbstractC3382xi.c;
        AbstractC0936Ds.b(c3152ui, "HAS_ANNOTATIONS");
        new C3079ti(c3152ui);
        AbstractC3305wi abstractC3305wi = AbstractC3382xi.d;
        AbstractC0936Ds.b(abstractC3305wi, "VISIBILITY");
        new C3079ti(abstractC3305wi, 0);
        AbstractC0936Ds.b(abstractC3305wi, "VISIBILITY");
        new C3079ti(abstractC3305wi, 1);
        AbstractC0936Ds.b(abstractC3305wi, "VISIBILITY");
        new C3079ti(abstractC3305wi, 2);
        AbstractC0936Ds.b(abstractC3305wi, "VISIBILITY");
        new C3079ti(abstractC3305wi, 3);
        AbstractC0936Ds.b(abstractC3305wi, "VISIBILITY");
        new C3079ti(abstractC3305wi, 4);
        AbstractC0936Ds.b(abstractC3305wi, "VISIBILITY");
        new C3079ti(abstractC3305wi, 5);
        AbstractC3305wi abstractC3305wi2 = AbstractC3382xi.e;
        AbstractC0936Ds.b(abstractC3305wi2, "MODALITY");
        new C3079ti(abstractC3305wi2, 0);
        AbstractC0936Ds.b(abstractC3305wi2, "MODALITY");
        new C3079ti(abstractC3305wi2, 1);
        AbstractC0936Ds.b(abstractC3305wi2, "MODALITY");
        new C3079ti(abstractC3305wi2, 2);
        AbstractC0936Ds.b(abstractC3305wi2, "MODALITY");
        new C3079ti(abstractC3305wi2, 3);
    }

    public C3079ti(int i, int i2, int i3) {
        this.a = i;
        this.b = i2;
        this.c = i3;
    }

    public final boolean a(int i) {
        return ((i >>> this.a) & ((1 << this.b) - 1)) == this.c;
    }

    public final int b(int i) {
        int i2 = (1 << this.b) - 1;
        int i3 = this.a;
        return (i & (~(i2 << i3))) + (this.c << i3);
    }

    public C3079ti(AbstractC3305wi abstractC3305wi, int i) {
        this(abstractC3305wi.a, abstractC3305wi.b, i);
    }

    public C3079ti(C3152ui c3152ui) {
        this(c3152ui, 1);
    }
}
