package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.InterfaceC3880d;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class DH extends AbstractC1476Wj {
    static final /* synthetic */ boolean e = true;
    private final C0722p1 b;
    private AbstractC1476Wj c;
    private final ConcurrentHashMap d;

    public DH(C0702m c0702m) {
        this(c0702m.a(), c0702m.E());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ KE a(KE ke) {
        return ke;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean f(C0767v2 c0767v2) {
        return a(AbstractC1476Wj.e(), c0767v2) == c0767v2;
    }

    protected abstract KE a(KE ke, C0684j2 c0684j2);

    public final DH b(Predicate predicate) {
        AbstractC1476Wj abstractC1476Wj = this.c;
        if (abstractC1476Wj.i()) {
            return abstractC1476Wj.d().a(predicate);
        }
        return null;
    }

    protected abstract C1237Oh b(C1237Oh c1237Oh);

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    /* JADX INFO: renamed from: c */
    public final C0767v2 a(AbstractC1476Wj abstractC1476Wj, C0767v2 c0767v2) {
        if (this == abstractC1476Wj || c0767v2.W0() || c0767v2.Y0() || c0767v2.U0()) {
            return c0767v2;
        }
        if (!c0767v2.M0()) {
            return b(AbstractC1476Wj.e(), c0767v2);
        }
        C0767v2 c0767v2A = (C0767v2) this.d.get(c0767v2);
        if (c0767v2A == null) {
            C0767v2 c0767v2B = c0767v2.b(this.b);
            C0767v2 c0767v2A2 = a(AbstractC1476Wj.e(), c0767v2B);
            c0767v2A = c0767v2B == c0767v2A2 ? c0767v2 : c0767v2.a(this.b, c0767v2A2);
            this.d.put(c0767v2, c0767v2A);
        }
        return c0767v2A;
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    public final DH d() {
        return this;
    }

    protected abstract C0767v2 e(C0767v2 c0767v2);

    public abstract C0684j2 f(C0684j2 c0684j2);

    public C0684j2 g(C0684j2 c0684j2) {
        return f(c0684j2);
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    public final boolean h() {
        return false;
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    public final boolean i() {
        return true;
    }

    public final C0722p1 j() {
        return this.b;
    }

    public final AbstractC1476Wj k() {
        return this.c;
    }

    public DH(C0722p1 c0722p1, AbstractC1476Wj abstractC1476Wj) {
        this.d = new ConcurrentHashMap();
        this.b = c0722p1;
        this.c = abstractC1476Wj;
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    public String a(String str) {
        return this.c.a(str);
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    public final C0767v2 b(AbstractC1476Wj abstractC1476Wj, C0767v2 c0767v2) {
        if (e || c0767v2.O0()) {
            return this == abstractC1476Wj ? c0767v2 : e(this.c.c(c0767v2));
        }
        throw new AssertionError("Expected class type, but was `" + c0767v2.r0() + "`");
    }

    public final void a(AbstractC1476Wj abstractC1476Wj, InterfaceC3880d interfaceC3880d) {
        AbstractC1476Wj abstractC1476Wj2 = this.c;
        this.c = abstractC1476Wj;
        interfaceC3880d.a();
        this.c = abstractC1476Wj2;
    }

    public final DH a(Predicate predicate) {
        AbstractC1476Wj abstractC1476Wj = this;
        while (abstractC1476Wj.i()) {
            DH dhD = abstractC1476Wj.d();
            if (predicate.test(dhD)) {
                return dhD;
            }
            abstractC1476Wj = dhD.c;
        }
        return null;
    }

    public final DH a(Predicate predicate, Predicate predicate2) {
        DH dhB = b(predicate.or(predicate2));
        if (dhB == null || predicate2.test(dhB)) {
            return null;
        }
        return dhB;
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    public KE a(C0684j2 c0684j2, C0684j2 c0684j3, EnumC1953et enumC1953et, AbstractC1476Wj abstractC1476Wj) {
        if (c0684j2.B0().M0()) {
            boolean z = e;
            if (!z) {
                if (a(AbstractC1476Wj.e(), c0684j2.I0()) != c0684j2.I0()) {
                    throw new AssertionError();
                }
            }
            if (!z && !c0684j2.G0().stream().allMatch(new Predicate() { // from class: com.android.tools.r8.internal.DH$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return this.f$0.f((C0767v2) obj);
                }
            })) {
                throw new AssertionError();
            }
            JE jeD = KE.d();
            jeD.a = c0684j2.b(a(AbstractC1476Wj.e(), c0684j2.B0()), this.b);
            return jeD.a(enumC1953et).a();
        }
        if (e || c0684j2.B0().O0()) {
            return a(c0684j2, c0684j3, enumC1953et, abstractC1476Wj, new InterfaceC1450Vj() { // from class: com.android.tools.r8.internal.DH$$ExternalSyntheticLambda3
                @Override // com.android.tools.r8.internal.InterfaceC1450Vj
                public final KE a(KE ke) {
                    return DH.a(ke);
                }
            });
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    protected C1237Oh a(com.android.tools.r8.graph.Y0 y0, AbstractC1476Wj abstractC1476Wj, final InterfaceC1424Uj interfaceC1424Uj) {
        if (this == abstractC1476Wj) {
            return AbstractC1476Wj.e().a(y0, abstractC1476Wj, interfaceC1424Uj);
        }
        return this.c.a(y0, abstractC1476Wj, new InterfaceC1424Uj() { // from class: com.android.tools.r8.internal.DH$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.internal.InterfaceC1424Uj
            public final C1237Oh a(C1237Oh c1237Oh) {
                return this.f$0.a(interfaceC1424Uj, c1237Oh);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ C1237Oh a(InterfaceC1424Uj interfaceC1424Uj, C1237Oh c1237Oh) {
        return interfaceC1424Uj.a(b(c1237Oh));
    }

    @Override // com.android.tools.r8.internal.AbstractC1476Wj
    protected KE a(C0684j2 c0684j2, final C0684j2 c0684j3, EnumC1953et enumC1953et, AbstractC1476Wj abstractC1476Wj, final InterfaceC1450Vj interfaceC1450Vj) {
        if (this == abstractC1476Wj) {
            AbstractC1476Wj abstractC1476WjE = AbstractC1476Wj.e();
            return abstractC1476WjE.a(c0684j2, c0684j3, enumC1953et, abstractC1476WjE, interfaceC1450Vj);
        }
        return this.c.a(c0684j2, f(c0684j3), enumC1953et, abstractC1476Wj, new InterfaceC1450Vj() { // from class: com.android.tools.r8.internal.DH$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.internal.InterfaceC1450Vj
            public final KE a(KE ke) {
                return this.f$0.a(interfaceC1450Vj, c0684j3, ke);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ KE a(InterfaceC1450Vj interfaceC1450Vj, C0684j2 c0684j2, KE ke) {
        return interfaceC1450Vj.a(a(ke, c0684j2));
    }
}
