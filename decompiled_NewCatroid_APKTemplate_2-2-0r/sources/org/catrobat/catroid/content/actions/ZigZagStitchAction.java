package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.embroidery.ZigZagRunningStitch;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ZigZagStitchAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ZigZagStitchAction extends TemporalAction {
    private Formula length;
    private Scope scope;
    private Formula width;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float delta) {
        float fFloatValue;
        float fFloatValue2 = 0.0f;
        try {
            Formula formula = this.length;
            fFloatValue = formula != null ? formula.interpretFloat(this.scope).floatValue() : 0.0f;
        } catch (InterpretationException e) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", e);
        }
        try {
            Formula formula2 = this.width;
            if (formula2 != null) {
                fFloatValue2 = formula2.interpretFloat(this.scope).floatValue();
            }
        } catch (InterpretationException e2) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", e2);
        }
        this.scope.getSprite().runningStitch.activateStitching(this.scope.getSprite(), new ZigZagRunningStitch(this.scope.getSprite(), fFloatValue, fFloatValue2));
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public void setLength(Formula length) {
        this.length = length;
    }

    public void setWidth(Formula width) {
        this.width = width;
    }
}
