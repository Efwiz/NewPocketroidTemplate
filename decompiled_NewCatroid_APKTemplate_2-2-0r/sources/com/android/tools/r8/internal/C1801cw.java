package com.android.tools.r8.internal;

import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.cw, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C1801cw extends AbstractC1116Jv {
    private final String a;
    private C0972Ew b = null;
    private final C1956ew c;
    private final C1197Mv d;

    C1801cw(String str) {
        this.a = str;
        this.c = new C1956ew(str);
        this.d = new C1197Mv(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C1463Vw c1463Vw) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C3319ww c3319ww) {
    }

    public final AbstractC1171Lw b() {
        C1956ew c1956ew = this.c;
        if (c1956ew.b != null) {
            if (c1956ew.c == null) {
                c1956ew.c = C1463Vw.k();
            }
            c1956ew.c.a(c1956ew.b.f());
        }
        C1463Vw.a aVar = c1956ew.c;
        C1463Vw c1463VwA = aVar != null ? aVar.a() : null;
        C1197Mv c1197Mv = this.d;
        if (c1197Mv.b != null) {
            if (c1197Mv.c == null) {
                c1197Mv.c = C3319ww.k();
            }
            c1197Mv.c.a(c1197Mv.b.f());
        }
        C3319ww.a aVar2 = c1197Mv.c;
        C3319ww c3319wwA = aVar2 != null ? aVar2.a() : null;
        if (this.b != null) {
            if (c1463VwA == null && c3319wwA == null) {
                return new C1088Iw().a(this.b.f()).a();
            }
            throw new C2883qv("Cannot define common member access as well as field or method pattern");
        }
        if (c1463VwA != null && c3319wwA != null) {
            throw new C2883qv("Cannot define both a field and a method pattern");
        }
        if (c1463VwA != null) {
            return c1463VwA;
        }
        return c3319wwA != null ? c3319wwA : AbstractC1171Lw.j();
    }

    final AbstractC2509m2 a(Consumer consumer, String str) {
        if (str.equals("memberAccess")) {
            int i = C1027Gw.f;
            C0972Ew c0972Ew = new C0972Ew();
            this.b = c0972Ew;
            return new C1724bw(this.a, c0972Ew);
        }
        AbstractC2509m2 abstractC2509m2A = this.c.a(new Consumer() { // from class: com.android.tools.r8.internal.cw$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1801cw.a((C1463Vw) obj);
            }
        }, str);
        if (abstractC2509m2A != null) {
            return abstractC2509m2A;
        }
        C1197Mv c1197Mv = this.d;
        new Consumer() { // from class: com.android.tools.r8.internal.cw$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1801cw.a((C3319ww) obj);
            }
        };
        c1197Mv.getClass();
        if (!str.equals("fieldAccess")) {
            return null;
        }
        C2954rw c2954rwI = C3023sw.i();
        c1197Mv.b = c2954rwI;
        return new C1169Lv(c1197Mv.a, c2954rwI);
    }
}
