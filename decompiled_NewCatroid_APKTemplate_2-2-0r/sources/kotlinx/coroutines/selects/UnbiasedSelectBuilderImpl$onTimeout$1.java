package kotlinx.coroutines.selects;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: SelectUnbiased.kt */
/* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/kotlinx-coroutines-core-jvm-1.6.4.jar:kotlinx/coroutines/selects/UnbiasedSelectBuilderImpl$onTimeout$1.class */
@Metadata(mv = {1, 6, 0}, k = 3, xi = 48, d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0006\b\u0000\u0010\u0002 \u0000H\n¢\u0006\u0002\b\u0003"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "R", "invoke"})
final class UnbiasedSelectBuilderImpl$onTimeout$1 extends Lambda implements Function0<Unit> {
    final /* synthetic */ UnbiasedSelectBuilderImpl<R> this$0;
    final /* synthetic */ long $timeMillis;
    final /* synthetic */ Function1<Continuation<? super R>, Object> $block;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    UnbiasedSelectBuilderImpl$onTimeout$1(UnbiasedSelectBuilderImpl<? super R> unbiasedSelectBuilderImpl, long $timeMillis, Function1<? super Continuation<? super R>, ? extends Object> function1) {
        super(0);
        this.this$0 = unbiasedSelectBuilderImpl;
        this.$timeMillis = $timeMillis;
        this.$block = function1;
    }

    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2() {
        this.this$0.getInstance().onTimeout(this.$timeMillis, this.$block);
    }

    @Override // kotlin.jvm.functions.Function0
    public /* bridge */ /* synthetic */ Unit invoke() {
        invoke2();
        return Unit.INSTANCE;
    }
}
