package com.android.tools.r8.graph;

import com.android.tools.r8.dex.C0408c;
import com.android.tools.r8.internal.C1367Tb;
import com.android.tools.r8.utils.C3945p;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class x5 extends AbstractC0709n0 {
    public x5(C0612b4 c0612b4, com.android.tools.r8.utils.A1 a1) {
        super(c0612b4, a1);
    }

    public static String a(C3945p c3945p, com.android.tools.r8.utils.A1 a1) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            PrintStream printStream = new PrintStream(byteArrayOutputStream);
            try {
                new x5(new C0408c(c3945p, a1, com.android.tools.r8.utils.y3.b()).a(), a1).b(printStream);
                printStream.close();
                return byteArrayOutputStream.toString(StandardCharsets.UTF_8);
            } catch (Throwable th) {
                try {
                    printStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (IOException e) {
            throw new C1367Tb("Failed to generate smali sting", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.tools.r8.graph.AbstractC0709n0
    /* JADX INFO: renamed from: b */
    public final void a(PrintStream printStream, T0 t0) {
    }

    @Override // com.android.tools.r8.graph.AbstractC0709n0
    final void c(C0729q2 c0729q2, PrintStream printStream) {
        printStream.append(".class ");
        printStream.append((CharSequence) c0729q2.e.F());
        printStream.append(" ");
        printStream.append((CharSequence) c0729q2.d.q0());
        printStream.append("\n\n");
        if (c0729q2.d != this.a.e.Z1) {
            printStream.append(".super ");
            printStream.append((CharSequence) c0729q2.f.q0());
            printStream.append("\n");
            for (C0767v2 c0767v2 : c0729q2.g.a) {
                printStream.append(".implements ");
                printStream.append((CharSequence) c0767v2.q0());
                printStream.append("\n");
            }
        }
    }

    @Override // com.android.tools.r8.graph.AbstractC0709n0
    final void b(C0729q2 c0729q2, PrintStream printStream) {
        printStream.append("# End of class ");
        printStream.append((CharSequence) c0729q2.d.q0());
        printStream.append("\n");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.tools.r8.graph.AbstractC0709n0
    /* JADX INFO: renamed from: b */
    public final void a(PrintStream printStream, o5 o5Var) {
        printStream.append("\n");
        printStream.append((CharSequence) o5Var.e().a(com.android.tools.r8.utils.P2.a(this.a.g(), this.a.d.e)));
        printStream.append("\n");
    }
}
