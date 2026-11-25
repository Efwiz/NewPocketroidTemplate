package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.naming.AbstractC3661s0;
import com.android.tools.r8.naming.C3605b;
import com.android.tools.r8.naming.C3651p0;
import com.android.tools.r8.naming.C3658q0;
import com.android.tools.r8.position.Position;
import com.android.tools.r8.utils.AbstractC3972u2;
import com.android.tools.r8.utils.C3932m1;
import com.android.tools.r8.utils.C3970u0;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.xD, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3341xD {
    static final /* synthetic */ boolean h = true;
    private final AbstractC3972u2 a;
    private final C0702m b;
    private final C3605b.a c;
    private final IdentityHashMap d = new IdentityHashMap();
    private final IdentityHashMap e = new IdentityHashMap();
    private final C3651p0 f = AbstractC3661s0.a();
    private final C3658q0 g = AbstractC3661s0.b();

    private C3341xD(C0702m c0702m, AbstractC3972u2 abstractC3972u2) {
        this.b = c0702m;
        this.a = abstractC3972u2;
        C3605b.a aVarA = C3605b.a();
        this.c = aVarA;
        aVarA.a(c0702m.Y().z0().toMapVersionMappingInformation());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(Object obj) {
        throw new C3405y10();
    }

    public final C3605b b() {
        this.d.values().forEach(new Consumer() { // from class: com.android.tools.r8.internal.xD$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((C3266wD) obj).a();
            }
        });
        a();
        return this.c.a();
    }

    public static C3341xD a(C0702m c0702m, AbstractC3972u2 abstractC3972u2) {
        return new C3341xD(c0702m, abstractC3972u2);
    }

    private void a() {
        ArrayList arrayList = new ArrayList(this.e.entrySet());
        final C3932m1 c3932m1 = new C3932m1();
        arrayList.sort(Map.Entry.comparingByKey());
        arrayList.forEach(new Consumer() { // from class: com.android.tools.r8.internal.xD$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c3932m1, (Map.Entry) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C3932m1 c3932m1, Map.Entry entry) {
        String str;
        C0767v2 c0767v2 = (C0767v2) entry.getKey();
        if (!h && this.b.f().d(c0767v2) != null && this.b.f().d(c0767v2).e0()) {
            throw new AssertionError();
        }
        String strR0 = c0767v2.r0();
        String str2 = (String) entry.getValue();
        do {
            str = "R8$$REMOVED$$CLASS$$" + c3932m1.b();
        } while (this.c.b(str));
        this.c.a(str, strR0, Position.UNKNOWN).a(new Consumer() { // from class: com.android.tools.r8.internal.xD$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C3341xD.a((com.android.tools.r8.naming.mappinginformation.e) obj);
            }
        }, com.android.tools.r8.naming.mappinginformation.a.a(str2));
    }

    public final C3191vD a(C0729q2 c0729q2) {
        return new C3191vD(this, c0729q2, this.b.E().a(c0729q2.d), C3970u0.b(this.b.z().c(c0729q2.getType()).toString())).a(this.a).a(this.b.D()).a();
    }
}
