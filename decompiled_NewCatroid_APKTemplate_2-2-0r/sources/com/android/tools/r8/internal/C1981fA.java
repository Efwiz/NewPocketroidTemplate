package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.KotlinClassMetadata;
import com.android.tools.r8.utils.C3888e1;
import com.android.tools.r8.utils.C3979v2;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.fA, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1981fA implements InterfaceC2743oz {
    private final List a;
    private final String b;
    private final int[] c;

    private C1981fA(AbstractC1639am abstractC1639am, String str, int[] iArr) {
        this.a = abstractC1639am;
        this.b = str;
        this.c = iArr;
    }

    static C1981fA a(KotlinClassMetadata.a aVar, String str, int[] iArr, C0722p1 c0722p1) {
        C1507Xl c1507XlG = AbstractC1639am.g();
        for (String str2 : aVar.b()) {
            c1507XlG.a(C2973sA.a(str2, c0722p1, str2));
        }
        return new C1981fA(c1507XlG.a(), str, iArr);
    }

    @Override // com.android.tools.r8.internal.InterfaceC2743oz
    public final String m() {
        return this.b;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2743oz
    public final boolean r() {
        return true;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2743oz
    public final int[] t() {
        return this.c;
    }

    @Override // com.android.tools.r8.internal.InterfaceC2743oz
    public final C3979v2 a(C0702m c0702m, AbstractC0727q0 abstractC0727q0) {
        final ArrayList arrayList = new ArrayList(this.a.size());
        Iterator it = this.a.iterator();
        boolean zA = false;
        while (it.hasNext()) {
            zA |= ((C2973sA) it.next()).a(new Consumer() { // from class: com.android.tools.r8.internal.fA$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    C1981fA.a(arrayList, (String) obj);
                }
            }, c0702m, (String) null);
        }
        int[] iArr = KotlinClassMetadata.b;
        AbstractC0936Ds.c(iArr, "metadataVersion");
        com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.r.a(iArr);
        return C3979v2.a(new KotlinClassMetadata.a(com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.i.a(4, iArr, (String[]) arrayList.toArray(new String[0]), null, null, 0, 56)).a(), Boolean.valueOf(zA));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(List list, String str) {
        if (str != null) {
            list.add(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Consumer a(C2973sA c2973sA) {
        Objects.requireNonNull(c2973sA);
        return new Kz$$ExternalSyntheticLambda1(c2973sA);
    }

    @Override // com.android.tools.r8.shaking.InterfaceC3680a0
    public final void a(com.android.tools.r8.graph.Q0 q0) {
        C3888e1.a(this.a, new Function() { // from class: com.android.tools.r8.internal.fA$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C1981fA.a((C2973sA) obj);
            }
        }, (Object) q0);
    }
}
