package org.catrobat.catroid.ui;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/ViewUtils.class
  classes14.dex
 */
/* JADX INFO: compiled from: ViewUtils.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\f\u0010\u0002\u001a\u00020\u0003*\u0004\u0018\u00010\u0004\u001a\f\u0010\u0005\u001a\u00020\u0003*\u0004\u0018\u00010\u0004\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0006"}, d2 = {"WAIT_TIME", "", "hideKeyboard", "", "Landroid/view/View;", "showKeyboard", "catroid_apktemplateTemplateRelease"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ViewUtils {
    private static final long WAIT_TIME = 100;

    public static final void showKeyboard(final View view) {
        if (view != null) {
            Object systemService = view.getContext().getSystemService("input_method");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.inputmethod.InputMethodManager");
            final InputMethodManager inputMethodManager = (InputMethodManager) systemService;
            view.postDelayed(new Runnable() { // from class: org.catrobat.catroid.ui.ViewUtils$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ViewUtils.showKeyboard$lambda$1$lambda$0(view, inputMethodManager);
                }
            }, WAIT_TIME);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void showKeyboard$lambda$1$lambda$0(View it, InputMethodManager inputMethodManager) {
        Intrinsics.checkNotNullParameter(it, "$it");
        Intrinsics.checkNotNullParameter(inputMethodManager, "$inputMethodManager");
        it.requestFocus();
        inputMethodManager.showSoftInput(it, 0);
    }

    public static final void hideKeyboard(View view) {
        if (view != null) {
            Object systemService = view.getContext().getSystemService("input_method");
            Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.inputmethod.InputMethodManager");
            ((InputMethodManager) systemService).hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
