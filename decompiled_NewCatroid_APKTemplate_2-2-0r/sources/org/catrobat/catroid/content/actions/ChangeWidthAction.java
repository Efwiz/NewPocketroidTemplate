package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ChangeWidthAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ChangeWidthAction extends TemporalAction {
    private Scope scope;
    private Formula size;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float delta) {
        try {
            Formula formula = this.size;
            this.scope.getSprite().look.setWidthV(Float.valueOf(((formula == null ? Float.valueOf(0.0f) : formula.interpretFloat(this.scope)).floatValue() / 100.0f) + this.scope.getSprite().look.getScaleX()));
        } catch (InterpretationException e) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", e);
        }
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public void setSize(Formula size) {
        this.size = size;
    }
}
