package org.catrobat.paintroid.ui;

import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: KeyboardListener.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001a\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\u000bH\u0002R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0007\"\u0004\b\b\u0010\t¨\u0006\u000f"}, d2 = {"Lorg/catrobat/paintroid/ui/KeyboardListener;", "", "activityRootView", "Landroid/view/View;", "(Landroid/view/View;)V", "isSoftKeyboardVisible", "", "()Z", "setSoftKeyboardVisible", "(Z)V", "dpToPx", "", "displayMetrics", "Landroid/util/DisplayMetrics;", "dpValue", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class KeyboardListener {
    private boolean isSoftKeyboardVisible;

    public KeyboardListener(final View activityRootView) {
        Intrinsics.checkNotNullParameter(activityRootView, "activityRootView");
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: org.catrobat.paintroid.ui.KeyboardListener$$ExternalSyntheticLambda0
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                KeyboardListener.m5973_init_$lambda0(activityRootView, this);
            }
        });
    }

    /* JADX INFO: renamed from: isSoftKeyboardVisible, reason: from getter */
    public final boolean getIsSoftKeyboardVisible() {
        return this.isSoftKeyboardVisible;
    }

    public final void setSoftKeyboardVisible(boolean z) {
        this.isSoftKeyboardVisible = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: _init_$lambda-0, reason: not valid java name */
    public static final void m5973_init_$lambda0(View activityRootView, KeyboardListener this$0) {
        Intrinsics.checkNotNullParameter(activityRootView, "$activityRootView");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        int height = activityRootView.getRootView().getHeight() - activityRootView.getHeight();
        DisplayMetrics displayMetrics = activityRootView.getResources().getDisplayMetrics();
        float f = height;
        Intrinsics.checkNotNullExpressionValue(displayMetrics, "displayMetrics");
        this$0.setSoftKeyboardVisible(f > dpToPx$default(this$0, displayMetrics, 0.0f, 2, null));
    }

    static /* synthetic */ float dpToPx$default(KeyboardListener keyboardListener, DisplayMetrics displayMetrics, float f, int i, Object obj) {
        if ((i & 2) != 0) {
            f = 300.0f;
        }
        return keyboardListener.dpToPx(displayMetrics, f);
    }

    private final float dpToPx(DisplayMetrics displayMetrics, float dpValue) {
        return TypedValue.applyDimension(1, dpValue, displayMetrics);
    }
}
