package com.android.tools.r8.shaking;

import com.android.tools.r8.utils.AbstractC3874b3;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class Q2 {
    private final boolean a;
    private final boolean b;
    private final boolean c;
    private final boolean d;

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a {
        private boolean a;
        private boolean b;
        private boolean c;
        private boolean d;

        public a a(boolean z) {
            this.a = z;
            return this;
        }

        public final a b() {
            this.a = AbstractC3874b3.b("com.android.tools.r8.experimental.enablecheckenumunboxed");
            this.b = AbstractC3874b3.b("com.android.tools.r8.experimental.enableconvertchecknotnull");
            this.c = AbstractC3874b3.b("com.android.tools.r8.experimental.enablewhyareyounotinlining");
            this.d = AbstractC3874b3.b("com.android.tools.r8.allowTestProguardOptions");
            return this;
        }

        public a c(boolean z) {
            this.c = z;
            return this;
        }

        public a d(boolean z) {
            this.d = z;
            return this;
        }

        public Q2 a() {
            return new Q2(this.a, this.b, this.c, this.d);
        }

        public a b(boolean z) {
            this.b = z;
            return this;
        }
    }

    Q2(boolean z, boolean z2, boolean z3, boolean z4) {
        this.a = z;
        this.b = z2;
        this.c = z3;
        this.d = z4;
    }

    public static a a() {
        return new a();
    }

    public final boolean b() {
        return this.a;
    }

    public final boolean c() {
        return this.b;
    }

    public final boolean d() {
        return this.c;
    }

    public final boolean e() {
        return this.d;
    }
}
