package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.zP, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3503zP {
    static final /* synthetic */ boolean g = true;
    private final int a;
    private final boolean b;
    private final boolean c;
    private final boolean d;
    private final boolean e;
    private final boolean f;

    C3503zP(int i, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        this.a = i;
        this.b = z;
        this.c = z2;
        this.d = z3;
        this.e = z4;
        this.f = z5;
        if (g) {
            return;
        }
        l();
    }

    public OP a() {
        return null;
    }

    public final int b() {
        return this.a;
    }

    public final boolean c() {
        return this.a == 17;
    }

    public final boolean d() {
        return this.a == 49;
    }

    public final boolean e() {
        return this.a == 50;
    }

    public final boolean f() {
        return this.a == 9;
    }

    public final boolean g() {
        return this.a == 27;
    }

    public boolean h() {
        return this instanceof OP;
    }

    public boolean i() {
        return (k() || e()) ? false : true;
    }

    public final boolean j() {
        return this.b;
    }

    public boolean k() {
        return this.a <= 17;
    }

    public void l() {
        if (!g && this.a >= 51) {
            throw new AssertionError();
        }
    }

    public final boolean m() {
        return this.d;
    }

    public final int n() {
        int i = this.a;
        if (this.b) {
            i |= 256;
        }
        if (this.c) {
            i |= 512;
        }
        if (this.d) {
            i |= 1024;
        }
        if (this.e) {
            i |= 2048;
        }
        return this.f ? i | 4096 : i;
    }

    public boolean a(boolean z) {
        if (k()) {
            return z || this.f;
        }
        return false;
    }

    public int a(boolean z, AP ap) {
        int i = this.a;
        if (i != 12) {
            if (i != 27) {
                if (i != 30 && i != 44) {
                    if (i != 49) {
                        if (i != 50) {
                            return 1;
                        }
                        return com.android.tools.r8.utils.M.a(this.e) + 2;
                    }
                }
            }
            return 2;
        }
        return com.android.tools.r8.utils.M.a(z) + 1;
    }
}
