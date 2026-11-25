package com.android.tools.r8.graph;

import com.android.dx.rop.code.AccessFlags;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.C1507Xl;
import java.util.function.BooleanSupplier;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class Q2 extends AbstractC0657g<Q2> {
    static final /* synthetic */ boolean e = true;
    public static final /* synthetic */ int f = 0;

    private Q2(int i, int i2) {
        super(i, i2);
    }

    public static Q2 O() {
        return k(4113);
    }

    public static Q2 g(int i) {
        int i2 = i & AccessFlags.FIELD_FLAGS;
        return new Q2(i2, i2);
    }

    public static Q2 j(int i) {
        int i2 = i & AccessFlags.FIELD_FLAGS;
        return new Q2(i2, i2);
    }

    public static Q2 k(int i) {
        if (!e && (i & AccessFlags.FIELD_FLAGS) != i) {
            throw new AssertionError();
        }
        int i2 = i & AccessFlags.FIELD_FLAGS;
        return new Q2(i2, i2);
    }

    public final Q2 N() {
        return new Q2(this.a, this.b);
    }

    public int P() {
        return this.b;
    }

    public final int Q() {
        return this.b;
    }

    public final boolean R() {
        return AbstractC0657g.d(this.b, 16384);
    }

    public final boolean T() {
        return AbstractC0657g.d(this.b, 128);
    }

    @Override // com.android.tools.r8.graph.AbstractC0657g, com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.x U() {
        return this;
    }

    public final boolean V() {
        return AbstractC0657g.d(this.b, 64);
    }

    @Override // com.android.tools.r8.graph.AbstractC0657g
    protected final AbstractC1639am c() {
        return new C1507Xl().a((Iterable) super.c()).a("volatile").a("transient").a(PsiKeyword.ENUM).a();
    }

    @Override // com.android.tools.r8.graph.AbstractC0657g
    protected final AbstractC1639am e() {
        return new C1507Xl().a((Iterable) super.e()).a(new BooleanSupplier() { // from class: com.android.tools.r8.graph.Q2$$ExternalSyntheticLambda0
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                return this.f$0.V();
            }
        }).a(new BooleanSupplier() { // from class: com.android.tools.r8.graph.Q2$$ExternalSyntheticLambda1
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                return this.f$0.T();
            }
        }).a(new BooleanSupplier() { // from class: com.android.tools.r8.graph.Q2$$ExternalSyntheticLambda2
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                return this.f$0.R();
            }
        }).a();
    }

    @Override // com.android.tools.r8.graph.AbstractC0657g
    /* JADX INFO: renamed from: x */
    public final AbstractC0657g U() {
        return this;
    }
}
