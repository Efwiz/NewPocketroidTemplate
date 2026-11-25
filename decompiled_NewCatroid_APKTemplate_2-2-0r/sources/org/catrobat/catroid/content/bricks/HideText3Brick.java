package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.CatroidApplication;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.utils.ShowTextUtils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/HideText3Brick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class HideText3Brick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_hide_text;
    }

    public HideText3Brick() {
        addAllowedBrickField(Brick.BrickField.NAME, R.id.brick_hide_text_edit_text);
    }

    public HideText3Brick(String value) {
        this(new Formula(value));
    }

    public HideText3Brick(Formula formula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.NAME, formula);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createHideText3Action(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.NAME), new ShowTextUtils.AndroidStringProvider(CatroidApplication.getAppContext())));
    }
}
