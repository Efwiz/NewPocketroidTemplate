package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/DeleteTableBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class DeleteTableBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_delete_table;
    }

    public DeleteTableBrick() {
        addAllowedBrickField(Brick.BrickField.TABLE_NAME, R.id.brick_delete_table_edit_name);
    }

    public DeleteTableBrick(String value) {
        this(new Formula(value));
    }

    public DeleteTableBrick(Formula formula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.TABLE_NAME, formula);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createDeleteTableAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.TABLE_NAME)));
    }
}
