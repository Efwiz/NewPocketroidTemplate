package org.catrobat.catroid.content.actions;

import android.util.Log;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.formulaeditor.UserVariable;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ForVariableFromToAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ForVariableFromToAction extends LoopAction {
    private UserVariable controlVariable;
    private Formula from;
    private int fromValue;
    private Scope scope;
    private Formula to;
    private int toValue;
    private boolean isCurrentLoopInitialized = false;
    private boolean isRepeatActionInitialized = false;
    private int step = 1;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.RepeatAction, com.badlogic.gdx.scenes.scene2d.actions.DelegateAction
    public boolean delegate(float delta) {
        if (!this.isRepeatActionInitialized && !interpretParameters()) {
            return true;
        }
        if (!this.isCurrentLoopInitialized) {
            setCurrentTime(0.0f);
            this.isCurrentLoopInitialized = true;
        }
        setCurrentTime(getCurrentTime() + delta);
        if (this.action != null && this.action.act(delta) && !isLoopDelayNeeded()) {
            if (!(this.controlVariable.getValue() instanceof Double) || ((this.step > 0 && ((Double) this.controlVariable.getValue()).doubleValue() >= this.toValue) || (this.step < 0 && ((Double) this.controlVariable.getValue()).doubleValue() <= this.toValue))) {
                return true;
            }
            changeControlVariable(this.step);
            this.isCurrentLoopInitialized = false;
            this.action.restart();
        }
        return false;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.RepeatAction, com.badlogic.gdx.scenes.scene2d.actions.DelegateAction, com.badlogic.gdx.scenes.scene2d.Action
    public void restart() {
        this.isCurrentLoopInitialized = false;
        this.isRepeatActionInitialized = false;
        super.restart();
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public void setRange(Formula from, Formula to) {
        this.from = from;
        this.to = to;
    }

    public void setControlVariable(UserVariable variable) {
        this.controlVariable = variable;
    }

    private boolean interpretParameters() {
        this.isRepeatActionInitialized = true;
        try {
            Formula formula = this.from;
            this.fromValue = (formula == null ? Double.valueOf(0.0d) : formula.interpretDouble(this.scope)).intValue();
            Formula formula2 = this.to;
            this.toValue = (formula2 == null ? Double.valueOf(0.0d) : formula2.interpretDouble(this.scope)).intValue();
            setStepValue();
            setControlVariable(this.fromValue);
            return true;
        } catch (InterpretationException e) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", e);
            return false;
        }
    }

    private void setStepValue() {
        this.step = this.fromValue <= this.toValue ? 1 : -1;
    }

    private void setControlVariable(int value) {
        this.controlVariable.setValue(Double.valueOf(value));
    }

    private void changeControlVariable(int value) {
        UserVariable userVariable = this.controlVariable;
        userVariable.setValue(Double.valueOf(((Double) userVariable.getValue()).doubleValue() + ((double) value)));
    }
}
