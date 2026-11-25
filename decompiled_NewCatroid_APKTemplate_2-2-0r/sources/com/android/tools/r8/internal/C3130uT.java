package com.android.tools.r8.internal;

import com.android.tools.r8.references.ClassReference;
import com.android.tools.r8.references.Reference;
import com.android.tools.r8.retrace.RetraceFieldResult;
import com.android.tools.r8.retrace.Retracer;
import com.android.tools.r8.utils.C3970u0;
import com.android.tools.r8.utils.C3979v2;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

/* JADX INFO: renamed from: com.android.tools.r8.internal.uT, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3130uT implements RetraceFieldResult {
    static final /* synthetic */ boolean d = true;
    private final List a;
    private final AbstractC1070Ih b;
    private final Retracer c;

    C3130uT(C2992sT c2992sT, List list, AbstractC1070Ih abstractC1070Ih, Retracer retracer) {
        this.a = list;
        this.b = abstractC1070Ih;
        this.c = retracer;
        boolean z = d;
        if (!z && c2992sT == null) {
            throw new AssertionError();
        }
        if (!z && list.isEmpty()) {
            throw new AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Stream a(C3979v2 c3979v2) {
        final C2923rT c2923rT = (C2923rT) c3979v2.a();
        List list = (List) c3979v2.b();
        return list == null ? Stream.of(new C3061tT(this, c2923rT, RT.a(this.b.a(c2923rT.a().getClassReference())), null)) : list.stream().map(new Function() { // from class: com.android.tools.r8.internal.uT$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(c2923rT, (com.android.tools.r8.naming.Q) obj);
            }
        });
    }

    @Override // com.android.tools.r8.retrace.RetraceResult
    public final boolean isAmbiguous() {
        if (this.a.size() > 1) {
            return true;
        }
        List list = (List) ((C3979v2) this.a.get(0)).b();
        return list != null && list.size() > 1;
    }

    @Override // com.android.tools.r8.retrace.RetraceResult
    public final boolean isEmpty() {
        List list = this.a;
        return list == null || list.isEmpty();
    }

    @Override // com.android.tools.r8.retrace.RetraceResult
    public final Stream stream() {
        return this.a.stream().flatMap(new Function() { // from class: com.android.tools.r8.internal.uT$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a((C3979v2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ C3061tT a(C2923rT c2923rT, com.android.tools.r8.naming.Q q) {
        OT otA;
        String strH;
        com.android.tools.r8.naming.Q.a aVarA = q.b().a();
        if (aVarA.e()) {
            otA = OT.a(Reference.classFromDescriptor(C3970u0.H(aVarA.g())));
        } else {
            otA = c2923rT.a();
        }
        ClassReference classReference = otA.getClassReference();
        if (aVarA.e()) {
            strH = aVarA.h();
        } else {
            strH = aVarA.a;
        }
        return new C3061tT(this, c2923rT, RT.a(Reference.field(classReference, strH, Reference.typeFromTypeName(aVarA.c))), q);
    }
}
