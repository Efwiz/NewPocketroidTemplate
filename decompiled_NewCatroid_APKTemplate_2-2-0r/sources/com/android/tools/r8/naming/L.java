package com.android.tools.r8.naming;

import java.io.BufferedReader;
import java.io.IOException;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class L implements M {
    private final BufferedReader a;

    private L(BufferedReader bufferedReader) {
        this.a = bufferedReader;
    }

    @Override // com.android.tools.r8.naming.M
    public final String a() {
        return this.a.readLine();
    }

    @Override // com.android.tools.r8.naming.M
    public final void close() throws IOException {
        this.a.close();
    }
}
