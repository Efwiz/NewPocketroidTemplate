package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserList;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/InsertItemIntoUserListBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class InsertItemIntoUserListBrick extends UserListBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.UserListBrick
    protected int getSpinnerId() {
        return R.id.insert_item_into_userlist_spinner;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_insert_item_into_userlist;
    }

    public InsertItemIntoUserListBrick() {
        addAllowedBrickField(Brick.BrickField.INSERT_ITEM_INTO_USERLIST_VALUE, R.id.brick_insert_item_into_userlist_value_edit_text);
        addAllowedBrickField(Brick.BrickField.INSERT_ITEM_INTO_USERLIST_INDEX, R.id.brick_insert_item_into_userlist_at_index_edit_text);
    }

    public InsertItemIntoUserListBrick(Formula userListFormulaValueToInsert, Formula userListFormulaIndexToInsert, UserList userList) {
        this(userListFormulaValueToInsert, userListFormulaIndexToInsert);
        this.userList = userList;
    }

    public InsertItemIntoUserListBrick(double value, Integer indexToInsert) {
        this(new Formula(Double.valueOf(value)), new Formula(indexToInsert));
    }

    public InsertItemIntoUserListBrick(Formula userListFormulaValueToInsert, Formula userListFormulaIndexToInsert) {
        this();
        setFormulaWithBrickField(Brick.BrickField.INSERT_ITEM_INTO_USERLIST_VALUE, userListFormulaValueToInsert);
        setFormulaWithBrickField(Brick.BrickField.INSERT_ITEM_INTO_USERLIST_INDEX, userListFormulaIndexToInsert);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick
    public Brick.BrickField getDefaultBrickField() {
        return Brick.BrickField.INSERT_ITEM_INTO_USERLIST_VALUE;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createInsertItemIntoUserListAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.INSERT_ITEM_INTO_USERLIST_INDEX), getFormulaWithBrickField(Brick.BrickField.INSERT_ITEM_INTO_USERLIST_VALUE), this.userList));
    }
}
