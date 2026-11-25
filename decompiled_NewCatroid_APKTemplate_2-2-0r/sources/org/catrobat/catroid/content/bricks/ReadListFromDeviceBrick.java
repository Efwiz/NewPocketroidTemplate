package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/ReadListFromDeviceBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ReadListFromDeviceBrick extends UserListBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.UserListBrick
    protected int getSpinnerId() {
        return R.id.read_list_from_device_spinner;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_read_list_from_device;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        if (this.userList == null || this.userList.getName() == null) {
            return;
        }
        sequence.addAction(sprite.getActionFactory().createReadListFromDeviceAction(this.userList));
    }
}
