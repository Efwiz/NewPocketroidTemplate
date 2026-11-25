package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetMaterialBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetMaterialBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_material;
    }

    public SetMaterialBrick() {
        addAllowedBrickField(Brick.BrickField.OBJECT_NAME, R.id.brick_set_material_object_edit);
        addAllowedBrickField(Brick.BrickField.MATERIAL_R, R.id.brick_set_material_r_edit);
        addAllowedBrickField(Brick.BrickField.MATERIAL_G, R.id.brick_set_material_g_edit);
        addAllowedBrickField(Brick.BrickField.MATERIAL_B, R.id.brick_set_material_b_edit);
        addAllowedBrickField(Brick.BrickField.MATERIAL_A, R.id.brick_set_material_a_edit);
        addAllowedBrickField(Brick.BrickField.MATERIAL_METALLIC, R.id.brick_set_material_metallic_edit);
        addAllowedBrickField(Brick.BrickField.MATERIAL_ROUGHNESS, R.id.brick_set_material_roughness_edit);
        addAllowedBrickField(Brick.BrickField.MATERIAL_TEXTURE_COLOR, R.id.brick_set_material_texture_color_edit);
        addAllowedBrickField(Brick.BrickField.MATERIAL_TEXTURE_NORMAL, R.id.brick_set_material_texture_normal_edit);
        addAllowedBrickField(Brick.BrickField.MATERIAL_TEXTURE_MR, R.id.brick_set_material_texture_mr_edit);
    }

    public SetMaterialBrick(String objectName) {
        this();
        setFormulaWithBrickField(Brick.BrickField.OBJECT_NAME, new Formula(objectName));
    }

    public SetMaterialBrick(Formula objectName, Formula r, Formula g, Formula b, Formula a, Formula metallic, Formula roughness, Formula colorTexture, Formula normalTexture, Formula mrTexture) {
        this();
        setFormulaWithBrickField(Brick.BrickField.OBJECT_NAME, objectName);
        setFormulaWithBrickField(Brick.BrickField.MATERIAL_R, r);
        setFormulaWithBrickField(Brick.BrickField.MATERIAL_G, g);
        setFormulaWithBrickField(Brick.BrickField.MATERIAL_B, b);
        setFormulaWithBrickField(Brick.BrickField.MATERIAL_A, a);
        setFormulaWithBrickField(Brick.BrickField.MATERIAL_METALLIC, metallic);
        setFormulaWithBrickField(Brick.BrickField.MATERIAL_ROUGHNESS, roughness);
        setFormulaWithBrickField(Brick.BrickField.MATERIAL_TEXTURE_COLOR, colorTexture);
        setFormulaWithBrickField(Brick.BrickField.MATERIAL_TEXTURE_NORMAL, normalTexture);
        setFormulaWithBrickField(Brick.BrickField.MATERIAL_TEXTURE_MR, mrTexture);
    }

    public SetMaterialBrick(String objectName, Double r, Double g, Double b, Double a, Double metallic, Double roughness, String colorTexture, String normalTexture, String mrTexture) {
        this(new Formula(objectName), r != null ? new Formula(r) : null, g != null ? new Formula(g) : null, b != null ? new Formula(b) : null, a != null ? new Formula(a) : null, metallic != null ? new Formula(metallic) : null, roughness != null ? new Formula(roughness) : null, colorTexture != null ? new Formula(colorTexture) : null, normalTexture != null ? new Formula(normalTexture) : null, mrTexture != null ? new Formula(mrTexture) : null);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetMaterialAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.OBJECT_NAME), getFormulaWithBrickField(Brick.BrickField.MATERIAL_R), getFormulaWithBrickField(Brick.BrickField.MATERIAL_G), getFormulaWithBrickField(Brick.BrickField.MATERIAL_B), getFormulaWithBrickField(Brick.BrickField.MATERIAL_A), getFormulaWithBrickField(Brick.BrickField.MATERIAL_METALLIC), getFormulaWithBrickField(Brick.BrickField.MATERIAL_ROUGHNESS), getFormulaWithBrickField(Brick.BrickField.MATERIAL_TEXTURE_COLOR), getFormulaWithBrickField(Brick.BrickField.MATERIAL_TEXTURE_NORMAL), getFormulaWithBrickField(Brick.BrickField.MATERIAL_TEXTURE_MR)));
    }
}
