package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0753u;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.position.Position;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ii, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1071Ii {
    static final /* synthetic */ boolean k = true;
    private final C0722p1 a;
    private C0684j2 b = null;
    private C0684j2 c = null;
    private boolean d = false;
    private boolean e = false;
    private EnumC1044Hi f = null;
    private Boolean g = null;
    private boolean h = false;
    private boolean i = false;
    private C0680j j = null;

    private C1071Ii(C0722p1 c0722p1) {
        this.a = c0722p1;
    }

    public static C1071Ii a(C0722p1 c0722p1) {
        return new C1071Ii(c0722p1);
    }

    public final C1071Ii b(C0684j2 c0684j2) {
        this.b = c0684j2;
        this.e = false;
        return this;
    }

    public final C1071Ii c(C0684j2 c0684j2) {
        this.b = c0684j2;
        this.e = false;
        this.d = true;
        return this;
    }

    public final C1071Ii d(C0684j2 c0684j2) {
        this.b = c0684j2;
        this.e = true;
        return this;
    }

    public final C1071Ii a(boolean z, Consumer consumer, Consumer consumer2) {
        if (z) {
            consumer.accept(this);
        } else {
            consumer2.accept(this);
        }
        return this;
    }

    public final C1071Ii b(C0684j2 c0684j2, boolean z) {
        this.c = c0684j2;
        this.f = EnumC1044Hi.a;
        this.g = Boolean.valueOf(z);
        return this;
    }

    public final C1071Ii d(C0684j2 c0684j2, boolean z) {
        this.c = c0684j2;
        this.f = z ? EnumC1044Hi.c : EnumC1044Hi.b;
        this.g = Boolean.valueOf(z);
        return this;
    }

    public final void c(C0684j2 c0684j2, boolean z) {
        this.c = c0684j2;
        this.f = EnumC1044Hi.d;
        this.g = Boolean.valueOf(z);
    }

    public final C1071Ii a(C0684j2 c0684j2, boolean z) {
        this.c = c0684j2;
        this.f = EnumC1044Hi.d;
        this.g = Boolean.valueOf(z);
        return this;
    }

    private int b() {
        int iOrdinal = this.f.ordinal();
        if (iOrdinal == 0) {
            return 184;
        }
        if (iOrdinal == 1) {
            return 182;
        }
        if (iOrdinal == 2) {
            return 185;
        }
        if (iOrdinal == 3) {
            return 183;
        }
        throw new C3405y10("Unexpected invoke type: " + this.f);
    }

    public final C1071Ii c() {
        this.h = true;
        return this;
    }

    public final C1071Ii a(C0680j c0680j) {
        this.j = c0680j;
        return this;
    }

    public final C1071Ii a(C0684j2 c0684j2) {
        if (!k && !c0684j2.b(this.a)) {
            throw new AssertionError();
        }
        this.c = c0684j2;
        this.i = true;
        this.f = EnumC1044Hi.d;
        this.g = Boolean.FALSE;
        return this;
    }

    public final C0753u a() {
        int iMax;
        int i;
        boolean z = k;
        if (!z) {
            if (!z && this.b == null) {
                throw new AssertionError();
            }
            if (!z && this.c == null) {
                throw new AssertionError();
            }
            if (!z && this.f == null) {
                throw new AssertionError();
            }
            if (!z && this.g == null) {
                throw new AssertionError();
            }
            if (!z) {
                if ((this.b.G0().size() + (!this.e ? 1 : 0)) - com.android.tools.r8.utils.M.a(this.d) != this.c.G0().size() + ((this.f == EnumC1044Hi.a || this.i) ? 0 : 1)) {
                    throw new AssertionError();
                }
            }
            if (this.i) {
                if (!z && !this.e) {
                    throw new AssertionError();
                }
                if (!z && this.b.I0().Y0()) {
                    throw new AssertionError();
                }
                if (!z && !this.c.I0().Y0()) {
                    throw new AssertionError();
                }
                if (!z && this.f != EnumC1044Hi.d) {
                    throw new AssertionError();
                }
            } else if (this.h && !this.b.I0().Y0() && !this.c.I0().Y0()) {
                if (!z && EnumC1812d20.a(this.b.I0()) != EnumC1812d20.a(this.c.I0())) {
                    throw new AssertionError();
                }
            } else if (!z && this.b.I0() != this.c.I0()) {
                throw new AssertionError();
            }
        }
        C1507Xl c1507XlG = AbstractC1639am.g();
        if (this.i) {
            if (!z && !this.e) {
                throw new AssertionError();
            }
            if (!z && this.f != EnumC1044Hi.d) {
                throw new AssertionError();
            }
            c1507XlG.a(new V7(null, this.c.B0()));
            c1507XlG.a(new D8(D8.a.Dup));
            iMax = 2;
            i = 0;
        } else {
            if (this.e) {
                iMax = 0;
            } else {
                c1507XlG.a(new O7(EnumC1812d20.OBJECT, 0));
                a(-1, this.b.e, c1507XlG);
                iMax = 1;
            }
            i = iMax;
        }
        C0767v2[] c0767v2Arr = this.b.G0().a;
        int iC = i;
        for (int i2 = 0; i2 < c0767v2Arr.length; i2++) {
            C0767v2 c0767v2 = c0767v2Arr[i2];
            EnumC1812d20 enumC1812d20A = EnumC1812d20.a(c0767v2);
            if (!this.d || i2 != c0767v2Arr.length - 1) {
                c1507XlG.a(new O7(enumC1812d20A, iC));
                a(i2, c0767v2, c1507XlG);
            }
            iMax += enumC1812d20A.c();
            iC += enumC1812d20A.c();
        }
        c1507XlG.a(new I7(b(), this.c, this.g.booleanValue()));
        if (!this.c.I0().Y0()) {
            iMax = Math.max(iMax, EnumC1812d20.a(this.c.I0()).c());
        }
        int i3 = iMax;
        if (this.b.I0().Y0()) {
            if (!k && this.i) {
                throw new AssertionError();
            }
            c1507XlG.a(new C3186v8());
        } else {
            if (!this.i && this.b.I0() != this.c.I0()) {
                if (!k && !this.h) {
                    throw new AssertionError();
                }
                if (this.b.I0() != this.a.Z1) {
                    c1507XlG.a(new M6(this.b.I0(), false));
                }
            }
            if (!k && this.b.I0().Y0()) {
                throw new AssertionError();
            }
            c1507XlG.a(new C3109u8(EnumC1812d20.a(this.b.I0())));
        }
        return new C0753u(this.b.e, i3, iC, c1507XlG.a(), AbstractC1639am.i(), AbstractC1639am.i(), Position.UNKNOWN, C2049g6.a());
    }

    private void a(int i, C0767v2 c0767v2, C1507Xl c1507Xl) {
        C0767v2 c0767v3;
        if (this.j == null) {
            return;
        }
        boolean z = this.e;
        if (z != (this.f == EnumC1044Hi.a)) {
            i += z ? -1 : 1;
        }
        if (i == -1) {
            c0767v3 = this.c.e;
        } else {
            c0767v3 = this.c.G0().a[i];
        }
        if (c0767v2 == c0767v3 || c0767v3 == this.j.a().Z1) {
            return;
        }
        if (!k && !this.j.c(c0767v3, c0767v2)) {
            throw new AssertionError();
        }
        c1507Xl.a(new M6(c0767v3, false));
    }
}
