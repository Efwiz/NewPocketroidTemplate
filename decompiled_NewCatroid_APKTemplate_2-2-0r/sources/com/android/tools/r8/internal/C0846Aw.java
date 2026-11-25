package com.android.tools.r8.internal;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Aw, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0846Aw {
    static final /* synthetic */ boolean e = true;
    private final EnumC3537zw a;
    private final AbstractC2496lv b;
    private final AbstractC2884qw c;
    private final AbstractC1171Lw d;

    /* JADX INFO: renamed from: com.android.tools.r8.internal.Aw$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a {
        private EnumC3537zw a;
        private AbstractC2496lv b;
        private AbstractC2884qw c;
        private AbstractC1171Lw d;

        private a() {
            this.a = null;
            this.b = AbstractC2496lv.a(C2424kx.a());
            this.c = AbstractC2884qw.a();
            this.d = AbstractC1171Lw.j();
        }

        public final a a(EnumC3537zw enumC3537zw) {
            this.a = enumC3537zw;
            return this;
        }

        public final a a(AbstractC2496lv abstractC2496lv) {
            this.b = abstractC2496lv;
            return this;
        }

        public a a(C2424kx c2424kx) {
            this.b = AbstractC2496lv.a(c2424kx);
            return this;
        }

        public final a a(AbstractC2884qw abstractC2884qw) {
            this.c = abstractC2884qw;
            return this;
        }

        public a a(AbstractC1171Lw abstractC1171Lw) {
            this.d = abstractC1171Lw;
            return this;
        }

        public C0846Aw a() {
            if (this.a == null) {
                this.a = this.d.i() ? EnumC3537zw.a : EnumC3537zw.b;
            }
            if (this.a == EnumC3537zw.a && !this.d.i()) {
                throw new C2883qv("Invalid kind ONLY_CLASS for item with member pattern: " + this.d);
            }
            if (this.a == EnumC3537zw.b && this.d.i()) {
                throw new C2883qv("Invalid kind ONLY_MEMBERS for item with no member pattern");
            }
            if (this.a == EnumC3537zw.c && this.d.i()) {
                throw new C2883qv("Invalid kind CLASS_AND_MEMBERS for item with no member pattern");
            }
            return new C0846Aw(this.a, this.b, this.c, this.d);
        }

        public final a a(C0846Aw c0846Aw) {
            this.a = c0846Aw.a;
            this.b = c0846Aw.b;
            this.c = c0846Aw.c;
            return a(c0846Aw.d);
        }
    }

    private C0846Aw(EnumC3537zw enumC3537zw, AbstractC2496lv abstractC2496lv, AbstractC2884qw abstractC2884qw, AbstractC1171Lw abstractC1171Lw) {
        boolean z = e;
        if (!z && enumC3537zw == null) {
            throw new AssertionError();
        }
        if (!z && abstractC2496lv == null) {
            throw new AssertionError();
        }
        if (!z && abstractC2884qw == null) {
            throw new AssertionError();
        }
        if (!z && abstractC1171Lw == null) {
            throw new AssertionError();
        }
        this.a = enumC3537zw;
        this.b = abstractC2496lv;
        this.c = abstractC2884qw;
        this.d = abstractC1171Lw;
    }

    public static C0846Aw a() {
        a aVarB = b();
        aVarB.a = EnumC3537zw.c;
        aVarB.b = AbstractC2496lv.a(C2424kx.a());
        aVarB.c = AbstractC2884qw.a();
        aVarB.d = AbstractC1171Lw.a();
        return aVarB.a();
    }

    public static a b() {
        return new a();
    }

    public final Collection c() {
        return this.b.c();
    }

    public final AbstractC2496lv d() {
        return this.b;
    }

    public final AbstractC2884qw e() {
        return this.c;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        C0846Aw c0846Aw = (C0846Aw) obj;
        return this.a.equals(c0846Aw.a) && this.b.equals(c0846Aw.b) && this.c.equals(c0846Aw.c) && this.d.equals(c0846Aw.d);
    }

    public final EnumC3537zw f() {
        return this.a;
    }

    public final AbstractC1171Lw g() {
        return this.d;
    }

    public final boolean h() {
        return this.a == EnumC3537zw.c;
    }

    public final int hashCode() {
        return Objects.hash(this.a, this.b, this.c, this.d);
    }

    public final boolean i() {
        return this.a == EnumC3537zw.a;
    }

    public final boolean j() {
        return this.a == EnumC3537zw.b;
    }

    public final String toString() {
        return "KeepClassPattern{kind=" + this.a + ", classReference=" + this.b + ", extendsPattern=" + this.c + ", memberPattern=" + this.d + "}";
    }

    public final boolean a(Predicate predicate) {
        if (this.a.equals(EnumC3537zw.c) && this.c.d()) {
            AbstractC1171Lw abstractC1171Lw = this.d;
            abstractC1171Lw.getClass();
            if ((abstractC1171Lw instanceof C1058Hw) && this.b.a(predicate)) {
                return true;
            }
        }
        return false;
    }
}
