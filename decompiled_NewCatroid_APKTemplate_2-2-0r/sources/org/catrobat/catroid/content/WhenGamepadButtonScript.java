package org.catrobat.catroid.content;

import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.ScriptBrick;
import org.catrobat.catroid.content.bricks.WhenGamepadButtonBrick;
import org.catrobat.catroid.content.eventids.EventId;
import org.catrobat.catroid.content.eventids.GamepadEventId;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/WhenGamepadButtonScript.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class WhenGamepadButtonScript extends Script {
    private static final long serialVersionUID = 1;
    private String action;

    public WhenGamepadButtonScript() {
    }

    public WhenGamepadButtonScript(String action) {
        this.action = action;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override // org.catrobat.catroid.content.Script
    public ScriptBrick getScriptBrick() {
        if (this.scriptBrick == null) {
            this.scriptBrick = new WhenGamepadButtonBrick(this);
        }
        return this.scriptBrick;
    }

    @Override // org.catrobat.catroid.content.Script
    public void addRequiredResources(final Brick.ResourcesSet resourcesSet) {
        resourcesSet.add((Integer) 22);
        super.addRequiredResources(resourcesSet);
    }

    @Override // org.catrobat.catroid.content.Script
    public EventId createEventId(Sprite sprite) {
        return new GamepadEventId(this.action);
    }
}
