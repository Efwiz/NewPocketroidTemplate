package org.catrobat.paintroid.tools.options;

import kotlin.Metadata;

/* JADX INFO: compiled from: FillToolOptionsView.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001:\u0001\u0006J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0007"}, d2 = {"Lorg/catrobat/paintroid/tools/options/FillToolOptionsView;", "", "setCallback", "", "callback", "Lorg/catrobat/paintroid/tools/options/FillToolOptionsView$Callback;", "Callback", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public interface FillToolOptionsView {

    /* JADX INFO: compiled from: FillToolOptionsView.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006"}, d2 = {"Lorg/catrobat/paintroid/tools/options/FillToolOptionsView$Callback;", "", "onColorToleranceChanged", "", "colorTolerance", "", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
    public interface Callback {
        void onColorToleranceChanged(int colorTolerance);
    }

    void setCallback(Callback callback);
}
