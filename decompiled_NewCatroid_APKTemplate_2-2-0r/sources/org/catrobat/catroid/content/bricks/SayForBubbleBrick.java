package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.utils.ShowTextUtils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SayForBubbleBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SayForBubbleBrick extends ThinkForBubbleBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.ThinkForBubbleBrick, org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_say_for_bubble;
    }

    public SayForBubbleBrick() {
    }

    public SayForBubbleBrick(String text, float duration) {
        super(text, duration);
    }

    @Override // org.catrobat.catroid.content.bricks.ThinkForBubbleBrick, org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createThinkSayForBubbleAction(sprite, sequence, new ShowTextUtils.AndroidStringProvider(CatroidApplication.getAppContext()), getFormulaWithBrickField(Brick.BrickField.STRING), 0));
        sequence.addAction(sprite.getActionFactory().createWaitForBubbleBrickAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.DURATION_IN_SECONDS)));
    }
}
