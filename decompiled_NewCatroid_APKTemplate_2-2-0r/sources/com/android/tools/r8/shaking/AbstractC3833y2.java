package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.AbstractC2734ot;
import com.android.tools.r8.internal.C2769pI;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.y2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3833y2 {

    /* JADX INFO: renamed from: com.android.tools.r8.shaking.y2$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a {
        private final C2769pI a;

        private a() {
            this.a = new C2769pI();
        }

        public a a(boolean z, G3 g3) {
            this.a.a(g3, z);
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        AbstractC3833y2 a() {
            C3839z2IA c3839z2IA = null;
            Object[] objArr = 0;
            Object[] objArr2 = 0;
            if (this.a.a(true)) {
                return new C3838z2(this.a);
            }
            return this.a.size() == 1 ? new b((G3) AbstractC2734ot.b(this.a.keySet())) : new A2(this.a.keySet());
        }
    }

    /* JADX INFO: renamed from: com.android.tools.r8.shaking.y2$b */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    static class b extends AbstractC3833y2 {
        final G3 a;

        private b(G3 g3) {
            this.a = g3;
        }

        @Override // com.android.tools.r8.shaking.AbstractC3833y2
        public final void a(StringBuilder sb) {
            sb.append(this.a.toString());
        }

        @Override // com.android.tools.r8.shaking.AbstractC3833y2
        protected final Iterable c() {
            return this.a.d();
        }

        @Override // com.android.tools.r8.shaking.AbstractC3833y2
        public final int e() {
            return 1;
        }

        @Override // com.android.tools.r8.shaking.AbstractC3833y2
        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            return Objects.equals(this.a, ((b) obj).a);
        }

        @Override // com.android.tools.r8.shaking.AbstractC3833y2
        public final int hashCode() {
            return Objects.hash(this.a);
        }

        @Override // com.android.tools.r8.shaking.AbstractC3833y2
        public final List a() {
            C0767v2 c0767v2C = this.a.c();
            if (c0767v2C == null) {
                return null;
            }
            return Collections.singletonList(c0767v2C);
        }

        @Override // com.android.tools.r8.shaking.AbstractC3833y2
        public final boolean a(C0767v2 c0767v2) {
            return this.a.b(c0767v2);
        }

        @Override // com.android.tools.r8.shaking.AbstractC3833y2
        protected final AbstractC3833y2 a(C0722p1 c0722p1) {
            return new b(this.a.a(c0722p1));
        }
    }

    public static a b() {
        return new a();
    }

    protected AbstractC3833y2 a(C0722p1 c0722p1) {
        return this;
    }

    public abstract List<C0767v2> a();

    public abstract void a(StringBuilder sb);

    public abstract boolean a(C0767v2 c0767v2);

    protected Iterable<O3> c() {
        return new y2$$ExternalSyntheticLambda0();
    }

    public final boolean d() {
        return c().iterator().hasNext();
    }

    public abstract int e();

    public abstract boolean equals(Object obj);

    public abstract int hashCode();

    public String toString() {
        StringBuilder sb = new StringBuilder();
        a(sb);
        return sb.toString();
    }

    public static AbstractC3833y2 a(G3 g3) {
        return new b(g3);
    }

    static Iterable a(AbstractC3833y2 abstractC3833y2) {
        return abstractC3833y2 == null ? new y2$$ExternalSyntheticLambda0() : abstractC3833y2.c();
    }
}
