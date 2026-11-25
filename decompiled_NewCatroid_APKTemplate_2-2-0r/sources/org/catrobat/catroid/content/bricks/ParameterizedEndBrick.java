package org.catrobat.catroid.content.bricks;

import java.util.List;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserList;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/ParameterizedEndBrick.class
  classes14.dex
 */
/* JADX INFO: compiled from: ParameterizedEndBrick.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0005¢\u0006\u0002\u0010\u0005J\u0018\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\f\u001a\u00020\rH\u0016J\u000e\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\u000e\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\u0016J\b\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0015H\u0014J\b\u0010\u0017\u001a\u00020\u0015H\u0016¨\u0006\u0018"}, d2 = {"Lorg/catrobat/catroid/content/bricks/ParameterizedEndBrick;", "Lorg/catrobat/catroid/content/bricks/UserListBrick;", "parent", "Lorg/catrobat/catroid/content/bricks/ParameterizedBrick;", "(Lorg/catrobat/catroid/content/bricks/ParameterizedBrick;)V", "()V", "addActionToSequence", "", "sprite", "Lorg/catrobat/catroid/content/Sprite;", "sequence", "Lorg/catrobat/catroid/content/actions/ScriptSequenceAction;", "consistsOfMultipleParts", "", "getAllParts", "", "Lorg/catrobat/catroid/content/bricks/Brick;", "getBrickID", "Ljava/util/UUID;", "getDragAndDropTargetList", "getPositionInDragAndDropTargetList", "", "getSpinnerId", "getViewResource", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ParameterizedEndBrick extends UserListBrick {
    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public boolean consistsOfMultipleParts() {
        return true;
    }

    @Override // org.catrobat.catroid.content.bricks.UserListBrick
    protected int getSpinnerId() {
        return R.id.brick_param_expected_list;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_parameterized_assert;
    }

    public ParameterizedEndBrick() {
        addAllowedBrickField(Brick.BrickField.ASSERT_LOOP_ACTUAL, R.id.brick_param_assert_text);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public ParameterizedEndBrick(ParameterizedBrick parent) {
        this();
        Intrinsics.checkNotNullParameter(parent, "parent");
        this.parent = parent;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public List<Brick> getAllParts() {
        List<Brick> allParts = this.parent.getAllParts();
        Intrinsics.checkNotNullExpressionValue(allParts, "getAllParts(...)");
        return allParts;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public List<Brick> getDragAndDropTargetList() {
        List<Brick> dragAndDropTargetList = this.parent.getParent().getDragAndDropTargetList();
        Intrinsics.checkNotNullExpressionValue(dragAndDropTargetList, "getDragAndDropTargetList(...)");
        return dragAndDropTargetList;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public int getPositionInDragAndDropTargetList() {
        return this.parent.getParent().getDragAndDropTargetList().indexOf(this.parent);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        Intrinsics.checkNotNullParameter(sprite, "sprite");
        Intrinsics.checkNotNullParameter(sequence, "sequence");
        Formula formulaWithBrickField = getFormulaWithBrickField(Brick.BrickField.ASSERT_LOOP_ACTUAL);
        UserList userList = this.userList;
        Brick brick = this.parent;
        Intrinsics.checkNotNull(brick, "null cannot be cast to non-null type org.catrobat.catroid.content.bricks.ParameterizedBrick");
        sequence.addAction(sprite.getActionFactory().createParameterizedAssertAction(sprite, sequence, formulaWithBrickField, userList, ((ParameterizedBrick) brick).getParameterizedData(), getPositionInformation()));
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public UUID getBrickID() {
        UUID brickID = this.parent.getBrickID();
        Intrinsics.checkNotNullExpressionValue(brickID, "getBrickID(...)");
        return brickID;
    }
}
