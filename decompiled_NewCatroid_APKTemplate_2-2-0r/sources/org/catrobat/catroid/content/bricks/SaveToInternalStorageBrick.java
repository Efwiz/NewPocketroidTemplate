package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SaveToInternalStorageBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SaveToInternalStorageBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_save_to_internal;
    }

    public SaveToInternalStorageBrick() {
        addAllowedBrickField(Brick.BrickField.PROJECT_FILE_NAME, R.id.brick_save_to_internal_project_file);
        addAllowedBrickField(Brick.BrickField.FILE_NAME, R.id.brick_save_to_internal_path);
    }

    public SaveToInternalStorageBrick(String projectFileName, String internalPath) {
        this(new Formula(projectFileName), new Formula(internalPath));
    }

    public SaveToInternalStorageBrick(Formula projectFileName, Formula internalPath) {
        this();
        setFormulaWithBrickField(Brick.BrickField.PROJECT_FILE_NAME, projectFileName);
        setFormulaWithBrickField(Brick.BrickField.FILE_NAME, internalPath);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSaveToInternalStorageAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.PROJECT_FILE_NAME), getFormulaWithBrickField(Brick.BrickField.FILE_NAME)));
    }
}
