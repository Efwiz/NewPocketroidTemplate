package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/LoadSceneBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class LoadSceneBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_load_scene;
    }

    public LoadSceneBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE, R.id.brick_load_scene_edit_text);
    }

    public LoadSceneBrick(String fileName) {
        this(new Formula(fileName));
    }

    public LoadSceneBrick(Formula fileNameFormula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE, fileNameFormula);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createLoadSceneAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE)));
    }
}
