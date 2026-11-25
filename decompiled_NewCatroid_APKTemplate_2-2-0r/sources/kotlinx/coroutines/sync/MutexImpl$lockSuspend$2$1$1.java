package kotlinx.coroutines.sync;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: Mutex.kt */
/* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/kotlinx-coroutines-core-jvm-1.6.4.jar:kotlinx/coroutines/sync/MutexImpl$lockSuspend$2$1$1.class */
@Metadata(mv = {1, 6, 0}, k = 3, xi = 48, d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "it", "", "invoke"})
final class MutexImpl$lockSuspend$2$1$1 extends Lambda implements Function1<Throwable, Unit> {
    final /* synthetic */ MutexImpl this$0;
    final /* synthetic */ Object $owner;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    MutexImpl$lockSuspend$2$1$1(MutexImpl $receiver, Object $owner) {
        super(1);
        this.this$0 = $receiver;
        this.$owner = $owner;
    }

    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2(@NotNull Throwable it) {
        this.this$0.unlock(this.$owner);
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
        invoke2(th);
        return Unit.INSTANCE;
    }
}
