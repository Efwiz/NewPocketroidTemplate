package org.catrobat.catroid.content.bricks;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ScriptSequenceAction;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.utils.Utils;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/bricks/GoNStepsBackBrick.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class GoNStepsBackBrick extends FormulaBrick {
    private static final long serialVersionUID = 1;

    @Override // org.catrobat.catroid.content.bricks.BrickBaseType
    public int getViewResource() {
        return R.layout.brick_go_back;
    }

    public GoNStepsBackBrick() {
        addAllowedBrickField(Brick.BrickField.STEPS, R.id.brick_go_back_edit_text);
    }

    public GoNStepsBackBrick(int steps) {
        this(new Formula(Integer.valueOf(steps)));
    }

    public GoNStepsBackBrick(Formula formula) {
        this();
        setFormulaWithBrickField(Brick.BrickField.STEPS, formula);
    }

    @Override // org.catrobat.catroid.content.bricks.FormulaBrick, org.catrobat.catroid.content.bricks.BrickBaseType, org.catrobat.catroid.content.bricks.Brick
    public View getView(Context context) {
        super.getView(context);
        TextView textView = (TextView) this.view.findViewById(R.id.brick_go_back_layers_text_view);
        if (getFormulaWithBrickField(Brick.BrickField.STEPS).isNumber()) {
            try {
                ProjectManager projectManager = ProjectManager.getInstance();
                textView.setText(this.view.getResources().getQuantityString(R.plurals.brick_go_back_layer_plural, Utils.convertDoubleToPluralInteger(getFormulaWithBrickField(Brick.BrickField.STEPS).interpretDouble(new Scope(projectManager.getCurrentProject(), projectManager.getCurrentSprite(), null)).doubleValue())));
            } catch (InterpretationException e) {
                Log.d(getClass().getSimpleName(), "Couldn't interpret Formula.", e);
            }
        } else {
            textView.setText(this.view.getResources().getQuantityString(R.plurals.brick_go_back_layer_plural, Utils.TRANSLATION_PLURAL_OTHER_INTEGER));
        }
        return this.view;
    }

    @Override // org.catrobat.catroid.content.bricks.Brick
    public void addActionToSequence(Sprite sprite, ScriptSequenceAction sequence) {
        sequence.addAction(sprite.getActionFactory().createGoNStepsBackAction(sprite, sequence, getFormulaWithBrickField(Brick.BrickField.STEPS)));
    }
}
