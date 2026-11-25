package com.android.tools.r8;

import com.android.tools.r8.dex.C0408c;
import com.android.tools.r8.graph.AbstractC0709n0;
import com.android.tools.r8.graph.C0612b4;
import com.android.tools.r8.graph.C0726q;
import com.android.tools.r8.graph.x5;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.C3879c3;
import com.android.tools.r8.utils.C3945p;
import com.android.tools.r8.utils.y3;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class A {
    public static void a(a aVar) {
        C3945p c3945pA = aVar.a();
        A1 a1B = aVar.b();
        try {
            C cB = b(aVar);
            try {
                Iterator it = c3945pA.d().iterator();
                while (it.hasNext()) {
                    a(aVar, cB, (ProgramResource) it.next(), a1B);
                }
                cB.close();
            } catch (Throwable th) {
                try {
                    cB.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static C b(a aVar) throws IOException {
        String str;
        Path path = aVar.e;
        if (path == null) {
            return new D();
        }
        if (!Files.isDirectory(path, new LinkOption[0])) {
            Path path2 = aVar.e;
            Path parent = path2.getParent();
            if (parent != null) {
                Files.createDirectories(parent, new FileAttribute[0]);
            }
            return new B(new PrintStream(Files.newOutputStream(path2, new OpenOption[0])));
        }
        Path path3 = aVar.e;
        if (aVar.h) {
            str = ".smali";
        } else {
            int i = C0726q.m;
            str = ".dump";
        }
        Path parent2 = path3.getParent();
        if (parent2 != null) {
            Files.createDirectories(parent2, new FileAttribute[0]);
        }
        return new C4004z(str, path3);
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a extends BaseCommand {
        private final Path e;
        private final d0 f;
        private final boolean g;
        private final boolean h;
        private final boolean i;
        private final boolean j;

        /* JADX INFO: renamed from: com.android.tools.r8.A$a$a, reason: collision with other inner class name */
        /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
        public static class C0021a extends BaseCommand.Builder<a, C0021a> {
            private Path f = null;
            private Path g = null;
            private boolean h = false;

            public C0021a c(Path path) {
                this.f = path;
                return this;
            }

            @Override // com.android.tools.r8.BaseCommand.Builder
            final BaseCommand.Builder d() {
                return this;
            }

            public C0021a c(boolean z) {
                this.h = z;
                return this;
            }

            public C0021a d(Path path) {
                this.g = path;
                return this;
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.android.tools.r8.BaseCommand.Builder
            protected final BaseCommand c() {
                Object[] objArr = 0;
                if (!isPrintHelp() && !isPrintVersion()) {
                    C3945p c3945pA = a().a();
                    Path path = this.f;
                    Path path2 = this.g;
                    return new a(c3945pA, path, path2 != null ? d0.a(path2) : null, this.h);
                }
                return new a(isPrintHelp(), isPrintVersion());
            }
        }

        private a(C3945p c3945p, Path path, d0.a aVar, boolean z) {
            super(c3945p);
            this.e = path;
            this.f = aVar;
            this.g = false;
            this.h = z;
            this.i = false;
            this.j = false;
        }

        public static C0021a c() {
            return new C0021a();
        }

        @Override // com.android.tools.r8.BaseCommand
        final A1 b() {
            A1 a1 = new A1();
            a1.K = true;
            return a1;
        }

        private a(boolean z, boolean z2) {
            super(z, z2);
            this.e = null;
            this.f = null;
            this.g = false;
            this.h = false;
            this.i = false;
            this.j = false;
        }
    }

    private static void a(a aVar, C c, final ProgramResource programResource, A1 a1) {
        AbstractC0709n0 c0726q;
        ExecutorService executorServiceA = C3879c3.a(a1);
        try {
            C0612b4 c0612b4A = new C0408c(C3945p.b().a(new ProgramResourceProvider() { // from class: com.android.tools.r8.A$$ExternalSyntheticLambda0
                @Override // com.android.tools.r8.ProgramResourceProvider
                public final Collection getProgramResources() {
                    return Collections.singletonList(programResource);
                }
            }).a(), a1, y3.b()).a(aVar.f, executorServiceA);
            if (aVar.h) {
                c0726q = new x5(c0612b4A, a1);
            } else {
                c0726q = new C0726q(c0612b4A, a1, aVar.g, aVar.i, !aVar.j);
            }
            if (c.b()) {
                c0726q.e(c.a(c0612b4A.g()).a(null));
            }
            c0726q.a(c.a(c0612b4A.g()), c.a());
        } finally {
            executorServiceA.shutdown();
        }
    }
}
