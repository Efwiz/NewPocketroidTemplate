package org.catrobat.catroid.content;

import org.catrobat.catroid.content.bricks.BroadcastReceiverBrick;
import org.catrobat.catroid.content.bricks.ScriptBrick;
import org.catrobat.catroid.content.eventids.BroadcastEventId;
import org.catrobat.catroid.content.eventids.EventId;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/BroadcastScript.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class BroadcastScript extends Script {
    private static final long serialVersionUID = 1;
    private String receivedMessage;

    public BroadcastScript() {
    }

    public BroadcastScript(String receivedMessage) {
        this.receivedMessage = receivedMessage;
    }

    @Override // org.catrobat.catroid.content.Script
    public ScriptBrick getScriptBrick() {
        if (this.scriptBrick == null) {
            this.scriptBrick = new BroadcastReceiverBrick(this);
        }
        return this.scriptBrick;
    }

    public String getBroadcastMessage() {
        return this.receivedMessage;
    }

    public void setBroadcastMessage(String broadcastMessage) {
        this.receivedMessage = broadcastMessage;
    }

    @Override // org.catrobat.catroid.content.Script
    public EventId createEventId(Sprite sprite) {
        return new BroadcastEventId(this.receivedMessage);
    }
}
