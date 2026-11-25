package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.KotlinClassMetadata;
import com.android.tools.r8.utils.C3979v2;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.gA, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2053gA implements InterfaceC2743oz {
    private final String a;
    private final C2137hA b;
    private final String c;
    private final int[] d;

    private C2053gA(String str, C2137hA c2137hA, String str2, int[] iArr) {
        this.a = str;
        this.b = c2137hA;
        this.c = str2;
        this.d = iArr;
    }

    static C2053gA a(KotlinClassMetadata.MultiFileClassPart multiFileClassPart, String str, int[] iArr, AbstractC0727q0 abstractC0727q0, C0702m c0702m, Consumer consumer) {
        return new C2053gA(multiFileClassPart.a().xs(), C2137hA.a(multiFileClassPart.b(), abstractC0727q0, c0702m, consumer, C1201Mz.a(multiFileClassPart, c0702m.Y())), str, iArr);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2743oz
    public final String m() {
        return this.c;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2743oz
    public final C2053gA q() {
        return this;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2743oz
    public final boolean s() {
        return true;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2743oz
    public final int[] t() {
        return this.d;
    }

    public final String u() {
        return this.a;
    }

    public final String v() {
        return this.b.u();
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
        boolean zA = this.b.a(c3247vy, abstractC0727q0, c0702m);
        int[] iArr = KotlinClassMetadata.b;
        String str = this.a;
        int[] iArr2 = KotlinClassMetadata.b;
        AbstractC0936Ds.c(str, "facadeClassName");
        AbstractC0936Ds.c(iArr2, "metadataVersion");
        com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.u uVar = new com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.u();
        c3247vy.a(uVar);
        return C3979v2.a(uVar.a(str, iArr2).a(), Boolean.valueOf(zA));
    }

    @Override // com.android.tools.r8.shaking.InterfaceC3680a0
    public final void a(com.android.tools.r8.graph.Q0 q0) {
        this.b.a(q0);
    }
}
