package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.KotlinClassMetadata;
import com.android.tools.r8.utils.C3979v2;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Gz, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1030Gz implements InterfaceC2743oz {
    private final C2137hA a;
    private final String b;
    private final int[] c;

    private C1030Gz(C2137hA c2137hA, String str, int[] iArr) {
        this.a = c2137hA;
        this.b = str;
        this.c = iArr;
    }

    public static C1030Gz a(KotlinClassMetadata.FileFacade fileFacade, String str, int[] iArr, AbstractC0727q0 abstractC0727q0, C0702m c0702m, Consumer consumer) {
        return new C1030Gz(C2137hA.a(fileFacade.b(), abstractC0727q0, c0702m, consumer, C1201Mz.a(fileFacade, c0702m.Y())), str, iArr);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2743oz
    public final boolean c() {
        return true;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2743oz
    public final String m() {
        return this.b;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2743oz
    public final C1030Gz n() {
        return this;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2743oz
    public final int[] t() {
        return this.c;
    }

    public final String u() {
        return this.a.u();
    }

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
    @Override // com.android.tools.r8.internal.InterfaceC2743oz
    public final C3979v2 a(C0702m c0702m, AbstractC0727q0 abstractC0727q0) {
        C3247vy c3247vy = new C3247vy();
        boolean zA = this.a.a(c3247vy, abstractC0727q0, c0702m);
        int[] iArr = KotlinClassMetadata.b;
        AbstractC0936Ds.c(iArr, "metadataVersion");
        com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.s sVar = new com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.s();
        c3247vy.a(sVar);
        return C3979v2.a(sVar.a(iArr).a(), Boolean.valueOf(zA));
    }

    @Override // com.android.tools.r8.shaking.InterfaceC3680a0
    public final void a(com.android.tools.r8.graph.Q0 q0) {
        this.a.a(q0);
    }
}
