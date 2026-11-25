package org.catrobat.catroid.content.bricks;

import org.catrobat.catroid.content.bricks.brickspinner.BrickSpinner;
import org.catrobat.catroid.formulaeditor.UserVariable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/UserVariableBrickInterface.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public interface UserVariableBrickInterface extends Brick, BrickSpinner.OnItemSelectedListener<UserVariable> {
    UserVariable getUserVariable();

    void setUserVariable(UserVariable userVariable);
}
