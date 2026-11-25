package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.AfterUpdateScript;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/WhenAfterUpdateBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class WhenAfterUpdateBrick extends ScriptBrickBaseType {
    private static final long serialVersionUID = 1;
    private AfterUpdateScript script;

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_when_after_update;
    }

    public WhenAfterUpdateBrick() {
        this(new AfterUpdateScript());
    }

    public WhenAfterUpdateBrick(AfterUpdateScript script) {
        script.setScriptBrick(this);
        this.commentedOut = script.isCommentedOut();
        this.script = script;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public Script getScript() {
        return this.script;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public Brick clone() throws CloneNotSupportedException {
        WhenAfterUpdateBrick whenAfterUpdateBrick = (WhenAfterUpdateBrick) super.clone();
        AfterUpdateScript afterUpdateScript = (AfterUpdateScript) this.script.clone();
        whenAfterUpdateBrick.script = afterUpdateScript;
        afterUpdateScript.setScriptBrick(whenAfterUpdateBrick);
        return whenAfterUpdateBrick;
    }
}
