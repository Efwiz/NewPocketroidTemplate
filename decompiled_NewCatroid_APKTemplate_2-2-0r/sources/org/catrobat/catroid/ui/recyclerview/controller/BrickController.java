package org.catrobat.catroid.ui.recyclerview.controller;

import android.util.Log;
import java.util.List;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.ScriptBrick;
import org.catrobat.catroid.content.bricks.UserDefinedReceiverBrick;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/controller/BrickController.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class BrickController {
    private static final String TAG = "BrickController";

    public void copy(List<Brick> bricksToCopy, Sprite parent) {
        for (Brick brick : bricksToCopy) {
            Script script = brick.getScript();
            if (brick instanceof ScriptBrick) {
                try {
                    parent.addScript(script.clone());
                } catch (CloneNotSupportedException e) {
                    Log.e(TAG, Log.getStackTraceString(e));
                }
            } else {
                try {
                    if (!bricksToCopy.contains(brick.getParent())) {
                        script.addBrick(brick.clone());
                    }
                } catch (CloneNotSupportedException e2) {
                    Log.e(TAG, Log.getStackTraceString(e2));
                }
            }
        }
    }

    public void delete(List<Brick> bricksToDelete, Sprite parent) {
        for (Brick brick : bricksToDelete) {
            Script script = brick.getScript();
            if (brick instanceof UserDefinedReceiverBrick) {
                parent.removeUserDefinedBrick(((UserDefinedReceiverBrick) brick).getUserDefinedBrick());
            }
            if (brick instanceof ScriptBrick) {
                parent.removeScript(script);
            } else if (!bricksToDelete.contains(brick.getParent())) {
                script.removeBrick(brick);
            }
        }
    }
}
