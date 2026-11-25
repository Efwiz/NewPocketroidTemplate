package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetShadowsBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetShadowsBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_shadows;
    }

    public SetShadowsBrick() {
        addAllowedBrickField(Brick.BrickField.TEXT, R.id.brick_set_shadows_edit_text);
    }

    public SetShadowsBrick(String value) {
        this(new Formula(value));
    }

    public SetShadowsBrick(Formula formula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.TEXT, formula);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetShadowsAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.TEXT)));
    }
}
