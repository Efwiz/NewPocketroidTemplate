package org.catrobat.catroid.content;

import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.LookData;
import org.catrobat.catroid.content.bricks.ScriptBrick;
import org.catrobat.catroid.content.bricks.WhenBackgroundChangesBrick;
import org.catrobat.catroid.content.eventids.EventId;
import org.catrobat.catroid.content.eventids.SetLookEventId;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/WhenBackgroundChangesScript.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class WhenBackgroundChangesScript extends Script {
    private static final long serialVersionUID = 1;
    private LookData look;

    @Override // org.catrobat.catroid.content.Script
    public ScriptBrick getScriptBrick() {
        if (this.scriptBrick == null) {
            this.scriptBrick = new WhenBackgroundChangesBrick(this);
        }
        return this.scriptBrick;
    }

    public LookData getLook() {
        return this.look;
    }

    public void setLook(LookData look) {
        this.look = look;
    }

    @Override // org.catrobat.catroid.content.Script
    public EventId createEventId(Sprite sprite) {
        return new SetLookEventId(ProjectManager.getInstance().getCurrentlyPlayingScene().getBackgroundSprite(), this.look);
    }
}
