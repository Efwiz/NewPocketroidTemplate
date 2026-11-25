package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetCameraZoomBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetCameraZoomBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_camera_zoom;
    }

    public SetCameraZoomBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE, R.id.brick_set_camera_zoom_value);
    }

    public SetCameraZoomBrick(double zoom) {
        this(new Formula(Double.valueOf(zoom)));
    }

    public SetCameraZoomBrick(Formula zoom) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE, zoom);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetCameraZoomAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE)));
    }
}
