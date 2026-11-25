package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Pb, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1257Pb extends AbstractC1176Mb {
    static final /* synthetic */ boolean c = true;
    private final C1341Sb a;
    private final int b;

    private C1257Pb(C1341Sb c1341Sb, int i) {
        super();
        this.a = c1341Sb;
        this.b = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(AbstractC1176Mb abstractC1176Mb) {
        if (c) {
            return;
        }
        this.a.a(abstractC1176Mb);
    }

    @Override // com.android.tools.r8.internal.AbstractC1176Mb
    final StringBuilder b(StringBuilder sb) {
        return sb.append('$').append(this.b);
    }

    public final C1230Ob a(com.android.tools.r8.graph.o5 o5Var) {
        C1230Ob c1230Ob = new C1230Ob(this, o5Var);
        if (!c) {
            a(c1230Ob);
        }
        return c1230Ob;
    }

    public final C1203Nb a() {
        C1203Nb c1203Nb = new C1203Nb(this, this.a.b);
        if (!c) {
            a(c1203Nb);
        }
        return c1203Nb;
    }

    public final C1150Lb a(N6 n6) {
        C1150Lb c1150Lb = new C1150Lb(this, n6);
        if (!c) {
            a(c1150Lb);
        }
        return c1150Lb;
    }

    @Override // com.android.tools.r8.internal.AbstractC1176Mb
    final StringBuilder a(StringBuilder sb) {
        return sb.append('$').append(this.b);
    }
}
