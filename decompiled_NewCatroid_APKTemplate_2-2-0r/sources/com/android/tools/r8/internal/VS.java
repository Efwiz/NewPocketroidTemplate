package com.android.tools.r8.internal;

import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class VS implements D4 {
    static final /* synthetic */ boolean d = true;
    private final VL a;
    private final VL b;
    private final GL c;

    public VS() {
        VL vl = new VL(new Predicate() { // from class: com.android.tools.r8.internal.VS$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((AbstractC2412ko) obj).H1();
            }
        });
        this.a = vl;
        VL vl2 = new VL(new Predicate() { // from class: com.android.tools.r8.internal.VS$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((AbstractC2412ko) obj).y1();
            }
        });
        this.b = vl2;
        this.c = GL.b(vl, vl2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C1343Sd c1343Sd, AbstractC2412ko abstractC2412ko) {
        return abstractC2412ko == c1343Sd;
    }

    @Override // com.android.tools.r8.internal.D4
    public final void a() {
    }

    @Override // com.android.tools.r8.internal.D4
    public final boolean a(C3486zB c3486zB) {
        VD vdA = this.c.a(c3486zB);
        if (vdA == null) {
            return false;
        }
        final C1343Sd c1343SdI = this.a.a(vdA).I();
        AbstractC2412ko abstractC2412koA = this.b.a(vdA);
        AbstractC1915eM abstractC1915eMY0 = c1343SdI.Y0();
        if (abstractC1915eMY0.equals(abstractC2412koA.Y0())) {
            c3486zB.s();
            return true;
        }
        c3486zB.next();
        AbstractC2412ko next = c3486zB.next();
        if (!d && next != abstractC2412koA) {
            throw new AssertionError();
        }
        boolean z = false;
        while (next.y1() && c3486zB.hasNext()) {
            AbstractC2412ko next2 = c3486zB.next();
            if (!next2.Y0().equals(next.Y0())) {
                break;
            }
            next.a(abstractC1915eMY0);
            z = true;
            next = next2;
        }
        c3486zB.c(new Predicate() { // from class: com.android.tools.r8.internal.VS$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return VS.a(c1343SdI, (AbstractC2412ko) obj);
            }
        });
        if (!z) {
            return false;
        }
        c3486zB.next();
        c3486zB.s();
        return true;
    }
}
