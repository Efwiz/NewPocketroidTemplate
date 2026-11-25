package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.UserVariable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/GetZipFileNamesBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class GetZipFileNamesBrick extends UserVariableBrickWithFormula {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.UserVariableBrickWithFormula
    protected int getSpinnerId() {
        return R.id.brick_get_zip_files_variable_spinner;
    }

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_get_zip_file_names;
    }

    public GetZipFileNamesBrick() {
        addAllowedBrickField(Brick.BrickField.NAME, R.id.brick_get_zip_files_edit_text);
    }

    public GetZipFileNamesBrick(String zipFileName) {
        this(new Formula(zipFileName));
    }

    public GetZipFileNamesBrick(Formula zipFileName, UserVariable userVariable) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NAME, zipFileName);
        this.userVariable = userVariable;
    }

    public GetZipFileNamesBrick(Formula zipFileName) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NAME, zipFileName);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createGetZipFileNamesAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NAME), this.userVariable));
    }
}
