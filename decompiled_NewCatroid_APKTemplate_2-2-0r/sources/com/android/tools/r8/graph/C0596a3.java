package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC2734ot;
import com.android.tools.r8.internal.InterfaceC3205vM;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.graph.a3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0596a3 {
    static final /* synthetic */ boolean b = true;
    private AbstractC0677i3 a;

    private C0596a3() {
        this.a = null;
    }

    public final void a(AbstractC0677i3 abstractC0677i3) {
        boolean z = b;
        if (this.a == null) {
            this.a = abstractC0677i3;
            return;
        }
        final com.android.tools.r8.utils.N n = new com.android.tools.r8.utils.N();
        final ArrayList arrayList = new ArrayList();
        final ArrayList arrayList2 = new ArrayList();
        this.a.a(new Consumer() { // from class: com.android.tools.r8.graph.a3$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                n.a((AbstractC0677i3.a) obj);
            }
        }, new Consumer() { // from class: com.android.tools.r8.graph.a3$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                arrayList.add((C0685j3) obj);
            }
        }, new Consumer() { // from class: com.android.tools.r8.graph.a3$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                arrayList2.add((AbstractC0629c3) obj);
            }
        });
        abstractC0677i3.a(new Consumer() { // from class: com.android.tools.r8.graph.a3$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C0596a3.a(n, (AbstractC0677i3.a) obj);
            }
        }, new Consumer() { // from class: com.android.tools.r8.graph.a3$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C0596a3.a(arrayList, (C0685j3) obj);
            }
        }, new Consumer() { // from class: com.android.tools.r8.graph.a3$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C0596a3.a(arrayList2, (AbstractC0629c3) obj);
            }
        });
        if (!n.c()) {
            if (arrayList.size() == 1 && arrayList2.isEmpty()) {
                this.a = (AbstractC0677i3) arrayList.get(0);
                return;
            } else if (arrayList.isEmpty() && arrayList2.size() == 1) {
                this.a = (AbstractC0677i3) arrayList2.get(0);
                return;
            } else {
                this.a = new C0654f3(arrayList, arrayList2);
                return;
            }
        }
        if (arrayList.isEmpty() && arrayList2.isEmpty()) {
            this.a = (AbstractC0677i3) n.a();
            return;
        }
        if (((AbstractC0677i3.a) n.a()).x()) {
            this.a = new C0661g3(((AbstractC0677i3.a) n.a()).m(), arrayList, arrayList2);
            return;
        }
        C0668h3 c0668h3K = ((AbstractC0677i3.a) n.a()).k();
        if (!z && c0668h3K == null) {
            throw new AssertionError();
        }
        this.a = new C0636d3(c0668h3K, arrayList, arrayList2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.utils.N n, AbstractC0677i3.a aVar) {
        if (n.c()) {
            if (b) {
                if (((AbstractC0677i3.a) n.a()).x()) {
                    return;
                }
            } else {
                throw new AssertionError("Unexpected multiple results between program and classpath");
            }
        }
        n.a(aVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(List list, final C0685j3 c0685j3) {
        if (AbstractC2734ot.b(list, new InterfaceC3205vM() { // from class: com.android.tools.r8.graph.a3$$ExternalSyntheticLambda6
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return C0596a3.a(c0685j3, (C0685j3) obj);
            }
        })) {
            return;
        }
        list.add(c0685j3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C0685j3 c0685j3, C0685j3 c0685j4) {
        return c0685j4.d() == c0685j3.d();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(List list, final AbstractC0629c3 abstractC0629c3) {
        if (AbstractC2734ot.b(list, new InterfaceC3205vM() { // from class: com.android.tools.r8.graph.a3$$ExternalSyntheticLambda7
            @Override // com.android.tools.r8.internal.InterfaceC3205vM
            public final boolean apply(Object obj) {
                return C0596a3.a(abstractC0629c3, (AbstractC0629c3) obj);
            }
        })) {
            return;
        }
        list.add(abstractC0629c3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(AbstractC0629c3 abstractC0629c3, AbstractC0629c3 abstractC0629c4) {
        return abstractC0629c4.h() == abstractC0629c3.h();
    }

    public final AbstractC0677i3 a(C0611b3 c0611b3) {
        AbstractC0677i3 abstractC0677i3 = this.a;
        return abstractC0677i3 == null ? c0611b3 : abstractC0677i3;
    }
}
