package com.android.tools.r8.internal;

import java.util.Objects;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ww, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3319ww extends AbstractC1171Lw {
    static final /* synthetic */ boolean d = true;
    private final C3023sw a;
    private final AbstractC3244vw b;
    private final AbstractC3466yw c;

    /* JADX INFO: renamed from: com.android.tools.r8.internal.ww$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a {
        private C3023sw a;
        private AbstractC3244vw b;
        private AbstractC3466yw c;

        private a() {
            this.a = C3023sw.h();
            this.b = AbstractC3244vw.a();
            this.c = AbstractC3466yw.a();
        }

        public final a a(C3023sw c3023sw) {
            this.a = c3023sw;
            return this;
        }

        public a a(AbstractC3244vw abstractC3244vw) {
            this.b = abstractC3244vw;
            return this;
        }

        public final a a(AbstractC3466yw abstractC3466yw) {
            this.c = abstractC3466yw;
            return this;
        }

        public C3319ww a() {
            return new C3319ww(this.a, this.b, this.c);
        }
    }

    private C3319ww(C3023sw c3023sw, AbstractC3244vw abstractC3244vw, AbstractC3466yw abstractC3466yw) {
        boolean z = d;
        if (!z && c3023sw == null) {
            throw new AssertionError();
        }
        if (!z && abstractC3244vw == null) {
            throw new AssertionError();
        }
        if (!z && abstractC3466yw == null) {
            throw new AssertionError();
        }
        this.a = c3023sw;
        this.b = abstractC3244vw;
        this.c = abstractC3466yw;
    }

    public static a k() {
        return new a();
    }

    @Override // com.android.tools.r8.internal.AbstractC1171Lw
    public final C3319ww b() {
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
        if (obj == null || C3319ww.class != obj.getClass()) {
            return false;
        }
        C3319ww c3319ww = (C3319ww) obj;
        return this.a.equals(c3319ww.a) && this.b.equals(c3319ww.b) && this.c.equals(c3319ww.c);
    }

    public final int hashCode() {
        return Objects.hash(this.a, this.b, this.c);
    }

    public final C3023sw l() {
        return this.a;
    }

    public final AbstractC3244vw m() {
        return this.b;
    }

    public final AbstractC3466yw n() {
        return this.c;
    }

    public final String toString() {
        return "KeepFieldPattern{access=" + this.a + ", name=" + this.b + ", type=" + this.c + "}";
    }
}
