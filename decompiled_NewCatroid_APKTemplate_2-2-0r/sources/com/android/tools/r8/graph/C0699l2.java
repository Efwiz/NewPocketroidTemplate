package com.android.tools.r8.graph;

import com.android.tools.r8.internal.C1944ek;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.naming.AbstractC3649o0;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

/* JADX INFO: renamed from: com.android.tools.r8.graph.l2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0699l2 extends I3 implements V4 {
    static final /* synthetic */ boolean h = true;
    public final EnumC0691k2 d;
    public final AbstractC0642e2 e;
    public final boolean f;
    public final C0684j2 g;

    public C0699l2(EnumC0691k2 enumC0691k2, AbstractC0642e2 abstractC0642e2, boolean z, C0684j2 c0684j2) {
        this.d = enumC0691k2;
        this.e = abstractC0642e2;
        this.f = z;
        this.g = c0684j2;
    }

    public static C0699l2 a(C1944ek c1944ek, M3 m3, C0767v2 c0767v2) {
        AbstractC0642e2 abstractC0642e2B;
        EnumC0691k2 enumC0691k2A = EnumC0691k2.a(c1944ek, m3, c0767v2);
        if (enumC0691k2A.b()) {
            String strC = c1944ek.c();
            abstractC0642e2B = m3.a(m3.f(strC), c1944ek.b(), c1944ek.a());
        } else {
            String strC2 = c1944ek.c();
            abstractC0642e2B = m3.b(m3.f(strC2), c1944ek.b(), c1944ek.a());
        }
        return m3.a.a.a(enumC0691k2A, abstractC0642e2B, c1944ek.e(), (C0684j2) null);
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.x U() {
        return this;
    }

    @Override // com.android.tools.r8.graph.AbstractC0740s
    public final boolean c(Object obj) {
        if (!(obj instanceof C0699l2)) {
            return false;
        }
        C0699l2 c0699l2 = (C0699l2) obj;
        return this.d.equals(c0699l2.d) && this.e.equals(c0699l2.e) && this.f == c0699l2.f && Objects.equals(this.g, c0699l2.g);
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.y p() {
        return new com.android.tools.r8.utils.structural.y() { // from class: com.android.tools.r8.graph.l2$$ExternalSyntheticLambda7
            @Override // com.android.tools.r8.utils.structural.y
            public final void a(com.android.tools.r8.utils.structural.z zVar) {
                C0699l2.a(zVar);
            }
        };
    }

    @Override // com.android.tools.r8.graph.AbstractC0594a1
    public final String q0() {
        return toString();
    }

    @Override // com.android.tools.r8.graph.AbstractC0740s
    public final int s0() {
        return Objects.hash(this.d, Integer.valueOf(this.e.s0()), Boolean.valueOf(this.f), this.g);
    }

    public final Y0 t0() {
        if (h || this.d.b()) {
            return (Y0) this.e;
        }
        throw new AssertionError();
    }

    public final String toString() {
        return "MethodHandle: {" + this.d + ", " + this.e.r0() + "}";
    }

    public final C0684j2 u0() {
        if (h || this.d.h()) {
            return (C0684j2) this.e;
        }
        throw new AssertionError();
    }

    public final boolean v0() {
        return this.d.b();
    }

    public final boolean w0() {
        return this.d.h();
    }

    public final void a(C0702m c0702m, com.android.tools.r8.dex.L l) {
        if (l.a(this)) {
            if (this.e.x0()) {
                this.e.t0().a(c0702m, l);
                return;
            }
            C0684j2 c0684j2V0 = this.e.v0();
            if (this.g != null) {
                if (c0684j2V0.b(c0702m, l)) {
                    C0684j2 c0684j2 = this.g;
                    c0684j2.getClass();
                    C0759u2 c0759u2A = c0702m.z().a(c0684j2);
                    c0759u2A.getClass();
                    l.a(c0759u2A);
                    return;
                }
                return;
            }
            if (c0684j2V0.b(c0702m, l)) {
                C0759u2 c0759u2A2 = c0702m.z().a(c0684j2V0);
                c0759u2A2.getClass();
                l.a(c0759u2A2);
            }
        }
    }

    @Override // com.android.tools.r8.graph.I3
    public final int a(C0645e5 c0645e5) {
        return c0645e5.a(this);
    }

    public final C1944ek a(AbstractC3649o0 abstractC3649o0) {
        String strD;
        String string;
        String string2;
        boolean z;
        int i;
        if (this.d.h()) {
            C0684j2 c0684j2U0 = u0();
            strD = abstractC3649o0.d(c0684j2U0.e);
            C0684j2 c0684j2 = this.g;
            if (c0684j2 != null) {
                string = abstractC3649o0.a(c0684j2).toString();
            } else {
                string = abstractC3649o0.a(c0684j2U0).toString();
            }
            string2 = c0684j2U0.h.a(abstractC3649o0);
            if (c0684j2U0.e.b1().equals("Ljava/lang/invoke/LambdaMetafactory;")) {
                if (!h && this.f) {
                    throw new AssertionError();
                }
                z = false;
            } else {
                z = this.f;
            }
        } else {
            if (!h && !this.d.b()) {
                throw new AssertionError();
            }
            Y0 y0T0 = t0();
            strD = abstractC3649o0.d(y0T0.e);
            string = abstractC3649o0.a(y0T0).toString();
            string2 = abstractC3649o0.c(y0T0.h).toString();
            z = this.f;
        }
        String str = string2;
        boolean z2 = z;
        String str2 = strD;
        String str3 = string;
        switch (this.d.ordinal()) {
            case 0:
                i = 4;
                break;
            case 1:
                i = 2;
                break;
            case 2:
                i = 3;
                break;
            case 3:
                i = 1;
                break;
            case 4:
                i = 6;
                break;
            case 5:
                i = 5;
                break;
            case 6:
                i = 8;
                break;
            case 7:
            case 9:
                i = 7;
                break;
            case 8:
                i = 9;
                break;
            default:
                throw new C3405y10();
        }
        return new C1944ek(i, str2, str3, str, z2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int a(C0699l2 c0699l2) {
        return c0699l2.d.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.a(new ToIntFunction() { // from class: com.android.tools.r8.graph.l2$$ExternalSyntheticLambda0
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return C0699l2.a((C0699l2) obj);
            }
        }).a(new Function() { // from class: com.android.tools.r8.graph.l2$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0699l2) obj).t0();
            }
        }, new Predicate() { // from class: com.android.tools.r8.graph.l2$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((C0699l2) obj).v0();
            }
        }).a(new Function() { // from class: com.android.tools.r8.graph.l2$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0699l2) obj).u0();
            }
        }, new Predicate() { // from class: com.android.tools.r8.graph.l2$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((C0699l2) obj).w0();
            }
        }).b(new Predicate() { // from class: com.android.tools.r8.graph.l2$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((C0699l2) obj).f;
            }
        }).h(new Function() { // from class: com.android.tools.r8.graph.l2$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0699l2) obj).g;
            }
        });
    }
}
