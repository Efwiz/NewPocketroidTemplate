package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/AssertEqualsBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class AssertEqualsBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_assert_equals;
    }

    public AssertEqualsBrick() {
        addAllowedBrickField(Brick.BrickField.ASSERT_EQUALS_ACTUAL, R.id.brick_assert_actual);
        addAllowedBrickField(Brick.BrickField.ASSERT_EQUALS_EXPECTED, R.id.brick_assert_expected);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick
    public Brick.BrickField getDefaultBrickField() {
        return Brick.BrickField.ASSERT_EQUALS_ACTUAL;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createAssertEqualsAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.ASSERT_EQUALS_ACTUAL), getFormulaWithBrickField(Brick.BrickField.ASSERT_EQUALS_EXPECTED), getPositionInformation()));
    }
}
