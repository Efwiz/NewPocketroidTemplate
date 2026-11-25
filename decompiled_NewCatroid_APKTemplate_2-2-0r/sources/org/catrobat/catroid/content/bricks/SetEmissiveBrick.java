package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetEmissiveBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetEmissiveBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_emissive;
    }

    public SetEmissiveBrick() {
        addAllowedBrickField(Brick.BrickField.OBJECT_NAME, R.id.brick_set_emissive_object);
        addAllowedBrickField(Brick.BrickField.RED, R.id.brick_set_emissive_r);
        addAllowedBrickField(Brick.BrickField.GREEN, R.id.brick_set_emissive_g);
        addAllowedBrickField(Brick.BrickField.BLUE, R.id.brick_set_emissive_b);
        addAllowedBrickField(Brick.BrickField.ALPHA, R.id.brick_set_emissive_a);
        addAllowedBrickField(Brick.BrickField.INTENSITY, R.id.brick_set_emissive_intensity);
        addAllowedBrickField(Brick.BrickField.MATERIAL_TEXTURE_COLOR, R.id.brick_set_emissive_texture);
    }

    public SetEmissiveBrick(String objectName, float r, float g, float b, float a, float intensity, String texturePath) {
        this(new Formula(objectName), new Formula(Float.valueOf(r)), new Formula(Float.valueOf(g)), new Formula(Float.valueOf(b)), new Formula(Float.valueOf(a)), new Formula(Float.valueOf(intensity)), new Formula(texturePath));
    }

    public SetEmissiveBrick(Formula objectName, Formula r, Formula g, Formula b, Formula a, Formula intensity, Formula texturePath) {
        this();
        setFormulaWithBrickField(Brick.BrickField.OBJECT_NAME, objectName);
        setFormulaWithBrickField(Brick.BrickField.RED, r);
        setFormulaWithBrickField(Brick.BrickField.GREEN, g);
        setFormulaWithBrickField(Brick.BrickField.BLUE, b);
        setFormulaWithBrickField(Brick.BrickField.ALPHA, a);
        setFormulaWithBrickField(Brick.BrickField.INTENSITY, intensity);
        setFormulaWithBrickField(Brick.BrickField.MATERIAL_TEXTURE_COLOR, texturePath);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetEmissiveAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.OBJECT_NAME), getFormulaWithBrickField(Brick.BrickField.RED), getFormulaWithBrickField(Brick.BrickField.GREEN), getFormulaWithBrickField(Brick.BrickField.BLUE), getFormulaWithBrickField(Brick.BrickField.ALPHA), getFormulaWithBrickField(Brick.BrickField.INTENSITY), getFormulaWithBrickField(Brick.BrickField.MATERIAL_TEXTURE_COLOR)));
    }
}
