package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.TextActor;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetTextAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetTextAction extends TemporalAction {
    private TextActor actor;
    private Formula endX;
    private Formula endY;
    private Scope scope;
    private Formula text;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void begin() {
        try {
            this.actor = new TextActor(this.text.interpretString(this.scope), this.endX.interpretInteger(this.scope).intValue(), this.endY.interpretInteger(this.scope).intValue());
            StageActivity.getActiveStageListener().addActor(this.actor);
        } catch (InterpretationException e) {
            Log.e(getClass().getSimpleName(), Log.getStackTraceString(e));
        }
    }

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        try {
            String strInterpretString = this.text.interpretString(this.scope);
            int iIntValue = this.endX.interpretInteger(this.scope).intValue();
            int iIntValue2 = this.endY.interpretInteger(this.scope).intValue();
            this.actor.setText(strInterpretString);
            this.actor.setPosX(iIntValue);
            this.actor.setPosY(iIntValue2);
        } catch (InterpretationException e) {
            Log.e(getClass().getSimpleName(), Log.getStackTraceString(e));
        }
    }

    public void setPosition(Formula x, Formula y) {
        this.endX = x;
        this.endY = y;
    }

    public void setText(Formula text) {
        this.text = text;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }
}
