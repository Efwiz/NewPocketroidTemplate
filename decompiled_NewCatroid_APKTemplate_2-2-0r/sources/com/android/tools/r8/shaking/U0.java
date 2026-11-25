package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.internal.JA;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class U0 {
    static final /* synthetic */ boolean a = true;

    public C0729q2 a() {
        return null;
    }

    public final void a(Consumer consumer, Consumer consumer2) {
        if (this instanceof S0) {
            consumer.accept(a());
        } else {
            if (!a && !(this instanceof T0)) {
                throw new AssertionError();
            }
            consumer2.accept(b());
        }
    }

    public JA b() {
        return null;
    }

    public boolean c() {
        return this instanceof S0;
    }
}
