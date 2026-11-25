package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/LaunchProjectBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class LaunchProjectBrick extends FormulaBrick {
    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_launch_project;
    }

    public LaunchProjectBrick() {
        addAllowedBrickField(Brick.BrickField.PROJECT_NAME, R.id.brick_launch_project_name);
    }

    public LaunchProjectBrick(String name2) {
        this(new Formula(name2));
    }

    public LaunchProjectBrick(Formula name2) {
        this();
        setFormulaWithBrickField(Brick.BrickField.PROJECT_NAME, name2);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createLaunchProjectAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.PROJECT_NAME)));
    }
}
