package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.stage.ShowBubbleActor;
import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/SetVisibleAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class SetVisibleAction extends TemporalAction {
    private Sprite sprite;
    private boolean visible;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float delta) {
        ShowBubbleActor bubbleActorForSprite;
        this.sprite.look.setLookVisible(this.visible);
        if (StageActivity.getActiveStageListener() == null || (bubbleActorForSprite = StageActivity.getActiveStageListener().getBubbleActorForSprite(this.sprite)) == null) {
            return;
        }
        bubbleActorForSprite.setVisible(this.visible);
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
