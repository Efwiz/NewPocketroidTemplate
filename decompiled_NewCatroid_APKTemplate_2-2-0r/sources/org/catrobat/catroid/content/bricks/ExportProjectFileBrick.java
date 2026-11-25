package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/ExportProjectFileBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ExportProjectFileBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_export_project_file;
    }

    public ExportProjectFileBrick() {
        addAllowedBrickField(Brick.BrickField.NAME, R.id.brick_export_file_source_edit_text);
    }

    public ExportProjectFileBrick(String fileName) {
        this(new Formula(fileName));
    }

    public ExportProjectFileBrick(Formula fileName) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NAME, fileName);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createExportProjectFileAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NAME)));
    }
}
