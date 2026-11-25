package com.android.tools.r8.utils;

import java.io.BufferedReader;
import java.io.IOException;

/* JADX INFO: renamed from: com.android.tools.r8.utils.y0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
final class C3990y0 implements InterfaceC3986x0 {
    private final BufferedReader a;

    public C3990y0(BufferedReader bufferedReader) {
        this.a = bufferedReader;
    }

    @Override // com.android.tools.r8.utils.InterfaceC3986x0
    public final boolean a(String str) throws IOException {
        String strA = B0.a(str);
        String line = this.a.readLine();
        boolean zEquals = line.equals(strA);
        if (zEquals) {
            return zEquals;
        }
        throw new AssertionError("\nMismatch for line: " + strA + "\n    and dump-line: " + line);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() throws IOException {
        this.a.close();
    }
}
