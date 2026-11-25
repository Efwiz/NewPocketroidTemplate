package org.catrobat.paintroid.tools.options;

import kotlin.Metadata;

/* JADX INFO: compiled from: SmudgeToolOptionsView.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001:\u0001\u0006J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0007"}, d2 = {"Lorg/catrobat/paintroid/tools/options/SmudgeToolOptionsView;", "Lorg/catrobat/paintroid/tools/options/BrushToolOptionsView;", "setCallback", "", "callback", "Lorg/catrobat/paintroid/tools/options/SmudgeToolOptionsView$Callback;", "Callback", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public interface SmudgeToolOptionsView extends BrushToolOptionsView {

    /* JADX INFO: compiled from: SmudgeToolOptionsView.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\u0005H&¨\u0006\b"}, d2 = {"Lorg/catrobat/paintroid/tools/options/SmudgeToolOptionsView$Callback;", "", "onDragChanged", "", "drag", "", "onPressureChanged", "pressure", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public interface Callback {
        void onDragChanged(int drag);

        void onPressureChanged(int pressure);
    }

    void setCallback(Callback callback);
}
