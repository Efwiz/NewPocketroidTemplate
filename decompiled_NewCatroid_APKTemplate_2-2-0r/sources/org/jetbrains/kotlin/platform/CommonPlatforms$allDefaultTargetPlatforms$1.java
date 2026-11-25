package org.jetbrains.kotlin.platform;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.SequenceScope;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: CommonPlatforms.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u008a@"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "Lkotlin/sequences/SequenceScope;", "Lorg/jetbrains/kotlin/platform/TargetPlatform;"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "org.jetbrains.kotlin.platform.CommonPlatforms$allDefaultTargetPlatforms$1", f = "CommonPlatforms.kt", i = {0}, l = {51, 52}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {"$this$sequence"}, s = {"L$0"})
final class CommonPlatforms$allDefaultTargetPlatforms$1 extends RestrictedSuspendLambda implements Function2<SequenceScope<? super TargetPlatform>, Continuation<? super Unit>, Object> {
    private /* synthetic */ Object L$0;
    int label;

    CommonPlatforms$allDefaultTargetPlatforms$1(Continuation<? super CommonPlatforms$allDefaultTargetPlatforms$1> continuation) {
        super(2, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        CommonPlatforms$allDefaultTargetPlatforms$1 commonPlatforms$allDefaultTargetPlatforms$1 = new CommonPlatforms$allDefaultTargetPlatforms$1(continuation);
        commonPlatforms$allDefaultTargetPlatforms$1.L$0 = obj;
        return commonPlatforms$allDefaultTargetPlatforms$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(SequenceScope<? super TargetPlatform> sequenceScope, Continuation<? super Unit> continuation) {
        return ((CommonPlatforms$allDefaultTargetPlatforms$1) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        SequenceScope sequenceScope;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            sequenceScope = (SequenceScope) this.L$0;
            this.L$0 = sequenceScope;
            this.label = 1;
            if (sequenceScope.yieldAll(CommonPlatforms.INSTANCE.getAllSimplePlatforms(), this) == coroutine_suspended) {
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
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
        this.L$0 = null;
        this.label = 2;
        if (sequenceScope.yieldAll(CollectionsKt.listOf(CommonPlatforms.INSTANCE.getDefaultCommonPlatform()), this) == coroutine_suspended) {
            return coroutine_suspended;
        }
        return Unit.INSTANCE;
    }
}
