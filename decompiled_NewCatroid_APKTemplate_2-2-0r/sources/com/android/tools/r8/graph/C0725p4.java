package com.android.tools.r8.graph;

import com.android.dx.rop.code.AccessFlags;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.C1507Xl;
import java.util.function.BooleanSupplier;
import net.bytebuddy.description.modifier.ModifierContributor;

/* JADX INFO: renamed from: com.android.tools.r8.graph.p4, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0725p4 extends AbstractC0657g<C0725p4> {
    static final /* synthetic */ boolean e = true;

    private C0725p4(int i, int i2) {
        super(i, i2);
    }

    public static C0719o4 O() {
        return new C0719o4();
    }

    public static C0725p4 Q() {
        return b(4105, false);
    }

    public static C0725p4 a(int i, boolean z) {
        int i2 = (i & ModifierContributor.ForMethod.MASK) | (z ? 65536 : 0);
        return new C0725p4(i2, i2);
    }

    public static C0725p4 b(int i, boolean z) {
        if (e || (i & ModifierContributor.ForMethod.MASK) == i) {
            return a(i, z);
        }
        throw new AssertionError();
    }

    public static C0725p4 g(int i) {
        int i2 = i & AccessFlags.METHOD_FLAGS;
        C0725p4 c0725p4 = new C0725p4(i2, i2);
        if (AbstractC0657g.d(c0725p4.b, 131072)) {
            c0725p4.d(32);
            c0725p4.f(131072);
        }
        return c0725p4;
    }

    public final boolean N() {
        return o() || j() || Y();
    }

    public final C0725p4 P() {
        return new C0725p4(this.a, this.b);
    }

    public final void R() {
        c(64);
    }

    public int T() {
        return this.b & (-65537);
    }

    @Override // com.android.tools.r8.graph.AbstractC0657g, com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.x U() {
        return this;
    }

    public final int V() {
        C0725p4 c0725p4P = P();
        if (c0725p4P.c0() && !c0725p4P.Z()) {
            c0725p4P.f(32);
            c0725p4P.d(131072);
        }
        return c0725p4P.b;
    }

    public boolean W() {
        return AbstractC0657g.d(this.b, 1024);
    }

    public boolean X() {
        return AbstractC0657g.d(this.b, 64);
    }

    public final boolean Y() {
        return AbstractC0657g.d(this.b, 65536);
    }

    public boolean Z() {
        return AbstractC0657g.d(this.b, 256);
    }

    public boolean a0() {
        return AbstractC0657g.d(this.b, 2048);
    }

    @Override // com.android.tools.r8.graph.AbstractC0657g
    protected final AbstractC1639am c() {
        return new C1507Xl().a((Iterable) super.c()).a("synchronized").a("bridge").a("varargs").a("native").a("abstract").a("strictfp").a();
    }

    public boolean c0() {
        return AbstractC0657g.d(this.b, 32);
    }

    public final boolean d0() {
        return AbstractC0657g.d(this.b, 128);
    }

    @Override // com.android.tools.r8.graph.AbstractC0657g
    protected final AbstractC1639am e() {
        return new C1507Xl().a((Iterable) super.e()).a(new BooleanSupplier() { // from class: com.android.tools.r8.graph.p4$$ExternalSyntheticLambda0
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                return this.f$0.c0();
            }
        }).a(new BooleanSupplier() { // from class: com.android.tools.r8.graph.p4$$ExternalSyntheticLambda1
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                return this.f$0.X();
            }
        }).a(new BooleanSupplier() { // from class: com.android.tools.r8.graph.p4$$ExternalSyntheticLambda2
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                return this.f$0.d0();
            }
        }).a(new BooleanSupplier() { // from class: com.android.tools.r8.graph.p4$$ExternalSyntheticLambda3
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                return this.f$0.Z();
            }
        }).a(new BooleanSupplier() { // from class: com.android.tools.r8.graph.p4$$ExternalSyntheticLambda4
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                return this.f$0.W();
            }
        }).a(new BooleanSupplier() { // from class: com.android.tools.r8.graph.p4$$ExternalSyntheticLambda5
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                return this.f$0.a0();
            }
        }).a();
    }

    public final void e0() {
        d(1024);
    }

    public void f0() {
        d(64);
    }

    public void g0() {
        d(256);
    }

    public final void h0() {
        f(1024);
    }

    public final void i0() {
        f(64);
    }

    public final void j0() {
        f(65536);
    }

    public final void k0() {
        f(131072);
    }

    public final void l0() {
        f(128);
    }

    @Override // com.android.tools.r8.graph.AbstractC0657g
    /* JADX INFO: renamed from: x */
    public final AbstractC0657g U() {
        return this;
    }

    public final void a(C0722p1 c0722p1, C0684j2 c0684j2) {
        c0722p1.getClass();
        C0759u2 c0759u2 = c0684j2.f;
        if (c0759u2 == c0722p1.Y0 || c0759u2 == c0722p1.Z0) {
            d(65536);
        }
    }
}
