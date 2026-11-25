package com.android.tools.r8.shaking;

import com.android.tools.r8.internal.OU;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.f1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3716f1 {
    static final /* synthetic */ boolean d = true;
    final AbstractC3710e1 a;
    final HashSet b = new HashSet();
    final Set c = OU.c();

    AbstractC3716f1(AbstractC3710e1 abstractC3710e1) {
        this.a = abstractC3710e1;
    }

    public X0 a() {
        return null;
    }

    public C3698c1 b() {
        return null;
    }

    public C3754l1 c() {
        return null;
    }

    public final AbstractC3716f1 d() {
        AbstractC3710e1 abstractC3710e1 = this.a;
        abstractC3710e1.b = false;
        abstractC3710e1.n();
        return o();
    }

    public final AbstractC3716f1 e() {
        AbstractC3710e1 abstractC3710e1 = this.a;
        abstractC3710e1.c = false;
        abstractC3710e1.n();
        return o();
    }

    public final AbstractC3716f1 f() {
        AbstractC3710e1 abstractC3710e1 = this.a;
        abstractC3710e1.d = false;
        abstractC3710e1.n();
        return o();
    }

    public final AbstractC3716f1 g() {
        AbstractC3710e1 abstractC3710e1 = this.a;
        abstractC3710e1.e = false;
        abstractC3710e1.n();
        return o();
    }

    public final AbstractC3716f1 h() {
        AbstractC3710e1 abstractC3710e1 = this.a;
        abstractC3710e1.f = false;
        abstractC3710e1.n();
        return o();
    }

    public final AbstractC3716f1 i() {
        AbstractC3710e1 abstractC3710e1 = this.a;
        abstractC3710e1.g = false;
        abstractC3710e1.n();
        return o();
    }

    public final boolean j() {
        AbstractC3710e1 abstractC3710e1 = this.a;
        return abstractC3710e1.b(abstractC3710e1.c());
    }

    public final boolean k() {
        return this.a.h;
    }

    public final boolean l() {
        return this.a.e;
    }

    public final boolean m() {
        return this.a.f;
    }

    public final AbstractC3722g1 n() {
        AbstractC3722g1 abstractC3722g1A = this.a.a();
        AbstractC3722g1 abstractC3722g1 = this.a.a;
        if (d || ((abstractC3722g1.a || !abstractC3722g1A.a) && ((abstractC3722g1.b || !abstractC3722g1A.b) && ((abstractC3722g1.c || !abstractC3722g1A.c) && ((abstractC3722g1.d || !abstractC3722g1A.d) && ((abstractC3722g1.e || !abstractC3722g1A.e) && ((abstractC3722g1.f || !abstractC3722g1A.f) && (!abstractC3722g1.g || abstractC3722g1A.g)))))))) {
            return abstractC3722g1A;
        }
        throw new AssertionError();
    }

    abstract AbstractC3716f1 o();

    public final AbstractC3716f1 p() {
        AbstractC3710e1 abstractC3710e1 = this.a;
        abstractC3710e1.h = true;
        abstractC3710e1.n();
        return o();
    }

    public final AbstractC3716f1 q() {
        this.a.m();
        return o();
    }

    public final AbstractC3716f1 a(boolean z, Consumer consumer) {
        if (z) {
            consumer.accept(o());
        }
        return o();
    }

    public AbstractC3716f1 a(AbstractC3716f1 abstractC3716f1) {
        AbstractC3710e1 abstractC3710e1 = abstractC3716f1.a;
        a(!abstractC3710e1.b, new Consumer() { // from class: com.android.tools.r8.shaking.f1$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((AbstractC3716f1) obj).d();
            }
        });
        a(!abstractC3710e1.c, new Consumer() { // from class: com.android.tools.r8.shaking.f1$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((AbstractC3716f1) obj).e();
            }
        });
        a(!abstractC3710e1.d, new Consumer() { // from class: com.android.tools.r8.shaking.f1$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((AbstractC3716f1) obj).f();
            }
        });
        a(!abstractC3710e1.e, new Consumer() { // from class: com.android.tools.r8.shaking.f1$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((AbstractC3716f1) obj).g();
            }
        });
        a(!abstractC3710e1.f, new Consumer() { // from class: com.android.tools.r8.shaking.f1$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((AbstractC3716f1) obj).h();
            }
        });
        a(!abstractC3710e1.g, new Consumer() { // from class: com.android.tools.r8.shaking.f1$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((AbstractC3716f1) obj).i();
            }
        });
        a(abstractC3710e1.h, new Consumer() { // from class: com.android.tools.r8.shaking.f1$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((AbstractC3716f1) obj).p();
            }
        });
        this.b.addAll(abstractC3716f1.b);
        this.c.addAll(abstractC3716f1.c);
        return o();
    }

    public final void a(com.android.tools.r8.utils.A1 a1) {
        boolean z = d;
        if (!z && this.a.f) {
            throw new AssertionError();
        }
        if (!z && this.b.isEmpty() && this.c.isEmpty() && a1.o1()) {
            throw new AssertionError();
        }
    }
}
