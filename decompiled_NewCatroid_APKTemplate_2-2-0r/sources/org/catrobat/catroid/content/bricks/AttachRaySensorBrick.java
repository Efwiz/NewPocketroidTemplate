package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/AttachRaySensorBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class AttachRaySensorBrick extends FormulaBrick {
    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_attach_ray_sensor;
    }

    public AttachRaySensorBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_attach_ray_name);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_attach_ray_obj);
        addAllowedBrickField(Brick.BrickField.VALUE_3, R.id.brick_attach_ray_off_x);
        addAllowedBrickField(Brick.BrickField.VALUE_4, R.id.brick_attach_ray_off_y);
        addAllowedBrickField(Brick.BrickField.VALUE_5, R.id.brick_attach_ray_off_z);
        addAllowedBrickField(Brick.BrickField.VALUE_6, R.id.brick_attach_ray_dir_x);
        addAllowedBrickField(Brick.BrickField.VALUE_7, R.id.brick_attach_ray_dir_y);
        addAllowedBrickField(Brick.BrickField.VALUE_8, R.id.brick_attach_ray_dir_z);
        addAllowedBrickField(Brick.BrickField.VALUE_9, R.id.brick_attach_ray_dist);
    }

    public AttachRaySensorBrick(String ray, String obj, float ofx, float ofy, float ofz, float dirx, float diry, float dirz, float dist) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, new Formula(ray));
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, new Formula(obj));
        setFormulaWithBrickField(Brick.BrickField.VALUE_3, new Formula(Float.valueOf(ofx)));
        setFormulaWithBrickField(Brick.BrickField.VALUE_4, new Formula(Float.valueOf(ofy)));
        setFormulaWithBrickField(Brick.BrickField.VALUE_5, new Formula(Float.valueOf(ofz)));
        setFormulaWithBrickField(Brick.BrickField.VALUE_6, new Formula(Float.valueOf(dirx)));
        setFormulaWithBrickField(Brick.BrickField.VALUE_7, new Formula(Float.valueOf(diry)));
        setFormulaWithBrickField(Brick.BrickField.VALUE_8, new Formula(Float.valueOf(dirz)));
        setFormulaWithBrickField(Brick.BrickField.VALUE_9, new Formula(Float.valueOf(dist)));
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createAttachRaySensorAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1), getFormulaWithBrickField(Brick.BrickField.VALUE_2), getFormulaWithBrickField(Brick.BrickField.VALUE_3), getFormulaWithBrickField(Brick.BrickField.VALUE_4), getFormulaWithBrickField(Brick.BrickField.VALUE_5), getFormulaWithBrickField(Brick.BrickField.VALUE_6), getFormulaWithBrickField(Brick.BrickField.VALUE_7), getFormulaWithBrickField(Brick.BrickField.VALUE_8), getFormulaWithBrickField(Brick.BrickField.VALUE_9)));
    }
}
