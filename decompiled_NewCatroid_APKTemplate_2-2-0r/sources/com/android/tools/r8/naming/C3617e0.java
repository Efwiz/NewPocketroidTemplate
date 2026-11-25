package com.android.tools.r8.naming;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.C1367Tb;
import com.android.tools.r8.shaking.C3733i;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.naming.e0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
class C3617e0 extends AbstractC3613c0 implements InterfaceC3612c {
    final C0702m e;
    final C0722p1 f;

    C3617e0(C0702m c0702m) {
        super(c0702m.Y().D0().e(), c0702m.Y().D0().y());
        this.e = c0702m;
        this.f = c0702m.a();
    }

    @Override // com.android.tools.r8.naming.InterfaceC3612c
    public boolean a(C0767v2 c0767v2) {
        return false;
    }

    @Override // com.android.tools.r8.naming.InterfaceC3612c
    public C0759u2 b(C0767v2 c0767v2) {
        if (((C3733i) this.e.f()).k(c0767v2)) {
            return null;
        }
        return c0767v2.e;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.lang.Object, java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r2v3, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r5v5, types: [com.android.tools.r8.graph.p1] */
    /* JADX WARN: Type inference failed for: r7v1, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @Override // com.android.tools.r8.naming.InterfaceC3612c
    public C0759u2 a(C0767v2 c0767v2, char[] cArr, I i, Predicate predicate) {
        ?? r1;
        ?? r0 = 0;
        ?? r2 = 0;
        while (true) {
            ?? r3 = a(cArr, i) + ";";
            if (r3.equals(r0)) {
                throw new C1367Tb("Generating same name '" + r3 + "' when given a new minified name to '" + c0767v2.toString() + "'.");
            }
            if (!r3.endsWith("LR;") && !r3.endsWith("/R;")) {
                r1 = r2;
                r1 = r2;
                r1 = r3;
            }
            if (r1 != 0 && !predicate.test(r1)) {
                return this.f.c(r1);
            }
            r0 = r3;
            r2 = r1;
        }
    }
}
