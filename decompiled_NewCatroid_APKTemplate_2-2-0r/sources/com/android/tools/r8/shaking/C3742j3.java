package com.android.tools.r8.shaking;

import java.util.Objects;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.j3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3742j3 {
    public final boolean a;
    public final boolean b;
    public final boolean c;
    public final boolean d;
    public final boolean e;
    public final boolean f;
    public final boolean g;

    /* JADX INFO: renamed from: com.android.tools.r8.shaking.j3$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a {
        private boolean a;
        private boolean b;
        private boolean c;
        private boolean d;
        private boolean e;
        private boolean f;
        private boolean g;

        private a() {
            this.a = false;
            this.b = false;
            this.c = false;
            this.d = false;
            this.e = false;
            this.f = false;
            this.g = false;
        }

        final C3742j3 a() {
            return new C3742j3(this.a, this.b, this.c, this.d, this.e, this.f, this.g);
        }

        public final void b() {
            this.a = true;
        }

        public final void c() {
            this.b = true;
        }

        public final void d() {
            this.f = true;
            this.c = true;
        }

        public final void e() {
            this.e = true;
        }

        public final void f() {
            this.c = true;
        }

        public final void g() {
            this.d = true;
        }

        public final void h() {
            this.g = true;
        }
    }

    private C3742j3(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7) {
        this.a = z;
        this.b = z2;
        this.c = z3;
        this.d = z4;
        this.e = z5;
        this.f = z6;
        this.g = z7;
    }

    public static a a() {
        return new a();
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof C3742j3)) {
            return false;
        }
        C3742j3 c3742j3 = (C3742j3) obj;
        return this.a == c3742j3.a && this.b == c3742j3.b && this.c == c3742j3.c && this.d == c3742j3.d && this.e == c3742j3.e && this.f == c3742j3.f && this.g == c3742j3.g;
    }

    public final int hashCode() {
        return Objects.hash(Boolean.valueOf(this.a), Boolean.valueOf(this.b), Boolean.valueOf(this.c), Boolean.valueOf(this.d), Boolean.valueOf(this.e), Boolean.valueOf(this.f), Boolean.valueOf(this.g));
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        a(sb, this.a, "allowaccessmodification");
        a(sb, this.b, "allowannotationremoval");
        a(sb, this.c, "allowrepackaging");
        a(sb, this.f, "allowobfuscation");
        a(sb, this.d, "allowshrinking");
        a(sb, this.e, "allowoptimization");
        a(sb, this.g, "includedescriptorclasses");
        return sb.toString();
    }

    private static void a(StringBuilder sb, boolean z, String str) {
        if (z) {
            if (sb.length() != 0) {
                sb.append(',');
            }
            sb.append(str);
        }
    }
}
