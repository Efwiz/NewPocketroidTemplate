package com.android.tools.r8;

import com.android.tools.r8.graph.AbstractC0709n0;
import com.android.tools.r8.graph.InterfaceC0703m0;
import com.android.tools.r8.naming.C3605b;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.z, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C4004z implements C {
    private final Path a;
    private final String b;

    public C4004z(String str, Path path) {
        this.a = path;
        this.b = str;
    }

    @Override // com.android.tools.r8.C
    public final InterfaceC0703m0 a(C3605b c3605b) {
        return AbstractC0709n0.a(c3605b, this.a, this.b);
    }

    @Override // com.android.tools.r8.C
    public final boolean b() {
        return false;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
    }

    @Override // com.android.tools.r8.C
    public final Consumer a() {
        return new Consumer() { // from class: com.android.tools.r8.z$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((PrintStream) obj).close();
            }
        };
    }
}
