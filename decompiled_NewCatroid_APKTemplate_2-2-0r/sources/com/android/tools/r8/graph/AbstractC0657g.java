package com.android.tools.r8.graph;

import com.android.tools.r8.graph.AbstractC0657g;
import com.android.tools.r8.internal.AbstractC1639am;
import io.netty.util.internal.StringUtil;
import java.util.function.BooleanSupplier;
import java.util.function.ToIntFunction;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: renamed from: com.android.tools.r8.graph.g, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC0657g<T extends AbstractC0657g<T>> implements com.android.tools.r8.utils.structural.x<T> {
    private static final AbstractC1639am c = AbstractC1639am.a("public", "private", "protected", "static", "final", "synthetic");
    static final /* synthetic */ boolean d = true;
    protected int a;
    protected int b;

    protected AbstractC0657g(int i, int i2) {
        this.a = i;
        this.b = i2;
    }

    public static boolean d(int i, int i2) {
        return (i & i2) != 0;
    }

    public void A() {
        if (!d && (m() || l())) {
            throw new AssertionError();
        }
        d(2);
    }

    public void B() {
        if (!d && (m() || j())) {
            throw new AssertionError();
        }
        d(4);
    }

    public void C() {
        if (!d && (j() || l())) {
            throw new AssertionError();
        }
        d(1);
    }

    public void D() {
        d(8);
    }

    public void E() {
        d(4096);
    }

    public final String F() {
        AbstractC1639am abstractC1639amC = c();
        AbstractC1639am abstractC1639amE = e();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < abstractC1639amC.size(); i++) {
            if (((BooleanSupplier) abstractC1639amE.get(i)).getAsBoolean() && !((String) abstractC1639amC.get(i)).equals(PsiKeyword.SUPER)) {
                if (sb.length() > 0) {
                    sb.append(StringUtil.SPACE);
                }
                sb.append((String) abstractC1639amC.get(i));
            }
        }
        return sb.toString();
    }

    public final AbstractC0657g G() {
        f(16);
        return U();
    }

    public void I() {
        f(2);
    }

    public void K() {
        f(4);
    }

    public void L() {
        f(1);
    }

    public final AbstractC0657g M() {
        f(4096);
        return U();
    }

    protected AbstractC1639am c() {
        return c;
    }

    protected AbstractC1639am e() {
        return AbstractC1639am.a(new BooleanSupplier() { // from class: com.android.tools.r8.graph.g$$ExternalSyntheticLambda2
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                return this.f$0.m();
            }
        }, new BooleanSupplier() { // from class: com.android.tools.r8.graph.g$$ExternalSyntheticLambda3
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                return this.f$0.j();
            }
        }, new BooleanSupplier() { // from class: com.android.tools.r8.graph.g$$ExternalSyntheticLambda4
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                return this.f$0.l();
            }
        }, new BooleanSupplier() { // from class: com.android.tools.r8.graph.g$$ExternalSyntheticLambda5
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                return this.f$0.o();
            }
        }, new BooleanSupplier() { // from class: com.android.tools.r8.graph.g$$ExternalSyntheticLambda6
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                return this.f$0.h();
            }
        }, new BooleanSupplier() { // from class: com.android.tools.r8.graph.g$$ExternalSyntheticLambda7
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                return this.f$0.q();
            }
        });
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof AbstractC0657g)) {
            return false;
        }
        AbstractC0657g abstractC0657g = (AbstractC0657g) obj;
        return this.a == abstractC0657g.a && this.b == abstractC0657g.b;
    }

    protected final void f(int i) {
        int i2 = this.a;
        int i3 = ~i;
        this.a = i2 & i3;
        this.b = i3 & this.b;
    }

    public final int g() {
        if (m()) {
            return 3;
        }
        if (l()) {
            return 2;
        }
        return j() ? 0 : 1;
    }

    public boolean h() {
        return d(this.b, 16);
    }

    public final int hashCode() {
        return this.a | this.b;
    }

    public boolean i() {
        return (m() || j() || l()) ? false : true;
    }

    public boolean j() {
        return d(this.b, 2);
    }

    public final boolean k() {
        return !d(this.a, 1) && d(this.b, 1);
    }

    public boolean l() {
        return d(this.b, 4);
    }

    public boolean m() {
        return d(this.b, 1);
    }

    public boolean o() {
        return d(this.b, 8);
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.y p() {
        return new com.android.tools.r8.utils.structural.y() { // from class: com.android.tools.r8.graph.g$$ExternalSyntheticLambda8
            @Override // com.android.tools.r8.utils.structural.y
            public final void a(com.android.tools.r8.utils.structural.z zVar) {
                AbstractC0657g.a(zVar);
            }
        };
    }

    public boolean q() {
        return d(this.b, 4096);
    }

    public final boolean r() {
        return g() == 1 || g() == 2;
    }

    public final int s() {
        return this.b;
    }

    public final void t() {
        this.b |= 16;
    }

    public final String toString() {
        AbstractC1639am abstractC1639amC = c();
        AbstractC1639am abstractC1639amE = e();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < abstractC1639amC.size(); i++) {
            if (((BooleanSupplier) abstractC1639amE.get(i)).getAsBoolean()) {
                if (sb.length() > 0) {
                    sb.append(StringUtil.SPACE);
                }
                sb.append((String) abstractC1639amC.get(i));
            }
        }
        return sb.toString();
    }

    public void u() {
        c(6);
        this.b |= 1;
    }

    public final void v() {
        this.b |= 8;
    }

    @Override // com.android.tools.r8.utils.structural.x
    /* JADX INFO: renamed from: x, reason: merged with bridge method [inline-methods] */
    public abstract AbstractC0657g U();

    public void z() {
        d(16);
    }

    protected static void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.a(new ToIntFunction() { // from class: com.android.tools.r8.graph.g$$ExternalSyntheticLambda0
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((AbstractC0657g) obj).a;
            }
        }).a(new ToIntFunction() { // from class: com.android.tools.r8.graph.g$$ExternalSyntheticLambda1
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((AbstractC0657g) obj).b;
            }
        });
    }

    public final void b() {
        c(4096);
    }

    protected final void c(int i) {
        this.b = (~i) & this.b;
    }

    public final int d() {
        return this.a;
    }

    public final AbstractC0657g a() {
        c(16);
        return U();
    }

    protected final void d(int i) {
        this.a |= i;
        this.b = i | this.b;
    }
}
