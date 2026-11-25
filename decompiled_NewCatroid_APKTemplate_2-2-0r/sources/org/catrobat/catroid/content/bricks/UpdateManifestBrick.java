package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/UpdateManifestBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class UpdateManifestBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_update_manifest;
    }

    public UpdateManifestBrick() {
        addAllowedBrickField(Brick.BrickField.TEXT_1, R.id.brick_manif_edit_apk);
        addAllowedBrickField(Brick.BrickField.TEXT_2, R.id.brick_manif_edit_pkg);
        addAllowedBrickField(Brick.BrickField.TEXT_3, R.id.brick_manif_edit_name);
        addAllowedBrickField(Brick.BrickField.TEXT_4, R.id.brick_manif_edit_vcode);
        addAllowedBrickField(Brick.BrickField.TEXT_5, R.id.brick_manif_edit_vname);
        addAllowedBrickField(Brick.BrickField.TEXT_6, R.id.brick_manif_edit_min);
        addAllowedBrickField(Brick.BrickField.TEXT_7, R.id.brick_manif_edit_target);
        addAllowedBrickField(Brick.BrickField.TEXT_8, R.id.brick_manif_edit_debug);
        addAllowedBrickField(Brick.BrickField.TEXT_9, R.id.brick_manif_edit_p_add);
        addAllowedBrickField(Brick.BrickField.TEXT_10, R.id.brick_manif_edit_p_rem);
    }

    public UpdateManifestBrick(Formula apk, Formula pkg, Formula name2, Formula vCode, Formula vName, Formula min, Formula target, Formula debug, Formula pAdd, Formula pRem) {
        this();
        setFormulaWithBrickField(Brick.BrickField.TEXT_1, apk);
        setFormulaWithBrickField(Brick.BrickField.TEXT_2, pkg);
        setFormulaWithBrickField(Brick.BrickField.TEXT_3, name2);
        setFormulaWithBrickField(Brick.BrickField.TEXT_4, vCode);
        setFormulaWithBrickField(Brick.BrickField.TEXT_5, vName);
        setFormulaWithBrickField(Brick.BrickField.TEXT_6, min);
        setFormulaWithBrickField(Brick.BrickField.TEXT_7, target);
        setFormulaWithBrickField(Brick.BrickField.TEXT_8, debug);
        setFormulaWithBrickField(Brick.BrickField.TEXT_9, pAdd);
        setFormulaWithBrickField(Brick.BrickField.TEXT_10, pRem);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createUpdateManifestAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.TEXT_1), getFormulaWithBrickField(Brick.BrickField.TEXT_2), getFormulaWithBrickField(Brick.BrickField.TEXT_3), getFormulaWithBrickField(Brick.BrickField.TEXT_4), getFormulaWithBrickField(Brick.BrickField.TEXT_5), getFormulaWithBrickField(Brick.BrickField.TEXT_6), getFormulaWithBrickField(Brick.BrickField.TEXT_7), getFormulaWithBrickField(Brick.BrickField.TEXT_8), getFormulaWithBrickField(Brick.BrickField.TEXT_9), getFormulaWithBrickField(Brick.BrickField.TEXT_10)));
    }
}
