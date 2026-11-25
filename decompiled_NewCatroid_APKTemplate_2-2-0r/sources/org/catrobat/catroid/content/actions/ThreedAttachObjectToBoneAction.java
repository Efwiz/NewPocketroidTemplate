package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.stage.StageActivity;
import org.catrobat.catroid.stage.StageListener;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/ThreedAttachObjectToBoneAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class ThreedAttachObjectToBoneAction extends TemporalAction {
    public Formula boneName;
    public Formula childId;
    public Formula offsetX;
    public Formula offsetY;
    public Formula offsetZ;
    public Formula parentId;
    public Scope scope;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        StageListener activeStageListener = StageActivity.getActiveStageListener();
        if (activeStageListener == null || activeStageListener.sceneManager == null) {
            return;
        }
        try {
            String strInterpretString = this.childId.interpretString(this.scope);
            String strInterpretString2 = this.parentId.interpretString(this.scope);
            String strInterpretString3 = this.boneName.interpretString(this.scope);
            float fFloatValue = this.offsetX.interpretFloat(this.scope).floatValue();
            float fFloatValue2 = this.offsetY.interpretFloat(this.scope).floatValue();
            float fFloatValue3 = this.offsetZ.interpretFloat(this.scope).floatValue();
            if (strInterpretString.isEmpty() || strInterpretString2.isEmpty() || strInterpretString3.isEmpty()) {
                return;
            }
            activeStageListener.sceneManager.attachObjectToBone(strInterpretString, strInterpretString2, strInterpretString3, fFloatValue, fFloatValue2, fFloatValue3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
