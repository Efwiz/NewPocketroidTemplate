package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.InterfaceC0694k5;
import com.android.tools.r8.graph.n5;
import com.android.tools.r8.utils.C3879c3;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class D {
    static final /* synthetic */ boolean c = true;
    private final C0702m a;
    private final ArrayList b;

    private D(C0702m c0702m) {
        this.b = new ArrayList();
        this.a = c0702m;
        c0702m.Y();
    }

    public static D a(C0702m c0702m) {
        return new D(c0702m);
    }

    public static D b(C0702m c0702m) {
        return new C(c0702m, c0702m.y().a().a(X.d()));
    }

    public final ArrayList a(Collection collection, ExecutorService executorService) {
        if (!c && !this.b.isEmpty()) {
            throw new AssertionError();
        }
        C3879c3.a(collection, new Consumer() { // from class: com.android.tools.r8.shaking.D$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((C0729q2) obj);
            }
        }, executorService);
        this.b.sort(new Comparator() { // from class: com.android.tools.r8.shaking.D$$ExternalSyntheticLambda2
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return D.a((InterfaceC0694k5) obj, (InterfaceC0694k5) obj2);
            }
        });
        return this.b;
    }

    boolean b(InterfaceC0694k5 interfaceC0694k5) {
        return this.a.v().a(interfaceC0694k5).h();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int a(InterfaceC0694k5 interfaceC0694k5, InterfaceC0694k5 interfaceC0694k6) {
        return interfaceC0694k5.getReference().a(interfaceC0694k6.getReference());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0729q2 c0729q2) {
        if (a((InterfaceC0694k5) c0729q2)) {
            c0729q2.k(new Consumer() { // from class: com.android.tools.r8.shaking.D$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a((n5) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(InterfaceC0694k5 interfaceC0694k5) {
        if (!b(interfaceC0694k5)) {
            return true;
        }
        synchronized (this.b) {
            this.b.add(interfaceC0694k5);
        }
        return false;
    }
}
