package com.android.tools.r8.internal;

import com.android.tools.r8.naming.C3605b;
import com.android.tools.r8.retrace.ProguardMapProducer;
import java.io.InputStream;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class HN implements ProguardMapProducer {
    private final C3605b a;

    public HN(C3605b c3605b) {
        this.a = c3605b;
    }

    public final C3605b a() {
        return this.a;
    }

    @Override // com.android.tools.r8.retrace.ProguardMapProducer
    public final InputStream get() {
        throw new C3405y10("Should never get on ProguardMapProducerInternal");
    }
}
