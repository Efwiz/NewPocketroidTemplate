package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/DeleteVarBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class DeleteVarBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_delete_var;
    }

    public DeleteVarBrick() {
        addAllowedBrickField(Brick.BrickField.VARNAME, R.id.brick_delete_var_edit_text);
    }

    public DeleteVarBrick(String value) {
        this(new Formula(value));
    }

    public DeleteVarBrick(Formula formula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VARNAME, formula);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createDeleteVarAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VARNAME)));
    }
}
