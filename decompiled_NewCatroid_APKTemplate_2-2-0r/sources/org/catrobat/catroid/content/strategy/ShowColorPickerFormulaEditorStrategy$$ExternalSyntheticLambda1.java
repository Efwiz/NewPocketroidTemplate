package org.catrobat.catroid.content.strategy;

import org.catrobat.paintroid.colorpicker.OnColorPickedListener;

/* JADX INFO: compiled from: D8$$SyntheticClass */
/* JADX INFO: loaded from: classes14.dex */
public final /* synthetic */ class ShowColorPickerFormulaEditorStrategy$$ExternalSyntheticLambda1 implements OnColorPickedListener {
    public final /* synthetic */ ShowFormulaEditorStrategy.Callback f$0;

    @Override // org.catrobat.paintroid.colorpicker.OnColorPickedListener
    public final void colorChanged(int i) {
        this.f$0.setValue(i);
    }
}
