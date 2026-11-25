package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.PenColor;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetPenColorAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetPenColorAction extends TemporalAction {
    private Formula blue;
    private Formula green;
    private Formula red;
    private Scope scope;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float delta) {
        try {
            Formula formula = this.red;
            int iIntValue = 0;
            int iIntValue2 = formula == null ? 0 : formula.interpretInteger(this.scope).intValue();
            Formula formula2 = this.green;
            int iIntValue3 = formula2 == null ? 0 : formula2.interpretInteger(this.scope).intValue();
            Formula formula3 = this.blue;
            if (formula3 != null) {
                iIntValue = formula3.interpretInteger(this.scope).intValue();
            }
            Color color = new Color();
            Color.argb8888ToColor(color, android.graphics.Color.argb(255, iIntValue2, iIntValue3, iIntValue));
            this.scope.getSprite().penConfiguration.setPenColor(new PenColor(color.r, color.g, color.b, color.a));
        } catch (InterpretationException e) {
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", e);
        }
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public void setRed(Formula red) {
        this.red = red;
    }

    public void setGreen(Formula green) {
        this.green = green;
    }

    public void setBlue(Formula blue) {
        this.blue = blue;
    }
}
