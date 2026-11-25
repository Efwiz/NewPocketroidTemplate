package com.android.tools.r8.internal;

import com.android.tools.r8.naming.C3634k;
import com.android.tools.r8.references.MethodReference;
import com.android.tools.r8.utils.C3979v2;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.wD, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3266wD {
    static final /* synthetic */ boolean d = true;
    private final MethodReference a;
    private List b;
    private final ArrayList c;

    private C3266wD(MethodReference methodReference) {
        this.b = null;
        this.c = new ArrayList();
        this.a = methodReference;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(Object obj) {
        throw new C3405y10();
    }

    public final void a() {
        if (this.b == null || this.c.isEmpty()) {
            boolean z = d;
            if (!z && this.b == null) {
                throw new AssertionError("Mapped outline positions is null");
            }
            if (!z) {
                throw new AssertionError("Mapped outline positions is empty");
            }
            return;
        }
        for (C3979v2 c3979v2 : this.c) {
            C3634k.b bVar = (C3634k.b) c3979v2.a();
            InterfaceC1050Ho interfaceC1050Ho = (InterfaceC1050Ho) c3979v2.b();
            final C0964Eo c0964Eo = new C0964Eo(0);
            interfaceC1050Ho.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.wD$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    this.f$0.a(c0964Eo, (Integer) obj, (Integer) obj2);
                }
            });
            bVar.a(new Consumer() { // from class: com.android.tools.r8.internal.wD$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C3266wD.a((com.android.tools.r8.naming.mappinginformation.e) obj);
                }
            }, PK.a(c0964Eo, this.a));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(AbstractC3401y abstractC3401y, Integer num, Integer num2) {
        int iA;
        int iIntValue = num.intValue();
        Iterator it = this.b.iterator();
        while (true) {
            if (!it.hasNext()) {
                iA = -1;
                break;
            }
            C3114uD c3114uD = (C3114uD) it.next();
            if (c3114uD.b().e() == iIntValue) {
                iA = c3114uD.a();
                break;
            }
        }
        if (iA != -1) {
            ((C0964Eo) abstractC3401y).b(iA, num2.intValue());
        }
    }
}
