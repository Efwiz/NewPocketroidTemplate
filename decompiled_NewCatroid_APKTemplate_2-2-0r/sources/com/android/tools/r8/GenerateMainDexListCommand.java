package com.android.tools.r8;

import com.android.tools.r8.experimental.graphinfo.GraphConsumer;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.shaking.N2;
import com.android.tools.r8.shaking.V2;
import com.android.tools.r8.shaking.W2;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.C3945p;
import com.android.tools.r8.utils.M1;
import com.android.tools.r8.utils.O2;
import com.android.tools.r8.utils.StringDiagnostic;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class GenerateMainDexListCommand extends BaseCommand {
    static final String j = String.join("\n", AbstractC1639am.a("Usage: maindex [options] <input-files>", " where <input-files> are JAR files", " and options are:", "  --lib <file>             # Add <file> as a library resource.", "  --main-dex-rules <file>  # Proguard keep rules for classes to place in the", "                           # primary dex file.", "  --main-dex-list <file>   # List of classes to place in the primary dex file.", "  --main-dex-list-output <file>  # Output the full main-dex list in <file>.", "  --version                # Print the version.", "  --help                   # Print this message."));
    static final /* synthetic */ boolean k = true;
    private final List e;
    private final StringConsumer f;
    private final GraphConsumer g;
    private final C0722p1 h;
    private final O2 i;

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class Builder extends BaseCommand.Builder<GenerateMainDexListCommand, Builder> {
        private final C0722p1 f;
        private final ArrayList g;
        private StringConsumer h;
        private GraphConsumer i;

        private Builder() {
            this.f = new C0722p1();
            this.g = new ArrayList();
            this.h = null;
            this.i = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(List list) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                this.g.add(new V2((Path) it.next()));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void c(Path[] pathArr) {
            for (Path path : pathArr) {
                this.g.add(new V2(path));
            }
        }

        public Builder addMainDexRules(final List<String> list, final Origin origin) {
            a(new Runnable() { // from class: com.android.tools.r8.GenerateMainDexListCommand$Builder$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.a(list, origin);
                }
            });
            return this;
        }

        public Builder addMainDexRulesFiles(final Path... pathArr) {
            a(new Runnable() { // from class: com.android.tools.r8.GenerateMainDexListCommand$Builder$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.c(pathArr);
                }
            });
            return this;
        }

        @Override // com.android.tools.r8.BaseCommand.Builder
        final BaseCommand.Builder d() {
            return this;
        }

        public Builder setMainDexKeptGraphConsumer(GraphConsumer graphConsumer) {
            this.i = graphConsumer;
            return this;
        }

        public Builder setMainDexListConsumer(StringConsumer stringConsumer) {
            this.h = stringConsumer;
            return this;
        }

        public Builder setMainDexListOutputPath(Path path) {
            this.h = new StringConsumer.FileConsumer(path);
            return this;
        }

        public Builder addMainDexRulesFiles(final List<Path> list) {
            a(new Runnable() { // from class: com.android.tools.r8.GenerateMainDexListCommand$Builder$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.a(list);
                }
            });
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(List list, Origin origin) {
            this.g.add(new W2(list, Paths.get(".", new String[0]), origin));
        }

        @Override // com.android.tools.r8.BaseCommand.Builder
        protected final BaseCommand c() {
            AbstractC1639am abstractC1639amA;
            if (!isPrintHelp() && !isPrintVersion()) {
                ArrayList arrayList = this.g;
                C0722p1 c0722p1 = this.f;
                O2 o2B = b();
                int i = N2.o;
                if (arrayList.isEmpty()) {
                    abstractC1639amA = AbstractC1639am.i();
                } else {
                    N2 n2 = new N2(c0722p1, o2B);
                    n2.a(arrayList);
                    abstractC1639amA = AbstractC1639am.a(n2.b().v());
                }
                return new GenerateMainDexListCommand(this.f, a().a(), abstractC1639amA, new M1(this.h), this.i, b());
            }
            return new GenerateMainDexListCommand(isPrintHelp(), isPrintVersion());
        }

        private Builder(DiagnosticsHandler diagnosticsHandler) {
            super(diagnosticsHandler);
            this.f = new C0722p1();
            this.g = new ArrayList();
            this.h = null;
            this.i = null;
        }
    }

    private GenerateMainDexListCommand(C0722p1 c0722p1, C3945p c3945p, AbstractC1639am abstractC1639am, M1 m1, GraphConsumer graphConsumer, O2 o2) {
        super(c3945p);
        this.h = c0722p1;
        this.e = abstractC1639am;
        this.f = m1;
        this.g = graphConsumer;
        this.i = o2;
    }

    private static void a(String[] strArr, Builder builder) {
        int i = 0;
        while (i < strArr.length) {
            String strTrim = strArr[i].trim();
            if (strTrim.length() != 0) {
                if (strTrim.equals("--help")) {
                    builder.setPrintHelp(true);
                } else if (strTrim.equals("--version")) {
                    builder.setPrintVersion(true);
                } else if (strTrim.equals("--lib")) {
                    i++;
                    builder.addLibraryFiles(Paths.get(strArr[i], new String[0]));
                } else if (strTrim.equals("--main-dex-rules")) {
                    i++;
                    builder.addMainDexRulesFiles(Paths.get(strArr[i], new String[0]));
                } else if (strTrim.equals("--main-dex-list")) {
                    i++;
                    builder.addMainDexListFiles(Paths.get(strArr[i], new String[0]));
                } else if (strTrim.equals("--main-dex-list-output")) {
                    i++;
                    builder.setMainDexListOutputPath(Paths.get(strArr[i], new String[0]));
                } else {
                    if (strTrim.startsWith("--")) {
                        builder.b().error(new StringDiagnostic("Unknown option: " + strTrim, com.android.tools.r8.origin.a.e));
                    }
                    builder.addProgramFiles(Paths.get(strTrim, new String[0]));
                }
            }
            i++;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder parse(String[] strArr) {
        Builder builder = builder();
        a(strArr, builder);
        return builder;
    }

    @Override // com.android.tools.r8.BaseCommand
    A1 b() {
        A1 a1 = new A1(this.h, this.i);
        a1.f = DexIndexedConsumer.emptyConsumer();
        a1.o1 = this.e;
        a1.w1 = this.f;
        a1.G1 = this.g;
        a1.p1 = a1.W0;
        a1.R = false;
        a1.P0().a = false;
        if (!k && !a1.v0) {
            throw new AssertionError();
        }
        a1.v0 = false;
        return a1;
    }

    final O2 c() {
        return this.i;
    }

    public StringConsumer getMainDexListConsumer() {
        return this.f;
    }

    public static Builder builder(DiagnosticsHandler diagnosticsHandler) {
        return new Builder(diagnosticsHandler);
    }

    private GenerateMainDexListCommand(boolean z, boolean z2) {
        super(z, z2);
        this.h = new C0722p1();
        this.e = AbstractC1639am.i();
        this.f = null;
        this.g = null;
        this.i = new O2();
    }
}
