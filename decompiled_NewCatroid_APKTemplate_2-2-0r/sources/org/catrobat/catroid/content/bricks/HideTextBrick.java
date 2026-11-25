package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.UserVariable;
import org.catrobat.catroid.utils.ShowTextUtils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/HideTextBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class HideTextBrick extends UserVariableBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.UserVariableBrick
    protected int getSpinnerId() {
        return R.id.hide_variable_spinner;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_hide_variable;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        if (this.userVariable == null || this.userVariable.getName() == null) {
            this.userVariable = new UserVariable("NoVariableSet", CatroidApplication.getAppContext().getString(R.string.no_variable_selected));
            this.userVariable.setDummy(true);
        }
        sequence.addAction(sprite.getActionFactory().createHideVariableAction(sprite, this.userVariable, new ShowTextUtils.AndroidStringProvider(CatroidApplication.getAppContext())));
    }
}
