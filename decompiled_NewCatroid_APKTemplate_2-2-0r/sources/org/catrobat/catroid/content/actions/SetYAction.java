package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetYAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetYAction extends TemporalAction {
    private Scope scope;
    private Formula yPosition;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float delta) {
        try {
            Formula formula = this.yPosition;
            this.scope.getSprite().look.setYInUserInterfaceDimensionUnit((formula == null ? Float.valueOf(0.0f) : formula.interpretFloat(this.scope)).floatValue());
        } catch (InterpretationException e) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", e);
        }
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public void setY(Formula y) {
        this.yPosition = y;
    }
}
