package org.catrobat.catroid.content.actions;

import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/WaitForBubbleBrickAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class WaitForBubbleBrickAction extends WaitAction {
    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void end() {
        if (StageActivity.getActiveStageListener().getBubbleActorForSprite(this.scope.getSprite()) != null) {
            StageActivity.getActiveStageListener().removeBubbleActorForSprite(this.scope.getSprite());
        }
    }
}
