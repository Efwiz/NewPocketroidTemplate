package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.ExitProjectScript;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/WhenProjectExitsBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class WhenProjectExitsBrick extends ScriptBrickBaseType {
    private static final long serialVersionUID = 1;
    private ExitProjectScript script;

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_when_project_exits;
    }

    public WhenProjectExitsBrick() {
        this(new ExitProjectScript());
    }

    public WhenProjectExitsBrick(ExitProjectScript script) {
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
        WhenProjectExitsBrick whenProjectExitsBrick = (WhenProjectExitsBrick) super.clone();
        ExitProjectScript exitProjectScript = (ExitProjectScript) this.script.clone();
        whenProjectExitsBrick.script = exitProjectScript;
        exitProjectScript.setScriptBrick(whenProjectExitsBrick);
        return whenProjectExitsBrick;
    }
}
