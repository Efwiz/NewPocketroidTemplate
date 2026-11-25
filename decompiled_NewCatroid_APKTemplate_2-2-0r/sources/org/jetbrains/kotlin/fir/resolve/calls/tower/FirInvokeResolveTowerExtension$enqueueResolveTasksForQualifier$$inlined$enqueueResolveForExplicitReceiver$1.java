package org.jetbrains.kotlin.fir.resolve.calls.tower;

import com.gaurav.avnc.vnc.XKeySym;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineConstantsKt;
import org.jetbrains.kotlin.fir.expressions.FirResolvedQualifier;
import org.jetbrains.kotlin.fir.resolve.calls.CallInfo;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: FirInvokeResolveTowerExtension.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001H\u008a@¨\u0006\u0003"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "org/jetbrains/kotlin/fir/resolve/calls/tower/FirInvokeResolveTowerExtension$enqueueInvokeReceiverTask$1", "org/jetbrains/kotlin/fir/resolve/calls/tower/FirInvokeResolveTowerExtension$enqueueResolveForExplicitReceiver$$inlined$enqueueInvokeReceiverTask$1"}, k = 3, mv = {1, 9, 0}, xi = 48)
@DebugMetadata(c = "org.jetbrains.kotlin.fir.resolve.calls.tower.FirInvokeResolveTowerExtension$enqueueResolveTasksForQualifier$$inlined$enqueueResolveForExplicitReceiver$1", f = "FirInvokeResolveTowerExtension.kt", i = {}, l = {XKeySym.XK_Ecaron}, m = CoroutineCodegenUtilKt.INVOKE_SUSPEND_METHOD_NAME, n = {}, s = {})
public final class FirInvokeResolveTowerExtension$enqueueResolveTasksForQualifier$$inlined$enqueueResolveForExplicitReceiver$1 extends SuspendLambda implements Function1<Continuation<? super Unit>, Object> {
    final /* synthetic */ InvokeReceiverResolveTask $invokeReceiverProcessor;
    final /* synthetic */ CallInfo $invokeReceiverVariableInfo$inlined;
    final /* synthetic */ FirResolvedQualifier $receiver$inlined;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirInvokeResolveTowerExtension$enqueueResolveTasksForQualifier$$inlined$enqueueResolveForExplicitReceiver$1(InvokeReceiverResolveTask invokeReceiverResolveTask, Continuation continuation, CallInfo callInfo, FirResolvedQualifier firResolvedQualifier) {
        super(1, continuation);
        this.$invokeReceiverProcessor = invokeReceiverResolveTask;
        this.$invokeReceiverVariableInfo$inlined = callInfo;
        this.$receiver$inlined = firResolvedQualifier;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation<Unit> create(Continuation<?> continuation) {
        return new FirInvokeResolveTowerExtension$enqueueResolveTasksForQualifier$$inlined$enqueueResolveForExplicitReceiver$1(this.$invokeReceiverProcessor, continuation, this.$invokeReceiverVariableInfo$inlined, this.$receiver$inlined);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Continuation<? super Unit> continuation) {
        return ((FirInvokeResolveTowerExtension$enqueueResolveTasksForQualifier$$inlined$enqueueResolveForExplicitReceiver$1) create(continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            InvokeReceiverResolveTask invokeReceiverResolveTask = this.$invokeReceiverProcessor;
            CallInfo callInfo = this.$invokeReceiverVariableInfo$inlined;
            FirResolvedQualifier firResolvedQualifier = this.$receiver$inlined;
            this.label = 1;
            if (invokeReceiverResolveTask.runResolverForQualifierReceiver(callInfo, firResolvedQualifier, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException(CoroutineConstantsKt.ILLEGAL_STATE_ERROR_MESSAGE);
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
