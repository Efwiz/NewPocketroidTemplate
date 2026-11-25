package com.android.tools.r8.internal;

import java.util.Objects;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ov, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2736ov {
    static final /* synthetic */ boolean e = true;
    private final C3395xv a;
    private final C2264iv b;
    private final AbstractC2190hx c;
    private final C2649nv d;

    /* JADX INFO: renamed from: com.android.tools.r8.internal.ov$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a {
        private C3395xv a;
        private C2264iv b;
        private AbstractC2190hx c;
        private C2649nv d;

        private a() {
            this.a = C3395xv.f();
            this.b = C2264iv.b();
            this.c = AbstractC2190hx.a();
        }

        public final a a(C3395xv c3395xv) {
            this.a = c3395xv;
            return this;
        }

        public final a a(C2264iv c2264iv) {
            this.b = c2264iv;
            return this;
        }

        public a a(AbstractC2190hx abstractC2190hx) {
            this.c = abstractC2190hx;
            return this;
        }

        public a a(C2649nv c2649nv) {
            this.d = c2649nv;
            return this;
        }

        public C2736ov a() {
            if (!this.d.b()) {
                return new C2736ov(this.a, this.b, this.c, this.d);
            }
            throw new C2883qv("KeepEdge must have non-empty set of consequences.");
        }
    }

    private C2736ov(C3395xv c3395xv, C2264iv c2264iv, AbstractC2190hx abstractC2190hx, C2649nv c2649nv) {
        boolean z = e;
        if (!z && c3395xv == null) {
            throw new AssertionError();
        }
        if (!z && c2264iv == null) {
            throw new AssertionError();
        }
        if (!z && abstractC2190hx == null) {
            throw new AssertionError();
        }
        if (!z && c2649nv == null) {
            throw new AssertionError();
        }
        this.a = c3395xv;
        this.b = c2264iv;
        this.c = abstractC2190hx;
        this.d = c2649nv;
    }

    public static a a() {
        return new a();
    }

    public final C2264iv b() {
        return this.b;
    }

    public final C2649nv c() {
        return this.d;
    }

    public final C3395xv d() {
        return this.a;
    }

    public final AbstractC2190hx e() {
        return this.c;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || C2736ov.class != obj.getClass()) {
            return false;
        }
        C2736ov c2736ov = (C2736ov) obj;
        return this.c.equals(c2736ov.c) && this.d.equals(c2736ov.d);
    }

    public final int hashCode() {
        return Objects.hash(this.c, this.d);
    }

    public final String toString() {
        return "KeepEdge{preconditions=" + this.c + ", consequences=" + this.d + "}";
    }
}
