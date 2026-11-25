package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: AbstractChannel.kt */
/* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/kotlinx-coroutines-core-jvm-1.6.4.jar:kotlinx/coroutines/channels/AbstractChannel$receiveCatching$1.class */
@Metadata(mv = {1, 6, 0}, k = 3, xi = 48)
@DebugMetadata(f = "AbstractChannel.kt", l = {633}, i = {}, s = {}, n = {}, m = "receiveCatching-JP2dKIU", c = "kotlinx.coroutines.channels.AbstractChannel")
final class AbstractChannel$receiveCatching$1 extends ContinuationImpl {
    /* synthetic */ Object result;
    final /* synthetic */ AbstractChannel<E> this$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    AbstractChannel$receiveCatching$1(AbstractChannel<E> abstractChannel, Continuation<? super AbstractChannel$receiveCatching$1> continuation) {
        super(continuation);
        this.this$0 = abstractChannel;
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    @Nullable
    public final Object invokeSuspend(@NotNull Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        Object objMo4428receiveCatchingJP2dKIU = this.this$0.mo4428receiveCatchingJP2dKIU(this);
        return objMo4428receiveCatchingJP2dKIU == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objMo4428receiveCatchingJP2dKIU : ChannelResult.m4442boximpl(objMo4428receiveCatchingJP2dKIU);
    }
}
