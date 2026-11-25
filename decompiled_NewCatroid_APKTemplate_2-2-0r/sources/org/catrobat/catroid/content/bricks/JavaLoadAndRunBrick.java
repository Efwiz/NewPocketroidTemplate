package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/JavaLoadAndRunBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class JavaLoadAndRunBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_java_load_and_run;
    }

    public JavaLoadAndRunBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_java_load_path_field);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_java_load_class_field);
        addAllowedBrickField(Brick.BrickField.VALUE_3, R.id.brick_java_load_method_field);
        addAllowedBrickField(Brick.BrickField.VALUE_4, R.id.brick_java_load_arg_field);
    }

    public JavaLoadAndRunBrick(String dexPath, String className, String methodName, String methodArg) {
        this(new Formula(dexPath), new Formula(className), new Formula(methodName), new Formula(methodArg));
    }

    public JavaLoadAndRunBrick(Formula dexPath, Formula className, Formula methodName, Formula methodArg) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, dexPath);
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, className);
        setFormulaWithBrickField(Brick.BrickField.VALUE_3, methodName);
        setFormulaWithBrickField(Brick.BrickField.VALUE_4, methodArg);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createLoadAndRunDexAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1), getFormulaWithBrickField(Brick.BrickField.VALUE_2), getFormulaWithBrickField(Brick.BrickField.VALUE_3), getFormulaWithBrickField(Brick.BrickField.VALUE_4)));
    }
}
