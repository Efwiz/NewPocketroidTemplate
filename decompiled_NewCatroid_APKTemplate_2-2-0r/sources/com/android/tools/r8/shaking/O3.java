package com.android.tools.r8.shaking;

import com.android.tools.r8.internal.C3405y10;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class O3 {

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    static class a extends O3 {
        b a;
        final int b;

        a(int i) {
            this.b = i;
        }

        @Override // com.android.tools.r8.shaking.O3
        final a a() {
            return this;
        }

        @Override // com.android.tools.r8.shaking.O3
        final void c() {
        }

        @Override // com.android.tools.r8.shaking.O3
        final boolean d() {
            return true;
        }

        @Override // com.android.tools.r8.shaking.O3
        final O3 f() {
            String str;
            b bVar = this.a;
            if (bVar != null) {
                synchronized (bVar) {
                    str = bVar.b;
                }
                if (str != null) {
                    a aVar = new a(this.b);
                    aVar.a(this.a.f());
                    return aVar;
                }
            }
            return this;
        }

        final String g() {
            String str;
            b bVar = this.a;
            if (bVar == null) {
                return null;
            }
            synchronized (bVar) {
                str = bVar.b;
            }
            return str;
        }

        public final String toString() {
            return "<" + this.b + ">";
        }

        void a(b bVar) {
            this.a = bVar;
        }

        @Override // com.android.tools.r8.shaking.O3
        final void a(String str) {
            throw new C3405y10("A back reference refers back to a previously matched wildcard.");
        }
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    static class b extends O3 {
        final String a;
        private String b = null;

        b(String str) {
            this.a = str;
        }

        @Override // com.android.tools.r8.shaking.O3
        final synchronized void a(String str) {
            this.b = str;
        }

        @Override // com.android.tools.r8.shaking.O3
        final b b() {
            return this;
        }

        @Override // com.android.tools.r8.shaking.O3
        final synchronized void c() {
            this.b = null;
        }

        @Override // com.android.tools.r8.shaking.O3
        final boolean e() {
            return true;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.android.tools.r8.shaking.O3
        /* JADX INFO: renamed from: g, reason: merged with bridge method [inline-methods] */
        public final b f() {
            if (this.b == null) {
                return this;
            }
            b bVar = new b(this.a);
            String str = this.b;
            synchronized (bVar) {
                bVar.b = str;
            }
            return bVar;
        }

        public final String toString() {
            return this.a;
        }
    }

    a a() {
        return null;
    }

    abstract void a(String str);

    b b() {
        return null;
    }

    abstract void c();

    boolean d() {
        return false;
    }

    boolean e() {
        return false;
    }

    abstract O3 f();
}
