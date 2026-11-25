package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.shaking.InterfaceC3680a0;
import com.android.tools.r8.utils.C3970u0;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.sA, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2973sA implements InterfaceC3680a0 {
    static final /* synthetic */ boolean c = true;
    private final C0767v2 a;
    private final String b;

    private C2973sA(String str, C0767v2 c0767v2) {
        this.b = str;
        this.a = c0767v2;
        if (!c && c0767v2 == null) {
            throw new AssertionError();
        }
    }

    static C2973sA a(String str, C0722p1 c0722p1, String str2) {
        int i = C3970u0.c;
        return C3970u0.E(str.replace('/', '.')) ? b(C3970u0.l(str), c0722p1, str2) : new C2973sA(str);
    }

    static C2973sA b(String str, C0722p1 c0722p1, String str2) {
        return C3970u0.A(str) ? new C2973sA(str2, c0722p1.e(str)) : new C2973sA(str2);
    }

    public final String toString() {
        C0767v2 c0767v2 = this.a;
        return c0767v2 != null ? c0767v2.e.toString() : this.b;
    }

    public final String u() {
        return this.b;
    }

    final String v() {
        C0767v2 c0767v2 = this.a;
        return c0767v2 == null ? this.b : AbstractC1745cA.a(c0767v2.b1(), true);
    }

    private C2973sA(String str) {
        this.a = null;
        this.b = str;
        if (!c && str == null) {
            throw new AssertionError();
        }
    }

    final boolean b(Consumer consumer, C0702m c0702m, String str) {
        C0767v2 c0767v2 = this.a;
        if (c0767v2 == null) {
            consumer.accept(this.b);
            return false;
        }
        C0767v2 c0767v2A = a(c0702m, c0767v2);
        if (c0767v2A == null) {
            String strB1 = this.a.b1();
            if (AbstractC1654az.a.contains(strB1)) {
                consumer.accept(strB1);
                return false;
            }
            consumer.accept(str);
            return true;
        }
        String string = c0702m.z().c(c0767v2A).toString();
        consumer.accept(string);
        return !this.a.b1().equals(string);
    }

    final boolean a(final Consumer consumer, C0702m c0702m, final String str) {
        if (this.a == null) {
            consumer.accept(this.b);
            return false;
        }
        return b(new Consumer() { // from class: com.android.tools.r8.internal.sA$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2973sA.a(str, consumer, (String) obj);
            }
        }, c0702m, str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(String str, Consumer consumer, String str2) {
        if (str2 != null && !str2.equals(str)) {
            consumer.accept(C3970u0.f(str2));
        } else {
            consumer.accept(str2);
        }
    }

    private static C0767v2 a(C0702m c0702m, C0767v2 c0767v2) {
        if (c0767v2.M0()) {
            C0767v2 c0767v2A = a(c0702m, c0767v2.b(c0702m.a()));
            if (c0767v2A != null) {
                return c0767v2.a(c0702m.a(), c0767v2A);
            }
            return null;
        }
        if (!c0767v2.O0()) {
            return c0767v2;
        }
        AbstractC1476Wj abstractC1476WjE = c0702m.E();
        abstractC1476WjE.getClass();
        C0767v2 c0767v2B = abstractC1476WjE.b(AbstractC1476Wj.e(), c0767v2);
        if (!c0702m.f().i() || ((C3733i) c0702m.i0().f()).n(c0767v2B)) {
            return c0767v2B;
        }
        return null;
    }

    @Override // com.android.tools.r8.shaking.InterfaceC3680a0
    public final void a(com.android.tools.r8.graph.Q0 q0) {
        C0767v2 c0767v2 = this.a;
        if (c0767v2 == null || !c0767v2.O0()) {
            return;
        }
        q0.a(this.a);
    }

    public final C0767v2 a(AbstractC1476Wj abstractC1476Wj) {
        C0767v2 c0767v2 = this.a;
        if (c0767v2 == null || !c0767v2.O0()) {
            return null;
        }
        C0767v2 c0767v3 = this.a;
        abstractC1476Wj.getClass();
        return abstractC1476Wj.b(AbstractC1476Wj.e(), c0767v3);
    }
}
