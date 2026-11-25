package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserList;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/ReplaceItemInUserListBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ReplaceItemInUserListBrick extends UserListBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.UserListBrick
    protected int getSpinnerId() {
        return R.id.replace_item_in_userlist_spinner;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_replace_item_in_userlist;
    }

    public ReplaceItemInUserListBrick() {
        addAllowedBrickField(Brick.BrickField.REPLACE_ITEM_IN_USERLIST_VALUE, R.id.brick_replace_item_in_userlist_value_edit_text);
        addAllowedBrickField(Brick.BrickField.REPLACE_ITEM_IN_USERLIST_INDEX, R.id.brick_replace_item_in_userlist_at_index_edit_text);
    }

    public ReplaceItemInUserListBrick(double value, Integer indexToReplace) {
        this(new Formula(Double.valueOf(value)), new Formula(indexToReplace));
    }

    public ReplaceItemInUserListBrick(Formula valueFormula, Formula indexFormula, UserList userList) {
        this(valueFormula, indexFormula);
        this.userList = userList;
    }

    public ReplaceItemInUserListBrick(Formula valueFormula, Formula indexFormula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.REPLACE_ITEM_IN_USERLIST_VALUE, valueFormula);
        setFormulaWithBrickField(Brick.BrickField.REPLACE_ITEM_IN_USERLIST_INDEX, indexFormula);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick
    public Brick.BrickField getDefaultBrickField() {
        return Brick.BrickField.REPLACE_ITEM_IN_USERLIST_INDEX;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createReplaceItemInUserListAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.REPLACE_ITEM_IN_USERLIST_INDEX), getFormulaWithBrickField(Brick.BrickField.REPLACE_ITEM_IN_USERLIST_VALUE), this.userList));
    }
}
