package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserVariable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/JavaCompileBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class JavaCompileBrick extends UserVariableBrickWithFormula {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.UserVariableBrickWithFormula
    protected int getSpinnerId() {
        return R.id.java_compile_error_spinner;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_java_compile;
    }

    public JavaCompileBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_java_compile_src_field);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_java_compile_dest_field);
    }

    public JavaCompileBrick(Formula srcFolder, Formula destDexFile, UserVariable errorVariable) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, srcFolder);
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, destDexFile);
        this.userVariable = errorVariable;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createCompileJavaToDexAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1), getFormulaWithBrickField(Brick.BrickField.VALUE_2), this.userVariable));
    }
}
