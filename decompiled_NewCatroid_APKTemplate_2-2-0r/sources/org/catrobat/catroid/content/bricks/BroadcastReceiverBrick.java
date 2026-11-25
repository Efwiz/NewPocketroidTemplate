package org.catrobat.catroid.content.bricks;

import java.util.Iterator;
import java.util.List;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.BroadcastScript;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/BroadcastReceiverBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class BroadcastReceiverBrick extends BroadcastMessageBrick implements ScriptBrick {
    private static final long serialVersionUID = 1;
    private BroadcastScript broadcastScript;

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public int getPositionInDragAndDropTargetList() {
        return -1;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public int getPositionInScript() {
        return -1;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_broadcast_receive;
    }

    public BroadcastReceiverBrick() {
        this.broadcastScript = new BroadcastScript();
    }

    public BroadcastReceiverBrick(BroadcastScript broadcastScript) {
        broadcastScript.setScriptBrick(this);
        this.commentedOut = broadcastScript.isCommentedOut();
        this.broadcastScript = broadcastScript;
    }

    @Override // org.catrobat.catroid.content.bricks.BroadcastMessageBrick
    public String getBroadcastMessage() {
        return this.broadcastScript.getBroadcastMessage();
    }

    @Override // org.catrobat.catroid.content.bricks.BroadcastMessageBrick
    public void setBroadcastMessage(String broadcastMessage) {
        this.broadcastScript.setBroadcastMessage(broadcastMessage);
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public Script getScript() {
        return this.broadcastScript;
    }

    @Override // org.catrobat.catroid.content.bricks.BroadcastMessageBrick, org.catrobat.catroid.content.bricks.BrickBaseType
    public Brick clone() throws CloneNotSupportedException {
        BroadcastReceiverBrick broadcastReceiverBrick = (BroadcastReceiverBrick) super.clone();
        BroadcastScript broadcastScript = (BroadcastScript) this.broadcastScript.clone();
        broadcastReceiverBrick.broadcastScript = broadcastScript;
        broadcastScript.setScriptBrick(broadcastReceiverBrick);
        return broadcastReceiverBrick;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void addToFlatList(List<Brick> bricks) {
        super.addToFlatList(bricks);
        Iterator<Brick> it = getScript().getBrickList().iterator();
        while (it.hasNext()) {
            it.next().addToFlatList(bricks);
        }
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public List<Brick> getDragAndDropTargetList() {
        return getScript().getBrickList();
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void setCommentedOut(boolean commentedOut) {
        super.setCommentedOut(commentedOut);
        getScript().setCommentedOut(commentedOut);
    }
}
