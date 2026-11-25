package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.WhenMouseWheelScrolledScript;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/WhenMouseWheelScrolledBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class WhenMouseWheelScrolledBrick extends ScriptBrickBaseType {
    private static final long serialVersionUID = 1;
    private WhenMouseWheelScrolledScript script;

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_when_mouse_wheel_scrolled;
    }

    public WhenMouseWheelScrolledBrick() {
        this(new WhenMouseWheelScrolledScript());
    }

    public WhenMouseWheelScrolledBrick(WhenMouseWheelScrolledScript script) {
        this.script = script;
        script.setScriptBrick(this);
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public Script getScript() {
        return this.script;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public Brick clone() throws CloneNotSupportedException {
        WhenMouseWheelScrolledBrick whenMouseWheelScrolledBrick = (WhenMouseWheelScrolledBrick) super.clone();
        WhenMouseWheelScrolledScript whenMouseWheelScrolledScript = (WhenMouseWheelScrolledScript) this.script.clone();
        whenMouseWheelScrolledBrick.script = whenMouseWheelScrolledScript;
        whenMouseWheelScrolledScript.setScriptBrick(whenMouseWheelScrolledBrick);
        return whenMouseWheelScrolledBrick;
    }
}
