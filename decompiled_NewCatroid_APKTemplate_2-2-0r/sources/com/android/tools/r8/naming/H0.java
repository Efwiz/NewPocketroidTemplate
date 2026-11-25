package com.android.tools.r8.naming;

import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.StringConsumer;
import com.android.tools.r8.utils.O2;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class H0 {
    private StringConsumer a;
    private DiagnosticsHandler b;

    public final H0 a(StringConsumer stringConsumer) {
        this.a = stringConsumer;
        return this;
    }

    public final H0 a(O2 o2) {
        this.b = o2;
        return this;
    }

    public final I0 a() {
        return new I0(this.a, this.b);
    }
}
