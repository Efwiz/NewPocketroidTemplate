package com.android.tools.r8.relocator;

import com.android.tools.r8.AbstractC3600l;
import com.android.tools.r8.ClassFileConsumer;
import com.android.tools.r8.CompilationFailedException;
import com.android.tools.r8.CompilationMode;
import com.android.tools.r8.Diagnostic;
import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.internal.AbstractC0984Fi;
import com.android.tools.r8.internal.AbstractC1048Hm;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.AbstractC2178hm;
import com.android.tools.r8.internal.C1367Tb;
import com.android.tools.r8.internal.C1946em;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.origin.PathOrigin;
import com.android.tools.r8.references.PackageReference;
import com.android.tools.r8.references.Reference;
import com.android.tools.r8.shaking.E3;
import com.android.tools.r8.shaking.L2;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.AbstractC3867a1;
import com.android.tools.r8.utils.C3870b;
import com.android.tools.r8.utils.C3945p;
import com.android.tools.r8.utils.ExceptionDiagnostic;
import com.android.tools.r8.utils.N;
import com.android.tools.r8.utils.O2;
import com.android.tools.r8.utils.R0;
import com.android.tools.r8.utils.StringDiagnostic;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class RelocatorCommand {
    private static final AbstractC1048Hm i = AbstractC1048Hm.a("--output", "--input", "--map", "--thread-count");
    static final String j = String.join("\n", AbstractC0984Fi.a(Arrays.asList("The Relocator CLI is EXPERIMENTAL and is subject to change", "Usage: relocator [options]", " where options are:", "  --input <file>          # Input file to remap, class, zip or jar.", "  --output <file>         # Output result in <outfile>.", "  --map <from->to>        # Registers a mapping.", "  --thread-count <number> # A specified number of threads to run with.", "  --version               # Print the version of d8.", "  --help                  # Print this message.")));
    static final /* synthetic */ boolean k = true;
    private final boolean a;
    private final boolean b;
    private final O2 c;
    private final C0722p1 d;
    private final ClassFileConsumer e;
    private final C3945p f;
    private final AbstractC2178hm g;
    private final int h;

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class Builder {
        static final /* synthetic */ boolean h = true;
        private final C3945p.a a;
        private final O2 b;
        private final C1946em c = AbstractC2178hm.e();
        private ClassFileConsumer d = null;
        private int e = -1;
        private boolean f;
        private boolean g;

        Builder(C3945p.a aVar) {
            this.a = aVar;
            this.b = aVar.c();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(Collection collection) {
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                Path path = (Path) it.next();
                try {
                    this.a.d(path);
                } catch (C1367Tb e) {
                    this.b.error(new ExceptionDiagnostic(e, new PathOrigin(path)));
                }
            }
        }

        public static Builder parse(String[] strArr, Origin origin) {
            return a(strArr, origin, RelocatorCommand.builder());
        }

        public Builder addPackageMapping(PackageReference packageReference, PackageReference packageReference2) {
            this.c.a(packageReference, packageReference2);
            return this;
        }

        public Builder addProgramFile(final Path path) {
            try {
                new Runnable() { // from class: com.android.tools.r8.relocator.RelocatorCommand$Builder$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.a(path);
                    }
                }.run();
            } catch (C1367Tb e) {
                this.b.error(e.c());
            } catch (C3870b unused) {
            }
            return this;
        }

        public Builder addProgramFiles(Path... pathArr) {
            return addProgramFiles(Arrays.asList(pathArr));
        }

        public RelocatorCommand build() throws CompilationFailedException {
            final N n = new N();
            R0.a(this.b, new R0.a() { // from class: com.android.tools.r8.relocator.RelocatorCommand$Builder$$ExternalSyntheticLambda1
                @Override // com.android.tools.r8.utils.R0.a
                public final void run() {
                    this.f$0.a(n);
                }
            });
            return (RelocatorCommand) n.a();
        }

        public void error(Diagnostic diagnostic) {
            this.b.error(diagnostic);
        }

        public Builder setConsumer(ClassFileConsumer classFileConsumer) {
            this.d = classFileConsumer;
            return this;
        }

        public Builder setOutputPath(Path path) {
            if (path == null) {
                this.d = null;
                return this;
            }
            this.d = new ClassFileConsumer.ArchiveConsumer(path, true);
            return this;
        }

        public Builder setPrintHelp(boolean z) {
            this.g = z;
            return this;
        }

        public Builder setPrintVersion(boolean z) {
            this.f = z;
            return this;
        }

        public Builder setThreadCount(int i) {
            if (i <= 0) {
                this.b.a("Invalid threadCount: " + i);
            } else {
                this.e = i;
            }
            return this;
        }

        public static Builder parse(String[] strArr, Origin origin, DiagnosticsHandler diagnosticsHandler) {
            return a(strArr, origin, RelocatorCommand.builder(diagnosticsHandler));
        }

        public Builder addProgramFiles(final Collection<Path> collection) {
            try {
                new Runnable() { // from class: com.android.tools.r8.relocator.RelocatorCommand$Builder$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.a(collection);
                    }
                }.run();
            } catch (C1367Tb e) {
                this.b.error(e.c());
            } catch (C3870b unused) {
            }
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(Path path) {
            try {
                this.a.d(path);
            } catch (C1367Tb e) {
                this.b.error(new ExceptionDiagnostic(e, new PathOrigin(path)));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(N n) {
            if (!this.g && !this.f) {
                this.b.a();
                if (this.d == null) {
                    this.b.error(new StringDiagnostic("No output path or consumer has been specified"));
                }
                this.b.a();
                n.a(new RelocatorCommand(this.c.a(), this.a.a(), this.b, new C0722p1(), this.d, this.e));
                return;
            }
            n.a(new RelocatorCommand(this.g, this.f));
        }

        /* JADX WARN: Code duplicated, block: B:75:0x0180  */
        private static Builder a(String[] strArr, Origin origin, final Builder builder) {
            int i;
            String str;
            Objects.requireNonNull(builder);
            String[] strArrA = AbstractC3867a1.a(strArr, new Consumer() { // from class: com.android.tools.r8.relocator.RelocatorCommand$Builder$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.error((Diagnostic) obj);
                }
            });
            Path path = null;
            for (int i2 = 0; i2 < strArrA.length; i2 = i + 1) {
                String strTrim = strArrA[i2].trim();
                if (RelocatorCommand.i.contains(strTrim)) {
                    i = i2 + 1;
                    if (i < strArrA.length) {
                        str = strArrA[i];
                    } else {
                        builder.error(new StringDiagnostic("Missing parameter for " + strArrA[i2] + ".", origin));
                        if (path == null) {
                            path = Paths.get(".", new String[0]);
                        }
                        builder.setOutputPath(path);
                        return builder;
                    }
                } else {
                    i = i2;
                    str = null;
                }
                if (strTrim.length() != 0) {
                    strTrim.hashCode();
                    switch (strTrim) {
                        case "--input":
                            if (!h && str == null) {
                                throw new AssertionError();
                            }
                            builder.addProgramFile(Paths.get(str, new String[0]));
                            break;
                            break;
                        case "--map":
                            if (!h && str == null) {
                                throw new AssertionError();
                            }
                            int iIndexOf = str.indexOf("->");
                            if (iIndexOf < 0) {
                                builder.error(new StringDiagnostic("--map " + str + " is not on the form from->to"));
                            } else {
                                builder.addPackageMapping(Reference.packageFromString(str.substring(0, iIndexOf)), Reference.packageFromString(str.substring(iIndexOf + 2)));
                            }
                            break;
                            break;
                        case "--thread-count":
                            AbstractC3600l.a(new Consumer() { // from class: com.android.tools.r8.relocator.RelocatorCommand$Builder$$ExternalSyntheticLambda2
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    this.f$0.error((Diagnostic) obj);
                                }
                            }, strTrim, str, origin, new Consumer() { // from class: com.android.tools.r8.relocator.RelocatorCommand$Builder$$ExternalSyntheticLambda3
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    this.f$0.setThreadCount(((Integer) obj).intValue());
                                }
                            });
                            break;
                        case "--help":
                            builder.setPrintHelp(true);
                            break;
                        case "--output":
                            if (!h && str == null) {
                                throw new AssertionError();
                            }
                            if (path != null) {
                                builder.error(new StringDiagnostic("Cannot output both to '" + path.toString() + "' and '" + str + FormatHelper.QUOTE, origin));
                            } else {
                                path = Paths.get(str, new String[0]);
                            }
                            break;
                            break;
                        case "--version":
                            builder.setPrintVersion(true);
                            break;
                        default:
                            builder.error(new StringDiagnostic("Unknown argument: " + strTrim, origin));
                            break;
                    }
                }
            }
            if (path == null) {
                path = Paths.get(".", new String[0]);
            }
            builder.setOutputPath(path);
            return builder;
        }
    }

    private RelocatorCommand(boolean z, boolean z2) {
        this.a = z;
        this.b = z2;
        this.c = null;
        this.d = null;
        this.e = null;
        this.f = null;
        this.g = null;
        this.h = -1;
    }

    public static Builder builder() {
        return new Builder(C3945p.b());
    }

    public static Builder parse(String[] strArr, Origin origin) {
        return Builder.parse(strArr, origin);
    }

    public C3945p getApp() {
        return this.f;
    }

    public ClassFileConsumer getConsumer() {
        return this.e;
    }

    public C0722p1 getFactory() {
        return this.d;
    }

    public A1 getInternalOptions() {
        A1 a1 = new A1(CompilationMode.DEBUG, L2.a(this.d, getReporter()).e().c().d().a(AbstractC1639am.c("*")).b(E3.a().a("**", false).b()).a(), getReporter());
        a1.D1 = true;
        a1.a0 = getThreadCount();
        ClassFileConsumer classFileConsumer = this.e;
        a1.f = classFileConsumer;
        if (!k && classFileConsumer == null) {
            throw new AssertionError();
        }
        a1.j = classFileConsumer.getDataResourceConsumer();
        return a1;
    }

    public Map<PackageReference, PackageReference> getMapping() {
        return this.g;
    }

    public O2 getReporter() {
        return this.c;
    }

    public int getThreadCount() {
        return this.h;
    }

    public boolean isPrintHelp() {
        return this.a;
    }

    public boolean isPrintVersion() {
        return this.b;
    }

    public static Builder builder(DiagnosticsHandler diagnosticsHandler) {
        return new Builder(C3945p.a(new O2(diagnosticsHandler)));
    }

    private RelocatorCommand(AbstractC2178hm abstractC2178hm, C3945p c3945p, O2 o2, C0722p1 c0722p1, ClassFileConsumer classFileConsumer, int i2) {
        this.a = false;
        this.b = false;
        this.g = abstractC2178hm;
        this.f = c3945p;
        this.c = o2;
        this.d = c0722p1;
        this.e = classFileConsumer;
        this.h = i2;
    }
}
