package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/BroadcastBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class BroadcastBrick extends BroadcastMessageBrick {
    private static final long serialVersionUID = 1;
    protected String broadcastMessage;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_broadcast;
    }

    public BroadcastBrick() {
        this.broadcastMessage = "";
    }

    public BroadcastBrick(String broadcastMessage) {
        this.broadcastMessage = broadcastMessage;
    }

    @Override // org.catrobat.catroid.content.bricks.BroadcastMessageBrick
    public String getBroadcastMessage() {
        return this.broadcastMessage;
    }

    @Override // org.catrobat.catroid.content.bricks.BroadcastMessageBrick
    public void setBroadcastMessage(String broadcastMessage) {
        this.broadcastMessage = broadcastMessage;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createBroadcastAction(this.broadcastMessage, false));
    }
}
