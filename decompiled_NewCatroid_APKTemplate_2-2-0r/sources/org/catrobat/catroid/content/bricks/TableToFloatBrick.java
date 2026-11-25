package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/TableToFloatBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class TableToFloatBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_table_to_float;
    }

    public TableToFloatBrick() {
        addAllowedBrickField(Brick.BrickField.TABLE_NAME, R.id.brick_table_to_float_table);
        addAllowedBrickField(Brick.BrickField.FLOAT_ARRAY, R.id.brick_table_to_float_float);
    }

    public TableToFloatBrick(String value, String val2) {
        this(new Formula(value), new Formula(val2));
    }

    public TableToFloatBrick(Formula formula, Formula form2) {
        this();
        setFormulaWithBrickField(Brick.BrickField.TABLE_NAME, formula);
        setFormulaWithBrickField(Brick.BrickField.FLOAT_ARRAY, form2);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createTableToFloat(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.TABLE_NAME), getFormulaWithBrickField(Brick.BrickField.FLOAT_ARRAY)));
    }
}
