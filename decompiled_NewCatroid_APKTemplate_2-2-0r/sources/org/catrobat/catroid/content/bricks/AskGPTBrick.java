package org.catrobat.catroid.content.bricks;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/AskGPTBrick.class
  classes14.dex
 */
/* JADX INFO: compiled from: AskGPTBrick.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005B\u0017\b\u0012\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007¢\u0006\u0002\u0010\tB\u0005¢\u0006\u0002\u0010\nJ\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0010\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u0015H\u0014J\b\u0010\u0016\u001a\u00020\u0015H\u0016¨\u0006\u0017"}, d2 = {"Lorg/catrobat/catroid/content/bricks/AskGPTBrick;", "Lorg/catrobat/catroid/content/bricks/UserVariableBrickWithFormula;", "value", "", "value2", "(Ljava/lang/String;Ljava/lang/String;)V", "prompt", "Lorg/catrobat/catroid/formulaeditor/Formula;", "system", "(Lorg/catrobat/catroid/formulaeditor/Formula;Lorg/catrobat/catroid/formulaeditor/Formula;)V", "()V", "addActionToSequence", "", "sprite", "Lorg/catrobat/catroid/content/Sprite;", "sequence", "Lorg/catrobat/catroid/content/actions/ScriptSequenceAction;", "addRequiredResources", "requiredResourcesSet", "Lorg/catrobat/catroid/content/bricks/Brick$ResourcesSet;", "getSpinnerId", "", "getViewResource", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AskGPTBrick extends UserVariableBrickWithFormula {
    @Override // org.catrobat.catroid.content.bricks.UserVariableBrickWithFormula
    protected int getSpinnerId() {
        return R.id.brick_ask_gpt_spinner;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_ask_gpt;
    }

    public AskGPTBrick() {
        addAllowedBrickField(Brick.BrickField.TEXT, R.id.brick_ask_gpt_edit_prompt);
        addAllowedBrickField(Brick.BrickField.BODY, R.id.brick_ask_gpt_edit_system);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public AskGPTBrick(String value, String value2) {
        this(new Formula(value), new Formula(value2));
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(value2, "value2");
    }

    private AskGPTBrick(Formula formula, Formula formula2) {
        this();
        setFormulaWithBrickField(Brick.BrickField.TEXT, formula);
        setFormulaWithBrickField(Brick.BrickField.BODY, formula2);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        Intrinsics.checkNotNullParameter(sprite, "sprite");
        Intrinsics.checkNotNullParameter(sequence, "sequence");
        sequence.addAction(sprite.getActionFactory().createAskGPTAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.TEXT), getFormulaWithBrickField(Brick.BrickField.BODY), this.userVariable));
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public void addRequiredResources(Brick.ResourcesSet requiredResourcesSet) {
        Intrinsics.checkNotNullParameter(requiredResourcesSet, "requiredResourcesSet");
        requiredResourcesSet.add((Integer) 21);
        super.addRequiredResources(requiredResourcesSet);
    }
}
