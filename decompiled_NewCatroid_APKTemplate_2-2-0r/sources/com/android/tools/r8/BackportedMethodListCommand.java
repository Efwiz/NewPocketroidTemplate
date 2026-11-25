package com.android.tools.r8;

import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.internal.AbstractC1048Hm;
import com.android.tools.r8.internal.C1133Kk;
import com.android.tools.r8.internal.C1233Oe;
import com.android.tools.r8.internal.InterfaceC1206Ne;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.C3945p;
import com.android.tools.r8.utils.EnumC3891f;
import com.android.tools.r8.utils.O2;
import com.android.tools.r8.utils.StringDiagnostic;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class BackportedMethodListCommand {
    private final boolean a;
    private final boolean b;
    private final O2 c;
    private final int d;
    private final InterfaceC1206Ne e;
    private final C3945p f;
    private final StringConsumer g;
    private final C0722p1 h;

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class Builder {
        private final O2 a;
        private int b;
        private ArrayList c;
        private final C3945p.a d;
        private StringConsumer e;
        private boolean f;
        private boolean g;

        private Builder(DiagnosticsHandler diagnosticsHandler) {
            this.b = EnumC3891f.B.d();
            this.c = new ArrayList();
            this.f = false;
            this.g = false;
            this.d = C3945p.b();
            this.a = new O2(diagnosticsHandler);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void a(StringDiagnostic stringDiagnostic) {
            this.a.error(stringDiagnostic);
        }

        public Builder addDesugaredLibraryConfiguration(d0 d0Var) {
            this.c.add(d0Var);
            return this;
        }

        public Builder addLibraryFiles(Path... pathArr) {
            addLibraryFiles(Arrays.asList(pathArr));
            return this;
        }

        public Builder addLibraryResourceProvider(ClassFileResourceProvider classFileResourceProvider) {
            this.d.b(classFileResourceProvider);
            return this;
        }

        public BackportedMethodListCommand build() {
            InterfaceC1206Ne interfaceC1206NeA;
            C3945p c3945pA = this.d.a();
            if (!this.c.isEmpty() && c3945pA.i().isEmpty()) {
                this.a.error(new StringDiagnostic("With desugared library configuration a library is required"));
            }
            if (isPrintHelp() || isPrintVersion()) {
                return new BackportedMethodListCommand(isPrintHelp(), isPrintVersion());
            }
            if (this.e == null) {
                this.e = new C0589e();
            }
            C0722p1 c0722p1 = new C0722p1();
            O2 o2 = this.a;
            int i = this.b;
            if (this.c.isEmpty()) {
                interfaceC1206NeA = C1133Kk.g();
            } else {
                if (this.c.size() > 1) {
                    this.a.b("Only one desugared library configuration is supported.");
                }
                interfaceC1206NeA = C1233Oe.a((d0) this.c.get(0), c0722p1, null, false, getMinApiLevel());
            }
            return new BackportedMethodListCommand(o2, i, interfaceC1206NeA, c3945pA, this.e, c0722p1);
        }

        public int getMinApiLevel() {
            return this.b;
        }

        public boolean isPrintHelp() {
            return this.f;
        }

        public boolean isPrintVersion() {
            return this.g;
        }

        public Builder setConsumer(StringConsumer stringConsumer) {
            this.e = stringConsumer;
            return this;
        }

        public Builder setMinApiLevel(int i) {
            if (i <= 0) {
                this.a.error(new StringDiagnostic("Invalid minApiLevel: " + i));
            } else {
                this.b = i;
            }
            return this;
        }

        public Builder setOutputPath(Path path) {
            this.e = new C0405d(path);
            return this;
        }

        public Builder setPrintHelp(boolean z) {
            this.f = z;
            return this;
        }

        public Builder setPrintVersion(boolean z) {
            this.g = z;
            return this;
        }

        public Builder addDesugaredLibraryConfiguration(String str) {
            return addDesugaredLibraryConfiguration(d0.a(str, Origin.unknown()));
        }

        public Builder addLibraryFiles(Collection<Path> collection) {
            Iterator<Path> it = collection.iterator();
            while (it.hasNext()) {
                this.d.c(it.next());
            }
            return this;
        }
    }

    private BackportedMethodListCommand(boolean z, boolean z2) {
        this.a = z;
        this.b = z2;
        this.c = new O2();
        this.d = -1;
        this.e = null;
        this.f = null;
        this.g = null;
        this.h = null;
    }

    public static Builder builder() {
        return new Builder(new C0404c());
    }

    public static Builder parse(String[] strArr) {
        int i;
        String str;
        AbstractC1048Hm abstractC1048HmA = AbstractC1048Hm.a("--output", "--min-api", "--desugared-lib", "--lib");
        Builder builder = builder();
        boolean z = false;
        for (int i2 = 0; i2 < strArr.length; i2 = i + 1) {
            String strTrim = strArr[i2].trim();
            if (abstractC1048HmA.contains(strTrim)) {
                i = i2 + 1;
                if (i >= strArr.length) {
                    builder.a(new StringDiagnostic("Missing parameter for " + strArr[i2] + "."));
                    break;
                }
                str = strArr[i];
            } else {
                i = i2;
                str = null;
            }
            if (strTrim.equals("--help")) {
                builder.setPrintHelp(true);
            } else if (strTrim.equals("--version")) {
                builder.setPrintVersion(true);
            } else if (strTrim.equals("--min-api")) {
                if (z) {
                    builder.a(new StringDiagnostic("Cannot set multiple --min-api options"));
                } else {
                    a(builder, str);
                    z = true;
                }
            } else if (strTrim.equals("--desugared-lib")) {
                builder.addDesugaredLibraryConfiguration(d0.a(Paths.get(str, new String[0])));
            } else if (strTrim.equals("--lib")) {
                builder.addLibraryFiles(Paths.get(str, new String[0]));
            } else if (strTrim.equals("--output")) {
                builder.setOutputPath(Paths.get(str, new String[0]));
            } else {
                builder.a(new StringDiagnostic("Unknown option: " + strTrim));
            }
        }
        return builder;
    }

    final C3945p a() {
        return this.f;
    }

    final A1 b() {
        A1 a1 = new A1(this.h, this.c);
        a1.c(EnumC3891f.c(this.d));
        a1.a(this.e);
        return a1;
    }

    final O2 c() {
        return this.c;
    }

    public StringConsumer getBackportedMethodListConsumer() {
        return this.g;
    }

    public InterfaceC1206Ne getDesugaredLibraryConfiguration() {
        return this.e;
    }

    public int getMinApiLevel() {
        return this.d;
    }

    public boolean isPrintHelp() {
        return this.a;
    }

    public boolean isPrintVersion() {
        return this.b;
    }

    private static void a(Builder builder, String str) {
        try {
            int i = Integer.parseInt(str);
            if (i < 1) {
                builder.a(new StringDiagnostic("Invalid argument to --min-api: " + str));
            } else {
                builder.setMinApiLevel(i);
            }
        } catch (NumberFormatException unused) {
            builder.a(new StringDiagnostic("Invalid argument to --min-api: " + str));
        }
    }

    public static Builder builder(DiagnosticsHandler diagnosticsHandler) {
        return new Builder(diagnosticsHandler);
    }

    private BackportedMethodListCommand(O2 o2, int i, InterfaceC1206Ne interfaceC1206Ne, C3945p c3945p, StringConsumer stringConsumer, C0722p1 c0722p1) {
        this.a = false;
        this.b = false;
        this.c = o2;
        this.d = i;
        this.e = interfaceC1206Ne;
        this.f = c3945p;
        this.g = stringConsumer;
        this.h = c0722p1;
    }
}
