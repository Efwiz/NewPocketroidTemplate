package com.android.tools.r8.internal;

import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.StringConsumer;
import com.android.tools.r8.dex.C0408c;
import com.android.tools.r8.graph.C0612b4;
import com.android.tools.r8.utils.C3879c3;
import com.android.tools.r8.utils.C3945p;
import com.android.tools.r8.utils.ExceptionDiagnostic;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ExecutorService;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ae, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0826Ae {
    static final /* synthetic */ boolean a = true;

    public static EF a(Path path, Set<Path> set, Set<Path> set2, Path path2, com.android.tools.r8.utils.A1 a1) throws IOException {
        EF efC;
        com.android.tools.r8.d0.a aVarA = com.android.tools.r8.d0.a(path);
        try {
            C1333Rt c1333RtD = C1386Tt.a(aVarA.a()).d();
            if (C1233Oe.b(c1333RtD, a1.e, aVarA.getOrigin())) {
                Files.copy(path, path2, new CopyOption[0]);
                return null;
            }
            C3945p.a aVarB = C3945p.b();
            aVarB.i(set);
            C0408c c0408c = new C0408c(aVarB.f(set2).a(), a1, com.android.tools.r8.utils.y3.b());
            ExecutorService executorServiceA = C3879c3.a(a1);
            if (!a && a1.d0) {
                throw new AssertionError();
            }
            a1.d0 = true;
            C0612b4 c0612b4A = c0408c.a(executorServiceA);
            a1.d0 = false;
            if (C1233Oe.a(c1333RtD, a1.e, aVarA.getOrigin())) {
                efC = new IF(a1.j0(), a1.e).c(aVarA);
            } else {
                efC = new C1822dB(com.android.tools.r8.utils.y3.b()).a(new KF(a1.j0(), a1.e).c(aVarA), c0612b4A);
            }
            Files.write(path2, Collections.singleton(a(a1, c0612b4A, efC)), new OpenOption[0]);
            return efC;
        } catch (Exception e) {
            a1.e.a(new ExceptionDiagnostic(e, aVarA.getOrigin()));
            throw null;
        }
    }

    private static String a(com.android.tools.r8.utils.A1 a1, C0612b4 c0612b4, EF ef) {
        LF lfA = new C1425Uk(com.android.tools.r8.utils.y3.b()).a(ef, c0612b4);
        final com.android.tools.r8.utils.N n = new com.android.tools.r8.utils.N();
        NF.a(lfA, new StringConsumer() { // from class: com.android.tools.r8.internal.Ae$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.StringConsumer
            public final void accept(String str, DiagnosticsHandler diagnosticsHandler) {
                n.a(str);
            }
        }, a1.j0());
        return (String) n.a();
    }
}
