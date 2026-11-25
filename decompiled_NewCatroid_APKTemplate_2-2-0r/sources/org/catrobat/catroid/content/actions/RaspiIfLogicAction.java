package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.devices.raspberrypi.RPiSocketConnection;
import org.catrobat.catroid.devices.raspberrypi.RaspberryPiService;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/RaspiIfLogicAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class RaspiIfLogicAction extends Action {
    private static final String TAG = "RaspiIfLogicAction";
    private Action elseAction;
    private Action ifAction;
    private boolean isInitialized = false;
    private int pin;
    private Formula pinNumber;
    private Scope scope;

    public void setPinNumber(Formula pinNumber) {
        this.pinNumber = pinNumber;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [int] */
    /* JADX WARN: Type inference failed for: r0v2, types: [java.lang.Integer] */
    /* JADX WARN: Type inference failed for: r0v3, types: [java.lang.Integer] */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v8 */
    /* JADX WARN: Type inference failed for: r0v9 */
    protected void begin() {
        ?? ValueOf = 0;
        try {
            Formula formula = this.pinNumber;
            ValueOf = formula == null ? 0 : formula.interpretInteger(this.scope);
        } catch (InterpretationException e) {
            ValueOf = Integer.valueOf((int) ValueOf);
            Log.e(TAG, "Formula interpretation for this specific Brick failed.", e);
        }
        this.pin = ValueOf.intValue();
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        if (!this.isInitialized) {
            begin();
            this.isInitialized = true;
        }
        if (readIfConditionValue()) {
            return this.ifAction.act(delta);
        }
        return this.elseAction.act(delta);
    }

    protected boolean readIfConditionValue() {
        RPiSocketConnection rPiSocketConnection = RaspberryPiService.getInstance().connection;
        try {
            Log.d(TAG, "RPi get " + this.pin);
            return rPiSocketConnection.getPin(this.pin);
        } catch (Exception e) {
            Log.e(TAG, "RPi: exception during getPin: " + e);
            return false;
        }
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

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public void setActor(Actor actor) {
        super.setActor(actor);
        this.ifAction.setActor(actor);
        this.elseAction.setActor(actor);
    }
}
