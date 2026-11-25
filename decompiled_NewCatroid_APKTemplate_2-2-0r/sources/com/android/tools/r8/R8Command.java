package com.android.tools.r8;

import com.android.tools.r8.experimental.graphinfo.GraphConsumer;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.BY;
import com.android.tools.r8.internal.C0931Dn;
import com.android.tools.r8.internal.C1036Hf;
import com.android.tools.r8.internal.C2422kw;
import com.android.tools.r8.internal.C2736ov;
import com.android.tools.r8.internal.C2738ox;
import com.android.tools.r8.internal.C3451yh;
import com.android.tools.r8.internal.InterfaceC1206Ne;
import com.android.tools.r8.internal.InterfaceC3442yY;
import com.android.tools.r8.naming.H0;
import com.android.tools.r8.naming.I0;
import com.android.tools.r8.naming.U0;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.origin.PathOrigin;
import com.android.tools.r8.shaking.J0;
import com.android.tools.r8.shaking.L2;
import com.android.tools.r8.shaking.N2;
import com.android.tools.r8.shaking.Q2;
import com.android.tools.r8.shaking.V2;
import com.android.tools.r8.shaking.W2;
import com.android.tools.r8.startup.StartupProfileProvider;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.A2;
import com.android.tools.r8.utils.ArchiveResourceProvider;
import com.android.tools.r8.utils.C3945p;
import com.android.tools.r8.utils.EnumC3891f;
import com.android.tools.r8.utils.ExceptionDiagnostic;
import com.android.tools.r8.utils.O0;
import com.android.tools.r8.utils.O2;
import com.android.tools.r8.utils.S2;
import com.android.tools.r8.utils.StringDiagnostic;
import com.android.tools.r8.utils.T2;
import com.android.tools.r8.utils.X0;
import com.android.tools.r8.utils.Y2;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class R8Command extends BaseCompilerCommand {
    static final /* synthetic */ boolean S = true;
    private final L2 A;
    private final boolean B;
    private final boolean C;
    private final boolean D;
    private final boolean E;
    private final Optional F;
    private final StringConsumer G;
    private final StringConsumer H;
    private final StringConsumer I;
    private final StringConsumer J;
    private final GraphConsumer K;
    private final GraphConsumer L;
    private final Consumer M;
    private final StringConsumer N;
    private final InterfaceC1206Ne O;
    private final C3451yh P;
    private final String Q;
    private final boolean R;
    private final List z;

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class Builder extends BaseCompilerCommand.Builder<R8Command, Builder> {
        static final /* synthetic */ boolean Z = true;
        private final ArrayList D;
        private Consumer E;
        private Consumer F;
        private StringConsumer G;
        private final ArrayList H;
        private boolean I;
        private boolean J;
        private boolean K;
        private boolean L;
        private Optional M;
        private StringConsumer N;
        private StringConsumer O;
        private StringConsumer P;
        private GraphConsumer Q;
        private GraphConsumer R;
        private InputDependencyGraphConsumer S;
        private final ArrayList T;
        private String U;
        private boolean V;
        private boolean W;
        private S2 X;
        private final Q2.a Y;

        Builder() {
            this(new V());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void b(List list, Origin origin) {
            this.H.add(new W2(list, Paths.get(".", new String[0]), origin));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void e(Path path) {
            this.D.add(new V2(path));
        }

        final Builder a(String str) {
            this.U = str;
            return this;
        }

        public Builder addFeatureSplit(Function<FeatureSplit.Builder, FeatureSplit> function) {
            FeatureSplit featureSplitApply = function.apply(FeatureSplit.a(b()));
            this.T.add(featureSplitApply);
            Iterator<ProgramResourceProvider> it = featureSplitApply.getProgramResourceProviders().iterator();
            while (it.hasNext()) {
                addProgramResourceProvider((ProgramResourceProvider) new U(it.next()));
            }
            return this;
        }

        public Builder addMainDexRules(final List<String> list, final Origin origin) {
            a(new Runnable() { // from class: com.android.tools.r8.R8Command$Builder$$ExternalSyntheticLambda4
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

        public Builder addProguardConfiguration(final List<String> list, final Origin origin) {
            a(new Runnable() { // from class: com.android.tools.r8.R8Command$Builder$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.b(list, origin);
                }
            });
            return this;
        }

        public Builder addProguardConfigurationFiles(final Path... pathArr) {
            a(new Runnable() { // from class: com.android.tools.r8.R8Command$Builder$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.c(pathArr);
                }
            });
            return this;
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
            return CompilationMode.RELEASE;
        }

        public boolean getProguardCompatibility() {
            return this.L;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.android.tools.r8.BaseCommand.Builder
        /* JADX INFO: renamed from: r, reason: merged with bridge method [inline-methods] */
        public final R8Command c() {
            AbstractC1639am abstractC1639amA;
            Object[] objArr = 0;
            if (isPrintHelp() || isPrintVersion()) {
                return new R8Command(isPrintHelp(), isPrintVersion());
            }
            O2 o2B = b();
            C0722p1 c0722p1 = new C0722p1();
            ArrayList arrayList = this.D;
            int i = N2.o;
            if (arrayList.isEmpty()) {
                abstractC1639amA = AbstractC1639am.i();
            } else {
                N2 n2 = new N2(c0722p1, o2B);
                n2.a(arrayList);
                abstractC1639amA = AbstractC1639am.a(n2.b().v());
            }
            AbstractC1639am abstractC1639am = abstractC1639amA;
            InterfaceC1206Ne interfaceC1206NeA = a(c0722p1, false);
            N2 n3 = new N2(c0722p1, o2B, this.Y.a(), this.S);
            if (!this.H.isEmpty()) {
                n3.a(this.H);
            }
            L2.a aVarD = n3.d();
            aVarD.a(this.L);
            if (getMode() == CompilationMode.DEBUG) {
                this.J = true;
                aVarD.d();
            }
            if (this.I) {
                aVarD.e();
            }
            if (this.J) {
                aVarD.c();
            }
            Consumer consumer = this.E;
            if (consumer != null) {
                consumer.accept(aVarD);
            }
            a(o2B, n3);
            a(n3);
            L2 l2A = aVarD.a();
            a().d(l2A.k());
            if (!Z && getProgramConsumer() == null) {
                throw new AssertionError();
            }
            R8Command r8Command = new R8Command(a().a(), getProgramConsumer(), abstractC1639am, getMainDexListConsumer(), l2A, getMode(), getMinApiLevel(), o2B, getProgramConsumer() instanceof ClassFileConsumer ? A1.g.OFF : this.m, l2A.L(), l2A.E(), this.K, this.L, this.M, this.t, this.N, this.O, this.P, this.Q, this.R, this.F, p(), getIncludeClassesChecksum(), getDexClassChecksumFilter(), this.G, interfaceC1206NeA, this.T.isEmpty() ? null : new C3451yh(this.T), h(), k(), this.U, m(), j(), getMapIdProvider(), getSourceFileProvider(), this.V, getAndroidPlatformBuild(), g(), l(), i(), getCancelCompilationChecker());
            InputDependencyGraphConsumer inputDependencyGraphConsumer = this.S;
            if (inputDependencyGraphConsumer != null) {
                inputDependencyGraphConsumer.finished();
            }
            return r8Command;
        }

        void s() {
            this.Y.a(true);
        }

        public Builder setDesugaredLibraryKeepRuleConsumer(StringConsumer stringConsumer) {
            this.G = stringConsumer;
            return this;
        }

        public Builder setDisableMinification(boolean z) {
            this.J = z;
            return this;
        }

        public Builder setDisableTreeShaking(boolean z) {
            this.I = z;
            return this;
        }

        public Builder setEnableExperimentalKeepAnnotations(boolean z) {
            this.W = z;
            return this;
        }

        public Builder setEnableExperimentalMissingLibraryApiModeling(boolean z) {
            this.V = z;
            return this;
        }

        public Builder setInputDependencyGraphConsumer(InputDependencyGraphConsumer inputDependencyGraphConsumer) {
            this.S = inputDependencyGraphConsumer;
            return this;
        }

        public Builder setKeptGraphConsumer(GraphConsumer graphConsumer) {
            this.Q = graphConsumer;
            return this;
        }

        public Builder setMainDexKeptGraphConsumer(GraphConsumer graphConsumer) {
            this.R = graphConsumer;
            return this;
        }

        public Builder setProguardCompatibility(boolean z) {
            this.L = z;
            return this;
        }

        public Builder setProguardConfigurationConsumer(StringConsumer stringConsumer) {
            this.P = stringConsumer;
            return this;
        }

        public Builder setProguardSeedsConsumer(StringConsumer stringConsumer) {
            this.O = stringConsumer;
            return this;
        }

        public Builder setProguardUsageConsumer(StringConsumer stringConsumer) {
            this.N = stringConsumer;
            return this;
        }

        void t() {
            this.Y.b(true);
        }

        void u() {
            this.Y.c(true);
        }

        void v() {
            this.Y.d(true);
        }

        Builder(DiagnosticsHandler diagnosticsHandler) {
            super(diagnosticsHandler);
            this.D = new ArrayList();
            this.E = null;
            this.F = null;
            this.G = null;
            this.H = new ArrayList();
            this.I = false;
            this.J = false;
            this.K = false;
            this.L = false;
            this.M = Optional.empty();
            this.N = null;
            this.O = null;
            this.P = null;
            this.Q = null;
            this.R = null;
            this.S = null;
            this.T = new ArrayList();
            this.U = "";
            this.V = false;
            this.W = false;
            this.X = null;
            this.Y = Q2.a().b();
            b(!(System.getProperty("com.android.tools.r8.allowDexInputToR8") != null));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void f(Collection collection) {
            collection.forEach(new Consumer() { // from class: com.android.tools.r8.R8Command$Builder$$ExternalSyntheticLambda6
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.e((Path) obj);
                }
            });
        }

        Builder a(S2 s2) {
            this.X = s2;
            return this;
        }

        public Builder addMainDexRulesFiles(final Collection<Path> collection) {
            a(new Runnable() { // from class: com.android.tools.r8.R8Command$Builder$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.f(collection);
                }
            });
            return this;
        }

        @Override // com.android.tools.r8.BaseCommand.Builder
        public Builder addProgramResourceProvider(ProgramResourceProvider programResourceProvider) {
            return (Builder) super.addProgramResourceProvider((ProgramResourceProvider) new W(programResourceProvider));
        }

        public Builder addProguardConfigurationFiles(final List<Path> list) {
            a(new Runnable() { // from class: com.android.tools.r8.R8Command$Builder$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.a(list);
                }
            });
            return this;
        }

        final void c(boolean z) {
            this.K = z;
        }

        @Override // com.android.tools.r8.BaseCompilerCommand.Builder, com.android.tools.r8.BaseCommand.Builder
        final void e() {
            if (isPrintHelp()) {
                return;
            }
            O2 o2B = b();
            if (getProgramConsumer() instanceof DexFilePerClassFileConsumer) {
                o2B.a("R8 does not support compiling to a single DEX file per Java class file");
            }
            if (getMainDexListConsumer() != null && this.D.isEmpty() && !a().d()) {
                o2B.a("Option --main-dex-list-output requires --main-dex-rules and/or --main-dex-list");
            }
            if (!(getProgramConsumer() instanceof ClassFileConsumer)) {
                int minApiLevel = getMinApiLevel();
                EnumC3891f enumC3891f = EnumC3891f.L;
                if (minApiLevel >= enumC3891f.d() && (getMainDexListConsumer() != null || !this.D.isEmpty() || a().d())) {
                    o2B.a("R8 does not support main-dex inputs and outputs when compiling to API level " + enumC3891f.d() + " and above");
                }
            }
            for (FeatureSplit featureSplit : this.T) {
                if (!Z && !(featureSplit.getProgramConsumer() instanceof DexIndexedConsumer)) {
                    throw new AssertionError();
                }
                if (!(getProgramConsumer() instanceof DexIndexedConsumer)) {
                    o2B.a("R8 does not support class file output when using feature splits");
                }
            }
            for (Path path : this.e) {
                if (X0.d(path)) {
                    o2B.error(new StringDiagnostic("R8 does not support compiling DEX inputs", new PathOrigin(path)));
                }
            }
            if ((getProgramConsumer() instanceof ClassFileConsumer) && o()) {
                o2B.a("R8 does not support --min-api when compiling to class files");
            }
            if (n() && getDisableDesugaring()) {
                o2B.a("Using desugared library configuration requires desugaring to be enabled");
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

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(List list, Origin origin) {
            this.D.add(new W2(list, Paths.get(".", new String[0]), origin));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void c(Path[] pathArr) {
            for (Path path : pathArr) {
                this.H.add(new V2(path));
            }
        }

        @Override // com.android.tools.r8.BaseCompilerCommand.Builder
        public Builder addStartupProfileProviders(Collection<StartupProfileProvider> collection) {
            return (Builder) super.addStartupProfileProviders((Collection) collection);
        }

        @Override // com.android.tools.r8.BaseCompilerCommand.Builder
        public Builder setOutput(Path path, OutputMode outputMode) {
            setOutput(path, outputMode, true);
            return this;
        }

        public Builder addStartupProfileProviders(StartupProfileProvider... startupProfileProviderArr) {
            return (Builder) addStartupProfileProviders((Collection) Arrays.asList(startupProfileProviderArr));
        }

        void b(Consumer<List<com.android.tools.r8.shaking.S2>> consumer) {
            Consumer consumer2 = this.F;
            if (consumer2 != null) {
                consumer = consumer2.andThen(consumer);
            }
            this.F = consumer;
        }

        @Override // com.android.tools.r8.BaseCompilerCommand.Builder
        public Builder setOutput(Path path, OutputMode outputMode, boolean z) {
            this.M = Optional.of(Boolean.valueOf(z));
            return (Builder) super.setOutput(path, outputMode, z);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(List list) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                this.H.add(new V2((Path) it.next()));
            }
        }

        @Override // com.android.tools.r8.BaseCompilerCommand.Builder
        protected final N a(Path path, OutputMode outputMode, boolean z) {
            return super.a(path, outputMode, z);
        }

        private void a(final O2 o2, N2 n2) {
            ArchiveResourceProvider archiveResourceProviderA;
            Set setB = T2.b(new J0[0]);
            ArrayDeque arrayDeque = new ArrayDeque(a().b());
            while (true) {
                for (J0 j0 : n2.d().h()) {
                    if (setB.add(j0) && (archiveResourceProviderA = a().a(j0)) != null) {
                        arrayDeque.add(archiveResourceProviderA);
                    }
                }
                if (arrayDeque.isEmpty()) {
                    return;
                }
                InterfaceC3442yY interfaceC3442yYA = BY.a(new InterfaceC3442yY() { // from class: com.android.tools.r8.R8Command$Builder$$ExternalSyntheticLambda2
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return this.f$0.a(o2);
                    }
                });
                while (!arrayDeque.isEmpty()) {
                    DataResourceProvider dataResourceProvider = ((ProgramResourceProvider) arrayDeque.pop()).getDataResourceProvider();
                    if (dataResourceProvider != null) {
                        try {
                            X x = new X(o2, interfaceC3442yYA);
                            dataResourceProvider.accept(x);
                            x.a(n2);
                        } catch (ResourceException e) {
                            o2.error(new ExceptionDiagnostic(e));
                        }
                    }
                }
            }
        }

        private Builder(C3945p c3945p) {
            super(c3945p);
            this.D = new ArrayList();
            this.E = null;
            this.F = null;
            this.G = null;
            this.H = new ArrayList();
            this.I = false;
            this.J = false;
            this.K = false;
            this.L = false;
            this.M = Optional.empty();
            this.N = null;
            this.O = null;
            this.P = null;
            this.Q = null;
            this.R = null;
            this.S = null;
            this.T = new ArrayList();
            this.U = "";
            this.V = false;
            this.W = false;
            this.X = null;
            this.Y = Q2.a().b();
            b(!(System.getProperty("com.android.tools.r8.allowDexInputToR8") != null));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ S2 a(O2 o2) {
            S2 s2A = this.X;
            if (s2A == null) {
                s2A = S2.a(Version.getMajorVersion(), Version.getMinorVersion(), Version.getPatchVersion());
            }
            if (s2A.a() >= 0) {
                return s2A;
            }
            S2 s2A2 = S2.a(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
            o2.c("Running R8 version " + Version.getVersionString() + ", which cannot be represented as a semantic version. Using an artificial version newer than any known version for selecting Proguard configurations embedded under META-INF/. This means that all rules with a '-upto-' qualifier will be excluded and all rules with a -from- qualifier will be included.");
            return s2A2;
        }

        private Builder(C3945p c3945p, DiagnosticsHandler diagnosticsHandler) {
            super(c3945p, diagnosticsHandler);
            this.D = new ArrayList();
            this.E = null;
            this.F = null;
            this.G = null;
            this.H = new ArrayList();
            this.I = false;
            this.J = false;
            this.K = false;
            this.L = false;
            this.M = Optional.empty();
            this.N = null;
            this.O = null;
            this.P = null;
            this.Q = null;
            this.R = null;
            this.S = null;
            this.T = new ArrayList();
            this.U = "";
            this.V = false;
            this.W = false;
            this.X = null;
            this.Y = Q2.a().b();
            b(!(System.getProperty("com.android.tools.r8.allowDexInputToR8") != null));
        }

        private void a(final N2 n2) {
            if (this.W) {
                try {
                    Iterator it = a().b().iterator();
                    while (it.hasNext()) {
                        for (final ProgramResource programResource : ((ProgramResourceProvider) it.next()).getProgramResources()) {
                            if (programResource.getKind() == ProgramResource.Kind.CF) {
                                Set<C2736ov> setA = C2422kw.a(programResource.getBytes());
                                final C2738ox c2738ox = new C2738ox(new Consumer() { // from class: com.android.tools.r8.R8Command$Builder$$ExternalSyntheticLambda7
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        R8Command.Builder.a(programResource, n2, (String) obj);
                                    }
                                });
                                setA.forEach(new Consumer() { // from class: com.android.tools.r8.R8Command$Builder$$ExternalSyntheticLambda8
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        c2738ox.b((C2736ov) obj);
                                    }
                                });
                            }
                        }
                    }
                } catch (ResourceException e) {
                    a().c().a(new ExceptionDiagnostic(e));
                    throw null;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void a(ProgramResource programResource, N2 n2, String str) {
            n2.a(new W2(Collections.singletonList(str), null, programResource.getOrigin()));
        }

        void a(final Consumer<L2.a> consumer) {
            final Consumer consumer2 = this.E;
            this.E = new Consumer() { // from class: com.android.tools.r8.R8Command$Builder$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    R8Command.Builder.a(consumer2, consumer, (L2.a) obj);
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void a(Consumer consumer, Consumer consumer2, L2.a aVar) {
            if (consumer != null) {
                consumer.accept(aVar);
            }
            consumer2.accept(aVar);
        }
    }

    private R8Command(C3945p c3945p, ProgramConsumer programConsumer, AbstractC1639am abstractC1639am, StringConsumer stringConsumer, L2 l2, CompilationMode compilationMode, int i, O2 o2, A1.g gVar, boolean z, boolean z2, boolean z3, boolean z4, Optional optional, StringConsumer stringConsumer2, StringConsumer stringConsumer3, StringConsumer stringConsumer4, StringConsumer stringConsumer5, GraphConsumer graphConsumer, GraphConsumer graphConsumer2, Consumer consumer, boolean z5, boolean z6, BiPredicate biPredicate, StringConsumer stringConsumer6, InterfaceC1206Ne interfaceC1206Ne, C3451yh c3451yh, List list, List list2, String str, int i2, O0 o0, MapIdProvider mapIdProvider, SourceFileProvider sourceFileProvider, boolean z7, boolean z8, List list3, List list4, ClassConflictResolver classConflictResolver, CancelCompilationChecker cancelCompilationChecker) {
        AbstractC1639am abstractC1639am2;
        super(c3945p, compilationMode, programConsumer, stringConsumer, i, o2, gVar, z5, z6, biPredicate, list, list2, i2, o0, mapIdProvider, sourceFileProvider, z8, list3, list4, classConflictResolver, cancelCompilationChecker);
        if (S) {
            abstractC1639am2 = abstractC1639am;
        } else {
            abstractC1639am2 = abstractC1639am;
            if (abstractC1639am2 == null) {
                throw new AssertionError();
            }
        }
        this.z = abstractC1639am2;
        this.A = l2;
        this.B = z;
        this.C = z2;
        this.D = z3;
        this.E = z4;
        this.F = optional;
        this.G = stringConsumer2;
        this.H = stringConsumer3;
        this.I = stringConsumer4;
        this.J = stringConsumer5;
        this.K = graphConsumer;
        this.L = graphConsumer2;
        this.M = consumer;
        this.N = stringConsumer6;
        this.O = interfaceC1206Ne;
        this.P = c3451yh;
        this.Q = str;
        this.R = z7;
    }

    static Builder a(C3945p c3945p) {
        return new Builder(c3945p);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static List<ParseFlagInfo> getParseFlagsInformation() {
        return AbstractC1639am.a(a0.a());
    }

    static String i() {
        int i = a0.d;
        StringBuilder sb = new StringBuilder();
        Y2.a(sb, "Usage: r8 [options] [@<argfile>] <input-files>", " where <input-files> are any combination class, zip, or jar files", " and each <argfile> is a file containing additional arguments (one per line)", " and options are:");
        new ParseFlagPrinter().addFlags(AbstractC1639am.a(a0.a())).appendLinesToBuilder(sb);
        return sb.toString();
    }

    public static Builder parse(String[] strArr, Origin origin) {
        return a0.a(strArr, origin);
    }

    @Override // com.android.tools.r8.BaseCommand
    A1 b() {
        I0 i0A;
        A1 a1 = new A1(getMode(), this.A, g());
        boolean z = S;
        if (!z && a1.n1.B0) {
            throw new AssertionError();
        }
        a1.f = getProgramConsumer();
        a1.c(EnumC3891f.c(getMinApiLevel()));
        a1.A0 = e();
        if (!z && a1.o1() != getEnableTreeShaking()) {
            throw new AssertionError();
        }
        if (!z && a1.k1() != getEnableMinification()) {
            throw new AssertionError();
        }
        if (!z && a1.O0) {
            throw new AssertionError();
        }
        a1.O0 = this.A.C() || !(!this.E || a1.l1() || a1.o1() || a1.k1());
        a1.o1 = this.z;
        a1.p1 = a1.W0;
        a1.w1 = getMainDexListConsumer();
        a1.r1 = (a1.l1() || a1.k1()) ? A1.j.b : A1.j.a;
        A1.h hVarO0 = a1.O0();
        if (!z && !a1.l1() && !hVarO0.g()) {
            throw new AssertionError();
        }
        if (!z && a1.G) {
            throw new AssertionError();
        }
        if (!z && !a1.t && a1.l1()) {
            throw new AssertionError();
        }
        if (!a1.o1()) {
            a1.w = false;
            a1.t = false;
        }
        StringConsumer fileConsumer = this.G;
        boolean zH = this.A.H();
        Path pathS = this.A.s();
        if (zH) {
            fileConsumer = pathS != null ? new StringConsumer.FileConsumer(pathS, fileConsumer) : new Y(fileConsumer);
        }
        if (fileConsumer == null) {
            i0A = null;
        } else {
            int i = I0.d;
            i0A = new H0().a(fileConsumer).a(g()).a();
        }
        a1.x1 = i0A;
        StringConsumer fileConsumer2 = this.H;
        boolean zJ = this.A.J();
        Path pathT = this.A.t();
        if (zJ) {
            fileConsumer2 = pathT != null ? new StringConsumer.FileConsumer(pathT, fileConsumer2) : new Y(fileConsumer2);
        }
        a1.y1 = fileConsumer2;
        StringConsumer fileConsumer3 = this.I;
        boolean zI = this.A.I();
        Path pathW = this.A.w();
        if (zI) {
            fileConsumer3 = pathW != null ? new StringConsumer.FileConsumer(pathW, fileConsumer3) : new Y(fileConsumer3);
        }
        a1.z1 = fileConsumer3;
        StringConsumer fileConsumer4 = this.J;
        boolean zG = this.A.G();
        Path pathR = this.A.r();
        if (zG) {
            fileConsumer4 = pathR != null ? new StringConsumer.FileConsumer(pathR, fileConsumer4) : new Y(fileConsumer4);
        }
        a1.A1 = fileConsumer4;
        a1.F1 = this.K;
        a1.G1 = this.L;
        a1.j = a1.f.getDataResourceConsumer();
        a1.k = this.P;
        a1.I1 = this.M;
        a1.l = C0931Dn.a(getOutputInspections());
        if (!this.R) {
            a1.a().a();
            a1.a().c();
        }
        if (!z && a1.T0 != null) {
            throw new AssertionError();
        }
        AssertionsConfiguration.Builder builderA = AssertionsConfiguration.a(g());
        a1.T0 = new com.android.tools.r8.utils.D(getAssertionsConfiguration(), getProgramConsumer() instanceof ClassFileConsumer ? AssertionsConfiguration.Builder.passthroughAllAssertions(builderA) : AssertionsConfiguration.Builder.compileTimeDisableAllAssertions(builderA));
        if (a1.f instanceof ClassFileConsumer) {
            hVarO0.a();
            a1.A0 = A1.g.OFF;
        }
        if (!z && a1.S0) {
            throw new AssertionError();
        }
        a1.S0 = this.E;
        if (this.D) {
            a1.t = false;
        }
        a1.q1 = isOptimizeMultidexForLinearAlloc();
        a1.a(this.O);
        String property = this.Q.isEmpty() ? System.getProperty("com.android.tools.r8.synthesizedClassPrefix", "") : this.Q;
        a1.Z = property;
        boolean zIsEmpty = property.isEmpty();
        a1.K0 = !zIsEmpty;
        if (!zIsEmpty) {
            a1.a().d = false;
        }
        a1.E1 = this.N;
        a1.J1 = getMapIdProvider();
        a1.K1 = U0.a(getSourceFileProvider(), this.A, a1);
        a1.a(getAndroidPlatformBuild());
        a1.q0().a(c());
        if (!h().isEmpty()) {
            a1.G0().a(h());
        }
        a1.g = A2.a(d(), a1.e);
        a1.c = getCancelCompilationChecker();
        if (!A1.O1) {
            if (!z && a1.a0 != -1) {
                throw new AssertionError();
            }
            a1.a0 = getThreadCount();
        }
        com.android.tools.r8.dex.U.b bVar = com.android.tools.r8.dex.U.b.R8;
        a1.m0 = bVar;
        a1.a(f());
        C1036Hf.a aVarC = C1036Hf.a(bVar).c();
        a(aVarC);
        a1.l0 = aVarC.a(this.F).f(getEnableTreeShaking()).d(getEnableMinification()).b(this.E).a(this.P).a(this.A).b(this.z).a(this.O).a(this.R).a();
        return a1;
    }

    public C0722p1 getDexItemFactory() {
        return this.A.f();
    }

    public boolean getEnableMinification() {
        return this.C;
    }

    public boolean getEnableTreeShaking() {
        return this.B;
    }

    public boolean getProguardCompatibility() {
        return this.E;
    }

    static Builder a(C3945p c3945p, DiagnosticsHandler diagnosticsHandler) {
        return new Builder(c3945p, diagnosticsHandler);
    }

    public static Builder builder(DiagnosticsHandler diagnosticsHandler) {
        return new Builder(diagnosticsHandler);
    }

    public static Builder parse(String[] strArr, Origin origin, DiagnosticsHandler diagnosticsHandler) {
        return a0.a(strArr, origin, diagnosticsHandler);
    }

    private R8Command(boolean z, boolean z2) {
        super(z, z2);
        this.z = AbstractC1639am.i();
        this.A = null;
        this.B = false;
        this.C = false;
        this.D = false;
        this.E = false;
        this.F = null;
        this.G = null;
        this.H = null;
        this.I = null;
        this.J = null;
        this.K = null;
        this.L = null;
        this.M = null;
        this.N = null;
        this.O = null;
        this.P = null;
        this.Q = null;
        this.R = false;
    }
}
