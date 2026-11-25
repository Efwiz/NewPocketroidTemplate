package org.catrobat.catroid.content;

import org.catrobat.catroid.content.bricks.ScriptBrick;
import org.catrobat.catroid.content.bricks.WhenMouseButtonClickedBrick;
import org.catrobat.catroid.content.eventids.EventId;
import org.catrobat.catroid.content.eventids.MouseButtonEventId;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/WhenMouseButtonClickedScript.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class WhenMouseButtonClickedScript extends Script {
    private static final long serialVersionUID = 1;
    private int buttonCode;

    public WhenMouseButtonClickedScript(int buttonCode) {
        this.buttonCode = buttonCode;
    }

    public int getButtonCode() {
        return this.buttonCode;
    }

    public void setButtonCode(int buttonCode) {
        this.buttonCode = buttonCode;
    }

    @Override // org.catrobat.catroid.content.Script
    public ScriptBrick getScriptBrick() {
        if (this.scriptBrick == null) {
            this.scriptBrick = new WhenMouseButtonClickedBrick(this);
        }
        return this.scriptBrick;
    }

    @Override // org.catrobat.catroid.content.Script
    public EventId createEventId(Sprite sprite) {
        return new MouseButtonEventId(this.buttonCode);
    }
}
