package org.catrobat.catroid.content;

import android.os.SystemClock;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.eventids.WhenConditionEventId;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.InterpretationException;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/ConditionScriptTrigger.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ConditionScriptTrigger {
    static final int ALREADY_TRIGGERED = 1;
    static final long EVALUATE_AND_TRIGGER_ACTIONS_THRESHOLD = 200;
    private static final int FRAMES_TO_SKIP = 3;
    private static final String TAG = "ConditionScriptTrigger";
    static final int TRIGGER_NOW = 0;
    private final Formula formula;
    private boolean sceneFirstStart = false;
    private boolean sceneRestarted = false;
    private boolean sceneAlreadyStarted = false;
    private long startTime = 0;
    private int frameSkipCounter = 0;
    private int status = 0;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/ConditionScriptTrigger$TriggerStatus.class
      classes14.dex
     */
    @Retention(RetentionPolicy.SOURCE)
    @interface TriggerStatus {
    }

    ConditionScriptTrigger(Formula formula) {
        this.formula = formula;
    }

    void evaluateAndTriggerActions(Sprite sprite) {
        boolean zInterpretBoolean;
        try {
            Scope scope = new Scope(ProjectManager.getInstance().getCurrentProject(), sprite, null);
            if (this.sceneFirstStart || this.sceneRestarted) {
                if (this.startTime == 0) {
                    this.startTime = SystemClock.uptimeMillis();
                } else if (SystemClock.uptimeMillis() - this.startTime >= EVALUATE_AND_TRIGGER_ACTIONS_THRESHOLD) {
                    if (this.sceneFirstStart) {
                        this.sceneFirstStart = false;
                    } else if (this.sceneRestarted) {
                        this.sceneRestarted = false;
                    }
                    this.startTime = 0L;
                    zInterpretBoolean = this.formula.interpretBoolean(scope);
                }
                zInterpretBoolean = false;
            } else {
                zInterpretBoolean = this.formula.interpretBoolean(scope);
            }
            if (zInterpretBoolean) {
                triggerScript(sprite);
            } else {
                this.status = 0;
            }
        } catch (InterpretationException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
    }

    private void triggerScript(Sprite sprite) {
        if (this.status == 0) {
            sprite.look.fire(new EventWrapper(new WhenConditionEventId(this.formula), false));
            this.status = 1;
        }
    }

    public void updateSceneFirstStart() {
        this.sceneFirstStart = true;
        this.sceneAlreadyStarted = true;
    }

    public void resetStartTimeIfSceneRestarted() {
        if (this.sceneAlreadyStarted) {
            this.startTime = 0L;
            this.sceneRestarted = true;
            this.sceneFirstStart = false;
        }
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof ConditionScriptTrigger) {
            return this.formula.equals(((ConditionScriptTrigger) o).formula);
        }
        return false;
    }

    public int hashCode() {
        return this.formula.hashCode();
    }
}
