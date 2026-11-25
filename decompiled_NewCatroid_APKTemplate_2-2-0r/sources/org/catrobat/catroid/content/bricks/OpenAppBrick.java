package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/OpenAppBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class OpenAppBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_open_app;
    }

    public OpenAppBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_open_app_pkg_field);
    }

    public OpenAppBrick(String packageName) {
        this(new Formula(packageName));
    }

    public OpenAppBrick(Formula packageName) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, packageName);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createOpenAppAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1)));
    }
}
