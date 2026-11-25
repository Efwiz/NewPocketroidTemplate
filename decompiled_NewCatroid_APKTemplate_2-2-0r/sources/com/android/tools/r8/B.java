package com.android.tools.r8;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.InterfaceC0703m0;
import com.android.tools.r8.naming.C3605b;
import com.android.tools.r8.utils.C3912j0;
import java.io.PrintStream;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class B implements C {
    private final PrintStream a;

    private B(PrintStream printStream) {
        this.a = printStream;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ PrintStream a(AbstractC0727q0 abstractC0727q0) {
        return this.a;
    }

    @Override // com.android.tools.r8.C
    public final boolean b() {
        return true;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        this.a.flush();
        this.a.close();
    }

    @Override // com.android.tools.r8.C
    public final InterfaceC0703m0 a(C3605b c3605b) {
        return new InterfaceC0703m0() { // from class: com.android.tools.r8.B$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.graph.InterfaceC0703m0
            public final PrintStream a(AbstractC0727q0 abstractC0727q0) {
                return this.f$0.a(abstractC0727q0);
            }
        };
    }

    @Override // com.android.tools.r8.C
    public final Consumer a() {
        return C3912j0.b();
    }
}
