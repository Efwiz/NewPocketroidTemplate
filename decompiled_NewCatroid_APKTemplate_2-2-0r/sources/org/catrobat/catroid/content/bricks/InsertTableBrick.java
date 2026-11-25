package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/InsertTableBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class InsertTableBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_insert_table;
    }

    public InsertTableBrick() {
        addAllowedBrickField(Brick.BrickField.TABLE_NAME, R.id.brick_insert_table_edit_name);
        addAllowedBrickField(Brick.BrickField.VALUE, R.id.brick_insert_table_edit_value);
        addAllowedBrickField(Brick.BrickField.SIZE_X, R.id.brick_insert_table_edit_x);
        addAllowedBrickField(Brick.BrickField.SIZE_Y, R.id.brick_insert_table_edit_y);
    }

    public InsertTableBrick(String value, String value2, Integer value3, Integer value4) {
        this(new Formula(value), new Formula(value2), new Formula(value3), new Formula(value4));
    }

    public InsertTableBrick(Formula formula, Formula formula2, Formula formula3, Formula formula4) {
        this();
        setFormulaWithBrickField(Brick.BrickField.TABLE_NAME, formula);
        setFormulaWithBrickField(Brick.BrickField.VALUE, formula2);
        setFormulaWithBrickField(Brick.BrickField.SIZE_X, formula3);
        setFormulaWithBrickField(Brick.BrickField.SIZE_Y, formula4);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createInsertTableAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.TABLE_NAME), getFormulaWithBrickField(Brick.BrickField.VALUE), getFormulaWithBrickField(Brick.BrickField.SIZE_X), getFormulaWithBrickField(Brick.BrickField.SIZE_Y)));
    }
}
