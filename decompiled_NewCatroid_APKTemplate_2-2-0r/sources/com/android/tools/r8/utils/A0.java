package com.android.tools.r8.utils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
final class A0 implements InterfaceC3986x0 {
    private final Writer a;

    public A0(BufferedWriter bufferedWriter) {
        this.a = bufferedWriter;
    }

    @Override // com.android.tools.r8.utils.InterfaceC3986x0
    public final boolean a(String str) throws IOException {
        this.a.write(str.replace("\r", "<CR>"));
        this.a.write(10);
        return true;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() throws IOException {
        this.a.close();
    }
}
