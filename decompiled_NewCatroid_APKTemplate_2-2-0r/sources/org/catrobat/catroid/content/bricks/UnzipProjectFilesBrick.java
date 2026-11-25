package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/UnzipProjectFilesBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class UnzipProjectFilesBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_unzip_project_files;
    }

    public UnzipProjectFilesBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_unzip_project_files_edit_archive_name);
    }

    public UnzipProjectFilesBrick(String archiveName) {
        this(new Formula(archiveName));
    }

    public UnzipProjectFilesBrick(Formula archiveName) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, archiveName);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createUnzipProjectFilesAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1)));
    }
}
