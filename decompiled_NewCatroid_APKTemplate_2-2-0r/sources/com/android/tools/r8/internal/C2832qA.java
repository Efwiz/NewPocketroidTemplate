package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.shaking.InterfaceC3680a0;
import com.android.tools.r8.utils.C3888e1;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.qA, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2832qA implements InterfaceC3680a0 {
    private static final List g = AbstractC1639am.i();
    private static final List h = AbstractC1639am.i();
    private final int a;
    private final int b;
    private final String c;
    private final EnumC1364Sy d;
    private final List e;
    private final List f;

    private C2832qA(int i, int i2, String str, EnumC1364Sy enumC1364Sy, AbstractC1639am abstractC1639am, AbstractC1639am abstractC1639am2) {
        this.a = i;
        this.b = i2;
        this.c = str;
        this.d = enumC1364Sy;
        this.e = abstractC1639am;
        this.f = abstractC1639am2;
    }

    static AbstractC1639am a(List list, C0722p1 c0722p1, com.android.tools.r8.utils.O2 o2) {
        List listA;
        if (list.isEmpty()) {
            return (AbstractC1639am) g;
        }
        C1507Xl c1507XlG = AbstractC1639am.g();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            C1119Jy c1119Jy = (C1119Jy) it.next();
            int iB = c1119Jy.b();
            int iC = c1119Jy.c();
            String strD = c1119Jy.d();
            EnumC1364Sy enumC1364SyF = c1119Jy.f();
            List<C0942Dy> listE = c1119Jy.e();
            if (listE.isEmpty()) {
                listA = h;
            } else {
                C1507Xl c1507XlG2 = AbstractC1639am.g();
                Iterator<C0942Dy> it2 = listE.iterator();
                while (it2.hasNext()) {
                    c1507XlG2.a(C2759pA.a(c0722p1, o2, it2.next()));
                }
                listA = c1507XlG2.a();
            }
            c1507XlG.a(new C2832qA(iB, iC, strD, enumC1364SyF, (AbstractC1639am) listA, C2577mz.a(c0722p1, ((C1800cv) c1119Jy.a(com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.n.b)).b())));
        }
        return c1507XlG.a();
    }

    final boolean a(Consumer consumer, C0702m c0702m) {
        C1119Jy c1119Jy = (C1119Jy) AbstractC1745cA.a(consumer, new C1119Jy(this.a, this.c, this.b, this.d));
        return AbstractC1745cA.a(c0702m, this.f, ((C1800cv) c1119Jy.a(com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.n.b)).b(), new C2681oA$$ExternalSyntheticLambda2()) | AbstractC1745cA.a(c0702m, this.e, c1119Jy.e(), new Cz$$ExternalSyntheticLambda4());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Consumer a(C2759pA c2759pA) {
        Objects.requireNonNull(c2759pA);
        return new Iz$$ExternalSyntheticLambda0(c2759pA);
    }

    @Override // com.android.tools.r8.shaking.InterfaceC3680a0
    public final void a(com.android.tools.r8.graph.Q0 q0) {
        C3888e1.a(this.e, new Function() { // from class: com.android.tools.r8.internal.qA$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C2832qA.a((C2759pA) obj);
            }
        }, (Object) q0);
        C3888e1.a(this.f, new Function() { // from class: com.android.tools.r8.internal.qA$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C2832qA.a((C2577mz) obj);
            }
        }, (Object) q0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Consumer a(C2577mz c2577mz) {
        Objects.requireNonNull(c2577mz);
        return new C2682oA$$ExternalSyntheticLambda3(c2577mz);
    }
}
