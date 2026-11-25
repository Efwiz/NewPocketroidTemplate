package androidx.core.view;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: View.kt */
/* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/core-ktx-1.2.0.aar:classes.jar:androidx/core/view/ViewKt$postOnAnimationDelayed$runnable$1.class */
@Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 3, d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "run"})
public final class ViewKt$postOnAnimationDelayed$runnable$1 implements Runnable {
    final /* synthetic */ Function0 $action;

    public ViewKt$postOnAnimationDelayed$runnable$1(Function0 function0) {
        this.$action = function0;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.$action.invoke();
    }
}
