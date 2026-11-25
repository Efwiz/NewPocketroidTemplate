package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/AttachToCameraBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class AttachToCameraBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_attach_to_camera;
    }

    public AttachToCameraBrick() {
        addAllowedBrickField(Brick.BrickField.NAME, R.id.brick_attach_camera_object_name);
    }

    public AttachToCameraBrick(String objectName) {
        this(new Formula(objectName));
    }

    public AttachToCameraBrick(Formula objectName) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NAME, objectName);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createAttachToCameraAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NAME)));
    }
}
