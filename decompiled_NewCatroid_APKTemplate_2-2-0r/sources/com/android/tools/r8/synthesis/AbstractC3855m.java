package com.android.tools.r8.synthesis;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.AbstractC0860Bk;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.AbstractC3154uk;
import com.android.tools.r8.internal.C1121Ka;

/* JADX INFO: renamed from: com.android.tools.r8.synthesis.m, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC3855m {
    static final /* synthetic */ boolean c = true;
    private final L.b a;
    private final C3846e b;

    AbstractC3855m(L.b bVar, C3846e c3846e) {
        boolean z = c;
        if (!z && bVar == null) {
            throw new AssertionError();
        }
        if (!z && c3846e == null) {
            throw new AssertionError();
        }
        this.a = bVar;
        this.b = c3846e;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C0767v2 a(C0767v2 c0767v2, C0767v2 c0767v3, C0767v2 c0767v4) {
        return c0767v4 == c0767v2 ? c0767v3 : c0767v4;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C0767v2 a(C0767v2 c0767v2, C0767v2 c0767v3, C0767v2 c0767v4, C0767v2 c0767v5, C0767v2 c0767v6) {
        return (c0767v6 == c0767v2 || c0767v6 == c0767v3 || c0767v6 == c0767v4) ? c0767v5 : c0767v6;
    }

    abstract int a(AbstractC3855m abstractC3855m, com.android.tools.r8.utils.structural.t tVar);

    abstract void a(com.android.tools.r8.utils.structural.m mVar, com.android.tools.r8.utils.structural.t tVar);

    public InterfaceC3854l b() {
        return null;
    }

    public abstract T c();

    final C3846e d() {
        return this.b;
    }

    public abstract AbstractC0727q0 e();

    final L.b f() {
        return this.a;
    }

    public boolean g() {
        return false;
    }

    public boolean h() {
        return this instanceof I;
    }

    public abstract boolean i();

    abstract U j();

    final AbstractC3154uk a(com.android.tools.r8.utils.structural.t tVar, boolean z) {
        com.android.tools.r8.utils.structural.m mVar = new com.android.tools.r8.utils.structural.m(AbstractC0860Bk.a().a());
        mVar.a(this.a.c());
        if (!this.a.h()) {
            e().getType().a(mVar);
            return mVar.c();
        }
        if (z) {
            this.b.c().a(mVar, tVar);
        }
        mVar.a(this.b.a().hashCode());
        a(mVar, tVar);
        return mVar.c();
    }

    final String a(C0702m c0702m) {
        if (!c0702m.Y().t0 && this.b.d() && !this.a.e()) {
            return L.a(e().getType(), this.a);
        }
        return L.b(e().getType(), this.a);
    }

    /* JADX WARN: Code duplicated, block: B:28:0x0079  */
    final int a(AbstractC3855m abstractC3855m, boolean z, AbstractC1476Wj abstractC1476Wj, C1121Ka c1121Ka) {
        com.android.tools.r8.utils.structural.t tVar;
        int iCompareTo;
        int iCompareTo2 = this.a.compareTo(abstractC3855m.a);
        if (iCompareTo2 != 0) {
            return iCompareTo2;
        }
        final C0767v2 type = e().getType();
        final C0767v2 type2 = abstractC3855m.e().getType();
        if (!this.a.h()) {
            return type.compareTo(type2);
        }
        if (z && (iCompareTo = this.b.compareTo(abstractC3855m.b)) != 0) {
            return iCompareTo;
        }
        if (this.b.a() != abstractC3855m.b.a()) {
            int iA = c1121Ka.a(this.b.a(), abstractC3855m.b.a());
            if (c || iA != 0) {
                return iA;
            }
            throw new AssertionError();
        }
        if (abstractC1476Wj.i()) {
            final C0767v2 c0767v2A = abstractC1476Wj.a(type);
            final C0767v2 c0767v2A2 = abstractC1476Wj.a(type2);
            if (type == c0767v2A && type2 == c0767v2A2) {
                tVar = null;
            } else {
                tVar = new com.android.tools.r8.utils.structural.t() { // from class: com.android.tools.r8.synthesis.m$$ExternalSyntheticLambda0
                    @Override // com.android.tools.r8.utils.structural.t
                    public final C0767v2 a(C0767v2 c0767v2) {
                        return AbstractC3855m.a(type2, c0767v2A, c0767v2A2, type, c0767v2);
                    }
                };
            }
        } else {
            tVar = null;
        }
        if (tVar == null) {
            tVar = new com.android.tools.r8.utils.structural.t() { // from class: com.android.tools.r8.synthesis.m$$ExternalSyntheticLambda1
                @Override // com.android.tools.r8.utils.structural.t
                public final C0767v2 a(C0767v2 c0767v2) {
                    return AbstractC3855m.a(type2, type, c0767v2);
                }
            };
        }
        return a(abstractC3855m, tVar);
    }
}
