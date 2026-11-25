package org.catrobat.catroid.content.strategy;

import android.view.View;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/strategy/ShowFormulaEditorStrategy.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public interface ShowFormulaEditorStrategy {

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/strategy/ShowFormulaEditorStrategy$Callback.class
      classes14.dex
     */
    public interface Callback {
        int getValue();

        void setValue(int value);

        void showFormulaEditor(View view);
    }

    void showFormulaEditorToEditFormula(View view, Callback callback);
}
