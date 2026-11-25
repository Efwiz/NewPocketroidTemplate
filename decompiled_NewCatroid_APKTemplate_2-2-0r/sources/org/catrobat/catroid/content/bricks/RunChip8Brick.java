package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/RunChip8Brick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class RunChip8Brick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_run_chip8;
    }

    public RunChip8Brick() {
        addAllowedBrickField(Brick.BrickField.TEXT, R.id.brick_chip8_edit_file);
    }

    public RunChip8Brick(String filename) {
        this(new Formula(filename));
    }

    public RunChip8Brick(Formula filenameFormula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.TEXT, filenameFormula);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createRunChip8Action(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.TEXT)));
    }
}
