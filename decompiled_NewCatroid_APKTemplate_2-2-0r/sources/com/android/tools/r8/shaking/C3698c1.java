package com.android.tools.r8.shaking;

import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.c1, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3698c1 extends AbstractC3716f1 {
    public C3698c1(C3704d1 c3704d1) {
        super(c3704d1.j());
    }

    @Override // com.android.tools.r8.shaking.AbstractC3716f1
    public final C3698c1 b() {
        return this;
    }

    @Override // com.android.tools.r8.shaking.AbstractC3716f1
    final AbstractC3716f1 o() {
        return this;
    }

    public final C3698c1 r() {
        ((C3691b1) this.a).i = false;
        return this;
    }

    @Override // com.android.tools.r8.shaking.AbstractC3716f1
    public final C3698c1 a(C3698c1 c3698c1) {
        return (C3698c1) ((C3698c1) super.a((AbstractC3716f1) c3698c1)).a(!((C3691b1) c3698c1.a).i, new Consumer() { // from class: com.android.tools.r8.shaking.c1$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((C3698c1) obj).r();
            }
        });
    }
}
