package org.catrobat.catroid.content;

import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.bricks.ScriptBrick;
import org.catrobat.catroid.content.bricks.WhenBounceOffBrick;
import org.catrobat.catroid.content.eventids.BounceOffEventId;
import org.catrobat.catroid.content.eventids.EventId;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/WhenBounceOffScript.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class WhenBounceOffScript extends Script {
    private static final long serialVersionUID = 1;
    private transient Sprite spriteToBounceOff;
    private String spriteToBounceOffName;

    public WhenBounceOffScript() {
        this.spriteToBounceOffName = "";
    }

    public WhenBounceOffScript(String spriteToBounceOffName) {
        this.spriteToBounceOffName = "";
        if (spriteToBounceOffName == null) {
            this.spriteToBounceOffName = "";
        } else {
            this.spriteToBounceOffName = spriteToBounceOffName;
        }
    }

    @Override // org.catrobat.catroid.content.Script
    public ScriptBrick getScriptBrick() {
        if (this.scriptBrick == null) {
            this.scriptBrick = new WhenBounceOffBrick(this);
        }
        return this.scriptBrick;
    }

    public String getSpriteToBounceOffName() {
        if (this.spriteToBounceOffName.isEmpty()) {
            return null;
        }
        return this.spriteToBounceOffName;
    }

    public void setSpriteToBounceOffName(String spriteToCollideWithName) {
        if (spriteToCollideWithName == null) {
            this.spriteToBounceOffName = "";
        } else {
            this.spriteToBounceOffName = spriteToCollideWithName;
        }
        updateSpriteToCollideWith(ProjectManager.getInstance().getCurrentlyEditedScene());
    }

    public void updateSpriteToCollideWith(Scene scene) {
        if (this.spriteToBounceOffName.isEmpty()) {
            return;
        }
        Sprite sprite = scene.getSprite(this.spriteToBounceOffName);
        this.spriteToBounceOff = sprite;
        if (sprite == null) {
            this.spriteToBounceOffName = "";
        }
    }

    @Override // org.catrobat.catroid.content.Script
    public EventId createEventId(Sprite sprite) {
        return new BounceOffEventId(sprite, this.spriteToBounceOff);
    }
}
