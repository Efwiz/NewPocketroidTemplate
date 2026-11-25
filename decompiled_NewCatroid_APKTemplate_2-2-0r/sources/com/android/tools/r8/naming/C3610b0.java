package com.android.tools.r8.naming;

import com.android.tools.r8.graph.AbstractC0594a1;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.J3;
import com.android.tools.r8.graph.Y0;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.C3970u0;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/* JADX INFO: renamed from: com.android.tools.r8.naming.b0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
class C3610b0 extends AbstractC3647n0 {
    static final /* synthetic */ boolean g = true;
    final C0702m d;
    private final Map e;
    private final IdentityHashMap f;

    C3610b0(C0702m c0702m, C3614d c3614d, T t, C3666v c3666v) {
        super(c0702m.a());
        IdentityHashMap identityHashMap = new IdentityHashMap();
        this.f = identityHashMap;
        this.d = c0702m;
        this.e = c3614d.a;
        identityHashMap.putAll(c3614d.b);
        identityHashMap.putAll(t.a);
        identityHashMap.putAll(c3666v.a);
    }

    @Override // com.android.tools.r8.naming.AbstractC3649o0
    public String a(String str) {
        return (String) this.e.getOrDefault(str, str);
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder();
        this.f.forEach(new BiConsumer() { // from class: com.android.tools.r8.naming.b0$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C3610b0.a(sb, (AbstractC0594a1) obj, (C0759u2) obj2);
            }
        });
        return sb.toString();
    }

    @Override // com.android.tools.r8.naming.AbstractC3649o0
    protected C0759u2 a(C0767v2 c0767v2) {
        return (C0759u2) this.f.getOrDefault(c0767v2, c0767v2.e);
    }

    @Override // com.android.tools.r8.naming.AbstractC3649o0
    public C0759u2 a(J3 j3, A1 a1) {
        if (j3.c() == null) {
            return null;
        }
        C0767v2 c0767v2B = j3.b();
        String strA = C3970u0.a(c0767v2B.e.toString());
        String strA2 = C3970u0.a(c(c0767v2B).toString());
        if (strA.equals(strA2)) {
            return j3.c();
        }
        String strA3 = C3970u0.a(j3.d(), c0767v2B, j3.c());
        if (strA3 == null) {
            strA3 = String.valueOf('$');
        }
        int iLastIndexOf = strA2.lastIndexOf(strA3);
        if (iLastIndexOf < 0) {
            if (!g && a1.r1() && !a1.D0().x()) {
                throw new AssertionError(c0767v2B + " -> " + strA2);
            }
            return a1.a.c(C3970u0.w(c(c0767v2B).toString()));
        }
        return a1.a.c(strA2.substring(strA3.length() + iLastIndexOf));
    }

    @Override // com.android.tools.r8.naming.AbstractC3649o0
    public C0759u2 a(C0684j2 c0684j2) {
        return (C0759u2) this.f.getOrDefault(c0684j2, c0684j2.C0());
    }

    @Override // com.android.tools.r8.naming.AbstractC3649o0
    public C0759u2 a(Y0 y0) {
        return (C0759u2) this.f.getOrDefault(y0, y0.C0());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(StringBuilder sb, AbstractC0594a1 abstractC0594a1, C0759u2 c0759u2) {
        if (abstractC0594a1 instanceof C0767v2) {
            sb.append("[c] ");
        } else if (abstractC0594a1 instanceof C0684j2) {
            sb.append("[m] ");
        } else if (abstractC0594a1 instanceof Y0) {
            sb.append("[f] ");
        }
        sb.append(abstractC0594a1.r0());
        sb.append(" -> ");
        sb.append(c0759u2.r0());
        sb.append('\n');
    }
}
