package com.android.tools.r8;

import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.C0931Dn;
import com.android.tools.r8.internal.C1036Hf;
import com.android.tools.r8.internal.InterfaceC1206Ne;
import com.android.tools.r8.naming.H0;
import com.android.tools.r8.naming.I0;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.shaking.N2;
import com.android.tools.r8.shaking.V2;
import com.android.tools.r8.shaking.W2;
import com.android.tools.r8.startup.StartupProfileProvider;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.A2;
import com.android.tools.r8.utils.C3945p;
import com.android.tools.r8.utils.C3991y1;
import com.android.tools.r8.utils.EnumC3891f;
import com.android.tools.r8.utils.O0;
import com.android.tools.r8.utils.O2;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class D8Command extends BaseCompilerCommand {
    static final /* synthetic */ boolean M = true;
    private final GlobalSyntheticsConsumer A;
    private final SyntheticInfoConsumer B;
    private final DesugarGraphConsumer C;
    private final StringConsumer D;
    private final InterfaceC1206Ne E;
    private final String F;
    private final boolean G;
    private final boolean H;
    private final AbstractC1639am I;
    private final StringConsumer J;
    private final boolean K;
    private final C0722p1 L;
    private final boolean z;

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class Builder extends BaseCompilerCommand.Builder<D8Command, Builder> {
        private boolean D;
        private GlobalSyntheticsConsumer E;
        private ArrayList F;
        private DesugarGraphConsumer G;
        private SyntheticInfoConsumer H;
        private StringConsumer I;
        private String J;
        private boolean K;
        private final ArrayList L;
        private boolean M;

        private Builder(DiagnosticsHandler diagnosticsHandler) {
            super(diagnosticsHandler);
            this.D = false;
            this.E = null;
            this.F = new ArrayList();
            this.G = null;
            this.H = null;
            this.I = null;
            this.J = "";
            this.K = true;
            this.L = new ArrayList();
            this.M = false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void e(Path path) {
            this.L.add(new V2(path));
        }

        final Builder a(String str) {
            this.J = str;
            return this;
        }

        @Override // com.android.tools.r8.BaseCommand.Builder
        public /* bridge */ /* synthetic */ BaseCommand.Builder addClasspathFiles(Collection collection) {
            return addClasspathFiles((Collection<Path>) collection);
        }

        public Builder addGlobalSyntheticsFiles(Path... pathArr) {
            return addGlobalSyntheticsFiles(Arrays.asList(pathArr));
        }

        public Builder addGlobalSyntheticsResourceProviders(GlobalSyntheticsResourceProvider... globalSyntheticsResourceProviderArr) {
            return addGlobalSyntheticsResourceProviders(Arrays.asList(globalSyntheticsResourceProviderArr));
        }

        public Builder addMainDexRules(final List<String> list, final Origin origin) {
            a(new Runnable() { // from class: com.android.tools.r8.D8Command$Builder$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.a(list, origin);
                }
            });
            return this;
        }

        public Builder addMainDexRulesFiles(Path... pathArr) {
            return addMainDexRulesFiles(Arrays.asList(pathArr));
        }

        @Override // com.android.tools.r8.BaseCompilerCommand.Builder
        public /* bridge */ /* synthetic */ BaseCompilerCommand.Builder addStartupProfileProviders(Collection collection) {
            return addStartupProfileProviders((Collection<StartupProfileProvider>) collection);
        }

        @Override // com.android.tools.r8.BaseCommand.Builder
        final BaseCommand.Builder d() {
            return this;
        }

        @Override // com.android.tools.r8.BaseCompilerCommand.Builder
        final CompilationMode f() {
            return CompilationMode.DEBUG;
        }

        public DesugarGraphConsumer getDesugarGraphConsumer() {
            return this.G;
        }

        public SyntheticInfoConsumer getSyntheticInfoConsumer() {
            return this.H;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.android.tools.r8.BaseCommand.Builder
        /* JADX INFO: renamed from: r, reason: merged with bridge method [inline-methods] */
        public final D8Command c() {
            AbstractC1639am abstractC1639amA;
            Object[] objArr = 0;
            if (isPrintHelp() || isPrintVersion()) {
                return new D8Command(isPrintHelp(), isPrintVersion());
            }
            this.D |= getProgramConsumer() instanceof DexFilePerClassFileConsumer;
            C0722p1 c0722p1 = new C0722p1();
            InterfaceC1206Ne interfaceC1206NeA = a(c0722p1, false);
            ArrayList arrayList = this.L;
            O2 o2B = b();
            int i = N2.o;
            if (arrayList.isEmpty()) {
                abstractC1639amA = AbstractC1639am.i();
            } else {
                N2 n2 = new N2(c0722p1, o2B);
                n2.a(arrayList);
                abstractC1639amA = AbstractC1639am.a(n2.b().v());
            }
            AbstractC1639am abstractC1639am = abstractC1639amA;
            if (!this.F.isEmpty()) {
                addProgramResourceProvider(new C3991y1(this.F));
            }
            int iD = ((getProgramConsumer() instanceof ClassFileConsumer) && getDisableDesugaring()) ? EnumC3891f.B.d() : getMinApiLevel();
            C3945p c3945pA = a().a();
            CompilationMode mode = getMode();
            ProgramConsumer programConsumer = getProgramConsumer();
            StringConsumer mainDexListConsumer = getMainDexListConsumer();
            O2 o2B2 = b();
            A1.g gVar = this.m;
            boolean z = this.D;
            return new D8Command(c3945pA, mode, programConsumer, mainDexListConsumer, iD, o2B2, gVar, z, z ? this.E : null, p(), getIncludeClassesChecksum(), getDexClassChecksumFilter(), getDesugarGraphConsumer(), getSyntheticInfoConsumer(), this.I, interfaceC1206NeA, h(), k(), this.J, this.K, abstractC1639am, m(), j(), getMapIdProvider(), this.t, this.M, getAndroidPlatformBuild(), g(), l(), i(), getCancelCompilationChecker(), c0722p1);
        }

        public Builder setDesugarGraphConsumer(DesugarGraphConsumer desugarGraphConsumer) {
            this.G = desugarGraphConsumer;
            return this;
        }

        public Builder setDesugaredLibraryKeepRuleConsumer(StringConsumer stringConsumer) {
            this.I = stringConsumer;
            return this;
        }

        public Builder setEnableExperimentalMissingLibraryApiModeling(boolean z) {
            this.M = z;
            return this;
        }

        public Builder setGlobalSyntheticsConsumer(GlobalSyntheticsConsumer globalSyntheticsConsumer) {
            this.E = globalSyntheticsConsumer;
            return this;
        }

        public Builder setIntermediate(boolean z) {
            this.D = z;
            return this;
        }

        public Builder setProguardInputMapFile(Path path) {
            a().e(path);
            return this;
        }

        public Builder setSyntheticInfoConsumer(SyntheticInfoConsumer syntheticInfoConsumer) {
            this.H = syntheticInfoConsumer;
            return this;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(List list, Origin origin) {
            this.L.add(new W2(list, Paths.get(".", new String[0]), origin));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void c(byte[] bArr, Origin origin) {
            a().b(bArr, origin);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void f(Collection collection) {
            collection.forEach(new Consumer() { // from class: com.android.tools.r8.D8Command$Builder$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.e((Path) obj);
                }
            });
        }

        @Override // com.android.tools.r8.BaseCommand.Builder
        public Builder addClasspathResourceProvider(ClassFileResourceProvider classFileResourceProvider) {
            return (Builder) super.addClasspathResourceProvider(classFileResourceProvider);
        }

        @Override // com.android.tools.r8.BaseCommand.Builder
        public Builder addDexProgramData(final byte[] bArr, final Origin origin) {
            a(new Runnable() { // from class: com.android.tools.r8.D8Command$Builder$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.c(bArr, origin);
                }
            });
            return this;
        }

        public Builder addGlobalSyntheticsFiles(Collection<Path> collection) {
            Iterator<Path> it = collection.iterator();
            while (it.hasNext()) {
                addGlobalSyntheticsResourceProviders(new L(it.next()));
            }
            return this;
        }

        public Builder addGlobalSyntheticsResourceProviders(Collection<GlobalSyntheticsResourceProvider> collection) {
            final ArrayList arrayList = this.F;
            Objects.requireNonNull(arrayList);
            collection.forEach(new Consumer() { // from class: com.android.tools.r8.D8Command$Builder$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    arrayList.add((GlobalSyntheticsResourceProvider) obj);
                }
            });
            return this;
        }

        public Builder addMainDexRulesFiles(final Collection<Path> collection) {
            a(new Runnable() { // from class: com.android.tools.r8.D8Command$Builder$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.f(collection);
                }
            });
            return this;
        }

        @Override // com.android.tools.r8.BaseCompilerCommand.Builder, com.android.tools.r8.BaseCommand.Builder
        final void e() {
            if (isPrintHelp()) {
                return;
            }
            O2 o2B = b();
            if (a().d()) {
                if (this.D) {
                    o2B.a("Option --main-dex-list cannot be used with --intermediate");
                }
                if (getProgramConsumer() instanceof DexFilePerClassFileConsumer) {
                    o2B.a("Option --main-dex-list cannot be used with --file-per-class");
                }
            }
            if (!this.L.isEmpty()) {
                if (this.D) {
                    o2B.a("Option --main-dex-rules cannot be used with --intermediate");
                }
                if (getProgramConsumer() instanceof DexFilePerClassFileConsumer) {
                    o2B.a("Option --main-dex-rules cannot be used with --file-per-class");
                }
            }
            if (getMainDexListConsumer() != null && this.L.isEmpty() && !a().d()) {
                o2B.a("Option --main-dex-list-output requires --main-dex-rules and/or --main-dex-list");
            }
            int minApiLevel = getMinApiLevel();
            EnumC3891f enumC3891f = EnumC3891f.L;
            if (minApiLevel >= enumC3891f.d() && (getMainDexListConsumer() != null || a().d())) {
                o2B.a("D8 does not support main-dex inputs and outputs when compiling to API level " + enumC3891f.d() + " and above");
            }
            if (n() && getDisableDesugaring()) {
                o2B.a("Using desugared library configuration requires desugaring to be enabled");
            }
            if ((getProgramConsumer() instanceof ClassFileConsumer) && getDisableDesugaring() && o()) {
                o2B.a("Compiling to CF with --min-api and --no-desugaring is not supported");
            }
            if (!((ArrayList) l()).isEmpty()) {
                if (this.D) {
                    o2B.a("D8 startup layout is not supported in intermediate mode");
                }
                if (getMinApiLevel() < enumC3891f.d()) {
                    o2B.a("D8 startup layout requires native multi dex support (API level " + enumC3891f.d() + " and above)");
                }
            }
            super.e();
        }

        @Override // com.android.tools.r8.BaseCompilerCommand.Builder
        public Builder setProguardMapConsumer(StringConsumer stringConsumer) {
            return (Builder) super.setProguardMapConsumer(stringConsumer);
        }

        public Builder setProguardMapOutputPath(Path path) {
            if (BaseCompilerCommand.Builder.C || path != null) {
                return (Builder) setProguardMapConsumer((StringConsumer) new StringConsumer.FileConsumer(path));
            }
            throw new AssertionError();
        }

        @Override // com.android.tools.r8.BaseCommand.Builder
        public Builder addClasspathFiles(Path... pathArr) {
            return (Builder) super.addClasspathFiles(pathArr);
        }

        @Override // com.android.tools.r8.BaseCompilerCommand.Builder
        public Builder addStartupProfileProviders(Collection<StartupProfileProvider> collection) {
            return (Builder) super.addStartupProfileProviders((Collection) collection);
        }

        @Override // com.android.tools.r8.BaseCommand.Builder
        public Builder addClasspathFiles(Collection<Path> collection) {
            return (Builder) super.addClasspathFiles(collection);
        }

        public Builder addStartupProfileProviders(StartupProfileProvider... startupProfileProviderArr) {
            return (Builder) addStartupProfileProviders((Collection) Arrays.asList(startupProfileProviderArr));
        }

        private Builder(C3945p c3945p) {
            super(c3945p);
            this.D = false;
            this.E = null;
            this.F = new ArrayList();
            this.G = null;
            this.H = null;
            this.I = null;
            this.J = "";
            this.K = true;
            this.L = new ArrayList();
            this.M = false;
        }
    }

    private D8Command(C3945p c3945p, CompilationMode compilationMode, ProgramConsumer programConsumer, StringConsumer stringConsumer, int i, O2 o2, A1.g gVar, boolean z, GlobalSyntheticsConsumer globalSyntheticsConsumer, boolean z2, boolean z3, BiPredicate biPredicate, DesugarGraphConsumer desugarGraphConsumer, SyntheticInfoConsumer syntheticInfoConsumer, StringConsumer stringConsumer2, InterfaceC1206Ne interfaceC1206Ne, List list, List list2, String str, boolean z4, AbstractC1639am abstractC1639am, int i2, O0 o0, MapIdProvider mapIdProvider, StringConsumer stringConsumer3, boolean z5, boolean z6, List list3, List list4, ClassConflictResolver classConflictResolver, CancelCompilationChecker cancelCompilationChecker, C0722p1 c0722p1) {
        super(c3945p, compilationMode, programConsumer, stringConsumer, i, o2, gVar, z2, z3, biPredicate, list, list2, i2, o0, mapIdProvider, null, z6, list3, list4, classConflictResolver, cancelCompilationChecker);
        this.z = z;
        this.A = globalSyntheticsConsumer;
        this.B = syntheticInfoConsumer;
        this.C = desugarGraphConsumer;
        this.D = stringConsumer2;
        this.E = interfaceC1206Ne;
        this.F = str;
        this.G = z4;
        this.H = false;
        this.I = abstractC1639am;
        this.J = stringConsumer3;
        this.K = z5;
        this.L = c0722p1;
    }

    static Builder a(C3945p c3945p) {
        return new Builder(c3945p);
    }

    public static Builder builder() {
        return new Builder(new C3602n());
    }

    public static List<ParseFlagInfo> getParseFlagsInformation() {
        return C3675p.a();
    }

    public static Builder parse(String[] strArr, Origin origin) {
        return C3675p.a(strArr, origin);
    }

    @Override // com.android.tools.r8.BaseCommand
    A1 b() {
        I0 i0A;
        A1 a1 = new A1(this.L, g());
        boolean z = M;
        if (!z && a1.W0) {
            throw new AssertionError();
        }
        a1.W0 = getMode() == CompilationMode.DEBUG;
        ProgramConsumer programConsumer = getProgramConsumer();
        a1.f = programConsumer;
        if (programConsumer instanceof ClassFileConsumer) {
            if (!z && !a1.O) {
                throw new AssertionError();
            }
            a1.O = false;
            if (!z && !a1.P) {
                throw new AssertionError();
            }
            a1.P = false;
        } else if (!z && a1.i0().b && e() != A1.g.OFF) {
            throw new AssertionError();
        }
        a1.w1 = getMainDexListConsumer();
        a1.p1 = a1.W0 || this.H;
        a1.L0 = this.G;
        a1.c(EnumC3891f.c(getMinApiLevel()));
        boolean z2 = this.z;
        a1.t0 = z2;
        a1.v0 = z2;
        a1.a(this.A);
        a1.a(this.B);
        a1.H1 = this.C;
        a1.o1 = this.I;
        StringConsumer stringConsumer = this.J;
        if (stringConsumer == null) {
            i0A = null;
        } else {
            int i = I0.d;
            i0A = new H0().a(stringConsumer).a(g()).a();
        }
        a1.x1 = i0A;
        a1.r1 = (a1.W0 || this.J == null) ? A1.j.a : A1.j.b;
        a1.t1().a = true;
        if (!z && a1.o1()) {
            throw new AssertionError();
        }
        if (!z && a1.k1()) {
            throw new AssertionError();
        }
        if (!z && a1.o) {
            throw new AssertionError();
        }
        a1.o = true;
        if (!z && a1.P0().a) {
            throw new AssertionError();
        }
        if (!z && a1.y) {
            throw new AssertionError();
        }
        if (!z && a1.t) {
            throw new AssertionError();
        }
        if (!z && a1.R) {
            throw new AssertionError();
        }
        if (!z && a1.T.a) {
            throw new AssertionError();
        }
        if (!z && a1.G) {
            throw new AssertionError();
        }
        a1.A0 = e();
        a1.H = getIncludeClassesChecksum();
        a1.I = getDexClassChecksumFilter();
        a1.q1 = isOptimizeMultidexForLinearAlloc();
        a1.a(this.E);
        a1.Z = this.F.isEmpty() ? System.getProperty("com.android.tools.r8.synthesizedClassPrefix", "") : this.F;
        a1.E1 = this.D;
        if (!this.K) {
            a1.a().a();
            a1.a().c();
        }
        if (!z && a1.T0 != null) {
            throw new AssertionError();
        }
        a1.T0 = new com.android.tools.r8.utils.D(getAssertionsConfiguration(), AssertionsConfiguration.a(g()).setCompileTimeDisable().setScopeAll().build());
        a1.l = C0931Dn.a(getOutputInspections());
        if (!A1.O1) {
            if (!z && a1.a0 != -1) {
                throw new AssertionError();
            }
            a1.a0 = getThreadCount();
        }
        a1.l0();
        A1.h hVarO0 = a1.O0();
        if (a1.Y0()) {
            hVarO0.m();
        } else {
            if (!z && !(a1.f instanceof ClassFileConsumer)) {
                throw new AssertionError();
            }
            hVarO0.a();
        }
        a1.a(getAndroidPlatformBuild());
        a1.q0().a(c());
        if (!h().isEmpty()) {
            a1.G0().a(h());
        }
        a1.g = A2.a(d(), a1.e);
        a1.c = getCancelCompilationChecker();
        com.android.tools.r8.dex.U.b bVar = com.android.tools.r8.dex.U.b.D8;
        a1.m0 = bVar;
        a1.a(f());
        C1036Hf.a aVarC = C1036Hf.a(bVar).c();
        a(aVarC);
        a1.l0 = aVarC.c(this.z).a(this.E).b(this.I).a(this.K).a();
        return a1;
    }

    public static Builder parse(String[] strArr, Origin origin, DiagnosticsHandler diagnosticsHandler) {
        return C3675p.a(strArr, origin, diagnosticsHandler);
    }

    public static Builder builder(DiagnosticsHandler diagnosticsHandler) {
        return new Builder(diagnosticsHandler);
    }

    private D8Command(boolean z, boolean z2) {
        super(z, z2);
        this.z = false;
        this.A = null;
        this.B = null;
        this.C = null;
        this.D = null;
        this.E = null;
        this.F = null;
        this.G = true;
        this.H = false;
        this.I = null;
        this.J = null;
        this.K = false;
        this.L = null;
    }
}
