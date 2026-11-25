package com.android.tools.r8.internal;

import com.android.tools.r8.naming.C3634k;
import com.android.tools.r8.references.ClassReference;
import com.android.tools.r8.references.FieldReference;
import com.android.tools.r8.references.MethodReference;
import com.android.tools.r8.references.Reference;
import com.android.tools.r8.references.TypeReference;
import com.android.tools.r8.retrace.RetraceClassResult;
import com.android.tools.r8.retrace.RetraceFieldResult;
import com.android.tools.r8.retrace.RetraceMethodResult;
import com.android.tools.r8.retrace.RetraceStackTraceContext;
import com.android.tools.r8.utils.C3979v2;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.OptionalInt;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/* JADX INFO: renamed from: com.android.tools.r8.internal.sT, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2992sT implements RetraceClassResult {
    private final ClassReference a;
    private final C3634k b;
    private final YT c;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX INFO: renamed from: -$$Nest$sma, reason: not valid java name */
    public static /* bridge */ /* synthetic */ List m1685$$Nest$sma(C3634k c3634k, GE ge) {
        return a(c3634k, ge);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX INFO: renamed from: -$$Nest$sma, reason: not valid java name */
    public static /* bridge */ /* synthetic */ List m1686$$Nest$sma(C3634k c3634k, AbstractC1070Ih abstractC1070Ih) {
        return a(c3634k, abstractC1070Ih);
    }

    private C2992sT(ClassReference classReference, C3634k c3634k, YT yt) {
        this.a = classReference;
        this.b = c3634k;
        this.c = yt;
    }

    static C2992sT a(ClassReference classReference, C3634k c3634k, YT yt) {
        return new C2992sT(classReference, c3634k, yt);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(InterfaceC2249ie interfaceC2249ie, List list, BiFunction biFunction, C2923rT c2923rT) {
        Object objApply;
        C3634k c3634k = c2923rT.c;
        if (c3634k == null || (objApply = biFunction.apply(c3634k, interfaceC2249ie)) == null) {
            list.add(new C3979v2(c2923rT, null));
        } else {
            list.add(new C3979v2(c2923rT, objApply));
        }
    }

    @Override // com.android.tools.r8.retrace.RetraceResult
    public final boolean isEmpty() {
        return this.b != null;
    }

    @Override // com.android.tools.r8.retrace.RetraceClassResult
    public final RetraceFieldResult lookupField(String str) {
        return a(AbstractC1070Ih.a(this.a, str));
    }

    @Override // com.android.tools.r8.retrace.RetraceClassResult
    public final RetraceMethodResult lookupMethod(String str) {
        return a(GE.a(this.a, str));
    }

    @Override // com.android.tools.r8.retrace.RetraceResult
    public final Stream stream() {
        C3634k c3634k = this.b;
        return Stream.of(new C2923rT(this, OT.a(c3634k == null ? this.a : Reference.classFromTypeName(c3634k.a)), this.b));
    }

    final C3130uT a(FieldReference fieldReference) {
        return a(AbstractC1070Ih.a(fieldReference));
    }

    @Override // com.android.tools.r8.retrace.RetraceClassResult
    public final RetraceFieldResult lookupField(String str, TypeReference typeReference) {
        return a(AbstractC1070Ih.a(Reference.field(this.a, str, typeReference)));
    }

    @Override // com.android.tools.r8.retrace.RetraceClassResult
    public final RetraceMethodResult lookupMethod(String str, List list, TypeReference typeReference) {
        return a(GE.a(Reference.method(this.a, str, list, typeReference)));
    }

    private C3130uT a(AbstractC1070Ih abstractC1070Ih) {
        return (C3130uT) a(abstractC1070Ih, new BiFunction() { // from class: com.android.tools.r8.internal.sT$$ExternalSyntheticLambda5
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return C2992sT.a((C3634k) obj, (AbstractC1070Ih) obj2);
            }
        }, new rT$$ExternalSyntheticLambda1());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static List a(C3634k c3634k, AbstractC1070Ih abstractC1070Ih) {
        List list = (List) c3634k.f.get(abstractC1070Ih.getName());
        if (list == null || list.isEmpty()) {
            return null;
        }
        if (!(abstractC1070Ih instanceof C1043Hh)) {
            return list;
        }
        FieldReference fieldReferenceB = abstractC1070Ih.a().b();
        final com.android.tools.r8.naming.Q.a aVar = new com.android.tools.r8.naming.Q.a(fieldReferenceB.getFieldName(), fieldReferenceB.getFieldType().getTypeName());
        return com.android.tools.r8.utils.V1.a((Collection) list, new Predicate() { // from class: com.android.tools.r8.internal.sT$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C2992sT.a(aVar, (com.android.tools.r8.naming.Q) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(com.android.tools.r8.naming.Q.a aVar, com.android.tools.r8.naming.Q q) {
        return q.d().equals(aVar);
    }

    final AT a(MethodReference methodReference) {
        return a(GE.a(methodReference));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public AT a(GE ge) {
        return (AT) a(ge, new BiFunction() { // from class: com.android.tools.r8.internal.sT$$ExternalSyntheticLambda0
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return C2992sT.a((C3634k) obj, (GE) obj2);
            }
        }, new rT$$ExternalSyntheticLambda3());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static List a(final C3634k c3634k, GE ge) {
        C3634k.c cVar = c3634k.e.get(ge.getName());
        if (cVar == null || cVar.b().isEmpty()) {
            return null;
        }
        List listC = cVar.c();
        if (ge instanceof FE) {
            final com.android.tools.r8.naming.Q.b bVarA = com.android.tools.r8.naming.Q.b.a(ge.a().b());
            listC = com.android.tools.r8.utils.V1.a((Collection) listC, new Predicate() { // from class: com.android.tools.r8.internal.sT$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return C2992sT.a(bVarA, (C3634k.c) obj);
                }
            });
        }
        return com.android.tools.r8.utils.V1.a((Collection) listC, new Function() { // from class: com.android.tools.r8.internal.sT$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C2992sT.a(c3634k, (C3634k.c) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(com.android.tools.r8.naming.Q.b bVar, C3634k.c cVar) {
        return ((C3634k.b) com.android.tools.r8.utils.V1.b(cVar.b())).d().equals(bVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C1676bE a(C3634k c3634k, C3634k.c cVar) {
        return new C1676bE(cVar.a(c3634k), cVar);
    }

    private Object a(final InterfaceC2249ie interfaceC2249ie, final BiFunction biFunction, InterfaceC2851qT interfaceC2851qT) {
        ClassReference classReferenceClassFromTypeName;
        final ArrayList arrayList = new ArrayList();
        C3634k c3634k = this.b;
        if (c3634k == null) {
            classReferenceClassFromTypeName = this.a;
        } else {
            classReferenceClassFromTypeName = Reference.classFromTypeName(c3634k.a);
        }
        Stream.of(new C2923rT(this, OT.a(classReferenceClassFromTypeName), this.b)).forEach(new Consumer() { // from class: com.android.tools.r8.internal.sT$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2992sT.b(interfaceC2249ie, arrayList, biFunction, (C2923rT) obj);
            }
        });
        return interfaceC2851qT.a(this, arrayList, interfaceC2249ie, this.c);
    }

    @Override // com.android.tools.r8.retrace.RetraceClassResult
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public final C3437yT lookupFrame(RetraceStackTraceContext retraceStackTraceContext, OptionalInt optionalInt, String str) {
        return a(GE.a(this.a, str)).narrowByPosition(retraceStackTraceContext, optionalInt);
    }

    @Override // com.android.tools.r8.retrace.RetraceClassResult
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public final C3437yT lookupFrame(RetraceStackTraceContext retraceStackTraceContext, OptionalInt optionalInt, String str, List list, TypeReference typeReference) {
        return a(GE.a(Reference.method(this.a, str, list, typeReference))).narrowByPosition(retraceStackTraceContext, optionalInt);
    }

    @Override // com.android.tools.r8.retrace.RetraceClassResult
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public final IT lookupThrownException(RetraceStackTraceContext retraceStackTraceContext) {
        return new IT(this.a, this.b);
    }
}
