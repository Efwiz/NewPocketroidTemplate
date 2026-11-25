package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC0984Fi;
import com.android.tools.r8.internal.AbstractC2734ot;
import com.android.tools.r8.internal.InterfaceC3010sj;
import com.android.tools.r8.naming.AbstractC3649o0;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.Collections;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.graph.r2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0737r2 extends I3 implements V4 {
    public static final C0737r2 g = new C0737r2(null, null, null);
    public final C0759u2 d;
    public final C0767v2 e;
    public final C0800x2 f;

    C0737r2(C0759u2 c0759u2, C0767v2 c0767v2, C0800x2 c0800x2) {
        this.d = c0759u2;
        this.e = c0767v2;
        this.f = c0800x2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(com.android.tools.r8.utils.structural.z zVar) {
        zVar.c(new Function() { // from class: com.android.tools.r8.graph.r2$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0737r2) obj).v0();
            }
        }).c(new Function() { // from class: com.android.tools.r8.graph.r2$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0737r2) obj).f;
            }
        }).c(new Function() { // from class: com.android.tools.r8.graph.r2$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0737r2) obj).d;
            }
        });
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.x U() {
        return this;
    }

    @Override // com.android.tools.r8.graph.AbstractC0740s
    public final boolean c(Object obj) {
        if (!(obj instanceof C0737r2)) {
            return false;
        }
        C0737r2 c0737r2 = (C0737r2) obj;
        return this.d.equals(c0737r2.d) && this.e.equals(c0737r2.e) && this.f.equals(c0737r2.f);
    }

    public final void d(Consumer consumer) {
        consumer.accept(this.e);
        this.f.forEach(consumer);
    }

    public final C0767v2 k(int i) {
        return this.f.a[i];
    }

    @Override // com.android.tools.r8.utils.structural.x
    public final com.android.tools.r8.utils.structural.y p() {
        return new com.android.tools.r8.utils.structural.y() { // from class: com.android.tools.r8.graph.r2$$ExternalSyntheticLambda5
            @Override // com.android.tools.r8.utils.structural.y
            public final void a(com.android.tools.r8.utils.structural.z zVar) {
                C0737r2.a(zVar);
            }
        };
    }

    @Override // com.android.tools.r8.graph.AbstractC0594a1
    public final String q0() {
        return a(AbstractC3649o0.a());
    }

    @Override // com.android.tools.r8.graph.AbstractC0740s
    public final int s0() {
        return (this.f.hashCode() * 31) + (this.e.hashCode() * 13) + (this.d.hashCode() * 7);
    }

    public int t0() {
        return this.f.size();
    }

    public final String toString() {
        return "Proto " + this.d + " " + this.e + " " + this.f;
    }

    public C0800x2 u0() {
        return this.f;
    }

    public C0767v2 v0() {
        return this.e;
    }

    public final String w0() {
        return a(AbstractC3649o0.a());
    }

    public final Iterable b(final C0722p1 c0722p1) {
        return AbstractC2734ot.a((Iterable) this.f, new InterfaceC3010sj() { // from class: com.android.tools.r8.graph.r2$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C0737r2.b(c0722p1, (C0767v2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C0767v2 b(C0722p1 c0722p1, C0767v2 c0767v2) {
        return c0767v2.b(c0722p1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C0767v2 a(C0722p1 c0722p1, C0767v2 c0767v2) {
        return c0767v2.b(c0722p1);
    }

    public final Iterable a(final C0722p1 c0722p1) {
        return AbstractC2734ot.a((Iterable) AbstractC0984Fi.a(Collections.singleton(this.e), this.f), new InterfaceC3010sj() { // from class: com.android.tools.r8.graph.r2$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C0737r2.a(c0722p1, (C0767v2) obj);
            }
        });
    }

    public final void a(C0702m c0702m, com.android.tools.r8.dex.L l) {
        if (l.a(this)) {
            C0759u2 c0759u2 = this.d;
            c0759u2.getClass();
            l.a(c0759u2);
            C0767v2 c0767v2 = this.e;
            c0767v2.getClass();
            if (l.a(c0767v2)) {
                C0759u2 c0759u2C = c0702m.z().c(c0767v2);
                c0759u2C.getClass();
                l.a(c0759u2C);
            }
            for (C0767v2 c0767v3 : this.f.a) {
                c0767v3.getClass();
                if (l.a(c0767v3)) {
                    C0759u2 c0759u2C2 = c0702m.z().c(c0767v3);
                    c0759u2C2.getClass();
                    l.a(c0759u2C2);
                }
            }
        }
    }

    @Override // com.android.tools.r8.graph.I3
    public final int a(C0645e5 c0645e5) {
        return c0645e5.a(this);
    }

    public final String a(AbstractC3649o0 abstractC3649o0) {
        StringBuilder sb = new StringBuilder("(");
        int i = 0;
        while (true) {
            C0767v2[] c0767v2Arr = this.f.a;
            if (i < c0767v2Arr.length) {
                sb.append(abstractC3649o0.c(c0767v2Arr[i]));
                i++;
            } else {
                sb.append(Constant.AFTER_QUTO);
                sb.append(abstractC3649o0.c(this.e));
                return sb.toString();
            }
        }
    }
}
