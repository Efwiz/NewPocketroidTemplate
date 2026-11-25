package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.AbstractC0657g;
import com.android.tools.r8.internal.AbstractC1639am;
import io.netty.util.internal.StringUtil;
import java.util.function.BooleanSupplier;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.o2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3774o2 {
    private static final int b;
    private static final AbstractC1639am c;
    private int a = 0;

    static {
        C3774o2 c3774o2 = new C3774o2();
        c3774o2.b(1);
        c3774o2.b(4);
        c3774o2.b(2);
        b = c3774o2.a;
        c = AbstractC1639am.a("public", "private", "protected", "static", "final", "abstract", "volatile", "transient", "synchronized", "native", "strictfp", "synthetic", "bridge", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME);
    }

    private AbstractC1639am a() {
        return AbstractC1639am.a(new BooleanSupplier() { // from class: com.android.tools.r8.shaking.o2$$ExternalSyntheticLambda0
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                return this.f$0.i();
            }
        }, new BooleanSupplier() { // from class: com.android.tools.r8.shaking.o2$$ExternalSyntheticLambda9
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                return this.f$0.g();
            }
        }, new BooleanSupplier() { // from class: com.android.tools.r8.shaking.o2$$ExternalSyntheticLambda10
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                return this.f$0.h();
            }
        }, new BooleanSupplier() { // from class: com.android.tools.r8.shaking.o2$$ExternalSyntheticLambda11
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                return this.f$0.j();
            }
        }, new BooleanSupplier() { // from class: com.android.tools.r8.shaking.o2$$ExternalSyntheticLambda12
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                return this.f$0.e();
            }
        }, new BooleanSupplier() { // from class: com.android.tools.r8.shaking.o2$$ExternalSyntheticLambda13
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                return this.f$0.b();
            }
        }, new BooleanSupplier() { // from class: com.android.tools.r8.shaking.o2$$ExternalSyntheticLambda1
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                return this.f$0.o();
            }
        }, new BooleanSupplier() { // from class: com.android.tools.r8.shaking.o2$$ExternalSyntheticLambda2
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                return this.f$0.n();
            }
        }, new BooleanSupplier() { // from class: com.android.tools.r8.shaking.o2$$ExternalSyntheticLambda3
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                return this.f$0.l();
            }
        }, new BooleanSupplier() { // from class: com.android.tools.r8.shaking.o2$$ExternalSyntheticLambda4
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                return this.f$0.f();
            }
        }, new BooleanSupplier() { // from class: com.android.tools.r8.shaking.o2$$ExternalSyntheticLambda5
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                return this.f$0.k();
            }
        }, new BooleanSupplier() { // from class: com.android.tools.r8.shaking.o2$$ExternalSyntheticLambda6
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                return this.f$0.m();
            }
        }, new BooleanSupplier() { // from class: com.android.tools.r8.shaking.o2$$ExternalSyntheticLambda7
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                return this.f$0.c();
            }
        }, new BooleanSupplier() { // from class: com.android.tools.r8.shaking.o2$$ExternalSyntheticLambda8
            @Override // java.util.function.BooleanSupplier
            public final boolean getAsBoolean() {
                return this.f$0.d();
            }
        });
    }

    public final void A() {
        b(4096);
    }

    public final void B() {
        b(128);
    }

    public final void C() {
        b(64);
    }

    public boolean b(AbstractC0657g abstractC0657g) {
        return (abstractC0657g.d() & this.a) == 0;
    }

    public final boolean c() {
        return a(64);
    }

    public final boolean d() {
        return a(65536);
    }

    public boolean e() {
        return a(16);
    }

    public final boolean equals(Object obj) {
        return (obj instanceof C3774o2) && this.a == ((C3774o2) obj).a;
    }

    public final boolean f() {
        return a(256);
    }

    public final boolean g() {
        return a(2);
    }

    public boolean h() {
        return a(4);
    }

    public final int hashCode() {
        return this.a;
    }

    public boolean i() {
        return a(1);
    }

    public final boolean j() {
        return a(8);
    }

    public final boolean k() {
        return a(2048);
    }

    public final boolean l() {
        return a(32);
    }

    public final boolean m() {
        return a(4096);
    }

    public final boolean n() {
        return a(128);
    }

    public final boolean o() {
        return a(64);
    }

    public final void p() {
        b(1024);
    }

    public final void q() {
        b(64);
    }

    public final void r() {
        b(65536);
    }

    public final void s() {
        b(16);
    }

    public final void t() {
        b(256);
    }

    public final String toString() {
        AbstractC1639am abstractC1639amA = a();
        StringBuilder sb = new StringBuilder();
        int i = 0;
        boolean z = false;
        while (true) {
            AbstractC1639am abstractC1639am = c;
            if (i >= abstractC1639am.size()) {
                return sb.toString();
            }
            if (((BooleanSupplier) abstractC1639amA.get(i)).getAsBoolean()) {
                if (z) {
                    sb.append(StringUtil.SPACE);
                } else {
                    z = true;
                }
                sb.append((String) abstractC1639am.get(i));
            }
            i++;
        }
    }

    public final void u() {
        b(2);
    }

    public final void v() {
        b(4);
    }

    public final void w() {
        b(1);
    }

    public final void x() {
        b(8);
    }

    public final void y() {
        b(2048);
    }

    public final void z() {
        b(32);
    }

    public boolean a(AbstractC0657g abstractC0657g) {
        int iD = abstractC0657g.d();
        int i = this.a;
        int i2 = b;
        int i3 = ~i2;
        return (i3 & (i & iD)) == (i & i3) && ((i & i2) == 0 || ((iD & i) & i2) != 0);
    }

    public final boolean b() {
        return a(1024);
    }

    private boolean a(int i) {
        return (i & this.a) != 0;
    }

    private void b(int i) {
        this.a = i | this.a;
    }
}
