package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.C3979v2;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
class o4 {
    static final n4 e = new n4();
    static final /* synthetic */ boolean f = true;
    private final Consumer a;
    private C0767v2 b = null;
    private m4 c = new m4();
    private ArrayList d = new ArrayList();

    o4(Consumer consumer) {
        this.a = consumer;
    }

    void a(C0729q2 c0729q2) {
        if (!f && this.b != null) {
            throw new AssertionError();
        }
        this.d.add(new C3979v2(c0729q2.d, null));
    }

    void b(C0729q2 c0729q2) {
        if (!f && this.b != null) {
            throw new AssertionError();
        }
        this.b = c0729q2.d;
    }

    public void a() {
        this.d.sort(new Comparator() { // from class: com.android.tools.r8.shaking.o4$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return o4.a((C3979v2) obj, (C3979v2) obj2);
            }
        });
        for (C3979v2 c3979v2 : this.d) {
            C0767v2 c0767v2 = (C0767v2) c3979v2.a();
            m4 m4Var = (m4) c3979v2.b();
            this.a.accept(c0767v2.r0());
            if (m4Var == null) {
                this.a.accept(com.android.tools.r8.utils.Y2.c);
            } else {
                this.a.accept(":" + com.android.tools.r8.utils.Y2.c);
                m4Var.a();
                m4Var.a.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.o4$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.a((com.android.tools.r8.graph.T0) obj);
                    }
                });
                m4Var.b.forEach(new Consumer() { // from class: com.android.tools.r8.shaking.o4$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.a((com.android.tools.r8.graph.W0) obj);
                    }
                });
            }
        }
        this.d = null;
    }

    void b() {
        m4 m4Var = this.c;
        if (!m4Var.a.isEmpty() || !m4Var.b.isEmpty()) {
            this.d.add(new C3979v2(this.b, this.c));
            this.c = new m4();
        }
        this.b = null;
    }

    void b(com.android.tools.r8.graph.W0 w0) {
        this.c.b.add(w0);
    }

    void b(com.android.tools.r8.graph.T0 t0) {
        this.c.a.add(t0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int a(C3979v2 c3979v2, C3979v2 c3979v3) {
        return ((C0767v2) c3979v2.a()).compareTo((C0767v2) c3979v3.a());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.graph.T0 t0) {
        this.a.accept("    ");
        String string = t0.f.toString();
        if (!string.isEmpty()) {
            this.a.accept(string);
            this.a.accept(" ");
        }
        this.a.accept(t0.getReference().h.r0());
        this.a.accept(" ");
        this.a.accept(t0.getReference().f.r0());
        this.a.accept(com.android.tools.r8.utils.Y2.c);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.graph.W0 w0) {
        this.a.accept("    ");
        String string = w0.f.toString();
        if (!string.isEmpty()) {
            this.a.accept(string);
            this.a.accept(" ");
        }
        this.a.accept(w0.getReference().h.e.r0());
        this.a.accept(" ");
        this.a.accept(w0.getReference().f.r0());
        this.a.accept("(");
        for (int i = 0; i < w0.getReference().h.f.a.length; i++) {
            if (i != 0) {
                this.a.accept(",");
            }
            this.a.accept(w0.getReference().h.f.a[i].r0());
        }
        this.a.accept(Constant.AFTER_QUTO);
        this.a.accept(com.android.tools.r8.utils.Y2.c);
    }
}
