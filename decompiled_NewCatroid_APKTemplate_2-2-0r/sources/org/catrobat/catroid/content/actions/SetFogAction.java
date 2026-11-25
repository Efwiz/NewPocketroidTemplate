package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetFogAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetFogAction extends TemporalAction {
    public Formula blue;
    public Formula density;
    public Formula green;
    public Formula red;
    public Scope scope;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        if (StageActivity.getActiveStageListener() == null || StageActivity.getActiveStageListener().getThreeDManager() == null) {
            return;
        }
        try {
            StageActivity.getActiveStageListener().getThreeDManager().setFog(this.red.interpretFloat(this.scope).floatValue(), this.green.interpretFloat(this.scope).floatValue(), this.blue.interpretFloat(this.scope).floatValue(), this.density.interpretFloat(this.scope).floatValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
