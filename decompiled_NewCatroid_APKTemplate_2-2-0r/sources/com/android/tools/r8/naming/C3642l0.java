package com.android.tools.r8.naming;

import com.android.tools.r8.DiagnosticsHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.naming.l0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3642l0 extends com.android.tools.r8.T {
    private final List a;

    public C3642l0(ArrayList arrayList) {
        this.a = arrayList;
    }

    @Override // com.android.tools.r8.J
    public final void finished(final DiagnosticsHandler diagnosticsHandler) {
        this.a.forEach(new Consumer() { // from class: com.android.tools.r8.naming.l0$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((com.android.tools.r8.T) obj).finished(diagnosticsHandler);
            }
        });
    }

    @Override // com.android.tools.r8.T
    public final void a(final y0 y0Var, final C3605b c3605b) {
        this.a.forEach(new Consumer() { // from class: com.android.tools.r8.naming.l0$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((com.android.tools.r8.T) obj).a(y0Var, c3605b);
            }
        });
    }
}
