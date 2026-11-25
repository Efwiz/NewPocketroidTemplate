package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetSkyboxBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetSkyboxBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_threed_set_skybox;
    }

    public SetSkyboxBrick() {
        addAllowedBrickField(Brick.BrickField.FILENAME, R.id.brick_threed_set_skybox_filename_edit);
    }

    public SetSkyboxBrick(String fileName) {
        this(new Formula(fileName));
    }

    public SetSkyboxBrick(Formula fileNameFormula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.FILENAME, fileNameFormula);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetSkyboxAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.FILENAME)));
    }
}
