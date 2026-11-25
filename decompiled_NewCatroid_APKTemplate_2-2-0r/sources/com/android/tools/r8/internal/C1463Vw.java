package com.android.tools.r8.internal;

import java.util.Objects;
import net.bytebuddy.description.method.MethodDescription;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Vw, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1463Vw extends AbstractC1171Lw {
    static final /* synthetic */ boolean e = true;
    private final C1225Nw a;
    private final AbstractC1309Qw b;
    private final AbstractC1545Yw c;
    private final AbstractC1437Uw d;

    /* JADX INFO: renamed from: com.android.tools.r8.internal.Vw$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a {
        private C1225Nw a;
        private AbstractC1309Qw b;
        private AbstractC1545Yw c;
        private AbstractC1437Uw d;

        private a() {
            this.a = C1225Nw.h();
            this.b = AbstractC1309Qw.a();
            this.c = AbstractC1545Yw.a();
            this.d = AbstractC1437Uw.a();
        }

        public final a a(C1225Nw c1225Nw) {
            this.a = c1225Nw;
            return this;
        }

        public a b() {
            this.c = AbstractC1545Yw.e();
            return this;
        }

        public a a(AbstractC1309Qw abstractC1309Qw) {
            this.b = abstractC1309Qw;
            return this;
        }

        public final a a(AbstractC1545Yw abstractC1545Yw) {
            this.c = abstractC1545Yw;
            return this;
        }

        public a a(AbstractC1437Uw abstractC1437Uw) {
            this.d = abstractC1437Uw;
            return this;
        }

        public C1463Vw a() {
            AbstractC1545Yw abstractC1545YwE = this.c;
            C1282Pw c1282PwB = this.b.b();
            if (c1282PwB != null && (c1282PwB.e().equals("<init>") || c1282PwB.e().equals(MethodDescription.TYPE_INITIALIZER_INTERNAL_NAME))) {
                if (!this.c.c()) {
                    AbstractC1545Yw abstractC1545Yw = this.c;
                    abstractC1545Yw.getClass();
                    if (!(abstractC1545Yw instanceof C1518Xw)) {
                        throw new C2883qv("Method constructor pattern must match 'void' type.");
                    }
                }
                abstractC1545YwE = AbstractC1545Yw.e();
            }
            return new C1463Vw(this.a, this.b, abstractC1545YwE, this.d);
        }
    }

    private C1463Vw(C1225Nw c1225Nw, AbstractC1309Qw abstractC1309Qw, AbstractC1545Yw abstractC1545Yw, AbstractC1437Uw abstractC1437Uw) {
        boolean z = e;
        if (!z && c1225Nw == null) {
            throw new AssertionError();
        }
        if (!z && abstractC1309Qw == null) {
            throw new AssertionError();
        }
        if (!z && abstractC1545Yw == null) {
            throw new AssertionError();
        }
        if (!z && abstractC1437Uw == null) {
            throw new AssertionError();
        }
        this.a = c1225Nw;
        this.b = abstractC1309Qw;
        this.c = abstractC1545Yw;
        this.d = abstractC1437Uw;
    }

    public static a k() {
        return new a();
    }

    @Override // com.android.tools.r8.internal.AbstractC1171Lw
    public final C1463Vw c() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC1171Lw
    public final C1027Gw d() {
        return this.a;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || C1463Vw.class != obj.getClass()) {
            return false;
        }
        C1463Vw c1463Vw = (C1463Vw) obj;
        return this.a.equals(c1463Vw.a) && this.b.equals(c1463Vw.b) && this.c.equals(c1463Vw.c) && this.d.equals(c1463Vw.d);
    }

    public final int hashCode() {
        return Objects.hash(this.a, this.b, this.c, this.d);
    }

    public final C1225Nw l() {
        return this.a;
    }

    public final AbstractC1309Qw m() {
        return this.b;
    }

    public final AbstractC1437Uw n() {
        return this.d;
    }

    public final AbstractC1545Yw o() {
        return this.c;
    }

    public final String toString() {
        return "KeepMethodPattern{access=" + this.a + ", name=" + this.b + ", returnType=" + this.c + ", parameters=" + this.d + "}";
    }
}
