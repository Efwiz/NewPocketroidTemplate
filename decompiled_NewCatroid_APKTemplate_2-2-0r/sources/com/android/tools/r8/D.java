package com.android.tools.r8;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.InterfaceC0703m0;
import com.android.tools.r8.naming.C3605b;
import com.android.tools.r8.utils.C3912j0;
import java.io.PrintStream;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class D implements C {
    private D() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ PrintStream a(AbstractC0727q0 abstractC0727q0) {
        return System.out;
    }

    @Override // com.android.tools.r8.C
    public final boolean b() {
        return true;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
    }

    @Override // com.android.tools.r8.C
    public final InterfaceC0703m0 a(C3605b c3605b) {
        return new InterfaceC0703m0() { // from class: com.android.tools.r8.D$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.graph.InterfaceC0703m0
            public final PrintStream a(AbstractC0727q0 abstractC0727q0) {
                return D.a(abstractC0727q0);
            }
        };
    }

    @Override // com.android.tools.r8.C
    public final Consumer a() {
        return C3912j0.b();
    }
}
