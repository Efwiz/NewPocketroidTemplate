package com.android.tools.r8;

import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.inspector.Inspector;
import com.android.tools.r8.internal.C1036Hf;
import com.android.tools.r8.internal.C1133Kk;
import com.android.tools.r8.internal.C1233Oe;
import com.android.tools.r8.internal.C1367Tb;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.C3478z3;
import com.android.tools.r8.internal.InterfaceC1206Ne;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.profile.art.ArtProfileConsumer;
import com.android.tools.r8.profile.art.ArtProfileProvider;
import com.android.tools.r8.startup.StartupProfileProvider;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.B2;
import com.android.tools.r8.utils.C3945p;
import com.android.tools.r8.utils.EnumC3891f;
import com.android.tools.r8.utils.O0;
import com.android.tools.r8.utils.O2;
import com.android.tools.r8.utils.V1;
import com.android.tools.r8.utils.X0;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class BaseCompilerCommand extends BaseCommand {
    static final /* synthetic */ boolean y = true;
    private final CompilationMode e;
    private final ProgramConsumer f;
    private final StringConsumer g;
    private final int h;
    private final O2 i;
    private final A1.g j;
    private final boolean k;
    private final boolean l;
    private final BiPredicate m;
    private final List n;
    private final List o;
    private final int p;
    private final O0 q;
    private final MapIdProvider r;
    private final SourceFileProvider s;
    private final boolean t;
    private final List u;
    private final List v;
    private final ClassConflictResolver w;
    private final CancelCompilationChecker x;

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static abstract class Builder<C extends BaseCompilerCommand, B extends Builder<C, B>> extends BaseCommand.Builder<C, B> {
        static final /* synthetic */ boolean C = true;
        private ClassConflictResolver A;
        private CancelCompilationChecker B;
        private ProgramConsumer f;
        private StringConsumer g;
        private Path h;
        private OutputMode i;
        private CompilationMode j;
        private int k;
        private int l;
        protected A1.g m;
        private ArrayList n;
        private boolean o;
        private boolean p;
        private BiPredicate q;
        private ArrayList r;
        private ArrayList s;
        protected StringConsumer t;
        private O0 u;
        private MapIdProvider v;
        private SourceFileProvider w;
        private boolean x;
        private ArrayList y;
        private ArrayList z;

        Builder(DiagnosticsHandler diagnosticsHandler) {
            super(diagnosticsHandler);
            this.f = null;
            this.g = null;
            this.h = null;
            this.i = OutputMode.DexIndexed;
            this.k = 0;
            this.l = -1;
            this.m = A1.g.ON;
            this.n = new ArrayList();
            this.o = false;
            this.p = false;
            this.q = new BiPredicate() { // from class: com.android.tools.r8.BaseCompilerCommand$Builder$$ExternalSyntheticLambda0
                @Override // java.util.function.BiPredicate
                public final boolean test(Object obj, Object obj2) {
                    return BaseCompilerCommand.Builder.a((String) obj, (Long) obj2);
                }
            };
            this.r = new ArrayList();
            this.s = new ArrayList();
            this.t = null;
            this.u = O0.a();
            this.v = null;
            this.w = null;
            this.x = false;
            this.y = new ArrayList();
            this.z = new ArrayList();
            this.A = null;
            this.B = null;
            this.j = f();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean a(String str, Long l) {
            return true;
        }

        public B addArtProfileForRewriting(ArtProfileProvider artProfileProvider, ArtProfileConsumer artProfileConsumer) {
            this.y.add(new C3478z3(artProfileProvider, artProfileConsumer));
            return (B) d();
        }

        public B addAssertionsConfiguration(Function<AssertionsConfiguration.Builder, AssertionsConfiguration> function) {
            this.r.add(function.apply(AssertionsConfiguration.a(b())));
            return (B) d();
        }

        public B addDesugaredLibraryConfiguration(String str) {
            this.n.add(d0.a(str, Origin.unknown()));
            return (B) d();
        }

        public B addOutputInspection(Consumer<Inspector> consumer) {
            this.s.add(consumer);
            return (B) d();
        }

        public B addSpecialLibraryConfiguration(String str) {
            return (B) addDesugaredLibraryConfiguration(str);
        }

        Builder addStartupProfileProviders(Collection collection) {
            this.z.addAll(collection);
            return (Builder) d();
        }

        final void c(Path path) {
            this.u = O0.a(path);
        }

        final void d(Path path) {
            this.u = O0.b(path);
        }

        @Override // com.android.tools.r8.BaseCommand.Builder
        void e() {
            O2 o2B = b();
            if (this.j == null) {
                o2B.a("Expected valid compilation mode, was null");
            }
            X0.a(o2B, this.h);
            if (getProgramConsumer() == null) {
                o2B.a("A ProgramConsumer or Output is required for compilation");
            }
            ArrayList arrayList = new ArrayList(3);
            if (this.f instanceof DexIndexedConsumer) {
                arrayList.add(DexIndexedConsumer.class);
            }
            if (this.f instanceof DexFilePerClassFileConsumer) {
                arrayList.add(DexFilePerClassFileConsumer.class);
            }
            if (this.f instanceof ClassFileConsumer) {
                arrayList.add(ClassFileConsumer.class);
            }
            if (arrayList.size() > 1) {
                StringBuilder sbAppend = new StringBuilder("Invalid program consumer. A program consumer can implement at most one consumer type but ").append(this.f.getClass().getName()).append(" implements types:");
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    sbAppend.append(" ").append(((Class) it.next()).getName());
                }
                o2B.a(sbAppend.toString());
            }
            int minApiLevel = getMinApiLevel();
            EnumC3891f enumC3891f = EnumC3891f.L;
            if (minApiLevel > enumC3891f.d() && getMinApiLevel() != EnumC3891f.ANDROID_PLATFORM.d()) {
                o2B.c("An API level of " + getMinApiLevel() + " is not supported by this compiler. Please use an API level of " + enumC3891f.d() + " or earlier");
            }
            if (n() && getAndroidPlatformBuild()) {
                o2B.a("Android platform builds cannot use desugared library");
            }
        }

        abstract CompilationMode f();

        final List g() {
            return this.y;
        }

        public boolean getAndroidPlatformBuild() {
            return this.x;
        }

        public CancelCompilationChecker getCancelCompilationChecker() {
            return this.B;
        }

        public BiPredicate<String, Long> getDexClassChecksumFilter() {
            return this.q;
        }

        public boolean getDisableDesugaring() {
            return this.m == A1.g.OFF;
        }

        public boolean getIncludeClassesChecksum() {
            return this.o;
        }

        public StringConsumer getMainDexListConsumer() {
            return this.g;
        }

        public MapIdProvider getMapIdProvider() {
            return this.v;
        }

        public int getMinApiLevel() {
            return o() ? this.k : EnumC3891f.b().d();
        }

        public CompilationMode getMode() {
            return this.j;
        }

        public OutputMode getOutputMode() {
            return this.i;
        }

        public Path getOutputPath() {
            return this.h;
        }

        public ProgramConsumer getProgramConsumer() {
            return this.f;
        }

        public SourceFileProvider getSourceFileProvider() {
            return this.w;
        }

        final List h() {
            return this.r;
        }

        final ClassConflictResolver i() {
            return this.A;
        }

        final O0 j() {
            return this.u;
        }

        final List k() {
            return this.s;
        }

        final List l() {
            return this.z;
        }

        final int m() {
            return this.l;
        }

        final boolean n() {
            return !this.n.isEmpty();
        }

        boolean o() {
            return this.k != 0;
        }

        protected final boolean p() {
            return this.p;
        }

        final void q() {
            this.u = O0.c();
        }

        public B setAndroidPlatformBuild(boolean z) {
            this.x = z;
            return (B) d();
        }

        public B setCancelCompilationChecker(CancelCompilationChecker cancelCompilationChecker) {
            this.B = cancelCompilationChecker;
            return (B) d();
        }

        public B setClassConflictResolver(ClassConflictResolver classConflictResolver) {
            this.A = classConflictResolver;
            return (B) d();
        }

        public B setDexClassChecksumFilter(BiPredicate<String, Long> biPredicate) {
            if (!C && biPredicate == null) {
                throw new AssertionError();
            }
            this.q = biPredicate;
            return (B) d();
        }

        public B setDisableDesugaring(boolean z) {
            this.m = z ? A1.g.OFF : A1.g.ON;
            return (B) d();
        }

        public B setEnableDesugaring(boolean z) {
            this.m = z ? A1.g.ON : A1.g.OFF;
            return (B) d();
        }

        public B setIncludeClassesChecksum(boolean z) {
            this.o = z;
            return (B) d();
        }

        public B setMainDexListConsumer(StringConsumer stringConsumer) {
            this.g = stringConsumer;
            return (B) d();
        }

        public B setMainDexListOutputPath(Path path) {
            this.g = new StringConsumer.FileConsumer(path);
            return (B) d();
        }

        public B setMapIdProvider(MapIdProvider mapIdProvider) {
            this.v = mapIdProvider;
            return (B) d();
        }

        public B setMinApiLevel(int i) {
            if (i <= 0) {
                b().a("Invalid minApiLevel: " + i);
            } else {
                this.k = i;
            }
            return (B) d();
        }

        public B setMode(CompilationMode compilationMode) {
            if (!C && compilationMode == null) {
                throw new AssertionError();
            }
            this.j = compilationMode;
            return (B) d();
        }

        public B setOptimizeMultidexForLinearAlloc(boolean z) {
            this.p = z;
            return (B) d();
        }

        public B setOutput(Path path, OutputMode outputMode) {
            return (B) setOutput(path, outputMode, false);
        }

        public B setProgramConsumer(ProgramConsumer programConsumer) {
            this.h = null;
            this.i = null;
            this.f = programConsumer;
            return (B) d();
        }

        Builder setProguardMapConsumer(StringConsumer stringConsumer) {
            this.t = stringConsumer;
            return (Builder) d();
        }

        public B setSourceFileProvider(SourceFileProvider sourceFileProvider) {
            this.w = sourceFileProvider;
            return (B) d();
        }

        protected N a(Path path, OutputMode outputMode, boolean z) {
            if (outputMode == OutputMode.DexIndexed) {
                return X0.a(path) ? new DexIndexedConsumer.ArchiveConsumer(path, z) : new DexIndexedConsumer.DirectoryConsumer(path, z);
            }
            if (outputMode == OutputMode.DexFilePerClass) {
                return X0.a(path) ? new C0821i(path, z) : new C3596j(path, z);
            }
            if (outputMode == OutputMode.DexFilePerClassFile) {
                return X0.a(path) ? new DexFilePerClassFileConsumer.ArchiveConsumer(path, z) : new DexFilePerClassFileConsumer.DirectoryConsumer(path, z);
            }
            if (outputMode == OutputMode.ClassFile) {
                return X0.a(path) ? new ClassFileConsumer.ArchiveConsumer(path, z) : new ClassFileConsumer.DirectoryConsumer(path, z);
            }
            throw new C3405y10("Unexpected output mode: " + outputMode);
        }

        protected Builder setOutput(Path path, OutputMode outputMode, boolean z) {
            boolean z2 = C;
            if (!z2 && path == null) {
                throw new AssertionError();
            }
            if (!z2 && outputMode == null) {
                throw new AssertionError();
            }
            this.h = path;
            this.i = outputMode;
            this.f = a(path, outputMode, z);
            return (Builder) d();
        }

        public B addDesugaredLibraryConfiguration(d0 d0Var) {
            this.n.add(d0Var);
            return (B) d();
        }

        final InterfaceC1206Ne a(C0722p1 c0722p1, boolean z) {
            if (this.n.isEmpty()) {
                return C1133Kk.g();
            }
            if (this.n.size() <= 1) {
                return C1233Oe.a((d0) this.n.get(0), c0722p1, b(), z, getMinApiLevel());
            }
            throw new C1367Tb("Only one desugared library configuration is supported.");
        }

        Builder(C3945p c3945p) {
            super(C3945p.a(c3945p));
            this.f = null;
            this.g = null;
            this.h = null;
            this.i = OutputMode.DexIndexed;
            this.k = 0;
            this.l = -1;
            this.m = A1.g.ON;
            this.n = new ArrayList();
            this.o = false;
            this.p = false;
            this.q = new BiPredicate() { // from class: com.android.tools.r8.BaseCompilerCommand$Builder$$ExternalSyntheticLambda0
                @Override // java.util.function.BiPredicate
                public final boolean test(Object obj, Object obj2) {
                    return BaseCompilerCommand.Builder.a((String) obj, (Long) obj2);
                }
            };
            this.r = new ArrayList();
            this.s = new ArrayList();
            this.t = null;
            this.u = O0.a();
            this.v = null;
            this.w = null;
            this.x = false;
            this.y = new ArrayList();
            this.z = new ArrayList();
            this.A = null;
            this.B = null;
            this.j = f();
        }

        final Builder a(int i) {
            if (i <= 0) {
                b().a("Invalid threadCount: " + i);
            } else {
                this.l = i;
            }
            return (Builder) d();
        }

        Builder(C3945p c3945p, DiagnosticsHandler diagnosticsHandler) {
            super(C3945p.a(c3945p, new O2(diagnosticsHandler)));
            this.f = null;
            this.g = null;
            this.h = null;
            this.i = OutputMode.DexIndexed;
            this.k = 0;
            this.l = -1;
            this.m = A1.g.ON;
            this.n = new ArrayList();
            this.o = false;
            this.p = false;
            this.q = new BiPredicate() { // from class: com.android.tools.r8.BaseCompilerCommand$Builder$$ExternalSyntheticLambda0
                @Override // java.util.function.BiPredicate
                public final boolean test(Object obj, Object obj2) {
                    return BaseCompilerCommand.Builder.a((String) obj, (Long) obj2);
                }
            };
            this.r = new ArrayList();
            this.s = new ArrayList();
            this.t = null;
            this.u = O0.a();
            this.v = null;
            this.w = null;
            this.x = false;
            this.y = new ArrayList();
            this.z = new ArrayList();
            this.A = null;
            this.B = null;
            this.j = f();
        }
    }

    BaseCompilerCommand(boolean z, boolean z2) {
        super(z, z2);
        this.f = null;
        this.g = null;
        this.e = null;
        this.h = 0;
        this.i = new O2();
        this.j = A1.g.ON;
        this.k = false;
        this.l = false;
        this.m = new BiPredicate() { // from class: com.android.tools.r8.BaseCompilerCommand$$ExternalSyntheticLambda1
            @Override // java.util.function.BiPredicate
            public final boolean test(Object obj, Object obj2) {
                return BaseCompilerCommand.a((String) obj, (Long) obj2);
            }
        };
        this.n = new ArrayList();
        this.o = null;
        this.p = -1;
        this.q = O0.c();
        this.r = null;
        this.s = null;
        this.t = false;
        this.u = null;
        this.v = null;
        this.w = null;
        this.x = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(String str, Long l) {
        return true;
    }

    List<C3478z3> c() {
        return this.u;
    }

    final ClassConflictResolver d() {
        return this.w;
    }

    A1.g e() {
        return this.j;
    }

    final O0 f() {
        return this.q;
    }

    O2 g() {
        return this.i;
    }

    public boolean getAndroidPlatformBuild() {
        return this.t;
    }

    public List<AssertionsConfiguration> getAssertionsConfiguration() {
        return Collections.unmodifiableList(this.n);
    }

    public CancelCompilationChecker getCancelCompilationChecker() {
        return this.x;
    }

    public BiPredicate<String, Long> getDexClassChecksumFilter() {
        return this.m;
    }

    public boolean getEnableDesugaring() {
        return this.j == A1.g.ON;
    }

    public boolean getIncludeClassesChecksum() {
        return this.k;
    }

    public StringConsumer getMainDexListConsumer() {
        return this.g;
    }

    public MapIdProvider getMapIdProvider() {
        return this.r;
    }

    public int getMinApiLevel() {
        return this.h;
    }

    public CompilationMode getMode() {
        return this.e;
    }

    public Collection<Consumer<Inspector>> getOutputInspections() {
        return Collections.unmodifiableList(this.o);
    }

    public ProgramConsumer getProgramConsumer() {
        return this.f;
    }

    public SourceFileProvider getSourceFileProvider() {
        return this.s;
    }

    public int getThreadCount() {
        return this.p;
    }

    List<StartupProfileProvider> h() {
        return this.v;
    }

    public boolean isOptimizeMultidexForLinearAlloc() {
        return this.l;
    }

    final void a(C1036Hf.a aVar) {
        aVar.a(B2.a(this.f)).a(getMode()).a(getMinApiLevel()).e(isOptimizeMultidexForLinearAlloc()).b(getThreadCount()).a(e()).a(V1.a((Collection) c(), (Function) new BaseCompilerCommand$$ExternalSyntheticLambda0())).c(h());
        if (getAndroidPlatformBuild()) {
            aVar.d();
        }
    }

    BaseCompilerCommand(C3945p c3945p, CompilationMode compilationMode, ProgramConsumer programConsumer, StringConsumer stringConsumer, int i, O2 o2, A1.g gVar, boolean z, boolean z2, BiPredicate biPredicate, List list, List list2, int i2, O0 o0, MapIdProvider mapIdProvider, SourceFileProvider sourceFileProvider, boolean z3, List list3, List list4, ClassConflictResolver classConflictResolver, CancelCompilationChecker cancelCompilationChecker) {
        super(c3945p);
        boolean z4 = y;
        if (!z4 && i <= 0) {
            throw new AssertionError();
        }
        if (!z4 && compilationMode == null) {
            throw new AssertionError();
        }
        this.e = compilationMode;
        this.f = programConsumer;
        this.g = stringConsumer;
        this.h = i;
        this.i = o2;
        this.j = gVar;
        this.l = z;
        this.k = z2;
        this.m = biPredicate;
        this.n = list;
        this.o = list2;
        this.p = i2;
        this.q = o0;
        this.r = mapIdProvider;
        this.s = sourceFileProvider;
        this.t = z3;
        this.u = list3;
        this.v = list4;
        this.w = classConflictResolver;
        this.x = cancelCompilationChecker;
    }
}
