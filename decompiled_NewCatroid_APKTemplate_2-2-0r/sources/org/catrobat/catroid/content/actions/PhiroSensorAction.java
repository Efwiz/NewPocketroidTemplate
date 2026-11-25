package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.FormulaElement;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.formulaeditor.Sensors;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/PhiroSensorAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class PhiroSensorAction extends Action {
    private static final int DISTANCE_THRESHOLD_VALUE = 850;
    private Action elseAction;
    private Action ifAction;
    private Formula ifCondition;
    private Boolean ifConditionValue;
    private boolean isInitialized = false;
    private boolean isInterpretedCorrectly;
    private Scope scope;
    private int sensorNumber;

    protected void begin() {
        try {
            Formula formula = this.ifCondition;
            if (formula == null) {
                this.isInterpretedCorrectly = false;
            } else {
                this.ifConditionValue = Boolean.valueOf(formula.interpretDouble(this.scope).intValue() <= DISTANCE_THRESHOLD_VALUE);
                this.isInterpretedCorrectly = true;
            }
        } catch (InterpretationException e) {
            this.isInterpretedCorrectly = false;
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", e);
        }
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        if (!this.isInitialized) {
            begin();
            this.isInitialized = true;
        }
        if (!this.isInterpretedCorrectly) {
            return true;
        }
        if (this.ifConditionValue.booleanValue()) {
            return this.ifAction.act(delta);
        }
        return this.elseAction.act(delta);
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public void restart() {
        this.ifAction.restart();
        this.elseAction.restart();
        this.isInitialized = false;
        super.restart();
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public void setIfAction(Action ifAction) {
        this.ifAction = ifAction;
    }

    public void setElseAction(Action elseAction) {
        this.elseAction = elseAction;
    }

    public void setIfCondition(Formula ifCondition) {
        this.ifCondition = ifCondition;
    }

    public void setSensor(int sensorNumber) {
        this.sensorNumber = sensorNumber;
        setIfCondition(new Formula(new FormulaElement(FormulaElement.ElementType.SENSOR, getPhiroProSensorByNumber().name(), null)));
    }

    private Sensors getPhiroProSensorByNumber() {
        int i = this.sensorNumber;
        if (i == 0) {
            return Sensors.PHIRO_FRONT_LEFT;
        }
        if (i == 1) {
            return Sensors.PHIRO_FRONT_RIGHT;
        }
        if (i == 2) {
            return Sensors.PHIRO_SIDE_LEFT;
        }
        if (i == 3) {
            return Sensors.PHIRO_SIDE_RIGHT;
        }
        if (i == 4) {
            return Sensors.PHIRO_BOTTOM_LEFT;
        }
        if (i == 5) {
            return Sensors.PHIRO_BOTTOM_RIGHT;
        }
        return Sensors.PHIRO_SIDE_RIGHT;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public void setActor(Actor actor) {
        super.setActor(actor);
        this.ifAction.setActor(actor);
        this.elseAction.setActor(actor);
    }
}
