package org.catrobat.catroid.content.actions;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/NativeViewListenerAction$update$1$16.class
  classes14.dex
 */
/* JADX INFO: compiled from: NativeViewListenerAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000%\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\r\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016J*\u0010\u0006\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\tH\u0016J*\u0010\f\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tH\u0016¨\u0006\u000e"}, d2 = {"org/catrobat/catroid/content/actions/NativeViewListenerAction$update$1$16", "Landroid/text/TextWatcher;", "afterTextChanged", "", "s", "Landroid/text/Editable;", "beforeTextChanged", "", "start", "", "count", "after", "onTextChanged", "before", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NativeViewListenerAction$update$1$16 implements TextWatcher {
    final /* synthetic */ Function0<Unit> $updateCursor;
    final /* synthetic */ View $view;

    @Override // android.text.TextWatcher
    public void afterTextChanged(Editable s) {
    }

    @Override // android.text.TextWatcher
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    NativeViewListenerAction$update$1$16(View view, Function0<Unit> function0) {
        this.$view = view;
        this.$updateCursor = function0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onTextChanged$lambda$0(Function0 tmp0) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        tmp0.invoke();
    }

    @Override // android.text.TextWatcher
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        View view = this.$view;
        final Function0<Unit> function0 = this.$updateCursor;
        view.post(new Runnable() { // from class: org.catrobat.catroid.content.actions.NativeViewListenerAction$update$1$16$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                NativeViewListenerAction$update$1$16.onTextChanged$lambda$0(function0);
            }
        });
    }
}
