package org.catrobat.catroid.content.actions;

import android.util.Log;
import com.badlogic.gdx.scenes.scene2d.Action;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/WaitUntilAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class WaitUntilAction extends Action {
    private static final float LOOP_DELAY = 0.02f;
    private Formula condition;
    private Scope scope;
    private boolean completed = false;
    private float currentTime = 0.0f;

    public void setCondition(Formula condition) {
        this.condition = condition;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public boolean act(float delta) {
        if (this.completed) {
            return true;
        }
        float f = this.currentTime + delta;
        this.currentTime = f;
        if (f < LOOP_DELAY) {
            return false;
        }
        this.currentTime = 0.0f;
        try {
            this.completed = this.condition.interpretBoolean(this.scope);
        } catch (InterpretationException e) {
            this.completed = false;
            Log.d(getClass().getSimpleName(), "Formula interpretation for this specific Brick failed.", e);
        }
        return this.completed;
    }

    @Override // com.badlogic.gdx.scenes.scene2d.Action
    public void restart() {
        this.completed = false;
    }
}
