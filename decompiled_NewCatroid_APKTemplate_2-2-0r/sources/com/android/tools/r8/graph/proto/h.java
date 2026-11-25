package com.android.tools.r8.graph.proto;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.AbstractC3292wV;
import java.util.Objects;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class h extends com.android.tools.r8.graph.proto.b {
    static final /* synthetic */ boolean d = true;
    private final AbstractC3292wV b;
    private final C0767v2 c;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static abstract class b<B extends b<B>> {
        AbstractC3292wV a;
        C0767v2 b;

        b() {
        }
    }

    h(AbstractC3292wV abstractC3292wV, C0767v2 c0767v2) {
        if (!d && c0767v2 == null) {
            throw new AssertionError();
        }
        this.b = abstractC3292wV;
        this.c = c0767v2;
    }

    public static a e() {
        return new a();
    }

    @Override // com.android.tools.r8.graph.proto.b
    public final h a() {
        return this;
    }

    @Override // com.android.tools.r8.graph.proto.b
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public h a(C0702m c0702m, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2) {
        AbstractC3292wV abstractC3292wVB = h() ? this.b.b(c0702m, abstractC1476Wj, abstractC1476Wj2) : null;
        C0767v2 c0767v2A = abstractC1476Wj.a(abstractC1476Wj2, this.c);
        return (abstractC3292wVB == this.b && c0767v2A == this.c) ? this : new h(abstractC3292wVB, c0767v2A);
    }

    @Override // com.android.tools.r8.graph.proto.b
    public final boolean c() {
        return true;
    }

    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        h hVar = (h) obj;
        return this.c == hVar.c && Objects.equals(this.b, hVar.b);
    }

    public final AbstractC3292wV f() {
        return this.b;
    }

    public C0767v2 g() {
        return this.c;
    }

    public boolean h() {
        return this.b != null;
    }

    public int hashCode() {
        return Objects.hash(this.b, this.c);
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a extends b<a> {
        public b a(AbstractC3292wV abstractC3292wV) {
            this.a = abstractC3292wV;
            return b();
        }

        final b b() {
            return this;
        }

        public b a(C0767v2 c0767v2) {
            this.b = c0767v2;
            return b();
        }

        public h a() {
            return new h(this.a, this.b);
        }
    }

    @Override // com.android.tools.r8.graph.proto.b
    public final com.android.tools.r8.graph.proto.b a(com.android.tools.r8.graph.proto.b bVar) {
        if (d) {
            return this;
        }
        throw new AssertionError("Once the argument is removed one cannot modify it any further.");
    }
}
