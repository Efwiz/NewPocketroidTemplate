package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.utils.ShowTextUtils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SayBubbleBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SayBubbleBrick extends ThinkBubbleBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.ThinkBubbleBrick, org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_say_bubble;
    }

    public SayBubbleBrick() {
    }

    public SayBubbleBrick(String text) {
        super(text);
    }

    @Override // org.catrobat.catroid.content.bricks.ThinkBubbleBrick, org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createThinkSayBubbleAction(sprite, sequence, new ShowTextUtils.AndroidStringProvider(CatroidApplication.getAppContext()), getFormulaWithBrickField(Brick.BrickField.STRING), 0));
    }
}
