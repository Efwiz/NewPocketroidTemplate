package com.android.tools.r8;

import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.C1036Hf;
import com.android.tools.r8.internal.C3478z3;
import com.android.tools.r8.internal.EnumC0928Dk;
import com.android.tools.r8.internal.InterfaceC1206Ne;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.A2;
import com.android.tools.r8.utils.C3945p;
import com.android.tools.r8.utils.C3979v2;
import com.android.tools.r8.utils.EnumC3891f;
import com.android.tools.r8.utils.O0;
import com.android.tools.r8.utils.O2;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiPredicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class L8Command extends BaseCompilerCommand {
    static final /* synthetic */ boolean D = true;
    private final R8Command A;
    private final InterfaceC1206Ne B;
    private final C0722p1 C;
    private final D8Command z;

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class Builder extends BaseCompilerCommand.Builder<L8Command, Builder> {
        static final /* synthetic */ boolean F = true;
        private final ArrayList D;
        private final ArrayList E;

        private Builder(DiagnosticsHandler diagnosticsHandler) {
            super(diagnosticsHandler);
            this.D = new ArrayList();
            this.E = new ArrayList();
        }

        public Builder addProguardConfiguration(List<String> list, Origin origin) {
            this.D.add(new C3979v2(list, origin));
            return this;
        }

        public Builder addProguardConfigurationFiles(Path... pathArr) {
            Collections.addAll(this.E, pathArr);
            return this;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.android.tools.r8.BaseCommand.Builder
        final BaseCommand c() {
            R8Command r8Command;
            D8Command d8CommandC;
            ProgramConsumer programConsumer;
            if (isPrintHelp() || isPrintVersion()) {
                return new L8Command(isPrintHelp(), isPrintVersion());
            }
            if (getMode() == null) {
                setMode(CompilationMode.DEBUG);
            }
            C0722p1 c0722p1 = new C0722p1();
            InterfaceC1206Ne interfaceC1206NeA = a(c0722p1, true);
            C3945p c3945pA = a().a();
            if (isShrinking()) {
                P p = new P();
                R8Command.Builder programConsumer2 = R8Command.builder(b()).addProgramResourceProvider((ProgramResourceProvider) p).a(interfaceC1206NeA.f()).setMinApiLevel(getMinApiLevel()).setMode(getMode()).setIncludeClassesChecksum(getIncludeClassesChecksum()).setDexClassChecksumFilter(getDexClassChecksumFilter()).setProgramConsumer(getProgramConsumer());
                for (C3478z3 c3478z3 : (ArrayList) g()) {
                    programConsumer2.addArtProfileForRewriting(c3478z3.a(), c3478z3.b());
                }
                Iterator<ClassFileResourceProvider> it = c3945pA.i().iterator();
                while (it.hasNext()) {
                    programConsumer2.addLibraryResourceProvider(it.next());
                }
                for (C3979v2 c3979v2 : this.D) {
                    programConsumer2.addProguardConfiguration((List) c3979v2.a(), (Origin) c3979v2.b());
                }
                StringConsumer stringConsumer = this.t;
                if (stringConsumer != null) {
                    programConsumer2.setProguardMapConsumer(stringConsumer);
                }
                programConsumer2.addProguardConfiguration(interfaceC1206NeA.e(), Origin.unknown());
                programConsumer2.addProguardConfiguration(AbstractC1639am.c("-dontwarn sun.misc.Unsafe"), Origin.unknown());
                programConsumer2.addProguardConfigurationFiles(this.E);
                programConsumer2.setDisableDesugaring(true);
                programConsumer2.q();
                R8Command r8CommandC = programConsumer2.c();
                d8CommandC = null;
                programConsumer = p;
                r8Command = r8CommandC;
            } else if (!(getProgramConsumer() instanceof ClassFileConsumer)) {
                P p2 = new P();
                D8Command.Builder programConsumer3 = ((D8Command.Builder) D8Command.builder(b()).addProgramResourceProvider(p2)).a(interfaceC1206NeA.f()).setMinApiLevel(getMinApiLevel()).setMode(getMode()).setIncludeClassesChecksum(getIncludeClassesChecksum()).setDexClassChecksumFilter(getDexClassChecksumFilter()).setProgramConsumer(getProgramConsumer());
                for (C3478z3 c3478z4 : (ArrayList) g()) {
                    programConsumer3.addArtProfileForRewriting(c3478z4.a(), c3478z4.b());
                }
                Iterator<ClassFileResourceProvider> it2 = c3945pA.i().iterator();
                while (it2.hasNext()) {
                    programConsumer3.addLibraryResourceProvider(it2.next());
                }
                programConsumer3.setDisableDesugaring(true);
                programConsumer3.q();
                d8CommandC = programConsumer3.c();
                programConsumer = p2;
                r8Command = null;
            } else {
                if (!F && !(getProgramConsumer() instanceof ClassFileConsumer)) {
                    throw new AssertionError();
                }
                r8Command = null;
                d8CommandC = null;
                programConsumer = getProgramConsumer();
            }
            return new L8Command(r8Command, d8CommandC, c3945pA, getMode(), programConsumer, getMainDexListConsumer(), getMinApiLevel(), b(), getIncludeClassesChecksum(), getDexClassChecksumFilter(), interfaceC1206NeA, h(), k(), m(), j(), getMapIdProvider(), i(), getCancelCompilationChecker(), c0722p1);
        }

        @Override // com.android.tools.r8.BaseCommand.Builder
        final BaseCommand.Builder d() {
            return this;
        }

        @Override // com.android.tools.r8.BaseCompilerCommand.Builder, com.android.tools.r8.BaseCommand.Builder
        final void e() {
            if (isPrintHelp()) {
                return;
            }
            O2 o2B = b();
            if (!n()) {
                o2B.a("L8 requires a desugared library configuration");
            }
            if (getProgramConsumer() instanceof DexFilePerClassFileConsumer) {
                o2B.a("L8 does not support compiling to dex per class");
            }
            if (a().d()) {
                o2B.a("L8 does not support a main dex list");
            } else if (getMainDexListConsumer() != null) {
                o2B.a("L8 does not support generating a main dex list");
            }
            if (isShrinking() && (getProgramConsumer() instanceof ClassFileConsumer)) {
                o2B.a("L8 does not support shrinking when generating class files");
            }
            if (!isShrinking() && this.t != null) {
                o2B.a("L8 does not support defining a map consumer when not shrinking");
            }
            super.e();
        }

        @Override // com.android.tools.r8.BaseCompilerCommand.Builder
        final CompilationMode f() {
            return CompilationMode.DEBUG;
        }

        public boolean isShrinking() {
            return (this.D.isEmpty() && this.E.isEmpty()) ? false : true;
        }

        public Builder addProguardConfigurationFiles(List<Path> list) {
            this.E.addAll(list);
            return this;
        }

        @Override // com.android.tools.r8.BaseCompilerCommand.Builder
        public Builder setAndroidPlatformBuild(boolean z) {
            throw b().b("L8 does not support configuring Android platform builds.");
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
    }

    private L8Command(R8Command r8Command, D8Command d8Command, C3945p c3945p, CompilationMode compilationMode, ProgramConsumer programConsumer, StringConsumer stringConsumer, int i, O2 o2, boolean z, BiPredicate biPredicate, InterfaceC1206Ne interfaceC1206Ne, List list, List list2, int i2, O0 o0, MapIdProvider mapIdProvider, ClassConflictResolver classConflictResolver, CancelCompilationChecker cancelCompilationChecker, C0722p1 c0722p1) {
        super(c3945p, compilationMode, programConsumer, stringConsumer, i, o2, A1.g.ON, false, z, biPredicate, list, list2, i2, o0, mapIdProvider, null, false, Collections.emptyList(), Collections.emptyList(), classConflictResolver, cancelCompilationChecker);
        this.z = d8Command;
        this.A = r8Command;
        this.B = interfaceC1206Ne;
        this.C = c0722p1;
    }

    public static Builder builder() {
        return new Builder(new O());
    }

    public static Builder parse(String[] strArr, Origin origin) {
        return Q.a(strArr, origin);
    }

    @Override // com.android.tools.r8.BaseCommand
    A1 b() {
        A1 a1 = new A1(this.C, g());
        boolean z = D;
        if (!z && a1.W0) {
            throw new AssertionError();
        }
        a1.W0 = getMode() == CompilationMode.DEBUG;
        if (!z && a1.w1 != null) {
            throw new AssertionError();
        }
        if (!z && a1.p1) {
            throw new AssertionError();
        }
        a1.c(EnumC3891f.c(getMinApiLevel()));
        if (!z && a1.t0) {
            throw new AssertionError();
        }
        if (!z && !a1.v0) {
            throw new AssertionError();
        }
        ProgramConsumer programConsumer = getProgramConsumer();
        a1.f = programConsumer;
        if (!z && !(programConsumer instanceof ClassFileConsumer)) {
            throw new AssertionError();
        }
        if (!z && a1.o1()) {
            throw new AssertionError();
        }
        if (!z && a1.k1()) {
            throw new AssertionError();
        }
        if (!z && a1.o) {
            throw new AssertionError();
        }
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
        A1.h hVarO0 = a1.O0();
        hVarO0.a();
        if (!z && hVarO0.a(EnumC0928Dk.a)) {
            throw new AssertionError();
        }
        if (!z && hVarO0.a(EnumC0928Dk.b)) {
            throw new AssertionError();
        }
        if (!z && a1.A0 != A1.g.ON) {
            throw new AssertionError();
        }
        if (!z && !a1.q1) {
            throw new AssertionError();
        }
        a1.q1 = false;
        if (!z && this.B == null) {
            throw new AssertionError();
        }
        a1.a(this.B);
        a1.Z = this.B.f();
        if (!z && a1.T0 != null) {
            throw new AssertionError();
        }
        a1.T0 = new com.android.tools.r8.utils.D(getAssertionsConfiguration(), AssertionsConfiguration.a(g()).setCompileTimeDisable().setScopeAll().build());
        a1.g = A2.a(d(), a1.e);
        a1.c = getCancelCompilationChecker();
        if (!A1.O1) {
            if (!z && a1.a0 != -1) {
                throw new AssertionError();
            }
            a1.a0 = getThreadCount();
        }
        a1.l0();
        a1.a().a();
        a1.a().c();
        a1.a(f());
        C1036Hf.a aVarC = C1036Hf.a(com.android.tools.r8.dex.U.b.L8).c();
        a(aVarC);
        R8Command r8Command = this.A;
        if (r8Command != null) {
            aVarC.a(r8Command.b().D0());
        }
        a1.l0 = aVarC.a(this.B).a();
        return a1;
    }

    @Override // com.android.tools.r8.BaseCompilerCommand
    final List c() {
        if (i() != null) {
            return i().c();
        }
        return j() != null ? j().c() : Collections.emptyList();
    }

    D8Command i() {
        return this.z;
    }

    R8Command j() {
        return this.A;
    }

    boolean k() {
        return this.A != null;
    }

    public static Builder parse(String[] strArr, Origin origin, DiagnosticsHandler diagnosticsHandler) {
        return Q.a(strArr, origin, diagnosticsHandler);
    }

    public static Builder builder(DiagnosticsHandler diagnosticsHandler) {
        return new Builder(diagnosticsHandler);
    }

    private L8Command(boolean z, boolean z2) {
        super(z, z2);
        this.A = null;
        this.z = null;
        this.B = null;
        this.C = null;
    }
}
