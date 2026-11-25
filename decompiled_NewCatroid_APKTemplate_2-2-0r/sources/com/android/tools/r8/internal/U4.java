package com.android.tools.r8.internal;

import com.android.tools.r8.OutputMode;
import com.android.tools.r8.ProgramConsumer;
import com.android.tools.r8.dex.C0548k;
import com.android.tools.r8.graph.AbstractC0681j0;
import com.android.tools.r8.graph.C0664h;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.utils.C3989y;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class U4 {

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public interface a {
        V4 a(AbstractC0681j0 abstractC0681j0) throws Exception;
    }

    public static C0729q2 a(Y4 y4, a aVar, Path path, ExecutorService executorService) throws Exception {
        while (true) {
            AbstractC0681j0 abstractC0681j0A = y4.a();
            y4.d();
            if (abstractC0681j0A == null) {
                return y4.b();
            }
            if (aVar == null) {
                com.android.tools.r8.utils.A1 a1 = abstractC0681j0A.d;
                ProgramConsumer programConsumer = a1.f;
                com.android.tools.r8.T t = a1.x1;
                C3989y c3989y = new C3989y(a1);
                C0548k.a((C0702m<?>) C0702m.a(C0664h.a(abstractC0681j0A, com.android.tools.r8.synthesis.y.d())), (com.android.tools.r8.dex.U) null).c(executorService);
                a1.G1();
                c3989y.a().b(path, OutputMode.DexIndexed);
                a1.f = programConsumer;
                a1.x1 = t;
                System.out.println("Bisecting completed with build in " + path + "/");
                System.out.println("Continue bisection by passing either --result-good or --result-bad");
                return null;
            }
            y4.a(aVar.a(abstractC0681j0A));
        }
    }
}
