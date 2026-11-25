package com.android.tools.r8.utils;

import com.android.tools.r8.CancelCompilationChecker;
import com.android.tools.r8.ClassFileConsumer;
import com.android.tools.r8.CompilationMode;
import com.android.tools.r8.DataResourceConsumer;
import com.android.tools.r8.DesugarGraphConsumer;
import com.android.tools.r8.DexFilePerClassFileConsumer;
import com.android.tools.r8.DexIndexedConsumer;
import com.android.tools.r8.FeatureSplit;
import com.android.tools.r8.GlobalSyntheticsConsumer;
import com.android.tools.r8.MapIdProvider;
import com.android.tools.r8.ProgramConsumer;
import com.android.tools.r8.SourceFileProvider;
import com.android.tools.r8.StringConsumer;
import com.android.tools.r8.SyntheticInfoConsumer;
import com.android.tools.r8.Version;
import com.android.tools.r8.dex.InterfaceC0407b;
import com.android.tools.r8.errors.IncompleteNestNestDesugarDiagnosic;
import com.android.tools.r8.errors.InterfaceDesugarMissingTypeDiagnostic;
import com.android.tools.r8.errors.InvalidLibrarySuperclassDiagnostic;
import com.android.tools.r8.errors.MissingNestHostNestDesugarDiagnostic;
import com.android.tools.r8.experimental.graphinfo.GraphConsumer;
import com.android.tools.r8.graph.AbstractC0681j0;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0635d2;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0754u0;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0796x$$ExternalSyntheticLambda0;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1048Hm;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.AbstractC2734ot;
import com.android.tools.r8.internal.AbstractC2763pC;
import com.android.tools.r8.internal.AbstractC3472z00;
import com.android.tools.r8.internal.C0862Bm;
import com.android.tools.r8.internal.C0994Fs;
import com.android.tools.r8.internal.C1036Hf;
import com.android.tools.r8.internal.C1105Jk;
import com.android.tools.r8.internal.C1367Tb;
import com.android.tools.r8.internal.C2173hh;
import com.android.tools.r8.internal.C2215iD;
import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.C2750p20;
import com.android.tools.r8.internal.C2927rW;
import com.android.tools.r8.internal.C3048tG;
import com.android.tools.r8.internal.C3065tW;
import com.android.tools.r8.internal.C3226vg;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.C3451yh;
import com.android.tools.r8.internal.CM;
import com.android.tools.r8.internal.CN;
import com.android.tools.r8.internal.E00;
import com.android.tools.r8.internal.EnumC0928Dk;
import com.android.tools.r8.internal.F00;
import com.android.tools.r8.internal.G00;
import com.android.tools.r8.internal.InterfaceC1206Ne;
import com.android.tools.r8.internal.InterfaceC3010sj;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.internal.RQ;
import com.android.tools.r8.internal.WL;
import com.android.tools.r8.internal.YS;
import com.android.tools.r8.internal.Z8;
import com.android.tools.r8.ir.optimize.EnumC3542a0;
import com.android.tools.r8.naming.MapVersion;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.MethodPosition;
import com.android.tools.r8.position.Position;
import com.android.tools.r8.references.ClassReference;
import com.android.tools.r8.references.FieldReference;
import com.android.tools.r8.references.MethodReference;
import com.android.tools.r8.references.Reference;
import com.android.tools.r8.shaking.C3733i;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class A1 implements com.android.tools.r8.shaking.K0 {
    public static final boolean O1;
    public static final Z8 P1;
    public static final int Q1;
    static final /* synthetic */ boolean R1 = true;
    public boolean A;
    public g A0;
    public StringConsumer A1;
    public boolean B;
    public boolean B0;
    private InterfaceC3890e3 B1;
    public boolean C;
    public boolean C0;
    public C2215iD C1;
    public boolean D;
    public boolean D0;
    public boolean D1;
    public boolean E;
    public boolean E0;
    public StringConsumer E1;
    public boolean F;
    public EnumC3904h2 F0;
    public GraphConsumer F1;
    public boolean G;
    public EnumC3904h2 G0;
    public GraphConsumer G1;
    public boolean H;
    public boolean H0;
    public DesugarGraphConsumer H1;
    public BiPredicate I;
    public boolean I0;
    public Consumer I1;
    public boolean J;
    public boolean J0;
    public MapIdProvider J1;
    public boolean K;
    public boolean K0;
    public SourceFileProvider K1;
    public boolean L;
    public boolean L0;
    private final Set L1;
    public int M;
    private final boolean M0;
    private final AtomicBoolean M1;
    public Thread N;
    private final boolean N0;
    private final Set N1;
    public boolean O;
    public boolean O0;
    public boolean P;
    public boolean P0;
    public int Q;
    public boolean Q0;
    public boolean R;
    public boolean R0;
    public boolean S;
    public boolean S0;
    public final m T;
    public D T0;
    public boolean U;
    public boolean U0;
    public boolean V;
    public boolean V0;
    public boolean W;
    public boolean W0;
    public boolean X;
    private final F1 X0;
    public boolean Y;
    private final c Y0;
    public String Z;
    private final d Z0;
    public final C0722p1 a;
    public int a0;
    private final e a1;
    public boolean b0;
    private final i b1;
    public boolean c0;
    private final h c1;
    private final com.android.tools.r8.shaking.L2 d;
    public boolean d0;
    private final l d1;
    public final O2 e;
    public boolean e0;
    private final o e1;
    public boolean f0;
    private final RQ f1;
    public boolean g0;
    private final C1 g1;
    public boolean h0;
    private final a h1;
    public boolean i0;
    private final f i1;
    public DataResourceConsumer j;
    public boolean j0;
    private final k j1;
    public C3451yh k;
    private O0 k0;
    private final com.android.tools.r8.internal.D3 k1;
    public C1036Hf l0;
    private final C3065tW l1;
    public boolean m;
    public com.android.tools.r8.dex.U.b m0;
    private final C2927rW m1;
    public boolean n;
    private boolean n0;
    public final p n1;
    public boolean o;
    private com.android.tools.r8.dex.U o0;
    public List o1;
    public boolean p;
    public AbstractC1048Hm p0;
    public boolean p1;
    public D1 q;
    public AbstractC1048Hm q0;
    public boolean q1;
    public boolean r;
    public List r0;
    public j r1;
    public boolean s;
    private EnumC3891f s0;
    private final HashMap s1;
    public boolean t;
    public boolean t0;
    private final HashMap t1;
    public boolean u;
    private boolean u0;
    private final HashMap u1;
    public boolean v;
    public boolean v0;
    public boolean v1;
    public boolean w;
    public boolean w0;
    public StringConsumer w1;
    public boolean x;
    public boolean x0;
    public com.android.tools.r8.T x1;
    public boolean y;
    public List<String> y0;
    public StringConsumer y1;
    public boolean z;
    public boolean z0;
    public StringConsumer z1;
    private final AtomicBoolean b = new AtomicBoolean(false);
    public CancelCompilationChecker c = null;
    public ProgramConsumer f = null;
    public InterfaceC0407b g = null;
    private GlobalSyntheticsConsumer h = null;
    private SyntheticInfoConsumer i = null;
    public List l = Collections.emptyList();

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public interface b {
        boolean a(C0702m<?> c0702m, o5 o5Var, int i);
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public class c {
        static final /* synthetic */ boolean e = true;
        private boolean a = true;
        private boolean b = true;
        private boolean c = false;

        public c() {
        }

        public void a() {
            this.a = false;
        }

        public final boolean b() {
            if (A1.this.l1() && A1.this.o1()) {
                return this.a;
            }
            return false;
        }

        public final boolean c() {
            return this.c;
        }

        public c a(boolean z) {
            this.b = z;
            return this;
        }

        public c c(boolean z) {
            this.c = z;
            return this;
        }

        public c b(boolean z) {
            if (z) {
                if (!e && !b()) {
                    throw new AssertionError();
                }
            } else {
                a();
            }
            return this;
        }
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class d {
        private boolean a = true;
        private boolean b = false;

        public final boolean a() {
            return this.a;
        }

        public final boolean b() {
            return this.b;
        }

        public d a(boolean z) {
            this.a = z;
            return this;
        }

        public d b(boolean z) {
            this.b = z;
            return this;
        }
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public class e {
        static final /* synthetic */ boolean c = true;
        public int a = -1;

        public e() {
        }

        public final int a() {
            int i = this.a;
            if (i >= 0) {
                return i;
            }
            A1 a1 = A1.this;
            if (a1.f instanceof ClassFileConsumer) {
                return 50;
            }
            if (c || a1.Y0()) {
                return 65;
            }
            throw new AssertionError();
        }
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class f {
        public boolean a;
        public boolean b;
        public boolean c;
        public boolean d;

        public f() {
            this.a = System.getProperty("com.android.tools.r8.sortMethodsOnCfWriting") != null;
            this.b = System.getProperty("com.android.tools.r8.allowAllDesugaredInput") != null;
            this.c = System.getProperty("com.android.tools.r8.noCfMarkerForDesugaredCode") != null;
            this.d = System.getProperty("com.android.tools.r8.lambdaClassFieldsNotFinal") == null;
        }
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public enum g {
        OFF,
        ON;

        public final boolean a() {
            return this == OFF;
        }

        public final boolean b() {
            return this == ON;
        }
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public class h {
        static final /* synthetic */ boolean j = true;
        private boolean a;
        private boolean b;
        private boolean c;
        private boolean d;
        private boolean e;
        private boolean f;
        private boolean g;
        private boolean h;

        public h() {
            this.a = !Version.isDevelopmentVersion() || System.getProperty("com.android.tools.r8.disableHorizontalClassMerging") == null;
            this.b = true;
            this.c = true;
            this.d = System.getProperty("com.android.tools.r8.enableHorizontalInterfaceMerging") != null;
            this.e = false;
            this.f = System.getProperty("com.android.tools.r8.enableSameFilePolicy") != null;
            this.g = true;
            this.h = false;
        }

        public void a() {
            this.a = false;
        }

        public void b() {
            this.b = false;
        }

        public void c() {
            this.g = false;
        }

        public void d() {
            this.a = true;
        }

        public int e() {
            return 30;
        }

        public final boolean f() {
            return this.c;
        }

        public final boolean g() {
            return (!this.h && A1.this.l1() && A1.this.o1()) ? false : true;
        }

        public final boolean h() {
            return this.f;
        }

        public final boolean i() {
            return this.g;
        }

        public void j() {
            this.c = true;
        }

        public void k() {
            this.d = true;
        }

        public void l() {
            this.e = true;
        }

        public void m() {
            this.h = true;
        }

        public void a(boolean z) {
            this.a = z;
        }

        public final boolean b(EnumC0928Dk enumC0928Dk) {
            if (!this.d) {
                return false;
            }
            if (enumC0928Dk.b()) {
                return this.e;
            }
            if (j || enumC0928Dk.a()) {
                return true;
            }
            throw new AssertionError();
        }

        public void c(boolean z) {
            this.f = z;
        }

        public final boolean a(EnumC0928Dk enumC0928Dk) {
            if (this.a) {
                A1 a1 = A1.this;
                if (!a1.W0 && !a1.t0) {
                    if (enumC0928Dk.b()) {
                        if (!this.b) {
                            return false;
                        }
                        A1 a2 = A1.this;
                        return a2.b1.a && a2.o1();
                    }
                    if (j || enumC0928Dk.a()) {
                        return true;
                    }
                    throw new AssertionError();
                }
            }
            return false;
        }

        public void b(boolean z) {
            this.d = z;
        }
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class i {
        static final /* synthetic */ boolean l = true;
        public boolean a = !AbstractC3874b3.a("com.android.tools.r8.disableinlining", false);
        public int b = AbstractC3874b3.b();
        public int[] c = {Integer.MAX_VALUE, 28, 16, 12, 10};
        public int d = 1500;
        public int e = 4;
        public int f = 15;
        public boolean g = true;
        public boolean h = true;
        public boolean i = true;
        public b j = null;
        private final A1 k;

        public i(A1 a1) {
            this.k = a1;
        }

        public static void a(A1 a1) {
            a1.P0().a = false;
        }

        public static void b(A1 a1) {
            a1.n1.U = AbstractC1048Hm.c(EnumC3542a0.a);
        }

        public final int a() {
            int i = this.b;
            if (i >= 0) {
                return i;
            }
            A1 a1 = this.k;
            if (a1.f instanceof ClassFileConsumer) {
                return 3;
            }
            if (l || a1.Y0()) {
                return 5;
            }
            throw new AssertionError();
        }

        public final boolean a(C0702m c0702m, o5 o5Var, int i) {
            b bVar = this.j;
            if (bVar != null) {
                return bVar.a(c0702m, o5Var, i);
            }
            if (this.k.x1().c() && i == 1) {
                return o5Var.t() == c0702m.Z().h.i;
            }
            return false;
        }
    }

    /* JADX WARN: $VALUES field not found */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static final class j {
        public static final j a = new j("OFF", 0);
        public static final j b = new j("ON", 1);

        private j(String str, int i) {
            super(str, i);
        }
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class k {
        public boolean a = false;
        public boolean b = AbstractC3874b3.a("com.android.tools.r8.allowemptymappedranges", false);
        public boolean c = true;
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class l {
        static final /* synthetic */ boolean c = true;
        private boolean a = true;
        private ArrayList b = new ArrayList();

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean b(C0702m c0702m, AbstractC3472z00 abstractC3472z00, AbstractC0727q0 abstractC0727q0) {
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean c(C0702m c0702m, AbstractC3472z00 abstractC3472z00, AbstractC0727q0 abstractC0727q0) {
            return abstractC3472z00.a(c0702m);
        }

        public void a() {
            this.a = false;
        }

        public void d() {
            if (!c && this.a) {
                throw new AssertionError();
            }
            this.b.add(new E1() { // from class: com.android.tools.r8.utils.A1$l$$ExternalSyntheticLambda2
                @Override // com.android.tools.r8.utils.E1
                public final boolean a(C0702m c0702m, AbstractC3472z00 abstractC3472z00, AbstractC0727q0 abstractC0727q0) {
                    return A1.l.b(c0702m, abstractC3472z00, abstractC0727q0);
                }
            });
        }

        public void e() {
            if (!c && this.a) {
                throw new AssertionError();
            }
            this.b.add(new E1() { // from class: com.android.tools.r8.utils.A1$l$$ExternalSyntheticLambda3
                @Override // com.android.tools.r8.utils.E1
                public final boolean a(C0702m c0702m, AbstractC3472z00 abstractC3472z00, AbstractC0727q0 abstractC0727q0) {
                    return A1.l.c(c0702m, abstractC3472z00, abstractC0727q0);
                }
            });
        }

        public void f() {
            if (!c && this.a) {
                throw new AssertionError();
            }
            this.b.add(new E1() { // from class: com.android.tools.r8.utils.A1$l$$ExternalSyntheticLambda4
                @Override // com.android.tools.r8.utils.E1
                public final boolean a(C0702m c0702m, AbstractC3472z00 abstractC3472z00, AbstractC0727q0 abstractC0727q0) {
                    return A1.l.d(c0702m, abstractC3472z00, abstractC0727q0);
                }
            });
        }

        public l a(final ClassReference classReference) {
            if (!c && this.a) {
                throw new AssertionError();
            }
            this.b.add(new E1() { // from class: com.android.tools.r8.utils.A1$l$$ExternalSyntheticLambda1
                @Override // com.android.tools.r8.utils.E1
                public final boolean a(C0702m c0702m, AbstractC3472z00 abstractC3472z00, AbstractC0727q0 abstractC0727q0) {
                    return A1.l.a(classReference, c0702m, abstractC3472z00, abstractC0727q0);
                }
            });
            return this;
        }

        public final boolean b() {
            return !this.b.isEmpty();
        }

        public final boolean c() {
            return this.a;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean d(C0702m c0702m, AbstractC3472z00 abstractC3472z00, AbstractC0727q0 abstractC0727q0) {
            return abstractC3472z00.w() && abstractC3472z00.b().R().L0().equals("java.util.zip.ZipFile") && abstractC0727q0.o1().equals(CommonClassNames.JAVA_LANG_AUTO_CLOSEABLE);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean a(ClassReference classReference, C0702m c0702m, AbstractC3472z00 abstractC3472z00, AbstractC0727q0 abstractC0727q0) {
            return abstractC0727q0.o1().equals(classReference.getTypeName());
        }

        public final boolean a(final C0702m c0702m, final AbstractC3472z00 abstractC3472z00, final AbstractC0727q0 abstractC0727q0) {
            return this.a || this.b.stream().anyMatch(new Predicate() { // from class: com.android.tools.r8.utils.A1$l$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return A1.l.a(c0702m, abstractC3472z00, abstractC0727q0, (E1) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ boolean a(C0702m c0702m, AbstractC3472z00 abstractC3472z00, AbstractC0727q0 abstractC0727q0, E1 e1) {
            return e1.a(c0702m, abstractC3472z00, abstractC0727q0);
        }
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class m {
        public boolean a = true;
        public int b = 3;
        public int c = 99;
        public int d = 20;
    }

    /* JADX WARN: $VALUES field not found */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static final class n {
        public static final n a = new n("NONE", 0);
        public static final n b = new n("MINIFICATION", 1);
        public static final n c = new n("REPACKAGE", 2);
        public static final n d = new n("FLATTEN", 3);

        private n(String str, int i) {
            super(str, i);
        }

        public final boolean a() {
            return this == d;
        }

        public final boolean b() {
            return this == b;
        }

        public final boolean c() {
            return this == c;
        }
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class o {
        public boolean a = false;
        public boolean b = false;
        public boolean c = false;
        public boolean d = false;
        public boolean e = false;

        public final boolean a() {
            return c();
        }

        public final boolean b() {
            return this.e;
        }

        public final boolean c() {
            return this.a || this.b || this.c || this.e;
        }
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class p {
        public boolean A;
        public boolean A0;
        public boolean B;
        public boolean B0;
        public boolean C;
        public int C0;
        public boolean D;
        public a D0;
        public boolean E;
        public Runnable E0;
        public boolean F;
        public boolean F0;
        public boolean G;
        public b G0;
        public boolean H;
        public Consumer<o5> H0;
        public boolean I;
        public Predicate<C0684j2> I0;
        public boolean J;
        public boolean J0;
        public boolean K;
        public boolean K0;
        public boolean L;
        public boolean M;
        public boolean N;
        public boolean O;
        public boolean P;
        public boolean Q;
        public boolean R;
        public boolean S;
        public int T;
        public Set<EnumC3542a0> U;
        public boolean V;
        public boolean W;
        public boolean X;
        public BiConsumer<C2409kl, C0702m<?>> Y;
        public Consumer<C2409kl> Z;
        public int a0;
        public boolean b0;
        public boolean c0;
        public PrintStream d0;
        public boolean e0;
        public boolean f0;
        public boolean g0;
        public boolean h0;
        public boolean i;
        public int i0;
        public boolean j;
        public boolean j0;
        public com.android.tools.r8.internal.F2 k;
        public boolean k0;
        public Predicate<C0729q2> l;
        public boolean l0;
        public byte[] m;
        public boolean m0;
        public InterfaceC3908i1 n;
        public boolean n0;
        public BiFunction o;
        public Set<String> o0;
        public BiConsumer<C3733i, com.android.tools.r8.shaking.L.a> p;
        public boolean p0;
        public Consumer<String> q;
        public boolean q0;
        public Function r;
        public boolean r0;
        public BiConsumer<C0722p1, C1105Jk> s;
        public boolean s0;
        public Function<List<WL>, List<WL>> t;
        public boolean t0;
        public G3<C0702m<?>, Iterable<C0729q2>, C0729q2, C0729q2> u;
        public boolean u0;
        public BiConsumer<C0722p1, C3226vg> v;
        public boolean v0;
        public BiConsumer<C0722p1, C2750p20> w;
        public boolean w0;
        public Consumer<Deque<CN>> x;
        public boolean x0;
        public boolean y;
        public boolean y0;
        public boolean z;
        public boolean z0;
        public boolean a = true;
        public boolean b = false;
        public boolean c = false;
        public boolean d = true;
        public boolean e = false;
        private boolean f = false;
        private B0 g = null;
        public boolean h = false;

        /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
        public static class b {
            public int a = 0;
            public int b = 0;
        }

        public p() {
            this.i = System.getProperty("com.android.tools.r8.dexVersion40ForApiLevel30") != null;
            this.j = System.getProperty("com.android.tools.r8.dexContainerExperiment") != null;
            this.k = com.android.tools.r8.internal.F2.emptyConsumer();
            this.l = CM.b();
            this.m = null;
            this.n = (!A1.b() || A1.O1) ? C3898g1.a() : C3903h1.a();
            this.o = new BiFunction() { // from class: com.android.tools.r8.utils.A1$p$$ExternalSyntheticLambda0
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return A1.p.a((com.android.tools.r8.dex.W) obj, (com.android.tools.r8.dex.r0) obj2);
                }
            };
            this.p = null;
            this.q = null;
            this.r = new Function() { // from class: com.android.tools.r8.utils.A1$p$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return new YS((C0702m) obj);
                }
            };
            this.s = C3912j0.a();
            this.t = Function.identity();
            this.u = new G3() { // from class: com.android.tools.r8.utils.A1$p$$ExternalSyntheticLambda2
                @Override // com.android.tools.r8.utils.G3
                public final Object a(Object obj, Object obj2, Object obj3) {
                    return A1.p.a((C0702m) obj, (Iterable) obj2, (C0729q2) obj3);
                }
            };
            this.v = C3912j0.a();
            this.w = C3912j0.a();
            this.x = new Consumer() { // from class: com.android.tools.r8.utils.A1$p$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    A1.p.a((Deque) obj);
                }
            };
            this.y = false;
            this.z = true;
            this.A = false;
            this.B = true;
            this.C = (Version.isDevelopmentVersion() && System.getProperty("com.android.tools.r8.allowTypeErrors") == null) ? false : true;
            this.D = false;
            this.E = true;
            this.F = true;
            this.G = true;
            this.H = true;
            this.I = true;
            this.J = true;
            this.K = false;
            this.L = true;
            this.M = true;
            this.N = System.getProperty("com.android.tools.r8.enableEnumUnboxingDebugLogs") != null;
            this.O = false;
            this.P = false;
            this.Q = false;
            this.R = false;
            this.S = false;
            this.T = System.getProperty("com.android.tools.r8.pc2pcOverheadThreshold") != null ? Integer.parseInt(System.getProperty("com.android.tools.r8.pc2pcOverheadThreshold")) : 200000;
            this.U = null;
            this.V = false;
            this.W = false;
            this.X = false;
            this.Y = null;
            this.Z = null;
            this.a0 = -1;
            this.b0 = false;
            this.c0 = false;
            this.d0 = System.out;
            this.e0 = System.getProperty("com.android.tools.r8.trackDesugaredAPIConversions") != null;
            this.f0 = false;
            this.g0 = false;
            this.h0 = false;
            this.i0 = -1;
            this.j0 = System.getProperty("com.android.tools.r8.forceIRForCfToCfDesugar") != null;
            this.k0 = false;
            this.l0 = System.getProperty("com.android.tools.r8.allowInvalidCfAccessFlags") != null;
            this.m0 = System.getProperty("com.android.tools.r8.verifyInputs") != null;
            this.n0 = false;
            this.o0 = new HashSet();
            this.p0 = System.getProperty("com.android.tools.r8.enableTestAssertions") != null;
            this.q0 = System.getProperty("com.android.tools.r8.disableMarkingMethodsFinal") != null;
            this.r0 = System.getProperty("com.android.tools.r8.disableMarkingClassesFinal") != null;
            this.s0 = false;
            this.t0 = true;
            this.u0 = true;
            this.v0 = false;
            this.w0 = true;
            this.x0 = false;
            this.y0 = true;
            this.z0 = false;
            this.A0 = false;
            this.B0 = false;
            this.C0 = -1;
            this.D0 = new a();
            this.E0 = null;
            this.F0 = false;
            this.G0 = new b();
            this.H0 = C3912j0.b();
            this.I0 = null;
            this.J0 = false;
            this.K0 = false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ com.android.tools.r8.dex.W a(com.android.tools.r8.dex.W w, com.android.tools.r8.dex.r0 r0Var) {
            return w;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ C0729q2 a(C0702m c0702m, Iterable iterable, C0729q2 c0729q2) {
            return c0729q2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void a(Deque deque) {
        }

        /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
        public static class a {
            public Comparator<C0684j2> a = null;

            public final Comparator a(Comparator comparator) {
                return this.a != null ? new Comparator() { // from class: com.android.tools.r8.utils.A1$p$a$$ExternalSyntheticLambda0
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        return this.f$0.a((C2173hh) obj, (C2173hh) obj2);
                    }
                } : comparator;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ int a(C2173hh c2173hh, C2173hh c2173hh2) {
                return this.a.compare(((com.android.tools.r8.graph.W0) c2173hh.a()).getReference(), ((com.android.tools.r8.graph.W0) c2173hh2.a()).getReference());
            }
        }

        public final void a(C0702m c0702m) {
            if (this.g == null && !this.f) {
                this.f = true;
                String property = System.getProperty("com.android.tools.r8.checkdeterminism");
                if (property != null) {
                    a(B0.a(Paths.get(property, new String[0])));
                }
            }
            B0 b0 = this.g;
            if (b0 != null) {
                b0.a(c0702m);
            }
        }

        public final void a(InterfaceC3905h3 interfaceC3905h3) {
            if (this.g == null && !this.f) {
                this.f = true;
                String property = System.getProperty("com.android.tools.r8.checkdeterminism");
                if (property != null) {
                    a(B0.a(Paths.get(property, new String[0])));
                }
            }
            B0 b0 = this.g;
            if (b0 != null) {
                interfaceC3905h3.a(b0);
            }
        }

        public void a(B0 b0) {
            this.g = b0;
        }

        public void a(BiFunction<com.android.tools.r8.dex.W, com.android.tools.r8.dex.r0, com.android.tools.r8.dex.W> biFunction) {
            this.o = biFunction;
        }
    }

    public A1() {
        this.m = System.getProperty("com.android.tools.r8.printtimes") != null;
        this.n = System.getProperty("com.android.tools.r8.printmemory") != null;
        this.o = false;
        this.p = true;
        this.q = new D1(AbstractC1639am.c("j$."), AbstractC1639am.c("java."));
        this.r = false;
        this.s = System.getProperty("com.android.tools.r8.fieldBitAccessAnalysis") != null;
        this.t = true;
        this.u = true;
        this.v = true;
        this.w = true;
        this.x = true;
        this.y = true;
        this.z = true;
        this.A = true;
        this.B = true;
        this.C = true;
        this.D = true;
        this.E = true;
        this.F = true;
        this.G = false;
        this.H = false;
        this.I = new BiPredicate() { // from class: com.android.tools.r8.utils.A1$$ExternalSyntheticLambda5
            @Override // java.util.function.BiPredicate
            public final boolean test(Object obj, Object obj2) {
                return A1.a((String) obj, (Long) obj2);
            }
        };
        this.J = false;
        this.K = false;
        this.L = System.getProperty("com.android.tools.r8.disableL8AnnotationRemoval") != null;
        this.M = 50;
        this.N = Thread.currentThread();
        this.O = true;
        this.P = true;
        this.Q = 3;
        this.R = true;
        this.S = true;
        this.T = new m();
        this.U = true;
        this.V = true;
        this.W = false;
        this.X = true;
        this.Y = false;
        this.Z = "";
        this.a0 = O1 ? 1 : -1;
        this.b0 = false;
        this.c0 = System.getProperty("com.android.tools.r8.strictdebuginfo") != null;
        this.d0 = false;
        this.e0 = false;
        this.f0 = System.getProperty("com.android.tools.r8.createSingletonsForStatelessLambdas") != null;
        this.g0 = System.getProperty("com.android.tools.r8.emitRecordAnnotationsInDex") != null;
        this.h0 = System.getProperty("com.android.tools.r8.emitRecordAnnotationsExInDex") != null;
        this.i0 = System.getProperty("com.android.tools.r8.emitNestAnnotationsInDex") != null;
        this.j0 = System.getProperty("com.android.tools.r8.emitPermittedSubclassesAnnotationsInDex") != null;
        this.k0 = O0.a();
        this.m0 = null;
        this.n0 = false;
        this.p0 = w0();
        this.q0 = v0();
        this.r0 = AbstractC1639am.i();
        this.s0 = EnumC3891f.b();
        this.t0 = false;
        this.u0 = false;
        this.v0 = true;
        this.w0 = System.getProperty("com.android.tools.r8.ignoreBootClasspathEnumsForMaindexTracing") != null;
        this.x0 = System.getProperty("com.android.tools.r8.pruneNonVissibleAnnotationClasses") != null;
        this.y0 = AbstractC1639am.i();
        this.z0 = true;
        this.A0 = g.ON;
        this.B0 = false;
        this.C0 = false;
        this.D0 = System.getProperty("com.android.tools.r8.throwErrorOnBackport") != null;
        this.E0 = true;
        EnumC3904h2 enumC3904h2 = EnumC3904h2.Auto;
        this.F0 = enumC3904h2;
        this.G0 = enumC3904h2;
        this.H0 = true;
        this.I0 = false;
        this.J0 = System.getProperty("com.android.tools.r8.disableEnqueuerDeferredTracing") == null;
        this.K0 = false;
        this.L0 = true;
        this.O0 = false;
        this.P0 = false;
        this.Q0 = false;
        this.R0 = false;
        this.S0 = false;
        this.T0 = null;
        this.U0 = false;
        this.V0 = false;
        this.W0 = false;
        this.X0 = new F1(this);
        this.Y0 = new c();
        this.Z0 = new d();
        this.a1 = new e();
        this.b1 = new i(this);
        this.c1 = new h();
        this.d1 = new l();
        this.e1 = new o();
        this.f1 = new RQ(this);
        this.g1 = new C1();
        this.h1 = new a();
        this.i1 = new f();
        this.j1 = new k();
        this.k1 = new com.android.tools.r8.internal.D3(this);
        this.l1 = new C3065tW();
        this.m1 = new C2927rW();
        this.n1 = new p();
        this.o1 = AbstractC1639am.i();
        this.q1 = true;
        this.r1 = j.b;
        this.s1 = new HashMap();
        this.t1 = new HashMap();
        this.u1 = new HashMap();
        this.v1 = false;
        this.w1 = null;
        this.x1 = null;
        this.y1 = null;
        this.z1 = null;
        this.A1 = null;
        this.B1 = null;
        this.C1 = C2215iD.g();
        this.D1 = false;
        this.E1 = null;
        this.F1 = null;
        this.G1 = null;
        this.H1 = null;
        this.I1 = null;
        this.J1 = null;
        this.K1 = null;
        this.L1 = OU.a();
        this.M1 = new AtomicBoolean(false);
        this.N1 = OU.a();
        this.e = new O2();
        this.a = new C0722p1();
        this.d = null;
        this.M0 = false;
        this.N0 = false;
    }

    public static EnumC3891f H1() {
        return EnumC3891f.N;
    }

    public static EnumC3891f Q0() {
        return EnumC3891f.O;
    }

    public static EnumC3891f R0() {
        return EnumC3891f.O;
    }

    public static EnumC3891f S0() {
        return EnumC3891f.P;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(String str, Long l2) {
        return true;
    }

    public static boolean b() {
        return !R1;
    }

    public static EnumC3891f f0() {
        return EnumC3891f.P;
    }

    public static EnumC3891f g0() {
        return EnumC3891f.P;
    }

    public static EnumC3891f h0() {
        return EnumC3891f.N;
    }

    private void q1() {
        if (!R1 && this.d.E()) {
            throw new AssertionError();
        }
        D0().h().a = true;
        D0().h().k = true;
        D0().h().g = true;
        D0().h().h = true;
        D0().h().i = true;
    }

    private static AbstractC1048Hm v0() {
        String property = System.getProperty("com.android.tools.r8.extensiveInterfaceMethodMinifierLoggingFilter");
        if (property == null) {
            return AbstractC1048Hm.k();
        }
        C0862Bm c0862BmG = AbstractC1048Hm.g();
        Y2.a(property, ';', (Consumer) new A1$$ExternalSyntheticLambda1(c0862BmG));
        return c0862BmG.a();
    }

    private static AbstractC1048Hm w0() {
        String property = System.getProperty("com.android.tools.r8.extensiveLoggingFilter");
        if (property == null) {
            return AbstractC1048Hm.k();
        }
        C0862Bm c0862BmG = AbstractC1048Hm.g();
        Y2.a(property, ';', (Consumer) new A1$$ExternalSyntheticLambda1(c0862BmG));
        return c0862BmG.a();
    }

    public static EnumC3891f w1() {
        return EnumC3891f.N;
    }

    public final boolean A() {
        return a(EnumC3891f.K);
    }

    public final com.android.tools.r8.dex.U A0() {
        if (!R1 && this.m0 == null) {
            throw new AssertionError();
        }
        if (this.n0) {
            return this.o0;
        }
        com.android.tools.r8.dex.U.b bVar = this.m0;
        com.android.tools.r8.dex.U uA = new com.android.tools.r8.dex.U(bVar).d(Version.LABEL).a(this.W0 ? CompilationMode.DEBUG : CompilationMode.RELEASE).a(this.f instanceof ClassFileConsumer ? com.android.tools.r8.dex.U.a.CF : com.android.tools.r8.dex.U.a.DEX).a(this.H);
        if (Y0() || this.A0 == g.ON) {
            uA.a(B0().d());
        }
        if (this.C1.p() != null) {
            uA.a(this.C1.p());
        }
        if (Version.isDevelopmentVersion()) {
            uA.c(L3.c.b());
        }
        if (bVar == com.android.tools.r8.dex.U.b.R8) {
            uA.b(this.S0 ? "compatibility" : "full");
        }
        if (this.u0) {
            uA.r();
        }
        return uA;
    }

    public final F1 A1() {
        return this.X0;
    }

    public final boolean B() {
        return a(EnumC3891f.Q);
    }

    public final EnumC3891f B0() {
        if (R1 || this.A0.b() || Y0() || this.s0.equals(EnumC3891f.B)) {
            return this.s0;
        }
        throw new AssertionError();
    }

    public final boolean B1() {
        return !b(EnumC3891f.Q);
    }

    public final boolean C() {
        return a(EnumC3891f.M);
    }

    public l C0() {
        return this.d1;
    }

    public final boolean C1() {
        return this.A0.b() && !Y();
    }

    public final boolean D() {
        return a(EnumC3891f.L);
    }

    public com.android.tools.r8.shaking.L2 D0() {
        return this.d;
    }

    public final boolean D1() {
        return this.A0.b() && !b(EnumC3891f.T) && this.B0;
    }

    public final boolean E() {
        return Y0() && a(EnumC3891f.T);
    }

    public RQ E0() {
        return this.f1;
    }

    public final boolean E1() {
        if (R1 || this.D1 || D0() != null) {
            return this.D1 || D0().h().s;
        }
        throw new AssertionError();
    }

    public final boolean F() {
        return a(EnumC3891f.K);
    }

    public C2927rW F0() {
        return this.m1;
    }

    public final boolean F1() {
        return o1() || k1() || D0().x();
    }

    public final boolean G() {
        return b(EnumC3891f.L);
    }

    public C3065tW G0() {
        return this.l1;
    }

    public void G1() {
        ProgramConsumer programConsumer = this.f;
        if (programConsumer != null) {
            programConsumer.finished(this.e);
            DataResourceConsumer dataResourceConsumer = this.j;
            if (dataResourceConsumer != null) {
                dataResourceConsumer.finished(this.e);
            }
        }
        C3451yh c3451yh = this.k;
        if (c3451yh != null) {
            Iterator it = c3451yh.b().iterator();
            while (it.hasNext()) {
                ProgramConsumer programConsumer2 = ((FeatureSplit) it.next()).getProgramConsumer();
                programConsumer2.finished(this.e);
                DataResourceConsumer dataResourceConsumer2 = programConsumer2.getDataResourceConsumer();
                if (dataResourceConsumer2 != null) {
                    dataResourceConsumer2.finished(this.e);
                }
            }
        }
        DesugarGraphConsumer desugarGraphConsumer = this.H1;
        if (desugarGraphConsumer != null) {
            desugarGraphConsumer.finished();
        }
    }

    public final boolean H() {
        return b(EnumC3891f.L);
    }

    public final SyntheticInfoConsumer H0() {
        return this.i;
    }

    public final boolean I() {
        return b(EnumC3891f.K);
    }

    public p I0() {
        return this.n1;
    }

    public final void I1() {
        if (this.M1.compareAndSet(false, true)) {
            this.e.warning(new StringDiagnostic("Could not read the kotlin metadata message reflectively which indicates the compiler running in the context of a Security Manager. Not being able to read the kotlin metadata will have a negative effect oncode size"));
        }
    }

    public final boolean J() {
        return b((EnumC3891f) null);
    }

    public final G00 J0() {
        return this.C1.E() ? new F00(this.C1) : new E00();
    }

    public final boolean K() {
        return b(EnumC3891f.P);
    }

    public final boolean K0() {
        return this.h != null;
    }

    public final boolean L() {
        return b(EnumC3891f.P);
    }

    public final boolean L0() {
        return this.r0.size() > 0;
    }

    public final boolean M() {
        return b(EnumC3891f.N);
    }

    public final boolean M0() {
        return this.d != null;
    }

    public final boolean N() {
        boolean z = this.n1.y0;
        return z ? z : this.f instanceof ClassFileConsumer;
    }

    public final boolean N0() {
        return this.y1 != null;
    }

    public final boolean O() {
        return b(EnumC3891f.O);
    }

    public h O0() {
        return this.c1;
    }

    public final boolean P() {
        return b(EnumC3891f.O);
    }

    public i P0() {
        return this.b1;
    }

    public final boolean Q() {
        return b(EnumC3891f.O);
    }

    public final boolean R() {
        return b(EnumC3891f.K);
    }

    public final boolean S() {
        return b(EnumC3891f.N);
    }

    public final boolean T() {
        if (!R1 && !Y0()) {
            throw new AssertionError();
        }
        if (!this.t0) {
            if (!B0().a(EnumC3891f.L)) {
                return false;
            }
        }
        return true;
    }

    public final boolean T0() {
        return (this.f instanceof ClassFileConsumer) && this.A0.b();
    }

    public final boolean U() {
        SourceFileProvider sourceFileProvider;
        if (Y0() && this.r1 == j.b) {
            if (B0().a(EnumC3891f.O) && (sourceFileProvider = this.K1) != null && sourceFileProvider.allowDiscardingSourceFile()) {
                return true;
            }
        }
        return false;
    }

    public final boolean U0() {
        return this.C1.d();
    }

    public final boolean V() {
        return b((EnumC3891f) null) || this.i0;
    }

    public final boolean V0() {
        return this.A0.b();
    }

    public final boolean W() {
        return b(EnumC3891f.L);
    }

    public final boolean W0() {
        return this.S0;
    }

    public final boolean X() {
        return b(EnumC3891f.N);
    }

    public final boolean X0() {
        return this.f instanceof ClassFileConsumer;
    }

    public final boolean Y() {
        return b((EnumC3891f) null) || this.g0;
    }

    public final boolean Y0() {
        ProgramConsumer programConsumer = this.f;
        return (programConsumer instanceof DexIndexedConsumer) || (programConsumer instanceof DexFilePerClassFileConsumer);
    }

    public final boolean Z() {
        return b(EnumC3891f.P);
    }

    public final boolean Z0() {
        return this.f != null && this.A0.b() && this.F0 == EnumC3904h2.Auto && !b(EnumC3891f.N);
    }

    public final boolean a0() {
        return b((EnumC3891f) null) || this.j0;
    }

    public final boolean a1() {
        com.android.tools.r8.shaking.L2 l2 = this.d;
        return l2 == null || l2.h().e;
    }

    public final boolean b0() {
        return this.a.i() || b(EnumC3891f.R);
    }

    public final boolean b1() {
        return this.d.h().d;
    }

    public void c(EnumC3891f enumC3891f) {
        if (!R1 && enumC3891f == null) {
            throw new AssertionError();
        }
        this.s0 = enumC3891f;
    }

    public final boolean c0() {
        return b(EnumC3891f.K);
    }

    public final boolean c1() {
        return this.d.h().c;
    }

    public boolean d() {
        return a(EnumC3891f.J);
    }

    public final boolean d0() {
        if (this.c == null) {
            if (R1 || !this.b.get()) {
                return false;
            }
            throw new AssertionError();
        }
        if (this.b.get()) {
            return true;
        }
        if (!this.c.cancel()) {
            return false;
        }
        this.b.set(true);
        return true;
    }

    public final boolean d1() {
        return this.d.h().m;
    }

    public final boolean e() {
        return a(EnumC3891f.Q);
    }

    public e e0() {
        return this.a1;
    }

    public final boolean e1() {
        return this.d.h().o;
    }

    public final boolean f() {
        return a(EnumC3891f.Q);
    }

    public final boolean f1() {
        return this.d.h().q;
    }

    public final boolean g() {
        return a(EnumC3891f.M);
    }

    public final boolean g1() {
        return this.d.h().l;
    }

    public final boolean h() {
        return a(EnumC3891f.M);
    }

    public final boolean h1() {
        return this.d.h().n;
    }

    public final boolean i() {
        return a(EnumC3891f.L);
    }

    public f i0() {
        return this.i1;
    }

    public final boolean i1() {
        return this.d.h().p;
    }

    public final boolean j() {
        return a(EnumC3891f.L);
    }

    public C0722p1 j0() {
        return this.a;
    }

    public final boolean j1() {
        return c().b;
    }

    public final boolean k() {
        return a(EnumC3891f.L);
    }

    final void k0() {
        l0();
        this.E = false;
        this.F = false;
    }

    public final boolean k1() {
        com.android.tools.r8.shaking.L2 l2;
        if (R1 || (l2 = this.d) == null || this.N0 == l2.E()) {
            return this.N0;
        }
        throw new AssertionError();
    }

    public final boolean l() {
        return a(EnumC3891f.J_MR1);
    }

    public final void l0() {
        this.b1.a = false;
        this.y = false;
        this.v = false;
        this.t = false;
        this.w = false;
        this.T.a = false;
        this.R = false;
        this.B = false;
        this.G = false;
        this.A = false;
        this.Y0.a();
        this.c1.m();
    }

    public final boolean l1() {
        return M0() && D0().F();
    }

    public final boolean m() {
        return a(EnumC3891f.L);
    }

    public void m0() {
        this.E = false;
    }

    public final boolean m1() {
        return !this.W0;
    }

    public final boolean n() {
        return a(EnumC3891f.N);
    }

    public final boolean n0() {
        return this.A0.b() && this.s0.e(EnumC3891f.ANDROID_PLATFORM);
    }

    public final boolean n1() {
        com.android.tools.r8.shaking.L2 l2;
        if (this.W0 || (l2 = this.d) == null) {
            return false;
        }
        n nVarO = l2.o();
        nVarO.getClass();
        if (nVarO == n.a) {
            return false;
        }
        return k1() || !this.S0;
    }

    public final boolean o() {
        return a(EnumC3891f.N);
    }

    final void o0() {
        this.s = true;
        o oVar = this.e1;
        oVar.b = true;
        oVar.c = true;
        oVar.a = true;
        oVar.e = true;
    }

    public final boolean o1() {
        com.android.tools.r8.shaking.L2 l2;
        if (R1 || (l2 = this.d) == null || this.M0 == l2.L()) {
            return this.M0;
        }
        throw new AssertionError();
    }

    public final boolean p() {
        if (R1 || Y0()) {
            return !this.W0 && a(EnumC3891f.M);
        }
        throw new AssertionError();
    }

    public final boolean p0() {
        int i2 = AbstractC3997z1.a[this.G0.ordinal()];
        if (i2 == 1) {
            return false;
        }
        if (i2 == 2) {
            return this.A0.b() && !b(EnumC3891f.K);
        }
        throw new C3405y10();
    }

    public final boolean p1() {
        return this.P && !this.W0;
    }

    public final boolean q() {
        return a(EnumC3891f.Q);
    }

    public com.android.tools.r8.internal.D3 q0() {
        return this.k1;
    }

    public final boolean r() {
        return a(EnumC3891f.M);
    }

    public d r0() {
        return this.Z0;
    }

    public final boolean r1() {
        return D0().h().e || D0().h().c;
    }

    public final boolean s() {
        return a(EnumC3891f.S);
    }

    public final ClassFileConsumer s0() {
        return (ClassFileConsumer) this.f;
    }

    public final C1 s1() {
        return this.g1;
    }

    public final boolean t() {
        return a(EnumC3891f.N);
    }

    public final DexIndexedConsumer t0() {
        return (DexIndexedConsumer) this.f;
    }

    public k t1() {
        return this.j1;
    }

    public final boolean u() {
        return a(EnumC3891f.M);
    }

    public final O0 u0() {
        return this.k0;
    }

    public final boolean u1() {
        return a1();
    }

    public final boolean v() {
        return a(EnumC3891f.M);
    }

    public final void v1() {
        boolean zE;
        if (this.t1.size() > 0) {
            this.e.info(new StringDiagnostic("Invalid parameter counts in MethodParameter attributes. This is likely due to Proguard having removed a parameter."));
            for (Origin origin : new TreeSet(this.t1.keySet())) {
                StringBuilder sb = new StringBuilder("Methods with invalid MethodParameter attributes:");
                for (B1 b1 : (List) this.t1.get(origin)) {
                    sb.append("\n  ").append(b1.a).append(" expected count: ").append(b1.b).append(" actual count: ").append(b1.c);
                }
                this.e.info(new StringDiagnostic(sb.toString(), origin));
            }
        }
        if (this.u1.size() > 0) {
            Iterator it = this.u1.values().iterator();
            int size = 0;
            while (it.hasNext()) {
                size += ((List) it.next()).size();
            }
            this.e.info(new StringDiagnostic("Stripped invalid locals information from " + size + (size == 1 ? " method." : " methods.")));
            for (Origin origin2 : new TreeSet(this.u1.keySet())) {
                StringBuilder sb2 = new StringBuilder("Methods with invalid locals information:");
                for (C3979v2 c3979v2 : (List) this.u1.get(origin2)) {
                    sb2.append("\n  ").append(((o5) c3979v2.a()).w());
                    sb2.append("\n  ").append((String) c3979v2.b());
                }
                this.e.info(new StringDiagnostic(sb2.toString(), origin2));
            }
            zE = true;
        } else {
            zE = false;
        }
        if (this.s1.size() > 0) {
            this.e.info(new StringDiagnostic("InnerClasses attribute has entries missing a corresponding EnclosingMethod attribute. Such InnerClasses attribute entries are ignored."));
            for (Origin origin3 : new TreeSet(this.s1.keySet())) {
                StringBuilder sb3 = new StringBuilder("Classes with missing EnclosingMethod: ");
                boolean z = true;
                for (G1 g1 : (List) this.s1.get(origin3)) {
                    if (z) {
                        z = false;
                    } else {
                        sb3.append(", ");
                    }
                    sb3.append(g1.b);
                    zE |= g1.a.e(Z8.e);
                }
                this.e.info(new StringDiagnostic(sb3.toString(), origin3));
            }
        }
        if (zE) {
            this.e.info(new StringDiagnostic("Some warnings are typically a sign of using an outdated Java toolchain. To fix, recompile the source with an updated toolchain."));
        }
    }

    public final boolean w() {
        return Y0() && this.s0.a(EnumC3891f.L);
    }

    public final boolean x() {
        return a(EnumC3891f.L);
    }

    public final GlobalSyntheticsConsumer x0() {
        return this.h;
    }

    public o x1() {
        return this.e1;
    }

    public final boolean y() {
        return a(EnumC3891f.L);
    }

    public final H1 y0() {
        if (!R1 && this.f == null) {
            throw new AssertionError();
        }
        ProgramConsumer programConsumer = this.f;
        if (programConsumer instanceof DexIndexedConsumer) {
            return H1.a;
        }
        if (programConsumer instanceof DexFilePerClassFileConsumer) {
            return H1.b;
        }
        if (programConsumer instanceof ClassFileConsumer) {
            return H1.c;
        }
        throw new UnsupportedOperationException("Cannot find internal output mode.");
    }

    public final Z8 y1() {
        if (R1 || (this.f instanceof ClassFileConsumer)) {
            return Z8.e;
        }
        throw new AssertionError();
    }

    public final boolean z() {
        return a(EnumC3891f.N);
    }

    public final MapVersion z0() {
        return this.n1.J0 ? MapVersion.MAP_VERSION_EXPERIMENTAL : MapVersion.STABLE;
    }

    public final void z1() {
        this.B1 = null;
        this.C1 = C2215iD.g();
    }

    private static String b(AbstractC0727q0 abstractC0727q0) {
        return "Class " + abstractC0727q0.d.F0() + " requires its nest host " + abstractC0727q0.g1().F0() + " to be on program or class path.";
    }

    public final void a(boolean z) {
        if (!R1 && this.u0) {
            throw new AssertionError();
        }
        if (!z) {
            EnumC3891f enumC3891f = this.s0;
            enumC3891f.getClass();
            if (enumC3891f != EnumC3891f.ANDROID_PLATFORM) {
                return;
            }
        }
        a().b();
        this.C0 = true;
        this.u0 = z;
    }

    public c c() {
        return this.Y0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ List c(Origin origin) {
        return new ArrayList();
    }

    private static String b(C3048tG c3048tG) {
        final ArrayList arrayList = new ArrayList();
        final ArrayList arrayList2 = new ArrayList();
        final ArrayList arrayList3 = new ArrayList();
        c3048tG.b().a(new Consumer() { // from class: com.android.tools.r8.utils.A1$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                arrayList.add((C0729q2) obj);
            }
        }, new Consumer() { // from class: com.android.tools.r8.utils.A1$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                arrayList2.add((C0754u0) obj);
            }
        }, new Consumer() { // from class: com.android.tools.r8.utils.A1$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                arrayList3.add((C0635d2) obj);
            }
        });
        Iterator it = c3048tG.c().iterator();
        while (it.hasNext()) {
            ((AbstractC0727q0) it.next()).a(new Consumer() { // from class: com.android.tools.r8.utils.A1$$ExternalSyntheticLambda6
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    arrayList.add((C0729q2) obj);
                }
            }, new Consumer() { // from class: com.android.tools.r8.utils.A1$$ExternalSyntheticLambda7
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    arrayList2.add((C0754u0) obj);
                }
            }, new Consumer() { // from class: com.android.tools.r8.utils.A1$$ExternalSyntheticLambda8
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    arrayList3.add((C0635d2) obj);
                }
            });
        }
        StringBuilder sbAppend = new StringBuilder("Compilation of classes ").append(Y2.a(", ", arrayList, new Function() { // from class: com.android.tools.r8.utils.A1$$ExternalSyntheticLambda9
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((C0729q2) obj).o1();
            }
        })).append(" requires its nest mates ");
        if (c3048tG.e()) {
            sbAppend.append(Y2.a(", ", c3048tG.d(), new C0796x$$ExternalSyntheticLambda0())).append(" (unavailable) ");
        }
        if (!arrayList3.isEmpty()) {
            sbAppend.append(Y2.a(", ", arrayList3, new Function() { // from class: com.android.tools.r8.utils.A1$$ExternalSyntheticLambda10
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((C0635d2) obj).o1();
                }
            })).append(" (on library path) ");
        }
        sbAppend.append("to be on program or class path.");
        if (!arrayList2.isEmpty()) {
            sbAppend.append("(Classes ").append(Y2.a(", ", arrayList2, new Function() { // from class: com.android.tools.r8.utils.A1$$ExternalSyntheticLambda11
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((C0754u0) obj).o1();
                }
            })).append(" from the same nest are on class path).");
        }
        return sbAppend.toString();
    }

    static {
        O1 = System.getProperty("com.android.tools.r8.deterministicdebugging") != null;
        P1 = Z8.l;
        EnumC3891f.L.c().getClass();
        Q1 = H0.V41.b();
    }

    public void a(com.android.tools.r8.dex.U u) {
        this.n0 = true;
        this.o0 = u;
    }

    public void a(O0 o0) {
        this.k0 = o0;
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static class a {
        public boolean a;
        public boolean b;
        public boolean c;
        public boolean d;
        public boolean e;
        public boolean f;
        public Map<MethodReference, EnumC3891f> g;
        public Map<FieldReference, EnumC3891f> h;
        public Map<ClassReference, EnumC3891f> i;
        public BiConsumer<MethodReference, com.android.tools.r8.androidapi.h> j;

        public a() {
            this.a = System.getProperty("com.android.tools.r8.disableApiModeling") == null;
            this.b = System.getProperty("com.android.tools.r8.disableApiModeling") == null;
            this.c = System.getProperty("com.android.tools.r8.disableApiModeling") == null;
            this.d = System.getProperty("com.android.tools.r8.disableApiModeling") == null;
            this.e = System.getProperty("com.android.tools.r8.disableApiModeling") == null;
            this.f = System.getProperty("com.android.tools.r8.reportUnknownApiReferences") != null;
            this.g = new HashMap();
            this.h = new HashMap();
            this.i = new HashMap();
            this.j = null;
        }

        public final void a(final C0722p1 c0722p1, final BiConsumer biConsumer) {
            if (this.g.isEmpty() && this.h.isEmpty() && this.i.isEmpty()) {
                return;
            }
            this.i.forEach(new BiConsumer() { // from class: com.android.tools.r8.utils.A1$a$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    A1.a.a(biConsumer, c0722p1, (ClassReference) obj, (EnumC3891f) obj2);
                }
            });
            this.h.forEach(new BiConsumer() { // from class: com.android.tools.r8.utils.A1$a$$ExternalSyntheticLambda1
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    A1.a.a(biConsumer, c0722p1, (FieldReference) obj, (EnumC3891f) obj2);
                }
            });
            this.g.forEach(new BiConsumer() { // from class: com.android.tools.r8.utils.A1$a$$ExternalSyntheticLambda2
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    A1.a.a(biConsumer, c0722p1, (MethodReference) obj, (EnumC3891f) obj2);
                }
            });
        }

        public void b() {
            this.a = false;
            this.b = false;
            this.e = false;
            this.d = false;
            this.c = false;
        }

        public void c() {
            this.e = false;
            this.d = false;
        }

        public boolean d() {
            return this.a && this.b;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void a(BiConsumer biConsumer, C0722p1 c0722p1, ClassReference classReference, EnumC3891f enumC3891f) {
            biConsumer.accept(c0722p1.e(classReference.getDescriptor()), enumC3891f);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void a(BiConsumer biConsumer, C0722p1 c0722p1, FieldReference fieldReference, EnumC3891f enumC3891f) {
            biConsumer.accept(c0722p1.a(fieldReference), enumC3891f);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ void a(BiConsumer biConsumer, C0722p1 c0722p1, MethodReference methodReference, EnumC3891f enumC3891f) {
            biConsumer.accept(c0722p1.a(methodReference), enumC3891f);
        }

        public void a() {
            this.b = false;
        }
    }

    public final void a(GlobalSyntheticsConsumer globalSyntheticsConsumer) {
        this.h = globalSyntheticsConsumer;
    }

    public final void a(SyntheticInfoConsumer syntheticInfoConsumer) {
        this.i = syntheticInfoConsumer;
    }

    public final boolean a(com.android.tools.r8.shaking.L.a aVar) {
        if (aVar.c()) {
            return (this.c1.a(EnumC0928Dk.a) && !this.c1.g()) || this.t;
        }
        if (aVar.b()) {
            return this.c1.a(EnumC0928Dk.b) && !this.c1.g();
        }
        if (R1) {
            return false;
        }
        throw new AssertionError();
    }

    public a a() {
        return this.h1;
    }

    public void a(final InterfaceC1206Ne interfaceC1206Ne) {
        if (interfaceC1206Ne.isEmpty()) {
            return;
        }
        this.B1 = new InterfaceC3890e3() { // from class: com.android.tools.r8.utils.A1$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.utils.InterfaceC3890e3
            public final void accept(Object obj, Object obj2) {
                this.f$0.a(interfaceC1206Ne, (y3) obj, (AbstractC0681j0) obj2);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(InterfaceC1206Ne interfaceC1206Ne, y3 y3Var, AbstractC0681j0 abstractC0681j0) {
        this.C1 = interfaceC1206Ne.a(abstractC0681j0, y3Var);
    }

    public void a(y3 y3Var, AbstractC0681j0 abstractC0681j0) throws Throwable {
        if (this.B1 == null) {
            return;
        }
        y3Var.a("Load machine specification");
        this.B1.accept(y3Var, abstractC0681j0);
        y3Var.c();
    }

    public final void a(AbstractC0727q0 abstractC0727q0) {
        this.e.a(new MissingNestHostNestDesugarDiagnostic(abstractC0727q0.c, Position.UNKNOWN, b(abstractC0727q0)));
        throw null;
    }

    public final void a(C3048tG c3048tG) {
        this.e.a(new IncompleteNestNestDesugarDiagnosic(c3048tG.b().c, Position.UNKNOWN, b(c3048tG)));
        throw null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ List b(Origin origin) {
        return new ArrayList();
    }

    public final void a(Origin origin, MethodPosition methodPosition, C0767v2 c0767v2, C0684j2 c0684j2) {
        if (this.L1.add(c0767v2)) {
            this.e.warning(new InterfaceDesugarMissingTypeDiagnostic(origin, methodPosition, Reference.classFromDescriptor(c0767v2.b1()), Reference.classFromDescriptor(c0684j2.e.b1()), null));
        }
    }

    public final boolean b(com.android.tools.r8.graph.W0 w0) {
        if (this.y0.size() == 0) {
            return false;
        }
        return this.y0.contains(w0.U1());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean b(EnumC3891f enumC3891f) {
        if (this.A0.b() || Y0()) {
            return enumC3891f != null && B0().a(enumC3891f);
        }
        if (R1 || this.s0.equals(EnumC3891f.B)) {
            return true;
        }
        throw new AssertionError();
    }

    public final void a(AbstractC0727q0 abstractC0727q0, AbstractC0727q0 abstractC0727q1, C0767v2 c0767v2) {
        if (this.L1.add(c0767v2)) {
            this.e.warning(new InterfaceDesugarMissingTypeDiagnostic(abstractC0727q0.c, Position.UNKNOWN, Reference.classFromDescriptor(c0767v2.b1()), Reference.classFromDescriptor(abstractC0727q0.getType().b1()), abstractC0727q0 == abstractC0727q1 ? null : Reference.classFromDescriptor(abstractC0727q1.getType().b1())));
        }
    }

    public final Z8 b(Z8 z8) {
        if (!R1 && !(this.f instanceof ClassFileConsumer)) {
            throw new AssertionError();
        }
        if (!this.A0.b()) {
            return z8;
        }
        Z8 z9 = b(EnumC3891f.N) ? Z8.h : Z8.g;
        return z9.e(z8) ? z9 : z8;
    }

    public final void a(Origin origin, C0767v2 c0767v2, C0767v2 c0767v3, String str, Set set) {
        if (this.N1.add(c0767v3)) {
            this.e.warning(new InvalidLibrarySuperclassDiagnostic(origin, Reference.classFromDescriptor(c0767v2.b1()), Reference.classFromDescriptor(c0767v3.b1()), str, AbstractC2763pC.a(AbstractC2734ot.a((Iterable) set, new InterfaceC3010sj() { // from class: com.android.tools.r8.utils.A1$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((C0684j2) obj).E0();
                }
            }))));
        }
    }

    public final void a(C0767v2 c0767v2, Origin origin, Z8 z8) {
        G1 g1 = new G1(z8, c0767v2);
        synchronized (this.s1) {
            ((List) this.s1.computeIfAbsent(origin, new Function() { // from class: com.android.tools.r8.utils.A1$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return A1.c((Origin) obj);
                }
            })).add(g1);
        }
    }

    public final void a(C0684j2 c0684j2, Origin origin, int i2, int i3) {
        B1 b1 = new B1(i2, i3, c0684j2);
        synchronized (this.t1) {
            ((List) this.t1.computeIfAbsent(origin, new Function() { // from class: com.android.tools.r8.utils.A1$$ExternalSyntheticLambda12
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return A1.b((Origin) obj);
                }
            })).add(b1);
        }
    }

    public final void a(o5 o5Var, Origin origin, C0994Fs c0994Fs) {
        if (!this.b0) {
            synchronized (this.u1) {
                ((List) this.u1.computeIfAbsent(origin, new Function() { // from class: com.android.tools.r8.utils.A1$$ExternalSyntheticLambda4
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return A1.a((Origin) obj);
                    }
                })).add(new C3979v2(o5Var, c0994Fs.getMessage()));
            }
            return;
        }
        throw new C1367Tb("Fatal warning: Invalid debug info", c0994Fs);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ List a(Origin origin) {
        return new ArrayList();
    }

    public final boolean a(com.android.tools.r8.graph.W0 w0) {
        if (!L0()) {
            return true;
        }
        return this.r0.contains(w0.U1());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(EnumC3891f enumC3891f) {
        if (this.A0.b() || Y0()) {
            return enumC3891f == null || !B0().a(enumC3891f);
        }
        if (R1 || this.s0.equals(EnumC3891f.B)) {
            return true;
        }
        throw new AssertionError();
    }

    public final boolean a(Z8 z8) {
        if (!R1 && !(this.f instanceof ClassFileConsumer)) {
            throw new AssertionError();
        }
        return z8.a(y1());
    }

    public A1(C0722p1 c0722p1, O2 o2) {
        this.m = System.getProperty("com.android.tools.r8.printtimes") != null;
        this.n = System.getProperty("com.android.tools.r8.printmemory") != null;
        this.o = false;
        this.p = true;
        this.q = new D1(AbstractC1639am.c("j$."), AbstractC1639am.c("java."));
        this.r = false;
        this.s = System.getProperty("com.android.tools.r8.fieldBitAccessAnalysis") != null;
        this.t = true;
        this.u = true;
        this.v = true;
        this.w = true;
        this.x = true;
        this.y = true;
        this.z = true;
        this.A = true;
        this.B = true;
        this.C = true;
        this.D = true;
        this.E = true;
        this.F = true;
        this.G = false;
        this.H = false;
        this.I = new BiPredicate() { // from class: com.android.tools.r8.utils.A1$$ExternalSyntheticLambda5
            @Override // java.util.function.BiPredicate
            public final boolean test(Object obj, Object obj2) {
                return A1.a((String) obj, (Long) obj2);
            }
        };
        this.J = false;
        this.K = false;
        this.L = System.getProperty("com.android.tools.r8.disableL8AnnotationRemoval") != null;
        this.M = 50;
        this.N = Thread.currentThread();
        this.O = true;
        this.P = true;
        this.Q = 3;
        this.R = true;
        this.S = true;
        this.T = new m();
        this.U = true;
        this.V = true;
        this.W = false;
        this.X = true;
        this.Y = false;
        this.Z = "";
        this.a0 = O1 ? 1 : -1;
        this.b0 = false;
        this.c0 = System.getProperty("com.android.tools.r8.strictdebuginfo") != null;
        this.d0 = false;
        this.e0 = false;
        this.f0 = System.getProperty("com.android.tools.r8.createSingletonsForStatelessLambdas") != null;
        this.g0 = System.getProperty("com.android.tools.r8.emitRecordAnnotationsInDex") != null;
        this.h0 = System.getProperty("com.android.tools.r8.emitRecordAnnotationsExInDex") != null;
        this.i0 = System.getProperty("com.android.tools.r8.emitNestAnnotationsInDex") != null;
        this.j0 = System.getProperty("com.android.tools.r8.emitPermittedSubclassesAnnotationsInDex") != null;
        this.k0 = O0.a();
        this.m0 = null;
        this.n0 = false;
        this.p0 = w0();
        this.q0 = v0();
        this.r0 = AbstractC1639am.i();
        this.s0 = EnumC3891f.b();
        this.t0 = false;
        this.u0 = false;
        this.v0 = true;
        this.w0 = System.getProperty("com.android.tools.r8.ignoreBootClasspathEnumsForMaindexTracing") != null;
        this.x0 = System.getProperty("com.android.tools.r8.pruneNonVissibleAnnotationClasses") != null;
        this.y0 = AbstractC1639am.i();
        this.z0 = true;
        this.A0 = g.ON;
        this.B0 = false;
        this.C0 = false;
        this.D0 = System.getProperty("com.android.tools.r8.throwErrorOnBackport") != null;
        this.E0 = true;
        EnumC3904h2 enumC3904h2 = EnumC3904h2.Auto;
        this.F0 = enumC3904h2;
        this.G0 = enumC3904h2;
        this.H0 = true;
        this.I0 = false;
        this.J0 = System.getProperty("com.android.tools.r8.disableEnqueuerDeferredTracing") == null;
        this.K0 = false;
        this.L0 = true;
        this.O0 = false;
        this.P0 = false;
        this.Q0 = false;
        this.R0 = false;
        this.S0 = false;
        this.T0 = null;
        this.U0 = false;
        this.V0 = false;
        this.W0 = false;
        this.X0 = new F1(this);
        this.Y0 = new c();
        this.Z0 = new d();
        this.a1 = new e();
        this.b1 = new i(this);
        this.c1 = new h();
        this.d1 = new l();
        this.e1 = new o();
        this.f1 = new RQ(this);
        this.g1 = new C1();
        this.h1 = new a();
        this.i1 = new f();
        this.j1 = new k();
        this.k1 = new com.android.tools.r8.internal.D3(this);
        this.l1 = new C3065tW();
        this.m1 = new C2927rW();
        this.n1 = new p();
        this.o1 = AbstractC1639am.i();
        this.q1 = true;
        this.r1 = j.b;
        this.s1 = new HashMap();
        this.t1 = new HashMap();
        this.u1 = new HashMap();
        this.v1 = false;
        this.w1 = null;
        this.x1 = null;
        this.y1 = null;
        this.z1 = null;
        this.A1 = null;
        this.B1 = null;
        this.C1 = C2215iD.g();
        this.D1 = false;
        this.E1 = null;
        this.F1 = null;
        this.G1 = null;
        this.H1 = null;
        this.I1 = null;
        this.J1 = null;
        this.K1 = null;
        this.L1 = OU.a();
        this.M1 = new AtomicBoolean(false);
        this.N1 = OU.a();
        boolean z = R1;
        if (!z && o2 == null) {
            throw new AssertionError();
        }
        if (!z && c0722p1 == null) {
            throw new AssertionError();
        }
        this.e = o2;
        this.a = c0722p1;
        this.d = null;
        this.M0 = false;
        this.N0 = false;
        l0();
    }

    public A1(CompilationMode compilationMode, com.android.tools.r8.shaking.L2 l2, O2 o2) {
        this.m = System.getProperty("com.android.tools.r8.printtimes") != null;
        this.n = System.getProperty("com.android.tools.r8.printmemory") != null;
        this.o = false;
        this.p = true;
        this.q = new D1(AbstractC1639am.c("j$."), AbstractC1639am.c("java."));
        this.r = false;
        this.s = System.getProperty("com.android.tools.r8.fieldBitAccessAnalysis") != null;
        this.t = true;
        this.u = true;
        this.v = true;
        this.w = true;
        this.x = true;
        this.y = true;
        this.z = true;
        this.A = true;
        this.B = true;
        this.C = true;
        this.D = true;
        this.E = true;
        this.F = true;
        this.G = false;
        this.H = false;
        this.I = new BiPredicate() { // from class: com.android.tools.r8.utils.A1$$ExternalSyntheticLambda5
            @Override // java.util.function.BiPredicate
            public final boolean test(Object obj, Object obj2) {
                return A1.a((String) obj, (Long) obj2);
            }
        };
        this.J = false;
        this.K = false;
        this.L = System.getProperty("com.android.tools.r8.disableL8AnnotationRemoval") != null;
        this.M = 50;
        this.N = Thread.currentThread();
        this.O = true;
        this.P = true;
        this.Q = 3;
        this.R = true;
        this.S = true;
        this.T = new m();
        this.U = true;
        this.V = true;
        this.W = false;
        this.X = true;
        this.Y = false;
        this.Z = "";
        this.a0 = O1 ? 1 : -1;
        this.b0 = false;
        this.c0 = System.getProperty("com.android.tools.r8.strictdebuginfo") != null;
        this.d0 = false;
        this.e0 = false;
        this.f0 = System.getProperty("com.android.tools.r8.createSingletonsForStatelessLambdas") != null;
        this.g0 = System.getProperty("com.android.tools.r8.emitRecordAnnotationsInDex") != null;
        this.h0 = System.getProperty("com.android.tools.r8.emitRecordAnnotationsExInDex") != null;
        this.i0 = System.getProperty("com.android.tools.r8.emitNestAnnotationsInDex") != null;
        this.j0 = System.getProperty("com.android.tools.r8.emitPermittedSubclassesAnnotationsInDex") != null;
        this.k0 = O0.a();
        this.m0 = null;
        this.n0 = false;
        this.p0 = w0();
        this.q0 = v0();
        this.r0 = AbstractC1639am.i();
        this.s0 = EnumC3891f.b();
        this.t0 = false;
        this.u0 = false;
        this.v0 = true;
        this.w0 = System.getProperty("com.android.tools.r8.ignoreBootClasspathEnumsForMaindexTracing") != null;
        this.x0 = System.getProperty("com.android.tools.r8.pruneNonVissibleAnnotationClasses") != null;
        this.y0 = AbstractC1639am.i();
        this.z0 = true;
        this.A0 = g.ON;
        this.B0 = false;
        this.C0 = false;
        this.D0 = System.getProperty("com.android.tools.r8.throwErrorOnBackport") != null;
        this.E0 = true;
        EnumC3904h2 enumC3904h2 = EnumC3904h2.Auto;
        this.F0 = enumC3904h2;
        this.G0 = enumC3904h2;
        this.H0 = true;
        this.I0 = false;
        this.J0 = System.getProperty("com.android.tools.r8.disableEnqueuerDeferredTracing") == null;
        this.K0 = false;
        this.L0 = true;
        this.O0 = false;
        this.P0 = false;
        this.Q0 = false;
        this.R0 = false;
        this.S0 = false;
        this.T0 = null;
        this.U0 = false;
        this.V0 = false;
        this.W0 = false;
        this.X0 = new F1(this);
        this.Y0 = new c();
        this.Z0 = new d();
        this.a1 = new e();
        this.b1 = new i(this);
        this.c1 = new h();
        this.d1 = new l();
        this.e1 = new o();
        this.f1 = new RQ(this);
        this.g1 = new C1();
        this.h1 = new a();
        this.i1 = new f();
        this.j1 = new k();
        this.k1 = new com.android.tools.r8.internal.D3(this);
        this.l1 = new C3065tW();
        this.m1 = new C2927rW();
        this.n1 = new p();
        this.o1 = AbstractC1639am.i();
        this.q1 = true;
        this.r1 = j.b;
        this.s1 = new HashMap();
        this.t1 = new HashMap();
        this.u1 = new HashMap();
        this.v1 = false;
        this.w1 = null;
        this.x1 = null;
        this.y1 = null;
        this.z1 = null;
        this.A1 = null;
        this.B1 = null;
        this.C1 = C2215iD.g();
        this.D1 = false;
        this.E1 = null;
        this.F1 = null;
        this.G1 = null;
        this.H1 = null;
        this.I1 = null;
        this.J1 = null;
        this.K1 = null;
        this.L1 = OU.a();
        this.M1 = new AtomicBoolean(false);
        this.N1 = OU.a();
        boolean z = R1;
        if (!z && o2 == null) {
            throw new AssertionError();
        }
        if (!z && l2 == null) {
            throw new AssertionError();
        }
        this.W0 = compilationMode == CompilationMode.DEBUG;
        this.e = o2;
        this.d = l2;
        this.a = l2.f();
        this.M0 = l2.L();
        this.N0 = l2.E();
        if (!l2.F()) {
            k0();
        }
        if (this.W0) {
            if (!z && k1()) {
                throw new AssertionError();
            }
            if (!z && l1()) {
                throw new AssertionError();
            }
            q1();
        }
        this.U0 = l2.B();
        if (l2.K()) {
            o0();
        }
    }
}
