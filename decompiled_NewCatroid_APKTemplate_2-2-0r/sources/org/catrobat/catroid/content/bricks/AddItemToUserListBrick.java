package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/AddItemToUserListBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class AddItemToUserListBrick extends UserListBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.UserListBrick
    protected int getSpinnerId() {
        return R.id.add_item_to_userlist_spinner;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_add_item_to_userlist;
    }

    public AddItemToUserListBrick() {
        addAllowedBrickField(Brick.BrickField.LIST_ADD_ITEM, R.id.brick_add_item_to_userlist_edit_text);
    }

    public AddItemToUserListBrick(double value) {
        this(new Formula(Double.valueOf(value)));
    }

    public AddItemToUserListBrick(Formula formula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.LIST_ADD_ITEM, formula);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createAddItemToUserListAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.LIST_ADD_ITEM), this.userList));
    }
}
