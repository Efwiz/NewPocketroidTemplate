package com.android.tools.r8.shaking;

import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class X0 extends AbstractC3716f1 {
    public X0(Y0 y0) {
        super(y0.j());
    }

    @Override // com.android.tools.r8.shaking.AbstractC3716f1
    public final X0 a() {
        return this;
    }

    @Override // com.android.tools.r8.shaking.AbstractC3716f1
    final AbstractC3716f1 o() {
        return this;
    }

    public final X0 r() {
        ((W0) this.a).i = false;
        return this;
    }

    public final X0 s() {
        ((W0) this.a).j = true;
        return this;
    }

    @Override // com.android.tools.r8.shaking.AbstractC3716f1
    public final X0 a(X0 x0) {
        return (X0) ((X0) ((X0) super.a((AbstractC3716f1) x0)).a(((W0) x0.a).j, new Consumer() { // from class: com.android.tools.r8.shaking.X0$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((X0) obj).s();
            }
        })).a(!((W0) x0.a).i, new Consumer() { // from class: com.android.tools.r8.shaking.X0$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((X0) obj).r();
            }
        });
    }
}
