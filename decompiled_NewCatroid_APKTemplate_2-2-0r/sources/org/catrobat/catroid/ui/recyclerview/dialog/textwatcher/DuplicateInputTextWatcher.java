package org.catrobat.catroid.ui.recyclerview.dialog.textwatcher;

import java.util.Iterator;
import java.util.List;
import org.catrobat.catroid.common.Nameable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/dialog/textwatcher/DuplicateInputTextWatcher.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class DuplicateInputTextWatcher<T extends Nameable> extends InputWatcher.TextWatcher {
    private List<T> originalScope;

    public DuplicateInputTextWatcher(List<T> originalScope) {
        this.originalScope = originalScope;
        getScopeFromOriginalScope();
    }

    public void setOriginalScope(List<T> scope) {
        this.originalScope = scope;
        getScopeFromOriginalScope();
    }

    private void getScopeFromOriginalScope() {
        List<T> list = this.originalScope;
        if (list != null) {
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                this.scope.add(it.next().getName());
            }
        }
    }
}
