package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.position.Position;
import com.android.tools.r8.utils.AbstractC3938n2;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Bh, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0857Bh {
    static final /* synthetic */ boolean e = true;
    private com.android.tools.r8.graph.Y0 a;
    private AbstractC3938n2 b = AbstractC3938n2.g();
    private AbstractC3938n2 c = AbstractC3938n2.g();
    private C0684j2 d;

    private C0857Bh() {
    }

    public static C0857Bh b() {
        return new C0857Bh();
    }

    public final C0857Bh a(boolean z, Consumer consumer, Consumer consumer2) {
        if (!z) {
            consumer = consumer2;
        }
        consumer.accept(this);
        return this;
    }

    public final C0857Bh c() {
        this.c = AbstractC3938n2.b;
        return this;
    }

    public final C0857Bh d() {
        this.c = AbstractC3938n2.a;
        return this;
    }

    public final C0857Bh a(com.android.tools.r8.graph.Y0 y0) {
        this.a = y0;
        this.b = AbstractC3938n2.a;
        return this;
    }

    public final C0857Bh b(com.android.tools.r8.graph.Y0 y0) {
        this.a = y0;
        this.b = AbstractC3938n2.b;
        return this;
    }

    public final C0857Bh a(C0684j2 c0684j2) {
        this.d = c0684j2;
        return this;
    }

    public final C0753u a() {
        int iMax;
        boolean z = e;
        if (!z) {
            if (!z && this.a == null) {
                throw new AssertionError();
            }
            if (!z && this.b.e()) {
                throw new AssertionError();
            }
            if (!z && this.c.e()) {
                throw new AssertionError();
            }
            if (!z && this.d == null) {
                throw new AssertionError();
            }
        }
        C1507Xl c1507XlG = AbstractC1639am.g();
        int iC = 0;
        if (this.b.d()) {
            c1507XlG.a(new O7(EnumC1812d20.OBJECT, 0));
            iMax = 1;
            iC = 1;
        } else {
            iMax = 0;
        }
        if (this.c.d()) {
            EnumC1812d20 enumC1812d20A = EnumC1812d20.a(this.a.getType());
            c1507XlG.a(new O7(enumC1812d20A, iC));
            iMax += enumC1812d20A.c();
            iC += enumC1812d20A.c();
        }
        int i = iC;
        int iA = com.android.tools.r8.utils.M.a(this.c.d()) + 178 + (this.b.f() << 1);
        com.android.tools.r8.graph.Y0 y0 = this.a;
        c1507XlG.a(AbstractC2206i7.a(iA, y0, y0));
        if (this.c.d()) {
            c1507XlG.a(new C3186v8());
        } else {
            EnumC1812d20 enumC1812d20A2 = EnumC1812d20.a(this.a.getType());
            iMax = Math.max(enumC1812d20A2.c(), iMax);
            c1507XlG.a(new C3109u8(enumC1812d20A2));
        }
        return new C0753u(this.d.B0(), iMax, i, c1507XlG.a(), AbstractC1639am.i(), AbstractC1639am.i(), Position.UNKNOWN);
    }
}
