package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/StringToTableBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class StringToTableBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_string_to_table;
    }

    public StringToTableBrick() {
        addAllowedBrickField(Brick.BrickField.STRING, R.id.brick_string_to_table_edit_str);
        addAllowedBrickField(Brick.BrickField.X_POSITION, R.id.brick_string_to_table_edit_x);
        addAllowedBrickField(Brick.BrickField.Y_POSITION, R.id.brick_string_to_table_edit_y);
        addAllowedBrickField(Brick.BrickField.TABLE_NAME, R.id.brick_string_to_table_edit_name);
    }

    public StringToTableBrick(String value, String x, String y, String name2) {
        this(new Formula(value), new Formula(x), new Formula(y), new Formula(name2));
    }

    public StringToTableBrick(Formula formula, Formula x, Formula y, Formula name2) {
        this();
        setFormulaWithBrickField(Brick.BrickField.STRING, formula);
        setFormulaWithBrickField(Brick.BrickField.X_POSITION, x);
        setFormulaWithBrickField(Brick.BrickField.Y_POSITION, y);
        setFormulaWithBrickField(Brick.BrickField.TABLE_NAME, name2);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createStringToTableAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.STRING), getFormulaWithBrickField(Brick.BrickField.X_POSITION), getFormulaWithBrickField(Brick.BrickField.Y_POSITION), getFormulaWithBrickField(Brick.BrickField.TABLE_NAME)));
    }
}
