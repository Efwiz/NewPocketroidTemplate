package com.android.tools.r8.naming;

import com.android.tools.r8.AbstractC0402a;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.J3;
import com.android.tools.r8.graph.Y0;
import com.android.tools.r8.utils.A1;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class V0 extends AbstractC3647n0 {
    static final /* synthetic */ boolean g = true;
    private final C0722p1 d;
    private final AbstractC3649o0 e;
    private final Map f;

    private V0(C0702m c0702m, IdentityHashMap identityHashMap) {
        super(c0702m.a());
        this.d = c0702m.a();
        this.e = c0702m.z();
        this.f = identityHashMap;
    }

    public static AbstractC3649o0 a(C0702m c0702m) {
        final C0722p1 c0722p1A = c0702m.a();
        if (!c0702m.Y().D1() || (c0702m.f().d(c0722p1A.o5) == null && c0702m.f().d(c0722p1A.B2) == null)) {
            return c0702m.z();
        }
        c0702m.f().d().forEach(new Consumer() { // from class: com.android.tools.r8.naming.V0$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                V0.a(c0722p1A, (C0729q2) obj);
            }
        });
        IdentityHashMap identityHashMap = new IdentityHashMap();
        a(c0702m, c0722p1A.o5, c0722p1A.B5, identityHashMap);
        a(c0702m, c0722p1A.B2, c0722p1A.A5, identityHashMap);
        return new V0(c0702m, identityHashMap);
    }

    @Override // com.android.tools.r8.naming.AbstractC3649o0
    public final boolean b() {
        return this.e.b();
    }

    @Override // com.android.tools.r8.naming.AbstractC3649o0
    public final C0759u2 e(C0767v2 c0767v2) {
        return this.e.e(c0767v2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(final C0722p1 c0722p1, C0729q2 c0729q2) {
        c0729q2.j(new Predicate() { // from class: com.android.tools.r8.naming.V0$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return V0.a(c0722p1, (J3) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C0722p1 c0722p1, J3 j3) {
        return j3.b() == c0722p1.o5;
    }

    private static void a(C0702m c0702m, C0767v2 c0767v2, C0767v2 c0767v3, final IdentityHashMap identityHashMap) {
        final C0759u2 c0759u2C;
        final C0722p1 c0722p1A = c0702m.a();
        if (!g && !c0702m.f().g().e()) {
            throw new AssertionError();
        }
        String string = c0767v2.e.toString();
        final C0759u2 c0759u2C2 = c0722p1A.c(string.substring(0, string.length() - 1));
        String string2 = c0767v3.e.toString();
        if (c0702m.Y().Z.isEmpty()) {
            c0759u2C = c0722p1A.c("L" + AbstractC0402a.a(string2, 1, 1));
        } else {
            c0759u2C = c0722p1A.c("L" + c0702m.Y().Z + AbstractC0402a.a(string2, 1, 1));
        }
        c0702m.f().g().a(c0767v2).forEach(new Consumer() { // from class: com.android.tools.r8.naming.V0$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                V0.a(identityHashMap, c0759u2C2, c0759u2C, c0722p1A, (C0767v2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Map map, C0759u2 c0759u2, C0759u2 c0759u3, C0722p1 c0722p1, C0767v2 c0767v2) {
        map.put(c0767v2, c0767v2.e.a(c0759u2, c0759u3, c0722p1));
    }

    @Override // com.android.tools.r8.naming.AbstractC3649o0
    protected final C0759u2 a(C0767v2 c0767v2) {
        boolean z = g;
        if (!z && c0767v2 == this.d.B5) {
            throw new AssertionError();
        }
        if (!z && c0767v2 == this.d.A5) {
            throw new AssertionError();
        }
        C0759u2 c0759u2 = (C0759u2) this.f.get(c0767v2);
        return c0759u2 != null ? c0759u2 : this.e.c(c0767v2);
    }

    @Override // com.android.tools.r8.naming.AbstractC3649o0
    public final C0759u2 a(J3 j3, A1 a1) {
        boolean z = g;
        if (!z) {
            C0767v2 c0767v2B = j3.b();
            if (!z && c0767v2B == this.d.B5) {
                throw new AssertionError();
            }
            if (!z && c0767v2B == this.d.A5) {
                throw new AssertionError();
            }
            if (((C0759u2) this.f.get(c0767v2B)) != null) {
                throw new AssertionError();
            }
        }
        return this.e.a(j3, a1);
    }

    @Override // com.android.tools.r8.naming.AbstractC3649o0
    public final C0759u2 a(C0684j2 c0684j2) {
        return this.e.a(c0684j2);
    }

    @Override // com.android.tools.r8.naming.AbstractC3649o0
    public final C0759u2 a(Y0 y0) {
        return this.e.a(y0);
    }

    @Override // com.android.tools.r8.naming.AbstractC3649o0
    public final String a(String str) {
        return this.e.a(str);
    }
}
