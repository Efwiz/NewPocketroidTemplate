package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/Export3dObjectToGlbBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class Export3dObjectToGlbBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_export_3d_glb;
    }

    public Export3dObjectToGlbBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_export_3d_glb_id_field);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_export_3d_glb_file_field);
    }

    public Export3dObjectToGlbBrick(String objectId, String destFileName) {
        this(new Formula(objectId), new Formula(destFileName));
    }

    public Export3dObjectToGlbBrick(Formula objectId, Formula destFileName) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, objectId);
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, destFileName);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createExport3dObjectToGlbAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1), getFormulaWithBrickField(Brick.BrickField.VALUE_2)));
    }
}
