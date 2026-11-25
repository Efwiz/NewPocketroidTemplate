package org.catrobat.catroid.content.bricks;

import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/BroadcastWithParamsBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class BroadcastWithParamsBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_broadcast_with_params;
    }

    public BroadcastWithParamsBrick() {
        addAllowedBrickField(Brick.BrickField.VALUE_1, R.id.brick_broadcast_with_params_edit_signal);
        addAllowedBrickField(Brick.BrickField.VALUE_2, R.id.brick_broadcast_with_params_edit_data);
    }

    public BroadcastWithParamsBrick(String signal, String data) {
        this(new Formula(signal), new Formula(data));
    }

    public BroadcastWithParamsBrick(Formula signal, Formula data) {
        this();
        setFormulaWithBrickField(Brick.BrickField.VALUE_1, signal);
        setFormulaWithBrickField(Brick.BrickField.VALUE_2, data);
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createBroadcastWithParamsAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.VALUE_1), getFormulaWithBrickField(Brick.BrickField.VALUE_2)));
    }
}
