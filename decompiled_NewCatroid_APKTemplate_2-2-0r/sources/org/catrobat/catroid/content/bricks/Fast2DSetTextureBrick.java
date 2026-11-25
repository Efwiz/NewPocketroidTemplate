package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/Fast2DSetTextureBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class Fast2DSetTextureBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_fast2d_set_texture;
    }

    public Fast2DSetTextureBrick() {
        addAllowedBrickField(Brick.BrickField.NAME, R.id.brick_fast2d_set_texture_id);
        addAllowedBrickField(Brick.BrickField.STRING, R.id.brick_fast2d_set_texture_file);
    }

    public Fast2DSetTextureBrick(String id2, String filePath) {
        this(new Formula(id2), new Formula(filePath));
    }

    public Fast2DSetTextureBrick(Formula id2, Formula filePath) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NAME, id2);
        setFormulaWithBrickField(Brick.BrickField.STRING, filePath);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createFast2DSetTextureAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NAME), getFormulaWithBrickField(Brick.BrickField.STRING)));
    }
}
