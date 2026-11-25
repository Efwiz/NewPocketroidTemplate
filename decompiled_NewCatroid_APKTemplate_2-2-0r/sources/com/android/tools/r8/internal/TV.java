package com.android.tools.r8.internal;

import com.android.tools.r8.references.Reference;
import com.android.tools.r8.retrace.RetraceClassElement;
import com.android.tools.r8.retrace.RetraceClassResult;
import com.android.tools.r8.retrace.RetraceFieldElement;
import com.android.tools.r8.retrace.RetraceFieldResult;
import com.android.tools.r8.retrace.RetraceFrameElement;
import com.android.tools.r8.retrace.RetraceFrameResult;
import com.android.tools.r8.retrace.RetraceStackTraceContext;
import com.android.tools.r8.retrace.RetraceStackTraceElementProxyResult;
import com.android.tools.r8.retrace.RetraceThrownExceptionElement;
import com.android.tools.r8.retrace.RetraceTypeElement;
import com.android.tools.r8.retrace.RetraceTypeResult;
import com.android.tools.r8.retrace.RetracedClassReference;
import com.android.tools.r8.retrace.RetracedMethodReference;
import com.android.tools.r8.retrace.RetracedSingleFrame;
import com.android.tools.r8.retrace.RetracedSourceFile;
import com.android.tools.r8.retrace.RetracedTypeReference;
import com.android.tools.r8.retrace.Retracer;
import com.android.tools.r8.retrace.StackTraceElementProxy;
import com.android.tools.r8.retrace.StackTraceElementProxyRetracer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class TV implements StackTraceElementProxyRetracer {
    private final Retracer a;

    public TV(Retracer retracer) {
        this.a = retracer;
    }

    private GT a(GT gt, final RetraceClassResult retraceClassResult) {
        FT ftA = gt.a();
        ftA.a = gt.stream().flatMap(new Function() { // from class: com.android.tools.r8.internal.TV$$ExternalSyntheticLambda10
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(retraceClassResult, (SV) obj);
            }
        });
        return ftA.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ SV b(RetraceClassResult retraceClassResult, SV sv, RetraceThrownExceptionElement retraceThrownExceptionElement) {
        return a(retraceClassResult, sv, retraceThrownExceptionElement);
    }

    @Override // com.android.tools.r8.retrace.StackTraceElementProxyRetracer
    public final RetraceStackTraceElementProxyResult retrace(StackTraceElementProxy stackTraceElementProxy, RetraceStackTraceContext retraceStackTraceContext) {
        FT ftB = FT.b();
        ftB.a = Stream.of(new SV(stackTraceElementProxy, null, null, null, null, null, null, -1, false, false, retraceStackTraceContext));
        ftB.b = new Supplier() { // from class: com.android.tools.r8.internal.TV$$ExternalSyntheticLambda13
            @Override // java.util.function.Supplier
            public final Object get() {
                return RetraceStackTraceContext.empty();
            }
        };
        GT gtA = ftB.a();
        if (!stackTraceElementProxy.hasClassName() && !stackTraceElementProxy.hasFieldOrReturnType() && !stackTraceElementProxy.hasMethodArguments()) {
            return gtA;
        }
        GT gtB = b(a(gtA, stackTraceElementProxy), stackTraceElementProxy);
        if (!stackTraceElementProxy.hasClassName()) {
            return gtB;
        }
        RetraceClassResult retraceClassResultRetraceClass = this.a.retraceClass(stackTraceElementProxy.getClassReference());
        if (stackTraceElementProxy.hasMethodName()) {
            return a(gtB, stackTraceElementProxy, retraceClassResultRetraceClass, retraceStackTraceContext);
        }
        return stackTraceElementProxy.hasFieldName() ? a(gtB, stackTraceElementProxy, retraceClassResultRetraceClass) : a(gtB, retraceClassResultRetraceClass);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ SV b(StackTraceElementProxy stackTraceElementProxy, RetraceClassResult retraceClassResult, SV sv, RetraceFrameResult retraceFrameResult, RetraceFrameElement retraceFrameElement, RetracedSingleFrame retracedSingleFrame) {
        return a(stackTraceElementProxy, retraceClassResult, sv, retraceFrameResult, retraceFrameElement, retracedSingleFrame);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ SV b(RetraceClassResult retraceClassResult, SV sv, RetraceFieldResult retraceFieldResult, RetraceFieldElement retraceFieldElement) {
        return a(retraceClassResult, sv, retraceFieldResult, retraceFieldElement);
    }

    private GT b(GT gt, StackTraceElementProxy stackTraceElementProxy) {
        if (!stackTraceElementProxy.hasMethodArguments()) {
            return gt;
        }
        List list = (List) Arrays.stream(stackTraceElementProxy.getMethodArguments().split(",")).map(new Function() { // from class: com.android.tools.r8.internal.TV$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a((String) obj);
            }
        }).collect(Collectors.toList());
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ArrayList());
        final List list2 = (List) com.android.tools.r8.utils.V1.a(list, arrayList, new BiFunction() { // from class: com.android.tools.r8.internal.TV$$ExternalSyntheticLambda4
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return TV.a((List) obj, (RetraceTypeResult) obj2);
            }
        });
        final boolean z = list2.size() > 1;
        FT ftA = gt.a();
        ftA.a = gt.stream().flatMap(new Function() { // from class: com.android.tools.r8.internal.TV$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return TV.a(list2, z, (SV) obj);
            }
        });
        return ftA.a();
    }

    private static SV a(RetraceClassResult retraceClassResult, SV sv, final RetraceThrownExceptionElement retraceThrownExceptionElement) {
        RV rvA = sv.a();
        rvA.b = retraceThrownExceptionElement.getRetracedClass();
        RV rvA2 = rvA.a(retraceClassResult.isAmbiguous());
        rvA2.j = true;
        rvA2.k = retraceThrownExceptionElement.getContext();
        return rvA2.a(a(new Supplier() { // from class: com.android.tools.r8.internal.TV$$ExternalSyntheticLambda11
            @Override // java.util.function.Supplier
            public final Object get() {
                return retraceThrownExceptionElement.getSourceFile();
            }
        }, retraceThrownExceptionElement.getRetracedClass(), retraceClassResult)).a();
    }

    private GT a(GT gt, final StackTraceElementProxy stackTraceElementProxy, final RetraceClassResult retraceClassResult, final RetraceStackTraceContext retraceStackTraceContext) {
        final com.android.tools.r8.utils.N n = new com.android.tools.r8.utils.N(RetraceStackTraceContext.empty());
        FT ftA = gt.a();
        ftA.b = new Supplier() { // from class: com.android.tools.r8.internal.TV$$ExternalSyntheticLambda25
            @Override // java.util.function.Supplier
            public final Object get() {
                return (RetraceStackTraceContext) n.a();
            }
        };
        ftA.a = gt.stream().flatMap(new Function() { // from class: com.android.tools.r8.internal.TV$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(retraceClassResult, retraceStackTraceContext, stackTraceElementProxy, n, (SV) obj);
            }
        });
        return ftA.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Stream a(final RetraceClassResult retraceClassResult, final RetraceStackTraceContext retraceStackTraceContext, final StackTraceElementProxy stackTraceElementProxy, final com.android.tools.r8.utils.N n, final SV sv) {
        OptionalInt optionalIntEmpty;
        if (stackTraceElementProxy.hasLineNumber()) {
            optionalIntEmpty = OptionalInt.of(stackTraceElementProxy.getLineNumber());
        } else {
            optionalIntEmpty = OptionalInt.empty();
        }
        final RetraceFrameResult retraceFrameResultLookupFrame = retraceClassResult.lookupFrame(retraceStackTraceContext, optionalIntEmpty, stackTraceElementProxy.getMethodName());
        if (retraceFrameResultLookupFrame.isEmpty()) {
            return retraceClassResult.stream().map(new Function() { // from class: com.android.tools.r8.internal.TV$$ExternalSyntheticLambda15
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.a(sv, retraceClassResult, stackTraceElementProxy, (RetraceClassElement) obj);
                }
            });
        }
        return retraceFrameResultLookupFrame.stream().flatMap(new Function() { // from class: com.android.tools.r8.internal.TV$$ExternalSyntheticLambda16
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(n, retraceStackTraceContext, stackTraceElementProxy, retraceClassResult, sv, retraceFrameResultLookupFrame, (RetraceFrameElement) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public SV a(SV sv, RetraceClassResult retraceClassResult, final StackTraceElementProxy stackTraceElementProxy, final RetraceClassElement retraceClassElement) {
        RV rvA = sv.a();
        rvA.j = true;
        RV rvA2 = rvA.a(retraceClassResult.isAmbiguous());
        rvA2.b = retraceClassElement.getRetracedClass();
        return rvA2.a(stackTraceElementProxy.hasLineNumber(), new Consumer() { // from class: com.android.tools.r8.internal.TV$$ExternalSyntheticLambda23
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                TV.a(stackTraceElementProxy, (RV) obj);
            }
        }).a(a(new Supplier() { // from class: com.android.tools.r8.internal.TV$$ExternalSyntheticLambda24
            @Override // java.util.function.Supplier
            public final Object get() {
                return retraceClassElement.getSourceFile();
            }
        }, retraceClassElement.getRetracedClass(), retraceClassResult)).a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(StackTraceElementProxy stackTraceElementProxy, RV rv) {
        rv.h = stackTraceElementProxy.getLineNumber();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Stream a(com.android.tools.r8.utils.N n, RetraceStackTraceContext retraceStackTraceContext, final StackTraceElementProxy stackTraceElementProxy, final RetraceClassResult retraceClassResult, final SV sv, final RetraceFrameResult retraceFrameResult, final RetraceFrameElement retraceFrameElement) {
        n.a(retraceFrameElement.getRetraceStackTraceContext());
        return retraceFrameElement.streamRewritten(retraceStackTraceContext).map(new Function() { // from class: com.android.tools.r8.internal.TV$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.b(stackTraceElementProxy, retraceClassResult, sv, retraceFrameResult, retraceFrameElement, (RetracedSingleFrame) obj);
            }
        });
    }

    private static SV a(final StackTraceElementProxy stackTraceElementProxy, RetraceClassResult retraceClassResult, SV sv, RetraceFrameResult retraceFrameResult, final RetraceFrameElement retraceFrameElement, RetracedSingleFrame retracedSingleFrame) {
        boolean z = retracedSingleFrame.getIndex() == 0;
        final RetracedMethodReference methodReference = retracedSingleFrame.getMethodReference();
        RV rvA = sv.a();
        rvA.b = methodReference.getHolderClass();
        rvA.c = methodReference;
        RV rvA2 = rvA.a(retraceFrameResult.isAmbiguous());
        rvA2.j = z;
        rvA2.k = retraceFrameElement.getRetraceStackTraceContext();
        return rvA2.a(stackTraceElementProxy.hasLineNumber(), new Consumer() { // from class: com.android.tools.r8.internal.TV$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                TV.a(methodReference, stackTraceElementProxy, (RV) obj);
            }
        }).a(a(new Supplier() { // from class: com.android.tools.r8.internal.TV$$ExternalSyntheticLambda7
            @Override // java.util.function.Supplier
            public final Object get() {
                return retraceFrameElement.getSourceFile(methodReference);
            }
        }, methodReference.getHolderClass(), retraceClassResult)).a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(RetracedMethodReference retracedMethodReference, StackTraceElementProxy stackTraceElementProxy, RV rv) {
        rv.h = retracedMethodReference.getOriginalPositionOrDefault(stackTraceElementProxy.getLineNumber());
    }

    private GT a(GT gt, final StackTraceElementProxy stackTraceElementProxy, final RetraceClassResult retraceClassResult) {
        FT ftA = gt.a();
        ftA.a = gt.stream().flatMap(new Function() { // from class: com.android.tools.r8.internal.TV$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(retraceClassResult, stackTraceElementProxy, (SV) obj);
            }
        });
        return ftA.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Stream a(final RetraceClassResult retraceClassResult, StackTraceElementProxy stackTraceElementProxy, final SV sv) {
        final RetraceFieldResult retraceFieldResultLookupField = retraceClassResult.lookupField(stackTraceElementProxy.getFieldName());
        return retraceFieldResultLookupField.stream().map(new Function() { // from class: com.android.tools.r8.internal.TV$$ExternalSyntheticLambda12
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.b(retraceClassResult, sv, retraceFieldResultLookupField, (RetraceFieldElement) obj);
            }
        });
    }

    private static SV a(RetraceClassResult retraceClassResult, SV sv, RetraceFieldResult retraceFieldResult, final RetraceFieldElement retraceFieldElement) {
        RV rvA = sv.a();
        rvA.b = retraceFieldElement.getField().getHolderClass();
        rvA.d = retraceFieldElement.getField();
        RV rvA2 = rvA.a(retraceFieldResult.isAmbiguous());
        rvA2.j = true;
        return rvA2.a(a(new Supplier() { // from class: com.android.tools.r8.internal.TV$$ExternalSyntheticLambda21
            @Override // java.util.function.Supplier
            public final Object get() {
                return retraceFieldElement.getSourceFile();
            }
        }, retraceFieldElement.getField().getHolderClass(), retraceClassResult)).a();
    }

    private static Consumer a(final Supplier supplier, final RetracedClassReference retracedClassReference, final RetraceClassResult retraceClassResult) {
        return new Consumer() { // from class: com.android.tools.r8.internal.TV$$ExternalSyntheticLambda18
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                TV.a(supplier, retracedClassReference, retraceClassResult, (RV) obj);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(Supplier supplier, RetracedClassReference retracedClassReference, RetraceClassResult retraceClassResult, RV rv) {
        String strA;
        StackTraceElementProxy stackTraceElementProxy = rv.a;
        if (stackTraceElementProxy.hasSourceFile()) {
            RetracedSourceFile retracedSourceFile = (RetracedSourceFile) supplier.get();
            if (retracedSourceFile.hasRetraceResult()) {
                strA = retracedSourceFile.getSourceFile();
            } else {
                strA = NT.a(retracedClassReference.getTypeName(), stackTraceElementProxy.getSourceFile(), retraceClassResult.isEmpty());
            }
            rv.g = strA;
        }
    }

    private GT a(GT gt, StackTraceElementProxy stackTraceElementProxy) {
        if (!stackTraceElementProxy.hasFieldOrReturnType()) {
            return gt;
        }
        FT ftA = gt.a();
        String fieldOrReturnType = stackTraceElementProxy.getFieldOrReturnType();
        if (fieldOrReturnType.equals(PsiKeyword.VOID)) {
            ftA.a = gt.stream().map(new Function() { // from class: com.android.tools.r8.internal.TV$$ExternalSyntheticLambda19
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.a((SV) obj);
                }
            });
            return ftA.a();
        }
        final RetraceTypeResult retraceTypeResultRetraceType = this.a.retraceType(Reference.typeFromTypeName(fieldOrReturnType));
        final List list = (List) retraceTypeResultRetraceType.stream().collect(Collectors.toList());
        ftA.a = gt.stream().flatMap(new Function() { // from class: com.android.tools.r8.internal.TV$$ExternalSyntheticLambda20
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(list, retraceTypeResultRetraceType, (SV) obj);
            }
        });
        return ftA.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public SV a(SV sv) {
        XT xtA = XT.a();
        boolean z = sv.i;
        RV rvA = sv.a();
        rvA.e = xtA;
        return rvA.a(z).a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Stream a(List list, final RetraceTypeResult retraceTypeResult, final SV sv) {
        return list.stream().map(new Function() { // from class: com.android.tools.r8.internal.TV$$ExternalSyntheticLambda9
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(sv, retraceTypeResult, (RetraceTypeElement) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public SV a(SV sv, RetraceTypeResult retraceTypeResult, RetraceTypeElement retraceTypeElement) {
        RetracedTypeReference type = retraceTypeElement.getType();
        boolean zIsAmbiguous = retraceTypeResult.isAmbiguous();
        RV rvA = sv.a();
        rvA.e = type;
        return rvA.a(zIsAmbiguous).a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ RetraceTypeResult a(String str) {
        return this.a.retraceType(Reference.typeFromTypeName(str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ List a(final List list, RetraceTypeResult retraceTypeResult) {
        final ArrayList arrayList = new ArrayList();
        retraceTypeResult.forEach(new Consumer() { // from class: com.android.tools.r8.internal.TV$$ExternalSyntheticLambda17
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                TV.a(list, arrayList, (RetraceTypeElement) obj);
            }
        });
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(List list, final List list2, final RetraceTypeElement retraceTypeElement) {
        list.forEach(new Consumer() { // from class: com.android.tools.r8.internal.TV$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                TV.a(retraceTypeElement, list2, (List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(RetraceTypeElement retraceTypeElement, List list, List list2) {
        ArrayList arrayList = new ArrayList(list2);
        arrayList.add(retraceTypeElement.getType());
        list.add(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Stream a(List list, final boolean z, final SV sv) {
        return list.stream().map(new Function() { // from class: com.android.tools.r8.internal.TV$$ExternalSyntheticLambda14
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return TV.a(sv, z, (List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static SV a(SV sv, boolean z, List list) {
        RV rvA = sv.a();
        rvA.f = list;
        return rvA.a(z).a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Stream a(final RetraceClassResult retraceClassResult, final SV sv) {
        return retraceClassResult.lookupThrownException(sv.k).stream().map(new Function() { // from class: com.android.tools.r8.internal.TV$$ExternalSyntheticLambda22
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.b(retraceClassResult, sv, (RetraceThrownExceptionElement) obj);
            }
        });
    }
}
