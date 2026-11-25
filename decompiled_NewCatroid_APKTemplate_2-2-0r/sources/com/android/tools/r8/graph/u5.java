package com.android.tools.r8.graph;

import com.android.tools.r8.internal.OU;
import java.util.Set;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class u5 {
    private final AbstractC0681j0 a;
    private final Set b;
    private final Set c;
    private final Set d;
    private final Set e;
    private final Set f;

    private u5(AbstractC0681j0 abstractC0681j0, Set set, Set set2, Set set3, Set set4, Set set5) {
        this.a = abstractC0681j0;
        this.b = set;
        this.c = set2;
        this.d = set3;
        this.e = set4;
        this.f = set5;
    }

    public static t5 a() {
        return new t5();
    }

    public final Set b() {
        return this.b;
    }

    public final Set c() {
        return this.c;
    }

    public final AbstractC0681j0 d() {
        return this.a;
    }

    public final Set e() {
        return this.d;
    }

    public final Set f() {
        return this.e;
    }

    public final Set g() {
        return this.f;
    }

    public final boolean h() {
        return !this.d.isEmpty();
    }

    public final boolean i() {
        return !this.e.isEmpty();
    }

    public final boolean j() {
        return !this.f.isEmpty();
    }

    public final boolean k() {
        return this.d.isEmpty() && this.e.isEmpty() && this.f.isEmpty() && this.b.isEmpty();
    }

    public final t5 l() {
        return new t5(this);
    }

    public final boolean a(Y0 y0) {
        return this.e.contains(y0) || this.d.contains(y0.B0());
    }

    public final boolean a(C0684j2 c0684j2) {
        return this.f.contains(c0684j2) || this.d.contains(c0684j2.B0());
    }

    public final boolean a(AbstractC0744s2 abstractC0744s2) {
        return ((Boolean) abstractC0744s2.a(new Function() { // from class: com.android.tools.r8.graph.u5$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Boolean.valueOf(this.f$0.a((C0767v2) obj));
            }
        }, new Function() { // from class: com.android.tools.r8.graph.u5$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Boolean.valueOf(this.f$0.a((Y0) obj));
            }
        }, new Function() { // from class: com.android.tools.r8.graph.u5$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Boolean.valueOf(this.f$0.a((C0684j2) obj));
            }
        })).booleanValue();
    }

    public final boolean a(C0767v2 c0767v2) {
        return this.d.contains(c0767v2);
    }

    public static u5 a(AbstractC0681j0 abstractC0681j0) {
        return new u5(abstractC0681j0, OU.c(), OU.c(), OU.c(), OU.c(), OU.c());
    }
}
