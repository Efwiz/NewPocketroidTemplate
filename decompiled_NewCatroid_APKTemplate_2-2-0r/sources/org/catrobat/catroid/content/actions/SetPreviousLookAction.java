package org.catrobat.catroid.content.actions;

import kotlin.Metadata;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetPreviousLookAction.class
  classes14.dex
 */
/* JADX INFO: compiled from: SetPreviousLookAction.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/catrobat/catroid/content/actions/SetPreviousLookAction;", "Lorg/catrobat/catroid/content/actions/SetRelativeLookAction;", "()V", "change", "", "getChange", "()I", "setChange", "(I)V", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SetPreviousLookAction extends SetRelativeLookAction {
    private int change = -1;

    @Override // org.catrobat.catroid.content.actions.SetRelativeLookAction
    public int getChange() {
        return this.change;
    }

    @Override // org.catrobat.catroid.content.actions.SetRelativeLookAction
    public void setChange(int i) {
        this.change = i;
    }
}
