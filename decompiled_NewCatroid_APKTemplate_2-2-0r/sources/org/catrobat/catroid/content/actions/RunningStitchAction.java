package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.embroidery.SimpleRunningStitch;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/RunningStitchAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class RunningStitchAction extends TemporalAction {
    private Formula length;
    private Scope scope;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float delta) {
        int iIntValue = 0;
        try {
            Formula formula = this.length;
            if (formula != null) {
                iIntValue = formula.interpretInteger(this.scope).intValue();
            }
        } catch (InterpretationException e) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", e);
        }
        this.scope.getSprite().runningStitch.activateStitching(this.scope.getSprite(), new SimpleRunningStitch(this.scope.getSprite(), iIntValue));
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public void setLength(Formula length) {
        this.length = length;
    }
}
