package org.catrobat.catroid.content.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.stage.StageActivity;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/actions/StampAction.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class StampAction extends TemporalAction {
    private Sprite sprite;

    @Override // com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
    protected void update(float delta) {
        this.sprite.penConfiguration.setStamp(true);
        StageActivity.getActiveStageListener().getPenActor().stampToFrameBuffer();
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
