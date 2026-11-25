package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.view.View;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/SetTempoBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetTempoBrick extends FormulaBrick {
    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_set_tempo;
    }

    public SetTempoBrick() {
        addAllowedBrickField(Brick.BrickField.TEMPO, R.id.brick_set_tempo_edit_text);
    }

    public SetTempoBrick(int tempo) {
        this(new Formula(Integer.valueOf(tempo)));
    }

    public SetTempoBrick(Formula formula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.TEMPO, formula);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        return this.view;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createSetTempoAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.TEMPO)));
    }
}
