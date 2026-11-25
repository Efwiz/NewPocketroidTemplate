package org.catrobat.catroid.merge;

import org.catrobat.catroid.common.Nameable;
import org.catrobat.catroid.ui.recyclerview.dialog.ReplaceExistingProjectDialogFragment;
import org.catrobat.catroid.ui.recyclerview.dialog.textwatcher.InputWatcher;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/merge/NewProjectNameTextWatcher.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class NewProjectNameTextWatcher<T extends Nameable> extends InputWatcher.TextWatcher {
    @Override // org.catrobat.catroid.ui.recyclerview.dialog.textwatcher.InputWatcher.TextWatcher
    protected boolean isNameUnique(String name2) {
        return !ReplaceExistingProjectDialogFragment.projectExistsInDirectory(name2).booleanValue();
    }
}
