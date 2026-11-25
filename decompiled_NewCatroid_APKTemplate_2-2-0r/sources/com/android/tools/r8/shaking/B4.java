package com.android.tools.r8.shaking;

import java.io.PrintStream;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class B4 {
    private final PrintStream a;
    private int b = -1;

    public B4(PrintStream printStream) {
        this.a = printStream;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        for (int i = 0; i < this.b; i++) {
            this.a.print("  ");
        }
    }

    final void a(String str) {
        a();
        this.a.print("|- ");
        this.a.println(str);
    }
}
