package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import java.util.LinkedList;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ik, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1073Ik {
    static final /* synthetic */ boolean b = true;
    private final K4 a = K4.b();

    final void a(final C2688oE c2688oE) {
        c2688oE.a(new Consumer() { // from class: com.android.tools.r8.internal.Ik$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c2688oE, (C0729q2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C2688oE c2688oE, C0729q2 c0729q2) {
        C0767v2 type = c0729q2.getType();
        C0767v2 type2 = c2688oE.f().getType();
        if (!b && this.a.containsKey(type)) {
            throw new AssertionError();
        }
        this.a.a(type, type2);
    }

    final C1073Ik a(LinkedList linkedList) {
        linkedList.forEach(new Consumer() { // from class: com.android.tools.r8.internal.Ik$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C2688oE) obj);
            }
        });
        return this;
    }

    final C1105Jk a() {
        return new C1105Jk(this.a);
    }
}
