package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/CameraSettingsBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class CameraSettingsBrick extends FormulaBrick {
    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_camera_settings;
    }

    public CameraSettingsBrick() {
        addAllowedBrickField(Brick.BrickField.FOV, R.id.camera_fov_edit);
        addAllowedBrickField(Brick.BrickField.INTENSITY, R.id.camera_shake_int_edit);
        addAllowedBrickField(Brick.BrickField.DURATION, R.id.camera_shake_dur_edit);
    }

    public CameraSettingsBrick(float fov, float shakeInt, float shakeDur) {
        this(new Formula(Float.valueOf(fov)), new Formula(Float.valueOf(shakeInt)), new Formula(Float.valueOf(shakeDur)));
    }

    public CameraSettingsBrick(Formula fov, Formula shakeInt, Formula shakeDur) {
        this();
        setFormulaWithBrickField(Brick.BrickField.FOV, fov);
        setFormulaWithBrickField(Brick.BrickField.INTENSITY, shakeInt);
        setFormulaWithBrickField(Brick.BrickField.DURATION, shakeDur);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createCameraSettingsAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.FOV), getFormulaWithBrickField(Brick.BrickField.INTENSITY), getFormulaWithBrickField(Brick.BrickField.DURATION)));
    }
}
