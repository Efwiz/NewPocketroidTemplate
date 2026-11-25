package com.android.tools.r8.graph.proto;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.AbstractC3292wV;
import java.util.Objects;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class l extends b {
    static final /* synthetic */ boolean f = true;
    private final C0767v2 b;
    private final C0767v2 c;
    private final C0767v2 d;
    private final AbstractC3292wV e;

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a {
        private C0767v2 a;
        private C0767v2 b;
        private C0767v2 c;
        private AbstractC3292wV d;

        public final a a(boolean z, Consumer consumer) {
            if (z) {
                consumer.accept(this);
            }
            return this;
        }

        public a b(C0767v2 c0767v2) {
            this.c = c0767v2;
            return this;
        }

        public a c(C0767v2 c0767v2) {
            this.b = c0767v2;
            return this;
        }

        public final a a(C0767v2 c0767v2) {
            this.a = c0767v2;
            return this;
        }

        public final a a(AbstractC3292wV abstractC3292wV) {
            this.d = abstractC3292wV;
            return this;
        }

        public l a() {
            return new l(this.b, this.c, this.a, this.d);
        }
    }

    private l(C0767v2 c0767v2, C0767v2 c0767v3, C0767v2 c0767v4, AbstractC3292wV abstractC3292wV) {
        this.b = c0767v4;
        this.c = c0767v2;
        this.d = c0767v3;
        this.e = abstractC3292wV;
    }

    public static a e() {
        return new a();
    }

    @Override // com.android.tools.r8.graph.proto.b
    public final l b() {
        return this;
    }

    public final boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        l lVar = (l) obj;
        return this.c == lVar.c && this.d == lVar.d && Objects.equals(this.e, lVar.e);
    }

    public final C0767v2 f() {
        return this.b;
    }

    public C0767v2 g() {
        return this.d;
    }

    public C0767v2 h() {
        return this.c;
    }

    public final int hashCode() {
        return Objects.hash(this.c, this.d, this.e);
    }

    public final AbstractC3292wV i() {
        return this.e;
    }

    final boolean j() {
        return this.d.Y0();
    }

    public final boolean k() {
        return this.b != null;
    }

    public final boolean l() {
        return this.e != null;
    }

    @Override // com.android.tools.r8.graph.proto.b
    public final b a(b bVar) {
        if (bVar.c()) {
            return bVar;
        }
        if (f || (bVar instanceof l)) {
            return a(bVar.b());
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.graph.proto.b
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public final l a(C0702m c0702m, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2) {
        C0767v2 c0767v2 = this.b;
        C0767v2 c0767v2A = c0767v2 != null ? abstractC1476Wj.a(abstractC1476Wj2, c0767v2) : null;
        C0767v2 c0767v2A2 = abstractC1476Wj.a(abstractC1476Wj2, this.d);
        AbstractC3292wV abstractC3292wVB = l() ? this.e.b(c0702m, abstractC1476Wj, abstractC1476Wj2) : null;
        return (c0767v2A == this.b && c0767v2A2 == this.d && abstractC3292wVB == this.e) ? this : new l(this.c, c0767v2A2, c0767v2A, abstractC3292wVB);
    }

    public final l a(l lVar) {
        boolean z = f;
        if (!z && g().Y0()) {
            throw new AssertionError();
        }
        if (z || g() == lVar.h()) {
            return new l(h(), lVar.g(), this.b, lVar.e);
        }
        throw new AssertionError();
    }
}
