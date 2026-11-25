package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/AssertUserListsBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class AssertUserListsBrick extends UserDataBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_assert_userlists;
    }

    public AssertUserListsBrick() {
        addAllowedBrickData(Brick.BrickData.ASSERT_LISTS_ACTUAL, R.id.brick_assert_lists_actual);
        addAllowedBrickData(Brick.BrickData.ASSERT_LISTS_EXPECTED, R.id.brick_assert_lists_expected);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createAssertUserListsAction(sprite, sequence, getUserListWithBrickData(Brick.BrickData.ASSERT_LISTS_ACTUAL), getUserListWithBrickData(Brick.BrickData.ASSERT_LISTS_EXPECTED), getPositionInformation()));
    }
}
