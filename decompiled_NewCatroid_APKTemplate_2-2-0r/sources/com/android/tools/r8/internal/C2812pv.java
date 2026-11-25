package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.pv, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2812pv {
    HashMap a = new HashMap();
    HashMap b = new HashMap();

    private void a(final C2736ov c2736ov) {
        c2736ov.b().a(new BiConsumer() { // from class: com.android.tools.r8.internal.pv$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(c2736ov, (String) obj, (C0846Aw) obj2);
            }
        });
    }

    public static C2736ov b(C2736ov c2736ov) {
        C2812pv c2812pv = new C2812pv();
        c2812pv.a(c2736ov);
        return c2812pv.b.isEmpty() ? c2736ov : C2736ov.a().a(c2736ov.d()).a(c2812pv.a(c2736ov.b())).a(c2812pv.a(c2736ov.e())).a(c2812pv.a(c2736ov.c())).a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C2736ov c2736ov, String str, C0846Aw c0846Aw) {
        String str2;
        if (c0846Aw.i() && c0846Aw.d().b().f()) {
            List list = (List) this.a.computeIfAbsent(c0846Aw.d().b().b(), new Function() { // from class: com.android.tools.r8.internal.pv$$ExternalSyntheticLambda4
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return C2812pv.a((String) obj);
                }
            });
            Iterator it = list.iterator();
            do {
                if (!it.hasNext()) {
                    str2 = null;
                    break;
                }
                str2 = (String) it.next();
            } while (!c0846Aw.equals(c2736ov.b().a(str2).a()));
            if (str2 != null) {
                this.b.put(str, str2);
            } else {
                list.add(str);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ List a(String str) {
        return new ArrayList();
    }

    private C2264iv a(C2264iv c2264iv) {
        int i = C2264iv.d;
        final C2187hv c2187hv = new C2187hv();
        c2264iv.a(new BiConsumer() { // from class: com.android.tools.r8.internal.pv$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(c2187hv, (String) obj, (C0846Aw) obj2);
            }
        });
        return c2187hv.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C2187hv c2187hv, String str, C0846Aw c0846Aw) {
        if (this.b.containsKey(str)) {
            return;
        }
        String strA = c0846Aw.d().a();
        if (strA != null) {
            c0846Aw = C0846Aw.b().a(c0846Aw).a(AbstractC2496lv.a((String) this.b.getOrDefault(strA, strA))).a();
        }
        c2187hv.a(str, c0846Aw);
    }

    private AbstractC2190hx a(AbstractC2190hx abstractC2190hx) {
        if (abstractC2190hx.c()) {
            return abstractC2190hx;
        }
        final AbstractC2190hx.a aVarB = AbstractC2190hx.b();
        abstractC2190hx.a(new Consumer() { // from class: com.android.tools.r8.internal.pv$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(aVarB, (C2572mv) obj);
            }
        });
        return aVarB.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(AbstractC2190hx.a aVar, C2572mv c2572mv) {
        AbstractC0940Dw abstractC0940DwA;
        C2572mv.a aVarA = C2572mv.a();
        AbstractC0940Dw abstractC0940DwB = c2572mv.b();
        if (abstractC0940DwB.c()) {
            String strA = abstractC0940DwB.a();
            abstractC0940DwA = AbstractC0940Dw.a((String) this.b.getOrDefault(strA, strA));
        } else {
            C0846Aw c0846AwB = abstractC0940DwB.b();
            String strA2 = c0846AwB.d().a();
            if (strA2 != null) {
                c0846AwB = C0846Aw.b().a(c0846AwB).a(AbstractC2496lv.a((String) this.b.getOrDefault(strA2, strA2))).a();
            }
            abstractC0940DwA = AbstractC0940Dw.a(c0846AwB);
        }
        aVar.a(aVarA.a(abstractC0940DwA).a());
    }

    private C2649nv a(C2649nv c2649nv) {
        final C2649nv.a aVarA = C2649nv.a();
        c2649nv.a(new Consumer() { // from class: com.android.tools.r8.internal.pv$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(aVarA, (C2814px) obj);
            }
        });
        return aVarA.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C2649nv.a aVar, C2814px c2814px) {
        AbstractC0940Dw abstractC0940DwA;
        C2814px.a aVarA = C2814px.a().a(c2814px.c());
        AbstractC0940Dw abstractC0940DwB = c2814px.b();
        if (abstractC0940DwB.c()) {
            String strA = abstractC0940DwB.a();
            abstractC0940DwA = AbstractC0940Dw.a((String) this.b.getOrDefault(strA, strA));
        } else {
            C0846Aw c0846AwB = abstractC0940DwB.b();
            String strA2 = c0846AwB.d().a();
            if (strA2 != null) {
                c0846AwB = C0846Aw.b().a(c0846AwB).a(AbstractC2496lv.a((String) this.b.getOrDefault(strA2, strA2))).a();
            }
            abstractC0940DwA = AbstractC0940Dw.a(c0846AwB);
        }
        aVar.a(aVarA.a(abstractC0940DwA).a());
    }
}
