package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ux, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1438Ux {

    /* JADX INFO: renamed from: com.android.tools.r8.internal.Ux$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static final class a extends AbstractC1438Ux {
        private final String a;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public a(String str) {
            super(0);
            AbstractC0936Ds.c(str, "name");
            this.a = str;
        }

        public final String a() {
            return this.a;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof a) && AbstractC0936Ds.a((Object) this.a, (Object) ((a) obj).a);
        }

        public final int hashCode() {
            return this.a.hashCode();
        }

        public final String toString() {
            return "TypeAlias(name=" + this.a + ')';
        }
    }

    /* JADX INFO: renamed from: com.android.tools.r8.internal.Ux$b */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static final class b extends AbstractC1438Ux {
        private final int a;

        public b(int i) {
            super(0);
            this.a = i;
        }

        public final int a() {
            return this.a;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof b) && this.a == ((b) obj).a;
        }

        public final int hashCode() {
            return Integer.hashCode(this.a);
        }

        public final String toString() {
            return "TypeParameter(id=" + this.a + ')';
        }
    }

    public /* synthetic */ AbstractC1438Ux(int i) {
        this();
    }

    private AbstractC1438Ux() {
    }
}
