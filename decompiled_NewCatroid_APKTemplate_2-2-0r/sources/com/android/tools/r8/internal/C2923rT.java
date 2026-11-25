package com.android.tools.r8.internal;

import com.android.tools.r8.naming.C3634k;
import com.android.tools.r8.references.ClassReference;
import com.android.tools.r8.references.MethodReference;
import com.android.tools.r8.references.Reference;
import com.android.tools.r8.references.TypeReference;
import com.android.tools.r8.retrace.RetraceClassElement;
import com.android.tools.r8.retrace.RetraceFieldResult;
import com.android.tools.r8.retrace.RetraceFrameResult;
import com.android.tools.r8.retrace.RetraceMethodResult;
import com.android.tools.r8.retrace.RetraceResult;
import com.android.tools.r8.retrace.RetraceStackTraceContext;
import com.android.tools.r8.retrace.RetraceUnknownJsonMappingInformationResult;
import com.android.tools.r8.retrace.RetracedClassReference;
import com.android.tools.r8.retrace.RetracedSourceFile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.OptionalInt;
import java.util.function.BiFunction;

/* JADX INFO: renamed from: com.android.tools.r8.internal.rT, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2923rT implements RetraceClassElement {
    private final C2992sT a;
    private final OT b;
    private final C3634k c;

    private C2923rT(C2992sT c2992sT, OT ot, C3634k c3634k) {
        this.a = c2992sT;
        this.b = ot;
        this.c = c3634k;
    }

    public final OT a() {
        return this.b;
    }

    @Override // com.android.tools.r8.retrace.RetraceElement
    public final RetraceResult getParentResult() {
        return this.a;
    }

    @Override // com.android.tools.r8.retrace.RetraceClassElement
    public final RetracedClassReference getRetracedClass() {
        return this.b;
    }

    @Override // com.android.tools.r8.retrace.RetraceClassElement
    public final RetracedSourceFile getSourceFile() {
        OT ot = this.b;
        YT yt = this.a.c;
        boolean z = NT.b;
        ClassReference classReference = ot.getClassReference();
        return new WT(classReference, yt.a(classReference));
    }

    @Override // com.android.tools.r8.retrace.RetraceClassElement
    public final RetraceUnknownJsonMappingInformationResult getUnknownJsonMappingInformation() {
        return LT.a(this.c.c());
    }

    @Override // com.android.tools.r8.retrace.RetraceElement
    public final boolean isCompilerSynthesized() {
        C3634k c3634k = this.a.b;
        if (c3634k == null) {
            return false;
        }
        Iterator<com.android.tools.r8.naming.mappinginformation.e> it = c3634k.c().iterator();
        while (it.hasNext()) {
            if (it.next().k()) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.tools.r8.retrace.RetraceClassElement
    public final RetraceFieldResult lookupField(String str) {
        return a(AbstractC1070Ih.a(this.b.getClassReference(), str));
    }

    @Override // com.android.tools.r8.retrace.RetraceClassElement
    public final RetraceFrameResult lookupFrame(RetraceStackTraceContext retraceStackTraceContext, OptionalInt optionalInt, String str) {
        return this.a.a(GE.a(this.b.getClassReference(), str)).narrowByPosition(retraceStackTraceContext, optionalInt);
    }

    @Override // com.android.tools.r8.retrace.RetraceClassElement
    public final RetraceMethodResult lookupMethod(String str) {
        return a(GE.a(this.b.getClassReference(), str));
    }

    private C3130uT a(C1009Gh c1009Gh) {
        BiFunction biFunction = new BiFunction() { // from class: com.android.tools.r8.internal.rT$$ExternalSyntheticLambda0
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return C2992sT.m1686$$Nest$sma((C3634k) obj, (AbstractC1070Ih) obj2);
            }
        };
        rT$$ExternalSyntheticLambda1 rt__externalsyntheticlambda1 = new rT$$ExternalSyntheticLambda1();
        ArrayList arrayList = new ArrayList();
        C2992sT.b(c1009Gh, arrayList, biFunction, this);
        C2992sT c2992sT = this.a;
        return (C3130uT) rt__externalsyntheticlambda1.a(c2992sT, arrayList, c1009Gh, c2992sT.c);
    }

    private AT a(EE ee) {
        BiFunction biFunction = new BiFunction() { // from class: com.android.tools.r8.internal.rT$$ExternalSyntheticLambda2
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return C2992sT.m1685$$Nest$sma((C3634k) obj, (GE) obj2);
            }
        };
        rT$$ExternalSyntheticLambda3 rt__externalsyntheticlambda3 = new rT$$ExternalSyntheticLambda3();
        ArrayList arrayList = new ArrayList();
        C2992sT.b(ee, arrayList, biFunction, this);
        C2992sT c2992sT = this.a;
        return (AT) rt__externalsyntheticlambda3.a(c2992sT, arrayList, ee, c2992sT.c);
    }

    @Override // com.android.tools.r8.retrace.RetraceClassElement
    public final RetraceFrameResult lookupFrame(RetraceStackTraceContext retraceStackTraceContext, OptionalInt optionalInt, String str, List list, TypeReference typeReference) {
        return this.a.a(GE.a(Reference.method(this.b.getClassReference(), str, list, typeReference))).narrowByPosition(retraceStackTraceContext, optionalInt);
    }

    @Override // com.android.tools.r8.retrace.RetraceClassElement
    public final RetraceFrameResult lookupFrame(RetraceStackTraceContext retraceStackTraceContext, OptionalInt optionalInt, MethodReference methodReference) {
        return this.a.a(GE.a(methodReference)).narrowByPosition(retraceStackTraceContext, optionalInt);
    }
}
