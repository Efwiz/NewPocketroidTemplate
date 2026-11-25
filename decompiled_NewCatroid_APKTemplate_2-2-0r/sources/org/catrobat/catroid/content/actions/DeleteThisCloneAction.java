package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/DeleteThisCloneAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class DeleteThisCloneAction extends TemporalAction {
    private Sprite sprite;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float percent) {
        if (this.sprite == null) {
            return;
        }
        StageActivity.getActiveStageListener().removeClonedSpriteFromStage(this.sprite);
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
