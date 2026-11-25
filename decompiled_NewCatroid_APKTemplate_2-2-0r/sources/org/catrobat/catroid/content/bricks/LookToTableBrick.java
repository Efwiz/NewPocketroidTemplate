package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/LookToTableBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class LookToTableBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_look_to_table;
    }

    public LookToTableBrick() {
        addAllowedBrickField(Brick.BrickField.RED, R.id.brick_look_to_table_edit_r);
        addAllowedBrickField(Brick.BrickField.GREEN, R.id.brick_look_to_table_edit_g);
        addAllowedBrickField(Brick.BrickField.BLUE, R.id.brick_look_to_table_edit_b);
        addAllowedBrickField(Brick.BrickField.ALPHA, R.id.brick_look_to_table_edit_a);
    }

    public LookToTableBrick(String r, String g, String b, String a) {
        this(new Formula(r), new Formula(g), new Formula(b), new Formula(a));
    }

    public LookToTableBrick(Formula r, Formula g, Formula b, Formula a) {
        this();
        setFormulaWithBrickField(Brick.BrickField.RED, r);
        setFormulaWithBrickField(Brick.BrickField.GREEN, g);
        setFormulaWithBrickField(Brick.BrickField.BLUE, b);
        setFormulaWithBrickField(Brick.BrickField.ALPHA, a);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createLookToTableAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.RED), getFormulaWithBrickField(Brick.BrickField.GREEN), getFormulaWithBrickField(Brick.BrickField.BLUE), getFormulaWithBrickField(Brick.BrickField.ALPHA)));
    }
}
