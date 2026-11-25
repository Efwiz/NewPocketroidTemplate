package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/ExtractFileBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ExtractFileBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_extract_file;
    }

    public ExtractFileBrick() {
        addAllowedBrickField(Brick.BrickField.TEXT_1, R.id.brick_extract_edit_apk);
        addAllowedBrickField(Brick.BrickField.TEXT_2, R.id.brick_extract_edit_inner);
        addAllowedBrickField(Brick.BrickField.TEXT_3, R.id.brick_extract_edit_dest);
    }

    public ExtractFileBrick(String apk, String inner, String dest) {
        this(new Formula(apk), new Formula(inner), new Formula(dest));
    }

    public ExtractFileBrick(Formula apk, Formula inner, Formula dest) {
        this();
        setFormulaWithBrickField(Brick.BrickField.TEXT_1, apk);
        setFormulaWithBrickField(Brick.BrickField.TEXT_2, inner);
        setFormulaWithBrickField(Brick.BrickField.TEXT_3, dest);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createExtractFileAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.TEXT_1), getFormulaWithBrickField(Brick.BrickField.TEXT_2), getFormulaWithBrickField(Brick.BrickField.TEXT_3)));
    }
}
