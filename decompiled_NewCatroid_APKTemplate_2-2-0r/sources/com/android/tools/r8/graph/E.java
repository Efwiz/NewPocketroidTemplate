package com.android.tools.r8.graph;

import com.android.dx.rop.code.AccessFlags;
import com.android.tools.r8.dex.C0582t;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.C1507Xl;
import com.android.tools.r8.internal.Z8;
import java.util.function.BooleanSupplier;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class E extends AbstractC0657g<E> {
    static final /* synthetic */ boolean e = true;

    private E(int i, int i2) {
        super(i, i2);
    }

    public static E N() {
        return new E(4113, 4113);
    }

    public static E g(int i) {
        int i2 = i & 95807;
        return new E(i2, i2);
    }

    public static E j(int i) {
        int i2 = i & AccessFlags.INNER_CLASS_FLAGS;
        return new E(i2, i2);
    }

    public static E k(int i) {
        if (e || (i & AccessFlags.INNER_CLASS_FLAGS) == i) {
            return j(i);
        }
        throw new AssertionError();
    }

    public final void O() {
        c(1024);
    }

    public int P() {
        boolean z = e;
        if (!z && W() && !R()) {
            throw new AssertionError();
        }
        if (!z && W() && Y()) {
            throw new AssertionError();
        }
        if (!z && W() && h()) {
            throw new AssertionError();
        }
        if (!z && W() && V()) {
            throw new AssertionError();
        }
        return this.b;
    }

    public final int Q() {
        int i = this.b & (-65569);
        return W() ? i | 1024 : i;
    }

    public boolean R() {
        return AbstractC0657g.d(this.b, 1024);
    }

    public boolean T() {
        return AbstractC0657g.d(this.b, 8192);
    }

    @Override // com.android.tools.r8.graph.AbstractC0657g, com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.x U() {
        return this;
    }

    public boolean V() {
        return AbstractC0657g.d(this.b, 16384);
    }

    public boolean W() {
        return AbstractC0657g.d(this.b, 512);
    }

    public final boolean X() {
        return AbstractC0657g.d(this.b, 65536);
    }

    public boolean Y() {
        return AbstractC0657g.d(this.b, 32);
    }

    public void Z() {
        d(1024);
    }

    public final boolean a(Z8 z8, boolean z) {
        if (!W()) {
            return (T() || (h() && R())) ? false : true;
        }
        if (z8.a(Z8.i) && Y()) {
            return false;
        }
        if (!z8.d(C0582t.b) || R() || z) {
            return (h() || V()) ? false : true;
        }
        return false;
    }

    public void a0() {
        d(8192);
    }

    @Override // com.android.tools.r8.graph.AbstractC0657g
    protected final AbstractC1639am c() {
        return new C1507Xl().a((Iterable) super.c()).a(PsiKeyword.INTERFACE).a("abstract").a("annotation").a(PsiKeyword.ENUM).a(PsiKeyword.SUPER).a(PsiKeyword.RECORD).a();
    }

    public final void c0() {
        d(32);
    }

    public void d0() {
        f(1024);
    }

    @Override // com.android.tools.r8.graph.AbstractC0657g
    protected final AbstractC1639am e() {
        return new C1507Xl().a((Iterable) super.e()).a(new BooleanSupplier() { // from class: com.android.tools.r8.graph.E$$ExternalSyntheticLambda0
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                return this.f$0.W();
            }
        }).a(new BooleanSupplier() { // from class: com.android.tools.r8.graph.E$$ExternalSyntheticLambda1
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                return this.f$0.R();
            }
        }).a(new BooleanSupplier() { // from class: com.android.tools.r8.graph.E$$ExternalSyntheticLambda2
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                return this.f$0.T();
            }
        }).a(new BooleanSupplier() { // from class: com.android.tools.r8.graph.E$$ExternalSyntheticLambda3
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                return this.f$0.V();
            }
        }).a(new BooleanSupplier() { // from class: com.android.tools.r8.graph.E$$ExternalSyntheticLambda4
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                return this.f$0.Y();
            }
        }).a(new BooleanSupplier() { // from class: com.android.tools.r8.graph.E$$ExternalSyntheticLambda5
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                return this.f$0.X();
            }
        }).a();
    }

    public void e0() {
        f(512);
    }

    public final void f0() {
        f(65536);
    }

    public void g0() {
        f(32);
    }

    @Override // com.android.tools.r8.graph.AbstractC0657g
    /* JADX INFO: renamed from: x */
    public final AbstractC0657g U() {
        return this;
    }
}
