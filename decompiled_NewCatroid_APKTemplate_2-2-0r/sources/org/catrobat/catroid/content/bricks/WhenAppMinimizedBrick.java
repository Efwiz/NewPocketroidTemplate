package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.WhenAppMinimizedScript;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/WhenAppMinimizedBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class WhenAppMinimizedBrick extends ScriptBrickBaseType {
    private static final long serialVersionUID = 1;
    private WhenAppMinimizedScript script;

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_when_app_minimized;
    }

    public WhenAppMinimizedBrick() {
        this(new WhenAppMinimizedScript());
    }

    public WhenAppMinimizedBrick(WhenAppMinimizedScript script) {
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
        WhenAppMinimizedBrick whenAppMinimizedBrick = (WhenAppMinimizedBrick) super.clone();
        WhenAppMinimizedScript whenAppMinimizedScript = (WhenAppMinimizedScript) this.script.clone();
        whenAppMinimizedBrick.script = whenAppMinimizedScript;
        whenAppMinimizedScript.setScriptBrick(whenAppMinimizedBrick);
        return whenAppMinimizedBrick;
    }
}
