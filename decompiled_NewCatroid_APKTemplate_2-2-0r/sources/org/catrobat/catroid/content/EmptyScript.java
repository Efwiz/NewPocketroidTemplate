package org.catrobat.catroid.content;

import org.catrobat.catroid.content.bricks.EmptyEventBrick;
import org.catrobat.catroid.content.bricks.ScriptBrick;
import org.catrobat.catroid.content.eventids.EventId;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/EmptyScript.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class EmptyScript extends Script {
    @Override // org.catrobat.catroid.content.Script
    public EventId createEventId(Sprite sprite) {
        return null;
    }

    @Override // org.catrobat.catroid.content.Script
    public ScriptBrick getScriptBrick() {
        if (this.scriptBrick == null) {
            this.scriptBrick = new EmptyEventBrick(this);
        }
        return this.scriptBrick;
    }
}
