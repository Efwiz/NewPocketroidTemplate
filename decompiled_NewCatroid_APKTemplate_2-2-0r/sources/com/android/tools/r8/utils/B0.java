package com.android.tools.r8.utils;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class B0 {
    private final InterfaceC3996z0 a;

    private B0(InterfaceC3996z0 interfaceC3996z0) {
        this.a = interfaceC3996z0;
    }

    public static B0 a(Path path) {
        return new B0(new C3982w0(path));
    }

    private static String a(C0729q2 c0729q2) {
        return c0729q2.getType().r0() + " " + c0729q2.Y().e();
    }

    public final void a(InterfaceC3905h3 interfaceC3905h3) throws IOException {
        InterfaceC3986x0 interfaceC3986x0A = ((C3982w0) this.a).a();
        try {
            interfaceC3905h3.accept(interfaceC3986x0A);
            interfaceC3986x0A.close();
        } catch (Throwable th) {
            try {
                interfaceC3986x0A.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public final void a(C0702m c0702m) {
        try {
            InterfaceC3986x0 interfaceC3986x0A = ((C3982w0) this.a).a();
            try {
                ArrayList arrayList = new ArrayList(c0702m.f().d());
                arrayList.sort(Comparator.comparing(new Function() { // from class: com.android.tools.r8.utils.B0$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return ((C0729q2) obj).getType();
                    }
                }));
                for (int i = 0; i < arrayList.size(); i++) {
                    a(interfaceC3986x0A, (C0729q2) arrayList.get(i));
                }
                interfaceC3986x0A.close();
            } catch (Throwable th) {
                try {
                    interfaceC3986x0A.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void a(InterfaceC3986x0 interfaceC3986x0, C0729q2 c0729q2) {
        if (interfaceC3986x0.a(a(c0729q2))) {
            for (com.android.tools.r8.graph.W0 w0 : c0729q2.M1()) {
                if (interfaceC3986x0.a(w0.getReference().r0())) {
                    if (w0.q1()) {
                        Iterator<String> it = Y2.f(w0.a1().toString()).iterator();
                        while (it.hasNext() && interfaceC3986x0.a(it.next())) {
                        }
                    } else {
                        interfaceC3986x0.a("<nocode>");
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String a(String str) {
        return str.replace("\r", "<CR>");
    }
}
