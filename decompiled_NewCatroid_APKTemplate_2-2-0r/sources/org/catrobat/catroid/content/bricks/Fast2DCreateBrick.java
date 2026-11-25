package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/Fast2DCreateBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class Fast2DCreateBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_fast2d_create;
    }

    public Fast2DCreateBrick() {
        addAllowedBrickField(Brick.BrickField.NAME, R.id.brick_fast2d_create_edit_id);
    }

    public Fast2DCreateBrick(String entityId) {
        this(new Formula(entityId));
    }

    public Fast2DCreateBrick(Formula entityIdFormula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NAME, entityIdFormula);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createFast2DCreateAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NAME)));
    }
}
