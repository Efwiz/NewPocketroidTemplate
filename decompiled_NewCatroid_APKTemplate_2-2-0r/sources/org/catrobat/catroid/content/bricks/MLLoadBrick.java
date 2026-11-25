package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/MLLoadBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class MLLoadBrick extends FormulaBrick {
    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_ml_file_load;
    }

    public MLLoadBrick() {
        addAllowedBrickField(Brick.BrickField.STRING, R.id.brick_ml_edit_filename);
    }

    public MLLoadBrick(String fileName) {
        this(new Formula(fileName));
    }

    public MLLoadBrick(Formula fileName) {
        this();
        setFormulaWithBrickField(Brick.BrickField.STRING, fileName);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createMLFileAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.STRING), false));
    }
}
