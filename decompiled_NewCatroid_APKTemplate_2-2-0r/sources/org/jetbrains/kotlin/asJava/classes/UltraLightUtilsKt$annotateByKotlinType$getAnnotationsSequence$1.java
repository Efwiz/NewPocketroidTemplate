package org.jetbrains.kotlin.asJava.classes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequenceScope;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiAnnotation;
import org.jetbrains.kotlin.com.intellij.psi.PsiTypeElement;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeProjection;

/* JADX INFO: compiled from: ultraLightUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlin/sequences/SequenceScope;", "", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiAnnotation;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "org.jetbrains.kotlin.asJava.classes.UltraLightUtilsKt$annotateByKotlinType$getAnnotationsSequence$1", f = "ultraLightUtils.kt", i = {0, 1}, l = {176, 178}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {"$this$sequence", "$this$sequence"}, s = {"L$0", "L$0"})
final class UltraLightUtilsKt$annotateByKotlinType$getAnnotationsSequence$1 extends RestrictedSuspendLambda implements Function2<SequenceScope<? super List<? extends PsiAnnotation>>, Continuation<? super Unit>, Object> {
    final /* synthetic */ PsiTypeElement $psiContext;
    final /* synthetic */ KotlinType $this_getAnnotationsSequence;
    private /* synthetic */ Object L$0;
    Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    UltraLightUtilsKt$annotateByKotlinType$getAnnotationsSequence$1(KotlinType kotlinType, PsiTypeElement psiTypeElement, Continuation<? super UltraLightUtilsKt$annotateByKotlinType$getAnnotationsSequence$1> continuation) {
        super(2, continuation);
        this.$this_getAnnotationsSequence = kotlinType;
        this.$psiContext = psiTypeElement;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        UltraLightUtilsKt$annotateByKotlinType$getAnnotationsSequence$1 ultraLightUtilsKt$annotateByKotlinType$getAnnotationsSequence$1 = new UltraLightUtilsKt$annotateByKotlinType$getAnnotationsSequence$1(this.$this_getAnnotationsSequence, this.$psiContext, continuation);
        ultraLightUtilsKt$annotateByKotlinType$getAnnotationsSequence$1.L$0 = obj;
        return ultraLightUtilsKt$annotateByKotlinType$getAnnotationsSequence$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(SequenceScope<? super List<? extends PsiAnnotation>> sequenceScope, Continuation<? super Unit> continuation) {
        return ((UltraLightUtilsKt$annotateByKotlinType$getAnnotationsSequence$1) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code duplicated, block: B:22:0x0082  */
    /* JADX WARN: Code duplicated, block: B:28:0x00a6 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:30:? A[LOOP:0: B:20:0x007c->B:30:?, LOOP_END, SYNTHETIC] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        SequenceScope sequenceScope;
        SequenceScope sequenceScope2;
        Iterator<TypeProjection> it;
        KotlinType nullableAnyType;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            sequenceScope = (SequenceScope) this.L$0;
            Annotations annotations = this.$this_getAnnotationsSequence.getAnnotations();
            PsiTypeElement psiTypeElement = this.$psiContext;
            ArrayList arrayList = new ArrayList();
            Iterator<AnnotationDescriptor> it2 = annotations.iterator();
            while (it2.hasNext()) {
                KtUltraLightSimpleAnnotation lightAnnotation = UltraLightAnnotationsKt.toLightAnnotation(it2.next(), psiTypeElement);
                if (lightAnnotation != null) {
                    arrayList.add(lightAnnotation);
                }
            }
            this.L$0 = sequenceScope;
            this.label = 1;
            if (sequenceScope.yield(arrayList, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i == 1) {
                sequenceScope = (SequenceScope) this.L$0;
                ResultKt.throwOnFailure(obj);
            } else {
                if (i != 2) {
                    throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
                }
                it = (Iterator) this.L$1;
                sequenceScope2 = (SequenceScope) this.L$0;
                ResultKt.throwOnFailure(obj);
            }
            while (it.hasNext()) {
                nullableAnyType = it.next().getNullableAnyType();
                Intrinsics.checkNotNullExpressionValue(nullableAnyType, "getType(...)");
                this.L$0 = sequenceScope2;
                this.L$1 = it;
                this.label = 2;
                if (sequenceScope2.yieldAll(UltraLightUtilsKt.annotateByKotlinType$getAnnotationsSequence(nullableAnyType, this.$psiContext), this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            }
            return Unit.INSTANCE;
        }
        sequenceScope2 = sequenceScope;
        it = this.$this_getAnnotationsSequence.getArguments().iterator();
        while (it.hasNext()) {
            nullableAnyType = it.next().getNullableAnyType();
            Intrinsics.checkNotNullExpressionValue(nullableAnyType, "getType(...)");
            this.L$0 = sequenceScope2;
            this.L$1 = it;
            this.label = 2;
            if (sequenceScope2.yieldAll(UltraLightUtilsKt.annotateByKotlinType$getAnnotationsSequence(nullableAnyType, this.$psiContext), this) == coroutine_suspended) {
                return coroutine_suspended;
            }
        }
        return Unit.INSTANCE;
    }
}
