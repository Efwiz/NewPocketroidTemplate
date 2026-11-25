package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.raptor.ThreeDManager;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ThreedBindBoneToObjectAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ThreedBindBoneToObjectAction extends TemporalAction {
    public Formula boneName;
    public Formula modelId;
    public Scope scope;
    public Formula targetId;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        ThreeDManager threeDManager;
        StageListener activeStageListener = StageActivity.getActiveStageListener();
        if (activeStageListener == null || (threeDManager = activeStageListener.getThreeDManager()) == null) {
            return;
        }
        try {
            String strInterpretString = this.boneName.interpretString(this.scope);
            String strInterpretString2 = this.modelId.interpretString(this.scope);
            String strInterpretString3 = this.targetId.interpretString(this.scope);
            if (strInterpretString.isEmpty() || strInterpretString2.isEmpty()) {
                return;
            }
            threeDManager.bindBoneToObject(strInterpretString2, strInterpretString, strInterpretString3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
