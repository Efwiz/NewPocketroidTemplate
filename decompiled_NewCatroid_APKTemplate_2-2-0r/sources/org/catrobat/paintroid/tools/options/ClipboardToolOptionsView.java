package org.catrobat.paintroid.tools.options;

import kotlin.Metadata;
import org.jetbrains.kotlin.cli.common.arguments.CommonCompilerArguments;

/* JADX INFO: compiled from: ClipboardToolOptionsView.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001:\u0001\tJ\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH&¨\u0006\n"}, d2 = {"Lorg/catrobat/paintroid/tools/options/ClipboardToolOptionsView;", "", "enablePaste", "", CommonCompilerArguments.ENABLE, "", "setCallback", "callback", "Lorg/catrobat/paintroid/tools/options/ClipboardToolOptionsView$Callback;", "Callback", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public interface ClipboardToolOptionsView {

    /* JADX INFO: compiled from: ClipboardToolOptionsView.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\b\u0010\u0004\u001a\u00020\u0003H&J\b\u0010\u0005\u001a\u00020\u0003H&¨\u0006\u0006"}, d2 = {"Lorg/catrobat/paintroid/tools/options/ClipboardToolOptionsView$Callback;", "", "copyClicked", "", "cutClicked", "pasteClicked", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public interface Callback {
        void copyClicked();

        void cutClicked();

        void pasteClicked();
    }

    void enablePaste(boolean enable);

    void setCallback(Callback callback);
}
