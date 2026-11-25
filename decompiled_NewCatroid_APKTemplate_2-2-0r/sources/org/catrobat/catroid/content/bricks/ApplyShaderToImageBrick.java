package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/ApplyShaderToImageBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ApplyShaderToImageBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_apply_shader_to_image;
    }

    public ApplyShaderToImageBrick() {
        addAllowedBrickField(Brick.BrickField.NAME, R.id.brick_apply_shader_filename);
        addAllowedBrickField(Brick.BrickField.TEXT, R.id.brick_apply_shader_vsh);
        addAllowedBrickField(Brick.BrickField.X_POSITION, R.id.brick_apply_shader_fsh);
    }

    public ApplyShaderToImageBrick(String filename, String vsh, String fsh) {
        this(new Formula(filename), new Formula(vsh), new Formula(fsh));
    }

    public ApplyShaderToImageBrick(Formula filename, Formula vsh, Formula fsh) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NAME, filename);
        setFormulaWithBrickField(Brick.BrickField.TEXT, vsh);
        setFormulaWithBrickField(Brick.BrickField.X_POSITION, fsh);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createApplyShaderToImageAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NAME), getFormulaWithBrickField(Brick.BrickField.TEXT), getFormulaWithBrickField(Brick.BrickField.X_POSITION)));
    }
}
