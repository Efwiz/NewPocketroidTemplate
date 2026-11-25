package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.shaking.InterfaceC3680a0;
import com.android.tools.r8.utils.C3888e1;
import com.android.tools.r8.utils.C3970u0;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Kz, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1148Kz implements InterfaceC3680a0 {
    private static final List e = AbstractC1639am.i();
    static final /* synthetic */ boolean f = true;
    private final String a;
    private final C2973sA b;
    private final List c;
    private final String d;

    private C1148Kz(String str, C2973sA c2973sA, AbstractC1639am abstractC1639am) {
        this.a = str;
        this.b = c2973sA;
        this.c = abstractC1639am;
        this.d = null;
    }

    public static C1148Kz a(com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j jVar, C0722p1 c0722p1) {
        if (jVar == null) {
            return null;
        }
        String strC = jVar.c();
        String strB = jVar.b();
        try {
            for (String str : C3970u0.e(strB)) {
                if (str.charAt(0) == 'L' && !C3970u0.z(str)) {
                    return new C1148Kz(jVar.c(), strB);
                }
            }
            String strU = C3970u0.u(strB);
            C2973sA c2973sAB = C2973sA.b(strU, c0722p1, strU);
            String[] strArrE = C3970u0.e(strB);
            if (strArrE.length == 0) {
                return new C1148Kz(strC, c2973sAB, (AbstractC1639am) e);
            }
            C1507Xl c1507XlG = AbstractC1639am.g();
            for (String str2 : strArrE) {
                c1507XlG.a(C2973sA.b(str2, c0722p1, str2));
            }
            return new C1148Kz(strC, c2973sAB, c1507XlG.a());
        } catch (C1023Gs unused) {
        }
    }

    public final String toString() {
        String str = this.d;
        if (str != null) {
            return this.a + "(" + str + Constant.AFTER_QUTO;
        }
        if (!f && this.b == null) {
            throw new AssertionError();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.a);
        sb.append("(");
        Iterator it = this.c.iterator();
        while (it.hasNext()) {
            sb.append(((C2973sA) it.next()).toString());
        }
        sb.append(Constant.AFTER_QUTO);
        sb.append(this.b.toString());
        return sb.toString();
    }

    private C1148Kz(String str, String str2) {
        this.a = str;
        this.d = str2;
        this.c = e;
        this.b = null;
    }

    final boolean a(Consumer consumer, C0702m c0702m) {
        return a(consumer, null, c0702m);
    }

    final boolean a(Consumer consumer, com.android.tools.r8.graph.W0 w0, C0702m c0702m) {
        String str = this.d;
        boolean zB = false;
        if (str != null) {
            consumer.accept(new com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j(this.a, str));
            return false;
        }
        if (!f && this.b == null) {
            throw new AssertionError();
        }
        String str2 = this.a;
        if (w0 != null) {
            String string = w0.getReference().f.toString();
            String string2 = c0702m.z().a(w0.getReference()).toString();
            if (!string.equals(string2)) {
                zB = true;
                str2 = string2;
            }
        }
        final StringBuilder sb = new StringBuilder();
        sb.append("(");
        String strB1 = c0702m.a().Z1.b1();
        Iterator it = this.c.iterator();
        while (it.hasNext()) {
            zB |= ((C2973sA) it.next()).b(new Consumer() { // from class: com.android.tools.r8.internal.Kz$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    sb.append((String) obj);
                }
            }, c0702m, strB1);
        }
        sb.append(Constant.AFTER_QUTO);
        boolean zB2 = this.b.b(new Consumer() { // from class: com.android.tools.r8.internal.Kz$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                sb.append((String) obj);
            }
        }, c0702m, strB1) | zB;
        consumer.accept(new com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j(str2, sb.toString()));
        return zB2;
    }

    @Override // com.android.tools.r8.shaking.InterfaceC3680a0
    public final void a(com.android.tools.r8.graph.Q0 q0) {
        if (this.d != null) {
            return;
        }
        if (!f && this.b == null) {
            throw new AssertionError();
        }
        this.b.a(q0);
        C3888e1.a(this.c, new Function() { // from class: com.android.tools.r8.internal.Kz$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C1148Kz.a((C2973sA) obj);
            }
        }, (Object) q0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Consumer a(C2973sA c2973sA) {
        Objects.requireNonNull(c2973sA);
        return new Kz$$ExternalSyntheticLambda1(c2973sA);
    }
}
