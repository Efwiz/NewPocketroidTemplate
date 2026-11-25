package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/LoadNativeModuleBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class LoadNativeModuleBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_python_load_native_module;
    }

    public LoadNativeModuleBrick() {
        addAllowedBrickField(Brick.BrickField.FILE_NAME, R.id.brick_python_module_file_name_text);
    }

    public LoadNativeModuleBrick(String fileName) {
        this(new Formula(fileName));
    }

    public LoadNativeModuleBrick(Formula fileName) {
        this();
        setFormulaWithBrickField(Brick.BrickField.FILE_NAME, fileName);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createLoadNativeModuleAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.FILE_NAME)));
    }
}
