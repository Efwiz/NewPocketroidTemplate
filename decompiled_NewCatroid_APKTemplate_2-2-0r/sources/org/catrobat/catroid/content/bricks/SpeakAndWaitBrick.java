package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SpeakAndWaitBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SpeakAndWaitBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_speak_and_wait;
    }

    public SpeakAndWaitBrick() {
        addAllowedBrickField(Brick.BrickField.SPEAK, R.id.brick_speak_and_wait_edit_text);
    }

    public SpeakAndWaitBrick(String text) {
        this(new Formula(text));
    }

    public SpeakAndWaitBrick(Formula formula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.SPEAK, formula);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void addRequiredResources(final Brick.ResourcesSet requiredResourcesSet) {
        requiredResourcesSet.add((Integer) 1);
        super.addRequiredResources(requiredResourcesSet);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSpeakAndWaitAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.SPEAK)));
    }
}
