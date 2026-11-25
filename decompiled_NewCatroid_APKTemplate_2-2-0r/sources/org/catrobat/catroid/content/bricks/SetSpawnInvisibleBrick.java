package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.content.actions.SetSpawnInvisibleAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetSpawnInvisibleBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetSpawnInvisibleBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_spawn_invisible;
    }

    public SetSpawnInvisibleBrick() {
        addAllowedBrickField(Brick.BrickField.STRING, R.id.brick_spawn_invisible_edit_text);
    }

    public SetSpawnInvisibleBrick(String objectId) {
        this();
        setFormulaWithBrickField(Brick.BrickField.STRING, new Formula(objectId));
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction((SetSpawnInvisibleAction) sprite.getActionFactory().createSetSpawnInvisibleAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.STRING)));
    }
}
