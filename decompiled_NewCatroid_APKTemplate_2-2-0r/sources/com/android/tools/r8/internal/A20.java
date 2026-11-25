package com.android.tools.r8.internal;

import com.android.tools.r8.ir.optimize.EnumC3542a0;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.PrintStream;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class A20 extends AbstractC3477z20 {
    static final /* synthetic */ boolean e = true;
    private final com.android.tools.r8.graph.o5 a;
    private final com.android.tools.r8.graph.o5 b;
    private final PrintStream c;
    private boolean d = false;

    A20(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2, PrintStream printStream) {
        this.a = o5Var;
        this.b = o5Var2;
        this.c = printStream;
    }

    private void a(String str) {
        this.c.print("Method `");
        this.c.print(this.a.w());
        this.c.print("` was not inlined into `");
        this.c.print(this.b.w());
        if (str != null) {
            this.c.print("`: ");
            this.c.println(str);
        } else {
            this.c.println("`.");
        }
        this.d = true;
    }

    @Override // com.android.tools.r8.internal.AbstractC3477z20
    public final void b() {
        a("inlinee can only be inlined into methods in the same class.");
    }

    @Override // com.android.tools.r8.internal.AbstractC3477z20
    public final void c() {
        a("inlinee can only be inlined into methods in the same class (and its nest members).");
    }

    @Override // com.android.tools.r8.internal.AbstractC3477z20
    public final void d() {
        a("inlinee can only be inlined into methods in the same package (declared package private or accesses package private type or member).");
    }

    @Override // com.android.tools.r8.internal.AbstractC3477z20
    public final void e() {
        a("inlinee can only be inlined into methods in the same package and methods in subtypes of the inlinee's enclosing class(declared protected or accesses protected type or member).");
    }

    @Override // com.android.tools.r8.internal.AbstractC3477z20
    public final void f() {
        a("inlinee is not accessible from the caller context.");
    }

    @Override // com.android.tools.r8.internal.AbstractC3477z20
    public final void g() {
        a("inlinee does not have code.");
    }

    @Override // com.android.tools.r8.internal.AbstractC3477z20
    public final void h() {
        a("unsupported instruction in inlinee.");
    }

    @Override // com.android.tools.r8.internal.AbstractC3477z20
    public final void i() {
        a("inlinee not processed yet.");
    }

    @Override // com.android.tools.r8.internal.AbstractC3477z20
    public final void j() {
        a("not inlining due to code size heuristic (inlinee may have multiple callers and is not considered trivial).");
    }

    @Override // com.android.tools.r8.internal.AbstractC3477z20
    public final void k() {
        a("inlining could increase the main dex size (caller is in main dex and inlinee refers to classes not in main dex).");
    }

    @Override // com.android.tools.r8.internal.AbstractC3477z20
    public final void l() {
        a("cannot inline across feature splits.");
    }

    @Override // com.android.tools.r8.internal.AbstractC3477z20
    public final void m() {
        a("caller's instruction budget is exceeded.");
    }

    @Override // com.android.tools.r8.internal.AbstractC3477z20
    public final void n() {
        a("cannot guarantee that the enclosing class of the inlinee is guaranteed to be class initialized before the first side-effecting instruction in the inlinee.");
    }

    @Override // com.android.tools.r8.internal.AbstractC3477z20
    public final void o() {
        a("inlining into constructors not supported when generating class files.");
    }

    @Override // com.android.tools.r8.internal.AbstractC3477z20
    public final void p() {
        a("method is kept by a Proguard configuration rule.");
    }

    @Override // com.android.tools.r8.internal.AbstractC3477z20
    public final void q() {
        a("could lead to nondeterministic output since the inlinee is being optimized concurrently.");
    }

    @Override // com.android.tools.r8.internal.AbstractC3477z20
    public final void r() {
        a("the receiver is always null at the call site.");
    }

    @Override // com.android.tools.r8.internal.AbstractC3477z20
    public final void s() {
        a("the receiver may be null at the call site.");
    }

    @Override // com.android.tools.r8.internal.AbstractC3477z20
    public final void t() {
        a("recursive calls are not inlined.");
    }

    @Override // com.android.tools.r8.internal.AbstractC3477z20
    public final void u() {
        a("could not find a single target.");
    }

    @Override // com.android.tools.r8.internal.AbstractC3477z20
    public final void v() {
        if (!e && !this.d) {
            throw new AssertionError();
        }
        this.d = false;
    }

    @Override // com.android.tools.r8.internal.AbstractC3477z20
    public final void b(int i, int i2) {
        a(i, i2, "could lead to an explosion in the number of moves due to the exceptional control flow", "estimated number of control flow resolution blocks");
    }

    @Override // com.android.tools.r8.internal.AbstractC3477z20
    public final void c(int i, int i2) {
        a(i, i2, "would exceed the caller's instruction budget", "number of instructions in inlinee");
    }

    @Override // com.android.tools.r8.internal.AbstractC3477z20
    public final void d(int i, int i2) {
        a(i, i2, "could negatively impact register allocation due to the number of monitor instructions", "estimated number of locks after inlining");
    }

    private void a(int i, int i2, String str, String str2) {
        a(str + " (" + str2 + ": " + i + ", threshold: " + i2 + ").");
    }

    @Override // com.android.tools.r8.internal.AbstractC3477z20
    public final void a() {
        a("computed API level for caller is unknown");
    }

    @Override // com.android.tools.r8.internal.AbstractC3477z20
    public final void a(int i, int i2) {
        a("number of arguments (" + i + ") does not match arity of method (" + i2 + ").");
    }

    @Override // com.android.tools.r8.internal.AbstractC3477z20
    public final void a(com.android.tools.r8.androidapi.h hVar, com.android.tools.r8.androidapi.h hVar2) {
        boolean z = e;
        if (!z && !hVar.y()) {
            throw new AssertionError();
        }
        if (hVar2.H()) {
            a("computed API level for inlinee is unknown");
        } else {
            if (!z && !hVar2.y()) {
                throw new AssertionError();
            }
            a("computed API level for inlinee (" + hVar2.b0().a() + ") is higher than caller's (" + hVar.b0().a() + Constant.AFTER_QUTO);
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC3477z20
    public final void a(EnumC3542a0 enumC3542a0, Set set) {
        a("not a valid inlining reason (was: " + enumC3542a0 + ", allowed: one of " + com.android.tools.r8.utils.Y2.a(", ", set) + ").");
    }

    @Override // com.android.tools.r8.internal.AbstractC3477z20
    public final void a(C1793co c1793co) {
        a("final field `" + c1793co.getField() + "` must be initialized in a constructor of `" + this.a.t().r0() + "`.");
    }

    @Override // com.android.tools.r8.internal.AbstractC3477z20
    public final void a(C1166Ls c1166Ls) {
        a("must invoke a constructor from the class being instantiated (would invoke `" + c1166Ls.S2().r0() + "`).");
    }

    @Override // com.android.tools.r8.internal.AbstractC3477z20
    public final void a(AbstractC2412ko abstractC2412ko) {
        a("would lead to use of uninitialized object (user: `" + abstractC2412ko.toString() + "`).");
    }
}
