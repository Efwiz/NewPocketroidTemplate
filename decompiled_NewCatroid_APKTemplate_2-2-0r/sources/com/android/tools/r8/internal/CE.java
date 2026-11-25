package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class CE {
    public abstract boolean a();

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a extends CE {
        static final /* synthetic */ boolean d = true;
        private boolean a = true;
        private boolean b;
        private boolean c;

        public a(com.android.tools.r8.utils.A1 a1) {
            this.b = a1.p1();
            this.c = a1.X0();
        }

        public final void a(TE te) {
            if (!d && !te.c()) {
                throw new AssertionError();
            }
            this.a = false;
        }

        public final a b() {
            this.b = false;
            return this;
        }

        public boolean c() {
            return this.a;
        }

        public final boolean d() {
            return this.b;
        }

        public final a e() {
            this.c = true;
            return this;
        }

        @Override // com.android.tools.r8.internal.CE
        public boolean a() {
            return this.c;
        }
    }
}
