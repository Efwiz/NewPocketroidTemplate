package com.android.tools.r8.internal;

import java.util.Objects;

/* JADX INFO: renamed from: com.android.tools.r8.internal.px, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C2814px {
    static final /* synthetic */ boolean c = true;
    private final AbstractC0940Dw a;
    private final C1651ax b;

    /* JADX INFO: renamed from: com.android.tools.r8.internal.px$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a {
        private AbstractC0940Dw a;
        private C1651ax b;

        private a() {
            this.b = C1651ax.d();
        }

        public final a a(AbstractC0940Dw abstractC0940Dw) {
            this.a = abstractC0940Dw;
            return this;
        }

        public a a(C0846Aw c0846Aw) {
            this.a = AbstractC0940Dw.a(c0846Aw);
            return this;
        }

        public a a(C1651ax c1651ax) {
            this.b = c1651ax;
            return this;
        }

        public C2814px a() {
            if (this.a != null) {
                return new C2814px(this.a, this.b);
            }
            throw new C2883qv("Target must define an item pattern");
        }
    }

    private C2814px(AbstractC0940Dw abstractC0940Dw, C1651ax c1651ax) {
        boolean z = c;
        if (!z && abstractC0940Dw == null) {
            throw new AssertionError();
        }
        if (!z && c1651ax == null) {
            throw new AssertionError();
        }
        this.a = abstractC0940Dw;
        this.b = c1651ax;
    }

    public static a a() {
        return new a();
    }

    public final AbstractC0940Dw b() {
        return this.a;
    }

    public final C1651ax c() {
        return this.b;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        C2814px c2814px = (C2814px) obj;
        return this.a.equals(c2814px.a) && this.b.equals(c2814px.b);
    }

    public final int hashCode() {
        return Objects.hash(this.a, this.b);
    }

    public final String toString() {
        return "KeepTarget{item=" + this.a + ", options=" + this.b + "}";
    }
}
