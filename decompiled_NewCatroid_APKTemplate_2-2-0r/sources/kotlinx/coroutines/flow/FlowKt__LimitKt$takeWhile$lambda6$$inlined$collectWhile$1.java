package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.InlineMarker;
import kotlinx.coroutines.flow.internal.AbortFlowException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;

/* JADX INFO: Add missing generic type declarations: [T] */
/* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__LimitKt$takeWhile$lambda-6$$inlined$collectWhile$1, reason: invalid class name */
/* JADX INFO: compiled from: Limit.kt */
/* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/kotlinx-coroutines-core-jvm-1.6.4.jar:kotlinx/coroutines/flow/FlowKt__LimitKt$takeWhile$lambda-6$$inlined$collectWhile$1.class */
@Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000\u0013\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u0019\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00028\u0000H\u0096@ø\u0001\u0000¢\u0006\u0002\u0010\u0005\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0006¸\u0006\u0000"}, d2 = {"kotlinx/coroutines/flow/FlowKt__LimitKt$collectWhile$collector$1", "Lkotlinx/coroutines/flow/FlowCollector;", "emit", "", "value", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"})
public final class FlowKt__LimitKt$takeWhile$lambda6$$inlined$collectWhile$1<T> implements FlowCollector<T> {
    final /* synthetic */ Function2 $predicate$inlined;
    final /* synthetic */ FlowCollector $this_unsafeFlow$inlined;

    /* JADX INFO: renamed from: kotlinx.coroutines.flow.FlowKt__LimitKt$takeWhile$lambda-6$$inlined$collectWhile$1$1, reason: invalid class name */
    /* JADX INFO: compiled from: Limit.kt */
    /* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/kotlinx-coroutines-core-jvm-1.6.4.jar:kotlinx/coroutines/flow/FlowKt__LimitKt$takeWhile$lambda-6$$inlined$collectWhile$1$1.class */
    @Metadata(mv = {1, 6, 0}, k = 3, xi = 48)
    @DebugMetadata(f = "Limit.kt", l = {142, 143}, i = {0, 0, 1}, s = {"L$0", "L$1", "L$0"}, n = {"this", "value", "this"}, m = "emit", c = "kotlinx.coroutines.flow.FlowKt__LimitKt$takeWhile$lambda-6$$inlined$collectWhile$1")
    public static final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        /* synthetic */ Object result;
        int label;
        Object L$1;

        public AnonymousClass1(Continuation $completion) {
            super($completion);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object $result) {
            this.result = $result;
            this.label |= Integer.MIN_VALUE;
            return FlowKt__LimitKt$takeWhile$lambda6$$inlined$collectWhile$1.this.emit(null, this);
        }
    }

    public FlowKt__LimitKt$takeWhile$lambda6$$inlined$collectWhile$1(Function2 function2, FlowCollector flowCollector) {
        this.$predicate$inlined = function2;
        this.$this_unsafeFlow$inlined = flowCollector;
    }

    /* JADX WARN: Code duplicated, block: B:17:0x00b9  */
    /* JADX WARN: Code duplicated, block: B:19:0x00da  */
    /* JADX WARN: Code duplicated, block: B:23:0x00f4  */
    /* JADX WARN: Code duplicated, block: B:26:0x00f9  */
    /* JADX WARN: Code duplicated, block: B:28:0x0105  */
    /* JADX WARN: Code duplicated, block: B:7:0x0024  */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlinx.coroutines.flow.FlowCollector
    @Nullable
    public Object emit(T t, @NotNull Continuation<? super Unit> continuation) throws Throwable {
        AnonymousClass1 anonymousClass1;
        Object value;
        Object objInvoke;
        boolean z;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            if ((anonymousClass1.label & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label -= Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        } else {
            anonymousClass1 = new AnonymousClass1(continuation);
        }
        Object $result = anonymousClass1.result;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        switch (anonymousClass1.label) {
            case 0:
                ResultKt.throwOnFailure($result);
                value = t;
                anonymousClass1.L$0 = this;
                anonymousClass1.L$1 = value;
                anonymousClass1.label = 1;
                InlineMarker.mark(6);
                objInvoke = this.$predicate$inlined.invoke(value, anonymousClass1);
                InlineMarker.mark(7);
                if (objInvoke == coroutine_suspended) {
                    return coroutine_suspended;
                }
                if (((Boolean) objInvoke).booleanValue()) {
                    anonymousClass1.L$0 = this;
                    anonymousClass1.L$1 = null;
                    anonymousClass1.label = 2;
                    if (this.$this_unsafeFlow$inlined.emit(value, anonymousClass1) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    z = true;
                } else {
                    z = false;
                }
                if (z) {
                    return Unit.INSTANCE;
                }
                throw new AbortFlowException(this);
            case 1:
                value = anonymousClass1.L$1;
                this = (FlowKt__LimitKt$takeWhile$lambda6$$inlined$collectWhile$1) anonymousClass1.L$0;
                ResultKt.throwOnFailure($result);
                objInvoke = $result;
                if (((Boolean) objInvoke).booleanValue()) {
                    anonymousClass1.L$0 = this;
                    anonymousClass1.L$1 = null;
                    anonymousClass1.label = 2;
                    if (this.$this_unsafeFlow$inlined.emit(value, anonymousClass1) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    z = true;
                } else {
                    z = false;
                }
                if (z) {
                    throw new AbortFlowException(this);
                }
                return Unit.INSTANCE;
            case 2:
                this = (FlowKt__LimitKt$takeWhile$lambda6$$inlined$collectWhile$1) anonymousClass1.L$0;
                ResultKt.throwOnFailure($result);
                z = true;
                if (z) {
                    throw new AbortFlowException(this);
                }
                return Unit.INSTANCE;
            default:
                throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
        }
    }
}
