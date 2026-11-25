package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/ReadVariableFromDeviceBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ReadVariableFromDeviceBrick extends UserVariableBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.UserVariableBrick
    protected int getSpinnerId() {
        return R.id.read_variable_from_device_spinner;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_read_variable_from_device;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        if (this.userVariable == null || this.userVariable.getName() == null) {
            return;
        }
        sequence.addAction(sprite.getActionFactory().createReadVariableFromDeviceAction(this.userVariable));
    }
}
