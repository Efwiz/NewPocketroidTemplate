package com.android.tools.r8.tracereferences;

import com.android.tools.r8.ArchiveClassFileProvider;
import com.android.tools.r8.ClassFileResourceProvider;
import com.android.tools.r8.CompilationFailedException;
import com.android.tools.r8.Diagnostic;
import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.dex.U;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.C1031Ha;
import com.android.tools.r8.internal.C1036Hf;
import com.android.tools.r8.internal.C1507Xl;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.origin.PathOrigin;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.ArchiveResourceProvider;
import com.android.tools.r8.utils.ExceptionDiagnostic;
import com.android.tools.r8.utils.N;
import com.android.tools.r8.utils.O2;
import com.android.tools.r8.utils.R0;
import com.android.tools.r8.utils.StringDiagnostic;
import com.android.tools.r8.utils.X0;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class TraceReferencesCommand {
    private final boolean a;
    private final boolean b;
    private final O2 c;
    private final AbstractC1639am d;
    private final AbstractC1639am e;
    private final AbstractC1639am f;
    private final TraceReferencesConsumer g;

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class Builder {
        private boolean a;
        private boolean b;
        private final O2 c;
        private final C1507Xl d;
        private final C1507Xl e;
        private final C1507Xl f;
        private TraceReferencesConsumer g;

        private Builder(DiagnosticsHandler diagnosticsHandler) {
            this.a = false;
            this.b = false;
            this.d = AbstractC1639am.g();
            this.e = AbstractC1639am.g();
            this.f = AbstractC1639am.g();
            this.c = new O2(diagnosticsHandler);
        }

        final O2 a() {
            return this.c;
        }

        public Builder addLibraryFiles(Path... pathArr) {
            addLibraryFiles(Arrays.asList(pathArr));
            return this;
        }

        public Builder addLibraryResourceProvider(ClassFileResourceProvider classFileResourceProvider) {
            this.d.a(classFileResourceProvider);
            return this;
        }

        public Builder addSourceFiles(Path... pathArr) {
            addSourceFiles(Arrays.asList(pathArr));
            return this;
        }

        public Builder addTargetFiles(Path... pathArr) {
            addTargetFiles(Arrays.asList(pathArr));
            return this;
        }

        public final TraceReferencesCommand build() throws CompilationFailedException {
            final N n = new N(null);
            R0.a(this.c, new R0.a() { // from class: com.android.tools.r8.tracereferences.TraceReferencesCommand$Builder$$ExternalSyntheticLambda0
                @Override // com.android.tools.r8.utils.R0.a
                public final void run() {
                    this.f$0.a(n);
                }
            });
            return (TraceReferencesCommand) n.a();
        }

        public boolean isPrintHelp() {
            return this.a;
        }

        public boolean isPrintVersion() {
            return this.b;
        }

        public Builder setConsumer(TraceReferencesConsumer traceReferencesConsumer) {
            this.g = traceReferencesConsumer;
            return this;
        }

        public Builder setPrintHelp(boolean z) {
            this.a = z;
            return this;
        }

        public Builder setPrintVersion(boolean z) {
            this.b = z;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static String a(byte[] bArr) {
            C1031Ha c1031Ha = new C1031Ha(bArr);
            d dVar = new d();
            c1031Ha.a(7, dVar);
            return "L" + dVar.b() + ";";
        }

        public Builder addLibraryFiles(Collection<Path> collection) {
            Iterator<Path> it = collection.iterator();
            while (it.hasNext()) {
                a(it.next(), this.d);
            }
            return this;
        }

        public Builder addSourceFiles(Collection<Path> collection) {
            for (Path path : collection) {
                if (!Files.exists(path, new LinkOption[0])) {
                    this.c.error(new ExceptionDiagnostic(new NoSuchFileException(path.toString()), new PathOrigin(path)));
                }
                if (X0.a(path)) {
                    this.f.a(ArchiveResourceProvider.fromArchive(path, false));
                } else if (X0.b(path)) {
                    try {
                        C1507Xl c1507Xl = this.f;
                        byte[] allBytes = Files.readAllBytes(path);
                        c1507Xl.a(new e(path, allBytes, a(allBytes)));
                    } catch (IOException e) {
                        this.c.error(new ExceptionDiagnostic(e));
                    }
                } else if (X0.d(path)) {
                    this.f.a(new f(path));
                } else {
                    this.c.error(new StringDiagnostic("Unsupported source file type", new PathOrigin(path)));
                }
            }
            return this;
        }

        public Builder addTargetFiles(Collection<Path> collection) {
            Iterator<Path> it = collection.iterator();
            while (it.hasNext()) {
                a(it.next(), this.e);
            }
            return this;
        }

        private void a(Path path, C1507Xl c1507Xl) {
            if (!Files.exists(path, new LinkOption[0])) {
                this.c.error(new ExceptionDiagnostic(new NoSuchFileException(path.toString()), new PathOrigin(path)));
            }
            if (X0.a(path)) {
                try {
                    c1507Xl.a(new ArchiveClassFileProvider(path));
                    return;
                } catch (IOException e) {
                    this.c.error(new ExceptionDiagnostic(e, new PathOrigin(path)));
                    return;
                }
            }
            if (X0.b(path)) {
                try {
                    c1507Xl.a(new g(new PathOrigin(path), Files.readAllBytes(path)));
                    return;
                } catch (IOException e2) {
                    this.c.error(new ExceptionDiagnostic(e2));
                    return;
                }
            }
            this.c.error(new StringDiagnostic("Unsupported source file type", new PathOrigin(path)));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(N n) {
            TraceReferencesCommand traceReferencesCommand;
            if (!isPrintHelp() && !isPrintVersion()) {
                AbstractC1639am abstractC1639amA = this.d.a();
                AbstractC1639am abstractC1639amA2 = this.e.a();
                AbstractC1639am abstractC1639amA3 = this.f.a();
                if (abstractC1639amA.isEmpty()) {
                    this.c.error(new StringDiagnostic("No library specified"));
                }
                abstractC1639amA2.isEmpty();
                if (abstractC1639amA3.isEmpty()) {
                    this.c.error(new StringDiagnostic("No source specified"));
                }
                if (this.g == null) {
                    this.c.error(new StringDiagnostic("No consumer specified"));
                }
                traceReferencesCommand = new TraceReferencesCommand(this.a, this.b, this.c, abstractC1639amA, abstractC1639amA2, abstractC1639amA3, this.g);
            } else {
                traceReferencesCommand = new TraceReferencesCommand(isPrintHelp(), isPrintVersion());
            }
            n.a(traceReferencesCommand);
            this.c.a();
        }

        final void a(Diagnostic diagnostic) {
            this.c.error(diagnostic);
        }
    }

    TraceReferencesCommand(boolean z, boolean z2, O2 o2, AbstractC1639am abstractC1639am, AbstractC1639am abstractC1639am2, AbstractC1639am abstractC1639am3, TraceReferencesConsumer traceReferencesConsumer) {
        this.a = z;
        this.b = z2;
        this.c = o2;
        this.d = abstractC1639am;
        this.e = abstractC1639am2;
        this.f = abstractC1639am3;
        this.g = traceReferencesConsumer;
    }

    public static Builder builder(DiagnosticsHandler diagnosticsHandler) {
        return new Builder(diagnosticsHandler);
    }

    public static Builder parse(String[] strArr, Origin origin) {
        return h.a(strArr, origin);
    }

    final TraceReferencesConsumer a() {
        return this.g;
    }

    A1 b() {
        A1 a1 = new A1();
        a1.K0 = true;
        a1.I0 = true;
        TraceReferencesConsumer traceReferencesConsumer = this.g;
        C1036Hf.a aVarA = C1036Hf.a(U.b.TraceReferences).c().a(traceReferencesConsumer.getClass().getName());
        if (traceReferencesConsumer instanceof TraceReferencesKeepRules) {
            aVarA.d(((TraceReferencesKeepRules) traceReferencesConsumer).allowObfuscation());
        }
        a1.l0 = aVarA.a();
        return a1;
    }

    final AbstractC1639am c() {
        return this.d;
    }

    final O2 d() {
        return this.c;
    }

    final AbstractC1639am e() {
        return this.f;
    }

    final AbstractC1639am f() {
        return this.e;
    }

    public boolean isPrintHelp() {
        return this.a;
    }

    public boolean isPrintVersion() {
        return this.b;
    }

    public static Builder builder() {
        return new Builder(new c());
    }

    public static Builder parse(String[] strArr, Origin origin, DiagnosticsHandler diagnosticsHandler) {
        return h.a(strArr, origin, diagnosticsHandler);
    }

    public static Builder parse(Collection<String> collection, Origin origin) {
        return h.a((String[]) collection.toArray(new String[collection.size()]), origin);
    }

    TraceReferencesCommand(boolean z, boolean z2) {
        this.a = z;
        this.b = z2;
        this.c = null;
        this.d = null;
        this.e = null;
        this.f = null;
        this.g = null;
    }
}
