package org.catrobat.paintroid.ui.tools;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.chip.Chip;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.paintroid.R;
import org.catrobat.paintroid.tools.options.ClipboardToolOptionsView;
import org.jetbrains.kotlin.cli.common.arguments.CommonCompilerArguments;

/* JADX INFO: compiled from: DefaultClipboardToolOptionsView.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\fH\u0002J\u0010\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\u0006H\u0016R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lorg/catrobat/paintroid/ui/tools/DefaultClipboardToolOptionsView;", "Lorg/catrobat/paintroid/tools/options/ClipboardToolOptionsView;", "rootView", "Landroid/view/ViewGroup;", "(Landroid/view/ViewGroup;)V", "callback", "Lorg/catrobat/paintroid/tools/options/ClipboardToolOptionsView$Callback;", "copyChip", "Lcom/google/android/material/chip/Chip;", "cutChip", "pasteChip", "enablePaste", "", CommonCompilerArguments.ENABLE, "", "initializeListeners", "setCallback", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class DefaultClipboardToolOptionsView implements ClipboardToolOptionsView {
    private ClipboardToolOptionsView.Callback callback;
    private final Chip copyChip;
    private final Chip cutChip;
    private final Chip pasteChip;

    public DefaultClipboardToolOptionsView(ViewGroup rootView) {
        Intrinsics.checkNotNullParameter(rootView, "rootView");
        View viewInflate = LayoutInflater.from(rootView.getContext()).inflate(R.layout.dialog_pocketpaint_clipboard_tool, rootView);
        Intrinsics.checkNotNullExpressionValue(viewInflate, "inflater.inflate(R.layou…clipboard_tool, rootView)");
        View viewFindViewById = viewInflate.findViewById(R.id.action_copy);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById, "stampToolOptionsView.fin…iewById(R.id.action_copy)");
        this.copyChip = (Chip) viewFindViewById;
        View viewFindViewById2 = viewInflate.findViewById(R.id.action_paste);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById2, "stampToolOptionsView.fin…ewById(R.id.action_paste)");
        this.pasteChip = (Chip) viewFindViewById2;
        View viewFindViewById3 = viewInflate.findViewById(R.id.action_cut);
        Intrinsics.checkNotNullExpressionValue(viewFindViewById3, "stampToolOptionsView.findViewById(R.id.action_cut)");
        this.cutChip = (Chip) viewFindViewById3;
        enablePaste(false);
        initializeListeners();
    }

    private final void initializeListeners() {
        this.copyChip.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.paintroid.ui.tools.DefaultClipboardToolOptionsView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DefaultClipboardToolOptionsView.m5984initializeListeners$lambda0(this.f$0, view);
            }
        });
        this.cutChip.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.paintroid.ui.tools.DefaultClipboardToolOptionsView$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DefaultClipboardToolOptionsView.m5985initializeListeners$lambda1(this.f$0, view);
            }
        });
        this.pasteChip.setOnClickListener(new View.OnClickListener() { // from class: org.catrobat.paintroid.ui.tools.DefaultClipboardToolOptionsView$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DefaultClipboardToolOptionsView.m5986initializeListeners$lambda2(this.f$0, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initializeListeners$lambda-0, reason: not valid java name */
    public static final void m5984initializeListeners$lambda0(DefaultClipboardToolOptionsView this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ClipboardToolOptionsView.Callback callback = this$0.callback;
        if (callback == null) {
            return;
        }
        callback.copyClicked();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initializeListeners$lambda-1, reason: not valid java name */
    public static final void m5985initializeListeners$lambda1(DefaultClipboardToolOptionsView this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ClipboardToolOptionsView.Callback callback = this$0.callback;
        if (callback == null) {
            return;
        }
        callback.cutClicked();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: initializeListeners$lambda-2, reason: not valid java name */
    public static final void m5986initializeListeners$lambda2(DefaultClipboardToolOptionsView this$0, View view) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        ClipboardToolOptionsView.Callback callback = this$0.callback;
        if (callback == null) {
            return;
        }
        callback.pasteClicked();
    }

    @Override // org.catrobat.paintroid.tools.options.ClipboardToolOptionsView
    public void setCallback(ClipboardToolOptionsView.Callback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.callback = callback;
    }

    @Override // org.catrobat.paintroid.tools.options.ClipboardToolOptionsView
    public void enablePaste(boolean enable) {
        this.pasteChip.setEnabled(enable);
    }
}
