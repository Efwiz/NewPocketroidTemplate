package com.android.tools.r8.utils;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.C1367Tb;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
final class W implements Supplier {
    static final /* synthetic */ boolean f = true;
    private X a;
    private AbstractC3876c0 b;
    private C0767v2 c;
    private AbstractC0727q0 d;
    private volatile boolean e;

    private W(X x, AbstractC3876c0 abstractC3876c0, C0767v2 c0767v2) {
        this.d = null;
        this.e = false;
        this.a = x;
        this.b = abstractC3876c0;
        this.c = c0767v2;
    }

    @Override // java.util.function.Supplier
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public final AbstractC0727q0 get() {
        if (this.e) {
            return this.d;
        }
        synchronized (this) {
            if (!this.e) {
                if (!f && (this.a == null || this.b == null || this.c == null)) {
                    throw new AssertionError();
                }
                this.b.a(this.c, new Consumer() { // from class: com.android.tools.r8.utils.W$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.a((AbstractC0727q0) obj);
                    }
                });
                this.a = null;
                this.b = null;
                this.c = null;
                this.e = true;
            }
        }
        boolean z = f;
        if (!z && !this.e) {
            throw new AssertionError();
        }
        if (z || (this.a == null && this.b == null && this.c == null)) {
            return this.d;
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(AbstractC0727q0 abstractC0727q0) {
        boolean z = f;
        if (!z && abstractC0727q0 == null) {
            throw new AssertionError();
        }
        if (!z && !this.a.e().a(abstractC0727q0)) {
            throw new AssertionError();
        }
        if (!z && this.e) {
            throw new AssertionError();
        }
        if (abstractC0727q0.d == this.c) {
            AbstractC0727q0 abstractC0727q1 = this.d;
            if (abstractC0727q1 == null) {
                this.d = abstractC0727q0;
                return;
            } else {
                this.d = null;
                this.d = this.a.a(abstractC0727q1, abstractC0727q0);
                return;
            }
        }
        throw new C1367Tb("Class content provided for type descriptor " + this.c.r0() + " actually defines class " + abstractC0727q0.d.r0());
    }
}
