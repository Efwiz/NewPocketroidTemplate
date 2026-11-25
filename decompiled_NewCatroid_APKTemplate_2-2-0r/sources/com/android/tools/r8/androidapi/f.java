package com.android.tools.r8.androidapi;

import com.android.tools.r8.graph.AbstractC0642e2;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.Y0;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.C1507Xl;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.utils.C3912j0;
import com.android.tools.r8.utils.EnumC3891f;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class f {
    static final /* synthetic */ boolean g = true;
    private final b a;
    private final e b;
    private final C0702m c;
    private final C0722p1 d;
    private final Set e = OU.a();
    private final boolean f;

    private f(C0702m c0702m, b bVar, AbstractC1639am abstractC1639am) {
        this.c = c0702m;
        this.a = bVar;
        this.d = c0702m.a();
        this.b = new e(abstractC1639am, c0702m.Y(), c0702m.a0());
        this.f = c0702m.Y().a().f;
    }

    public static f a(C0702m c0702m, b bVar) {
        if (!g && !c0702m.Y().a().a) {
            throw new AssertionError();
        }
        final C1507Xl c1507XlG = AbstractC1639am.g();
        BiConsumer biConsumerA = C3912j0.a(new BiFunction() { // from class: com.android.tools.r8.androidapi.f$$ExternalSyntheticLambda3
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return a.a((AbstractC0744s2) obj, (EnumC3891f) obj2);
            }
        }, new Consumer() { // from class: com.android.tools.r8.androidapi.f$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                c1507XlG.a((a) obj);
            }
        });
        d.a(c0702m.a(), biConsumerA);
        c0702m.Y().a().a(c0702m.a(), biConsumerA);
        return new f(c0702m, bVar, c1507XlG.a());
    }

    public final Set a() {
        return this.e;
    }

    public final h a(C0767v2 c0767v2, h hVar, j jVar) {
        if (g || !hVar.J()) {
            return a((AbstractC0744s2) c0767v2, (h) jVar, false).e(hVar);
        }
        throw new AssertionError();
    }

    public final h a(AbstractC0744s2 abstractC0744s2, h hVar) {
        return a(abstractC0744s2, hVar, false);
    }

    public final h a(AbstractC0642e2 abstractC0642e2, j jVar) {
        return a((AbstractC0744s2) abstractC0642e2, (h) jVar, true);
    }

    private h a(AbstractC0744s2 abstractC0744s2, h hVar, boolean z) {
        C0767v2 c0767v2C = abstractC0744s2.C();
        if (c0767v2C.M0()) {
            if (abstractC0744s2.y0() && abstractC0744s2.v0().b(this.d.g4.c)) {
                return this.c.o();
            }
            return a(c0767v2C.b(this.d), hVar, z);
        }
        if (!c0767v2C.W0() && !c0767v2C.Y0()) {
            AbstractC0727q0 abstractC0727q0C = this.c.c(c0767v2C);
            if (abstractC0727q0C == null) {
                return hVar;
            }
            if (!abstractC0727q0C.f0()) {
                return this.c.o();
            }
            if (abstractC0744s2.C() == this.d.Z1) {
                return this.c.o();
            }
            if (!z && this.c.Y().C1.a(abstractC0744s2)) {
                return this.c.o();
            }
            if (abstractC0744s2.y0() && !abstractC0744s2.v0().b(this.d) && this.d.g4.a(abstractC0744s2.v0())) {
                EnumC3891f enumC3891fA = this.b.a(abstractC0744s2.C());
                if (enumC3891fA == null) {
                    return this.c.o();
                }
                return this.a.a(enumC3891fA);
            }
            final e eVar = this.b;
            Objects.requireNonNull(eVar);
            Function function = new Function() { // from class: com.android.tools.r8.androidapi.f$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return eVar.a((C0767v2) obj);
                }
            };
            final e eVar2 = this.b;
            Objects.requireNonNull(eVar2);
            Function function2 = new Function() { // from class: com.android.tools.r8.androidapi.f$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return eVar2.a((Y0) obj);
                }
            };
            final e eVar3 = this.b;
            Objects.requireNonNull(eVar3);
            EnumC3891f enumC3891f = (EnumC3891f) abstractC0744s2.a(function, function2, new Function() { // from class: com.android.tools.r8.androidapi.f$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return eVar3.a((C0684j2) obj);
                }
            });
            if (enumC3891f == null) {
                if (this.f) {
                    this.e.add(abstractC0744s2);
                }
                return hVar;
            }
            return this.a.a(enumC3891f.c(this.c.Y().B0()));
        }
        return this.c.o();
    }
}
