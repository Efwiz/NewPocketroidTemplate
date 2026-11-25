package kotlinx.coroutines.selects;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.intrinsics.CancellableKt;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: Runnable.kt */
/* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/kotlinx-coroutines-core-jvm-1.6.4.jar:kotlinx/coroutines/selects/SelectBuilderImpl$onTimeout$$inlined$Runnable$1.class */
@Metadata(mv = {1, 6, 0}, k = 3, xi = 48, d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002¨\u0006\u0003"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "run", "kotlinx/coroutines/RunnableKt$Runnable$1"})
public final class SelectBuilderImpl$onTimeout$$inlined$Runnable$1 implements Runnable {
    final /* synthetic */ SelectBuilderImpl this$0;
    final /* synthetic */ Function1 $block$inlined;

    public SelectBuilderImpl$onTimeout$$inlined$Runnable$1(SelectBuilderImpl selectBuilderImpl, Function1 function1) {
        this.this$0 = selectBuilderImpl;
        this.$block$inlined = function1;
    }

    @Override // java.lang.Runnable
    public final void run() throws Throwable {
        if (this.this$0.trySelect()) {
            CancellableKt.startCoroutineCancellable(this.$block$inlined, this.this$0.getCompletion());
        }
    }
}
