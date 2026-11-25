package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserList;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/DeleteItemOfUserListBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class DeleteItemOfUserListBrick extends UserListBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.UserListBrick
    protected int getSpinnerId() {
        return R.id.delete_item_of_userlist_spinner;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_delete_item_of_userlist;
    }

    public DeleteItemOfUserListBrick() {
        addAllowedBrickField(Brick.BrickField.LIST_DELETE_ITEM, R.id.brick_delete_item_of_userlist_edit_text);
    }

    public DeleteItemOfUserListBrick(Integer item) {
        this(new Formula(item));
    }

    public DeleteItemOfUserListBrick(Formula formula, UserList userList) {
        this(formula);
        this.userList = userList;
    }

    public DeleteItemOfUserListBrick(Formula formula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.LIST_DELETE_ITEM, formula);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createDeleteItemOfUserListAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.LIST_DELETE_ITEM), this.userList));
    }
}
