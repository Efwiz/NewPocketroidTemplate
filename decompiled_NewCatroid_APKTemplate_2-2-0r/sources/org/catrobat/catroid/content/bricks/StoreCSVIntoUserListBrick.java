package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserList;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/StoreCSVIntoUserListBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class StoreCSVIntoUserListBrick extends UserListBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.UserListBrick
    protected int getSpinnerId() {
        return R.id.brick_store_csv_into_userlist_spinner;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_store_csv_into_userlist;
    }

    public StoreCSVIntoUserListBrick() {
        addAllowedBrickField(Brick.BrickField.STORE_CSV_INTO_USERLIST_COLUMN, R.id.brick_store_csv_into_userlist_column_edit_text);
        addAllowedBrickField(Brick.BrickField.STORE_CSV_INTO_USERLIST_CSV, R.id.brick_store_csv_into_userlist_csv_edit_text);
    }

    public StoreCSVIntoUserListBrick(Formula userListFormulaColumnToInsert, Formula userListFormulaCSVToInsert, UserList userList) {
        this(userListFormulaColumnToInsert, userListFormulaCSVToInsert);
        this.userList = userList;
    }

    public StoreCSVIntoUserListBrick(Integer column, String csv) {
        this(new Formula(column), new Formula(csv));
    }

    public StoreCSVIntoUserListBrick(Formula userListFormulaColumnToInsert, Formula userListFormulaCSVToInsert) {
        this();
        setFormulaWithBrickField(Brick.BrickField.STORE_CSV_INTO_USERLIST_COLUMN, userListFormulaColumnToInsert);
        setFormulaWithBrickField(Brick.BrickField.STORE_CSV_INTO_USERLIST_CSV, userListFormulaCSVToInsert);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick
    public Brick.BrickField getDefaultBrickField() {
        return Brick.BrickField.STORE_CSV_INTO_USERLIST_COLUMN;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createStoreCSVIntoUserListAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.STORE_CSV_INTO_USERLIST_COLUMN), getFormulaWithBrickField(Brick.BrickField.STORE_CSV_INTO_USERLIST_CSV), this.userList));
    }
}
