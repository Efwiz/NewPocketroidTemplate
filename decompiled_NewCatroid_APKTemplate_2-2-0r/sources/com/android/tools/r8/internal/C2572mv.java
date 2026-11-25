package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.mv, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2572mv {
    static final /* synthetic */ boolean b = true;
    private final AbstractC0940Dw a;

    /* JADX INFO: renamed from: com.android.tools.r8.internal.mv$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a {
        private AbstractC0940Dw a;

        private a() {
        }

        public final a a(AbstractC0940Dw abstractC0940Dw) {
            this.a = abstractC0940Dw;
            return this;
        }

        public a a(C0846Aw c0846Aw) {
            this.a = AbstractC0940Dw.a(c0846Aw);
            return this;
        }

        public C2572mv a() {
            return new C2572mv(this.a);
        }
    }

    private C2572mv(AbstractC0940Dw abstractC0940Dw) {
        if (!b && abstractC0940Dw == null) {
            throw new AssertionError();
        }
        this.a = abstractC0940Dw;
    }

    public static a a() {
        return new a();
    }

    public final AbstractC0940Dw b() {
        return this.a;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || C2572mv.class != obj.getClass()) {
            return false;
        }
        return this.a.equals(((C2572mv) obj).a);
    }

    public final int hashCode() {
        return this.a.hashCode();
    }

    public final String toString() {
        return this.a.toString();
    }
}
