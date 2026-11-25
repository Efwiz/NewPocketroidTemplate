package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.yv, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3465yv {
    static final /* synthetic */ boolean g = true;
    private final C2736ov a;
    private String b;
    private int c = 1;
    private final C2187hv d = C2264iv.a();
    private final AbstractC2190hx.a e = AbstractC2190hx.b();
    private final C2649nv.a f = C2649nv.a();

    private C3465yv(C2736ov c2736ov) {
        this.a = c2736ov;
        a();
    }

    public static C2736ov a(C2736ov c2736ov) {
        return C2812pv.b(new C3465yv(c2736ov).c());
    }

    private String b() {
        String str = this.b;
        int i = this.c;
        this.c = i + 1;
        return str + i;
    }

    private C2736ov c() {
        this.a.b().a(new BiConsumer() { // from class: com.android.tools.r8.internal.yv$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a((String) obj, (C0846Aw) obj2);
            }
        });
        this.a.e().a(new Consumer() { // from class: com.android.tools.r8.internal.yv$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C2572mv) obj);
            }
        });
        this.a.c().a(new Consumer() { // from class: com.android.tools.r8.internal.yv$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C2814px) obj);
            }
        });
        return C2736ov.a().a(this.a.d()).a(this.d.a()).a(this.e.a()).a(this.f.a()).a();
    }

    private void a() {
        final ArrayList<String> arrayList = new ArrayList();
        this.a.b().a(new BiConsumer() { // from class: com.android.tools.r8.internal.yv$$ExternalSyntheticLambda3
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C3465yv.a(arrayList, (String) obj, (C0846Aw) obj2);
            }
        });
        if (!arrayList.isEmpty()) {
            int iMax = 0;
            for (String str : arrayList) {
                int i = 0;
                while (i < str.length() && str.charAt(i) == 'X') {
                    i++;
                }
                iMax = Math.max(iMax, i);
            }
            StringBuilder sb = new StringBuilder();
            for (int i2 = 0; i2 <= iMax; i2++) {
                sb.append('X');
            }
            this.b = "SyntheticBinding" + ((Object) sb);
            return;
        }
        this.b = "SyntheticBinding";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(List list, String str, C0846Aw c0846Aw) {
        if (str.startsWith("SyntheticBinding")) {
            list.add(str.substring(16));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(String str, C0846Aw c0846Aw) {
        this.d.a(str, a(c0846Aw));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C2572mv c2572mv) {
        AbstractC2190hx.a aVar = this.e;
        C2572mv.a aVarA = C2572mv.a();
        AbstractC0940Dw abstractC0940DwB = c2572mv.b();
        if (!abstractC0940DwB.c()) {
            C0846Aw c0846AwA = a(abstractC0940DwB.b());
            String strB = b();
            this.d.a(strB, c0846AwA);
            abstractC0940DwB = AbstractC0940Dw.a(strB);
        }
        aVar.a(aVarA.a(abstractC0940DwB).a());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C2814px c2814px) {
        C2649nv.a aVar = this.f;
        C2814px.a aVarA = C2814px.a().a(c2814px.c());
        AbstractC0940Dw abstractC0940DwB = c2814px.b();
        if (!abstractC0940DwB.c()) {
            C0846Aw c0846AwA = a(abstractC0940DwB.b());
            String strB = b();
            this.d.a(strB, c0846AwA);
            abstractC0940DwB = AbstractC0940Dw.a(strB);
        }
        aVar.a(aVarA.a(abstractC0940DwB).a());
    }

    private C0846Aw a(C0846Aw c0846Aw) {
        if (c0846Aw.i()) {
            return c0846Aw;
        }
        AbstractC2496lv abstractC2496lvD = c0846Aw.d();
        if (!abstractC2496lvD.d()) {
            String strB = b();
            AbstractC2496lv abstractC2496lvA = AbstractC2496lv.a(strB);
            this.d.a(strB, C0846Aw.b().a(c0846Aw.d()).a(c0846Aw.e()).a());
            abstractC2496lvD = abstractC2496lvA;
        }
        if (!g && !c0846Aw.f().equals(EnumC3537zw.b) && !c0846Aw.f().equals(EnumC3537zw.c)) {
            throw new AssertionError();
        }
        return C0846Aw.b().a(c0846Aw.f()).a(abstractC2496lvD).a(c0846Aw.g()).a();
    }
}
