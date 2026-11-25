package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/CopyProjectFileBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class CopyProjectFileBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_copy_project_file;
    }

    public CopyProjectFileBrick() {
        addAllowedBrickField(Brick.BrickField.NAME, R.id.brick_copy_file_source_edit_text);
        addAllowedBrickField(Brick.BrickField.TEXT, R.id.brick_copy_file_destination_edit_text);
    }

    public CopyProjectFileBrick(String sourceFileName, String newFileName) {
        this(new Formula(sourceFileName), new Formula(newFileName));
    }

    public CopyProjectFileBrick(Formula sourceFileName, Formula newFileName) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NAME, sourceFileName);
        setFormulaWithBrickField(Brick.BrickField.TEXT, newFileName);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createCopyProjectFileAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NAME), getFormulaWithBrickField(Brick.BrickField.TEXT)));
    }
}
