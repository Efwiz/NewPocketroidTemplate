package com.android.tools.r8.graph;

import com.android.tools.r8.ClassFileConsumer;
import com.android.tools.r8.internal.C1230Ob;
import com.android.tools.r8.internal.C1341Sb;
import com.android.tools.r8.internal.C1821dA;
import com.android.tools.r8.internal.C2037fz;
import com.android.tools.r8.internal.C3012sl;
import com.android.tools.r8.internal.DK;
import com.android.tools.r8.internal.EK;
import com.android.tools.r8.internal.VE;
import com.beust.jcommander.Parameters;
import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import name.antonsmirnov.firmata.FormatHelper;

/* JADX INFO: renamed from: com.android.tools.r8.graph.q, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0726q extends AbstractC0709n0 {
    static final /* synthetic */ boolean l = true;
    public static final /* synthetic */ int m = 0;
    private final boolean c;
    private final boolean d;
    private final boolean e;
    private final boolean f;
    private final boolean g;
    private final C0664h h;
    private final C2037fz i;
    private final C1341Sb j;
    private final com.android.tools.r8.utils.P2 k;

    public C0726q(AbstractC0681j0 abstractC0681j0, com.android.tools.r8.utils.A1 a1, boolean z, boolean z2, boolean z3) {
        super(abstractC0681j0, a1);
        new com.android.tools.r8.utils.y3("AssemblyWriter");
        this.j = C1341Sb.a(a1);
        this.c = z;
        this.d = z;
        this.e = z;
        this.f = z2;
        this.g = z3;
        if (z2) {
            this.h = C0664h.a(abstractC0681j0.i(), com.android.tools.r8.synthesis.y.d());
            if (a1.f == null) {
                a1.f = ClassFileConsumer.emptyConsumer();
            }
            a1.T.a = false;
        } else {
            this.h = null;
        }
        this.i = new C2037fz(abstractC0681j0.e);
        this.k = com.android.tools.r8.utils.P2.a(abstractC0681j0.g(), abstractC0681j0.d.e);
    }

    private void a(final o5 o5Var) {
        final C3012sl c3012sl = new C3012sl(C0702m.a(this.h));
        final EK ekA = EK.a(o5Var, VE.a(), this.j.a());
        ekA.a(new DK() { // from class: com.android.tools.r8.graph.q$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.internal.DK
            public final void a(o5 o5Var2, C1230Ob c1230Ob) {
                C0726q.a(c3012sl, o5Var, ekA, o5Var2, c1230Ob);
            }
        });
    }

    @Override // com.android.tools.r8.graph.AbstractC0709n0
    final void b(C0729q2 c0729q2, PrintStream printStream) {
    }

    @Override // com.android.tools.r8.graph.AbstractC0709n0
    final void c(C0729q2 c0729q2, PrintStream printStream) {
        String strB = this.k.b(c0729q2.getType());
        printStream.println("# Bytecode for");
        printStream.println("# Class: '" + strB + FormatHelper.QUOTE);
        if (this.c) {
            a(c0729q2, c0729q2.s0(), printStream);
            printStream.println("# Flags: '" + c0729q2.e + FormatHelper.QUOTE);
            C0767v2 c0767v2 = c0729q2.f;
            if (c0767v2 != this.a.e.Z1) {
                printStream.println("# Extends: '" + this.k.b(c0767v2) + FormatHelper.QUOTE);
            }
            for (C0767v2 c0767v3 : c0729q2.g.a) {
                printStream.println("# Implements: '" + this.k.b(c0767v3) + FormatHelper.QUOTE);
            }
            if (!c0729q2.e1().isEmpty()) {
                printStream.println("# InnerClasses:");
                for (J3 j3 : c0729q2.e1()) {
                    printStream.println("#  Outer: " + (j3.d() != null ? this.k.b(j3.d()) : Parameters.DEFAULT_OPTION_PREFIXES) + ", inner: " + this.k.b(j3.b()) + ", inner name: " + j3.c() + ", access: " + Integer.toHexString(j3.a()));
                }
            }
            O2 o2B1 = c0729q2.b1();
            if (o2B1 != null) {
                printStream.println("# EnclosingMethod:");
                if (o2B1.a() != null) {
                    printStream.println("#  Class: " + this.k.b(o2B1.a()));
                } else {
                    printStream.println("#  Method: " + this.k.c(o2B1.b()));
                }
            }
        }
        printStream.println();
    }

    @Override // com.android.tools.r8.graph.AbstractC0709n0
    final void d(PrintStream printStream) {
        if (this.d) {
            printStream.println("#");
            printStream.println("# Fields:");
            printStream.println("#");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.tools.r8.graph.AbstractC0709n0
    /* JADX INFO: renamed from: b */
    public final void a(PrintStream printStream, T0 t0) {
        if (this.d) {
            a((C0729q2) null, t0.s0(), printStream);
            printStream.print(t0.f + " ");
            printStream.print(this.k.d(t0.getReference()));
            if (!this.k.b()) {
                printStream.println("# Residual: '" + t0.getReference().r0() + FormatHelper.QUOTE);
            }
            if (t0.E0() && t0.Z0()) {
                printStream.print(" = " + t0.Y0());
            }
            printStream.println();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C3012sl c3012sl, o5 o5Var, EK ek, o5 o5Var2, C1230Ob c1230Ob) {
        c3012sl.b(o5Var, com.android.tools.r8.ir.optimize.info.q.b(), ek, c1230Ob);
    }

    private void a(C0729q2 c0729q2, C0658g0 c0658g0, PrintStream printStream) {
        if (!this.e || c0658g0.isEmpty()) {
            return;
        }
        printStream.println("# Annotations:");
        for (C0633d0 c0633d0 : c0658g0.c) {
            C0767v2 c0767v2 = c0633d0.b.a;
            C2037fz c2037fz = this.i;
            if (c0767v2 == c2037fz.a.t3) {
                if (!l && c0729q2 == null) {
                    throw new AssertionError("Kotlin metadata is a class annotation");
                }
                C1821dA.a(c0633d0, printStream, c2037fz);
            } else {
                Stream<String> streamLines = new BufferedReader(new StringReader(c0633d0.u0() + " " + this.k.b(c0633d0.t0()) + com.android.tools.r8.utils.Y2.a(c0633d0.b.b, new Function() { // from class: com.android.tools.r8.graph.q$$ExternalSyntheticLambda1
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return this.f$0.a((C0649f0) obj);
                    }
                }, com.android.tools.r8.utils.Y2.a.SQUARE))).lines();
                String str = com.android.tools.r8.utils.Y2.c;
                printStream.print((String) streamLines.collect(Collectors.joining(new StringBuilder().append(str).append("#    ").toString(), "#  ", str)));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.tools.r8.graph.AbstractC0709n0
    /* JADX INFO: renamed from: b */
    public final void a(PrintStream printStream, o5 o5Var) {
        W wA1;
        W0 w0E = o5Var.e();
        printStream.println("#");
        printStream.println("# Method: '" + this.k.c(w0E.getReference()) + "':");
        a((C0729q2) null, w0E.s0(), printStream);
        printStream.println("# " + w0E.f);
        if (!this.k.b()) {
            printStream.println("# Residual: '" + w0E.getReference().r0() + FormatHelper.QUOTE);
        }
        printStream.println("#");
        printStream.println();
        if (this.g && (wA1 = w0E.a1()) != null) {
            if (this.f) {
                a(o5Var);
            } else {
                printStream.println(wA1.a(w0E, this.k));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String a(C0649f0 c0649f0) {
        return c0649f0.s0().toString() + " = " + a(c0649f0.t0());
    }

    private String a(B2 b2) {
        if (b2.f1()) {
            return this.k.b((C0767v2) b2.L0().j1());
        }
        if (b2.a1()) {
            return this.k.c(((C0699l2) b2.G0().b).u0());
        }
        if (b2.Z0()) {
            return this.k.c((C0684j2) b2.F0().b);
        }
        if (b2.O0()) {
            return this.k.b(b2.t0().b);
        }
        if (b2.U0()) {
            return this.k.d((Y0) b2.A0().b);
        }
        if (b2.V0()) {
            return this.k.d((Y0) b2.B0().b);
        }
        if (b2.P0()) {
            return "[" + b2.v0() + "]";
        }
        return b2.toString();
    }

    @Override // com.android.tools.r8.graph.AbstractC0709n0
    final void c(PrintStream printStream) {
        printStream.println();
    }
}
