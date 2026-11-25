package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetSaveScenesBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetSaveScenesBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_save_scenes;
    }

    public SetSaveScenesBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE, R.id.brick_set_save_scenes_edit_text);
    }

    public SetSaveScenesBrick(Integer value) {
        this(new Formula(value));
    }

    public SetSaveScenesBrick(Formula formula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE, formula);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetSaveScenesAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE)));
    }
}
