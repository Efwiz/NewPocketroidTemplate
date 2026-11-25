package kotlinx.coroutines.android;

import kotlin.Metadata;
import kotlinx.coroutines.CancellableContinuation;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: renamed from: kotlinx.coroutines.android.HandlerDispatcherKt$awaitFrame$lambda-3$$inlined$Runnable$1, reason: invalid class name */
/* JADX INFO: compiled from: Runnable.kt */
/* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/kotlinx-coroutines-android-1.6.4.jar:kotlinx/coroutines/android/HandlerDispatcherKt$awaitFrame$lambda-3$$inlined$Runnable$1.class */
@Metadata(mv = {1, 6, 0}, k = 3, xi = 48, d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002¨\u0006\u0003"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "run", "kotlinx/coroutines/RunnableKt$Runnable$1"})
public final class HandlerDispatcherKt$awaitFrame$lambda3$$inlined$Runnable$1 implements Runnable {
    final /* synthetic */ CancellableContinuation $cont$inlined;

    public HandlerDispatcherKt$awaitFrame$lambda3$$inlined$Runnable$1(CancellableContinuation cancellableContinuation) {
        this.$cont$inlined = cancellableContinuation;
    }

    @Override // java.lang.Runnable
    public final void run() {
        HandlerDispatcherKt.updateChoreographerAndPostFrameCallback(this.$cont$inlined);
    }
}
