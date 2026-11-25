package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.KotlinClassMetadata;
import com.android.tools.r8.utils.C3979v2;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.nA, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2593nA implements InterfaceC2743oz {
    static final /* synthetic */ boolean e = true;
    private final C1228Nz a;
    private final String b;
    private final int[] c;
    private final int d;

    private C2593nA(C1228Nz c1228Nz, int i, String str, int[] iArr) {
        this.a = c1228Nz;
        this.d = i;
        this.b = str;
        this.c = iArr;
    }

    static C2593nA a(KotlinClassMetadata.SyntheticClass syntheticClass, String str, int[] iArr, AbstractC0727q0 abstractC0727q0, C2037fz c2037fz, C0702m c0702m) {
        C3095ty c3095tyC = syntheticClass.c();
        if (e || c3095tyC == null || syntheticClass.b()) {
            return new C2593nA(c3095tyC != null ? C1228Nz.a(abstractC0727q0, c3095tyC, c0702m.a(), c0702m.Y().e, C1201Mz.a(syntheticClass, c0702m.Y())) : null, a(abstractC0727q0, c2037fz), str, iArr);
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.InterfaceC2743oz
    public final C2593nA g() {
        return this;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2743oz
    public final String m() {
        return this.b;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2743oz
    public final boolean p() {
        return true;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2743oz
    public final int[] t() {
        return this.c;
    }

    public final boolean u() {
        return (this.a == null || this.d == 3) ? false : true;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2743oz
    public final C3979v2 a(C0702m c0702m, AbstractC0727q0 abstractC0727q0) {
        if (this.a == null) {
            int[] iArr = KotlinClassMetadata.b;
            AbstractC0936Ds.c(iArr, "metadataVersion");
            return C3979v2.a(new com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.w().a(iArr).a(), Boolean.FALSE);
        }
        final com.android.tools.r8.utils.N n = new com.android.tools.r8.utils.N();
        boolean zA = this.a.a(c0702m, abstractC0727q0, new Consumer() { // from class: com.android.tools.r8.internal.nA$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                n.a((C3095ty) obj);
            }
        });
        if (!e && !n.c()) {
            throw new AssertionError();
        }
        int[] iArr2 = KotlinClassMetadata.b;
        C3095ty c3095ty = (C3095ty) n.a();
        int[] iArr3 = KotlinClassMetadata.b;
        AbstractC0936Ds.c(c3095ty, "kmLambda");
        AbstractC0936Ds.c(iArr3, "metadataVersion");
        com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.w wVar = new com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.w();
        C2815py c2815py = c3095ty.b;
        if (c2815py != null) {
            int iF = c2815py.f();
            C2815py c2815py2 = c3095ty.b;
            if (c2815py2 != null) {
                AbstractC3025sy abstractC3025syA = wVar.a(iF, c2815py2.g());
                C2815py c2815py3 = c3095ty.b;
                if (c2815py3 != null) {
                    c2815py3.a(abstractC3025syA);
                    wVar.a();
                    return C3979v2.a(wVar.a(iArr3).a(), Boolean.valueOf(zA));
                }
                AbstractC0936Ds.a("function");
                throw null;
            }
            AbstractC0936Ds.a("function");
            throw null;
        }
        AbstractC0936Ds.a("function");
        throw null;
    }

    @Override // com.android.tools.r8.shaking.InterfaceC3680a0
    public final void a(com.android.tools.r8.graph.Q0 q0) {
        C1228Nz c1228Nz = this.a;
        if (c1228Nz != null) {
            c1228Nz.a(q0);
        }
    }

    public static int a(AbstractC0727q0 abstractC0727q0, C2037fz c2037fz) {
        C0767v2 c0767v2 = abstractC0727q0.f;
        if (c0767v2 == c2037fz.b.a) {
            return 1;
        }
        return (c0767v2 == c2037fz.a.Z1 && abstractC0727q0.g.size() == 1) ? 2 : 3;
    }
}
