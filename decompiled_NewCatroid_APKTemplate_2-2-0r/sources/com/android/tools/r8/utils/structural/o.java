package com.android.tools.r8.utils.structural;

import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.Y0;
import com.android.tools.r8.graph.Y0$$ExternalSyntheticLambda1;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class o {
    public abstract void a(double d);

    public abstract void a(float f);

    public abstract void a(int i);

    public abstract void a(long j);

    public abstract void a(C0759u2 c0759u2);

    public abstract void a(C0767v2 c0767v2);

    public abstract void a(Object obj, y yVar);

    public final void a(List list) {
        Iterator it = list.iterator();
        o$$ExternalSyntheticLambda0 o__externalsyntheticlambda0 = new o$$ExternalSyntheticLambda0();
        q qVar = (q) this;
        while (it.hasNext()) {
            o__externalsyntheticlambda0.a(it.next(), qVar);
        }
    }

    public abstract void a(boolean z);

    public final void a(Y0 y0) {
        a(y0, y0.p());
    }

    public final void a(C0684j2 c0684j2) {
        a(c0684j2, c0684j2.p());
    }

    public final void a(AbstractC0744s2 abstractC0744s2) {
        abstractC0744s2.a(new Y0$$ExternalSyntheticLambda1(this), new Consumer() { // from class: com.android.tools.r8.utils.structural.o$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((Y0) obj);
            }
        }, new Consumer() { // from class: com.android.tools.r8.utils.structural.o$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C0684j2) obj);
            }
        });
    }
}
