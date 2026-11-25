package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import java.io.File;
import org.catrobat.catroid.content.Scope;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.raptor.ThreeDManager;
import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetObjectTextureAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetObjectTextureAction extends TemporalAction {
    public Formula objectId;
    public Scope scope;
    public Formula textureName;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        ThreeDManager threeDManager = StageActivity.getActiveStageListener().getThreeDManager();
        if (threeDManager == null) {
            return;
        }
        try {
            String strInterpretString = this.objectId.interpretString(this.scope);
            String strInterpretString2 = this.textureName.interpretString(this.scope);
            if (!strInterpretString.isEmpty() && !strInterpretString2.isEmpty()) {
                if (strInterpretString2.startsWith("buffer://")) {
                    threeDManager.setObjectTexture(strInterpretString, strInterpretString2);
                } else {
                    File file = this.scope.getProject().getFile(strInterpretString2);
                    if (file != null && file.exists()) {
                        threeDManager.setObjectTexture(strInterpretString, file.getAbsolutePath());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
