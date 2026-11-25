package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ChangeColorByNAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ChangeColorByNAction extends TemporalAction {
    private static final String TAG = "ChangeColorByNAction";
    private Formula color;
    private Scope scope;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float delta) {
        try {
            Formula formula = this.color;
            this.scope.getSprite().look.changeColorInUserInterfaceDimensionUnit(formula == null ? 25.0f : formula.interpretFloat(this.scope).floatValue());
        } catch (InterpretationException e) {
            Log.d(TAG, "\t\tFormula interpretation for this specific Brick failed.", e);
        }
    }

    public void setColor(Formula color) {
        this.color = color;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }
}
