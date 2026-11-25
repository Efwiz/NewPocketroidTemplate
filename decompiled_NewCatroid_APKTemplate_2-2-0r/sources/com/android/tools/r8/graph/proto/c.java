package com.android.tools.r8.graph.proto;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.W0;
import com.android.tools.r8.internal.AbstractC1053Hr;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.C1430Up;
import com.android.tools.r8.internal.C2732or;
import com.android.tools.r8.internal.H2;
import com.android.tools.r8.internal.InterfaceC1456Vp;
import com.android.tools.r8.internal.InterfaceC1912eK;
import com.android.tools.r8.internal.InterfaceC2806pp;
import com.android.tools.r8.internal.InterfaceC3088tr;
import com.android.tools.r8.internal.YJ;
import com.android.tools.r8.utils.InterfaceC3937n1;
import java.util.Objects;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class c {
    private static final C1430Up e = new C1430Up();
    private static final c f = new c();
    static final /* synthetic */ boolean g = true;
    private final InterfaceC1456Vp a;
    private final int b;
    private final e c;
    private final boolean d;

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a {
        private C1430Up a = new C1430Up();
        private int b = -1;
        private e c = e.a();
        private boolean d;

        public a a(int i, b bVar) {
            this.a.a(i, bVar);
            return this;
        }

        public final a b() {
            this.d = true;
            return this;
        }

        public final a a(c cVar) {
            cVar.a(new InterfaceC3937n1() { // from class: com.android.tools.r8.graph.proto.c$a$$ExternalSyntheticLambda0
                @Override // com.android.tools.r8.utils.InterfaceC3937n1
                public final void a(int i, Object obj) {
                    this.f$0.a(i, (b) obj);
                }
            });
            return this;
        }

        public c a() {
            if (this.a.isEmpty()) {
                e eVar = this.c;
                eVar.getClass();
                if ((eVar instanceof g) && !this.d) {
                    return c.f;
                }
            }
            return new c(this.a.isEmpty() ? c.e : this.a, this.b, this.c, this.d);
        }

        public a a(int i) {
            this.b = i;
            return this;
        }

        public final a a(e eVar) {
            this.c = eVar;
            return this;
        }
    }

    private c() {
        this.a = e;
        this.b = -1;
        this.c = e.a();
        this.d = false;
    }

    public static c b() {
        return f;
    }

    public final void a(InterfaceC3937n1 interfaceC3937n1) {
        YJ it = this.a.b().iterator();
        while (it.hasNext()) {
            InterfaceC2806pp interfaceC2806pp = (InterfaceC2806pp) it.next();
            interfaceC3937n1.a(interfaceC2806pp.a(), (b) interfaceC2806pp.getValue());
        }
    }

    public final InterfaceC3088tr c() {
        int iH = h();
        if (iH == 0) {
            return AbstractC1053Hr.a;
        }
        if (iH == this.a.size()) {
            return this.a.keySet();
        }
        C2732or c2732or = new C2732or(iH);
        YJ it = this.a.b().iterator();
        while (it.hasNext()) {
            InterfaceC2806pp interfaceC2806pp = (InterfaceC2806pp) it.next();
            if (((b) interfaceC2806pp.getValue()).c()) {
                c2732or.a(interfaceC2806pp.a());
            }
        }
        return c2732or;
    }

    public final boolean d() {
        e eVar = this.c;
        eVar.getClass();
        return !(eVar instanceof g);
    }

    public final boolean e() {
        InterfaceC1912eK it = this.a.values().iterator();
        while (it.hasNext()) {
            if (((b) it.next()).c()) {
                return true;
            }
        }
        return false;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        c cVar = (c) obj;
        return this.a.equals(cVar.a) && this.c.equals(cVar.c) && this.b == cVar.b && this.d == cVar.d;
    }

    public final boolean f() {
        return this.d;
    }

    public final boolean g() {
        return this == f;
    }

    public final int h() {
        return a(Integer.MAX_VALUE, this.a);
    }

    public final int hashCode() {
        return Objects.hash(this.a, this.c, Integer.valueOf(this.b), Boolean.valueOf(this.d));
    }

    public int i() {
        if (g || !g()) {
            return this.b;
        }
        throw new AssertionError();
    }

    public final int b(int i) {
        return a(i, a(i, this.a));
    }

    private static int a(int i, InterfaceC1456Vp interfaceC1456Vp) {
        YJ it = interfaceC1456Vp.b().iterator();
        int i2 = 0;
        while (it.hasNext()) {
            InterfaceC2806pp interfaceC2806pp = (InterfaceC2806pp) it.next();
            int iA = interfaceC2806pp.a();
            b bVar = (b) interfaceC2806pp.getValue();
            if (iA >= i) {
                if (g || iA > i || !bVar.c()) {
                    break;
                    break;
                    break;
                }
                throw new AssertionError();
            }
            if (bVar.c()) {
                i2++;
            }
        }
        return i2;
    }

    private c(C1430Up c1430Up, int i, e eVar, boolean z) {
        boolean z2 = g;
        if (!z2 && c1430Up == null) {
            throw new AssertionError();
        }
        if (!z2 && eVar == null) {
            throw new AssertionError();
        }
        if (!z2 && c1430Up.isEmpty() && c1430Up != e) {
            throw new AssertionError();
        }
        if (!z2 && c1430Up.isEmpty() && eVar.b() && !z) {
            throw new AssertionError("should use empty.");
        }
        if (!z2 && i < 0) {
            throw new AssertionError();
        }
        this.a = c1430Up;
        this.b = i;
        this.c = eVar;
        this.d = z;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public b a(int i) {
        return (b) this.a.getOrDefault(Integer.valueOf(i), b.a);
    }

    public final int a(int i, int i2) {
        return this.c.b(i - i2);
    }

    public final c a(final C0702m c0702m, final H2 h2, final AbstractC1476Wj abstractC1476Wj) {
        if (g()) {
            return this;
        }
        final a aVarA = a();
        a(new InterfaceC3937n1() { // from class: com.android.tools.r8.graph.proto.c$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.utils.InterfaceC3937n1
            public final void a(int i, Object obj) {
                c.a(c0702m, h2, abstractC1476Wj, aVarA, i, (b) obj);
            }
        });
        if (aVarA.a.isEmpty()) {
            e eVar = aVarA.c;
            eVar.getClass();
            if ((eVar instanceof g) && !aVarA.d) {
                return this;
            }
        }
        a(new InterfaceC3937n1() { // from class: com.android.tools.r8.graph.proto.c$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.utils.InterfaceC3937n1
            public final void a(int i, Object obj) {
                c.a(aVarA, i, (b) obj);
            }
        });
        a aVarA2 = aVarA.a(this.b);
        aVarA2.d = this.d;
        return aVarA2.a();
    }

    public final boolean c(int i) {
        return this.a.a(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0702m c0702m, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2, a aVar, int i, b bVar) {
        b bVarA = bVar.a(c0702m, abstractC1476Wj, abstractC1476Wj2);
        if (bVarA != bVar) {
            aVar.a(i, bVarA);
        }
    }

    public static a a() {
        return new a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(a aVar, int i, b bVar) {
        if (aVar.a.a(i)) {
            return;
        }
        aVar.a(i, bVar);
    }

    /* JADX WARN: Code restructure failed: missing block: B:63:0x0053, code lost:
    
        r7 = null;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public com.android.tools.r8.graph.proto.c a(com.android.tools.r8.graph.proto.c r11) {
        /*
            Method dump skipped, instruction units count: 287
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.tools.r8.graph.proto.c.a(com.android.tools.r8.graph.proto.c):com.android.tools.r8.graph.proto.c");
    }

    public final Consumer a(final W0 w0) {
        return new Consumer() { // from class: com.android.tools.r8.graph.proto.c$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(w0, (W0.a) obj);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(W0 w0, W0.a aVar) {
        aVar.a(w0, this);
    }
}
