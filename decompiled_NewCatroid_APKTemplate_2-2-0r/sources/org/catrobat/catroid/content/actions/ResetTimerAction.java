package org.catrobat.catroid.content.actions;

import android.os.SystemClock;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.formulaeditor.SensorHandler;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ResetTimerAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ResetTimerAction extends TemporalAction {
    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        SensorHandler.timerReferenceValue = SystemClock.uptimeMillis();
    }
}
