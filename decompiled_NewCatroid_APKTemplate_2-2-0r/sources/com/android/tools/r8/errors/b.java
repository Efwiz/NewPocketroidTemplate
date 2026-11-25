package com.android.tools.r8.errors;

import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.Position;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class b {
    private C0767v2 a;
    private C0759u2 b;
    private Origin c;
    private Position d;

    public final b a(C0759u2 c0759u2, C0767v2 c0767v2) {
        this.a = c0767v2;
        this.b = c0759u2;
        return this;
    }

    public final b a(Origin origin) {
        this.c = origin;
        return this;
    }

    public final b a(Position position) {
        this.d = position;
        return this;
    }

    public final AssumeValuesMissingStaticFieldDiagnostic a() {
        return new AssumeValuesMissingStaticFieldDiagnostic(this.a, this.b, this.c, this.d);
    }
}
